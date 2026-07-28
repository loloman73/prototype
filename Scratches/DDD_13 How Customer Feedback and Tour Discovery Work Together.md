### Question:
how do they work together the Customer Feedback subdomain and the Tour Discovery subdomain?

### How Customer Feedback and Tour Discovery Work Together

These two subdomains have a **producer-consumer relationship** where Feedback produces data that Discovery consumes 
to improve search and recommendations.

---

### The Integration Flow

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                             │
│  CUSTOMER FEEDBACK CONTEXT (Supporting)                                     │
│  ──────────────────────────────────────                                     │
│                                                                             │
│  Customer submits:                                                          │
│  • Review (text + rating 1-5)                                               │
│  • Like / Unlike                                                            │
│                                                                             │
│  Aggregates:                                                                │
│  • Review (Aggregate Root)                                                  │
│    ├─ ReviewId                                                              │
│    ├─ CustomerId                                                            │
│    ├─ TourId                                                                │
│    ├─ Rating (1-5)                                                          │
│    ├─ Comment (text)                                                        │
│    └─ CreatedAt                                                             │
│                                                                             │
│  Domain Events Emitted:                                                     │
│  • ReviewSubmitted { tourId, rating, customerId }                           │
│  • TourLiked { tourId, customerId }                                         │
│  • TourUnliked { tourId, customerId }                                       │
│                                                                             │
└──────────────────────────┬──────────────────────────────────────────────────┘
                           │
                           │ Domain Events (async)
                           │
                           ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                             │
│  TOUR DISCOVERY CONTEXT (Core)                                              │
│  ─────────────────────────────                                              │
│                                                                             │
│  Listens to Feedback events and maintains a READ MODEL:                     │
│                                                                             │
│  TourSearchReadModel                                                        │
│  ├─ TourId                                                                  │
│  ├─ Title, Description                                                      │
│  ├─ AverageRating ◄──── calculated from ReviewSubmitted events              │
│  ├─ TotalReviews  ◄──── incremented on each ReviewSubmitted                 │
│  ├─ TotalLikes    ◄──── incremented/decremented on Like/Unlike              │
│  ├─ Languages, Topics, AgeGroups, Duration                                  │
│  ├─ AccessibilityTags                                                       │
│  └─ Location                                                                │
│                                                                             │
│  Uses this data for:                                                        │
│  • Sort by "Best Rated"                                                     │
│  • Sort by "Most Popular" (likes)                                           │
│  • Filter: "4+ stars only"                                                  │
│  • "Recommended for you" (combines with Customer Profile preferences)       │
│  • Display rating/likes on search results                                   │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

### The Mechanism: Event-Driven Integration

#### Step 1: Customer Submits a Review (Feedback Context)

```kotlin
// Feedback Context - Application Service
@Service
class SubmitReviewHandler(
    private val reviewRepository: ReviewRepository,
    private val eventPublisher: DomainEventPublisher
) {
    @Transactional
    fun handle(command: SubmitReviewCommand) {
        val review = Review.create(
            customerId = command.customerId,
            tourId = command.tourId,
            rating = command.rating,
            comment = command.comment
        )
        reviewRepository.save(review)
        
        eventPublisher.publish(
            ReviewSubmitted(
                tourId = review.tourId,
                rating = review.rating,
                customerId = review.customerId,
                occurredAt = Instant.now()
            )
        )
    }
}
```

#### Step 2: Tour Discovery Listens and Updates Read Model

```kotlin
// Discovery Context - Event Listener
@Component
class FeedbackEventListener(
    private val tourSearchReadModelRepository: TourSearchReadModelRepository
) {
    @EventListener
    fun onReviewSubmitted(event: ReviewSubmitted) {
        val readModel = tourSearchReadModelRepository.findByTourId(event.tourId)
        readModel?.let {
            it.totalReviews += 1
            it.averageRating = recalculateAverage(it, event.rating)
            tourSearchReadModelRepository.save(it)
        }
    }
    
    @EventListener
    fun onTourLiked(event: TourLiked) {
        val readModel = tourSearchReadModelRepository.findByTourId(event.tourId)
        readModel?.let {
            it.totalLikes += 1
            tourSearchReadModelRepository.save(it)
        }
    }
    
    @EventListener
    fun onTourUnliked(event: TourUnliked) {
        val readModel = tourSearchReadModelRepository.findByTourId(event.tourId)
        readModel?.let {
            it.totalLikes = maxOf(0, it.totalLikes - 1)
            tourSearchReadModelRepository.save(it)
        }
    }
}
```

#### Step 3: Discovery Uses the Data for Search

```kotlin
// Discovery Context - Search Service
@Service
class TourSearchService(
    private val readModelRepository: TourSearchReadModelRepository
) {
    fun search(criteria: SearchCriteria): List<TourSearchResult> {
        return readModelRepository.findByCriteria(
            languages = criteria.languages,
            topics = criteria.topics,
            minRating = criteria.minRating,        // ◄── From Feedback data
            sortBy = criteria.sortBy               // ◄── "BEST_RATED", "MOST_LIKED"
        )
    }
}
```

---

### Why This Pattern (Not Direct Queries)?

| Approach                                            | Problem                                             |
|-----------------------------------------------------|-----------------------------------------------------|
| ❌ Discovery queries Feedback DB directly            | Tight coupling, violates bounded context boundaries |
| ❌ Discovery calls Feedback API synchronously        | Performance bottleneck on every search              |
| ✅ Discovery maintains its own read model via events | Decoupled, fast queries, eventual consistency       |

This is a classic **CQRS pattern**: Feedback is the **write side**, Discovery's read model is the **read side**, 
and **domain events** synchronize them.

---

### Complete Integration Map

```
                    FEEDBACK CONTEXT
                    ────────────────
                    
  Customer ──► SubmitReview ──► Review (saved)
                                    │
                                    │ emits ReviewSubmitted
                                    ▼
               ┌────────────────────────────────────────┐
               │          EVENT BUS                      │
               │                                         │
               │  ReviewSubmitted { tourId, rating }     │
               │  TourLiked { tourId }                   │
               │  TourUnliked { tourId }                 │
               └───────┬──────────────────┬──────────────┘
                       │                  │
                       ▼                  ▼
              TOUR DISCOVERY        CUSTOMER PROFILE
              CONTEXT               CONTEXT
              ──────────────        ────────────────
              
              Updates:              Updates:
              • AverageRating       • Review History
              • TotalReviews        • Reviewed Tours
              • TotalLikes
              
              Enables:
              • Sort by rating
              • Filter by min rating
              • "Most popular" sort
              • Display stars ⭐⭐⭐⭐
```

---

### What Each Context Owns

| Aspect          | Feedback Context                     | Tour Discovery Context                      |
|-----------------|--------------------------------------|---------------------------------------------|
| **Owns**        | Individual reviews, ratings, likes   | Aggregated scores (avg rating, total likes) |
| **Stores**      | Full review text, who wrote it, when | Only numeric summaries per tour             |
| **Answers**     | "Show me all reviews for Tour X"     | "Find tours with 4+ stars sorted by rating" |
| **Updates**     | When customer submits/edits review   | When it receives Feedback events            |
| **Consistency** | Immediate (write model)              | Eventual (read model, slight delay)         |

---

### Summary

```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃          FEEDBACK ↔ DISCOVERY INTEGRATION                                  ┃
┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫
┃                                                                            ┃
┃  Pattern:     Event-Driven (CQRS)                                          ┃
┃  Direction:   Feedback (upstream) → Discovery (downstream)                 ┃
┃  Events:      ReviewSubmitted, TourLiked, TourUnliked                      ┃
┃  Discovery:   Maintains aggregated read model (avg rating, total likes)    ┃
┃  Consistency: Eventual (acceptable for search/recommendations)             ┃
┃  Benefit:     Decoupled contexts, fast search queries                      ┃
┃                                                                            ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
```

The key insight: **Feedback owns the truth** (individual reviews), **Discovery owns a projection** (aggregated scores). 
They communicate through domain events, keeping both contexts independent and focused on their own responsibility.
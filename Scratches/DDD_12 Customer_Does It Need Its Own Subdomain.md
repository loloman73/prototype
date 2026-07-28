### Question
what about the Customer? Does it need a Subdomain to manage the Customer profile functionality? 
The Customer has a history of purchases and preferences that may affect the search and recommendations.

### Customer: Does It Need Its Own Subdomain?

This is a nuanced question. Let's analyze based on what you've described.

---

### Customer Appears in Multiple Contexts

| Context             | What "Customer" Means                 | Data Needed                       |
|---------------------|---------------------------------------|-----------------------------------|
| **Identity**        | A user account with login credentials | Email, password, role             |
| **Purchasing**      | A buyer who places orders             | Payment methods, billing address  |
| **Tour Discovery**  | A searcher with preferences           | Preferences, search history       |
| **Recommendations** | A profile for personalization         | Purchase history, likes, behavior |
| **Feedback**        | A reviewer who rates tours            | Reviews written, ratings given    |

This is the same pattern we saw with Tour Guide — **Customer exists differently in multiple bounded contexts**.

---

### Analysis: Subdomain or Not?

| Question                                                | Answer                                   | Implication              |
|---------------------------------------------------------|------------------------------------------|--------------------------|
| Does Customer have unique business rules?               | Yes — preferences affect recommendations | Some domain logic        |
| Does it have its own ubiquitous language?               | Partially — "preferences", "history"     | Could be a context       |
| Would a domain expert talk about "Customer Management"? | Maybe — depends on business focus        | Borderline               |
| Is there a "Customer" aggregate with behavior?          | Yes — preferences, history tracking      | Has domain logic         |
| Could a separate team own this?                         | Possibly — personalization team          | Could justify separation |

---

### The Key Factor: How Complex is Personalization?

#### Scenario A: Simple Preferences (No Subdomain Needed)
```
Customer just stores:
• Preferred languages
• Preferred topics
• Favorite age groups
• Purchase history (list of Tour IDs)

→ This is just data, no complex behavior
→ Keep as part of Tour Discovery or Purchasing context
```

#### Scenario B: Complex Personalization (Subdomain Justified)
```
Customer involves:
• ML-based recommendations
• Behavior tracking (views, clicks, time spent)
• A/B testing of recommendations
• Personalization algorithms
• Customer segmentation

→ This is a separate business capability
→ Deserves its own subdomain
```

---

### Recommended Approach for Your Platform

Based on your description ("history of purchases and preferences that may affect search and recommendations"), I recommend a **lightweight Customer Profile context** that feeds into Tour Discovery:

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                             │
│  IDENTITY CONTEXT (Generic)                                                 │
│  ──────────────────────────                                                 │
│  ┌─────────────────────┐                                                    │
│  │ User Account        │                                                    │
│  │ • UserId            │                                                    │
│  │ • Email             │                                                    │
│  │ • Password          │                                                    │
│  │ • Role (CUSTOMER)   │                                                    │
│  └──────────┬──────────┘                                                    │
│             │ shared identity                                               │
└─────────────┼───────────────────────────────────────────────────────────────┘
              │
              ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  CUSTOMER PROFILE CONTEXT (Supporting Subdomain)                            │
│  ───────────────────────────────────────────────                            │
│                                                                             │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │ CustomerProfile (Aggregate Root)                                     │   │
│  │                                                                      │   │
│  │ • CustomerId (= UserId)                                              │   │
│  │ • Preferences                                                        │   │
│  │   ├─ PreferredLanguages: List<LanguageId>                            │   │
│  │   ├─ PreferredTopics: List<TopicId>                                  │   │
│  │   ├─ PreferredAgeGroups: List<AgeGroupId>                            │   │
│  │   └─ AccessibilityNeeds: List<AccessibilityTagId>                    │   │
│  │ • PurchaseHistory: List<PurchaseRecord>                              │   │
│  │   ├─ TourId                                                          │   │
│  │   ├─ PurchasedAt                                                     │   │
│  │   └─ Downloaded: Boolean                                             │   │
│  │ • Favorites: List<TourId>                                            │   │
│  │ • RecentlyViewed: List<TourId>                                       │   │
│  │                                                                      │   │
│  │ Behavior:                                                            │   │
│  │   + updatePreferences(preferences)                                   │   │
│  │   + recordPurchase(tourId)                                           │   │
│  │   + addToFavorites(tourId)                                           │   │
│  │   + recordView(tourId)                                               │   │
│  │   + getRecommendationProfile(): RecommendationProfile                │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                                                                             │
│  Domain Events:                                                             │
│  • PreferencesUpdated                                                       │
│  • TourPurchased (listened from Purchasing)                                 │
│  • TourFavorited                                                            │
│  • TourViewed                                                               │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
              │
              │ provides RecommendationProfile
              ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  TOUR DISCOVERY CONTEXT (Core Subdomain)                                    │
│  ───────────────────────────────────────                                    │
│                                                                             │
│  Uses CustomerProfile data for:                                             │
│  • Personalized search results                                              │
│  • "Recommended for you" section                                            │
│  • "Because you liked X" suggestions                                        │
│  • Filtering by accessibility needs                                         │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

### Updated Subdomain Classification

#### 🔴 CORE Subdomains
| Subdomain           | Description                                             |
|---------------------|---------------------------------------------------------|
| **Tour Management** | Tours, Tour Points, Audio Guides, Tour Guide profiles   |
| **Tour Discovery**  | Search, filtering, recommendations (uses Customer data) |

#### 🟡 SUPPORTING Subdomains
| Subdomain             | Description                               |
|-----------------------|-------------------------------------------|
| **Customer Profile**  | **NEW** — Preferences, history, favorites |
| **Content Approval**  | Approval workflows                        |
| **Customer Feedback** | Reviews, ratings                          |
| **Geolocation**       | Coordinate validation                     |

#### ⚪ GENERIC Subdomains
| Subdomain          | Description                                 |
|--------------------|---------------------------------------------|
| **Identity**       | User accounts (Customer, Tour Guide, Admin) |
| **Purchasing**     | Shopping cart, orders, entitlements         |
| **Payment**        | Transactions                                |
| **Administration** | Reference data                              |
| **Support**        | Tickets                                     |
| **Finance**        | Revenue, reports                            |

---

### Context Relationships

```
┌──────────────┐     ┌──────────────┐     ┌──────────────┐
│   Identity   │     │  Purchasing  │     │   Feedback   │
│   Context    │     │   Context    │     │   Context    │
└──────┬───────┘     └──────┬───────┘     └──────┬───────┘
       │                    │                    │
       │ UserId             │ TourPurchased      │ ReviewSubmitted
       │                    │ event              │ event
       ▼                    ▼                    ▼
┌─────────────────────────────────────────────────────────┐
│              CUSTOMER PROFILE CONTEXT                   │
│                                                         │
│  Listens to events, maintains unified customer view     │
│                                                         │
│  CustomerProfile                                        │
│  • Preferences                                          │
│  • Purchase History (from Purchasing events)            │
│  • Review History (from Feedback events)                │
│  • Favorites, Recently Viewed                           │
└────────────────────────┬────────────────────────────────┘
                         │
                         │ RecommendationProfile
                         ▼
              ┌──────────────────────┐
              │   TOUR DISCOVERY     │
              │      CONTEXT         │
              │                      │
              │  Personalized search │
              │  Recommendations     │
              └──────────────────────┘
```

---

### Implementation Pattern

#### Customer Profile Aggregate

```kotlin
// domain/customerprofile/model/CustomerProfile.kt
class CustomerProfile private constructor(
    val id: CustomerId,
    private var preferences: CustomerPreferences,
    private val purchaseHistory: MutableList<PurchaseRecord> = mutableListOf(),
    private val favorites: MutableSet<TourId> = mutableSetOf(),
    private val recentlyViewed: MutableList<ViewRecord> = mutableListOf()
) {
    companion object {
        fun create(customerId: CustomerId): CustomerProfile {
            return CustomerProfile(
                id = customerId,
                preferences = CustomerPreferences.default()
            )
        }
    }
    
    fun updatePreferences(newPreferences: CustomerPreferences) {
        this.preferences = newPreferences
        // Could emit PreferencesUpdated event
    }
    
    fun recordPurchase(tourId: TourId, purchasedAt: Instant) {
        purchaseHistory.add(PurchaseRecord(tourId, purchasedAt))
    }
    
    fun addToFavorites(tourId: TourId) {
        favorites.add(tourId)
    }
    
    fun removeFromFavorites(tourId: TourId) {
        favorites.remove(tourId)
    }
    
    fun recordView(tourId: TourId) {
        recentlyViewed.add(0, ViewRecord(tourId, Instant.now()))
        if (recentlyViewed.size > MAX_RECENT_VIEWS) {
            recentlyViewed.removeLast()
        }
    }
    
    // Used by Tour Discovery for recommendations
    fun getRecommendationProfile(): RecommendationProfile {
        return RecommendationProfile(
            preferredLanguages = preferences.languages,
            preferredTopics = preferences.topics,
            preferredAgeGroups = preferences.ageGroups,
            accessibilityNeeds = preferences.accessibilityTags,
            purchasedTourIds = purchaseHistory.map { it.tourId },
            favoriteTourIds = favorites.toList(),
            recentlyViewedTourIds = recentlyViewed.map { it.tourId }
        )
    }
    
    private companion object {
        const val MAX_RECENT_VIEWS = 50
    }
}

// Value Objects
data class CustomerPreferences(
    val languages: List<LanguageId>,
    val topics: List<TopicId>,
    val ageGroups: List<AgeGroupId>,
    val accessibilityTags: List<AccessibilityTagId>
) {
    companion object {
        fun default() = CustomerPreferences(
            languages = emptyList(),
            topics = emptyList(),
            ageGroups = emptyList(),
            accessibilityTags = emptyList()
        )
    }
}

data class PurchaseRecord(
    val tourId: TourId,
    val purchasedAt: Instant,
    val downloaded: Boolean = false
)

data class ViewRecord(
    val tourId: TourId,
    val viewedAt: Instant
)

// Read model for Tour Discovery
data class RecommendationProfile(
    val preferredLanguages: List<LanguageId>,
    val preferredTopics: List<TopicId>,
    val preferredAgeGroups: List<AgeGroupId>,
    val accessibilityNeeds: List<AccessibilityTagId>,
    val purchasedTourIds: List<TourId>,
    val favoriteTourIds: List<TourId>,
    val recentlyViewedTourIds: List<TourId>
)
```

#### Event Listener (Sync from Purchasing)

```kotlin
// application/customerprofile/listener/PurchaseEventListener.kt
@Component
class PurchaseEventListener(
    private val customerProfileRepository: CustomerProfileRepository
) {
    @EventListener
    fun onTourPurchased(event: TourPurchasedEvent) {
        val profile = customerProfileRepository.findById(event.customerId)
            ?: CustomerProfile.create(event.customerId)
        
        profile.recordPurchase(event.tourId, event.purchasedAt)
        customerProfileRepository.save(profile)
    }
}
```

---

### Summary

| Question                                  | Answer                                                              |
|-------------------------------------------|---------------------------------------------------------------------|
| **Does Customer need its own subdomain?** | ✅ Yes — as a **Supporting** subdomain                               |
| **Why?**                                  | Preferences + history have behavior that affects core functionality |
| **What's the aggregate?**                 | CustomerProfile (preferences, history, favorites)                   |
| **How does it integrate?**                | Provides RecommendationProfile to Tour Discovery                    |
| **Is it Core?**                           | No — it supports discovery but isn't the main value proposition     |

```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                    CUSTOMER PROFILE SUMMARY                                ┃
┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫
┃                                                                            ┃
┃  Type:        SUPPORTING Subdomain                                         ┃
┃  Aggregate:   CustomerProfile                                              ┃
┃  Contains:    Preferences, Purchase History, Favorites, Recently Viewed    ┃
┃  Feeds into:  Tour Discovery (recommendations, personalized search)        ┃
┃  Listens to:  Purchasing events, Feedback events                           ┃
┃                                                                            ┃
┃  Separate from Identity (user account) and Purchasing (orders/payments)    ┃
┃                                                                            ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
```
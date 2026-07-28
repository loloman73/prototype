### Question:
what about the upload of photos from the Tourist Guide? Does it need its own Subdomain?

### Photo Upload: Subdomain or Infrastructure?

Great question! Let's analyze where photo upload belongs.

---

### The Key Distinction

| Type | Examples | Where It Belongs |
|------|----------|------------------|
| **Business Subdomain** | Tour Management, Approval, Feedback | Core/Supporting domain logic |
| **Infrastructure Service** | File Storage, Email, SMS, Logging | Technical capability, no business rules |

**Photo upload is an infrastructure concern**, not a business subdomain.

---

### Why Photos Don't Need Their Own Subdomain

| Question | Answer |
|----------|--------|
| Does photo upload have unique business rules? | No — it's just storing files |
| Does it have its own ubiquitous language? | No — "photo" is understood everywhere |
| Would a domain expert talk about "Photo Management"? | No — they talk about "Tour Point photos" |
| Is there a "Photo" aggregate with behavior? | No — photos are attributes of other entities |
| Could different teams own this? | No — it's a shared technical capability |

---

### Where Photos Belong in Your Model

```
TOUR MANAGEMENT CONTEXT (Core Subdomain)
│
├── Tour (Aggregate Root)
│   └── ...
│
├── TourPoint (Entity within Tour)
│   ├── Location
│   ├── Description
│   └── Photos: List<PhotoReference>  ◄── Photos are VALUE OBJECTS here
│
├── MediaGuide (Aggregate Root)
│   └── AudioFileReference            ◄── Similar pattern for audio files
│
└── TourGuideProfile (Aggregate Root)
    └── ProfilePhoto: PhotoReference  ◄── Photo is a value object

INFRASTRUCTURE LAYER (Shared Service)
│
└── FileStorageService                ◄── Technical implementation
    ├── upload(file): FileReference
    ├── download(reference): File
    └── delete(reference)
```

---

### The Correct Pattern

#### Domain Layer (Value Object)

```kotlin
// domain/tourmanagement/model/valueobject/PhotoReference.kt
data class PhotoReference(
    val fileId: String,           // Reference to stored file
    val url: String,              // Public URL for display
    val thumbnailUrl: String?,    // Optional thumbnail
    val mimeType: String,         // image/jpeg, image/png
    val sizeBytes: Long,
    val uploadedAt: Instant
)

// domain/tourmanagement/model/TourPoint.kt
class TourPoint(
    val id: TourPointId,
    val location: TourPointLocation,
    val description: String,
    val photos: MutableList<PhotoReference> = mutableListOf()  // Value objects
) {
    fun addPhoto(photo: PhotoReference) {
        require(photos.size < MAX_PHOTOS) { "Maximum $MAX_PHOTOS photos allowed" }
        photos.add(photo)
    }
    
    fun removePhoto(fileId: String) {
        photos.removeIf { it.fileId == fileId }
    }
    
    companion object {
        const val MAX_PHOTOS = 10
    }
}
```

#### Infrastructure Layer (Service)

```kotlin
// infrastructure/storage/FileStorageService.kt
interface FileStorageService {
    fun upload(file: MultipartFile, folder: String): FileReference
    fun delete(fileId: String)
    fun getUrl(fileId: String): String
}

// infrastructure/storage/S3FileStorageService.kt
@Service
class S3FileStorageService(
    private val s3Client: S3Client,
    private val config: S3Config
) : FileStorageService {
    
    override fun upload(file: MultipartFile, folder: String): FileReference {
        val fileId = UUID.randomUUID().toString()
        val key = "$folder/$fileId"
        
        s3Client.putObject(
            PutObjectRequest.builder()
                .bucket(config.bucket)
                .key(key)
                .contentType(file.contentType)
                .build(),
            RequestBody.fromInputStream(file.inputStream, file.size)
        )
        
        return FileReference(
            fileId = fileId,
            url = "${config.cdnUrl}/$key",
            mimeType = file.contentType ?: "application/octet-stream",
            sizeBytes = file.size
        )
    }
    
    override fun delete(fileId: String) { /* ... */ }
    override fun getUrl(fileId: String): String { /* ... */ }
}
```

#### Application Layer (Use Case)

```kotlin
// features/tour-management/add-tour-point-photo/AddTourPointPhotoHandler.kt
@Service
class AddTourPointPhotoHandler(
    private val tourRepository: TourRepository,
    private val fileStorageService: FileStorageService  // Infrastructure service
) {
    @Transactional
    fun handle(command: AddTourPointPhotoCommand): PhotoReference {
        val tour = tourRepository.findById(command.tourId)
            ?: throw TourNotFoundException(command.tourId)
        
        // Validate ownership
        require(tour.ownerId == command.tourGuideId) { "Not authorized" }
        
        // Upload file (infrastructure)
        val fileRef = fileStorageService.upload(
            file = command.photoFile,
            folder = "tours/${command.tourId}/points/${command.tourPointId}"
        )
        
        // Create domain value object
        val photoReference = PhotoReference(
            fileId = fileRef.fileId,
            url = fileRef.url,
            thumbnailUrl = null,  // Could generate async
            mimeType = fileRef.mimeType,
            sizeBytes = fileRef.sizeBytes,
            uploadedAt = Instant.now()
        )
        
        // Add to domain model
        val tourPoint = tour.findTourPoint(command.tourPointId)
        tourPoint.addPhoto(photoReference)  // Domain logic (max photos check)
        
        tourRepository.save(tour)
        return photoReference
    }
}
```

---

### Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                             │
│  TOUR MANAGEMENT CONTEXT                                                    │
│  ───────────────────────                                                    │
│                                                                             │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ Domain Layer                                                         │   │
│  │                                                                      │   │
│  │  Tour ──► TourPoint ──► PhotoReference (Value Object)               │   │
│  │                              │                                       │   │
│  │                              │ Contains only:                        │   │
│  │                              │ • fileId                              │   │
│  │                              │ • url                                 │   │
│  │                              │ • metadata                            │   │
│  │                              │                                       │   │
│  │  Business Rules:                                                     │   │
│  │  • Max 10 photos per TourPoint                                      │   │
│  │  • Only owner can add/remove                                        │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                      │                                      │
│                                      │ uses                                 │
│                                      ▼                                      │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ Application Layer (Use Cases)                                        │   │
│  │                                                                      │   │
│  │  AddTourPointPhotoHandler                                           │   │
│  │  RemoveTourPointPhotoHandler                                        │   │
│  │  UpdateTourGuideProfilePhotoHandler                                 │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                      │                                      │
└──────────────────────────────────────┼──────────────────────────────────────┘
                                       │ depends on
                                       ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  INFRASTRUCTURE LAYER (Shared)                                              │
│  ─────────────────────────────                                              │
│                                                                             │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ FileStorageService (Interface)                                       │   │
│  │                                                                      │   │
│  │  • upload(file): FileReference                                      │   │
│  │  • delete(fileId)                                                   │   │
│  │  • getUrl(fileId): String                                           │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                          │                                                  │
│            ┌─────────────┴─────────────┐                                   │
│            ▼                           ▼                                   │
│  ┌──────────────────┐       ┌──────────────────┐                          │
│  │ S3StorageService │       │LocalStorageService│                          │
│  │ (Production)     │       │ (Development)    │                          │
│  └──────────────────┘       └──────────────────┘                          │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

### When WOULD Photos Be a Subdomain?

Photos would be a separate subdomain only if:

| Scenario | Example |
|----------|---------|
| **Photo marketplace** | Users can buy/sell stock photos |
| **Photo editing service** | Complex transformations, filters, AI enhancement |
| **Photo moderation** | Separate team reviews photos for inappropriate content |
| **Photo licensing** | Different usage rights, royalties |
| **Photo analytics** | Track views, engagement, A/B testing |

For your case (Tour Point photos, profile photos), these are just **attributes** of your domain entities.

---

### Summary

| Question | Answer |
|----------|--------|
| **Does photo upload need its own subdomain?** | ❌ No |
| **Where do photos belong?** | Value Objects in Tour Management context |
| **Where does upload logic belong?** | Infrastructure layer (FileStorageService) |
| **What business rules apply?** | Max photos per TourPoint, ownership validation |
| **What's the pattern?** | Domain holds references, Infrastructure handles storage |

```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                         PHOTO HANDLING SUMMARY                             ┃
┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫
┃                                                                            ┃
┃  Domain:         PhotoReference (Value Object) in TourPoint/Profile        ┃
┃  Infrastructure: FileStorageService (S3, local, etc.)                      ┃
┃  Subdomain:      NOT needed — photos are attributes, not a business area   ┃
┃                                                                            ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
```
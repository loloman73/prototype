### Different Views of the Same Data = Separate Bounded Contexts

Since Administrator needs audit fields (`lastEdit`, `created`, `createdBy`) that Core doesn't care about, 
you now have **different models for the same concept**. This changes the pattern.

### Updated Recommendation: Upstream/Downstream with Anti-Corruption Layer

```
domain/src/main/kotlin/xenagos/domain/
├── core/
│   └── model/
│       ├── Tour.kt
│       ├── TourPoint.kt
│       ├── AccessibilityTag.kt      # Simple: id, name only
│       ├── TopicTag.kt              # Simple: id, name only
│       ├── Language.kt              # Simple: id, name only
│       └── ...
└── generic/
    └── administrator/
        └── model/
            ├── AdminAccessibilityTag.kt  # Full: id, name, created, createdBy, lastEdit
            ├── AdminTopicTag.kt          # Full: id, name, created, createdBy, lastEdit
            ├── AdminLanguage.kt          # Full: id, name, created, createdBy, lastEdit
            └── ...
```

### Context Map

```
┌─────────────────────────┐         ┌─────────────────────────┐
│   ADMINISTRATOR (U)     │         │      CORE (D)           │
│   Generic Subdomain     │         │   Core Subdomain        │
│                         │         │                         │
│  AdminAccessibilityTag  │────────▶│  AccessibilityTag       │
│  - id                   │   ACL   │  - id                   │
│  - name                 │         │  - name                 │
│  - created              │         │                         │
│  - createdBy            │         │                         │
│  - lastEdit             │         │                         │
└─────────────────────────┘         └─────────────────────────┘
        (Upstream)                        (Downstream)
```

**U** = Upstream (owns the data)  
**D** = Downstream (consumes simplified view)  
**ACL** = Anti-Corruption Layer (translates between models)

### Anti-Corruption Layer Implementation

In the **Core subdomain's application layer**, create a mapper/translator:

```kotlin
// core application layer
class TagTranslator(
    private val adminOutputPort: AdminAccessibilityTagsOutputPort
) {
    fun getAccessibilityTags(): List<AccessibilityTag> {
        return adminOutputPort.getAll().map { adminTag ->
            AccessibilityTag(
                id = adminTag.id,
                name = adminTag.name
                // audit fields ignored
            )
        }
    }
}
```

### Key Points

1. **Each subdomain has its own model** - no shared kernel needed
2. **Administrator is upstream** - source of truth with full audit data
3. **Core is downstream** - uses simplified read-only view
4. **ACL protects Core** - from Administrator's complexity and changes
5. **Same database table** - persistence adapter maps to appropriate domain model based on context

### Persistence Strategy

Both models can map to the **same database table**:

```kotlin
// Administrator persistence - maps all columns
class AdminAccessibilityTagEntity {
    val id: UUID
    val name: String
    val created: Instant
    val createdBy: UUID
    val lastEdit: Instant
}

// Core persistence - maps only needed columns  
class AccessibilityTagEntity {
    val id: UUID
    val name: String
}
```

This approach gives you clean separation while avoiding data duplication.
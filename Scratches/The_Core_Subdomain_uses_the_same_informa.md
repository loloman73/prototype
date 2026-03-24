### Sharing Domain Entities Between Subdomains

You do **NOT** need to duplicate domain entities. This is a common DDD scenario with several established patterns:

### Your Situation
- **Administrator Subdomain**: Owns and manages reference data (CRUD operations)
- **Core Subdomain**: Consumes/references this data (read-only)

### Recommended Solutions

#### Option 1: Shared Kernel (Recommended for your case)
Create a `shared-kernel` package/module containing the shared entities:

```
domain/src/main/kotlin/xenagos/domain/
├── shared/                          # Shared Kernel
│   └── model/
│       ├── AccessibilityTag.kt
│       ├── TopicTag.kt
│       ├── AgeGroup.kt
│       ├── Language.kt
│       ├── MediaType.kt
│       ├── DurationType.kt
│       └── AmeaTag.kt
├── core/                            # Core Subdomain
│   └── model/
│       ├── Tour.kt                  # References shared entities
│       ├── TourPoint.kt
│       └── Xenagos.kt
└── generic/
    └── administrator/               # Administrator Subdomain
        └── (no separate model - uses shared kernel)
```

Both subdomains import from `shared`. The Shared Kernel is co-owned and changes require coordination.

#### Option 2: Upstream/Downstream with Conformist
Administrator is **upstream** (owns the data), Core is **downstream** (conforms to it):

```
domain/src/main/kotlin/xenagos/domain/
├── generic/
│   └── administrator/
│       └── model/                   # Entities live here (owned by Admin)
│           ├── AccessibilityTag.kt
│           └── ...
└── core/
    └── model/
        ├── Tour.kt                  # Imports from administrator.model
        └── ...
```

Core subdomain depends on Administrator's model directly.

#### Option 3: Separate Models with Anti-Corruption Layer (Overkill for your case)
Only needed when subdomains have different views of the same concept. Not recommended here since both use identical structures.

### Recommendation

**Use Option 1 (Shared Kernel)** because:
1. The entities are truly shared with identical meaning in both contexts
2. Reference data is simple and stable (tags, languages)
3. Both subdomains are in the same bounded context/project
4. Avoids unnecessary duplication and mapping

### Final Structure

```
xenagos/domain/
├── shared/model/           # Shared Kernel (AccessibilityTag, Language, etc.)
├── core/model/             # Core entities (Tour, TourPoint, Xenagos)
└── generic/administrator/  # Admin-specific logic only (no duplicate models)
```

The Administrator subdomain's **application layer** handles CRUD operations on shared entities, while Core subdomain's application layer only reads them.
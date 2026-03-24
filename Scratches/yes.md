### Implementing the Administrator Generic Subdomain

This refactoring involves **60+ files** across all layers. The safest and most efficient approach is to use **IntelliJ IDEA's refactoring tools**.

### Step-by-Step Instructions

#### Step 1: Refactor Package Names in Each Module

For each module, right-click on the `admin` package → **Refactor** → **Rename** (or press `Shift+F6`):

| Module                  | Current Package                            | New Package                                                |
|-------------------------|--------------------------------------------|------------------------------------------------------------|
| **input-port**          | `xenagos.application.port.input.admin`     | `xenagos.application.port.input.generic.administrator`     |
| **output-port**         | `xenagos.application.port.output.admin`    | `xenagos.application.port.output.generic.administrator`    |
| **application**         | `xenagos.application.admin`                | `xenagos.application.generic.administrator`                |
| **persistence-adapter** | `xenagos.adapter.output.persistence.admin` | `xenagos.adapter.output.persistence.generic.administrator` |
| **web-adapter**         | `xenagos.adapter.input.web.admin`          | `xenagos.adapter.input.web.generic.administrator`          |

#### Step 2: Move Domain Entities (Optional but Recommended)

Create a new package structure in the domain module:
```
domain/src/main/kotlin/xenagos/domain/
├── core/model/           # Move Tour, TourPoint, Xenagos, MediaGuide here
└── generic/
    └── administrator/
        └── model/        # Move AccessibilityTag, TopicTag, AgeGroup, 
                          # Language, MediaType, DurationType, AmeaTag here
```

Right-click on each entity → **Refactor** → **Move** (or press `F6`)

#### Step 3: Update Test Packages

Apply the same package renames to test directories:
- `input-port/src/test/kotlin/xenagos/application/port/input/admin`
- `application/src/test/kotlin/xenagos/application/admin`
- `persistence-adapter/src/test/kotlin/xenagos/adapter/output/persistence/admin`
- `web-adapter/src/test/kotlin/xenagos/adapter/input/web/admin`

#### Step 4: Update Web Resources (Manual)

Rename template folders:
- `web-adapter/src/main/resources/templates/fragments/admin` → `administrator`
- `web-adapter/src/main/resources/templates/pages/admin` → `administrator`
- `web-adapter/src/main/resources/templates/layouts/layout_admin.html` → `layout_administrator.html`

Update references in HTML files and controllers.

#### Step 5: Build and Test

```bash
./gradlew clean build
```

### Why Use IDE Refactoring?

IntelliJ's refactoring will automatically:
- Update all package declarations
- Update all import statements across the entire project
- Update Spring component scan paths
- Maintain file history in version control

### Final Structure

```
xenagos/
├── domain/
│   ├── core/model/                    # Core Subdomain
│   └── generic/administrator/model/   # Administrator Generic Subdomain
├── application/
│   ├── generic/administrator/         # Admin app services & mappers
│   └── service/                       # Core services
└── adapter/
    ├── input/web/generic/administrator/
    └── output/persistence/generic/administrator/
```

This structure clearly separates your **Core Subdomain** (Tours) from the **Administrator Generic Subdomain** (reference data management).
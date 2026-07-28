### Question:
give me an example using the ReferenceDataRepository Generic Approach for my AdminAgeGroupsController and AdminAccessibilityTagsController

### Generic Reference Data Controller Example

You already have an excellent foundation with `BaseAdminController` and `BaseAdminUseCase`. Here's how to take it further with a fully generic approach.

---

### Current State (What You Have)

Your current pattern requires creating a new controller for each reference data type:

```
AdminAgeGroupsController      → AdminAgeGroupUseCase
AdminAccessibilityTagsController → AdminAccessibilityTagsUseCase
AdminTopicsController         → AdminTopicsUseCase  (future)
AdminLanguagesController      → AdminLanguagesUseCase (future)
... and so on
```

Each controller is ~57 lines of nearly identical code.

---

### Option 1: Enhanced Generic Base Controller (Recommended)

Keep your current approach but reduce boilerplate further:

```kotlin
// BaseAdminController.kt - Enhanced version
abstract class BaseAdminController<
    TNew : BaseAdminNewRequestDTO,
    TUpdate : BaseAdminUpdateRequestDTO,
    TResponse : BaseAdminResponseDTO
>(
    protected val service: BaseAdminUseCase<TNew, TUpdate, TResponse>
) {
    abstract val fragmentForAddOneNewRequest: String
    abstract val fragmentForUpdateOneRequest: String
    abstract val myURLEndpoint: String
    abstract val templateName: String
    abstract fun createEmptyNewRequestDTO(): TNew
    abstract fun createEmptyUpdateRequestDTO(): TUpdate

    companion object {
        const val ADMIN_TEMPLATE_PATH_PREFIX = "pages/admin/"
    }

    @GetMapping
    fun showAll(model: Model): String {
        model.addAttribute("listAllModel", service.getAll())
        model.addAttribute("addOneNewModel", createEmptyNewRequestDTO())
        model.addAttribute("updateOneModel", createEmptyUpdateRequestDTO())
        return ADMIN_TEMPLATE_PATH_PREFIX + templateName
    }

    @HxRequest
    @PostMapping
    fun addOneNew(
        @Valid @ModelAttribute("addOneNewModel") requestDTO: TNew,
        bindingResult: BindingResult,
        response: HttpServletResponse
    ): String = handleAddOneNew(bindingResult, response) { service.saveOneNew(requestDTO) }

    @HxRequest
    @PutMapping
    fun updateOne(
        @Valid @ModelAttribute("updateOneModel") requestDTO: TUpdate,
        bindingResult: BindingResult,
        response: HttpServletResponse
    ): String = handleUpdateOne(bindingResult, response) { service.updateOne(requestDTO) }

    @HxRequest
    @DeleteMapping("/{id}")
    fun deleteOne(
        @PathVariable id: UUID,
        response: HttpServletResponse
    ): String = handleDeleteOne(response) { service.deleteOne(id) }

    // ... existing handle methods unchanged
}
```

Then your controllers become minimal:

```kotlin
// AdminAgeGroupsController.kt - Simplified
@Controller
@RequestMapping("/admin/age-groups")
class AdminAgeGroupsController(service: AdminAgeGroupUseCase) : 
    BaseAdminController<
        AdminAgeGroupNewRequestDTO,
        AdminAgeGroupUpdateRequestDTO,
        AdminAgeGroupResponseDTO
    >(service) {

    override val fragmentForAddOneNewRequest = "age-group-modal-form-add-new"
    override val fragmentForUpdateOneRequest = "age-group-modal-form-edit"
    override val myURLEndpoint = "age-groups"
    override val templateName = "adminAgeGroups"
    
    override fun createEmptyNewRequestDTO() = AdminAgeGroupNewRequestDTO.createEmptyDeactivated()
    override fun createEmptyUpdateRequestDTO() = AdminAgeGroupUpdateRequestDTO.createEmptyDeactivated()
}

// AdminAccessibilityTagsController.kt - Simplified
@Controller
@RequestMapping("/admin/accessibility-tags")
class AdminAccessibilityTagsController(service: AdminAccessibilityTagsUseCase) : 
    BaseAdminController<
        AdminAccessibilityTagNewRequestDTO,
        AdminAccessibilityTagUpdateRequestDTO,
        AdminAccessibilityTagResponseDTO
    >(service) {

    override val fragmentForAddOneNewRequest = "accessibility-tag-modal-form-add-new"
    override val fragmentForUpdateOneRequest = "accessibility-tag-modal-form-edit"
    override val myURLEndpoint = "accessibility-tags"
    override val templateName = "adminAccessibilityTags"
    
    override fun createEmptyNewRequestDTO() = AdminAccessibilityTagNewRequestDTO.createEmptyDeactivated()
    override fun createEmptyUpdateRequestDTO() = AdminAccessibilityTagUpdateRequestDTO.createEmptyDeactivated()
}
```

**Benefit:** Each new reference data type only needs ~15 lines of controller code.

---

### Option 2: Configuration-Driven Single Controller

If you want to go even further, use a single controller with configuration:

```kotlin
// ReferenceDataConfig.kt
data class ReferenceDataConfig<
    TNew : BaseAdminNewRequestDTO,
    TUpdate : BaseAdminUpdateRequestDTO,
    TResponse : BaseAdminResponseDTO
>(
    val entityType: String,                    // "age-groups", "accessibility-tags"
    val templateName: String,                  // "adminAgeGroups"
    val fragmentAddNew: String,                // "age-group-modal-form-add-new"
    val fragmentUpdate: String,                // "age-group-modal-form-edit"
    val emptyNewDTO: () -> TNew,
    val emptyUpdateDTO: () -> TUpdate,
    val useCase: BaseAdminUseCase<TNew, TUpdate, TResponse>
)

// ReferenceDataRegistry.kt
@Component
class ReferenceDataRegistry(
    ageGroupUseCase: AdminAgeGroupUseCase,
    accessibilityTagsUseCase: AdminAccessibilityTagsUseCase
) {
    private val configs = mapOf(
        "age-groups" to ReferenceDataConfig(
            entityType = "age-groups",
            templateName = "adminAgeGroups",
            fragmentAddNew = "age-group-modal-form-add-new",
            fragmentUpdate = "age-group-modal-form-edit",
            emptyNewDTO = { AdminAgeGroupNewRequestDTO.createEmptyDeactivated() },
            emptyUpdateDTO = { AdminAgeGroupUpdateRequestDTO.createEmptyDeactivated() },
            useCase = ageGroupUseCase
        ),
        "accessibility-tags" to ReferenceDataConfig(
            entityType = "accessibility-tags",
            templateName = "adminAccessibilityTags",
            fragmentAddNew = "accessibility-tag-modal-form-add-new",
            fragmentUpdate = "accessibility-tag-modal-form-edit",
            emptyNewDTO = { AdminAccessibilityTagNewRequestDTO.createEmptyDeactivated() },
            emptyUpdateDTO = { AdminAccessibilityTagUpdateRequestDTO.createEmptyDeactivated() },
            useCase = accessibilityTagsUseCase
        )
    )

    fun getConfig(entityType: String) = configs[entityType]
        ?: throw IllegalArgumentException("Unknown entity type: $entityType")
    
    fun getAllEntityTypes() = configs.keys
}
```

**Note:** This approach is more complex and has challenges with Spring's `@Valid` annotation and type-safe model binding. Option 1 is usually the better choice.

---

### Option 3: Convention-Based Approach

Use naming conventions to reduce configuration:

```kotlin
// AdminReferenceDataController.kt
@Controller
@RequestMapping("/admin/{entityType}")
class AdminReferenceDataController(
    private val registry: ReferenceDataRegistry
) {
    companion object {
        const val ADMIN_TEMPLATE_PATH_PREFIX = "pages/admin/"
    }

    @GetMapping
    fun showAll(
        @PathVariable entityType: String,
        model: Model
    ): String {
        val config = registry.getConfig(entityType)
        model.addAttribute("listAllModel", config.useCase.getAll())
        model.addAttribute("addOneNewModel", config.emptyNewDTO())
        model.addAttribute("updateOneModel", config.emptyUpdateDTO())
        return ADMIN_TEMPLATE_PATH_PREFIX + config.templateName
    }

    // POST, PUT, DELETE would need special handling for type-safe binding
    // This is where the complexity lies with Spring MVC
}
```

---

### My Recommendation

**Stick with Option 1** (Enhanced Generic Base Controller) because:

| Factor | Option 1 | Option 2/3 |
|--------|----------|------------|
| Type Safety | ✅ Full compile-time safety | ⚠️ Runtime type casting |
| Spring `@Valid` | ✅ Works naturally | ❌ Complex workarounds needed |
| IDE Support | ✅ Full autocomplete | ⚠️ Limited |
| Debugging | ✅ Clear stack traces | ⚠️ Generic handlers harder to debug |
| Boilerplate | ~15 lines per entity | ~5 lines config per entity |
| Complexity | Low | Medium-High |

---

### Summary

Your current architecture is already well-designed. The main improvement is moving the common CRUD methods (`showAll`, `addOneNew`, `updateOne`, `deleteOne`) into the base class, leaving only configuration in the concrete controllers.

```
Before: ~57 lines per controller
After:  ~15 lines per controller (Option 1)
```

This keeps the benefits of type safety while eliminating repetitive code.
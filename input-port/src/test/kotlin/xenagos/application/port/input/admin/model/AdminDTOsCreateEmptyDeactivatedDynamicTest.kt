package xenagos.application.port.input.admin.model

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.reflections.Reflections
import org.reflections.scanners.Scanners
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.functions
import kotlin.reflect.full.memberProperties

//uses reflection to find all DTOs and test that createEmptyDeactivated() returns active = false
class AdminDTOsCreateEmptyDeactivatedDynamicTest {

    @TestFactory
    fun `test all DTOs with createEmptyDeactivated() return active = false`(): Collection<DynamicTest> {
        val packageName = "xenagos.application.port.input.admin.model"
        val reflections = Reflections(packageName, Scanners.SubTypes.filterResultsBy { true })

        // Get all classes in the package
        val allClasses = reflections.getSubTypesOf(Any::class.java)
            .filter { it.packageName == packageName }
            .map { it.kotlin }

        val tests = mutableListOf<DynamicTest>()

        for (kClass in allClasses) {
            val companionObject = kClass.companionObject

            if (companionObject != null) {
                val companionInstance = kClass.companionObjectInstance
                val createEmptyDeactivatedFunction = companionObject.functions
                    .find { it.name == "createEmptyDeactivated" && it.parameters.size == 1 }

                if (createEmptyDeactivatedFunction != null && companionInstance != null) {
                    tests.add(
                        DynamicTest.dynamicTest("${kClass.simpleName}.createEmptyDeactivated() should return active = false") {
                            // Call the function
                            val result = createEmptyDeactivatedFunction.call(companionInstance)

                            assertNotNull(result, "${kClass.simpleName}.createEmptyDeactivated() returned null")

                            // Find the 'active' property
                            val activeProperty = result!!::class.memberProperties
                                .find { it.name == "active" }

                            assertNotNull(activeProperty, "${kClass.simpleName} does not have an 'active' property")

                            val activeValue = activeProperty!!.call(result)

                            assertFalse(
                                activeValue as Boolean,
                                "${kClass.simpleName}.createEmptyDeactivated() should return active = false but was $activeValue"
                            )
                        }
                    )
                }
            }
        }

        // Ensure we found at least some DTOs to test
        assertNotNull(tests, "No DTOs with createEmptyDeactivated() function found")
        assert(tests.isNotEmpty()) { "No DTOs with createEmptyDeactivated() function found in package $packageName" }

        println("Found ${tests.size} DTOs with createEmptyDeactivated() function")

        return tests
    }
}
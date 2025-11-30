package xenagos.adapter.output.persistence

import org.junit.platform.suite.api.IncludeClassNamePatterns
import org.junit.platform.suite.api.SelectPackages
import org.junit.platform.suite.api.Suite

/**
 * JUnit 5 suite to run all persistence integration tests in one go (from the IDE).
 * It will pick up every test class under the selected packages whose class name matches `.*PersistenceIT`.
 */
@Suite
@SelectPackages("xenagos.adapter.output.persistence.admin")
@IncludeClassNamePatterns(".*PersistenceIT")
class PersistenceIntegrationTestSuite

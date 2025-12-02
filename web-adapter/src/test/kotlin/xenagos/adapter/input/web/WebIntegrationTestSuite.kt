package xenagos.adapter.input.web

import org.junit.platform.suite.api.IncludeClassNamePatterns
import org.junit.platform.suite.api.SelectPackages
import org.junit.platform.suite.api.Suite

@Suite
@SelectPackages("xenagos.adapter.input.web.admin")
@IncludeClassNamePatterns(".*ControllerIT")
class WebIntegrationTestSuite
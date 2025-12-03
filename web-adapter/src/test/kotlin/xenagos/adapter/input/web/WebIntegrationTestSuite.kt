package xenagos.adapter.input.web

import org.junit.platform.suite.api.IncludeClassNamePatterns
import org.junit.platform.suite.api.SelectPackages
import org.junit.platform.suite.api.Suite

/**
 * DO NOT USE - IT IS NOT WORKING - CONTAINER STOPS AFTER EACH TEST
 **/

@Suite
@SelectPackages("xenagos.adapter.input.web.admin")
@IncludeClassNamePatterns(".*ControllerIT")
class WebIntegrationTestSuite
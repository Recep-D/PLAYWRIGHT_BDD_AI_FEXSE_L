package com.ıaCreation.runner;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.FEATURES_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.FILTER_TAGS_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty,html:target/cucumber-reports/html-report,json:target/cucumber-reports/json/Cucumber.json,junit:target/cucumber-reports/junit/Cucumber.xml,io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.ıaCreation.step_definitions,com.ıaCreation.hooks")
@ConfigurationParameter(key = FEATURES_PROPERTY_NAME, value = "src/test/resources/features")
// Optional tag filter: set tags here (e.g., "@watches and not @skip").
// Can be overridden via: mvn test -Dcucumber.filter.tags="@watches"
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@vib")
public class TestRunner {
    // This class serves as the entry point for running Cucumber tests
    // All configuration is handled through annotations
}

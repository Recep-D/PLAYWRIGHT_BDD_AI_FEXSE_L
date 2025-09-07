package com.ıaCreation.hooks;

import com.ıaCreation.utilities.Driver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

/**
 * Test Hooks for managing browser lifecycle in Cucumber tests
 * This class handles setup and teardown operations for each scenario
 */
public class TestHooks {

    /**
     * Runs before each scenario
     * Initializes the browser and navigates to the configured URL
     */
    @Before
    public void setUp(Scenario scenario) {
        System.out.println("Starting scenario: " + scenario.getName());
        
        // Initialize browser and navigate to URL
        Driver.getPage(); // This will initialize the browser if not already done
        Driver.navigateToUrl();
        
        System.out.println("Browser initialized and navigated to URL");
    }

    /**
     * Runs after each scenario
     * Performs cleanup operations
     */
    @After
    public void tearDown(Scenario scenario) {
        System.out.println("Finishing scenario: " + scenario.getName());
        
        if (scenario.isFailed()) {
            System.out.println("Scenario failed: " + scenario.getName());
            // You can add screenshot capture here if needed
            // byte[] screenshot = Driver.getPage().screenshot();
            // scenario.attach(screenshot, "image/png", "Screenshot");
        }
        
        // Close the browser after each scenario to ensure clean state
        Driver.closeDriver();
        
        System.out.println("Browser closed and cleanup completed");
    }
}

package com.ıaCreation.hooks;

import com.ıaCreation.utilities.BrowserFactory;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        BrowserFactory.getPage(); // initialize if needed
        // Start Playwright tracing with screenshots, snapshots and sources
        BrowserContext ctx = BrowserFactory.context();
        ctx.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
        BrowserFactory.navigateToUrl();
        
        System.out.println("Browser initialized and navigated to URL");
    }

    /**
     * Runs after each scenario
     * Performs cleanup operations
     */
    @After
    public void tearDown(Scenario scenario) {
        System.out.println("Finishing scenario: " + scenario.getName());
        
        String safeName = toSafeName(scenario.getName());
        Page page = BrowserFactory.getPage();
        BrowserContext ctx = BrowserFactory.context();

        // 1) Full page screenshot → Allure
        try {
            byte[] png = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
            Allure.addAttachment(safeName + " - screenshot", "image/png", new ByteArrayInputStream(png), ".png");
        } catch (Exception e) {
            System.out.println("Screenshot failed: " + e.getMessage());
        }

        // 2) Stop tracing to target/traces/<test>.zip → Allure
        try {
            Path tracesDir = Paths.get("target", "traces");
            Files.createDirectories(tracesDir);
            Path traceZip = tracesDir.resolve(safeName + ".zip");
            ctx.tracing().stop(new Tracing.StopOptions().setPath(traceZip));
            if (Files.exists(traceZip)) {
                Allure.addAttachment(safeName + " - trace", "application/zip", Files.newInputStream(traceZip), ".zip");
            }
        } catch (IOException ioe) {
            System.out.println("Tracing stop/attach failed: " + ioe.getMessage());
        }

        // 3) Close page then attach video if present
        Path videoPath = null;
        try {
            page.close();
        } catch (Exception ignored) {}
        try {
            if (page.video() != null) {
                // video file may be available after context close, try best-effort here
                videoPath = page.video().path();
            }
        } catch (Exception ignored) {}
        if (videoPath != null && Files.exists(videoPath)) {
            try {
                Allure.addAttachment(safeName + " - video", "video/mp4", Files.newInputStream(videoPath), ".mp4");
            } catch (IOException ignored) {}
        }
        
        // 4) Close the rest to ensure clean state
        BrowserFactory.closeDriver();
        
        System.out.println("Browser closed and cleanup completed");
    }

    private String toSafeName(String name) {
        String normalized = name.replaceAll("[^a-zA-Z0-9-_]+", "_");
        if (normalized.length() > 100) {
            normalized = normalized.substring(0, 100);
        }
        return normalized;
    }
}

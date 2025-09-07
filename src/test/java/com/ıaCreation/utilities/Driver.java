package com.ıaCreation.utilities;

import com.microsoft.playwright.*;
import com.microsoft.playwright.BrowserContext;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Driver {
    
    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext context;
    private static Page page;
    
    public static Page getPage() {
        if (page == null) {
            initializeDriver();
        }
        return page;
    }
    
    private static void initializeDriver() {
        String browserName = ConfigurationReader.getBrowserName();
        boolean headless = ConfigurationReader.isHeadless();
        
        playwright = Playwright.create();
        
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(headless);
        
        switch (browserName.toLowerCase()) {
            case "chromium":
            case "chrome":
                browser = playwright.chromium().launch(launchOptions);
                break;
            case "firefox":
                browser = playwright.firefox().launch(launchOptions);
                break;
            case "webkit":
            case "safari":
                browser = playwright.webkit().launch(launchOptions);
                break;
            default:
                throw new IllegalArgumentException("Browser not supported: " + browserName);
        }
        
        context = browser.newContext();
        page = context.newPage();
        
        // Set full screen using dynamic screen size detection
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) dimension.getWidth();
        int height = (int) dimension.getHeight();
        page.setViewportSize(width, height);
        
        // Set longer timeouts for slower page loads
        page.setDefaultTimeout(60000); // 60 seconds
        page.setDefaultNavigationTimeout(60000); // 60 seconds
    }
    
    public static void navigateToUrl() {
        String url = ConfigurationReader.getUrl();
        getPage().navigate(url);
    }
    
    public static void navigateToUrl(String url) {
        getPage().navigate(url);
    }
    
    public static void closeDriver() {
        if (page != null) {
            page.close();
            page = null;
        }
        if (context != null) {
            context.close();
            context = null;
        }
        if (browser != null) {
            browser.close();
            browser = null;
        }
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
    }
    
    public static void takeScreenshot(String fileName) {
        if (page != null) {
            page.screenshot(new Page.ScreenshotOptions().setPath(java.nio.file.Paths.get(fileName)));
        }
    }
    
    public static String getTitle() {
        return getPage().title();
    }
    
    public static String getCurrentUrl() {
        return getPage().url();
    }
}

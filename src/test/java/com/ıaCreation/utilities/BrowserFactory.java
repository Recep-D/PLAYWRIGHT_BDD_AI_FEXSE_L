package com.ıaCreation.utilities;

import com.microsoft.playwright.*;
import com.microsoft.playwright.BrowserContext;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BrowserFactory {
    
    private static final ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> tlBrowser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> tlContext = new ThreadLocal<>();
    private static final ThreadLocal<Page> tlPage = new ThreadLocal<>();
    
    public static Page getPage() {
        if (tlPage.get() == null) {
            initializeDriver();
        }
        return tlPage.get();
    }
    
    private static void initializeDriver() {
        String browserName = ConfigurationReader.getBrowserName();
        boolean headless = ConfigurationReader.isHeadless();
        
        tlPlaywright.set(Playwright.create());
        
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(headless);
        
        switch (browserName.toLowerCase()) {
            case "chromium":
            case "chrome":
                tlBrowser.set(tlPlaywright.get().chromium().launch(launchOptions));
                break;
            case "firefox":
                tlBrowser.set(tlPlaywright.get().firefox().launch(launchOptions));
                break;
            case "webkit":
            case "safari":
                tlBrowser.set(tlPlaywright.get().webkit().launch(launchOptions));
                break;
            default:
                throw new IllegalArgumentException("Browser not supported: " + browserName);
        }
        
        Path videoDir = Paths.get("target", "videos");
        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions()
                .setRecordVideoDir(videoDir)
                .setRecordVideoSize(1280, 720);
        tlContext.set(tlBrowser.get().newContext(contextOptions));
        tlPage.set(tlContext.get().newPage());
        
        // Set full screen using dynamic screen size detection
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) dimension.getWidth();
        int height = (int) dimension.getHeight();
        tlPage.get().setViewportSize(width, height);
        
        // Set longer timeouts for slower page loads
        tlPage.get().setDefaultTimeout(60000); // 60 seconds
        tlPage.get().setDefaultNavigationTimeout(60000); // 60 seconds
    }
    
    public static void navigateToUrl() {
        String url = ConfigurationReader.getUrl();
        getPage().navigate(url);
    }
    
    public static void navigateToUrl(String url) {
        getPage().navigate(url);
    }
    
    public static void closeDriver() {
        Page page = tlPage.get();
        if (page != null) {
            try { page.close(); } catch (Exception ignored) {}
            tlPage.remove();
        }
        BrowserContext context = tlContext.get();
        if (context != null) {
            try { context.close(); } catch (Exception ignored) {}
            tlContext.remove();
        }
        Browser browser = tlBrowser.get();
        if (browser != null) {
            try { browser.close(); } catch (Exception ignored) {}
            tlBrowser.remove();
        }
        Playwright playwright = tlPlaywright.get();
        if (playwright != null) {
            try { playwright.close(); } catch (Exception ignored) {}
            tlPlaywright.remove();
        }
    }
    
    public static void takeScreenshot(String fileName) {
        Page page = tlPage.get();
        if (page != null) {
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(fileName)));
        }
    }
    
    public static String getTitle() {
        return getPage().title();
    }
    
    public static String getCurrentUrl() {
        return getPage().url();
    }

    public static BrowserContext context() {
        if (tlContext.get() == null) {
            initializeDriver();
        }
        return tlContext.get();
    }
}

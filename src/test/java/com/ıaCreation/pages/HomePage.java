package com.ıaCreation.pages;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.ıaCreation.utilities.BrowserFactory;

public class HomePage {

    private Page page;
    // Constructor
    public HomePage() {
        this.page = BrowserFactory.getPage();
    }

    // Locators - Following ReadMe.txt priority: getByTestId > getByRole > getById > CSS
    private Locator homeModule() {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Home"));
    }

    private Locator realEstateButton() {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Real Estate"));
    }

    private Locator firstMarketplaceCardOverlay() {
        // Click the first marketplace item overlay by matching multiple class names (robust to order)
        return page.locator("//div[contains(@class,'absolute') and contains(@class,'inset-0') and contains(@class,'bg-gradient-to-t') and contains(@class,'rounded-xl') and contains(@class,'opacity-80')]").first();
    }

    private Locator detailsButton() {
        return page.locator("button:has-text(\"Details\")").first();
    }

    private Locator euIntegrationSection() {
        return page.getByText("EU Integration");
    }

    
    // Page Actions
    public void navigateToHomePage() {
        BrowserFactory.navigateToUrl();
    }
    
    public void clickHomeModule() {
        // Try role=button "Home"
        try {
            if (homeModule().isVisible()) {
                homeModule().click();
                return;
            }
        } catch (Exception ignored) {}

        // Try role=link "Home"
        try {
            Locator link = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Home"));
            if (link.isVisible()) {
                link.click();
                return;
            }
        } catch (Exception ignored) {}

        // Try text fallback
        try {
            Locator text = page.locator("text=Home").first();
            if (text.isVisible()) {
                text.click();
                return;
            }
        } catch (Exception ignored) {}

        // As a last resort, click the first nav/home-like element
        page.locator("nav :text('Home')").first().click();
    }
    
    public void scrollDownPage() {
        page.evaluate("window.scrollBy(0, 500)");
    }
    
    public void clickRealEstateButton() {
        realEstateButton().click();
    }
    
    public void clickFirstAssetImage() {
        firstMarketplaceCardOverlay().click();
    }
    
    public void clickDetailsButton() {
        Locator btn = detailsButton();
        try { btn.scrollIntoViewIfNeeded(); } catch (Exception ignored) {}
        try { btn.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)); } catch (Exception ignored) {}
        btn.click();
    }
    
    public void scrollToEuIntegrationSection() {
        euIntegrationSection().scrollIntoViewIfNeeded();
    }
    
    public void clickEuIntegration() {
        euIntegrationSection().click();
    }
    
    // Cookies
    private Locator cookiesAcceptButton() {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Accept"));
    }
    
    public void acceptCookiesIfPresent() {
        try {
            if (cookiesAcceptButton().isVisible()) {
                cookiesAcceptButton().click();
                return;
            }
        } catch (Exception ignored) {}
        
        // Fallbacks for different button texts
        try {
            Locator acceptAll = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Accept All"));
            if (acceptAll.isVisible()) {
                acceptAll.click();
                return;
            }
        } catch (Exception ignored) {}
        
        try {
            Locator anyAccept = page.locator("text=Accept").first();
            if (anyAccept.isVisible()) {
                anyAccept.click();
                return;
            }
        } catch (Exception ignored) {}

        // Turkish variants
        try {
            Locator kabulEt = page.locator("text=Kabul Et").first();
            if (kabulEt.isVisible()) {
                kabulEt.click();
                return;
            }
        } catch (Exception ignored) {}

        try {
            Locator tumunuKabulEt = page.locator("text=Tümünü Kabul Et").first();
            if (tumunuKabulEt.isVisible()) {
                tumunuKabulEt.click();
            }
        } catch (Exception ignored) {}
    }
    
    // Verification Methods
    public boolean isEuIntegrationDetailsDisplayed() {
        return euIntegrationSection().isVisible();
    }
    
    public void waitForEuIntegrationDetails() {
        euIntegrationSection().waitFor();
    }
    
    // Generic Methods
    public void clickElementByText(String text) {
        page.locator("text=" + text).first().click();
    }
    
    public void scrollToElementByText(String text) {
        page.locator("text=" + text).first().scrollIntoViewIfNeeded();
    }
    
    public boolean isElementVisible(String text) {
        return page.locator("text=" + text).first().isVisible();
    }
    
    public String getPageTitle() {
        return BrowserFactory.getTitle();
    }
    
    public String getCurrentUrl() {
        return BrowserFactory.getCurrentUrl();
    }
}
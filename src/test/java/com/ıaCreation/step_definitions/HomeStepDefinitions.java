package com.ıaCreation.step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import com.ıaCreation.pages.HomePage;

public class HomeStepDefinitions {
    
    private HomePage homePage;
    
    
    @Given("I am on the Fexse Home page")
    public void i_am_on_the_fexse_home_page() {
        homePage = new HomePage();
        // Navigation is handled by TestHooks, so we just initialize the page object
        // Handle Cookies popup if present
        homePage.acceptCookiesIfPresent();
    }
    
    @When ("I scroll down the page down to {string} section")
    public void i_scroll_down_the_page(String sectionName) {
        homePage.scrollToElementByText(sectionName);
    }
    
    @And("I click on the {string} button")
    public void i_click_on_the_button(String buttonName) {
        if (buttonName.equals("Real Estate")) {
            homePage.clickRealEstateButton();
        } else if (buttonName.equals("Details")) {
            homePage.clickDetailsButton();
        } else {
            homePage.clickElementByText(buttonName);
        }
    }
    
    @And("I click on the first asset image")
    public void i_click_on_the_first_asset_image() {
        homePage.clickFirstAssetImage();
    }
    
    @And("I scroll down to the {string} section")
    public void i_scroll_down_to_the_section(String sectionName) {
        homePage.scrollToElementByText(sectionName);
    }
    
    @And("I click on {string}")
    public void i_click_on(String elementText) {
        if (elementText.equals("EU Integration")) {
            homePage.clickEuIntegration();
        } else {
            homePage.clickElementByText(elementText);
        }
    }
    
    @Then("I should see the EU Integration details displayed")
    public void i_should_see_the_eu_integration_details_displayed() {
        homePage.waitForEuIntegrationDetails();
        
        boolean isVisible = homePage.isEuIntegrationDetailsDisplayed();
        if (!isVisible) {
            throw new AssertionError("EU Integration details are not displayed");
        }
    }
    
    @When("I navigate to the {string} section")
    public void i_navigate_to_the_section(String sectionName) {
        homePage.navigateToSection(sectionName);
    }
    
    @And("I filter by category {string}")
    public void i_filter_by_category(String category) {
        homePage.filterByCategory(category);
    }
    
    @Then("I should see a loading skeleton that disappears")
    public void i_should_see_a_loading_skeleton_that_disappears() {
        homePage.waitForLoadingSkeletonToDisappear();
    }
    
    @And("I should see at least {int} assets in the grid")
    public void i_should_see_at_least_assets_in_the_grid(int minCount) {
        homePage.verifyAssetsInGrid(minCount);
    }
    
    @When("I open the first asset in the grid")
    public void i_open_the_first_asset_in_the_grid() {
        homePage.openFirstAssetInGrid();
    }
    
    @Then("I should be on the Asset Details page")
    public void i_should_be_on_the_asset_details_page() {
        homePage.verifyAssetDetailsPage();
    }
    
    @When("I switch to the {string} tab")
    public void i_switch_to_the_tab(String tabName) {
        homePage.switchToTab(tabName);
    }
    
    @Then("I should see a document named {string}")
    public void i_should_see_a_document_named(String documentName) {
        homePage.verifyDocumentExists(documentName);
    }
    
    @And("the document should be downloadable or open in a new tab successfully")
    public void the_document_should_be_downloadable_or_open_in_a_new_tab_successfully() {
        homePage.verifyDocumentDownloadable();
    }
}

@home @ui @smoke
Feature: User will examine the Home page
  As a user:
  I want to navigate through the Home and Real Estate sections
  So that I can verify that the page and its elements work correctly

  Scenario: Navigate from Home to Real Estate asset details
    Given I am on the Fexse Home page
    When I scroll down the page down to "Marketplace" section
    And I click on the "Real Estate" button
    And I click on the first asset image
    And I click on the "Details" button
    And I scroll down to the "EU Integration" section
    And I click on "EU Integration"
    Then I should see the EU Integration details displayed

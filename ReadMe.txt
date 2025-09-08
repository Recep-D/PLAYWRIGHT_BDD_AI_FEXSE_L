description:
globs:
alwaysApply: true
---
# This is a robust and maintainable BDD automation project built with Playwright, Java, and Cucumber.
# Core principles: Code simplicity, unique locators, and smart waiting mechanisms.

# --- GENERAL RULES ---
# 1. AUTO-WAITING: Use Playwright’s built-in auto-waiting mechanisms.
# Methods like locator.click(), locator.fill(), assertThat(locator).isVisible() already wait for the element to be ready.
# NEVER use Thread.sleep(). Only in very rare cases (e.g., waiting for an animation to finish), use page.waitForTimeout().

# 2. UNIQUE LOCATOR: Locator priority order should be:
#    - page.getByTestId("...") or [data-testid="..."] (Best choice)
#    - page.getByRole(...)
#    - page.getById("#...")
#    - Non-dynamic, stable CSS classes or XPath (Last resort)

# 3. CODE SIMPLICITY: Avoid unnecessary code.
# Methods must do only one thing, and their names should reflect that purpose.
# Instead of chaining complex commands, create helper methods.

# --- FILE RULES ---

# Feature Files: Clear, business-focused scenarios.
src/test/resources/features//*.feature ->
A BDD test scenario. Contains user-focused steps, free of technical details.

# Page Object Classes: Encapsulation of a web page or component.
src/test/java/com/yourcompany/pages/*.java ->
This is a Page Object class.
- Contains only private final Page page and private final Locator fields.
- Constructor accepts a Page object and initializes locators.
- Public methods for page actions (navigate, click, fill). These leverage Playwright’s auto-waiting.
- Public methods for verifications. These use assertThat(locator)... with reliable assertions and internal waiting.

# Step Definition Classes: Bridge between Gherkin and Java.
src/test/java/com/yourcompany/stepdefs/*.java ->
This is a Cucumber Step Definition class.
- PURPOSE: Delegate Gherkin steps to corresponding Page Object methods.
- MUST NOT call Playwright API directly like page.click(). All interactions must go through Page classes.
- Constructor obtains the current Page instance via PlaywrightManager.getPage() and initializes the relevant Page Object.

# Core Classes: Framework infrastructure.
src/test/java/com/yourcompany/core/*.java ->
This contains framework infrastructure code.
- PlaywrightManager: Manages Playwright lifecycle with ThreadLocal, ensuring thread safety for parallel execution.
- Hooks: Uses @Before and @After to start and close the browser cleanly for each scenario.

Allure + Playwright Trace Reporting
-----------------------------------

Run tests:
- mvn test

Serve Allure report (auto-opens in browser):
- mvn allure:serve

Alternatively, generate static report and open:
- mvn allure:report
- open target/site/allure-maven-plugin/index.html

# Test Runner: Entry point for running tests.
src/test/java/com/yourcompany/runners/*.java ->
This is a JUnit/Cucumber test runner. Contains only @CucumberOptions for configuration.

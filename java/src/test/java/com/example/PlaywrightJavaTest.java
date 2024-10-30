package org.example;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.assertions.PageAssertions;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.*;


public class PlaywrightJavaTest {
    // These are shared between all tests in this class.
    static Playwright playwright;
    static Browser browser;

    // A new instance of the following objects appear for each test method.
    BrowserContext context;
    Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
    }

     @AfterEach
     void closeContext() {
        context.close();
    }

    // Log in
    @Test
    void shouldLoginLogOut() {
        page.navigate("https://practice.expandtesting.com/login");

        // Verify the page with the specified text appears correctly
        assertThat(page.getByRole(AriaRole.HEADING,
                                    new Page.GetByRoleOptions().setName("Test Login page for Automation Testing Practice"))).isVisible();

        // Enter username
        page.getByLabel("Username").fill("practice");

        // Enter password value
        page.getByRole(AriaRole.BUTTON,
                        new Page.GetByRoleOptions().setName("Login")).click();

        // Verify the login success message appears
        assertThat(page.getByText("You logged into a secure area!")).isVisible();
    }
}
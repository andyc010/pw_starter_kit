package org.example;

import support.DateAndTime;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.assertions.PageAssertions;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.FileChooser;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.nio.file.Paths;

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

    // Log in/log out test, using https://practice.expandtesting.com/login page
    @Test
    void shouldLoginLogOut() {
        page.navigate("https://practice.expandtesting.com/login");

        // Verify the page with the specified text appears correctly
        assertThat(page.getByRole(AriaRole.HEADING,
                                    new Page.GetByRoleOptions().setName("Test Login page for Automation Testing Practice"))).isVisible();

        // Enter username
        page.getByLabel("Username").fill("practice");

        // Enter password
        page.getByLabel("Password").fill("SuperSecretPassword!");

        // Enter password value
        page.getByRole(AriaRole.BUTTON,
                        new Page.GetByRoleOptions().setName("Login")).click();

        // Verify the login success message appears
        assertThat(page.getByText("You logged into a secure area!")).isVisible();

        // Click the Logout button/link
        page.getByRole(AriaRole.LINK,
                        new Page.GetByRoleOptions().setName("Logout")).click();
        
        // Verify the logout success message appears
        assertThat(page.getByText("You logged out of the secure area!")).isVisible();
    }

    // Form test/automation, using https://practice-automation.com/form-fields/ page
    @Test
    void shouldFillForm() {
        page.navigate("https://practice-automation.com/form-fields/");

        // Verify the page with the specified text appears correctly
        assertThat(page.getByRole(AriaRole.HEADING,
                                    new Page.GetByRoleOptions().setName("Form Fields"))).isVisible();
    
        // Add the current date & time to the Name & Password textboxes
        page.getByTestId("name-input").fill(DateAndTime.displayCurrentDateAndTime(DateAndTime.Usage.TEXT));
        page.getByLabel("Password").fill(DateAndTime.displayCurrentDateAndTime(DateAndTime.Usage.TEXT));

        // For the checkboxes, check the first two ("Water" and "Milk")
        page.getByTestId("drink1").click();
        page.getByTestId("drink2").click();

        // For the "What is your favorite color?" question with the radiobuttons, select the "Blue" option
        page.getByTestId("color2").click();

        // Fororg. the "Do you like automation?" question with the dropdown, select the "Yes" option
        page.getByTestId("automation").selectOption("Yes");

        // Add the current date & time to the Message textbox
        page.getByTestId("message").fill(DateAndTime.displayCurrentDateAndTime(DateAndTime.Usage.TEXT));

        // Get a screenshot of the entire page after webpage elements have been selected
        page.screenshot(new Page.ScreenshotOptions().setFullPage(true).setPath(Paths.get("./screenshots/" + DateAndTime.displayCurrentDateAndTime(DateAndTime.Usage.FILENAME) + ".png")));
        
        // Click the Submit button to submit the form
        page.getByTestId("submit-btn").click();

        // Alert here, dismiss it
        page.onDialog(dialog -> {
            dialog.dismiss();
        });
    }

    // Another form test/automation with a slider control, using https://practice-automation.com
    @Test
    void shouldEditSlider() {
        page.navigate("https://practice-automation.com/slider/");

        // Verify that the webpage with the specified text appears correctly
        assertThat(page.getByRole(AriaRole.HEADING,
                                    new Page.GetByRoleOptions().setName("Slider"))).isVisible();

        // Move the slider such that the text beneath it reads "Current value: 50"
        page.locator("#slideMe").fill("50");

        // Verify that the text reads "50"
        assertThat(page.getByText("50")).isVisible();
    }

    // File upload test, using https://practice.expandtesting.com/upload page
    @Test
    void shouldUploadFile() {
        page.navigate("https://practice.expandtesting.com/upload");

        // Verify the page that is needed is present
        assertThat(page.getByRole(AriaRole.HEADING,
                    new Page.GetByRoleOptions().setName("File Uploader page for Automation Testing Practice"))).isVisible();

        // Upload the file
        final String uploadFile = "20241016_csv_sample_file_upload.csv"; 
        FileChooser fileChooser = page.waitForFileChooser(() -> page.getByTestId("file-input").click());
        // "Paths" is from a Java library, see import statement at the top of this file
        fileChooser.setFiles(Paths.get("../upload_files/" + uploadFile));

        // Click the Upload button
        page.getByTestId("file-submit").click();

        // Verify the "File Uploaded!" message appears
        assertThat(page.getByRole(AriaRole.HEADING,
                    new Page.GetByRoleOptions().setName("File Uploaded!"))).isVisible();
    }

    // Table with data test, using https://practice-automation.com/tables/ page
    @Test
    void shouldSeeTables() {
        page.navigate("https://practice-automation.com/tables/");

        // Verify that the page that is wanted is appearing
        assertThat(page.getByRole(AriaRole.HEADING,
                    new Page.GetByRoleOptions().setName("Tables"))).isVisible();

        // Using the sortable table, press the Next link
        page.getByText("Next").click();

        // Verify the information on the first row (11th entry/rank with country = Ethiopia, population = 126.5 million) is present
        assertThat(page.locator("td:has-text(\"Ethiopia\")")).isVisible();
        assertThat(page.locator("td:has-text(\"126.5\")")).isVisible();
    }
}
using System.Threading.Tasks;
using Microsoft.Playwright;
using Microsoft.Playwright.NUnit;
using NUnit.Framework;

namespace PlaywrightTests;

[Parallelizable(ParallelScope.Self)]
[TestFixture]
public class PlaywrightDotnetTests : PageTest
{
    // Log in/log out test, using https://practice.expandtesting.com/login page
    [Test]
    public async Task LoginLogout()
    {
        await Page.GotoAsync("https://practice.expandtesting.com/login");

        // Verify the page with the specified text appears correctly
        await Expect(Page.GetByRole(AriaRole.Heading, new() { Name = "Test Login page for Automation Testing Practice" })).ToBeVisibleAsync();

        // Enter username
        await Page.GetByLabel("Username").FillAsync("practice");

        // Enter password value
        await Page.GetByLabel("Password").FillAsync("SuperSecretPassword!");

        // Press the Login button
        await Page.GetByRole(AriaRole.Button, new() { Name = "Login" }).ClickAsync();

        // Verify the login success message appears
        await Expect(Page.GetByText("You logged into a secure area!")).ToBeVisibleAsync();

        // Click the Logout button/link
        await Page.GetByRole(AriaRole.Link, new() { Name = "Logout" }).ClickAsync();

        // Verify the logout success message appears
        await Expect(Page.GetByText("You logged out of the secure area!")).ToBeVisibleAsync();
    }

    // Form test/automation, using https://practice-automation.com/form-fields/ page
    [Test]
    public async Task SelectFormElements()
    {
        await Page.GotoAsync("https://practice-automation.com/form-fields/");

        // Verify the page with the specified text appears correctly
        await Expect(Page.GetByRole(AriaRole.Heading, new() { Name = "Form Fields" })).ToBeVisibleAsync();
    
        // Add the current date & time to the Name & Password textboxes
        await Page.GetByTestId("name-input").FillAsync(DateAndTime.displayCurrentDateAndTime(DateAndTime.Usage.text));
        await Page.GetByLabel("Password").FillAsync(DateAndTime.displayCurrentDateAndTime(DateAndTime.Usage.text));

        // For the checkboxes, check the first two ("Water" and "Milk")
        await Page.GetByTestId("drink1").ClickAsync();
        await Page.GetByTestId("drink2").ClickAsync();

        // For the "What is your favorite color?" question with the radiobuttons, select the "Blue" option
        await Page.GetByTestId("color2").ClickAsync();

        // For the "Do you like automation?" question with the dropdown, select the "Yes" option
        await Page.GetByTestId("automation").SelectOptionAsync("Yes");

        // Add the current date & time to the Message textbox
        await Page.GetByTestId("message").FillAsync(DateAndTime.displayCurrentDateAndTime(DateAndTime.Usage.text));

        // Get a screenshot of the entire page after webpage elements have been selected
        await Page.ScreenshotAsync(new()
        {
            // Need to adjust the screeenshots path such that the screenshots folder is in the dotnet one
            FullPage = true,
            Path = "../../../screenshots/" + DateAndTime.displayCurrentDateAndTime(DateAndTime.Usage.filename) + ".png",
        });

        // Click the Submit button to submit the form
        await Page.GetByTestId("submit-btn").ClickAsync();

        // Alert here, dismiss it
        Page.Dialog += async (_, dialog) =>
        {
            await dialog.DismissAsync();
        };
    }

    // Another form test/automation with a slider control, using https://practice-automation.com
    [Test]
    public async Task MoveFormSlider()
    {
        await Page.GotoAsync("https://practice-automation.com/slider/");
    
        // Verify that the webpage with the specified text appears correctly
        await Expect(Page.GetByRole(AriaRole.Heading, new() { Name = "Slider" })).ToBeVisibleAsync();

        // Move the slider such that the text beneath it reads "Current value: 50"
        await Page.Locator("#slideMe").FillAsync("50");

        // Verify that the text reads "50"
        await Expect(Page.GetByText("50")).ToBeVisibleAsync();
    }

    // File upload test, using https://practice.expandtesting.com/upload page
    [Test]
    public async Task FileUpload()
    {
        const string upload_file_used = "20241016_csv_sample_file_upload.csv";
        
        await Page.GotoAsync("https://practice.expandtesting.com/upload");

        // Verify the page that is needed is present
        await Expect(Page.GetByRole(AriaRole.Heading, new() { Name = "File Uploader page for Automation Testing Practice" })).ToBeVisibleAsync();

        // Upload the file
        var fileChooser = await Page.RunAndWaitForFileChooserAsync(async() =>
        {
           await Page.GetByTestId("file-input").ClickAsync(); 
        });
        // The executable is in the bin folder deep within the project for .NET, so this will be different from the rest of the projects
        await fileChooser.SetFilesAsync("../../../../upload_files/" + upload_file_used);

        // Click the Upload button
        await Page.GetByTestId("file-submit").ClickAsync();

        // Verify the "File Uploaded!" message appears
        await Expect(Page.GetByRole(AriaRole.Heading, new() { Name = "File Uploaded!" })).ToBeVisibleAsync();
    }

    // Table with data test, using https://practice-automation.com/tables/ page
    [Test]
    public async Task ViewTableData()
    {
        await Page.GotoAsync("https://practice-automation.com/tables/");

        // Verify that the page that is wanted is appearing
        await Expect(Page.GetByRole(AriaRole.Heading, new() { Name = "Tables" })).ToBeVisibleAsync();

        // Using the sortable table, press the Next link
        await Page.GetByText("Next").ClickAsync();

        // Verify the information on the first row (11th entry/rank with country = Ethiopia, population = 126.5 million) is present
        await Expect(Page.Locator("td", new() { HasText = "Ethiopia" })).ToBeVisibleAsync();
        await Expect(Page.Locator("td", new() { HasText = "126.5" })).ToBeVisibleAsync();
    }
}
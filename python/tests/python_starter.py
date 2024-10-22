# With the way that everything is organized, I had to add the first two lines in order for pytest, python to
# navigate to the correct directory for the support class for getting the date & time
import sys
sys.path.insert(1, "support")
from date_and_time import Usage, return_date_and_time
from playwright.sync_api import Page, expect
from pathlib import Path

# Log in/log out test, using https://practice.expandtesting.com/login page
def test_log_in_log_out(page: Page):
    page.goto("https://practice.expandtesting.com/login")

    # Verify the page with the specified text appears correctly
    expect(page.get_by_role("heading", name="Test Login page for Automation Testing Practice")).to_be_visible()

    # Enter username
    page.get_by_label("Username").fill("practice")

    # Enter password value
    page.get_by_label("Password").fill("SuperSecretPassword!")

    # Press the Login button
    page.get_by_role("button", name="Login").click()

    # Verify the login success message appears
    expect(page.get_by_text("You logged into a secure area!")).to_be_visible()

    # Click the Logout button/link
    page.get_by_role("link", name="Logout").click()

    # Verify the logout success message appears
    expect(page.get_by_text("You logged out of the secure area!")).to_be_visible()

# A small function needed to handle the dialog in the following test
def handle_dialog(dialog):
    print(dialog.message)
    dialog.dismiss()

def test_form_textbox_checkbox_radiobutton_dropdown_alert(page: Page):
    page.goto("https://practice-automation.com/form-fields/")

    # Verify the page with the specified text appears correctly
    expect(page.get_by_role("heading", name="Form Fields")).to_be_visible()
    
    # Add the current date & time to the Name & Password textboxes
    page.get_by_test_id("name-input").fill(return_date_and_time(Usage.TEXT))
    page.get_by_label("Password").fill(return_date_and_time(Usage.TEXT))

    # For the checkboxes, check the first two ("Water" and "Milk")
    page.get_by_test_id("drink1").click()
    page.get_by_test_id("drink2").click()

    # For the "What is your favorite color?" question with the radiobuttons, select the "Blue" option
    page.get_by_test_id("color2").click()

    # For the "Do you like automation?" question with the dropdown, select the "Yes" option
    page.get_by_test_id("automation").select_option("Yes")

    # Add the current date & time to the Message textbox
    page.get_by_test_id("message").fill(return_date_and_time(Usage.TEXT))

    # Get a screenshot of the entire page after webpage elements have been selected
    # Used TestInfo class to get name of the test, from https://stackoverflow.com/questions/73774819/how-to-get-the-current-test-name-for-playwright-in-typescript
    page.screenshot(path=Path("./screenshots/" + return_date_and_time(Usage.FILENAME) + '.png'), full_page=True)

    # Click the Submit button to submit the form
    page.get_by_test_id("submit-btn").click()

    # Alert here, dismiss it
    page.on("dialog", handle_dialog)

# Another form test/automation with a slider control, using https://practice-automation.com
def test_form_slider(page: Page):
    page.goto("https://practice-automation.com/slider/")
    
    # Verify that the webpage with the specified text appears correctly
    expect(page.get_by_role("heading", name="Slider")).to_be_visible()

    # Move the slider such that the text beneath it reads "Current value: 50"
    page.locator("#slideMe").fill("50")

    # Verify that the text reads "50"
    expect(page.get_by_text("50")).to_be_visible()

# For this next test, can use one of the supplied files in the upload_files directory
upload_file_used = "20241016_csv_sample_file_upload.csv"

# File upload test, using https://practice.expandtesting.com/upload page
def test_file_upload(page: Page): 
    page.goto("https://practice.expandtesting.com/upload")

    # Verify the page that is needed is present
    expect(page.get_by_role("heading", name="File Uploader page for Automation Testing Practice")).to_be_visible()

    # Upload the file
    with page.expect_file_chooser() as fc_info:
        page.get_by_test_id("file-input").click()
    file_chooser = fc_info.value
    file_chooser.set_files(Path("../upload_files/" + upload_file_used))

    # Click the Upload button
    page.get_by_test_id("file-submit").click()

    # Verify the "File Uploaded!" message appears
    expect(page.get_by_role("heading", name="File Uploaded!")).to_be_visible()

# Table with data test, using https://practice-automation.com/tables/ page
def test_tables(page: Page):
    page.goto("https://practice-automation.com/tables/")

    # Verify that the page that is wanted is appearing
    expect(page.get_by_role("heading", name="Tables")).to_be_visible()

    # Using the sortable table, press the Next link
    page.get_by_text("Next").click()

    # Verify the information on the first row (11th entry/rank with country = Ethiopia, population = 126.5 million) is present
    expect(page.locator("td", has_text="Ethiopia")).to_be_visible()
    expect(page.locator("td", has_text="126.5")).to_be_visible()
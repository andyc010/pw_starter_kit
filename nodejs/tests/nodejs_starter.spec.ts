import { test, expect } from '@playwright/test';
import { Usage, displayCurrentDateAndTime } from  "../support/date_and_time.ts";

// Log in/log out test, using https://practice.expandtesting.com/login page
test('Log_in_log_out', async ({ page }) => {
    await page.goto("https://practice.expandtesting.com/login");

    // Verify the page with the specified text appears correctly
    await expect(page.getByRole('heading', { name: 'Test Login page for Automation Testing Practice' })).toBeVisible();

    // Enter username
    await page.getByLabel('Username').fill('practice');

    // Enter password value
    await page.getByLabel('Password').fill('SuperSecretPassword!');

    // Press the Login button
    await page.getByRole('button', { name: 'Login'}).click();

    // Verify the login success message appears
    await expect(page.getByText('You logged into a secure area!')).toBeVisible();

    // Click the Logout button/link
    await page.getByRole('link', { name: 'Logout'}).click();

    // Verify the logout success message appears
    await expect(page.getByText('You logged out of the secure area!')).toBeVisible();
});

// Form test/automation, using https://practice-automation.com/form-fields/ page
test('Form_textbox_checkbox_radiobutton_dropdown_alert', async ({ page }, TestInfo) => {
    await page.goto("https://practice-automation.com/form-fields/");

    // Verify the page with the specified text appears correctly
    await expect(page.getByRole('heading', { name: 'Form Fields' })).toBeVisible();
    
    // Add the current date & time to the Name & Password textboxes
    await page.getByTestId('name-input').fill(displayCurrentDateAndTime(Usage.text));
    await page.getByLabel('Password').fill(displayCurrentDateAndTime(Usage.text));

    // For the checkboxes, check the first two ("Water" and "Milk")
    await page.getByTestId("drink1").click();
    await page.getByTestId("drink2").click();

    // For the "What is your favorite color?" question with the radiobuttons, select the "Blue" option
    await page.getByTestId("color2").click();

    // For the "Do you like automation?" question with the dropdown, select the "Yes" option
    await page.getByTestId("automation").selectOption("Yes");

    // Add the current date & time to the Message textbox
    await page.getByTestId("message").fill(displayCurrentDateAndTime(Usage.text));

    // Get a screenshot of the entire page after webpage elements have been selected
    // Used TestInfo class to get name of the test, from https://stackoverflow.com/questions/73774819/how-to-get-the-current-test-name-for-playwright-in-typescript
    await page.screenshot({ path: './screenshots/' + displayCurrentDateAndTime(Usage.file) + '_' + TestInfo.title +'.png', fullPage: true });

    // Click the Submit button to submit the form
    await page.getByTestId('submit-btn').click();

    // Alert here, dismiss it
    page.on('dialog', dialog => dialog.accept());
});

// Another form test/automation with a slider control, using https://practice-automation.com
test('Form_slider', async ({ page }) => {
    await page.goto("https://practice-automation.com/slider/");
    
    // Verify that the webpage with the specified text appears correctly
    await expect(page.getByRole('heading', { name: 'Slider' })).toBeVisible();

    // Move the slider such that the text beneath it reads "Current value: 50"
    await page.locator('#slideMe').fill('50');

    // Verify that the text reads "50"
    await expect(page.getByText('50')).toBeVisible();
});

// For this next test, can use one of the supplied files in the upload_files directory
const upload_file_used = '20241016_csv_sample_file_upload.csv';

// File upload test, using https://practice.expandtesting.com/upload page
test('File_upload', async ({ page }) => {
    await page.goto("https://practice.expandtesting.com/upload");

    // Verify the page that is needed is present
    await expect(page.getByRole('heading', { name: 'File Uploader page for Automation Testing Practice' })).toBeVisible();

    // Upload the file
    const fileChooserPromise = page.waitForEvent('filechooser');
    await page.getByTestId('file-input').click();
    const fileChooser = await fileChooserPromise;
    // 'path' is from nodejs, see https://nodejs.org/en/learn/manipulating-files/nodejs-file-paths
    const path = require('node:path');
    await fileChooser.setFiles(path.join('../upload_files/', upload_file_used));

    // Click the Upload button
    await page.getByTestId('file-submit').click();

    // Verify the "File Uploaded!" message appears
    await expect(page.getByRole('heading', { name: 'File Uploaded!' })).toBeVisible();
});

// Table with data test, using https://practice-automation.com/tables/ page
test('Tables', async ({ page }) => {
    await page.goto("https://practice-automation.com/tables/");

    // Verify that the page that is wanted is appearing
    await expect(page.getByRole('heading', { name: 'Tables' })).toBeVisible();

    // Using the sortable table, press the Next link
    await page.getByText('Next').click();

    // Verify the information on the first row (11th entry/rank with country = Ethiopia, population = 126.5 million) is present
    await expect(page.locator('td', { hasText: 'Ethiopia' })).toBeVisible();
    await expect(page.locator('td', { hasText: '126.5'})).toBeVisible();
});
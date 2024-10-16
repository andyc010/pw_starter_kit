import { test, expect } from '@playwright/test';
import { Usage, displayCurrentDateAndTime } from  "../support/classes/date_and_time.ts";

// Log in/log out test, use expandtesting.com/login
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

// Form test/automation, use practice-automation.com/form-fields/ page
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
    //await page.screenshot({ path: '../screenshots/' + displayCurrentDateAndTime(Usage.file) + '_screenshot.png', fullPage: true });
    await page.screenshot({ path: './screenshots/' + displayCurrentDateAndTime(Usage.file) + '_' + TestInfo.title +'.png', fullPage: true });

    // Click the Submit button to submit the form
    await page.getByTestId('submit-btn').click();

    // Alert here
    page.on('dialog', dialog => dialog.accept());
});
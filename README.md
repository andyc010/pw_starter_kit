# Playwright Starter Kit

## Introduction
This repository is a collection of basic tests using the [Playwright](https://playwright.dev) framework in an effort to help people (including myself!) get started on working with it. Or (at the very least), take a closer look at using Playwright in a certain environment/language, such as in .NET w/C#.

I am using publicly-available websites of [Practice Test Automation WebSite](https://practice.expandtesting.com/) and [automateNow](https://practice-automation.com/). These websites are part of a [list](https://github.com/BMayhew/awesome-sites-to-test-on) from Butch Mayhew, one of the [Playwright ambassadors](https://playwright.dev/python/community/ambassadors).

I am hoping that I am doing my part to help the adoption of Playwright being used in testing.

## Running the tests
The instructions are generally from the Playwright documentation at https://playwright.dev/.

### NodeJS
First, ensure NodeJS is installed on the computer that will be used to install the tests. Then, download the folder & files (or clone the repository).

Go inside the `nodeJS` folder, and using a command-line interface (as in a terminal for Linux or Mac, or PowerShell for Windows), type the following command to install the necessary files for the tests to run:
```
npm init playwright@latest
```
To run the tests, type the following command:
```
npx playwright test tests/nodejs_starter.spec.ts
```

### Python
I've created a Python virtual environment to be used for Playwright testing, and documentation is provided at https://docs.python.org/3/library/venv.html to create one.

After creating one, activate the virtual environment and type the following command:
```
pip install pytest-playwright
```
Type the following command to install the required browsers:
```
playwright install
```

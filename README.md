# Playwright Starter Kit

## Introduction
This repository is a collection of basic tests using the [Playwright](https://playwright.dev) framework in an effort to help individuals (including myself!) get started on working with Playwright, or to simply take a closer look at using it in a certain environment/language, such as in .NET w/C#.

I am using publicly-available websites of [Practice Test Automation WebSite](https://practice.expandtesting.com/) and [automateNow](https://practice-automation.com/). These websites are part of a [list](https://github.com/BMayhew/awesome-sites-to-test-on) from Butch Mayhew, one of the [Playwright ambassadors](https://playwright.dev/python/community/ambassadors).

My hope that I am making a contribution to the adoption of Playwright being used in testing.

## Running the tests
The instructions are generally from the Playwright documentation at https://playwright.dev/.

To help limit the amount of differences between running on different operating systems, these tests below should work with a minimal amount of changes by using the commands below.

(I have been using Linux operating systems of Fedora & Linux Mint while creating these tests)

### NodeJS
First, ensure NodeJS is installed on the computer that will be used to install the tests. Then, download the folder & files (or clone the repository).

Go inside the `nodeJS` folder of the repo, and using a command-line interface (as in a terminal for Linux or Mac, or PowerShell for Windows), type the following command to install the necessary files for the tests to run:
```
npm init playwright@latest
```
To run the tests, type the following command:
```
npx playwright test tests/nodejs_starter.spec.ts
```

### Python
I recommend creating a Python virtual environment to be used for Playwright testing, and documentation is provided at https://docs.python.org/3/library/venv.html to create one. Once one has been set up & activated, run the following commands:
```
pip install pytest-playwright
playwright install
```
To run the tests **without seeing the browser (headless)**, navigate into the `python` folder of the repository, and type:
```
pytest tests/python_starter.py
```
To run the tests while seeing them in action in browsers, type the following instead:
```
pytest tests/python_starter.py --headed
```

### Java
First, Java or OpenJDK will need to be installed along with Maven, as the following commands below will require to have both installed.

Once Java/OpenJDK and Maven have been installed, go inside the `java` folder of the repository & enter the following command to run the tests:
```
mvn test
```

### .NET (and C#)
Once the files have been downloaded/repository is cloned, enter the `dotnet` directory and enter the following command:
```
dotnet build
```
Then, using PowerShell (if on Mac or an Ubuntu/Ubuntu-based distro of Linux such as Linux Mint, it will need to be installed), enter the following command to install the browsers necessary for testing:
```
pwsh bin/Debug/net8.0/playwright.ps1 install
```
Finally, to run the tests, type the following command:
```
dotnet test --filter "PlaywrightDotnetTests"
```
To run the tests with the tests actually running in the browser, type the following for Linux:
```
HEADED=1 dotnet test --filter "PlaywrightDotnetTests"
```
Here is the same command but using PowerShell (Windows):
```
$env:HEADED="1"
dotnet test --filter "PlaywrightDotnetTests"
```


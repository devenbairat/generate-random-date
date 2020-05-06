# generate-random-date
This repository contains test which generates 50 random dates on 'https://codebeautify.org/generate-random-date' for a given start date, end date and a date format as inputs.
Then the generated dates are validated for:
1) The number of dates generated are same to the number of dates requested.
2) The format of all the generated dates are as per the selected date format.
3) All the random dates generated are having year, month, day, hours, minutes and seconds in range of the requested start date and end date.

## Coverage:
Only 1 happy path scenario has been added for coverage. 
The scenario can be easily converted to Scenario Outline by changing the date format, start date and end date. The corresponding regex validations needs to be updated accordingly to fulfil the input criteria.

# The following version of Chrome and Chromedriver were used on MacOS:
Google Chrome: Version 81.0.4044.138 (Official Build) (64-bit)
ChromeDriver: 81.0.4044.138 (https://chromedriver.storage.googleapis.com/index.html?path=81.0.4044.138/)

# Test Execution:
Clone the build.
Make sure you have maven installed.
Open terminal at the root folder of the project and execute this command: mvn test

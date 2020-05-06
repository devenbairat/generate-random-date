Feature: Random Date Generator
  As a user of Random Date Generator,
  I want to validate the generated dates,
  Are as per the input criteria

  @browser @date-generator
  Scenario: Verify exact random dates are generated as per the input criteria
    Given I go to the random date generator homepage
    And set the random date generation criteria as
      | datesToGenerate | dateFormat          | startDate  | endDate    |
      | 50              | yyyy-mm-dd-hh-mm-ss | 2000-01-01 | 2009-02-02 |
    When I click to generate random dates
    Then random generated dates should contain
      | datesGenerated | dateFormat          | yearRange    | monthRange       | dayRange                       | hourRange               | minuteRange  | secondRange  |
      | 50             | yyyy-MM-dd hh:mm:ss | ^20(0[0-9])$ | ^0[1-9]\|1[0-2]$ | ^(0?[1-9]\|[12][0-9]\|3[01])$  | ^([01][0-9]\|2[0-3])$   | ^[0-5][0-9]$ | ^[0-5][0-9]$ |

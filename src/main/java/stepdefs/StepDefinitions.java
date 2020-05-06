package stepdefs;

import cucumber.api.Scenario;
import io.cucumber.datatable.DataTable;
import pages.RandomDateGenerator;
import cucumber.api.java8.En;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.*;

public class StepDefinitions implements En {

    private WebDriver driver;
    private RandomDateGenerator rdg;

    public StepDefinitions() {


        Before(new String[] {"@browser"}, 0, 1, (Scenario scenario) -> {
            driver = new ChromeDriver();
            driver.manage().window().fullscreen();
        });

        Before(new String[]{"@date-generator"}, 0, 10, (Scenario scenario) -> {
            rdg = new RandomDateGenerator(driver);
        });

        Given("^I go to the random date generator homepage$", () -> {
            rdg.navigateToHomePage();
        });

        And("^set the random date generation criteria as$", (DataTable dataTable) -> {
            List<Map<String, String>> data =  dataTable.asMaps();
            rdg.setNumberOfDates(data.get(0).get("datesToGenerate"))
                .setDateOutputFormat(data.get(0).get("dateFormat"))
                .setStartDate(data.get(0).get("startDate"))
                .setEndDate(data.get(0).get("endDate"));

        });

        And("^I click to generate random dates$", () -> {
            rdg.generateRandomDates();
        });

        And("^random generated dates should contain$", (DataTable dataTable) -> {
            String randomDates = rdg.getRandomGeneratedDates();
            rdg.assertDateRange(dataTable, randomDates);
        });

        After(new String[]{"@date-generator"}, (Scenario scenario) -> {
            driver.quit();
        });
    }
}

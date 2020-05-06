package pages;

import framework.browser.BrowserBase;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public class RandomDateGenerator extends BrowserBase {

    private static final String RDG_HOME_URL = "https://codebeautify.org/generate-random-date";
    private static final By BY_GENERATE_DATES = By.xpath("//div[contains(@class,'primary-button')]/button[contains(@class,'generatejson')]");
    private static final By BY_DATE_OUTPUT_FORMAT = By.xpath("//select[contains(@id,'option-format')]");
    private static final By BY_DATES_NUM = By.xpath("//input[@placeholder='Count']");
    private static final By BY_START_DATE = By.xpath("//input[@placeholder='Start date']");
    private static final By BY_END_DATE = By.xpath("//input[@placeholder='End date']");
    private static final By BY_RANDOM_DATES = By.xpath("//div[@class='data-wrapper']/textarea");

    public RandomDateGenerator(WebDriver driver) {
        super(driver);
    }

    public void navigateToHomePage() {
        getDriver().navigate().to(RDG_HOME_URL);
    }

    public RandomDateGenerator setNumberOfDates(String count) {
        WebElement dateNum = driverWait(60).until(ExpectedConditions.presenceOfElementLocated(BY_DATES_NUM));
        dateNum.clear();
        dateNum.sendKeys(count);
        return this;
    }

    public RandomDateGenerator setDateOutputFormat(String value) {
        WebElement dateFormatDropdown = driverWait(60).until(ExpectedConditions.presenceOfElementLocated(BY_DATE_OUTPUT_FORMAT));
        Select dateFormat = new Select(dateFormatDropdown);
        dateFormat.selectByValue(value);
        return this;
    }

    public RandomDateGenerator setStartDate(String date) {
        WebElement startDate = driverWait(60).until(ExpectedConditions.presenceOfElementLocated(BY_START_DATE));
        startDate.clear();
        startDate.sendKeys(date);
        return this;
    }

    public RandomDateGenerator setEndDate(String date) {
        WebElement endDate = driverWait(60).until(ExpectedConditions.presenceOfElementLocated(BY_END_DATE));
        endDate.clear();
        endDate.sendKeys(date);
        return this;
    }

    public void generateRandomDates() {
        WebElement generateDates = driverWait(60).until(ExpectedConditions.elementToBeClickable(BY_GENERATE_DATES));
        generateDates.click();
    }

    public String getRandomGeneratedDates() {
        WebElement getGeneratedDates = driverWait(60).until(ExpectedConditions.elementToBeClickable(BY_RANDOM_DATES));
        return getGeneratedDates.getAttribute("value");
    }

    public void assertDateRange(DataTable dataTable, String randomDates) {
        List<Map<String, String>> data =  dataTable.asMaps();

        String dateFormat     = data.get(0).get("dateFormat");
        String datesGenerated = data.get(0).get("datesGenerated");
        String yearRange      = data.get(0).get("yearRange");
        String monthRange     = data.get(0).get("monthRange");
        String dayRange       = data.get(0).get("dayRange");
        String hourRange      = data.get(0).get("hourRange");
        String minuteRange    = data.get(0).get("minuteRange");
        String secondRange    = data.get(0).get("secondRange");

        assertNumberOfDatesGenerated(randomDates, datesGenerated);

        DateFormat format = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        String[] dates = randomDates.split("\n");
        for (String d : dates) {
            try {
                Date date = format.parse(d);

                assertRandomDateYearIsInRange(date, yearRange);
                assertRandomDateMonthIsInRange(date, monthRange);
                assertRandomDateDayIsInRange(date, dayRange);
                assertRandomDateHourIsInRange(date, hourRange);
                assertRandomDateMinutesIsInRange(date, minuteRange);
                assertRandomDateSecondsIsInRange(date, secondRange);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void assertNumberOfDatesGenerated(String randomDates, String datesGenerated) {
        Integer numOfDates = randomDates.split("\n").length;
        assertEquals(numOfDates.toString(), datesGenerated);
    }

    private void assertRandomDateYearIsInRange(Date date, String yearRange){
        DateFormat year    = new SimpleDateFormat("yyyy");
        assertThat(year.format(date), matchesPattern(yearRange));
    }

    private void assertRandomDateMonthIsInRange(Date date, String monthRange){
        DateFormat month   = new SimpleDateFormat("MM");
        assertThat(month.format(date), matchesPattern(monthRange));
    }

    private void assertRandomDateDayIsInRange(Date date, String dayRange){
        DateFormat day     = new SimpleDateFormat("dd");
        assertThat(day.format(date), matchesPattern(dayRange));
    }

    private void assertRandomDateHourIsInRange(Date date, String hourRange){
        DateFormat hours   = new SimpleDateFormat("hh");
        assertThat(hours.format(date), matchesPattern(hourRange));
    }

    private void assertRandomDateMinutesIsInRange(Date date, String minuteRange){
        DateFormat minutes = new SimpleDateFormat("mm");
        assertThat(minutes.format(date), matchesPattern(minuteRange));
    }

    private void assertRandomDateSecondsIsInRange(Date date, String secondRange){
        DateFormat seconds = new SimpleDateFormat("ss");
        assertThat(seconds.format(date), matchesPattern(secondRange));
    }
}

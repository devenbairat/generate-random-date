package framework.browser;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public abstract class BrowserBase {

    private static final By BY_COOKIE_BANNER_CLOSE = By.xpath("//div[contains(@class,'cmc-cookie-policy-banner__close')]");

    private WebDriver driver;

    public BrowserBase(WebDriver driver) {
        this.driver = driver;
    }

    protected WebDriver getDriver() {
        return driver;
    }

    protected WebDriverWait driverWait(long timeoutSeconds) {
        return new WebDriverWait(driver, timeoutSeconds);
    }

    public void closeCookieBanner(){
        driverWait(60).until(ExpectedConditions.elementToBeClickable(BY_COOKIE_BANNER_CLOSE));
        List<WebElement> cookieBanner = getDriver().findElements(BY_COOKIE_BANNER_CLOSE);
        if (cookieBanner.size() > 0)
            cookieBanner.get(0).click();
    }

    public void waitForPageLoadComplete(int specifiedTimeout) {
        Wait<WebDriver> wait = new WebDriverWait(driver, specifiedTimeout);
        wait.until(driver -> String
                .valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
                .equals("complete"));
    }

    public void scrollPageToDisplayElement(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        driverWait(30).until(ExpectedConditions.elementToBeClickable(element));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException("Unexpected interrupt", e);
        }
        actions.perform();
    }
}

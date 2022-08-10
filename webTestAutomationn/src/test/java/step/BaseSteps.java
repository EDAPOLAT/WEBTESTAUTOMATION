package step;
import WebAutomationBase.helper.ElementHelper;
import WebAutomationBase.helper.StoreHelper;
import WebAutomationBase.model.ElementInfo;
import base.BaseTest;
import core.MailType;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.Log4jLoggerAdapter;
import java.util.Random;


public class BaseSteps extends BaseTest {

    public static final int DEFAULT_TIMEOUT = 30;
    public static final int DEFAULT_SLEEP_IN_MILLISECOND = 500;

    FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);

    JavascriptExecutor jsDriver= (JavascriptExecutor) driver;

    private Random random = new Random();

    public static Log4jLoggerAdapter logger = (Log4jLoggerAdapter) LoggerFactory
            .getLogger(BaseSteps.class);

    public WebElement findElement(String key) {
        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        By infoParam = ElementHelper.getElementInfoToBy(elementInfo);
        WebDriverWait webDriverWait = new WebDriverWait(driver, 60);
        WebElement webElement = webDriverWait
                .until(ExpectedConditions.presenceOfElementLocated(infoParam));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})",
                webElement);
        return webElement;
    }

    public void sendKeys(String key, String text){

        findElement(key).sendKeys(text);
        logger.info("Elemente " + text + " texti yazıldı.");
    }

    public String generateEmail() {
        return generateEmail(MailType.GMAIL);
    }

    public String generateEmail(MailType mailType) {
        String mailHeader = generateAlphabetic(generateNumberBetweenTwoDigits(4, 10)).toLowerCase();
        return mailHeader + mailType.value;
    }

    public String generateAlphabetic(int numberOfDigit) {
        return RandomStringUtils.randomAlphabetic(numberOfDigit);
    }
    public int generateNumberBetweenTwoDigits(int min, int max) {
        if (min > max) {
            throw new IndexOutOfBoundsException("minimum değer maximum değerden büyük olamaz!");
        }
        return generateRandomNumber(max - min + 1) + min;
    }

    public int generateRandomNumber(int bound) {
        return random.nextInt(bound);
    }


    public void clickElement(By by){
        findElements(by).click();
        logger.info("Elemente tıklandı.");
    }

    public WebElement findElements(By by){

        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public By getBy(String key){

        By by = ElementHelper.getElementInfoToBy(getElementInfo(key));
        logger.info(key + " elementi " + by.toString() + " by değerine sahip");
        return by;
    }

    public ElementInfo getElementInfo(String key){

        return StoreHelper.INSTANCE.findElementInfoByKey(key);
    }

    public WebElement waitUntilVisible(By locator) {
        return waitUntilVisible(locator, DEFAULT_TIMEOUT);
    }

    public WebElement waitUntilVisible(By locator, int second) {
        try {
            return getWebDriverWait(second).until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception ex) {
            String errorMessage = String.format("'%s' elementi sayfa üzerinde görünür olması beklenirken sorun oluştu! Hata kodu: %s", locator, ex.getMessage());
            logger.error(errorMessage);
            Assert.fail(errorMessage);
        }
        return null;
    }

    public WebDriverWait getWebDriverWait (int second) {
        return new WebDriverWait(driver, second, DEFAULT_SLEEP_IN_MILLISECOND);
    }


    public void selectByVisibleText(By by, String text){

        getSelect(by).selectByVisibleText(text);
    }
    public Select getSelect(By by){

        return new Select(findElements(by));
    }
    public void waitBySeconds(long seconds){

        waitByMilliSeconds(seconds*1000);
    }
    public void waitByMilliSeconds(long milliSeconds){

        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getText(By by){

        return findElements(by).getText();
    }
    public void mustBeContains(String actualText, String expectedText) {
        if (!actualText.contains(expectedText)) {
            String assertionMessage = String.format("'%s', '%s' değerini içermiyor!", actualText, expectedText);
            logger.error(assertionMessage);
            Assert.fail(assertionMessage);
        }
        logger.info("'{}', '{}' değerini içeriyor.", actualText, expectedText);
    }

    public void hover(WebElement webElement) {
        Actions action = new Actions(driver);
        action.moveToElement(webElement).build().perform();
    }

    public String clearCharacter(String dirtyString, String[] clearCharacters) {
        if (dirtyString == null)
            dirtyString = "";

        for (String character : clearCharacters) {
            dirtyString = dirtyString.replace(character, "");
        }
        return dirtyString.trim();
    }

    public void scrollElement(WebElement webElement){

        jsDriver.executeScript("arguments[0].scrollIntoView();", webElement);
    }


}


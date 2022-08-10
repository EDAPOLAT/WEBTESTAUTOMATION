package step;

import com.thoughtworks.gauge.Step;
import org.openqa.selenium.By;
import java.util.HashMap;


public class StepImplementation extends BaseSteps {

    private static HashMap<String, String> userVariableHashMap = new HashMap<>();

    @Step("<key> alanına rastgele mail adresi yaz")
    public void generateRandomMail(String key) {
        String randomMailAdres = generateEmail();
        sendKeys(key, randomMailAdres);
    }

    @Step("<key> elementine tıkla")
    public void clickElement(String key) {

        clickElement(getBy(key));
    }

    @Step("Sayfa üzerinde <key> objesi görüntülenene kadar bekle")
    public void waitUntilVisible(String key) {
        By locator =getBy(key);
       waitUntilVisible(locator);
        logger.info("'{}' objesi sayfa üzerinde görüntülenene kadar beklendi.", key);
    }

    @Step("<key> select objesinde <text> değerini seç")
    public void selectElementByText(String key, String text) {

        selectByVisibleText(getBy(key), text);
    }

    @Step("<key> elementine <text> textini yaz")
    public void ssendKeys(String key,String text) {
        if (!key.equals("")) {
            findElement(key).sendKeys(text);
            logger.info(key + " elementine " + text + " texti yazildi.");
        }
    }

    @Step("<seconds> saniye bekle")
    public void waitBySeconds(long seconds) {

       waitBySeconds(seconds);
    }

    @Step("<key> elementinin değeri <expectedValue> değerini içeriyor mu kontrol et")
    public void implementation4(String key, String expectedValue) {
        By locator = getBy(key);
        String actualValue = getText(locator);
        mustBeContains(actualValue, expectedValue);
    }

    @Step("<key> elementine odaklan")
    public void hover(String key) {
        hover(findElement(key));
        logger.info("'{}' objesine odaklanıldı.", key);
    }

    @Step("<key> elementi görüntülünene kadar kaydır")
    public void scrollElement(String key){
        scrollElement(findElement(key));

    }

    @Step("<key> objesinin değeri <savedUserVariable> değişken değerini içeriyor mu kontrol et")
    public void implementation5(String key, String savedUserVariable) {
        By locator = getBy(key);
        String actualValue = getText(locator);
        String expectedValue = userVariableHashMap.get(savedUserVariable);
        actualValue = clearCharacter(actualValue, new String[]{".", "/", ",", " "});
        expectedValue = clearCharacter(expectedValue, new String[]{".", "/", ",", " "});
        mustBeContains(actualValue, expectedValue);
    }

    @Step("<key> objesinin değerini <variableName> değişkenine kaydet")
    public void saveObjectValueToUserVariable(String key, String variableName) {
        By locator = getBy(key);
        String elementText = getText(locator);
        if (!"".equals(elementText)) {
            elementText = elementText.trim();
        }
        userVariableHashMap.put(variableName, elementText);
        logger.info("'{}' değeri '{}' değişkenine kaydedildi.", elementText, variableName);
    }



}

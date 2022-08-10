package base;


import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import io.restassured.RestAssured;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;


public class BaseTest {

  protected static WebDriver driver;

  protected static WebDriverWait webDriverWait;

  private static Logger logger = LoggerFactory.getLogger(BaseTest.class);

  public static String browserName="chrome";

  DesiredCapabilities capabilities = new DesiredCapabilities();



  @BeforeScenario

  public void setUp() throws Exception {

    String baseUrl = "http://automationpractice.com/";
    String selectPlatform = "win";
    String selectBrowser = "chrome";



    if (StringUtils.isEmpty(System.getenv("key"))){
      if ("win".equalsIgnoreCase(selectPlatform)){
        if ("chrome".equalsIgnoreCase(selectBrowser)){
          ChromeOptions options = new ChromeOptions();
          capabilities = DesiredCapabilities.chrome();
          DesiredCapabilities capabilities = DesiredCapabilities.chrome();
          LoggingPreferences loggingPreferences = new LoggingPreferences();
          loggingPreferences.enable(LogType.BROWSER, Level.ALL);
          capabilities.setCapability(CapabilityType.LOGGING_PREFS, loggingPreferences);
          options.merge(capabilities);
          Map<String, Object> prefs = new HashMap<String, Object>();
          prefs.put("profile.default_content_setting_values.notifications", 2);
          options.setExperimentalOption("prefs", prefs);
          options.addArguments("--kiosk");
          options.addArguments("--disable-notifications");
          options.addArguments("--disable-popup-blocking");
          options.addArguments("--start-fullscreen");
          System.setProperty("webdriver.chrome.driver","webdriver/chromedriver.exe");
          driver = new ChromeDriver(options);
          driver.manage().timeouts().pageLoadTimeout(60,TimeUnit.SECONDS);


        }else if ("firefox".equalsIgnoreCase(selectBrowser)){
          FirefoxOptions options = new FirefoxOptions();
          capabilities = DesiredCapabilities.firefox();
          Map<String, Object> prefs = new HashMap<String, Object>();
          prefs.put("profile.default_content_setting_values.notifications", 2);
          options.addArguments("--kiosk");
          options.addArguments("--disable-notifications");
          options.addArguments("--start-fullscreen");
          FirefoxProfile profile = new FirefoxProfile();
          capabilities.setCapability(FirefoxDriver.PROFILE,profile);
          capabilities.setCapability("marionette",true);
          System.setProperty("webdriver.gecko.driver","webdriver/geckodriver.exe");
          driver = new FirefoxDriver(options);
          driver.manage().timeouts().pageLoadTimeout(60,TimeUnit.SECONDS);
        }
      }else if ("MicrosoftEdge".equalsIgnoreCase(selectBrowser)){
        EdgeDriver options=new EdgeDriver();
        capabilities = DesiredCapabilities.edge();
        DesiredCapabilities capabilities = DesiredCapabilities.edge();
        Map<String, Object> prefs = new HashMap<String, Object>();
        capabilities.setPlatform(DesiredCapabilities.edge().getPlatform());
        capabilities.setBrowserName(DesiredCapabilities.edge().getBrowserName());
        prefs.put("profile.default_content_setting_values.notifications", 2);
        System.setProperty("webdriver.edge.driver","webdriver/EdgeDriver.exe");
        driver.manage().timeouts().pageLoadTimeout(60,TimeUnit.SECONDS);
      }


      }


    driver.manage().timeouts().pageLoadTimeout(60,TimeUnit.SECONDS);

    driver.manage().window().maximize();

    driver.get(baseUrl);

  }
  @AfterScenario

  public void tearDown() {
    driver.quit();

  }

}
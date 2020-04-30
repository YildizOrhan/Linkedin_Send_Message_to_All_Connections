package Base;

import Enums.BrowserType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import java.net.MalformedURLException;

public class DriverManager {
    public DriverManager() {
    }

    public static WebDriver driver;

    private static String browserType;

    public static void setBrowserType(String browser) {

        browserType = browser;
    }

    public static WebDriver getDriver() throws MalformedURLException {
        BrowserType browser = BrowserType.valueOf(browserType);
        if (driver == null) {
            switch (browser) {
                case CHROME:

                    ChromeOptions options = new ChromeOptions();
                    WebDriverManager.chromedriver().setup();
                    options.addArguments("--disable-notifications");
                  //  options.addArguments("--headless");
                    driver = new ChromeDriver(options);
                    break;
                case IE:
                    InternetExplorerOptions optionsIE = new InternetExplorerOptions();
                    optionsIE.setCapability("browserName", "internet explorer");
                    optionsIE.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                    optionsIE.setCapability("IntroduceInstabilityByIgnoringProtectedModeSettings", true);
                    optionsIE.setCapability("requireWindowFocus", true);
                    WebDriverManager.iedriver().setup();
                    driver = new InternetExplorerDriver(optionsIE);
                    break;
                case FIREFOX:
                    FirefoxOptions optionsFirefox = new FirefoxOptions();
                    optionsFirefox.addArguments("--start-maximized");
                    optionsFirefox.addArguments("disable-infobars");
                    optionsFirefox.addArguments("--disable-notifications");
                    optionsFirefox.addArguments("--disable-popup-blocking");
                    optionsFirefox.addArguments("--disable-extensions");
                    optionsFirefox.addPreference("intl.accept_languages", "en-au");
                    optionsFirefox.setAcceptInsecureCerts(true);
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver(optionsFirefox);
                    break;
                default:
                    //...
                    break;
            }
        }
        return driver;
    }

}

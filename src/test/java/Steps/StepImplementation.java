package Steps;

import Base.DriverManager;
import Helpers.ClickHelper;
import Helpers.KeyboardHelper;
import Helpers.NavigationHelper;
import Helpers.TextHelper;
import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.thoughtworks.gauge.*;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;


public class StepImplementation {

    @BeforeSuite
    public void setup() throws MalformedURLException, InterruptedException {
        if (DriverManager.driver == null) {
            DriverManager.setBrowserType("CHROME");
            DriverManager.getDriver().manage().window().maximize();
            DriverManager.getDriver().manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
            DriverManager.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }
    }

    @AfterSuite
    public void closeDriver() {
        //    DriverManager.getDriver().close();
        //    DriverManager.getDriver().quit();
        //    DriverManager.driver = null;
    }

    private static String locatorFilePath = "src\\test\\resources\\pageObject.json";

    public static By createBy(String elementName) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(elementName), "Element elementName is missing.");

        File file = new File(locatorFilePath);
        Preconditions.checkArgument(file.exists(), "Unable to locate " + locatorFilePath);
        try {
            JsonArray array = new JsonParser().parse(new FileReader(file)).getAsJsonArray();
            Iterator<JsonElement> iterator = array.iterator();
            JsonObject foundObject = null;
            while (iterator.hasNext()) {
                JsonObject object = iterator.next().getAsJsonObject();
                if (
                        elementName.equalsIgnoreCase(object.get("elementName").getAsString())) {
                    foundObject = object;
                    break;
                }
            }

            String locateUsing = foundObject.get("locateUsing").getAsString();
            String locator = foundObject.get("locator").getAsString();
            Preconditions.checkArgument(StringUtils.isNotEmpty(locator), "Locator cannot be null (or) empty.");

            if (("xpath".equalsIgnoreCase(locateUsing))) {
                return new By.ByXPath(locator);
            }
            if (("id".equalsIgnoreCase(locateUsing))) {
                return new By.ById(locator);
            }
            if (("elementName".equalsIgnoreCase(locateUsing))) {
                return new By.ByName(locator);
            }
            if (("className".equalsIgnoreCase(locateUsing))) {
                return new By.ByClassName(locator);
            }
            if (("css".equalsIgnoreCase(locateUsing))) {
                return new By.ByCssSelector(locator);
            }
            if (("tagName".equalsIgnoreCase(locateUsing))) {
                return new By.ByTagName(locator);
            }
            if (("linkText".equalsIgnoreCase(locateUsing))) {
                return new By.ByLinkText(locator);
            }
            if (("partialLinkText".equalsIgnoreCase(locateUsing))) {
                return new By.ByPartialLinkText(locator);
            }
            throw new UnsupportedOperationException("Currently " + locateUsing + " is NOT supported.");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Step("Navigate to <URL>")
    public void GoToURL(String URL) throws Exception {
        NavigationHelper.navigateTo(URL);
    }

    @Step("Click <Element>")
    public void clickElement(String element) throws MalformedURLException {
        ClickHelper.click(createBy(element));
    }

    @Step("I fill in <Email or Phone> field with <yildizmehtaporhan@gmail.com>")
    public void fillField(String element, String text) throws MalformedURLException {
        TextHelper.enterText(createBy(element), text);
    }


    @Step("Send <Message> message to all connection")
    public void sendMessage(String message) throws Exception {
        int connectionCount = Integer.parseInt(getAllConnection());
        System.out.println(connectionCount);
        for (int i = 2; i <= connectionCount; i=i+2) {
            ClickHelper.click(By.xpath("//*[@class='mn-connections__header']/following::button[" + i + "]"));
            TextHelper.enterText(By.xpath("//*[@aria-label='Write a message…']"),message);
            Thread.sleep(100);
            //Mesajı göndermek için yorum satırını kaldırın :)
            //ClickHelper.click(By.cssSelector(".msg-form__send-button.artdeco-button.artdeco-button--1"));
            KeyboardHelper.keybordESC();
        }
    }
    public static String getAllConnection() throws Exception {
        String conHeader = TextHelper.getText(By.cssSelector(".mn-connections__header"));
        String countConnection = conHeader.replaceAll("\\D+", "");
        return countConnection;
    }
}

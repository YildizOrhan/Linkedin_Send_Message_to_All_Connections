package Helpers;

import Base.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;

public class TextHelper {


    public static void enterText(By locator, String input) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), 120);
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
            DriverManager.getDriver().findElement(locator).sendKeys(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getText(By locator) throws MalformedURLException {

        return DriverManager.getDriver().findElement(locator).getText();
    }

}




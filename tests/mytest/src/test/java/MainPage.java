import org.junit.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



class MainPage extends PageBase {

      private By footerBy = By.xpath("/html/body/footer/div/div[2]/div/div/div[1]");

     private By accessLoginBy = By.xpath("/html/body/header/nav/div/div[2]/a");

     private By signUpLocator = By.xpath("(//a[@href='/get-started/'])[2]");
 
    
    public MainPage(WebDriver driver ) {
        super(driver);
        this.driver.get("https://www.tutorialspoint.com/");
        String title = driver.getTitle();
        System.out.println("Page title: " + title);
    }    

    public Login_Page accessLogin() {
        this.waitAndReturnElement(accessLoginBy).click();
        return new Login_Page(this.driver);
    }

    public String getFooterText() {
        return this.waitAndReturnElement(footerBy).getText();
    }

}
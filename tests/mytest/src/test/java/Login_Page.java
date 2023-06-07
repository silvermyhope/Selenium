import org.junit.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

public class Login_Page extends PageBase {

    private By loginBy = By.xpath("//*[@id='navbarCollapse']/div[2]/div/a");
    private By usernameFieldLocator = By.xpath("//*[@id='user_email']");
    private By passwordFieldLocator = By.xpath("//*[@id='user_password']");
    private By clickLocator = By.xpath("//*[@id='remember_me']");
    private By loginButtonLocator = By.xpath("//*[@id='user_login' and not(@disabled)]");

    private By createAccountLocator = By.xpath("//a[@href='signup.jsp']");


    public Login_Page(WebDriver driver) {
        super(driver);
    }

    public Dashboard_Page login() {
        return new Dashboard_Page(this.driver);
    }

    public Dashboard_Page logIn(String username, String password) {
        this.waitAndReturnElement(usernameFieldLocator).sendKeys(username);
        this.waitAndReturnElement(passwordFieldLocator).sendKeys(password);
        this.waitAndReturnElement(clickLocator).click();
        this.waitAndReturnElement(loginButtonLocator).click();

        try {
            Thread.sleep(10000); // Wait for 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String dashboardPageUrl = driver.getCurrentUrl();
        System.out.println(dashboardPageUrl);
        return new Dashboard_Page(this.driver);
        
    }

    public Registration_Page createAccount() {
        this.waitAndReturnElement(createAccountLocator).click();
        return new Registration_Page(this.driver);
    }
}

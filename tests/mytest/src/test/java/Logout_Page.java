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

public class Logout_Page extends PageBase {
    public Logout_Page(WebDriver driver) {
        super(driver);
        this.driver.get("https://www.tutorialspoint.com/market/student/dashboard.jsp?v=1685875169");
    }

    public Dashboard_Page navigateBackToDashboardPage() {
        driver.navigate().back();
        return new Dashboard_Page(driver);
    }
}

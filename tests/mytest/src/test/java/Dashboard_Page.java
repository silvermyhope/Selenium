import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;


public class Dashboard_Page extends PageBase {
    private By downArrowLocator = By.xpath("//*[@id='profileMenu']");
    private By profileLocator = By.xpath("//div//ul//li//a[contains(@class, 'profile-progress')]");
    //private By logoutLocator = By.xpath("//*[@id='profile-menu']/li[6]/b/a");
    private By logoutLocator = By.xpath("//li[@class='logout']/a");
    private By dashboardBodyLocator = By.xpath("//body");

    public Dashboard_Page(WebDriver driver) {
        super(driver);
        this.driver.get("https://www.tutorialspoint.com/market/student/dashboard.jsp");
        System.out.println(driver.getCurrentUrl());
    }

    public String getDashboardBodyText() {
        WebDriverWait wait = new WebDriverWait(driver, 60);
        return this.waitAndReturnElement(dashboardBodyLocator).getText();
    }

    public Logout_Page logout() {
        this.waitAndReturnElement(downArrowLocator).click();
        WebElement logoutElement = this.waitAndReturnElement(logoutLocator);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", logoutElement);
        return new Logout_Page(this.driver);
    }
}

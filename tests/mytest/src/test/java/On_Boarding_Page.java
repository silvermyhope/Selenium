import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class On_Boarding_Page extends PageBase{

    //private By prepareRadioLocator = By.xpath("(//main[@class='onboarding-survey-main'])//span[contains(text(),'Prepare for Job Interviews')]");
    //private By skipLocator = By.xpath("//div[@class='ui-content has-icon align-icon-right']");
    //private By closeButtonLocator = By.xpath("//button[@data-balloon='Close']");

    public On_Boarding_Page(WebDriver driver) {
        super(driver);
    }

    public Dashboard_Page onboard() {
        return new Dashboard_Page(this.driver);
    }
}

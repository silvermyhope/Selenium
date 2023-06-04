import org.openqa.selenium.WebDriver;

public class Static_Page_Load extends PageBase{
    private String url;

    public Static_Page_Load(WebDriver driver, String url) {
        super(driver);
        this.url = url;
    }

    public void navigateToPage() {
        driver.get(url);
    }
}

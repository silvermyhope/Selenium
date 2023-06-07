import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.net.URL;
import java.net.MalformedURLException;

import org.openqa.selenium.support.ui.*;

import org.junit.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class SeleniumTest {
	
	private WebDriver driver;
    private Properties properties;
    private String[] staticPages;

    @Before
    public void setup() throws MalformedURLException {
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver_win32/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();

        this.driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        this.driver.manage().window().maximize();

        properties = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    /*  */ 
    public Dashboard_Page login() {
        MainPage mainPageResult = new MainPage(this.driver);
        Login_Page loginPage = mainPageResult.accessLogin();
        String email = properties.getProperty("user.email");
        String password = properties.getProperty("user.password");
        return loginPage.logIn(email, password);
    }

    @Test
    public void testStaticPageLoad() {
        MainPage mainPageResult = new MainPage(this.driver);
        String staticPagesString = properties.getProperty("static.pages");
        staticPages = staticPagesString.split(",");
        String staticPageTitle = properties.getProperty("static.page.title");

        for (String url : staticPages) {
            Static_Page_Load staticPage = new Static_Page_Load(this.driver, url);
            staticPage.navigateToPage();
        }
    }


    @Test
    public void testLogin() {
        Dashboard_Page dashboardPage = login();
        String dashboardBodyTextContains = properties.getProperty("dashboard.body.text2.contains");
        String dashboardPageText = dashboardPage.getDashboardBodyText();
        assertTrue(dashboardPageText.contains(dashboardBodyTextContains));
        
    }

    @Test
    public void testInvalidLogin() {
        MainPage mainPageResult = new MainPage(this.driver);
        Login_Page loginPage = mainPageResult.accessLogin();
        String email = "fake.email@gmail.com";
        String password = "try.password";
        Dashboard_Page dashboardPage = loginPage.logIn(email, password);
        String dashboardBodyTextContains = properties.getProperty("access.account.body.text.contains");        
        assertTrue(dashboardPage.getBodyText().contains(dashboardBodyTextContains));
    }

    // Registration form
    @Test
    public void testRegistrationPage() {
        MainPage mainPageResult = new MainPage(this.driver);
        Login_Page loginPage = mainPageResult.accessLogin();
        Registration_Page registrationPage = loginPage.createAccount();
        String fullName = properties.getProperty("user.registration.fullName");
        String email = properties.getProperty("user.registration.email");
        String password = properties.getProperty("user.registration.password");
        String phonenumber = properties.getProperty("user.registration.phonenumber");
        String countryCode = properties.getProperty("user.registration.countryCode");
        On_Boarding_Page accountForRegistration = registrationPage.createAccountForRegistration(fullName, email, password, phonenumber, countryCode);
        Dashboard_Page dashboardPage = accountForRegistration.onboard();
        //assertTrue(dashboardPage.getBodyText().contains("Welcome "+name.split(" ")[0]+"!"));
    }

    @Test
    public void testLogout() {
        // Logout
        Dashboard_Page dashboardPage = login();
        Logout_Page logoutPage = dashboardPage.logout();
        //String logoutBodyTextContains = properties.getProperty("logout.body.text.contains");
        //assertTrue(logoutPage.getBodyText().contains(logoutBodyTextContains));

        // History test (browser back button)
        logoutPage.navigateBackToDashboardPage();
        // String dashboardPageTitle = properties.getProperty("dashboard.page.title");
        // assertEquals(dashboardPageTitle, this.driver.getTitle());
    }

    @After
    public void close() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }
}
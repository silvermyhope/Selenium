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

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public class SeleniumTest {
	
	private WebDriver driver;
    private Properties properties;
    private String[] staticPages;

    @Before
    public void setup() throws MalformedURLException {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--incognito");

        //driver = new ChromeDriver(options);  
        this.driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        this.driver.manage().window().maximize();


        properties = new Properties();
        try (InputStream input = new FileInputStream("config.properties");
         InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    /*  */ 
    // public Dashboard_Page login() {
    //     MainPage mainPageResult = new MainPage(this.driver);
    //     Login_Page loginPage = mainPageResult.accessLogin();
    //     String email = properties.getProperty("user.email");
    //     String password = properties.getProperty("user.password");
    //     Dashboard_Page dashboard = loginPage.logIn(email, password);
    //     return dashboard;
    // }

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
    public void testInvalidLogin() {
        MainPage mainPageResult = new MainPage(this.driver);
        Login_Page loginPage = mainPageResult.accessLogin();
        String email = "fake.email@gmail.com";
        String password = "try.password";
        Dashboard_Page dashboardPage = loginPage.logIn(email, password);
        String dashboardBodyTextContains = properties.getProperty("access.account.body.text.contains");        
        assertTrue(dashboardPage.getBodyText().contains(dashboardBodyTextContains));
    }

    @Test
    public void testLogin() throws Exception {
        MainPage mainPageResult = new MainPage(this.driver);
        Login_Page loginPage = mainPageResult.accessLogin();
        String email = properties.getProperty("user.email");
        String password = properties.getProperty("user.password");
        Dashboard_Page dashboardPage = loginPage.logIn(email, password);
        System.out.println(dashboardPage.getBodyText());

        driver.get("https://www.tutorialspoint.com/market/student/dashboard.jsp");

        String dashboardBodyTextContains = properties.getProperty("dashboard.body.text.contains");  
        System.out.println("dash Text: " + dashboardBodyTextContains);

        //assertTrue(dashboardPage.getBodyText().contains(dashboardBodyTextContains));
    }

    // // Registration form
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

    // @Test
    // public void testLogout() {
    //     // Logout
    //     Dashboard_Page dashboardPage = login();
    //     Logout_Page logoutPage = dashboardPage.logout();
    //     //String logoutBodyTextContains = properties.getProperty("logout.body.text.contains");
    //     //assertTrue(logoutPage.getBodyText().contains(logoutBodyTextContains));

    //     // History test (browser back button)
    //     logoutPage.navigateBackToDashboardPage();
    //     // String dashboardPageTitle = properties.getProperty("dashboard.page.title");
    //     // assertEquals(dashboardPageTitle, this.driver.getTitle());
    // }
    
    @Test
    public void testMainFooterText() {
        MainPage mainPageResult = new MainPage(this.driver);
        WebElement footerElement = driver.findElement(By.xpath("/html/body/footer/div/div[2]/div/div/div[1]")); // Modify the locator according to your HTML structure
        String footerText = footerElement.getText();
        String footerTextContains = properties.getProperty("footer.text.contains");
        System.out.println("Footer Text: " + footerText);
        System.out.println("conf Text: " + footerTextContains);
        Assert.assertEquals(footerText, footerTextContains);
    }

    @After
    public void close() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }
}
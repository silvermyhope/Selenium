import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import javax.mail.search.SubjectTerm;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration_Page extends PageBase {

    private By nameLocator = By.xpath("//*[@id='textRegName']");
    private By countryCodeLocator = By.xpath("//*[@id='country_code']");
    private By phonenumberLocator = By.xpath("//*[@id='phone']");
    private By numVerificationButtonLocator = By.xpath("//*[@id='validate_mobile_number']");
    private By verifiedButtonLocator = By.xpath("//*[@id='verified_mobile']");

    private By emailLocator = By.xpath("//*[@id='textSRegEmail']");
    private By verifyEmailButtonLocator = By.xpath("//*[@id='validate_email_id']");
    private By emailVerifiedLocator = By.xpath("//*[@id='verified_email']");

    private By otpLocator = By.xpath("//*[@id='txtValidateMobileOTP']");
    private By verifyOTBButtonLocator = By.xpath("//*[@id='validateMobileOtp']");
    private By passwordLocator = By.xpath("//*[@id='user_password']");
    private By createAccountLocator = By.xpath("//*[@id='signUpNew']");

    public Registration_Page(WebDriver driver) {
        super(driver);
    }

    public On_Boarding_Page createAccountForRegistration(String fullName, String email, String password, String phonenumber, String countryCode) {
        this.waitAndReturnElement(nameLocator).sendKeys(fullName);
        this.waitAndReturnElement(emailLocator).sendKeys(email);
        this.waitAndReturnElement(passwordLocator).sendKeys(password);
        this.waitAndReturnElement(phonenumberLocator).sendKeys(phonenumber);

        // Select the country code option
        Select countryCodeSelect = new Select(this.waitAndReturnElement(countryCodeLocator));
        countryCodeSelect.selectByValue(countryCode);

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(verifyEmailButtonLocator)).click();

        // Handle email verification using OTP
        String otp = retrieveOTPFromEmail(email);  // Retrieve OTP from email
        this.waitAndReturnElement(otpLocator).sendKeys(otp);
        this.waitAndReturnElement(verifyOTBButtonLocator).click();

        try {
            Thread.sleep(5000); // Delay for 5 seconds (5000 milliseconds)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Click createAccountLocator
        this.waitAndReturnElement(createAccountLocator).click();

        return new On_Boarding_Page(this.driver);
    }

    private String retrieveOTPFromEmail(String email) {
        String otp = null;
        String host = "imap.gmail.com";
        String username = "ritakonjo1618@gmail.com";
        String password = "Silver@16";
        String folderName = "inbox";

        try {
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "imaps");
            properties.put("mail.imap.host", host);
            properties.put("mail.imap.port", "993");

            Session session = Session.getInstance(properties, null);
            Store store = session.getStore("imaps");
            store.connect(host, username, password);

            Folder folder = store.getFolder(folderName);
            folder.open(Folder.READ_WRITE);

            Message[] messages = folder.search(new SubjectTerm("Your OTP"));
            if (messages.length > 0) {
                Message message = messages[messages.length - 1];
                Multipart multipart = (Multipart) message.getContent();
                BodyPart bodyPart = multipart.getBodyPart(0);
                String text = (String) bodyPart.getContent();
                otp = extractOTPFromEmailText(text);
            }

            folder.close(true);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return otp;
    }


    private String extractOTPFromEmailText(String emailText) {
        // Extract OTP from email text using regular expressions or string manipulation
        // Modify this method based on the actual email content format
        // Search for the OTP pattern in the email text
        Pattern otpPattern = Pattern.compile("One Time Password \\(OTP\\)\\s*Dear Guest,\\s*Please use the following One Time Password \\(OTP\\) to Signup with Tutorialspoint.\\s*(\\w+)");
        Matcher matcher = otpPattern.matcher(emailText);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
        }
}
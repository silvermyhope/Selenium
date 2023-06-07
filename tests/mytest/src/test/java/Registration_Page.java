import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.util.Properties;

public class Registration_Page extends PageBase{

    //private By nameLocator = By.xpath("//input[@aria-label='First & Last name']");
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
        WebElement countryCodeElement = this.waitAndReturnElement(countryCodeLocator);
        Select countryCodeSelect = new Select(countryCodeElement);
        countryCodeSelect.selectByValue(countryCode);


        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(verifyEmailButtonLocator)).click();

        // Handle email verification using OTP
        String otp = retrieveOTPFromEmail();  // Implement the logic to retrieve OTP from email
        this.waitAndReturnElement(otpLocator).sendKeys(otp);
        this.waitAndReturnElement(verifyOTBButtonLocator).click();

        // Go back to the registration page to confirm verification
        driver.navigate().back();

        return new On_Boarding_Page(this.driver);
    }


    private String retrieveOTPFromEmail() {
        String host = "imap.gmail.com";
        String username = "ritakonjo1618@gmail.com"; // Replace with your Gmail address
        String password = "Silver@16"; // Replace with your Gmail password
    
        try {
            Properties properties = new Properties();
            properties.setProperty("mail.store.protocol", "imaps");
            Session session = Session.getInstance(properties);
    
            // Connect to the Gmail IMAP server
            Store store = session.getStore("imaps");
            store.connect(host, username, password);
    
            // Open the inbox folder
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);
    
            // Search for the verification email using the subject or other criteria
            Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
            for (Message message : messages) {
                String subject = message.getSubject();
                if (subject != null && subject.equals("Signup One Time Password (OTP)")) {
                    // Extract the OTP from the email content
                    String otp = extractOTPFromEmailContent(message);
                    // Mark the email as read
                    message.setFlag(Flags.Flag.SEEN, true);
                    // Close the inbox folder and disconnect from the server
                    inbox.close(true);
                    store.close();
                    // Return the retrieved OTP
                    return otp;
                }
            }
    
            // If no verification email found or OTP not extracted, handle accordingly
            // Close the inbox folder and disconnect from the server
            inbox.close(true);
            store.close();
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        // Return null or handle error case
        return null;
    }

    private String extractOTPFromEmailContent(Message message) {
        String emailContent = "";
        try {
            Object content = message.getContent();
            if (content instanceof String) {
                emailContent = (String) content;
            } else if (content instanceof Multipart) {
                Multipart multipart = (Multipart) content;
                for (int i = 0; i < multipart.getCount(); i++) {
                    BodyPart bodyPart = multipart.getBodyPart(i);
                    if (bodyPart.isMimeType("text/plain")) {
                        emailContent = (String) bodyPart.getContent();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Extract the OTP using custom logic based on the email content
        // Replace the pattern with the actual pattern you expect in the email
        String otp = emailContent.replaceAll("[^\\d]", "");

        return otp;
    }

}

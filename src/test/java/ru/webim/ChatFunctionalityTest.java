package ru.webim;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.*;
import ru.webim.page.objects.LoginPage;
import ru.webim.page.objects.MainPage;
import ru.webim.page.objects.OperatorPage;

import java.sql.DriverPropertyInfo;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static ru.webim.Constants.*;

public class ChatFunctionalityTest {

    public WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private OperatorPage operatorPage;


    @BeforeSuite
    public void setUp() {
        System.setProperty(WEB_DRIVER_CHROME, WEB_DRIVER_PATH_CHROME);
    }

    @BeforeTest
    public void initializeWebDriver() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(25, TimeUnit.SECONDS);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        operatorPage = PageFactory.initElements(driver, OperatorPage.class);
    }

    @BeforeMethod
    public void openWebChat() {
        mainPage.openWebsiteMainPage();

        //Wait 3 seconds, till webim webchat button will be shown
        mainPage.waitWebchatButton();

        mainPage.openChat();
    }

    @Test(priority=1)
    public void openingClosingChatTest() {

        assertTrue(mainPage.chatWindow.isDisplayed());

        mainPage.closeChat();

        assertFalse(mainPage.chatWindow.isDisplayed());
    }

    @Test(priority=2)
    public void sendTextTest() {

        mainPage.sendText();

        assertTrue(mainPage.textIsDisplayedInChat(SEND_TEXT));
    }

    @Test(priority=3)
    public void settingOperatorRatingTest() throws Exception{

        mainPage.sendText();

        String mainWindowHandle = driver.getWindowHandle();

        loginPage.loginAsOperator();
        operatorPage.answerToClient();

        driver.switchTo().window(mainWindowHandle);
        mainPage.setOperatorRating();

        assertTrue(mainPage.ratingWasGiven());
    }

    @Test(priority=4)
    public void sendFileTest() {

        mainPage.sendFile();

        assertTrue(mainPage.fileWasSent());
    }

    @AfterMethod
    public void closeWebChat() {
        if (mainPage.chatWindow.isDisplayed()) {
            mainPage.closeChat();
        }
    }

    @AfterTest
    public void close() {
        driver.close();
    }

    @AfterSuite
    public void tearDown() {
        if (driver.toString().contains("null")) {
            driver.quit();
        }
    }
}

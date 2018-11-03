package ru.webim;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.*;
import ru.webim.page.objects.LoginPage;
import ru.webim.page.objects.MainPage;
import ru.webim.page.objects.OperatorPage;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static ru.webim.Constants.WEB_DRIVER;
import static ru.webim.Constants.WEB_DRIVER_PATH;

public class ChatFunctionalityTest {

    public WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private OperatorPage operatorPage;


    @BeforeSuite
    public void setUp() {
        System.setProperty(WEB_DRIVER, WEB_DRIVER_PATH);
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
        //1. Open test site by URL	https://demo-pro.webim.ru/
        mainPage.openWebsiteMainPage();

        //2. Wait 3 seconds, till webim webchat button will be shown
        mainPage.waitWebchatButton();

        //3. Click on the webchat button
        mainPage.openChat();
    }

    @Test
    public void openingClosingChatTest() {

        //4. Assert that chat in shown
        assertTrue(mainPage.chatWindow.isDisplayed());

        //5. Close Chat
        mainPage.closeChat();

        //6. Assert chat is closed
        assertFalse(mainPage.chatWindow.isDisplayed());
    }

    @Test
    public void sendTextTest() {

        mainPage.sendText();

        //10. Assert that text is shown in chat
        assertTrue(mainPage.textIsDisplayedInChat());
    }

    @Test
    public void sendFileTest() {

        mainPage.sendFile();

        assertTrue(mainPage.fileWasSent());
    }

    @Test
    public void settingOperatorRatingTest() throws Exception{

        mainPage.sendText();

        String mainWindowHandle = driver.getWindowHandle();

        loginPage.loginAsOperator();
        operatorPage.answerToClient();

        driver.switchTo().window(mainWindowHandle);
        mainPage.setOperatorRating();

        assertTrue(mainPage.ratingWasGiven());
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

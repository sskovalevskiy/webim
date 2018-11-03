package ru.webim.page.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.webim.NoOperatorException;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static ru.webim.Constants.PATH_TO_FILE;
import static ru.webim.Constants.SEND_TEXT;
import static ru.webim.Constants.SITE_URL;

public class MainPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 3);
    }

    @FindBy(css = "a.webim_button.webim-button-corner")
    private WebElement chatOpeningButton;

    @FindBy(id = "webim_chat")
    public WebElement chatWindow;

    @FindBy(css = ".webim-action-close")
    private WebElement closingChatButton;

    @FindBy(css = ".webim-message-area.webim-textarea-ext")
    private WebElement textArea;

    @FindBy(css = ".webim-icon.webim-icon-send")
    private WebElement sendTextButton;

    @FindBy(css = ".webim-dialogue")
    private List<WebElement> dialoguesInChat;

    @FindBy(css = "button.webim-action span.webim-icon-actions")
    private WebElement threeDotsButton;

    @FindBy(css = "li.webim-chat-action-upload")
    private WebElement sendFileLink;

    @FindBy(css = ".webim-chat-action.webim-chat-action-rate .webim-icon-rate")
    private WebElement setRateLink;

    @FindBy(css = ".webim-form-control.webim-rate-error.webim-notice.webim-notice-error.webim-on-error")
    private WebElement noOperatorAlert;

    @FindBy(css = ".webim-form-control.webim-operator-rate > li")
    private WebElement operatorRatingFirstStar;

    @FindBy(css = "button.webim-btn.webim-btn-send.webim-js-button-style")
    private WebElement setRaitingButton;

    @FindBy(css = ".webim-icon.webim-icon-check")
    private WebElement successMarker;

    public void openWebsiteMainPage() {
        driver.get(SITE_URL);
    }

    public void waitWebchatButton() {
        wait.until(ExpectedConditions.visibilityOf(chatOpeningButton));
    }

    public void openChat() {
        chatOpeningButton.click();
    }

    public void closeChat() {
        closingChatButton.click();
    }

    public void sendText() {
        textArea.sendKeys(SEND_TEXT);
        sendTextButton.click();
    }

    public boolean textIsDisplayedInChat() {
        LinkedList<WebElement> webimDialogues = new LinkedList<>(dialoguesInChat);

        ArrayList<String> messages = new ArrayList<>();
        webimDialogues.getLast().findElements(By.cssSelector("div.webim-message-body")).forEach(e -> messages.add(e.getText()));

        return messages.contains(SEND_TEXT);
    }

    public void sendFile(){

        clickChatActionsBlockLink(sendFileLink);

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("document.getElementsByClassName('webim-fileupload')[1].style = ''");

        driver.findElement(By.cssSelector("input.webim-fileupload")).sendKeys("/home/ssk/NEW_FILE.txt");

//        setClipboard("/home/ssk/NEW_FILE.txt");

//        sendFileLink.click();

//        Robot robot = null;
//        try {
//            robot = new Robot();
//        } catch (AWTException e) {
//            e.printStackTrace();
//        }
//        // Ctrl-V + Enter on Win
//        robot.delay(3000);
//        robot.keyPress(KeyEvent.VK_CONTROL);
//        robot.keyPress(KeyEvent.VK_V);
//        robot.keyRelease(KeyEvent.VK_V);
//        robot.keyRelease(KeyEvent.VK_CONTROL);
//        robot.keyPress(KeyEvent.VK_ENTER);

    }

    public boolean fileWasSent() {
        return false;
    }

    public void setOperatorRating() throws Exception {
        clickChatActionsBlockLink(setRateLink);

        if (noOperatorAlert.isDisplayed()) {
            throw new NoOperatorException();
        }

        operatorRatingFirstStar.click();

        setRaitingButton.click();
        wait.until(ExpectedConditions.invisibilityOf(setRaitingButton));
    }

    public boolean ratingWasGiven() {

        clickChatActionsBlockLink(setRateLink);
        return operatorRatingFirstStar.getAttribute("class").contains("webim-selected-rate");
    }

    private void clickChatActionsBlockLink(WebElement clickableElement) {

        threeDotsButton.click();
        wait.until(ExpectedConditions.visibilityOf(clickableElement));
        clickableElement.click();
    }

    private static void setClipboard(String str) {
        StringSelection ss = new StringSelection(str);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
    }
}

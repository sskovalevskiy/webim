package ru.webim.page.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OperatorPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = ".bg-ico.webim-status.online")
    private WebElement operatorWorkingPlaceButton;

    @FindBy(name = "chat_message_textarea")
    private WebElement chatMessageTextarea;

    @FindBy(css = "ul#collapse_id_queue > li")
    private WebElement visitorsList;

    public OperatorPage(WebDriver webDriver) {
        this.driver = webDriver;
        wait = new WebDriverWait(driver, 3);
    }

    public void answerToClient() {

        wait.until(ExpectedConditions.visibilityOf(operatorWorkingPlaceButton));
        operatorWorkingPlaceButton.click();

        wait.until(ExpectedConditions.visibilityOf(visitorsList));
        visitorsList.click();

        wait.until(ExpectedConditions.visibilityOf(chatMessageTextarea));
        chatMessageTextarea.sendKeys("Hello from Selenium! Слава богам, заработало");
        chatMessageTextarea.sendKeys(Keys.ENTER);
    }
}

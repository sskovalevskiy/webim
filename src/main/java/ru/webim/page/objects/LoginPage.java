package ru.webim.page.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;

import static ru.webim.Constants.*;

public class LoginPage {

    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @FindBy(id = "login_or_email")
    private WebElement loginField;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(xpath = "//*[contains(text(), 'Войти')]")
    private WebElement enterButton;



    public void loginAsOperator(){

        ((JavascriptExecutor) driver).executeScript("window.open('','_blank');");
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());

        driver.switchTo().window(tabs.get(1));

        driver.navigate().to(OPERATOR_PAGE_URL);
        loginField.sendKeys(E_MAIL);
        password.sendKeys(PASSWORD);
        enterButton.click();
    }
}

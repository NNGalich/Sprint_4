package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPageScooter {
    private WebDriver driver;

    //Кнопка "да все привыкли" про куки
    private By acceptCookieButton = By.cssSelector(".App_CookieButton__3cvqF");

    //Верхняя кнопка для заказа
    private By upperOrderButton = By.cssSelector(".Header_Nav__AGCXC>.Button_Button__ra12g");

    //Нижняя кнопка для заказа
    private By lowerOrderButton = By.cssSelector(".Button_Button__ra12g.Button_Middle__1CSJM");

    //Все вопросы из раздела "Вопросы о важном"
    private By faqQuestions = By.cssSelector(".Home_FAQ__3uVm4>.accordion>.accordion__item>.accordion__heading>.accordion__button");

    //Все ответы из раздела "Вопросы о важном"
    private By faqAnswers = By.cssSelector(".Home_FAQ__3uVm4>.accordion>.accordion__item>.accordion__panel>p");

    public MainPageScooter(WebDriver driver){
        this.driver = driver;
    }

    public void clickAcceptCookie() {
        driver.findElement(acceptCookieButton).click();
    }

    private void clickUpperOrderButton() {
        driver.findElement(upperOrderButton).click();
    }

    private void scrollToUpperOrderButton() {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(upperOrderButton));
    }

    private void clickLowerOrderButton() {
        driver.findElement(lowerOrderButton).click();
    }

    private void scrollToLowerOrderButton() {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(upperOrderButton));
    }

    public void scrollToOrderButtonByName(String name) {
        if (name.equals("upper")) {
            scrollToUpperOrderButton();
        } else if (name.equals("lower")) {
            scrollToLowerOrderButton();
        }
    }

    public void clickOrderButtonByName(String name) {
        if (name.equals("upper")) {
            clickUpperOrderButton();
        } else if (name.equals("lower")) {
            clickLowerOrderButton();
        }
    }

    public void clickFaqQuestionByIndex(int index) {
        findFaqQuestionElementByIndex(index).click();
    }

    public String getFaqAnswerTextByIndex(int index) {
        return findFaqAnswerElementByIndex(index).getText();
    }

    private WebElement findFaqAnswerElementByIndex(int index) {
        return driver.findElements(faqAnswers).get(index);
    }

    // Метод ожидания появления текста ответа
    public void waitForVisibilityFaqAnswerByIndex(int index) {
        new WebDriverWait(driver, 1)
                .until(ExpectedConditions.visibilityOf(findFaqAnswerElementByIndex(index)));
    }

    private WebElement findFaqQuestionElementByIndex(int index) {
        return driver.findElements(faqQuestions).get(index);
    }

    // Метод прокрутки экрана до нужного элемента
    public void scrollToFaqQuestionByIndex(int index) {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", findFaqQuestionElementByIndex(index));
    }
}

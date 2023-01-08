package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPageScooter {
    private WebDriver driver;

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

    public void clickUpperOrderButton() {
        driver.findElement(upperOrderButton).click();
    }

    public void clickLowerOrderButton() {
        driver.findElement(lowerOrderButton).click();
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

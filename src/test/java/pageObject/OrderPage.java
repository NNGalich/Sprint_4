package pageObject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrderPage {

    private WebDriver driver;

    //Хэдер "Для кого самокат"
    private By firstFormHeader = By.xpath(".//div[text()='Для кого самокат']");

    //Хэдер "Для кого самокат"
    private By secondFormHeader = By.xpath(".//div[text()='Про аренду']");

    // Поле ввода "Имя"
    private By nameField = By.xpath(".//div/div[2]/div[2]/div[1]/input");

    //Поле ввода "Фамилия"
    private By secondNameField = By.xpath(".//div/div[2]/div[2]/div[2]/input");

    //Поле ввода "Адрес: куда привезти заказ"
    private By addressField = By.xpath(".//div/div[2]/div[2]/div[3]/input");

    //Поле ввода "Станция метро"
    private By metroField = By.cssSelector(".select-search__input");

    //Все станции метро
    private By metroStationButtons = By.cssSelector("li>button.select-search__option>.Order_Text__2broi");

    //Поле ввода "Телефон: на него позвонит курьер"
    private By phoneField = By.xpath(".//div/div[2]/div[2]/div[5]/input");

    //Кнопка "Далее" на странице с первой формой ввода данных для заказа
    private By nextButton = By.cssSelector(".Button_Button__ra12g.Button_Middle__1CSJM");

    //Поле "Когда привезти самокат"
    private By deliveryDateField = By.cssSelector(".react-datepicker__input-container>.Input_Input__1iN_Z.Input_Responsible__1jDKN");

    //Поле "Срок аренды"
    private By rentalPeriodField = By.xpath(".//div/div[2]/div[2]/div[2]/div");

    //пункты в выпадающем списке поля "Срок аренды"
    private By rentalPeriodOptions = By.xpath(".//div[2]/div[2]/div[2]/div[2]/div");

    //Чек-бокс "черный жемчуг"
    private By blackColor = By.id("black");

    //Чек-бокс "серая безысходность"
    private By greyColor = By.id("grey");

    //Поле "Комментарий для курьера"
    private By commentField = By.cssSelector(".Input_InputContainer__3NykH>.Input_Input__1iN_Z.Input_Responsible__1jDKN");

    //Кнопка "Заказать"
    private By orderButton = By.xpath(".//div/div[2]/div[3]/button[2]");

    //Кнопка "Да"
    private By yesButton = By.xpath(".//div/div[2]/div[5]/div[2]/button[2]");

    //"Подтверждение заказа"
    private By confirmOrderHeader = By.xpath(".//div[text()='Хотите оформить заказ?']");
    // Заказ оформлен

    private By successOrderHeader = By.xpath(".//div[text()='Заказ оформлен']");

    //кнопка "посмотреть заказ"
    private By showOrderButton = By.cssSelector(".Order_Modal__YZ-d3 .Button_Button__ra12g.Button_Middle__1CSJM");

    public OrderPage(WebDriver driver){
        this.driver = driver;
    }

    //Метод ожидания появления первой формы для заполнения
    public void waitForFirstScreenLoaded() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(firstFormHeader));
    }

    //Метод ожидания появления первой формы для заполнения
    public void waitForSecondScreenLoaded() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(secondFormHeader));
    }

    //Заполняем поле "Имя"
    public void setCustomerName(String customerName) {
        driver.findElement(nameField).sendKeys(customerName);
    }

    //Заполняем поле "Фамилия"
    public void setCustomerSecondName(String customerSecondName) {
        driver.findElement(secondNameField).sendKeys(customerSecondName);
    }

    //Заполняем поле "Адрес: куда привезти заказ"
    public void setCustomerAddress(String customerAddress) {
        driver.findElement(addressField).sendKeys(customerAddress);
    }

    //Выбираем станцию метро
    public void clickCustomerMetroSelector() {
        driver.findElement(metroField).click();
    }

    public void waitForMetroStationElementsVisible() {
        new WebDriverWait(driver, 1)
                .until(ExpectedConditions.visibilityOfElementLocated(metroStationButtons));
    }

    private WebElement findMetroStationButtonByStationName(String customerMetro) {
        // Находим все элементы, соответствующие станциям метро
        List<WebElement> metroStations = driver.findElements(metroStationButtons);

        //перебираем все элементы в списке
        for (WebElement metroStationElement : metroStations) {
            //И если нашёлся элемент с искомым текстом
            if (metroStationElement.getText().equals(customerMetro)) {
                //То возвращаем его
                return metroStationElement;
            }
        }
        //если ничего не нашлось, то ничего не возвращаем
        return null;
    }

    //Прокрутить до кнопки с конкретной станцией метро
    public void scrollToMetroStationByttonByStationName(String customerMetro) {
        ((JavascriptExecutor)driver)
                .executeScript(
                        "arguments[0].scrollIntoView();",
                        findMetroStationButtonByStationName(customerMetro)
                );
    }

    //Нажать на конкретную станцию метро
    public void clickMetroStationButtonByStationName(String customerMetro) {
        findMetroStationButtonByStationName(customerMetro).click();
    }

    //Заполняем поле "Телефон: на него позвонит курьер"
    public void setCustomerPhone(String customerPhone) {
        driver.findElement(phoneField).sendKeys(customerPhone);
    }

    //Жмем кнопку "Далее"
    public void clickNextButton() {
        driver.findElement(nextButton).click();
    }

    //Заполняем поле "Когда привезти самокат"
    public void setDeliveryDate(String deliveryDate) {
        driver.findElement(deliveryDateField).sendKeys(deliveryDate + "\n");
    }

    //Метод клика по полю "Срок аренды"
    public void clickRentalPeriodField() {
        driver.findElement(rentalPeriodField).click();
    }

    //Ждать, пока не появятся варианты выбора сроков доставки
    public void waitForRentalPeriodButtonsVisible() {
        new WebDriverWait(driver, 1)
                .until(ExpectedConditions.visibilityOfElementLocated(rentalPeriodOptions));
    }

    private WebElement findRentalPeriodButtonByText(String text) {
        //находим все варианты выбора
        List<WebElement> elements = driver.findElements(rentalPeriodOptions);
        //итерируемся по ним
        for (WebElement element : elements) {
            //если у какого-то варианта интересующий нас текст
            if (element.getText().equals(text)) {
                //Возвращаем найденный элемент
                return element;
            }
        }
        //возвращаем ничего
        return null;
    }

    public void scrollToRentalPeriodButtonByText(String text) {
        ((JavascriptExecutor)driver)
                .executeScript(
                        "arguments[0].scrollIntoView();",
                        findRentalPeriodButtonByText(text)
                );
    }

    public void clickToRentalPeriodButtonByText(String text) {
        findRentalPeriodButtonByText(text).click();
    }

    //Кликаем на чекбокс про цвет самоката
    public void clickColorCheckboxByName(String colorName) {
        if (colorName.equals("black")) {
            driver.findElement(blackColor).click();
        } else if (colorName.equals("grey")) {
            driver.findElement(greyColor).click();
        }
    }

    //Метод заполнения поля "Комментарий для курьера"
    public void setComment(String comment) {
        driver.findElement(commentField).sendKeys(comment);
    }

    //Метод клика по кнопке "Заказать"
    public void clickOrderButton() {
        driver.findElement(orderButton).click();
    }

    //Метод ожидания появления окна с информацией об успешном заказе
    public void waitForModalFormLoaded() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(confirmOrderHeader));
    }

    //нажимаем на кнопку "да" в модальном окне
    public void clickYesButton() {
        driver.findElement(yesButton).click();
    }

    public void waitForSuccessModalFormLoaded() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(successOrderHeader));
    }

    public void clickShowOrderButton() {
        driver.findElement(showOrderButton).click();
    }
}

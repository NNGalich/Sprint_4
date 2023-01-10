package tests;

import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObject.MainPageScooter;
import pageObject.OrderPage;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;

@RunWith(Parameterized.class)
public class OrderTest {
    //Какую кнопку заказа нажимать: верхнюю или нижнюю
    private final String orderButtonName;
    //Имя пользователя
    private final String firstName;
    //фамилия пользователя
    private final String surname;
    //Адрес доставки
    private final String address;
    //Станция метро
    private final String metroStation;
    //Номер телефона
    private final String phoneNumber;
    // Дата доставки
    private final String deliveryDate;
    //Срок аренды
    private final String rentalPeriod;
    //Цвет самоката: black или grey
    private final String scooterColor;
    //Комментарий к заказу
    private final String comment;

    //Поле с браузером
    private WebDriver driver;
    //Объект с локаторами главной страницы
    private MainPageScooter objMainPageScooter;
    //объект с локаторами страницы заказа
    private  OrderPage objOrderPage;

    public OrderTest(
            String orderButtonName,
            String firstName,
            String surname,
            String address,
            String metroStation,
            String phoneNumber,
            String deliveryDate,
            String rentalPeriod,
            String scooterColor,
            String comment
    ) {
        this.orderButtonName = orderButtonName;
        this.firstName = firstName;
        this.surname = surname;
        this.address = address;
        this.metroStation = metroStation;
        this.phoneNumber = phoneNumber;
        this.deliveryDate = deliveryDate;
        this.rentalPeriod = rentalPeriod;
        this.scooterColor = scooterColor;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Object[][] getTestParameters() {
        return new Object[][]{
                {
                    "upper",
                    "Коля",
                    "Галич",
                    "ул. Большая Пионерскаая, д. 15с1",
                    "Черкизовская",
                    "+79161234567",
                    "01.01.2023",
                    "сутки",
                    "black",
                    ""
                },
                {
                    "lower",
                    "Николай",
                    "Галичевский",
                    "ул. Большая Пионерскаая, д. 15с1",
                    "Семёновская",
                    "89167654321",
                    "01.01.2025",
                    "семеро суток",
                    "grey",
                    "Оставить у двери"
                }
        };
    }

    @Before
    public void setUp() {
        //Создаём браузер
        driver = new ChromeDriver();
        driver.get("https://qa-scooter.praktikum-services.ru/");

        //Создаём pageObject объекты для главной страницы и для страницы заказа
        objMainPageScooter = new MainPageScooter(driver);
        objOrderPage = new OrderPage(driver);
    }

    @Test
    public void checkOrderAScooter() {
        //Принимаем куки, чтобы плашка не мешалась
        objMainPageScooter.clickAcceptCookie();

        //Прокручиваем страницу до кнопки заказа
        objMainPageScooter.scrollToOrderButtonByName(orderButtonName);
        //Кликаем на кнопку заказа на главной странице
        objMainPageScooter.clickOrderButtonByName(orderButtonName);

        //Дожидаемся хедера "Для кого самокат"
        objOrderPage.waitForFirstScreenLoaded();

        //Убеждаемся, что произошёл редирект и мы находимся на правильной странице
        MatcherAssert.assertThat(driver.getCurrentUrl(), is("https://qa-scooter.praktikum-services.ru/order"));

        //Заполняем первый шаг формы: имя, фамилия, адрес
        objOrderPage.setCustomerName(firstName);
        objOrderPage.setCustomerSecondName(surname);
        objOrderPage.setCustomerAddress(address);

        //Выбираем станцию метро: кликаем на кнопку с выпадающим списком
        objOrderPage.clickCustomerMetroSelector();
        //Выбираем станцию метро: дожидаемся, пока список не будет показан
        objOrderPage.waitForMetroStationElementsVisible();
        //Выбираем станцию метро: находим и кликаем на конкретную станцию метро
        objOrderPage.scrollToMetroStationByttonByStationName(metroStation);
        objOrderPage.clickMetroStationButtonByStationName(metroStation);

        //проставляем номер телефона
        objOrderPage.setCustomerPhone(phoneNumber);

        // Кликаем на кнопку "далее"
        objOrderPage.clickNextButton();

        //Дожидаемся загрузки следующей формы
        objOrderPage.waitForSecondScreenLoaded();

        //Заполняем дату "когда привезти самокат"
        objOrderPage.setDeliveryDate(deliveryDate);

        //Нажимаем на чекбокс с цветом
        objOrderPage.clickColorCheckboxByName(scooterColor);

        //Заполняем срок аренды: кликаем на поле ввода
        objOrderPage.clickRentalPeriodField();
        //Дожидаемся, пока не покажется список
        objOrderPage.waitForRentalPeriodButtonsVisible();
        //Скроллим на нужную кнопку
        objOrderPage.scrollToRentalPeriodButtonByText(rentalPeriod);
        //Нажимаем на конкретный вариант со сроком аренды
        objOrderPage.clickToRentalPeriodButtonByText(rentalPeriod);

        //Заполняем поле "комментарий"
        objOrderPage.setComment(comment);

        //нажимаем на кнопку "заказать"
        objOrderPage.clickOrderButton();

        //Ждём появления модального окна
        objOrderPage.waitForModalFormLoaded();

        //Нажимаем "да"
        objOrderPage.clickYesButton();

        //Ждём появления нового окна
        objOrderPage.waitForSuccessModalFormLoaded();

        //нажимаем на "показать заказ"
        objOrderPage.clickShowOrderButton();

        //Убеждаемся, что произошёл редирект и мы находимся на правильной странице
        MatcherAssert.assertThat(driver.getCurrentUrl(), startsWith("https://qa-scooter.praktikum-services.ru/track"));
    }

    @After
    public void teardown() {
        // Закрой браузер
        driver.quit();
    }
}

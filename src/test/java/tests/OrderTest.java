package tests;

import org.hamcrest.MatcherAssert;
import org.junit.After;
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
    private final TestParameters testParameters;

    private WebDriver driver;

    public OrderTest(TestParameters testParameters) {
        this.testParameters = testParameters;
    }

    @Parameterized.Parameters
    public static Object[][] getTestParameters() {
        return new Object[][]{
                {
                    new TestParameters(
                            "Коля",
                            "Галич",
                            "ул. Большая Пионерскаая, д. 15с1",
                            "Черкизовская",
                            "+79161234567",
                            "01.01.2023",
                            "сутки",
                            "black",
                            ""
                    )
                },
                {
                        new TestParameters(
                                "Николай",
                                "Галичевский",
                                "ул. Большая Пионерскаая, д. 15с1",
                                "Семёновская",
                                "89167654321",
                                "01.01.2025",
                                "семеро суток",
                                "black",
                                "Оставить у двери"
                        )
                },
        };
    }

    @Test
    public void checkOrderAScooter() {
        //Создаём браузер
        driver = new ChromeDriver();
        driver.get("https://qa-scooter.praktikum-services.ru/");

        //Создаём pageObject объекты для главной страницы и для страницы заказа
        MainPageScooter objMainPageScooter = new MainPageScooter(driver);
        OrderPage objOrderPage = new OrderPage(driver);

        //Кликаем на кнопку заказа на главной странице
        objMainPageScooter.clickUpperOrderButton();

        //Дожидаемся хедера "Для кого самокат"
        objOrderPage.waitForFirstScreenLoaded();

        //Убеждаемся, что произошёл редирект и мы находимся на правильной странице
        MatcherAssert.assertThat(driver.getCurrentUrl(), is("https://qa-scooter.praktikum-services.ru/order"));

        //Заполняем первый шаг формы: имя, фамилия, адрес
        objOrderPage.setCustomerName(testParameters.getFirstName());
        objOrderPage.setCustomerSecondName(testParameters.getSurname());
        objOrderPage.setCustomerAddress(testParameters.getAddress());

        //Выбираем станцию метро: кликаем на кнопку с выпадающим списком
        objOrderPage.clickCustomerMetroSelector();
        //Выбираем станцию метро: дожидаемся, пока список не будет показан
        objOrderPage.waitForMetroStationElementsVisible();
        //Выбираем станцию метро: находим и кликаем на конкретную станцию метро
        objOrderPage.scrollToMetroStationByttonByStationName(testParameters.getMetroStation());
        objOrderPage.clickMetroStationButtonByStationName(testParameters.getMetroStation());

        //проставляем номер телефона
        objOrderPage.setCustomerPhone(testParameters.getPhoneNumber());

        // Кликаем на кнопку "далее"
        objOrderPage.clickNextButton();

        //Дожидаемся загрузки следующей формы
        objOrderPage.waitForSecondScreenLoaded();

        //Заполняем дату "когда привезти самокат"
        objOrderPage.setDeliveryDate(testParameters.getDeliveryDate());

        //Нажимаем на чекбокс с цветом
        objOrderPage.clickColorCheckboxByName(testParameters.getScooterColor());

        //Заполняем срок аренды: кликаем на поле ввода
        objOrderPage.clickRentalPeriodField();
        //Дожидаемся, пока не покажется список
        objOrderPage.waitForRentalPeriodButtonsVisible();
        //Скроллим на нужную кнопку
        objOrderPage.scrollToRentalPeriodButtonByText(testParameters.getRentalPeriod());
        //Нажимаем на конкретный вариант со сроком аренды
        objOrderPage.clickToRentalPeriodButtonByText(testParameters.getRentalPeriod());

        //Заполняем поле "комментарий"
        objOrderPage.setComment(testParameters.getComment());

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

    private static class TestParameters {
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

        public TestParameters(
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

        public String getFirstName() {
            return firstName;
        }

        public String getSurname() {
            return surname;
        }

        public String getAddress() {
            return address;
        }

        public String getMetroStation() {
            return metroStation;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getDeliveryDate() {
            return deliveryDate;
        }

        public String getRentalPeriod() {
            return rentalPeriod;
        }

        public String getScooterColor() {
            return scooterColor;
        }

        public String getComment() {
            return comment;
        }
    }
}

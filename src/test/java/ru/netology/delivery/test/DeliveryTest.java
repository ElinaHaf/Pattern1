package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;
import static ru.netology.delivery.data.DataGenerator.generateDate;

class DeliveryTest {

    @BeforeEach
    void setup() {
        Faker faker = new Faker(new Locale("ru"));
        //Configuration.headless = true;
        open("http://localhost:9999");

    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = generateDate(daysToAddForSecondMeeting);
        $("[data-test-id=city] .input__control").sendKeys(validUser.getCity());
        //LocalDate.now().plusDays(daysToAddForFirstMeeting).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(firstMeetingDate);
        $("[name='name']").sendKeys(validUser.getName());
        $("[name='phone']").sendKeys(validUser.getPhone());
        $(By.className("checkbox__box")).click();
        $(By.className("button__text")).click();
        $x("//*[contains(text(),'Успешно!')]").shouldBe(Condition.visible);
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + generateDate(daysToAddForFirstMeeting)));
                //.shouldBe(Condition.visible);
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(secondMeetingDate);
        $(By.className("button__text")).click();
        //$(".notification__content")
                //.shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?")).shouldBe(Condition.visible);
        //$(By.className("notification__content")).shouldHave(Condition.text("Необходимо подтверждение")).shouldBe(Condition.visible);
        $x("//span[contains(text(),'Перепланировать')]").click();
        //$(By.className("button")).click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + generateDate(daysToAddForSecondMeeting)))
                .shouldBe(Condition.visible);
    }


}
// TODO: добавить логику теста в рамках которого будет выполнено планирование и перепланирование встречи.
// Для заполнения полей формы можно использовать пользователя validUser и строки с датами в переменных
// firstMeetingDate и secondMeetingDate. Можно также вызывать методы generateCity(locale),
// generateName(locale), generatePhone(locale) для генерации и получения в тесте соответственно города,
// имени и номера телефона без создания пользователя в методе generateUser(String locale) в датагенераторе


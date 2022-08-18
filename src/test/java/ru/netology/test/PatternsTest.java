package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGeneration;


import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class PatternsTest {


    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        Configuration.holdBrowserOpen = true;
        var city = DataGeneration.Registration.generateCity("ru");
        var fullName = DataGeneration.Registration.generateName("ru");
        var phone = DataGeneration.Registration.generatePhone("ru");
        var daysToAddForFirstMeeting = 3;
        var firstMeetingDate = DataGeneration.Registration.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 5;
        var secondMeetingDate = DataGeneration.Registration.generateDate(daysToAddForSecondMeeting);

        $("[data-test-id=city] input").setValue(city);
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(firstMeetingDate);
        $("[data-test-id=name] input").setValue(fullName);
        $("[data-test-id=phone] input").setValue(phone);
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(),'Запланировать')]").click();
        $("[data-test-id=success-notification]")
                .shouldHave(exactText("Успешно! Встреча успешно запланирована на " + firstMeetingDate));
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(secondMeetingDate);
        $x("//*[contains(text(),'Запланировать')]").click();
        $("[data-test-id = replan-notification]")
                .shouldHave(text("Необходимо подтверждение"), Duration.ofSeconds(15))
                .shouldBe(visible);
        $("[data-test-id = replan-notification] .notification__content")
                .shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"), Duration.ofSeconds(15))
                .shouldBe(visible);
        $("[data-test-id = replan-notification] .button")
                .shouldHave(text("Перепланировать"), Duration.ofSeconds(15))
                .shouldBe(visible);
        $("[data-test-id = replan-notification] .button").click();
        $("[data-test-id = success-notification]")
                .shouldHave(text("Успешно!"), Duration.ofSeconds(15))
                .shouldBe(visible);
    }
}

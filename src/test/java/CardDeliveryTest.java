import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.Keys.BACK_SPACE;


public class CardDeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void testSuccessfulReplan() {
        var daysFirstMeeting = 4;
        var daysSecondMeeting = 5;

        $("[data-test-id='city'] .input__control").setValue(DataGenerator.generateCity("ru"));
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(DataGenerator
                .generateData(daysFirstMeeting, "dd.MM.yyyy"));
        $("[data-test-id='name'] [name='name']").setValue(DataGenerator.generateName("ru"));
        $("[data-test-id='phone'] [name='phone']").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] .notification__content")
                .shouldHave(text("Встреча успешно запланирована на "
                        + DataGenerator.generateData(daysFirstMeeting, "dd.MM.yyyy")), Duration.ofSeconds(15))
                .shouldBe(visible);
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(BACK_SPACE);
        $("[data-test-id='date'] .input__control").setValue(DataGenerator
                .generateData(daysSecondMeeting, "dd.MM.yyyy"));
        $(".button").click();
        $("[data-test-id='replan-notification'] .notification__content")
                .shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"))
                .shouldBe(visible);
        $("[data-test-id='replan-notification'] .button__text").click();
        $("[data-test-id='success-notification']").shouldHave(text("Встреча успешно запланирована на " + DataGenerator.generateData(daysSecondMeeting, "dd.MM.yyyy")));

    }
}
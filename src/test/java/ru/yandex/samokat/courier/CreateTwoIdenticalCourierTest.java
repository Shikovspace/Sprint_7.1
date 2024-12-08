package java.ru.yandex.samokat.courier;

import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.model.CourierAccount;
import ru.yandex.samokat.steps.Steps;

import java.util.Locale;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotEquals;

public class CreateTwoIdenticalCourierTest {
    private final Faker faker = new Faker(new Locale("en"));
    private final Steps steps = new Steps();
    private CourierAccount account;

    @Before
    public void setUp() {
        account = new CourierAccount(
                faker.funnyName().name(),
                faker.internet().password(),
                faker.name().firstName());
    }

    @Test
    @DisplayName("Создание курьера - нельзя создать двух одинаковых курьеров")
    public void createIdenticalAccountsForbidden() {
        ValidatableResponse createFirst = steps.create(account);
        int firstStatusCode = createFirst.extract().statusCode();
        assertThat("Ожидаем код 201", firstStatusCode, equalTo(HttpStatus.SC_CREATED));

        ValidatableResponse createSecond = steps.create(account);
        int secondStatusCode = createSecond.extract().statusCode();
        assertNotEquals("Статус код не должен быть 201", secondStatusCode, equalTo(HttpStatus.SC_CREATED));
    }
}

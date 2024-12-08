package ru.yandex.samokat.courier;

import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.model.CourierAccount;
import ru.yandex.samokat.steps.Steps;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotEquals;

public class CreateNewCourierWithDuplicateLoginTest {
    private final Faker faker = new Faker(new Locale("en"));
    private final Steps steps = new Steps();
    private CourierAccount account;
    private List<CourierAccount> testData;

    @Before
    public void setUp() {
        testData = new ArrayList<>();
        account = new CourierAccount(
                faker.funnyName().name(),
                faker.internet().password(),
                faker.name().firstName());
        testData.add(account);
    }

    @Test
    @DisplayName("Создание курьера - если создать пользователя с логином, который уже есть, возвращается ошибка")
    public void createIdenticalLoginForbidden() {
        ValidatableResponse createFirst = steps.create(account);
        int statusCode = createFirst.extract().statusCode();
        assertThat("Создали первого курьера. Код 201", statusCode, equalTo(HttpStatus.SC_CREATED));
        CourierAccount courierSecondAccount = new CourierAccount(
                account.getLogin(),
                faker.internet().password(),
                faker.name().firstName());
        testData.add(courierSecondAccount);
        ValidatableResponse createSecond = steps.create(courierSecondAccount);
        statusCode = createSecond.extract().statusCode();
        assertNotEquals("Статус код не должен быть 201", statusCode, equalTo(HttpStatus.SC_CREATED));
    }

    @After
    public void cleanUp() {
        steps.delete(testData);
    }
}

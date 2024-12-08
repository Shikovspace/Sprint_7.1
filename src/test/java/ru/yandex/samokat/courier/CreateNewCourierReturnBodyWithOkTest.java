package ru.yandex.samokat.courier;

import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.model.CourierAccount;
import ru.yandex.samokat.steps.Steps;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class CreateNewCourierReturnBodyWithOkTest {
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
    @DisplayName("Создание курьера - успешный запрос возвращает ok: true")
    public void createNewCourierReturnBodyWithOk() {
        ValidatableResponse response = steps.create(account);
        boolean expected = true;
        boolean actual = response.extract().body().jsonPath().getBoolean("ok");
        assertEquals("Успешный запрос возвращает ok: true", expected, actual);
    }

    @After
    public void cleanUp() {
        steps.delete(testData);
    }
}

package ru.yandex.samokat.login;

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

public class LoginIsNotCreatedUserShowErrorTest {
    private static final String ACCOUNT_ERROR = "Учетная запись не найдена";

    private final Faker faker = new Faker(new Locale("en"));
    private final Steps steps = new Steps();
    private CourierAccount account;
    private List<CourierAccount> testData;

    @Before
    public void setUp() {
        testData = new ArrayList<>();
        account = new CourierAccount(faker.funnyName().name(), faker.internet().password(), faker.name().firstName());
        testData.add(account);
    }

    @Test
    @DisplayName("Если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
    public void loginIsNotCreatedUserShowError() {
        ValidatableResponse response = steps.login(account);
        assertEquals("Авторизация под несуществующим пользователем должна вернуть ошибку",
                response.extract().body().jsonPath().getString("message"), ACCOUNT_ERROR);
    }

    @After
    public void cleanUp() {
        steps.delete(testData);
    }
}

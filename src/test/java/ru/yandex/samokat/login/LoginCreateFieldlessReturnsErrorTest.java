package java.ru.yandex.samokat.login;

import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
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

public class LoginCreateFieldlessReturnsErrorTest {

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
    @DisplayName("Если одного из полей нет, запрос возвращает ошибку.")
    public void createFieldlessReturnsError() {
        steps.create(account);
        CourierAccount wrongAccount = new CourierAccount();
        testData.add(wrongAccount);
        wrongAccount.setPassword(account.getPassword());
        assertThat("Пароль обязательное поле, ждем 400 код", steps.login(wrongAccount).extract().statusCode(),
                equalTo(HttpStatus.SC_BAD_REQUEST));
    }

    @After
    public void cleanUp() {
        steps.delete(testData);
    }
}

package java.ru.yandex.samokat.courier;

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

public class CreateNewCourierTest {
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
    @DisplayName("Создание курьера - курьера можно создать")
    public void createNewCourierReturnSC_CREATED() {
        ValidatableResponse response = steps.create(account);
        assertThat("Ждём 201",
                response.extract().statusCode(),
                equalTo(HttpStatus.SC_CREATED));
    }

    @After
    public void cleanUp() {
        steps.delete(testData);
    }
}

package ru.yandex.samokat.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import ru.yandex.model.CourierAccount;
import ru.yandex.model.Login;
import ru.yandex.model.Order;

import java.util.List;

import static io.restassured.RestAssured.given;

public class Steps {

    private final String baseUrl = "https://qa-scooter.praktikum-services.ru/api/v1";

    @Step("Создание курьера")
    public ValidatableResponse create(CourierAccount account) {

        return given()
                .header("Content-Type", "application/json")
                .body(account)
                .post(baseUrl + "/courier")
                .then()
                .log().all();
    }

    @Step("Авторизация курьера в системе")
    public ValidatableResponse login(CourierAccount account) {
        Login body = new Login(account);
        return given()
                .header("Content-Type", "application/json")
                .body(body)
                .post(baseUrl + "/courier/login")
                .then()
                .log().all();
    }

    @Step("Удаление курьеров")
    public void delete(List<CourierAccount> accounts) {
        ValidatableResponse loginResp;
        if(!accounts.isEmpty()) {
            for (CourierAccount account: accounts) {
                loginResp = login(account);
                if (loginResp.extract().statusCode() == HttpStatus.SC_OK) {
                    given()
                            .header("Content-Type", "application/json")
                            .get(baseUrl + String.format("/courier/%d",
                                    loginResp.extract().body().jsonPath().getInt("id")));
                }
            }
        }
    }

    @Step("Создание заказа")
    public ValidatableResponse orderCreate(Order order) {
        return given()
                .header("Content-Type", "application/json")
                .body(order)
                .post(baseUrl + "/orders")
                .then()
                .log().all();
    }

    @Step("Список заказов")
    public Response orders() {
        return given()
                .header("Content-Type", "application/json")
                .get(baseUrl + "/orders");
    }
}

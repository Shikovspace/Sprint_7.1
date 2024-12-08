package ru.yandex.samokat.order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import ru.yandex.model.Orders;
import ru.yandex.samokat.steps.Steps;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class OrdersTest {
    private final Steps steps = new Steps();

    @Test
    @DisplayName("Список заказов - В тело ответа возвращается список заказов")
    public void ordersReturnsOrderList() {
        Response response = steps.orders();
        assertThat("В тело ответа возвращается список заказов",
                response.as(Orders.class).getOrders(),
                notNullValue());
    }
}

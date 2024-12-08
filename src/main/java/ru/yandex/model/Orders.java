package ru.yandex.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Orders {
    private List<OrdersItem> orders;
    private PageInfo pageInfo;
    private List<Station> availableStations;
}

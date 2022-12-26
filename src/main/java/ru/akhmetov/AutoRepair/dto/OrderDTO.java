package ru.akhmetov.AutoRepair.dto;

import jakarta.validation.constraints.Min;
import ru.akhmetov.AutoRepair.models.OrderType;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
public class OrderDTO {
    @Min(value = 0, message = "Стоимость не должна быть меньше нуля")
    private int value;

    private OrderType orderType;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }
}

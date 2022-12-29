package ru.akhmetov.AutoRepair.dto;

import jakarta.validation.constraints.Min;
import ru.akhmetov.AutoRepair.models.OrderType;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
public class OrderDTO {

    private int id;

    private String name;
    @Min(value = 0, message = "Стоимость не должна быть меньше нуля")
    private int value;

    private OrderType orderType;

    public OrderDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

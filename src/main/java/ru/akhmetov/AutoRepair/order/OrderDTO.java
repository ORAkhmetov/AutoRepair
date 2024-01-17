package ru.akhmetov.AutoRepair.order;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**  Объект передачи данных (DTO - Data Transfer Object) для сущности "Order" **/
@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {

    private int id;

    private String name;
    @Min(value = 0, message = "Стоимость не должна быть меньше нуля")
    private int value;

    private OrderType orderType;

}

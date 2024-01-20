package ru.akhmetov.AutoRepair.appeal;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.akhmetov.AutoRepair.car.Car;
import ru.akhmetov.AutoRepair.order.Order;
import java.time.LocalDate;
import java.util.List;
/**  Объект передачи данных (DTO - Data Transfer Object) для сущности "Appeal"  **/
@Getter
@Setter
@NoArgsConstructor
public class AppealDTO {

    private int id;

    private String name;

    private String faultDescription;

    private Car car;

    @Min(value = 0, message = "Пробег не должен быть меньше нуля")
    private int mileage;

    private LocalDate dateOfAppeal;

    private List<Order> orderList;

    private List<PhotoAppeal> photoAppealList;

}

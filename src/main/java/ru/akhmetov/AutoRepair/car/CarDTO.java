package ru.akhmetov.AutoRepair.car;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.akhmetov.AutoRepair.client.Client;
/**  Объект передачи данных (DTO - Data Transfer Object) для сущности "Car"  **/

@Getter
@Setter
@NoArgsConstructor
public class CarDTO {

    private int id;

    @NotEmpty(message = "Модель не должна быть пустой")
    private String model;

    @NotEmpty(message = "Госномер не должен быть пустым")
    private String stateNumber;

    @NotEmpty(message = "VIN не должен быть пустым")
    private String vin;
    private String filename;

    private Client owner;
    public CarDTO(Car car) {
        this.model = car.getModel();
        this.stateNumber = car.getStateNumber();
        this.vin = car.getVin();
        this.owner = car.getOwner();
    }
}

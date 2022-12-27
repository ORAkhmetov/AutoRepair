package ru.akhmetov.AutoRepair.dto;

import jakarta.validation.constraints.NotEmpty;
import ru.akhmetov.AutoRepair.models.Car;
import ru.akhmetov.AutoRepair.models.Client;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
public class CarDTO {

    @NotEmpty(message = "Модель не должна быть пустой")
    private String model;

    @NotEmpty(message = "Госномер не должн быть пустым")
    private String stateNumber;

    @NotEmpty(message = "VIN не должен быть пустым")
    private String vin;

    private Client owner;
    public CarDTO(Car car) {
        this.model = car.getModel();
        this.stateNumber = car.getStateNumber();
        this.vin = car.getVin();
        this.owner = car.getOwner();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStateNumber() {
        return stateNumber;
    }

    public void setStateNumber(String stateNumber) {
        this.stateNumber = stateNumber;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }
}

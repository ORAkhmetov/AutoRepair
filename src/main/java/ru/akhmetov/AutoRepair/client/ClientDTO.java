package ru.akhmetov.AutoRepair.client;

import jakarta.validation.constraints.NotEmpty;
import ru.akhmetov.AutoRepair.car.Car;

import java.util.List;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
public class ClientDTO {

    private int id;

    @NotEmpty(message = "Имя не должно быть пустым")
    private String fullName;
    private List<Car> carList;

    public ClientDTO(Client client) {
        this.fullName = client.getFullName();
    }

    public ClientDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Car> getCarList() {
        return carList;
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }
}

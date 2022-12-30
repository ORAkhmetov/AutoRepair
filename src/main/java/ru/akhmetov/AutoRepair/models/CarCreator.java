package ru.akhmetov.AutoRepair.models;

import java.util.List;

/**
 * @author Oleg Akhmetov on 30.12.2022
 */
public class CarCreator {
    private List<Car> carList;

    public CarCreator() {
    }
    public int createCar(Client client) {
        Car car = new Car(client);
        carList.add(car);
        return carList.indexOf(car);
    }

    public List<Car> getCarList() {
        return carList;
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }
}

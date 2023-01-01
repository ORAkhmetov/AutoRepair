package ru.akhmetov.AutoRepair.services;

import ru.akhmetov.AutoRepair.dto.CarDTO;
import ru.akhmetov.AutoRepair.dto.ClientDTO;
import ru.akhmetov.AutoRepair.models.Car;
import ru.akhmetov.AutoRepair.models.Client;

import java.util.List;

/**
 * @author Oleg Akhmetov on 27.12.2022
 */
public interface CarsService {
    List<Car> findAll();
    Car findOne(int id);
    void save(Car car);
    void update(int id, Car updatedCar) ;
    void delete(int id);
    public List<Car> getCarsByClient(Client client);
    default void enrichCar(Car car, Client client) {
        car.setOwner(client);
    }
}

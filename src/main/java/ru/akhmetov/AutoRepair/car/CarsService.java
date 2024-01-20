package ru.akhmetov.AutoRepair.car;

import ru.akhmetov.AutoRepair.client.Client;

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

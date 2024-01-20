package ru.akhmetov.AutoRepair.car;

import ru.akhmetov.AutoRepair.client.Client;
import java.util.List;
import java.util.Optional;

/**  Предоставляет базовые методы для управления заказами  **/
public interface CarsService {

    List<Car> findAll();
    Car findOne(int id);
    void save(Car car);
    void update(int id, Car updatedCar) ;
    void delete(int id);
    List<Car> getCarsByClient(Client client);
    default void enrichCar(Car car, Client client) {
        car.setOwner(client);
    }

    List<Car> getCarsByModel(String query);

    List<Car> getCarsByStateNumber(String query);

    Optional<Car> getCarByVin(String vin);
    Optional<Car> getCarByStateNumber(String stateNumber);
}

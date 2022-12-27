package ru.akhmetov.AutoRepair.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akhmetov.AutoRepair.models.Car;
import ru.akhmetov.AutoRepair.models.Client;
import ru.akhmetov.AutoRepair.repositories.CarsRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Service
@Transactional(readOnly = true) //Все методы readOnly, если не помечены аналогичной аннотацией
public class DefaultCarsService implements CarsService {
    private final CarsRepository carsRepository;

    public DefaultCarsService(CarsRepository carsRepository) {
        this.carsRepository = carsRepository;
    }
    public List<Car> findAll() {
        return carsRepository.findAll();
    }
    public Car findOne(int id) {
        Optional<Car> foundedCar = carsRepository.findById(id);
        return foundedCar.orElse(null);
    }
    @Transactional
    public void save(Car car) {
        carsRepository.save(car);
    }
    @Transactional
    public void update(int id, Car updatedCar) {
        //Если объекту в методе установить id, то при выполнении метода save, Hibernate поймет, что такой объект уже есть
        // и выполнит update, т к в поле id есть аннотация id
        updatedCar.setId(id);
        carsRepository.save(updatedCar);
    }
    @Transactional
    public void delete(int id) {
        carsRepository.deleteById(id);
    }

    public List<Car> getCarsByClient(Client client) {
        return carsRepository.getCarsByOwner(client);
    }
}
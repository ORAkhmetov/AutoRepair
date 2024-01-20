package ru.akhmetov.AutoRepair.car;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akhmetov.AutoRepair.client.Client;
import java.util.List;
import java.util.Optional;
/**  Реализацию интерфейса "CarsService"  **/
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) //Все методы readOnly, если не помечены аналогичной аннотацией
public class CarsServiceImpl implements CarsService {
    private final CarsRepository carsRepository;



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
        Car dataCar = findOne(id);
        updatedCar.setOwner(dataCar.getOwner());
        updatedCar.setAppealList(dataCar.getAppealList());
        carsRepository.save(updatedCar);
    }
    @Transactional
    public void delete(int id) {
        carsRepository.deleteById(id);
    }

    public List<Car> getCarsByClient(Client client) {
        return carsRepository.getCarsByOwner(client);
    }
    public Optional<Car> getCarByVin(String vin) {
        return carsRepository.getCarByVin(vin);
    }
    public Optional<Car> getCarByStateNumber(String stateNumber) {
        return carsRepository.getCarByStateNumber(stateNumber);
    }
    public List<Car> getCarsByModel(String query) {
        return carsRepository.getCarByModelContainingIgnoreCase(query);
    }
    public List<Car> getCarsByStateNumber (String query){
        return carsRepository.getCarByStateNumberContainingIgnoreCase(query);
    }
}

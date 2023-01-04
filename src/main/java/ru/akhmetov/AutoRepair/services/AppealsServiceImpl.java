package ru.akhmetov.AutoRepair.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akhmetov.AutoRepair.models.Appeal;
import ru.akhmetov.AutoRepair.models.Car;
import ru.akhmetov.AutoRepair.models.Client;
import ru.akhmetov.AutoRepair.repositories.AppealsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Service
@Transactional(readOnly = true) //Все методы readOnly, если не помечены аналогичной аннотацией
public class AppealsServiceImpl implements AppealsService {
    private final AppealsRepository appealsRepository;

    public AppealsServiceImpl(AppealsRepository appealsRepository) {
        this.appealsRepository = appealsRepository;
    }
    public List<Appeal> findAll() {
        return appealsRepository.findAll();
    }
    public Appeal findOne(int id) {
        Optional<Appeal> foundedCase = appealsRepository.findById(id);
        return foundedCase.orElse(null);
    }
    @Transactional
    public void save(Appeal appeal) {
        appealsRepository.save(appeal);
    }
    @Transactional
    public void update(int id, Appeal updatedAppeal) {
        //Если объекту в методе установить id, то при выполнении метода save, Hibernate поймет, что такой объект уже есть
        // и выполнит update, т к в поле id есть аннотация id
        Appeal appeal = findOne(id);
        updatedAppeal.setCar(appeal.getCar());
        updatedAppeal.setDateOfAppeal(appeal.getDateOfAppeal());
        updatedAppeal.setOrderList(appeal.getOrderList());
        appealsRepository.save(updatedAppeal);
    }
    @Transactional
    public void delete(int id) {
        appealsRepository.deleteById(id);
    }

    public List<Appeal> getAppealsByClient(Client client) {//Создаем список всех кейсов для конкретного клиента
        List<Car> carList = client.getCarList();
        List<Appeal> appealList = new ArrayList<>();
        for (Car car : carList) {
           appealList.addAll(car.getAppealList());
        }
        return appealList;
    }
    public List<Appeal> getAppealsByCar(Car car) {
        return appealsRepository.getAppealsByCar(car);
    }
}

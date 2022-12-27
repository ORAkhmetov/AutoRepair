package ru.akhmetov.AutoRepair.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akhmetov.AutoRepair.models.Car;
import ru.akhmetov.AutoRepair.models.Case;
import ru.akhmetov.AutoRepair.models.Client;
import ru.akhmetov.AutoRepair.repositories.CasesRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Service
@Transactional(readOnly = true) //Все методы readOnly, если не помечены аналогичной аннотацией
public class DefaultCasesService implements CasesService{
    private final CasesRepository casesRepository;

    public DefaultCasesService(CasesRepository casesRepository) {
        this.casesRepository = casesRepository;
    }
    public List<Case> findAll() {
        return casesRepository.findAll();
    }
    public Case findOne(int id) {
        Optional<Case> foundedCase = casesRepository.findById(id);
        return foundedCase.orElse(null);
    }
    @Transactional
    public void save(Case aCase) {
        casesRepository.save(aCase);
    }
    @Transactional
    public void update(int id, Case updatedCase) {
        //Если объекту в методе установить id, то при выполнении метода save, Hibernate поймет, что такой объект уже есть
        // и выполнит update, т к в поле id есть аннотация id
        updatedCase.setId(id);
        casesRepository.save(updatedCase);
    }
    @Transactional
    public void delete(int id) {
        casesRepository.deleteById(id);
    }

    public List<Case> getCasesByClient(Client client) {//Создаем список всех кейсов для конкретного клиента
        List<Car> carList = client.getCarList();
        List<Case> caseList = new ArrayList<>();
        for (Car car : carList) {
           caseList.addAll(car.getCaseList());
        }
        return caseList;
    }
}

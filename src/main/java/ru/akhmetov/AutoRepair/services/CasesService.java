package ru.akhmetov.AutoRepair.services;

import ru.akhmetov.AutoRepair.models.Car;
import ru.akhmetov.AutoRepair.models.Case;
import ru.akhmetov.AutoRepair.models.Client;

import java.util.Date;
import java.util.List;

/**
 * @author Oleg Akhmetov on 27.12.2022
 */
public interface CasesService {
    List<Case> findAll();
    Case findOne(int id);
    void save(Case aCase);
    void update(int id, Case updatedCase) ;
    void delete(int id);
    List<Case> getCasesByClient(Client client);
    default void enrichCase(Case aCase) {
        aCase.setDateOfCase(new Date());
    }
}

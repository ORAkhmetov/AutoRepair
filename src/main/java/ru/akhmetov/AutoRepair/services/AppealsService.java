package ru.akhmetov.AutoRepair.services;

import ru.akhmetov.AutoRepair.models.Appeal;
import ru.akhmetov.AutoRepair.models.Client;

import java.util.Date;
import java.util.List;

/**
 * @author Oleg Akhmetov on 27.12.2022
 */
public interface AppealsService {
    List<Appeal> findAll();
    Appeal findOne(int id);
    void save(Appeal appeal);
    void update(int id, Appeal updatedAppeal) ;
    void delete(int id);
    List<Appeal> getAppealsByClient(Client client);
    default void enrichCase(Appeal appeal) {
        appeal.setDateOfAppeal(new Date());
    }
}

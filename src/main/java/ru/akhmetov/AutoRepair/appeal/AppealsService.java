package ru.akhmetov.AutoRepair.appeal;

import ru.akhmetov.AutoRepair.car.Car;
import ru.akhmetov.AutoRepair.client.Client;
import java.time.LocalDate;
import java.util.List;
/**  Предоставляет базовые методы для управления заказами  **/

public interface AppealsService {
    List<Appeal> findAll();
    Appeal findOne(int id);
    void save(Appeal appeal);
    void update(int id, Appeal updatedAppeal) ;
    void delete(int id);
    List<Appeal> getAppealsByClient(Client client);
    default void enrichAppeal(Appeal appeal, Car car) {
        appeal.setDateOfAppeal(LocalDate.now());
        appeal.setCar(car);
    }
}

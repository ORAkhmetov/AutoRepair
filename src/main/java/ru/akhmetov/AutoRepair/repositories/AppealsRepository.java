package ru.akhmetov.AutoRepair.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.akhmetov.AutoRepair.models.Appeal;
import ru.akhmetov.AutoRepair.models.Car;

import java.util.List;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Repository
public interface AppealsRepository extends JpaRepository<Appeal, Integer> {
    List<Appeal> getAppealsByCar(Car car);
}

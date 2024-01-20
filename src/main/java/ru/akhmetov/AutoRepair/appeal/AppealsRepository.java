package ru.akhmetov.AutoRepair.appeal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.akhmetov.AutoRepair.car.Car;

import java.util.List;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Repository
public interface AppealsRepository extends JpaRepository<Appeal, Integer> {
    List<Appeal> getAppealsByCar(Car car);
}

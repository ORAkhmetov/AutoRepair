package ru.akhmetov.AutoRepair.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.akhmetov.AutoRepair.models.Car;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Repository
public interface CarsRepository extends JpaRepository<Car, Long> {
}

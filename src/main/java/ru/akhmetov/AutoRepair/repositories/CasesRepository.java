package ru.akhmetov.AutoRepair.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.akhmetov.AutoRepair.models.Car;
import ru.akhmetov.AutoRepair.models.Case;

import java.util.List;
import java.util.Optional;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Repository
public interface CasesRepository extends JpaRepository<Case, Integer> {
    List<Case> getCasesByCar(Car car);
}

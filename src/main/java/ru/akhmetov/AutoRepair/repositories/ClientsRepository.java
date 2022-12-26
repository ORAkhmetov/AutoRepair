package ru.akhmetov.AutoRepair.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.akhmetov.AutoRepair.models.Car;
import ru.akhmetov.AutoRepair.models.Client;

import java.util.List;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Repository
public interface ClientsRepository extends JpaRepository<Client, Integer> {

}

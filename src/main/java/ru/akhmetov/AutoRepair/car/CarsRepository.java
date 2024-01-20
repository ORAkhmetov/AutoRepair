package ru.akhmetov.AutoRepair.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.akhmetov.AutoRepair.client.Client;
import java.util.List;
import java.util.Optional;
/**  Репозиторий Spring Data JPA, предназначенный для работы с сущностью "Car"  **/
@Repository
public interface CarsRepository extends JpaRepository<Car, Integer> {
    List<Car> getCarsByOwner(Client client);//Находит машины клиента
    Optional<Car> getCarByVin(String vin);
    Optional<Car> getCarByStateNumber(String stateNumber);
    List<Car> getCarByModelContainingIgnoreCase (String query);
    List<Car> getCarByStateNumberContainingIgnoreCase (String query);
}

package ru.akhmetov.AutoRepair.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.akhmetov.AutoRepair.appeal.Appeal;

import java.util.List;
/**  Репозиторий Spring Data JPA, предназначенный для работы с сущностью "Order"  **/

@Repository
public interface OrdersRepository extends JpaRepository<Order, Integer> {
    List<Order> getOrdersByAppeal(Appeal appeal);
}

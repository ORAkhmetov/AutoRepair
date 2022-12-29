package ru.akhmetov.AutoRepair.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.akhmetov.AutoRepair.models.Case;
import ru.akhmetov.AutoRepair.models.Order;

import java.util.List;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Repository
public interface OrdersRepository extends JpaRepository<Order, Integer> {
    List<Order> getOrdersByACase(Case aCase);
}

package ru.akhmetov.AutoRepair.services;

import ru.akhmetov.AutoRepair.models.Case;
import ru.akhmetov.AutoRepair.models.Client;
import ru.akhmetov.AutoRepair.models.Order;

import java.util.List;

/**
 * @author Oleg Akhmetov on 27.12.2022
 */
public interface OrdersService {
    List<Order> findAll();
    Order findOne(int id);
    void save(Order order);
    void update(int id, Order updatedOrder) ;
    void delete(int id);
    default void enrichOrder(Order order) {}
}

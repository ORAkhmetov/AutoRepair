package ru.akhmetov.AutoRepair.order;

import ru.akhmetov.AutoRepair.appeal.Appeal;

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
    default void enrichOrder(Order order, Appeal appeal) {
        order.setAppeal(appeal);
    }
}

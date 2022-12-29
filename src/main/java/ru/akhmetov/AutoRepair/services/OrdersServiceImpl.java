package ru.akhmetov.AutoRepair.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akhmetov.AutoRepair.models.Case;
import ru.akhmetov.AutoRepair.models.Order;
import ru.akhmetov.AutoRepair.repositories.OrdersRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Service
@Transactional(readOnly = true) //Все методы readOnly, если не помечены аналогичной аннотацией
public class OrdersServiceImpl implements OrdersService {
    private final OrdersRepository ordersRepository;

    public OrdersServiceImpl(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }
    public List<Order> findAll() {
        return ordersRepository.findAll();
    }
    public Order findOne(int id) {
        Optional<Order> foundedOrder = ordersRepository.findById(id);
        return foundedOrder.orElse(null);
    }
    @Transactional
    public void save(Order order) {
        ordersRepository.save(order);
    }
    @Transactional
    public void update(int id, Order updatedOrder) {
        //Если объекту в методе установить id, то при выполнении метода save, Hibernate поймет, что такой объект уже есть
        // и выполнит update, т к в поле id есть аннотация id
        updatedOrder.setId(id);
        ordersRepository.save(updatedOrder);
    }
    @Transactional
    public void delete(int id) {
        ordersRepository.deleteById(id);
    }

    public List<Order> getOrdersByCase (Case aCase) {
        return ordersRepository.getOrdersByACase(aCase);
    }
}

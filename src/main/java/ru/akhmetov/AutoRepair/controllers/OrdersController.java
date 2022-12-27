package ru.akhmetov.AutoRepair.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.akhmetov.AutoRepair.dto.OrderDTO;
import ru.akhmetov.AutoRepair.models.Order;
import ru.akhmetov.AutoRepair.services.DefaultOrdersService;
import ru.akhmetov.AutoRepair.util.OrderValidator;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Controller
public class OrdersController {

    private final DefaultOrdersService defaultOrdersService;
    private final OrderValidator orderValidator;
    @Autowired
    public OrdersController(DefaultOrdersService defaultOrdersService, OrderValidator orderValidator) {
        this.defaultOrdersService = defaultOrdersService;
        this.orderValidator = orderValidator;
    }

    private Order convertToOrder(OrderDTO orderDTO) {
        Order order = new Order(
                orderDTO.getValue(),
                orderDTO.getOrderType());
        enrichOrder(order);
        return order;
    }
    private void enrichOrder(Order order) {

    }
}

package ru.akhmetov.AutoRepair.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.akhmetov.AutoRepair.models.Order;
import ru.akhmetov.AutoRepair.services.OrdersServiceImpl;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Component
public class OrderValidator implements Validator {

    private final OrdersServiceImpl ordersService;

    @Autowired
    public OrderValidator(OrdersServiceImpl ordersService) {
        this.ordersService = ordersService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Order.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}

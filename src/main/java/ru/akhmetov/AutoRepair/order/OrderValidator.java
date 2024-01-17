package ru.akhmetov.AutoRepair.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
/**  Валидация  **/

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

package ru.akhmetov.AutoRepair.order;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
/**  Валидация  **/

@Component
@AllArgsConstructor
public class OrderValidator implements Validator {

    private final OrdersService ordersService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Order.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}

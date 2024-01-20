package ru.akhmetov.AutoRepair.order;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
/**  Преобразование объектов между сущностью "Order" и  "DTO"  **/
@Component
@RequiredArgsConstructor
public class OrdersMapper {
    private final ModelMapper modelMapper;

    public OrderDTO convertToOrderDTO(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }
    public Order convertToOrder(OrderDTO orderDTO) {
        return modelMapper.map(orderDTO, Order.class);
    }
}

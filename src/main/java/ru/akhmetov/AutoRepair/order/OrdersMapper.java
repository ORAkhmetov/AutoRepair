package ru.akhmetov.AutoRepair.order;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author Oleg Akhmetov on 27.12.2022
 */
@Component
public class OrdersMapper {
    private final ModelMapper modelMapper;

    public OrdersMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public OrderDTO convertToOrderDTO(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }
    public Order convertToOrder(OrderDTO orderDTO) {
        return modelMapper.map(orderDTO, Order.class);
    }
}

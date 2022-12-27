package ru.akhmetov.AutoRepair.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.akhmetov.AutoRepair.dto.OrderDTO;
import ru.akhmetov.AutoRepair.models.Order;

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

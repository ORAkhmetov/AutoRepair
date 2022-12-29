package ru.akhmetov.AutoRepair.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.akhmetov.AutoRepair.dto.OrderDTO;
import ru.akhmetov.AutoRepair.mappers.OrdersMapper;
import ru.akhmetov.AutoRepair.models.Client;
import ru.akhmetov.AutoRepair.models.Order;
import ru.akhmetov.AutoRepair.services.OrdersServiceImpl;
import ru.akhmetov.AutoRepair.util.OrderValidator;

import java.util.stream.Collectors;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Controller
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersServiceImpl ordersServiceImpl;
    private final OrderValidator orderValidator;
    private final OrdersMapper ordersMapper;
    @Autowired
    public OrdersController(OrdersServiceImpl ordersServiceImpl, OrderValidator orderValidator, OrdersMapper ordersMapper) {
        this.ordersServiceImpl = ordersServiceImpl;
        this.orderValidator = orderValidator;
        this.ordersMapper = ordersMapper;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("orders", ordersServiceImpl.findAll().stream()
                .map(ordersMapper::convertToOrderDTO).collect(Collectors.toList()));
        return "orders/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("order", ordersMapper.convertToOrderDTO(ordersServiceImpl.findOne(id)));
        return "orders/show";
    }
    @GetMapping("/new")
    public String newOrder(@ModelAttribute("order") OrderDTO orderDTO) {
        return "orders/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("order") @Valid OrderDTO orderDTO,
                         BindingResult bindingResult) {
        Order order = ordersMapper.convertToOrder(orderDTO);
        orderValidator.validate(order, bindingResult);
        if (bindingResult.hasErrors())
            return "orders/new";

        ordersServiceImpl.save(order);
        return "redirect:/orders";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("order", ordersMapper.convertToOrderDTO(ordersServiceImpl.findOne(id)));
        return "orders/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("order") @Valid OrderDTO orderDTO, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        orderValidator.validate(orderDTO, bindingResult);//Добавил, не было в рыбе
        if (bindingResult.hasErrors())
            return "orders/edit";

        ordersServiceImpl.update(id, ordersMapper.convertToOrder(orderDTO));
        return "redirect:/orders";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        ordersServiceImpl.delete(id);
        return "redirect:/orders";
    }
}

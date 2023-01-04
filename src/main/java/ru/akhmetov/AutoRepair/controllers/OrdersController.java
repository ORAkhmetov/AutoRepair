package ru.akhmetov.AutoRepair.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.akhmetov.AutoRepair.dto.OrderDTO;
import ru.akhmetov.AutoRepair.mappers.OrdersMapper;
import ru.akhmetov.AutoRepair.models.Order;
import ru.akhmetov.AutoRepair.services.AppealsServiceImpl;
import ru.akhmetov.AutoRepair.services.OrdersServiceImpl;
import ru.akhmetov.AutoRepair.util.OrderValidator;

import java.util.stream.Collectors;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Controller
@RequestMapping("/orders")
public class OrdersController {
    private static int appealCreatedOrder;

    private final OrdersServiceImpl ordersServiceImpl;
    private final OrderValidator orderValidator;
    private final OrdersMapper ordersMapper;
    private final AppealsServiceImpl appealsService;
    @Autowired
    public OrdersController(OrdersServiceImpl ordersServiceImpl, OrderValidator orderValidator, OrdersMapper ordersMapper, AppealsServiceImpl appealsService) {
        this.ordersServiceImpl = ordersServiceImpl;
        this.orderValidator = orderValidator;
        this.ordersMapper = ordersMapper;
        this.appealsService = appealsService;
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
    public String newOrder(@ModelAttribute("order") OrderDTO orderDTO,
                           @RequestParam(value = "appeal_id", required = false) String appeal_id) {
        if (appeal_id == null)
            appealCreatedOrder = 0;
        else
            appealCreatedOrder = Integer.parseInt(appeal_id);
        return "orders/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("order") @Valid OrderDTO orderDTO,
                         BindingResult bindingResult) {
        Order order = ordersMapper.convertToOrder(orderDTO);
        orderValidator.validate(order, bindingResult);
        if (bindingResult.hasErrors())
            return "orders/new";
        if (appealCreatedOrder == 0)
            ordersServiceImpl.enrichOrder(order, null);
        else
            ordersServiceImpl.enrichOrder(order, appealsService.findOne(appealCreatedOrder));
        appealCreatedOrder = 0;
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

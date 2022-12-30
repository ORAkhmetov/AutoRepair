package ru.akhmetov.AutoRepair.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.akhmetov.AutoRepair.dto.AppealDTO;
import ru.akhmetov.AutoRepair.mappers.AppealsMapper;
import ru.akhmetov.AutoRepair.mappers.OrdersMapper;
import ru.akhmetov.AutoRepair.models.Appeal;
import ru.akhmetov.AutoRepair.services.AppealsServiceImpl;
import ru.akhmetov.AutoRepair.services.OrdersServiceImpl;
import ru.akhmetov.AutoRepair.util.AppealValidator;

import java.util.stream.Collectors;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Controller
@RequestMapping("/appeals")
public class AppealsController {
    private final AppealsServiceImpl appealsServiceImpl;
    private final AppealValidator appealValidator;
    private final AppealsMapper appealsMapper;

    private final OrdersServiceImpl ordersService;
    private final OrdersMapper ordersMapper;
    @Autowired
    public AppealsController(AppealsServiceImpl appealsServiceImpl, AppealValidator appealValidator, AppealsMapper appealsMapper, OrdersServiceImpl ordersService, OrdersMapper ordersMapper) {
        this.appealsServiceImpl = appealsServiceImpl;
        this.appealValidator = appealValidator;
        this.appealsMapper = appealsMapper;
        this.ordersService = ordersService;
        this.ordersMapper = ordersMapper;
    }
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("appeals", appealsServiceImpl.findAll().stream()
                .map(appealsMapper::convertToAppealDTO).collect(Collectors.toList()));
        return "appeals/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Appeal appeal = appealsServiceImpl.findOne(id);
        model.addAttribute("appeal", appealsMapper.convertToAppealDTO(appeal));
        model.addAttribute("orders", ordersService.getOrdersByAppeal(appeal).stream()
                .map(ordersMapper::convertToOrderDTO).collect(Collectors.toList()));
        return "appeals/show";
    }
    @GetMapping("/new")
    public String newAppeal(@ModelAttribute("appeal") AppealDTO appealDTO) {
        return "appeals/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("appeal") @Valid AppealDTO appealDTO,
                         BindingResult bindingResult) {
        Appeal appeal = appealsMapper.convertToAppeal(appealDTO);
        appealValidator.validate(appeal, bindingResult);
        if (bindingResult.hasErrors())
            return "appeals/new";

        appealsServiceImpl.save(appeal);
        return "redirect:/appeals";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("appeal", appealsMapper.convertToAppealDTO(appealsServiceImpl.findOne(id)));
        return "appeals/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("appeal") @Valid AppealDTO appealDTO, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        Appeal appeal = appealsMapper.convertToAppeal(appealDTO);
        appealValidator.validate(appealDTO, bindingResult);//Добавил, не было в рыбе
        if (bindingResult.hasErrors())
            return "appeals/edit";

        appealsServiceImpl.update(id, appeal);
        return "redirect:/appeals";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        appealsServiceImpl.delete(id);
        return "redirect:/appeals";
    }
}

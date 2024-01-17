package ru.akhmetov.AutoRepair.appeal;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.akhmetov.AutoRepair.order.OrderDTO;
import ru.akhmetov.AutoRepair.order.OrdersMapper;
import ru.akhmetov.AutoRepair.car.CarsServiceImpl;
import ru.akhmetov.AutoRepair.order.OrdersServiceImpl;
import java.util.LinkedList;
import java.util.stream.Collectors;
/**  Контроллер, обрабатывающий запросы, связанные с обращениями клиентов  **/

@Controller
@RequestMapping("/appeals")
@AllArgsConstructor
public class AppealsController {
    private static int carCreatedAppeal;

    private final AppealsService appealsService;
    private final AppealValidator appealValidator;
    private final AppealsMapper appealsMapper;
    private final OrdersServiceImpl ordersService;
    private final OrdersMapper ordersMapper;
    private final CarsServiceImpl carsService;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("appeals", appealsService.findAll().stream()
                .map(appealsMapper::convertToAppealDTO).collect(Collectors.toList()));
        return "appeals/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Appeal appeal = appealsService.findOne(id);
        model.addAttribute("appeal", appealsMapper.convertToAppealDTO(appeal));
        model.addAttribute("orders", ordersService.getOrdersByAppeal(appeal).stream()
                .map(ordersMapper::convertToOrderDTO).collect(Collectors.toList()));
        return "appeals/show";
    }
    @GetMapping("/new")
    public String newAppeal(@ModelAttribute("appeal") AppealDTO appealDTO,
                            @RequestParam(value = "car_id", required = false) String car_id) {
        if (car_id == null)
            carCreatedAppeal = 0;
        else
            carCreatedAppeal = Integer.parseInt(car_id);
        return "appeals/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("appeal") @Valid AppealDTO appealDTO,
                         BindingResult bindingResult) {
        Appeal appeal = appealsMapper.convertToAppeal(appealDTO);
        appealValidator.validate(appeal, bindingResult);
        if (bindingResult.hasErrors())
            return "appeals/new";
        if (carCreatedAppeal == 0)
            appealsService.enrichAppeal(appeal, null);
        else
            appealsService.enrichAppeal(appeal, carsService.findOne(carCreatedAppeal));
        carCreatedAppeal = 0;
        appealsService.save(appeal);
        return "redirect:/appeals";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("appeal", appealsMapper.convertToAppealDTO(appealsService.findOne(id)));
        return "appeals/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("appeal") @Valid AppealDTO appealDTO, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        Appeal appeal = appealsMapper.convertToAppeal(appealDTO);
        appealValidator.validate(appealDTO, bindingResult);//Добавил, не было в рыбе
        if (bindingResult.hasErrors())
            return "appeals/edit";

        appealsService.update(id, appeal);
        return "redirect:/appeals";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        appealsService.delete(id);
        return "redirect:/appeals";
    }

    @GetMapping("/{id}/orders")
    public String showOrders(@PathVariable("id") int id, Model model) {
        Appeal appeal = appealsService.findOne(id);
        model.addAttribute("appeal", appealsMapper.convertToAppealDTO(appeal));
        model.addAttribute("orders", ordersService.getOrdersByAppeal(appeal).stream()
                .map(ordersMapper::convertToOrderDTO).collect(Collectors.toList()));
        return "orders/index";
    }
    @GetMapping("/{id}/photos")
    public String showPhotos(@PathVariable("id") int id, Model model) {
        Appeal appeal = appealsService.findOne(id);
        model.addAttribute("appeal", appealsMapper.convertToAppealDTO(appeal));
        return "appeals/showPhotos";
    }
    @PatchMapping("/{id}/orders")
    public String changeOrders(@ModelAttribute("orders") @Valid LinkedList<OrderDTO> ordersDTOList, BindingResult bindingResult,
                               @PathVariable("id") int id) {
        Appeal appealWithUpdatedOrders = appealsService.findOne(id);
        appealWithUpdatedOrders.setOrderList(ordersDTOList.stream().map(ordersMapper::convertToOrder).collect(Collectors.toList()));
        System.out.println(ordersDTOList.stream().map(ordersMapper::convertToOrder).collect(Collectors.toList()));
        appealsService.update(id, appealWithUpdatedOrders);
        System.out.println("patch");
        return "redirect:/appeals/{id}";
    }
}

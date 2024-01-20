package ru.akhmetov.AutoRepair.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.akhmetov.AutoRepair.appeal.AppealsService;
import ru.akhmetov.AutoRepair.car.CarsMapper;
import ru.akhmetov.AutoRepair.appeal.AppealsMapper;
import ru.akhmetov.AutoRepair.car.CarsService;
import ru.akhmetov.AutoRepair.client.ClientsMapper;
import ru.akhmetov.AutoRepair.client.ClientsService;
import java.util.stream.Collectors;


/** Контроллер стартовой страницы **/
@Controller
@RequiredArgsConstructor
@RequestMapping("/startPage")
public class StartPageController {

    private final ClientsService clientsService;
    private final CarsService carsService;
    private final AppealsService appealsService;
    private final CarsMapper carsMapper;
    private final ClientsMapper clientsMapper;
    private final AppealsMapper appealsMapper;


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("clients", clientsService.findAll().stream()
                .map(clientsMapper::convertToClientDTO).collect(Collectors.toList()));

        model.addAttribute("cars", carsService.findAll().stream()
                .map(carsMapper::convertToCarDTO).collect(Collectors.toList()));

        model.addAttribute("appeals", appealsService.findAll().stream()
                .map(appealsMapper::convertToAppealDTO).collect(Collectors.toList()));
        return "startPage";
    }
}

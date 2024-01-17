package ru.akhmetov.AutoRepair.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.akhmetov.AutoRepair.car.CarsMapper;
import ru.akhmetov.AutoRepair.appeal.AppealsMapper;
import ru.akhmetov.AutoRepair.client.ClientsMapper;
import ru.akhmetov.AutoRepair.car.CarsServiceImpl;
import ru.akhmetov.AutoRepair.appeal.AppealsServiceImpl;
import ru.akhmetov.AutoRepair.client.ClientsServiceImpl;
import java.util.stream.Collectors;


/** Контроллер стартовой страницы **/
@Controller
@AllArgsConstructor
@RequestMapping("/startPage")
public class StartPageController {

    private final ClientsServiceImpl clientsServiceImpl;
    private final CarsServiceImpl carsServiceImpl;
    private final AppealsServiceImpl appealsServiceImpl;
    private final CarsMapper carsMapper;
    private final ClientsMapper clientsMapper;
    private final AppealsMapper appealsMapper;


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("clients", clientsServiceImpl.findAll().stream()
                .map(clientsMapper::convertToClientDTO).collect(Collectors.toList()));

        model.addAttribute("cars", carsServiceImpl.findAll().stream()
                .map(carsMapper::convertToCarDTO).collect(Collectors.toList()));

        model.addAttribute("appeals", appealsServiceImpl.findAll().stream()
                .map(appealsMapper::convertToAppealDTO).collect(Collectors.toList()));
        return "startPage";
    }
}

package ru.akhmetov.AutoRepair.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.akhmetov.AutoRepair.mappers.CarsMapper;
import ru.akhmetov.AutoRepair.mappers.AppealsMapper;
import ru.akhmetov.AutoRepair.mappers.ClientsMapper;
import ru.akhmetov.AutoRepair.services.CarsServiceImpl;
import ru.akhmetov.AutoRepair.services.AppealsServiceImpl;
import ru.akhmetov.AutoRepair.services.ClientsServiceImpl;

import java.util.stream.Collectors;

/**
 * @author Oleg Akhmetov on 29.12.2022
 */
@Controller
@RequestMapping("/startPage")
public class StartPageController {

    private final ClientsServiceImpl clientsServiceImpl;
    private final CarsServiceImpl carsServiceImpl;
    private final AppealsServiceImpl appealsServiceImpl;
    private final CarsMapper carsMapper;
    private final ClientsMapper clientsMapper;
    private final AppealsMapper appealsMapper;

    public StartPageController(ClientsServiceImpl clientsServiceImpl, CarsServiceImpl carsServiceImpl,
                               AppealsServiceImpl appealsServiceImpl, CarsMapper carsMapper, ClientsMapper clientsMapper,
                               AppealsMapper appealsMapper) {
        this.clientsServiceImpl = clientsServiceImpl;
        this.carsServiceImpl = carsServiceImpl;
        this.appealsServiceImpl = appealsServiceImpl;
        this.carsMapper = carsMapper;
        this.clientsMapper = clientsMapper;
        this.appealsMapper = appealsMapper;
    }

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

package ru.akhmetov.AutoRepair.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.akhmetov.AutoRepair.mappers.CarsMapper;
import ru.akhmetov.AutoRepair.mappers.CasesMapper;
import ru.akhmetov.AutoRepair.mappers.ClientsMapper;
import ru.akhmetov.AutoRepair.services.CarsServiceImpl;
import ru.akhmetov.AutoRepair.services.CasesServiceImpl;
import ru.akhmetov.AutoRepair.services.ClientsServiceImpl;
import ru.akhmetov.AutoRepair.util.ClientValidator;

import java.util.stream.Collectors;

/**
 * @author Oleg Akhmetov on 29.12.2022
 */
@Controller
@RequestMapping("/startPage")
public class StartPageController {

    private final ClientsServiceImpl clientsServiceImpl;
    private final CarsServiceImpl carsServiceImpl;
    private final CasesServiceImpl casesServiceImpl;
    private final CarsMapper carsMapper;
    private final ClientsMapper clientsMapper;
    private final CasesMapper casesMapper;

    public StartPageController(ClientsServiceImpl clientsServiceImpl, CarsServiceImpl carsServiceImpl,
                               CasesServiceImpl casesServiceImpl, CarsMapper carsMapper, ClientsMapper clientsMapper,
                               CasesMapper casesMapper) {
        this.clientsServiceImpl = clientsServiceImpl;
        this.carsServiceImpl = carsServiceImpl;
        this.casesServiceImpl = casesServiceImpl;
        this.carsMapper = carsMapper;
        this.clientsMapper = clientsMapper;
        this.casesMapper = casesMapper;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("clients", clientsServiceImpl.findAll().stream()
                .map(clientsMapper::convertToClientDTO).collect(Collectors.toList()));

        model.addAttribute("cars", carsServiceImpl.findAll().stream()
                .map(carsMapper::convertToCarDTO).collect(Collectors.toList()));

        model.addAttribute("cases", casesServiceImpl.findAll().stream()
                .map(casesMapper::convertToCaseDTO).collect(Collectors.toList()));
        return "startPage";
    }
}

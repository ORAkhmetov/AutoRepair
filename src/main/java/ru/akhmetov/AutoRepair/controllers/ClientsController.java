package ru.akhmetov.AutoRepair.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.akhmetov.AutoRepair.dto.ClientDTO;
import ru.akhmetov.AutoRepair.models.Car;
import ru.akhmetov.AutoRepair.models.Client;
import ru.akhmetov.AutoRepair.services.CarsService;
import ru.akhmetov.AutoRepair.services.CasesService;
import ru.akhmetov.AutoRepair.services.ClientsService;
import ru.akhmetov.AutoRepair.util.ClientValidator;

import java.util.List;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Controller
@RequestMapping("/clients")
public class ClientsController {
    private final ClientsService clientsService;
    private final CarsService carsService;
    private final CasesService casesService;
    private final ClientValidator clientValidator;

    @Autowired
    public ClientsController(ClientsService clientsService, CarsService carsService, CasesService casesService, ClientValidator clientValidator) {
        this.clientsService = clientsService;
        this.carsService = carsService;
        this.casesService = casesService;
        this.clientValidator = clientValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("clients", clientsService.findAll());
        return "clients/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Client client = clientsService.findOne(id);
        model.addAttribute("client", client);
        model.addAttribute("cars", carsService.getCarsByClient(client));
        model.addAttribute("cases", casesService.getCasesByClient(client));
        return "clients/show";
    }
    @GetMapping("/new")
    public String newClient(@ModelAttribute("client") Client client) {
        return "clients/new";
    }


    private Client convertToClient(ClientDTO clientDTO) {
        Client client = new Client(clientDTO.getFullName());
        enrichClient(client);
        return client;
    }

    private void enrichClient(Client client) {

    }

}

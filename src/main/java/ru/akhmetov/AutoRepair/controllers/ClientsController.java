package ru.akhmetov.AutoRepair.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.akhmetov.AutoRepair.dto.ClientDTO;
import ru.akhmetov.AutoRepair.mappers.CarsMapper;
import ru.akhmetov.AutoRepair.mappers.CasesMapper;
import ru.akhmetov.AutoRepair.mappers.ClientsMapper;
import ru.akhmetov.AutoRepair.models.Client;
import ru.akhmetov.AutoRepair.services.DefaultCarsService;
import ru.akhmetov.AutoRepair.services.DefaultCasesService;
import ru.akhmetov.AutoRepair.services.DefaultClientsService;
import ru.akhmetov.AutoRepair.util.ClientValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Controller
@RequestMapping("/clients")
public class ClientsController {
    private final DefaultClientsService defaultClientsService;
    private final DefaultCarsService defaultCarsService;
    private final DefaultCasesService defaultCasesService;
    private final ClientValidator clientValidator;
    private final ModelMapper modelMapper;
    private final CarsMapper carsMapper;
    private final ClientsMapper clientsMapper;
    private final CasesMapper casesMapper;

    @Autowired
    public ClientsController(DefaultClientsService defaultClientsService, DefaultCarsService defaultCarsService, DefaultCasesService defaultCasesService, ClientValidator clientValidator, ModelMapper modelMapper, CarsMapper carsMapper, ClientsMapper clientsMapper, CasesMapper casesMapper) {
        this.defaultClientsService = defaultClientsService;
        this.defaultCarsService = defaultCarsService;
        this.defaultCasesService = defaultCasesService;
        this.clientValidator = clientValidator;
        this.modelMapper = modelMapper;
        this.carsMapper = carsMapper;
        this.clientsMapper = clientsMapper;
        this.casesMapper = casesMapper;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("clients", defaultClientsService.findAll());
        return "clients/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Client client = defaultClientsService.findOne(id);
        model.addAttribute("client", clientsMapper.convertToClientDTO(client));

        model.addAttribute("cars", defaultCarsService.getCarsByClient(client).stream()
                .map(carsMapper::convertToCarDTO).collect(Collectors.toList()));

        model.addAttribute("cases", defaultCasesService.getCasesByClient(client).stream()
                .map(casesMapper::convertToCaseDTO).collect(Collectors.toList()));

        return "clients/show";
    }
    @GetMapping("/new")
    public String newClient(@ModelAttribute("client") ClientDTO clientDTO) {
        return "clients/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("client") @Valid ClientDTO clientDTO,
                         BindingResult bindingResult) {
        clientValidator.validate(clientDTO, bindingResult);
        if (bindingResult.hasErrors())
            return "clients/new";

        defaultClientsService.save(new Client(clientDTO));
        return "redirect:/clients";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("client", defaultClientsService.findOne(id));
        return "clients/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("client") @Valid Client client, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        clientValidator.validate(client, bindingResult);//Добавил, не было в рыбе
        if (bindingResult.hasErrors())
            return "clients/edit";

        defaultClientsService.update(id, client);
        return "redirect:/clients";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        defaultClientsService.delete(id);
        return "redirect:/clients";
    }


    private Client convertToClient(ClientDTO clientDTO) {
        return modelMapper.map(clientDTO, Client.class);
    }

    private ClientDTO convertToClientDTO(Client client) {
        return modelMapper.map(client, ClientDTO.class);
    }
    private List<ClientDTO> convertListToClientDTO(List<Client> clientList) {
        List<ClientDTO> convertedList = new ArrayList<>();
        for (Client client : clientList) {
            convertedList.add(modelMapper.map(client, ClientDTO.class));
        }
        return convertedList;
    }

}

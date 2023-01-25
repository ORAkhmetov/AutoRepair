package ru.akhmetov.AutoRepair.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.akhmetov.AutoRepair.dto.ClientDTO;
import ru.akhmetov.AutoRepair.mappers.CarsMapper;
import ru.akhmetov.AutoRepair.mappers.AppealsMapper;
import ru.akhmetov.AutoRepair.mappers.ClientsMapper;
import ru.akhmetov.AutoRepair.models.Client;
import ru.akhmetov.AutoRepair.services.CarsServiceImpl;
import ru.akhmetov.AutoRepair.services.AppealsServiceImpl;
import ru.akhmetov.AutoRepair.services.ClientsServiceImpl;
import ru.akhmetov.AutoRepair.util.ClientValidator;

import java.util.stream.Collectors;


/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Controller
@RequestMapping("/clients")
public class ClientsController {

    private final ClientsServiceImpl clientsServiceImpl;
    private final CarsServiceImpl carsServiceImpl;
    private final AppealsServiceImpl appealsServiceImpl;
    private final ClientValidator clientValidator;
    private final CarsMapper carsMapper;
    private final ClientsMapper clientsMapper;
    private final AppealsMapper appealsMapper;

    @Autowired
    public ClientsController(ClientsServiceImpl clientsServiceImpl, CarsServiceImpl carsServiceImpl,
                             AppealsServiceImpl appealsServiceImpl, ClientValidator clientValidator, CarsMapper carsMapper,
                             ClientsMapper clientsMapper, AppealsMapper appealsMapper) {
        this.clientsServiceImpl = clientsServiceImpl;
        this.carsServiceImpl = carsServiceImpl;
        this.appealsServiceImpl = appealsServiceImpl;
        this.clientValidator = clientValidator;
        this.carsMapper = carsMapper;
        this.clientsMapper = clientsMapper;
        this.appealsMapper = appealsMapper;
    }

    @GetMapping()
    public String index(Model model, @RequestParam(value = "page", required = false) Integer page) {

        model.addAttribute("clients", clientsServiceImpl.findWithPagination(page).stream()
                .map(clientsMapper::convertToClientDTO).collect(Collectors.toList()));
        return "clients/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Client client = clientsServiceImpl.findOne(id);
        model.addAttribute("client", clientsMapper.convertToClientDTO(client));

        model.addAttribute("cars", carsServiceImpl.getCarsByClient(client).stream()
                .map(carsMapper::convertToCarDTO).collect(Collectors.toList()));

        model.addAttribute("appeal", appealsServiceImpl.getAppealsByClient(client).stream()
                .map(appealsMapper::convertToAppealDTO).collect(Collectors.toList()));

        return "clients/show";
    }
    @GetMapping("/new")
    public String newClient(@ModelAttribute("client") ClientDTO clientDTO) {
        return "clients/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("client") @Valid ClientDTO clientDTO,
                         BindingResult bindingResult) {
        Client client = clientsMapper.convertToClient(clientDTO);
        clientValidator.validate(client, bindingResult);
        if (bindingResult.hasErrors())
            return "clients/new";

        clientsServiceImpl.save(client);
        return "redirect:/clients?page=0";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("client", clientsMapper.convertToClientDTO(clientsServiceImpl.findOne(id)));
        return "clients/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("client") @Valid ClientDTO clientDTO, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        Client client = clientsMapper.convertToClient(clientDTO);
        clientValidator.validate(client, bindingResult);//Добавил, не было в рыбе
        if (bindingResult.hasErrors())
            return "clients/edit";

        clientsServiceImpl.update(id, client);
        return "redirect:/clients";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        clientsServiceImpl.delete(id);
        return "redirect:/clients";
    }
    @GetMapping("/search")
    public String searchPage() {
        return "clients/search";
    }

    @PostMapping("/search")
    public String search(Model model, @RequestParam("searchQuery") String searchQuery) {
        System.out.println(searchQuery);
        model.addAttribute("foundedClients", clientsServiceImpl.getClientsByFullNameContainingIgnoreCase(searchQuery));
        return "clients/search";
    }
}

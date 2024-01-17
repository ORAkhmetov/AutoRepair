package ru.akhmetov.AutoRepair.client;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.akhmetov.AutoRepair.car.CarsMapper;
import ru.akhmetov.AutoRepair.appeal.AppealsMapper;
import ru.akhmetov.AutoRepair.appeal.AppealsServiceImpl;
import ru.akhmetov.AutoRepair.car.CarsService;

import java.util.stream.Collectors;
/**  Контроллер, обрабатывающий запросы, связанные с клиентами  **/

//Изменение 1
@Controller
@RequestMapping("/clients")
@AllArgsConstructor

public class ClientsController {

    private final ClientsService clientsService;
    private final CarsService carsService;
    private final AppealsServiceImpl appealsServiceImpl;
    private final ClientValidator clientValidator;
    private final CarsMapper carsMapper;
    private final ClientsMapper clientsMapper;
    private final AppealsMapper appealsMapper;

    @GetMapping()
    public String index(Model model, @RequestParam(value = "page", required = false) Integer page) {
        if (page == null) {
            model.addAttribute("clients", clientsService.findAll().stream()
                    .map(clientsMapper::convertToClientDTO).collect(Collectors.toList()));
            return "clients/index";
        } else {
            model.addAttribute("clients", clientsService.findWithPagination(page).stream()
                    .map(clientsMapper::convertToClientDTO).collect(Collectors.toList()));
            return "clients/index";
        }
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Client client = clientsService.findOne(id);
        model.addAttribute("client", clientsMapper.convertToClientDTO(client));

        model.addAttribute("cars", carsService.getCarsByClient(client).stream()
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

        clientsService.save(client);
        return "redirect:/clients?page=0";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("client", clientsMapper.convertToClientDTO(clientsService.findOne(id)));
        return "clients/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("client") @Valid ClientDTO clientDTO, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        Client client = clientsMapper.convertToClient(clientDTO);
        clientValidator.validate(client, bindingResult);//Добавил, не было в рыбе
        if (bindingResult.hasErrors())
            return "clients/edit";

        clientsService.update(id, client);
        return "redirect:/clients?page=0";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        clientsService.delete(id);
        return "redirect:/clients";
    }
    @GetMapping("/search")
    public String searchPage() {
        return "clients/search";
    }

    @PostMapping("/search")
    public String search(Model model, @RequestParam("searchQuery") String searchQuery) {
        System.out.println(searchQuery);
        model.addAttribute("foundedClients", clientsService.getClientsByFullName(searchQuery));
        return "clients/search";
    }
}

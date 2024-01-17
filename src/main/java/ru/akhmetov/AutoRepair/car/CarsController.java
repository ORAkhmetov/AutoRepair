package ru.akhmetov.AutoRepair.car;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.akhmetov.AutoRepair.appeal.AppealsMapper;
import ru.akhmetov.AutoRepair.appeal.AppealsServiceImpl;
import ru.akhmetov.AutoRepair.client.ClientsServiceImpl;
import java.io.IOException;
import java.util.stream.Collectors;
/**  Контроллер, обрабатывающий запросы, связанные с машинами  **/

@AllArgsConstructor
@RequestMapping("/cars")
@Controller
public class CarsController {

    private static int ownerCreatedCar;

    private final CarsService carsService;
    private final CarValidator carValidator;
    private final CarsMapper carsMapper;
    private final AppealsMapper appealsMapper;
    private final AppealsServiceImpl appealsService;
    private final ClientsServiceImpl clientsService;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("cars", carsService.findAll().stream()
                .map(carsMapper::convertToCarDTO).collect(Collectors.toList()));
        return "cars/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Car car = carsService.findOne(id);
        model.addAttribute("car", carsMapper.convertToCarDTO(car));
        model.addAttribute("appeals", appealsService.getAppealsByCar(car).stream()
                .map(appealsMapper::convertToAppealDTO).collect(Collectors.toList()));

        return "cars/show";
    }
    @GetMapping("/new")
    public String newCar(@ModelAttribute("car") CarDTO carDTO,
                         @RequestParam(value = "owner_id", required = false) String owner_id) {
        if (owner_id == null)
            ownerCreatedCar = 0;
        else
            ownerCreatedCar = Integer.parseInt(owner_id);
        return "cars/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("car") @Valid CarDTO carDTO,
                         BindingResult bindingResult) throws IOException {
        Car car = carsMapper.convertToCar(carDTO);
        carValidator.validate(car, bindingResult);
        if (bindingResult.hasErrors())
            return "cars/new";
        if (ownerCreatedCar == 0)
            carsService.enrichCar(car, null);
        else
            carsService.enrichCar(car, clientsService.findOne(ownerCreatedCar));
        ownerCreatedCar = 0;
        carsService.save(car);
        return "redirect:/cars";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("car", carsMapper.convertToCarDTO(carsService.findOne(id)));
        return "cars/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("car") @Valid CarDTO carDTO, BindingResult bindingResult,
                         @PathVariable("id") int id) throws IOException {
        Car car = carsMapper.convertToCar(carDTO);
        carValidator.validate(car, bindingResult);//Добавил, не было в рыбе
        if (bindingResult.hasErrors())
            return "cars/edit";

        carsService.update(id, car);
        return "redirect:/cars";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        carsService.delete(id);
        return "redirect:/cars";
    }

    @GetMapping("/searchForModel")
    public String searchForModelPage() {
        return "cars/searchForModel";
    }

    @PostMapping("/searchForModel")
    public String searchForModel(Model model, @RequestParam("searchQuery") String searchQuery) {
        System.out.println(searchQuery);
        model.addAttribute("foundedCars", carsService.getCarsByModel(searchQuery));
        return "cars/searchForModel";
    }
    @GetMapping("/searchForStateNumber")
    public String searchForStateNumberPage() {
        return "cars/searchForStateNumber";
    }

    @PostMapping("/searchForStateNumber")
    public String searchForStateNumber(Model model, @RequestParam("searchQuery") String searchQuery) {
        System.out.println(searchQuery);
        model.addAttribute("foundedCars", carsService.getCarsByStateNumber(searchQuery));
        return "cars/searchForStateNumber";
    }

}

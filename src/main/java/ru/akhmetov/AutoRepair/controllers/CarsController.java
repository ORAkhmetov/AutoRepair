package ru.akhmetov.AutoRepair.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.akhmetov.AutoRepair.dto.CarDTO;
import ru.akhmetov.AutoRepair.dto.ClientDTO;
import ru.akhmetov.AutoRepair.mappers.CarsMapper;
import ru.akhmetov.AutoRepair.models.Client;
import ru.akhmetov.AutoRepair.services.CarsServiceImpl;
import ru.akhmetov.AutoRepair.util.CarValidator;

import java.util.stream.Collectors;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Controller
@RequestMapping("/cars")
public class CarsController {
    private final CarsServiceImpl carsServiceImpl;
    private final CarValidator carValidator;
    private final CarsMapper carsMapper;

    @Autowired
    public CarsController(CarsServiceImpl carsServiceImpl, CarValidator carValidator, CarsMapper carsMapper) {
        this.carsServiceImpl = carsServiceImpl;
        this.carValidator = carValidator;
        this.carsMapper = carsMapper;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("cars", carsServiceImpl.findAll().stream()
                .map(carsMapper::convertToCarDTO).collect(Collectors.toList()));
        return "cars/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
                model.addAttribute("car", carsMapper.convertToCarDTO(carsServiceImpl.findOne(id)));

        return "cars/show";
    }
    @GetMapping("/new")
    public String newCar(@ModelAttribute("car") ClientDTO clientDTO) {
        return "cars/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("car") @Valid CarDTO carDTO,
                         BindingResult bindingResult) {
        carValidator.validate(carDTO, bindingResult);
        if (bindingResult.hasErrors())
            return "cars/new";

        carsServiceImpl.save(carsMapper.convertToCar(carDTO));
        return "redirect:/cars";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("car", carsMapper.convertToCarDTO(carsServiceImpl.findOne(id)));
        return "cars/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("car") @Valid CarDTO carDTO, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        carValidator.validate(carDTO, bindingResult);//Добавил, не было в рыбе
        if (bindingResult.hasErrors())
            return "cars/edit";

        carsServiceImpl.update(id, carsMapper.convertToCar(carDTO));
        return "redirect:/cars";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        carsServiceImpl.delete(id);
        return "redirect:/cars";
    }

}

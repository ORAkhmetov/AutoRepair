package ru.akhmetov.AutoRepair.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.akhmetov.AutoRepair.dto.CarDTO;
import ru.akhmetov.AutoRepair.mappers.CarsMapper;
import ru.akhmetov.AutoRepair.mappers.AppealsMapper;
import ru.akhmetov.AutoRepair.models.Car;
import ru.akhmetov.AutoRepair.services.CarsServiceImpl;
import ru.akhmetov.AutoRepair.services.AppealsServiceImpl;
import ru.akhmetov.AutoRepair.services.ClientsServiceImpl;
import ru.akhmetov.AutoRepair.util.CarValidator;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.FileHandler;
import java.util.stream.Collectors;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Controller
@RequestMapping("/cars")
public class CarsController {

    @Value("${upload.path}")
    private String uploadPath;

    private static int ownerCreatedCar;
    private final CarsServiceImpl carsServiceImpl;
    private final CarValidator carValidator;
    private final CarsMapper carsMapper;
    private final AppealsMapper appealsMapper;
    private final AppealsServiceImpl appealsService;
    private final ClientsServiceImpl clientsService;

    @Autowired
    public CarsController(CarsServiceImpl carsServiceImpl, CarValidator carValidator, CarsMapper carsMapper, AppealsMapper appealsMapper, AppealsServiceImpl appealsService, ClientsServiceImpl clientsService) {
        this.carsServiceImpl = carsServiceImpl;
        this.carValidator = carValidator;
        this.carsMapper = carsMapper;
        this.appealsMapper = appealsMapper;
        this.appealsService = appealsService;
        this.clientsService = clientsService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("cars", carsServiceImpl.findAll().stream()
                .map(carsMapper::convertToCarDTO).collect(Collectors.toList()));
        return "cars/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Car car = carsServiceImpl.findOne(id);
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
            carsServiceImpl.enrichCar(car, null);
        else
            carsServiceImpl.enrichCar(car, clientsService.findOne(ownerCreatedCar));
        ownerCreatedCar = 0;
        carsServiceImpl.save(car);
        return "redirect:/cars";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("car", carsMapper.convertToCarDTO(carsServiceImpl.findOne(id)));
        return "cars/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("car") @Valid CarDTO carDTO, BindingResult bindingResult,
                         @RequestParam(value = "filename", required = false) MultipartFile file,
                         @PathVariable("id") int id) throws IOException {
        Car car = carsMapper.convertToCar(carDTO);
        carValidator.validate(car, bindingResult);//Добавил, не было в рыбе
        if (bindingResult.hasErrors())
            return "cars/edit";
        if (file != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(resultFileName));
            car.setFilename(String.valueOf(file));
        }
        carsServiceImpl.update(id, car);
        return "redirect:/cars";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        carsServiceImpl.delete(id);
        return "redirect:/cars";
    }

}

package ru.akhmetov.AutoRepair.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.akhmetov.AutoRepair.dto.CarDTO;
import ru.akhmetov.AutoRepair.models.Car;
import ru.akhmetov.AutoRepair.services.DefaultCarsService;
import ru.akhmetov.AutoRepair.util.CarValidator;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Controller
public class CarsController {
    private final DefaultCarsService defaultCarsService;
    private final CarValidator carValidator;

    @Autowired
    public CarsController(DefaultCarsService defaultCarsService, CarValidator carValidator) {
        this.defaultCarsService = defaultCarsService;
        this.carValidator = carValidator;
    }

}

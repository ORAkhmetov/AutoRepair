package ru.akhmetov.AutoRepair.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.akhmetov.AutoRepair.dto.CarDTO;
import ru.akhmetov.AutoRepair.models.Car;
import ru.akhmetov.AutoRepair.services.CarsService;
import ru.akhmetov.AutoRepair.util.CarValidator;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Controller
public class CarsController {
    private final CarsService carsService;
    private final CarValidator carValidator;

    @Autowired
    public CarsController(CarsService carsService, CarValidator carValidator) {
        this.carsService = carsService;
        this.carValidator = carValidator;
    }
    private Car covertToCar(CarDTO carDTO) {
        Car car = new Car(
                carDTO.getModel(),
                carDTO.getStateNumber(),
                carDTO.getVin(),
                carDTO.getOwner());
        enrichCar(car);
        return car;
    }
    private void enrichCar(Car car) {

    }
}

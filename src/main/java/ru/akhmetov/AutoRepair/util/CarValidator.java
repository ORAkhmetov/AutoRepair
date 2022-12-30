package ru.akhmetov.AutoRepair.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.akhmetov.AutoRepair.models.Car;
import ru.akhmetov.AutoRepair.services.CarsServiceImpl;

import java.util.Optional;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Component
public class CarValidator implements Validator {

    private final CarsServiceImpl carsService;

    @Autowired
    public CarValidator(CarsServiceImpl carsService) {
        this.carsService = carsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Car.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Car validCar = (Car) target;
        Optional<Car> foundedCarByVin = carsService.getCarByVin(validCar.getVin());
        Optional<Car> foundedCarByStateNumber = carsService.getCarByStateNumber(validCar.getStateNumber());

        if (foundedCarByVin.isPresent()) {
            if (foundedCarByVin.get().getId() != validCar.getId())
                errors.rejectValue("vin", "", "Машина с таким VIN уже существует");
        }
        if (foundedCarByStateNumber.isPresent()) {
            if (foundedCarByStateNumber.get().getId() != validCar.getId())
                errors.rejectValue("stateNumber", "", "Машина с таким госномером уже существует");

        }
    }
}

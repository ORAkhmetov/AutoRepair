package ru.akhmetov.AutoRepair.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.akhmetov.AutoRepair.models.Car;
import ru.akhmetov.AutoRepair.services.CarsServiceImpl;

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
        Car car = (Car) target;

        if (carsService.getCarByVin(car.getVin()).isPresent())
            errors.rejectValue("vin", "", "Машина с таким VIN уже существует");
        if (carsService.getCarByStateNumber(car.getStateNumber()).isPresent())
            errors.rejectValue("stateNumber", "", "Машина с таким госномером уже существует");
    }
}

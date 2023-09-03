package ru.akhmetov.AutoRepair.car;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Oleg Akhmetov on 27.12.2022
 */
@Component
public class CarsMapper {
    private final ModelMapper modelMapper;
    @Autowired
    public CarsMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public Car convertToCar(CarDTO carDTO) {
        return modelMapper.map(carDTO, Car.class);
    }
    public CarDTO convertToCarDTO(Car car) {
        return modelMapper.map(car, CarDTO.class);
    }

}

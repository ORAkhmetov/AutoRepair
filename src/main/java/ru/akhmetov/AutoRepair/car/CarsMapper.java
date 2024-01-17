package ru.akhmetov.AutoRepair.car;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
/**  Преобразование объектов между сущностью "Car" и  "DTO"  **/

@Component
@AllArgsConstructor
public class CarsMapper {
    private final ModelMapper modelMapper;

    public Car convertToCar(CarDTO carDTO) {
        return modelMapper.map(carDTO, Car.class);
    }
    public CarDTO convertToCarDTO(Car car) {
        return modelMapper.map(car, CarDTO.class);
    }

}

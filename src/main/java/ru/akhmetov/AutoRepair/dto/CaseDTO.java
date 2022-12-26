package ru.akhmetov.AutoRepair.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import ru.akhmetov.AutoRepair.models.Car;

import java.util.Date;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
public class CaseDTO {

    private Car car;

    @Min(value = 0, message = "Пробег не должн быть меньше нуля")
    private int mileage;

    private Date dateOfCase;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public Date getDateOfCase() {
        return dateOfCase;
    }

    public void setDateOfCase(Date dateOfCase) {
        this.dateOfCase = dateOfCase;
    }
}

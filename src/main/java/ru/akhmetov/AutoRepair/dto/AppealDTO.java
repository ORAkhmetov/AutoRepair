package ru.akhmetov.AutoRepair.dto;

import jakarta.validation.constraints.Min;
import ru.akhmetov.AutoRepair.models.Car;
import ru.akhmetov.AutoRepair.models.Order;
import ru.akhmetov.AutoRepair.models.PhotoAppeal;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
public class AppealDTO {

    private int id;

    private String name;

    private String faultDescription;

    private Car car;

    @Min(value = 0, message = "Пробег не должен быть меньше нуля")
    private int mileage;

    private LocalDate dateOfAppeal;

    private List<Order> orderList;

    private List<PhotoAppeal> photoAppealList;

    public AppealDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public LocalDate getDateOfAppeal() {
        return dateOfAppeal;
    }

    public void setDateOfAppeal(LocalDate dateOfAppeal) {
        this.dateOfAppeal = dateOfAppeal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFaultDescription() {
        return faultDescription;
    }

    public void setFaultDescription(String faultDescription) {
        this.faultDescription = faultDescription;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public List<PhotoAppeal> getPhotoAppealList() {
        return photoAppealList;
    }

    public void setPhotoAppealList(List<PhotoAppeal> photoAppealList) {
        this.photoAppealList = photoAppealList;
    }
}

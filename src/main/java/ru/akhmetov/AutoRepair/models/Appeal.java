package ru.akhmetov.AutoRepair.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Entity
@Table(name = "appeal")
public class Appeal {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "fault_description")
    private String faultDescription;

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;

    @Column(name = "mileage")
    @Min(value = 0, message = "Пробег не должн быть меньше нуля")
    private int mileage;

    @Column(name = "date_of_appeal")
    private LocalDate dateOfAppeal;

    @OneToMany(mappedBy = "appeal")
    private List<Order> orderList;

    @OneToMany(mappedBy = "appeal")
    private List<PhotoAppeal> photoAppealList;

    public Appeal(Car car, int mileage, LocalDate dateOfAppeal) {
        this.car = car;
        this.mileage = mileage;
        this.dateOfAppeal = dateOfAppeal;
        this.orderList = new ArrayList<>();
    }

    public Appeal() {

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

    public void setDateOfAppeal(LocalDate dateOfCase) {
        this.dateOfAppeal = dateOfCase;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
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

    public List<PhotoAppeal> getPhotoAppealList() {
        return photoAppealList;
    }

    public void setPhotoAppealList(List<PhotoAppeal> photoAppealList) {
        this.photoAppealList = photoAppealList;
    }
}

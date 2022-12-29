package ru.akhmetov.AutoRepair.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Entity
@Table(name = "acase")
public class Case {

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

    @Column(name = "date_of_case")
    private Date dateOfCase;

    @OneToMany(mappedBy = "aCase")
    private List<Order> orderList;

    public Case(Car car, int mileage, Date dateOfCase) {
        this.car = car;
        this.mileage = mileage;
        this.dateOfCase = dateOfCase;
        this.orderList = new ArrayList<>();
    }

    public Case() {

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

    public Date getDateOfCase() {
        return dateOfCase;
    }

    public void setDateOfCase(Date dateOfCase) {
        this.dateOfCase = dateOfCase;
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
}

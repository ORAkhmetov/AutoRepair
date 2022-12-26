package ru.akhmetov.AutoRepair.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Entity
@Table(name = "case")
public class Case {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;

    @Column(name = "mileage")
    private int mileage;

    @Column(name = "date_of_case")
    private Date dateOfCase;

    @OneToMany(mappedBy = "case_id")
    private List<Order> orderList;

    public Case(Car car, int mileage, Date dateOfCase) {
        this.car = car;
        this.mileage = mileage;
        this.dateOfCase = dateOfCase;
        this.orderList = new ArrayList<>();
    }

    public Case() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
}

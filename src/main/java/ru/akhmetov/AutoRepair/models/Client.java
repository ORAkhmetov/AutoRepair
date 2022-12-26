package ru.akhmetov.AutoRepair.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Entity
@Table(name = "client")
public class Client {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_name")
    @NotEmpty(message = "Имя не должно быть пустым")
    private String fullName;

    @OneToMany(mappedBy = "owner")
    private List<Car> carList;

    public Client(String fullName) {
        this.fullName = fullName;
        this.carList = new ArrayList<>();
    }

    public Client() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Car> getCarList() {
        return carList;
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }
}

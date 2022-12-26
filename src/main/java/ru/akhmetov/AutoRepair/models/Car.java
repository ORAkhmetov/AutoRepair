package ru.akhmetov.AutoRepair.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Entity
@Table(name = "car")
public class Car {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "model")
    private String model;

    @Column(name = "state_number")
    private String stateNumber;

    @Column(name = "vin")
    private String vin;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Client owner;

    @OneToMany(mappedBy = "car_id")
    private List<Case> caseList;

    public Car(String model, String stateNumber, String vin, Client owner) {
        this.model = model;
        this.stateNumber = stateNumber;
        this.vin = vin;
        this.owner = owner;
        this.caseList = new ArrayList<>();
    }

    public Car() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStateNumber() {
        return stateNumber;
    }

    public void setStateNumber(String stateNumber) {
        this.stateNumber = stateNumber;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public List<Case> getCaseList() {
        return caseList;
    }

    public void setCaseList(List<Case> caseList) {
        this.caseList = caseList;
    }
}

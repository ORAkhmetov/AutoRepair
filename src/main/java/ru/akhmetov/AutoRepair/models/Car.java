package ru.akhmetov.AutoRepair.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.akhmetov.AutoRepair.dto.CarDTO;

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
    @NotEmpty(message = "Модель не должна быть пустой")
    private String model;

    @Column(name = "state_number")
    @NotEmpty(message = "Госномер не должен быть пустым")
    private String stateNumber;

    @Column(name = "vin")
    @NotEmpty(message = "VIN не должен быть пустым")
    private String vin;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client owner;

    @OneToMany(mappedBy = "car")
    private List<Case> caseList;

    public Car(CarDTO carDTO) {
        this.model = carDTO.getModel();
        this.stateNumber = carDTO.getStateNumber();
        this.vin = carDTO.getVin();
        this.owner = carDTO.getOwner();
    }

    public Car() {

    }

    public Car(String model, String stateNumber, String vin, Client owner) {
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

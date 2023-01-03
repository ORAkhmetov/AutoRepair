package ru.akhmetov.AutoRepair.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import ru.akhmetov.AutoRepair.dto.CarDTO;

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

    @Column(name = "filename")
    private String filename;
    @OneToMany(mappedBy = "car")
    private List<Appeal> appealList;

    public Car(CarDTO carDTO) {
        this.model = carDTO.getModel();
        this.stateNumber = carDTO.getStateNumber();
        this.vin = carDTO.getVin();
        this.owner = carDTO.getOwner();
    }

    public Car(Client owner) {
        this.owner = owner;
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

    public List<Appeal> getAppealList() {
        return appealList;
    }

    public void setAppealList(List<Appeal> appealList) {
        this.appealList = appealList;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}

package ru.akhmetov.AutoRepair.car;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.akhmetov.AutoRepair.appeal.Appeal;
import ru.akhmetov.AutoRepair.client.Client;
import java.util.List;
/** Сущность для работы с таблицей "car" **/

@Entity
@Getter
@Setter
@NoArgsConstructor
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
}

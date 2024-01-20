package ru.akhmetov.AutoRepair.client;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.akhmetov.AutoRepair.car.Car;

import java.util.List;
/** Сущность для работы с таблицей "client" **/
@Entity
@Getter
@Setter
@NoArgsConstructor
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

    public Client(ClientDTO clientDTO) {
        this.fullName = clientDTO.getFullName();
    }


}

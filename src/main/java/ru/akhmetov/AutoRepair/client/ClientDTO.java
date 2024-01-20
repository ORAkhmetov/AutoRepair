package ru.akhmetov.AutoRepair.client;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.akhmetov.AutoRepair.car.Car;

import java.util.List;
/**  Объект передачи данных (DTO - Data Transfer Object) для сущности "Client"  **/

@Getter
@Setter
@NoArgsConstructor
public class ClientDTO {

    private int id;

    @NotEmpty(message = "Имя не должно быть пустым")
    private String fullName;
    private List<Car> carList;

    public ClientDTO(Client client) {
        this.fullName = client.getFullName();
    }

}

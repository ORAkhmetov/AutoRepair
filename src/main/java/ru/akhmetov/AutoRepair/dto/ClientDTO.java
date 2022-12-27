package ru.akhmetov.AutoRepair.dto;

import jakarta.validation.constraints.NotEmpty;
import ru.akhmetov.AutoRepair.models.Client;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
public class ClientDTO {

    @NotEmpty(message = "Имя не должно быть пустым")
    private String fullName;

    public ClientDTO(Client client) {
        this.fullName = client.getFullName();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}

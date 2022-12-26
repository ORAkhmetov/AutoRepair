package ru.akhmetov.AutoRepair.dto;

import jakarta.validation.constraints.NotEmpty;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
public class ClientDTO {

    @NotEmpty(message = "Имя не должно быть пустым")
    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}

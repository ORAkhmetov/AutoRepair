package ru.akhmetov.AutoRepair.client;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Component
public class ClientValidator implements Validator {
    private final ClientsServiceImpl clientsService;

    public ClientValidator(ClientsServiceImpl clientsService) {
        this.clientsService = clientsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Client.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Client validClient = (Client) target;
        Optional<Client> foundedClient = clientsService.getClientByFullName(validClient.getFullName());
        if (foundedClient.isPresent()) {
            if (foundedClient.get().getId() != validClient.getId())
                errors.rejectValue("fullName", "", "Клиент с таким именем уже существует");

        }
    }
}

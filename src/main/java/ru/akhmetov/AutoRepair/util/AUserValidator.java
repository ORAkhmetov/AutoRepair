package ru.akhmetov.AutoRepair.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.akhmetov.AutoRepair.models.AUser;
import ru.akhmetov.AutoRepair.services.RegistrationService;

import java.util.Optional;

/**
 * @author Oleg Akhmetov on 28.12.2022
 */
@Component
public class AUserValidator implements Validator {
    private final RegistrationService registrationService;

    @Autowired
    public AUserValidator(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return AUser.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Optional<AUser> aUser = registrationService.isUserCreated((AUser) target);

        if (aUser.isPresent())
            errors.rejectValue("username", "", "Пользователь с таким именем уже зарегистрирован");

    }
}

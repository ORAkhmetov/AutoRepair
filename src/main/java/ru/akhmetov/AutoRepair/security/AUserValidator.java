package ru.akhmetov.AutoRepair.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.util.Optional;
/**  Валидация  **/

@Component
@RequiredArgsConstructor
public class AUserValidator implements Validator {
    private final RegistrationService registrationService;

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

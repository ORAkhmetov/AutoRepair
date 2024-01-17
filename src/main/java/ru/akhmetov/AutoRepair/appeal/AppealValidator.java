package ru.akhmetov.AutoRepair.appeal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
/**  Валидация  **/

@Component
public class AppealValidator implements Validator {
    private final AppealsServiceImpl casesService;

    @Autowired
    public AppealValidator(AppealsServiceImpl casesService) {
        this.casesService = casesService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Appeal.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}

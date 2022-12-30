package ru.akhmetov.AutoRepair.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.akhmetov.AutoRepair.models.Appeal;
import ru.akhmetov.AutoRepair.services.AppealsServiceImpl;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
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

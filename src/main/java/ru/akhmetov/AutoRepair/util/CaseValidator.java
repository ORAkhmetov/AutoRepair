package ru.akhmetov.AutoRepair.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.akhmetov.AutoRepair.models.Case;
import ru.akhmetov.AutoRepair.services.CasesServiceImpl;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Component
public class CaseValidator implements Validator {
    private final CasesServiceImpl casesService;

    @Autowired
    public CaseValidator(CasesServiceImpl casesService) {
        this.casesService = casesService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Case.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {


    }
}

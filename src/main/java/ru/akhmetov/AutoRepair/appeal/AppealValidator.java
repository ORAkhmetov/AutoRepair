package ru.akhmetov.AutoRepair.appeal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
/**  Валидация  **/

@Component
@RequiredArgsConstructor
public class AppealValidator implements Validator {
    private final AppealsServiceImpl casesService;



    @Override
    public boolean supports(Class<?> clazz) {
        return Appeal.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}

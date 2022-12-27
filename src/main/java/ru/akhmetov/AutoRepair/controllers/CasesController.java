package ru.akhmetov.AutoRepair.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.akhmetov.AutoRepair.dto.CaseDTO;
import ru.akhmetov.AutoRepair.models.Case;
import ru.akhmetov.AutoRepair.services.DefaultCasesService;
import ru.akhmetov.AutoRepair.util.CaseValidator;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Controller
public class CasesController {
    private final DefaultCasesService defaultCasesService;
    private final CaseValidator caseValidator;
    @Autowired
    public CasesController(DefaultCasesService defaultCasesService, CaseValidator caseValidator) {
        this.defaultCasesService = defaultCasesService;
        this.caseValidator = caseValidator;
    }
    private Case convertToCase(CaseDTO caseDTO) {
        Case aCase = new Case(
                caseDTO.getCar(),
                caseDTO.getMileage(),
                caseDTO.getDateOfCase());
        enrichCase(aCase);
        return aCase;
    }
    private void enrichCase(Case aCase) {

    }
}

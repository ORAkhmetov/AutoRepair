package ru.akhmetov.AutoRepair.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.akhmetov.AutoRepair.dto.CaseDTO;
import ru.akhmetov.AutoRepair.models.Case;
import ru.akhmetov.AutoRepair.services.CasesService;
import ru.akhmetov.AutoRepair.util.CaseValidator;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Controller
public class CasesController {
    private final CasesService casesService;
    private final CaseValidator caseValidator;
    @Autowired
    public CasesController(CasesService casesService, CaseValidator caseValidator) {
        this.casesService = casesService;
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

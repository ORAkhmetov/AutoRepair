package ru.akhmetov.AutoRepair.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akhmetov.AutoRepair.repositories.CasesRepository;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Service
@Transactional(readOnly = true) //Все методы readOnly, если не помечены аналогичной аннотацией
public class CasesService {
    private final CasesRepository casesRepository;

    public CasesService(CasesRepository casesRepository) {
        this.casesRepository = casesRepository;
    }
}
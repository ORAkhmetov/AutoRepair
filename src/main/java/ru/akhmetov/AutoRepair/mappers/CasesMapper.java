package ru.akhmetov.AutoRepair.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.akhmetov.AutoRepair.dto.CaseDTO;
import ru.akhmetov.AutoRepair.models.Case;

/**
 * @author Oleg Akhmetov on 27.12.2022
 */
@Component
public class CasesMapper {
    private final ModelMapper modelMapper;

    public CasesMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public CaseDTO convertToCaseDTO(Case aCase) {
        return modelMapper.map(aCase, CaseDTO.class);
    }
    public Case convertToCase(CaseDTO caseDTO) {
        return modelMapper.map(caseDTO, Case.class);
    }
}

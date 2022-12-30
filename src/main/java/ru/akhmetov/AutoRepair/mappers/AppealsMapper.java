package ru.akhmetov.AutoRepair.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.akhmetov.AutoRepair.dto.AppealDTO;
import ru.akhmetov.AutoRepair.models.Appeal;

/**
 * @author Oleg Akhmetov on 27.12.2022
 */
@Component
public class AppealsMapper {
    private final ModelMapper modelMapper;

    public AppealsMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public AppealDTO convertToAppealDTO(Appeal appeal) {
        return modelMapper.map(appeal, AppealDTO.class);
    }
    public Appeal convertToAppeal(AppealDTO appealDTO) {
        return modelMapper.map(appealDTO, Appeal.class);
    }
}

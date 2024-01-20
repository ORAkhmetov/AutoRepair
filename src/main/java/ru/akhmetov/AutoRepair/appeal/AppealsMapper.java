package ru.akhmetov.AutoRepair.appeal;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
/**  Преобразование объектов между сущностью "Appeals" и  "DTO"  **/

@Component
@RequiredArgsConstructor
public class AppealsMapper {
    private final ModelMapper modelMapper;


    public AppealDTO convertToAppealDTO(Appeal appeal) {
        return modelMapper.map(appeal, AppealDTO.class);
    }
    public Appeal convertToAppeal(AppealDTO appealDTO) {
        return modelMapper.map(appealDTO, Appeal.class);
    }
}

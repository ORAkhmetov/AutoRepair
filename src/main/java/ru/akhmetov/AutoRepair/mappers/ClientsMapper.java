package ru.akhmetov.AutoRepair.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.akhmetov.AutoRepair.dto.ClientDTO;
import ru.akhmetov.AutoRepair.models.Client;

/**
 * @author Oleg Akhmetov on 27.12.2022
 */
@Component
public class ClientsMapper {
    private final ModelMapper modelMapper;

    public ClientsMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ClientDTO convertToClientDTO(Client client) {
        return modelMapper.map(client, ClientDTO.class);
    }
    public Client convertToClient(ClientDTO clientDTO) {
        return modelMapper.map(clientDTO, Client.class);
    }
}

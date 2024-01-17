package ru.akhmetov.AutoRepair.client;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
/**  Преобразование объектов между сущностью "Client" и  "DTO"  **/

@Component
@AllArgsConstructor
public class ClientsMapper {
    private final ModelMapper modelMapper;

    public ClientDTO convertToClientDTO(Client client) {
        return modelMapper.map(client, ClientDTO.class);
    }
    public Client convertToClient(ClientDTO clientDTO) {
        return modelMapper.map(clientDTO, Client.class);
    }
}

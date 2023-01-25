package ru.akhmetov.AutoRepair.services;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akhmetov.AutoRepair.dto.ClientDTO;
import ru.akhmetov.AutoRepair.models.Client;
import ru.akhmetov.AutoRepair.repositories.ClientsRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Service
@Transactional(readOnly = true) //Все методы readOnly, если не помечены аналогичной аннотацией
public class ClientsServiceImpl implements ClientsService{
    private final ClientsRepository clientsRepository;

    public ClientsServiceImpl(ClientsRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }
    public List<Client> findAll() {
        return clientsRepository.findAll();
    }
    public List<Client> findWithPagination(Integer page) {
        return clientsRepository.findAll(PageRequest.of(page, 10)).getContent();
    }
    public Client findOne(int id) {
        Optional<Client> foundedClient = clientsRepository.findById(id);
        return foundedClient.orElse(null);
    }
    @Transactional
    public void save(Client client) {
        enrichClient(client);
        clientsRepository.save(client);
    }
    @Transactional
    public void update(int id, Client updatedClient) {
        //Если объекту в методе установить id, то при выполнении метода save, Hibernate поймет, что такой объект уже есть
        // и выполнит update, т к в поле id есть аннотация id
        updatedClient.setId(id);
        clientsRepository.save(updatedClient);
    }
    @Transactional
    public void delete(int id) {
        clientsRepository.deleteById(id);
    }

    public Optional<Client> getClientByFullName(String fullName) {
        return clientsRepository.getClientByFullName(fullName);
    }
    public List<Client> getClientsByFullNameContainingIgnoreCase(String query) {
        return clientsRepository.getClientByFullNameContainingIgnoreCase(query);
    }
}

package ru.akhmetov.AutoRepair.client;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
/**  Реализацию интерфейса "ClientService"  **/
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) //Все методы readOnly, если не помечены аналогичной аннотацией
public class ClientsServiceImpl implements ClientsService{
    private final ClientsRepository clientsRepository;

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
    public List<Client> getClientsByFullName(String query) {
        return clientsRepository.getClientByFullNameContainingIgnoreCase(query);
    }
}

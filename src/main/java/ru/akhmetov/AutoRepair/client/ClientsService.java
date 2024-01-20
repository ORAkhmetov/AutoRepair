package ru.akhmetov.AutoRepair.client;

import java.util.List;
import java.util.Optional;
/**  Предоставляет базовые методы для управления заказами  **/
public interface ClientsService {
    List<Client> findAll();
    Client findOne(int id);
    void save(Client client);
    void update(int id, Client updatedClient) ;
    void delete(int id);
    default void enrichClient(Client client) {}
    List<Client>getClientsByFullName(String query);
    List<Client> findWithPagination(Integer page);
    Optional<Client> getClientByFullName(String fullName);
}

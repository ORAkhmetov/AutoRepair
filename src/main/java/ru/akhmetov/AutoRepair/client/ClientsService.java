package ru.akhmetov.AutoRepair.client;


import java.util.List;


/**
 * @author Oleg Akhmetov on 27.12.2022
 */
public interface ClientsService {
    List<Client> findAll();
    Client findOne(int id);
    void save(Client client);
    void update(int id, Client updatedClient) ;
    void delete(int id);
    default void enrichClient(Client client) {

    }
}

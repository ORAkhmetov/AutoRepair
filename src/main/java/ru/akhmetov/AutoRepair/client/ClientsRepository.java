package ru.akhmetov.AutoRepair.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Oleg Akhmetov on 26.12.2022
 */
@Repository
public interface ClientsRepository extends JpaRepository<Client, Integer> {
    Optional<Client> getClientByFullName(String fullName);
    List<Client> getClientByFullNameContainingIgnoreCase(String query);
}

package ru.akhmetov.AutoRepair.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.akhmetov.AutoRepair.models.AUser;
import ru.akhmetov.AutoRepair.repositories.AUsersRepository;

import java.util.Optional;

/**
 * @author Oleg Akhmetov on 28.12.2022
 */
@Service
public class AUsersService {
    private final AUsersRepository aUsersRepository;

    @Autowired
    public AUsersService(AUsersRepository aUsersRepository) {
        this.aUsersRepository = aUsersRepository;
    }

    public Optional<AUser> getAUserByUsername(String username) {
        return aUsersRepository.getAUserByUsername(username);
    }
}

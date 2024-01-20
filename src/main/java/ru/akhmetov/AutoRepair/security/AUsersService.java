package ru.akhmetov.AutoRepair.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

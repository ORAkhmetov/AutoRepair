package ru.akhmetov.AutoRepair.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akhmetov.AutoRepair.models.AUser;
import ru.akhmetov.AutoRepair.repositories.AUsersRepository;

import java.util.Optional;

/**
 * @author Oleg Akhmetov on 28.12.2022
 */
@Service
public class RegistrationService {
    private final AUsersRepository aUsersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(AUsersRepository aUsersRepository, PasswordEncoder passwordEncoder) {
        this.aUsersRepository = aUsersRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    public void register(AUser aUser) {
        aUser.setPassword(passwordEncoder.encode(aUser.getPassword()));
        aUser.setRole("ROLE_ADMIN");
        aUsersRepository.save(aUser);
    }

    public Optional<AUser> isUserCreated(AUser aUser) {
        return aUsersRepository.getAUserByUsername(aUser.getUsername());
    }
}

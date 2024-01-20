package ru.akhmetov.AutoRepair.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
/**  Обработка регестрации пользователя  **/

@RequiredArgsConstructor
@Service
public class RegistrationService {
    private final AUsersRepository aUsersRepository;
    private final PasswordEncoder passwordEncoder;

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

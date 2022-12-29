package ru.akhmetov.AutoRepair.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.akhmetov.AutoRepair.models.AUser;
import ru.akhmetov.AutoRepair.repositories.AUsersRepository;
import ru.akhmetov.AutoRepair.security.UserDetailsImpl;

import java.util.Optional;

/**
 * @author Oleg Akhmetov on 28.12.2022
 */
@Service
public class AUserDetailsService implements UserDetailsService {
    private final AUsersRepository aUsersRepository;

    @Autowired
    public AUserDetailsService(AUsersRepository aUsersRepository) {
        this.aUsersRepository = aUsersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AUser> aUser = aUsersRepository.getAUserByUsername(username);

        if (aUser.isEmpty())
            throw new UsernameNotFoundException("Пользователь не найден");
        return new UserDetailsImpl(aUser.get());
    }
}

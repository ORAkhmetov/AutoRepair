package ru.akhmetov.AutoRepair.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

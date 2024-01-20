package ru.akhmetov.AutoRepair.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;
/** Этот класс служит для загрузки данных о пользователе **/

@RequiredArgsConstructor
@Service
public class AUserDetailsService implements UserDetailsService {
    private final AUsersRepository aUsersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AUser> aUser = aUsersRepository.getAUserByUsername(username);

        if (aUser.isEmpty())
            throw new UsernameNotFoundException("Пользователь не найден");
        return new UserDetailsImpl(aUser.get());
    }
}

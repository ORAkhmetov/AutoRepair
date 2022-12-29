package ru.akhmetov.AutoRepair.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.akhmetov.AutoRepair.models.AUser;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Oleg Akhmetov on 28.12.2022
 */
public class UserDetailsImpl implements UserDetails {

    private final AUser aUser;

    public UserDetailsImpl(AUser aUser) {
        this.aUser = aUser;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(aUser.getRole()));
    }

    @Override
    public String getPassword() {
        return this.aUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.aUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    //Нужно, чтобы получать данные аутентифицированного пользователя
    public AUser getAUser() {
        return this.aUser;
    }
}

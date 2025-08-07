package io.tongnooma.Service;

import io.tongnooma.Persistance.UtilisateurJPAEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
public class CustomUserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private final UtilisateurJPAEntity utilisateur;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return utilisateur.getPassword();
    }

    @Override
    public String getUsername() {
        return utilisateur.getEmail();
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

    public Long getId() {
        return utilisateur.getId();
    }

    /*public String getRole() {
        return utilisateur.getRole().name();
    }*/

}


package br.com.meli.desafio_final.dto;

import br.com.meli.desafio_final.model.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


public class UserDetailsData implements UserDetails, GrantedAuthority {
    private final Optional<User> user;

    public UserDetailsData(Optional<User> user) {
        this.user = user;
    }

    @Override
    public String getAuthority() {
        return this.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(this.getRole()));
        return authorities;
    }

    public long getId() { return user.orElse(new User()).getId(); }

    public String getRole() { return user.orElse(new User()).getRole(); }

    @Override
    public String getPassword() {
        return user.orElse(new User()).getPassword();
    }

    @Override
    public String getUsername() {
        return user.orElse(new User()).getEmail();
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
}

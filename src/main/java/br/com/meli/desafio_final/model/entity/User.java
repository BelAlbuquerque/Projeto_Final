package br.com.meli.desafio_final.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails {
        @Id
        @Column(unique = true, nullable = false)
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @Column(nullable = false)
        @Size(min = 3, max = 55, message = "O 'username' deve possuir o mínimo de 3 caracteries e o maximo de 20.")
        @NotEmpty(message = "O campo 'username' é obrigatório.")
        private String name;

        @Column(nullable = false)
        @NotEmpty(message =  "O campo 'role' é obrigatório.")
       // @Pattern(regexp = "user|admin", message = "Valor Inválido para este campo")
        private String role; // admin user

        @Column(unique = true, nullable = false)
        @NotEmpty(message = "O campo 'email' é obrigatório.")
        @Email(message = "email invalido")
        private String email;

        @Column(nullable = false)
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotEmpty(message = "o campo 'password' não pode ser vazio")
        private String password;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
                authorities.add(new SimpleGrantedAuthority(this.role));
                return authorities;
        }

        public long getId() { return this.id; }

        public String getRole() { return this.role; }

        @Override
        public String getPassword() {
                return this.password;
        }

        @Override
        public String getUsername() {
                return this.email;
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

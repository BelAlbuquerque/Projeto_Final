package br.com.meli.desafio_final.model.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agent {
    @Id
    @Column(unique = true, nullable = false)
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @Size(min = 3, max = 55, message = "O 'username' deve possuir o mínimo de 3 caracteries e o maximo de 20.")
    @NotEmpty(message = "O campo 'username' é obrigatório.")
    private String name;


    @OneToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

}


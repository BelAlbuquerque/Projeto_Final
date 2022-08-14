package br.com.meli.desafio_final.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
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

 }

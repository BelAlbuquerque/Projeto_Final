package br.com.meli.desafio_final.request;

import br.com.meli.desafio_final.model.entity.User;
import br.com.meli.desafio_final.model.entity.Warehouse;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class UserRequest extends User {
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;
    public UserRequest(
            long id,
            @Size(min = 3, max = 55, message = "O 'username' deve possuir o mínimo de 3 caracteries e o maximo de 20.")
            @NotEmpty(message = "O campo 'username' é obrigatório.") String name,
            @NotEmpty(message = "O campo 'role' é obrigatório.") String role,
            @NotEmpty(message = "O campo 'email' é obrigatório.")
            @Email(message = "email invalido") String email,
            @NotEmpty(message = "o campo 'password' não pode ser vazio") String password,
            Warehouse warehouse
    ) {
        super(id, name, role, email, password);
        this.warehouse = warehouse;
    }

 }

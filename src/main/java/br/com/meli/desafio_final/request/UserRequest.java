package br.com.meli.desafio_final.request;

import br.com.meli.desafio_final.model.entity.User;
import br.com.meli.desafio_final.model.entity.Warehouse;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Getter
public class UserRequest extends User {
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;
}

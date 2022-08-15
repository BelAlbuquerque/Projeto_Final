package br.com.meli.desafio_final.util;

import br.com.meli.desafio_final.dto.UserDto;
import br.com.meli.desafio_final.model.entity.User;
import br.com.meli.desafio_final.model.entity.Warehouse;
import br.com.meli.desafio_final.request.UserRequest;

public class UserUtils {

    public static UserRequest newUserRequestSeller() {
        return new UserRequest(1L, "Name", "SELLER", "seller@email.com", "password", new Warehouse());
    }


    public static UserRequest newUserRequestAgent() {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(1L);
        return new UserRequest(2L, "Name", "AGENT", "agent@email.com", "password", warehouse);
    }

    public static UserRequest newUserRequestBuyer() {
        return new UserRequest(1L, "Name", "BUYER", "buyer@email.com", "password", new Warehouse());
    }

    public static User newUserSeller() {
        return new User(1L, "Name", "SELLER", "seller@email.com", "password");
    }

    public static User newUserAgent() {
        return new User(2L, "Name", "AGENT", "agent@email.com", "password");
    }

    public static User newUserBuyer() {
        return new User(1L, "Name", "BUYER", "buyer@email.com", "password");
    }

    public static UserDto newUserDtoSeller() {
        return new UserDto(newUserRequestSeller());
    }

    public static UserDto newUserDtoAgent() {
        return new UserDto(newUserRequestAgent());
    }

    public static UserDto newUserDtoBuyer() {
        return new UserDto(newUserRequestBuyer());
    }
}

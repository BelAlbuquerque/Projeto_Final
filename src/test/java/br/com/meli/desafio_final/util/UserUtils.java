package br.com.meli.desafio_final.util;

import br.com.meli.desafio_final.dto.TokenDto;
import br.com.meli.desafio_final.dto.UserDto;
import br.com.meli.desafio_final.model.entity.User;
import br.com.meli.desafio_final.model.entity.Warehouse;
import br.com.meli.desafio_final.request.LoginRequest;
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

    public static String token() {
        return "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaW5ndWluaGE4QGRqaW5nby5jb20iLCJpYXQiOjE2NjA1OTQ1MTUsImV4cCI6MTY2MDY4MDkxNX0.0eNQovwjfB6vDGxBUzlT5BSPJ2QAPdSQ9V2j7wuMSSI";
    }

    public static LoginRequest loginRequest() {
        LoginRequest login = new LoginRequest();
        login.setEmail(newUserSeller().getEmail());
        login.setPassword(newUserSeller().getPassword());
        return login;
    }
}

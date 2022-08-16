package br.com.meli.desafio_final.util;

import br.com.meli.desafio_final.dto.AllBuyersBySellerDto;
import br.com.meli.desafio_final.dto.AllSellersByBuyerDto;
import br.com.meli.desafio_final.dto.TokenDto;
import br.com.meli.desafio_final.dto.UserDto;
import br.com.meli.desafio_final.model.entity.User;
import br.com.meli.desafio_final.model.entity.Warehouse;
import br.com.meli.desafio_final.request.LoginRequest;
import br.com.meli.desafio_final.request.UserRequest;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
        return new User(1L, "Name", "SELLER", "seller@email.com", "$2a$10$eURFnyaPsM29B.sLcDxSFO78lx/H1UVQE5jJ0pzKDIUfs4nx9qmKy");
    }

    public static User newUserSeller2() {
        return new User(2L, "Name", "SELLER", "seller2@email.com", "$2a$10$eURFnyaPsM29B.sLcDxSFO78lx/H1UVQE5jJ0pzKDIUfs4nx9qmKy");
    }
    public static User newUserAgent() {
        return new User(2L, "Name", "AGENT", "agent@email.com", "password");
    }

    public static User newUserBuyer() {
        return new User(1L, "Name", "BUYER", "buyer@email.com", "password");
    }

    public static User newUserBuyer2() {
        User user = new User();
        user.setName("Name");
        user.setRole("BUYER");
        user.setEmail("buyer2@email.com");
        user.setPassword("password");
        return user;
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
        login.setPassword("19naosao20");
        return login;
    }

    public static List<AllSellersByBuyerDto> allSellersByBuyerDtoList() {
        ArrayList<AllSellersByBuyerDto> list = new ArrayList<>();
        list.add(new AllSellersByBuyerDtoFake(1L, "email1@email.com", "Name1", 1L));
        list.add(new AllSellersByBuyerDtoFake(2L, "email2@email.com", "Name2", 2L));
        return list;
    }

    public static List<AllBuyersBySellerDto> allBuyersBySellerDtoList() {
        ArrayList<AllBuyersBySellerDto> list = new ArrayList<>();
        list.add(new AllBuyersBySellerDtoFake(1L, "email1@email.com", "Name1", 1L));
        list.add(new AllBuyersBySellerDtoFake(2L, "email2@email.com", "Name2", 2L));
        return list;
    }
}

@AllArgsConstructor
class AllBuyersBySellerDtoFake implements AllBuyersBySellerDto {
    private Long id;
    private String email;
    private String name;
    private Long purchase_order_id;

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Long getPurchase_order_id() {
        return null;
    }
}

@AllArgsConstructor
class AllSellersByBuyerDtoFake implements AllSellersByBuyerDto {
    private Long id;
    private String email;
    private String name;
    private Long purchase_order_id;

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Long getPurchase_order_id() {
        return null;
    }
}
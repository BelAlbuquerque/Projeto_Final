package br.com.meli.desafio_final.controller;

import br.com.meli.desafio_final.dto.*;
import br.com.meli.desafio_final.request.LoginRequest;
import br.com.meli.desafio_final.request.UserRequest;
import br.com.meli.desafio_final.service.implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    /**
     * Rota para login de um usuário.
     * @param loginRequest
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<TokenDto> userLogin(@RequestBody LoginRequest loginRequest) {
         return ResponseEntity.status(HttpStatus.OK).body(userService.userLogin(loginRequest));
    }

    /**
     * Rota para cadastro de um novo usuário.
     * @param user
     * @return
     */
    @PostMapping("/user/registry")
    public ResponseEntity<UserDto> saveNewUser(@RequestBody UserRequest user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveNewUser(user));
    }

    /**
     * Retorna todos os vendedores que o usuário, que está acessando a rota, já comprou algum produto.
     * @param request
     * @return
     */
    @GetMapping("/user/sellers")
    public ResponseEntity<List<AllSellersByBuyerDto>> findAllSellersByBuyer(HttpServletRequest request) {
        Long buyerId = (Long) request.getAttribute("userId");
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAllSellersByBuyer(buyerId));
    }

    /**
     * Retorna uma lista de clientes que já compraram do vendedor que está acessando a rota.
     * @param request
     * @return
     */
    @GetMapping("/user/buyers")
    public ResponseEntity<List<AllBuyersBySellerDto>> findAllBuyersBySeller(HttpServletRequest request) {
        Long sellerId = (Long) request.getAttribute("userId");
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAllBuyersBySeller(sellerId));
    }
}

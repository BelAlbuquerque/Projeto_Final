package br.com.meli.desafio_final.controller;

import br.com.meli.desafio_final.configJwt.TokenService;
import br.com.meli.desafio_final.dto.TokenDto;
import br.com.meli.desafio_final.dto.UserDto;
import br.com.meli.desafio_final.model.entity.User;
import br.com.meli.desafio_final.request.LoginRequest;
import br.com.meli.desafio_final.service.implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;


    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> authenticate(@RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken loginData = loginRequest.convert();
        Authentication authentication = manager.authenticate(loginData);
        String token = tokenService.generateToken(authentication);
        return ResponseEntity.ok(new TokenDto(token, "Bearer"));
    }

    @PostMapping("/user/registry")
    public ResponseEntity<User> saveNewUser(@RequestBody UserDto user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return ResponseEntity.ok(userService.save(user));
    }
}

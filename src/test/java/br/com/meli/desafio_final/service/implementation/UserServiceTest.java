package br.com.meli.desafio_final.service.implementation;

import br.com.meli.desafio_final.configJwt.TokenService;
import br.com.meli.desafio_final.dto.TokenDto;
import br.com.meli.desafio_final.dto.UserDto;
import br.com.meli.desafio_final.model.entity.Agent;
import br.com.meli.desafio_final.model.entity.Buyer;
import br.com.meli.desafio_final.model.entity.Seller;
import br.com.meli.desafio_final.model.entity.User;
import br.com.meli.desafio_final.repository.AgentRepository;
import br.com.meli.desafio_final.repository.BuyerRepository;
import br.com.meli.desafio_final.repository.SellerRepository;
import br.com.meli.desafio_final.repository.UserRepository;
import br.com.meli.desafio_final.request.UserRequest;
import br.com.meli.desafio_final.util.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private AgentRepository agentRepository;

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private BuyerRepository buyerRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WarehouseService warehouseService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @Test
    void testSaveNewUserSeller() {
        BDDMockito.when(userRepository.save(ArgumentMatchers.any(User.class)))
                .thenReturn(UserUtils.newUserSeller());

        BDDMockito.when(sellerRepository.save(ArgumentMatchers.any(Seller.class)))
                .thenReturn(SellerUtils.newSeller1ToSave());

        UserDto userToSave = userService.saveNewUser(UserUtils.newUserRequestSeller());

        Assertions.assertThat(userToSave).isNotNull();
        Assertions.assertThat(userToSave.getName()).isEqualTo(UserUtils.newUserDtoSeller().getName());
        Assertions.assertThat(userToSave.getRole()).isEqualTo("SELLER");
    }

    @Test
    void testSaveNewUserAgent() {
        BDDMockito.when(userRepository.save(ArgumentMatchers.any(User.class)))
                .thenReturn(UserUtils.newUserAgent());

        BDDMockito.when(warehouseService.findWarehouse(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.of(WarehouseUtils.newWarehouse()));

        BDDMockito.when(agentRepository.save(ArgumentMatchers.any(Agent.class)))
                .thenReturn(AgentUtils.newAgent());

        UserDto userToSave = userService.saveNewUser(UserUtils.newUserRequestAgent());

        Assertions.assertThat(userToSave).isNotNull();
        Assertions.assertThat(userToSave.getName()).isEqualTo(UserUtils.newUserDtoAgent().getName());
        Assertions.assertThat(userToSave.getRole()).isEqualTo("AGENT");
    }

    @Test
    void testSaveNewUserBuyer() {
        BDDMockito.when(userRepository.save(ArgumentMatchers.any(User.class)))
                .thenReturn(UserUtils.newUserBuyer());

        BDDMockito.when(buyerRepository.save(ArgumentMatchers.any(Buyer.class)))
                .thenReturn(BuyerUtils.newBuyer1ToSave());

        UserDto userToSave = userService.saveNewUser(UserUtils.newUserRequestBuyer());

        Assertions.assertThat(userToSave).isNotNull();
        Assertions.assertThat(userToSave.getName()).isEqualTo(UserUtils.newUserDtoBuyer().getName());
        Assertions.assertThat(userToSave.getRole()).isEqualTo("BUYER");
    }

    @Test
    void testUserLogin() {
        BDDMockito.when(authenticationManager.authenticate(ArgumentMatchers.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new AuthenticationFake());

        BDDMockito.when(tokenService.generateToken(ArgumentMatchers.any(Authentication.class)))
                .thenReturn(UserUtils.token());

        TokenDto tokenDto = userService.userLogin(UserUtils.loginRequest());

        Assertions.assertThat(tokenDto).isNotNull();
        Assertions.assertThat(tokenDto.getToken()).isEqualTo(UserUtils.token());
        Assertions.assertThat(tokenDto.getType()).isEqualTo("Bearer");
    }
}

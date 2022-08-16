package br.com.meli.desafio_final.integrationTest;

import br.com.meli.desafio_final.dto.AllBuyersBySellerDto;
import br.com.meli.desafio_final.dto.TokenDto;
import br.com.meli.desafio_final.integrationTest.utils.UserUtilsIntegration;
import br.com.meli.desafio_final.model.entity.Adsense;
import br.com.meli.desafio_final.model.entity.Seller;
import br.com.meli.desafio_final.model.entity.User;
import br.com.meli.desafio_final.model.entity.Warehouse;
import br.com.meli.desafio_final.repository.*;
import br.com.meli.desafio_final.util.BuyerUtils;
import br.com.meli.desafio_final.util.SellerUtils;
import br.com.meli.desafio_final.util.UserUtils;
import br.com.meli.desafio_final.util.WarehouseUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import static org.assertj.core.api.Assertions.assertThatCode;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    AdsenseRepository adsenseRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    BuyerRepository buyerRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
    }

    @Test
    void login_whenSuccess() throws Exception {

        userRepository.save(UserUtils.newUserSeller());

        ResultActions response = mockMvc.perform(post("/login")
                .content(objectMapper.writeValueAsString(UserUtils.loginRequest()))
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.token",
                        CoreMatchers.is(instanceOf(String.class))))
                .andExpect(jsonPath("$.type",
                        CoreMatchers.is("Bearer")));
    }

    @Test
    void login_whenNotSuccess() throws Exception {
        ResultActions response = mockMvc.perform(post("/login")
                .content(objectMapper.writeValueAsString(UserUtils.loginRequest()))
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isForbidden());
    }

    @Test
    void saveNewUser_whenSuccess() throws Exception {

        ResultActions response = mockMvc.perform(post("/user/registry")
                .content(UserUtilsIntegration.buyerPayload())
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isCreated());
    }

    @Test
    void saveNewUserl_whenSuccess() throws Exception {
        userRepository.save(UserUtils.newUserSeller());
        userRepository.save(UserUtils.newUserSeller2());
        userRepository.save(UserUtils.newUserBuyer());
        userRepository.save(UserUtils.newUserBuyer2());

        sellerRepository.save(SellerUtils.newSeller1ToSave());
        sellerRepository.save(SellerUtils.newSeller2ToSave());
        buyerRepository.save(BuyerUtils.newBuyer1ToSave());
        buyerRepository.save(BuyerUtils.newBuyer2ToSave());

        productRepository.saveAll(UserUtilsIntegration.productList());
        adsenseRepository.saveAll(UserUtilsIntegration.adsenseList());

        ResultActions response1 = mockMvc.perform(post("http://127.0.0.1:8080/api/v2/fresh-products/orders")
                .header("Authorization", "Bearer " + UserUtilsIntegration.buyerToken())
                .content(UserUtilsIntegration.purchaseOrder1())
                .contentType(MediaType.APPLICATION_JSON));
        response1.andExpect(status().isCreated());

        ResultActions response2 = mockMvc.perform(post("http://127.0.0.1:8080/api/v2/fresh-products/orders")
                .header("Authorization", "Bearer " + UserUtilsIntegration.buyerToken())
                .content(UserUtilsIntegration.purchaseOrder2())
                .contentType(MediaType.APPLICATION_JSON));
        response2.andExpect(status().isCreated());

        ResultActions response3 = mockMvc.perform(get("http://127.0.0.1:8080/user/sellers")
                .header("Authorization", "Bearer " + UserUtilsIntegration.buyerToken()));
        response3.andExpect(status().isOk());

        ResultActions response4 = mockMvc.perform(get("http://127.0.0.1:8080/user/buyers")
                .header("Authorization", "Bearer " + UserUtilsIntegration.sellerToken()));
        response4.andExpect(status().isOk());


    }


}

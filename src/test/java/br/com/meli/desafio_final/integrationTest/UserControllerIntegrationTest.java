package br.com.meli.desafio_final.integrationTest;

import br.com.meli.desafio_final.dto.AllBuyersBySellerDto;
import br.com.meli.desafio_final.dto.TokenDto;
import br.com.meli.desafio_final.model.entity.Warehouse;
import br.com.meli.desafio_final.repository.UserRepository;
import br.com.meli.desafio_final.repository.WarehouseRepository;
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
        String payload = "{\n" +
                "    \"name\": \"Panqueca\",\n" +
                "    \"email\": \"punkpunk@punk.com\",\n" +
                "    \"password\": \"stogges\",\n" +
                "    \"role\": \"BUYER\"\n" +
                "}";
        ResultActions response = mockMvc.perform(post("/user/registry")
                .content(payload)
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isCreated());
    }
}

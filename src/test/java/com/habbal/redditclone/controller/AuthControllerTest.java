package com.habbal.redditclone.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.habbal.redditclone.dto.RegisterRequest;
import com.habbal.redditclone.model.User;
import com.habbal.redditclone.repository.UserRepository;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-it.properties"
)
@AutoConfigureMockMvc
class AuthControllerTest {

    private final ObjectMapper mapper = new ObjectMapper();

    private RegisterRequest registerRequest;

    @Value("${api.version}")
    private String apiVersion;

    private String registerUrl;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {

        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        registerRequest = RegisterRequest.builder()
                .email("user@email.com")
                .password("password")
                .username("username")
                .build();

        registerUrl = "/api/" + apiVersion + "/auth/register";
    }

    @Test
    public void test_register_returnsOk() throws Exception {
        mockMvc.perform(post("/api/v1/auth/register")
                        .content(mapper.writeValueAsString(registerRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void test_register_returnsNonNullId() throws Exception {
        User userToCreate = new User();
        userToCreate.setEmail(registerRequest.getEmail());
        userToCreate.setPassword(registerRequest.getPassword());
        userToCreate.setUsername(registerRequest.getUsername());

        String createdUserId = mockMvc.perform(post(registerUrl)
                        .content(mapper.writeValueAsString(registerRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();


        assertThat(createdUserId, is(notNullValue()));

        User createdUser = userRepository.findById(Long.valueOf(createdUserId)).orElseThrow();

        assertEquals(createdUser.getEmail(), userToCreate.getEmail());
        assertEquals(createdUser.getUsername(), userToCreate.getUsername());
    }

}
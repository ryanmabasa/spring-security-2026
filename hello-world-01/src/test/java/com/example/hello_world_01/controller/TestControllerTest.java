package com.example.hello_world_01.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestController.class)
class TestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("The /test endpoint cannot be called unauthenticated")
    @WithAnonymousUser
    public void testFailedAuthentication() throws Exception {
        mvc.perform(get("/test"))
                .andExpect(unauthenticated())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("A user with privileges can authenticate and is authorized to call /test")
    @WithMockUser
    public void testSuccessfulAuthorization() throws Exception {
        mvc.perform(get("/test"))
                .andExpect(authenticated())
                .andExpect(status().isOk());
    }

}
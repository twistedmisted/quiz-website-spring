package com.epam.final_project.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.RequestDispatcher;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser(username = "user", password = "user", authorities = "user")
public class MyErrorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void handleErrorTest_statusCode400() throws Exception {
        this.mockMvc.perform(get("/error").requestAttr(RequestDispatcher.ERROR_STATUS_CODE, "404"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/h2")
                        .string("Page not found. Check your link."));
    }

    @Test
    public void handleErrorTest_statusCode500() throws Exception {
        this.mockMvc.perform(get("/error").requestAttr(RequestDispatcher.ERROR_STATUS_CODE, "500"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/h2")
                        .string("Sorry, but we can't do that right now."));
    }

    @Test
    public void handleErrorTest_withoutStatusCode() throws Exception {
        this.mockMvc.perform(get("/error"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/h2")
                        .string("Our Engineers are on it. Try again later."));
    }

}

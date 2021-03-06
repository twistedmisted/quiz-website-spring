package com.epam.final_project.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "user", password = "user", authorities = "user")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-quiz-before.sql", "/create-user-before.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-quiz-after.sql", "/create-user-after.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AppControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void homePageTest() throws Exception {
        this.mockMvc.perform(get("/app/home"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/div[4]/p").string("My quizzes"));
    }

    @Test
    public void showAllQuizzesTest_sortByName() throws Exception {
        this.mockMvc.perform(get("/app/all-quizzes").param("sortBy", "name"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/div[3]/div[2]/a").nodeCount(2));
    }

    @Test
    public void showAllQuizzesTest_sortBySubject() throws Exception {
        this.mockMvc.perform(get("/app/all-quizzes").param("sortBy", "Java"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/div[3]/div[2]/a").nodeCount(1));
    }

}
package com.holidayguru.web.controllers;
import com.holidayguru.HolidayguruApplication;
import com.holidayguru.data.entities.Role;
import com.holidayguru.data.entities.User;
import com.holidayguru.data.repositories.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(classes = HolidayguruApplication.class)
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;




    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUo(){
        userRepository.deleteAll();

        User user = new User();

        user.setFirstName("Hristo");
        user.setSecondName("Stavrev");
        user.setEmail("stavrev@abv.bg");
        user.setAuthorities(Set.of(new Role("ROLE_USER"), new Role("ROLE_MODERATOR"), new Role("ROLE_ADMIN"), new Role("ROLE_ROOT")));
        user.setPassword("123456");
        user.setUsername("stavrev1");

        this.userRepository.saveAndFlush(user);

    }


    @AfterEach
    public void clean(){
        userRepository.deleteAll();
    }


    @Test
    public void userRegister_ShouldReturnCorrectStatusCodeAndPage() throws Exception {
        mockMvc.perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }



    @Test
    public void userRegisterConfirm_ShouldCreateNewUSerWithCorrectParams() throws Exception {

        long repoCount = this.userRepository.count();

        mockMvc.perform(MockMvcRequestBuilders
                .post("/users/register")
                .with(csrf())
                .param("username", "emiliq123")
                .param("firstName", "Emiliq")
                .param("secondName", "Kostadinova")
                .param("email", "emiliq@yahoo.com")
                .param("password", "123456")
                .param("repeatPassword", "123456")
        ).andExpect(status().is3xxRedirection());

        Assertions.assertEquals(repoCount + 1, this.userRepository.count());
    }



    @Test
    public void userRegisterConfirm_ShouldNotCreateUserWithWrongPasswordParams() throws Exception {
        long repoCount = this.userRepository.count();
        mockMvc.perform(MockMvcRequestBuilders
                .post("/users/register")
                .with(csrf())
                .param("username", "minka123")
                .param("firstName", "Minka")
                .param("secondName", "Minkova")
                .param("email", "minka@yahoo.com")
                .param("password", "123")
                .param("repeatPassword", "12")
        ).andExpect(status().is3xxRedirection());
        Assertions.assertEquals(repoCount, this.userRepository.count());
    }



    @Test
    public void userRegisterConfirm_ShouldNotCreateUserWithWrongEmailParams() throws Exception {
        long repoCount = this.userRepository.count();
        mockMvc.perform(MockMvcRequestBuilders
                .post("/users/register")
                .with(csrf())
                .param("username", "minka123")
                .param("firstName", "Minka")
                .param("secondName", "Minkova")
                .param("email", "minka.com")
                .param("password", "123456")
                .param("repeatPassword", "123456")
        ).andExpect(status().is3xxRedirection());
        Assertions.assertEquals(repoCount, this.userRepository.count());
    }



    @Test
    public void userRegisterConfirm_ShouldNotCreateUserWithWrongUsernameParams() throws Exception {
        long repoCount = this.userRepository.count();
        mockMvc.perform(MockMvcRequestBuilders
                .post("/users/register")
                .with(csrf())
                .param("username", "min")
                .param("firstName", "Minka")
                .param("secondName", "Minkova")
                .param("email", "minka.com")
                .param("password", "123456")
                .param("repeatPassword", "123456")
        ).andExpect(status().is3xxRedirection());
        Assertions.assertEquals(repoCount, this.userRepository.count());
    }


    @Test
    public void userRegisterConfirm_ShouldNotCreateUserWithWrongNamesParams() throws Exception {
        long repoCount = this.userRepository.count();
        mockMvc.perform(MockMvcRequestBuilders
                .post("/users/register")
                .with(csrf())
                .param("username", "min")
                .param("firstName", "Mi")
                .param("secondName", "M")
                .param("email", "minka.com")
                .param("password", "123456")
                .param("repeatPassword", "123456")
        ).andExpect(status().is3xxRedirection());
        Assertions.assertEquals(repoCount, this.userRepository.count());
    }



    @Test
    public void userLogin_ReturnThePageWithCorrectStatusCode() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }



}



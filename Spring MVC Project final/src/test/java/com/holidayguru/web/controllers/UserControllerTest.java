package com.holidayguru.web.controllers;
import com.holidayguru.HolidayguruApplication;
import com.holidayguru.data.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(classes = HolidayguruApplication.class)
class UserControllerTest  {

    @Autowired
     MockMvc mockMvc;

    private final static String REGISTER_VIEW = "register";
    @MockBean
    protected UserRepository userRepository;
    @MockBean
    protected ModelMapper modelMapper;


    @Test
    public void register_ReturnCorrectStatusCode_200() throws Exception {
        mockMvc.perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name(REGISTER_VIEW));
    }



    @Test
    public void registerConfirm_ShouldReturnStatusCode_300() throws Exception {

        mockMvc.perform(get("/users/my-profile"))
                .andExpect(status().is(302));
    }

    @Test
    public void myProfileEdit_ReturnCorrectStatusCode_302() throws Exception {
        mockMvc.perform(get("/users/profile-edit"))
                .andExpect(status().is(302));
    }




}

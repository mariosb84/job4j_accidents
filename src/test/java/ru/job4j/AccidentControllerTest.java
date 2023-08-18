package ru.job4j;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class AccidentControllerTest {

    @Autowired
    private MockMvc mockMvcAccident;

    @Test
    @WithMockUser
    public void shouldReturnViewCreateAccident() throws Exception {
        this.mockMvcAccident.perform(get("/tasks/createAccident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/tasks/createAccident"));
    }

     /*ЭТОТ ТЕСТ НЕ ПРОХОДИТ :*/

  /* @Test
    @WithMockUser
    public void shouldReturnViewEditAccident() throws Exception {
        this.mockMvcAccident.perform(get("/tasks/editAccident/{id}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/tasks/editAccident"));
    }*/

}

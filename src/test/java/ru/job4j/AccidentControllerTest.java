package ru.job4j;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.model.Accident;
import ru.job4j.model.AccidentType;
import ru.job4j.model.Rule;
import ru.job4j.service.AccidentDataService;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class AccidentControllerTest {

    @Autowired
    private MockMvc mockMvcAccident;

    @MockBean
    private AccidentDataService accidents;

    @Test
    @WithMockUser
    public void shouldReturnViewCreateAccident() throws Exception {
        this.mockMvcAccident.perform(get("/tasks/createAccident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/tasks/createAccident"));
    }

   @Test
    @WithMockUser
    public void shouldReturnViewEditAccident() throws Exception {
       var accident = new Accident(1, "Name", "Text",
               "Address", new AccidentType(), Set.of(new Rule()));
       when(accidents.findById(accident.getId())).thenReturn(Optional.of(accident));
        this.mockMvcAccident.perform(get("/tasks/editAccident/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/tasks/editAccident"));
    }


}

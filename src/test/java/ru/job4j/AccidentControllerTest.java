package ru.job4j;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Test
    @WithMockUser
    void whenPostSaveAccidentThenShouldReturnIndexPage() throws Exception {
        var rIds = List.of(1, 2, 3);
        var accident = new Accident(0, "Name", "Text", "Address",
                new AccidentType(1,"Empty"), Collections.emptySet());
        this.mockMvcAccident.perform(post("/tasks/saveAccident")
                .param("name", "Name")
                .param("text", "Text")
                .param("address", "Address")
                .param("type.id", "1")
                .param("type.name", "Empty")
                .param("rIds", "1", "2", "3"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        ArgumentCaptor<Accident> accidentCapture = ArgumentCaptor.forClass(Accident.class);
        ArgumentCaptor<List<Integer>> rIdsCapture = ArgumentCaptor.forClass(List.class);

        verify(accidents).add(accidentCapture.capture(), rIdsCapture.capture());

        assertThat(accidentCapture.getValue()).usingRecursiveComparison()
                .isEqualTo(accident);
        assertThat(rIdsCapture.getValue()).usingRecursiveComparison()
                .isEqualTo(rIds);
    }

    @Test
    @WithMockUser
    void whenPostEditAccidentThenShouldReturnRedirectIndexPageAndArgumentCaptureEquals() throws Exception {
        var rIds = List.of(1, 2, 3);
        var accident = new Accident(0, "Name", "Text", "Address",
                new AccidentType(1,"Empty"), Collections.emptySet());
        when(accidents.update(accident, accident.getId(), rIds)).thenReturn(true);
        this.mockMvcAccident.perform(post("/tasks/updateAccident")
                .param("id", "0")
                .param("name", "Name")
                .param("text", "Text")
                .param("address", "Address")
                .param("type.id", "1")
                .param("type.name", "Empty")
                .param("rIds", "1", "2", "3"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        ArgumentCaptor<Accident> accidentCapture = ArgumentCaptor.forClass(Accident.class);
        ArgumentCaptor<List<Integer>> rIdsCapture = ArgumentCaptor.forClass(List.class);
        ArgumentCaptor<Integer> accidentIdCapture = ArgumentCaptor.forClass(Integer.class);

        verify(accidents).update(accidentCapture.capture(), accidentIdCapture.capture(), rIdsCapture.capture());

        assertThat(accidentCapture.getValue()).usingRecursiveComparison()
                .isEqualTo(accident);
        assertThat(rIdsCapture.getValue()).usingRecursiveComparison()
                .isEqualTo(rIds);
        assertThat(accidentIdCapture.getValue()).usingRecursiveComparison()
                .isEqualTo(accident.getId());
    }

}

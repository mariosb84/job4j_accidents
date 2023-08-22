package ru.job4j;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.model.Authority;
import ru.job4j.model.User;
import ru.job4j.service.AuthorityService;
import ru.job4j.service.UserService;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class RegControllerTest {

    @Autowired
    private MockMvc mockMvcReg;
    @MockBean
    private UserService users;
    @MockBean
    private AuthorityService authorities;

    @Test
    @WithMockUser
    public void shouldReturnViewReg() throws Exception {
        this.mockMvcReg.perform(get("/reg"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/securityLogin/reg"));
    }

    @Test
    @WithMockUser
    void whenRegistrationSaveThenRedirectLoginPage() throws Exception {
        var authority = new Authority(1, "ROLE_USER");
        var user = new User(0, "Password", "Login", authority, true);

        when(authorities.findByAuthority(authority.getAuthority())).thenReturn(authority);
        when(users.save(user)).thenReturn(Optional.of(user));

        this.mockMvcReg.perform(post("/reg")
                .param("username", "Login")
                .param("password", "Password"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/reg?success=true"));
    }

}

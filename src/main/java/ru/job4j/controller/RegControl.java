package ru.job4j.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.model.User;
import ru.job4j.service.AuthorityService;
import ru.job4j.service.UserService;

@ThreadSafe
@Controller
@AllArgsConstructor
public class RegControl {

    private final PasswordEncoder encoder;
    private final UserService users;
    private final AuthorityService authorities;

    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorities.findByAuthority("ROLE_USER"));
        /*users.save(user);*/
        /*if (!users.findByNameUsers(user.getUsername()).isEmpty()) {
            return "redirect:/reg?fail=true";
        }
        users.save(user);*/
        try {
            users.save(user);
        } catch (ConstraintViolationException e) {
            /*return "redirect:/reg?fail=true";*/
            return "redirect:/reg";
        }
        return "redirect:/reg?success=true";
    }

    @GetMapping("/reg")
    public String regPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail,
                          @RequestParam(name = "success", required = false) Boolean success) {
        model.addAttribute("fail", fail != null);
        model.addAttribute("success", success != null);
        return "/securityLogin/reg";
    }

}

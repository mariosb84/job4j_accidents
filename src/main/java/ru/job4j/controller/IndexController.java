package ru.job4j.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.service.AccidentDataService;

@ThreadSafe
@Controller
@AllArgsConstructor
public class IndexController {

    private final AccidentDataService accidentService;

   @GetMapping("/")
    public String index(Model model) {
       /* model.addAttribute("user", "Petr Arsentev");*/
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        model.addAttribute("accidents", accidentService.findAll());
        return "/tasks/index";
    }

}

package ru.job4j.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/*import ru.job4j.service.AccidentService;*/
import ru.job4j.service.HbmAccidentService;

@ThreadSafe
@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class IndexController {

    private final /*AccidentService*/ HbmAccidentService accidentService;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("user", "Petr Arsentev");
        model.addAttribute("accidents", accidentService.findAll());
        return "/tasks/index";
    }

}

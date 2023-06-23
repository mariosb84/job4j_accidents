package ru.job4j.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.model.Accident;
import ru.job4j.model.AccidentType;
import ru.job4j.service.AccidentService;

import java.util.ArrayList;
import java.util.List;

@ThreadSafe
@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class AccidentController {

    private final AccidentService accidents;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        List<AccidentType> types = new ArrayList<>();
        types.add(new AccidentType(1, "Две машины"));
        types.add(new AccidentType(2, "Машина и человек"));
        types.add(new AccidentType(3, "Машина и велосипед"));
        model.addAttribute("types", types);
        model.addAttribute("accident", new Accident());
        return "/tasks/createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident) {
        accidents.add(accident);
        return "redirect:/index";
    }

    @GetMapping("/editAccident")
    public String viewEditAccident(Model model, @PathVariable int id) {
        var accidentOptional = accidents.findById(id);
        if (accidentOptional.isEmpty()) {
            model.addAttribute("message", "Инцидент с указанным идентификатором не найден");
            return "errors/error404";
        }
        model.addAttribute("accident", accidentOptional.get());
        return "/tasks/editAccident";
    }

    @PostMapping("/updateAccident")
    public String update(Model model, @ModelAttribute Accident accident) {
        var isUpdate = accidents.update(accident, accident.getId());
        if (!isUpdate) {
            model.addAttribute("message", "Задание с указанным идентификатором не найдено");
            return "errors/error404";
        }
        return "redirect:/index";
    }

}

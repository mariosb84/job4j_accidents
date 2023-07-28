package ru.job4j.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.model.Accident;
import ru.job4j.service.*;
/*import ru.job4j.service.AccidentService;*/

import java.util.List;

@ThreadSafe
@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class AccidentController {

    private final /*AccidentService*/ HbmAccidentService accidents;
    private final /*AccidentTypeService*/ HbmAccidentTypeService accidentTypes;
    private final /*AccidentRuleService*/ HbmAccidentRuleService accidentRules;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", accidentTypes.findAll());
        model.addAttribute("rules", accidentRules.findAll());
        model.addAttribute("accident", new Accident());
        return "/tasks/createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, @RequestParam("rIds") List<Integer> rulesIds) {
        accidents.add(accident, rulesIds);
        return "redirect:/tasks/index";
    }

    @GetMapping("/editAccident/{id}")
    public String viewEditAccident(Model model, @PathVariable int id) {
        model.addAttribute("types", accidentTypes.findAll());
        model.addAttribute("rules", accidentRules.findAll());
        var accidentOptional = accidents.findById(id);
        if (accidentOptional.isEmpty()) {
            model.addAttribute("message", "Инцидент с указанным идентификатором не найден");
            return "errors/error404";
        }
        model.addAttribute("accident", accidentOptional.get());
        return "/tasks/editAccident";
    }

    @PostMapping("/updateAccident")
    public String update(Model model, @ModelAttribute Accident accident, @RequestParam("rIds") List<Integer> rulesIds) {
        var isUpdate = accidents.update(accident, accident.getId(), rulesIds);
        if (!isUpdate) {
            model.addAttribute("message", "Задание с указанным идентификатором не найдено");
            return "errors/error404";
        }
        return "redirect:/tasks/index";
    }

}

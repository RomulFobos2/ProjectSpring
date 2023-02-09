package com.company.ProjectSpring.controllers;

import com.company.ProjectSpring.models.Appeal;
import com.company.ProjectSpring.repo.AppealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ManagerPageQuestionnaireController {
    @Autowired
    private AppealRepository appealRepository;

    @GetMapping("/manager/questionnaire/{id}")
    public String questionnaireMain(@PathVariable(value = "id") long id, Model model) {
        Appeal currentAppeal = appealRepository.findById(id).orElseThrow();
        model.addAttribute("currentAppeal", currentAppeal);
        return "manager/questionnaire";
    }
}

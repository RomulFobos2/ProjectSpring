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
public class UserPageQuestionnaireController {
    @Autowired
    private AppealRepository appealRepository;

    @GetMapping("/questionnaire/{id}")
    public String questionnaireMain(@PathVariable(value = "id") long id, Model model) {
        Appeal currentAppeal = appealRepository.findById(id).orElseThrow();
        if(currentAppeal.getEvaluationAverage() != null){
            return "redirect:/appeal-details/" + id;
        }
        model.addAttribute("currentAppeal", currentAppeal);
        return "user/questionnaire";
    }

    @PostMapping("/questionnaire-send/{id}")
    public String questionnaireMain(@PathVariable(value = "id") long id, @RequestParam String q1, @RequestParam String q2, @RequestParam String q3, @RequestParam String q4, @RequestParam String q5,Model model) {
        Appeal appeal = appealRepository.findById(id).orElseThrow();
        appeal.setEvaluation_Q1(q1);
        appeal.setEvaluation_Q2(q2);
        appeal.setEvaluation_Q3(q3);
        appeal.setEvaluation_Q4(q4);
        appeal.setTextComment_Q5(q5);
        float averageEvaluation = (float) (Integer.parseInt(q1) + Integer.parseInt(q2) + Integer.parseInt(q3) + Integer.parseInt(q4))/4;
        appeal.setEvaluationAverage(String.valueOf(averageEvaluation));
        appealRepository.save(appeal);
        return "redirect:/appeal-details/" + id;
    }
}

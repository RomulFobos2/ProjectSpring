package com.company.ProjectSpring.controllers;

import com.company.ProjectSpring.models.Appeal;
import com.company.ProjectSpring.models.Role;
import com.company.ProjectSpring.models.User;
import com.company.ProjectSpring.repo.AppealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class AdminPanelAppealController {
    @Autowired
    private AppealRepository appealRepository;

    @GetMapping("/admin/appeals-main")
    public String appeals(Model model){
        return "admin/appeals-main";
    }

    @GetMapping("/admin/all-appeals-znp")
    public String allAppeals(Model model){
        List<Appeal> currentAppeals = ((List<Appeal>)appealRepository.findAll()).stream().filter(x -> x.getDateAnswer() != null).filter(x -> (x.getDateAnswer().getTime() >= new Date().getTime())).collect(Collectors.toList());
        //Iterable<Appeal> appeals = appealRepository.findAll(); //Предыдщуая реализация, не удаляем. Т.к. не известно обязателе ли тип Iterable для атрибута
        model.addAttribute("currentAppeals", currentAppeals);
        return "admin/all-appeals-znp";
    }

    @GetMapping("/admin/all-appealsOld-znp")
    public String allAppealsOld(Model model){
        List<Appeal> oldAppeals = ((List<Appeal>)appealRepository.findAll()).stream().filter(x -> x.getDateAnswer() != null).filter(x -> (x.getDateAnswer().getTime() < new Date().getTime())).collect(Collectors.toList());
        model.addAttribute("oldAppeals", oldAppeals);
        return "admin/all-appealsOld-znp";
    }

    @GetMapping("/admin/all-appeals-online")
    public String allAppealsOnline(Model model){
        Iterable<Appeal> currentAppeals  = ((List<Appeal>) appealRepository.findAll()).stream().filter(x -> x.getPassword() != null).filter(x -> x.getTextResponse() == null).collect(Collectors.toList());
        model.addAttribute("currentAppeals", currentAppeals);
        return "admin/all-appeals-online";
    }

    @GetMapping("/admin/all-appealsOld-online")
    public String allAppealsOldOnline(Model model){
        Iterable<Appeal> currentAppeals  = ((List<Appeal>) appealRepository.findAll()).stream().filter(x -> x.getPassword() != null).filter(x -> x.getTextResponse() != null).collect(Collectors.toList());
        model.addAttribute("currentAppeals", currentAppeals);
        return "admin/all-appealsOld-online";
    }

    @GetMapping("/admin/appeal-details/{id}")
    public String appeal_details(@PathVariable(value = "id") Long id, Model model){
        if(!appealRepository.existsById(id)){
            return "redirect:/admin/all-appeals";
        }
        Optional<Appeal> appeal = appealRepository.findById(id);
        ArrayList<Appeal> res = new ArrayList<>();
        appeal.ifPresent(res::add);
        model.addAttribute("appeal", res);
        return "admin/appeal-details";

    }
}

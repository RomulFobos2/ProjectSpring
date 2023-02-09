package com.company.ProjectSpring.controllers;

import com.company.ProjectSpring.models.Appeal;
import com.company.ProjectSpring.models.Department;
import com.company.ProjectSpring.models.Service;
import com.company.ProjectSpring.models.User;
import com.company.ProjectSpring.repo.AppealRepository;
import com.company.ProjectSpring.repo.DepartmentRepository;
import com.company.ProjectSpring.repo.ServiceRepository;
import com.company.ProjectSpring.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

@Controller
public class TestController {
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private AppealRepository appealRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/test")
    public String test(Model model) throws ParseException {
        Iterable<Service> services = serviceRepository.findAll();
        model.addAttribute("allServices", services);
        Iterable<Department> departments = departmentRepository.findAll();
        model.addAttribute("allDepartments", departments);
        ArrayList<Department> arrayListDepartments = (ArrayList<Department>) departments;
        model.addAttribute("arrayListDepartments", arrayListDepartments);
        Iterable<Appeal> appeals = appealRepository.findAll();
        ArrayList<Appeal> arrayListAppeals = (ArrayList<Appeal>) appeals;
        model.addAttribute("arrayListAppeals", arrayListAppeals);
        return "test";
    }

    @PostMapping("/test")
    public String appealAdd(@RequestParam Long inputService, @RequestParam String StrDateAnswer, @RequestParam String inputTime, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        if(user == null){
            return "login";
        }
//        SimpleDateFormat simpleDateFormat_inner = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault());
        SimpleDateFormat simpleDateFormat_inner = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        Date dateAnswer = null;
        try {
            dateAnswer = simpleDateFormat_inner.parse(StrDateAnswer + " " + inputTime);
        } catch (ParseException e) {

        }
        Service service = serviceRepository.findById(inputService).orElseThrow();
        Appeal appeal = new Appeal(new Date(), dateAnswer, user, service);
        appealRepository.save(appeal);
        return "redirect:/appeal-main";
    }

}

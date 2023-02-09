package com.company.ProjectSpring.controllers;

import com.company.ProjectSpring.models.*;
import com.company.ProjectSpring.repo.AppealRepository;
import com.company.ProjectSpring.repo.UserRepository;
import com.company.ProjectSpring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ManagerPanelStatisticController {
    @Autowired
    private AppealRepository appealRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/manager/statistic")
    public String statistic(Model model) {
        User manager = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Role roleManagerDept = userService.getRoleManagerDept(manager);
        //Выводим только те заявки, которые относятся к тому же департаменту, что и менеджер просматривающих заявки
        Iterable<Appeal> currentAppeals  = ((List<Appeal>) appealRepository.findAll()).stream().filter(x -> x.getService().getDepartment().getDepartmentRole().equals(roleManagerDept)).collect(Collectors.toList());
        Department managerDepartment = userService.getDepartmentManager(manager);
        Iterable<Service> services = managerDepartment.getServiceList();
        model.addAttribute("allServices", services);
        model.addAttribute("currentAppeals", currentAppeals);
        return "manager/statistic";
    }
}

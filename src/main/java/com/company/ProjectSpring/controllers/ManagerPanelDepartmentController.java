package com.company.ProjectSpring.controllers;

import com.company.ProjectSpring.models.Department;
import com.company.ProjectSpring.models.Role;
import com.company.ProjectSpring.models.User;
import com.company.ProjectSpring.repo.DepartmentRepository;
import com.company.ProjectSpring.repo.UserRepository;
import com.company.ProjectSpring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ManagerPanelDepartmentController {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/manager/department")
    public String department(Model model) {
        User manager = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Department department = userService.getDepartmentManager(manager);
        model.addAttribute("department", department);
        return "manager/department";
    }

    @GetMapping("/manager/editDepartment")
    public String departmentEdit(Model model) {
        User manager = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Department department = userService.getDepartmentManager(manager);
        model.addAttribute("department", department);
        return "manager/editDepartment";
    }

    @PostMapping("/manager/editDepartment")
    public String addDepartment(@RequestParam String inputDepartment, @RequestParam String inputAddress, @RequestParam String inputEmail,
                                @RequestParam String inputTel, @RequestParam String inputDescription, @RequestParam String inputShortDescription, Model model) {
        User manager = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Department department = userService.getDepartmentManager(manager);
        department.setName(inputDepartment);
        department.setAddress(inputAddress);
        department.setEmail(inputEmail);
        department.setNumber(inputTel);
        department.setDescription(inputDescription);
        department.setShortDescription(inputShortDescription);
        departmentRepository.save(department);
        return "redirect:/manager/department";
    }
}

package com.company.ProjectSpring.controllers;

import com.company.ProjectSpring.models.Department;
import com.company.ProjectSpring.models.Role;
import com.company.ProjectSpring.repo.DepartmentRepository;
import com.company.ProjectSpring.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class AdminPanelDepartmentController {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/admin/all-departments")
    public String allDepartment(Model model) {
        Iterable<Department> departments = departmentRepository.findAll();
        model.addAttribute("allDepartments", departments);
        return "admin/all-departments";
    }

    @GetMapping("/admin/all-departments/department-details/{id}")
    public String department_details(@PathVariable(value = "id") long id, Model model) {
        if(!departmentRepository.existsById(id)){
            return "redirect:/admin/all-departments";
        }
        Optional<Department> department = departmentRepository.findById(id);
        ArrayList<Department> res = new ArrayList<>();
        department.ifPresent(res::add);
        model.addAttribute("department", res);
        return "admin/department-details";
    }

    @PostMapping("/admin/all-departments/department-details/{id}/remove")
    public String delete_department(@PathVariable(value = "id") long id,Model model) {
        Department department = departmentRepository.findById(id).orElseThrow();
        departmentRepository.delete(department);
        return "redirect:/admin/all-departments";
    }

    @GetMapping("/admin/admin-addDepartment")
    public String addDepartment(Model model) {
        return "admin/admin-addDepartment";
    }

    @PostMapping("/admin/admin-addDepartment")
    public String addDepartment(@RequestParam String inputDepartment, @RequestParam String inputDepartmentRole, @RequestParam String inputAddress, @RequestParam String inputEmail,
                                @RequestParam String inputTel, @RequestParam String inputDescription, @RequestParam String inputShortDescription, Model model) {
        //Добавляем уникальное значение к каждой роли, чтобы не выполнять проверку на наличие имени роли в БД.
        inputDepartmentRole = "ROLE_DEP" + inputDepartmentRole.toUpperCase() + "_" + java.util.UUID.randomUUID().toString().split("-")[0];
        Role departmentRole = new Role(inputDepartmentRole);
        Department department = new Department(inputDepartment, inputAddress, inputEmail, inputTel, departmentRole, inputDescription, inputShortDescription);
        Department departmentFromDB = departmentRepository.findByName(inputDepartment);
        if(departmentFromDB != null){
            model.addAttribute("departmentNameError", "Данное структурное подразделение уже существует");
            return "admin/admin-addDepartment";
        }
        roleRepository.save(departmentRole);
        departmentRepository.save(department);
        return "redirect:/admin/all-departments";
    }
}
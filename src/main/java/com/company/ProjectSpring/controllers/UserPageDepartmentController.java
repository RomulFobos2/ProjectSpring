package com.company.ProjectSpring.controllers;

import com.company.ProjectSpring.models.Department;
import com.company.ProjectSpring.models.FormDocument;
import com.company.ProjectSpring.models.Service;
import com.company.ProjectSpring.repo.DepartmentRepository;
import com.company.ProjectSpring.repo.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class UserPageDepartmentController {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private ServiceRepository serviceRepository;

    @GetMapping("/all-departments")
    public String allDepartment(Model model) {
        Iterable<Department> departments = departmentRepository.findAll();
        model.addAttribute("allDepartments", departments);
        return "user/all-departments";
    }

    @GetMapping("/department-details/{id}")
    public String department_details(@PathVariable(value = "id") long id, Model model) {
        if(!departmentRepository.existsById(id)){
            return "redirect:/all-departments";
        }
        Optional<Department> department = departmentRepository.findById(id);
        ArrayList<Department> res = new ArrayList<>();
        department.ifPresent(res::add);
        Iterable<Service> services = department.get().getServiceList();
        model.addAttribute("allServices", services);
        model.addAttribute("department", res);
        return "user/department-details";
    }

    @GetMapping("department-details/service-details/{id}")
    public String service_details(@PathVariable(value = "id") long id, Model model) {
        if(!serviceRepository.existsById(id)){
            return "redirect:/user/all-departments";
        }
        Service service = serviceRepository.findById(id).orElseThrow();
        List<FormDocument> formDocuments = service.getFormDocumentList();
        model.addAttribute("formDocuments", formDocuments);
        model.addAttribute("service", service);
        return "user/service-details";
    }
}
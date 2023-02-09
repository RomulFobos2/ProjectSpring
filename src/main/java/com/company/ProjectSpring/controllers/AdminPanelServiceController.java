package com.company.ProjectSpring.controllers;

import com.company.ProjectSpring.models.Department;
import com.company.ProjectSpring.models.FormDocument;
import com.company.ProjectSpring.models.Service;
import com.company.ProjectSpring.repo.DepartmentRepository;
import com.company.ProjectSpring.repo.FormDocumentRepository;
import com.company.ProjectSpring.repo.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AdminPanelServiceController {
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private FormDocumentRepository formDocumentRepository;

    @GetMapping("/admin/all-services")
    public String allServices(Model model) {
        Iterable<Service> services = serviceRepository.findAll();
        model.addAttribute("allServices", services);
        return "admin/all-services";
    }

    @GetMapping("/admin/all-services/service-details/{id}")
    public String service_details(@PathVariable(value = "id") long id, Model model) {
        if(!serviceRepository.existsById(id)){
            return "redirect:/admin/all-services";
        }
//        Optional<Service> service = serviceRepository.findById(id);
//        ArrayList<Service> res = new ArrayList<>();
//        service.ifPresent(res::add);
//        model.addAttribute("service", res);
        Service service = serviceRepository.findById(id).orElseThrow();
        List<FormDocument> formDocuments = service.getFormDocumentList();
        model.addAttribute("formDocuments", formDocuments);
        model.addAttribute("service", service);
        return "admin/service-details";
    }

    @PostMapping("/admin/all-services/service-details/{id}/remove")
    public String delete_service(@PathVariable(value = "id") long id,Model model) {
        Service service = serviceRepository.findById(id).orElseThrow();
        serviceRepository.delete(service);
        return "redirect:/admin/all-services";
    }

    @GetMapping("/admin/admin-addService")
    public String addService(Model model) {
        Iterable<Department> departments = departmentRepository.findAll();
        model.addAttribute("allDepartments", departments);
        return "admin/admin-addService";
    }

    @PostMapping("/admin/admin-addService")
    public String addService(@RequestParam String inputService, @RequestParam Integer inputDuration, @RequestParam Long inputDepartment,
                             @RequestParam String inputDescription, @RequestParam String inputShortDescription, @RequestParam Boolean inputOnline, Model model) {
        Department department = departmentRepository.findById(inputDepartment).orElseThrow();
        Service service = new Service(inputService, inputDuration, department, inputDescription, inputShortDescription, inputOnline);
        for(Service var_service : department.getServiceList()){
            if(var_service.getServiceName().equals(inputService)){
                model.addAttribute("serviceNameError", "У выбранного подразделения уже имеется такая услуга");
                //Костыль или нет?
                Iterable<Department> departments = departmentRepository.findAll();
                model.addAttribute("allDepartments", departments);
                return "admin/admin-addService";
            }
        }
        serviceRepository.save(service);
        return "redirect:/admin/all-services";
    }

    @PostMapping("/admin/all-services/service-details/formDocument-details/{id}/remove")
    public String delete_formDocument(@PathVariable(value = "id") long id, Model model) {
        FormDocument formDocument = formDocumentRepository.findById(id).orElseThrow();
        formDocumentRepository.delete(formDocument);
        return "redirect:/admin/all-services";
    }
}

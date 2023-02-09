package com.company.ProjectSpring.controllers;

import com.company.ProjectSpring.models.Department;
import com.company.ProjectSpring.models.FormDocument;
import com.company.ProjectSpring.models.Service;
import com.company.ProjectSpring.models.User;
import com.company.ProjectSpring.repo.DepartmentRepository;
import com.company.ProjectSpring.repo.FormDocumentRepository;
import com.company.ProjectSpring.repo.ServiceRepository;
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
public class ManagerPanelServiceController {
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private FormDocumentRepository formDocumentRepository;

    @GetMapping("/manager/all-services")
    public String allServices(Model model) {
        User manager = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Department managerDepartment = userService.getDepartmentManager(manager);
        Iterable<Service> services = managerDepartment.getServiceList();
        model.addAttribute("allServices", services);
        return "manager/all-services";
    }

    @GetMapping("/manager/all-services/service-details/{id}")
    public String service_details(@PathVariable(value = "id") long id, Model model) {
        if (!serviceRepository.existsById(id)) {
            return "redirect:/manager/all-services";
        }
//        Optional<Service> service = serviceRepository.findById(id);
//        ArrayList<Service> res = new ArrayList<>();
//        service.ifPresent(res::add);
//        model.addAttribute("service", res);
        Service service = serviceRepository.findById(id).orElseThrow();
        List<FormDocument> formDocuments = service.getFormDocumentList();
        model.addAttribute("formDocuments", formDocuments);
        model.addAttribute("service", service);
        return "manager/service-details";
    }

    @PostMapping("/manager/all-services/service-details/{id}/remove")
    public String delete_service(@PathVariable(value = "id") long id, Model model) {
        Service service = serviceRepository.findById(id).orElseThrow();
        serviceRepository.delete(service);
        return "redirect:/manager/all-services";
    }

    @GetMapping("/manager/manager-addService")
    public String addService(Model model) {
        User manager = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Department department = userService.getDepartmentManager(manager);
        model.addAttribute("department", department);
        return "manager/manager-addService";
    }

    @PostMapping("/manager/manager-addService")
    public String addService(@RequestParam String inputService, @RequestParam Integer inputDuration,
                             @RequestParam String inputDescription, @RequestParam String inputShortDescription, @RequestParam Boolean inputOnline, Model model) {
        User manager = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Department department = userService.getDepartmentManager(manager);
        Service service = new Service(inputService, inputDuration, department, inputDescription, inputShortDescription, inputOnline);
        for (Service var_service : department.getServiceList()) {
            if (var_service.getServiceName().equals(inputService)) {
                model.addAttribute("serviceNameError", "У выбранного подразделения уже имеется такая услуга");
                model.addAttribute("department", department);
                return "manager/manager-addService";
            }
        }
        serviceRepository.save(service);
        return "redirect:/manager/all-services";
    }

    @GetMapping("/manager/editService/{id}")
    public String serviceEdit(@PathVariable(value = "id") long id, Model model) {
        if (!serviceRepository.existsById(id)) {
            return "redirect:/manager/all-services";
        }
        Service service = serviceRepository.findById(id).orElseThrow();
        User manager = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Department department = userService.getDepartmentManager(manager);
        model.addAttribute("department", department);
        model.addAttribute("service", service);
        return "manager/editService";
    }

    @PostMapping("/manager/editService/{id}")
    public String editService(@PathVariable(value = "id") long id, @RequestParam String inputService, @RequestParam Integer inputDuration,
                              @RequestParam String inputDescription, @RequestParam String inputShortDescription, Model model) {
        User manager = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Department department = userService.getDepartmentManager(manager);
        Service service = serviceRepository.findById(id).orElseThrow();
        service.setServiceName(inputService);
        service.setServiceDuration(inputDuration);
        service.setDescription(inputDescription);
        service.setShortDescription(inputShortDescription);
        serviceRepository.save(service);
        return "redirect:/manager/all-services";
    }

    @PostMapping("/manager/all-services/service-details/formDocument-details/{id}/remove")
    public String delete_formDocument(@PathVariable(value = "id") long id, Model model) {
        FormDocument formDocument = formDocumentRepository.findById(id).orElseThrow();
        formDocumentRepository.delete(formDocument);
        return "redirect:/manager/all-services";
    }

}

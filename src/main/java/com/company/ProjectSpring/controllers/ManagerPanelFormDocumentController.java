package com.company.ProjectSpring.controllers;

import com.company.ProjectSpring.models.*;
import com.company.ProjectSpring.repo.DocumentRepository;
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
import java.util.stream.Collectors;

@Controller
public class ManagerPanelFormDocumentController {
    @Autowired
    private FormDocumentRepository formDocumentRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/manager/all-formDocuments")
    public String allFormsDocuments(Model model) {
        User manager = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Department department = userService.getDepartmentManager(manager);
//        List<FormDocument> var_list = (List<FormDocument>) formDocumentRepository.findAll();
//        Iterable<FormDocument> formDocuments = var_list.stream().filter(x -> x.getService().getDepartment().getName().equals(department.getName())).collect(Collectors.toList());
        //Реализация через БД
        List<FormDocument> formDocuments = formDocumentRepository.findAllByDepartmentId(department.getId());
        model.addAttribute("allFormDocuments", formDocuments);
        return "manager/all-formDocuments";
    }

    @GetMapping("/manager/all-formDocuments/formDocument-details/{id}")
    public String formDocument_details(@PathVariable(value = "id") long id, Model model) {
        if (!formDocumentRepository.existsById(id)) {
            return "redirect:/manager/all-formDocuments";
        }
        Optional<FormDocument> formDocument = formDocumentRepository.findById(id);
        ArrayList<FormDocument> res = new ArrayList<>();
        formDocument.ifPresent(res::add);
        model.addAttribute("formDocument", res);
        return "manager/formDocument-details";
    }

    @PostMapping("/manager/all-formDocuments/formDocument-details/{id}/remove")
    public String delete_formDocument(@PathVariable(value = "id") long id, Model model) {
        FormDocument formDocument = formDocumentRepository.findById(id).orElseThrow();
        formDocumentRepository.delete(formDocument);
        return "redirect:/manager/all-formDocuments";
    }

    @GetMapping("manager/manager-addFormDocument")
    public String addFormDocument(Model model) {
        User manager = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Department department = userService.getDepartmentManager(manager);
        Iterable<Service> services = serviceRepository.findByDepartmentId(department.getId());
        model.addAttribute("allServices", services);
        Iterable<Document> documents = documentRepository.findAll();
        model.addAttribute("allDocuments", documents);
        return "manager/manager-addFormDocument";
    }

    @PostMapping("manager/manager-addFormDocument")
    public String addFormDocument(@RequestParam Long inputService, @RequestParam Long inputDocument, @RequestParam String inputFormDocument, Model model) {
        FormDocument formDocumentFromDB = formDocumentRepository.findUnique(inputService, inputDocument, inputFormDocument);
        if(formDocumentFromDB != null){
            Iterable<Document> documents = documentRepository.findAll();
            model.addAttribute("allDocuments", documents);
            User manager = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            Department department = userService.getDepartmentManager(manager);
            Iterable<Service> services = serviceRepository.findByDepartmentId(department.getId());
            model.addAttribute("allServices", services);

            model.addAttribute("formDocumentNameError", "Данная форма документа уже существует");
            return "manager/manager-addFormDocument";
        }
        Service service = serviceRepository.findById(inputService).orElseThrow();
        Document document = documentRepository.findById(inputDocument).orElseThrow();
        FormDocument formDocument = new FormDocument(service, document, inputFormDocument);
        formDocumentRepository.save(formDocument);
        return "redirect:/manager/all-formDocuments";
    }
}

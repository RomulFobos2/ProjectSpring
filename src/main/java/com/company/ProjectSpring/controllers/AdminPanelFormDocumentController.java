package com.company.ProjectSpring.controllers;

import com.company.ProjectSpring.models.Department;
import com.company.ProjectSpring.models.Document;
import com.company.ProjectSpring.models.FormDocument;
import com.company.ProjectSpring.models.Service;
import com.company.ProjectSpring.repo.DocumentRepository;
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
import java.util.Optional;

@Controller
public class AdminPanelFormDocumentController {
    @Autowired
    private FormDocumentRepository formDocumentRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private DocumentRepository documentRepository;

    @GetMapping("/admin/all-formDocuments")
    public String allFormsDocuments(Model model){
        Iterable<FormDocument> formDocuments = formDocumentRepository.findAll();
        model.addAttribute("allFormDocuments", formDocuments);
        return "admin/all-formDocuments";
    }

    @GetMapping("/admin/all-formDocuments/formDocument-details/{id}")
    public String formDocument_details(@PathVariable(value = "id") long id, Model model){
        if (!formDocumentRepository.existsById(id)){
            return "redirect:/admin/all-formDocuments";
        }
        Optional<FormDocument> formDocument = formDocumentRepository.findById(id);
        ArrayList<FormDocument> res = new ArrayList<>();
        formDocument.ifPresent(res::add);
        model.addAttribute("formDocument", res);
        return "admin/formDocument-details";
    }

    @PostMapping("/admin/all-formDocuments/formDocument-details/{id}/remove")
    public String delete_formDocument(@PathVariable(value = "id") long id, Model model){
        FormDocument formDocument = formDocumentRepository.findById(id).orElseThrow();
        formDocumentRepository.delete(formDocument);
        return "redirect:/admin/all-formDocuments";
    }

    @GetMapping("admin/admin-addFormDocument")
    public String addFormDocument(Model model){
        Iterable<Service> services = serviceRepository.findAll();
        model.addAttribute("allServices", services);
        Iterable<Document> documents = documentRepository.findAll();
        model.addAttribute("allDocuments", documents);
        return "admin/admin-addFormDocument";
    }

    @PostMapping("admin/admin-addFormDocument")
    public String addFormDocument(@RequestParam Long inputService, @RequestParam Long inputDocument, @RequestParam String inputFormDocument, Model model){
        Service service = serviceRepository.findById(inputService).orElseThrow();
        Document document = documentRepository.findById(inputDocument).orElseThrow();
        FormDocument formDocument = new FormDocument(service, document, inputFormDocument);
        formDocumentRepository.save(formDocument);
        return "redirect:/admin/all-formDocuments";
    }
}

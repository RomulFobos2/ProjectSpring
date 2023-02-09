package com.company.ProjectSpring.controllers;

import com.company.ProjectSpring.models.Document;
import com.company.ProjectSpring.repo.DocumentRepository;
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
public class ManagerPanelDocumentController {
    @Autowired
    private DocumentRepository documentRepository;

    @GetMapping("/manager/all-documents")
    public String allDocuments(Model model){
        Iterable<Document> documents = documentRepository.findAll();
        model.addAttribute("allDocuments", documents);
        return "manager/all-documents";
    }

    @GetMapping("/manager/all-documents/document-details/{id}")
    public String document_details(@PathVariable(value = "id") long id, Model model){
        if(!documentRepository.existsById(id)){
            return "redirect:/manager/all-documents";
        }
        Optional<Document> document = documentRepository.findById(id);
        ArrayList<Document> res = new ArrayList<>();
        document.ifPresent(res::add);
        model.addAttribute("document", res);
        return "manager/document-details";
    }

//    @PostMapping("/manager/all-documents/document-details/{id}/remove")
//    public String delete_document(@PathVariable(value = "id") long id, Model model){
//        Document document = documentRepository.findById(id).orElseThrow();
//        documentRepository.delete(document);
//        return "redirect:/manager/all-documents";
//    }

    @GetMapping("manager/manager-addDocument")
    public String addDocument(Model model){
        return "manager/manager-addDocument";
    }

    @PostMapping("manager/manager-addDocument")
    public String addDocument(@RequestParam String inputDocument, Model model){
        Document document = new Document(inputDocument);
        Document documentFromDB = documentRepository.findByName(inputDocument);
        if(documentFromDB != null){
            model.addAttribute("documentNameError", "Данный документ уже существует");
            return "manager/manager-addDocument";
        }
        documentRepository.save(document);
        return "redirect:/manager/all-documents";
    }
}

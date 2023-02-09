package com.company.ProjectSpring.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_document")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FormDocument> formDocumentList;

    public Document(String name) {
        this.name = name;
    }

    public Document() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FormDocument> getFormDocumentList() {
        return formDocumentList;
    }

    public void setFormDocumentList(List<FormDocument> formDocumentList) {
        this.formDocumentList = formDocumentList;
    }
}

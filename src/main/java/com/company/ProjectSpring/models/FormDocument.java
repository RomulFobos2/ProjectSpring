package com.company.ProjectSpring.models;

import javax.persistence.*;

@Entity
@Table(name = "t_formDocument")
public class FormDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;

    private String name; //Значения всего два "копия" и "оригинал"

    public FormDocument(Service service, Document document, String name) {
        this.service = service;
        this.document = document;
        this.name = name;
    }

    public FormDocument() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

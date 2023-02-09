package com.company.ProjectSpring.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean online;
    private String serviceName;
    private int serviceDuration; //длительность оказания услуги
    @Column(length = 10000)
    private String description; //Описание услуги
    @Column(length = 10000)
    private String shortDescription; //Краткое описание услуги
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appeal> appealList;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FormDocument> formDocumentList;

    public Service(String serviceName, int serviceDuration, Department department, String description, String shortDescription, boolean online) {
        this.serviceName = serviceName;
        this.serviceDuration = serviceDuration;
        this.department = department;
        this.description = description;
        this.shortDescription = shortDescription;
        this.online = online;
    }

    public Service() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getServiceDuration() {
        return serviceDuration;
    }

    public void setServiceDuration(int serviceDuration) {
        this.serviceDuration = serviceDuration;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Appeal> getAppealList() {
        return appealList;
    }

    public void setAppealList(List<Appeal> appealList) {
        this.appealList = appealList;
    }

    public List<FormDocument> getFormDocumentList() {
        return formDocumentList;
    }

    public void setFormDocumentList(List<FormDocument> formDocumentList) {
        this.formDocumentList = formDocumentList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}

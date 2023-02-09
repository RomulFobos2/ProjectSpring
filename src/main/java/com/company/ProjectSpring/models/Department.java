package com.company.ProjectSpring.models;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "t_department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String email;
    private String number;
    @Column(length = 10000)
    private String description; //Описание департамента
    @Column(length = 10000)
    private String shortDescription; //Описание департамента

    @OneToOne
    @JoinColumn(name = "role_id")
    private Role departmentRole;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Service> serviceList;

    public Department(String name, String address, String email, String number, Role departmentRole, String description, String shortDescription) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.number = number;
        this.departmentRole = departmentRole;
        this.description = description;
        this.shortDescription = shortDescription;
    }

    public Department() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
    }

    public Role getDepartmentRole() {
        return departmentRole;
    }

    public void setDepartmentRole(Role departmentRole) {
        this.departmentRole = departmentRole;
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
}

package com.company.ProjectSpring.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Сущность "Обращение"
 */
@Entity
@Table(name = "t_appeal")
public class Appeal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //id и он же номер обращения
    private Long id;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date dateAppeal;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date dateAnswer;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    private String password;

    @Column(length=100000)
    private byte[] file;

    private String textResponse;

    @Column(length=100000)
    private byte[] fileResponse;

    private String evaluation_Q1;
    private String evaluation_Q2;
    private String evaluation_Q3;
    private String evaluation_Q4;
    private String textComment_Q5;
    private String evaluationAverage;

    public Appeal(Date dateAppeal, Date dateAnswer, User user, Service service) {
        this.dateAppeal = dateAppeal;
        this.dateAnswer = dateAnswer;
        this.user = user;
        this.service = service;
    }

    public Appeal(Date dateAppeal, User user, Service service, String password, byte[] file) {
        this.dateAppeal = dateAppeal;
        this.user = user;
        this.service = service;
        this.password = password;
        this.file = file;
    }

    public Appeal() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateAppeal() {
        return dateAppeal;
    }

    public void setDateAppeal(Date dateAppeal) {
        this.dateAppeal = dateAppeal;
    }

    public Date getDateAnswer() {
        return dateAnswer;
    }

    public void setDateAnswer(Date dateAnswer) {
        this.dateAnswer = dateAnswer;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonIgnore
    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getTextResponse() {
        return textResponse;
    }

    public void setTextResponse(String textResponse) {
        this.textResponse = textResponse;
    }

    public byte[] getFileResponse() {
        return fileResponse;
    }

    public void setFileResponse(byte[] fileResponse) {
        this.fileResponse = fileResponse;
    }

    public String getEvaluation_Q1() {
        return evaluation_Q1;
    }

    public void setEvaluation_Q1(String evaluation_Q1) {
        this.evaluation_Q1 = evaluation_Q1;
    }

    public String getEvaluation_Q2() {
        return evaluation_Q2;
    }

    public void setEvaluation_Q2(String evaluation_Q2) {
        this.evaluation_Q2 = evaluation_Q2;
    }

    public String getEvaluation_Q3() {
        return evaluation_Q3;
    }

    public void setEvaluation_Q3(String evaluation_Q3) {
        this.evaluation_Q3 = evaluation_Q3;
    }

    public String getEvaluation_Q4() {
        return evaluation_Q4;
    }

    public void setEvaluation_Q4(String evaluation_Q4) {
        this.evaluation_Q4 = evaluation_Q4;
    }

    public String getTextComment_Q5() {
        return textComment_Q5;
    }

    public void setTextComment_Q5(String textComment_Q5) {
        this.textComment_Q5 = textComment_Q5;
    }

    public String getEvaluationAverage() {
        return evaluationAverage;
    }

    public void setEvaluationAverage(String evaluationAverage) {
        this.evaluationAverage = evaluationAverage;
    }
}

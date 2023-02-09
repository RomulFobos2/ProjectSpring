package com.company.ProjectSpring.controllers;

import com.company.ProjectSpring.models.Appeal;
import com.company.ProjectSpring.models.Role;
import com.company.ProjectSpring.models.User;
import com.company.ProjectSpring.repo.AppealRepository;
import com.company.ProjectSpring.repo.UserRepository;
import com.company.ProjectSpring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ManagerPanelAppealController {
    @Autowired
    private AppealRepository appealRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/manager/appeals-main")
    public String appeals(Model model) {
        return "/manager/appeals-main";
    }

    @GetMapping("/manager/all-appeals-znp")
    public String allAppeals(Model model){
        User manager = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Role roleManagerDept = userService.getRoleManagerDept(manager);
        //Выводим только те заявки, которые относятся к тому же департаменту, что и менеджер просматривающих заявки
        Iterable<Appeal> currentAppeals  = ((List<Appeal>) appealRepository.findAll()).stream().filter(x -> x.getDateAnswer() != null).filter(x -> x.getService().getDepartment().getDepartmentRole().equals(roleManagerDept)).filter(x -> (x.getDateAnswer().getTime() >= new Date().getTime())).collect(Collectors.toList());
        model.addAttribute("currentAppeals", currentAppeals);
        return "manager/all-appeals-znp";
    }

    @GetMapping("/manager/all-appealsOld-znp")
    public String allAppealsOld(Model model){
        User manager = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Role roleManagerDept = userService.getRoleManagerDept(manager);
        //Выводим только те заявки, которые относятся к тому же департаменту, что и менеджер просматривающих заявки
        Iterable<Appeal> oldAppeals  = ((List<Appeal>) appealRepository.findAll()).stream().filter(x -> x.getDateAnswer() != null).filter(x -> x.getService().getDepartment().getDepartmentRole().equals(roleManagerDept)).filter(x -> (x.getDateAnswer().getTime() < new Date().getTime())).collect(Collectors.toList());
        model.addAttribute("oldAppeals", oldAppeals);
        return "manager/all-appealsOld-znp";
    }

    @GetMapping("/manager/all-appeals-online")
    public String allAppealsOnline(Model model){
        User manager = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Role roleManagerDept = userService.getRoleManagerDept(manager);
        //Выводим только те заявки, которые относятся к тому же департаменту, что и менеджер просматривающих заявки
        Iterable<Appeal> currentAppeals  = ((List<Appeal>) appealRepository.findAll()).stream().filter(x -> x.getPassword() != null).filter(x -> x.getTextResponse() == null).filter(x -> x.getService().getDepartment().getDepartmentRole().equals(roleManagerDept)).collect(Collectors.toList());
        model.addAttribute("currentAppeals", currentAppeals);
        return "manager/all-appeals-online";
    }

    @GetMapping("/manager/all-appealsOld-online")
    public String allAppealsOldOnline(Model model){
        User manager = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Role roleManagerDept = userService.getRoleManagerDept(manager);
        //Выводим только те заявки, которые относятся к тому же департаменту, что и менеджер просматривающих заявки
        Iterable<Appeal> currentAppeals  = ((List<Appeal>) appealRepository.findAll()).stream().filter(x -> x.getPassword() != null).filter(x -> x.getTextResponse() != null).filter(x -> x.getService().getDepartment().getDepartmentRole().equals(roleManagerDept)).collect(Collectors.toList());
        model.addAttribute("currentAppeals", currentAppeals);
        return "manager/all-appealsOld-online";
    }

    @GetMapping("/manager/appeal-details/{id}")
    public String appeal_details(@PathVariable(value = "id") Long id, Model model){
        if(!appealRepository.existsById(id)){
            return "redirect:/manager/all-appeals";
        }
        Optional<Appeal> appeal = appealRepository.findById(id);
        ArrayList<Appeal> res = new ArrayList<>();
        appeal.ifPresent(res::add);
        model.addAttribute("appeal", res);
        return "manager/appeal-details";
    }

    @GetMapping("/manager/download/{id}")
    public ResponseEntity<Object> downloadFile(@PathVariable(value = "id") Long id, Model model) throws Exception{
        Appeal appeal = appealRepository.findById(id).orElseThrow();
        InputStreamResource resource  = new InputStreamResource(new ByteArrayInputStream(appeal.getFile()));
        HttpHeaders headers = new HttpHeaders();
        String nameFileDownload = appeal.getUser().getUsername() + "." + appeal.getId() + ".zip";
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", nameFileDownload));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        ResponseEntity<Object>
                responseEntity = ResponseEntity.ok().headers(headers).contentLength(appeal.getFile().length).contentType(
                MediaType.parseMediaType("application/txt")).body(resource);
        return responseEntity;
    }

    //Первый вариант скачивания. Когда просто помещаем файл на комп.
//    @GetMapping("/manager/download/{id}")
//    public String downloadFile(@PathVariable(value = "id") Long id, Model model){
//        System.out.println("Скачиваем файл");
//        Appeal appeal = appealRepository.findById(id).orElseThrow();
//        try {
//            FileOutputStream fileOutputStream = new FileOutputStream(new File("download"));
//            fileOutputStream.write(appeal.getFile());
//            fileOutputStream.flush();
//            fileOutputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "redirect:manager/appeal-details";
//    }
}

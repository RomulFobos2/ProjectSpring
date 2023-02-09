package com.company.ProjectSpring.controllers;

import com.company.ProjectSpring.models.*;
import com.company.ProjectSpring.repo.*;
import com.company.ProjectSpring.service.HtmlLetter;
import com.company.ProjectSpring.service.MailSender;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.inputstream.ZipInputStream;
import net.lingala.zip4j.io.outputstream.ZipOutputStream;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

//https://devmark.ru/article/spring-boot-hibernate?from=/articles/tag/spring-boot?page%3D1
@Controller
public class UserPageAppealController {
    @Autowired
    private AppealRepository appealRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private FormDocumentRepository formDocumentRepository;
    @Autowired
    private MailSender mailSender;

    @GetMapping("/service-main")
    public String serviceMain(Model model) {
        return "user/service-main";
    }

    @GetMapping("/appeals")
    public String appeal(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        List<Appeal> appealsWithoutQuest = user.getAppealList();
        appealsWithoutQuest = appealsWithoutQuest.stream().filter(x -> x.getEvaluationAverage() == null).collect(Collectors.toList());
        model.addAttribute("count", appealsWithoutQuest.size());
        return "user/appeals";
    }

    @GetMapping("/appeals-main-znp")
    public String appealMain(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        Iterable<Appeal> currentAppeal = user.getAppealList().stream().filter(x -> x.getDateAnswer() != null).filter(x -> (x.getDateAnswer().getTime() >= new Date().getTime())).collect(Collectors.toList());
        model.addAttribute("currentAppeal", currentAppeal);
        return "user/appeals-main-znp";
    }

    @GetMapping("/appeals-old-znp")
    public String appealOld(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        //Ввводим только те заявки, время у которых меньше текущей даты
        Iterable<Appeal> oldAppeal = user.getAppealList().stream().filter(x -> x.getDateAnswer() != null).filter(x -> (x.getDateAnswer().getTime() < new Date().getTime())).collect(Collectors.toList());
        model.addAttribute("oldAppeal", oldAppeal);
        return "user/appeals-old-znp";
    }

    @GetMapping("/appeals-main-online")
    public String appealMainOnline(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        Iterable<Appeal> currentAppeal = user.getAppealList().stream().filter(x -> x.getPassword() != null).filter(x -> x.getTextResponse() == null).collect(Collectors.toList());
        model.addAttribute("currentAppeal", currentAppeal);
        return "user/appeals-main-online";
    }

    @GetMapping("/appeals-old-online")
    public String appealOldOnline(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        Iterable<Appeal> currentAppeal = user.getAppealList().stream().filter(x -> x.getPassword() != null).filter(x -> x.getTextResponse() != null).collect(Collectors.toList());
        model.addAttribute("currentAppeal", currentAppeal);
        return "user/appeals-old-online";
    }

    @GetMapping("/appeals-main-without")
    public String appealWithoit(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        List<Appeal> appealsWithoutQuest = user.getAppealList();
        appealsWithoutQuest = appealsWithoutQuest.stream().filter(x -> x.getEvaluationAverage() == null).collect(Collectors.toList());
        model.addAttribute("currentAppeal", appealsWithoutQuest);
        return "user/appeals-main-without";
    }

    @GetMapping("/appeal-add")
    public String appealAdd(Model model) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        //Убрал реализацию добавления моделатрибут через Iterable, сделал все ArrayList
        //Надеюсь не скажется на работе)
        //Iterable<Service> services = serviceRepository.findAll();
        ArrayList<Service> services = (ArrayList<Service>) serviceRepository.findOffline();
        model.addAttribute("allServices", services);
        //Iterable<Department> departments = departmentRepository.findAll();
        ArrayList<Department> departments = (ArrayList<Department>) departmentRepository.findAll();
        model.addAttribute("allDepartments", departments);
//        ArrayList<Department> arrayListDepartments = (ArrayList<Department>) departments;
//        model.addAttribute("arrayListDepartments", arrayListDepartments);
        ArrayList<Appeal> arrayListAppeals = (ArrayList<Appeal>) ((ArrayList<Appeal>) appealRepository.findAll()).stream().filter(x -> x.getDateAnswer() != null).collect(Collectors.toList());
        model.addAttribute("arrayListAppeals", arrayListAppeals);
        //Массив с датами запесей конкретного пользователя
        ArrayList<Date> userDateOfAppeals = (ArrayList<Date>) ((ArrayList<Date>) appealRepository.findDateByUserId(user.getId())).stream().filter(x -> x != null).collect(Collectors.toList());
        model.addAttribute("userDateOfAppeals", userDateOfAppeals);
        ArrayList<FormDocument> formDocuments = (ArrayList<FormDocument>) formDocumentRepository.findAll();
        model.addAttribute("formDocuments", formDocuments);
        return "user/appeal-add";
    }

    @PostMapping("/appeal-add")
    public String appealAdd(@RequestParam Long inputService, @RequestParam String StrDateAnswer, @RequestParam String inputTime, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        if (user == null) {
            return "login";
        }
//        SimpleDateFormat simpleDateFormat_inner = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault());
        SimpleDateFormat simpleDateFormat_inner = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        Date dateAnswer = null;
        try {
            dateAnswer = simpleDateFormat_inner.parse(StrDateAnswer + " " + inputTime);
        } catch (ParseException e) {

        }
        Service service = serviceRepository.findById(inputService).orElseThrow();
        Appeal appeal = new Appeal(new Date(), dateAnswer, user, service);
        mailSender.sendHTML(user.getUsername(), "Запись на прием", HtmlLetter.getLetterTextZNP(user, appeal));
        appealRepository.save(appeal);
        return "redirect:/appeals-main-znp";
    }

    @GetMapping("/appeal-add-online")
    public String appealAddOnline(Model model) {
        ArrayList<Service> services = (ArrayList<Service>) serviceRepository.findOnline();
        model.addAttribute("allServices", services);
        ArrayList<Department> departments = (ArrayList<Department>) departmentRepository.findAll();
        model.addAttribute("allDepartments", departments);
        ArrayList<FormDocument> formDocuments = (ArrayList<FormDocument>) formDocumentRepository.findAll();
        model.addAttribute("formDocuments", formDocuments);
        return "user/appeal-add-online";
    }

    //Старый способ записи зашифрованного архива в БД. Использовался жесткий диск в качестве посредника.
//    @PostMapping("/appeal-add-online")
//    public String appealAddOnline(@RequestParam Long inputService, @RequestParam("inputFile") List<MultipartFile> files) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = userRepository.findByUsername(auth.getName());
//        String codeToAppeal = java.util.UUID.randomUUID().toString().split("-")[0] + java.util.UUID.randomUUID().toString().split("-")[0];
//        if (user == null) {
//            return "login";
//        }
//        try {
//            ZipParameters zipParameters = new ZipParameters();
//            zipParameters.setCompressionMethod(CompressionMethod.DEFLATE);
//            zipParameters.setCompressionLevel(CompressionLevel.NORMAL);
//            zipParameters.setEncryptFiles(true);
//            zipParameters.setEncryptionMethod(EncryptionMethod.AES);
//            zipParameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);
//            ZipFile zipFile = new ZipFile(user.getUsername() + ".zip", codeToAppeal.toCharArray());
//            for(MultipartFile file : files){
//                if (!file.isEmpty()){
//                    zipParameters.setFileNameInZip(file.getOriginalFilename());
//                    zipFile.addStream(file.getInputStream(), zipParameters);
//                }
//            }
//            FileInputStream fileInputStream = new FileInputStream(new File(user.getUsername() + ".zip"));
//            Service service = serviceRepository.findById(inputService).orElseThrow();
//            Appeal appeal = new Appeal(new Date(), user, service, codeToAppeal, fileInputStream.readAllBytes());
//            appealRepository.save(appeal);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "redirect:/appeals-main-online";
//    }

    @PostMapping("/appeal-add-online")
    public String appealAddOnline(@RequestParam Long inputService, @RequestParam("inputFile") List<MultipartFile> files) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        String codeToAppeal = java.util.UUID.randomUUID().toString().split("-")[0] + java.util.UUID.randomUUID().toString().split("-")[0];
        if (user == null) {
            return "login";
        }
        try {
            ZipParameters zipParameters = new ZipParameters();
            zipParameters.setCompressionMethod(CompressionMethod.DEFLATE);
            zipParameters.setCompressionLevel(CompressionLevel.NORMAL);
            zipParameters.setEncryptFiles(true);
            zipParameters.setEncryptionMethod(EncryptionMethod.AES);
            zipParameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ZipOutputStream zout = new ZipOutputStream(bo, codeToAppeal.toCharArray());
            for(MultipartFile file : files){
                if(!file.isEmpty()){
                    zipParameters.setFileNameInZip(file.getOriginalFilename());
                    zout.putNextEntry(zipParameters);
                    zout.write(file.getBytes());
                    zout.closeEntry();
                }
            }
            zout.close();
            Service service = serviceRepository.findById(inputService).orElseThrow();
            Appeal appeal = new Appeal(new Date(), user, service, codeToAppeal, bo.toByteArray());
            mailSender.sendHTML(user.getUsername(), "Получение онлайн услуги", HtmlLetter.getLetterTextOnline(user, appeal));
            appealRepository.save(appeal);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/appeals-main-online";
    }

    @GetMapping("/appeal-details/{id}")
    public String appealDetails(@PathVariable(value = "id") long id, Model model) {
        if (!appealRepository.existsById(id)) {
            return "redirect:/appeal-main";
        }
        Optional<Appeal> appeal = appealRepository.findById(id);
        ArrayList<Appeal> res = new ArrayList<>();
        appeal.ifPresent(res::add);
        model.addAttribute("appeal", res);
        return "user/appeal-details";
    }

    @PostMapping("/appeal-details/{id}/remove")
    public String delete_user(@PathVariable(value = "id") long id, Model model) {
        Appeal appeal = appealRepository.findById(id).orElseThrow();
        appealRepository.delete(appeal);
        return "redirect:/appeals";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Object> downloadFile(@PathVariable(value = "id") Long id, Model model) throws Exception{
        Appeal appeal = appealRepository.findById(id).orElseThrow();
        InputStreamResource resource  = new InputStreamResource(new ByteArrayInputStream(appeal.getFileResponse()));
        HttpHeaders headers = new HttpHeaders();
        String nameFileDownload = appeal.getUser().getUsername() + "." + appeal.getId() + ".zip";
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", nameFileDownload));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        ResponseEntity<Object>
                responseEntity = ResponseEntity.ok().headers(headers).contentLength(appeal.getFileResponse().length).contentType(
                MediaType.parseMediaType("application/txt")).body(resource);
        return responseEntity;
    }

}
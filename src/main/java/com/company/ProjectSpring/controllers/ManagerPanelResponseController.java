package com.company.ProjectSpring.controllers;

import com.company.ProjectSpring.models.Appeal;
import com.company.ProjectSpring.repo.AppealRepository;
import com.company.ProjectSpring.repo.UserRepository;
import com.company.ProjectSpring.service.HtmlLetter;
import com.company.ProjectSpring.service.MailSender;
import com.company.ProjectSpring.service.UserService;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.io.outputstream.ZipOutputStream;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@Controller
public class ManagerPanelResponseController {
    @Autowired
    private AppealRepository appealRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private MailSender mailSender;

    @GetMapping("/manager/manager-addResponse/{id}")
    public String addResponse(@PathVariable(value = "id") Long id, Model model) throws Exception{
        Appeal appeal = appealRepository.findById(id).orElseThrow();
        model.addAttribute("appeal", appeal);
        return "manager/manager-addResponse";
    }

    //Старый метод.
//    @PostMapping("/manager/manager-addResponse")
//    public String addResponse(@RequestParam Long appealID, @RequestParam String inputResponse, @RequestParam("inputFileOfResponse") List<MultipartFile> files, Model model) throws Exception{
//        Appeal appeal = appealRepository.findById(appealID).orElseThrow();
//        appeal.setTextResponse(inputResponse);
//
//        ZipParameters zipParameters = new ZipParameters();
//        zipParameters.setCompressionMethod(CompressionMethod.DEFLATE);
//        zipParameters.setCompressionLevel(CompressionLevel.NORMAL);
//        zipParameters.setEncryptFiles(true);
//        zipParameters.setEncryptionMethod(EncryptionMethod.AES);
//        zipParameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);
//        ZipFile zipFile = new ZipFile(appeal.getUser().getUsername() + "Res.zip", appeal.getPassword().toCharArray());
//        for(MultipartFile file : files){
//            if (!file.isEmpty()){
//                zipParameters.setFileNameInZip(file.getOriginalFilename());
//                zipFile.addStream(file.getInputStream(), zipParameters);
//            }
//        }
//        FileInputStream fileInputStream = new FileInputStream(new File(appeal.getUser().getUsername() + "Res.zip"));
//        appeal.setFileResponse(fileInputStream.readAllBytes());
//        appealRepository.save(appeal);
//        return "redirect:/manager/all-appeals-online";
//    }

    @PostMapping("/manager/manager-addResponse")
    public String addResponse(@RequestParam Long appealID, @RequestParam String inputResponse, @RequestParam("inputFileOfResponse") List<MultipartFile> files, Model model) throws Exception{
        Appeal appeal = appealRepository.findById(appealID).orElseThrow();
        appeal.setTextResponse(inputResponse);

        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setCompressionMethod(CompressionMethod.DEFLATE);
        zipParameters.setCompressionLevel(CompressionLevel.NORMAL);
        zipParameters.setEncryptFiles(true);
        zipParameters.setEncryptionMethod(EncryptionMethod.AES);
        zipParameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);

        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ZipOutputStream zout = new ZipOutputStream(bo, appeal.getPassword().toCharArray());

        for(MultipartFile file : files){
            if (!file.isEmpty()){
                zipParameters.setFileNameInZip(file.getOriginalFilename());
                zout.putNextEntry(zipParameters);
                zout.write(file.getBytes());
                zout.closeEntry();
            }
        }
        zout.close();
        appeal.setFileResponse(bo.toByteArray());
        String textLetter = "Вы получили ответ на услугу " + appeal.getService().getServiceName() + ". Зайдите в личный кабинет, чтобы прочитать его.";
        mailSender.sendHTML(appeal.getUser().getUsername(), "Ответ на заявку", HtmlLetter.getLetterTextManager(appeal));
        appealRepository.save(appeal);
        return "redirect:/manager/all-appeals-online";
    }


}

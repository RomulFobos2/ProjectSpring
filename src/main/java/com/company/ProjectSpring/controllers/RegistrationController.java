package com.company.ProjectSpring.controllers;

import com.company.ProjectSpring.models.User;
import com.company.ProjectSpring.service.OTPGenerator;
import com.company.ProjectSpring.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Контроллер для регистрации пользователей
 */
@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute(new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam String inputLastName, @RequestParam String inputFirstName, @RequestParam String inputPatronymicName, @RequestParam String inputSex, @RequestParam String username, @RequestParam String password, @RequestParam String passwordConfirm, Model model, HttpServletRequest request) {
        User user = new User(inputLastName, inputFirstName, inputPatronymicName, inputSex, username, password, passwordConfirm);
        if(userService.chekUserName(username)){
        //if(!userService.saveUser(user)){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";
        }
        if(!user.getPassword().equals(user.getPasswordConfirm())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }

        String oneTimePassword = OTPGenerator.getOneTimePassword();
        userService.sendOneTimePasswordMail(username, oneTimePassword);
        //userService.sendOneTimePasswordSMS(username, oneTimePassword);

        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute("oneTimePassword", oneTimePassword);
        return "redirect:/login-activate";
    }


    @GetMapping("/login-activate")
    public String user_activation(Model model, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("user", user);
        return "login-activate";
    }

    @PostMapping("/login-activate")
    public String chek_user_activation(@RequestParam String code, HttpServletRequest request, Model model){
        User user = (User) request.getSession().getAttribute("user");
        //Если пользователь ввел код отправленный ему на почту верно, то сохраняем его в БД
        if(code.equals((String) request.getSession().getAttribute("oneTimePassword"))){
            userService.saveUser(user);
            return "redirect:/login";
        }
        else {
            model.addAttribute("codeError", "Неверно введен код подтверждения");
            //Почему то в model attribute теряется объект user с предудщей его подгрузки. Пришлось дополнительно добавлять его в этой части
            model.addAttribute("user", user);
            return "login-activate";
        }
    }
}
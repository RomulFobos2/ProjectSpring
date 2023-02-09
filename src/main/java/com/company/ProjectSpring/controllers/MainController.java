package com.company.ProjectSpring.controllers;

import com.company.ProjectSpring.models.User;
import com.company.ProjectSpring.repo.UserRepository;
import com.company.ProjectSpring.service.OTPGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Контроллер для отображения страниц доступных без авторизации
 */
@Controller
public class MainController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная страница");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "О нас");
        return "about";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        return "admin/admin";
    }

    @GetMapping("/manager")
    public String manager(Model model) {
        return "manager/manager";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Вход");
        return "login";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        User currentUser = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("currentUser", currentUser);
        return "profile";
    }

    @GetMapping("/change-password")
    public String changePassword(Model model) {
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String passwordCurrent, @RequestParam String passwordNew, @RequestParam String passwordConfirm, Model model) {
        User currentUser = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if(bCryptPasswordEncoder.matches(passwordCurrent, currentUser.getPassword())){
            if (passwordNew.equals(passwordConfirm)) {
                currentUser.setPassword(bCryptPasswordEncoder.encode(passwordNew));
                userRepository.save(currentUser);
            }
            else {
                model.addAttribute("passwordError", "Пароли не совпадают. Потдвердите новый пароль верно");
                return "change-password";
            }
        }
        else {
            model.addAttribute("passwordError", "Текущий пароль введен неверно");
            return "change-password";
        }
        return "redirect:/logout";
    }
}
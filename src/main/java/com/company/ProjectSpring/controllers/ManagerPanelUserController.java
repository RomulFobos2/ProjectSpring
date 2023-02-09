package com.company.ProjectSpring.controllers;

import com.company.ProjectSpring.models.User;
import com.company.ProjectSpring.repo.UserRepository;
import com.company.ProjectSpring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ManagerPanelUserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/manager/all-users")
    public String userList(Model model) {
        //Получаем пользователя, под которым выполнен вход (страница доступна только апдмину, соответсвенно пользователь будет только с ролью админа.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //Получаем список всех пользователей, для отображения на странице
        List<User> var_list = userService.allUsers();
        //Оставляем только пользователей у которых имеется роль USER.
        Iterable<User> users = var_list.stream().filter(x -> x.getRoles().stream().allMatch(y -> y.getName().equals("ROLE_USER"))).collect(Collectors.toList());
        model.addAttribute("allUsers", users);
        return "manager/all-users";
    }

    //Формируем динамически страницу для каждого пользователя. Внутри страницы можно сделать операции над пользователем
    @GetMapping("/manager/all-users/user-details/{id}")
    public String userDetails(@PathVariable(value = "id") long id, Model model) {
        if (!userRepository.existsById(id)) {
            return "redirect:/manager/all-users";
        }
        Optional<User> user = userRepository.findById(id);
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("user", res);
        return "manager/user-details";
    }
}

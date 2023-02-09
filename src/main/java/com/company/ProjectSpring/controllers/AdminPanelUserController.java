package com.company.ProjectSpring.controllers;

import com.company.ProjectSpring.models.Role;
import com.company.ProjectSpring.models.User;
import com.company.ProjectSpring.repo.RoleRepository;
import com.company.ProjectSpring.repo.UserRepository;
import com.company.ProjectSpring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Контроллер для отображения страниц для администратора
 */
@Controller
public class AdminPanelUserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/admin/all-users")
    public String userList(Model model) {
        //Получаем пользователя, под которым выполнен вход (страница доступна только апдмину, соответсвенно пользователь будет только с ролью админа.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //Получаем списко всех пользователей, для отображения на странице
        List<User> var_list = userService.allUsers();
        //Удаляем текущего пользователя из списка для отображения, чтобы он не мог удалить сам себя
        var_list.remove(userRepository.findByUsername(auth.getName()));
        //Оставляем только пользователей у которых имеется роль USER.
        Iterable<User> users = var_list.stream().filter(x -> x.getRoles().stream().anyMatch(y -> y.getName().equals("ROLE_USER"))).collect(Collectors.toList());
        model.addAttribute("allUsers", users);
        return "admin/all-users";
    }

    //Формируем динамически страницу для каждого пользователя. Внутри страницы можно сделать операции над пользователем
    @GetMapping("/admin/all-users/user-details/{id}")
    public String userDetails(@PathVariable(value = "id") long id, Model model) {
        if (!userRepository.existsById(id)) {
            return "redirect:/admin/all-users";
        }
        Optional<User> user = userRepository.findById(id);
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("user", res);
        //Создаем объект var_AdminRole и добавляем его в атрибуты страницы.
        //чтобы потом определять пользователей у которого есть такая роль и не выводить кнопку "Сделать администратором" на странице user-details
        Role var_AdminRole = new Role(2L, "ROLE_ADMIN");
        model.addAttribute("var_AdminRole", var_AdminRole);
        return "admin/user-details";
    }

//    Метод для добавления роли Admin пользователю.
//    Не испльзуется. НЕ удалять
    @PostMapping("/admin/all-users/user-details/{id}/add_admin")
    public String add_admin(@PathVariable(value = "id") long id,Model model) {
        User user = userRepository.findById(id).orElseThrow();
        user.getRoles().add(new Role(2L, "ROLE_ADMIN"));
        userRepository.save(user);
        return "redirect:/admin/all-users";
    }

    //Метод для удаления пользователя.
    //Не испльзуется. НЕ удалять
    @PostMapping("/admin/all-users/user-details/{id}/remove")
    public String delete_user(@PathVariable(value = "id") long id,Model model) {
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
        return "redirect:/admin/all-users";
    }
}
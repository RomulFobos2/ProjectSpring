package com.company.ProjectSpring.controllers;

import com.company.ProjectSpring.models.Department;
import com.company.ProjectSpring.models.Role;
import com.company.ProjectSpring.models.User;
import com.company.ProjectSpring.repo.DepartmentRepository;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class AdminPanelManagerController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("/admin/all-managers")
    public String userList(Model model) {
        //Получаем пользователя, под которым выполнен вход (страница доступна только апдмину, соответсвенно пользователь будет только с ролью админа.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //Получаем списко всех пользователей, для отображения на странице
        List<User> var_list = userService.allUsers();
        //Удаляем текущего пользователя из списка для отображения, чтобы он не мог удалить сам себя
        var_list.remove(userRepository.findByUsername(auth.getName()));
        //Оставляем только пользователей у которых имеется роль Manager.
        Iterable<User> managers = var_list.stream().filter(x -> x.getRoles().stream().anyMatch(y -> y.getName().equals("ROLE_MANAGER"))).collect(Collectors.toList());
        model.addAttribute("allManagers", managers);
        return "admin/all-managers";
    }

    @GetMapping("/admin/admin-addManager")
    public String addDepartment(Model model) {
        Iterable<Department> departments = departmentRepository.findAll();
        model.addAttribute("allDepartments", departments);
        return "admin/admin-addManager";
    }

    @PostMapping("/admin/admin-addManager")
    public String addService(@RequestParam String managerName, @RequestParam String password, @RequestParam String passwordConfirm, @RequestParam Long inputDepartment, Model model) {
        Department department = departmentRepository.findById(inputDepartment).orElseThrow();
        User manager = new User(managerName, password, passwordConfirm);
        if(userService.chekUserName(managerName)){
            model.addAttribute("managerNameError", "Пользователь с таким именем уже существует");
            Iterable<Department> departments = departmentRepository.findAll();
            model.addAttribute("allDepartments", departments);
            return "admin/admin-addManager";
        }
        if(!manager.getPassword().equals(manager.getPasswordConfirm())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            Iterable<Department> departments = departmentRepository.findAll();
            model.addAttribute("allDepartments", departments);
            return "admin/admin-addManager";
        }
        userService.saveManager(manager, department.getDepartmentRole());
        return "redirect:/admin/all-managers";
    }

    //Формируем динамически страницу для каждого пользователя. Внутри страницы можно сделать операции над пользователем
    @GetMapping("/admin/all-managers/manager-details/{id}")
    public String userDetails(@PathVariable(value = "id") long id, Model model) {
        if (!userRepository.existsById(id)) {
            return "redirect:/admin/all-managers";
        }
        Optional<User> manager = userRepository.findById(id);
        ArrayList<User> res = new ArrayList<>();
        manager.ifPresent(res::add);
        model.addAttribute("manager", res);
        //Создаем объект var_AdminRole и добавляем его в атрибуты страницы.
        //чтобы потом определять пользователей у которого есть такая роль и не выводить кнопку "Сделать администратором" на странице user-details
        Role var_AdminRole = new Role(2L, "ROLE_ADMIN");
        model.addAttribute("var_AdminRole", var_AdminRole);
        return "admin/manager-details";
    }

    @PostMapping("/admin/all-managers/manager-details/{id}/add_admin")
    public String add_admin(@PathVariable(value = "id") long id,Model model) {
        User manager = userRepository.findById(id).orElseThrow();
        manager.getRoles().add(new Role(2L, "ROLE_ADMIN"));
        userRepository.save(manager);
        return "redirect:/admin/all-managers";
    }

    @PostMapping("/admin/all-managers/manager-details/{id}/remove")
    public String delete_user(@PathVariable(value = "id") long id,Model model) {
        User manager = userRepository.findById(id).orElseThrow();
        userRepository.delete(manager);
        return "redirect:/admin/all-managers";
    }
}

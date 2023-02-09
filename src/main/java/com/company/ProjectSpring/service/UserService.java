package com.company.ProjectSpring.service;

import com.company.ProjectSpring.models.Department;
import com.company.ProjectSpring.models.Role;
import com.company.ProjectSpring.models.User;
import com.company.ProjectSpring.repo.DepartmentRepository;
import com.company.ProjectSpring.repo.RoleRepository;
import com.company.ProjectSpring.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private MailSender mailSender;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return false;
        }
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean saveManager(User user, Role role) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return false;
        }
        //user.setRoles(Collections.singleton(new Role(3L, "ROLE_MANAGER")));
        user.setRoles(new HashSet<Role>());
        user.getRoles().add(new Role(3L, "ROLE_MANAGER"));
        user.getRoles().add(role);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    //Проверяем свободно имя или нет.
    public boolean chekUserName(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return false;
        }
        return true;
    }

    public void sendOneTimePasswordMail(String username, String oneTimePassword){
        String message = String.format("Здравствуйте." +" Ваш одноразовый пароль: " + oneTimePassword);
        mailSender.send(username, "Код активации", message);
    }

    public void sendOneTimePasswordSMS(String username, String oneTimePassword){
        String message = String.format("Здравствуйте. Ваш одноразовый пароль: " + oneTimePassword);
        SmsSender.sendMessage(username, message);
    }

    //Получение роли у мэнэджера, которая относится к департаменту
    public Role getRoleManagerDept(User manager){
        Role result = null;
        for(Role role : manager.getRoles()){
            String str_role = role.getName().substring(0, 8);
            if (str_role.equals("ROLE_DEP")){
                result = role;
            }
        }
        return result;
    }

    public Department getDepartmentManager(User manager){
        Department result = null;
        Role roleManagerDept = getRoleManagerDept(manager);
        List<Department> var_list = (List<Department>) departmentRepository.findAll();
        result = var_list.stream().filter(x -> x.getDepartmentRole().equals(roleManagerDept)).findFirst().orElseThrow();
        return result;
    }
}
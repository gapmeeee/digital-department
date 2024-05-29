package com.example.demo.services;


import com.example.demo.moduls.User;
import com.example.demo.moduls.enums.Role;
import com.example.demo.repositoties.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder paswordEncoder;
    public boolean createUser(User user){
        String email = user.getEmail();
        if (userRepository.findByEmail(email)!= null) return false;
        user.setActive(true);
        user.setPassword(paswordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_ADMIN);
        log.info("Saving new User with Email: {}", email);
        userRepository.save(user);
        return true;
    }
    public List<User> list(){
        return userRepository.findAll();
    }

    public void banUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user!=null){
            if(user.isActive()){
                user.setActive(false);
            }
            else {
                user.setActive(true);
            }
            log.info("Ban user with id = {}; email: {}", user.getId(), user.getEmail());
        }
        userRepository.save(user);

    }

    public void changeUserRoles(User user, String role) {
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        System.out.println("Attempting to change role to: " + role);
        user.getRoles().clear();
        if (roles.contains(role)) {
            user.getRoles().clear();  // Очищаем текущие роли пользователя
            user.getRoles().add(Role.valueOf(role));  // Добавляем новую роль
            System.out.println("Role changed to: " + role);
        }
        userRepository.save(user);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal==null){
            return new User();
        }
        return userRepository.findByEmail(principal.getName());
    }

}

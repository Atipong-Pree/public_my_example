package com.example.securityjwt;

import com.example.securityjwt.entity.Role;
import com.example.securityjwt.entity.User;
import com.example.securityjwt.model.ERole;
import com.example.securityjwt.model.SignUpRequestBody;
import com.example.securityjwt.repository.RoleRepository;
import com.example.securityjwt.sevice.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Test
    @Rollback
    public void testAddUser() {
        User user = new User();

        user.setUsername("test4");
        user.setEmail("test4@gmail.com");
        user.setPassword(passwordEncoder.encode("123456"));
        Role role = roleRepository.findByName(ERole.ROLE_USER).orElse(new Role());
        Set<Role> roles = new HashSet<Role>();
        roles.add(role);
        user.setRoles(roles);
        User newUser = userService.addUser(user);
        assertThat(newUser.getId()).isGreaterThan(1);
    }

    @Test
    public void testGetUserById() {

        long userId = 1;
        User existUser = userService.getUserById(userId);
        assertThat(existUser).isNotNull();
        assertThat(existUser.getId()).isEqualTo(userId);

    }

    @Test
    public void testDeleteUserById() {
        long userId = 5;
        Boolean result = userService.deleteUserById(userId);
        assertTrue(result);
    }

    @Test
    public void testEditUser() {
        long userId = 4;
        User existUser = userService.getUserById(userId);
        String newEmail = "testedit@gmail.com";

        existUser.setEmail(newEmail);
        User updateUser = userService.updateUser(existUser);
        assertThat(updateUser.getEmail()).isEqualTo(newEmail);

    }


    @Test
    public void testGetNewPassword() {
        String strPass = "123456";
        String encode1 = userService.createdPasswordEncode(strPass);
        //String encode2 = passwordEncoder.encode(strPass);

        System.out.println("encode1 : "+encode1);
        //System.out.println("encode2 : "+encode2);

        assertTrue(passwordEncoder.matches(strPass,encode1));

    }


}

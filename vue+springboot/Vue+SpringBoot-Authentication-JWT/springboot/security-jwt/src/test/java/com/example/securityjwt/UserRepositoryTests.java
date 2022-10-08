package com.example.securityjwt;

import com.example.securityjwt.entity.Role;
import com.example.securityjwt.entity.User;
import com.example.securityjwt.repository.RoleRepository;
import com.example.securityjwt.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void testCreateUser(){

        //User user = new User("john","john@gmail.com",passwordEncoder().encode("123456"));
        //User user = new User("jack","jack@gmail.com",passwordEncoder().encode("123456"));
        User user = new User("jane","jane@gmail.com",passwordEncoder().encode("123456"));
        User newUser = userRepository.save(user);
        assertThat(newUser).isNotNull();

    }

    @Test
    public void testCreateUsers(){

        List<User> userList = new ArrayList<>();

        userList.add(new User("john","john@gmail.com",passwordEncoder().encode("123456")));
        userList.add(new User("jack","jack@gmail.com",passwordEncoder().encode("123456")));
        userList.add(new User("jane","jane@gmail.com",passwordEncoder().encode("123456")));

        List<User> newUserList = userRepository.saveAll(userList);
        assertThat(newUserList.size()).isEqualTo(3);

    }





    @Test
    public void testPasswordEncoder(){

        String idForEncode = "bcrypt";
        Map<String,PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        encoders.put("sha256", new StandardPasswordEncoder());
        PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(idForEncode, encoders);


        // Create an encoder with strength 16
        //BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        String result = passwordEncoder.encode("myPassword");
        System.out.println("password : "+result);
        assertTrue(passwordEncoder.matches("myPassword", result));

    }




    private PasswordEncoder passwordEncoder(){
        String idForEncode = "bcrypt";
        Map<String,PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        encoders.put("sha256", new StandardPasswordEncoder());
        PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(idForEncode, encoders);

        return passwordEncoder;
    }

    @Test
    public void  assignRoleToUser(){
        long userId = 3;
        long roleId = 3;
        User user = userRepository.findById(userId).get();
        user.addRole(new Role(roleId));

       User updateUser =  userRepository.save(user);
        assertThat(updateUser.getRoles()).hasSizeGreaterThan(0);



    }


}

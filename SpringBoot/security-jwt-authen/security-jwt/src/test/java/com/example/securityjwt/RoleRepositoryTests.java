package com.example.securityjwt;

import com.example.securityjwt.entity.Role;
import com.example.securityjwt.model.ERole;
import com.example.securityjwt.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class RoleRepositoryTests {

    @Autowired
    RoleRepository roleRepository;

    @Test
    public void testCreateRole(){

        List<Role> roleList = new ArrayList<>();

        roleList.add(new Role(ERole.ROLE_ADMIN));
        roleList.add(new Role(ERole.ROLE_MODERATOR));
        roleList.add(new Role(ERole.ROLE_USER));

        List<Role> newRoleList = roleRepository.saveAll(roleList);

        assertThat(newRoleList.size()).isEqualTo(3);

    }




}

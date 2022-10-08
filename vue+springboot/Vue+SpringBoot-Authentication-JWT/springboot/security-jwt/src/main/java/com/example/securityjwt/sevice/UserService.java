package com.example.securityjwt.sevice;

import com.example.securityjwt.entity.Role;
import com.example.securityjwt.entity.User;
import com.example.securityjwt.exception.APIException;
import com.example.securityjwt.model.ERole;
import com.example.securityjwt.repository.RoleRepository;
import com.example.securityjwt.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    // view user
    public User getUserById(Long userId) {

        try {
            logger.debug("get user by id" + userId);
            User existUser = userRepository.findById(userId).get();
            return existUser;
        } catch (NoSuchElementException ex) {
            logger.debug(ex.getMessage());
            throw new APIException("User not found with id : " + userId);
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new APIException(ex.getMessage());
        }

    }

    public User getUserByUserName(String userName) {

        try {
            logger.debug("get user by username" + userName);
            User existUser = userRepository.findByUsername(userName);
            if(existUser == null){
                throw new APIException("User not found with username : " + userName);
            }
            return existUser;
        } catch (NoSuchElementException ex) {
            logger.debug(ex.getMessage());
            throw new APIException("User not found with username : " + userName);
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new APIException(ex.getMessage());
        }

    }


    // add user
    public User addUser(User user) {

        try {
            logger.debug("add user");
            User newUser = userRepository.save(user);

            return newUser;

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new APIException(ex.getMessage());
        }

    }

    // edit user data
    public User updateUser(User userEdit) {

        try {
            logger.debug("edit user");
            if (userEdit == null) {
                throw new APIException("User data can not be null");
            } else {
                if (userEdit.getId() != null) {
                    User updateUser = userRepository.save(userEdit);
                    return updateUser;
                } else {
                    throw new APIException("User id can not be null");
                }
            }
        }catch (IllegalArgumentException ex){
            logger.error(ex.getMessage());
            throw new APIException(ex.getMessage());
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new APIException(ex.getMessage());
        }
    }

    //delete user data
    public Boolean deleteUserById(Long userId) {

        try {
            logger.debug("Delete user");
            userRepository.deleteById(userId);
            return true;
        }catch (IllegalArgumentException ex){
            logger.error(ex.getMessage());
            throw new APIException("Can not delete user with id is null : "+ex.getMessage());
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new APIException(ex.getMessage());
        }

    }

    public Boolean checkUsernameExists(String username){

        return userRepository.existsByUsername(username);
    }

    public Boolean checkEmailExists(String email){

        return userRepository.existsByEmail(email);
    }

    public String createdPasswordEncode(String password){
       return passwordEncoder.encode(password);
    }

    public Set<Role> getRolesUser(String roleName) {

        Role role = new Role();
        Set<Role> roles = new HashSet<Role>();
        switch (roleName){
            case "admin":
                role = roleRepository.findByName(ERole.ROLE_ADMIN).orElse(new Role());
                roles.add(role);
                break;
            case "mod":
                role = roleRepository.findByName(ERole.ROLE_MODERATOR).orElse(new Role());
                roles.add(role);
                break;
            default:
                role = roleRepository.findByName(ERole.ROLE_USER).orElse(new Role());
                roles.add(role);
                break;
        }

        return roles;
    }



}

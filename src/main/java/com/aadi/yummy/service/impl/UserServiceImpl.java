package com.aadi.yummy.service.impl;

import com.aadi.yummy.dto.UserDto;
import com.aadi.yummy.entities.User;
import com.aadi.yummy.exception.ResourceNotFoundException;
import com.aadi.yummy.repository.UserRepo;
import com.aadi.yummy.service.UserService;
import com.aadi.yummy.utils.Helper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        //generate random id and get in user
        userDto.setId(Helper.generateRandomId());
        User user = convertUserDtoTOUser(userDto);
        User savedUser = userRepo.save(user);

        return convertUserToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto uerDto, String userId) {
        return null;
    }

    @Override
    public Page<UserDto> getAll(Pageable pageable) {
        Page<User> userPage = userRepo.findAll(pageable);
        //we need to convert user to userdto
        return userPage.map((user) -> convertUserToUserDto(user));
    }

    @Override
    public UserDto getUserById(String id) {
        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return convertUserToUserDto(user);
    }

    @Override
    public void deleteUserById(String id) {
        userRepo.deleteById(id);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepo.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        return convertUserToUserDto(user);
    }

    @Override
    public List<UserDto> getUsersByName(String name) {
        List<UserDto> listOfAllUsersFound = userRepo.findByName(name).stream().map((user) -> convertUserToUserDto(user)).toList();
        return listOfAllUsersFound;
    }

    @Override
    public UserDto getUserByName(String username) {
        User user = userRepo.findByName(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with username: " + username));

        return convertUserToUserDto(user);
    }

    //ass we need to pass entity to repo so we need to convert dto to entity
    //it does the same work likee user.setName("aditya") etc...
    private User convertUserDtoTOUser(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return user;
    }


    private UserDto convertUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

//    @Override
//    public User saveUser(User user) {
//        user.setId(UUID.randomUUID().toString());
//        User savedUser = userRepo.save(user);
//        return savedUser;
//    }
//
//    @Override
//    @Transactional
//    public User updateUser(User user, String userId){
//        User dbUser = userRepo.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
//        dbUser.setName(user.getName());
//        //.... aise hi sbhi fields ko upadte kr lenge
//        User updatedUser = userRepo.save(dbUser);
//        return updatedUser;
//    }
//
//    @Override
//    public void testUserRoleEntity() {
//        User user = new User();
//        user.setName("Abhi");
//        user.setAddress("c 83 pannu tower");
//        user.setPassword("Test@123");
//        user.setId(UUID.randomUUID().toString());
//        user.setEmail("abhi@gmail.com");
//        user.setRole(Role.ADMIN);
//        user.setAvailable(true);
//        user.setPhoneNumber("123456789");
//
//
//        RoleEntity  roleEntity = new RoleEntity();
//        roleEntity.setName("ROLE_ADMIN");
//        RoleEntity roleEntity1 = new RoleEntity();
//        roleEntity1.setName("ROLE_CUSTOMER");
//
//        user.getRoleEntity().add(roleEntity);
//        user.getRoleEntity().add(roleEntity1);
//
//        roleEntity.getUsers().add(user);
//        roleEntity1.getUsers().add(user);
//        userRepo.save(user);
//    }
}

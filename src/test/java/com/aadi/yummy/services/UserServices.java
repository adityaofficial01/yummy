package com.aadi.yummy.services;
import com.aadi.yummy.entities.Restaurant;
import com.aadi.yummy.entities.Role;
import com.aadi.yummy.entities.User;
import com.aadi.yummy.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class UserServices {

    @Autowired
    private UserService userService;
//    @Test
//    public void testSaveUser() {
//        System.out.println("saveUSer");
//        User user = new User();
//        user.setName("Aditya");
//        user.setAddress("c 83 pannu tower");
//        user.setPassword("Test@123");
//        user.setId(UUID.randomUUID().toString());
//        user.setEmail("rajputaadihr@gmail.com");
//        user.setRole(Role.ADMIN);
//        user.setAvailable(true);
//        user.setPhoneNumber("123456789");
//
//
//        Restaurant restaurant = new Restaurant();
//        restaurant.setId(UUID.randomUUID().toString());
//        restaurant.setName("Burger king");
//        restaurant.setAddress("phase 5 mohali");
//        restaurant.setOpen(true);
//
//
//        Restaurant restaurant1 = new Restaurant();
//        restaurant1.setId(UUID.randomUUID().toString());
//        restaurant1.setName("Burger king");
//        restaurant1.setAddress("phase 5 mohali");
//        restaurant1.setOpen(true);
//
//
//        restaurant.setUser(user);
//        restaurant1.setUser(user);
//
//        user.getRestaurants().add(restaurant);
//        user.getRestaurants().add(restaurant1);
//
//
//        User saved = userService.saveUser(user);
//        System.out.println(saved.getName());
//    }
//
//    @Test
//    public void testUpdateUser(){
//        userService.testUserRoleEntity();
////        userService.updateUser();
//    }
}

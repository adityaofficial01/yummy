package com.aadi.yummy.service;

import com.aadi.yummy.dto.UserDto;
import com.aadi.yummy.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserDto saveUser(UserDto userDto);
    UserDto updateUser(UserDto uerDto, String userId);
    Page<UserDto> getAll(Pageable pageable);
    UserDto getUserById(String id);
    UserDto getUserByName(String id);
    void deleteUserById(String id);
    UserDto getUserByEmail(String email);
    List<UserDto> getUsersByName(String name);

}

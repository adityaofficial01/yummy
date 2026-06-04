package com.aadi.yummy.controller;

import com.aadi.yummy.dto.UserDto;
import com.aadi.yummy.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    //create user
    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        UserDto userDtoSaved = userService.saveUser(userDto);
        return new ResponseEntity<>(userDtoSaved, HttpStatus.CREATED);
    }

    //get all users
    @GetMapping
    public ResponseEntity<Page<UserDto>> getAllUsers(
            @RequestParam (value = "page", required = false, defaultValue = "0") int page,
            @RequestParam (value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "sortBy", required = false, defaultValue = "name") String sortBy,
            @RequestParam(value = "sortDir",required = false, defaultValue = "asc") String sortDir
    ){

        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size,sort);

        return ResponseEntity.ok(userService.getAll(pageable));
    }


    // get user by id
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> findById(@PathVariable("userId") String id){
        return ResponseEntity.ok(userService.getUserById(id));
    }
}

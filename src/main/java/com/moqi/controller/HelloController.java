package com.moqi.controller;

import com.moqi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author moqi
 * On 2021/8/15 23:05
 */
@RestController
public class HelloController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello/{id}")
    String person(@PathVariable Long id) {
        return id + ", randomNumber = " + userService.getRandomNumber(1000);
    }

}

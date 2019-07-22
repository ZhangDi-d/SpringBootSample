package com.ryze.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xueLai on 2019/7/22.
 */
@RestController
public class HelloController {
    @GetMapping("/")
    @ResponseBody
    public String hello(){
        return "hello gradle!";
    }
}

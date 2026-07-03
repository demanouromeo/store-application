package com.dmsacad.store.controllers;

import com.dmsacad.store.pojo.MyMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("message")
public class MessageController {
    @GetMapping("/")
    public MyMessage sayHello(){
        return new MyMessage("Hi. Here you are");
    }
}

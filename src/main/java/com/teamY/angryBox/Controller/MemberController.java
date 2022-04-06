package com.teamY.angryBox.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @GetMapping("/")
    public String helloworld(){
        return "this is angrybox.link!!!";
    }
    @GetMapping("hello")
    public String helloDayea(){
        return "hello Yang Dayea";
    }
}

package com.teamY.angryBox.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @GetMapping("hello")
    public String helloDayea(){
        return "hello Yang Dayea";
    }
}

package com.holidayguru.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/host")
public class HostController {


    @GetMapping("/add")
    public String addHost(){
        return "host-add";
    }


    @GetMapping("/my-hosts")
    public String myHosts(){
        return "my-hosts";
    }



}

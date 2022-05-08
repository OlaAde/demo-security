package com.example.demosecurity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping(value = "/demo")
    public String demo() {
        return "demo";
    }

    @GetMapping(value = "/api/demo")
    public String apiDemo() {
        return "demo";
    }
}
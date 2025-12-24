package com.example.exam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HelloController {
    // view로 연결하기 위한 함수만 정의 = controller

    @GetMapping("hello")
    // http://localhost:8080/hello
    public String Hello(Model model){
        // 변수 name 값을 view(mustache)로 전송하기 위해서 Model(클래스) 필요
        model.addAttribute("name","홍길동");
        return "hello";
        // mustache 의 파일명
    }
}

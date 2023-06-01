package com.multi.board.multiboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {// 단순 페이지 이동만 정의

    @RequestMapping("/")
    public String hello(Model model){
        model.addAttribute("headTitle","테스트 게시판 홈페이지");
        return "main";
    }
    
    @GetMapping("/member/register")
    public String register(Model model){
        model.addAttribute("headTitle","회원가입");
        return "member/register";
    }

    @GetMapping("/member/login")
    public String logIn(Model model){
        model.addAttribute("headTitle","로그인");
        return "member/login";
    }
    
}

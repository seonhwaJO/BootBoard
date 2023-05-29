package com.campus.testBoard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {	// 단순 페이지 이동은 여기로 바꿈
	
	@GetMapping("/")
	public String hello(Model model) {	//메인 페이지
		model.addAttribute("headTitle","게시판 테스트 홈페이지");
		model.addAttribute("title","게시판 테스트 홈페이지");
		model.addAttribute("contents","반가습니다! 게시판 테스트 홈페이지입니다!");
		return "main";
	}
	@GetMapping("/member/register")
	public String register(Model model) {	// 회원가입 페이지 이동
		model.addAttribute("headTitle","회원가입");
		return "member/register";
	}
	
	@GetMapping("/member/login")
	public String logIn(Model model) {	// 회원가입 페이지 이동
		model.addAttribute("headTitle","로그인");
		return "member/login";
	}
}

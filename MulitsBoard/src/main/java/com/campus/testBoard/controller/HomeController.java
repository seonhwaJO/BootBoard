package com.campus.testBoard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String hello(Model model) {
		model.addAttribute("title","게시판 테스트 홈페이지");
		model.addAttribute("contents","반가습니다! 게시판 테스트 홈페이지입니다!");
		return "main";
	}
}

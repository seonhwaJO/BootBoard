package com.campus.testBoard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.campus.testBoard.dto.MemberDTO;
import com.campus.testBoard.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	private final MemberService memberService;
	
	@Autowired
	public MemberController(MemberService memberService) {	// 주입 생성자로 변경
		this.memberService = memberService;
	}
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("headTitle","회원가입");
		model.addAttribute("title","회원가입 페이지");
		model.addAttribute("contents","회원가입 정보를 입력해주세요.");
		return "member/register";
	}
	
	@PostMapping("/register")
	public String registerMember(MemberDTO memberDto, Model model) {
		System.out.println(memberDto.getPassword());
		if(memberService.addMember(memberDto)) {
			return "member/register_ok";
		}else {
			return "member/register_fail";
		}
	}
}

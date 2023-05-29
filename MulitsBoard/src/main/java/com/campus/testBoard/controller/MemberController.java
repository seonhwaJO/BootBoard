package com.campus.testBoard.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.campus.testBoard.dto.LoginMemberDTO;
import com.campus.testBoard.dto.MemberDTO;
import com.campus.testBoard.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/member")
public class MemberController {
	
	private final MemberService memberService;
	
	@Autowired
	public MemberController(MemberService memberService) {	// 생성자 주입으로 변경
		this.memberService = memberService;
	}
	
	@PostMapping("/register")
	@ResponseBody
	public Map<String, String> registerMember(@RequestBody MemberDTO memberDto) {	//회원가입 처리
		return memberService.addMember(memberDto);
	}
	
	@GetMapping("/checkId")
	@ResponseBody
	public Map<String, Object> checkIdDuplication(@RequestParam("userId") String id) {	// 아이디 중복 체크 결과 반환
		return memberService.checkIdService(id);
	}
	
	@GetMapping("/checkEmail")
	@ResponseBody
	public Map<String, Object> checkEmailDuplication(@RequestParam("email") String email) {	// 아이디 중복 체크 결과 반환
		return memberService.checkEmailService(email);
	}
	
	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<Map<String, Object> > logIn(@RequestBody LoginMemberDTO loginMemberDTO, HttpServletRequest request) {	//로그인 처리
	    MemberDTO memberDTO = memberService.loginService(loginMemberDTO);	// 사용자 입력정보를 바탕으로 DTO 생성
	    if (memberDTO != null) {	//DTO가 Null아니면 session처리 
	        HttpSession session = request.getSession();	// 세션가져오기
	        session.setAttribute("userId", memberDTO.getId());	//세션에 아이디입력 - 회원정보 같은거 조회할 때 사용
	        session.setAttribute("userName", memberDTO.getName());	//세션에 이름 입력 - 이름은 자주 쓸거 같아서 넣어놓음
	        session.setMaxInactiveInterval(1800); // 세션 만료 시간 설정, 30분
	        Map<String, Object> responseMap = new HashMap<>();	//로그인 완료 메세지를 위해 이름만 반환
	        responseMap.put("userName", memberDTO.getName());
	        return ResponseEntity.ok(responseMap);
	    } else {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();	// 틀리면 에러 보냄 401에러
	    }
	}
	
	@GetMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request) {
	    HttpSession session = request.getSession(false);	//세션 가져오기
	    if (session != null && session.getAttribute("userId") != null) {
	        session.invalidate(); // 세션 무효화
	        return new ResponseEntity<>("로그아웃", HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>("로그인되어 있지 않습니다.", HttpStatus.UNAUTHORIZED);
	    }
	}
}

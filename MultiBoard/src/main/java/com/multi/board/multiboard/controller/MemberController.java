package com.multi.board.multiboard.controller;

import com.multi.board.multiboard.Service.MemberService;
import com.multi.board.multiboard.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor    //롬복에서 생성자 주입
public class MemberController {

    private final MemberService memberService;	//RequiredArgsConstruct에서 자동 생성자 주입

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
}


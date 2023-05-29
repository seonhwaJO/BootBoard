package com.campus.testBoard.service;

import java.util.Map;

import com.campus.testBoard.dto.LoginMemberDTO;
import com.campus.testBoard.dto.MemberDTO;

public interface MemberService {
	Map<String, String> addMember(MemberDTO memberDto);	//사용자 추가
	Map<String, Object> checkIdService(String id);  // id체크
	Map<String, Object> checkEmailService(String email);	//email 체크
	MemberDTO loginService(LoginMemberDTO loginMemberDTO);	//로그인 서비스
}

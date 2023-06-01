package com.multi.board.multiboard.Service;

import com.multi.board.multiboard.dto.MemberDTO;

import java.util.Map;

public interface MemberService {
    Map<String, String> addMember(MemberDTO memberDto);	//사용자 추가
    Map<String, Object> checkIdService(String id);  // id체크
    Map<String, Object> checkEmailService(String email);	//email 체크
}


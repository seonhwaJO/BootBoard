package com.campus.testBoard.dao;

import java.sql.SQLException;

import com.campus.testBoard.dto.LoginMemberDTO;
import com.campus.testBoard.dto.MemberDTO;

public interface MemberDAO {
	void join(MemberDTO memberDto)throws SQLException;	//회원추가

	int getNumerBuyId(String id)throws SQLException;		//아이디 개수 확인
	
	int getNumerBuyEmail(String email)throws SQLException;	//email 개수 확인

	boolean validateCredentials(LoginMemberDTO loginMemberDTO)throws SQLException;	// id, 패스워드 맞는지 확인
	
	MemberDTO getUserById(String id)throws SQLException;	//아이디로 멤버 정보 가져오기(이름, 아이디만)
}

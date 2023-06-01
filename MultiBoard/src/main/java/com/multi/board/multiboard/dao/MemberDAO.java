package com.multi.board.multiboard.dao;

import com.multi.board.multiboard.dto.LoginMemberDTO;
import com.multi.board.multiboard.dto.MemberDTO;

import java.sql.SQLException;
import java.util.Optional;

public interface MemberDAO {
    void join(MemberDTO memberDto)throws SQLException;	//회원추가

    int getNumerBuyId(String id)throws SQLException;		//아이디 개수 확인

    int getNumerBuyEmail(String email)throws SQLException;	//email 개수 확인

    Optional<LoginMemberDTO> findByusername(String id)throws SQLException;	// id, 패스워드 맞는지 확인

    MemberDTO getUserById(String id)throws SQLException;	//아이디로 멤버 정보 가져오기(이름, 아이디만)

}

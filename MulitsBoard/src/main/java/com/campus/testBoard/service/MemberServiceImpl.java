package com.campus.testBoard.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.campus.testBoard.dao.MemberDAO;
import com.campus.testBoard.dto.MemberDTO;

@Service
public class MemberServiceImpl implements MemberService{
	
	private final MemberDAO memberDAO;
	
	@Autowired
	public MemberServiceImpl(MemberDAO memberDAO) {	//생성자로 주입 변경
		this.memberDAO = memberDAO;
	}
	
	@Override
	public Boolean addMember(MemberDTO memberDto) {
		try {
			return memberDAO.join(memberDto);
		} catch (SQLException e) {
			System.out.println("sql 에러입니다. 꼭 확인하세요.");
			e.printStackTrace();
		}
		return null;
	}
}

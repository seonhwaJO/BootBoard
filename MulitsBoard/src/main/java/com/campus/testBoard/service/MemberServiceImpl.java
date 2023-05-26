package com.campus.testBoard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.campus.testBoard.dao.MemberDAO;
import com.campus.testBoard.dto.MemberDTO;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	MemberDAO memberDAO;
	
	@Override
	public Boolean addMember(MemberDTO memberDto) {
		return memberDAO.join(memberDto);
	}
	
}

package com.campus.testBoard.dao;

import java.sql.SQLException;

import com.campus.testBoard.dto.MemberDTO;

public interface MemberDAO {
	boolean join(MemberDTO memberDto)throws SQLException;
}

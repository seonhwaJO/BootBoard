package com.campus.testBoard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.campus.testBoard.dto.LoginMemberDTO;
import com.campus.testBoard.dto.MemberDTO;

import jakarta.annotation.PostConstruct;

@Repository
public class MemberDAOImpl extends JdbcDaoSupport implements MemberDAO {

	private DataSource dataSource;

	@PostConstruct// 빈 초기화 시 실행
	private void initialize() {
		setDataSource(dataSource);
	}

	@Autowired	// 생략 가능하지만 의미를 위해 붙임
	public MemberDAOImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void join(MemberDTO memberDto) throws SQLException {	//회원가입 sql
		String sql = "INSERT INTO member VALUES (?,?,?,?,?,?)";

		try (Connection conn = getConnection(); 
			  PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, memberDto.getId());
			pstmt.setString(2, memberDto.getPassword());
			pstmt.setString(3, memberDto.getName());
			pstmt.setString(4, memberDto.getEmail());
			pstmt.setString(5, memberDto.getAddress());
			pstmt.setString(6, memberDto.getPhone());
			pstmt.executeUpdate();
		}
	}

	@Override
	public int getNumerBuyId(String id) throws SQLException {	//id 중복 체크를 위해 id 개수를 가져옴
	    String sql = "SELECT COUNT(*) FROM member WHERE id = ?";
	    int count = 0;
	    try (Connection conn = getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, id);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                count = rs.getInt(1);
	            }
	        }
	    }
	    return count;
	}
	
	@Override
	public int getNumerBuyEmail(String email) throws SQLException {	//이메일 중복확인, 아이디에 상관없이 무조건 이메일은 중복되면 안됨
	    String sql = "SELECT COUNT(*) FROM member WHERE email = ?";
	    int count = 0;
	    try (Connection conn = getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, email);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                count = rs.getInt(1);
	            }
	        }
	    }
	    return count;
	}

	@Override
	public boolean validateCredentials(LoginMemberDTO loginMemberDTO) throws SQLException{	// 
	    String sql = "SELECT COUNT(*) FROM member WHERE id = ? and password = ?";
	    int count = 0;
	    try (Connection conn = getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, loginMemberDTO.getId());
	        pstmt.setString(2, loginMemberDTO.getPassword());
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                count = rs.getInt(1);
	            }
	        }
	    }
	    return count > 0; // 회원이 존재하는 경우 true, 그렇지 않은 경우 false 반환
	}

	@Override
	public MemberDTO getUserById(String id)throws SQLException {	// id로 user 정보 가져옴
	    MemberDTO memberDTO = new MemberDTO();
		String sql = "SELECT id,name FROM member WHERE id = ?";
	    try (Connection conn = getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, id);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                memberDTO.setId(rs.getString(1));
	                memberDTO.setName(rs.getString(2));
	            }
	        }
	    }
	    return memberDTO;
	}
}

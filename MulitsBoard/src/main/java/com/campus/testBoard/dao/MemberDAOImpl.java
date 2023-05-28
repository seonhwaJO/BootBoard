package com.campus.testBoard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.campus.testBoard.dto.MemberDTO;

import jakarta.annotation.PostConstruct;

@Repository
public class MemberDAOImpl extends JdbcDaoSupport implements MemberDAO {

	DataSource dataSource;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}

	public MemberDAOImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public boolean join(MemberDTO memberDto) throws SQLException {
		String sql = "INSERT INTO member_table VALUES (?,?,?,?,?)";
		int count = 0;
		try (Connection conn = getConnection(); 
			  PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, memberDto.getId());
			pstmt.setString(2, memberDto.getPassword());
			pstmt.setString(3, memberDto.getName());
			pstmt.setString(4, memberDto.getAddress());
			pstmt.setString(5, memberDto.getCellphone());
			count = pstmt.executeUpdate();
			if (count != 0) {
				return true;
			}
		}
		return false;
	}
}

package com.campus.testBoard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.campus.testBoard.dto.MemberDTO;

import jakarta.annotation.PostConstruct;


@Repository
public class MemberDAOImpl extends JdbcDaoSupport implements MemberDAO{
	
	DataSource dataSource;
	
    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }
	
    public MemberDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
	@Override
	public boolean join(MemberDTO memberDto) {
		String sql = "INSERT INTO member_table VALUES (?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberDto.getId());
			pstmt.setString(2, memberDto.getPassword());
			pstmt.setString(3, memberDto.getName());
			pstmt.setString(4, memberDto.getAddress());
			pstmt.setString(5, memberDto.getCellphone());
			count = pstmt.executeUpdate();
			
			if(count !=0 ) {
				return true;
			}
		}catch(SQLException throwables) {
			throwables.printStackTrace();
			return false;
		}finally {
			close(conn,pstmt,rs);
		}
		return false;
	}
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (conn != null) {
            close(conn);
        }
    }

    private void close(Connection conn) {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}

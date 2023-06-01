package com.multi.board.multiboard.security;

import com.multi.board.multiboard.dao.MemberDAO;
import com.multi.board.multiboard.dto.LoginMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberSecurityService implements UserDetailsService {

    private final MemberDAO memberDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginMemberDTO loginMemberDTO = new LoginMemberDTO();
        try {
            Optional<LoginMemberDTO> _loginMemberDTO = this.memberDAO.findByusername(username);
            if (_loginMemberDTO.isEmpty()) {	// 있으면 DTO 반환
                throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
            }
            loginMemberDTO = _loginMemberDTO.get();
        } catch (SQLException e) {	//SQL 에러
            System.out.println("sql 에러입니다. 꼭 확인하세요.");
            e.printStackTrace();
        }
        return new User(loginMemberDTO.getId(), loginMemberDTO.getPassword(), new ArrayList<>());	//패스워드 확인 을 위한 return, 마지막 ArrayList는 role이 없어서 null 반환
    }

}

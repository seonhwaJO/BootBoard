package com.multi.board.multiboard.Service;

import com.multi.board.multiboard.dao.MemberDAO;
import com.multi.board.multiboard.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberDAO memberDAO;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Map<String, String> addMember(MemberDTO memberDTO) { // 사용자 추가
        Map<String, String> response = new HashMap<>();
        try {
            String password = passwordEncoder.encode(memberDTO.getPassword());// 패스워드 암호화
            memberDTO.setPassword(password);
            memberDAO.join(memberDTO);	// 회원가입처리
            response.put("message", "회원가입이 완료되었습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public Map<String, Object> checkIdService(String id) { // 아이디 중복 체크
        Map<String, Object> result = new HashMap<>(); // 상태와 메세지를 함께 전달
        String message = null;
        int status = 0;

        try {
            int num = memberDAO.getNumerBuyId(id);
            if (num == 0) {	//중복 없음
                status = 0;
                message = "사용할 수 있는 아이디입니다.";
            } else {	//중복있음
                status = 1;
                message = "이미 존재하는 아이디입니다. 다른 아이디를 입력해주세요";
            }
        } catch (SQLException e) {
            System.out.println("sql 에러입니다. 꼭 확인하세요.");
            e.printStackTrace();
        }

        result.put("status", status);
        result.put("message", message);
        return result;
    }

    @Override
    public Map<String, Object> checkEmailService(String email) {	//email 중복확인
        Map<String, Object> result = new HashMap<>(); // 상태와 메세지를 함께 전달
        String message = null;
        int status = 0;

        try {
            int num = memberDAO.getNumerBuyEmail(email);
            if (num == 0) {	// 중복없음
                status = 0;
                message = "사용할 수 있는 이메일입니다.";
            } else {	//중복있음
                status = 1;
                message = "이미 존재하는 이메일입니다. 다른 이메일을 입력해주세요";
            }
        } catch (SQLException e) {
            System.out.println("sql 에러입니다. 꼭 확인하세요.");
            e.printStackTrace();
        }

        result.put("status", status);
        result.put("message", message);
        return result;
    }
}

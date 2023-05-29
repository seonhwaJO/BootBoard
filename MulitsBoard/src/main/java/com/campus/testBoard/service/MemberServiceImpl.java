package com.campus.testBoard.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.campus.testBoard.dao.MemberDAO;
import com.campus.testBoard.dto.LoginMemberDTO;
import com.campus.testBoard.dto.MemberDTO;

@Service
public class MemberServiceImpl implements MemberService {

	private final MemberDAO memberDAO;

	@Autowired
	public MemberServiceImpl(MemberDAO memberDAO) { // 생성자로 주입 변경
		this.memberDAO = memberDAO;
	}

	@Override
	public Map<String, String> addMember(MemberDTO memberDto) { // 사용자 추가
		Map<String, String> response = new HashMap<>();
		try {
			memberDAO.join(memberDto);	// 회원가입처리
			response.put("message", "회원가입이 완료되었습니다.");
		} catch (SQLException e) {
			System.out.println("sql 에러입니다. 꼭 확인하세요.");
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

	@Override
	public MemberDTO loginService(LoginMemberDTO loginMemberDTO) {	//로그인 서비스를 위한 메서드
		try {
			boolean isLoggedIn = memberDAO.validateCredentials(loginMemberDTO);	// 사용자 입력값이 있는 지 확인
			if (isLoggedIn) {	// 있으면 DTO 반환
				return memberDAO.getUserById(loginMemberDTO.getId());
			} else {	//없으면 null반환
				return null;
			}
		} catch (SQLException e) {	//SQL 에러
			System.out.println("sql 에러입니다. 꼭 확인하세요.");
			e.printStackTrace();
			return null;
		}
	}

}

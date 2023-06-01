$(function() {
	$('#input_id').keyup(function() {
		var userId = $('#input_id').val();

		if (userId.length >= 4 && /^[a-z0-9]+$/.test(userId)) {
			$.ajax({
				url: "/member/checkId",
				type: "get",
				data: { userId: userId },
				dataType: 'json',
				success: function(result) {
					var status = result.status;
					var message = result.message;
					if (status > 0) {
						$("#checkId").html(message);
						$("#checkId").css('color', 'red');
					} else {
						$("#checkId").html(message);
						$("#checkId").css('color', 'green');
					}
				},
				error: function() {
					alert("서버 요청 실패");
				}
			});
		} else {
			if (userId.length > 3) {
				$("#checkId").html("영문소문자 3자 이상 가능합니다.");
				$("#checkId").css('color', 'red');
			} else {
				$("#checkId").html("");
			}
		}
	});
	$('#input_password').keyup(function() {
		var password = $('#input_password').val();

		// 패스워드 유효성 검사
		var isValid = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@$!%*#?&])[a-zA-Z\d@$!%*#?&]{8,}$/.test(password);

		if (!isValid) {
			$('#input_password').addClass('is-invalid');
		} else {
			$('#input_password').removeClass('is-invalid');
		}
	});

	$('#check_password').keyup(function() {
		var password = $('#input_password').val();
		var confirmPassword = $('#check_password').val();

		if (password !== confirmPassword) {
			$('#check_password').addClass('is-invalid');
		} else {
			$('#check_password').removeClass('is-invalid');
		}
	});

	$('#input_email').keyup(function() {
		var email = $('#input_email').val();

		if (validateEmail(email)) {
			$.ajax({
				url: "/member/checkEmail",
				type: "get",
				data: { email: email },
				dataType: 'json',
				success: function(result) {
					var status = result.status;
					var message = result.message;
					if (status > 0) {
						$("#checkEmail").html(message);
						$("#checkEmail").css('color', 'red');
					} else {
						$("#checkEmail").html(message);
						$("#checkEmail").css('color', 'green');
					}
				},
				error: function() {
					alert("서버 요청 실패");
				}
			});
		} else {
			$("#checkEmail").html("");
		}
	});
	$('#input_phone').on('input', function(e) {
		var input = e.target;
		var formatted = input.value.replace(/\D/g, '');
		input.value = formatted;
	});
	$('form').submit(function(e) {
		e.preventDefault();

		var isValid = validateForm();

		if (isValid) {
			// 폼이 유효한 경우 서버에 제출
			$(this).unbind('submit').submit();
		} else {
			// 폼이 유효하지 않은 경우 첫 번째 빈 칸으로 포커스 이동
			focusFirstEmptyField();
		}
	});
	$('#registerButton').click(function(e) {
		e.preventDefault(); // 폼 전송 막기

		var user = {
			id: $("#input_id").val(),
			password: $("#input_password").val(),
			name: $("#input_name").val(),
			email: $("#input_email").val(),
			address: $("#input_address").val(),
			phone: $("#input_phone").val()
		};

		var option = {
			url: "/member/register",
			type: "post",
			data: JSON.stringify(user),
			contentType: "application/json",
			dataType: "json",
			success: function(response) {
				alert(response.message);
				window.location.href = "/"; // 홈으로 리디렉션
			},
			error: function(xhr, status, error) {
				alert("회원 가입에 실패했습니다."); // 회원 가입 실패 시 기본 알림 메시지 표시
			}
		};

		$.ajax(option);
		return false;
	});

});

function validateForm() {
	var isValid = true;

	// 입력 필드 유효성 검사 로직 추가
	var fields = ['input_id', 'input_password', 'check_password', 'input_email'];
	for (var i = 0; i < fields.length; i++) {
		var value = $('#' + fields[i]).val();
//		if (value.trim() === '') {
//			isValid = false;
//			break;
//		}
	}

	return isValid;
}

function focusFirstEmptyField() {
	var fields = ['input_id', 'input_password', 'check_password', 'input_email'];
	for (var i = 0; i < fields.length; i++) {
		var value = $('#' + fields[i]).val();
		if (value.trim() === '') {
			$('#' + fields[i]).focus();
			break;
		}
	}
}

function formatPhoneNumber(phone) {
	// 입력된 휴대폰 번호에서 숫자를 제외한 모든 문자를 제거합니다.
	var cleaned = phone.replace(/\D/g, '');

	// 형식이 적용된 휴대폰 번호를 생성합니다.
	var formattedPhone = '';
	var groups = cleaned.match(/^(\d{3})(\d{3,4})(\d{4})$/);
	if (groups) {
		formattedPhone = groups[1] + '-' + groups[2] + '-' + groups[3];
	}

	return formattedPhone;
}


function validateEmail(email) { // 이메일 형식 유효성 검사
	var regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
	return regex.test(email);
}
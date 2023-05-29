$(function() {
	$('#loginButton').click(function(e) {
		var user = {
			id: $("#login_id").val(),
			password: $("#login_password").val()
		};

		$.ajax({
			url: "/member/login",
			type: "POST",
			data: JSON.stringify(user),
			contentType: "application/json",
			dataType: "json",
			success: function(response) {
		        var userName = response.userName;
		        alert(userName + "님 로그인되었습니다.");
		        $("#login_id").val(""); // 로그인 아이디 필드 초기화
		        $("#login_password").val(""); // 로그인 패스워드 필드 초기화
		        window.location.href = "/"; // 홈으로 리디렉션
			},
			error: function(xhr, status, error) {
				alert("아이디/비밀번호가 맞지 않습니다."); // 회원 가입 실패 시 기본 알림 메시지 표시
				$("#login_password").val(""); // 로그인 패스워드 필드 초기화
			}
		});
	});
	
	$('#logoutButton').click(function(e) {
    $.ajax({
        url: "/member/logout",
        type: "GET",
        success: function(response) {
            alert(response);
            window.location.href = "/"; // Redirect to the home page
        },
        error: function(xhr, status, error) {
            alert("로그아웃 실패: " + xhr.responseText); // Display error message if logout fails
        }
    });
});
});


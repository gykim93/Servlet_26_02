<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<body>
	<a href="../home/main">메인으로 이동</a>

	<h1>회원 가입</h1>
	<script type="text/javascript">
		function JoinForm_submit(form) {
			let loginId = form.loginId.value.trim();
			let loginPw = form.loginPw.value.trim();
			let loginPwConfirm = form.loginPwConfirm.value.trim();
			let name = form.name.value.trim();

			if (form.loginId.value.length == 0) {
				alert('loginId 사용');
				return;
			}
			if (loginPw.length == 0) {
				alert('loginPw 사용');
				return;
			}
			if (loginPwConfirm.length == 0) {
				alert('loginPwConfirm 사용');
				return;
			}
			if (loginPw != loginPwConfirm) {
				alert('패스워드 불일치');
				form.loginPw.focus();
				return;
			}
			form.submit();
		}
	</script>
	<form onsubmit="JoinForm_submit(this); return false;" action="doJoin"
		method="POST">
		<div>
			아이디 : <input autocomplete="off" name="loginId" type="text"
				placeholder="loginId 입력" />
		</div>
		<div>
			비밀번호 : <input autocomplete="off" name="loginPw" type="text"
				placeholder="loginPw 입력" />
		</div>
		<div>
			비밀번호 확인 : <input autocomplete="off" name="loginPwConfirm" type="text"
				placeholder="loginPwConfirm 입력" />
		</div>
		<div>
			이름 : <input autocomplete="off" name="name" type="text"
				placeholder="name 입력" />
		</div>
		<div>
			<input type="submit" value="가입" />
		</div>
	</form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>로그인</title>
	<script>
		function login() {
			if (form.userId.value == "") {
				alert("아이디를 입력하세요.");
				form.userId.focus();
				return false;
			}
			if (form.password.value == "") {
				alert("비밀번호를 입력하세요.");
				form.password.focus();
				return false;
			}
			form.submit();
		}
	</script>
</head>
<body>
	<div>로그인</div>
	<!-- login form -->
	<form name="loginForm" method="POST" action="#">
		<div>
			
		</div>
		<table>
			<tr>
				<th>아이디</th>
				<td><input type="text" name="userId" /></td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td><input type="password" name="password" /></td>
			</tr>
		</table>
		<div>
			<button type="button" onClick="#">로그인</button>
		</div>
	</form>
	<div>
		회원가입
	</div>
</body>
</html>
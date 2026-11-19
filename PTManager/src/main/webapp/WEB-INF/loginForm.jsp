<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>PT Manager 로그인</title>
</head>
<body>
	<!-- login form -->
	<form id="loginForm" name="loginForm" method="POST" action="">
		<table class="">
			<tr>
				<th>사용자 아이디</th>
				<td><input type="text" name="userId" /></td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td><input type="password" name="password" /></td>
			</tr>
		</table>
		<div class="buttons">
			<button type="button" onClick="login()">로그인</button>
			<button type="button" onClick="">회원가입</button>
		</div>
	</form>
</body>
</html>
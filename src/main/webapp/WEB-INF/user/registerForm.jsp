<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<body>
	<div>회원가입</div>
	<!-- registration form -->
	<form name="registerForm" method="POST" action="#">
		<div>
		</div>
		
		<table>
			<tr>
				<th>아이디</th>
				<td><input type="text" name="userId" placeholder="아이디를 입력하세요" /></td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td><input type="password" name="password" placeholder="비밀번호를 입력하세요" /></td>
			</tr>
			<tr>
				<th>비밀번호 확인</th>
				<td><input type="password" name="password2" placeholder="비밀번호를 한 번 더 입력하세요" /></td>
			</tr>
			<tr>
				<th>이름</th>
				<td><input type="text" name="userName" placeholder="이름을 입력하세요" /></td>
			</tr>
			<tr>
				<th>이메일</th>
				<td><input type="text" name="email" placeholder="이메일을 입력하세요" /></td>
			</tr>
			<tr>
				<th>전화번호</th>
				<td><input type="text" name="phone" placeholder="전화번호를 입력하세요" /></td>
			</tr>
			<tr>
				<th>키</th>
				<td><input type="text" name="height" placeholder="키를 입력하세요" /></td>
			</tr>
			<tr>
				<th>역할</th>
				<td>
					<input type="radio" name="role" value="member">일반 회원
					<input type="radio" name="role" value="ptMember">PT 회원
					<input type="radio" name="role" value="trainer">트레이너
				</td>
			</tr>
		</table>
		<div>
			<button type="button" onClick="#">회원가입</button>
		</div>
	</form>
</body>
</html>
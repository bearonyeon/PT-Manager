<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>회원가입</title>
	<script>
		function userCreate() {
			const form = document.registerForm;
			
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
			if (form.password2.value == "") {
				alert("비밀번호를 입력하세요.");
				form.password2.focus();
				return false;
			}
			
			if (form.password.value != form.password2.value) {
				alert("비밀번호가 일치하지 않습니다.");
				form.password2.focus();
				return false;
			}
			
			if (form.name.value == "") {
				alert("이름을 입력하세요.");
				form.name.focus();
				return false;
			}
			if (form.email.value == "") {
				alert("이메일을 입력하세요.");
				form.email.focus();
				return false;
			}
			
			var emailExp = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+(\.[A-Za-z0-9\-]+)+$/;
			if (emailExp.test(form.email.value) == false) {
				alert("이메일 형식이 올바르지 않습니다.");
				form.email.focus();
				return false;
			}
			
			if (form.phone.value == "") {
				alert("전화번호를 입력하세요.");
				form.phone.focus();
				return false;
			}
			
			var phoneExp = /^\d{2,3}-\d{3,4}-\d{4}$/;
			if (phoneExp.test(form.phone.value) == false) {
				alert("전화번호 형식이 올바르지 않습니다.");
				form.phone.focus();
				return false;
			}
		
			if (form.height.value == "") {
				alert("키를 입력하세요.\n(소수점 이하 2자리까지만 입력 가능)");
				form.height.focus();
				return false;
			}
			// 키 입력 값이 소수점 이하 2자리 이하인지 검증해야 됨
			
			var heightExp = /^\d{2,3}(\.\d{1,2})?$/;
			if (heightExp.test(form.height.value) == false) {
				alert("키 형식이 올바르지 않습니다.");
				form.height.focus();
				return false;
			}
			form.submit();
		}
	</script>
</head>
<body>
	<div>회원가입</div>
	<!-- registration form -->
	<form name="registerForm" method="POST" action="<c:url value='/user/register' />">
		<!-- 회원가입이 실패한 경우 exception 객체에 저장된 오류 메시지를 출력 -->
		<c:if test="${registerFailed}">
			<div>${exception.message}</div>
		</c:if>
		
		<table>
			<tr>
				<th>아이디</th>
				<td><input type="text" name="userId" <c:if test="${registerFailed}">value="${account.userId}"</c:if> /></td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td><input type="password" name="password" <c:if test="${registerFailed}">value="${account.password}"</c:if> /></td>
			</tr>
			<tr>
				<th>비밀번호 확인</th>
				<td><input type="password" name="password2" autocomplete="new-password" /></td>
			</tr>
			<tr>
				<th>이름</th>
				<td><input type="text" name="name" <c:if test="${registerFailed}">value="${account.name}"</c:if> /></td>
			</tr>
			<tr>
				<th>이메일</th>
				<td><input type="text" name="email" <c:if test="${registerFailed}">value="${account.email}"</c:if> /></td>
			</tr>
			<tr>
				<th>전화번호</th>
				<td><input type="text" name="phone" <c:if test="${registerFailed}">value="${account.phone}"</c:if> /></td>
			</tr>
			<tr>
				<th>키</th>
				<td><input type="text" name="height" <c:if test="${registerFailed}">value="${account.height}"</c:if> /></td>
			</tr>
			<tr>
				<th>역할</th>
				<td>
					<input type="radio" name="role" value="MEMBER" <c:if test="${registerFailed && account.role == 'MEMBER'}">checked</c:if>>일반 회원
					<input type="radio" name="role" value="PTMEMBER" <c:if test="${registerFailed && account.role == 'PTMEMBER'}">checked</c:if>>PT 회원
					<input type="radio" name="role" value="TRAINER" <c:if test="${registerFailed && account.role == 'TRAINER'}">checked</c:if>>트레이너
				</td>
			</tr>
		</table>
		<div>
			<button type="button" onClick="userCreate()">회원가입</button>
		</div>
	</form>
</body>
</html>
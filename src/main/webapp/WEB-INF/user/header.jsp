<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<nav class="navbar">
    <a href="${pageContext.request.contextPath}/main">PTManager</a>
    
    <ul>
        <!-- 1. 비로그인 상태 -->
        <c:if test="${empty sessionScope.userId}">
            <li><a href="${pageContext.request.contextPath}/user/login">로그인</a></li>
            <li><a href="${pageContext.request.contextPath}/user/register">회원가입</a></li>
        </c:if>

        <!-- 2. 일반 회원 (MEMBER) 메뉴 -->
        <c:if test="${sessionScope.userRole eq 'MEMBER'}">
            <li><a href="${pageContext.request.contextPath}/trainer/list">트레이너 찾기</a></li>
            <li><a href="${pageContext.request.contextPath}/pt/apply">PT 신청</a></li>
            <li><a href="${pageContext.request.contextPath}/user/mypage">마이페이지</a></li>
        </c:if>

        <!-- 3. PT 회원 (PT_MEMBER) 메뉴 -->
        <c:if test="${sessionScope.userRole eq 'PT_MEMBER'}">
            <li><a href="${pageContext.request.contextPath}/routine/my">내 운동 루틴</a></li>
            <li><a href="${pageContext.request.contextPath}/diet/my">식단 기록</a></li>
            <li><a href="${pageContext.request.contextPath}/schedule/my">PT 일정 관리</a></li>
        </c:if>

        <!-- 4. 트레이너 (TRAINER) 메뉴 -->
        <c:if test="${sessionScope.userRole eq 'TRAINER'}">
            <li><a href="${pageContext.request.contextPath}/member/manage">담당 회원 관리</a></li>
            <li><a href="${pageContext.request.contextPath}/routine/create">루틴 작성</a></li>
            <li><a href="${pageContext.request.contextPath}/schedule/trainer">수업 일정표</a></li>
        </c:if>

        <!-- 공통 로그아웃 -->
        <c:if test="${not empty sessionScope.userId}">
            <li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>
        </c:if>
    </ul>
</nav>
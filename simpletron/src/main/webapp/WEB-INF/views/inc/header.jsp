<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<h1 style="float: left; width: 150px;">
	<a href="/simpletron"><img
		src="/simpletron/resources/images/Logo.png" style="width: 80px;"
		alt="Hoon's SimpleTron" /></a>
</h1>

<div id="memberMenu" style="float: right; position: relative; top: 7px;">
	<c:choose>
		<c:when test="${empty check}">
			<button value="로그인" class="b01_simple_rollover"
				onclick="location.href='/simpletron/users/login'">로그인</button>
			<button value="회원가입" class="b01_simple_rollover"
				onclick="location.href='/simpletron/users/signUp'">회원가입</button>
		</c:when>
		<c:otherwise>
			<button value="로그아웃" class="b01_simple_rollover"
				onclick="location.href='/simpletron/users/logout'">로그아웃</button>
			<button value="내정보수정" class="b01_simple_rollover"
				onclick="location.href='simpletron/users/editAccount'">정보수정</button>
		</c:otherwise>
	</c:choose>
</div>
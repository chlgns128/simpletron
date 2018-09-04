<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%@ page import="java.io.PrintWriter"%>
<%@ page import="com.sim.board.controller.SimpleTronMethod"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="Keywords" content="게시판 상세보기" />
<meta name="Description" content="게시판 상세보기" />
<link rel="stylesheet" href="../resources/css/screen.css"
	type="text/css" media="screen" />
<title>후니의 심플트론</title>
<script type="text/javascript">
	//<![CDATA[
	function goResult() {
		var form = document.getElementById("ResultCodingForm");
		form.submit();
	}

	//]]>
</script>
</head>
<body>



	<div id="wrap">

		<div id="header">
			<%@ include file="../inc/header.jsp"%>
		</div>

		<div id="main-menu">
			<%@ include file="../inc/main-menu.jsp"%>
		</div>

		<div id="container">
			<div id="content" style="min-height: 800px;">
				<form id="startCodingForm" action="readCode" method="post">

					<c:if test="${empty memory}">
						<h5 style="font-size: 25px; font-family: nanum gothic;">코드를
							입력해주십시오.</h5>

						<input type="text" id="memory" name="memory" size="50"
							class="input-control" required autofocus />
						<input type="submit" value="전송" class="btn btn-info" />
						<input type="hidden" value="${sum }" name="sum" />

					</c:if>

					<c:if test="${!empty memory && memory != 4300 && memory != -99999}">
						<h5 style="font-size: 25px; font-family: nanum gothic;">다음
							코드를 입력해주십시오.</h5>

						<input type="text" id="memory" name="memory" size="50"
							class="input-control" required autofocus />
						<input type="submit" value="전송" class="btn btn-info" />
						<input type="hidden" value="${sum }" name="sum" />

					</c:if>

					<c:if test="${!empty memory && memory == 4300}">
						<h5 style="font-size: 25px; font-family: nanum gothic;">프로그램이
							종료되었습니다.</h5>
						<h5 style="font-size: 25px; font-family: nanum gothic;">-99999를
							입력하시면 결과화면을 볼 수 있습니다.</h5>

						<input type="text" id="memory" name="memory" size="50"
							class="input-control" required autofocus />
						<input type="submit" value="전송" class="btn btn-info" />
						<input type="hidden" value="${sum }" name="sum" />

					</c:if>

					<c:if test="${!empty memory && memory == -99999}">
						<h5 style="font-size: 25px; font-family: nanum gothic;">후니의
							심플트론을 이용해주셔서 감사합니다.</h5>
						<h5 style="font-size: 25px; font-family: nanum gothic;">하단의
							버튼 클릭하시면 결과화면으로 이동합니다.</h5>

						<input type="button" value="결과 보기" class="btn btn-info"
							onclick='goResult();'>
					</c:if>

					<c:if test="${!empty ic }">
						<h5 style="font-size: 25px; font-family: nanum gothic;">출력값 :
							${ic }</h5>
					</c:if>
					<h5 style="font-size: 25px; font-family: nanum gothic;">${sum }번째
						코드</h5>




				</form>

				<form id="ResultCodingForm" action="resultScreen" method="post">

					<input type="hidden" value="${ic }" name="ic" />

				</form>
				<!--  본문 끝 -->

			</div>
			<!-- content 끝 -->
		</div>
		<!--  container 끝 -->

		<div id="sidebar">
			<%@ include file="board-menu.jsp"%>
		</div>

		<div id="footer">
			<%@ include file="../inc/footer.jsp"%>
		</div>

	</div>


</body>
</html>

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

		location.href = "/simpletron/board/resultScreen";
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
				<form id="resultCodingForm" action="resultScreen" method="post">

					
						<h5 style="font-size: 25px; font-family: nanum gothic;">정수를
							입력해주십시오.</h5>

						<input type="text" id="value" name="value" size="50"
							class="input-control" required autofocus />
						<input type="submit" value="전송" class="btn btn-info" />
						<input type="hidden" value="${ic }" name="ic" />
						<input type="hidden" value="${operand }" name="operand1" />
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

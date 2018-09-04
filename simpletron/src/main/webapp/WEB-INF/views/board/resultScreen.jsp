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
	function goWrite() {
		var form = document.getElementById("upload");
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

				<table class="table div-table list-tbl bg-white">

					<tr>
						<th style="width: 60px; text-align: center;">InstructionCounter</th>
						<th style="width: 84px; text-align: center;">Memory</th>
					</tr>


					<!--  반복 구간 시작 -->

					<c:forEach var="sm" items="${list }" varStatus="status">
						<tr>
							<td style="text-align: center;">${sm.ic }</td>
							<td style="text-align: center;">${sm.memory }</td>

						</tr>
					</c:forEach>

					<!--  반복 구간 끝 -->
				</table>

				<input type="button" value="업로드" class="btn btn-warning"
					onclick="goWrite();" />

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

		<div id="form-group" style="display: none;">
			<form id="upload" action="./upload" method="post">
				<p>
					<input type="hidden" name="boardCd" value="free" /><input
						type="hidden" name="id" value="${check.id}" />
				</p>
			</form>
		</div>

	</div>


</body>
</html>

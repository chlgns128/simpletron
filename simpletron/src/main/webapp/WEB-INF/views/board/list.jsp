<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="Keywords" content="게시판 목록" />
<meta name="Description" content="게시판 목록" />
<link rel="stylesheet" href="../resources/css/screen.css"
	type="text/css" media="screen" />
<title>${boardNm }</title>
<script type="text/javascript">
	//<![CDATA[
	function goList(page) {
		var form = document.getElementById("listForm");
		form.curPage.value = page;
		form.submit();
	}
	function goWrite() {
		var form = document.getElementById("writeForm");
		form.submit();
	}
	function goView(articleNo) {
		var form = document.getElementById("viewForm");
		form.articleNo.value = articleNo;
		form.submit();

	}
	function goCode() {

		location.href = "/simpletron/board/readCode";

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

				<!-- 본문 시작 -->

				<h1>
					<a href="/simpletron/board/list?boardCd=${boardCd }">${boardNm }</a>
				</h1>
				<div id="bbs">
					<table class="table div-table list-tbl bg-white">

						<tr>
							<th style="width: 60px;">번호</th>
							<th>제목</th>
							<th style="width: 84px;">날짜</th>
							<th style="width: 60px;">조회수</th>
							<th style="width: 60px;">추천</th>
						</tr>
						<!--  반복 구간 시작 -->
						<c:forEach var="article" items="${list }" varStatus="status">
							<tr>
								<td style="text-align: center;">${article.articleNo }</td>
								<td><a href="javascript:goView('${article.articleNo }')">${article.title }</a>
									<c:if test="${article.attachFileNum > 0 }">
										<img src="../resources/images/attach.png" alt="첨부파일" />
									</c:if> <c:if test="${article.commentNum > 0 }">
										<span class="bbs-strong">[${article.commentNum }]</span>
									</c:if></td>

								<td style="text-align: center;">${article.writeDate }</td>
								<td style="text-align: center;">${article.hit }</td>
								<td style="text-align: center;">${article.recommend }</td>


							</tr>
						</c:forEach>
						<!--  반복 구간 끝 -->
					</table>
				</div>
				<!--paging-->
				<div id="paging" style="text-align: center;">

					<c:if test="${prevLink > 0 }">
						<a href="javascript:goList('${prevPage }')">[이전]</a>
					</c:if>

					<c:forEach var="i" items="${pageLinks }" varStatus="stat">
						<c:choose>
							<c:when test="${curPage == i}">
								<span class="bbs-strong">${i }</span>
							</c:when>
							<c:otherwise>
								<a href="javascript:goList('${i }')">${i }</a>
							</c:otherwise>
						</c:choose>
					</c:forEach>

					<c:if test="${nextLink > 0 }">
						<a href="javascript:goList('${nextPage }')">[다음]</a>
					</c:if>

				</div>

				<div id="list-menu" style="text-align: right;">
					<c:if test="${boardCd == 'data' && check.id != 'chlgns' }">
					관리자만 자료를 업로드 할 수 있습니다.
					</c:if>
					<c:if test="${boardCd == 'data' && check.id == 'chlgns' }">
						<input type="button" value="업로드" class="btn btn-warning"
							onclick="goWrite()" />
					</c:if>
					<c:if test="${boardCd != 'data' && !empty check.id && boardCd != 'free'}">
						<input type="button" value="질문하기" class="btn btn-warning"
							onclick="goWrite()" />
					</c:if>
					<c:if test="${boardCd != 'data' && !empty check.id && boardCd != 'qna'}">
						<input type="button" value="코드 실행 및 작성" class="btn btn-info"
							onclick="goCode()" />
					</c:if>
				</div>

				<div id="search" style="text-align: center;">
					<form id="searchForm" action="./list" method="get"
						style="margin: 0; padding: 0;">
						<p style="margin: 0; padding: 0;">
							<input type="hidden" name="boardCd" value="${boardCd }" /> <input
								type="text" name="searchWord" size="15" maxlength="30" /> <input
								type="submit" value="검색" />
						</p>
					</form>
				</div>


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

	<div id="form-group" style="display: none;">
		<form id="listForm" action="./list" method="get">
			<p>
				<input type="hidden" name="boardCd" value="${boardCd }" /> <input
					type="hidden" name="curPage" /> <input type="hidden"
					name="searchWord" value="${param.searchWord }" />
			</p>
		</form>

		<form id="writeForm" action="./write" method="get">
			<p>
				<input type="hidden" name="boardCd" value="${boardCd }" /> <input
					type="hidden" name="curPage" value="${curPage }" /> <input
					type="hidden" name="searchWord" value="${param.searchWord }" />
			</p>
		</form>

		<form id="viewForm" action="./view" method="get">
			<p>
				<input type="hidden" name="articleNo" /> <input type="hidden"
					name="boardCd" value="${boardCd }" /> <input type="hidden"
					name="curPage" value="${curPage }" /> <input type="hidden"
					name="searchWord" value="${param.searchWord }" />
			</p>
		</form>
	</div>
	<%-- param.curPage : ${param.curPage }, ${curPage } --%>
</body>
</html>
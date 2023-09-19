<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>

<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value=""> <input
						type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:set var="count" value="${fn:length(list) }" />
					<c:forEach items="${list }" var="vo" varStatus="status">
						<tr>
							<td>${count - status.index}</td>
							<td style="padding-left:${(vo.depth-1)*30}px">
							<c:if test="${vo.depth >= 2}"> 
							<img src="${pageContext.request.contextPath }/assets/images/reply.png">							
							</c:if>
							<a href="${pageContext.request.contextPath }/board?a=detailform&no=${vo.no}&title=${vo.title }&contents=${vo.contents}&g_no=${vo.g_no}&depth=${vo.depth}&o_no=${vo.o_no}">${vo.title }</a></td>
							<td>${vo.name }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<td><c:choose>
									<c:when test="${empty authUser }">

									</c:when>
									<c:when test="${authUser.getNo() == vo.user_no }">
										<a
											href="${pageContext.request.contextPath }/board?a=deleteform&no=${vo.no}"
											class="del">삭제</a>
									</c:when>
									<c:otherwise>

									</c:otherwise>
								</c:choose></td>

						</tr>
					</c:forEach>
				</table>
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:choose>
							<c:when test="${begin == 1}">
							</c:when>
							<c:otherwise>
								<li><a
									href="${pageContext.request.contextPath }/board/${begin - 5}/${begin - 5}">◀</a></li>
							</c:otherwise>
						</c:choose>

						<c:set var="chk" value="true" />
						<c:forEach var="i" begin="${begin }"
							end="${begin + 4 }" step="1">
							<c:if test="${chk}">
								<c:choose>
									<c:when test="${empty list }">
										<c:set var="chk" value="false" />
									</c:when>
									<c:when test="${i-1 == list.get(0).total}">
										<c:set var="chk" value="false" />
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${page == i}">
												<li class="selected"><a
													href="${pageContext.request.contextPath }/board/${begin}/${i}">${i }</a></li>
											</c:when>
											<c:otherwise>
												<li><a
													href="${pageContext.request.contextPath }/board/${begin}/${i}">${i }</a></li>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</c:if>
						</c:forEach>

						<c:choose>
							<c:when test="${empty list }">
							</c:when>
							<c:when test="${begin+5 > list.get(0).total}">
							</c:when>
							<c:otherwise>
								<li><a
									href="${pageContext.request.contextPath }/board/${begin + 5}/${begin + 5}">▶</a></li>
							</c:otherwise>
						</c:choose>
					</ul>
				</div>
				<!-- pager 추가 -->
				<div class="bottom">
				<c:choose>
						<c:when test="${empty authUser }">
						</c:when>
						<c:otherwise>
							<a href="${pageContext.request.contextPath }/board?a=insertboardform" id="new-book">글쓰기</a>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>

</html>
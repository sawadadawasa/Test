<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="../css/member.css" type="text/css">
<title>Insert title here</title>
</head>
<body>
<div>
	<h1>メンバー検索</h1>
</div>
<div id ="sample0">
	<form action="${pageContext.request.contextPath }/member/search"
		method="post" action="#">
		名前（カナ）：<input type="text" name="code" id="code" /> <br> グループ：
		<select
			name="group">
			<option value="0">グループ</option>
			<c:forEach var="groupList" items="${groupList }">
				<option value="${groupList }">${groupList }</option>
			</c:forEach>
		</select>
		<button type="submit" value="検索">検索</button>
		<button type="reset" value="クリア">クリア</button>

	</form>
	</div>
	<c:if test="${count != null}">
		<div id=sample1>
			<h2>
				検索件数：
				<c:out value="${count }" />
				件
			</h2><br>
			<h3>マウスを置くと黄色になるよ</h3>
		</div>
	</c:if>
	<table class="aaa">
		<tr>
			<th>名前（漢字）</th>
			<th>名前（かな）</th>
			<th>メールアドレス</th>
			<th>拠点</th>
			<th>グループ</th>
			<th>入社年月日</th>
		</tr>
		<c:forEach var="member" items="${memberList }">
			<tr>
				<td><c:out value="${member.name }" /></td>
				<td><c:out value="${member.nameKana }" /></td>
				<td><c:out value="${member.mailAddress }" /></td>
				<td><c:out value="${member.area }" /></td>
				<td><c:out value="${member.group }" /></td>
				<td><c:out value="${member.joinDate }" /></td>
			</tr>
		</c:forEach>

	</table>

</body>
</html>
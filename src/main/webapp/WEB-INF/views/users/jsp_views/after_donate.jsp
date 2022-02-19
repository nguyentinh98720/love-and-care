<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
div.vatiDonate>div {
	background-image: url('<c:url value="/static/media/heart.jpg"/>');
	background-repeat: no-repeat;
	background-size: cover;
}
</style>
<div class="container my-5 vatiDonate">
	<div class="card card-body text-center" style="height: 50vh">
		<h5>Cảm ơn <c:out value="${sessionScope.user.firstName}"/> <c:out value="${sessionScope.user.lastName}"/> đã đóng góp cho chương trình</h5>
		<p><c:out value="${param.eventTitle}"/></p>
		<div class="p-5 d-flex justify-content-center">
			<a href="https://love-and-care.herokuapp.com/greeting/home" class="m-5 btn btn-success">Về trang chủ</a>
			<form action="https://love-and-care.herokuapp.com/quyen-gop/buoc-1" method="post">
				<input type="hidden" name="eventId" value='<c:out value="${param.eventId}"/>'>
				<input type="hidden" name="eventTitle" value='<c:out value="${param.eventTitle}"/>'>
				<input type="submit" class="m-5 btn btn-info" value="Tiếp tục quyên góp">
			</form>
		</div>
	</div>
</div>

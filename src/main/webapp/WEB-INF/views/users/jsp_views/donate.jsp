<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
div.vatiDonate>div {
	background-image: url('<c:url value="/static/media/heart.jpg"/>');
	background-repeat: no-repeat;
	background-size: cover;
}
</style>
<div class="container vatiDonate">
	<div class="card card-body my-5 p-5">
		<p class="text-danger text-center" id="warningDonate"></p>
		<form action="https://love-and-care.herokuapp.com/quyen-gop/buoc-2" method="post" onsubmit="return verifyDonate()">
			<input type="hidden" name="eventId" value='<c:out value="${requestScope.eventId}"/>'>
			<table class="table table-borderless my-5">
				<tr>
					<th>Đến chương trình:</th>
					<th class="text-dark"><c:out value="${requestScope.eventTitle}"/></th>
				</tr>
				<tr>
					<th>Cách thức quyên góp:</th>
					<td><label for="test"><input type="radio" id="test"
							name="donateAction" value="test" checked> Quyên góp thường</label><br>
						<label for="paypal"><input type="radio" id="paypal"
							name="donateAction" value="paypal"> Quyên góp qua Paypal</label>
					</td>
				</tr>
				<tr>
					<th>Số tiền quyên góp:</th>
					<td><input type="number" id="donateMoney" class="form-control" min="0" name="money"></td>
				</tr>
				<tr>
					<th>-</th>
					<td><strong id="readMoney"></strong></td>
				</tr>
				<tr>
					<th>Lời nhắn:</th>
					<td><textarea id="message" rows="2" class="form-control" name="message"></textarea>
					</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="secret" id="checkboxSecret">
						<label for="checkboxSecret" class="fw-bold" style="cursor:pointer;">
							<small class="user-select-none text-secondary" id="secretSelect">Giấu thông tin cho lần quyên góp này</small>
						</label>
					</td>
					<td class="d-flex justify-content-center">
						<a href='https://love-and-care.herokuapp.com/quyen-gop/noi-dung-chuong-trinh?eventId=<c:out value="${requestScope.eventId}"/>'
							role="button" class="btn btn-secondary mx-5">Quay lại</a>
						<input type="submit" value="Quyên góp" class="btn btn-info text-light mx-5" id="submitButton">
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
<script type="text/javascript" src='<c:url value="/static/client/js/donateAction.js"/>'></script>

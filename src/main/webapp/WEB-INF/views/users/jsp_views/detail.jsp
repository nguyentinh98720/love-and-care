<%@page import="java.util.Collections"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.tinhnv.model.donate.Donate"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.Currency"%>
<%@page import="org.apache.tomcat.jni.Local"%>
<%@page import="java.util.Calendar"%>
<%@page import="org.apache.tomcat.util.codec.binary.Base64"%>
<%@page import="com.tinhnv.model.others.ImgEvent"%>
<%@page import="java.util.List"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.sql.Date"%>
<%@page import="com.tinhnv.model.event.EventFullInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
EventFullInfo donation = (EventFullInfo) request.getAttribute("donation");
int id = donation.getId();
String title = donation.getTitle();
String detail = donation.getDetail();
Date startDate = donation.getStartDate();
Date endDate = donation.getEndDate();
BigDecimal purpose = donation.getPurpose();
BigDecimal achievement = donation.getAchievement();
boolean status = donation.getStatus();
List<ImgEvent> images = donation.getImages();
long donationCount = donation.getDonationCount();

String timeActiveOfEvent = "Chương trình đang diễn ra";
Calendar start = Calendar.getInstance();
Date now = new Date(System.currentTimeMillis());
start.setTime(now);
Calendar end = Calendar.getInstance();
boolean acceptDonate = false;
if (endDate != null) {
	end.setTime(endDate);
	long days = (end.getTime().getTime() - start.getTime().getTime()) / (1000 * 60 * 60 * 24);
	if (days >= 0) {
		acceptDonate = true;
		timeActiveOfEvent = "<span class='badge bg-info'>Còn lại " + days + " ngày</span>";
	} else {
		timeActiveOfEvent = "<span class='badge bg-secondary'>Đã kết thúc</span>";
	}
} else {
	acceptDonate = true;
	timeActiveOfEvent = "<span class='badge bg-success'><small>Chương trình được tổ chức dài hạn</small></span>";
}
%>
<%!String sourceOfImage(ImgEvent image) {
		String type = image.getType();
		byte[] arr = image.getArray();
		String encoderFile = Base64.encodeBase64String(arr);
		return "<br><img src='data:" + type + ";base64," + encoderFile + "' style='width:100%;'/><br>";
}%>
<style>
figure.image>img, p>img {
	width: 100% !important;
}
</style>
<!-- Begin Page Content -->
<div class="container">

	<div class="row mt-4">

		<div class="col-lg-8" style="position:relative;">

			<div class="mb-4">
				<h4><%=title%></h4>
				<!-- title detail and images -->
				<%
				out.println(detail);
				%>
			</div>
			<%
			if (acceptDonate) {
			%>
			<div class="text-center py-1 mb-4"
				style="position: sticky; bottom: 0; background-color: #00000010; display:none;"
				id="labelOfDonateButton">
				<label for="DonateNow" class="btn btn-danger">Quyên góp ngay</label>
			</div>
			<% } %>
		</div>

		<!-- Second Column -->
		<div class="col-lg-4">

			<!-- Background Gradient Utilities -->
			<div class="card shadow mb-4">
				<div class="card-header py-3">
					<h6 class="fw-light">TIến độ</h6>
				</div>
				<div class="card-body">
					<div class="no-gutters align-items-center">
						<div>
							<div class="text-success mb-1">Tổng số lượt quyên góp</div>
							<div class="h5 font-weight-bold text-gray-800">
								<!-- tổng số lượt quyên góp -->
								<%=donationCount%>
							</div>
						</div>
					</div>
					<div class="no-gutters align-items-center">
						<div>
							<div class="text-info mb-1">Đã quyên góp được</div>
							<div class="no-gutters align-items-center">
								<div>
									<p class="font-weight-bold text-gray-800">
										<!-- số tiền quyên góp được đến thời điểm hiện tại -->
										<%
										Locale locale = new Locale("vi", "VN");
										achievement = achievement.setScale(0);
										purpose = purpose.setScale(0);
										NumberFormat format = NumberFormat.getCurrencyInstance(locale);
										out.println(format.format(achievement.doubleValue()) + " / " + format.format(purpose.doubleValue()));
										%>
									</p>
								</div>
								<div>
									<div class="progress mb-2">
										<%
										double result = achievement.doubleValue() / purpose.doubleValue() * 100;
										int percent = (int) result;
										%>
										<!-- tính toán và ghi số % ở đây nữa-->
										<div class="progress-bar bg-info" role="progressbar"
											style="width:<%=percent%>%"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="no-gutters align-items-center">
						<div>
							<div class="text-warning mt-3">Trạng thái</div>
							<div class="h5 font-weight-bold text-gray-800">
								<!-- tính toán và viết số ngày còn lại vào đây -->
								<%= timeActiveOfEvent%>
							</div>
						</div>
					</div>
					<%
					if (acceptDonate) {
					%>
					<div class="d-flex justify-content-center">
						<form
							action="https://love-and-care.herokuapp.com/quyen-gop/buoc-1"
							method="post">
							<input type="hidden" name="eventId" value="<%=id%>">
							<input type="submit" class="mt-3 px-5 btn btn-outline-danger"
								value="Quyên góp" id="DonateNow">
						</form>
					</div>
					<%
					}
					%>
				</div>
			</div>
			<div class="card border-success mb-4">
				<div class="card-header py-3">
					<h6 class="fw-light">Nhà hảo tâm mới nhất</h6>
				</div>
				<div class="card-body">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>Nhà hảo tâm</th>
								<th>Đóng góp</th>
								<!-- <th>Ngày</th>
								<th>Lời nhắn</th> -->
							</tr>
						</thead>
						<tbody style="cursor: pointer">
							<!-- bảng danh sách ở đây DANH SÁCH TOP 10 TÀI KHOẢN ĐÓNG GÓP GẦN ĐÂY NHẤT -->
							<%
							List<Donate> topDonate = donation.getTopDonate();
							int rowI = 0;
							for (Donate donate : topDonate) {
								String accountName = donate.getAccountName();
								BigDecimal money = donate.getMoney();
								Date date = donate.getDate();
								String message = donate.getMessage();
								if(message.length() > 15) {message = message.substring(0, 15) + "...";}
								boolean secret = donate.isSecret();
								out.println("<tr onclick='showMoreVati(" + rowI + ")'><td>" + (secret ? "Nhà hảo tâm" : accountName) + "</td><td>"
								+ format.format(money.doubleValue()) + "</td></tr><tr id='rowVati" + rowI
								+ "' style='display:none' class='fw-light fs-6'><td><i class='fas fa-clock'></i> "
								+ new SimpleDateFormat("dd/MM/yyy").format(date) + "</td><td><i class='fas fa-sms'></i> " + message
								+ "</td></tr>");
								rowI++;
							}
							%>
						</tbody>
					</table>
				</div>
			</div>
			<div class="card border-warning mb-4">
				<div class="card-header py-3">
					<h6 class="fw-light">Nhà hảo tâm đóng góp nhiều nhất</h6>
				</div>
				<div class="card-body">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>Nhà hảo tâm</th>
								<th>Đóng góp</th>
								<!-- <th>Ngày</th>
								<th>Lời nhắn</th> -->
							</tr>
						</thead>
						<tbody style="cursor: pointer">
							<!-- bảng danh sách ở đây DANH SÁCH TOP 10 TÀI KHOẢN ĐÓNG GÓP NHIỀU NHẤT -->
							<%
							List<Donate> richDonate = donation.getRichDonate();
							int rowY = 0;
							for (Donate donate : richDonate) {
								String accountName = donate.getAccountName();
								BigDecimal money = donate.getMoney();
								Date date = donate.getDate();
								String message = donate.getMessage();
								if(message.length() > 15) {message = message.substring(0, 15) + "...";}
								boolean secret = donate.isSecret();
								out.println("<tr onclick='showMoreVaty(" + rowY + ")'><td>" + (secret ? "Nhà hảo tâm" : accountName) + "</td><td>"
								+ format.format(money.doubleValue()) + "</td></tr><tr id='rowVaty" + rowY
								+ "' style='display:none;' class='fw-light fs-6'><td><i class='fas fa-clock'></i> "
								+ new SimpleDateFormat("dd/MM/yyy").format(date) + "</td><td><i class='fas fa-sms'></i> " + message
								+ "</td></tr>");
								rowY++;
							}
							%>
						</tbody>
					</table>
				</div>
			</div>
		</div>

	</div>

</div>
<!-- /.container-->

<script src='<c:url value="/static/client/js/detail.js"/>'></script>
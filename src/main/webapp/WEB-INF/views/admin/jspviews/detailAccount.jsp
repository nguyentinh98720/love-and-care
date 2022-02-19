<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<c:set var="account" value="${requestScope.account}" />
<c:set var="avatar" value="${account.avatar}"/>
<!-- Begin Page Content -->
<div class="container-fluid">
	<div class="card shadow mb-4">
		<div class="card-header py-3 d-flex justify-content-between align-items-center">
			<h6 class="m-0 font-weight-bold text-primary">Nhà hảo tâm</h6>
			<div class="m-0 p-0">
				<a class="m-2 btn btn-outline-info pr-2"
					href='${pageContext.request.contextPath}/tai-khoan/cap-nhat-tai-khoan?accountId=<c:out value="${account.id}"/>'
					title="Chỉnh sửa thông tin"> <i class="fa fa-edit"
					aria-hidden="true"></i>
				</a>
			</div>
		</div>
		<div class="card-body">
			<div class="row">
				<div class="col-lg-4 text-center">
					<c:choose>
						<c:when test="${account.avatar.type == null}">
							<img src='${pageContext.request.contextPath}/static/media/user.png'
								alt='avatar' class='img-thumbnail' width='200' />
						</c:when>
						<c:otherwise>
							<img src='data:<c:out value="${avatar.type}"/>;base64,<c:out value="${avatar.encode}"/>'
								alt='avatar' class='img-thumbnail' width='200' />
						</c:otherwise>
					</c:choose>
				</div>
				<div class="col-lg-8">
					<table class="table text-center">
						<tr>
							<td><i class="fas fa-signature"></i></td>
							<td><c:out value="${account.firstName}" /> <c:out value="${account.lastName}" /></td>
						</tr>
						<tr>
							<td><i class="fas fa-envelope-square"></i></td>
							<td><c:out value="${account.email}" /></td>
						</tr>
						<tr>
							<td><i class="fas fa-map-marker-alt"></i></td>
							<td><c:out value="${account.address}" /></td>
						</tr>
						<tr>
							<td><i class="fas fa-phone"></i></td>
							<td><c:out value="${account.phoneNumber}" /></td>
						</tr>
						<tr>
							<td><i class="fas fa-user-cog"></i></td>
							<td><c:out value="${account.role}" /></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="mt-4">
				<table class="table table-dark table-striped text-center">
					<tr>
						<td>Đã tham gia:</td>
						<td><c:out value="${account.numEventJoined}" /> chương trình</td>
					</tr>
					<tr>
						<td>Tổng số tiền đã đóng góp:</td>
						<td><fmt:formatNumber value="${account.totalMoneySpend}"
								type="number" maxFractionDigits="0" /> vnd</td>
					</tr>
					<tr>
						<td>Tham gia ủng hộ</td>
						<td><c:out value="${account.numDonateAction}" /> lần</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>
<!-- /.container-fluid -->
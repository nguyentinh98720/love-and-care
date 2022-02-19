<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class="row justify-content-center">
	<div class="col-xl-5 col-lg-6 col-md-12 py-5">
		<div class="card o-hidden border-0 shadow-lg my-5">
			<div class="card-body p-0">
				<!-- Nested Row within Card Body -->
				<div class="p-5">
					<div class="text-center">
						<h1 class="h4 text-gray-900 mb-2">Quên mật khẩu?</h1>
						<p class="mb-4">
<c:choose>
<c:when test="${param.message != null}">
						<i class="text-danger"><c:out value="${param.message}"/></i>
</c:when>
<c:otherwise>
						Nhập địa chỉ email đã đăng ký cho tài khoản. Kiểm tra hộp thư để lấy mật khẩu mới.
</c:otherwise>
</c:choose>
						</p>
					</div>
<!-- Set link to submit -->
					<form class="user" action="${pageContext.request.contextPath}/greeting/lay-lai-mat-khau"
						method="post" onsubmit="return verifyResetPassword()">
						<div class="form-group d-flex justify-content-center vatiSet">
							<span class="badge badge-warning text-danger" id="emailWarning"></span>
							<input type="email" class="form-control form-control-user"
								id="inputEmail" aria-describedby="emailHelp"
								placeholder="Email" name="email" value='<c:out value="${param.email}"/>'>
						</div>
						<input type="submit" class="btn btn-primary btn-user btn-block"
							value="Khôi phục mật khẩu">
					</form>
					<hr>
					<div class="text-center">
<!-- Link here create account -->
						<a class="small" href="${pageContext.request.contextPath}/greeting/dang-ky-tai-khoan">Đăng ký tài khoản!</a>
					</div>
					<div class="text-center">
<!-- Link here login -->
						<a class="small" href="${pageContext.request.contextPath}/greeting/dang-nhap">Đã có tài khoản? Đăng nhập!</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src='<c:url value="/static/client/js/resetPassword.js"/>'></script>
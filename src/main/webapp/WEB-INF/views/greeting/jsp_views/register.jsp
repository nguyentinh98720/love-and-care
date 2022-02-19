<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="card o-hidden border-0 shadow-lg my-5">
	<div class="card-body p-0">
		<div class="p-5">
			<div class="text-center">
				<h1 class="h4 text-gray-900 mb-4">Đăng ký tài khoản</h1>
<c:if test="${requestScope.message != null}">
				<p class="small text-warning"><c:out value="${requestScope.message}"/></p>
</c:if>
			</div>
			<form class="user" action="${pageContext.request.contextPath}/greeting/dang-ky-tai-khoan"
					onsubmit="return verifyForm()" method="post">
				<div class="form-group row">
				<input type="hidden" name="page" value="register"/>
					<div class="col-sm-6 mb-3 mb-sm-0 d-flex justify-content-center vatiSet">
						<span class="badge badge-warning text-danger" id="firstNameWarning"></span>
						<input type="text" class="form-control form-control-user"
							id="inputFirstName" placeholder="Tên *" name="firstName">
					</div>
					<div class="col-sm-6 d-flex justify-content-center vatiSet">
						<span class="badge badge-warning text-danger" id="lastNameWarning"></span>
						<input type="text" class="form-control form-control-user"
							id="inputLastName" placeholder="Họ" name="lastName">
					</div>
				</div>
				<div class="form-group d-flex justify-content-center vatiSet">
					<span class="badge badge-warning text-danger" id="emailWarning"></span>
					<input type="email" class="form-control form-control-user"
						id="inputEmail" placeholder="Email *" name="email"
						value='<c:out value="${requestScope.email}"/>'>
				</div>
				<div class="form-group row">
					<div class="col-12 mb-3 mb-sm-0 d-flex justify-content-center vatiSet">
						<span class="badge badge-warning text-danger" id="phoneNumberWarning"></span>
						<input type="text" class="form-control form-control-user"
							id="inputPhoneNumber" placeholder="Số điện thoại" name="phoneNumber">
					</div>
					<input type="hidden" id="inputAddress" name="address">
					<span class="badge badge-warning text-danger" id="addressWarning"></span>
					<div class="col-lg-4 mt-3">
						<input list="province" type="text" class="form-control form-control-user" id="getProvince" placeholder="Tỉnh / Thành phỗ">
						<datalist id="province"></datalist>
					</div>
					<div class="col-lg-4 mt-3">
						<input list="district" type="text" class="form-control form-control-user" id="getDistrict" placeholder="Quận huyện / Thị xã">
						<datalist id="district"></datalist>
					</div>
					<div class="col-lg-4 mt-3">
						<input list="wards" type="text" class="form-control form-control-user" id="getWards" placeholder="Phường / Xã / Thị trấn">
						<datalist id="wards"></datalist>
					</div>
				</div>
				<div class="row d-flex justify-content-center my-3">
					<input type="submit"
						class="col-lg-4 btn btn-primary btn-user btn-block"
						value="Đăng ký" />
				</div>
				<div class="row d-flex justify-content-center align-items-center">
					<hr class="p-0 col-lg-5 d-none d-lg-block">
					<p class="small col-lg-2 p-0 text-center">Hoặc</p>
					<hr class="p-0 col-lg-5 d-none d-lg-block">
				</div>
				<div class="row d-flex justify-content-around">
<!-- Link here - register with google -->
					<button type="button" class="col-lg-3 btn btn-google btn-user btn-block" id="glogin">
						<i class="fab fa-google fa-fw"></i> Đăng nhập với Google
					</button> <i class="d-none"></i>
<!-- Link here - register with facebook -->
					<button type="button" class="col-lg-3 btn btn-facebook btn-user btn-block" onclick="fblogin()">
						<i class="fab fa-facebook-f fa-fw"></i> Đăng nhập với Facebook
					</button>
				</div>
			</form>
			<hr>
			<div class="text-center">
<!-- Link here - forgot password -->
				<a class="small" href="${pageContext.request.contextPath}/greeting/quen-mat-khau">Bạn quên mật khẩu?</a>
			</div>
			<div class="text-center">
<!-- Link here - login -->
				<a class="small" href="${pageContext.request.contextPath}/greeting/dang-nhap">Đã có tài khoản? Đăng nhập!</a>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src='<c:url value="/static/client/js/register.js"/>'></script>
<script type="text/javascript" src='<c:url value="/static/client/js/googleAndFacebookLogin.js"/>'></script>
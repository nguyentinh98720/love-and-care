<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class="row justify-content-center">
	<div class="col-xl-5 col-lg-6 col-md-12">
		<div class="card o-hidden border-0 shadow-lg my-5">
			<div class="card-body p-0">
				<div class="p-5">
					<div class="text-center" id="messageArea">
<c:choose>
<c:when test="${requestScope.message != null}">
						<p class="text-danger"><c:out value="${requestScope.message}"/></p>
</c:when>
<c:when test="${param.message != null}">
						<p class="text-danger"><c:out value="${param.message}"/></p>
</c:when>
<c:otherwise>
						<h1 class="h4 text-gray-900 mb-4">Welcome Back!</h1>
</c:otherwise>
</c:choose>
					</div>
<!-- Set link submit form here -->
					<form class="user" action="${pageContext.request.contextPath}/greeting/dang-nhap"
											method="post" onsubmit="return verifyLogin()">
<c:choose>
<c:when test="${param.email != null}">
						<div class="form-group d-flex justify-content-center vatiSet">
							<span class="badge badge-warning text-danger" id="emailWarning"></span>
							<input type="email" class="form-control form-control-user" name="email"
								id="inputEmail" aria-describedby="emailHelp" placeholder="Email"
								value='<c:out value="${param.email}"/>'>
						</div>
						<div class="form-group d-flex justify-content-center vatiSet">
							<span class="badge badge-warning text-danger" id="passwordWarning"></span>
							<input type="password" class="form-control form-control-user" name="password"
								id="inputPassword" placeholder="Mật khẩu">
						</div>
</c:when>
<c:when test="${requestScope.message != null}">
						<div class="form-group d-flex justify-content-center vatiSet">
							<span class="badge badge-warning text-danger" id="emailWarning"></span>
							<input type="email" class="form-control form-control-user" name="email"
								id="inputEmail" aria-describedby="emailHelp" placeholder="Email"
								value='<c:out value="${requestScope.email}"/>'>
						</div>
						<div class="form-group d-flex justify-content-center vatiSet">
							<span class="badge badge-warning text-danger" id="passwordWarning"></span>
							<input type="password" class="form-control form-control-user" name="password"
								id="inputPassword" placeholder="Mật khẩu">
						</div>
</c:when>
<c:otherwise>
						<div class="form-group d-flex justify-content-center vatiSet">
							<span class="badge badge-warning text-danger" id="emailWarning"></span>
							<input type="email" class="form-control form-control-user" name="email"
								id="inputEmail" aria-describedby="emailHelp" placeholder="Email"
								value='<c:out value="${cookie.emailRemember.value}"/>'>
						</div>
						<div class="form-group d-flex justify-content-center vatiSet">
							<span class="badge badge-warning text-danger" id="passwordWarning"></span>
							<input type="password" class="form-control form-control-user" name="password"
								id="inputPassword" placeholder="Mật khẩu"
								value='<c:out value="${cookie.passwordRemember.value}"/>'>
						</div>
</c:otherwise>
</c:choose>
						<div class="form-group">
							<div class="custom-control custom-checkbox small">
								<input type="checkbox" class="custom-control-input" name="remember" id="customCheck">
								<label class="custom-control-label" for="customCheck"> Nhớ mật khẩu </label>
							</div>
						</div>
						<input type="submit" class="btn btn-primary btn-user btn-block"
							value="Đăng nhập">
						<hr>
<!-- login with google -->
						<button type="button" class="btn btn-google btn-user btn-block"  id="glogin">
							<i class="fab fa-google fa-fw"></i> Đăng nhập bằng Google
						</button>
<!-- login with facebook -->
						<button type="button" class="btn btn-facebook btn-user btn-block" onclick="fblogin()">
							<i class="fab fa-facebook-f fa-fw"></i> Đăng nhập bằng Facebook
						</button>
					</form>
					<hr>
					<div class="text-center">
<!-- Link here forgot password -->
						<a class="small" href="${pageContext.request.contextPath}/greeting/quen-mat-khau">Quên mật khẩu?</a>
					</div>
					<div class="text-center">
<!-- Link here create new Account -->
						<a class="small" href="${pageContext.request.contextPath}/greeting/dang-ky-tai-khoan">Đăng ký tài khoản!</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src='<c:url value="/static/client/js/login.js"/>'></script>
<script type="text/javascript" src='<c:url value="/static/client/js/googleAndFacebookLogin.js"/>'></script>
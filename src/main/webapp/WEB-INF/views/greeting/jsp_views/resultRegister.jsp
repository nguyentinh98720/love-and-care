<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="result" value="${param.result}"/>

<div class="row justify-content-center">

	<div class="col-xl-5 col-lg-6 col-md-12 py-5">

		<div class="card o-hidden border-0 shadow-lg my-5">
			<div class="card-body p-0">
				<!-- Nested Row within Card Body -->
				<div class="p-5">
					<div class="text-center">
<c:choose>
<c:when test="${result == true}">
						<h1 class="h4 text-gray-900 mb-2">Tài khoản mới</h1>
						<p class="mb-4">Kiểm tra hộp thư email của bạn để lấy mật khẩu.</p>
<!-- Set link to login -->
						<a href="${pageContext.request.contextPath}/greeting/dang-nhap"
							role="button" class="btn btn-primary btn-user btn-block">Đăng nhập</a>
</c:when>
<c:otherwise>
						<h1 class="h4 text-gray-900 mb-2">Đăng ký không thành công!</h1>
						<p class="mb-4 text-danger">Xin lỗi vì sự bất tiện này, chúng tôi không thể hoàn thành việc đăng ký tài khoản. Hãy kiểm tra địa chỉ email của bạn và thử lại.</p>
</c:otherwise>
</c:choose>
					</div>
					<hr>
					<div class="text-center">
<!-- Link here create account -->
						<a class="small" href="${pageContext.request.contextPath}/greeting/dang-ky-tai-khoan">Đăng ký tài khoản!</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
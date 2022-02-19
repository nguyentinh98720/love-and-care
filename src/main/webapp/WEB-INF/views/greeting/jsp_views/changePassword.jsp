<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row justify-content-center">
	<div class="col-xl-5 col-lg-6 col-md-12">
		<div class="card o-hidden border-0 shadow-lg my-5">
			<div class="card-body p-0">
				<div class="p-5">
					<div class="text-center">
<c:choose>
<c:when test="${requestScope.message != null}">
						<p class="text-info"><c:out value="${requestScope.message}"/></p>
</c:when>
<c:otherwise>
						<h1 class="h4 text-gray-900 mb-4">Đổi mật khẩu</h1>
</c:otherwise>
</c:choose>
					</div>
					<form class="user" action="${pageContext.request.contextPath}/greeting/doi-mat-khau" method="post"
								onsubmit="return verifyChagePassword()">
						<input type="hidden" name="userId" value='<c:out value="${sessionScope.user.id}"/>'/>
						<div class="form-group d-flex justify-content-center vatiSet">
							<span class="badge badge-warning text-danger" id="oldPassWarning"></span>
							<input type="password" class="form-control form-control-user" name="oldPass"
								id="inputOldPass" placeholder="Mật khẩu cũ" autocomplete="current-password">
						</div>
						<hr>
						<div class="form-group d-flex justify-content-center vatiSet">
							<span class="badge badge-warning text-danger" id="newPassWarning"></span>
							<input type="password" class="form-control form-control-user" name="newPass"
								id="inputNewPass" placeholder="Mật khẩu mới" autocomplete="new-password">
						</div>
						<div class="form-group d-flex justify-content-center vatiSet">
							<span class="badge badge-warning text-danger" id="repeatPassWarning"></span>
							<input type="password" class="form-control form-control-user"	name="repeatPass"
								id="inputRepeatPass" placeholder="Nhập lại mật khẩu mới" autocomplete="repeat-password">
						</div>
						<hr>
						<input type="submit" class="btn btn-primary btn-user btn-block"
							value="Đổi mật khẩu">
					</form>
					<hr>
					<div class="text-center">
<!-- Link here homepage -->
						<a class="small" href="${pageContext.request.contextPath}/greeting/trang-chinh">Hủy bỏ!</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src='<c:url value="/static/client/js/changePassword.js"/>'></script>
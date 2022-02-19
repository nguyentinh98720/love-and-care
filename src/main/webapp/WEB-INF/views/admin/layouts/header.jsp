<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="user" value="${sessionScope.user}"/>
<c:set var="avatar" value="${user.avatar}"/>
<!-- Topbar -->
<nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

	<!-- Sidebar Toggle (Topbar) -->
	<button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
		<i class="fa fa-bars"></i>
	</button>

	<!-- Topbar Navbar -->
	<ul class="navbar-nav ml-auto">
		<li class="nav-item no-arrow">
<!-- LINK HERE go to main webpage -->
			<a href="${pageContext.request.contextPath}/greeting/home" id="toMainSite-vatinguyen" class="btn btn-sm btn-primary mt-3" role="button">
				<i class="fa fa-home"></i>
				<span>Vào trang web</span>
			</a>
		</li>

		<div class="topbar-divider d-none d-sm-block"></div>

		<!-- Nav Item - User Information -->
		<li class="nav-item dropdown no-arrow">
			<span class="nav-link dropdown-toggle" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
<!-- NAME AND IMAGE tên và hình ảnh của tài khoản đang đăng nhập -->
				<span class="mr-2 d-lg-inline text-gray-600 small"><c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/>
				</span>
<c:choose>
<c:when test="${avatar.arr != null}">
					<img class="img-profile rounded-circle" alt="avatar" src='data:<c:out value="${avatar.type}"/>;base64,<c:out value="${avatar.encode}"/>'>
</c:when>
<c:otherwise>
					<img class="img-profile rounded-circle" alt="avatar" src='<c:url value="/static/media/user.png"/>'>
</c:otherwise>
</c:choose>
				</span>
				
		 <!-- Dropdown - User Information -->
			<div class="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown">
<!-- LINK HERE dẫn đên trang cá nhân -->
				<a class="dropdown-item" href="https://love-and-care.herokuapp.com/quan-ly/thong-tin-tai-khoan">
					<i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
						Trang cá nhân
				</a>
				<div class="dropdown-divider"></div>
<!-- LINK HERE về trang chủ -->
				<a class="dropdown-item" href="#"
					data-toggle="modal" data-target="#logoutModal">
					<i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
					Đăng xuất
				</a>
			</div>
		</li>
	</ul>
</nav>
<!-- End of Topbar -->
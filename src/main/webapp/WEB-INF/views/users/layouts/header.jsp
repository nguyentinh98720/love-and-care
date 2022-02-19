<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <header class="p-4 bg-danger bg-gradient">
        <div class="container">
          <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
            <a href="https://love-and-care.herokuapp.com/greeting/home" class="d-flex align-items-center mb-2 mb-lg-0 text-light text-decoration-none">
                <i class="h1 far fa-heart"></i>
                <h4>LoveAndCare</h4>
            </a>
            
            <ul class="nav col-12 col-lg-auto me-lg-auto ms-4 mb-2 justify-content-center mb-md-0">
              <li><a href="https://love-and-care.herokuapp.com/greeting/home" class="nav-link px-2 link-light">Home</a></li>
              <li><a href="#hanging-icons" class="nav-link px-2 link-light">Cộng tác</a></li>
              <li><a href="#hanging-icons" class="nav-link px-2 link-light">Về trang web</a></li>
            </ul>
    
            <div class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3">
<!-- LINK SEARCH HERE -->
              <form action="https://love-and-care.herokuapp.com/action/tim-kiem-chuong-trinh" class="input-group" id="searchFormHeader">
                  <input type="search" class="form-control text-secondary" placeholder="Tìm kiếm chương trình" aria-label="Search" name="titleSearch">
                  <button type="submit" class="btn btn-outline-info text-light">
                    <i class="fas fa-search"></i>
                  </button>
              </form>
            </div>
<c:choose>
<c:when test="${sessionScope.user != null}">
<c:set var="account" value="${sessionScope.user}"/>
<c:set var="image" value="${account.avatar}"/>
            <div class="dropdown text-end">
              <a href="#" class="d-block link-light text-decoration-none dropdown-toggle" id="dropdownUser1"
					data-bs-toggle="dropdown" aria-expanded="false">
<c:choose>
<c:when test="${image.encode != null}">
                <img src='data:<c:out value="${image.type}"/>;base64,<c:out value="${image.encode}"/>' alt="avatar"
                		width="32" height="32" class="rounded-circle">
</c:when>
<c:otherwise>
                <img src='<c:url value="/static/media/user.png"/>' alt="avatar" width="32" height="32" class="rounded-circle">
</c:otherwise>
</c:choose>
              </a>
              <ul class="dropdown-menu text-small" aria-labelledby="dropdownUser1">
<c:if test="${account.role eq 'ROLE_ADMIN'}">
				<li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/tong-quan">Dashboard</a></li>
				<li><hr class="dropdown-divider"></li>
</c:if>
<!-- LINK TO ACCOUNT PROFILE -->
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/quan-ly/thong-tin-tai-khoan">
                	<span><c:out value="${account.firstName}"/> <c:out value="${account.lastName}"/></span></a></li>
<!-- HISTORY DONATION -->
                <li><a class="dropdown-item" href="https://love-and-care.herokuapp.com/quan-ly/lich-su-quyen-gop">Lịch sử quyên góp</a></li>
                <li><hr class="dropdown-divider"></li>
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/greeting/dang-xuat">Đăng xuất</a></li>
              </ul>
            </div>
</c:when>
<c:otherwise>
			<ul class="nav">
		        <li class="nav-item"><a href="${pageContext.request.contextPath}/greeting/dang-nhap" class="nav-link btn btn-outline-danger text-light px-2 mx-1">Đăng nhập</a></li>
		        <li class="nav-item"><a href="${pageContext.request.contextPath}/greeting/dang-ky-tai-khoan" class="nav-link btn btn-outline-danger text-light px-2 mx-1">Đăng ký</a></li>
		    </ul>
</c:otherwise>
</c:choose>
          </div>
        </div>
      </header>
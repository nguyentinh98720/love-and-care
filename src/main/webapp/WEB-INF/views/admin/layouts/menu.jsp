<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>


<c:set var="pageName" value="${requestScope.page}"/>
<!-- Sidebar -->
        <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

            <!-- Sidebar - Brand -->
<!-- LINK HERE vào trang chính của website -->
            <a class="sidebar-brand d-flex align-items-center justify-content-center" href="https://love-and-care.herokuapp.com/admin/tong-quan">
                <div class="sidebar-brand-icon rotate-n-15">
                    <i class="fas fa-heart"></i>
                </div>
                <div class="sidebar-brand-text mx-3">Love$Care</div>
            </a>

            <!-- Divider -->
            <hr class="sidebar-divider my-0">

            <!-- Nav Item - Dashboard -->
<!-- LINK HERE trang tổng quan -->
<c:choose>
<c:when test="${pageName eq 'dashboard'}">
            <li class="nav-item active">
</c:when>
<c:otherwise>
            <li class="nav-item">
</c:otherwise>
</c:choose>
                <a class="nav-link" href="https://love-and-care.herokuapp.com/admin/tong-quan">
                    <i class="fas fa-fw fa-tachometer-alt"></i>
                    <span>Tổng quan</span></a>
            </li>

            <!-- Divider -->
            <hr class="sidebar-divider">

            <!-- Heading -->
            <div class="sidebar-heading">
                Chương trình quyên góp
            </div>

            <!-- Nav Item - Donation manage -->
<c:choose>
<c:when test="${pageName eq 'eventManage'}">
            <li class="nav-item active">
</c:when>
<c:otherwise>
            <li class="nav-item">
</c:otherwise>
</c:choose>

<!-- LINK HERE quản lý chương trình -->
                <a class="nav-link collapsed" href="https://love-and-care.herokuapp.com/admin/quan-ly-chuong-trinh">
                    <i class="fas fa-fw fa-cog"></i>
                    <span>Quản lý chương trình</span>
                </a>
            </li>

            <!-- Nav Item - New donation event -->
<!-- LINK HERE thêm chương trình mới -->
<c:choose>
<c:when test="${pageName eq 'newEvent'}">
            <li class="nav-item active">
</c:when>
<c:otherwise>
            <li class="nav-item">
</c:otherwise>
</c:choose>

                <a class="nav-link collapsed" href="https://love-and-care.herokuapp.com/admin/them-chuong-trinh">
                    <i class="fas fa-fw fa-plus-circle"></i>
                    <span>Thêm mới</span>
                </a>
            </li>

            <!-- Divider -->
            <hr class="sidebar-divider">

            <!-- Heading -->
            <div class="sidebar-heading">
                Tài khoản
            </div>

            <!-- Nav Item - Account manage -->
<c:choose>
<c:when test="${pageName eq 'accountManage'}">
            <li class="nav-item active">
</c:when>
<c:otherwise>
            <li class="nav-item">
</c:otherwise>
</c:choose>

<!-- LINK HERE quản lý tài khoản -->
                <a class="nav-link collapsed" href="https://love-and-care.herokuapp.com/admin/quan-ly-tai-khoan">
                    <i class="fas fa-fw fa-address-book"></i>
                    <span>Quản lý tài khoản</span>
                </a>
            </li>

            <!-- Nav Item - New account -->
<c:choose>
<c:when test="${pageName eq 'newAccount'}">
            <li class="nav-item active">
</c:when>
<c:otherwise>
            <li class="nav-item">
</c:otherwise>
</c:choose>

<!-- LINK HERE thêm mới tài khoản -->
                <a class="nav-link" href="https://love-and-care.herokuapp.com/admin/them-tai-khoan">
                    <i class="fas fa-fw fa-plus-square"></i>
                    <span>Tạo tài khoản</span></a>
            </li>

            <!-- Divider -->
            <hr class="sidebar-divider">

            <!-- Nav Item - Timeline -->
<c:choose>
<c:when test="${pageName eq 'timeline'}">
            <li class="nav-item active">
</c:when>
<c:otherwise>
            <li class="nav-item">
</c:otherwise>
</c:choose>

<!-- LINK HERE cập nhật các lượt đóng góp mới nhất -->
                <a class="nav-link" href="https://love-and-care.herokuapp.com/admin/dong-thoi-gian">
                    <i class="fas fa-fw fa-heartbeat"></i>
                    <span>Dòng thời gian</span>
                </a>
            </li>

            <!-- Divider -->
            <hr class="sidebar-divider d-none d-md-block">

            <!-- Sidebar Toggler (Sidebar) -->
            <div class="text-center d-none d-md-inline">
                <button class="rounded-circle border-0" id="sidebarToggle"></button>
            </div>

        </ul>
        <!-- End of Sidebar -->
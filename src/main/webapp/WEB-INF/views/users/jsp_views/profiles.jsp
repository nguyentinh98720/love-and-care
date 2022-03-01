<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="profile" value="${requestScope.profile}"/>
<c:set var="avatar" value="${profile.avatar}"/>

<div class="container" style="min-height: 50vh;">
    <div class="card card-body my-5" id="accountInfoContainer">
      <form action="https://love-and-care.herokuapp.com/quan-ly/cap-nhat-thong-tin-tai-khoan" method="post"
      	class="row text-center" enctype="multipart/form-data" onsubmit="return verifyUpdateProfile()">
        <div class="col-lg-4 imgSetVaTi">
<c:set var="hasAvatar" value="${avatar.arr != null}"/>
<c:choose>
<c:when test="${hasAvatar}">
          <img src='data:<c:out value="${avatar.type}"/>;base64,<c:out value="${avatar.encode}"/>'
          	alt="avatar" class="img-thumbnail" width="200" />
</c:when>
<c:otherwise>
          <i class="bg-light text-success" style="display: inline-block; height: 200px; width: 150px;">Chưa có ảnh đại diện</i>
</c:otherwise>
</c:choose>
          <div class="d-none">
<c:if test="${hasAvatar}">
            <button type="button" role="button" onclick="deleteAvatar()" class="btn btn-danger rounded-circle"
              title="Xóa ảnh">
              <i class="fas fa-trash"></i>
            </button>
</c:if>
            <label for="avatar-upload" class="btn btn-primary rounded-circle" title="Cập nhật ảnh đại diện">
              <i class="fas fa-sync-alt"></i>
            </label>
            <input type="file" id="avatar-upload" style="display: none;" name="avatar">
            <p class="text-warning" id="updateAvatarWarning"></p>
          </div>
        </div>
        <div class="col-lg-8">
          <table class="table">
            <tr>
              <td><i class="fas fa-signature"></i></td>
              <td id="fullName">
                <span><c:out value="${profile.firstName}"/> <c:out value="${profile.lastName}"/></span>
                <div class="input-group d-none">
                  <input class="form-control" aria-label="First Name" type="text" value='<c:out value="${profile.firstName}"/>' name="firstName"/>
                  <input class="form-control" aria-label="Last Name" type="text" value='<c:out value="${profile.lastName}"/>' name="lastName"/>
                </div>
              </td>
            </tr>
            <tr>
              <td><i class="fas fa-envelope-square"></i></td>
              <td id="email">
                <span><c:out value="${profile.email}"/></span>
                <input type="email" class="form-control d-none" disabled value='<c:out value="${profile.email}"/>'>
              </td>
            </tr>
            <tr>
              <td><i class="fas fa-map-marker-alt"></i></td>
              <td id="address">
                <span><c:out value="${profile.address}"/></span>
                <input type="text" class="form-control d-none" value='<c:out value="${profile.address}"/>' name="address">
              </td>
            </tr>
            <tr>
              <td><i class="fas fa-phone"></i></td>
              <td id="phoneNumber">
                <span><c:out value="${profile.phoneNumber}"/></span>
                <input type="text" class="form-control d-none" value='<c:out value="${profile.phoneNumber}"/>' name="phoneNumber">
              </td>
            </tr>
          </table>
        </div>
        <div class="col-12 mt-5 d-none" id="editAction">
          <button type="button" role="button" class="btn btn-sm btn-secondary mx-2" onclick="canceEdit()">Hủy tác vụ</button>
          <button type="submit" class="btn btn-sm btn-info text-light mx-2">Cập nhật</button>
          <span class="badge bg-danger text-light"></span>
        </div>
        <div class="col-lg-12 mt-5" id="editSelect">
          <a href="https://love-and-care.herokuapp.com/quan-ly/lich-su-quyen-gop" class="mx-2 btn btn-outline-info">Xem lịch sử</a>
          <span onclick="editInformation()" class="btn btn-outline-success mx-2">Chỉnh sửa</span>
          <a href="https://love-and-care.herokuapp.com/greeting/doi-mat-khau" class="mx-2 btn btn-outline-warning">Đổi mật khẩu</a>
        </div>
      </form>
    </div>
  </div>
  <script src='<c:url value="/static/client/js/profiles.js"/>'></script>
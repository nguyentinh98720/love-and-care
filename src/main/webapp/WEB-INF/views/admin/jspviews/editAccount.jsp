<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="account" value="${requestScope.account}" />
<c:set var="avatar" value="${account.avatar}"/>

<!-- Begin Page Content -->
<div class="container-fluid">


	<!-- DataTales Example -->
	<div class="card shadow mb-4">
		<div
			class="card-header py-3 d-flex justify-content-between align-items-center">
			<h6 class="m-0 font-weight-bold text-primary">
				Chỉnh sửa / Cập nhật thông tin tài khoản
			</h6>
			<input type="hidden" name="namePage" id="namePage"
				value="editExistAccount">
			<div class="m-0 p-0">
				<button onclick='resetPasswordById(<c:out value="${account.id}"/>)'
					class="btn btn-warning" role="button" id="resetPassword">
					Khôi phục mật khẩu
				</button>
				<!-- FUNCTION HERE delete the account -->
				<button class="m-2 btn text-white rounded-circle bg-danger"
					role="button" onclick='deleteAccountFromEdit(<c:out value="${account.id}"/>)'>
					<i class="fa fa-trash" aria-hidden="true"></i>
				</button>
			</div>
		</div>
		<div class="card-body">
			<!-- LINK HERE gửi thông tin về server -->
			<form action="${pageContext.request.contextPath}/tai-khoan/hoan-thanh-cap-nhat"
				method="post" onsubmit="return editExistAccount()"
				enctype="multipart/form-data">
			<!-- ACCOUNT ID chèn mã tài khoản vào đây -->
				<input type="hidden" id="accountId" name="accountId"
						value='<c:out value="${account.id}"/>' />
				<div class="form-group row">
					<div class="col-lg-6">
						<label for="emailForEditAccout"> Email <span
							id="verifyEmail" class="badge text-danger bg-warning"></span>
						</label>
						<!-- ACCOUNT EMAIL -->
						<input type="email" class="form-control" id="emailForEditAccout"
							name="email" readonly value='<c:out value="${account.email}"/>' />
					</div>
					<div class="col-lg-6">
						<label for="passwordForEditAccout"> Mật khẩu <span
							id="verifyPassword" class="badge text-danger bg-warning"></span>
						</label>
						<!-- ACCOUNT PASSWORD -->
						<input type="password" class="form-control"
							id="passwordForEditAccout" name="password" readonly
							value='<c:out value="${account.password}"/>' />
					</div>
				</div>
				<div class="row my-2">
					<div class="col-lg-4">
						<label for="firstNameForEditAccount"> Tên <span
							id="verifyFirstName" class="badge text-danger bg-warning"></span>
						</label>
						<!-- ACCOUNT FIRST NAME -->
						<input type="text" class="form-control"
							id="firstNameForEditAccount" name="firstName"
							value='<c:out value="${account.firstName}"/>' />
					</div>
					<div class="col-lg-4">
						<label for="lastNameForEditAccount"> Họ (tên đệm) <span
							id="verifyLastName" class="badge text-danger bg-warning"></span>
						</label>
						<!-- ACCOUNT LAST NAME -->
						<input type="text" class="form-control"
							id="lastNameForEditAccount" name="lastName"
							value='<c:out value="${account.lastName}"/>' />
					</div>
					<div class="col-lg-4">
						<label for="phoneNumberForEditAccount"> Số điện thoại liên
							hệ <span id="verifyPhoneNumber"
							class="badge text-danger bg-warning"></span>
						</label>
						<!-- PHONE NUMBER -->
						<input type="text" class="form-control"
							id="phoneNumberForEditAccount" name="phoneNumber"
							value='<c:out value="${account.phoneNumber}"/>' />
					</div>
				</div>
				<div class="row my-2">
					<div class="col-lg-8">
						<label for="addressForEditAccount"> Địa chỉ <span
							id="verifyAddress" class="badge text-danger bg-warning"></span>
						</label>
						<!-- ACCOUNT ADDRESS -->
						<input type="text" class="form-control" id="addressForEditAccount"
							name="address" value='<c:out value="${account.address}"/>' />
					</div>
					<div class="col-lg-2 col-md-6">
						<label for="statusForEditAccount">Trạng thái</label>
						<!-- ACCOUNT STATUS -->
						<select id="statusForEditAccount" class="form-control"
							name="status">
							<!-- Nhớ dùng điều kiện để chọn selected -->
							<c:choose>
								<c:when test="${account.status eq true}">
									<option selected value="true">Cho phép hoạt động</option>
									<option value="false">Khóa tài khoản</option>
								</c:when>
								<c:otherwise>
									<option value="true">Cho phép hoạt động</option>
									<option selected value="false">Khóa tài khoản</option>
								</c:otherwise>
							</c:choose>
						</select>
					</div>
					<div class="col-lg-2 col-md-6">
						<label for="roleForEditAccount">Quyền hạn</label>
						<!-- ACCOUNT ROLE -->
						<input type="hidden" id="baseRoleOfAccount" value='${account.role}'>
						<select id="roleForEditAccount" class="form-control" name="role">
							<!-- Nhớ dùng điều kiện để chọn selected -->
							<c:choose>
								<c:when test="${account.role eq 'ROLE_ADMIN'}">
									<option selected value="ROLE_ADMIN">Tài khoản quản trị</option>
									<option value="ROLE_USER">Tài khoản thường</option>
								</c:when>
								<c:when test="${account.role eq 'ROLE_USER'}">
									<option value="ROLE_ADMIN">Tài khoản quản trị</option>
									<option selected value="ROLE_USER">Tài khoản thường</option>
								</c:when>
							</c:choose>
						</select>
					</div>
				</div>
				<div class="my-2" id="imageAccountManage">
					<p>Ảnh đại diện</p>
					<div>
						<!-- hình ảnh sẽ được chèn vào đây -->
						<c:choose>
							<c:when test="${avatar.encode == null}">
								<div style='width: 200px; height: 200px'
									class='d-flex justify-content-center align-items-center bg-dark text-white'>
									Chưa có ảnh đại diện
								</div>
							</c:when>
							<c:otherwise>
								<img src='data:<c:out value="${avatar.type}"/>;base64,<c:out value="${avatar.encode}"/>'
									alt='avatar' class='img-thumbnail' width='200' />

								<span class='btn btn-sm btn-danger text-white'
									style='position: relative; bottom: 0rem; right: 2rem'
									onclick='deleteImgFromEditAccount(<c:out value="${account.id}"/>)'>
									<i class='fa fa-trash' aria-hidden='true'></i>
								</span>

							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="form-group my-2">
					<label for="updateImageAccount">Cập nhật / Thay mới ảnh (&lt;10Mb)</label>
					<!-- IMAGES -->
					<input type="file" class="input-group-text" id="updateImageAccount"
						name="imgAccount">
				</div>
				<hr class="sidebar-divider d-none d-md-block">
				<div class="d-flex justify-content-center">
					<!-- LINK HERE dẫn về trang danh sách không submit lưu thông tin -->
					<button role="button" type="button" class="btn btn-secondary mx-4"
						onclick="canceEditAccount()">Hủy bỏ</button>
					<input id="lastStepWarning" class="btn btn-success mx-4" type="submit" value="Lưu lại" />
				</div>
			</form>
		</div>
	</div>

</div>
<!-- /.container-fluid -->
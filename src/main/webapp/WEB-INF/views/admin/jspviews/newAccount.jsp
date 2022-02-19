<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- Begin Page Content -->
                <div class="container-fluid">
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">Tạo mới tài khoản</h6>
                            <input type="hidden" name="namePage" id="namePage" value="createAccount">
                        </div>
                        <div class="card-body">
<!-- LINK HERE gửi thông tin về server -->
                            <form action="${pageContext.request.contextPath}/tai-khoan/them-tai-khoan-moi" method="post"
                            	onsubmit="return creatNewAccount()" enctype="multipart/form-data">
                                <div class="row my-2">
                                    <div class="col-lg-12">
                                        <label for="emailForCreateAccout">
                                            Email
                                            <span id="verifyEmail" class="badge text-danger bg-warning"></span>
                                        </label>
<!-- ACCOUNT EMAIL -->
                                        <input type="email" class="form-control" id="emailForCreateAccout" name="email" required/>
                                    </div>
<!--                                     <div class="col-lg-6">
                                        <label for="passwordForCreatAccout">
                                            Mật khẩu
                                            <span id="verifyPassword" class="badge text-danger bg-warning"></span>
                                        </label>
ACCOUNT PASSWORD
                                        <input type="password" class="form-control" id="passwordForCreateAccout" name="password" required/>
                                    </div>
                                    <div class="col-lg-6">
                                        <label for="repeatPasswordForCreatAccout">
                                            Nhập lại mật khẩu
                                            <span id="verifyRepeatPassword" class="badge text-danger bg-warning"></span>
                                        </label>
                                        <input type="password" class="form-control" id="repeatPasswordForCreateAccout" name="repeatPassword" required/>
                                    </div>
 -->                                </div>
                                <div class="row my-2">
                                    <div class="col-lg-6">
                                        <label for="firstNameForCreateAccount">
                                            Tên
                                            <span id="verifyFirstName" class="badge text-danger bg-warning"></span>
                                        </label>
<!-- ACCOUNT FIRST NAME -->
                                        <input type="text" class="form-control" id="firstNameForCreateAccount" name="firstName">
                                    </div>
                                    <div class="col-lg-6">
                                        <label for="lastNameForCreateAccount">
                                            Họ (tên đệm)
                                            <span id="verifyLastName" class="badge text-danger bg-warning"></span>
                                        </label>
<!-- ACCOUNT LAST NAME -->
                                        <input type="text" class="form-control" id="lastNameForCreateAccount" name="lastName">
                                    </div>
                                </div>
                                <div class="row my-2">
                                    <div class="col-lg-8 col-md-12">
                                        <label for="phoneNumberForCreateAccount">
                                            Số điện thoại liên hệ
                                            <span id="verifyPhoneNumber" class="badge text-danger bg-warning"></span>
                                        </label>
<!-- PHONE NUMBER -->
                                        <input type="text" class="form-control" id="phoneNumberForCreateAccount" name="phoneNumber">
                                    </div>
                                </div>
                                <div class="row my-2">
                                    <div class="col-lg-2 col-md-6 col-12">
                                        <label for="statusForCreateAccount">Trạng thái</label>
<!-- ACCOUNT STATUS -->
                                        <select id="statusForCreateAccount" class="form-control" name="status">
                                            <option selected value="true">Hoạt động ngay</option>
                                            <option value="false">Khóa tạm thời</option>
                                        </select>
                                    </div>
                                    <div class="col-lg-2 col-md-6 col-12">
                                        <label for="roleForCreateAccount">Quyền hạn</label>
<!-- ACCOUNT ROLE -->
                                        <select id="roleForCreateAccount" class="form-control" name="role">
                                            <option value="ROLE_ADMIN">Tài khoản quản trị</option>
                                            <option selected value="ROLE_USER">Tài khoản thường</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="row my-2">
<!-- Address account -->            
                                    <div class="col-12">
                                        <span class="p-0 m-0">Địa chỉ</span>
                                        <span id="verifyAddress" class="badge text-danger bg-warning"></span>
                                        <input type="hidden" id="addressForCreateAccount" name="address">
                                    </div>
                                    <div class="col-lg-4">
                                        <label for="province"><small>Tỉnh/Thành phố</small></label>
                                        <input list="province" type="text" class="form-control" id="getProvince">
                                        <datalist id="province"></datalist>
                                    </div>
                                    <div class="col-lg-4">
                                        <label for="district"><small>Quận/Huyện</small></label>
                                        <input list="district" type="text" class="form-control" id="getDistrict">
                                        <datalist id="district"></datalist>
                                    </div>
                                    <div class="col-lg-4">
                                        <label for="wards"><small>Xã/Phường</small></label>
                                        <input list="wards" type="text" class="form-control" id="getWards">
                                        <datalist id="wards"></datalist>
                                    </div>
                               </div>
                                <div class="form-group my-2">
                                    <label for="createImageAccount">Ảnh đại diện (&lt;10Mb)</label>
<!-- IMAGES -->
                                    <input type="file" class="input-group-text" id="createImageAccount" name="imgAccount">
                                </div>
                                <hr class="sidebar-divider d-none d-md-block">
                                <div class="d-flex justify-content-center">
<!-- LINK HERE dẫn về trang dashboard không submit lưu thông tin -->
                                    <a class="btn btn-secondary mx-4" href="${pageContext.request.contextPath}/admin/quan-ly-tai-khoan">Hủy bỏ</a>
                                    <input class="btn btn-danger mx-4" type="reset" value="Reset"/>
                                    <input  id="lastStepWarning" class="btn btn-success mx-4" type="submit" value="Lưu lại"/>
                                </div>
                            </form>
                        </div>
                    </div>

                </div>
                <!-- /.container-fluid -->
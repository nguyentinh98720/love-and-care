<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="resultUpdate" value="${param.resultUpdate}"/>
<c:if test="${resultUpdate != null}"><input type="hidden" id="resultUpdate" value="${resultUpdate}"/></c:if>
<c:set var="resultCreate" value="${param.resultCreate}"/>
<c:if test="${resultCreate != null}"><input type="hidden" id="resultCreate" value="${resultCreate}"/></c:if>

<!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header row">
                            <div class="col-lg-3 col-md-5 col-8 m-0 d-flex flex-column">
                                <h6 class="font-weight-bold text-primary">
                                    Danh sách tài khoản
                                    <span class="badge badge-light" id="countChooseAccount"></span>
                                </h6>
                                <div class="m-0 p-0">
                                    <label for="chooseAllAccount" class="m-1 p-0 btn btn-sm btn-light">Chọn tất cả</label>
                                    <span class="btn btn-outline-danger btn-sm d-none" id="chooseDeleteAccount" title="Xóa các tài khoản đã chọn">
                                        <i class="fa fa-trash" aria-hidden="true"></i>
                                    </span>
                                    <span class="btn btn-outline-warning btn-sm d-none" id="chooseLockAccount" title="Khóa các tài khoản đã chọn">
                                        <i class="fa fa-user-lock" aria-hidden="true"></i>
                                    </span>
                                    <span class="btn btn-outline-success btn-sm d-none" id="chooseActiveAccount" title="Mở khóa các tài khoản đã chọn">
                                        <i class="fa fa-lock-open" aria-hidden="true"></i>
                                    </span>
                                </div>
                            </div>
                            <div class="col-lg-1 col-md-1 col-4 d-flex justify-content-center align-items-center">
                                <button class="btn btn-sm btn-primary" role="button" title="Làm mới danh sách" onclick='reloadPage()'>
                                    <i class="fa fa-sync" aria-hidden="true"></i>
                                </button>
                            </div>
                                
                            <div class="col-lg-2 col-md-3 col-6 d-flex justify-content-center justify-content-lg-end align-items-center">
                                <div class="m-0 p-0">
                                    <select class="custom-select" id="filterRoleAccount" title="Quyền truy cập">
                                        <option value="all" selected>Role</option>
                                        <option value="user">User</option>
                                        <option value="admin">Admin</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-lg-2 col-md-3 col-6 d-flex justify-content-center align-items-center">
                                <div class="m-0 p-0">
                                    <select class="custom-select" id="filterStatusAccount" title="Lọc danh sách">
                                        <option value="all" selected>Status</option>
                                        <option value="active">Hoạt động</option>
                                        <option value="lock">Bị khóa</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-lg-3 col-md-6 d-flex justify-content-center align-items-center">
                                <div class="m-0 p-0 input-group">
                                    <div class="input-group-prepend">
                                        <button class="btn btn-outline-secondary" type="button" id="searchButtonAccountManage">Tìm kiếm</button>
                                    </div>
                                    <select id="filterSearchAccount" style="max-width: 50px;" title="Tìm kiếm theo mục:">
                                        <option value="name" selected>Tên</option>
                                        <option value="phone">SDT</option>
                                        <option value="address">Địa chỉ</option>
                                    </select>
                                    <input type="text" class="form-control" id="searchInputAccountManage" aria-describedby="searchButtonAccountManage">
                                </div>
                            </div>
                            <div class="col-lg-1 col-md-6 d-flex justify-content-center align-items-center">
                                <div class="m-0 p-0 col-lg-1 col-md-1 col-4 d-flex justify-content-center align-items-center">
<!-- LINK HERE create new account -->
                                    <a href="https://love-and-care.herokuapp.com/admin/them-tai-khoan" class="btn text-white rounded-circle bg-info" role="button" title="Thêm tài khoản mới">
                                        <i class="fa fa-plus" aria-hidden="true"></i>
                                    </a>
                                </div>
                            </div>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table text-center" id="dataTableAccount">
                                    <thead>
                                        <tr>
                                            <th class="p-0 m-0">
                                                <label class="p-0 labelCheckbox">
                                                    <input type="checkbox" id="chooseAllAccount" value="all"/>
                                                    <span class="checkmark"></span>
                                                </label>
                                            </th>
                                           	<th style="width: 50px"></th>
                                            <th>Email</th>
                                            <th>Tên</th>
                                            <th>Họ</th>
                                            <th>SDT</th>
                                            <!-- <th>Địa chỉ</th> -->
                                            <th>Quyền hạn</th>
                                            <th>Trạng thái</th>
                                            <th>Tác vụ</th>
                                        </tr>
                                    </thead>
                                    <tbody>
<!-- Dữ liệu được thêm ở đây -->            
                                        
                                    </tbody>
                                    <tfoot>
                                        <tr>
                                        <td aria-label="Page navigation" colspan="8">
                                            <ul id="paginationListAccountDetail" class="pagination pagination-sm justify-content-center m-0">
                                                <li id="accountPrev" class="page-item">
                                                <span class="page-link" aria-label="Previous" role="button">&laquo;</span>
                                                </li>
<!-- Cài đặt số trang -->
                                                <li id="accountNext" class="page-item">
                                                <span class="page-link" aria-label="Next" role="button">&raquo;</span>
                                                </li>
                                            </ul>
                                        </td>
                                        </tr>
                                    </tfoot>
                                </table>
                            </div>
                        </div>
                    </div>

                </div>
                <!-- /.container-fluid -->
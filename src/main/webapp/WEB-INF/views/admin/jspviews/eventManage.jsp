<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
                            <div class="col-lg-3 col-md-6 col-8 m-0 d-flex flex-column">
                                <h6 class="font-weight-bold text-primary">
                                    Danh sách chương trình
                                    <span class="badge badge-light" id="countChoose"></span>
                                </h6>
                                <div class="m-0 p-0">
                                    <label for="chooseAll" class="m-1 p-0 btn btn-sm btn-light">Chọn tất cả</label>
                                    <span class="btn btn-outline-danger btn-sm d-none" id="chooseDelete" title="Xóa mục đã chọn">
                                        <i class="fa fa-trash" aria-hidden="true"></i>
                                    </span>
                                    <span class="btn btn-outline-warning btn-sm d-none" id="chooseTurnOff" title="Ngừng hoạt động mục đã chọn">
                                        <i class="fa fa-power-off" aria-hidden="true"></i>
                                    </span>
                                    <span class="btn btn-outline-success btn-sm d-none" id="chooseTurnOn" title="Khởi động mục đã chọn">
                                        <i class="fa fa-toggle-on" aria-hidden="true"></i>
                                    </span>
                                </div>
                            </div>
                            <div class="col-lg-1 col-md-6 col-4 d-flex justify-content-start align-items-center">
<!-- Refresh page -->
                                <button class="btn btn-sm btn-primary" role="button" title="Làm mới" onclick="reloadPage()">
                                    <i class="fa fa-sync" aria-hidden="true"></i>
                                </button>
                            </div>

                            <div class="col-lg-2 col-md-6 d-flex justify-content-center justify-content-lg-end align-items-center">
                                <div class="m-0 p-0">
                                    <select class="custom-select my-1 mr-sm-2" id="sortResult" title="Sắp xếp">
                                        <option value="lastest" selected>Mới nhất</option>
                                        <option value="oldest">Cũ nhất</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-lg-2 col-md-6 d-flex justify-content-center align-items-center">
                                <div class="m-0 p-0">
                                    <select class="custom-select my-1 mr-sm-2" id="filterResult" title="Lọc">
                                        <option value="all" selected>Tất cả</option>
                                        <option value="running">Đang triển khai</option>
                                        <option value="stopping">Tạm dừng</option>
                                        <option value="ended">Đã kết thúc</option>
                                        <option value="unlimit">Không thời hạn</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-lg-3 col-md-6 d-flex justify-content-center align-items-center">
                                <div class="m-0 p-0 input-group">
                                    <div class="input-group-prepend">
                                        <button class="btn btn-outline-secondary" type="button" id="searchButtonEventManage">Tìm kiếm</button>
                                    </div>
                                    <input type="text" class="form-control" id="searchInputEventManage" aria-describedby="searchButtonEventManage">
                                </div>
                            </div>
                            <div class="col-lg-1 col-md-6 d-flex justify-content-center align-items-center">
                                <div class="m-0 p-0 d-flex justify-content-center align-items-center">
<!-- LINK HERE create new event -->
                                    <a href="https://love-and-care.herokuapp.com/admin/them-chuong-trinh" class="btn text-white rounded-circle bg-info" role="button" title="Thêm chương trình mới">
                                        <i class="fa fa-plus" aria-hidden="true"></i>
                                    </a>
                                </div>
                            </div>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table text-center" id="dataTable">
                                    <thead>
                                        <tr>
                                            <td class="align-middle p-1">
                                                <label class="labelCheckbox">
                                                    <input type="checkbox" id="chooseAll" value="all"/>
                                                    <span class="checkmark"></span>
                                                </label>
                                            </td>
                                            <th>Mã CT</th>
                                            <th class="text-nowrap">Tên chương trình</th>
                                            <th class="text-nowrap">Ngày triển khai</th>
                                            <th class="text-nowrap">Mục tiêu</th>
                                            <th class="text-nowrap">Quyên góp được</th>
                                            <th class="text-nowrap">Kết thúc</th>
                                            <th class="text-nowrap">Trạng thái</th>
                                            <th class="text-nowrap">Tác vụ</th>
                                        </tr>
                                    </thead>
                                    <tbody>
<!-- Dữ liệu được thêm ở đây -->            
                                    </tbody>
                                    <tfoot>
                                        <tr>
                                        <td aria-label="Page navigation" colspan="9">
                                            <ul id="paginationListEventDetail" class="pagination pagination-sm justify-content-center m-0">
                                                <li id="prevpage" class="page-item">
                                                <span class="page-link" aria-label="Previous" role="button">&laquo;</span>
                                                </li>
<!-- Cài đặt số trang -->
                                                <li id="nextpage" class="page-item">
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
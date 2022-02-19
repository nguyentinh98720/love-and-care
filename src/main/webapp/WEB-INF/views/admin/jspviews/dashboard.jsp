<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- Page Heading -->
                    <div class="d-sm-flex align-items-center justify-content-between mb-4">
                        <h1 class="h3 mb-0 text-gray-800">Tổng quan</h1>
<!-- LINK HERE dẫn đến trang chi tiết chương trình -->
                        <a href="${pageContext.request.contextPath}/admin/dong-thoi-gian" class="d-none d-sm-inline-block btn btn-sm btn-secondary shadow-sm">
                            <i class="fas fa-list fa-sm text-white-50"></i>
                            Dòng thời gian
                        </a>
                    </div>

                    
                    <!-- Content Row -->
                    <div class="row">

<!-- Hiển thị danh sách chương trình, biểu đồ tiến độ quyên góp -->
                        <!-- Area Chart -->
                        <div class="col-xl-8 col-lg-12">
                            <div class="card shadow mb-4">
                                <!-- Card Header - Dropdown -->
                                <div
                                    class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                                    <h6 class="m-0 font-weight-bold text-primary">
                                   		Chương trình đang diễn ra
										<span class="badge bagde-pill badge-success" id="theRunningEvent"></span>
									</h6>
                                    <div class="dropdown no-arrow">
                                        <span class="dropdown-toggle" role="button" id="dropdownMenuLink"
                                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            <i class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
                                        </span>
                                        <div class="dropdown-menu dropdown-menu-right shadow animated--fade-in"
                                            aria-labelledby="dropdownMenuLink">
<!-- Quản lý chương trình  cụ thể với mã id: xem chi tiết, cập nhật thông tin, tạm ngừng hoạt động-->
<!-- MÃ CHƯƠNG TRÌNH, LINK đến trang cập nhật thông tin, trang xem chi tiết và tạm dừng triển khai-->
                                            <div class="dropdown-header" id="eventIdDropdown">Mã chương trình</div>
                                            <a class="dropdown-item" href="#" id="eventEditDropdown">Cập nhật thông tin</a>
                                            <a class="dropdown-item" href="#" id="eventDetailDropdown">Xem chi tiết</a>
                                            <div class="dropdown-divider"></div>
<!-- Làm mới bảng và biểu đồ khi tạm dừng triển khai chương trình -->
                                            <span class="dropdown-item" style="color:#f00;" id="stopActionDashboard">Tạm dừng triển khai</span>
                                        </div>
                                    </div>
                                </div>
                                <!-- Card Body -->
                                <div class="card-body">
                                    <div class="chart-area">
                                        <canvas id="myAreaChart"></canvas>
                                    </div>
<!-- BẢNG CHƯƠNG TRÌNH hiển thị danh sách các chương trình đang diễn ra -->
                                    <table class="table table-hover mb-0">
                                        <thead>
                                            <tr>
                                                <th>#</th>
                                                <th>Tiêu đề</th>
                                                <th>Kết thúc</th>
                                            </tr>
                                        </thead>
                                        <tbody id="rowParent" class="font-weight-light" style="cursor: pointer;">
<!-- Chèn thông tin vào bảng với số thứ tự, tiêu đề và ngày kết thúc -->
                                            <tr id="row1">
                                                <th>-</th>
                                                <input type="hidden">
                                                <td class="small title"></td>
                                                <td class="small date"></td>
                                            </tr>
                                            <tr id="row2">
                                                <th>-</th>
                                                <input type="hidden">
                                                <td class="small title"></td>
                                                <td class="small date"></td>
                                            </tr>
                                            <tr id="row3">
                                                <th>-</th>
                                                <input type="hidden">
                                                <td class="small title"></td>
                                                <td class="small date"></td>
                                            </tr>
                                            <tr id="row4">
                                                <th>-</th>
                                                <input type="hidden">
                                                <td class="small title"></td>
                                                <td class="small date"></td>
                                            </tr>
                                            <tr id="row5">
                                                <th>-</th>
                                                <input type="hidden">
                                                <td class="small title"></td>
                                                <td class="small date"></td>
                                            </tr>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                            <td aria-label="Page navigation" colspan="4">
                                                <ul id="paginationHere" class="pagination pagination-sm justify-content-center m-0">
                                                    <li id="prev" class="page-item">
                                                    <span class="page-link" aria-label="Previous" role="button">&laquo;</span>
                                                    </li>

<!-- Cài đặt số trang, next and previous page ở đây -->

                                                    <li id="next" class="page-item">
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

<!-- Biểu đồ chia tỉ lệ số chương trình tổng số chương trình quyên góp đã tổ chức, đang diễn ra và tạm hoãn  -->
                        <!-- Pie Chart -->
                        <div class="col-xl-4 col-lg-12">
                            <div class="card shadow mb-4">
                                <!-- Card Header - Dropdown -->
                                <div
                                    class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                                    <h6 class="m-0 font-weight-bold text-primary">Sơ đồ thống kê</h6>
                                    <div class="dropdown no-arrow">
                                        <span class="dropdown-toggle" role="button" id="dropdownMenuLink"
                                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            <i class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
                                        </span>
<!-- QUẢN LÝ CHƯƠNG TRÌNH chung với các tác vụ thêm mới hoặc vào trang quản lý chung-->
<!-- LINK HERE đưa đến trang quản lý, trang thêm mới chương trình -->
                                        <div class="dropdown-menu dropdown-menu-right shadow animated--fade-in"
                                            aria-labelledby="dropdownMenuLink">
                                            <div class="dropdown-header">Quản lý chương trình</div>
                                            <a class="dropdown-item" href="${pageContext.request.contextPath}/admin/them-chuong-trinh">Thêm mới</a>
                                            <a class="dropdown-item" href="${pageContext.request.contextPath}/admin/quan-ly-chuong-trinh">Quản lý chung</a>
                                        </div>
                                    </div>
                                </div>
                                <!-- Card Body -->
                                <div class="card-body">
                                    <div class="chart-pie pt-4 pb-2">
                                        <canvas id="myPieChart"></canvas>
                                    </div>

<!-- LƯU Ý PHẦN NÀY LẤY CON SỐ Ở PHÍA SERVER VÀ TÍNH PHẦN TRĂM Ở PHÍA CLIENT (trong file js) -->
<!-- Phần con số cụ thể ở đây, phần % ở lại code js để hiển thị biểu đồ -->
<!-- JS đã lo phần hiển thị, bỏ qua mã java ở phần này -->
                                    <table class="table table-borderless mt-4 small p-0">
                                        <tr>
                                            <td class="p-0">
                                                <p class="mr-2">
                                                    <i class="fas fa-circle text-light"></i> Tổng số
                                                </p>
                                            </td>
                                            <td id="totalEvent" class="p-0">
<!-- Tổng số chương trình được tổ chức -->      0
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="p-0">
                                                <p class="mr-2">
                                                    <i class="fas fa-circle text-dark"></i> Đã kết thúc
                                                </p>
                                            </td>
                                            <td id="endingEvent" class="p-0">
<!-- Số chương trình đã kết thúc -->            0
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="p-0">
                                                <p class="mr-2">
                                                    <i class="fas fa-circle text-danger"></i> Đang tạm hoãn
                                                </p>
                                            </td>
                                            <td id="stoppingEvent" class="p-0">
<!-- Số chương trình đang tạm hoãn -->          0
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="p-0">
                                                <p class="mr-2">
                                                    <i class="fas fa-circle text-info"></i> Đang diễn ra
                                                </p>
                                            </td>
                                            <td id="runningEvent" class="p-0">
<!-- Số chương trình đang diễn ra -->           0
                                            </td>
                                        </tr>
                                    </table>
                                </div>
<!-- Bỏ qua mã java luôn ở phần này -->
                                <table class="table table-borderless small mt-3 mb-4 font-weight-bold">
                                    <tr>
                                        <td>Số tiền huy động nhiều nhất</td>
                                        <td>
<!-- Truy vấn số tiền huy động nhiều nhất, đưa đến trang chi tiết chương trình -->
                                            <a id="maxEventMoney" class="text-decoration-none" href="#">
                                                0
                                            </a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Số tiền quyên góp nhiều nhất</td>
                                        <td>
<!-- Truy vấn số tiền nhiều nhất mà lần quyên góp mang lại, đưa đến trang thông tin người quyên góp -->
                                            <a id="maxDonationMoney" class="text-decoration-none" href="#">
                                                0
                                            </a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Thời gian huy động ngắn nhất</td>
                                        <td>
<!-- Truy vấn chương trình có thời gian diễn ra là ngắn nhất, đưa đến trang chi tiết chương trình -->
                                            <a id="minEventTime" href="#" class="text-decoration-none">
                                                0
                                            </a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Chưa xác định thời gian kết thúc</td>
                                        <td>
<!-- Truy vấn số lượng chương trình chưa xác định thời gian kết thúc triển khai, đưa đến trang quản lý danh sách với kết quả truy vấn được -->
                                            <a id="noEndTimeEvent" href="#" class="text-decoration-none">
                                                0
                                                <span> chương trình</span>
                                            </a>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>

                    <div class="row">

                        <!-- Tổng số tài khoản đăng ký hiện có trong ứng dụng web -->
                        <div class="col-xl-3 col-md-6 mb-4">
                            <div class="card border-left-primary shadow h-100 py-2">
                                <div class="card-body">
                                    <div class="row no-gutters align-items-center">
                                        <div class="col mr-2">
                                            <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                                Tổng số tài khoản đăng ký
                                            </div>
                                            <div class="h5 mb-0 font-weight-bold text-gray-800">
<!-- Truy vấn tổng số tài khoản đăng ký,  dẫn đến trang quản lý tài khoản -->
                                                <a id="totalAccount" href="${pageContext.request.contextPath}/quan-ly-tai-khoan" class="text-decoration-none">
                                                    0
                                                </a>
                                            </div>
                                        </div>
                                        <div class="col-auto">
                                            <i class="fas fa-users fa-2x text-gray-300"></i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Số tài khoản quản trị quản trị hệ thống -->
                        <div class="col-xl-3 col-md-6 mb-4">
                            <div class="card border-left-success shadow h-100 py-2">
                                <div class="card-body">
                                    <div class="row no-gutters align-items-center">
                                        <div class="col mr-2">
                                            <div class="text-xs font-weight-bold text-success text-uppercase mb-1">
                                                Tài khoản quản trị (admin)
                                            </div>
                                            <div class="h5 mb-0 font-weight-bold text-gray-800">
<!-- Truy vấn số tài khoản quản trị hiện có trong hệ thống, dẫn đến trang quản lý tài khoản với kết quả lọc admin -->
                                                <a id="adminAccount" href="#" class="text-decoration-none">
                                                    0
                                                </a>
                                            </div>
                                        </div>
                                        <div class="col-auto">
                                            <i class="fas fa-user-md fa-2x text-gray-300"></i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Số tài khoản người dùng đang hoạt động -->
                        <div class="col-xl-3 col-md-6 mb-4">
                            <div class="card border-left-info shadow h-100 py-2">
                                <div class="card-body">
                                    <div class="row no-gutters align-items-center">
                                        <div class="col mr-2">
                                            <div class="text-xs font-weight-bold text-info text-uppercase mb-1">
                                                Tài khoản nhà hảo tâm
                                            </div>
                                            <div class="h5 mb-0 font-weight-bold text-gray-800">
<!-- Truy vấn số tài khoản người dùng hiện có trong hệ thống, dẫn đến trang quản lý tài khoản với kết quả lọc user -->
                                                <a id="userAccount" href="#" class="text-decoration-none">
                                                    0
                                                </a>
                                            </div>
                                        </div>
                                        <div class="col-auto">
                                            <i class="fas fa-user fa-2x text-gray-300"></i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Số tài khoản đang bị khóa hoạt động, yêu cầu xác thực -->
                        <div class="col-xl-3 col-md-6 mb-4">
                            <div class="card border-left-warning shadow h-100 py-2">
                                <div class="card-body">
                                    <div class="row no-gutters align-items-center">
                                        <div class="col mr-2">
                                            <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
                                                Tài khoản bị khóa
                                            </div>
                                            <div class="h5 mb-0 font-weight-bold text-gray-800">
<!-- Truy vấn số tài khoản đang ở trạng thái bị chặn hoạt động và yêu cầu xác thực -->
                                                <a id="lockedAccount" href="#" class="text-decoration-none">
                                                    0
                                                </a>
                                            </div>
                                        </div>
                                        <div class="col-auto">
                                            <i class="fas fa-user-secret fa-2x text-gray-300"></i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xl-12">
                            <div class="card shadow mb-4 table-responsive">
                                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                                    <h6 class="m-0 font-weight-bold text-primary">Lần quyên góp gần đây nhất</h6>
<!-- LINK HERE dẫn tới trang dòng thời gian để xem chi tiết hơn -->
                                    <a href="${pageContext.request.contextPath}/admin/dong-thoi-gian" class="btn btn-sm btn-secondary" role="button">
                                        <i class="fa fa-table"></i>
                                        <span>Xem thêm</span>
                                    </a>
                                </div>
                                <table id="donateTable" class="table table-hover m-0">
                                    <thead>
                                        <tr>
                                            <th>Nhà hảo tâm</th>
                                            <th>Chương trình</th>
                                            <th>Số tiền</th>
                                            <th>Thời gian</th>
                                            <th>Lời nhắn</th>
                                        </tr>
                                    </thead>
                                    <tbody style="cursor: pointer;">
<!-- Danh sách thông tin của các lần quyên góp từ thiện mới nhất -->
                                    </tbody>
                                </table>
                                <div class="card-footer">
                                    <i class="m-0">Tên nhà hảo tâm với font chữ nhỏ và in nghiêng yêu cầu ẩn danh.</i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- /.container-fluid -->
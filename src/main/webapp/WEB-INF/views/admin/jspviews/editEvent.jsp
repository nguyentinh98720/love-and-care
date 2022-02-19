<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	
<style>
.ck-editor__editable_inline {
    min-height: 500px !important;
}
</style>

<c:set var="event" value="${requestScope.event}"/>
<!-- Begin Page Content -->
                <div class="container-fluid">


                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3 d-flex justify-content-between align-items-center">
                            <h6 class="m-0 font-weight-bold text-primary">Chỉnh sửa / Cập nhật thông tin chương trình</h6>
                            <input type="hidden" name="pageName" id="pageName" value="editEvent"/>
                            <div class="m-0 p-0">
<!-- FUNCTION HERE delete the event -->
                                <button class="m-2 btn text-white rounded-circle bg-danger" onclick='clickDeleteEvent(<c:out value="${event.id}"/>)' title="Xóa chương trình quyên góp này">
                                    <i class="fa fa-trash" aria-hidden="true"></i>
                                </button>
                            </div>
                        </div>
                        <div class="card-body">
<!-- LINK HERE gửi thông tin về server -->
                            <form action="https://love-and-care.herokuapp.com/chuong-trinh/ck-hoan-thanh-cap-nhat" method="post" onsubmit="return editExistEvent()">
<!-- EVENT ID chèn mã chương trình vào đây -->
	                            <input type="hidden" id="eventId" name="eventId" value='<c:out value="${event.id}"/>'/>
                                <div class="form-group">
                                    <label for="titleForEdit">
                                        Tên / Tiêu đề chương trình
                                        <span id="verifyTitle" class="badge text-danger bg-warning"></span>
                                    </label>
<!-- EVENT TITLE -->
                                    <textarea class="form-control" id="titleForEdit" rows="2" name="title"><c:out value="${event.title}"/></textarea>
                                </div>
                                <div class="row my-2">
                                    <div class="col-lg-4">
                                        <label for="startDateForEdit">
                                            Ngày bất đầu
                                            <span id="verifyStartDate" class="badge text-danger bg-warning"></span>
                                        </label>
<!-- START DATE -->
                                        <input type="date" class="form-control" id="startDateForEdit" name="startDate" value='<c:out value="${event.startDate}"/>'>
                                    </div>
                                    <div class="col-lg-4">
                                        <label for="endDateForEdit">
                                            Ngày kết thúc
                                            <span id="verifyEndDate" class="badge text-danger bg-warning"></span>
                                        </label>
<!-- END DATE -->
                                        <input type="date" class="form-control" id="endDateForEdit" name="endDate" value='<c:out value="${event.endDate}"/>'>
                                    </div>
                                    <div class="col-lg-4">
                                        <label for="statusForEdit">Trạng thái chương trình</label>
<!-- STATUS -->
                                        <select id="statusForEdit" class="form-control" name="status">
<!-- Nhớ dùng điều kiện để chọn selected -->
<c:choose>
<c:when test="${event.status == true}">
                                            <option selected value="true">Đang triển khai</option>
                                            <option value="false">Dừng triển khai</option>
</c:when>
<c:when test="${event.status == false}">
                                            <option value="true">Đang triển khai</option>
                                            <option selected value="false">Dừng triển khai</option>
</c:when>
</c:choose>
                                        </select>
                                      </div>
                                </div>
                                <div class="row my-2">
                                    <div class="col-lg-6">
                                        <label for="purposeForEdit">
                                            Số tiền cần gây quỹ
                                            <span id="verifyPurpose" class="badge text-danger bg-warning"></span>
                                        </label>
<!-- PURPOSE -->
                                        <input type="number" class="form-control" id="purposeForEdit" name="purpose"
                                        		value='<c:out value="${event.purpose.setScale(0)}"/>'>
                                        <h6 class="text-success mt-2" id="readingPurpose"></h6>
                                    </div>
                                    <div class="col-lg-6">
                                        <label for="achievementNotEdit">Đã quyên góp được</label>
<!-- ACHIEVEMENT readonly, not send to server -->
                                        <input type="number" class="form-control" id="achievementNotEdit" readonly
                                        		value='<c:out value="${event.achievement.setScale(0)}"/>'>
                                        <h6 class="text-secondary mt-2" id="readingAchievement"></h6>
                                    </div>
                                </div>
                                <div class="form-group my-2">
                                    <label for="detailForEdit">
                                        Nội dung chương trình
                                        <span id="verifyDetail" class="badge text-danger bg-warning"></span>
                                    </label>
<!-- DETAIL -->
                                    <textarea class="form-control" id="detailForEdit" rows="10" name="detail"><c:out value="${event.detail}"/></textarea>
                                </div>
                                <hr class="sidebar-divider d-none d-md-block">
                                <div class="d-flex justify-content-center">
<!-- LINK HERE dẫn về trang danh sách không submit lưu thông tin -->
                                    <button type="button" class="btn btn-secondary mx-4" onclick="canceEditEvent()">Hủy bỏ</button>
                                    <input id="lastStepWarning" class="btn btn-success mx-4" type="submit" value="Lưu lại"/>
                                </div>
                            </form>
                        </div>
                    </div>

                </div>
                <!-- /.container-fluid -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<style>
.ck-editor__editable_inline {
    min-height: 500px !important;
}
</style>

<!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">Thêm chương trình mới</h6>
                            <input type="hidden" name="pageName" id="pageName" value="createEvent"/>
                        </div>
                        <div class="card-body">
<!-- LINK HERE gửi thông tin về server -->
                            <form action="/LoveAndCare/chuong-trinh/them-chuong-trinh" method="post" onsubmit="return creatNewEvent()" enctype="multipart/form-data">
                                <div class="form-group">
                                    <label for="titleForNewEvent">
                                        Tên / Tiêu đề chương trình
                                        <span id="verifyTitle" class="badge text-danger bg-warning"></span>
                                    </label>
<!-- EVENT TITLE -->
                                    <textarea class="form-control" id="titleForNewEvent" rows="2" name="title"></textarea>
                                </div>
                                <div class="row my-2">
                                    <div class="col-lg-6">
                                        <label for="startDateForNewEvent">
                                            Ngày bất đầu
                                            <span id="verifyStartDate" class="badge text-danger bg-warning"></span>
                                        </label>
<!-- START DATE -->
                                        <input type="date" class="form-control" id="startDateForNewEvent" name="startDate">
                                    </div>
                                    <div class="col-lg-6">
                                        <label for="endDateForNewEvent">
                                            Ngày kết thúc
                                            <span id="verifyEndDate" class="badge text-danger bg-warning"></span>
                                        </label>
<!-- END DATE -->
                                        <input type="date" class="form-control" id="endDateForNewEvent" name="endDate">
                                    </div>
                                </div>
                                <div class="row my-2">
                                    <div class="col-lg-6">
                                        <label for="purposeForNewEvent">
                                            Số tiền cần gây quỹ
                                            <span id="verifyPurpose" class="badge text-danger bg-warning"></span>
                                        </label>
<!-- PURPOSE -->
                                        <input type="number" class="form-control" id="purposeForNewEvent" name="purpose" min="0">
                                    </div>
                                </div>
                                <div class="form-group my-2">
                                    <label for="detailForNewEvent" style="width: 100%">
                                        Nội dung chương trình
                                        <span id="verifyDetail" class="badge text-danger bg-warning"></span>
<!-- DETAIL -->
                                    <textarea class="form-control" id="detailForNewEvent" rows="10" name="detail"></textarea>
                                    </label>
                                </div>
                                <div class="form-group my-2">
                                    <label for="addImgNewEvent">Hình ảnh (&lt;10Mb)</label>
<!-- IMAGES -->
                                    <input type="file" class="input-group-text" id="addImgNewEvent" name="images" multiple>
                                </div>
                                <hr class="sidebar-divider d-none d-md-block">
                                <div class="d-flex justify-content-center">
<!-- LINK HERE dẫn về trang chủ khi không submit triển khai chương trình -->
                                    <a class="btn btn-secondary mx-4" href="/LoveAndCare/admin/quan-ly-chuong-trinh">Hủy bỏ</a>
                                    <input class="btn btn-danger mx-4" type="reset" value="Reset"/>
                                    <input id="lastStepWarning" class="btn btn-success mx-4" type="submit" value="Triển khai"/>
                                </div>
                            </form>
                        </div>
                    </div>

                </div>
                <!-- /.container-fluid -->
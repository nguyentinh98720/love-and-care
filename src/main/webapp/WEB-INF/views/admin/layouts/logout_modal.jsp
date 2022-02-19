<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- Logout Modal -->
 <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
       aria-hidden="true">
       <div class="modal-dialog" role="document">
           <div class="modal-content">
               <div class="modal-header">
                   <h5 class="modal-title" id="exampleModalLabel">Bạn muốn đăng xuất chứ?</h5>
                   <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                       <span aria-hidden="true">×</span>
                   </button>
               </div>
               <div class="modal-body">Nhấn vào đăng xuất để thoát phiên làm việc</div>
               <div class="modal-footer">
                   <button class="btn btn-secondary" type="button" data-dismiss="modal">Quay lại</button>
<!-- LINK HERE dẫn về trang login -->
                   <a class="btn btn-warning" href="${pageContext.request.contextPath}/greeting/dang-xuat">Đăng xuất</a>
               </div>
           </div>
       </div>
   </div>
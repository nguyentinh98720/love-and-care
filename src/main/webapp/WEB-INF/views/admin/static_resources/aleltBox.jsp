<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- ========================================================= ALERT BOX ===================================================== -->
    <div class="alert" role="alert" id="VATIAlert" style="width: 50%; position: fixed; top: 20px; left: 40%; z-index: 2; display: none;">
        <h5 class="alert-heading">Thông báo</h5>
        <p>Message here...</p>
        <div class="d-flex justify-content-end align-items-end">
            <button type="button" class="btn btn-outline-info mx-4 font-weight-bold" style="display: none;">Xác nhận</button>
            <span role="button" class="btn btn-outline-secondary font-weight-bold" style="display: none;">Hủy</span>
        </div>
    </div>

<script type="text/javascript" src='<c:url value="/static/main/javaScript/VATIAlertbox.js"/>'></script>
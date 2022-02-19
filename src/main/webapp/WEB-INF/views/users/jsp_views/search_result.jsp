<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="album py-5">
	<div class="container">
		<div class="text-center">
			<span class="fs-5">Kết quả tìm kiếm cho: </span>
			<span class="text-secondary h5" id="searchValue"><c:out value="${requestScope.searchValue}"/></span>
		</div>
		<div id="areaForEvent" class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-3">
<!-- ============ =========== =========== ========== -->
		</div>
		<div class="mt-5 d-flex justify-content-center align-items-center">
			<button class="btn px-4 rounded-pill border-info text-info" id="showMoreButton">
				<i class="fas fa-chevron-circle-up"></i>
				<span class="ps-1">Xem thêm</span>
				<div class="d-flex justify-content-center fw-bold"><i class="h3 fas fa-circle-notch"></i></div>
			</button>
		</div>
	</div>
</div>
<script src='<c:url value="/static/client/js/donationForSearchPage.js"/>'></script>
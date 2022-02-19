<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="statistics" value="${requestScope.statistics}"/>
<div class="container">
  <div class="card card-body my-5">
  <div>
    <span class="fs-5">Nhà hảo tâm: </span>
    <span class="text-secondary h5"><c:out value="${sessionScope.user.firstName}"/> </span>
    <span class="text-secondary h5"><c:out value="${sessionScope.user.lastName}"/></span>
  </div>
    <div class="alert alert-primary">
      <table class="table table-sm">
        <tr>
          <th>Số chương trình đã tham gia:</th>
          <td><c:out value="${statistics.numEventJoin}"/></td>
        </tr>
        <tr>
          <th>Lượt quyên góp:</th>
          <td id="total"><c:out value="${statistics.numDonateAction}"/></td>
        </tr>
        <tr>
          <th>Tổng số tiền đã chi:</th>
          <td>
          	<fmt:setLocale value="vi_VN"/>
          	<fmt:formatNumber type="currency" pattern="###,### ¤"
          	groupingUsed="true" value="${statistics.totalMoneySpent}"
          	maxFractionDigits="0" currencyCode="VND"/>
          </td>
        </tr>
      </table>
    </div>
    <table class="table table-hover" id="historyTable">
      <thead>
        <tr>
        <th>Mã hóa đơn</th>
        <th>Chương trình</th>
        <th>Ngày</th>
        <th>Số tiền</th>
        <th>Ghi chú</th>
      </tr>
      </thead>
      <tbody>
<!-- insert history data here -->
      </tbody>
      <tfoot>
        <tr><td colspan="5" class="text-center">
          <button id="showMoreButton" class="btn btn-sm btn-light">Cũ hơn</button>
        </td></tr>
      </tfoot>
    </table>
  </div>
</div>
<script type="text/javascript" src='<c:url value="/static/client/js/history.js"/>'></script>
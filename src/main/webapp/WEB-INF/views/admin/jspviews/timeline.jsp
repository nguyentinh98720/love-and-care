<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="list" value="${requestScope.listEvent}"/>
<c:set var="eventSelected" value="${requestScope.eventSelected}"/>
<!-- Begin Page Content -->
<div class="container-fluid">
                    <div class="row">
                        <div class="col-xl-12">
                            <div class="card shadow mb-4 table-responsive">
                                <div class="row card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary col-lg-3 d-flex align-items-center">
                                        Dòng thời gian</h6>
                                    <div class="col-lg-5 col-6 d-flex justify-content-start align-items-center">
                                    	Có &nbsp<span class="text-warning" id="numberResult"></span>&nbsp kết quả.
                                    </div>
                                    <div class="col-lg-4">
                                        <div class="input-group" id="searchAccountName">
                                            <input type="text" class="form-control bg-light border-0 small text-info"
                                                placeholder="Tìm theo tên tài khoản.." aria-label="Search">
                                            <div class="input-group-append">
                                                <button class="btn btn-primary" type="button" onclick="searchAction()">
                                                    <i class="fas fa-search fa-sm"></i>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="mt-3 col-12">
                                        <select class="custom-select" id="eventSelect">
                                        <c:choose>
                                        <c:when test="${eventSelected eq 0}">
                                            <option selected value="0">Tất cả</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="0">Tất cả</option>
                                        </c:otherwise>
                                        </c:choose>
                                       	<c:forEach var="event" items="${list}">
                                       	<c:set var="title" value="${event.title}"/>
                                       	<c:set var="id" value="${event.id}"/>
                                       	<c:choose>
                                       	<c:when test="${eventSelected eq id}">
                                           	<option selected value='<c:out value="${id}"/>'><c:out value="${title}"/></option>
                                       	</c:when>
                                       	<c:otherwise>
                                           	<option value='<c:out value="${id}"/>'><c:out value="${title}"/></option>
                                       	</c:otherwise>
                                       	</c:choose>
                                      	</c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <table id="donateTableDetail" class="table table-hover m-0">
                                    <thead>
                                        <tr>
                                            <th>Nhà hảo tâm</th>
                                            <th>Số tiền</th>
                                            <th>Thời gian</th>
                                            <th style="width: 30%">Lời nhắn</th>
                                        </tr>
                                    </thead>
                                    <tbody style="cursor: pointer;">
                                    </tbody>
                                </table>
                                <div class="card-footer text-center">
                                    <button class="btn btn-light" id="showMoreButton">Xem thêm</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="container" style="position: fixed; top: 10%; z-index:3; display: none;"
                        id="detailDonateCard">
                        <div class="card shadow bg-gradient-info">
                            <div class="card-header bg-gradient-light d-flex justify-content-between">
                                <h5 class="text-success m-0">Chi tiết hóa đơn</h5>
                                <button class="btn btn-sm btn-outline-danger" onclick="hideDetail()">
                                    <i class="fas fa-times"></i>
                                </button>
                            </div>
                            <div class="card-body">
                                <table class="table table-borderless text-light">
                                    <tr>
                                        <th class="text-nowrap">Chương trình</th>
                                        <td id="title"></td>
                                    </tr>
                                    <tr>
                                        <th class="text-nowrap">Nhà hảo tâm</th>
                                        <td id="accountName"></td>
                                    </tr>
                                    <tr>
                                        <th class="text-nowrap">Số tiền quyên góp</th>
                                        <td id="money"></td>
                                    </tr>
                                    <tr>
                                        <th class="text-nowrap">Ngày quyên góp</th>
                                        <td id="date"></td>
                                    </tr>
                                    <tr>
                                        <th class="text-nowrap">Lời nhắn</th>
                                        <td id="message"></td>
                                    </tr>
                                    <tr>
                                        <th class="text-nowrap">Yêu cầu ẩn danh</th>
                                        <td id="secret"></td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
<!-- /.container-fluid -->
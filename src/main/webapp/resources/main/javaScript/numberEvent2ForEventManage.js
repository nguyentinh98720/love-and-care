let TOTAL_EVENTMANAGE = 0;
let START_EVENTMANAGE = 0;
let NUM_PER_PAGE_EVENTMANAGE = 7;
let currentData = [];

$(document).ready(function() {
	let resultUpdate = $("input#resultUpdate").val();
	let resultCreate = $("input#resultCreate").val();
	if(resultUpdate == 'true') {
		displayAlertSuccess("Cập nhật thông tin chương trình thành công");
	} else if(resultUpdate == 'false') {
		displayAlertError("Có lỗi xảy ra, vui lòng thử lại");
	}
	if(resultCreate == 'true') {
		displayAlertSuccess("Tạo mới thành công. Chương trình đang được triển khai");
	} else if(resultCreate == 'false') {
		displayAlertError("Có lỗi xảy ra, vui lòng thử lại");
	}

    //dữ liệu được tải ngay khi vào trang lượt đầu tiên
    getResult('lastest','all');

    let sort = $("select#sortResult");
    let filter = $("select#filterResult");
    let searchButton = $("button#searchButtonEventManage");
    let searchInput = $("input#searchInputEventManage");
    sort.change(function() {
        getResult(sort.val(), filter.val());
        displayAndPagination(0);
        searchInput.val('');
    })
    filter.change(function() {
        getResult(sort.val(), filter.val());
        displayAndPagination(0);
        searchInput.val('');
    })
    searchButton.click(function() {
        if(searchInput.val() != '' && searchInput.val() != null) {
            $("#sortResult>option").removeAttr("selected");
            $("#sortResult>option:first-child").attr("selected","selected");
            $("#filterResult>option").removeAttr("selected");
            $("#filterResult>option:first-child").attr("selected","selected");
            getSearch(searchInput.val());
            displayAndPagination(0);
        } else {
			searchInput.addClass("bg-warning border-danger");
            return false;
        }
    })
	searchButton.mouseleave(function() {
		searchInput.removeClass("bg-warning border-danger");
	});
    $("ul>li#prevpage").click(function() {
        START_EVENTMANAGE = START_EVENTMANAGE - NUM_PER_PAGE_EVENTMANAGE;
        displayAndPagination(START_EVENTMANAGE);
    })
    $("ul>li#nextpage").click(function() {
        START_EVENTMANAGE = START_EVENTMANAGE + NUM_PER_PAGE_EVENTMANAGE;
        displayAndPagination(START_EVENTMANAGE);
    })
    $("input#chooseAll").change(function() {
        toggleChooseAll();
    })
    $("span#chooseDelete").click(function() {
        deleteFromChoose();
    })
    $("span#chooseTurnOff").click(function() {
        turnOffStatusFromChoose();
    })
	$("span#chooseTurnOn").click(function() {
		turnOnStatusFromChoose();
	})
})

//lấy danh sách theo điều kiện sắp xếp và lọc
function getResult(sort, filter) {
    let data = [];
/* ======================================================== AJAX HERE ============================================================ */
	let url = "https://love-and-care.herokuapp.com/chuong-trinh/danh-sach-chuong-trinh/" + sort + "/" + filter;
	$.ajax({
		url: url,
		data: null,
		type: 'post',
		cache: false,
		success: function(result){
			$.each(result, function(index, donation)  {
				data.push(donation);
			})
		    data.reverse;
		    currentData = data;
		    currentData.forEach((donation) => {
		        donation.choose = false;
		    })
		    displayAndPagination(0);
			$("span#countChoose").text(currentData.length);
		},
		error: function() {
			diplayAlertError("error, there are something wrong here,,,");
		}
	})
}

//lấy danh sách theo kết quả tìm kiếm (tìm kiếm theo tên)
function getSearch(search) {
    let data = [];

/* ======================================================== AJAX HERE ============================================================= */
	let url = "https://love-and-care.herokuapp.com/chuong-trinh/tim-kiem-chuong-trinh/" + search;
	$.ajax({
		url: url,
		data: null,
		type: 'post',
		cache: false,
		success: function(result){
			$.each(result, function(index, donation)  {
				data.push(donation);
			})
		    data.reverse;
		    currentData = data;
		    currentData.forEach((donation) => {
		        donation.choose = false;
		    })
		    displayAndPagination(0);
			$("span#countChoose").text(currentData.length);
		},
		error: function() {
			alert("error, there are something wrong here,,,");
		}
	})
}

//hiển thị danh sách ra bảng
function displayAndPagination(from) {

    TOTAL_EVENTMANAGE = currentData.length;

    START_EVENTMANAGE = from;
    let to = START_EVENTMANAGE + NUM_PER_PAGE_EVENTMANAGE;
    if(to > TOTAL_EVENTMANAGE) {
        to = TOTAL_EVENTMANAGE;
    }

    showOntable(START_EVENTMANAGE, to);
    paginationEventManage(START_EVENTMANAGE);
}

function paginationEventManage(from) {
/* tải và lưu mảng, hiển thị và phân trang đều bằng js, lưu ý rằng mảng bắt đầu từ vị trí 0, điều kiện để hiển thị prev và next sẽ khác so với phân trang và lấy trực tiếp từ cơ sở dữ liệu */
    $("ul#paginationListEventDetail>li").remove(".liForListEventManage");
    let numPage = Math.ceil(TOTAL_EVENTMANAGE/NUM_PER_PAGE_EVENTMANAGE)
    let prev = from - NUM_PER_PAGE_EVENTMANAGE;
    let next = from + NUM_PER_PAGE_EVENTMANAGE;
    //nút điều hướng về trang trước
    if(prev >= 0) {
        $("li#prevpage").show();
    } else {
        $("li#prevpage").hide();
    }
    //nút điều hướng về trang sau
    if(next < TOTAL_EVENTMANAGE) {
        $("li#nextpage").show();
    } else {
        $("li#nextpage").hide();
    }
    for(let i = numPage; i > 0; i--) {
        let paginationFromValue = NUM_PER_PAGE_EVENTMANAGE*(i-1);
        if(from == paginationFromValue) {
            $("li#prevpage").after("<li class='page-item active liForListEventManage'><span class='page-link' role='button' onclick='displayAndPagination(" + paginationFromValue + ")'>" + i + "</span></li>")
        } else {
            $("li#prevpage").after("<li class='page-item liForListEventManage'><span class='page-link' role='button' onclick='displayAndPagination(" + paginationFromValue + ")'>" + i + "</span></li>")
        }
    }
}

function showOntable(from, to) {
	var formater = new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND'});
    let table = $("table#dataTable>tbody");
    table.empty();
    for(let i = from; i < to; i++) {
        table.append("<tr><td class='align-middle p-1'>" + checkbox(currentData[i].id, currentData[i].choose) + "</td><td class='align-middle'>" + currentData[i].id +
        "</td><td class='text-left'>" + currentData[i].title + "</td><td class='align-middle'>" + getDateFormat(currentData[i].startDate) +
        "</td><td class='align-middle'>" + formater.format(currentData[i].purpose) + "</td><td class='align-middle'>" + formater.format(currentData[i].achievement) +
        "</td><td class='align-middle'>" + getDateFormat(currentData[i].endDate) + "</td><td class='align-middle text-center'>" +
		statusButtonOfEvent(currentData[i].id, currentData[i].status, currentData[i].endDate) +
        "</td><td class='align-middle'><div class='row''>" + manage(currentData[i].id) + "</div></td></tr>");
    }
}

//lấy định dạng ngày tháng
function getDateFormat(date) {
	if(date == null || date == undefined) {
		return 'Chưa xác định';
	}
	return new Date(date).toLocaleDateString("vi-VN");
}

//thành phần checkbox ở đầu mỗi chương trình
function checkbox(id, choose) {
    let content = "";
    if(choose) {
        content = "<label class='labelCheckbox labelVati'><input type='checkbox' onchange='choose(" + id + ")' checked/><span class='checkmark'></span></label>";
    } else {
        content = "<label class='labelCheckbox labelVati'><input type='checkbox' onchange='choose(" + id + ")'/><span class='checkmark'></span></label>";
    }
    return content;
}

//các thành phần trong ô tác vụ của mỗi chương trình
function manage(id) {
/* ============================================ SET LINK HERE =========================================================== */
    //link to detail information of event
    let detailEvent = "<a class='btn text-success d-flex justify-content-center align-items-center col-4 border' href='https://love-and-care.herokuapp.com/chuong-trinh/chi-tiet-chuong-trinh/" + id + "' role='button' title='Chi tiết chương trình'><i class='fas fa-info' aria-hidden='true'></i></a>";
    //link to edit event information
    let editEvent = "<a class='btn text-primary d-flex justify-content-center align-items-center col-4 border' href='https://love-and-care.herokuapp.com/chuong-trinh/cap-nhat-chuong-trinh?eventId=" + id + "' role='button' title='Chỉnh sửa/Cập nhật'><i class='fa fa-edit' aria-hidden='true'></i></a>";
    //delete the event from database
    let deleteEvent = "<span class='btn text-danger d-flex justify-content-center align-items-center col-4 border' role='button' onclick='deleteEvent(" + id + ")' title='Xóa'><i class='fas fa-trash' aria-hidden='true'></i></span>";

    return detailEvent + editEvent + deleteEvent;
}

//thành phần (nút on off) trạng thái hoạt động trong ô trạng thái
function statusButtonOfEvent(id, status, endDate) {
    let buttonText = "";
    let today = new Date();
    let endDateOfEvent = new Date(endDate);
    if(today > endDateOfEvent) {
        buttonText = "<span class='badge badge-pill badge-secondary'>Đã kết thúc</span>";
    } else if(status) {
        buttonText = "<label class='switchVATI'><input type='checkbox' checked onchange='changeStatusEventById(" + id + ")'/><span class='sliderVATI round'></span></label>";
    } else {
        buttonText = "<label class='switchVATI'><input type='checkbox' onchange='changeStatusEventById(" + id + ")'/><span class='sliderVATI round'></span></label>";
    }
    return buttonText;
}

//Quy trình khi xóa một chương trình cụ thể
function deleteEvent(id) {
    for(let i = 0; i < currentData.length; i++) {
        if(currentData[i].id == id) {
            let title = currentData[i].title;
            if(displayAlertConfirm("Xóa chương trình: " + title)) {
                if(serverDelete(id)) {
                    currentData.splice(i,1);
                    displayAlertSuccess("Xóa thành công");
                } else {
                    displayAlertError("something wrong.... :(<br>The event cann't be deleted!");
                }
                break;
            }
        }
    }
    displayAndPagination(START_EVENTMANAGE);
}

//Gửi mã id của chương trình đến server để xử lý xóa khỏi cơ sở dữ liệu
function serverDelete(id) {
/* ============================================= POST HERE - DELETE AN EVENT WITH IT'S ID ===================================== */
	let success = false;
	let url = "https://love-and-care.herokuapp.com/chuong-trinh/xoa-chuong-trinh";
	$.ajax({
		url: url,
		data: {'id':id},
		type: 'post',
		async: false,
		cache: false,
		success: function(result){
			success = result;
		},
		error: function() {
			displayAlertError("error, there are something wrong here,,,");
		}
	})
	return success;
}

function changeStatusEventById(id) {
    for(let i = 0; i < currentData.length; i++) {
        if(currentData[i].id == id) {
            if(serverToggeStatusEvent(currentData[i].id, currentData[i].status)) {
                currentData[i].status = !currentData[i].status;
                if(currentData[i].status) {
                    displayAlertSuccess("Đã khởi động chương trình.");
                } else {
                    displayAlertSuccess("Đã ngừng hoạt động chương trình");
                }
            } else {
                displayAlertError("something wrong... :(<br>Can not change the status of this event!");
            }
            break;
        }
    }
    displayAndPagination(START_EVENTMANAGE);
}

//Gửi mã id của chương trình lên server để thao tác bật tắt trạng thái triển khai của một chương trình
function serverToggeStatusEvent(id, status) {
/* =========================================== POST HERE - TURN ON OR OFF AND EVENT STATUS ======================================= */
/* LƯU Ý ĐẢO NGƯỢC GIÁ TRỊ STATUS TRƯỚC KHI GỬI LÊN SERVER ĐỂ ĐẶT TRẠNG THÁI */
	let success = false;
	let url = "https://love-and-care.herokuapp.com/chuong-trinh/cai-dat-trang-thai";
	$.ajax({
		url: url,
		data: {'id':id,'status':!status},
		type: 'post',
		async: false,
		cache: false,
		success: function(result){
			success = result;
		},
		error: function() {
			displayAlertError("error, there are something wrong here,,,");
		}
	})
	return success;
}

//Nhận vào giá trị id, trạng thái thay đổi của checkbox để quyết định một chương trình có được chọn hay không
function choose(id) {
    currentData.forEach((donation) => {
        if(donation.id == id) {
            donation.choose = !donation.choose;
        }
    })
    chooseAllAndNextStep();
}

//checked hoặc unchecked tất cả các chương trình một lần khi click chọn tất cả
function toggleChooseAll() {
    // $("input#chooseAll").removeAttr("checked","checked");
    if($("input#chooseAll").is(":checked")) {
        chooseAllTheEventInList();
    } else {
        notChooseAllTheEventInList();
    }
    displayAndPagination(START_EVENTMANAGE);
}

//checked tất cả các mục
function chooseAllTheEventInList() {
	for(let a = 0; a < TOTAL_EVENTMANAGE; a++) {
            currentData[a].choose = true;
        }
        displayAddOnWhenCheckboxEvent(TOTAL_EVENTMANAGE);
}
//unchecked tất cả các mục
function notChooseAllTheEventInList() {
	for(let b = 0; b < TOTAL_EVENTMANAGE; b++) {
            currentData[b].choose = false;
        }
        displayAddOnWhenCheckboxEvent(0);
}

//kiểm tra số lượng các checkbox đã tick, nếu tick hết thì ô tick tất cả cũng được tick và ngược lại
//Kèm với việc hiển thị các nút chức năng tương ứng
function chooseAllAndNextStep() {
    let num = 0;
    currentData.forEach((donation) => {
        if(donation.choose) {
            num++;
        }
    })
    if(num == TOTAL_EVENTMANAGE) {
        document.getElementById("chooseAll").checked = true;
        $("span#chooseAllLabel").text("Bỏ chọn tất cả");
    } else {
        document.getElementById("chooseAll").checked = false;
        $("span#chooseAllLabel").text("Chọn tất cả");
    }
    displayAddOnWhenCheckboxEvent(num);
    displayAndPagination(START_EVENTMANAGE);
}

//nhận số lượng checkbox được chọn và hiển thị các thành phần xóa, tắt trạng thái và số lượng
function displayAddOnWhenCheckboxEvent(num) {
    let numberChoosen = $("span#countChoose");
    let deleteButton = $("span#chooseDelete");
    let turnOffButton = $("span#chooseTurnOff");
	let turnOnButton = $("span#chooseTurnOn");
    if(num > 0) {
        numberChoosen.text(num + "/" + TOTAL_EVENTMANAGE);
        deleteButton.removeClass("d-none");
        turnOffButton.removeClass("d-none");
        turnOnButton.removeClass("d-none");
    } else {
        numberChoosen.text(TOTAL_EVENTMANAGE);
        deleteButton.addClass("d-none");
        turnOffButton.addClass("d-none");
        turnOnButton.addClass("d-none");
    }
}

//Chức năng cho nút Xóa các mục đã chọn
function deleteFromChoose() {
    let success = false;
    let num = 0;
    if(displayAlertConfirm("Bạn có chắc là muốn xóa danh sách các chương trình đã chọn khỏi cơ sở dữ liệu?")) {
        for(let i = 0; i < currentData.length; i++) {
            if(currentData[i].choose) {
                if(serverDelete(currentData[i].id)) {
                    currentData.splice(i--, 1);
                    success = true;
                    num++;
                } else {
                    displayAlertError(num + " event(s) has been deleted<br>There are something wrong here... :(<br>With Event has id: " + currentData[i].id);
                    success = false;
                    break;
                }
            }
        }
    }
    displayAndPagination(START_EVENTMANAGE);
    if(success) {
        displayAlertSuccess("Xóa thành công " + num + " chương trình!");
    }
}

//Chức năng cho nút tắt kích hoạt các mục đã chọn
function turnOffStatusFromChoose() {
    let success = false;
    let num = 0;
	const timeNow = new Date();
	timeNow.setHours(0);
	timeNow.setMinutes(0);
	
    if(displayAlertConfirm("Bạn có chắc chắn muốn hủy kích hoạt các chương trình đã chọn?")) {
        for(let donation of currentData) {
	console.log(donation.endDate);
            if(donation.choose && donation.status && !(new Date(donation.endDate) <= timeNow)) {
                if(serverToggeStatusEvent(donation.id, true)) {
                    donation.status = false;
                    success = true;
                    num++;
                } else {
                    displayAlertError(num + " event(s) has been changed status<br>There are something wrong with status of event has id: " + donation.id +"<br>:(");
                    success = false;
                    break;
                }
            }
        }
    }
	notChooseAllTheEventInList();
	document.getElementById("chooseAll").checked = false;
    displayAndPagination(START_EVENTMANAGE);
    if(success) {
        displayAlertSuccess("Ngừng kích hoạt thành công " + num + " chương trình!");
    }
}

//chức năng cho nút khởi động các mục đã chọn
function turnOnStatusFromChoose() {
	let success = false;
	let num = 0;
	let numEnded = 0;
	const timeNow = new Date();
	timeNow.setHours(0);
	timeNow.setMinutes(0);
	
	if(displayAlertConfirm("Các chương trình đã chọn sẽ được triển khai trên trang thông tin.")) {
        for(let donation of currentData) {
			if(new Date(donation.endDate) <= timeNow) {
				numEnded++;
				success = true;
			} else {
	            if(donation.choose && !donation.status) {
	                if(serverToggeStatusEvent(donation.id, false)) {
	                    donation.status = true;
	                    success = true;
	                    num++;
	                } else {
	                    displayAlertError(num + " event(s) has been changed status<br>There are something wrong with status of event has id: " + donation.id +"<br>:(");
	                    success = false;
	                    break;
	                }
				}
            }
        }
    }
	notChooseAllTheEventInList();
	document.getElementById("chooseAll").checked = false;
    displayAndPagination(START_EVENTMANAGE);
    if(success) {
        displayAlertSuccess("Khởi động thành công " + num + " chương trình!<br>Bỏ qua " + numEnded + " chương trình đã kết thúc! :)");
    }
}

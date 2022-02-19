const NUM_GET = 10;
let TOTAL_ITEM;
let START_ITEM;
let searching;

let formatMoney;

let search;
let eventId;

let dataArea;
let showMoreButton;
let showTotalNum;

let detailCard;

const list = [];

$(document).ready(function() {
    search = $("div#searchAccountName");
    eventId = $("select#eventSelect");
    dataArea = $("table#donateTableDetail>tbody");
    showMoreButton = $("button#showMoreButton");
    detailCard = $("div#detailDonateCard");
    showTotalNum = $("span#numberResult");
    searching = false;
	formatMoney = new Intl.NumberFormat("vi-VN", {style: 'currency', currency: 'VND'});

    START_ITEM = 1;
    getAndShowByEventId(eventId.val());

    search.children("input").focus(function() {
        search.children("input").removeClass("border-danger");
        search.children("div").children("button").removeAttr("disabled");
        search.children("input").addClass("border-0");
    })

    eventId.change(function() {
		START_ITEM = 1;
        canceSearchAction();
        getAndShowByEventId(eventId.val());
    })

    showMoreButton.click(function() {
        START_ITEM += NUM_GET;
        
        if(START_ITEM > TOTAL_ITEM) {
            showMoreButton.attr("disabled", "disabled");
            showMoreButton.html("<i class='text-warning fas fa-folder-open'></i><span> Hết</span>");
            return;
        }
        if(searching) {
            showNewData(
                getDataFromServer(START_ITEM, eventId.val(), true, search.children("input").val()),
                false
            );
        } else {
            showNewData(
                getDataFromServer(START_ITEM, eventId.val(), false, null),
                false
            );
        }
    })
})

//khi click vào nút tìm kiếm, kiểm tra dữ liệu có trong ô input và trạng thái đang tìm kiếm hay chuẩn bị tìm kiếm
function searchAction() {
    let searchInput = search.children("input");
    if(searching) {
        canceSearchAction();
        getAndShowByEventId(eventId.val());
    } else {
        if(searchInput.val() == "") {
            searchInput.removeClass("border-0");
            searchInput.addClass("border-danger");
            search.children("div").children("button").attr("disabled","disabled");
        } else {
            searching = true;
            getAndShowBySearch(searchInput.val(), eventId.val());
            search.children("div").children("button").html("<i class='fas fa-times'></i>");
        }
    }
}

//Hủy trạng thái tìm kiếm
function canceSearchAction() {
    searching = false;
    search.children("input").val("");
    search.children("div").children("button").html("<i class='fas fa-search'></i>");
}

//Hàm tìm kiếm và hiển thị các lượt quyên góp bằng tên các nhà hảo tâm và mã chương trình cụ thể
function getAndShowBySearch(searchValue, eventId) {
    START_ITEM = 1;
    showMoreButton.removeAttr("disabled");
    showMoreButton.text("Xem thêm");
    TOTAL_ITEM = getMaxSizeOfList(eventId, true, searchValue);
    showNewData(
        getDataFromServer(START_ITEM, eventId, true, searchValue),
        true
        );
}
//Hàm hiển thị các lượt quyên góp dựa vào mã chương trình cụ thể
function getAndShowByEventId(eventId) {
    START_ITEM = 1;
    showMoreButton.removeAttr("disabled");
    showMoreButton.text("Xem thêm");
    TOTAL_ITEM = getMaxSizeOfList(eventId, false, null);
    showNewData(
        getDataFromServer(START_ITEM, eventId, false, null),
        true
    );
}

//Hàm điều hướng và lấy giá trị từ server
function getDataFromServer(from, eventId, isSearching, searchValue) {
    let to = from + NUM_GET;
    if(to > TOTAL_ITEM + 1) {
        to = TOTAL_ITEM + 1;
    }
    let data = [];
    $.ajax({
		url: 'https://love-and-care.herokuapp.com/admin/danh-sach-dong-gop',
		data: {'from': from, 'to': to, 'eventId': eventId, 'isSearching': isSearching, 'searchValue': searchValue},
		cache: false,
		type: 'post',
		async: false,
		success: function(result) {
			for(let donate of result) {
				data.push(donate);
			}
		},
		error: function(err) {
			console.log("Opp! something wrong..");
			console.log(err);
		}
	});
	return data;
}

/*
Khi doClear == true: Hàm xóa hoàn toàn dữ liệu trong bảng và hiển thị dữ liệu (data) mới
khi doClear == false: Hàm tiếp tục chèn dữ liệu mới (data) vào bảng và dữ nguyên dữ liệu cũ
 */
function showNewData(data, doClear) {
    if(doClear) {
        dataArea.empty();
        list.length = 0;
    }
    data.forEach((donate) => {
        dataArea.append(content(donate));
        list.push(donate);
    })
}

function getMaxSizeOfList(eventId, isSearching, searchValue) {
	let data = 0;
	$.ajax({
		url: "https://love-and-care.herokuapp.com/admin/so-luong-truy-van",
		data: {'eventId': eventId, 'isSearching': isSearching, 'searchValue': searchValue},
		type: 'post',
		cache: false,
		async: false,
		success: function(result) {
			data = result;
		},
		error: function(err) {
			console.log(err);
		}		
	})
    showTotalNum.text(data);
	return data;
}

function content(donate) {
    let content = "";

    if(donate.secret) {
        content = "<tr onclick='showDetailDonate(" + donate.donateId +
        ")'><td><small class='text-secondary'>" + donate.accountName +
        "</small></td><td>" + formatMoney.format(donate.money) +
        "</td><td>" + new Date(donate.date).toLocaleDateString("vi-VN") +
        "</td><td>" + donate.message + "</td></tr>";
    } else {
        content = "<tr onclick='showDetailDonate(" + donate.donateId +
        ")'><td>" + donate.accountName +
        "</td><td>" + formatMoney.format(donate.money) +
        "</td><td>" + new Date(donate.date).toLocaleDateString("vi-VN") +
        "</td><td>" + donate.message + "</td></tr>";
    }
    return content;
}

function showDetailDonate(donateId) {
	for(let donate of list) {
		if(donate.donateId == donateId) {
			data = donate;
		    let detailArea = detailCard.find("table");
		    detailArea.find("#accountName").text(data.accountName);
		    detailArea.find("#money").text(formatMoney.format(data.money));
		    detailArea.find("#date").text(new Date(data.date).toLocaleDateString("vi-VN"));
		    detailArea.find("#message").text(data.message);
		    detailArea.find("#secret").text(data.secret? "Có" : "Không");
		    detailCard.fadeIn();
		    $.post("https://love-and-care.herokuapp.com/chuong-trinh/tieu-de-chuong-trinh?eventId=" + donate.eventId,
		    function(result) {
			    detailArea.find("#title").text(result);
			});
		}
	}
	return;
}

function hideDetail() {
    detailCard.fadeOut();
}
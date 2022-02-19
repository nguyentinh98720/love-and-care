$(document).ready(function() {
    eventNumberForDashboard();
    accountNumberForDashboard();
    lastestDonateForDashboard();
})
var formater = new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND'});

/* 
*Truy vấn các chương trình có trong cơ sở dữ liệu với các điều kiện
- Số tiền huy động nhiều nhất mà một chương trình gây quỹ triển khai -> kèm mã id chương trình -> link đến thông tin chi tiết chương trình đó
- Số tiền quyên góp lớn nhất mà một nhà hảo tâm quyên góp cho chương trình gây quỹ bất kỳ -> kèm mã id nhà hảo tâm -> link đến trang cá nhân
- Thời gian huy động và gây quỹ thành công ngắn nhất của một chương trình -> kèm id chương trình -> đến trang thông tin chương trình
- Số lượng chương trình chưa xác định thời gian kết thúc triển khai -> link to servlet
*/

function eventNumberForDashboard() {
    //dùng $.post để lấy thông tin
/* ==================================================== POST ==================================================== */

    let a1 = $("a#maxEventMoney");
    let a3 = $("a#minEventTime");
	$.post(
		"https://love-and-care.herokuapp.com/chuong-trinh/chuong-trinh-dac-biet",
		function(result) {
		    //số tiền gây quỹ lớn nhất
			let maxEventMoney = result.highestPurpose;
		    a1.text(formater.format(maxEventMoney.purpose));
			let url1 = "https://love-and-care.herokuapp.com/chuong-trinh/chi-tiet-chuong-trinh/" + maxEventMoney.id;
		    a1.attr("href", url1);
		    //thời gian huy động ngắn nhất
			let minEventTime = result.shortestTimeDeployment;
			let difference = (new Date(minEventTime.endDate).getTime() - new Date(minEventTime.startDate).getTime())/(1000*60*60*24);
//		    a3.text(Math.round(difference) + " ngày");
		    a3.text(difference + " ngày");
			let url2 = "https://love-and-care.herokuapp.com/chuong-trinh/chi-tiet-chuong-trinh/" + minEventTime.id;
		    a3.attr("href", url2);
		}
	);
    
    let a2 = $("a#maxDonationMoney");
	$.post(
		"https://love-and-care.herokuapp.com/quyen-gop/dong-gop-lon-nhat",
		function(result) {
		    //số tiền quyên góp nhiều nhất
		    a2.text(formater.format(result.contributed));
			let url3 = "https://love-and-care.herokuapp.com/tai-khoanr/thong-tin-tai-khoan?accountId" + result.accountId;
		    a2.attr("href", url3);
		}
	);
    
    
    let a4 = $("a#noEndTimeEvent");
	$.post(
		"https://love-and-care.herokuapp.com/chuong-trinh/khong-thoi-han",
		function(result) {
		    //chưa xác định thời gian kết thúc
		    a4.text(result + " chương trình");
		}
	);
    a4.attr("href", "https://love-and-care.herokuapp.com/admin/quan-ly-chuong-trinh");
}

/*
*Truy vắn số lượng các tài khoản
*/

function accountNumberForDashboard() {
    //dùng $.get lấy thông tin
/* ==================================================== GET ============================================== */    
	let urlAccountManage = "https://love-and-care.herokuapp.com/admin/quan-ly-tai-khoan";

    let b1 = $("a#totalAccount");
    let b2 = $("a#adminAccount");
    let b3 = $("a#userAccount");
    let b4 = $("a#lockedAccount");

    b1.attr("href",urlAccountManage);
    b2.attr("href",urlAccountManage);
    b3.attr("href",urlAccountManage);
    b4.attr("href",urlAccountManage);

	$.post(
		"https://love-and-care.herokuapp.com/tai-khoan/thong-ke-tai-khoan",
		function(data) {
		    //Tổng số lượng tài khoản trong hệ thống
		    b1.text(data.total);
		    //Số lượng tài khoản quản trị
		    b2.text(data.adminCount);
		    //Số lượng tài khoản người dùng
		    b3.text(data.userCount);
		    //Số lượng tài khoản đang bị khóa hoạt động
		    b4.text(data.lockedCount);
			
		}
	);
}

function lastestDonateForDashboard() {
	let topSize = 10;
    let table = $("table#donateTable>tbody");
    table.empty();
    
    //$.post lấy danh sách lần quyên góp mới nhất
/* ==================================================== Post ============================================== */    
	$.post(
		"https://love-and-care.herokuapp.com/quyen-gop/danh-sach-dong-gop-moi-nhat",
		{'topSize': topSize},
		function(result) {
			if(result.length <= topSize) {
				showDataOnTable(result, table);
			}
		}
	);
}

function showDataOnTable(data, table) {
    for(let i = 0; i < data.length; i++) {
		let title = data[i].eventTitle;
		if(title.length > 70) {title = title.substr(0,70) + "...";};
		let message = data[i].message;
		if(message == undefined) {message = ""};
        if(data[i].secret == false) {
            table.append("<tr onclick='showDetailForEvent(" + data[i].eventId + ")'><td>" + data[i].accountName +
            "</td><td>" + title + "</td><td>" + formater.format(data[i].money) + "</td><td>" +
            new Date(data[i].date).toLocaleDateString() + "</td><td>" + message + "</td></tr>");
        } else {
            table.append("<tr onclick='showDetailForEvent(" + data[i].eventId + ")'><td><small><i>" + data[i].accountName +
            "</i></small></td><td>" + title + "</td><td>" + formater.format(data[i].money) + "</td><td>" +
            new Date(data[i].date).toLocaleDateString() + "</td><td>" + message + "</td></tr>");
        }
    }
}

function showDetailForEvent(eventId) {
	location.replace("https://love-and-care.herokuapp.com/chuong-trinh/chi-tiet-chuong-trinh/" + eventId);
}
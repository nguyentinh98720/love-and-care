const NUM_PER_GET = 5;
let TOTAL = 0;
let START = 1;

let insertArea;
let showMore;

$(document).ready(function() {
	insertArea = $("table#historyTable");
	showMore = $("button#showMoreButton");
	
	TOTAL = $("td#total").text();
	
	history(START);
	
	showMore.click(function() {
		START += NUM_PER_GET;
		history(START);
	})
})

function history(from) {
	if(from > TOTAL) {
		showMore.text("Hết rồi");
		showMore.attr("disabled", "disabled");
		return;
	}
	getHistoryFromServer(from);
}

function getHistoryFromServer(from) {
	let to = from + NUM_PER_GET;
	$.post(
		"https://love-and-care.herokuapp.com/quan-ly/lich-su-quyen-gop",
		{'from': from, 'to': to},
		function(result) {
			if(result == null) {
				alert("Bạn cần đăng nhập lại!");
				location.replace("https://love-and-care.herokuapp.com/greeting/dang-nhap");
			} else {
				showList(result);
			}
		}
	)
}

function showList(data) {
	data.forEach((content) => {
		insertArea.append(dataContent(content));
	})
}

function dataContent(content) {
	let formatMoney = new Intl.NumberFormat("vi-VN", {style: 'currency', currency: 'VND'});
	
	let title = content.eventTitle;
	if(title.length > 100) {
		title = title.substr(1, 100) + "...";
	}
	return "<tr><td>" + content.donateId + "</td><td><a class='text-decoration-none link-dark' " +
	"href='https://love-and-care.herokuapp.com/quyen-gop/noi-dung-chuong-trinh?eventId=" + content.eventId + "'>" + title + "</a></td><td>" +
	new Date(content.date).toLocaleDateString("vi-VN") + "</td><td>" + formatMoney.format(content.money) +
	"</td><td>" + getNoteContent(content) + "</td></tr>";
}

function getNoteContent(content) {
	let result = "";
	if(content.secret == true) {
		result += "<span class='badge bg-dark text-light'><i class='fas fa-user-secret'></i></span><small> Ẩn danh</small><br>";
	}
	if(content.message != null && content.message != "") {
		result += "<span class='badge bg-primary'><i class='fas fa-comment-alt'></i></span><small> " + content.message + "</small>";
	}
	return result;
}
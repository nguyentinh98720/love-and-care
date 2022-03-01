const NUM_PER_GET = 3;
let START_GET = 1;
let ONLY_ACTIVE = true;
let total_get = 6;
let area = $("div#areaForEvent");
let showMoreButton = $("button#showMoreButton");

function imgSource(imageData) {
	if(imageData.arr != null) {
		return "<img src='data:" + imageData.type + ";base64," + imageData.encode + "' class='card-img-top' alt='Hình ảnh chương trình'>";
	}
	return "<img src='https://love-and-care.herokuapp.com/static/media/donation.jpg' class='card-img-top' alt='Hình ảnh chương trình'>";
}

function donationCard(id, image, title, time, achievement, purpose, numDonates) {
	if(title.length > 110) {
		title = title.substr(0, 110) + "...";
	}
	var formater = new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND'});
	let percents = Math.round(achievement/purpose * 100);
	let card = "<acticle onclick='detailDonationUrl(" + id + ")' class='col p-0 text-decoration-none text-dark vatiCard'>" + 
	"<div class='card mx-2'>" + image +
	"<div class='card-body'><h5 class='card-title pb-2'>" + title + "</h5><div class='d-flex justify-content-end align-items-center py-3'>" + time +
	"</div><div class='d-flex justify-content-start align-items-center py-2'>" + "<p class='px-1 text-success'>" + formater.format(achievement) +
	"</p><p class='px-1'>/</p><p class='px-1 text-warning fw-bold'>" + formater.format(purpose) +
	"</p></div><div class='progress'><div class='progress-bar progress-bar-striped bg-info' role='progressbar' style='width:" + percents +
	"%' aria-valuenow='" + percents + "'aria-valuemin='0' aria-valuemax='100'></div></div></div><div class='card-footer d-flex justify-content-around align-items-center'>" +
	"<small class='text-muted'>" + numDonates + " lượt đóng góp</small><small class='text-muted'>Đạt " + percents + "% kế hoạch</small></div></div></acticle>";
	return card;
}
function detailDonationUrl(id) {
	let url = "https://love-and-care.herokuapp.com/quyen-gop/noi-dung-chuong-trinh?eventId=" + id;
	location.replace(url);
}
function getList(from) {
	if(from > total_get) {
		loaded();
		showMoreButton.removeClass("border-info text-info");
		showMoreButton.children("span").text("Đã hết");
		showMoreButton.attr("disabled", "disabled");
	} else {
		getListFromServer(from, ONLY_ACTIVE);
	}
}
function getListFromServer(from, only_active) {
	let to = from +  NUM_PER_GET;
	let url = "https://love-and-care.herokuapp.com/action/lay-danh-sach";
	$.ajax({
		url: url,
		data: {'from': from, 'to': to, 'only_active': only_active},
		type: 'post',
		cache: false,
		success: function(result) {
			let list = result.list;
			total_get = result.total;
			showList(list);
		},
		error: function(error) {
			console.log(error);
		}
	});
}

function showList(list) {
	for(let donation of list) {
		let id = donation.id;
		let title = donation.title;
		let time;
		let timeNow = new Date();
		timeNow.setHours(0,0,0);
		if(new Date(donation.endDate) < timeNow) {
			time = "<p class='btn btn-sm btn-secondary rounded-pill text-light'>Đã kết thúc</p>";
		} else if(donation.endDate != null) {
			let days = (new Date(donation.endDate).getTime() - new Date(donation.startDate).getTime())/(3600*24*1000);
			temp = "Còn " + Math.round(days) + " ngày";
			time = "<p class='card-text text-warning'>" + temp + "</p>";
		} else {
			time = "<p class='text-info'>Dài hạn</p>";
		}
		let achievement = donation.achievement; let purpose = donation.purpose; let numDonates = donation.numDonates;
		let image = imgSource(donation.image);
		area.append(donationCard(id,image,title,time,achievement, purpose,numDonates));
	}
	loaded();
}

$(document).ready(function() {
	getList(START_GET);
//	START_GET += NUM_PER_GET;
//	getList(START_GET);
	
	showMoreButton.click(function() {
		loadingList();
		START_GET += NUM_PER_GET;
		getList(START_GET);
	})
})

function loadingList() {
	showMoreButton.attr("disabled", "disabled");
	showMoreButton.removeClass("border-info text-info");
	showMoreButton.addClass("btn-light");
	showMoreButton.children("div").children("i").show();
}

function loaded() {
	showMoreButton.removeAttr("disabled");
	showMoreButton.removeClass("btn-light");
	showMoreButton.addClass("border-info text-info");
	showMoreButton.children("div").children("i").hide();
}
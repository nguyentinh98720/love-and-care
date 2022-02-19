function canceCreateEvent() {
	let eventId = $("input#eventId").val();
	let success = false;
	if(displayAlertConfirm("Tất cả thay đổi sẽ không được lưu. Bạn vẫn muốn thoát chứ?")) {
		$.ajax({
			url: "https://love-and-care.herokuapp.com/chuong-trinh/xoa-chuong-trinh",
			data: {'id': eventId},
			type: 'post',
			cache: false,
			async: false,
			success: function(result) {
				success = result;
			},
			error: function() {
				success = false;
			}
		})
	}
	if(success == true) {
		location.replace("https://love-and-care.herokuapp.com/admin/quan-ly-chuong-trinh");
	} else {
		displayAlertError("Xảy ra lỗi khi xóa chương trình tạm này, yêu cầu xóa thủ công!! :(");
		setTimeout(function() {
			location.replace("https://love-and-care.herokuapp.com/admin/quan-ly-chuong-trinh");
		}, 3000);
	}
		
}

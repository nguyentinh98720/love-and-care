function clickDeleteEvent(id) {
	if(displayAlertConfirm("Bạn có chắc chắn muốn xóa chương trình quyên góp này không?")) {
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
		if(success) {
			displayAlertSuccess("Xóa chương trình thành công!");
			setTimeout(function() {
				location.replace("https://love-and-care.herokuapp.com/admin/quan-ly-chuong-trinh");
			}, 2000);
		} else {
			displayAlertError("Có lỗi xảy ra<br>Vui lòng thử lại.");
			setTimeout(function() {
				location.reload();
			}, 2000);
			
		}
	}
}

function canceEditEvent() {
	if(displayAlertConfirm("Tất cả thay đổi sẽ không được lưu. Bạn vẫn muốn thoát chứ?")) {
		location.replace("https://love-and-care.herokuapp.com/admin/quan-ly-chuong-trinh");
	}
}

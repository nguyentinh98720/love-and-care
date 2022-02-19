function resetPasswordById() {
	let userId = $("input#accountId").val();
	let userEmail = $("input#emailForEditAccout").val();
	if(displayAlertConfirm("Khôi phục mật khẩu\nMật khẩu mới được gủi tới email đăng ký.")){
		let button = $("button#resetPassword")
		button.removeClass("btn-warning");
		button.addClass("btn-secondary");
		button.attr("disabled", "disabled");
		let url = "https://love-and-care.herokuapp.com/tai-khoan/reset-mat-khau";
		$.ajax({
			url: url,
			data: {'id': userId, 'email': userEmail},
			type: 'post',
			cache: false,
			success: function(result) {
				if(result == 'true') {
					displayAlertSuccess("Đã khôi phục mật khẩu cho tài khoản này.");
				} else {
					displayAlertError("Có lỗi xảy ra trong quá trình khôi phục<br>Tải lại trang và thử lại.")
				}
			},
			error: function() {
				displayAlertError("Có lỗi xảy ra..")
			}
		})
	}
}
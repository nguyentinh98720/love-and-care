$(document).ready(function() {
	let roleSelect = $("select#roleForEditAccount");
	let roleBase = $("input#baseRoleOfAccount").val();
	roleSelect.change(function() {
		if(roleBase == 'ROLE_ADMIN' && roleSelect.val() == 'ROLE_USER') {
			displayAlertError("Bạn không thể chuyển một tài khoản Quản trị sang một tài khoản thường!!");
			roleSelect.children("option:last-child").remove();
		}
	})
})
/*
function imgCode(img) {
    let code = "";
    if(typeof img === 'undefined' || img == null || img == "") {
    } else {
        code = "<img src='" + img.url + "' alt='avatar' class='img-thumbnail' width='200'>" +
        "<span class='btn btn-sm btn-danger text-white' style='position: relative; bottom: 5.2rem; right: 2rem' onclick='deleteImgFromEditAccount(" + img.accountId  + ")'>" +
        "<i class='fa fa-trash' aria-hidden='true'></i></span>"
    }
    return code;
}
function showImageAccount(image) {
    $("div#imageAccountManage>div").append(imgCode(image));
}*/
function deleteImgFromEditAccount(id) {
    let code = "<div style='width:200px;height:200px' class='d-flex justify-content-center align-items-center bg-dark text-white'>Chưa có ảnh đại diện</div>";
    if(displayAlertConfirm("Xóa ảnh này:")) {
        if(serverDeleteAccountImage(id)) {
            $("div#imageAccountManage>div").remove();
            $("div#imageAccountManage").append(code);
			displayAlertSuccess("Đã xóa hình ảnh");
        } else {
            displayAlertError("sorry, there are something wrong.... :(");
        }
    }
}
function serverDeleteAccountImage(id) {
/* ================================================ DELETE IMAGE FROM SERVER ================================================================ */
	let success = false;
	let url = "https://love-and-care.herokuapp.com/tai-khoan/xoa-anh-dai-dien";
	$.ajax({
		url: url,
		data: {'id': id},
		method: 'post',
		async: false,
		cache: false,
		success: function(result) {
			if(result) {
				success = result;
			}
		},
		error: function() {
			displayAlertError("Đã xảy ra lỗi<br>ajax - 45");
		}
	})
	return success;
}
function deleteAccountFromEdit(id) {
	if(displayAlertConfirm("Bạn có chắc muốn xóa tài khoản này khỏi cơ sở dữ liệu?")) {
		let url = "https://love-and-care.herokuapp.com/tai-khoan/xoa-tai-khoan";
		$.ajax({
			url: url,
			data: {'id': id},
			async: false,
			method: 'post',
			success: function(result) {
				if(result) {
					displayAlertSuccess("Đã xóa tài khoản.");
					setTimeout(function() {
						location.replace("https://love-and-care.herokuapp.com/admin/quan-ly-tai-khoan");
					}, 2500)
				} else {
					displayAlertError("Có lỗi xảy ra, vui lòng thử lại. :(<br> ajax - 66");
				}
			}
		})
	}
}
function canceEditAccount() {
	if(displayAlertConfirm("Các thay đổi sẽ không được lưu lại, bạn vẫn muốn thoát chứ?")) {
		location.replace("https://love-and-care.herokuapp.com/admin/quan-ly-tai-khoan");
	}
}
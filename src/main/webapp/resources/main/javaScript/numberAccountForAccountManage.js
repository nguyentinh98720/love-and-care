let accountData = [];
let TOTALACCOUNT = 0;
let NUM_ACC_PER_PAGE = 5;
let START_ACC_POSITION = 0;

/* Quản lý thành phần account: sắp xếp, tìm kiếm, xóa,.. */
$(document).ready(function() {
	let resultUpdate = $("input#resultUpdate").val();
	let resultCreate = $("input#resultCreate").val();
	setTimeout(function() {
		if(resultUpdate == 'true') {
			displayAlertSuccess("Cập nhật thông tin tài khoản thành công");
		} else if(resultUpdate == 'false') {
			displayAlertError("Có lỗi xảy ra<br>Không thể cập nhật");
		}
		if(resultCreate == 'true') {
			displayAlertSuccess("Tạo mới tái khoản thành công");
		} else if(resultCreate == 'false') {
			displayAlertError("Có lỗi xảy ra, vui lòng thử lại");
		}
	}, 1500);

	//start here
	getAccountDataFromServerByFilter('all', 'all');
	
	//previous and next
	$("li#accountPrev").click(function() {
		displayAccountForAccoutTableManage(START_ACC_POSITION - NUM_ACC_PER_PAGE);
	})
	$("li#accountNext").click(function() {
		displayAccountForAccoutTableManage(START_ACC_POSITION + NUM_ACC_PER_PAGE);
	})

	let role = $("select#filterRoleAccount");
	let status = $("select#filterStatusAccount");
	let searchCondition = $("select#filterSearchAccount");
	let searchValue = $("input#searchInputAccountManage");
	let searchButton = $("button#searchButtonAccountManage");

	role.change(function() {
		let roleValue = role.val();
		let statusValue = status.val();
		searchValue.val("");
		getAccountDataFromServerByFilter(roleValue, statusValue);
		displayAccountForAccoutTableManage(0);
	});
	status.change(function() {
		let roleValue = role.val();
		let statusValue = status.val();
		searchValue.val("");
		getAccountDataFromServerByFilter(roleValue, statusValue);
		displayAccountForAccoutTableManage(0);
	});
	searchButton.click(function() {
		if (searchValue.val().trim() != "" && searchValue.val() != null) {
			let condition = searchCondition.val();
			let value = searchValue.val();
			getAccountDataFromServerBySearch(condition, value);
			role.children().removeAttr("selected");
			role.children("option:first-child").attr("selected", "selected");
			status.children().removeAttr("selected");
			status.children("option:first-child").attr("selected", "selected");
		} else {
			searchValue.addClass("bg-warning border-danger");
			return false;
		}
	});
	searchButton.mouseleave(function() {
		searchValue.removeClass("bg-warning border-danger");
	})
	$("input#chooseAllAccount").change(function() {
		toggleChooseAllAccount();
	})
	$("span#chooseDeleteAccount").click(function() {
		deleteChoosenAccount();
	})
	$("span#chooseLockAccount").click(function() {
		lockChoosenAccount();
	})
	$("span#chooseActiveAccount").click(function() {
		activeChoosenAccount();
	})
})

//lấy dữ liệu từ server theo kết quả lọc
function getAccountDataFromServerByFilter(role, status) {
	let data = [];
	/* ============================================================== POST DATA HERE ===================================================================== */

	let url = "https://love-and-care.herokuapp.com/tai-khoan/loc/" + role + "/" + status;
	$.ajax({
		url: url,
		data: null,
		type: 'post',
		cache: false,
		success: function(list) {
			$.each(list, function(index, account) {
/*==================================================== set image null here ===========**************/
account.avatar = null;
				data.push(account);
			})
			data.reverse;
			accountData = data;
			accountData.forEach((account) => {
				account.choose = false;
			})
			displayAccountForAccoutTableManage(0);
			$("span#countChooseAccount").text(TOTALACCOUNT);
	
		},
		error: function() {
			displayAlertError("Xảy ra lỗi, thử lại...");
		}
	})
}

//lấy dữ liệu từ server theo kết quả tìm kiếm
function getAccountDataFromServerBySearch(condition, value) {
	let data = [];
	/* ============================================================= POST DATA HERE ====================================================================== */

	let url = "https://love-and-care.herokuapp.com/tai-khoan/tim-kiem/" + condition + "/" + value;
	$.ajax({
		url: url,
		data: null,
		type: 'post',
		cache: false,
		success: function(list) {
			$.each(list, function(index, account) {
/*==================================================== set image null here ===========**************/
account.avatar = null;
				data.push(account);
			})
			data.reverse;
			accountData = data;
			accountData.forEach((account) => {
				account.choose = false;
			})
			displayAccountForAccoutTableManage(0);
			$("span#countChooseAccount").text(TOTALACCOUNT);
	
		},
		error: function() {
			displayAlertError("Xảy ra lỗi, thử lại...");
		}
	})
}


//hiển thị dữ liệu có trong bảng kết quả
function displayAccountForAccoutTableManage(from) {
	START_ACC_POSITION = from;

	TOTALACCOUNT = accountData.length;
	let to = from + NUM_ACC_PER_PAGE;
	if (to > TOTALACCOUNT) {
		to = TOTALACCOUNT;
	}

	showAccountToTable(START_ACC_POSITION, to);
	paginationAccountTable(START_ACC_POSITION);
}

//phân trang
function paginationAccountTable(from) {
	/* tải và lưu mảng, hiển thị và phân trang đều bằng js, lưu ý rằng mảng bắt đầu từ vị trí 0, điều kiện để hiển thị prev và next sẽ khác so với phân trang và lấy trực tiếp từ cơ sở dữ liệu */
	$("ul#paginationListAccountDetail>li").remove(".liForListAccountManage");
	let numPage = Math.ceil(TOTALACCOUNT / NUM_ACC_PER_PAGE)
	let prev = from - NUM_ACC_PER_PAGE;
	let next = from + NUM_ACC_PER_PAGE;
	//nút điều hướng về trang trước
	if (prev >= 0) {
		$("li#accountPrev").show();
	} else {
		$("li#accountPrev").hide();
	}
	//nút điều hướng về trang sau
	if (next < TOTALACCOUNT) {
		$("li#accountNext").show();
	} else {
		$("li#accountNext").hide();
	}
	for (let i = numPage; i > 0; i--) {
		let paginationFromValue = NUM_ACC_PER_PAGE * (i - 1);
		if (from == paginationFromValue) {
			$("li#accountPrev").after("<li class='page-item active liForListAccountManage'><span class='page-link' role='button' onclick='displayAccountForAccoutTableManage(" + paginationFromValue + ")'>" + i + "</span></li>")
		} else {
			$("li#accountPrev").after("<li class='page-item liForListAccountManage'><span class='page-link' role='button' onclick='displayAccountForAccoutTableManage(" + paginationFromValue + ")'>" + i + "</span></li>")
		}
	}

}

//chèn kết quả vào bảng
function showAccountToTable(from, to) {
	let table = $("table#dataTableAccount>tbody");
	table.empty();
	for (let i = from; i < to; i++) {
		let phoneNumber = (typeof accountData[i].phoneNumber === 'undefined')? "" : accountData[i].phoneNumber;
		table.append("<tr><td class='align-middle m-0 p-0'>" + checkboxAccount(accountData[i].id, accountData[i].choose) + "</td><td class='align-middle'>" +
			getImageAccount(accountData[i].id) + "</td><td class='text-left align-middle'>" +
			accountData[i].email + "</td><td class='align-middle'>" + accountData[i].firstName +
			"</td><td class='align-middle'>" + accountData[i].lastName + "</td><td class='align-middle'>" + phoneNumber +
			/*"</td><td>" + accountData[i].address +*/
			"</td><td class='align-middle'>" + accountData[i].role + "</td><td class='align-middle'>" + statusButton(accountData[i].id, accountData[i].status) +
			"</td><td class='row'>" + accountManage(accountData[i].id) + "</td></tr>");
	}
}

//hình ảnh cho tài khoản
function getImageAccount(accountId) {
	return `<div class="rounded-circle img-thumbnail"
			style="width:50px;height:50px;
			background-image:url('https://love-and-care.herokuapp.com/tai-khoan/avatar?accountId=${accountId}');
			background-repeat:none;background-size:cover;"`;
}

//thành phần checkbox đầu dòng mỗi tài khoản
function checkboxAccount(id, choose) {
	let content = "";
	if (choose) {
		content = "<label class='labelCheckbox labelVati'><input type='checkbox' onchange='chooseAccount(" + id + ")' checked/><span class='checkmark'></span></label>";
	} else {
		content = "<label class='labelCheckbox labelVati'><input type='checkbox' onchange='chooseAccount(" + id + ")'/><span class='checkmark'></span></label>";
	}
	return content;
}

//Khi click vào checkbox vào các checkbox dưới danh sách tài khoản
function chooseAccount(id) {
	let num = 0;
	accountData.forEach((account) => {
		if (account.id == id) {
			account.choose = !account.choose;
		}
		if (account.choose) {
			num++;
		}
	})
	if (num == TOTALACCOUNT) {
		document.getElementById("chooseAllAccount").checked = true;
	} else {
		document.getElementById("chooseAllAccount").checked = false;
	}
	displayAddOnWhenCheckbox(num);
	displayAccountForAccoutTableManage(START_ACC_POSITION);
}

//Khi click vào checkbox chọn tất cả
function toggleChooseAllAccount() {
	if ($("input#chooseAllAccount").is(":checked")) {
		accountData.forEach((account) => {
			if (!account.choose) {
				account.choose = true;
			}
		})
		displayAddOnWhenCheckbox(TOTALACCOUNT);
	} else {
		accountData.forEach((account) => {
			if (account.choose) {
				account.choose = false;
			}
		})
		displayAddOnWhenCheckbox(0);
	}
	displayAccountForAccoutTableManage(START_ACC_POSITION);
}

//hiển thị hay không các thành phần bổ sung khi có checkbox được chọn
function displayAddOnWhenCheckbox(num) {
	let deleteButton = $("span#chooseDeleteAccount");
	let turnOnButton = $("span#chooseActiveAccount");
	let turnOffButton = $("span#chooseLockAccount");
	let numberChoosen = $("span#countChooseAccount");
	if (num > 0) {
		deleteButton.removeClass("d-none");
		turnOffButton.removeClass("d-none");
		turnOnButton.removeClass("d-none");
		numberChoosen.text(num + "/" + TOTALACCOUNT);
	} else {
		deleteButton.addClass("d-none");
		turnOffButton.addClass("d-none");
		turnOnButton.addClass("d-none");
		numberChoosen.text(TOTALACCOUNT);
	}
}

//các thành phần trong ô tác vụ của mỗi tài khoản
function accountManage(id) {
	/* ============================================ SET LINK HERE =========================================================== */

	//link to detail information of event -- dẫn đến trang cá nhân
	let detailAccount = "<a class='btn text-success d-flex justify-content-center align-items-center col-4 border' href='https://love-and-care.herokuapp.com/tai-khoan/thong-tin-tai-khoan?accountId=" + id + "' role='button' title='Chi tiết thông tin tài khoản'><i class='fas fa-info' aria-hidden='true'></i></a>";
	//link to edit event information
	let editAccount = "<a class='btn text-primary d-flex justify-content-center align-items-center col-4 border' href='https://love-and-care.herokuapp.com/tai-khoan/cap-nhat-tai-khoan?accountId=" + id + "' role='button' title='Chỉnh sửa thông tin'><i class='fa fa-edit' aria-hidden='true'></i></a>";
	//delete the event from database
	let deleteAccount = "<span class='btn text-danger d-flex justify-content-center align-items-center col-4 border' role='button' onclick='deleteAccount(" + id + ")' title='Xóa tài khoản'><i class='fas fa-trash' aria-hidden='true'></i></span>";

	return detailAccount + editAccount + deleteAccount;
}
//khi thao tác xóa một tài khoản
function deleteAccount(id) {
	for (let i = 0; i < accountData.length; i++) {
		if (accountData[i].id == id) {
			let name = accountData[i].firstName + " " + accountData[i].lastName;
			if(accountData[i].role == 'ROLE_ADMIN') {
				displayAlertError("Bạn không thể xóa tài khoản quản trị!");
			} else {
				if (displayAlertConfirm("Chắc chắn rằng bạn muốn xóa tài khoản: " + name)) {
					if (serverDeleteAccount(id)) {
						accountData.splice(i, 1);
						displayAlertSuccess("Đẫ xóa tài khoản với tên: " + name);
					} else {
						if(accountData[i].status == true) {
							if(displayAlertConfirm("Không thể xóa tài khoản này!\nBạn có muốn khóa hoạt động không?")) {
								setTimeout(function() {
									changeStatusAccountById(id);
								}, 1000);
							}
						} else {
							displayAlertError("Không thể xóa tài khoản này!");
						}
					}
					break;
				}
			}
		}
	}
	displayAccountForAccoutTableManage(START_ACC_POSITION);
}
//gửi id của tài khoản lên server để xóa tài khoản này
function serverDeleteAccount(id) {
	let success = false;
	/* ========================================================= POST DELETE ACCOUNT HERE ================================================================== */

	let url = "https://love-and-care.herokuapp.com/tai-khoan/xoa-tai-khoan";
	$.ajax({
		url: url,
		data: {'id': id},
		type: 'post',
		cache: false,
		async: false,
		success: function(result) {
			success = result;
		},
		error: function() {
			displayAlertError("Xảy ra lỗi, thử lại...");
		}
	})
	return success;
}
//nút kiểm soát trạng thái hoạt động của tài khoản
function statusButton(id, status) {
	let buttonText = "";
	if (status) {
		buttonText = "<label class='switchVATI'><input type='checkbox' checked onchange='changeStatusAccountById(" + id + ")'/><span class='sliderVATI round'></span>";
	} else {
		buttonText = "<label class='switchVATI'><input type='checkbox' onchange='changeStatusAccountById(" + id + ")'/><span class='sliderVATI round'></span>";
	}
	return buttonText;
}
//thay đổi trạng thái hoạt động của tài khoản dựa vào mã id truyền vào
function changeStatusAccountById(id) {
	for (let i = 0; i < accountData.length; i++) {
		if (accountData[i].id == id) {
			if (serverChangeStatusAccount(id, accountData[i].status)) {
				accountData[i].status = !accountData[i].status;
				if(accountData[i].status) {
					displayAlertSuccess("Đã mở khóa cho tài khoản: " + accountData[i].firstName + " " + accountData[i].lastName)
				} else {
					displayAlertSuccess("Đã khóa hoạt động tài khoản: " + accountData[i].firstName + " " + accountData[i].lastName)
				}
			} else {
				displayAlertError("something wrong... :(<br>Can not change the status of this account!");
			}
			break;
		}
	}
	displayAccountForAccoutTableManage(START_ACC_POSITION);
}
//gửi id lên server để xử lý bật hoặc tát trạng thái hoạt động
function serverChangeStatusAccount(id, status) {
	let success = false;
	/* ================================================== POST DATA HERE -- CHANGE THE STATUS OF ACCOUNT ===================================================== */
	/* LƯU Ý ĐẢO NGƯỢC GIÁ TRỊ STATUS TRƯỚC KHI GỬI LÊN SERVER */

	let url = "https://love-and-care.herokuapp.com/tai-khoan/thay-doi-trang-thai";
	$.ajax({
		url: url,
		data: {'id': id, 'status': !status},
		type: 'post',
		cache: false,
		async: false,
		success: function(result) {
			success = result;
		},
		error: function() {
			displayAlertError("Xảy ra lỗi, thử lại...");
		}
	})
	return success;
}

//chọn button xóa các tài khoản đã chọn
function deleteChoosenAccount() {
	let num = 0;
	let success = false;
	if (displayAlertConfirm("Bạn có chắc chắn muốn xóa các tài khoản đã chọn khỏi cơ sở dữ liệu?")) {
		for (let i = 0; i < accountData.length; i++) {
			if (accountData[i].choose) {
				if (serverDeleteAccount(accountData[i].id)) {
					accountData.splice(i--, 1);
					num++;
					success = true;
				} else {
					displayAlertError(num + " account(s) has been deleted<br>There are something wrong with account has id: " + accountData[i].id);
					success = false
					break;
				}
			}
		}
	}
	if (success) {
		displayAlertSuccess("Xóa thành công " + num + " tài khoản.")
	}
	displayAccountForAccoutTableManage(START_ACC_POSITION);
}

//chọn button khóa kích hoạt các tài khoản đã chọn
function lockChoosenAccount() {
	let num = 0;
	let success = false;
	if (displayAlertConfirm("Bạn có chắc chắn muốn khóa các tài khoản đã chọn?")) {
		for (let account of accountData) {
			if (account.choose && account.status) {
				if (serverChangeStatusAccount(account.id, account.status)) {
					account.status = false;
					num++;
					success = true;
				} else {
					displayAlertError(num + " account(s) has been changed status<br>There are something wrong with account has id: " + account.id);
					success = false;
					break;
				}
			}
		}
	}
	if (success) {
		displayAlertSuccess("Khóa hoạt động thành công " + num + " tài khoản.");
	}
	displayAccountForAccoutTableManage(START_ACC_POSITION);
}

//khi click button mở khóa các tài khoản đã được chọn (checkbox)
function activeChoosenAccount() {
	let num = 0;
	let success = false;
	if (displayAlertConfirm("Các tài khoản được chọn sẽ được mở khóa.")) {
		for (let account of accountData) {
			if (account.choose && !account.status) {
				if (serverChangeStatusAccount(account.id, account.status)) {
					account.status = true;
					num++;
					success = true;
				} else {
					displayAlertError(num + " account(s) has been changed status<br>There are something wrong with account has id: " + account.id);
					success = false;
					break;
				}
			}
		}
	}
	if (success) {
		displayAlertSuccess("Mở khóa thành công " + num + " tài khoản.");
	}
	displayAccountForAccoutTableManage(START_ACC_POSITION);
}


/*function bufferToBase64(buf) {
    var binstr = Array.prototype.map.call(buf, function (ch) {
        return String.fromCharCode(ch);
    }).join('');
    return btoa(binstr);
}
*/
/*
function getImageSource(encoded, typeImg) {
	if(encoded == null) {
		return "<img class='rounded-circle' alt='avatar' src='https://love-and-care.herokuapp.com/static/media/user.png' width='50' height='50'>";
	}

//	let u8arr = new Uint8Array(arrByte);
//	let encoded = bufferToBase64(u8arr);
	
	return "<img src='data:" + typeImg + ";base64," + encoded + "' alt='avatar' width='50' height='50' class='rounded-circle'/>";
}*/
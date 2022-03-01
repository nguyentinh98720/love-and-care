let container;
let avatar;
let fullName;
let email;
let address;
let phoneNumber;
let editSelect;
let editAction;
let avatarInput;
let submitButton;
$(document).ready(function() {
    container = $("div#accountInfoContainer");
    avatar = $("div.imgSetVaTi");
    fullName = $("table>tbody>tr>td#fullName");
    email = $("table>tbody>tr>td#email");
    address = $("table>tbody>tr>td#address");
    phoneNumber = $("table>tbody>tr>td#phoneNumber");
    editSelect = $("div#editSelect");
    editAction = $("div#editAction");
    submitButton = $("form button:submit");
    avatarInput = $("input#avatar-upload");
    avatarInput.change(function() {
		$("p#updateAvatarWarning").prepend("<small class='text-primary'>" + avatarInput[0].files[0].name + "</small><br>");
		removeOldAvatar();
	})
	let firstNameInput = fullName.children("div").children("input:first-child");
	firstNameInput.focus(function() {
		clearWarning(firstNameInput);
	})
	let lastNameInput = fullName.children("div").children("input:last-child");
	lastNameInput.focus(function() {
		clearWarning(lastNameInput);
	})
	let addressInput = address.children("input");
	addressInput.focus(function() {
		clearWarning(addressInput);
	})
	let phoneNumberInput = phoneNumber.children("input");
	phoneNumberInput.focus(function() {
		clearWarning(phoneNumberInput);
	})
})
function editInformation() {
    container.slideUp(1000);
    setTimeout(function() {
        hideSomething([
            fullName.children("span"),
            email.children("span"),
            address.children("span"),
            phoneNumber.children("span"),
            editSelect
        ]);
        showSomething([
            avatar.children("div"),
            fullName.children("div"),
            email.children("input"),
            address.children("input"),
            phoneNumber.children("input"),
            editAction
        ]);
        container.fadeIn(2000);
    }, 1000);
}
function canceEdit() {
    container.fadeOut(1000);
    setTimeout(function() {
        hideSomething([
            avatar.children("div"),
            fullName.children("div"),
            email.children("input"),
            address.children("input"),
            phoneNumber.children("input"),
            editAction
        ]);
        showSomething([
            fullName.children("span"),
            email.children("span"),
            address.children("span"),
            phoneNumber.children("span"),
            editSelect
        ]);
        container.slideDown(2000);
    }, 1000);

}
function showSomething(elements) {
    elements.forEach((element) => {
        element.removeClass("d-none");
    })
}
function hideSomething(elements) {
    elements.forEach((element) => {
        element.addClass("d-none");
    })
}
function deleteAvatar() {
    if(confirm("Xác nhận xóa ảnh đại diện")) {
		$.post(
			"https://love-and-care.herokuapp.com/quan-ly/xoa-anh-dai-dien",
			function(result) {
				if(result == true) {
			        removeOldAvatar();
					alert("Đã xóa ảnh!\nHãy thêm ảnh đại diện mới để ai đó nhận ra bạn :)");
				} else {
			        alert("Xóa ảnh không thành công! Hãy thử lại sau..");
				}
			}
		);
    }
}
function removeOldAvatar() {
	avatar.children("i").remove();
	avatar.children("img").remove();
    avatar.children("div").children("button").remove();
    avatar.prepend(
		"<i class='bg-light text-success' style='display: inline-block; height: 200px; width: 150px;'>Ảnh đại diện</i>"
	);
	$("p#updateAvatarWarning").append("Hình ảnh sẽ được lưu lại sau khi cập nhật! Các thông tin được đồng bộ từ lần đăng nhập tiếp theo :v");
}
function verifyUpdateProfile() {
	let ok = (verifyTextLength(fullName.children("div").children("input:first-child"), 50, true) &&
		verifyTextLength(fullName.children("div").children("input:last-child"), 50, true) &&
		verifyTextLength(address.children("input"), 100, false) &&
		verifyTextLength(phoneNumber.children("input"), 15, false) &&
		checkFileSize());
	return ok;
}
function verifyTextLength(inputText, length, required) {
	if(inputText.val().length > length) {
		inputText.addClass("bg-danger text-light");
		editAction.children("span").text("Tối đa " + length + " ký tự cho trường màu đỏ");
		submitButton.attr("disabled", "disabled");
		return false;
	} else if(inputText.val() == "" && required) {
		inputText.addClass("bg-warning text-light");
		editAction.children("span").text("Hãy nhập đầy đủ thông tin trước khi cập nhật.");
		submitButton.attr("disabled", "disabled");
		return false;
	}
	return true;
}
function clearWarning(inputText) {
	inputText.removeClass("bg-danger bg-warning text-light");
	editAction.children("span").text("");
	submitButton.removeAttr("disabled");
}
function checkFileSize() {
	if(avatarInput != null) {
		if(avatarInput[0].files[0].size > 5*1024*1000) {
			editAction.children("span").text("Dung lượng ảnh tối đa là 5Mb");
			return false;
		}
	}
	return true;
}
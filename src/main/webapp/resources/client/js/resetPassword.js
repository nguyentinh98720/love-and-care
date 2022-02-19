let inputEmail = $("input#inputEmail");
let emailWarning = $("span#emailWarning");
let submitButton = $("input:submit");

$(document).ready(function() {
	inputEmail.focus(function() {
		clearWarning();
	})
})

function verifyEmail() {
	const emailRejex = /(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/;

	if(inputEmail.val().length == 0) {
		inputEmail.addClass("border-danger");
		emailWarning.text("Bạn đã đăng ký bằng email nào?!");
		emailWarning.show();
		return false;
	} else if(!emailRejex.test(inputEmail.val())) {
		inputEmail.addClass("border-danger");
		emailWarning.text("Email không hợp lệ");
		emailWarning.show();
		return false;
	}
	return true;
}

function clearWarning() {
	inputEmail.removeClass("border-danger");
	emailWarning.text("");
	emailWarning.hide();
	submitButton.removeAttr("disabled");
}

function verifyResetPassword() {
	submitButton.attr("disabled", "disabled");
	return verifyEmail();
}
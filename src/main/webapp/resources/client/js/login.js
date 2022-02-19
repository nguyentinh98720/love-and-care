let inputEmail = $("input#inputEmail");
let inputPassword = $("input#inputPassword");
let emailWarning = $("span#emailWarning");
let passwordWarning = $("span#passwordWarning");
let submitButton = $("input:submit");

$(document).ready(function() {
	inputEmail.blur(function() {
		verifyEmail();
	})
	inputEmail.focus(function() {
		clearWarning(inputEmail, emailWarning);
	})
	inputPassword.blur(function() {
		verifyPassword();
	})
	inputPassword.focus(function() {
		clearWarning(inputPassword, passwordWarning);
	})
})

function verifyEmail() {
	if(inputEmail.val().length == 0) {
		inputEmail.addClass("border-danger");
		emailWarning.text("Hãy nhập email của bạn");
		emailWarning.show();
		return false;
	} else if(!isValidEmail(inputEmail.val())) {
		inputEmail.addClass("border-danger");
		emailWarning.text("Email không hợp lệ");
		emailWarning.show();
		return false;
	}
	return true;
}

function verifyPassword() {
	if(inputPassword.val().length == 0) {
		inputPassword.addClass("border-danger");
		passwordWarning.text("Chưa nhập mật khẩu!");
		passwordWarning.show();
		return false;
	} else if(inputPassword.val().length < 6) {
		inputPassword.addClass("border-danger");
		passwordWarning.text("Kiểm tra lại mật khẩu!");
		passwordWarning.show();
		return false;
	}
	return true;
}
function clearWarning(input, warning) {
	input.removeClass("border-danger");
	warning.text("");
	warning.hide();
	submitButton.removeAttr("disabled");
}

function isValidEmail(email) {
	const emailRejex = /(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/;
	if(emailRejex.test(email)) {
		return true;
	} else {
		inputEmail.addClass("border-danger");
		emailWarning.text("Email không hợp lệ!");
		emailWarning.show();
		return false;
	}
}

function verifyLogin() {
	submitButton.attr("disabled", "disabled");
	return (verifyEmail() && verifyPassword());
}
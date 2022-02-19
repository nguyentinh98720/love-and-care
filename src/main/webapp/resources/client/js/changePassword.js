let inputOldPass = $("input#inputOldPass");
let inputNewPass = $("input#inputNewPass");
let inputRepeatPass = $("input#inputRepeatPass");
let oldPassWarning = $("span#oldPassWarning");
let newPassWarning = $("span#newPassWarning");
let repeatPassWarning = $("span#repeatPassWarning");
let submitButton = $("input:submit");

$(document).ready(function() {
	inputOldPass.blur(function() {
		verifyOldPass();
	})
	inputOldPass.focus(function() {
		clearWarning(inputOldPass, oldPassWarning);
	})
	inputNewPass.blur(function() {
		verifyNewPass();
	})
	inputNewPass.focus(function() {
		clearWarning(inputNewPass, newPassWarning);
	})
	inputRepeatPass.blur(function() {
		verifyRepeatPass();
	})
	inputRepeatPass.focus(function() {
		clearWarning(inputRepeatPass, repeatPassWarning);
	})
	inputRepeatPass.keyup(function() {
		verifyRepeatPassWhenKeyUp();
	})
})

function verifyOldPass() {
	if(inputOldPass.val().length == 0) {
		oldPassWarning.text("Bạn cần nhập trường này!");
		oldPassWarning.show();
		inputOldPass.addClass("border-danger");
		return false;
	} else if(inputOldPass.val().length < 6) {
		oldPassWarning.text("Kiểm tra lại mật khẩu!");
		oldPassWarning.show();
		inputOldPass.addClass("border-danger");
		return false;
	}
	return true;
}

function verifyNewPass() {
	if(inputOldPass.val().length == 0) {
		return false;
	} else if(inputNewPass.val().length == 0) {
		newPassWarning.text("Bạn cần nhập trường này!");
		newPassWarning.show();
		inputNewPass.addClass("border-danger");
		return false;
	} else if(inputNewPass.val().length < 8) {
		newPassWarning.text("Hãy đặt mật khẩu trên 8 ký tự!");
		newPassWarning.show();
		inputNewPass.addClass("border-danger");
		return false;
	}
	return true;
}

function verifyRepeatPass() {
	if(!verifyNewPass()) {
		return false;
	} else if(inputRepeatPass.val().length == 0) {
		repeatPassWarning.text("Hãy nhắc lại mật khẩu mới của bạn!");
		repeatPassWarning.show();
		inputRepeatPass.addClass("border-danger");
		return false;
	} else if(inputRepeatPass.val() != inputNewPass.val()) {
		repeatPassWarning.text("Không trùng khớp!");
		repeatPassWarning.show();
		inputRepeatPass.addClass("border-danger");
		return false;
	}
	return true;
}

function verifyRepeatPassWhenKeyUp() {
	if(inputRepeatPass.val() != inputNewPass.val()) {
		inputRepeatPass.addClass("border-danger");
	} else {
		inputRepeatPass.removeClass("border-danger");
		inputRepeatPass.addClass("border-success");
	}
}

function clearWarning(input, warning) {
	input.removeClass("border-danger border-success");
	warning.text("");
	warning.hide();
	submitButton.removeAttr("disabled");
}

function verifyChagePassword() {
	submitButton.attr("disabled", "disabled");
	return (verifyOldPass() && verifyNewPass() && verifyRepeatPass());
}
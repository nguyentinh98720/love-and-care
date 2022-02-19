	let inputFirstName = $("input#inputFirstName");
	let inputLastName = $("input#inputLastName");
	let inputEmail = $("input#inputEmail");
	let inputPhoneNumber = $("input#inputPhoneNumber");
	let inputAddress = $("input#inputAddress");
	let firstNameWarning = $("span#firstNameWarning");
	let lastNameWarning = $("span#lastNameWarning");
	let emailWarning = $("span#emailWarning");
	let phoneNumberWarning = $("span#phoneNumberWarning");
	let addressWarning = $("span#addressWarning");
	let isNewEmailForRegister = true;
	let submitButton = $("input:submit");
	
    let province = $("input#getProvince");
    let district = $("input#getDistrict");
    let wards = $("input#getWards");

$(document).ready(function() {
	
	getAddressListFromAPI();
	
	inputFirstName.blur(function() {
		firstNameVerify();
	})
	inputFirstName.focus(function() {
		clearWarning(inputFirstName, firstNameWarning);
	})
	inputLastName.blur(function() {
		lastNameVerify();
	})
	inputLastName.focus(function() {
		clearWarning(inputLastName, lastNameWarning);
	})
	inputEmail.blur(function() {
		emailVerify();
		isNewEmail(inputEmail.val());
	})
	inputEmail.focus(function() {
		clearWarning(inputEmail, emailWarning);
	})
	inputPhoneNumber.blur(function() {
		phoneNumberVerify();
	})
	inputPhoneNumber.focus(function() {
		clearWarning(inputPhoneNumber, phoneNumberWarning);
	})
	submitButton.mouseenter(function() {
		firstNameVerify();
		emailVerify();
	})

	province.focus(function() {
		clearWarning(inputAddress, addressWarning);
	})
	district.focus(function() {
		clearWarning(inputAddress, addressWarning);
	})
	wards.focus(function() {
		clearWarning(inputAddress, addressWarning);
	})
})

function getAddressListFromAPI() {
    let provinceData = [];
    let districtData = [];
    let wardsData = [];

    let provinceList = $("datalist#province");
    let districtList = $("datalist#district");
    let wardsList = $("datalist#wards");

    $.get(
        "https://provinces.open-api.vn/api/?depth=1",
        function(data) {
            provinceData = data;
            provinceData.forEach((prov) => {
                provinceList.append("<option value='" + prov.name + "'>");
            });
        }
    );

    let provInput = "";

    province.change(function() {
        district.val("");
        wards.val("");
        districtList.empty();
        wardsList.empty();
        provInput = province.val();
        let provInputCode = 0;
        provinceData.forEach((prov) => {
            if(provInput == prov.name) {
                provInputCode = prov.code;
            }
        })
        if(provInputCode != 0) {
            $.get(
                "https://provinces.open-api.vn/api/p/" + provInputCode + "/?depth=2",
                function(data) {
                    districtData = data.districts;
                    districtData.forEach((dist) => {
                        districtList.append("<option value='" + dist.name + "'>");
                    })
                 }
            )
        }

		resultAddressFromInput();
    })

    let distInput = "";

    district.change(function() {
        distInput = district.val();
        wards.val("");
        wardsList.empty();
		let distInputCode = 0;
        districtData.forEach((dist) => {
            if(distInput == dist.name) {
                distInputCode = dist.code;
            }
        })
        if(distInputCode != 0) {
            $.get(
                "https://provinces.open-api.vn/api/d/" + distInputCode + "/?depth=2",
                function(data) {
                    wardsData = data.wards;
                    wardsData.forEach((ward) => {
                        wardsList.append("<option value='" + ward.name + "'>");
                    })
                }
            );
        }

		resultAddressFromInput();
    })

	wards.blur(function() {
		resultAddressFromInput();
	})
}

function resultAddressFromInput() {
	let provinceValue = province.val();
	let districtValue = district.val();
	let wardsValue = wards.val();
	let result = ((provinceValue == "")? "" : provinceValue) + ((districtValue == "")? "" : (" - " + districtValue)) + ((wardsValue == "")? "" : (" - " + wardsValue));
	inputAddress.val(result);
	console.log(result);
}

function firstNameVerify() {
	if(inputFirstName.val() == "") {
		firstNameWarning.text("Hãy nhập tên của bạn!");
		firstNameWarning.show();
		inputFirstName.addClass("border-danger");
		return false;
	} else if(inputFirstName.val().length > 50) {
		firstNameWarning.text("Chỉ chấp nhận tên có độ dài dưỡi 50 ký tự!");
		firstNameWarning.show();
		inputFirstName.addClass("border-danger");
		return false;
	}
	return true;
}
function lastNameVerify() {
	if(inputLastName.val().length > 100) {
		lastNameWarning.text("Chỉ chấp nhận họ và tên đệm có độ dài dưỡi 100 ký tự!");
		lastNameWarning.show();
		inputLastName.addClass("border-danger");
		return false;
	}
	return true;
}
function emailVerify() {
	if(inputEmail.val() == "") {
		emailWarning.text("Hãy nhập email của bạn!");
		emailWarning.show();
		inputEmail.addClass("border-danger");
		return false;
	} else if(inputEmail.val().length > 100) {
		emailWarning.text("Chỉ chấp nhận tên có độ dài dưỡi 100 ký tự!");
		emailWarning.show();
		inputEmail.addClass("border-danger");
		return false;
	} else if(!isValidEmail(inputEmail.val())) {
		return false;
	}
	return true;
}
function phoneNumberVerify() {
	if(inputPhoneNumber.val().length > 15) {
		phoneNumberWarning.text("Số điện thoại không hợp lệ");
		phoneNumberWarning.show();
		inputPhoneNumber.addClass("border-danger");
		return false;
	}
	return true;
}
function addressVerify() {
	if(inputAddress.val().length > 100) {
		addressWarning.text("Hãy rút ngắn địa chỉ của bạn dưới 100 ký tự!");
		addressWarning.show();
		inputAddress.addClass("border-danger");
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

function verifyForm() {
	submitButton.attr("disabled", "disabled");
	return (firstNameVerify() && lastNameVerify() && emailVerify() && phoneNumberVerify() && addressVerify()) && isNewEmail(isNewEmailForRegister);
}

function isNewEmail(email) {
	let url = "https://love-and-care.herokuapp.com/greeting/kiem-tra-email";
	$.ajax({
		url: url,
		data: {'email': email},
		type: 'post',
		success: function(result) {
			repeatVerifyEmail(result);
			isNewEmailForRegister = result;
		},
		error: function() {
			alert("Something wrong when verify email!");
		}
	})
}
function repeatVerifyEmail(isNew) {
	if(!isNew) {
		inputEmail.addClass("border-danger");
		emailWarning.text("Email này đã có tài khoản, Bạn quên mật khẩu?");
		emailWarning.show();
	}
	return isNew;
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
let inputEmail;
let isNewEmail = true;
/*
let inputPassword;
let inputRepeatPassword = $("input#repeatPasswordForCreateAccout");
*/
let inputFirstName;
let inputLastName;
	let province;
	let district;
	let wards;
let inputAddress;
let inputPhoneNumber;
let emailWarning = $("span#verifyEmail");
/*
let passwordWarning = $("span#verifyPassword");
let repeatPasswordWarning = $("span#verifyRepeatPassword");
*/
let firstNameWarning = $("span#verifyFirstName");
let lastNameWarning = $("span#verifyLastName");
let addressWarning = $("span#verifyAddress");
let phoneNumberWarning = $("span#verifyPhoneNumber");
let namePage = $("input#namePage").val();
$(document).ready(function() {
    if(namePage == "createAccount") {
        inputEmail = $("input#emailForCreateAccout");
/*
        inputPassword = $("input#passwordForCreateAccout");
*/
        inputFirstName = $("input#firstNameForCreateAccount");
        inputLastName = $("input#lastNameForCreateAccount");
        inputAddress = $("input#addressForCreateAccount");
        inputPhoneNumber = $("input#phoneNumberForCreateAccount");
		province = $("input#getProvince");
		district = $("input#getDistrict");
		wards = $("input#getWards");
    } else if(namePage == "editExistAccount") {
        inputEmail = $("input#emailForEditAccout");
        inputPassword = $("input#passwordForEditAccout");
        inputFirstName = $("input#firstNameForEditAccount");
        inputLastName = $("input#lastNameForEditAccount");
        inputAddress = $("input#addressForEditAccount");
        inputPhoneNumber = $("input#phoneNumberForEditAccount");
    }
    inputEmail.blur(function() {
        forEmail();
    })
    inputEmail.focus(function() {
        clearFieldWarning(inputEmail, emailWarning);
    })
/*
    inputPassword.blur(function() {
        forPassword();
    })
    inputPassword.focus(function() {
        clearFieldWarning(inputPassword, passwordWarning);
    })
    inputRepeatPassword.blur(function() {
        forRepeatPassword();
    })
    inputRepeatPassword.focus(function() {
        clearFieldWarning(inputRepeatPassword, repeatPasswordWarning);
    })
*/
    inputFirstName.blur(function() {
        forName(firstNameWarning, inputFirstName);
    })
    inputFirstName.focus(function() {
        clearFieldWarning(inputFirstName, firstNameWarning);
    })
    inputLastName.blur(function() {
        forName(lastNameWarning, inputLastName);
    })
    inputLastName.focus(function() {
        clearFieldWarning(inputLastName, lastNameWarning);
    })
    inputAddress.blur(function() {
        forAddress();
    })
    inputAddress.focus(function() {
        clearFieldWarning(inputAddress, addressWarning);
    })
    inputPhoneNumber.blur(function() {
        forPhoneNumber();
    })
    inputPhoneNumber.focus(function() {
        clearFieldWarning(inputPhoneNumber, phoneNumberWarning);
    })
	$("input#lastStepWarning").mouseenter(function() {
		forEmail();
        forName(lastNameWarning, inputLastName);
        forName(firstNameWarning, inputFirstName);
        forAddress();
        forPhoneNumber();
	})
	
	if(typeof province !== 'undefined') {
		addressListFromAPI();
	}
})
function forPhoneNumber() {
    if(inputPhoneNumber.val().length > 15) {
        inputPhoneNumber.addClass("border-danger");
        phoneNumberWarning.text("Sorry, only accept < 15 characters! :)");
        return false;
    } else if(inputPhoneNumber.val() == "") {
        inputPhoneNumber.addClass("border-warning");
        phoneNumberWarning.text("Số điện thoại đang trống!");
    }
    return true;
}
function forAddress() {
    if(inputAddress.val().length > 100) {
        inputAddress.addClass("border-danger");
        addressWarning.text("Xin lỗi, hãy cắt ngắn địa chỉ của bạn. Chỉ chấp nhận dưới 100 ký tự!");
        return false;
    } else if(inputAddress.val() == "") {
        inputAddress.addClass("border-warning");
        addressWarning.text("Địa chỉ đang trống");
    }
    return true;
}
function forName(nameWarning, inputName) {
    if(inputName.val() == "") {
        nameWarning.text("Trường đang trống.");
        inputName.addClass("border-warning");
    }
    if(inputName.val().length > 50) {
        nameWarning.text("Chỉ chấp nhận tên dưới 50 ký tự");
        inputName.addClass("border-danger");
        return false;
    }
    return true;
}
/*
function forRepeatPassword() {
    if(inputRepeatPassword.val() == "" || inputRepeatPassword.val() == null) {
        repeatPasswordWarning.text("Hãy nhập lại mật khẩu!");
        inputRepeatPassword.addClass("border-danger");
        return false;
    } else if(inputPassword.val() != inputRepeatPassword.val()) {
        repeatPasswordWarning.text("Không trùng khớp!!");
        inputRepeatPassword.addClass("border-danger");
        return false;
    }
    return true;
}
*/
function forEmail() {
	let accept = false;
	let email = inputEmail.val();
    if(email == "" || email == null) {
        emailWarning.text("Trường email không được để trống");
        inputEmail.addClass("border-danger");
        return false;
    } else if(email.length > 100) {
        emailWarning.text("Xin lỗi, chỉ chấp nhận email có độ dài dưới 101 ký tự");
        inputEmail.addClass("border-danger");
        return false;
    }
	if(namePage == 'createAccount') {
		serverCheckExistEmailInDatabase(email);
	}
	return true;
}
function repeatWarning(isNew) {
	if(!isNew) {
        emailWarning.text("Email đã tồn tại");
        inputEmail.addClass("border-danger");
		return false;
	}
	return true;
}
/*
function forPassword() {
    if(inputPassword.val() == "" || inputPassword.val() == null) {
        passwordWarning.text("Hãy nhập mật khẩu");
        inputPassword.addClass("border-danger");
        return false;
    } else if(inputPassword.val().length < 8) {
        passwordWarning.text("Mật khẩu phải từ 8 ký tự trở lên!");
        inputPassword.addClass("border-danger");
        return false;
    }
    return true;
}
*/
function clearFieldWarning(inputWarning, spanWarning) {
    inputWarning.removeClass("border-danger border-warning");
    spanWarning.text("");
}
function creatNewAccount() {
    return forEmail() && /*forPassword() && forRepeatPassword() &&*/ forAddress() && forPhoneNumber() && forName(firstNameWarning, inputFirstName) && forName(lastNameWarning, inputLastName) && repeatWarning(isNewEmail);
}
function editExistAccount() {
    return /*forEmail() && forPassword() &&*/ forAddress() && forPhoneNumber() && forName(firstNameWarning, inputFirstName) && forName(lastNameWarning, inputLastName);
}
function serverCheckExistEmailInDatabase(email) {
	let url = "https://love-and-care.herokuapp.com/tai-khoan/kiem-tra-email";
	$.ajax({
		url: url,
		data: {'email': email},
		method: 'post',
		cache: false,
		success: function(result) {
			isNewEmail = result;
			repeatWarning(isNewEmail);
		}
	})
}

function resultAddressFromInput() {
	let provinceValue = province.val();
	let districtValue = district.val();
	let wardsValue = wards.val();
	let result = ((provinceValue == "")? "" : provinceValue) + ((districtValue == "")? "" : (" - " + districtValue)) + ((wardsValue == "")? "" : (" - " + wardsValue));
	inputAddress.val(result);
}

function addressListFromAPI() {
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
	
	province.focus(function() {
		clearFieldWarning(inputAddress, addressWarning);
	})
	district.focus(function() {
		clearFieldWarning(inputAddress, addressWarning);
	})
	wards.focus(function() {
		clearFieldWarning(inputAddress, addressWarning);
	})
}
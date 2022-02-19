let money;
let message;
let warning;
let reader;
let formatMoney;
let testDonate;
let paypalDonate;

$(document).ready(function () {
	formatMoney = new Intl.NumberFormat("vi-VN", {style: "currency", currency:"VND"});
	money = $("input#donateMoney"); 
	message = $("textarea#message");
	warning = $("p#warningDonate"); 
	
	testDonate = $("input:radio#test");
	paypalDonate = $("input:radio#paypal");

    reader = $("strong#readMoney");
    whenTestDonate();
    
    testDonate.click(function() {
		whenTestDonate();
	})
	
	paypalDonate.click(function() {
		whenPaypalDonate();
	})
    message.focus(function() {
        clearWarning();
    })
})

function whenTestDonate() {
    money.change(function () {
        reader.text(DocRaSoTien(money.val()));
    })
    money.keyup(function () {
        reader.text(DocRaSoTien(money.val()));
    })
    money.focus(function() {
        clearWarning();
    })
}

function whenPaypalDonate() {
	money.off("change");
	money.off("keyup");
	showExchangeRates();
}

function showExchangeRates() {
	$.get("https://love-and-care.herokuapp.com/quyen-gop/lay-khoa-api-ti-gia-tien-te", function(key) {
		getExchangeRates(key);
	});
}

function getExchangeRates(key) {
	let usd = 0;
	let vnd = 0;
	let url = "https://api.exchangeratesapi.io/v1/latest?access_key=" + key + "&symbols=VND,USD";
	$.ajax({
		url: url,
		type: 'get',
		success: function(data) {
			let rate = 0;
			let time = "";
			if(data.success == true) {
				usd = data.rates.USD;
				vnd = data.rates.VND;
				rate = Math.round(vnd/usd);
				time = data.date;
				saveLastestExchangeRateToServer(rate, time);
			} else {
				getLastestExchangeRateUpdateFromServer();
			}
		},
		error: function(err) {
			console.log(err);
			getLastestExchangeRateUpdateFromServer();
		}
	})
}

function saveLastestExchangeRateToServer(number, time) {
	let url = "https://love-and-care.herokuapp.com/quyen-gop/ti-gia-tien-te?action=set";
	$.post(url, {'rate': number, 'time': time});
	showCurrencyRateInfo(number, time);
}

function getLastestExchangeRateUpdateFromServer() {
	let url = "https://love-and-care.herokuapp.com/quyen-gop/ti-gia-tien-te?action=get";
	$.get(url, function(data) {
		if(data.rate > 0 && data.lastTimeUpdate != "empty") {
			showCurrencyRateInfo(data.rate, data.lastTimeUpdate);
		} else {
			$.get("https://love-and-care.herokuapp.com/quyen-gop/con-cach-nay-thoi", function(data) {
				if(data == null) {alright()}
				else {
					showCurrencyRateInfo(data.rate, data.lastTimeUpdate);
					saveLastestExchangeRateToServer(data.rate, data.lastTimeUpdate);
				}
			})
		}
	});
}

function showCurrencyRateInfo(rate, time) {
	reader.html("Quyên góp với mã tiền tệ là USD. Tỉ giá hiện tại là:<span class='badge bg-warning text-danger'> 1USD = " +
	formatMoney.format(rate) + "</span> cập nhập gần đây nhất: " + new Date(time).toLocaleDateString("vi-VN"));
}

function alright() {
	let timeTemp = 22830;
	reader.html("Quyên góp với mã tiền tệ là USD. Đã xảy ra lỗi và chúng tôi không thể cập nhật tỉ giá ngay bây giờ" +
	". Tỉ giá được tạm tính là:<span class='badge bg-warning text-danger'> 1USD = " +
	timeTemp + "</span> cập nhập gần đây nhất: 18/02/2022");
	saveLastestExchangeRateToServer(timeTemp, "empty");
}

function verifyDonate() {
	if(paypalDonate.is(":checked")) {
		return verifyInputMessage();
	}
    return verifyInputMoney() && verifyInputMessage();
}
function verifyInputMoney() {
    if(money.val() < 1000) {
        warning.text("Chỉ chấp nhận số tiền là bội của 1000");
        return false;
    }
    if(money.val() % 1000 != 0) {
        warning.text("Chỉ chấp nhận số tiền là bội của 1000");
		return false;
	}
    if(money.val() > 100000000) {
        warning.text("Số tiền quyên góp tối đa mỗi lượt là 100 triệu đồng");
        return false;
    }
    return true;
}

function verifyInputMessage() {
    if(message.val().length > 200) {
        warning.text("Số ký tự tối đa cho lời nhắn là 200 ký tự!");
        return false;
    }
    return true;
}

function clearWarning() {
    warning.text("");
}

var ChuSo = new Array(" không ", " một ", " hai ", " ba ", " bốn ", " năm ", " sáu ", " bảy ", " tám ", " chín ");
var Tien = new Array("", " nghìn", " triệu", " tỷ", " nghìn tỷ", " triệu tỷ");

//1. Hàm đọc số có ba chữ số;
function DocSo3ChuSo(baso) {
    var tram;
    var chuc;
    var donvi;
    var KetQua = "";
    tram = parseInt(baso / 100);
    chuc = parseInt((baso % 100) / 10);
    donvi = baso % 10;
    if (tram == 0 && chuc == 0 && donvi == 0) return "";
    if (tram != 0) {
        KetQua += ChuSo[tram] + " trăm ";
        if ((chuc == 0) && (donvi != 0)) KetQua += " linh ";
    }
    if ((chuc != 0) && (chuc != 1)) {
        KetQua += ChuSo[chuc] + " mươi";
        if ((chuc == 0) && (donvi != 0)) KetQua = KetQua + " linh ";
    }
    if (chuc == 1) KetQua += " mười ";
    switch (donvi) {
        case 1:
            if ((chuc != 0) && (chuc != 1)) { KetQua += " mốt "; }
            else { KetQua += ChuSo[donvi]; }
            break;
        case 5:
            if (chuc == 0) { KetQua += ChuSo[donvi]; }
            else { KetQua += " lăm "; }
            break;
        default:
            if (donvi != 0) { KetQua += ChuSo[donvi]; }
            break;
    }
    return KetQua;
}
//2. Hàm đọc số thành chữ (Sử dụng hàm đọc số có ba chữ số)
function DocRaSoTien(SoTien) {
    var lan = 0;
    var i = 0;
    var so = 0;
    var KetQua = "";
    var tmp = "";
    var ViTri = new Array();
    if (SoTien < 0)  return "Số tiền âm !";
    if (SoTien > 0) {
        so = SoTien;
    } else {
        so = -SoTien;
    }
    if (SoTien > 100000000) {
        //SoTien = 0;
        return "Lưu ý: Tối đa là 100 triệu đồng";
    }
    if (SoTien < 1000) {
        return "";
    }
    if (SoTien % 1000 != 0) {
        return "Lưu ý: Số tiền phải là bội của 1000";
    }
    ViTri[5] = Math.floor(so / 1000000000000000);
    if (isNaN(ViTri[5]))
        ViTri[5] = "0";
    so = so - parseFloat(ViTri[5].toString()) * 1000000000000000;
    ViTri[4] = Math.floor(so / 1000000000000);
    if (isNaN(ViTri[4]))
        ViTri[4] = "0";
    so = so - parseFloat(ViTri[4].toString()) * 1000000000000;
    ViTri[3] = Math.floor(so / 1000000000);
    if (isNaN(ViTri[3]))
        ViTri[3] = "0";
    so = so - parseFloat(ViTri[3].toString()) * 1000000000;
    ViTri[2] = Math.floor(so / 1000000);
    if (isNaN(ViTri[2]))
        ViTri[2] = "0";
    ViTri[1] = parseInt((so % 1000000) / 1000);
    if (isNaN(ViTri[1]))
        ViTri[1] = "0";
    ViTri[0] = parseInt(so % 1000);
    if (isNaN(ViTri[0]))
        ViTri[0] = "0";
    if (ViTri[5] > 0) {
        lan = 5;
    } else if (ViTri[4] > 0) {
        lan = 4;
    } else if (ViTri[3] > 0) {
        lan = 3;
    } else if (ViTri[2] > 0) {
        lan = 2;
    } else if (ViTri[1] > 0) {
        lan = 1;
    } else {
        lan = 0;
    }
    for (i = lan; i >= 0; i--) {
        tmp = DocSo3ChuSo(ViTri[i]);
        KetQua += tmp;
        if (ViTri[i] > 0) KetQua += Tien[i];
        if ((i > 0) && (tmp.length > 0)) KetQua += ',';//&& (!string.IsNullOrEmpty(tmp))
    }
    if (KetQua.substring(KetQua.length - 1) == ',') {
        KetQua = KetQua.substring(0, KetQua.length - 1);
    }
    KetQua = KetQua.substring(1, 2).toUpperCase() + KetQua.substring(2);
    return KetQua + " đồng.";
}
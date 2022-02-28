let test = false;
let result = false;

$(document).ready(function() {
    let box = $("div#VATIAlert");
    $("div#VATIAlert>div>button").click(function() {
        box.fadeOut();
        result = true;
        test = true;
    })
    $("div#VATIAlert>div>span").click(function() {
        box.fadeOut();
        result = false;
        test = true;
    })
})


//Hộp xác nhận thao tác
function displayAlertConfirm(message) {
    return confirm(message);
/* ========== no complete ================ 1:54 am 17/01/2022 ==================== */
    // return displayVatiAlert(message, "confirm");
}    
//Hiển thị hộp thoại thông báo thành công
function displayAlertSuccess(message) {
    alert(message);
    // displayVatiAlert(message, "success");
}    
//Hiển thị hộp thoại thông báo lỗi
function displayAlertError(message) {
    alert(message);
    // displayVatiAlert(message, "error");
}    

function displayVatiAlert(message, choose) {
    const alertbox = $("div#VATIAlert");
    const OK = $("div#VATIAlert>div>button");
    const CANCE = $("div#VATIAlert>div>span");

    alertbox.removeClass("alert-danger alert-success alert-warning");
    alertbox.children("p").html(message);
    
    if(choose == "confirm") {
        OK.show();
        CANCE.show();
        alertbox.addClass("alert-info");
        alertbox.slideDown();
        return showQuestion();
    } else if(choose == "success") {
        OK.hide();
        CANCE.hide();
        alertbox.addClass("alert-success");
        alertbox.slideDown();
        setTimeout(function() {
            alertbox.slideUp();
        }, 2000);
        return false;
    } else if(choose == "error") {
        OK.show();
        CANCE.hide();
        alertbox.addClass("alert-danger");
        alertbox.slideDown();
        return false;
    }
}

function reloadPage() {
	location.reload();
}
let inputDetail;
$(document).ready(function() {
    let pageName = $("input#pageName").val();
    
    let titleWarning = $("span#verifyTitle");
    let inputTitle;
    let startDateWarning = $("span#verifyStartDate");
    let inputStartDate;
    let endDateWarning = $("span#verifyEndDate");
    let inputEndDate;
    let purposeWarning = $("span#verifyPurpose");
    let inputPurpose;
    
    let readingPurpose = $("h6#readingPurpose");
//    let detailWarning = $("span#verifyDetail");
    
    if(pageName == "createEvent") {
        inputTitle = $("textarea#titleForNewEvent");
        inputStartDate = $("input#startDateForNewEvent");
        inputEndDate = $("input#endDateForNewEvent");
        inputPurpose = $("input#purposeForNewEvent");
        inputDetail = $("textarea#detailForNewEvent");
    } else if(pageName == "editEvent") {
        inputTitle = $("textarea#titleForEdit");
        inputStartDate = $("input#startDateForEdit");
        inputEndDate = $("input#endDateForEdit");
        inputPurpose = $("input#purposeForEdit");
        inputDetail = $("textarea#detailForEdit");
        $("h6#readingAchievement").text(readMoney(
			$("input#achievementNotEdit").val()
		));
    }
    
    inputTitle.blur(function() {
        forTitleWarning(inputTitle, titleWarning);
    })
    inputTitle.focus(function() {
        titleWarning.text("");
        inputTitle.removeClass("border-danger");
    })
    inputStartDate.blur(function() {
        forStartDateWarning(inputStartDate, startDateWarning);
    })
    inputStartDate.focus(function() {
        startDateWarning.text("");
        inputStartDate.removeClass("border-danger border-warning");
    })
    inputEndDate.blur(function() {
        forEndDateWarning(inputEndDate, endDateWarning, inputStartDate);
    })
    inputEndDate.focus(function() {
        if(inputStartDate.val() == "" || inputStartDate.val() == null) {
            startDateWarning.text("Chưa nhập ngày bắt đầu!");
            inputStartDate.addClass("border-danger");
        }
        endDateWarning.text("");
        inputEndDate.removeClass("border-danger border-warning");
    })
    inputPurpose.blur(function() {
        forPurposeWarning(inputPurpose, purposeWarning);
    })
    inputPurpose.focus(function() {
        purposeWarning.text("");
        inputPurpose.removeClass("border-danger");
    })
	readingPurpose.text(readMoney(inputPurpose.val()));	
    inputPurpose.keyup(function() {
		readingPurpose.text(readMoney(inputPurpose.val()));	
	})
    inputPurpose.change(function() {
		readingPurpose.text(readMoney(inputPurpose.val()));	
	})
/*
    inputDetail.blur(function() {
        forDetailWarning(inputDetail, detailWarning);
    })
    inputDetail.focus(function() {
        detailWarning.text("");
        inputDetail.removeClass("border-danger border-warning");
    })
*/
	$("input#lastStepWarning").mouseenter(function() {
//        forDetailWarning(inputDetail, detailWarning);
        forPurposeWarning(inputPurpose, purposeWarning);
        forTitleWarning(inputTitle, titleWarning);
        forStartDateWarning(inputStartDate, startDateWarning);
        forEndDateWarning(inputEndDate, endDateWarning, inputStartDate);
	})
})
function editExistEvent() {
    let titleWarning = $("span#verifyTitle");
    let inputTitle = $("textarea#titleForEdit");

    let startDateWarning = $("span#verifyStartDate");
    let inputStartDate = $("input#startDateForEdit");
    
    let endDateWarning = $("span#verifyEndDate");
    let inputEndDate = $("input#endDateForEdit");
    
    let purposeWarning = $("span#verifyPurpose");
    let inputPurpose = $("input#purposeForEdit");
/*    
    let detailWarning = $("span#verifyDetail");
    let inputDetail = $("textarea#detailForEdit");
*/
    return forTitleWarning(inputTitle, titleWarning) && forStartDateWarning(inputStartDate, startDateWarning) && forEndDateWarning(inputEndDate, endDateWarning) && forPurposeWarning(inputPurpose, purposeWarning) /*&& forDetailWarning(inputDetail, detailWarning)*/;
}
function creatNewEvent() {
    let titleWarning = $("span#verifyTitle");
    let inputTitle = $("textarea#titleForNewEvent");
    
    let startDateWarning = $("span#verifyStartDate");
    let inputStartDate = $("input#startDateForNewEvent");
    
    let endDateWarning = $("span#verifyEndDate");
    let inputEndDate = $("input#endDateForNewEvent");
    
    let purposeWarning = $("span#verifyPurpose");
    let inputPurpose = $("input#purposeForNewEvent");
/*    
    let detailWarning = $("span#verifyDetail");
    let inputDetail = $("textarea#detailForNewEvent");
*/
    return forTitleWarning(inputTitle, titleWarning) && forStartDateWarning(inputStartDate, startDateWarning) && forEndDateWarning(inputEndDate, endDateWarning) && forPurposeWarning(inputPurpose, purposeWarning) /*&& forDetailWarning(inputDetail, detailWarning)*/;
}
function forTitleWarning(inputTitle, titleWarning) {
    if(inputTitle.val() == "" || inputTitle.val() == null) {
        titleWarning.text("Tiêu đề chương trình không được để trống!");
        inputTitle.addClass("border-danger");
        return false;
    } else if(inputTitle.val().length > 200) {
        titleWarning.text("Mục tiêu đề không được vượt quá 200 ký tự!");
        inputTitle.addClass("border-danger");
        return false;
    } else {
        return true;
    }
}
function forStartDateWarning(inputStartDate, startDateWarning) {
    if(inputStartDate.val() == "" || inputStartDate.val() == null) {
        startDateWarning.text("Ngày bắt đầu không được để trống");
        inputStartDate.addClass("border-danger");
        return false;
    } else {
        let timeNow = new Date(new Date().getTime() - 86400000);
        let timeEvent = new Date(inputStartDate.val());
        if(timeNow > timeEvent) {
            startDateWarning.text("Ngày bắt đầu có vẻ trước ngày hiện tại!");
            inputStartDate.addClass("border-warning");
        }
        return true;
    }
}
function forEndDateWarning(inputEndDate, endDateWarning, inputStartDate) {
    if(inputEndDate.val() == "" || inputEndDate.val() == null) {
        inputEndDate.addClass("border-warning");
        return true;
    } else {
        let timeStart = new Date(inputStartDate.val());
        let timeEnd = new Date (inputEndDate.val());
        if(timeStart > timeEnd) {
            endDateWarning.text("Ngày kết thúc không hợp lệ!!");
            inputEndDate.addClass("border-danger");
            return false;
        } else {
            return true;
        }
    }
}
function forPurposeWarning(inputPurpose, purposeWarning) {
    if(inputPurpose.val() == "" || inputPurpose.val() == null) {
        purposeWarning.text("Bạn cần xác định số tiền cần quyên góp!!");
        inputPurpose.addClass("border-danger");
        return false;
    } else {
        return true;
    }
}
/*
function forDetailWarning(inputDetail, detailWarning) {
    if(inputDetail.val().length > 8000) {
        detailWarning.text("Nội dung chương trình không quá 8000 từ.");
        inputDetail.addClass("border-danger");
        return false;
    } else if(inputDetail.val() == "") {
        detailWarning.text("Chưa có nội dung chương trình");
        inputDetail.addClass("border-warning");
        return true;
    } else {
        return true;
    }
}*/

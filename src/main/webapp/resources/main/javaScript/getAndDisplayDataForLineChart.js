let START = 0;
let NUM_PER_PAGE = 5;
let TOTAL = 0;
const LENGTH_STATISTICS = 10; // total days
let dataForDashBoard = [];

let row1 = $("tr#row1");
let row2 = $("tr#row2");
let row3 = $("tr#row3");
let row4 = $("tr#row4");
let row5 = $("tr#row5");

let idDropdown = $("div#eventIdDropdown");
let linkEdit = $("a#eventEditDropdown");
let linkDetail = $("a#eventDetailDropdown");
let stopAction = $("span#stopActionDashboard");

let currentIdSelectEvent = 0;

$(document).ready(function() {

    //Truy cập, nhận thông tin và hiển thị danh sách mặc định là các sự kiện mới nhất
    getDataForListInDashBoard();

    $("li#prev").click(function() {
        from(START - NUM_PER_PAGE);
    })
    $("li#next").click(function() {
        from(START + NUM_PER_PAGE);
    })


    //nhận vào mã chương trình truyền cho hàm gửi lên servet truy vấn và đồng thời hiển thị thông tin lên biểu đồ
    row1.click(function() {
        showOnLineChar(row1);
    });
    row2.click(function() {
        showOnLineChar(row2)
    });
    row3.click(function() {
        showOnLineChar(row3)
    });
    row4.click(function() {
        showOnLineChar(row4)
    });
    row5.click(function() {
        showOnLineChar(row5)
    });
	stopAction.click(function() {
		stopAndActiveEvent();
	})

})

function stopAndActiveEvent() {
	$.post(
		"https://love-and-care.herokuapp.com/chuong-trinh/cai-dat-trang-thai",
		{'id': currentIdSelectEvent, 'status': false},
		function(result) {
			if(result) {
				for(let i in dataForDashBoard) {
					if(dataForDashBoard[i].id == currentIdSelectEvent) {
						displayAlertSuccess("Đã hoãn triển khai chương trình với tiêu đề: " + dataForDashBoard[i].title)
						dataForDashBoard.splice(i, 1);
						setTimeout(function() {
							location.reload();
						}, 3500);
						break;
					}
				}
			} else {
				displayAlertError("Không thể hoàn thành thao tác! Hãy thử lại sau.");
			}
		}
	)
}

function showOnLineChar(rowParam) {
    currentIdSelectEvent = rowParam.children("input").val();
    if(currentIdSelectEvent != "" && currentIdSelectEvent != null) {
        getDataForLineChart(currentIdSelectEvent);
        notActiveAllrow();
        active(rowParam);
    }
}
// tải về mảng dữ liệu tương ứng với id của chương trình khi click
function getDataForLineChart(id) {

    let labels = [];
	for(let i = 0; i < LENGTH_STATISTICS; i++) {
		let today = new Date();
		today.setDate(today.getDate() - i)
		//console.log(today.toLocaleDateString("vi-VN"));
		let dateStr = today.getDate() + "/" + (today.getMonth() + 1)
		labels.unshift(dateStr);
	}	

/* ================================================== GET DATA HERE ===================================================================== */
	$.post(
		"https://love-and-care.herokuapp.com/chuong-trinh/line-chart-statistics",
		{'eventId': id, 'length': LENGTH_STATISTICS},
		function(data) {
			let result = [];
			data.forEach((number) => {
				result.unshift(number);
			})
		    displayDataForLineChart(labels, result);
		}
	)
}
// nhận vào dữ liệu là 2 mảng bao gồm thời gian và số tiền để hiển thị
function displayDataForLineChart(labels, data) {
    let labelsOfChart = myLineChart.data.labels;
    let datasetsOfChart = myLineChart.data.datasets;
    //xóa dữ liệu khỏi mảng labels
    while(labelsOfChart.length > 0) {
        labelsOfChart.pop();
        myLineChart.update();
    }
    //xóa dữ liệu khỏi mảng data trong thuộc tính datasets
    datasetsOfChart.forEach((dataset) => {
        while(dataset.data.length > 0){
            dataset.data.pop();
            myLineChart.update();
        }
    })
    //chèn dữ liệu từ mảng labels tham số vào mảng labels của đối tượng chart
    for(let i = 0; i < labels.length; i++){
        labelsOfChart.push(labels[i]);
        myLineChart.update();
    }
    //chèn dữ liệu từ mảng data tham số vào mảng data trong thuộc tính datasets của đối tượng chart
    for(let i = 0; i < data.length; i++){
        datasetsOfChart.forEach((dataset) => {
            dataset.data.push(data[i]);
            myLineChart.update();
        })
    }
}

// Nhận vào dữ liệu là tổng số sản phẩm, số sản phẩm trên một trang để hiển thị phân trang và thanh điều hướng
function pagination(from) {
    $("ul#paginationHere>li").remove(".vatinumberPagination");
    let numPage = Math.ceil(TOTAL/NUM_PER_PAGE)
    let prev = from - NUM_PER_PAGE;
    let next = from + NUM_PER_PAGE;
    //nút điều hướng về trang trước
    if(prev >= 0) {
        $("li#prev").show();
    } else {
        $("li#prev").hide();
    }
    //nút điều hướng về trang sau
    if(next < TOTAL) {
        $("li#next").show();
    } else {
        $("li#next").hide();
    }
    for(let i = numPage; i > 0; i--) {
        let paginationFromValue = NUM_PER_PAGE*(i-1);
        if(from == paginationFromValue) {
            $("li#prev").after("<li class='page-item active vatinumberPagination'><span class='page-link' role='button' onclick='from(" + paginationFromValue + ")'>" + i + "</span></li>")
        } else {
            $("li#prev").after("<li class='page-item vatinumberPagination'><span class='page-link' role='button' onclick='from(" + paginationFromValue + ")'>" + i + "</span></li>")
        }
    }
}

function getDataForListInDashBoard() {
/* ======================================================= POST DATA HERE ========================================================== */
	let url = "https://love-and-care.herokuapp.com/chuong-trinh/line-chart-list";
    $.post(
		url,
		function(list) {
		    dataForDashBoard = list;
		    TOTAL = dataForDashBoard.length;
			from(0);
		    showOnLineChar(row1);
		    $("span#theRunningEvent").text(TOTAL);
		}
	)

}

function from(from) {
    START = from;
    pagination(from);
    display(from);
}

function display(from) {
    notActiveAllrow();
	let rowor = [row1, row2, row3, row4, row5];
	rowor.forEach((row) => {
		clearTable(row);
		fillTable(row, from++);
	})
}

function clearTable(row) {
	row.children("td").empty();
	row.children("input").val("");
}

function fillTable(row, numRow) {
	if(numRow < TOTAL) {
		row.children("th").text(numRow + 1);
	    row.children("input").val(dataForDashBoard[numRow].id);
	    row.children("td.title").text(titleSplit(dataForDashBoard[numRow].title));
	    row.children("td.date").text(toDateString(dataForDashBoard[numRow].endDate));
	} else {
		row.children("th").text("-");
	}
}

function titleSplit(title) {
	if(title.length > 100) {
		title = title.substr(0, 100) + "...";
	}
	return title;
}
function toDateString(date) {
	if(date == undefined || date == "") {
		return "Dài hạn";
	}
	return new Date(date).toLocaleDateString("vi-VN");
}

function active(row) {
	let rowId = row.children("input").val();
	let linkToEdit = "https://love-and-care.herokuapp.com/chuong-trinh/cap-nhat-chuong-trinh?eventId=" + rowId;
	let linkToDetail = "https://love-and-care.herokuapp.com/chuong-trinh/chi-tiet-chuong-trinh/" + rowId;
	idDropdown.text("MCT : " + rowId);
	linkEdit.attr("href", linkToEdit);
	linkDetail.attr("href", linkToDetail);
	
    row.addClass("bg-secondary text-white");
}
function notActiveAllrow() {
    $("#rowParent>tr").removeClass();
}


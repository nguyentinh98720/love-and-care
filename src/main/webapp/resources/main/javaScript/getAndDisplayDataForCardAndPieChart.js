$(document).ready(function() {

    $.ajax({
		url: 'https://love-and-care.herokuapp.com/chuong-trinh/pie-chart',
		data: null,
		type: 'post',
		success: function(data) {
		    let ending = data.ending;
		    let stopping = data.stopping;
		    let running = data.running;
		    let total = ending + stopping + running;
		    //hiển thị dữ liệu lên trang tổng quan
		    displayInCard(total, ending, stopping, running);
		    //gán dứ liệu tính toán được vào biểu đồ
		    insertInfoToPieChart(calculate(total, ending, stopping, running));
			
		},
		error: function(err) {
			console.log(err);
		}
	})
//Ví dụ cho dữ liệu được lấy về:
})

/* Hàm chèn số lượng các chương trình cụ thể lên bảng */
function displayInCard(total, ending, stopping, running) {
    $("#totalEvent").text(total);
    $("#endingEvent").text(ending);
    $("#stoppingEvent").text(stopping);
    $("#runningEvent").text(running);
}

/* Hàm tính toán tỉ lệ phần trăm giữa con số các sự kiện */
function calculate(total, ending, stopping, running) {
    return [Math.round(ending/total * 100), Math.round(stopping/total * 100), Math.round(running/total * 100)];
}

/* Hàm cung cấp thông tin cho biểu đồ */
function insertInfoToPieChart(data) {
    const datasetOfChart = myPieChart.data.datasets;
    //pop value from data array of datasets
    datasetOfChart.forEach((dataset) => {
        while(dataset.data.length > 0){
            dataset.data.pop();
            myPieChart.update();
        }
    })
    //push value from data array argument into data array of datasets of Chart
    for(let i = 0; i < data.length; i++){
        datasetOfChart.forEach((dataset) => {
            dataset.data.push(data[i]);
            myPieChart.update();
        })
    }
}
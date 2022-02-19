let images = [];

$(document).ready(function() {
    //mã id chương trình cần lấy ảnh từ server
    let eventId = $("input#eventId").val();
/* ====================================================== GET IMAGE FROM SERVER ========================================================= */
	let url = "https://love-and-care.herokuapp.com/chuong-trinh/anh-chuong-trinh";
		$.ajax({
		url: url,
		data: {'eventId':eventId},
		type: 'post',
		async: true,
		cache: false,
		success: function(result){
			images = result;
		    if(typeof images != 'undefined' || images != null || images != "") {
				showImgInDetailEvent();
		    }
		},
		error: function() {
			displayAlertError("error, there are something wrong here,,,");
		}
		})
})

function deleteImgFromEditEvent(id) {
    if(displayAlertConfirm("Xác nhận xóa hình ảnh")){
        for(let i = 0; i < images.length; i++) {
            if(images[i].id == id) {
                if(deleteImgFromServer(id)) {
                    images.splice(i, 1);
					displayAlertSuccess("Đã xóa hình ảnh");
                } else {
                    displayAlertError("something wrong... :(<br>Can not delete this image!");
                }
                break;
            }
        }
        showImgInDetailEvent();
    }
}

function showImgInDetailEvent() {
    let imgArea = $("div#imageEventManage");
    imgArea.children("div").remove();
    for(let i = 0; i < images.length; i++) {
        imgArea.append(imageObject(images[i]));
    }
}
function imageObject(image) {
	let url = getImageSource(image);
	let id = image.id;
    return "<div class='col-12 col-xl-2 col-lg-3 col-md-4 col-sm-6'><img src='" + url + "' alt='eventImage' class='img-thumbnail'>" +
        "<span class='btn btn-sm btn-danger text-white' style='position: relative; bottom: 2rem;' onclick='deleteImgFromEditEvent(" + id  + ")'>" +
        "<i class='fa fa-trash' aria-hidden='true'></i></span></div>";
}

function deleteImgFromServer(id) {
/* ======================================================= POST HERE - Delete by id ======================================= */

    let success = false;
		let url = "https://love-and-care.herokuapp.com/chuong-trinh/xoa-anh-chuong-trinh";
		$.ajax({
		url: url,
		data: {'id':id},
		type: 'post',
		async: false,
		cache: false,
		success: function(result){
			success = result;
			},
		error: function() {
			displayAlertError("error, there are something wrong here,,,");
			}
		})
    return success;
}

function clickDeleteEvent(id) {
	if(displayAlertConfirm("Bạn có chắc chắn muốn xóa chương trình quyên góp này không?")) {
		let success = false;
		let url = "https://love-and-care.herokuapp.com/chuong-trinh/xoa-chuong-trinh";
		$.ajax({
		url: url,
		data: {'id':id},
		type: 'post',
		async: false,
		cache: false,
		success: function(result){
			success = result;
			},
		error: function() {
			displayAlertError("error, there are something wrong here,,,");
			}
		})
		if(success) {
			displayAlertSuccess("Xóa chương trình thành công!");
			setTimeout(function() {
				location.replace("https://love-and-care.herokuapp.com/admin/quan-ly-chuong-trinh");
			}, 2000);
		} else {
			displayAlertError("Có lỗi xảy ra<br>Vui lòng thử lại.");
			setTimeout(function() {
				location.reload();
			}, 2000);
			
		}
	}
}

function canceEditEvent() {
	if(displayAlertConfirm("Tất cả thay đổi sẽ không được lưu. Bạn vẫn muốn thoát chứ?")) {
		location.replace("https://love-and-care.herokuapp.com/admin/quan-ly-chuong-trinh");
	}
}

function bufferToBase64(buf) {
    var binstr = Array.prototype.map.call(buf, function (ch) {
        return String.fromCharCode(ch);
    }).join('');
    return btoa(binstr);
}

function getImageSource(image) {
	let arr = image.arr;
	let type = image.type;
	
	let u8arr = new Uint8Array(arr);
	let encoded = bufferToBase64(u8arr);
	
	return "data:" + type + ";base64," + encoded;
}
// Tính toán thời gian thông báo kể từ khi được tạo
function timeCalculation(date) {
	var currentTime = new Date();
	var notificationTime = new Date(date);
	var timeDifference = currentTime - notificationTime;

	var secondsDifference = Math.floor(timeDifference / 1000);
	var minutesDifference = Math.floor(secondsDifference / 60);
	var hoursDifference = Math.floor(minutesDifference / 60);
	var daysDifference = Math.floor(hoursDifference / 24);

	var timeCalculate = '';
	if (daysDifference > 0) {
		timeCalculate = daysDifference + ' ngày ';
	}
	if (hoursDifference > 0) {
		var remainingHours = hoursDifference % 24;
		if (remainingHours > 0) {
			timeCalculate += remainingHours + ' giờ ';
		}
	}
	var remainingMinutes = minutesDifference % 60;
	timeCalculate += remainingMinutes + ' phút';
	if (timeCalculate == '0 phút') {
		timeCalculate = 'Vừa xong';
	}

	return timeCalculate;
}

function detailNotice(noticeId) {
	var url = 'detailNotice' + '?noticeId=' + noticeId;
	// window.location.href = "<s:url action='detailNotice'/>?noticeId="+ noticeId;
	window.location.href = url;
}

$('#btnNotice').click(function() {
	var userId = $('#loggedInUserId').val();
	console.log("ID: ", userId);
	$.ajax({
		url: 'showNotice',
		method: 'GET',
		data: { userId: userId },
		contentType: 'application/json',
		success: function(response) {
			console.log('Yêu cầu AJAX đã thành công!', response);
			if (response.status) {
				notifications = response.data
				// Xóa các thông báo hiện tại
				$("#notice-menu").empty();

				// Thêm các thông báo mới từ JSON vào menu
				notifications.forEach(function(notification) {
					var timeCalculate = timeCalculation(notification.createDate);
					var icon = '';
					if (notification.type === "Đặt ghế") {
						icon = "bg-light-green";
					} else if (notification.type === "Hủy ghế") {
						icon = "bg-red";
					} else {
						icon = "bg-yellow";
					}
					var title = '<h4>' + notification.title + '</h4>';
					var time = '<p><i class="material-icons">access_time</i> ' + timeCalculate + '</p>';
					var listItem = '<li><a href="#" onclick="detailNotice(' + notification.noticeId + ')" class=" waves-effect waves-block">' +
						'<div class="icon-circle ' + icon + '"><i class="material-icons">person</i></div>' +
						'<div class="menu-info">' + title + time + '</div></a></li>';

					$("#notice-menu").append(listItem);
				});
			}
		},
		error: function(status, error) {
			console.error('Lỗi khi thực hiện yêu cầu AJAX:', status, error);
		}
	})
})


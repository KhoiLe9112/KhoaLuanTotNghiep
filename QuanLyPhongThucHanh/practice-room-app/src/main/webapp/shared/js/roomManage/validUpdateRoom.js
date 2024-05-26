// Kiểm tra valids ngày
function validateDates() {
	var startDate = $('#startDate').val();
	var endDate = $('#endDate').val();
	var currentDate = new Date();

	if (startDate) {
		var startDateObj = new Date(startDate);
		if (startDateObj < currentDate) {
			alert('Ngày bắt đầu không thể nhỏ hơn ngày hiện tại.');
			$('#startDate').val('');
			return;
		}
	}

	if (endDate) {
		var endDateObj = new Date(endDate);
		if (endDateObj < currentDate) {
			alert('Ngày kết thúc không thể nhỏ hơn ngày hiện tại.');
			$('#endDate').val('');
			return;
		}
	}

	if (startDate && endDate) {
		var startDateObj = new Date(startDate);
		var endDateObj = new Date(endDate);
		if (startDateObj > endDateObj) {
			alert('Ngày bắt đầu không thể lớn hơn ngày kết thúc.');
			$('#startDate').val('');
			$('#endDate').val('');
		}
	}
}

$(document).ready(function() {
	//Lấy ngày hiện tại
	var currentDate = new Date(),
		currentDay = currentDate.getDate() < 10
			? '0' + currentDate.getDate()
			: currentDate.getDate(),
		currentMonth = currentDate.getMonth() < 10
			? '0' + (currentDate.getMonth() + 1)
			: (currentDate.getMonth() + 1);

	document.getElementById("currentDate").innerHTML = currentDay + '/' + currentMonth + '/' + currentDate.getFullYear();
	
	/*// Lấy giá trị ban đầu của trạng thái
	var initialStatus = $('#status').val();

	// Ẩn chọn ngày bắt đầu và ngày kết thúc khi trạng thái phòng là "Đang hoạt động"
	if (initialStatus === 'Đang hoạt động' || initialStatus === '') {
		$('#startDate').closest('.col-sm-2').hide();
		$('#endDate').closest('.col-sm-2').hide();
	}

	// Hiển thị chọn ngày bắt đầu và ngày kết thúc khi trạng thái phòng không phải là "Đang hoạt động"
	$('#status').on('change', function() {
		var selectedStatus = $(this).val();
		if (selectedStatus !== 'Đang hoạt động' && selectedStatus !== '') {
			$('#startDate').closest('.col-sm-2').show();
			$('#endDate').closest('.col-sm-2').show();
		} else {
			$('#startDate').closest('.col-sm-2').hide();
			$('#endDate').closest('.col-sm-2').hide();
		}
	});*/

	// Kiểm tra valids ngày bắt đầu và ngày kết thúc
	$('#startDate').on('change', function() {
		validateDates();
	});

	$('#endDate').on('change', function() {
		validateDates();
	});
	
	// Kiểm tra valids khi submit form
	$('#updateRoomForm').on('submit', function(event) {
        var room = $('#room').val();
        var validRooms = ["Lab - Doanh nghiệp"];
        
        var status = $('#status').val();
        var validStatus = ["Đang hoạt động", "Đóng cửa"];
        
        var startDate = $('#startDate').val();
        var endDate = $('#endDate').val();
        
        var validPeriod = ["Sáng", "Chiều"];
        var startPeriod = $('#startPeriod').val();
        var endPeriod = $('#endPeriod').val();
        
        var reason = $('#reason').val();
        
        // Check valids phòng
        if (room.trim() === "") {
            alert("Vui lòng chọn phòng");
            event.preventDefault();
            return;
        }
        if (!validRooms.includes(room)) {
            alert("Phòng không hợp lệ, vui lòng chọn một giá trị từ danh sách");
            event.preventDefault();
            return;
        }
        // Check valids trạng thái
        if (status.trim() === "") {
            alert("Vui lòng chọn trạng thái");
            event.preventDefault();
            return;
        }
        if (status.trim() === "Đang hoạt động") {
            alert("Vui lòng chọn trạng thái Đóng cửa để thêm lịch đóng cửa");
            event.preventDefault();
            return;
        }
        if (!validStatus.includes(status)) {
            alert("Trạng thái phòng không hợp lệ, vui lòng chọn một giá trị từ danh sách");
            event.preventDefault();
            return;
        }
        // Check valids ngày bắt đầu và ngày kết thúc
        if (startDate.trim() === "") {
            alert("Vui lòng chọn ngày bắt đầu");
            event.preventDefault();
            return;
        }
        if (endDate.trim() === "") {
            alert("Vui lòng chọn ngày kết thúc");
            event.preventDefault();
            return;
        }
        // Check valids buổi bắt đầu và buổi kết thúc
        if (startPeriod.trim() === "") {
            alert("Vui lòng chọn buổi bắt đầu");
            event.preventDefault();
            return;
        }
        if (endPeriod.trim() === "") {
            alert("Vui lòng chọn buổi kết thúc");
            event.preventDefault();
            return;
        }
        if (!validPeriod.includes(startPeriod)) {
            alert("Buổi bắt đầu không hợp lệ, vui lòng chọn một giá trị từ danh sách");
            event.preventDefault();
            return;
        }
        if (!validPeriod.includes(endPeriod)) {
            alert("Buổi kết thúc không hợp lệ, vui lòng chọn một giá trị từ danh sách");
            event.preventDefault();
            return;
        }
        // Check valids lý do đóng cửa
        if (reason.trim() === "") {
            alert("Vui lòng nhập lý do đóng cửa");
            event.preventDefault();
            return;
        }
		// Check confirm
		if (confirm('Xác nhận thêm lịch đóng cửa, sẽ xóa tất cả ghế đã đặt trước?')) {
			window.location.href = "<s:url action='newRoomSchedule'/>";
		} else {
			return false;
		}

    });
});

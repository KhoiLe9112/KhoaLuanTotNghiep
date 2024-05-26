// Lấy tất cả lịch đặt ghế
function checkSelection() {
	var usingDateStr = $('#usingDateStr').val();
	var period = $('#period').val();

	$.ajax({
		type: "GET",
		url: "getAvailableSeats",
		data: { usingDateStr: usingDateStr, period: period },
		success: function(response) {
			console.log('Yêu cầu AJAX đã thành công!', response);
			var stt = response.status;
			if (stt) {
				updateSeatList(response.data);
			} else {
				alert(response.message);
				$('#usingDateStr').val('');
			}
		}
	});
}

// Cập nhật lại trạng thái ghế
function updateSeatList(seats) {
	// Xóa tất cả ghế đã hiển thị trước đó
	$('.container .seat').removeClass('occupied');
	// Đánh dấu ghế đã đặt
	seats.forEach(function(seat) {
		$('#' + seat.seatId).addClass('occupied');
	});
}

// Lấy tất cả lịch đặt ghế và hiển thị dưới dạng datatable
/*function getSeatSchedules() {
	var usingDateStr2 = $('#usingDateStr2').val();
	var period2 = $('#period2').val();
	var userId = $('#userId').val();
	
	if (usingDateStr2 && period2) {
		$.ajax({
			type: "GET",
			url: "getBookedSeats",
			data: { usingDateStr: usingDateStr2, period: period2, userId: userId },
			success: function(response) {
				console.log('Yêu cầu AJAX đã thành công!', response);
				var dataTable = $('#dataTable').DataTable();
                dataTable.clear().draw();
	
				response.data.forEach(function(schedule) {
					$.ajax({
						type: "GET",
						url: "getUserJsonById",
						data: { userId: userId },
						success:function(response) {
							console.log('Yêu cầu AJAX getUserJsonById thành công!', response);
							if (response.status) {
								if (response.data.role === "User") {
									var row = [
				                        schedule.username,
				                        schedule.email,
				                        schedule.seatName,
				                        schedule.confirmed === 0 ? "Chưa xác nhận" : "Đã xác nhận",
										'<div class="center-btn">' +
											'<input type="hidden" id="scheduleId" name="schedule.scheduleId" value="' + schedule.scheduleId + '">' +
											'<button type="button" id="btnDeleteSeatSchedule" onclick="btnDeleteSeatSchedule(' + schedule.scheduleId + ')" class="btn bg-indigo waves-effect">' +
												'<i class="material-icons">delete</i>' +
											'</button>' +
										'</div>'
				                    ];
				                    dataTable.row.add(row);
				                    dataTable.draw();
								} else {
									var row = [
				                        schedule.username,
				                        schedule.email,
				                        schedule.seatName,
				                        schedule.confirmed === 0 ? "Chưa xác nhận" : "Đã xác nhận",
										'<div class="center-btn">' +
											'<input type="hidden" id="scheduleId" name="schedule.scheduleId" value="' + schedule.scheduleId + '">' +
											'<button type="button" id="btnDeleteSeatSchedule" onclick="btnDeleteSeatSchedule(' + schedule.scheduleId + ')" class="btn bg-indigo waves-effect">' +
												'<i class="material-icons">delete</i>' +
											'</button>' +
											'<span style="flex: 0 1 auto;">&nbsp;</span>' +
											'<button type="button" onclick="btnShowQR(' + schedule.scheduleId + ')" data-color="indigo" class="btn bg-indigo waves-effect button-demo js-modal-buttons">' +
												'<i class="material-icons">link</i>' +
											'</button>' +
										'</div>'
				                    ];
				                    dataTable.row.add(row);
				                    dataTable.draw();
								}
							}
						}
					})
	    		});
	    		
	    		//dataTable.draw();
	    		//dataTable.ajax.reload();
			}
		});
	}
}*/

function getSeatSchedules() {
	var usingDateStr2 = $('#usingDateStr2').val();
	var period2 = $('#period2').val();
	var userId = $('#userId').val();

	if (usingDateStr2 && period2) {
		$.ajax({
			type: "GET",
			url: "getBookedSeats",
			data: { usingDateStr: usingDateStr2, period: period2, userId: userId },
			success: function(response) {
				console.log('Yêu cầu AJAX đã thành công!', response);
				var dataTable = $('#dataTable').DataTable();
				dataTable.clear().draw();

				$.ajax({
					type: "GET",
					url: "getUserJsonById",
					data: { userId: userId },
					success: function(userResponse) {
						console.log('Yêu cầu AJAX getUserJsonById thành công!', userResponse);
						response.data.forEach(function(schedule) {
							var row = [
								schedule.username,
								schedule.email,
								schedule.seatName,
								schedule.confirmed === 0 ? "Chưa xác nhận" : "Đã xác nhận",
							];
							
							// Create the delete button element
							var deleteButtonElement = '<button type="button" id="btnDeleteSeatSchedule" onclick="btnDeleteSeatSchedule(' + schedule.scheduleId + ')" class="btn bg-indigo waves-effect">' +
								'<i class="material-icons">delete</i>' +
								'</button>';
								
							var inputIdElement = '<input type="hidden" id="scheduleId" name="schedule.scheduleId" value="' + schedule.scheduleId + '">';
							
							var space = '<span style="flex: 0 1 auto;">&nbsp;</span>';

							if (userResponse.data.role === "User") {
								// Nếu người dùng là "User", ẩn nút "btnShowQR"
								// Create the showQRBtn element
								var showQRButtonElement = '';
							} else {
								// Nếu người dùng không phải "User", hiển thị nút "btnShowQR"
								// Create the showQRBtn element
								var showQRButtonElement = '<button type="button" onclick="btnShowQR(' + schedule.scheduleId + ')" data-color="indigo" class="btn bg-indigo waves-effect button-demo js-modal-buttons">' +
									'<i class="material-icons">link</i>' +
									'</button>';
							}
							
							// Create the center button div
							var centerButtonDiv = '<div class="center-btn">' + inputIdElement + deleteButtonElement + space + showQRButtonElement + '</div>';
							row.push(centerButtonDiv);

							dataTable.row.add(row);
						});
						dataTable.draw();
					}
				});
			}
		});
	}
}

// Xóa lịch đặt ghế đã chọn
function btnDeleteSeatSchedule(scheduleId) {
	if (confirm("Bạn có chắc chắn muốn xóa lịch đặt ghế này?")) {
		$.ajax({
	        type: "POST",
	        url: "deleteSeatSchedule",
	        data: { scheduleId: scheduleId },
	        success: function(response) {
	            console.log('Yêu cầu AJAX xóa đã thành công!', response);
	            var stt = response.status;
	            if (stt) {
	                //var dataTable = $('#dataTable').DataTable();
	                //dataTable.ajax.reload();
	                getSeatSchedules();
	            }
				alert('Thông báo: ' + response.message);
	        },
	        error: function(textStatus, errorThrown) {
	            console.error('Lỗi khi thực hiện yêu cầu AJAX xóa:', textStatus, errorThrown);
	            alert('Đã xảy ra lỗi khi xóa schedule.');
	        }
	    });
	}
}

// Hiển thị QR
function btnShowQR(scheduleId) {
	$.ajax({
		type: "POST",
		url: "showQR",
		data: { scheduleId: scheduleId },
		success: function(response) {
			console.log('Yêu cầu AJAX xóa đã thành công!', response);
			var stt = response.status;
			if (stt) {
				$('#showQRCode img').attr('src', 'data:image/png;base64,' + response.message);
				$('#mdModal').modal('show');
			}
		},
		error: function(textStatus, errorThrown) {
			console.error('Lỗi khi thực hiện yêu cầu AJAX xóa:', textStatus, errorThrown);
			alert('Đã xảy ra lỗi khi hiển thị QR.');
		}
	});
}

$(document).ready(function() {
	// Gọi hàm checkSelection() khi thay đổi ngày sử dụng hoặc buổi sử dụng
	$('#usingDateStr').change(function() {
		const usingDateStr = $('#usingDateStr').val();
		const usingDate = new Date(usingDateStr);
		if (usingDate.getDay() === 0) {
			alert('Vui lòng chọn ngày khác. Ngày Chủ Nhật không thể đặt ghế.');
			$('#usingDateStr').val('');
			return;
		}
		checkSelection();
	});
	
	$('#period').change(function() {
		checkSelection();
	});
	
	$('#btnRefresh').click(function() {
		console.log('refresh');
		$('#usingDateStr').change();
		$('#period').change();
	});
	
	$('#usingDateStr2, #period2').change(function() {
        getSeatSchedules();
    });
    
    $('#btnRefresh2').click(function() {
		console.log('refresh');
		var dataTable = $('#dataTable').DataTable();
        dataTable.clear().draw();
		$('#usingDateStr2, #period2').change();
	});

	// Đổi trạng thái ghế khi chọn
	$('.container').on('click', '.seat:not(.occupied)', function(e) {
		const usingDateStr = $('#usingDateStr').val();
		const usingDate = new Date(usingDateStr);
		const period = $('#period').val();
		const userId = $('#user').val();
		if (usingDateStr.trim() === "") {
			alert("Vui lòng chọn ngày sử dụng");
            e.preventDefault();
            return;
		}
		if (period.trim() === "") {
			alert("Vui lòng chọn buổi sử dụng");
            e.preventDefault();
            return;
		}
		if (userId.trim() === "") {
			alert("Vui lòng chọn sinh viên sử dụng");
            e.preventDefault();
            return;
		}
		if (usingDate.getDay() === 0) {
			alert('Vui lòng chọn ngày khác. Ngày Chủ Nhật không thể đặt ghế.');
			$('#usingDateStr').val('');
			//$('.container .selected').removeClass('selected');
			e.preventDefault();
			return;
		}
		$(this).toggleClass('selected');
	});

	// Thêm lịch đặt ghế sau khi submit
	$('#btnSubmit').click(function(e) {
		e.preventDefault();
		
		const usingDateStr = $('#usingDateStr').val();
		const period = $('#period').val();
		const userId = $('#user').val();

		// Lấy danh sách id của ghế đã chọn
		/* var selectedSeats = $('.selected').map(function() {
			return this.id;
		}).get(); */
		let data = [];

		$('.container .selected').each(function() {
			const seatId = this.id;
			const dataItem = {
				usingDateStr: usingDateStr,
				period: period,
				userId: userId,
				seatId: seatId
			};
			data.push(dataItem);
		});
		//console.log('data: ', data);

		$.ajax({
			type: 'POST',
			url: 'bookSeats',
			contentType: 'application/json',
			data: JSON.stringify(data),
			success: function(response) {
				console.log('Yêu cầu AJAX đã thành công!', response);
				var stt = response.status;
				if (stt) {
					$('#usingDateStr').change();
					$('#period').change();
					// Xóa tất cả ghế đã chọn sau khi đặt ghế thành công
                	$('.container .seat').removeClass('selected');
				} else {
					$('#usingDateStr').change();
					$('#period').change();
				}
				alert('Thông báo: ' + response.message);
			},
			error: function(textStatus, errorThrown) {
				console.error('Lỗi khi thực hiện yêu cầu AJAX:', textStatus, errorThrown);
				alert('Đã xảy ra lỗi trong quá trình đặt ghế.');
			}
		});
	});
})
$(document).ready(function() {
    $('#newSubadminAssignForm').on('submit', function(event) {
        var subadmin = $('#subadmin').val();
        
        var period = $('#period').val();
        var validperiods = ["Sáng", "Chiều"];
        
        var assignDate = $('#assignDate').val();
        //var currentDate = new Date();
        
        // Check valids subadmin
        if (subadmin.trim() === "") {
            alert("Vui lòng chọn subadmin");
            event.preventDefault();
            return;
        }
        // Check valids phiên
        if (period.trim() === "") {
            alert("Vui lòng chọn phiên");
            event.preventDefault();
            return;
        }
        if (!validperiods.test(period)) {
            alert("Phiên không hợp lệ");
            event.preventDefault();
            return;
        }
        // Check valids ngày phân công
        if (assignDate.trim() === "") {
            alert("Vui lòng nhập ngày phân công");
            event.preventDefault();
            return;
        }
        /*$('#assignDate').on('change', function() {
			var assignDateObj = new Date(assignDate);
			if (assignDateObj < currentDate) {
				alert('Ngày phân công không thể nhỏ hơn ngày hiện tại.');
				event.preventDefault();
				$('#assignDate').val('');
				return;
			}
		});*/
    });
});
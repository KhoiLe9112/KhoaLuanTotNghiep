$(document).ready(function() {
	$('#confirmStudentForm').on('submit', function(event) {
		var studentCode = $('#studentCode').val();
		var validStudentCode = /^[0-9]{2}[a-zA-Z][0-9]{7}$/;
		
		// Check valids mã sinh viên
        if (studentCode.trim() === "") {
            alert("Vui lòng nhập mã sinh viên");
            event.preventDefault();
            return;
        }
        if (!validStudentCode.test(studentCode.trim())) {
            alert("Mã sinh viên không hợp lệ");
            event.preventDefault();
            return;
        }
	})
})
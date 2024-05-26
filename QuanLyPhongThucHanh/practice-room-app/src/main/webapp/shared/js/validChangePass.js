$(document).ready(function() {
	$('#changePasswordForm').on('submit', function(event) {
		console.log('đổi mk');
		var oldPassInput = $('#oldPassInput').val();
		var newPassInput = $('#newPassInput').val();
		var confirmNewPass = $('#confirmNewPass').val();
		
		// Check valids mật khẩu cũ
        if (oldPassInput.trim() === "") {
            alert("Vui lòng nhập mật khẩu cũ");
            event.preventDefault();
            return;
        }
        // Check valids mật khẩu mới
        if (newPassInput.trim() === oldPassInput.trim()) {
            alert("Mật khẩu mới không được trùng với mật khẩu cũ");
            event.preventDefault();
            return;
        }
        if (newPassInput.trim() === "") {
            alert("Vui lòng nhập mật khẩu mới");
            event.preventDefault();
            return;
        }
        // Check valids confirm mật khẩu mới
        if (confirmNewPass.trim() === "") {
            alert("Vui lòng nhập lại mật khẩu mới");
            event.preventDefault();
            return;
        }
        if (confirmNewPass.trim() !== newPassInput.trim()) {
            alert("Mật khẩu mới không hợp lệ");
            event.preventDefault();
            return;
        }
	})
})
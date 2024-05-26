function isHUSCEmail(email) {
	var validEmail = /^[0-9]{2}t102[0-9]{4}|[0-9]{2}t108[0-9]{4}@husc\.edu\.vn$/;
	return validEmail.test(email);
}

$(document).ready(function() {
	console.log('Start file login with firebase');

	// Initialize Firebase (giữ nguyên cấu hình)
	var config = {
		apiKey: "AIzaSyA2KBixW85XeQVpZ0zbd97oa4Vm4zSPyww",
		authDomain: "labtracker-6d5b8.firebaseapp.com",
		projectId: "labtracker-6d5b8",
		storageBucket: "labtracker-6d5b8.appspot.com",
		messagingSenderId: "481753958901",
		appId: "1:481753958901:web:0095d4c4104e3b409138bc",
		measurementId: "G-Z07VJSXGMR"
	};
	firebase.initializeApp(config);
	var database = firebase.database();

	// Google signin provider
	var ggProvider = new firebase.auth.GoogleAuthProvider();

	// Login with Google using jQuery click event handler
	$('#btnGoogle').click(function(e) {
		firebase.auth().signInWithPopup(ggProvider)
			.then(function(result) {
				var token = result.credential.accessToken;
				var user = result.user;

				// Kiểm tra xem email của người dùng có phải là email HUSC hay không
				if (isHUSCEmail(user.email)) {
					console.log('Người dùng đã đăng nhập bằng email HUSC:', user);
					var userId = user.uid;
					var username = user.displayName;
					var email = user.email;
					var phone = user.phoneNumber;
					
					// Tiếp tục logic đăng nhập cụ thể của HUSC
					$.ajax({
						type: "POST",
						url: "loginWithGoogle",
						data: {
							username: username,
							email: email,
							phone: phone
						 },
						success: function(response) {
							console.log('Yêu cầu AJAX xóa đã thành công!', response);
							var stt = response.status;
							if (stt) {
								window.location.href = "postManage";
							}
						},
						error: function(textStatus, errorThrown) {
							console.error('Lỗi khi thực hiện yêu cầu AJAX xóa:', textStatus, errorThrown);
						}
					});
				} else {
					firebase.auth().signOut().then(function() {
						alert('Đăng nhập không thành công. Email không thuộc HUSC.');
					}).catch(function(error) {
						console.error('Error signing out:', error.code);
					});
				}
			})
			.catch(function(error) {
				console.error('Error:', error.code);
			});
	});
});
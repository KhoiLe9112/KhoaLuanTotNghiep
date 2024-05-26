// Kiểm tra nếu đã lưu mật khẩu trước đó
var rememberedUserName = localStorage.getItem("rememberedEmail");
var rememberedPassword = localStorage.getItem("rememberedPassword");
if (rememberedUserName && rememberedPassword) {
	$("#email").val(rememberedUserName);
	$("#password").val(rememberedPassword);
	$("#rememberme").prop("checked", true);
}

// Xử lý khi checkbox thay đổi trạng thái
$("#rememberme").change(function() {
	if ($(this).is(":checked")) {
		localStorage.setItem("rememberedEmail", $("#email").val());
		localStorage.setItem("rememberedPassword", $("#password").val());
	} else {
		localStorage.removeItem("rememberedEmail");
		localStorage.removeItem("rememberedPassword");
	}
});
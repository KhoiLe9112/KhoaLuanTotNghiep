$(document).ready(function() {
    $('#newUserForm').on('submit', function(event) {
        var username = $('#username').val();
        var invalidUsername = /[0-9!@#$%^&*(),.?":{}|<>]/;
        
        var birthdate = $('#birthdate').val();
        var validBirthdate = /^(0[1-9]|[1-2][0-9]|3[01])\/(0[1-9]|1[0-2])\/\d{4}$/;
        
        var phone = $('#phone').val();
        var validPhone = /^[0-9]{10}$/;
        
        var email = $('#email').val();
        var validEmail = /^[0-9]{2}[a-z][0-9]{7}@husc\.edu\.vn$/;
        
        var role = $('#role').val();
        var validRoles = ["Admin", "Subadmin", "User"];
        
        // Check valids tên người dùng
        if (username.trim() === "") {
            alert("Vui lòng nhập tên người dùng");
            event.preventDefault();
            return;
        }
        if (invalidUsername.test(username)) {
            alert("Tên người dùng không hợp lệ");
            event.preventDefault();
            return;
        }   
        // Check valids ngày sinh
        if (birthdate.trim() === "") {
            alert("Vui lòng nhập ngày sinh");
            event.preventDefault();
            return;
        }
        if (!validBirthdate.test(birthdate)) {
            alert("Ngày sinh không hợp lệ");
            event.preventDefault();
            return;
        }
        // Check valids số điện thoại
        if (phone.trim() !== "" && !validPhone.test(phone)) {
            alert("Số điện thoại không hợp lệ");
            event.preventDefault();
            return;
        }   
        // Check valids email
        if (email.trim() === "") {
            alert("Vui lòng nhập email");
            event.preventDefault();
            return;
        }
        if (!validEmail.test(email)) {
            alert("Email không hợp lệ");
            event.preventDefault();
            return;
        }   
        // Check valids phân quyền
        if (role.trim() === "") {
            alert("Vui lòng chọn phân quyền");
            event.preventDefault();
            return;
        }
        if (!validRoles.includes(role)) {
            alert("Phân quyền không hợp lệ, vui lòng chọn một giá trị từ danh sách");
            event.preventDefault();
            return;
        }
    });
});
$(document).ready(function() {
    $("#logoutButton").click(function() {
		
		if (confirm('Xác nhận đăng xuất?')) {
			var provider = firebase.auth().currentUser.providerData[0].providerId;
			if (provider === 'google') {
				firebase.auth().signOut().then(function() {
					console.log('User signed out successfully');
					window.location.href = "<s:url action='logout'/>";
				}).catch(function(error) {
					console.error('Error signing out:', error.code);
				});
			} else {
				window.location.href = "<s:url action='logout'/>";
			}
		} else {
			return false;
		}
    });
});
$(document).ready(
	function() {

	    var user;
	    var token = getUrlParameter('token');
	    $.post('Caller', {
		action : "getUsername",
		token : token
	    }, function(returnedData) {
		if (returnedData.error == "") {
		    user = returnedData.username;
		    ;
		    setUsernameOnMenu();
		} else
		    alert(returnedData.error)
	    }, 'json');

	    function getUsername() {
		alert(user);
		return user;
	    }

	    function setUsernameOnMenu() {
		document.getElementById('username').innerText = getUsername();
	    }

	    function getUrlParameter(name) {
		name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
		var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
		var results = regex.exec(location.search);
		return results === null ? '' : decodeURIComponent(results[1]
			.replace(/\+/g, ' '));
	    }
	})
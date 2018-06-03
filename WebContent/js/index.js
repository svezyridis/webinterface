$(document).ready(
	function() {

	    var user;
	    $.post('Caller', {
		action : "getUsername",
		token : JSON.stringify(token)
	    }, function(returnedData) {
		if (returnedData.error == "") {
		    user = returnedData.username;
		    setMenu();
		} else
		    alert(returnedData.error)
	    }, 'json');

	    function getUsername() {
		
		return user;
	    }

	    function setMenu() {
		document.getElementById('username').innerText = getUsername();
		document.getElementById('list2').innerText = 'Galleries';
		document.getElementById('list2').setAttribute("href","Galleries.jsp");
		document.getElementById('list3').innerText = 'Friends';
		document.getElementById('list3').setAttribute("href","Friends.jsp");
		document.getElementById('signout').removeAttribute("hidden");
	    }

	    function getUrlParameter(name) {
		name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
		var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
		var results = regex.exec(location.search);
		return results === null ? '' : decodeURIComponent(results[1]
			.replace(/\+/g, ' '));
	    }
	})
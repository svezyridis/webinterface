$(document).ready(
	function() {

	    var user;
	    var dirid;
	    var callback;
	    
	    if (typeof token != 'undefined') {
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
	    }
	    setCallback();
	    setDirId();
	    setLoginPages();
	    

	    function getUsername() {
		
		return user;
	    }
	    function setDirId(){
		    $
	            .get(
	                'Caller', {action:"getAvailableDs"
	                },
	                function(returnedData) {
	                    $(returnedData)
	                    dirid=returnedData[0].identifier;
	                        
	                }, 'json');
		}
	    function setCallback(){
		    $
	            .get(
	                'Caller', {action:"getMyURL"
	                },
	                function(returnedData) {
	                    $(returnedData)
	            
	                    callback=returnedData.url;
	                        
	                },'json');
		}

	    function setMenu() {
		document.getElementById('username').innerText = getUsername();
		document.getElementById('list2').innerText = 'Galleries';
		document.getElementById('list2').setAttribute("href","Galleries.jsp");
		document.getElementById('list3').innerText = 'Friends';
		document.getElementById('list3').setAttribute("href","Friends.jsp");
		document.getElementById('signout').removeAttribute("hidden");
	    }
	    function setLoginPages(){
		$
                .get(
                    'Caller', {action:"getAvailableAs"
                    },
                    function(returnedData) {
                        $(returnedData)
                            .each(
                                function(index,
                                    system) {
                                   
                                    var par=document.createElement("P");
                                    par.innerText="Login with: ";
                                    var sys=document.createElement("A");
                                    sys.innerText=system.name;
                                    sys.setAttribute("href",system.loginurl+"?system="+dirid+"&callback="+callback);
                                    par.appendChild(sys);
                                    document.getElementById("systems").appendChild(par);
                                });
                    }, 'json');
	    }
	    

	    function getUrlParameter(name) {
		name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
		var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
		var results = regex.exec(location.search);
		return results === null ? '' : decodeURIComponent(results[1]
			.replace(/\+/g, ' '));
	    }
	})
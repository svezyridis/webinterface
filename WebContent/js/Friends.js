$(document)
    .ready(
        function() {

            var galleryid = getUrlParameter('galleryid');
            var user;

            if (typeof token == 'undefined') {
                var login = document.createElement("A");
                login.setAttribute("href", "login");
                login.innerText = "login";
                var divlogin = document.createElement("DIV");
                divlogin.innerText = "Please ";
                divlogin.appendChild(login);
                document.getElementById("images").appendChild(divlogin);

                return;

            }

            $.post('Caller', {
                action: "getUsername",
                token: JSON.stringify(token)
            }, function(returnedData) {
                if (returnedData.error == "") {
                    user = returnedData.username;
                    setMenu();
                    getFriends();
                    addFriendButton();
                } else
                    alert(returnedData.error)
            }, 'json');

            function getUsername() {

                return user;
            }

            function setMenu() {
                document.getElementById('username').innerText = getUsername();
                document.getElementById('list2').innerText = 'Galleries';
                document.getElementById('list2').setAttribute("href",
                    "Galleries.jsp");
                document.getElementById('list3').innerText = 'Friends';
                document.getElementById('list3').setAttribute("href",
                    "Friends.jsp");
                document.getElementById('list3').setAttribute("class",
                "active");
                document.getElementById('signout').removeAttribute("hidden");
            }
            function getFriends(){
        	$.post('Caller',{
        	    action:'getFriends',
        	    token:JSON.stringify(token)
        	    }, function(returnedData){
        		if (returnedData.error==""){
        		   $(returnedData.result).each(function(index,friend){
        		       var listelem=document.createElement("LI");
        		       var friendref=document.createElement("A");
        		       friendref.innerText=friend.friendname;
        		       friendref.setAttribute("href","Galleries.jsp?user="+friend.friendname)
        		       listelem.appendChild(friendref);
        		       document.getElementById("friends").appendChild(listelem);
        		       
        		   }) 
        		   
        		}
        		else
        		    alert(returnedData.error);
        		    
        	    },'json');
            }

            function getUrlParameter(name) {
                name = name.replace(/[\[]/, '\\[').replace(/[\]]/,
                    '\\]');
                var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
                var results = regex.exec(location.search);
                return results === null ? '' :
                    decodeURIComponent(results[1].replace(/\+/g,
                        ' '));
            }
            function addFriend(form) {
                var dataposted = new FormData(form);
                for (var pair of dataposted.entries()) {
                    console.log(pair[0] + ', ' + pair[1]);
                    if(pair[0]=='friendname')
                	var name=pair[1];
                }
                $
                    .ajax({

                        type: "POST",
                        url: 'Caller',
                        data: dataposted,
                        processData: false,
                        contentType: false,
                        success: function(response) {
                            response = JSON.parse(response);
                            if(response.error==''){
                            	alert('friend added');
                            	var listelem=document.createElement("LI");
                            	var friendref=document.createElement("A");
                            	friendref.innerText=name;
                            	friendref.setAttribute("href","Galleries.jsp?user="+name)
                            	listelem.appendChild(friendref);
                            	document.getElementById("friends").appendChild(listelem);
                        }
                            else
                        	alert(response.error);
                            	    
                        },
                        datatype: 'json'
                    })
            }
            function addFriendButton() {
                var postform = document
                    .createElement("form");
                postform.setAttribute("id",
                    "addfriend");
                var frienddiv = document.createElement("DIV");
                var action = document.createElement("input");
                action.setAttribute("name", "action");
                action.setAttribute("type", "hidden");
                action.setAttribute("value", "addFriend");
                var text = document.createElement("input");
                text.setAttribute("name", "friendname");
                text.setAttribute("type", "text");
                text.setAttribute("placeholder","type friend name");
                var tkn = document.createElement("input");
                tkn.setAttribute("name", "token");
                tkn.setAttribute("type", "hidden")
                tkn.setAttribute("value", JSON.stringify(token));
                var friendbtn = document
                    .createElement("button");
                friendbtn.setAttribute("type",
                    "submit");
                friendbtn.innerText = "Add Friend";
                postform.appendChild(text);
                postform.appendChild(tkn);
                
                postform.appendChild(action);
                postform.appendChild(friendbtn);
                postform.addEventListener('submit',
                    function(event) {
                        event.preventDefault();
                        addFriend(this);
                    });
                document.getElementById("button").appendChild(postform);
            
            };
            

    
            

        });
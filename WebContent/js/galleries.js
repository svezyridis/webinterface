$(document)
	.ready(
		function() {
		
		    var owner = getUrlParameter('user');		    
		    var user;
		   
		    if(token==null){
			
			var divlogin=document.createElement("DIV");
			divlogin.innerText="Please";
			var login= document.createElement("A");
			login.setAttribute("href","login");
			login.innerText="login";
			divlogin.appendChild(login);
			document.getElementById("images")
			    .appendChild(divlogin);
			
			
		    }
		    $.post('Caller', {
			action : "getUsername",
			token : JSON.stringify(token)
		    }, function(returnedData) {
			if (returnedData.error == "") {
			    user = returnedData.username;
			    setMenu();
			    getGalleries();
			} else
			    alert(returnedData.error)
		    }, 'json');

		    function getUsername() {
			
			return user;
		    }
		    function setMenu() {
			document.getElementById('username').innerText = getUsername();
			document.getElementById('list2').innerText = 'Galleries';
			document.getElementById('list2').setAttribute("class","active");
			document.getElementById('list2').setAttribute("href","Galleries.jsp");
			document.getElementById('list3').innerText = 'Friends';
			document.getElementById('list3').setAttribute("href","Friends.jsp");
			document.getElementById('signout').removeAttribute("hidden");
		    }
		    function getGalleries(){
		   

		    $
			    .post(
				    'Caller',
				    {
					action : "getGalleries",
					user : (owner==null||owner=="") ? user : owner, 
					token : JSON.stringify(token)
				    },
				    function(returnedData) {
					if (returnedData.error == "") {
					    var column1 = document
						    .createElement("DIV");
					    column1.setAttribute("class",
						    "column");
					    var column2 = document
						    .createElement("DIV");
					    column2.setAttribute("class",
						    "column");
					    var column3 = document
						    .createElement("DIV");
					    column3.setAttribute("class",
						    "column");
					    var column4 = document
						    .createElement("DIV");
					    column4.setAttribute("class",
						    "column");
					    var columns = [ column1, column2,
						    column3, column4 ];
					    $(returnedData.result)
						    .each(
							    function(index,
								    gallery) {
				
								var gal=document.createElement("A");
								gal.innerText=gallery.galleryname;
								gal.setAttribute("id",gallery.galleryname);
								gal.setAttribute("href","Gallery.jsp?galleryid="+gallery.galleryid);
								var img=document.createElement("IMG");
								    $.post(
									    'Caller',
									    {
										action : "getImages",
										gallid : gallery.galleryid,
										token:JSON.stringify(token)
									    },
									    function(returnedData) {
										if (returnedData.error == "") {
										    var i=getRandomInt(0,returnedData.result.length-1);
										   
										    img.setAttribute("src", returnedData.result[i].imageURL)                       
										    img.setAttribute("style", "width:100%");
									    }
										else{
										    img.setAttribute("src", "images/empty.png"); 
										    img.setAttribute("style", "width:100%");
										}
									    }, 'json');
								
								gal.appendChild(img);
								var deletebtn=addButton(gallery);
								gal.appendChild(deletebtn);

								columns[(index % 4)]
									.appendChild(gal)
							    });
					    document.getElementById("images")
						    .appendChild(column1);
					    document.getElementById("images")
						    .appendChild(column2);
					    document.getElementById("images")
						    .appendChild(column3);
					    document.getElementById("images")
						    .appendChild(column4);
					} else {
					    alert(returnedData.error);
					}
				    }, 'json');
		    }
		    function addButton(gallery) {
	        	var divbtn=document.createElement("DIV");
	                var deleteBtn = document.createElement("BUTTON");
	                deleteBtn.setAttribute("class", "delete-button");
	                deleteBtn.setAttribute("galname", gallery.galleryname);
	                deleteBtn.innerText = "Delete Gallery";
	                deleteBtn.onclick = function(event) {
	                    event.preventDefault();
	                    deleteGallery(this);
	                };
	                divbtn.appendChild(deleteBtn);
	                return divbtn;
	            }
		    


		    function addImage(image) {

			var aimg = document.createElement("A");
			aimg.setAttribute("href", image.imageURL);
			aimg.setAttribute("data-lightbox", "somedata");
			aimg.setAttribute("alt", "");
			aimg.setAttribute("imageid", image.id);
			aimg.setAttribute("class", "aimg")
			var img = document.createElement("IMG");
			img.setAttribute("src", image.imageURL);
			img.setAttribute("style", "width:100%")
			aimg.appendChild(img);
			return aimg;

		    }
		    function deleteGallery(button) {
	                var galname=button.getAttribute("galname");
	                $
	                .post(
	                    'Caller', {
	                        action: "deleteGallery",
	                        galleryname: galname,
	                        token: JSON.stringify(token)
	                    },
	                    function(returnedData) {
	                	if(returnedData.error==""){
	                	    document.getElementById(galname).remove();
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
			return results === null ? ''
				: decodeURIComponent(results[1].replace(/\+/g,
					' '));
		    }
		    function getRandomInt(min, max) {
			    return Math.floor(Math.random() * (max - min + 1)) + min;
			}
		    ;

		});

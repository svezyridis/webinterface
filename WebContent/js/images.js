$(document)
	.ready(
		function() {

		    alert("${variable}");
		    var user;
		    $.post('Caller', {
			action : "getUsername",galleryid:params[0][1]
		    }, function(returnedData) {
			username.innerText = returnedData.username;
			user = returnedData.username;
		    }, 'json');

		    function getUsername() {
			return user;
		    }

		    $.post('Caller', {
			action : "getImages"
		    }, function(returnedData) {
			var column1 = document.createElement("DIV");
			column1.setAttribute("class", "column");
			var column2 = document.createElement("DIV");
			column2.setAttribute("class", "column");
			var column3 = document.createElement("DIV");
			column3.setAttribute("class", "column");
			var column4 = document.createElement("DIV");
			column4.setAttribute("class", "column");
			var columns = [ column1, column2, column3, column4 ];
			$(returnedData.result).each(function(index, image) {
			    var div = document.createElement("DIV");
			    var aimg = addImage(image);
			    div.appendChild(aimg);
			    // commentbtn
			    var cmntBtn = addButton(image);
			    div.appendChild(cmntBtn);
			    // commentbox
			    var detailBox = addCommentBox(image.id);
			    div.appendChild(detailBox)

			    columns[(index % 4)].appendChild(div)
			});
			document.getElementById("images").appendChild(column1);
			document.getElementById("images").appendChild(column2);
			document.getElementById("images").appendChild(column3);
			document.getElementById("images").appendChild(column4);
		    }, 'json');

		    function showComments(button) {
			var commentbox = document.getElementById(button
				.getAttribute("buttonid"));
			if (commentbox.hasAttribute("hidden")) {
			    commentbox.removeAttribute("hidden");
			    button.innerText = "Hide Comments";
			} else {
			    commentbox.setAttribute("hidden", "true");
			    button.innerText = "Show Comments";
			}
		    }

		    function postComment(form) {
			var txt = document.getElementById("text"
				+ form.getAttribute("imageid"));
			$
				.post(
					'Caller',
					{
					    action : "postComment",
					    imageid : form
						    .getAttribute("imageid"),
					    text : txt.value
					},
					function(returnedData) {
					    if (returnedData.error == "") {
						alert('comment added');
						var list = document
							.getElementById("list"
								+ form
									.getAttribute("imageid"));
						var comment = document
							.createElement("LI");
						var commenterName = document
							.createElement("A");
						commenterName
							.setAttribute("href",
								"https://www.google.gr/");
						commenterName.setAttribute(
							"class",
							"commenterName");
						commenterName.innerText = getUsername();
						var commentText = document
							.createElement("DIV");
						commentText.setAttribute(
							"class", "commentText");
						var text = document
							.createElement("P");
						text.innerText = txt.value;
						var date = document
							.createElement("SPAN");
						date.setAttribute("class",
							"date sub-text");
						date.innerText = "Now";
						commentText.appendChild(text);
						commentText.appendChild(date);
						comment
							.appendChild(commenterName);
						comment
							.appendChild(commentText);
						list.appendChild(comment);

					    } else
						alert(returnedData.error);
					}, 'json');

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
		    function addButton(image) {
			var cmntBtn = document.createElement("BUTTON");
			cmntBtn.setAttribute("class", "comment-button");
			cmntBtn.setAttribute("buttonid", image.id);
			cmntBtn.innerText = "Show Comments";
			cmntBtn.onclick = function() {
			    showComments(this);
			};
			return cmntBtn;
		    }

		    function addCommentBox(imgid) {
			var detailBox = document.createElement("DIV");
			detailBox.setAttribute("class", "detailBox");
			detailBox.setAttribute("hidden", "true");
			detailBox.setAttribute("id", imgid);
			var titleBox = document.createElement("DIV");
			titleBox.setAttribute("class", "titleBox");
			var label = document.createElement("LABEL");
			label.innerText = "Comments";
			var close = document.createElement("BUTTON");
			close.setAttribute("type", "button");
			close.setAttribute("class", "close");
			close.setAttribute("aria-hidden", "true");
			titleBox.appendChild(label);
			titleBox.appendChild(close);
			var commentBox = document.createElement("DIV");
			commentBox.setAttribute("class", "commentBox");
			detailBox.appendChild(titleBox);
			detailBox.appendChild(commentBox);

			var actionBox = document.createElement("DIV");
			actionBox.setAttribute("class", "actionBox");
			var commentList = document.createElement("UL");
			commentList.setAttribute("class", "commentList");
			commentList.setAttribute("id", "list" + imgid);
			$
				.post(
					'Caller',
					{
					    action : "getComments",
					    imageid : imgid
					},
					function(returnedData) {
					    var comments = returnedData.comments;
					    $(comments)
						    .each(
							    function(index,
								    comm) {
								var comment = document
									.createElement("LI");
								var commenterName = document
									.createElement("A");
								commenterName
									.setAttribute(
										"href",
										"https://www.google.gr/");
								commenterName
									.setAttribute(
										"class",
										"commenterName");
								commenterName.innerText = comm.commenter;
								var commentText = document
									.createElement("DIV");
								commentText
									.setAttribute(
										"class",
										"commentText");
								var text = document
									.createElement("P");
								text.innerText = comm.text;
								var date = document
									.createElement("SPAN");
								date
									.setAttribute(
										"class",
										"date sub-text");
								date.innerText = comm.timestamp;
								commentText
									.appendChild(text);
								commentText
									.appendChild(date);
								comment
									.appendChild(commenterName);
								comment
									.appendChild(commentText);
								commentList
									.appendChild(comment);
							    });
					}, 'json');

			var form = document.createElement("FORM");
			form.setAttribute("class", "form-inline");
			form.setAttribute("role", "form");
			form.setAttribute("action", "#");
			form.setAttribute("imageid", imgid);
			var divcom = document.createElement("DIV");
			divcom.setAttribute("class", "form-group");
			var inputText = document.createElement("INPUT");
			inputText.setAttribute("class", "form-control");
			inputText.setAttribute("type", "text");
			inputText.setAttribute("placeholder", "Your Comments");
			inputText.setAttribute("id", "text" + imgid);
			var button = document.createElement("BUTTON");
			button.setAttribute("class", "btn btn-default");
			button.innerText = "Add";
			button.setAttribute("type", "submit");
			divcom.appendChild(inputText);
			divcom.appendChild(button);
			form.appendChild(divcom);
			form.addEventListener('submit', function() {
			    postComment(this);
			});
			actionBox.appendChild(commentList);
			actionBox.appendChild(form);
			detailBox.appendChild(actionBox);
			return detailBox;
		    }
		    function get_query() {
			var url = location.href;
			var qs = url.substring(url.indexOf('?') + 1).split('&');
			for (var i = 0, result = {}; i < qs.length; i++) {
			    qs[i] = qs[i].split('=');
			    result[qs[i][0]] = qs[i][1];
			}
			return result;
		    }
		});

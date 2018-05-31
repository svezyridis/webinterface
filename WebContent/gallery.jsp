<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.json.JSONObject"%>
<%@ page import="apicalls.Caller"%>
<%@ page import="java.util.List"%>
<%
	String galleryid = request.getParameter("galleryid");
%>
<%
	List<JSONObject> imageList = Caller.getImages("sth", galleryid);
%>


<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Hydrogen &mdash; A free HTML5 Template by FREEHTML5.CO</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="Free HTML5 Template by FREEHTML5.CO" />
<meta name="keywords"
	content="free html5, free template, free bootstrap, html5, css3, mobile first, responsive" />
<meta name="author" content="FREEHTML5.CO" />


<!-- Google Webfonts -->
<link
	href='http://fonts.googleapis.com/css?family=Roboto:400,300,100,500'
	rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Montserrat:400,700'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="css/test.css">
<link rel="stylesheet" href="css/test2.css">
<!-- Animate.css -->
<link rel="stylesheet" href="css/animate.css">
<!-- Icomoon Icon Fonts-->
<link rel="stylesheet" href="css/icomoon.css">
<!-- Magnific Popup -->
<link rel="stylesheet" href="css/magnific-popup.css">
<!-- Salvattore -->
<link rel="stylesheet" href="css/salvattore.css">
<!-- Theme Style -->
<link rel="stylesheet" href="css/style.css">
<!-- Modernizr JS -->
<script src="js/modernizr-2.6.2.min.js"></script>
<!-- FOR IE9 below -->
<!--[if lt IE 9]>
	<script src="js/respond.min.js"></script>
	<![endif]-->

</head>
<body>

	<div id="fh5co-offcanvass">
		<a href="#" class="fh5co-offcanvass-close js-fh5co-offcanvass-close">Menu
			<i class="icon-cross"></i>
		</a>
		<h1 class="fh5co-logo">
			<a class="navbar-brand" >Instatuc</a>
		</h1>
		<ul>
			<li class="active"><a href="index.jsp">Home</a></li>
			<li><a href="register.jsp">Register</a></li>
			<li><a href="pricing.html">Login</a></li>
		</ul>
	</div>
	<header id="fh5co-header" role="banner">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<a href="#" class="fh5co-menu-btn js-fh5co-menu-btn">Menu <i
						class="icon-menu"></i></a> <a class="navbar-brand" href="index.jsp">Instatuc</a>
				</div>
			</div>
		</div>
	</header>
	<!-- END .header -->


	<div id="fh5co-main">
		<div class="container">
			<%
				if (session.getAttribute("flash") != null) {
			%>
			<div class="flash">
				<%=session.getAttribute("flash")%>
			</div>
			<%
				session.removeAttribute("flash");
				}
			%>

			<div class="row">

				<div id="fh5co-board" data-columns>
					<%
						if (imageList != null) {
							for (JSONObject object : imageList) {
								List<JSONObject> comments = Caller.getComments(object);
					%>
					<div class="item">
						<div class="animate-box">
							<a href=<%=object.getString("imageURL")%>
								class="image-popup fh5co-board-img"><img
								src=<%=object.getString("imageURL")%>
								alt="Free HTML5 Bootstrap template"></a>
						</div>
						<div class="detailBox">
							<div class="titleBox">
								<label>Comments</label>
							</div>
							<div class="actionBox">
								<ul class="commentList">
									<%
										for (JSONObject comment : comments) {
									%>
									<li>
										<div class="commenterName"><%=comment.getString("commenter")%></div>
										<div class="commentText">
											<p class=""><%=comment.getString("text")%></p>
											<span class="date sub-text"><%=comment.getString("timestamp")%></span>
										</div>
									</li>
									<%
										}
									%>
								</ul>
								<form class="form-inline" role="form" method="post"
									action="Caller">
									<div class="form-group">
										<input class="form-control" type="text"
											placeholder="Your comments" name="comment" />
									</div>
									<div class="form-group">
										<input type="submit" class="btn btn-default" id="add"
											value="Add"> <input class="form-control"
											type="hidden" id="action" name="action" value="postComment" />
									</div>
								</form>
							</div>
						</div>
					</div>
						<%
		}
		} else {
			String error = Caller.getErrorMessage();
	%>
	<div> 
		<%=error %>
	</div>
	<%
		}
	%>
				</div>
			</div>
		</div>
	</div>


	<footer id="fh5co-footer">

		<div class="container">
			<div class="row row-padded">
				<div class="col-md-12 text-center">
					<p>
						<small>&copy; Hydrogen Free HTML5 Template. All Rights
							Reserved. <br>Designed by: <a href="http://freehtml5.co/"
							target="_blank">FREEHTML5.co</a> | Images by: <a
							href="http://pexels.com" target="_blank">Pexels</a>
						</small>
					</p>
				</div>
			</div>
		</div>
	</footer>


	<!-- jQuery -->
	<script src="js/jquery.min.js"></script>
	<!-- jQuery Easing -->
	<script src="js/jquery.easing.1.3.js"></script>
	<!-- Bootstrap -->
	<script src="js/bootstrap.min.js"></script>
	<!-- Waypoints -->
	<script src="js/jquery.waypoints.min.js"></script>
	<!-- Magnific Popup -->
	<script src="js/jquery.magnific-popup.min.js"></script>
	<!-- Salvattore -->
	<script src="js/salvattore.min.js"></script>
	<!-- Main JS -->
	<script src="js/main.js"></script>





</body>
</html>



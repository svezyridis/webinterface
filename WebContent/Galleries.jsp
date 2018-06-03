<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	if (session.getAttribute("token") != null && session.getAttribute("token") != "") {
%>
<script type="text/javascript">
    token =
<%=session.getAttribute("token")%>
    ;
</script>
<%
	}
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
<title>InstaTUC &mdash; The TUC image sharing network</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="The TUC image sharing network" />
<meta name="author" content="SAVVAS VEZYRIDIS" />


<!-- Google Webfonts -->
<link
	href='http://fonts.googleapis.com/css?family=Roboto:400,300,100,500'
	rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Montserrat:400,700'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="css/columns.css">
<link rel="stylesheet" href="css/commentbox.css">
<link rel="stylesheet" href="css/lightbox.css">
<!-- Animate.css -->
<link rel="stylesheet" href="css/animate.css">
<!-- Icomoon Icon Fonts-->
<link rel="stylesheet" href="css/icomoon.css">
<!-- Magnific Popup -->
<link rel="stylesheet" href="css/magnific-popup.css">
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
			<a class="navbar-brand" href="index.jsp" id="username">NOT LOGED
				IN</a>
		</h1>
		<ul id="menu">
			<li><a href="index" id="list1">Home</a></li>
			<li><a href="register" id="list2">Register</a></li>
			<li><a href="login" id="list3">Login</a></li>
			<li><form action="${pageContext.request.contextPath}/Caller"
					method="post" hidden="true" id="signout">
					<input type="submit" name="Sign out" value="Signout" />
					<input type="hidden" name="action" value="signout"/> 
				</form></li>
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
			<div class="row" id="images"></div>
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
</body>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

<script type="text/javascript" src="js/galleries.js"></script>

<script src="js/lightbox.min.js"></script>
<!-- jQuery Easing -->
<script src="js/jquery.easing.1.3.js"></script>
<!-- Bootstrap -->
<script src="js/bootstrap.min.js"></script>
<!-- Waypoints -->
<script src="js/jquery.waypoints.min.js"></script>
<!-- Magnific Popup -->
<script src="js/jquery.magnific-popup.min.js"></script>
<!-- Main JS -->
<script src="js/main.js"></script>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%

	if (request.getParameter("token") != null && request.getParameter("token") != "") {
		%>
		<script type="text/javascript">
		token=<%=request.getParameter("token")%>;
		</script><%
		session.setAttribute("token", request.getParameter("token"));
	}
	else{
		if (session.getAttribute("token")!=null && session.getAttribute("token")!=""){
			%>
			<script type="text/javascript">
			token=<%=session.getAttribute("token")%>;
		</script><%
		}
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
	href='https://fonts.googleapis.com/css?family=Roboto:400,300,100,500'
	rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Montserrat:400,700'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="css/test.css">
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
			<a class="navbar-brand" href="index.jsp" id="username">NOT LOGED
				IN</a>
		</h1>
		<ul id="menu">
			<li class="active"><a href="index" id="list1">Home</a></li>
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
						class="icon-menu"></i></a> <a class="navbar-brand" href="index.jsp">InstaTUC</a>
				</div>
			</div>
		</div>
	</header>
	<!-- END .header -->
	<div id="fh5co-main">
		<div class="container">

			<div class="row">

				<div id="fh5co-board" data-columns>

					<div class="item">
						<div class="animate-box">
							<a href="images/img_1.jpg" class="image-popup fh5co-board-img"
								title="Create your very own photo collections!"><img
								src="images/img_1.jpg" alt="Free HTML5 Bootstrap template"></a>
						</div>
						<div class="fh5co-desc">Create your very own collection of
							images!</div>
					</div>
					<div class="item">
						<div class="animate-box">
							<a href="images/img_2.jpg" class="image-popup fh5co-board-img"
								title="Meet other photography enthusiasts around the world!"><img
								src="images/img_2.jpg" alt="Free HTML5 Bootstrap template"></a>
							<div class="fh5co-desc">Meet other photography enthusiasts
								around the world!</div>
						</div>
					</div>
					<div class="item">
						<div class="animate-box">
							<a href="images/img_3.jpg" class="image-popup fh5co-board-img"
								title="Connect with your friends to share your photos!"><img
								src="images/img_3.jpg" alt="Free HTML5 Bootstrap template"></a>
							<div class="fh5co-desc">Connect with your friends to share
								your photos!</div>
						</div>
					</div>
					<div class="item">
						<div class="animate-box">
							<a href="images/img_4.jpg" class="image-popup fh5co-board-img"
								title="Build your social network!"><img
								src="images/img_4.jpg" alt="Free HTML5 Bootstrap template"></a>
							<div class="fh5co-desc">Build your social network!</div>
						</div>
					</div>
					<div class="item">
						<div class="animate-box">
							<a href="images/img_6.jpg" class="image-popup fh5co-board-img"
								title="Join us now!"><img src="images/img_6.jpg"
								alt="Free HTML5 Bootstrap template"></a>
							<div class="fh5co-desc">Join us now!</div>
						</div>
					</div>
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
							Reserved. <br>Designed by: Savvas Vezyridis
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
	<script src="js/index.js"></script>




</body>
</html>

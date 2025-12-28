<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>PCM|Merza</title>

<!-- Bootstrap -->
<link
	href="${pageContext.request.contextPath}/vendors/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Font Awesome -->
<link
	href="${pageContext.request.contextPath}/vendors/font-awesome/css/font-awesome.min.css"
	rel="stylesheet">
<!-- NProgress -->
<link
	href="${pageContext.request.contextPath}/vendors/nprogress/nprogress.css"
	rel="stylesheet">
<!-- jQuery custom content scroller -->
<link
	href="${pageContext.request.contextPath}/vendors/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.min.css"
	rel="stylesheet">
<!-- Custom Theme Style -->
<link href="${pageContext.request.contextPath}/Css/css/custom.min.css"
	rel="stylesheet">
<!-- JS -->
<script type="text/javascript"
	src="<c:out value="${pageContext.servletContext.contextPath}"/>/js/funciones.js"></script>
<!-- Alertas -->
<script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"
	rel="stylesheet">
<style type="text/css">
.form-control {
	font-size: 12px;
}
</style>
</head>
<body class="nav-md">
	<div class="container body">
		<div class="main_container">
			<div
				class="col-md-3 left_col menu_fixed mCustomScrollbar _mCS_1 mCS-autoHide mCS_no_scrollbar"
				style="overflow: visible;">
				<div id="mCSB_1"
					class="mCustomScrollBox mCS-minimal mCSB_vertical mCSB_outside"
					style="max-height: 626px;" tabindex="0">
					<div id="mCSB_1_container"
						class="mCSB_container mCS_y_hidden mCS_no_scrollbar_y"
						style="position: relative; top: 0; left: 0;" dir="ltr">
						<div class="left_col scroll-view">
							<div class="navbar nav_title" style="border: 0;">
								<a href="/PCM_QA/Comunes/Index.jsp" class="site_title"><i
									class="fas fa-briefcase"></i> <span><font
										style="vertical-align: inherit;">Grupo merza</font></span></a>
							</div>

							<div class="clearfix"></div>

							<!-- menu profile quick info -->
							<div class="container">
								<div class="row">
									<div class="col-4">
										<img class="img-responsive img-rounded"
											src="${pageContext.request.contextPath}/Images/merza.png"
											alt="..." width="60" height="35">
									</div>
									<div class="col-8">
										<span><font style="vertical-align: inherit;">Bienvenido
										</font></span>
										<h2 id="NombreUsuario">
											<font style="vertical-align: inherit;"> </font>
										</h2>
									</div>
								</div>

							</div>
							<!-- /menu profile quick info -->

							<br>

							<!-- sidebar menu -->
							<div id="sidebar-menu"
								class="main_menu_side hidden-print main_menu">
								<!-- 					Si es administrador puede ver el siguiente menu		 -->
								<div class="menu_section active">
									<ul class="nav side-menu" style="">
										<c:if
											test="${usuario.getIdRol() eq '1'  or usuario.getIdRol() eq '2' or usuario.getIdRol() eq '3' }">

											<li><a><i class="fa fa-home"></i><font
													style="vertical-align: inherit;">Home </font><span
													class="fa fa-chevron-down"></span></a>
												<ul class="nav child_menu">
													<li><a href="/PCM_QA/Comunes/Index.jsp">Index</a></li>
												</ul></li>
										</c:if>
										<c:if test="${usuario.getIdRol() eq '1' }">

											<li><a><i class="fa fa-cogs"></i><font
													style="vertical-align: inherit;">Administración </font><span
													class="fa fa-chevron-down"></span></a>
												<ul class="nav child_menu">
													<li><a
														href="${pageContext.servletContext.contextPath}/atributo?op=Listar&pagina=1">Atributos</a></li>
													<li><a
														href="${pageContext.servletContext.contextPath}/familia?op=Listar&pagina=1">Familias</a></li>
													<li><a
														href="${pageContext.servletContext.contextPath}/producto?op=AdministrarImg&pagina=1">Imagenes</a></li>
													<li><a
														href="${pageContext.servletContext.contextPath}/producto?op=AdministrarMateriales&pagina=1">Materiales</a></li>
													<li><a
														href="${pageContext.servletContext.contextPath}/plataforma?op=Listar&pagina=1">Plataformas</a></li>
													<li><a
														href="${pageContext.servletContext.contextPath}/usuario?op=Listar&pagina=1">Usuarios</a></li>
												</ul></li>
										</c:if>
									</ul>
								</div>
								<div class="menu_section">
									<h3>
										<font style="vertical-align: inherit;">General </font>
									</h3>
									<ul class="nav side-menu">
										<c:if test="${usuario.getIdRol() eq '4' or usuario.getIdRol() eq '5' }">
											<li><a
												href="${pageContext.servletContext.contextPath}/producto?op=AdministrarImg&pagina=1"><i
													class="fas fa-file-alt"></i> Imagenes</a></li>
										</c:if>
										<c:if
											test="${usuario.getIdRol() eq '2' or usuario.getIdRol() eq '3'}">
											<li><a
												href="${pageContext.servletContext.contextPath}/producto?op=Listar&pagina=1"><i
													class="fas fa-file-alt"></i> Consulta</a></li>
										</c:if>
										<c:if
											test="${usuario.getIdRol() eq '1' or usuario.getIdRol() eq '2' }">

											<li><a
												href="${pageContext.servletContext.contextPath}/Comunes/RegistroIndividual.jsp"><i
													class="fa fa-edit"></i><font
													style="vertical-align: inherit;">Carga individual</font></a></li>
											<li><a
												href="${pageContext.servletContext.contextPath}/Comunes/RegistroMasivo.jsp"><i
													class="fas fa-edit"></i> Carga masiva</a></li>
										</c:if>
										<c:if
											test="${usuario.getIdRol() eq '1' or usuario.getIdRol() eq '2' or usuario.getIdRol() eq '3'}">

											<li><a
												href="${pageContext.servletContext.contextPath}/Comunes/Reportes.jsp"><i
													class="fas fa-file-alt"></i> Reportes</a></li>

										</c:if>
									</ul>
								</div>

							</div>
							<!-- /sidebar menu -->

							<!-- /menu footer buttons -->
							<div class="sidebar-footer hidden-small">

								<a data-toggle="tooltip" data-placement="top" title=""
									href="/PCM_QA/Comunes/Login.jsp" data-original-title="Logout">
									<span class="glyphicon glyphicon-off" aria-hidden="true"></span>
								</a>
							</div>
							<!-- /menu footer buttons -->
						</div>
					</div>
				</div>
				<div id="mCSB_1_scrollbar_vertical"
					class="mCSB_scrollTools mCSB_1_scrollbar mCS-minimal mCSB_scrollTools_vertical"
					style="display: none;">
					<div class="mCSB_draggerContainer">
						<div id="mCSB_1_dragger_vertical" class="mCSB_dragger"
							style="position: absolute; min-height: 50px; display: block; height: 458px; max-height: 609px; top: 0px;"
							oncontextmenu="return false;">
							<div class="mCSB_dragger_bar" style="line-height: 50px;"></div>
						</div>
						<div class="mCSB_draggerRail"></div>
					</div>
				</div>
			</div>

			<!-- top navigation -->
			<div class="top_nav">
				<div class="nav_menu">
					<div class="nav toggle">
						<a id="menu_toggle"><i class="fa fa-bars"></i></a>
					</div>
					<nav class="nav navbar-nav">
						<ul class=" navbar-right">
							<li class="nav-item dropdown open" style="padding-left: 15px;">
								<a href="javascript:;" class="user-profile dropdown-toggle"
								aria-haspopup="true" id="navbarDropdown" data-toggle="dropdown"
								aria-expanded="false"> <img
									src="${pageContext.request.contextPath}/Images/user.png" alt=""><font
									style="vertical-align: inherit;">Perfil </font></a>
								<div class="dropdown-menu dropdown-usermenu pull-right"
									aria-labelledby="navbarDropdown">
									<a class="dropdown-item" href="/PCM_QA/usuario?op=Cuenta">Perfil</a>
									<a class="dropdown-item" href="/PCM_QA/Comunes/Login.jsp">Salir</a>
								</div>
							</li>
						</ul>
					</nav>
				</div>
			</div>
			<!-- /top navigation -->
			<!-- page content -->

			<!-- /page content -->
			<!-- footer content -->
			<footer>
				<div class="pull-right">
					<font style="vertical-align: inherit;"> PCM - MERZA </font>
				</div>
				<div class="clearfix"></div>
			</footer>
			<!-- /footer content -->
		</div>
	</div>
	<!-- jQuery -->
	<script
		src="${pageContext.request.contextPath}/vendors/jquery/dist/jquery.min.js"></script>
	<!-- Bootstrap -->
	<script
		src="${pageContext.request.contextPath}/vendors/bootstrap/dist/js/bootstrap.bundle.min.js"></script>

	<script
		src="${pageContext.request.contextPath}/vendors/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
	<!-- FastClick -->
	<script
		src="${pageContext.request.contextPath}/vendors/fastclick/lib/fastclick.js"></script>
	<!-- NProgress -->
	<script
		src="${pageContext.request.contextPath}/vendors/nprogress/nprogress.js"></script>
	<!-- jQuery custom content scroller -->
	<script
		src="${pageContext.request.contextPath}/vendors/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.concat.min.js"></script>
	<script src="${pageContext.request.contextPath}/Css/js/custom.js"></script>
	<!-- 	Autocompletado -->
	<script
		src="${pageContext.request.contextPath}/Autocompletado/awesomplete.min.js"></script>

</body>
</html>
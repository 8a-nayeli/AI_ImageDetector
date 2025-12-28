<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String url = "";
if (session.getAttribute("usuario") == null) {
	//no existe una sesion anterior
	url = "/Comunes/Login.jsp";
	response.sendRedirect(pageContext.getServletContext().getContextPath() + url);
}
%>
<!DOCTYPE html>
<html>
<head>


<title>Administracion marcas</title>

<meta charset="ISO-8859-1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!--icon-->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css"
	rel="stylesheet">
<!-- Bootstrap-->
<link
	href="<c:out value="${pageContext.servletContext.contextPath}"/>/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<script type="text/javascript"
	src="<c:out value="${pageContext.servletContext.contextPath}"/>/vendor/jquery/jquery.js"></script>
<script type="text/javascript"
	src="<c:out value="${pageContext.servletContext.contextPath}"/>/vendor/bootstrap/js/bootstrap.min.js"></script>
<!--JS-->
<script type="text/javascript"
	src="<c:out value="${pageContext.servletContext.contextPath}"/>/js/funciones.js"></script>
<script type="text/javascript"
	src="<c:out value="${pageContext.servletContext.contextPath}"/>/js/nav.js"></script>
<!-- CSS-->
<link rel="stylesheet" type="text/css"
	href="<c:out value="${pageContext.servletContext.contextPath}"/>/Css/nav.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
<style type="text/css">
label {
	color: white;
}
</style>

</head>
<body>
	<c:if test="${not empty success}">
		<script>
			$(document).ready(function() {
				Swal.fire('LISTO', '${success}', 'success');
			});
		</script>
	</c:if>
	<c:if test="${not empty error}">
		<script>
			$(document).ready(function() {
				Swal.fire('ERROR', '${error}', 'error');
			});
		</script>
	</c:if>
	<div class="page-wrapper chiller-theme toggled">
		<a id="show-sidebar" class="btn btn-sm btn-dark" href="#"> <i
			class="fas fa-bars"></i>
		</a>
		<nav id="sidebar" class="sidebar-wrapper">
			<div class="sidebar-content">
				<div class="sidebar-brand">
					<a
						href="${pageContext.servletContext.contextPath}/producto?op=Listar&pagina=1">HOME</a>
					<div id="close-sidebar">
						<i class="fas fa-times"></i>
					</div>
				</div>
				<div class="sidebar-header">
					<div class="user-pic">
						<img class="img-responsive img-rounded"
							src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/Logo2.png"
							alt="User picture">
					</div>
					<div class="user-info">
						<span class="user-name"> <strong>${usuario.getUsuario()}</strong>
						</span> <span class="user-role">${usuario.getCorreo()}</span> <span
							class="user-status"> <i class="fa fa-circle"></i> <span>Online</span>
						</span>
					</div>
				</div>
				<!-- sidebar-header  -->
				<div class="sidebar-search">
					<div>
						<div class="input-group">
							<input id="ibuscador" name="ibuscador  type="
								text" 
							class="form-control search-menu"
								placeholder="Marca...">
							<div class="input-group-append">
								<a id="abuscador" style="color: white;"
									onclick="getUsuarioBuscador('ibuscador','abuscador','marca')"
									class="btn btn-primary btn btn-primary btn-sm"><i
									class="fa fa-search" style="font-size: 12px; color: white"></i></a>
							</div>
						</div>


					</div>
				</div>
				<!-- sidebar-search  -->
				<div class="sidebar-menu">
					<ul>
						<c:if test="${usuario.getIdRol() eq '1'}">

							<li class="header-menu"><span>Gestión</span></li>
							<li class="sidebar-dropdown"><a href="#"> <i
									class="fa fa-cog"></i> <span>Administración</span>
							</a>
								<div class="sidebar-submenu">
									<ul>
										<li><a
											href="${pageContext.servletContext.contextPath}/atributo?op=Listar&pagina=1">Atributos
										</a></li>
										<li><a
											href="${pageContext.servletContext.contextPath}/familia?op=Listar&pagina=1">Familias</a>
										</li>
										<li><a
											href="${pageContext.servletContext.contextPath}/producto?op=AdministrarImg&pagina=1">Imagenes</a>
										</li>
										<!-- 										<li><a -->
										<%-- 											href="${pageContext.servletContext.contextPath}/marca?op=Listar&pagina=1">Marcas</a> --%>
										<!-- 										</li> -->
										<li><a
											href="${pageContext.servletContext.contextPath}/producto?op=AdministrarMateriales&pagina=1">Materiales</a>
										</li>
										<li><a
											href="${pageContext.servletContext.contextPath}/plataforma?op=Listar&pagina=1">Plataformas</a>
										</li>
										<li><a
											href="${pageContext.servletContext.contextPath}/usuario?op=Listar&pagina=1">Usuarios</a>
										</li>
									</ul>
								</div></li>
						</c:if>
						<li class="header-menu"><span>General</span></li>
						<c:if
							test="${usuario.getIdRol() eq '1' or usuario.getIdRol() eq '2'}">
							<li><a
								href="${pageContext.servletContext.contextPath}/Comunes/RegistroIndividual.jsp">
									<i class="fa fa-clipboard"></i> <span>Carga invididual</span>
							</a></li>
							<li><a
								href="${pageContext.servletContext.contextPath}/Comunes/RegistroMasivo.jsp">
									<i class="fa fa-clipboard"></i> <span>Carga masiva</span>
							</a></li>

						</c:if>
						<c:if
							test="${usuario.getIdRol() eq '1' or usuario.getIdRol() eq '2' or usuario.getIdRol() eq '3'}">
							<li><a
								href="${pageContext.servletContext.contextPath}/Comunes/Reportes.jsp">
									<i class="fa fa-clipboard"></i> <span>Reportes</span>
							</a></li>
						</c:if>
						<li class="header-menu"><span>Cuenta</span></li>
						<li><a href="/PCM/usuario?op=Cuenta"> <i
								class="fa fa-user"></i> <span>Cuenta</span>
						</a></li>

					</ul>
				</div>
				<!-- sidebar-menu  -->
			</div>
			<!-- sidebar-content  -->
			<div class="sidebar-footer">

				<a href="/PCM/usuario?op=Salir"> <i
					class="fa fa-power-off"></i>
				</a>
			</div>
		</nav>
		<!-- sidebar-wrapper  -->
		<main class="page-content">
			<div class="container-fluid">
				<!--     COMIENZA EL BODY -->
				<div class="container register">
					<a class="text-primary" data-toggle="modal" href="#modalNuevaMarca"
						style="font-size: large;"> Marca <img style="float: left;"
						src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/add.png">
					</a> <br>


					<div class="d-flex justify-content-center">
						<div class="col-md-6">
							<div class="table-responsive table-hover">
								<table class="table table-sm">
									<thead class="thead-dark">
										<tr>
											<th>ID Marca</th>
											<th>Marca</th>
											<th>Estatus</th>
											<th>Acciones</th>

										</tr>
									</thead>

									<tbody BGCOLOR="white" class="" id="myTable">
										<c:forEach var="dato" items="${datos}">

											<tr>
												<td style="font-size: small;">${dato.getIdMarca()}</td>
												<td style="font-size: small;">${dato.getNombre()}</td>
												<c:if test="${dato.getEstatus() eq 'A'}">
													<td style="font-size: small;">Activa</td>
												</c:if>
												<c:if test="${dato.getEstatus() eq 'I'}">
													<td style="font-size: small;">Inactiva</td>
												</c:if>

												<td><input type="hidden" name="id"
													value="${dato.getIdMarca()}"> <i
													class="fa fa-pencil-square-o"
													style="font-size: 24px; color: blue"
													onclick="editarMarca('${dato.getIdMarca()}')"></i></td>

											</tr>

										</c:forEach>

									</tbody>
								</table>
							</div>


						</div>
					</div>

					<%
		//Aqui tendremos la paginacion
		%>
					<nav aria-label="paginacion producto">
						<ul class="pagination justify-content-center">
							<c:if test="${pagina eq 1}">
								<li class="page-item disabled"><a class="page-link"
									href="${pageContext.servletContext.contextPath}/marca?op=Listar&pagina=${pagina-1}">Previous</a>
								</li>
							</c:if>
							<c:if test="${pagina > 1}">
								<li class="page-items"><a id="idprevious" class="page-link"
									href="${pageContext.servletContext.contextPath}/marca?op=Listar&pagina=${pagina-1}">Previous</a>
								</li>
							</c:if>
							<li class="page-item"><a class="page-link">Pagina
									${pagina}</a></li>
							<li class="page-item"><a id="idnext" class="page-link"
								href="${pageContext.servletContext.contextPath}/marca?op=Listar&pagina=${pagina+1}">Next</a>
							</li>
						</ul>
					</nav>
				</div>



				<footer class="text-center">
					<div class="mb-2">
						<small> © Grupo Merza </small>
					</div>
				</footer>
			</div>
		</main>
		<!-- page-content" -->
	</div>


	<!-- Modal nueva marca-->
	<div class="modal fade" id="modalNuevaMarca" data-backdrop="static"
		tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header"
					style="background: -webkit-linear-gradient(left, #1143a6, #00c6ff)">
					<h4 class="modal-title" style="color: white">Nueva marca</h4>
					<button type="button" class="close" onclick="limpiarFormMarca()"
						data-dismiss="modal">&times;</button>
				</div>

				<form id="registro"
					action="${pageContext.servletContext.contextPath}/marca?op=registro"
					method="post">
					<div class="modal-body">
						<div class="row">
							<div class="row" style="padding-right: 60px; padding-left: 60px;">
								<div class="col-xs-4"
									style="padding-right: 20px; padding-left: 20px;">
									<label style="color: black;">Nombre</label> <input type="text"
										name="marcaNueva" id="marcaNueva" class="form-control"
										placeholder="Nombre de marca" value="" required> <label
										style="color: black;">ID Marca</label> <input type="text"
										name="idMarca" id="marcaNueva" class="form-control"
										placeholder="ID marca" value="" required>
								</div>
							</div>

							<br>

						</div>
					</div>
					<div class="modal-footer">
						<button type="button" onclick="limpiarFormMarca()"
							class="btn btn-danger" data-dismiss="modal">
							<i class="fa fa-close" style="font-size: 48x; color: white"></i>
						</button>
						<button type="submit" style="background: green;"
							class="btn btn-sucess">
							<i class="fa fa-save" style="font-size: 48x; color: white"></i>
						</button>

						<!-- 						<button type="button" onclick="limpiarFormMarca()" -->
						<!-- 							class="btn btn-danger" data-dismiss="modal">Cerrar</button> -->
						<!-- 						<button type="submit" -->
						<!-- 							style="background: -webkit-linear-gradient(left, #1143a6, #00c6ff)" -->
						<!-- 							class="btn btn-info">Registrar</button> -->
					</div>
				</form>

			</div>
		</div>
	</div>


	<!-- Modal editar marca-->
	<div class="modal" id="modalModificacionMarca">
		<div class="modal-dialog">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header"
					style="background: -webkit-linear-gradient(left, #1143a6, #00c6ff)">
					<h4 class="modal-title" style="color: white">Edición</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<form id="ModificacionMarca"
					action="${pageContext.servletContext.contextPath}/marca?op=Modificar"
					method="post">
					<!-- Modal body -->
					<div class="modal-body">
						<div class="row" style="padding-right: 60px; padding-left: 60px;">
							<div class="col-xs-4"
								style="padding-right: 20px; padding-left: 20px;">
								<label style="color: black;">Marca</label> <input type="text"
									name="nombre_marca_editar" id="nombre_marca_editar"
									class="form-control" value="" required="required" readonly>
								<input type="hidden" name="idMarca_editar" id="idMarca_editar"
									class="form-control" value="" readonly>

							</div>

						</div>
						<div class="row" style="padding-right: 60px; padding-left: 60px;">
							<div class="col-xs-4"
								style="padding-right: 20px; padding-left: 20px;">

								<label style="color: black;">Estatus</label> <select
									name="estatus_editar" id="estatus_editar" class="form-control">
									<option>Activo</option>
									<option>Inactivo</option>
								</select>

							</div>


						</div>



					</div>

					<!-- Modal footer -->
					<div class="modal-footer">

						<button type="button" class="btn btn-danger" data-dismiss="modal">
							<i class="fa fa-close" style="font-size: 48x; color: white"></i>
						</button>
						<button type="submit" style="background: green;"
							class="btn btn-sucess">
							<i class="fa fa-save" style="font-size: 48x; color: white"></i>
						</button>

						<!-- 						<button type="submit" -->
						<!-- 							style="background: -webkit-linear-gradient(left, #1143a6, #00c6ff)" -->
						<!-- 							class="btn btn-info">Guardar</button> -->
						<!-- 						<button type="button" class="btn btn-danger" data-dismiss="modal">Cerrar</button> -->
					</div>
				</form>


			</div>
		</div>
	</div>





</body>
</html>
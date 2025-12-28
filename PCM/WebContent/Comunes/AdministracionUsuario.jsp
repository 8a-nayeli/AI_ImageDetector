<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
<meta charset="ISO-8859-1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Administración usuarios</title>
	<script
		src="${pageContext.request.contextPath}/vendors/jquery/dist/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.js"></script>

<jsp:include page="Master.jsp" />

</head>
<body>

	<c:if test="${not empty success}">
		<script>
			$(document).ready(function() {
				Swal.fire('LISTO', '${success}', 'success');
			});
		</script>
	</c:if>


	<div class="container body">
		<div class="main_container">
			<div class="right_col" role="main" style="min-height: 572px;">
				<div class="x_panel">
					<div>
						<a class="text-primary" data-toggle="modal" href="#modalNuevoUser"
							style="font-size: 10 px;">Nuevo </a>

					</div>
					<div class="x_content row justify-content-center">
						<div class="col-12 justify-content-center">
							<div class="x_panel">
								<div class="row justify-content-end">
									<div class="col-sm-4">
										<div class="input-group">
											<input id="ibuscador" name="ibuscador"  type="text" 
												class="form-control search-menu"
												placeholder="Nombre...">
											<div class="input-group-append">
												<a id="abuscador" style="color: white;"
													onclick="getUsuarioBuscador('ibuscador','abuscador','usuario')"
													class="btn-success btn-sm"><i
													class="fa fa-search" style="font-size: 12px; color: white"></i></a>
											</div>
										</div>


									</div>
								</div>

								<div class="row justify-content-end"></div>
								<div class="x_content">
									<div class="row">

										<div class="col-md-12 col-sm-12 ">
											<div class="x_panel">
												<div class="x_title">
													<h7>Consulta</h7>
													<div>
														<div class="container register">

															<div class="row">
																<div class="col-sm-12">
																	<div class="">

																		<div id="datatable_wrapper"
																			class="dataTables_wrapper container-fluid dt-bootstrap no-footer">

																			<div class="row">
																				<div class="col-sm-12">
																					<table class="table table-sm">
																						<thead class="thead-light">

																							<tr>
																								<th>Nombre</th>
																								<th>Correo</th>
																								<th>Rol</th>
																								<th>Estatus</th>
																								<th>Acciones</th>

																							</tr>
																						</thead>

																						<tbody BGCOLOR="white" class="" id="myTable">
																							<c:forEach var="dato" items="${datos}">


																								<tr>
																									<td style="font-size: small;">${dato.getUsuario()}</td>
																									<td style="font-size: small;">${dato.getCorreo()}</td>
																									<c:if test="${dato.getIdRol() eq '1'}">
																										<td style="font-size: small;">Administrador</td>
																									</c:if>
																									<c:if test="${dato.getIdRol() eq '2'}">
																										<td style="font-size: small;">Editor</td>
																									</c:if>
																									<c:if test="${dato.getIdRol() eq '3'}">
																										<td style="font-size: small;">Lector</td>
																									</c:if>
																									<c:if test="${dato.getIdRol() eq '4'}">
																										<td style="font-size: small;">Diseño</td>
																									</c:if>
																									<c:if test="${dato.getEstatus() eq 'A'}">
																										<td style="font-size: small;">Activo</td>
																									</c:if>
																									<c:if test="${dato.getEstatus() eq 'I'}">
																										<td style="font-size: small;">Inactivo</td>
																									</c:if>

																									<td><input type="hidden" name="id"
																										value="${dato.getIdUsuario()}">
																										<div id="${dato.getIdUsuario()}">

																											<i class="fa fa-pencil-square-o"
																												onclick="editar_Empleado('${dato.getIdUsuario()}')"
																												style="font-size: 24px; color: lightseagreen"></i>
																										</div></td>
																								</tr>

																							</c:forEach>

																						</tbody>
																					</table>

																					<nav aria-label="paginacion producto">
																						<ul class="pagination justify-content-center">
																							<c:if test="${pagina eq 1}">
																								<li class="page-item disabled"><a
																									class="page-link"
																									href="${pageContext.servletContext.contextPath}/usuario?op=Listar&pagina=${pagina-1}">Previous</a>
																								</li>
																							</c:if>
																							<c:if test="${pagina > 1}">
																								<li class="page-items"><a id="idprevious"
																									class="page-link"
																									href="${pageContext.servletContext.contextPath}/usuario?op=Listar&pagina=${pagina-1}">Previous</a>
																								</li>
																							</c:if>
																							<li class="page-item"><a class="page-link">Pagina
																									${pagina}</a></li>
																							<li class="page-item"><a id="idnext"
																								class="page-link"
																								href="${pageContext.servletContext.contextPath}/usuario?op=Listar&pagina=${pagina+1}">Next</a>
																							</li>
																						</ul>
																					</nav>
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>


						</div>
					</div>

				</div>
			</div>
		</div>
	</div>


	<!-- Modal Info de rol-->
	<div class="modal" id="modalRol">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">Permisos</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>

				<!-- Modal body -->
				<div class="modal-body">
					<div class="table-responsive table-bordered table-striped">
						<table class="table table-sm">
							<thead class="thead-dark">

								<tr>
									<th>ROL/PERMISOS</th>
									<th>Administración atributos</th>
									<th>Administración familias</th>
									<th>Administración imagenes</th>
									<th>Administración materiales</th>
									<th>Administración plataformas</th>
									<th>Administración usuarios</th>
									<th>Carga invidual</th>
									<th>Carga masiva imagenes</th>
									<th>Carga masiva materiales</th>
									<th>Consulta de materiales</th>
									<th>Reporte</th>
									<th>Cuenta</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>LECTOR</td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/tache.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/tache.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/tache.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/tache.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/tache.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/tache.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/tache.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/tache.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/tache.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/palomita.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/palomita.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/palomita.png"></td>

								</tr>
								<tr>
									<td>EDITOR</td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/tache.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/tache.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/tache.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/tache.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/tache.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/tache.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/palomita.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/palomita.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/palomita.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/palomita.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/palomita.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/palomita.png"></td>
								</tr>
								<tr>
									<td>ADMINISTRADOR</td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/palomita.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/palomita.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/palomita.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/palomita.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/palomita.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/palomita.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/palomita.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/palomita.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/palomita.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/palomita.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/palomita.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/palomita.png"></td>

								</tr>
								<tr>
									<td>DISEÑO</td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/tache.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/tache.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/palomita.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/tache.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/tache.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/tache.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/tache.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/palomita.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/tache.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/palomita.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/palomita.png"></td>
									<td><img
										src="<c:out value="${pageContext.servletContext.contextPath}"/>/Imagenes/palomita.png"></td>

								</tr>

							</tbody>
						</table>

					</div>
				</div>

				<!-- Modal footer -->
				<div class="modal-footer">
					<button type="button" class="btn btn-danger btn-sm"
						data-dismiss="modal">Cerrar</button>
				</div>

			</div>
		</div>
	</div>

	<!-- Modal nuevo user-->
	<div class="modal fade" id="modalNuevoUser" data-backdrop="static"
		tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Registro</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>

				<form method="post">

					<div class="modal-body">
						Datos de cuenta<br>
						<div class="form-group">
							<div class="row">
								<div class="col-xs-4"
									style="padding-right: 18px; padding-left: 18px;">
									<label> Correo</label> <input type="text" class="form-control"
										onkeypress="return sin_caracteres_especiales(event)"
										name="correo_nuevo" id="correo_nuevo" required>
								</div>
								<div class="col-xs-5" style="line-height: 30px">
									<br> <span class="input-group-text">@merza.com</span>

								</div>
							</div>

						</div>

						<div class="form-grup">
							<div class="row">
								<div class="col-xs-4"
									style="padding-right: 18px; padding-left: 18px;">
									<label> Permiso</label> <label>Rol</label> <select name="rol"
										id="rol" class="form-control" required>
										<option value="0">SELECCIONE ROL</option>
										<option value="4">Diseño</option>
										<option value="3">Lector</option>
										<option value="2">Editor</option>
										<option value="1">Administrador</option>
									</select>

								</div>
								<!-- 								<div class="col-xs-5" style="line-height: 100px"> -->

								<!-- 									<button type="button" class="btn btn-warning" -->
								<!-- 										data-toggle="modal" data-target="#modalRol">?</button> -->
								<!-- 								</div> -->

							</div>
						</div>
						<input type="hidden" name="nombre_nuevo" id="nombre_nuevo"
							class="form-control" placeholder="Nombre de usuario" value=""
							readonly required>


					</div>

					<input type="hidden" name="password_nuevo" id="password_nuevo"
						value=""> <input type="hidden" name="id_permiso"
						id="id_permiso"> <input type="hidden" name="encriptacion"
						id="encriptacion">


					<div class="modal-footer">
						<button type="button" onclick="limpiarFormUsuario()"
							class="btn btn-danger btn-sm" data-dismiss="modal">
							<i class="fa fa-close" style="font-size: 48x; color: white"></i>
						</button>
						<button type="button" class="btn btn-success btn-sm"
							onclick="registro_nuevo_user()">
							<i class="fa fa-save" style="font-size: 48x; color: white"></i>
						</button>
					</div>
				</form>

			</div>
		</div>
	</div>


	<!-- Modal editar usuario-->
	<div class="modal" id="modalModificacionUser">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">Edición</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<form id="ModificacionUsuario"
					action="${pageContext.servletContext.contextPath}/usuario?op=Modificar"
					method="post">
					<!-- Modal body -->
					<div class="modal-body">
						<input type="hidden" name="aux" id="aux" value="">
						<div class="row" style="padding-right: 60px; padding-left: 60px;">

							<div class="col-xs-4"
								style="padding-right: 20px; padding-left: 20px;">
								<label>Nombre de usuario</label> <input type="text"
									name="nombre_usuario_editar" id="nombre_usuario_editar"
									class="form-control" value="${datosUsuario.getNombre()}"
									readonly> <input type="hidden" name="id_usuario_editar"
									id="id_usuario_editar" class="form-control"
									value="${datosUsuario.getIdUsuario()}" readonly>

							</div>


							<div class="col-xs-4"
								style="padding-right: 20px; padding-left: 20px">
								<label> Correo</label> <input type="text" class="form-control"
									name="correo_editar" id="correo_editar"
									value="${datosUsuario.getCorreo()}" readonly>
							</div>
						</div>
						<div class="row" style="padding-right: 60px; padding-left: 60px;">
							<div class="col-xs-4"
								style="padding-right: 20px; padding-left: 20px;">

								<label>Estatus</label> <select name="estatus_editar"
									id="estatus_editar" class="form-control"
									onchange="editarUsuarioCambio();">
									<option>Activo</option>
									<option>Inactivo</option>
								</select>

							</div>

							<div class="col-xs-4"
								style="padding-right: 20px; padding-left: 20px;">

								<label>Rol</label> <select name="rol_editar" id="rol_editar"
									class="form-control" onchange="editarUsuarioCambio();">
									<option value="1">Administrador</option>
									<option value="2">Editor</option>
									<option value="3">Lector</option>
									<option value="4">Diseño</option>
								</select>

							</div>
							<div class="col-xs-4"
								style="padding-right: 20px; padding-left: 20px;">

								<label>Alertas de imagenes</label> <select name="alertas"
									id="alertas" class="form-control"
									onchange="editarUsuarioCambio();">
									<option value="1">Activadas</option>
									<option value="0">Desactivadas</option>

								</select>

							</div>

							<!-- 							<div class="col-xs-4" -->
							<!-- 								style="padding-right: 20px; padding-left: 5px;"> -->
							<!-- 								<br> -->
							<!-- 								<button type="button" class="btn btn-warning" -->
							<!-- 									data-toggle="modal" data-target="#modalRol">?</button> -->
							<!-- 							</div> -->

						</div>



					</div>

					<!-- Modal footer -->
					<div class="modal-footer">
						<button type="button" class="btn btn-danger btn-sm"
							data-dismiss="modal">
							<i class="fa fa-close" style="font-size: 48x; color: white"></i>
						</button>


						<button type="button" class="btn btn-success btn-sm"
							onclick="editarUsuario();">
							<i class="fa fa-save" style="font-size: 48x; color: white"></i>
						</button>


					</div>
				</form>


			</div>
		</div>
	</div>



</body>
</html>
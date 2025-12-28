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
<title>Administración de plataformas</title>

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
	<c:if test="${not empty error}">
		<script>
			$(document).ready(function() {
				Swal.fire('ERROR', '${error}', 'error');
			});
		</script>
	</c:if>


	<div class="container body">
		<div class="main_container">
			<div class="right_col" role="main" style="min-height: 572px;">
				<div class="x_panel">
					<div>
						<a class="text-primary" data-toggle="modal"
							href="#modalNuevaPlataforma" style="font-size: 10 px;">Nuevo
						</a>

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
													onclick="getUsuarioBuscador('ibuscador','abuscador','plataforma')"
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
																								<th>ID</th>
																								<th>Plataforma</th>
																								<th>Estatus</th>
																								<th>Acciones</th>

																							</tr>
																						</thead>

																						<tbody BGCOLOR="white" class="" id="myTable">
																							<c:forEach var="dato" items="${datos}">


																								<tr>
																									<td style="font-size: small;">${dato.getIdPlataforma()}</td>
																									<td style="font-size: small;">${dato.getNombre()}</td>
																									<c:if test="${dato.getEstatus() eq 'A'}">
																										<td style="font-size: small;">Activa</td>
																									</c:if>
																									<c:if test="${dato.getEstatus() eq 'I'}">
																										<td style="font-size: small;">Inactiva</td>
																									</c:if>

																									<td><input type="hidden" name="id"
																										value="${dato.getIdPlataforma()}"> <%-- 										<div id="${dato.getIdPlataforma()}"> --%>
																										<!-- 											<button type="button" class="btn btn-light" -->
																										<%-- 												onclick="editarPlataforma('${dato.getIdPlataforma()}')">Editar</button> --%>
																										<!-- 										</div> --> <i
																										class="fa fa-pencil-square-o"
																										style="font-size: 24px; color: lightseagreen"
																										onclick="editarPlataforma('${dato.getIdPlataforma()}')"></i></td>
																								</tr>

																							</c:forEach>

																						</tbody>
																					</table>

																					<nav aria-label="paginacion producto">
																						<ul class="pagination justify-content-center">
																							<c:if test="${pagina eq 1}">
																								<li class="page-item disabled"><a
																									class="page-link"
																									href="${pageContext.servletContext.contextPath}/plataforma?op=Listar&pagina=${pagina-1}">Previous</a>
																								</li>
																							</c:if>
																							<c:if test="${pagina > 1}">
																								<li class="page-items"><a id="idprevious"
																									class="page-link"
																									href="${pageContext.servletContext.contextPath}/plataforma?op=Listar&pagina=${pagina-1}">Previous</a>
																								</li>
																							</c:if>
																							<li class="page-item"><a class="page-link">Pagina
																									${pagina}</a></li>
																							<li class="page-item"><a id="idnext"
																								class="page-link"
																								href="${pageContext.servletContext.contextPath}/plataforma?op=Listar&pagina=${pagina+1}">Next</a>
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



	<!-- Modal nueva plataforma-->
	<div class="modal fade" id="modalNuevaPlataforma"
		data-backdrop="static" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Nueva plataforma</h4>
					<button type="button" class="close"
						onclick="limpiarFormPlataforma()" data-dismiss="modal">&times;</button>
				</div>
				<form id="registroPlataforma"
					action="${pageContext.servletContext.contextPath}/plataforma?op=registro"
					method="post">
					<div class="modal-body">
						<div class="row">

							<div class="col-6">
								<label>Plataforma</label> <input type="text"
									name="plataformaNueva" id="plataformaNueva"
									class="form-control" placeholder="Nombre de plataforma"
									value="" required>
							</div>
							<div class="col-6">
								<label>Tipo</label> <select name="tipoPlataforma"
									id="tipoPlataforma" class="form-control">
									<option value="0">Tipo de plataforma</option>
									<option value="dis">Web</option>
									<option value="app">Móvil</option>
								</select>
							</div>


						</div>



					</div>
					<div class="modal-footer">
						<!-- 						<button type="button" onclick="limpiarFormPlataforma()" -->
						<!-- 							class="btn btn-danger" data-dismiss="modal">Cerrar</button> -->
						<!-- 						<button type="submit" -->
						<!-- 							style="background: -webkit-linear-gradient(left, #1143a6, #00c6ff)" -->
						<!-- 							class="btn btn-info">Registrar</button> -->



						<button type="button" onclick="limpiarFormPlataforma()"
							class="btn btn-danger bt-sm" data-dismiss="modal">
							<i class="fa fa-close" style="font-size: 48x; color: white"></i>
						</button>
						<button type="submit" 
							class="btn btn-success bt-sm">
							<i class="fa fa-save" style="font-size: 48x; color: white"></i>
						</button>


					</div>
				</form>

			</div>
		</div>
	</div>


	<!-- Modal editar plataforma-->
	<div class="modal" id="modalModificacionPlataforma">
		<div class="modal-dialog">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">Edición</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<form id="ModificacionPlataforma"
					action="${pageContext.servletContext.contextPath}/plataforma?op=Modificar"
					method="post">
					<!-- Modal body -->
					<div class="modal-body">
						<div class="row" style="padding-right: 60px; padding-left: 60px;">
							<div class="col-6
								style="padding-right: 20px; padding-left: 20px;">
								<label>Plataforma</label> <input type="text"
									name="nombre_plataforma_editar" id="nombre_plataforma_editar"
									class="form-control" value="" readonly> <input
									type="hidden" name="idPlataforma_editar"
									id="idPlataforma_editar" class="form-control" value="" readonly>
							</div>
							<div class="col-6"
								style="padding-right: 20px; padding-left: 20px;">

								<label>Estatus</label> <select name="estatus_editar"
									id="estatus_editar" class="form-control">
									<option value="A">Activo</option>
									<option value="I">Inactivo</option>
								</select>


							</div>
							<div class="col-6"
								style="padding-right: 20px; padding-left: 20px;">
								<label>Tipo de plataforma</label> <select name="tipoPlataformaE"
									id="tipoPlataformaE" class="form-control">
									<option value="dis">Web</option>
									<option value="app">Móvil</option>
								</select>
							</div>

						</div>
					</div>
					<!-- Modal footer -->
					<div class="modal-footer">
						<button type="button" class="btn btn-danger btn-sm"
							data-dismiss="modal">
							<i class="fa fa-close" style="font-size: 48x; color: white"></i>
						</button>
						<button type="submit" class="btn btn-success btn-sm">
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
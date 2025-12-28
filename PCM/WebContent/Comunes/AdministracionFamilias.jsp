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
<title>Administración de familias</title>

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
						<a class="text-primary" data-toggle="modal"
							onclick="atributosExistentes()" style="font-size: 10 px;">Nuevo
						</a>

					</div>
					<div class="x_content row justify-content-center">



						<div class="col-12 justify-content-center">
							<div class="x_panel">
								<div class="row justify-content-end">
									<div class="col-sm-4">
										<div class="input-group">
											<input id="ibuscador" name="ibuscador" type="text"
												class="form-control search-menu" placeholder="Nombre...">
											<div class="input-group-append">
												<a id="abuscador" style="color: white;"
													onclick="getUsuarioBuscador('ibuscador','abuscador','familia')"
													class="btn-success btn-sm"><i class="fa fa-search"
													style="font-size: 12px; color: white"></i></a>
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
																								<th>Identificador</th>
																								<th>Familia</th>
																								<th>Acciones</th>

																							</tr>
																						</thead>

																						<tbody BGCOLOR="white" class="" id="myTable">
																							<c:forEach var="dato" items="${datos}">


																								<tr>
																									<td style="font-size: small;">${dato.getIdFamilia()}</td>
																									<td style="font-size: small;">${dato.getFamilia()}</td>

																									<td style="font-size: small;"><input
																										type="hidden" name="id"
																										value="${dato.getIdFamilia()}">
																										<div id="${dato.getIdFamilia()}">
																											<i class="fa fa-pencil-square-o"
																												style="font-size: 24px; color: lightseagreen"
																												onclick="editarFamilia('${dato.getIdFamilia()}')"></i>
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
																									href="${pageContext.servletContext.contextPath}/familia?op=Listar&pagina=${pagina-1}">Previous</a>
																								</li>
																							</c:if>
																							<c:if test="${pagina > 1}">
																								<li class="page-items"><a id="idprevious"
																									class="page-link"
																									href="${pageContext.servletContext.contextPath}/familia?op=Listar&pagina=${pagina-1}">Previous</a>
																								</li>
																							</c:if>
																							<li class="page-item"><a class="page-link">Pagina
																									${pagina}</a></li>
																							<li class="page-item"><a id="idnext"
																								class="page-link"
																								href="${pageContext.servletContext.contextPath}/familia?op=Listar&pagina=${pagina+1}">Next</a>
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

	<!-- Modal nueva familia-->
	<div class="modal fade" id="modalNuevaFamilia" data-backdrop="static"
		tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Nueva familia</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>

				<form
					action="${pageContext.servletContext.contextPath}/familia?op=registro"
					method="post">

					<div class="modal-body">
						<div class="row">
							<div class="col-12"
								style="padding-right: 18px; padding-left: 18px;">
								<label>Numero de familia</label> <input type="text"
									name="idFamilia" id="idFamilia" class="form-control"
									placeholder="ID familia" value="" required
									onkeypress="return solo_numeros(event,'idFamilia','2')"
									maxlength="2">

							</div>

						</div>
						<label style="font-size: medium">Atributos</label>

						<div id="atributosExistentes" class="row"
							style="padding-right: 18px; padding-left: 18px;"></div>
					</div>
					<div class="modal-footer">

						<button type="button" onclick="limpiarFormFamilia()"
							class="btn btn-danger btn-sm" data-dismiss="modal">
							<i class="fa fa-close" style="font-size: 48x; color: white"></i>
						</button>
						<button type="button" class="btn btn-success btn-sm"
							onclick="registrarFamilia()">
							<i class="fa fa-save" style="font-size: 48x; color: white"></i>
						</button>
					</div>
				</form>

			</div>
		</div>
	</div>

	<!-- Modal editar Familia-->
	<div class="modal" id="modalModificacionFamilia">
		<div class="modal-dialog">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">Edición</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<form id="ModificacionFamilia"
					action="${pageContext.servletContext.contextPath}/familia?op=modificacion"
					method="post">
					<!-- Modal body -->
					<div class="modal-body">
						<div class="row" style="padding-right: 18px; padding-left: 18px;">
							<div class="col-12"
								style="padding-right: 20px; padding-left: 20px;">
								<label>Familia</label> <input type="text"
									name="nombre_familia_editar" id="nombre_familia_editar"
									class="form-control" value="" readonly> <input
									type="hidden" name="idFamilia_editar" id="idFamilia_editar"
									class="form-control" value="" readonly>
							</div>
							<div class="col-12"
								style="padding-right: 20px; padding-left: 20px;">

								<label>Estatus</label> <select name="estatus_editar"
									id="estatus_editar" class="form-control">
									<option value="A">Activo</option>
									<option value="I">Inactivo</option>
								</select>


							</div>

							<label style="font-size: medium">Atributos</label>


							<div id="atributos" class="row"
								style="padding-right: 18px; padding-left: 18px;"></div>

						</div>
					</div>
					<!-- Modal footer -->
					<div class="modal-footer">
						<button type="button" class="btn btn-danger btn-sm"
							data-dismiss="modal">
							<i class="fa fa-close" style="font-size: 48x; color: white"></i>
						</button>
						<button type="button" class="btn btn-success btn-sm"
							onclick="checarAtributosFamilia()">
							<i class="fa fa-save" style="font-size: 48x; color: white"></i>
						</button>

					</div>
				</form>


			</div>
		</div>
	</div>


</body>
</html>
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
<title>Consulta general</title>
	<script
		src="${pageContext.request.contextPath}/vendors/jquery/dist/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.js"></script>

<jsp:include page="Master.jsp" />
<style type="text/css">
.awesomplete>ul {
	font-size: 10px;
}

.awesomplete mark {
	background: rgb(73, 182, 128)
}

.awesomplete .visually-hidden {
	position: absolute;
	clip: rect(0, 0, 0, 0);
	color: white;
}
</style>
</head>
<body onload="body('${pagina}')">
	<c:if test="${not empty error}">
		<script>
			$(document).ready(function() {
				Swal.fire('Información', '${error}', 'info');
			});
		</script>
	</c:if>
	<c:if test="${not empty csv }">
		<script>
			$(document).ready(function() {
				Swal.fire('Materiales no agregados', '${csv}', 'info');
			});
		</script>
	</c:if>
	<c:if test="${not empty registro }">
		<script>
			$(document).ready(function() {
				Swal.fire('Registro exitoso', '${registro}', 'success');
			});
		</script>
	</c:if>

	<div class="container body">
		<div class="main_container">
			<div class="right_col" role="main" style="min-height: 572px;">
				<div class="x_panel">
					<div>
						<div class="alert alert-success alert-dismissible fade show"
							role="alert">
							Recarga la página o presiona f5 si se modificaron imágenes.
							<button type="button" class="close" data-dismiss="alert"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
					</div>

					<div class="x_content justify-content-center">
						<div class="x_panel">
							<div class="container">
								<div class="row">
									<div class="col-sm-3">
										<input id="idMaterialBuscador" style="font-size: 10px"
											name="idMaterial" type="text" data-toggle="tooltip"
											data-placement="top"
											title="ID MATERIAL: Buscar a partir de un id material o varios separados por coma (,)"
											class="form-control awesomplete" data-autofirst="false"
											onkeypress="if(event.key == 'Enter') {filtroImg(1)}"
											data-list="" placeholder="idMaterial">
									</div>
									<div class="col-sm-3">
										<input style="font-size: 10px"
											id="descripcionMaterialBuscador" data-toggle="tooltip"
											data-placement="top"
											title="Buscar a partir de una descripción"
											name="descripcionMaterialBuscador" type="text"
											data-autofirst="true" data-list=""
											onkeypress="if(event.key == 'Enter') {filtroImg(1)}"
											class="form-control awesomplete" placeholder="Descripción">
									</div>
									<div class="col-sm-3">
										<input id="estatusBuscador" style="font-size: 10px"
											name="estatusBuscador" type="text"
											class="form-control awesomplete" data-toggle="tooltip"
											data-placement="top"
											title="Estatus: Nuevo,Pendiente,Rechazado,Aceptado"
											data-autofirst="true"
											onkeypress="if(event.key == 'Enter') {filtroAdmin(1)}"
											data-list="Nuevo,Pendiente,Rechazado,Aceptado"
											placeholder="Estatus...">
									</div>
									<div class="col-sm-3">
										<input id="plataformaBuscador" style="font-size: 10px"
											name="plataformaBuscador" type="text" data-toggle="tooltip"
											data-placement="top"
											title="Busca a partir de Nombre plataforma"
											class="form-control  awesomplete" data-autofirst="true"
											onkeypress="if(event.key == 'Enter') {filtroAdmin(1)}"
											data-list="Merzava,Amazon,Mercado Libre,Prueba,Duerova,
										B2B,Cavaduero,B2C,Rappi,Mobil C,Wabi,MitiendaVa"
											placeholder="Plataforma...">
									</div>
									<div class="col-sm-3">
										<input id="familiaBuscador" style="font-size: 10px"
											name="familiaBuscador" type="text" data-toggle="tooltip"
											data-placement="top"
											title="Busca a partir de Descripción familia"
											class="form-control awesomplete" data-autofirst="true"
											onkeypress="if(event.key == 'Enter') {filtroImg(1)}"
											data-list="ABARROTES COMESTIBLES,ABARROTES NO COMESTIBLES,CARNES FRÍAS Y SALCHICHONERÍA,VINOS Y LICORES,FARMACIA,PERFUMERÍA,DULCERÍA,LÁCTEOS,OTRAS CATEGORÍAS"
											class="form-control awesomplete" placeholder="Familia">
									</div>

									<div class="col-sm-3">
										<input id="categoriaBuscador" style="font-size: 10px"
											name="categoriaBuscador"
											title="Busca a partir de Descripción categoría"
											onkeypress="if(event.key == 'Enter') {filtroImg(1)}"
											class="form-control awesomplete" placeholder="Categoria">
									</div>
									<div class="col-sm-3">
										<input id="proveedorBuscador" style="font-size: 10px"
											name="proveedorBuscador" type="text"
											title="Busca a partir de Descripción proveedor"
											onkeypress="if(event.key == 'Enter') {filtroImg(1)}"
											class="form-control  awesomplete" placeholder="Proveedor">
									</div>

								</div>
								<br>
								<div class="row justify-content-end">
									<div class="input-group-append">
										<a id="abuscador" style="color: white;"
											onclick="filtroAdmin(1)" class="btn-success btn-sm"><i
											class="fa fa-search" style="font-size: 12px; color: white"></i></a>
									</div>
									<div class="input-group-append">
										<a id="abuscador" style="color: white;"
											onclick="consultaGeneralAdmi()" class="btn-success btn-sm"><i
											class="fa fa-trash" style="font-size: 12px; color: white"></i></a>
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
																			<div class="col-sm-12 table-responsive"
																				id="tablaDinamica">
																				<table class="table table-sm">
																					<thead class="thead-light">

																						<tr>
																							<th>Número de material</th>
																							<th>Descripción material</th>
																							<th>Descripción familia</th>
																							<th>Estatus</th>
																							<th>Acciones</th>
																						</tr>
																					</thead>

																					<tbody id="myTable">
																						<c:forEach var="dato" items="${datos}">
																							<tr>
																								<td style="font-size: small;">${dato.getIdMaterial()}</td>
																								<td style="font-size: small;">${dato.getDescripcionMaterial()}</td>
																								<td style="font-size: small;">${dato.getDescripcionFamilia()}</td>
																								<c:choose>
																									<c:when test="${dato.getEstatus() eq 'A'}">
																										<td
																											style="background: lightgreen; color: black; font-size: small;">Aceptado</td>
																									</c:when>
																									<c:when test="${dato.getEstatus() eq 'P'}">
																										<td
																											style="background: lightyellow; color: black; font-size: small;">Pendiente</td>
																									</c:when>
																									<c:when test="${dato.getEstatus() eq 'N'}">
																										<td
																											style="background: lightblue; color: black; font-size: small;">Nuevo</td>
																									</c:when>
																									<c:when test="${dato.getEstatus() eq 'R'}">
																										<td
																											style="background: lightred; color: black; font-size: small;">Rechazado</td>
																									</c:when>
																									<c:when test="${dato.getEstatus() eq 'I'}">
																										<td
																											style="background: lightgray; color: black; font-size: small;">Inacivo</td>
																									</c:when>
																								</c:choose>
																								<td><input type="hidden" name="id"
																									value="${dato.getIdMaterial()}">
																									<div id="${dato.getIdMaterial()}">
																										<div id="${dato.getIdMaterial()}">
																											<c:choose>
																												<c:when
																													test="${dato.getEstatus() eq 'R' or dato.getEstatus() eq 'P' or dato.getEstatus() eq 'N'}">
																													<td><input type="hidden" name="id"
																														value="${dato.getIdMaterial()}">
																														<div id="${dato.getIdMaterial()}">

																															<div id="${dato.getIdMaterial()}">
																																<i class="fa fa-pencil-square-o"
																																	style="font-size: 24px; color: blue"
																																	onclick="editarMaterial('${dato.getIdMaterial()}')"></i>
																															</div>


																														</div></td>
																												</c:when>
																											</c:choose>

																										</div>
																									</div></td>
																							</tr>
																						</c:forEach>

																					</tbody>
																				</table>
																				<nav aria-label="paginacion producto">
																					<ul class="pagination justify-content-center">
																						<%-- 							<c:if test="${pagina eq 1}"> --%>
																						<!-- 								<li class="page-item disabled"><a class="page-link" -->
																						<%-- 									href="${pageContext.servletContext.contextPath}/producto?op=Listar&pagina=${pagina-1}">Previous</a> --%>
																						<!-- 								</li> -->
																						<%-- 							</c:if> --%>
																						<%-- 							<c:if test="${pagina > 1}"> --%>
																						<li class="page-items"><a id="idprevious"
																							class="page-link"
																							href="${pageContext.servletContext.contextPath}/producto?op=Listar&pagina=${pagina-1}">Previous</a>
																						</li>
																						<%-- 							</c:if> --%>
																						<li class="page-item"><a class="page-link"
																							id="actual">Pagina ${pagina}</a></li>
																						<li class="page-item"><a id="idnext"
																							class="page-link"
																							href="${pageContext.servletContext.contextPath}/producto?op=Listar&pagina=${pagina+1}">Next</a>
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



	<!-- MODAL PARA MOSTRAR LAS IMAGENES -->
	<div class="modal fade" id="modalImagen">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header"
					style="background: -webkit-linear-gradient(left, #1143a6, #00c6ff)">
					<h4 class="modal-title" style="color: white;">Imagen</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>

				<!-- Modal body -->
				<div class="modal-body" align="center" id="modal_div">
					<form id="formModalImagenEditar" enctype="multipart/form-data"
						action="${pageContext.servletContext.contextPath}/producto?op=EDITARIMAGEN"
						method="post">
						<img id="myImage" src="" style="max-width: 100%;"> <input
							type="hidden" id="nombre_e" name="nombre_e"> <br> <input
							id="editarmyImage" type="button" onclick="editarImagen('','');"
							value="Editar" class="btn btn-warning"> <input
							id="eliminarmyImage" type="button" value="Eliminar"
							onclick="eliminarImagen('','')" class="btn btn-danger"> <input
							type="file" name="evidencia_e" id="evidencia_e" required
							onchange="previewFile('myImage','evidencia_e','nombre_e')">

					</form>
				</div>


				<!-- Modal footer -->
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-dismiss="modal">Cerrar</button>
				</div>

			</div>
		</div>
	</div>
	<!-- Modal ampliar imagenes-->
	<div id="ampliarImg" class="modal" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header" id="encabezado">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body" id="img"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-dismiss="modal">Cerrar</button>
				</div>
			</div>
		</div>
	</div>

	<!-- MODAL PARA MODIFICACION -->
	<div class="modal fade" data-backdrop="static"
		id="modalModificacionMaterial">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">EDICIÓN</h4>
					<button type="button" class="close"
						onclick="limpiarFormAdmin('materialesImagenes','plataformasExistentes')"
						data-dismiss="modal">&times;</button>
				</div>

				<!-- Modal body -->
				<div class="modal-body" id="modal_div">
					<%
						//aqui empieza el cuerpo del modal
					%>
					<form id="idCambioEstatus"
						action="${pageContext.servletContext.contextPath}/producto?op=actualizarEstatus"
						method="post" enctype="multipart/form-data">

						<div class="form-group">
							<div class="row">
								<div class="col-2">
									<label>ID material</label> <input type="text"
										style="background-color: white; border: white;"
										name="idMaterialEditar" id="idMaterialEditar"
										class="form-control" required readonly value="">

								</div>
								<div class="col-5">
									<label>Descripción material</label> <input type="text"
										style="background-color: white; border: white;"
										descripcionMaterialEditar"
												id="descripcionMaterialEditar"
										class="form-control" required readonly value="">

								</div>
								<div class="col-5">
									<label>Descripción comercial </label> <input type="text"
										name="descripcionComercialE" id="descripcionComercialE"
										class="form-control" placeholder="Sin información..." value="">
								</div>
							</div>
							<div class="row"
								style="padding-right: 20px; padding-left: 20px; font-size: 14px;">

								<div class="container">


									<a href="#c-info" data-toggle="collapse"
										onclick="verMas('c-info-input','c-info-mas')"> <i
										id="c-info-mas" class="fa fa-caret-square-o-down"
										style="font-size: 24px; color: lightseagreen"></i> <input
										id="c-info-input" value="0" type=hidden>
									</a> <label style="font-size: large;">INFORMACIÓN GENERAL</label> <br>
									<div id="c-info" class="collapse">

										<div class="row">
											<div class="col-2">
												<label>ID proveedor </label> <input type="text"
													name="idProveedorEditar" id="idProveedorEditar"
													class="form-control" required readonly value="">

											</div>
											<div class="col-4">
												<label>Descripción proveedor </label> <input type="text"
													name="decripcionProveedorEditar"
													id="descripcionProveedorEditar" class="form-control"
													required readonly value="">
											</div>
											<div class="col-2">
												<label>ID marca </label> <input type="text"
													name="idMarcaEditar" id="idMarcaEditar"
													class="form-control" required readonly value="">

											</div>
											<div class="col-4">
												<label>Descripción marca </label> <input type="text"
													name="decripcionMarcaEditar" id="descripcionMarcaEditar"
													class="form-control" required readonly value="">
											</div>
										</div>

										<div class="row">
											<div class="col-2">
												<label>ID familia </label> <input type="text"
													name="idFamiliaEditar" id="idFamiliaEditar"
													class="form-control" required readonly value="">

											</div>
											<div class="col-4">
												<label>Descripción familia </label> <input type="text"
													name="descripcionFamiliaEditar"
													id="descripcionFamiliaEditar" class="form-control" required
													readonly value="">
											</div>
											<div class="col-2">
												<label>ID GpoArt </label> <input type="text"
													name="idGpoArtEditar" id="idGpoArtEditar"
													class="form-control" required readonly value="">

											</div>
											<div class="col-4">
												<label>Descripción GpoArt </label> <input type="text"
													name="descripcionGpoArtEditar" id="descripcionGpoArtEditar"
													class="form-control" required readonly value="">
											</div>
										</div>


										<div class="row">
											<div class="col-2">
												<label>ID categoria </label> <input type="text"
													name="idCategoriaEditar" id="idCategoriaEditar"
													class="form-control" required readonly value="">

											</div>
											<div class="col-4">
												<label>Descripción categoria </label> <input type="text"
													name="descripcionCategoriaEditar"
													id="descripcionCategoriaEditar" class="form-control"
													required readonly value="">
											</div>
											<div class="col-2">
												<label>UM </label> <input type="text" name="umEditar"
													id="umEditar" class="form-control" required
													placeholder="Sin información..." readonly value="">
											</div>
											<div class="col-4">
												<label>EAN </label> <input type="text" name="eanEditar"
													id="eanEditar" class="form-control" required
													placeholder="Sin información..." readonly value="">

											</div>
										</div>

										<div class="row">

											<div class="col">

												<label>Conversión UM</label> <input type="text"
													name="conversionUmEditar" placeholder="Sin información..."
													id="conversionUmEditar" class="form-control" readonly
													required value="">

											</div>
											<div class="col">
												<label>Sustituto </label> <input type="text"
													name="sustitutoEditar" id="sustitutoEditar"
													class="form-control" required value="" readonly>
											</div>

											<div class="col">
												<label>Gramaje</label> <input type="text"
													name="gramajeEditar" id="gramajeEditar"
													class="form-control" placeholder="Sin información..."
													value="">

											</div>

										</div>

									</div>


									<a href="#c-atributos" data-toggle="collapse"
										onclick="verMas('c-atributos-input','c-atributos-mas')"> <i
										id="c-atributos-mas" class="fa fa-caret-square-o-down"
										style="font-size: 24px; color: lightseagreen"></i> <input
										id="c-atributos-input" value="0" type=hidden>
									</a> <label style="font-size: large;"> ATRIBUTOS</label> <br>

									<div id="c-atributos" class="collapse">
										<div class="col-12" id="atributosFamiliaEdit"></div>

									</div>

									<a href="#c-imagenes" data-toggle="collapse"
										onclick="verMas('c-imagenes-input','c-imagenes-mas')"> <i
										id="c-imagenes-mas" class="fa fa-caret-square-o-down"
										style="font-size: 24px; color: lightseagreen"></i> <input
										id="c-imagenes-input" value="0" type=hidden>
									</a> <label style="font-size: large;">IMAGENES</label> <br>
									<div id="c-imagenes" class="collapse">

										<br> <label>Total de imagenes:</label> <input
											type="number" id="cantImagenEU" name="cantImagenEU"
											style="border: white" value="" readonly="readonly"> <input
											type="hidden" id="cantImagenE" name="cantImagenE" value=""
											readonly="readonly"><input type="hidden" id="imgElim"
											name="imgElim" value="" readonly="readonly">



										<div class="container">

											<div class="row" id="materialesImagenes"></div>
										</div>
									</div>

									<a href="#c-plataformas" data-toggle="collapse"
										onclick="verMas('c-plataformas-input','c-plataformas-mas')">
										<i id="c-plataformas-mas" class="fa fa-caret-square-o-down"
										style="font-size: 24px; color: lightseagreen"></i> <input
										id="c-plataformas-input" value="0" type=hidden>
									</a> <label style="font-size: large;"> PLATAFORMAS</label> <br>
									<div id="c-plataformas" class="collapse">
										<div class="row" id="plataformasExistentes"></div>

									</div>
								</div>

							</div>

							<input type="hidden" id="opcion" name="opcion"> <input
								type="hidden" id="motivo" name="motivo">
						</div>
					</form>
				</div>
				<!-- Modal footer -->
				<div class="modal-footer">


					<button type="button" class="btn btn-secondary"
						onclick="limpiarFormAdmin('materialesImagenes','plataformasExistentes')"
						data-dismiss="modal">Cerrar</button>
					<button type="button" onclick="rechazarMaterial()"
						class="btn btn-danger" style="float: right">Rechazar</button>
					<button type="button" onclick="checarPlataforma()"
						class="btn btn-success" style="float: right">Aceptar</button>
					<!-- 						<form id="eliminarMaterial" -->
					<%-- 						action="${pageContext.servletContext.contextPath}/producto?op=eliminarMaterial&tipo=Admin" --%>
					<!-- 						method="post"> -->
					<!-- 						<button type="button" onclick="eliminarMatAdmin()" -->
					<!-- 						class="btn btn-danger" style="float: right">Eliminar</button> -->
					<!-- 						<input type="hidden" id="materialesEliminados" name="materialesEliminados"> -->
					<!-- 						</form> -->
				</div>

			</div>



		</div>
	</div>
</body>
</html>
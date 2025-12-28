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
<title>Administración imagenes</title>

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
<body onload="cargaInfo()">

	<c:if test="${not empty descarga}">
		<script>
			$(document).ready(function() {
				Swal.fire('Descarga correcta', '${descarga}', 'success');
			});
		</script>
	</c:if>
	<c:if test="${not empty errordescarga}">
		<script>
			$(document).ready(function() {
				Swal.fire('Error', '${errordescarga}', 'error');
			});
		</script>
	</c:if>


	<div class="container body">
		<div class="main_container">
			<div class="right_col" role="main" style="min-height: 572px;">

				<!-- 			//vamos a crear lo que el usuario de diseño puede ver  -->
				<div class="x_panel">
					<c:if test="${usuario.getIdRol() eq '4'}">

						<div class="container register">
							<div class="row">
								<div class="col-6">
									<div class="card bg-light">
										<div class="card-body text-center">
											<p class="card-text">Reporte de imagenes</p>

											<form
												action="${pageContext.servletContext.contextPath}/producto?op=ExcelImagen"
												onsubmit="espera();" method="post">
												<button type="submit" class="btn btn-success btn-sm">
													<i class="fa fa-download" style="font-size: 14px"></i>
												</button>
											</form>
										</div>
									</div>
								</div>

								<div class="col-6">
									<div class="card bg-light">
										<div class="card-body text-center">
											<p>Carga de imagenes</p>
											<form id="CargaMasivaImagenes"
												action="${pageContext.servletContext.contextPath}/producto?op=CargaMasivaImagenes"
												method="post" enctype="multipart/form-data">
												<div class="row">
												<div class="col-8">
													<input type="file" class="form-control" accept=".jpg,.png"
														name="files[]" id="cargaMasivaImagen"
														onchange="showdatafile('cargaMasivaImagen')"
														multiple="multiple" required="required">
												
												</div>
												<div class="col-4">
												<button type="button" class="btn btn-warning btn-sm"
														onclick="modalImagenes();">?</button>

													<button type="button" onclick="esperaCargaImg()"
														class="btn btn-success btn-sm">
														<i class="fa fa-save" style="font-size: 14x; color: white"></i>
													</button>
												</div>
												
													
													
													<div id="cargaImagenes"></div>
													<br>
												</div>
											

											</form>
										</div>
									</div>


								</div>

							</div>
						</div>

					</c:if>
				</div>
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
									<div class="col-sm-2">
										<input id="familiaBuscador" style="font-size: 10px"
											name="familiaBuscador" type="text" data-toggle="tooltip"
											data-placement="top"
											title="Busca a partir de la descripción familia"
											class="form-control awesomplete" data-autofirst="true"
											onkeypress="if(event.key == 'Enter') {filtroImg(1)}"
											data-list="ABARROTES COMESTIBLES,ABARROTES NO COMESTIBLES,CARNES FRÍAS Y SALCHICHONERÍA,VINOS Y LICORES,FARMACIA,PERFUMERÍA,DULCERÍA,LÁCTEOS,OTRAS CATEGORÍAS"
											class="form-control awesomplete" placeholder="Familia">
									</div>

									<div class="col-sm-2">
										<input id="categoriaBuscador" style="font-size: 10px"
											name="categoriaBuscador"
											title="Busca a partir de la descripción categoría"
											onkeypress="if(event.key == 'Enter') {filtroImg(1)}"
											class="form-control awesomplete" placeholder="Categoria">
									</div>
									<div class="col-sm-2">
										<input id="proveedorBuscador" style="font-size: 10px"
											name="proveedorBuscador" type="text"
											title="Busca a partir de la descripción del proveedor"
											onkeypress="if(event.key == 'Enter') {filtroImg(1)}"
											class="form-control  awesomplete" placeholder="Proveedor">
									</div>



								</div>
								<br>
								<div class="row justify-content-end">
									<div class="input-group-append">
										<a id="abuscador" style="color: white;" onclick="filtroImg(1)"
											class="btn-success btn-sm"><i class="fa fa-search"
											style="font-size: 12px; color: white"></i></a>
									</div>
									<div class="input-group-append">
										<a id="abuscador" style="color: white;"
											onclick="consultaGeneralImg()" class="btn-success btn-sm"><i
											class="fa fa-trash" style="font-size: 12px; color: white"></i></a>
									</div>

									<!-- 										<div type="hidden" class="col-12" id="espera" -->
									<!-- 											style="visibility: hidden;"> -->
									<!-- 											<label style="color: white;">Espera un momento...</label> <img -->
									<!-- 												src="/PCM_2.0/Images/cargando.gif"> -->
									<!-- 										</div> -->
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
																							<th>Acciones</th>
																						</tr>
																					</thead>

																					<tbody BGCOLOR="white" class="" id="myTable">
																						<c:forEach var="dato" items="${datos}">
																							<tr>
																								<td style="font-size: small;">${dato.getIdMaterial()}</td>
																								<td style="font-size: small;">${dato.getDescripcionMaterial()}</td>
																								<td><input type="hidden" name="id"
																									value="${dato.getIdMaterial()}">
																									<div id="${dato.getIdMaterial()}">

																										<div id="${dato.getIdMaterial()}">
																											<i class="fa fa-pencil-square-o"
																												style="font-size: 24px; color: lightseagreen"
																												onclick="adminImg('${dato.getIdMaterial()}')"></i>
																										</div>


																									</div></td>

																							</tr>

																						</c:forEach>

																					</tbody>
																				</table>

																				<nav aria-label="paginacion producto">
																					<ul class="pagination justify-content-center">
																						<li class="page-items"><a id="idprevious"
																							class="page-link"
																							href="${pageContext.servletContext.contextPath}/producto?op=AdministrarImg&pagina=${pagina-1}">Previous</a>
																						</li>
																						<li class="page-item"><a class="page-link"
																							id="actual">Pagina ${pagina}</a></li>
																						<li class="page-item"><a id="idnext"
																							class="page-link"
																							href="${pageContext.servletContext.contextPath}/producto?op=AdministrarImg&pagina=${pagina+1}">Next</a>
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

<!-- Modal Info de imagenes-->
	<div class="modal" id="modalInfoImagen">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header"
					style="background: -webkit-linear-gradient(left, #1143a6, #00c6ff)">
					<h4 class="modal-title" style="color: white">Registro exitoso
						de imagenes</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<div style="padding-right: 2cm; padding-left: 2cm">
					1. La extensión deberá ser .jpg o .png <br> 2. El nombre de la
					imagen se compone por:<br> * Número de material.<br> *
					Número de imagen.<br> * _app o _dis, lo cual corresponde a las
					medidas 300x300 y 1200x1200 respectivamente.<br> <b>
						Ejemplo: 10_1_app.jpg<br>
					</b> 4. Un artículo puede tener hasta 8 imágenes.<br> 5. La imagen
					debe ser de un producto existente.<br>

				</div>

				<!-- Modal body -->
				<div class="modal-body"></div>

				<!-- Modal footer -->
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-dismiss="modal">Cerrar</button>
				</div>

			</div>
		</div>
	</div>
	

	<!-- Modal imagenes-->
	<div class="modal" id="adminImg">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">Imagenes</h4>
					<button type="button" onclick="cancelarEliminarImagen()"
						class="close" data-dismiss="modal">&times;</button>
				</div>
				<div class="row justify-content-center">
					<div id="cantImg" class="justify-content-end"></div>
					<div class="container">

						<div class="row justify-content-center" id="divImg"></div>
					</div>

					<form id="eliminarImagen"
						action="${pageContext.servletContext.contextPath}/producto?op=adminImagenEliminar"
						method="post">

						<input type="hidden" id="imgElim" name="imgElim" value=""
							readonly="readonly">

					</form>

					<input type="hidden" id="cantImagenEU" name="cantImagenEU"
						style="border: white" value="" readonly="readonly"> <input
						type="hidden" id="cantImagenE" name="cantImagenEU"
						style="border: white" value="" readonly="readonly">
				</div>

				<!-- Modal body -->
				<div class="modal-body"></div>

				<!-- Modal footer -->
				<div class="modal-footer">
					<button type="button" onclick="confirmar()"
						class="btn btn-success btn-sm" data-dismiss="modal">Guardar</button>

					<button type="button" onclick="cancelarEliminarImagen()"
						class="btn btn-danger btn-sm" data-dismiss="modal">Cerrar</button>
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



</body>
</html>
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
<title>Carga masiva</title>

<jsp:include page="Master.jsp" />

</head>

<body onload="procesaCsv()">
	<!-- NUEVAS ALERTAS -->
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


	<!-- NECESITO LOS SIGUIENTES ALERT PARA NOTIFICAR SI TENGO PROCESOS EN FILA -->
	<c:if test="${not empty activados}">
		<script>
			$(document).ready(function() {
				Swal.fire('Listo', '${activados}', 'info');
			});
		</script>
	</c:if>
	<c:if test="${not empty eliminados}">
		<script>
			$(document).ready(function() {
				Swal.fire('Listo', '${eliminados}', 'info');
			});
		</script>
	</c:if>

	<div class="container body">
		<div class="main_container">
			<div class="right_col" role="main" style="min-height: 572px;">
				<div class="x_panel">
					<div class="container-fluid">
						<!--     COMIENZA EL BODY -->
						<div class="x_panel">
							<div class="x_content">
								<div class="row">
									<div class="col-md-12 justify-content-center">
										<div class="x_title">
											<h7>CARGA MASIVA ATRIBUTOS</h7>
											<div class="clearfix"></div>
										</div>
										<div class="container card bg-light">
											<div class="row">
												<div class="col-lg-6 col-md-12">
													<div class="card-body text-center">

														<p class="card-text">Subir archivo .CSV para registro
														</p>

														<form id="cargaMasivaMaterial" name="cargaMasivaMaterial"
															action="${pageContext.servletContext.contextPath}/producto?op=cargaMasivaMaterial"
															method="post" enctype="multipart/form-data">
															<input id="archivoCsvMaterial" name="archivoCsvMaterial"
																class="form-control" type="file" required="required">
															<br>

															<button type="button" onclick="csvMateriales()"
																class="btn btn-success btn-sm">
																<i class="fa fa-save"
																	style="font-size: 48x; color: white"></i>
															</button>
														</form>
													</div>
												</div>
												<div class="col-lg-6 col-md-12">
													<div class="card-body text-center">
														<p class="card-text">Descarga Template</p>
														<form
															action="${pageContext.servletContext.contextPath}/producto?op=registroFamiliaCsv"
															method="post" enctype="multipart/form-data">
															<select name="selectFamiliasCsv" id="selectFamiliasCsv"
																class="form-control" required>
																<option value="0">Selecciona una familia</option>
															</select> <br>
															<button type="submit" class="btn btn-success btn-sm">
																<i class="fa fa-download"
																	style="font-size: 48x; color: white"></i>
															</button>

														</form>
													</div>
													
														<div class="card-body text-center">
														<p class="card-text">Descarga Template Categoria-subcategoria</p>
														<form
															action="${pageContext.servletContext.contextPath}/producto?op=templateCategorias"
															method="post" enctype="multipart/form-data">
														
															<button type="submit" class="btn btn-success btn-sm">
																<i class="fa fa-download"
																	style="font-size: 48x; color: white"></i>
															</button>

														</form>
													</div>
												</div>

											</div>

										</div>
									</div>
								</div>


							</div>
						</div>
						<!-- 						CREAMOS EL PANEL 2 -->
						<div class="x_panel">
							<div class="x_content">
								<div class="row">
									<div class="col-md-12 justify-content-center">
										<div class="x_title">
											<h7>CARGA MASIVA DESCRIPCION COMERCIAL Y GRAMAJE</h7>
											<div class="clearfix"></div>
										</div>
										<div class="container card bg-light">
											<div class="row">
												<div class="col-lg-6 col-md-12">
													<div class="card-body text-center">

														<p class="card-text">Subir archivo .CSV para registro
														</p>

														<form id="cargaMasivaDC" name="cargaMasivaDC"
															action="${pageContext.servletContext.contextPath}/producto?op=cargaMasivaDC"
															method="post" enctype="multipart/form-data">
															<br> <input id="archivoCsvDC" name="archivoCsvDC"
																class="form-control" type="file" required="required">
															<br>
															<button type="button" onclick="csvDescripcion()"
																class="btn btn-success btn-sm">
																<i class="fa fa-save"
																	style="font-size: 48x; color: white"></i>
															</button>
														</form>
													</div>
												</div>
												<div class="col-lg-6 col-md-12">
													<div class="card-body text-center">
														<p class="card-text">Descarga Template</p>
														<form
															action="${pageContext.servletContext.contextPath}/producto?op=templateDc"
															method="post" enctype="multipart/form-data">

															<button type="submit" class="btn btn-success btn-sm">
																<i class="fa fa-download"
																	style="font-size: 48x; color: white"></i>
															</button>


														</form>
													</div>
												</div>

											</div>

										</div>
									</div>
								</div>


							</div>
						</div>

						<!-- 						NUEVO PANEL -->
						<div class="x_panel">
							<div class="x_content">
								<div class="row">
									<div class="col-md-12 justify-content-center">
										<div class="x_title">
											<h7>CARGA MASIVA IMAGENES</h7>
											<div class="clearfix"></div>
										</div>
										<div class="container card bg-light">
											<div class="row">
												<div class="col-lg-6 col-md-12">
													<div class="card-body text-center">

														<form id="CargaMasivaImagenes"
															action="${pageContext.servletContext.contextPath}/producto?op=CargaMasivaImagenes"
															method="post" enctype="multipart/form-data">
															<div class="row">

																<input type="file" class="form-control"
																	accept=".jpg,.png" name="files[]"
																	id="cargaMasivaImagen"
																	onchange="showdatafile('cargaMasivaImagen')"
																	multiple="multiple" required="required">

																<div id="cargaImagenes"></div>
																<br>
															</div>
															<!-- Modal footer -->
															<div class="modal-footer">
																<button type="button" class="btn btn-warning btn-sm"
																	onclick="modalImagenes()">?</button>

																<button type="button" onclick="esperaCargaImg()"
																	class="btn btn-success btn-sm">
																	<i class="fa fa-save" style="font-size: 48x"></i>
																</button>
															</div>

														</form>
													</div>
												</div>


											</div>

										</div>
									</div>
								</div>


							</div>
						</div>

						<!-- 						nuevo panel -->
						<div class="x_panel">
							<div class="x_content">
								<div class="row">
									<div class="col-md-12 justify-content-center">
										<div class="x_title">
											<h7 STYLE="COLOR:GREEN">ALTA MATERIALES PLATAFORMA</h7>
											<div class="clearfix"></div>
										</div>
										<div class="container card bg-light">
											<div class="row">
												<div class="col-lg-6 col-md-12">
													<span class="badge badge-pill badge-success">ALTA</span>
													<div class="card-body text-center">

														<p class="card-text">Subir archivo .CSV para registro
														</p>

														<form id="cargaMasivaMaterialPlataforma"
															name="cargaMasivaMaterialPlataforma"
															action="${pageContext.servletContext.contextPath}/producto?op=cargaMasivaMaterialPlataforma"
															method="post" enctype="multipart/form-data">
															<input id="archivoCsvMaterialPlataforma"
																name="archivoCsvMaterialPlataforma" class="form-control"
																type="file" required="required"> <br>
															<button type="button" class="btn btn-warning btn-sm"
																onclick="infoExtra()">?</button>
															<button type="button" onclick="csvMaterialesPlataforma()"
																class="btn btn-success btn-sm">
																<i class="fa fa-save"
																	style="font-size: 48x; color: white"></i>
															</button>
														</form>
													</div>
												</div>
												<div class="col-lg-6 col-md-12">
													<div class="card-body text-center">
														<p class="card-text">Descarga Template</p>
														<form
															action="${pageContext.servletContext.contextPath}/producto?op=registroMaterialesPlataformas"
															method="post" enctype="multipart/form-data">

															<button type="submit" class="btn btn-success btn-sm">
																<i class="fa fa-download"
																	style="font-size: 48x; color: white"></i>
															</button>


														</form>
													</div>
												</div>

											</div>

										</div>
									</div>
								</div>


							</div>
						</div>
						<!-- 						nuevo panel -->
						<div class="x_panel">
							<div class="x_content">
								<div class="row">
									<div class="col-md-12 justify-content-center">
										<div class="x_title">
											<h7 STYLE="COLOR:RED">BAJA MATERIALES PLATAFORMA</h7>
											<div class="clearfix"></div>
										</div>
										<div class="container card bg-light">
											<div class="row">
												<div class="col-lg-6 col-md-12">
													<span class="badge badge-pill badge-danger">BAJA</span>
													<div class="card-body text-center">

														<p class="card-text">Subir archivo .CSV para baja</p>

														<form id="MaterialBaja" name="MaterialBaja"
															action="${pageContext.servletContext.contextPath}/producto?op=bajaMaterialPlataforma"
															method="post" enctype="multipart/form-data">
															<input id="CsvBajaMaterial" name="CsvBajaMaterial"
																class="form-control" type="file" required="required">
															<br>
															<button type="button" onclick="bajaMateriales()"
																class="btn btn-success btn-sm">
																<i class="fa fa-save"
																	style="font-size: 48x; color: white"></i>
															</button>
														</form>
													</div>
												</div>
												<div class="col-lg-6 col-md-12">
													<div class="card-body text-center">
														<p class="card-text">Descarga Template</p>
														<form
															action="${pageContext.servletContext.contextPath}/producto?op=bajaMaterialesPlataformas"
															method="post" enctype="multipart/form-data">

															<button type="submit" class="btn btn-success btn-sm">
																<i class="fa fa-download"
																	style="font-size: 48x; color: white"></i>
															</button>


														</form>
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
				<div class="modal-header">
					<h4 class="modal-title">Registro exitoso de imagenes</h4>
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
					<button type="button" class="btn btn-danger btn-sm"
						data-dismiss="modal">Cerrar</button>
				</div>

			</div>
		</div>
	</div>

	<!-- Modal Info carga materiales plataformas-->
	<div class="modal" id="modalMaterialesPlataforma">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">Asignacion exitosa de materiales</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<div style="padding-right: 2cm; padding-left: 2cm">
					PASOS:<br> 1. Descargar template<br> 2. Todos los campos
					del template son OBLIGATORIOS: idPlataforma, idMaterial, Um (Unidad
					de medida) y destacado.<br> >2.1 Para conocer el idPlataforma
					consulta Administración -> plataformas <br> >2.2 El campo
					destacado se representa con 1 y 0, destacado y no destacado
					respectivamente. <br> 3. Subir el archivo con extensión .csv <br>
				</div>

				<!-- Modal body -->
				<div class="modal-body"></div>

				<!-- Modal footer -->
				<div class="modal-footer">
					<button type="button" class="btn btn-danger btn-sm"
						data-dismiss="modal">Cerrar</button>
				</div>

			</div>
		</div>
	</div>
	<!-- Modal Info de materiales-->
	<!-- 	<div class="modal" id="eliminarMat"> -->
	<!-- 		<div class="modal-dialog modal-lg"> -->
	<!-- 			<div class="modal-content"> -->

	<!-- 				Modal Header -->
	<!-- 				<div class="modal-header" -->
	<!-- 					style="background: -webkit-linear-gradient(left, #1143a6, #00c6ff)"> -->
	<!-- 					<h4 class="modal-title" style="color: white">Eliminar -->
	<!-- 						materiales</h4> -->
	<!-- 					<button type="button" class="close" data-dismiss="modal">&times;</button> -->
	<!-- 				</div> -->
	<!-- 				<div style="padding-right: 2cm; padding-left: 2cm"> -->
	<!-- 					¿Cómo eliminar de manera exitosa un material? <br> Inserta el número de material (número de sap), si deseas agregar -->
	<!-- 					más de uno separalo por coma. <br> Ejemplo: 1,2 <br> Los -->
	<!-- 					materiales a eliminar no deben estar asignandos a ninguna -->
	<!-- 					plataforma.<br> -->
	<!-- 					Debes tener en cuenta que las imágenes de este material también eliminaran.<br> -->
	<!-- 					Podrás activar de nuevo este material en el apartado "Activar materiales". -->


	<!-- 				</div> -->


	<!-- 				Modal body -->
	<!-- 				<div class="modal-body"></div> -->

	<!-- 				Modal footer -->
	<!-- 				<div class="modal-footer"> -->
	<!-- 					<button type="button" class="btn btn-danger" data-dismiss="modal">Cerrar</button> -->
	<!-- 				</div> -->

	<!-- 			</div> -->
	<!-- 		</div> -->
	<!-- 	</div> -->
	<!-- Modal Info de materiales-->
	<!-- 	<div class="modal" id="activarMat"> -->
	<!-- 		<div class="modal-dialog modal-lg"> -->
	<!-- 			<div class="modal-content"> -->

	<!-- 				Modal Header -->
	<!-- 				<div class="modal-header" -->
	<!-- 					style="background: -webkit-linear-gradient(left, #1143a6, #00c6ff)"> -->
	<!-- 					<h4 class="modal-title" style="color: white">Activar -->
	<!-- 						materiales</h4> -->
	<!-- 					<button type="button" class="close" data-dismiss="modal">&times;</button> -->
	<!-- 				</div> -->
	<!-- 				<div style="padding-right: 2cm; padding-left: 2cm"> -->
	<!-- 					¿Cómo activar de manera exitosa un material? <br> Inserta el número de material (número de sap), si deseas agregar -->
	<!-- 					más de uno separalo por coma. <br> Ejemplo: 1,2 <br> Los -->
	<!-- 					materiales deben existir en sap.<br> -->
	<!-- 					Podrás eliminar de nuevo este material en el apartado "Eliminar materiales". -->


	<!-- 				</div> -->


	<!-- 				Modal body -->
	<!-- 				<div class="modal-body"></div> -->

	<!-- 				Modal footer -->
	<!-- 				<div class="modal-footer"> -->
	<!-- 					<button type="button" class="btn btn-danger" data-dismiss="modal">Cerrar</button> -->
	<!-- 				</div> -->

	<!-- 			</div> -->
	<!-- 		</div> -->
	<!-- 	</div> -->
	<!-- Modal Congelado para carga de img-->
	<div class="modal fade" id="modalCongeladoImg" data-backdrop="static"
		data-keyboard="false" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h2 class="modal-title">Subiendo imagenes</h2>

				</div>

				<div class="text-center">
					<span class="label label-primary">Esto puede tardar un poco,
						espera un momento por favor.</span>
				</div>

			</div>
		</div>
	</div>


	<!-- MODAL CONGELADO PARA CSV-->
	<!-- Modal Congelado-->
	<div class="modal fade" id="modalCongelado" data-backdrop="static"
		data-keyboard="false" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title col-12 text-center">Editando
						información</h1>
				</div>

				<div class="text-center">
					<span class="label label-primary"> Esto puede tardar un
						poco, espera un momento por favor.</span>
				</div>

			</div>
		</div>
	</div>
	<!-- Modal Congelado dc-->
	<div class="modal fade" id="modalCongeladoDC" data-backdrop="static"
		data-keyboard="false" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
				<h1 class="modal-title col-12 text-center">Editando
						información</h1>
				</div>

				<div class="text-center">
					<span class="label label-primary"> Esto puede tardar un
						poco, espera un momento por favor.</span>
				</div>

			</div>
		</div>
	</div>

	<!-- Modal Info de imagenes-->
	<div class="modal" id="modalInfoImagen">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title col-12 text-center">Registro exitoso
						de imagenes</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<div style="padding-right: 2cm; padding-left: 2cm">
					1. Extensiones permitidas .jpg, .png <br> 2. El nombre de la
					imagen se compone por:<br> * Número de material.<br> *
					Número de imagen.<br> * _app, _web, o _dis lo cual corresponde
					a la plataforma.<br> <b> Ejemplo: 10_1_app.jpg<br>
					</b> 4. Un artículo puede tener hasta 8 imágenes de la misma medida.<br>
					5. La imagen debe ser de un producto registrado previamente.<br>

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
	<!-- Modal Congelado para carga de img-->
	<div class="modal fade" id="modalCongeladoImg" data-backdrop="static"
		data-keyboard="false" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title col-12 text-center">Actualizando
						imagenes</h1>

				</div>

				<div class="text-center">
					<span class="label label-primary">Esto puede tardar un poco,
						espera un momento por favor.</span>
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
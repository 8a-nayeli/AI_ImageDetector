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
<title>Registro individual</title>

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
					<div class="container-fluid">
						<!--     COMIENZA EL BODY -->
						<div id="modalRegistro">
							<div>
								<div>

									<div>
										<h4>Nuevo registro</h4>
									</div>

									<div>

										<form id="formRegistro" action="/PCM/producto?op=REGISTRO"
											method="post" enctype="multipart/form-data">
											<div class="container" >
												<div class="row justify-content-center">
													<div class="input-group col-6">
														<input type="text"
															class="form-control" id="numero_material"
															name="numero_material" placeholder="Numero de material"
															onkeypress="return solo_numeros(event, 'numero_material','18')"
															required>
														<div class="input-group-append">
															<a id="abuscador" style="color: white;"
																onclick="buscar_material()"
																class="btn-success btn-sm"><i class="fa fa-search"
																style="font-size: 12px; color: white"></i></a>
														</div>
													</div>
													
										


												</div>
												<br>
											</div>


											<div class="form-group">
												<div class="row"
													style="padding-right: 40px; padding-left: 40px;">
													<div class="col">
														<label>Descripción </label> <input type="text"
															name="descripcion" id="descripcion" class="form-control"
															required readonly value="">
													</div>

													<div class="col">
														<label>Descripción comercial* </label> <input type="text"
															name="descripcionComercial" id="descripcionComercial"
															class="form-control" required value=""
															placeholder="Descripción comercial...">
													</div>

													<div class="container">
														<div class="row">
															<div class="col-2">
																<label>ID proveedor </label> <input type="text"
																	name="idProveedor" id="idProveedor"
																	class="form-control" required readonly value="">

															</div>
															<div class="col-4">
																<label>Descripción proveedor </label> <input type="text"
																	name="decripcionProveedor" id="descripcionProveedor"
																	class="form-control" required readonly value="">
															</div>
															<div class="col-2">
																<label>ID marca </label> <input type="text"
																	name="idMarca" id="idMarca" class="form-control"
																	required readonly value="">

															</div>
															<div class="col-4">
																<label>Descripción marca </label> <input type="text"
																	name="descripcionMarca" id="descripcionMarca"
																	class="form-control" required readonly value="">
															</div>
														</div>


														<div class="row">
															<div class="col-2">
																<label>ID familia </label> <input type="text"
																	name="idFamilia" id="idFamilia" class="form-control"
																	required readonly value="">

															</div>
															<div class="col-4">
																<label>Descripción familia </label> <input type="text"
																	name="descripcionFamilia" id="descripcionFamilia"
																	class="form-control" required readonly value="">
															</div>
															<div class="col-2">
																<label>ID GpoArt </label> <input type="text"
																	name="idGpoArt" id="idGpoArt" class="form-control"
																	required readonly value="">

															</div>
															<div class="col-4">
																<label>Descripción GpoArt </label> <input type="text"
																	name="descripcionGpoArt" id="descripcionGpoArt"
																	class="form-control" required readonly value="">
															</div>
														</div>

														<div class="row">
															<div class="col-2">
																<label>ID categoria </label> <input type="text"
																	name="idCategoria" id="idCategoria"
																	class="form-control" required readonly value="">

															</div>
															<div class="col-4">
																<label>Descripción categoria </label> <input type="text"
																	name="descripcionCategoria" id="descripcionCategoria"
																	class="form-control" required readonly value="">
															</div>

															<div class="col-2">
																<label>UM </label> <input type="text" name="um" id="um"
																	class="form-control" required placeholder="UM..."
																	value="" onkeypress="caracteresMax('um','3')" readonly>
															</div>
															<div class="col-4">
																<label>EAN </label> <input type="text" name="ean"
																	id="ean" class="form-control" required
																	placeholder="EAN..." value=""
																	onkeypress="caracteresMax('ean','18')" readonly>

															</div>
														</div>
														<div class="row">
															<div class="col">
																<label>Conversión UM</label> <input type="text"
																	name="conversionUm" id="conversionUm"
																	class="form-control" required
																	placeholder="ConversionUm..." value=""
																	onkeypress="return decimales(event, 'conversionUm')"
																	readonly>
															</div>
															<div class="col">
																<label>Sustituto </label> <input type="text"
																	name="sustituto" id="sustituto" class="form-control"
																	required value="" placeholder="Sustituto..."
																	onkeypress="caracteresMax('sustituto','18')" readonly>

															</div>
															<div class="col">
																<label>Gramaje*</label> <input type="text"
																	name="gramaje" id="gramaje" class="form-control"
																	required placeholder="Gramaje..." value=""
																	onkeypress="caracteresMax('gramaje','8')">

															</div>


														</div>


													</div>
													<label style="font-size: x-large;"> ATRIBUTOS</label>
													<hr width=80% align="right" size=80 color="blue">

													<div id="atributosFamilia" class="container"></div>
													<label style="font-size: x-large;"> IMAGENES</label>
													<hr width=80% align="right" size=80 color="blue">

												</div>
												<div id="cuantasImg"
													style="padding-right: 40px; padding-left: 40px;"></div>
												<br>


												<div class="row"
													style="padding-right: 40px; padding-left: 40px;"
													id="imagenesAgregadas"></div>
												<button type="button" class="btn btn-warning"
													style="float: right" id="boton" name="boton"
													onclick="duplicater('duplicater0','imagen','evidencia','nombre','cantImagen','eliminar','boton','registro')">Agregar
													imagen</button>
												<br> <span style="visibility: hidden" id="Missing">La
													imagen es requerida</span>
												<div id="placer">
													<div
														style="display: none; border-style: solid; border-width: 1px; margin-bottom: 8px;"
														id="duplicater0">
														<label class="btn btn-primary"> Subir Imagen <input
															type="file" accept=".jpg,.png" style="display: none"
															onchange="previewFile('imagen','evidencia',nombre)"
															name="evidencia" id="evidencia">
														</label> <input type="hidden" id="nombre" name="nombre"> <img
															id="imagen" src=""
															style="max-width: 20%; max-height: 20%;">

														<button id="eliminar" type="button" class="btn btn-danger"
															style="float: right"
															onclick="elim('duplicater0','imagen','evidencia','nombre','cantImagen','eliminar','boton')">Eliminar</button>

													</div>


												</div>

												<input type="hidden" id="cantImagen" name="cantImagen"
													value=1>

											</div>

										</form>



									</div>

									<!-- Modal footer -->
									<div class="modal-footer">


										<button type="button"
											class="btn btn-success" onclick="validacionRegistro()">
											<i class="fa fa-save" style="font-size: 48x; color: white"></i>
										</button>


									</div>

								</div>
							</div>
						</div>


					</div>

				</div>
			</div>
		</div>
	</div>



</body>
</html>
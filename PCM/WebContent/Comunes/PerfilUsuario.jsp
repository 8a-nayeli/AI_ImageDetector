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
<title>Perfil de usuario</title>
<script
		src="${pageContext.request.contextPath}/vendors/jquery/dist/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.js"></script>
<jsp:include page="Master.jsp" />

</head>
<body>
	<c:if test="${not empty msgU}">
		<c:if test="${not empty successU}">
			<script>
				$(document).ready(function() {
					Swal.fire('', '${msgU}', 'success');
				});
			</script>
		</c:if>
		<c:if test="${empty successU}">
			<script>
				$(document).ready(function() {
					Swal.fire('', '${msgU}', 'error');
				});
			</script>
		</c:if>
	</c:if>

	<div class="container body">
		<div class="main_container">
			<div class="right_col" role="main" style="min-height: 572px;">
				<div class="x_panel">
					<div class="container-fluid">
						<!--     COMIENZA EL BODY -->
						<div>
							<div class="container-fluid">
								<div class="justify-row-center">
									<div class="col-6">
										<h3>Datos de usuario</h3>
										<form method="post">

											<div class="modal-body">
												VALIDACION DE DATOS <br>
												<form method="post">
													<label>Nombre de usuario</label> <input
														class="form-control" type="text" name="nombre_usuario"
														id="nombre_usuario" readonly value="${dato.getUsuario()}">
													<label>Correo</label> <input class="form-control"
														type="text" id="correo_confirmacion" name="correo_confirmacion" readonly
														value="${dato.getCorreo()}"> <br>
													
												</form>

												<div class="form-group">
													<div class="row">
														<div class="col-10"
															style="padding-right: 18px; padding-left: 18px;">
															<label style="color: black">Contraseña</label> <input
																type="password" name="pass_confirmacion"
																id="pass_confirmacion" class="form-control"
																placeholder="Contraseña actual" required> <input
																type="hidden" name="encriptacion_pass_actual"
																id="encriptacion_pass_actual" value="">

														</div>
														<br>
														<div class="col-2">
															<a class="btn btn-light"
																onclick="mostrarContrasena('pass_confirmacion')"> <img
																width="20" height="20"
																src="<c:out value="${pageContext.servletContext.contextPath}"/>/Images/password.png" /></a>
														</div>
													</div>
												</div>
												<div class="form-group">
													<div class="row">
														<div class="col-10"
															style="padding-right: 18px; padding-left: 18px;">
															<label style="color: black">Nueva contraseña</label> <input
																type="password" name="nuevo_pass" id="nuevo_pass"
																class="form-control" placeholder="Nueva contraseña"
																minlength="8" required>
														</div>
														<br>
														<div class="col-2">
															<a class="btn btn-light"
																onclick="mostrarContrasena('nuevo_pass')"> <img
																width="20" height="20"
																src="<c:out value="${pageContext.servletContext.contextPath}"/>/Images/password.png" /></a>
														</div>
													</div>
												</div>
												<div class="form-group">
													<div class="row">
														<div class="col-10"
															style="padding-right: 18px; padding-left: 18px;">
															<label style="color: black">Confirmar contraseña</label>
															<input type="password" name="nuevo_pass_confirmacion"
																id="nuevo_pass_confirmacion" class="form-control"
																placeholder="Confirmar contraseña" minlength="8"
																required> <input type="hidden"
																name="pass_encriptada_nueva" id="pass_encriptada_nueva"
																value="">

														</div>
														<br>
														<div class="col-2">
															<a class="btn btn-light"
																onclick="mostrarContrasena('nuevo_pass_confirmacion')">
																<img width="20" height="20"
																src="<c:out value="${pageContext.servletContext.contextPath}"/>/Images/password.png" />
															</a>
														</div>
													</div>
												</div>
											</div>
											<button type="button" class="btn btn-success btn-sm"
												onclick="validar_datos()">Modificar</button>



										</form>

									</div>

								</div>

							</div>
							<br>
						</div>

					</div>
				</div>
			</div>
		</div>

	</div>



	<!-- Modal para redireccionar-->
	<div class="modal fade" id="modalRedireccionar" data-backdrop="static"
		tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header"
					style="background: -webkit-linear-gradient(left, #1143a6, #00c6ff)">
					<h4 class="modal-title" style="color: white">Redireccionando</h4>

				</div>

				<div>
					<h3>Seras redireccionado en 5 segundos...</h3>
					<h1>Deberás iniciar sesion con tu nuevo password</h1>

				</div>

			</div>
		</div>
	</div>

</body>
</html>
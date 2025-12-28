<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Olvide contraseña</title>

<!-- HOJAS DE ESTILO -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/vendors/bootstrap/dist/css/bootstrap.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/Css/css/custom.min.css" />
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"
	rel="stylesheet">
	<!-- JS -->
<script type="text/javascript"
	src="<c:out value="${pageContext.servletContext.contextPath}"/>/js/funciones.js"></script>
<!-- Alertas -->
<script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<!-- jQuery -->
	<script
		src="${pageContext.request.contextPath}/vendors/jquery/dist/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.js"></script>
</head>
<body class="login" >
  <div class="login_wrapper">
            <div class="animate form login_form">
                <section class="login_content">
                    <form>
                        <h4>Restablecer mi contraseña</h4>
                        <div>
                            Usuario
                            <input type="text" class="form-control" id="nombreUsuario" name="nombreUsuario" placeholder="Usuario" required>
                        </div>
                        <div>
                            Nueva contraseña
                            <input type="text" class="form-control" id="nuevoPass" name="nuevoPass" placeholder="Contraseña" required>
                        </div>
                        <div>
                            Confirmar contraseña
                            <input type="text" class="form-control" id="confirmarPass" name="confirmarPass" placeholder="Confirmar contraseña" required>
                        </div>

                        <div class="d-flex justify-content-center">
                            <button type="button" class="btn btn-success btn-sm" onclick="validarPass()">Aceptar</button>
                        </div>

                        <div class="clearfix"></div>

                        <div class="separator">

                            <div class="clearfix"></div>
                            <br>

                            <div>
                            </div>
                        </div>
                    </form>
                </section>
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
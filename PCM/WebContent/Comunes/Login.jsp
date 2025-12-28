<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>ACCESO</title>

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
<body class="login">
	<!-- DENTRO DEL BODY VOY A AGREGAR UNAS ETIQUETAS PARA MENSAJES 	 -->
	<c:if test="${not empty msgL}">
		<script>
			$(document).ready(function() {
				Swal.fire('', '${msgL}', 'error');
			});
		</script>
	</c:if>
	<c:if test="${not empty successU}">
		<script>
			$(document).ready(function() {
				Swal.fire('', '${successU}', 'success');
			});
		</script>

	</c:if>
	<c:if test="${not empty errorU}">
		<script>
			$(document).ready(function() {
				Swal.fire('', '${errorU}', 'error');
			});
		</script>

	</c:if>
	<!-- TERMINAN LAS ETIQUETAS PARA MENSAJE -->

	<!-- 	COMENZAMOS CON EL FORM LOGIN -->
	<div>
		<a class="hiddenanchor" id="signup"></a> <a class="hiddenanchor"
			id="signin"></a>

		<div class="login_wrapper">
			<div class="animate form login_form"
				style="display: flex; align-items: center; flex-direction: column; justify-content: center; width: 100%; min-height: 100%; padding: 20px; -webkit-animation-name: fadeInDown; animation-name: fadeInDown; -webkit-animation-duration: 1s; animation-duration: 1s; -webkit-animation-fill-mode: both; animation-fill-mode: both; -webkit-border-radius: 10px 10px 10px 10px; border-radius: 10px 10px 10px 10px; background: #fff; padding: 30px; width: 90%; max-width: 450px; position: relative; padding: 0px; -webkit-box-shadow: 0 30px 60px 0 rgba(0, 0, 0, 0.3); box-shadow: 0 30px 60px 0 rgba(0, 0, 0, 0.3); text-align: center; background-color: #f6f6f6; border-top: 1px solid #dce8f1; padding: 20px; text-align: center; -webkit-border-radius: 10px 10px 10px 10px; border-radius: 10px 10px 10px 10px;">
				<section class="login_content">
					<form
						action="${pageContext.servletContext.contextPath}/usuario?op=L"
						onsubmit="espera();" onchange="encriptar();" method="post">
						<h1>PCM</h1>

						<div>
							<input type="text" class="form-control" id="nombre_usuario" name="nombre_usuario"
								placeholder="Usuario" required>
						</div>
						<div>
							<input type="password" class="form-control" id="password" name="password"
								placeholder="Contraseña" required="">
						</div>
						 <input type="hidden" name="encriptacion" id="encriptacion">
								
						<div class="d-flex justify-content-center">
							<button type="submit" class="btn btn-link">Iniciar</button>
							<a class="reset_pass" href="${pageContext.request.contextPath}/Comunes/OlvidePass.jsp">¿Olvidaste tu
								contraseña?</a>
						</div>
					</form>
					<div class="clearfix"></div>

					<div class="separator">

						<div class="clearfix"></div>

						<div>
							<img class="img-responsive img-rounded" height="70" width="100"
								src="${pageContext.request.contextPath}/Images/logo-merza.png"
								alt="...">

						</div>
					</div>
				</section>
			</div>


		</div>
	</div>
</body>
</html>
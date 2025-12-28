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
<body class="login">
<div>
        <a class="hiddenanchor" id="signup"></a>
        <a class="hiddenanchor" id="signin"></a>

        <div class="login_wrapper">
            <div class="animate form login_form">
                <section class="login_content">
                    <form>
                        <h1>Olvide mi contraseña</h1>
                        <div>
                            Usuario
                            <input type="text" class="form-control" id="nombreUsuario" name="nombreUsuario" placeholder="Usuario" required>
                        </div>

                        <div class="d-flex justify-content-center">
                            <button type="button" class="btn btn-success btn-sm" onclick="validardatos()">Aceptar</button>
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
    </div>
	<!-- 	COMENZAMOS CON EL FORM LOGIN -->

</body>
</html>
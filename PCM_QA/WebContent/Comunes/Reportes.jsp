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
<title>Reportes</title>

<jsp:include page="Master.jsp" />
</head>

<body onload="reportes();">
	<div class="container body">
		<div class="main_container">
			<div class="right_col" role="main" style="min-height: 572px;">
				<div class="x_panel">
					<div class="container-fluid row">
						<!--     COMIENZA EL BODY -->
						<div class="x_panel col-6">
							<div class="x_content">
								<div class="justify-content-center">
									<div class="x_title">
										<h7>Atributos registrados</h7>
										<div class="clearfix"></div>
										<form id="reporteGenerico"
											action="${pageContext.servletContext.contextPath}/producto?op=reporteAtributosAll"
											method="post">
											<button type="button" onclick=reporteGenerico();
												class="btn btn-success btn-sm">
												<i class="fa fa-download" style="font-size: 12px;">General</i>
											</button>
										</form>
									</div>
									<div class="container card bg-light">
										<!-- un reporte -->
										<form id="reporteAtributos"
											action="${pageContext.servletContext.contextPath}/producto?op=reporteAtributos"
											method="post" onsubmit="espera();">
											<select name="familia" id="familia" class="form-control"
												required="required">

												<option value="0">Familia</option>
												<option value="01">Abarrotes comestibles</option>
												<option value="02">Abarrotes no comestibles</option>
												<option value="03">Carnes frias y salchichoneria</option>
												<option value="04">Vinos y licores</option>
												<option value="05">Farmacia</option>
												<option value="06">Dulceria</option>
												<option value="07">Lacteos</option>
												<option value="08">Otras categorias</option>
											</select> <br> Desde<input id="fechaInicioA" class="form-control"
												name="fechaInicioA" type="date" value="" required="required">
											Hasta<input id="fechaFinA" class="form-control" name="fechaFinA"
												type="date" value="" required="required"> <br>
											<button type="button" onclick=reporteAtributos();
												class="btn btn-success btn-sm">
												<i class="fa fa-download" style="font-size: 12px"></i>
											</button>
										</form>
										<br>

									</div>


								</div>

							</div>
						</div>
						<!-- otro panel -->
						<div class="x_panel col-6">
							<div class="x_content">
								<div class="justify-content-center">
									<div class="x_title">
										<h7>Historial material</h7>
										<div class="clearfix"></div>

									</div>
									<div class="container card bg-light">
										<!-- un reporte -->
										<form
											action="${pageContext.servletContext.contextPath}/producto?op=ReporteHistorial"
											method="post" onsubmit="espera();">
											<input id="idMaterialPDF" class="form-control"
												name="idMaterialPDF" type="number" placeholder="ID MATERIAL"
												value="" required="required"> <br> 
												Desde<input
												id="fechaInicioH" class="form-control" name="fechaInicioH"
												type="date" value="" required="required"> Hasta <input
												id="fechaFinH" class="form-control" name="fechaFinH"
												type="date" value="" required="required"> <br>
											<button type="submit" class="btn btn-success btn-sm">
												<i class="fa fa-download" style="font-size: 12px">PDF</i>
											</button>
										</form>
										<br>

									</div>


								</div>

							</div>
						</div>
						<!-- otro panel -->
						<div class="x_panel col-6">
							<div class="x_content">
								<div class="justify-content-center">
									<div class="x_title">
										<h7>Materiales agregados por plataforma</h7>
										<div class="clearfix"></div>

									</div>
									<div class="container card bg-light">
										<!-- un reporte -->
										<form id="reporteMatPlataforma"
											action="${pageContext.servletContext.contextPath}/producto?op=ReporteMaterialPlataforma"
											onsubmit="espera();" method="post">
											<select name="plataformas" id="plataformas"
												class="form-control" required="required">
												<option value="0">Plataforma</option>
											</select> <br> <select name="categoria" id="categoria"
												class="form-control" required="required">
												<option value="">Categoría</option>
												<option value="">TODAS</option>

											</select> <br> Desde<input id="fechaInicio" class="form-control"
												name="fechaInicio" type="date" value="" required="required">
											Hasta<input id="fechaFin" class="form-control" name="fechaFin"
												type="date" value="" required="required">

											<!-- 											<button type="button" class="btn btn-ligth" -->
											<!-- 												onclick="seleccionarPlataforma()"> -->
											<!-- 												<i class="fa fa-download" -->
											<!-- 													style="font-size: 24px; color: blue">PDF</i> -->
											<!-- 											</button> -->
										</form>

										<form id="reporteMatPlataformaExcel"
											action="${pageContext.servletContext.contextPath}/producto?op=ReporteMaterialPlataformaExcel"
											onsubmit="espera();" method="post">
											<input id="plataformasExcel" name="plataformasExcel"
												type="hidden" value="" required="required"> <input
												id="categoriaExcel" name="categoriaExcel" type="text"
												style="visibility: hidden" value="" required="required">
											Desde<input id="fechaInicioExcel" name="fechaInicioExcel"
												type="hidden" value="" required="required">Hasta <input
												id="fechaFinExcel" name="fechaFinExcel" type="hidden"
												value="" required="required"> <br>
											<button type="button" class="btn btn-success btn-sm"
												onclick="reporteExcelPlataforma()">
												<i class="fa fa-download" style="font-size: 12px;"></i>
											</button>
										</form>
										<br>

									</div>


								</div>

							</div>
						</div>

						<!-- 						reporte -->
						<div class="x_panel col-6">
							<div class="x_content">
								<div class="justify-content-center">
									<div class="x_title">
										<h7>Información faltante</h7>
										<form id="reporteInfoFaltante"
											action="${pageContext.servletContext.contextPath}/producto?op=infoFaltante"
											onsubmit="espera();" method="post">
											<button type="button" class="btn btn-success btn-sm"
												onclick="reporteSinInfo()">
												<i class="fa fa-download" style="font-size: 12px"></i>
											</button>
										</form>
										<div class="clearfix"></div>
										<h7>Imagenes</h7>
										<form
											action="${pageContext.servletContext.contextPath}/producto?op=ExcelImagen"
											onsubmit="espera();" method="post">
											<button type="submit" class="btn btn-success btn-sm">
												<i class="fa fa-download" style="font-size: 12px"></i>
											</button>
										</form>
										<div class="clearfix"></div>
										<h7>Rutas imagenes(Amazon/Mercado libre)</h7>
										<form
											action="${pageContext.servletContext.contextPath}/producto?op=ReporteImagenes"
											onsubmit="espera();" method="post">
											<button type="submit" class="btn btn-success btn-sm">
												<i class="fa fa-download" style="font-size: 12px"></i>
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




</body>
</html>
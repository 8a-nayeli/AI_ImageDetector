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
<title>Index</title>
	<script
		src="${pageContext.request.contextPath}/vendors/jquery/dist/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.js"></script>

<jsp:include page="Master.jsp" />
</head>
<body onload="cantMaterialesPlataformas();">
	<div class="container body">
		<div class="main_container">
			<div class="right_col" role="main" style="min-height: 572px;">
				<div class="x_panel">
					<div class="x_content row justify-content-center">
						<div class="col-md-4 col-sm-4  profile_details">
							<div class="well profile_view">
								<div class="container row">
									<div class="right col-sm-6 text-center">
										<h4 class="brief">
											<i><font style="font-size: 20px; font-style: oblique;">Merza
													va </font></i>
										</h4>
										<img
											src="${pageContext.request.contextPath}/Images/MerzaVa.png"
											alt="..." class="img-circle profile_img mCS_img_loaded">
									</div>

									<div class="right col-sm-6 text-center">
										<div class="row">

											<h2>Materiales al momento</h2>
											<span id="MerzaVa" style="font-size: 30px"
												class="badge badge-success"></span>
										</div>
										<br>
										<form
											action="${pageContext.servletContext.contextPath}/producto?op=AllMaterialesPlataformas&plataforma=1"
											onsubmit="espera();" method="post">

											<button type="submit" class="btn btn-info btn-sm">
												<i class="fa fa-download"> </i><font
													style="vertical-align: inherit;"></font>
											</button>
										</form>


									</div>

								</div>

							</div>
						</div>
						<div class="col-md-4 col-sm-4  profile_details">
							<div class="well profile_view">
								<div class="container row">
									<div class="right col-sm-6 text-center">
										<h4 class="brief">
											<i><font style="font-size: 20px; font-style: oblique;">Duero
													va </font></i>
										</h4>
										<img class="img-responsive img-rounded mCS_img_loaded"
											src="${pageContext.request.contextPath}/Images/Duero_Va.png"
											alt="..." width="100" height="100">

									</div>

									<div class="right col-sm-6 text-center">
										<div class="row">

											<h2>Materiales al momento</h2>
											<span id="DueroVa" style="font-size: 30px"
												class="badge badge-success"></span>
										</div>
										<br>
										<form
											action="${pageContext.servletContext.contextPath}/producto?op=AllMaterialesPlataformas&plataforma=2"
											onsubmit="espera();" method="post">

											<button type="submit" class="btn btn-info btn-sm">
												<i class="fa fa-download"> </i><font
													style="vertical-align: inherit;"></font>
											</button>
										</form>

									</div>

								</div>

							</div>
						</div>
						<div class="col-md-4 col-sm-4  profile_details">
							<div class="well profile_view">
								<div class="container row">
									<div class="right col-sm-6 text-center">
										<h4 class="brief">
											<i><font style="font-size: 20px; font-style: oblique;">B2B
											</font></i>
										</h4>
										<img class="img-responsive img-rounded mCS_img_loaded"
											src="${pageContext.request.contextPath}/Images/Duero_Va.png"
											alt="..." width="100" height="100">
									</div>

									<div class="right col-sm-6 text-center">
										<div class="row">

											<h2>Materiales al momento</h2>
											<span id="B2B" style="font-size: 30px"
												class="badge badge-success"></span>
										</div>
										<br>
										<form
											action="${pageContext.servletContext.contextPath}/producto?op=AllMaterialesPlataformas&plataforma=3"
											onsubmit="espera();" method="post">

											<button type="submit" class="btn btn-info btn-sm">
												<i class="fa fa-download"> </i><font
													style="vertical-align: inherit;"></font>
											</button>
										</form>


									</div>

								</div>

							</div>
						</div>
						<div class="col-md-4 col-sm-4  profile_details">
							<div class="well profile_view">
								<div class="container row">
									<div class="right col-sm-6 text-center">
										<h4 class="brief">
											<i><font style="font-size: 20px; font-style: oblique;">B2C
											</font></i>
										</h4>
										<img class="img-responsive img-rounded mCS_img_loaded"
											src="${pageContext.request.contextPath}/Images/Duero_Va.png"
											alt="..." width="100" height="100">

									</div>

									<div class="right col-sm-6 text-center">
										<div class="row">

											<h2>Materiales al momento</h2>
											<span ID="B2C" style="font-size: 30px"
												class="badge badge-success"></span>
										</div>
										<br>
										<form
											action="${pageContext.servletContext.contextPath}/producto?op=AllMaterialesPlataformas&plataforma=5"
											onsubmit="espera();" method="post">

											<button type="submit" class="btn btn-info btn-sm">
												<i class="fa fa-download"> </i><font
													style="vertical-align: inherit;"></font>
											</button>
										</form>


									</div>

								</div>

							</div>
						</div>
						<div class="col-md-4 col-sm-4  profile_details">
							<div class="well profile_view">
								<div class="container row">
									<div class="right col-sm-6 text-center">
										<h4 class="brief">
											<i><font style="font-size: 12px; font-style: oblique;">MobilC/ Amazon /Mercado libre
											</font></i>
										</h4>
										<img class="img-responsive img-rounded mCS_img_loaded"
											src="${pageContext.request.contextPath}/Images/Duero_Va.png"
											alt="..." width="40" height="40">
											<br>
											<img class="img-responsive img-rounded mCS_img_loaded"
											src="${pageContext.request.contextPath}/Images/Amazon.png"
											alt="..." width="70" height="60">
											<br>
											<img class="img-responsive img-rounded mCS_img_loaded"
											src="${pageContext.request.contextPath}/Images/Mercado-libre.png"
											alt="..." width="40" height="50">

									</div>

									<div class="right col-sm-6 text-center">
										<div class="row">

											<h2>Materiales con imagen</h2>
											<span ID="IMG" style="font-size: 30px"
												class="badge badge-success"></span>
										</div>
										<br>
										<div class="row justify-content-end">
												<form
											action="${pageContext.servletContext.contextPath}/producto?op=ExcelImagen"
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
						<div class="col-md-4 col-sm-4  profile_details">
							<div class="well profile_view">
								<div class="container row">
									<div class="right col-sm-6 text-center">
										<h4 class="brief">
											<i><font style="font-size: 20px; font-style: oblique;">Cava
													Duero </font></i>
										</h4>
										<img class="img-responsive img-rounded mCS_img_loaded"
											src="${pageContext.request.contextPath}/Images/cava-del-duero-logo.png"
											alt="..." width="140" height="70">

									</div>

									<div class="right col-sm-6 text-center">
										<div class="row">

											<h2>Materiales transmitidos al momento</h2>
											<span id="Cava" style="font-size: 30px"
												class="badge badge-success"></span>
										</div>
										<br>
										<form
											action="${pageContext.servletContext.contextPath}/producto?op=AllMaterialesPlataformas&plataforma=4"
											onsubmit="espera();" method="post">

											<button type="submit" class="btn btn-info btn-sm">
												<i class="fa fa-download"> </i><font
													style="vertical-align: inherit;"></font>
											</button>
										</form>


									</div>

								</div>

							</div>
						</div>
						<div class="col-md-4 col-sm-4  profile_details">
							<div class="well profile_view">
								<div class="container row">
									<div class="right col-sm-6 text-center">
										<h4 class="brief">
											<i><font style="font-size: 20px; font-style: oblique;">Wabi
											</font></i>
										</h4>
										<img class="img-responsive img-rounded mCS_img_loaded"
											src="${pageContext.request.contextPath}/Images/Duero_Va.png"
											alt="..." width="100" height="100">

									</div>

									<div class="right col-sm-6 text-center">
										<div class="row">

											<h2>Materiales transmitidos al momento</h2>
											<span id="Wabi" style="font-size: 30px"
												class="badge badge-success"></span>
										</div>
										<br>

										<form
											action="${pageContext.servletContext.contextPath}/producto?op=AllMaterialesPlataformas&plataforma=8"
											onsubmit="espera();" method="post">

											<button type="submit" class="btn btn-info btn-sm">
												<i class="fa fa-download"> </i><font
													style="vertical-align: inherit;"></font>
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
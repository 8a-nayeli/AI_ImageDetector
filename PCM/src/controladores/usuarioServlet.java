package controladores;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.beans.Mail;
import modelo.beans.usuario;
import modelo.datos.conexion;
import modelo.datos.usuarioDAO;

@WebServlet(name = "usuario", urlPatterns = { "/usuario" })
public class usuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public usuarioServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("##Dentro de UsuarioServlet##");
		String op = request.getParameter("op");
		String url = "Login.jsp";
		HttpSession sesion = (HttpSession) request.getSession();
		usuario u = (usuario) sesion.getAttribute("usuario");
		response.setContentType("text/html");

		// generamos las referencias de los objetos a llenar

		usuarioDAO udao = new usuarioDAO();
		switch (op) {
		//
		case "Token":
			String aux1 = request.getParameter("aux");
			url = "/Comunes/RestablecerPassword.jsp?aux=" + aux1;
			break;

		case "L":// caso de login
			// verificamos que los datos son de algun usuario en la bd
			String usuario = request.getParameter("nombre_usuario");
			String password = request.getParameter("encriptacion");
			u = new usuario();
			if (conexion.getInstance().setup() != -1) {
				u = udao.validarSesion(usuario, password);
				if (u == null) {
					request.setAttribute("msgL", "Datos incorrectos");
					url = "/Login.jsp";

				} else {
					if (u.getUsuario() != null) {
						if (u.getEstatus().equals("A")) {
							// System.out.println(u.getUsuario()+" "+u.getIdRol()+" " +u.getEstatus());
							request.setAttribute("usuario", u);
							sesion = request.getSession(true);
							// hacemos que la sesion nunca expire con un -1
							sesion.setMaxInactiveInterval(2700);

							sesion.setAttribute("usuario", u);
							// directo a consulta
							url = "/producto?op=Listar&pagina=1";
							// Vamos al index
							// url="Comunes/Index.jsp";
							// Le tengo que decir que si es admin se vaya a Index
							if (u.getIdRol().equals("1")) {
								url = "/Comunes/Index.jsp";
							}
							if (u.getIdRol().equals("4") || u.getIdRol().equals("5")) { // Diseño
								url = "/producto?op=AdministrarImg&pagina=1";
							}

						} else {
							request.setAttribute("msgL", "Usuario inactivo");

						}
					}

				}

			} else {
				url = "Login.jsp";
				request.setAttribute("msgL", "Comprueba tu conexión");

			}

			break;

		case "Cuenta":
			System.out.println("Entro en Cuenta");
			// pasarle el objeto de usuario previamente creado en el login
			request.setAttribute("dato", u);
			System.out.println(u.getUsuario() + ",," + u.getPass() + ",," + u.getIdRol());

			url = "/Comunes/PerfilUsuario.jsp";

			break;

		case "busqueda":
			System.out.println("Servlet usuario busqueda");
			udao = new usuarioDAO();
			String id = request.getParameter("buscador");
			System.out.println("id" + id);
			String pagina1 = request.getParameter("pagina");
			String cantidad1 = "3";
			System.out.println("id" + id + "pagina " + pagina1 + "cantidad " + cantidad1);
			List<usuario> datos1 = udao.busqueda(pagina1, id);
			request.setAttribute("datos", datos1);
			request.setAttribute("pagina", pagina1);
			url = "Comunes/AdministracionUsuario.jsp";

			break;

		case "Listar":
			System.out.println("listar");
			udao = new usuarioDAO();
			String pagina = request.getParameter("pagina");
			List<usuario> datos = udao.busqueda(pagina, "");
			request.setAttribute("datos", datos);
			request.setAttribute("pagina", pagina);
			url = "Comunes/AdministracionUsuario.jsp";

			break;

		case "Modificar":
			System.out.println("MODIFICAR");
			String estatus = (request.getParameter("estatus_editar"));
			String rol = (request.getParameter("rol_editar"));
			String nombre = request.getParameter("nombre_usuario_editar");
			String idUsuarioModifica = u.getIdUsuario();
			String idUsuario = (request.getParameter("id_usuario_editar"));
			String correo = request.getParameter("correo_editar");
			String alertas = request.getParameter("alertas");
			udao.usuarioEdicion(rol, estatus, idUsuario, idUsuarioModifica, alertas);
			String auxString = (request.getParameter("aux"));// si es 2, mando alerta

			System.out.println("El valor de auxString es :" + auxString);
			if (auxString.equals("2")) {// Quiere decir que si tengo que mandar correo
				// info para alertas
				if (alertas.equals("1")) {
					alertas = "Activadas";
				} else {
					alertas = "Desactivadas";
				}
				// Info para rol
				String rolS = "";
				if (rol.equals("1")) {
					rolS = "Administrador";
				}
				if (rol.equals("2")) {
					rolS = "Editor";
				}
				if (rol.equals("3")) {
					rolS = "Lector";
				}
				if (rol.equals("4")) {
					rolS = "Diseño";
				}

				String titulo = "CAMBIO DE PRIVILEGIOS PCM";
				String cuerpo = "Buen día, <br>"
						+ " Tus privilegios para el portal: http://10.1.0.12:8080/PCM/  han sido modificados: <br>"
						+ "<h2> NOMBRE DE USUARIO: " + nombre + "<br>" + "ROL: " + rolS + "<br>" + "ESTATUS: " + estatus
						+ "<br>" + "Alertas de actualizción de imagen: " + alertas + "</h2>";

				Mail mailEdit = new Mail(correo, titulo, cuerpo, "gmail");
				String errorEdit = mailEdit.send();
				if (errorEdit.equals("")) { // Si error = ""
					System.out.println("Envio Correcto");
				} else { // Si error != "" --> Es decir, ha habido un error.
					System.out.println("Error de envio" + errorEdit);
				}
			}

			url = "/usuario?op=Listar&pagina=1";

			break;

		case "Salir":
			request.getSession().invalidate();
			// hacemos null el objeto usuario
			u = null;
			request.setAttribute("usuario", null);
			url = "/Login.jsp";
			break;

		case "olvidepass":
			u = new usuario();
			System.out.println("Olvide pass");
			correo = request.getParameter("email_cambio");
			String nombreUsuario = request.getParameter("usuario_cambio");
			System.out.println("Olvide pass " + correo + nombreUsuario);
			if (conexion.getInstance().setup() != -1) {
				if (u != null) {
					boolean aux = udao.olvidePass(correo, nombreUsuario);
					if (aux) {
						String pass = request.getParameter("encriptacion_cambio");
						String passNormal = request.getParameter("password_nuevo_cambio");
						udao.usuarioCambioPass(nombreUsuario, pass);

						String titulo = "CAMBIO DE CONTRASEÑA PCM";
						String cuerpo = "Buen día, <br>"
								+ " Tus nuevas credenciales para el  portal: http://10.1.0.12:8080/PCM/  son las siguientes: <br>"
								+ "<h2> NOMBRE DE USUARIO: " + nombreUsuario + "<br>" + "CONTRASEÑA: " + passNormal
								+ "</h2>";
						Mail mailOp = new Mail(correo, titulo, cuerpo, "gmail");
						String errorOp = mailOp.send();

						if (errorOp.equals("")) {
							System.out.println("Envio Correcto");
						} else {
							System.out.println("Envio incorrecto");

						}

						request.setAttribute("successU",
								"Se genero una nueva contraseña, por favor verifica en tu correo");
					} else {
						request.setAttribute("errorU", "Datos incorrectos");

					}

				}

			}
			url = "/Login.jsp";

			break;

		}

		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession sesion = (HttpSession) request.getSession();
		usuario u = (usuario) sesion.getAttribute("usuario");
		String op = request.getParameter("op");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		usuarioDAO udao = new usuarioDAO();
		usuario obju = new usuario();

		switch (op) {
		case "restablecerPass":
			System.out.println("RESTABLECER");
			String usuarioC = request.getParameter("usuario");
			String correo = request.getParameter("correo");
			String tokenMD5 = request.getParameter("tokenMD5");

			String titulo = "CAMBIO DE CONTRASEÑA PCM";
			String cuerpo = "<div class='d-flex justify-content-center' style='height:100%;margin:0;padding:0;font-family:Arial,Helvetica,sans-serif;font-size:24px;line-height:167%;background-color:#efeeee;padding-left:75px;padding-right:75px;padding-top:45px;padding-bottom:0px'>"
					+ "<h2 style=color:'blue'>PCM</h2> <br />" + "<div class='d-flex justify-content-center' >  "
					+ " <b> ¡Hola " + usuarioC + "!</b> " + "    <div style = 'background-color:white'>"
					+ "   Estas recibiendo este correo porque se ha solicitado un cambio de contraseña para tu cuenta, <br />"
					+ "  si no has sido tu, ignora esta mensaje.<br />" + " Para modificar tu contraseña da click"
					+ " <a class='reset_pass 'href='http://10.1.0.12:8080/PCM//usuario?op=Token&aux=" + tokenMD5
					+ "'>aquí</a> " + "</div>" + "<br />" + " </div>" + "<br />" + "</div>";

			Mail mailCp = new Mail(correo, titulo, cuerpo, "gmail");
			String errorCp = mailCp.send();

			if (errorCp.equals("")) {
				System.out.println("Envio Correcto");
			} else {
				System.out.println("Envio incorrecto");

			}

			response.getWriter().write("verdadero");

			break;
		case "validoUsuario":
			if (conexion.getInstance().setup() != -1) {

				// Tenemos que valida que el usuario exista para extraer el correo
				String usuario = request.getParameter("usuario");
				System.out.println("Valido usuario" + usuario);

				String correo1 = udao.validarUsuario(usuario);
				response.getWriter().write(correo1);
			}

			break;
		case "nombreUsuario":
			if (conexion.getInstance().setup() != -1) {

				// Tenemos que valida que el usuario exista para extraer el correo
				String usuario = request.getParameter("usuario");
				System.out.println("Valido usuario" + usuario);
				String correo1 = udao.validarUsuario(usuario);
				response.getWriter().write(correo1);
			}

			break;
		case "registroToken":
			// Registro en bd
			String usuario = request.getParameter("usuario");
			String token = request.getParameter("token");
			System.out.println("REGISTRO :" + usuario + token);
			String aux = udao.registroToken(usuario, token);
			response.getWriter().write(aux);
			break;
		case "restauraPass":
			String user = request.getParameter("user");
			String pass = request.getParameter("pass");
			udao.usuarioCambioPass(user, pass);

			response.getWriter().write("verdadero");

			break;

		case "AJAX":
			user = request.getParameter("user");
			String passNuevo = request.getParameter("passwordNuevo");
			String passViejo = request.getParameter("passwordViejo");
			pass = request.getParameter("pass");
			correo = request.getParameter("correo");
			obju = udao.validar_usuario_cambio_pass(user, passViejo);

			if (obju != null) {
				// si existe el usuario en la bd entonces, lo modificamos
				udao.usuarioCambioPass(user, passNuevo);

				titulo = "CAMBIO DE CONTRASEÑA PCM";
				cuerpo = "Buen día, <br>"
						+ " Tus nuevas credenciales para el  portal: http://10.1.0.12:8080/PCM/  son las siguientes: <br>"
						+ "<h2> NOMBRE DE USUARIO: " + user + "<br>" + "CONTRASEÑA: " + pass + "</h2>";
				mailCp = new Mail(correo, titulo, cuerpo, "gmail");
				errorCp = mailCp.send();

				if (errorCp.equals("")) {
					System.out.println("Envio Correcto");
				} else {
					System.out.println("Envio incorrecto");

				}

				response.getWriter().write("verdadero");
			} else {
				// no existe el usuario en la bd, regresamos como respuesta un false
				response.getWriter().write("falso");
			}
			break;

		case "REGISTRO":
			String nombreNuevo = request.getParameter("name");
			String correoNuevo = request.getParameter("email");
			String passNuevo1 = request.getParameter("pass");
			String rolNuevo = request.getParameter("rol");
			pass = request.getParameter("credencial");
			String idUsuarioRegistra = u.getIdUsuario();
			System.out.println(
					"REGISTRO: " + nombreNuevo + " " + correoNuevo + " " + pass + " " + rolNuevo + " " + passNuevo1);
			// validamos que el correo no exista ya en la BD
			// si el correo de usuario no existe en la bd, entonces lo agregamos
			if (udao.validarCorreo(correoNuevo))
			// no encontro correo registrado, puede agregar
			{
				// Direccionamiento a portal de prueba
				udao.usuarioNuevo(nombreNuevo, passNuevo1, correoNuevo, rolNuevo, idUsuarioRegistra);
				request.setAttribute("success", "Nuevo usuario registrado .");
				titulo = "CREDENCIALES DE ACCESO PCM";
				cuerpo = "Buen día, <br>"
						+ " Tus credenciales para el  portal: http://10.1.0.12:8080/PCM/  son las siguientes: <br>"
						+ "<h2> NOMBRE DE USUARIO: " + nombreNuevo + "<br>" + "CONTRASEÑA: " + pass + "</h2>";
				Mail mailRegistro = new Mail(correoNuevo, titulo, cuerpo, "gmail");
				String errorRegistro = mailRegistro.send();
				if (errorRegistro.equals("")) { // Si error = ""
					System.out.println("Envio Correcto");
				} else { // Si error != "" --> Es decir, ha habido un error.
					System.out.println("Error de envio" + errorRegistro);
				}
				response.getWriter().write("true");

			}

			else {// no se puede agregar usuario
				response.getWriter().write("false");
			}
			break;

		case "Editar":
			udao = new usuarioDAO();
			usuario datosUsuario = udao.consultaIndividual(request.getParameter("id"));
			response.getWriter()
					.write(datosUsuario.getIdUsuario() + " " + datosUsuario.getUsuario() + " "
							+ datosUsuario.getCorreo() + " " + datosUsuario.getIdRol() + " " + datosUsuario.getEstatus()
							+ " " + datosUsuario.getAlerta());
			break;

		case "ValidaToken":
			user = request.getParameter("user");
			token = request.getParameter("token");
			aux = udao.canjeToken(user, token);
			response.getWriter().write(aux);
			break;
		case "CambioPass": //Este metodo se utiliza cuando se olvida la contraseña y se modifica
			user = request.getParameter("user");
			pass = request.getParameter("pass");
			System.out.println("ENTRE AL ULTIMO METODO" +user+ pass);

			udao.usuarioCambioPass(user, pass);
			response.getWriter().write("true");


			break;
		default:
			doGet(request, response);
			break;

		}

	}

}

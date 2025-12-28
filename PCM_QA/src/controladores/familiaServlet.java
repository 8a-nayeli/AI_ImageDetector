package controladores;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.beans.familia;
import modelo.beans.usuario;
import modelo.datos.atributoDAO;
import modelo.datos.familiaDAO;


@WebServlet(name = "familia", urlPatterns = { "/familia" })
public class familiaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public familiaServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//System.out.println("##Dentro de marcaServlet##");
		String op = request.getParameter("op");
		String url = "AdministracionAtributos.jsp";

		HttpSession sesion = (HttpSession) request.getSession();
		usuario u = (usuario) sesion.getAttribute("usuario");
		familiaDAO fdao = new familiaDAO();
		atributoDAO adao = new atributoDAO();
		response.setContentType("text/html");
		switch (op) {
		case "modificacion":
			//System.out.println("ENTRE A MODIFIACION FAMILIA");
			String estatus = request.getParameter("estatus_editar");// ESTATUS PARA EL MATERIAL RECHAZADO O ACEPTADO
			String nombreFamilia = request.getParameter("nombre_familia_editar");
			String usuario = u.getIdUsuario();
			//System.out.println("estatus "+  estatus +" familia "  +nombreFamilia + "usuario " + usuario);
//CAMBIO ESTATUS
			fdao.cambiarEstatusFamilia(estatus, nombreFamilia, usuario);
			if (estatus.equals("A")) {
				String atributosR[] = request.getParameterValues("idchecks");
				if (atributosR != null && atributosR.length != 0) {// Check seleccionados
					for (int i = 0; i < atributosR.length; i++) {
						// SABER SI LA FAMILIA YA TIENE ESE ATRIBUTO 
						
						String estatusAtributoFamilia=fdao.estatusAtributoFamilia(nombreFamilia, atributosR[i]);
						//	System.out.println("valor esperado: " + estatusAtributoFamilia + "nombre familia: " + nombreFamilia + "atributo: " + atributosR[i]);
						if (estatusAtributoFamilia == null) {// lo puedo agregar
							String idAtributo = adao.idAtributo(atributosR[i]);
							String idFamilia=fdao.idFamilia(nombreFamilia);
							//System.out.println("idFamilia:" +  idFamilia +" idAtibuto: " + idAtributo +"usuario:" + usuario);
							adao.nuevoAtributoFamilia(idFamilia, idAtributo, usuario);
						}
						if (estatusAtributoFamilia.equals("I")) { // estaba inactivo, lo voy a editar a Activo
							String idAtributo = adao.idAtributo(atributosR[i]);
							String idFamilia=fdao.idFamilia(nombreFamilia);
							fdao.cambiarEstatusAtFam("A", idFamilia, idAtributo);

						}

					}
				}
				// ATRIBUTOS NO REGISTRADOS
				String atributosNR[] = request.getParameterValues("idchecksoff");
				if (atributosNR != null && atributosNR.length != 0) {// Check seleccionados
					for (int i = 0; i < atributosNR.length; i++) {
						//	System.out.println("entre a no registrados ---" +atributosNR[i]);
						// chechar que no exista el registro
						String estatusAtributoFamilia=fdao.estatusAtributoFamilia(nombreFamilia, atributosNR[i]);
						//System.out.println("estatus"+ estatusAtributoFamilia + nombreFamilia + atributosNR[i]);
						if (estatusAtributoFamilia.equals("A")) { // estaba activo, lo paso a I
							String idAtributo = adao.idAtributo(atributosNR[i]);
							String idFamilia=fdao.idFamilia(nombreFamilia);
							fdao.cambiarEstatusAtFam("I", idFamilia, idAtributo);

						}


					}
				}

			}

			url = "/familia?op=Listar&pagina=1";
			break;

		case "registro":
			String idFamilia = request.getParameter("idFamilia");
			String idUsuarioRegistra = u.getIdUsuario();
			//System.out.println("REGISTRO: " + idFamilia + idUsuarioRegistra);
//Ya no tengo que validar porque ajax ya valido 
			fdao.familiaNueva(idFamilia, idUsuarioRegistra);
			String atributo[] = request.getParameterValues("idchecks");
			if (atributo != null && atributo.length != 0) {
				for (int i = 0; i < atributo.length; i++) {
					// aqui insertamos el atributo en la familia con id s[i]
					// conocer id del atributo
					String idAtributo = adao.idAtributo(atributo[i]);
					adao.nuevoAtributoFamilia(idFamilia, idAtributo, idUsuarioRegistra);
					request.setAttribute("success", "Nueva familia registrada");

				}
			} else
				url = "/familia?op=Listar&pagina=1";
			break;
		case "busqueda":
			//System.out.println("Servlet familia busqueda");
			fdao = new familiaDAO();
			String id = request.getParameter("buscador");
			System.out.println("id" + id);
			String pagina1 = request.getParameter("pagina");
			//String cantidad1 = "3";
			//System.out.println("id" + id + "pagina " + pagina1 + "cantidad " + cantidad1);
			List<familia> datos1 = fdao.busqueda(pagina1, id);
			request.setAttribute("datos", datos1);
			request.setAttribute("pagina", pagina1);
			url = "Comunes/AdministracionFamilias.jsp";

			break;
		case "Listar":
			//System.out.println("listar familias");
			fdao = new familiaDAO();
			String pagina = request.getParameter("pagina");
			List<familia> datos = fdao.busqueda(pagina, "");
			request.setAttribute("datos", datos);
			request.setAttribute("pagina", pagina);
			url = "Comunes/AdministracionFamilias.jsp";
		}
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		switch (op) {
		// FAMILIAS
		case "checksAtributos": // Al editar la familia tomo este case
			// FAMILIA
			atributoDAO adao = new atributoDAO();
			String atributos = adao.atributosExistentes(); // todos los atributos
			atributos = atributos.substring(0, atributos.length() - 1);
			StringTokenizer st = new StringTokenizer(atributos, ",");
			int cantAtributos = st.countTokens();

			familiaDAO fdao = new familiaDAO();

			String atributosFamilia = fdao.atributosFamilia(request.getParameter("idFamilia"));// Atributos de la
																								// familia
			if (atributosFamilia != "") {
				atributosFamilia = atributosFamilia.substring(0, atributosFamilia.length() - 1);
			}
			//System.out.println("tamaño: " + cantAtributos + " | atributos: " + atributos + "| Atributos Familia: " + atributosFamilia);
			response.getWriter().write(cantAtributos + "," + atributosFamilia + "," + atributos);
			break;
		case "Editar":
			fdao = new familiaDAO();
			familia datosFamilia = fdao.consultaIndividual(request.getParameter("id"));
			response.getWriter().write(datosFamilia.getFamilia() + "$" + datosFamilia.getEstatus());
			break;
		case "atributosExistentes":
			adao = new atributoDAO();
			String atributo = adao.atributosExistentes();
			atributo = atributo.substring(0, atributo.length() - 1);
			st = new StringTokenizer(atributo, ",");
			cantAtributos = st.countTokens();
			System.out.println("tamaño: " + cantAtributos + " | atributo: " + atributo);
			response.getWriter().write(cantAtributos + "," + atributo);
			break;
		case "confirmacionRegistro":
			//System.out.println("entre a confirrmacion registro");
			String idFamilia = request.getParameter("idFamilia");
			fdao = new familiaDAO();
			String familia;
			int familiaValidar= Integer.parseInt(request.getParameter("idFamilia"));
			familia = fdao.validarFamiliaSap(familiaValidar);
			if ((familia != null)) // si encontro, se puede agregar
			{
				boolean aux1 = true;
				aux1 = fdao.validarFamiliaInterno(idFamilia);
				if (aux1) { // verdadero si no se puede agregar
					response.getWriter().write("internono");

				}

				else {
					String atributoChecar[] = request.getParameterValues("idchecks");
					if (atributoChecar == null) {
						response.getWriter().write("si" + "@" + familia + "@" + "0" + "@" + idFamilia);

					} else {
						response.getWriter().write("si" + "@" + familia + "@" + "1" + "@" + idFamilia);

					}

				}
			} else {
				response.getWriter().write("no");

			}

			break;
		default:
			doGet(request, response);
			break;
		}

	}

}

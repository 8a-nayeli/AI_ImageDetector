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
import modelo.beans.atributo;
import modelo.beans.usuario;
import modelo.datos.atributoDAO;
import modelo.datos.familiaDAO;
@WebServlet(name = "atributo", urlPatterns = { "/atributo" })
public class atributoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public atributoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("##Dentro de marcaServlet##");
		String op = request.getParameter("op");
		String url="AdministracionAtributos.jsp";
		
		HttpSession sesion = (HttpSession) request.getSession();
		usuario u = (usuario) sesion.getAttribute("usuario");
		atributoDAO adao = new atributoDAO();
		familiaDAO fdao = new familiaDAO();
	    response.setContentType("text/html");
		switch (op) {
		case "registro": 
			String atributoNuevo = request.getParameter("atributoNuevo");
			String idUsuarioRegistra=u.getIdUsuario();		
			//System.out.println("REGISTRO: "+atributoNuevo);
			//validamos que el atributo no exista
			boolean aux;
			aux=adao.validarAtributo(atributoNuevo);
			if((!aux))
				// no , puede agregar 
			{
				adao.atributoNuevo(atributoNuevo, idUsuarioRegistra);
				String idAtributo= adao.idAtributo(atributoNuevo);
				String idFamilia[] = request.getParameterValues("idchecks");
				if (idFamilia != null && idFamilia.length != 0) {
					for (int i = 0; i < idFamilia.length; i++) {
						//aqui insertamos el atributo en la familia 
						adao.nuevoAtributoFamilia(idFamilia[i], idAtributo, idUsuarioRegistra);
						
					}
				}
				request.setAttribute("success","Nuevo atributo registrado");
			}
						
			else 
			{//no se puede agregar marca
				request.setAttribute("error","atributo ya registrado, intente otro diferente");
			}
			

	
			url="/atributo?op=Listar&pagina=1";
			break;
			case "busqueda":
				//System.out.println("Servlet atributo busqueda");
	  			adao = new atributoDAO();
	  			String id= request.getParameter("buscador");
	  			//System.out.println("id"+ id );
	  		    String pagina1 = request.getParameter("pagina");
	  		   // String cantidad1="10";
	  		  // System.out.println("id"+ id+ "pagina "+ pagina1 + "cantidad "+ cantidad1  );
	  		    List<atributo> datos1 = adao.busqueda(pagina1,id);
	  			request.setAttribute("datos", datos1);
	  			request.setAttribute("pagina",pagina1);
	  			url="Comunes/AdministracionAtributos.jsp";
	  			
	  		break;
			case "Listar":
				System.out.println("listar atributos");
  				adao = new atributoDAO();
			    String pagina = request.getParameter("pagina");
				List<atributo> datos = adao.busqueda(pagina,"");
				request.setAttribute("datos", datos);
				request.setAttribute("pagina",pagina);
				url="Comunes/AdministracionAtributos.jsp";

				break;
			case "Modificar":
				//System.out.println("MODIFICAR ATRIBUTOS");
				String idAtributo= (request.getParameter("idAtributo_editar"));
				String estatus= (request.getParameter("estatus_editar"));
				String idUsuarioModifica=u.getIdUsuario();
				aux=false;
				//System.out.println("ID "+ idAtributo + " "+ estatus+ " "+ idUsuarioModifica);
				adao.atributoEdicion(idAtributo, estatus, idUsuarioModifica);
				request.setAttribute("success","Cambios realizados");
				url="/atributo?op=Listar&pagina=1";
				
				break;

		
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(url); 
		rd.forward(request, response);  
	    
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		switch (op) {
		case "familiasExistentesAtributos":
			System.out.println("Entre a familias existentes- atributos");
			familiaDAO fdao = new familiaDAO();
			String familia= fdao.familiasExistentes(); //22,33,44
			familia=familia.substring(0,familia.length()-1);
			StringTokenizer st = new StringTokenizer(familia, ",");
			int cantFamilias = st.countTokens();
			System.out.println("tamaño: "+cantFamilias+ " | familia: "  +familia);
			response.getWriter().write(cantFamilias+","+familia);

			
			break;
		case "Editar":
			atributoDAO adao = new atributoDAO();
			atributo datosAtributo= adao.consultaIndividual(request.getParameter("id"));
			response.getWriter().write(datosAtributo.getNombre()+"$"+datosAtributo.getEstatus());
			break;
	
			default:
			doGet(request, response);
			break;
		}
		

		
	}

}

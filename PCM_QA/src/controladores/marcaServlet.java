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
import modelo.beans.marca;
import modelo.beans.usuario;
import modelo.datos.marcaDAO;

@WebServlet(name = "marca", urlPatterns = { "/marca" })
public class marcaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public marcaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("##Dentro de marcaServlet##");
		String op = request.getParameter("op");
		String url="AdministracionMarca.jsp";
		
		HttpSession sesion = (HttpSession) request.getSession();
		usuario u = (usuario) sesion.getAttribute("usuario");
		marcaDAO mdao = new marcaDAO();
	    response.setContentType("text/html");
		switch (op) {
		case "registro": 
			String marcaNueva = request.getParameter("marcaNueva");
			String idMarca = request.getParameter("idMarca");

			String idUsuarioRegistra=u.getIdUsuario();
			System.out.println("REGISTRO: "+marcaNueva);
			//validamos que la marca no exista
			boolean aux;
			aux=mdao.validarMarca(marcaNueva);
			if((!aux))
				// no encontro correo registrado, puede agregar 
			{
				mdao.marcaNueva(marcaNueva, idMarca,idUsuarioRegistra);
				request.setAttribute("success","Nueva marca registrada");
			}
						
			else 
			{//no se puede agregar marca
				request.setAttribute("error","marca ya registrada, intente otra diferente");
			}
			url="/marca?op=Listar&pagina=1";
			break;
			case "busqueda":
				//System.out.println("Servlet marca busqueda");
	  			mdao = new marcaDAO();
	  			String id= request.getParameter("buscador");
	  			System.out.println("id"+ id );
	  		    String pagina1 = request.getParameter("pagina");
	  		    //registros mostrados por hoja
	  		    //String cantidad1="10";
	  		    //System.out.println("id"+ id+ "pagina "+ pagina1 + "cantidad "+ cantidad1  );
	  		    List<marca> datos1 = mdao.busqueda(pagina1,id);
	  			request.setAttribute("datos", datos1);
	  			request.setAttribute("pagina",pagina1);
	  			url="Comunes/AdministracionMarca.jsp";
	  			
	  		break;
			case "Listar":
				//System.out.println("listar marca");
  				mdao = new marcaDAO();
			    String pagina = request.getParameter("pagina");
				List<marca> datos = mdao.busqueda(pagina,"");
				request.setAttribute("datos", datos);
				request.setAttribute("pagina",pagina);
				url="Comunes/AdministracionMarca.jsp";

				break;
			case "Modificar":
				//System.out.println("MODIFICAR marca");
				String idRegistroMarca= (request.getParameter("idMarca_editar"));
				String nombre= (request.getParameter("nombre_marca_editar"));
				String estatus= (request.getParameter("estatus_editar"));
				String idUsuarioModifica=u.getIdUsuario();
				aux=false;
//				aux=mdao.validarMarcaEdicion(nombre);
//				if((!aux))
//					// no encontro correo registrado, puede agregar 
//				{
//					System.out.println("ID "+ idMarca + " "+ estatus+ " "+ idUsuarioModifica);
					mdao.marcaEdicion(idRegistroMarca,nombre, estatus, idUsuarioModifica);
					request.setAttribute("success","Cambios realizados");
//				}
//							
//				else 
//				{//no se puede agregar marca
//					request.setAttribute("error","Ya existe una marca con el mismo nombre");
//				}
		
				url="/marca?op=Listar&pagina=1";
				
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
		
		case "Editar":
			marcaDAO pldao = new marcaDAO();
			marca datosMarca= pldao.consultaIndividual(request.getParameter("id"));
			response.getWriter().write(datosMarca.getNombre()+"$"+datosMarca.getEstatus());
			break;
		default:
			doGet(request, response);
			break;
		}
		
		
	}

}

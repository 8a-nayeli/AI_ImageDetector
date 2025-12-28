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
import modelo.beans.plataforma;
import modelo.beans.usuario;
import modelo.datos.plataformaDAO;
import modelo.datos.productoDAO;

@WebServlet(name = "plataforma", urlPatterns = { "/plataforma" })
public class plataformaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public plataformaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("##Dentro de plataformaServlet##");
		String op = request.getParameter("op");
		String url="AdministracionPlataforma.jsp";
		
		HttpSession sesion = (HttpSession) request.getSession();
		usuario u = (usuario) sesion.getAttribute("usuario");
		plataformaDAO pldao = new plataformaDAO();
	    response.setContentType("text/html");
		switch (op) {
		case "registro": 
			System.out.println("Entre a registro plataforma");
			String plataformaNueva = request.getParameter("plataformaNueva");
			String idUsuarioRegistra=u.getIdUsuario();
			String tipo =request.getParameter("tipoPlataforma");
			//System.out.println("REGISTRO: "+plataformaNueva);
			//validamos que la plataforma no exista
			boolean aux;
			aux=pldao.validarPlataforma(plataformaNueva);
			if((!aux))
				// no encontro correo registrado, puede agregar 
			{
				pldao.plataformaNueva(plataformaNueva, idUsuarioRegistra,tipo);
				request.setAttribute("success","Nueva plataforma registrada");
			}
						
			else 
			{//no se puede agregar plataforma
				request.setAttribute("error","Plataforma ya registrada, intente otra diferente");
			}
			url="/plataforma?op=Listar&pagina=1";
			break;
			case "busqueda":
				//System.out.println("Servlet plataforma busqueda");
	  			pldao = new plataformaDAO();
	  			String id= request.getParameter("buscador");
	  			//System.out.println("id"+ id );
	  		    String pagina1 = request.getParameter("pagina");
	  		  //  String cantidad1="3";
	  		  // System.out.println("id"+ id+ "pagina "+ pagina1 + "cantidad "+ cantidad1  );
	  		    List<plataforma> datos1 = pldao.busqueda(pagina1,id);
	  			request.setAttribute("datos", datos1);
	  			request.setAttribute("pagina",pagina1);
	  			url="Comunes/AdministracionPlataforma.jsp";
	  			
	  		break;
			case "Listar":
				//System.out.println("listar plataforma");
  				pldao = new plataformaDAO();
			    String pagina = request.getParameter("pagina");
				List<plataforma> datos = pldao.busqueda(pagina,"");
				request.setAttribute("datos", datos);
				request.setAttribute("pagina",pagina);
				url="Comunes/AdministracionPlataforma.jsp";

				break;
			case "Modificar":
				//System.out.println("MODIFICAR plataforma");
				String idPlataforma= (request.getParameter("idPlataforma_editar"));
				String estatus= (request.getParameter("estatus_editar"));
				String idUsuarioModifica=u.getIdUsuario();
				aux=false;
				String tipoE= (request.getParameter("tipoPlataformaE"));
				//System.out.println("ID "+ idPlataforma + " "+ estatus+ " "+ idUsuarioModifica);
				pldao.plataformaEdicion(idPlataforma, estatus, idUsuarioModifica,tipoE);
				request.setAttribute("success","Cambios realizados");
				url="/plataforma?op=Listar&pagina=1";
				
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
		case "plataformasExistentes":
			plataformaDAO pdao = new plataformaDAO();
			String plataforma= pdao.plataformasExistentes(); //22,33,44
			plataforma=plataforma.substring(0,plataforma.length()-1);
			StringTokenizer st = new StringTokenizer(plataforma, ",");
			int cantPlataforma = st.countTokens()+1;
			String materialesPlataforma=pdao.materialesPlataforma(request.getParameter("idMaterial"));
			if(materialesPlataforma!="") {
				materialesPlataforma=materialesPlataforma.substring(0,materialesPlataforma.length()-1);	
			}
			//System.out.println("tamaño: "+cantPlataforma+ " | plataforma: "  +plataforma +"|Materiales plataforma" + materialesPlataforma);
			response.getWriter().write(cantPlataforma+","+materialesPlataforma+","+plataforma);
			break;
		case "plataformasExistentesv2":
			pdao = new plataformaDAO();
			plataforma = pdao.plataformasExistentes(); 
			plataforma=plataforma.substring(0,plataforma.length()-1);
			st = new StringTokenizer(plataforma, ";");
			cantPlataforma = st.countTokens();
			materialesPlataforma=pdao.materialesPlataforma(request.getParameter("idMaterial"));//Me entrega el nombre de la plataforma
			String destacadosPlataforma=pdao.destacadosPlataforma(request.getParameter("idMaterial"));//Me entrega si es detacado 
			String eanMaterial = pdao.eanPlataforma(request.getParameter("idMaterial"));//Nombre de plataforma y ean 
			if(materialesPlataforma!="") {
				//El material tiene plataforma asignada
				materialesPlataforma=materialesPlataforma.substring(0,materialesPlataforma.length()-1);	
			}
			if(destacadosPlataforma!="") {
				destacadosPlataforma=destacadosPlataforma.substring(0,destacadosPlataforma.length()-1);	
			}
			if(eanMaterial!="") {
				eanMaterial=eanMaterial.substring(0,eanMaterial.length()-1);	
			}

			
			productoDAO prdao = new productoDAO();
			String ean = prdao.materialesEan(request.getParameter("idMaterial"));
			ean = ean.replaceAll(",", ";");
			ean = ean.substring(0,ean.length()-1);
			
			String um = prdao.materialesUm(request.getParameter("idMaterial"));//PZ,CAJA,ETC
			um = um.replaceAll(",",";");
			um = um.substring(0,um.length()-1);

			String ConversionUm = prdao.materialesConversionUm(request.getParameter("idMaterial"));
			ConversionUm = ConversionUm.replaceAll(",", ";");
			ConversionUm = ConversionUm.substring(0,ConversionUm.length()-1);
			
			System.out.println("tamaño: "+cantPlataforma+ " | plataforma: "  +plataforma +"|Materiales plataforma" + materialesPlataforma+"|Conversion um"+ConversionUm+"| um"+um);
			response.getWriter().write(cantPlataforma+","+plataforma+","+ean+","+um+","+ConversionUm+","+materialesPlataforma+","+destacadosPlataforma+
					","+eanMaterial);

			//response.getWriter().write(cantPlataforma+","+materialesPlataforma+","+ean+","+um+","+aire+","+ConversionUm+","+plataforma);
			break;
		case "Editar":
			plataformaDAO pldao = new plataformaDAO();
			plataforma datosPlataforma= pldao.consultaIndividual(request.getParameter("id"));
			response.getWriter().write(datosPlataforma.getNombre()+"$"+datosPlataforma.getEstatus()+"$"+datosPlataforma.getTipo());
			break;
		default:
			doGet(request, response);
			break;
		}
		

		
	}

}

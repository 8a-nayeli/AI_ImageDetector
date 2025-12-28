package modelo.datos;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.beans.atributo;


public class atributoDAO {
	public void atributoEdicion(String idAtributo, String estatus, String idUsuarioModifica){
		System.out.print("ENTRE A EDICION ATRIBUTO"+ idAtributo + estatus + idUsuarioModifica);
		String sql = "{call atributoEdicion (?,?,?)}";
		try {
				CallableStatement cs = conexion.getInstance().getCN().prepareCall(sql);
				cs.setString(1,idAtributo);
				cs.setString(2, estatus);
				cs.setString(3, idUsuarioModifica);
				

				cs.execute();
				cs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	}
	
	
	public atributo consultaIndividual(String idPlataforma){
		String sql="select * from atributos where idAtributo=?";
		atributo a = new atributo();
		try{
			PreparedStatement ps=conexion.getInstance().getCN().prepareStatement(sql);
			ps.setString(1, idPlataforma);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				a.setNombre(rs.getString("nombre"));
				a.setEstatus(rs.getString("estatus"));
				a.setIdAtributo("idAtributo");
			
				
			}
			ps.close();
			rs.close();
		}
		catch(SQLException e){
			System.out.println("Error ConsultaIndividual plataforma:"+e.getMessage());
		}
		return a; 
	}
	
	public void nuevoAtributoFamilia(String familia, String idAtributo, String idUsuarioRegistra ){
		System.out.println("F: "+familia + "A:"+idAtributo+ "U:"+idUsuarioRegistra);
		String sql = "{call nuevoAtributoFamilia (?,?,?)}";		
		try {
				CallableStatement cs = conexion.getInstance().getCN().prepareCall(sql);
				cs.setString(1,familia);
				cs.setString(2, idAtributo);
				cs.setString(3, idUsuarioRegistra);
				cs.execute();
				cs.close();
				
			} catch (Exception e) {
				System.out.println("Error nuevo atributo familia:"+e.getMessage());
			}
		
	}
	
	public void atributoNuevo(String nombre, String idUsuarioRegistra ){
		String sql = "{call atributoNuevo (?,?)}";
		System.out.println("call atributoNuevo  "+nombre+" "+idUsuarioRegistra+"");
		
		try {
				CallableStatement cs = conexion.getInstance().getCN().prepareCall(sql);
				cs.setString(1,nombre);
				cs.setString(2, idUsuarioRegistra);
				cs.execute();
				cs.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}
	
	public String idAtributo(String nombre) {
		String aux=null;
		String sql = "SELECT idAtributo FROM atributos WHERE nombre=?";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ps.setString(1, nombre);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				aux=rs.getString(1);
			}
			else {
				aux=null;
			}
			
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			System.out.println("Error al conectar con la BD al validar " + e.getMessage());
		}
		return aux;
	}
	
	
	public boolean validarAtributo(String nombre) {
		boolean aux;
		aux=false;
		String sql = "SELECT * FROM atributos WHERE nombre=?";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ps.setString(1, nombre);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				aux=true; // Si existe, no se puede agregar
			}
			else {
				aux=false;
			}
			
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			System.out.println("Error al conectar con la BD al validar " + e.getMessage());
		}
		return aux;
	}
	
	
	public List<atributo> busqueda(String pagina, String busqueda)

	{
		System.out.println("SI ESTRE");

		System.out.println("BUSQUEDA GENERAL atributo");
		ArrayList<atributo> lista = new ArrayList<>();
		String sql = "execute busquedaGeneral 'atributos','nombre','idAtributo','"+pagina+"','20','"+busqueda+"',''";
		System.out.println("pagina " + pagina);

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				atributo atributo= new atributo();
				atributo.setIdAtributo(rs.getString("idAtributo"));
				atributo.setNombre(rs.getString("nombre"));
				atributo.setEstatus(rs.getString("estatus"));

				lista.add(atributo);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage()+ e.getLocalizedMessage() +e.getErrorCode());
		}
		return lista;
	}

	
	public String atributosExistentes()
	{
		ArrayList<atributo> lista = new ArrayList<>();
		String sql = "select nombre from atributos where estatus='A'";
		String strAtributo = "";
		
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			
			while (rs.next()) {
				atributo atributo= new atributo();
				atributo.setNombre(rs.getString("nombre"));
				
				strAtributo = strAtributo+rs.getString("nombre")+",";
				lista.add(atributo);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error: familias existentes" + e.getMessage()+ e.getLocalizedMessage() +e.getErrorCode());
		}
		return strAtributo;
	}

	
}


package modelo.datos;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.beans.plataforma;


public class plataformaDAO {
	
	public String eanPlataforma(String idMaterial)
	{
		String sql = "select p.nombre, mm.ean from plataformas as p inner join materialesMedidas mm on p.idPlataforma=mm.idPlataforma where mm.idMaterial="+idMaterial;
		String aux="";
		
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			
			while (rs.next()) {
				
				aux= aux+(rs.getString(1))+":"+rs.getString(2)+";";
				//plataforma:ean;
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error: eanPlataforma" + e.getMessage());
		}
		return aux;
	}
	
	
	public String destacadosPlataforma(String idMaterial)
	{
		String sql = "select mp.destacado from plataformas as p inner join materialesPlataformas mp on p.idPlataforma=mp.idPlataforma where mp.idMaterial="+idMaterial;
		String aux="";
		
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			
			while (rs.next()) {
				aux= aux+(rs.getString(1))+";";
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error: destacados plataformas" + e.getMessage());
		}
		return aux;
	}
	
	public String materialesPlataforma(String idMaterial) {
		String aux="";
		String sql ="select p.nombre from plataformas as p inner join materialesPlataformas mp on p.idPlataforma=mp.idPlataforma where mp.idMaterial="+idMaterial;
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				aux=aux+rs.getString(1)+";"; //
			}
			
			
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			System.out.println("Error materialesPlataforma" + e.getMessage());
		}
		return aux;
	}
	
	public String plataformasExistentes()
	{
		ArrayList<plataforma> lista = new ArrayList<>();
		String sql = "select nombre from plataformas where estatus='A'";
		String strplataforma = "";
		
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			
			while (rs.next()) {
				plataforma plataforma= new plataforma();
				plataforma.setNombre(rs.getString("nombre"));
				
				strplataforma = strplataforma+rs.getString("nombre")+";";
				lista.add(plataforma);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error: familias existentes" + e.getMessage());
		}
		return strplataforma;
	}

	
	public void plataformaNueva(String nombre, String idUsuarioRegistra, String tipo){
		String sql = "{call plataformaNueva (?,?,?)}";
		System.out.println("call plataformaNueva  "+nombre+","+idUsuarioRegistra+""+"Tipo" +tipo);
		
		try {
				CallableStatement cs = conexion.getInstance().getCN().prepareCall(sql);
				cs.setString(1,nombre);
				cs.setString(2, idUsuarioRegistra);
				cs.setString(3, tipo);
				cs.execute();
				cs.close();
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}
	
	public boolean validarPlataforma(String nombre) {
		boolean aux;
		aux=false;
		String sql = "SELECT * FROM plataformas WHERE nombre=?";
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
	
	
	public List<plataforma> busqueda(String pagina, String busqueda)
	{
		System.out.println("BUSQUEDA GENERAL PLATAFORMA, cambios");
		ArrayList<plataforma> lista = new ArrayList<>();
		String sql = "execute busquedaGeneral 'plataformas','nombre','idPlataforma','"+pagina+"','20','"+busqueda+"',''";
		System.out.println("pagina " + pagina);

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				plataforma plataforma = new plataforma();
				plataforma.setNombre(rs.getString("nombre"));
				plataforma.setEstatus(rs.getString("estatus"));
				plataforma.setIdPlataforma(rs.getString("idPlataforma"));
				
				lista.add(plataforma);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage()+ e.getLocalizedMessage() +e.getErrorCode());
		}
		return lista;
	}
		
	public plataforma consultaIndividual(String idPlataforma){
		String sql="select * from plataformas where idPlataforma=?";
		plataforma p = new plataforma();
		try{
			PreparedStatement ps=conexion.getInstance().getCN().prepareStatement(sql);
			ps.setString(1, idPlataforma);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				p.setNombre(rs.getString("nombre"));
				p.setEstatus(rs.getString("estatus"));
				p.setIdPlataforma("idPlataforma");
				p.setTipo(rs.getString("tipo"));
			
				
			}
			ps.close();
			rs.close();
		}
		catch(SQLException e){
			System.out.println("Error ConsultaIndividual plataforma:"+e.getMessage());
		}
		return p; 
	}
	
	public void plataformaEdicion(String idPlataforma, String estatus, String idUsuarioModifica, String tipo){
		System.out.print("ENTRE A EDICION PLATAFORMA"+ idPlataforma + estatus + idUsuarioModifica);
		String sql = "{call plataformaEdicion (?,?,?,?)}";
		try {
				CallableStatement cs = conexion.getInstance().getCN().prepareCall(sql);
				cs.setString(1,idPlataforma);
				cs.setString(2, estatus);
				cs.setString(3, idUsuarioModifica);
				cs.setString(4, tipo);
				

				cs.execute();
				cs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	}
	

	
}


package modelo.datos;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.beans.atributo;
import modelo.beans.familia;


public class familiaDAO {
	public void cambiarEstatusAtFam(String estatus, String idFamilia, String idAtributo) {
		String sql="UPDATE atributosFamilias SET estatus='"+estatus+"'where idFamilia='"+idFamilia+"' and idAtributo='"+idAtributo+"'";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ps.executeUpdate();
			ps.close();


		} catch (Exception e) {
			System.out.println("Error: cambiar estatus " + e.getMessage());
		}
	}

	
	public String idFamilia(String nombreFamilia) {
		String sql = "Select idFamilia from familias where familia='"+nombreFamilia+"'";
		String strAtributo = "";

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				strAtributo=rs.getString(1);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error:  idFamilia dao" + e.getMessage() + e.getLocalizedMessage()
					+ e.getErrorCode());
		}
		return strAtributo;
	}
	
	
	
	
	public String estatusAtributoFamilia(String nombreFamilia, String atributo) {
		String sql = "Select estatus from atributosFamilias where idFamilia=(select idFamilia from familias where familia='"+nombreFamilia+"') and idAtributo=(select idAtributo from atributos where nombre='"+atributo+"')";
		String strAtributo = "";

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				strAtributo=rs.getString(1);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error:  estatusAtributosFamilia familia dao" + e.getMessage() + e.getLocalizedMessage()
					+ e.getErrorCode());
		}
		return strAtributo;
	}
	
	

	
	
	public void cambiarEstatusFamilia(String estatus, String nombreFamilia, String idUsuario) {
		String sql = "{call familiaCambiarEstatus(?,?,?)}";
		try {
			CallableStatement cs = conexion.getInstance().getCN().prepareCall(sql);
			cs.setString(1, estatus);
			cs.setString(2, nombreFamilia);
			cs.setString(3, idUsuario);

			cs.execute();
			cs.close();

		} catch (Exception e) {
			System.out.println("Error: cambiarEstatusFamilia" + e.getMessage());		
			}

	}
	
	public String atributosFamilia(String idFamilia) {
		ArrayList<atributo> lista = new ArrayList<>();
		String sql = "SELECT a.nombre FROM atributos as a INNER JOIN atributosFamilias as  at ON a.idAtributo =at.idAtributo  where a.estatus='A' and at.estatus='A' and at.idFamilia='"+idFamilia+"'";
		String strAtributo = "";

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				atributo atributo = new atributo();
				atributo.setNombre(rs.getString("nombre"));
				strAtributo = strAtributo + rs.getString("nombre") + ";";
				lista.add(atributo);
			}
			System.out.println(strAtributo);
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error: atributosFamilia familia dao" + e.getMessage() + e.getLocalizedMessage()
					+ e.getErrorCode());
		}
		return strAtributo;
	}
	
	public familia consultaIndividual(String idFamilia){
		String sql="select * from familias where idFamilia=?";
		familia f = new familia();
		try{
			PreparedStatement ps=conexion.getInstance().getCN().prepareStatement(sql);
			ps.setString(1, idFamilia);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				f.setFamilia(rs.getString("familia"));
				f.setEstatus(rs.getString("estatus"));
				f.setIdFamilia("idFamilia");
			
				
			}
			ps.close();
			rs.close();
		}
		catch(SQLException e){
			System.out.println("Error ConsultaIndividual plataforma:"+e.getMessage());
		}
		return f; 
	}
	
	//metodo utilizado para el servlt de productos 

	public String familiasExistentes()
	{
		ArrayList<familia> lista = new ArrayList<>();
		String sql = "select familia from familias";
		String strfamilia = "";
		
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			
			while (rs.next()) {
				familia familia= new familia();
				familia.setFamilia(rs.getString("familia"));
				
				strfamilia = strfamilia+rs.getString("familia")+",";
				lista.add(familia);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error: familias existentes" + e.getMessage()+ e.getLocalizedMessage() +e.getErrorCode());
		}
		return strfamilia;
	}

	public void familiaNueva(String idFamilia, String idUsuarioRegistra ){
		String sql = "{call familiaNueva (?,?)}";
		System.out.println("call atributoNuevo  "+idFamilia+" "+idUsuarioRegistra+"");
		
		try {
				CallableStatement cs = conexion.getInstance().getCN().prepareCall(sql);
				cs.setString(1,idFamilia);
				cs.setString(2, idUsuarioRegistra);
				cs.execute();
				cs.close();
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}
	
	public String validarFamiliaSap(int idFamilia) {
		String aux;
		aux=null;
		String sql = "SELECT descripcionFamilia FROM materialesSap WHERE idFamilia=?";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ps.setInt(1, idFamilia);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				aux=rs.getString(1);// Si existe, si se puede agregar
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
	
	public boolean validarFamiliaInterno(String idFamilia) {
		boolean aux;
		aux=false;
		String sql = "SELECT * FROM familias WHERE idFamilia=?";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ps.setString(1, idFamilia);
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
	
	
	public List<familia> busqueda(String pagina, String busqueda)
	{
		System.out.println("BUSQUEDA GENERAL familia");
		ArrayList<familia> lista = new ArrayList<>();
		String sql = "execute busquedaGeneral 'familias','familia','idFamilia','"+pagina+"','10','"+busqueda+"',''";
		System.out.println("pagina " + pagina);

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				familia familia= new familia();
				familia.setIdFamilia(rs.getString("idFamilia"));
				familia.setFamilia(rs.getString("familia"));
				lista.add(familia);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error: busqueda " + e.getMessage()+ e.getLocalizedMessage() +e.getErrorCode());
		}
		return lista;
	}

	

	
}


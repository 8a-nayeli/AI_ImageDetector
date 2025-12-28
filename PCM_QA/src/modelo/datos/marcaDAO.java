package modelo.datos;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.beans.marca;

public class marcaDAO {
	public String marcasMaterial(String idMaterial)
	{
		String sql = "select descripcionMarca from materiales where idMaterial='"+idMaterial+"'";
		String marca = "";
		
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			
			if (rs.next()) {
				marca= rs.getString(1);
		
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error: marcas material"  +e.getErrorCode());
		}
		return marca;
	}
	
	
	public String marcasExistentesEditar(String idMaterial)
	{
		ArrayList<marca> lista = new ArrayList<>();
		String sql = "select nombre from marcas  where estatus='A' except (select descripcionMarca from materiales where idMaterial='"+idMaterial+"')";
		String strMarcas = "";
		
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			
			while (rs.next()) {
				marca marca= new marca();
				marca.setNombre(rs.getString("nombre"));
				strMarcas = strMarcas+rs.getString("nombre")+",";
				lista.add(marca);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error: marcas existentes editar"  +e.getErrorCode());
		}
		return strMarcas;
	}

	
	
	public String marcasExistentes()
	{
		ArrayList<marca> lista = new ArrayList<>();
		String sql = "select nombre from marcas where estatus='A'";
		String strMarcas = "";
		
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			
			while (rs.next()) {
				marca marca= new marca();
				marca.setNombre(rs.getString("nombre"));
				strMarcas = strMarcas+rs.getString("nombre")+",";
				lista.add(marca);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error: marcas existentes"  +e.getErrorCode());
		}
		return strMarcas;
	}

	
	
	public void marcaNueva(String nombre, String idMarca, String idUsuarioRegistra ){
		String sql = "{call marcaNueva (?,?,?)}";
		System.out.println("call marcaNueva  "+nombre+" "+idUsuarioRegistra+"");
		
		try {
				CallableStatement cs = conexion.getInstance().getCN().prepareCall(sql);
				cs.setString(1,nombre);
				cs.setString(2, idMarca);
				cs.setString(3, idUsuarioRegistra);
				cs.execute();
				cs.close();
				
				
			} catch (Exception e) {
				System.out.println("Error nueva marca " + e.getMessage());
			}
		
	}
	
	public boolean validarMarca(String nombre) {
		boolean aux;
		aux=false;
		String sql = "SELECT * FROM marcas WHERE nombre=?";
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
	
	public boolean validarMarcaEdicion(String nombre) {
		boolean aux;
		aux=false;
		String sql = "SELECT nombre FROM marcas except (select nombre from marcas WHERE nombre=?)";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ps.setString(1, nombre);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				aux=true;
			}// Si existe, no se puede agregar
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
	
	
	public List<marca> busqueda(String pagina, String busqueda)
	{
		System.out.println("BUSQUEDA GENERAL marca");
		ArrayList<marca> lista = new ArrayList<>();
		String sql = "execute busquedaGeneral 'marcas','nombre','idMarca','"+pagina+"','10','"+busqueda+"',''";
		System.out.println("pagina " + pagina);

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				marca marca= new marca();
				marca.setNombre(rs.getString("nombre"));
				marca.setEstatus(rs.getString("estatus"));
				marca.setIdMarca(rs.getString("idMarca"));
				
				lista.add(marca);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage()+ e.getLocalizedMessage() +e.getErrorCode());
		}
		return lista;
	}
		
	public marca consultaIndividual(String idMarca){
		String sql="select * from marcas where idMarca=?";
		marca m = new marca();
		try{
			PreparedStatement ps=conexion.getInstance().getCN().prepareStatement(sql);
			ps.setString(1, idMarca);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				m.setNombre(rs.getString("nombre"));
				m.setEstatus(rs.getString("estatus"));
				m.setIdMarca("idMarca");
			
				
			}
			ps.close();
			rs.close();
		}
		catch(SQLException e){
			System.out.println("Error ConsultaIndividual marca:"+e.getMessage());
		}
		return m; 
	}
	
	public void marcaEdicion(String idMarca,String nombre, String estatus, String idUsuarioModifica){
		System.out.print("ENTRE A EDICION MARCA"+ idMarca +nombre+ estatus + idUsuarioModifica);
		String sql = "{call marcaEdicion (?,?,?,?)}";
		try {
				CallableStatement cs = conexion.getInstance().getCN().prepareCall(sql);
				cs.setString(1,idMarca);
				cs.setString(2,nombre);
				cs.setString(3, estatus);
				cs.setString(4, idUsuarioModifica);
				

				cs.execute();
				cs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	}
	

	
}


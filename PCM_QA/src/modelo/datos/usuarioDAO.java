package modelo.datos;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.beans.usuario;

public class usuarioDAO {

	public String canjeToken(String usuario, String token ){
		String sql = "{call canjeToken (?,?)}";
		String aux  ="";
		
		try {
				CallableStatement cs = conexion.getInstance().getCN().prepareCall(sql);
				cs.setString(1,usuario);
				cs.setString(2,token);
				ResultSet rs = cs.executeQuery();

				if (rs.next()) {
					aux=rs.getString(1);
					System.out.println("SI HAY "+aux);

				}
		
				cs.execute();
				cs.close();
				
				
			} catch (Exception e) {
				System.out.println("Error registroToken " + e.getMessage());
				aux="NO";
			}
		return aux;
	}
	
	public String registroToken(String usuario, String token ){
		String sql = "{call olvidePass (?,?)}";
		String aux  ="si";
		
		try {
				CallableStatement cs = conexion.getInstance().getCN().prepareCall(sql);
				cs.setString(1,usuario);
				cs.setString(2,token);
				cs.execute();
				cs.close();
				
				
			} catch (Exception e) {
				System.out.println("Error registroToken " + e.getMessage());
				aux="no";
			}
		return aux;
	}
	public String validarUsuario(String usuario) {
		
		String correo="";
		String sql = "SELECT correo FROM usuarios WHERE usuario='"+usuario+"'"; //Los que si deben tener el correo
			try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				correo=rs.getString(1);
			}
			else
			{
				correo="";
			}
			
			
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			System.out.println("Error el validarUsuario" + e.getMessage());

		}
		return correo;
	}
	//Chechar los usuarios que deben recibir los correos de edicion de imagenes 
	public String alertas() {
		String alerta="";
		String sql = "SELECT correo FROM usuarios WHERE alertas=1"; //Los que si deben tener el correo
			try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				alerta=alerta+","+ rs.getString(1);
			}
			
			
			System.out.println(alerta);
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			System.out.println("Error el alertas" + e.getMessage());

		}
		return alerta;
	}
		
		
	
	
	//Validar si el correo ya cuenta con un nombre de usuario
		public boolean validarCorreo(String correo) {
		boolean salida=false;
		String sql = "SELECT correo FROM usuarios WHERE correo=?";
			try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ps.setString(1, correo);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				salida = false; // si encontro un usuario, por lo tanto ya no se puede crear otro nuevo 
			}else
			{
				salida = true; //no encontro un usuario, se puede crear uno nuevo
			}
			System.out.println(salida);
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			System.out.println("Error al conectar con la BD al validar " + e.getMessage());
			salida= false;
		}
		return salida;
	}
		
		

	public void usuarioNuevo(String usuario, String pass, String correo, String idPermiso, String idUsuarioRegistra ){
		String sql = "{call usuarioNuevo (?,?,?,?,?)}";
		System.out.println("call usuarioNuevo "+usuario+","+pass+","+correo+","+idPermiso+","+idUsuarioRegistra+"");
		
		try {
				CallableStatement cs = conexion.getInstance().getCN().prepareCall(sql);
				cs.setString(1,usuario);
				cs.setString(2,pass);
				cs.setString(3, correo);
				cs.setString(4, idPermiso);
				cs.setString(5, idUsuarioRegistra);
				cs.execute();
				cs.close();
				
				
			} catch (Exception e) {
				System.out.println("Error new user " + e.getMessage());
			}
		
	}
	
	
	
	public void usuarioCambioPass(String usuario, String pass){
		String sql = "{call usuarioCambioPass(?,?)}";
		try {
				CallableStatement cs = conexion.getInstance().getCN().prepareCall(sql);
				cs.setString(1,usuario);
				cs.setString(2,pass);
				cs.execute();
				cs.close();
								
			} catch (Exception e) {
				System.out.println("Error cambio pass " + e.getMessage());
			}
			//return null;
	}
	
	

	
	public usuario validarSesion(String usuario, String pass) {
		
		String sql = "SELECT * FROM usuarios WHERE usuario=? and pass=?";
		usuario user = new usuario();
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ps.setString(1, usuario);
			ps.setString(2, pass);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				user.setUsuario(rs.getString("usuario"));
				user.setIdUsuario(rs.getString("idUsuario"));
				user.setPass(rs.getString("pass"));
				user.setIdRol(rs.getString("idRol"));
				user.setCorreo(rs.getString("correo"));
				user.setEstatus(rs.getString("estatus"));
				return user;
			}
			
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			System.out.println("Error al conectar con la BD al validar " + e.getMessage());
		}
		return null;
	}

	
public usuario validar_usuario_cambio_pass(String usuario, String pass) {
		
		String sql = "SELECT * FROM usuarios WHERE usuario=? and pass=?";
		usuario user = new usuario();
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ps.setString(1, usuario);
			ps.setString(2, pass);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				user.setUsuario(rs.getString("usuario"));
				user.setCorreo(rs.getString("correo"));
				user.setPass(rs.getString("pass"));
				user.setIdRol(rs.getString("idRol"));
				return user;
			}
			
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			System.out.println("Error al conectar con la BD al validar " + e.getMessage());
		}
		return null;
	}
	
	

public List<usuario> busqueda(String pagina, String busqueda)
{
	System.out.println("BUSQUEDA GENERAL USUARIO");
	ArrayList<usuario> lista = new ArrayList<>();
	String sql = "execute busquedaGeneral 'usuarios','usuario','idUsuario','"+pagina+"','10','"+busqueda+"',''";
	System.out.println("pagina " + pagina);

	try {
		PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			usuario user = new usuario();
			user.setUsuario(rs.getString("usuario"));
			user.setCorreo(rs.getString("correo"));
			user.setIdRol(rs.getString("idRol"));
			user.setEstatus(rs.getString("estatus"));
			user.setIdUsuario(rs.getString("idUsuario"));
			user.setPass(rs.getString(4));
			lista.add(user);
		}
		ps.close();
		rs.close();
	} catch (SQLException e) {
		System.out.println("Error: busqueda " + e.getMessage()+ e.getLocalizedMessage() +e.getErrorCode());
	}
	return lista;
}
	
	public void usuarioEdicion(String rol, String estatus, String idUsuario, String idUsuarioModifica,String alerta){
		System.out.print("ENTRE A EDICION USUARIO"+ rol + estatus+ idUsuario + idUsuarioModifica);
		String sql = "{call usuarioEdicion (?,?,?,?,?)}";
		try {
				CallableStatement cs = conexion.getInstance().getCN().prepareCall(sql);
				cs.setString(1,rol);
				cs.setString(2,estatus);
				cs.setString(3,idUsuario);
				cs.setString(4,idUsuarioModifica);				
				cs.setString(5,alerta);

				cs.execute();
				cs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	}
	
	
	
	
	public usuario consultaIndividual(String idUsuario){
		String sql="select * from usuarios where idUsuario=?";
		usuario u = new usuario();
		try{
			PreparedStatement ps=conexion.getInstance().getCN().prepareStatement(sql);
			ps.setString(1, idUsuario);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				u.setUsuario(rs.getString("usuario"));
				u.setCorreo(rs.getString("correo"));
				u.setEstatus(rs.getString("estatus"));
				u.setIdRol(rs.getString("idRol"));
				u.setIdUsuario(rs.getString("idUsuario"));
				u.setAlerta(rs.getString("alertas"));
			}
			ps.close();
			rs.close();
		}
		catch(SQLException e){
			System.out.println("Error ConsultaIndividual DAO:"+e.getMessage());
		}
		return u; 
	}

	
	
//Validar si los datos son correctos olvide pass
public boolean olvidePass(String correo, String usuario) {
boolean salida=false;
String sql = "select usuario from usuarios where correo=? and usuario=?";
	try {
	PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
	ps.setString(1, correo);
	ps.setString(2, usuario);
	ResultSet rs = ps.executeQuery();
	
	if (rs.next()) {
		salida = true; // si encontro un usuario, por lo tanto si existe
	}else
	{
		salida = false; //no encontro un usuario
	}
	System.out.println(salida);
	rs.close();
	ps.close();
	
} catch (SQLException e) {
	System.out.println("Error en olvide pass " + e.getMessage());
	salida= false;
}
return salida;
}


}


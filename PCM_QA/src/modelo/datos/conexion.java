package modelo.datos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class conexion {
		//datos para conexion con la bd de SQLSERVER
			public static String user = "UsrPos";
			public static String pass = "P0s&Usr";
			
			private Connection cn=null;
			static conexion conexion=null;
			
			public int setup()
			{
				try {
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
  					System.out.println(user);
  					System.out.println(pass);
  					//;encrypt=false
  					this.cn = DriverManager.getConnection("jdbc:sqlserver://10.1.0.11;databaseName=ADU_PCM_Calidad;user="+user+";password="+pass+";useUnicode=true;characterEncoding=UTF-8");
					return 1;
				} catch (SQLException e) {
					System.out.println("CONEXION: Error al conectar con la BD");

					System.out.println(e.getMessage());
				} catch (ClassNotFoundException e) {
					System.out.println("CONEXION: Excepcion de Clase no encontrada");
					e.printStackTrace();
				}
				return -1;
			}
			
			public static conexion getInstance()
			{
				if(conexion == null)
				{
					conexion = new conexion();
				}
				return conexion;
			}
			
			public Connection getCN()
			{
				return this.cn;
			}
			
			public void cerrar()
			{
				try
				{
					cn.close();
				}catch(SQLException e)
				{
					System.out.println("CONEXION: Error al cerrar la conexion con la BD");
					System.out.println(e.getMessage());
				}
			}
	}


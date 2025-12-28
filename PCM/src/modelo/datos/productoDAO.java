package modelo.datos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.beans.atributo;
import modelo.beans.plataforma;
import modelo.beans.producto;
import modelo.beans.reporte;

public class productoDAO {	
	public List<producto> rutaImagen() {
	ArrayList<producto> lista = new ArrayList<>();

	String sql = "Select idmaterial, 'http://pcm.merza.com:8080'+ruta as ruta from materialesImagenes order by idmaterial";

	try {
		PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			producto producto = new producto();
			producto.setImagen(rs.getString(2));
			producto.setIdMaterial(rs.getString(1));

			lista.add(producto);
		}
		ps.close();
		rs.close();
	} catch (SQLException e) {
		System.out.println("Error en rutaImagen: " + e.getMessage() + e.getLocalizedMessage() + e.getErrorCode());
	}
	return lista;
}

	// contabiliza los materiales por plataforma
//Merzava,duerova,b2b,b2c,imagenes,cava,wabi
	public String CantMaterialesPlataformas() {
		String aux = "";
		String sql = "{call CantMaterialesPlataformas}";
		try {
			CallableStatement cs = conexion.getInstance().getCN().prepareCall(sql);
			ResultSet rs = cs.executeQuery();

			if (rs.next()) {
				aux = rs.getString(1);
			}
			cs.execute();
			cs.close();
		} catch (Exception e) {
			System.out.println("Error: CantMaterialesPlataformas " + e.getMessage());
		}
		return aux;
	}

	public String plataformasMateriales(String idMaterial) { // saber si un material esta asignado a una plataforma
		String sql = "SELECT COUNT(IDMATERIAL) FROM MATERIALESPLATAFORMAS WHERE IDPLATAFORMA='1'";
		String aux = "";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				aux = rs.getString(1);
			}
			ps.close();
			rs.close();

		} catch (SQLException e) {
			System.out.println("Error allDesMat:" + e.getMessage());
		}
		return aux;
	}

	public List<reporte> ReporteAtributosAll() {
		String sql = "exec reporteAtributosAll";
		ArrayList<reporte> lista = new ArrayList<>();

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				reporte reporte = new reporte();
				reporte.setIdMaterial(rs.getString("idMaterial"));
				reporte.setDescripcionComercial(rs.getString("descripcionComercial"));
				reporte.setAcabado(rs.getString("Acabado"));
				reporte.setAtributo_Generico_1(rs.getString(4));
				reporte.setAtributo_Generico_2(rs.getString(5));
				reporte.setAtributo_Generico_3(rs.getString(6));
				reporte.setAtributo_Generico_4(rs.getString(7));
				reporte.setAtributo_Generico_5(rs.getString(8));
				reporte.setAtributo_Generico_6(rs.getString(9));
				reporte.setBoca(rs.getString("Boca"));
				reporte.setContenido(rs.getString("Contenido"));
				reporte.setCrianza(rs.getString("Crianza"));
				reporte.setInformacion_Nutricional(rs.getString(13));
				reporte.setIngredientes(rs.getString("Ingredientes"));
				reporte.setMaridaje(rs.getString("Maridaje"));
				reporte.setNariz(rs.getString("Nariz"));
				reporte.setPais_de_Origen_Region(rs.getString(16));
				reporte.setPorcentaje_de_alcohol(rs.getString("Porcentaje de alcohol"));
				reporte.setTexto(rs.getString("Texto"));
				reporte.setTipo_de_uva(rs.getString("Tipo de uva"));
				reporte.setVista(rs.getString("Vista"));
				reporte.setVolumen(rs.getString("Volumen"));
				lista.add(reporte);

			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error reporte atributos general: " + e.getMessage());
		}
		return lista;
	}

	public String[][] reporteAtributos(String fecha1, String fecha2, String familia) {
		ArrayList<producto> lista = new ArrayList<>();
		String[][] datos = null;

		String sql = "{call reporteAtributos(?,?,?)}";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ps.setString(1, fecha1);
			ps.setString(2, fecha2);
			ps.setString(3, familia);

			ResultSet rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			String[] nombresColumnas = new String[columnCount];

			// The column count starts from 1
			System.out.println(columnCount);

			for (int i = 0; i < columnCount; i++) {
				String name = rsmd.getColumnName(i + 1);
				// System.out.println(name);
				nombresColumnas[i] = name;
				// Do stuff with name
			}

			int filas = 0;
			while (rs.next()) {
				filas++;
			}
			ps.close();
			rs.close();

			// volvemos a llamar todo de nuevo
			sql = "{call reporteAtributos(?,?,?)}";
			ps = conexion.getInstance().getCN().prepareStatement(sql);
			ps.setString(1, fecha1);
			ps.setString(2, fecha2);
			ps.setString(3, familia);
			rs = ps.executeQuery();

			datos = new String[filas + 1][columnCount];

			// ponemos el nombre de las columnas
			for (int i = 0; i < columnCount; i++) {
				datos[0][i] = nombresColumnas[i];
			}

			int f = 1;
			while (rs.next()) { // filas
				for (int i = 0; i < columnCount; i++) { // columnas
					String dato = rs.getString(nombresColumnas[i]);

					// llenamos con la fila f y las columnas i
					datos[f][i] = dato;
				}
				f++;
			}

			ps.close();
			rs.close();

		} catch (SQLException e) {
			System.out.println(
					"Error materiales Atributos: " + e.getMessage() + e.getLocalizedMessage() + e.getErrorCode());
		}
		return datos;
	}

	public void activarMaterial(String idMaterial, String idUsuario) {
		System.out.println("entre a material eliminar" + idMaterial + idUsuario);

		String sql = "{call activarMaterial(?,?)}";
		try {
			CallableStatement cs = conexion.getInstance().getCN().prepareCall(sql);
			cs.setString(1, idMaterial);
			cs.setString(2, idUsuario);
			cs.execute();
			cs.close();
		} catch (Exception e) {
			System.out.println("Error: activarMaterial " + e.getMessage());
		}

	}

	public void materialEliminar(String idMaterial, String idUsuario) {
		System.out.println("entre a material eliminar" + idMaterial + idUsuario);

		String sql = "{call materialEliminar(?,?)}";
		try {
			CallableStatement cs = conexion.getInstance().getCN().prepareCall(sql);
			cs.setString(1, idMaterial);
			cs.setString(2, idUsuario);
			cs.execute();
			cs.close();
		} catch (Exception e) {
			System.out.println("Error: materialEliminar " + e.getMessage());
		}

	}

	public int existMat(String idMaterial) { // saber si un material esta asignado a una plataforma
		String sql = "select * from materiales where idMaterial=" + idMaterial;
		int aux = 0;
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				// Si lo econtro
				aux = 1;
			}
			ps.close();
			rs.close();

		} catch (SQLException e) {
			System.out.println("Error allDesMat:" + e.getMessage());
		}
		return aux;
	}

	public int existMatPlat(String idMaterial) { // saber si un material esta asignado a una plataforma
		String sql = "select * from materialesPlataformas where idMaterial=" + idMaterial;
		int aux = 0;
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				// Si encontro, no puedo eliminar
				aux = 1;
			}
			ps.close();
			rs.close();

		} catch (SQLException e) {
			System.out.println("Error allDesMat:" + e.getMessage());
		}
		return aux;
	}

	public String existMatImg(String idMaterial) { // saber si un material esta asignado a una plataforma
		String sql = "select idImagen from materialesImagenes where idMaterial=" + idMaterial;
		String aux = "0";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				// Si encontro, no puedo eliminar
				aux = aux + rs.getString(1) + ",";
			}
			ps.close();
			rs.close();

		} catch (SQLException e) {
			System.out.println("Error allDesMat:" + e.getMessage());
		}
		return aux;
	}

	public String allCategoria() {
		String sql = "select distinct descripcionCategoria from materiales";
		String aux = "";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				aux = aux + rs.getString(1) + ",";

			}
			ps.close();
			rs.close();

		} catch (SQLException e) {
			System.out.println("Error allDesMat:" + e.getMessage());
		}
		return aux;
	}

	public String allProveedor() {
		String sql = "select distinct descripcionProveedor from materiales";
		String aux = "";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				aux = aux + rs.getString(1) + ",";

			}
			ps.close();
			rs.close();

		} catch (SQLException e) {
			System.out.println("Error allDesMat:" + e.getMessage());
		}
		return aux;
	}

	public String allDesMat() {
		String sql = "select distinct descripcionMaterial from materiales";
		String aux = "";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				aux = aux + rs.getString(1) + ",";

			}
			ps.close();
			rs.close();

		} catch (SQLException e) {
			System.out.println("Error allDesMat:" + e.getMessage());
		}
		return aux;
	}

	public String allIdMat() {
		String sql = "select distinct idMaterial from materiales";
		String aux = "";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				aux = aux + rs.getString(1) + ",";

			}
			ps.close();
			rs.close();

		} catch (SQLException e) {
			System.out.println("Error allIdMat:" + e.getMessage());
		}
		return aux;
	}

	public String categoriasExistentes() // Listado de categorias
	{
		ArrayList<producto> lista = new ArrayList<>();
		String sql = "select distinct descripcionCategoria from materiales ";
		String strCategoria = "";

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				producto producto = new producto();
				producto.setDescripcionCategoria(rs.getString(1));
				strCategoria = strCategoria + rs.getString(1) + ";";
				lista.add(producto);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error:categoriasExistentes" + e.getMessage());
		}
		return strCategoria;
	}

	public List<producto> materialesSinDesc() {// Sin descripcion comercial
		ArrayList<producto> lista = new ArrayList<>();

		String sql = "Select distinct idMaterial, descripcionMaterial, descripcionProveedor,descripcionMarca, descripcionGpoArt, descripcionFamilia from materiales where descripcionComercial like ''";

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				producto producto = new producto();

				producto.setIdMaterial(rs.getString("idMaterial"));
				producto.setDescripcionMaterial(rs.getString("descripcionMaterial"));
				producto.setDescripcionProveedor(rs.getString("descripcionProveedor"));
				producto.setDescripcionMarca(rs.getString("descripcionMarca"));
				producto.setDescripcionGpoArt(rs.getString("descripcionGpoArt"));
				producto.setDescripcionFamilia(rs.getString("descripcionFamilia"));

				lista.add(producto);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out
					.println("Error materialesSinDesc: " + e.getMessage() + e.getLocalizedMessage() + e.getErrorCode());
		}
		return lista;
	}

	public List<producto> materialesSinGramaje() {
		ArrayList<producto> lista = new ArrayList<>();

		String sql = "Select distinct idMaterial, descripcionMaterial, descripcionComercial, descripcionProveedor,descripcionMarca, descripcionGpoArt, descripcionFamilia from materiales where gramaje like ''";

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				producto producto = new producto();

				producto.setIdMaterial(rs.getString("idMaterial"));
				producto.setDescripcionMaterial(rs.getString("descripcionMaterial"));
				producto.setDescripcionComercial(rs.getString("descripcionComercial"));
				producto.setDescripcionProveedor(rs.getString("descripcionProveedor"));
				producto.setDescripcionMarca(rs.getString("descripcionMarca"));
				producto.setDescripcionFamilia(rs.getString("descripcionFamilia"));
				producto.setDescripcionGpoArt(rs.getString("descripcionGpoArt"));
				lista.add(producto);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println(
					"Error materialesSinGramaje: " + e.getMessage() + e.getLocalizedMessage() + e.getErrorCode());
		}
		return lista;
	}

	public List<producto> materialesSinAtributos() {
		ArrayList<producto> lista = new ArrayList<>();

		String sql = "SELECT distinct m.idMaterial, m.descripcionMaterial, m.descripcionComercial, m.gramaje,(select top 1 infoAtributos from materialesHistorial where idMaterial=m.idMaterial order by fechaModifica desc ) as infoAtributos, m.descripcionProveedor,m.descripcionMarca, m.descripcionGpoArt, m.descripcionFamilia FROM materiales as m left JOIN materialesHistorial as mh ON m.idMaterial=mh.idMaterial where  m.gramaje='' or m.descripcionComercial='' or m.idMaterial not in((select distinct m.idMaterial from materialesAtributos))";

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				producto producto = new producto();
				producto.setIdMaterial(rs.getString("idMaterial"));
				producto.setDescripcionMaterial(rs.getString("descripcionMaterial"));
				producto.setDescripcionComercial(rs.getString("descripcionComercial"));
				producto.setGramaje(rs.getString("gramaje"));
				producto.setInfoAtributos(rs.getString("infoAtributos"));
				producto.setDescripcionProveedor(rs.getString("descripcionProveedor"));
				producto.setDescripcionMarca(rs.getString("descripcionMarca"));
				producto.setDescripcionGpoArt(rs.getString("descripcionGpoArt"));
				producto.setDescripcionFamilia(rs.getString("descripcionFamilia"));
				lista.add(producto);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println(
					"Error materialesSinAtributos: " + e.getMessage() + e.getLocalizedMessage() + e.getErrorCode());
		}
		return lista;
	}

	public String materialSinImg(String comparativa) {
		String sql = "select distinct idMaterial from materialesImagenes where idMaterial in(" + comparativa + ")";
		String aux = "";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				aux = rs.getString(1) + "," + aux; // El resultado seria todas las img que si tienen una img, 1,10,9
			}
			ps.close();
			rs.close();

		} catch (SQLException e) {
			System.out.println("Error consultaImg:" + e.getMessage());
		}
		return aux;
	}

	public void editarImg(String nomImg, String user) { // Modifica el usuario y fecha en que se registro la img
		// System.out.println("Entre a modificar imagen");
		String sql = "execute modificarImg '" + nomImg + "'," + "'" + user + "'";

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Error en editarImg" + e.getMessage());
		}

	}

	public String consultaImg(String nombreImagen) {
		// System.out.println("nombre de imagen" + nombreImagen);
		String sql = "select ruta from materialesImagenes where nombre='" + nombreImagen + "'";
		String ruta = "";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ruta = rs.getString(1);

			}
			ps.close();
			rs.close();

		} catch (SQLException e) {
			System.out.println("Error consultaImg:" + e.getMessage());
		}
		return ruta;
	}

	public String descripcionComercial(String idMaterial) {
		String sql = "select descripcionComercial from materiales where idMaterial=" + idMaterial;
		String descripcion = "Sin descripción comercial";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				descripcion = rs.getString(1);

			}
			ps.close();
			rs.close();

		} catch (SQLException e) {
			System.out.println("Error descripcionComercial DAO:" + e.getMessage());
		}
		return descripcion;
	}

	public String consultaTodasRutas() {
		String sql = "select rutasImagenes from materiales where rutasImagenes not like ''";
		String ruta = "";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ruta = ruta + rs.getString(1) + "|";

			}
			ps.close();
			rs.close();

		} catch (SQLException e) {
			System.out.println("Error consultaTodasRutas DAO:" + e.getMessage());
		}
		return ruta;
	}

	public String consultaRutas(String idMaterial) {
		String sql = "select rutasImagenes from materiales where idMaterial=" + idMaterial;
		String ruta = "";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ruta = rs.getString(1);

			}
			ps.close();
			rs.close();

		} catch (SQLException e) {
			System.out.println("Error consultaRutas DAO:" + e.getMessage());
		}
		return ruta;
	}

	public String imagenMaterial(String idMaterial) {
		String sql = "select * from materialesImagenes where idMaterial=" + idMaterial;
		String ruta = "";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ruta = rs.getString(1);

			}
			ps.close();
			rs.close();

		} catch (SQLException e) {
			System.out.println("Error consultaRutas DAO:" + e.getMessage());
		}
		return ruta;
	}

	public List<producto> listadoImg(String pagina) { // Listado de los materiales que al
														// menos tienen una imagen
		ArrayList<producto> lista = new ArrayList<>();
		String sql = "SELECT  distinct (mi.idMaterial), m.descripcionMaterial FROM materialesImagenes  mi INNER JOIN materiales m ON  m.idMaterial=mi.idMaterial ORDER BY mi.idMaterial DESC OFFSET ("
				+ pagina + "+-1)* 20 ROWS FETCH NEXT 20 ROWS ONLY";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				producto p = new producto();
				p.setIdMaterial(rs.getNString("idMaterial"));
				p.setDescripcionMaterial(rs.getNString("descripcionMaterial"));
				lista.add(p);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error: listadoImg" + e.getMessage());
		}
		return lista;
	}

	public String cantMat() {// extre la cantidad de materiales registrados
		String sql = "select count (distinct idMaterial) as col from materiales";
		String salida = "";

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				salida = rs.getString(1);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error: cantMat" + e.getMessage() + e.getLocalizedMessage() + e.getErrorCode());
		}
		return salida;
	}

	public String cantImgNo() {// extre la cantidad de materiales sin imagen
		String sql = "Select count (distinct idMaterial) as col from materiales where idMaterial not in (select idMaterial from materialesImagenes)";
		String salida = "";

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				salida = rs.getString(1);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error: cantImgNo" + e.getMessage() + e.getLocalizedMessage() + e.getErrorCode());
		}
		return salida;
	}

	public String cantImg() {// extre la cantidad de materiales CON imagen
		String sql = "select count (distinct idMaterial) as mat_img from materialesImagenes";
		String salida = "";

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				salida = rs.getString(1);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error: cantImgNo" + e.getMessage() + e.getLocalizedMessage() + e.getErrorCode());
		}
		return salida;
	}

	public String descripcion(String idMaterial) {
		String sql = "select top 1 descripcionMaterial from materiales where idMaterial='" + idMaterial + "'";

		String salida = "";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				salida = rs.getString(1);
			}

			ps.close();
			rs.close();
			return salida;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return salida;
	}

	public String marcaMaterial(String idMaterial) {
		String aux = "";

		String sql = "select nombre from marcas where idMarca=(select idMarca from materiales where idMaterial='"
				+ idMaterial + "'";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				aux = rs.getString(1);
			}

			rs.close();
			ps.close();

		} catch (SQLException e) {
			System.out.println("Error puedeRegistrar" + e.getMessage());
		}
		return aux;
	}

	public void EliminarMedida(String idMaterial, String nombrePlataforma, String ean) {
		String sql = "delete materialesMedidas where "
				+ "idPlataforma=(select idPlataforma from plataformas where nombre='" + nombrePlataforma + "')"
				+ " and idMaterial='" + idMaterial + "' and ean='" + ean + "'";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareCall(sql);
			ps.execute();

			ps.close();

		} catch (Exception e) {
			System.out.println("Error eliminar medida: " + e.getMessage());
		}

	}

	public void medidaEditar(String idMaterial, String nombrePlataforma, String ean, String estatus, String idUsuario) {
		String sql = "{call medidaEditar(?,?,?,?,?)}";
		try {
			CallableStatement cs = conexion.getInstance().getCN().prepareCall(sql);
			cs.setString(1, idMaterial);
			cs.setString(2, nombrePlataforma);
			cs.setString(3, ean);
			cs.setString(4, estatus);
			cs.setString(5, idUsuario);

			cs.close();

		} catch (Exception e) {
			System.out.println("Error: mediada editar" + e.getMessage());
		}

	}

	// Metodo para checar que si el material ya tiene registrado un Ean
	public String puedeRegistrar(String idMaterial, String plataforma, String ean) {
		System.out.println(idMaterial + plataforma + ean);
		String aux = "si";
		String sql = "Select estatus from materialesMedidas where idMaterial='" + idMaterial
				+ "' and idPlataforma=(select idPlataforma from plataformas where nombre='" + plataforma
				+ "') and ean='" + ean + "'";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				aux = rs.getString(1);
			}

			rs.close();
			ps.close();

		} catch (SQLException e) {
			System.out.println("Error puedeRegistrar" + e.getMessage());
		}
		return aux;
	}

	public String materialesPlataforma(String idMaterial) {
		String aux = "";
		String sql = "select p.nombre from plataformas as p inner join materialesPlataformas mp on p.idPlataforma=mp.idPlataforma where mp.idMaterial="
				+ idMaterial;
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				aux = aux + rs.getString(1) + ";"; //
			}

			rs.close();
			ps.close();

		} catch (SQLException e) {
			System.out.println("Error materialesPlataforma" + e.getMessage());
		}
		return aux;
	}

	public String checarGramaje(String idMaterial) {
		String gramaje = "";
		String sql1 = "select gramaje from materiales where idMaterial = '" + idMaterial + "'";
		try {
			PreparedStatement ps1 = conexion.getInstance().getCN().prepareStatement(sql1);
			ResultSet rs1 = ps1.executeQuery();

			if (rs1.next()) {
				gramaje = rs1.getString(1);
			} else {
			}
			rs1.close();
			ps1.close();

		} catch (SQLException e) {
			System.out.println("Error checar gramaje" + e.getMessage());
		}
		return gramaje;
	}

	public String checarDescripcionComercial(String idMaterial) {
		String descripcionComercial = "";
		String sql1 = "select descripcionComercial from materiales where idMaterial = '" + idMaterial + "'";
		try {
			PreparedStatement ps1 = conexion.getInstance().getCN().prepareStatement(sql1);
			ResultSet rs1 = ps1.executeQuery();

			if (rs1.next()) {
				descripcionComercial = rs1.getString(1);
			} else {
			}
			rs1.close();
			ps1.close();

		} catch (SQLException e) {
			System.out.println("Error checar gramaje" + e.getMessage());
		}
		return descripcionComercial;
	}

	public void materialDestacado(String idMaterial, String nombrePlataforma, char valor) {
		String sql = "update materialesPlataformas set destacado=" + valor + " where idMaterial=" + idMaterial
				+ "and idPlataforma=(select idPlataforma from plataformas where nombre='" + nombrePlataforma + "')";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			System.out.println("Error: material destacado" + e.getMessage());
		}
	}

	public void medidaNueva(String idMaterial, String nombrePlataforma, String ean, String um, String conversionUm,
			String idUsuario) {
		String sql = "{call medidaNueva(?,?,?,?,?,?)}";
		// System.out.println("idMaterial: " + idMaterial + " Plataforma: " +
		// nombrePlataforma + " EAN:" + ean + " Um:"
		// + um + " ConUM:" + conversionUm + " user" + idUsuario);
		try {
			CallableStatement cs = conexion.getInstance().getCN().prepareCall(sql);
			cs.setString(1, idMaterial);
			cs.setString(2, nombrePlataforma);
			cs.setString(3, ean);
			cs.setString(4, um);
			cs.setString(5, conversionUm);
			cs.setString(6, idUsuario);

			cs.execute();
			cs.close();

		} catch (Exception e) {
			System.out.println("Error: medida nueva" + e.getMessage());
		}

	}

	public producto medidas(int valor, String idMaterial) {
		// System.out.println("entre a medidas");
		producto p = new producto();
		String sql = "select ean,um,conversionUm from materialesSap where idMaterial=" + idMaterial;

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			int count = 0;

			while (rs.next()) {
				++count;
				if (count == valor) {
					p.setEan(rs.getString("ean"));
					p.setUm(rs.getString("um"));
					p.setConversionUm(rs.getString("conversionUm"));

				}
			}

			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error: medidas" + e.getMessage());
		}
		return p;
	}

//	public String medidas(int valor, String idMaterial) {
//		String medidas = "";
//		String sql = "select ean from materialesSap where idMaterial="+idMaterial;
//		try {
//			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
//			ResultSet rs = ps.executeQuery();
//			
//			if(rs.next()) {
//				rs.absolute(valor);
//				medidas=rs.getString(0);
//				String sql1 = "select um from materialesSap where idMaterial="+idMaterial;
//				try {
//					PreparedStatement ps1 = conexion.getInstance().getCN().prepareStatement(sql1);
//					ResultSet rs1 = ps1.executeQuery();
//
//					if (rs1.next()) {
//						rs1.absolute(valor);
//						medidas=medidas+","+rs1.getString(0);
//						String sql2 = "select conversionUm from materialesSap where idMaterial="+idMaterial;
//						try {
//							PreparedStatement ps2 = conexion.getInstance().getCN().prepareStatement(sql2);
//							ResultSet rs2 = ps2.executeQuery();
//
//							if (rs2.next()) {
//								rs.absolute(valor);
//								medidas=medidas+","+rs2.getString(0);
//									//
//								String sql3 = "select gramaje from materialesSap where idMaterial="+idMaterial;
//								try {
//									PreparedStatement ps3 = conexion.getInstance().getCN().prepareStatement(sql3);
//									ResultSet rs3 = ps3.executeQuery();
//
//									if (rs3.next()) {
//										medidas=medidas+","+rs3.getString(0);
//
//									} else {
//									}
//									rs3.close();
//									ps3.close();
//
//								} catch (SQLException e) {
//									System.out.println("Error medidas 3" + e.getMessage());
//								}
//
//							} else {
//							}
//							rs2.close();
//							ps2.close();
//
//						} catch (SQLException e) {
//							System.out.println("Error medidas 2" + e.getMessage());
//						}
//					} else {
//					}
//					rs1.close();
//					ps1.close();
//
//				} catch (SQLException e) {
//					System.out.println("Error medidas 1 " + e.getMessage());
//				}
//
//			} else {
//			}
//			rs.close();
//			ps.close();
//
//		} catch (SQLException e) {
//			System.out.println("Error medidas 0 " + e.getMessage());
//		}
//		return medidas;
//	}

	public String conocerFamilia(String idMaterial) {
		String familia = "";
		String sql1 = "select idFamilia from materialesSap where idMaterial='" + idMaterial + "'";
		try {
			PreparedStatement ps1 = conexion.getInstance().getCN().prepareStatement(sql1);
			ResultSet rs1 = ps1.executeQuery();

			if (rs1.next()) {
				familia = rs1.getString(1);
			} else {
			}
			rs1.close();
			ps1.close();

		} catch (SQLException e) {
			System.out.println("Error conocerFamilia " + e.getMessage());
		}
		return familia;
	}

	public String conocerSustituto(String idMaterial) {
		String familia = "";
		String sql1 = "select idMaterialSustituto from materialesSap where idMaterial=" + idMaterial;
		try {
			PreparedStatement ps1 = conexion.getInstance().getCN().prepareStatement(sql1);
			ResultSet rs1 = ps1.executeQuery();

			if (rs1.next()) {
				familia = rs1.getString(1);
			} else {
			}
			rs1.close();
			ps1.close();

		} catch (SQLException e) {
			System.out.println("Error conocerFamilia " + e.getMessage());
		}
		return familia;
	}

	public String materialesEan(String idMaterial) {
		String sql = "select ean from materialesSap where idMaterial='" + idMaterial + "'";
		String salida = "";

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				salida = salida + rs.getString(1) + ",";
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error: materiales Ean" + e.getMessage() + e.getLocalizedMessage() + e.getErrorCode());
		}
		if (salida == "") {
			salida = "Sin información.";
		}
		return salida;
	}

	public String materialesUm(String idMaterial) {
		String sql = "select um from materialesSap where idMaterial='" + idMaterial + "'";
		String salida = "";

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				salida = salida + rs.getString(1) + ",";
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error: materiales um" + e.getMessage() + e.getLocalizedMessage() + e.getErrorCode());
		}
		if (salida == "") {
			salida = "Sin información.";
		}
		return salida;
	}

	public String materialesConversionUm(String idMaterial) {
		String sql = "select conversionUm from materialesSap where idMaterial='" + idMaterial + "'";
		String salida = "";

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				salida = salida + rs.getString(1) + ",";
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println(
					"Error: materiales ConversionUm" + e.getMessage() + e.getLocalizedMessage() + e.getErrorCode());
		}
		if (salida == "") {
			salida = "Sin información.";
		}
		return salida;
	}

	public String materialesGramaje(String idMaterial) {
		String sql = "select gramaje from materialesSap where idMaterial='" + idMaterial + "'";
		String salida = "";

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				salida = salida + rs.getString(1) + ",";
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out
					.println("Error: materiales Gramaje" + e.getMessage() + e.getLocalizedMessage() + e.getErrorCode());
		}
		return salida;
	}

	public void updateMatAtributo(String idMaterial, String nombreAtributo, String detalle) {
		String sql = "SELECT m.idMaterialAtributo FROM atributos as a INNER JOIN atributosFamilias as af ON af.idAtributo= a.idAtributo INNER JOIN materialesAtributos as m ON m.idAtributoFamilia= af.idAtributoFamilia where m.idMaterial='"
				+ idMaterial + "' and a.nombre='" + nombreAtributo + "'";
		String salida = "";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {// si lo encontro si existe
				salida = rs.getString(1);

				String sql2 = "Update materialesAtributos set descripcion='" + detalle + "' where idMaterialAtributo='"
						+ salida + "'";
				try {
					PreparedStatement ps2 = conexion.getInstance().getCN().prepareStatement(sql2);
					ps2.executeUpdate();
					ps2.close();

				} catch (Exception e) {
					System.out.println("Error: modificar mat atributo" + e.getMessage());
				}

			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error: checar Descripcion Atributo" + e.getMessage());
		}
	}

	public String checarDescripcionAtributo(String idMaterial, String nombreAtributo) {
		String sql = "SELECT m.descripcion FROM atributos as a INNER JOIN atributosFamilias as af ON af.idAtributo= a.idAtributo INNER JOIN materialesAtributos as m ON m.idAtributoFamilia= af.idAtributoFamilia where m.idMaterial='"
				+ idMaterial + "' and a.nombre='" + nombreAtributo + "'";
		String salida = "";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {// si lo encontro si existe
				salida = rs.getString(1);

			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error: checar Descripcion Atributo" + e.getMessage());
		}
		return salida;
	}

	public void materialModificar(String idMaterial, String idUsuario, String descripcionComercial, String gramaje) {
		// System.out.println("Modificar material: " + idMaterial +" "+idUsuario+ " "+
		// descripcionComercial+ " "+ gramaje + "" +marca);
		String sql = "{call materialModificar(?,?,?,?)}";
		try {
			CallableStatement cs = conexion.getInstance().getCN().prepareCall(sql);
			cs.setString(1, idMaterial);
			cs.setString(2, idUsuario);
			cs.setString(3, descripcionComercial);
			cs.setString(4, gramaje);
			cs.execute();
			cs.close();
		} catch (Exception e) {
			System.out.println("Error: material  modificacion" + e.getMessage());
		}

	}

	public String atributosDeFamilia(String idFamilia) {
		String sql = "SELECT a.nombre FROM atributos as a INNER JOIN atributosFamilias as at  ON at.idAtributo=a.idAtributo and at.idFamilia='"
				+ idFamilia + "'";
		String salida = "";

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {// si lo encontro, no se puede agregar
				salida = salida + rs.getString(1) + ",";

			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error: existeMaterial" + e.getMessage() + e.getLocalizedMessage() + e.getErrorCode());
		}
		return salida;
	}

	public boolean existeMaterial(String idMaterial) {
		String sql = "select idMaterial from materiales where idMaterial='" + idMaterial + "'";
		boolean salida = true;

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {// si lo encontro, no se puede agregar
				salida = false;
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error: existeMaterial" + e.getMessage() + e.getLocalizedMessage() + e.getErrorCode());
		}
		return salida;
	}

	public int cuantasImagenes(String idMaterial) {
		String sql = "select count(ruta) from materialesImagenes where idMaterial='" + idMaterial + "'";
		int salida = 0;

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				salida = rs.getInt(1);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error: cuantasImagenes" + e.getMessage() + e.getLocalizedMessage() + e.getErrorCode());
		}
		return salida;
	}

	public String materialesImagenes(String idMaterial) {
		String sql = "select ruta from materialesImagenes where idMaterial='" + idMaterial + "'";
		String salida = "";

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				salida = salida + rs.getString(1) + "$";
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out
					.println("Error:materialesImagenes" + e.getMessage() + e.getLocalizedMessage() + e.getErrorCode());
		}
		return salida;
	}

	public void historialR(String idMaterial, String estatus, String idUsuario, String motivo) {

		String sql2 = "{call historialR (?,?,?,?)}";

		try {
			CallableStatement cs = conexion.getInstance().getCN().prepareCall(sql2);
			cs.setString(1, idMaterial);
			cs.setString(2, estatus);
			cs.setString(3, idUsuario);
			cs.setString(5, motivo);

			cs.execute();
			cs.close();

		} catch (Exception e) {
			System.out.println("Error: agregar Historial " + e.getMessage());
		}

	}

	public void historial(String idMaterial, String estatus, String idUsuario) { //
//AGREGARLO AL HISTORIAL		
		String sql2 = "{call historial (?,?,?)}";
		try {
			CallableStatement cs = conexion.getInstance().getCN().prepareCall(sql2);
			cs.setString(1, idMaterial);
			cs.setString(2, estatus);
			cs.setString(3, idUsuario);

			cs.execute();
			cs.close();

		} catch (Exception e) {
			System.out.println("Error: agregar Historial " + e.getMessage());
		}

	}

	public void cambiarEstatus(String estatus, String idMaterial, String idUsuario) {
		String sql = "{call materialCambiarEstatus (?,?,?)}";
		try {
			CallableStatement cs = conexion.getInstance().getCN().prepareCall(sql);
			cs.setString(1, estatus);
			cs.setString(2, idMaterial);
			cs.setString(3, idUsuario);

			cs.execute();
			cs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void eliminarPlataforma(String idMaterial, String nombrePlataforma) {

		String sql = "Delete materialesPlataformas where idMaterial= '" + idMaterial + "' and idPlataforma="
				+ "(Select idPlataforma from plataformas where nombre='" + nombrePlataforma + "')";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ps.executeUpdate();
			ps.close();

		} catch (Exception e) {
			System.out.println("Error: cambiar estatus " + e.getMessage());
		}
	}

	public void cambiarEstatusMatpla(String estatus, String idMaterial, String nombrePlataforma) {

		String sql = "UPDATE materialesPlataformas SET estatus='" + estatus + "'where idMaterial= '" + idMaterial
				+ "' and idPlataforma=" + "(Select idPlataforma from plataformas where nombre='" + nombrePlataforma
				+ "')";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ps.executeUpdate();
			ps.close();

		} catch (Exception e) {
			System.out.println("Error: cambiar estatus " + e.getMessage());
		}
	}

	public String registroMatpla(String nombrePlataforma, String idMaterial) {
		String sql = "SELECT estatus from materialesPlataformas where idPlataforma=(SELECT idPlataforma from plataformas where nombre='"
				+ nombrePlataforma + "')and idMaterial='" + idMaterial + "'";
		String salida = null;
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				salida = rs.getString(1);// No lo puedo agregar
			}
			ps.close();
			rs.close();
		} catch (Exception e) {

		}
		return salida;
	}

	public void materialesPlataformasNuevo(String idMaterial, String nombrePlataforma, String idUsuarioRegistra) {
		String sql = "{call materialesPlataformasNuevo (?,?,?)}";
		try {
			CallableStatement cs = conexion.getInstance().getCN().prepareCall(sql);
			cs.setString(1, idMaterial);
			cs.setString(2, nombrePlataforma);
			cs.setString(3, idUsuarioRegistra);

			cs.execute();
			cs.close();

		} catch (Exception e) {
			System.out.println("error materiales plataforma nuevo" + e.getMessage());
		}

	}

	public String usuarioRegistra(String idMaterial) {
		String sql = "select correo from usuarios where idUsuario=(select idUsuarioModifica from materiales where idMaterial='"
				+ idMaterial + "')";

		String salida = "";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				salida = rs.getString(1);
			}

			ps.close();
			rs.close();
			return salida;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return salida;
	}

	public void copyInputStreamToFile(InputStream inputStream, File file) {
		// append = false
		try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
			int read;
			byte[] bytes = new byte[8192];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		} catch (FileNotFoundException e) {
			System.out.println("algo paso en : copyInputStreamToFile" + e.getMessage());
		} catch (IOException e) {
			System.out.println("algo paso en : copyInputStreamToFile" + e.getMessage());
		}

	}

	public String validacionRegistroMasivo(String idMarca, String idFamilia, String idMaterial) {
		// validar que el numero de material exista
		// validar que la familia exista
		// validar que la marca exista
		String sql = "select idMaterial from materialesSap where idMaterial='" + idMaterial + "'";
		String salida = "";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {

				String sql1 = "select familia from familias where idFamilia='" + idFamilia + "'";
				try {
					PreparedStatement ps1 = conexion.getInstance().getCN().prepareStatement(sql1);
					ResultSet rs1 = ps1.executeQuery();
					if (rs1.next()) {
						String sql2 = "select nombre from marcas where idMarca='" + idMarca + "'";
						try {
							PreparedStatement ps2 = conexion.getInstance().getCN().prepareStatement(sql2);
							ResultSet rs2 = ps2.executeQuery();
							if (rs2.next()) {
								String sql3 = "select idMaterial from materiales where idMaterial='" + idMaterial + "'";
								try {
									PreparedStatement ps3 = conexion.getInstance().getCN().prepareStatement(sql3);
									ResultSet rs3 = ps3.executeQuery();
									if (rs3.next()) {
										salida = "El material " + idMaterial + " ya se encuetra registrado";
									} else {
										salida = "si";

									}
									ps3.close();
									rs3.close();
									return salida;

								} catch (Exception e) {
									// TODO: handle exception
								}

							} else {

								salida = "El material " + idMaterial + " no se puede agregar, el ID marca: " + idMarca
										+ " no existe";
							}
							ps2.close();
							rs2.close();
							return salida;

						} catch (Exception e) {
							// TODO: handle exception
						}
					}

					else {

						salida = "El material " + idMaterial + " no se puede agregar, la familia: " + idFamilia
								+ "no existe";
					}
					ps1.close();
					rs1.close();
					return salida;

				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			else {

				salida = "El material no se puede agregar: " + idMaterial + "no existe";
			}
			ps.close();
			rs.close();

			return salida;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return salida;
	}

	public String regresarIdMarca(String nombreMarca) {
		String sql = "select idMarca from marcas where nombre=" + nombreMarca;
		String salida = "";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				salida = rs.getString(1);
			}
			ps.close();
			rs.close();
			return salida;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

	public String regresarIdFamilia(String nombreFamilia) {
		String sql = "select idFamilia from familias where familia=" + nombreFamilia;
		String salida = "";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				salida = rs.getString(1);
			}
			ps.close();
			rs.close();
			return salida;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

	public String nombreFamilia(String idFamilia) {
		String sql = "select familia from familias where idfamilia='" + idFamilia + "'";
		String salida = "";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				salida = rs.getString(1);
			}
			ps.close();
			rs.close();
			return salida;

		} catch (Exception e) {
			System.out.println("Error nombreFamilia:" + e.getMessage());
		}
		return salida;
	}

	public producto consultaIndividual(String idMaterial) {
		String sql = "execute consultaIndividualMaterial " + idMaterial;
		producto p = new producto();
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {

				p.setIdMaterial(rs.getString("idMaterial"));
				p.setDescripcionMaterial(rs.getString("descripcionMaterial"));
				p.setIdFamilia(rs.getString("idFamilia"));
				p.setDescripcionFamilia(rs.getString("descripcionFamilia"));
				p.setIdProveedor(rs.getString("idProveedor"));
				p.setDescripcionProveedor(rs.getString("descripcionProveedor"));
				p.setIdGpoArt(rs.getString("idGpoArt"));
				p.setDescripcionGpoArt(rs.getString("descripcionGpoArt"));
				p.setIdCategoria(rs.getString("idCategoria"));
				p.setDescripcionCategoria(rs.getString("descripcionCategoria"));
				p.setIdMarca(rs.getString("idMarca"));
				p.setDescripcionMarca(rs.getString("descripcionMarca"));
				p.setEan(rs.getString("ean"));
				p.setUm(rs.getString("um"));
				p.setGramaje(rs.getString("gramaje"));
				p.setConversionUm(rs.getString("conversionUm"));
				p.setSustituto(rs.getString("idMaterialSustituto"));
				p.setEstatus(rs.getString("estatus"));
				p.setDescripcionComercial(rs.getString("descripcionComercial"));
			}
			ps.close();
			rs.close();

		} catch (SQLException e) {
			System.out.println("Error ConsultaIndividual DAO:" + e.getMessage());
		}
		return p;
	}

	public List<producto> busqueda(String pagina) {
		ArrayList<producto> lista = new ArrayList<>();

		// String sql = "execute busquedaMateriales '" + pagina + "','" + busqueda +
		// "','" + tipo + "'";
		// System.out.println("nueva lista");
		String sql = "execute  filtrosMateriales '" + pagina + "','','','','','','',''";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				producto p = new producto();
				p.setIdMaterial(rs.getNString("idMaterial"));
				p.setDescripcionMaterial(rs.getNString("descripcionMaterial"));
				p.setDescripcionFamilia(rs.getNString("descripcionFamilia"));
				p.setEstatus(rs.getString("estatus"));
				lista.add(p);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return lista;
	}

	public List<producto> filtrado(int pagina, String idMaterial, String descripcion, String estatus, String categoria,
			String familia, String proveedor, String plataforma) {
		ArrayList<producto> lista = new ArrayList<>();
//exec filtrosMateriales 1,'idMaterial','descripcion','estatus','categoria','familia','proveedor','plataforma'
		String sql = "exec  filtrosMateriales '" + pagina + "','" + idMaterial + "','" + descripcion + "','" + estatus
				+ "','" + categoria + "','" + familia + "','" + proveedor + "','" + plataforma + "'";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				producto p = new producto();
				p.setIdMaterial(rs.getString(1));
				p.setDescripcionMaterial(rs.getString(2));
				p.setDescripcionFamilia(rs.getString(3));
				p.setEstatus(rs.getString(4));
				lista.add(p);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return lista;
	}

	public List<producto> filtradoImg(int pagina, String idMaterial, String descripcion, String categoria,
			String familia, String proveedor) {
		ArrayList<producto> lista = new ArrayList<>();
		String sql = "exec  filtroImg '" + pagina + "','" + idMaterial + "','" + descripcion + "','" + categoria + "','"
				+ familia + "','" + proveedor + "'";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				producto p = new producto();
				p.setIdMaterial(rs.getString(1));
				p.setDescripcionMaterial(rs.getString(2));

				lista.add(p);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error: filtroimg " + e.getMessage());
		}
		return lista;
	}

//	public List<producto> filtroBusqueda(String pagina, String filtro) {
//		System.out.println("ENTRE BUSQUEDA GENERAL producto");
//		ArrayList<producto> lista = new ArrayList<>();
//		String sql = "execute busquedaGeneral  'materiales','estatus','idMaterial','" + pagina + "','20',''," + "'"
//				+ filtro + "'";
//		System.out.println("pagina: " + pagina + " filtro: " + filtro);
//
//		try {
//			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
//			ResultSet rs = ps.executeQuery();
//
//			while (rs.next()) {
//				producto p = new producto();
//				p.setIdMaterial(rs.getNString("idMaterial"));
//				p.setDescripcionMaterial(rs.getNString("descripcionMaterial"));
//				p.setDescripcionFamilia(rs.getNString("descripcionFamilia"));
//				p.setEstatus(rs.getString("estatus"));
//				lista.add(p);
//			}
//			ps.close();
//			rs.close();
//		} catch (SQLException e) {
//			System.out.println("Error: " + e.getMessage());
//		}
//		return lista;
//	}

	public String idMarca(String nombre) {
		String sql = "select idMarca from marcas where nombre='" + nombre + "'";
		String idMarca = "";

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				idMarca = rs.getString(1);
			} else {
				idMarca = "";
			}

			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println(
					"Error: producto dao idMarca" + e.getMessage() + e.getLocalizedMessage() + e.getErrorCode());
		}
		return idMarca;
	}

	public void materialAtributoNuevo(String idMaterial, String descripcion, String atributo,
			String idUsuarioRegistra) {
		// System.out.println("Registar nuevo atributo: " + idMaterial + descripcion +
		// atributo + idUsuarioRegistra);
		String sql = "{call materialAtributoNuevo (?,?,?,?)}";
		try {
			CallableStatement cs = conexion.getInstance().getCN().prepareCall(sql);
			cs.setString(1, idMaterial);
			cs.setString(2, descripcion);
			cs.setString(3, atributo);
			cs.setString(4, idUsuarioRegistra);

			cs.execute();
			cs.close();

		} catch (Exception e) {
			System.out.println("Error: material atributo nuevo" + e.getMessage());
		}

	}

	public void materialNuevo(String idMaterial, String idMarca, String descripcionComercial,
			String idUsuarioRegistra) {
		String sql = "{call materialNuevo (?,?,?,?)}";
		// System.out.println("ENTRE A MATERIAL NUEVO :" + idMaterial+ " "+ idMarca + "
		// "+descripcionComercial+ " "+
		// idUsuarioRegistra);
		try {
			CallableStatement cs = conexion.getInstance().getCN().prepareCall(sql);
			cs.setString(1, idMaterial);
			cs.setString(2, idMarca);
			cs.setString(3, descripcionComercial);
			cs.setString(4, idUsuarioRegistra);

			cs.execute();
			cs.close();

		} catch (Exception e) {
			System.out.println("Error material nuevo" + e.getMessage());
		}

	}

	public String atributosFamilia(String nombre) {
		String idFamilia = "";
		String sql1 = "select idFamilia from familias where Familia = '" + nombre + "'";
		try {
			PreparedStatement ps1 = conexion.getInstance().getCN().prepareStatement(sql1);
			ResultSet rs1 = ps1.executeQuery();

			if (rs1.next()) {
				idFamilia = rs1.getString(1);
			} else {
			}
			rs1.close();
			ps1.close();

		} catch (SQLException e) {
			System.out.println("Error al conectar con la BD al validar " + e.getMessage());
		}

		ArrayList<atributo> lista = new ArrayList<>();
		String sql = "select (select nombre from atributos where idAtributo = a.idAtributo ) nombre from atributosFamilias a where idFamilia='"
				+ idFamilia + "'";
		String strAtributo = "";

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				atributo atributo = new atributo();
				atributo.setNombre(rs.getString("nombre"));
				strAtributo = strAtributo + rs.getString("nombre") + "@";
				lista.add(atributo);
			}
			// System.out.println(strAtributo);
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error: producto dao AtrbutosFamilia" + e.getMessage() + e.getLocalizedMessage()
					+ e.getErrorCode());
		}
		return strAtributo;
	}

	public String atributoFamilia2(String nombreAtributo, String nombreFamilia, int idMaterial) {
		String detalle = "";
		String sql1 = "select descripcion from materialesAtributos where idAtributoFamilia =(SELECT idAtributoFamilia from atributosFamilias where idFamilia=(select idFamilia from familias where Familia='"
				+ nombreFamilia + "') and idAtributo = (select idAtributo from atributos where nombre ='"
				+ nombreAtributo + "')) and idMaterial = " + idMaterial;
		try {
			PreparedStatement ps1 = conexion.getInstance().getCN().prepareStatement(sql1);
			ResultSet rs1 = ps1.executeQuery();

			if (rs1.next()) {
				detalle = rs1.getString(1);
			} else {
			}
			rs1.close();
			ps1.close();

		} catch (SQLException e) {
			System.out.println("Error al conectar con la BD al validar " + e.getMessage());
		}
		return detalle;
	}

//	public void RegistroMasivoMaterial(String[] numMatImg, InputStream[] imgs) {
//		for (int i = 0; i < numMatImg.length; i++) {
//			// obtener el numero de material
//			StringTokenizer st = new StringTokenizer(numMatImg[i], "_");
//			String numMat = "";
//			if (st.hasMoreTokens()) {
//				numMat = st.nextToken();// sin el _
//			}
//			// verificar que el nummat exista en la base de datos
//			String sql = "select * from articulos_conector where id_material=" + numMat;
//			try {
//				PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
//				ResultSet rs = ps.executeQuery();
//				if (rs.next()) {
//					// si existe un elemento con ese num material
//					// 2 verificar que el nombre de la imagen no exista ya en la tabla de
//					// imagenesconector
//					sql = "select * from imagen_conector where numero_material=" + numMat + " and nombre='"
//							+ numMatImg[i] + "'";
//					PreparedStatement ps2 = conexion.getInstance().getCN().prepareStatement(sql);
//					ResultSet rs2 = ps2.executeQuery();
//					if (!rs2.next()) {
//						// como no existe, puedo agregar la imagen
//						sql = "insert into imagen_conector values (?,?,?)";
//						PreparedStatement ps3 = conexion.getInstance().getCN().prepareStatement(sql);
//						ps3.setInt(1, Integer.parseInt(numMat));// material
//						ps3.setBinaryStream(2, imgs[i]);// imagen
//						ps3.setString(3, numMatImg[i]);// nombre
//
//						ps3.executeUpdate();
//					}
//				}
//
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//
//	}

//	public String[] paginacionGrupoarticulo(String pagina, String numreg, String numcategoria) {
//		String[] res = new String[2];
//		String sql = "{call registro_masivo(?,?,?)}";
//		String sql2 = "select descripcion_corta from articulos where id_material = ?";
//
//		int idmaterial = 0;
//		String descripcioncorta = "";
//
//		try {
//			CallableStatement cs = conexion.getInstance().getCN().prepareCall(sql);
//
//			cs.setString(1, pagina);
//			cs.setString(2, numreg);
//			cs.setString(3, numcategoria);
//			ResultSet rs = cs.executeQuery();
//
//			if (rs.next()) {
//				idmaterial = rs.getInt(1);
//
//				PreparedStatement ps = conexion.getInstance().getCN().prepareCall(sql2);
//				ps.setInt(1, idmaterial);
//				ResultSet rss = ps.executeQuery();
//
//				if (rss.next()) {
//					descripcioncorta = rss.getString(1);
//				}
//
//				ps.close();
//			}
//
//		} catch (Exception e) {
//			e.getMessage();
//		}
//
//		return res;
//	}

//public void agregar_atributos(String id_material, String id_categoria, String descripcion_corta,String id_usuario, String descripcion, String presentacion ,String gramaje, String ingredientes, String aroma_sabor ){
//		String sql = "{call registro_atributo(?,?,?,?,?,?,?,?,?)}";
//	
//		
//		System.out.println("call nuevo_usuario '"+id_material+"','"+descripcion_corta+"','"+id_usuario+"','"+presentacion+"','"+gramaje+"','"+ingredientes+"','"+aroma_sabor);
//		try {
//				CallableStatement cs = conexion.getInstance().getCN().prepareCall(sql);
//				cs.setString(1,id_material);
//				cs.setString(2,id_categoria);
//				cs.setString(3,descripcion_corta);
//				cs.setString(4, id_usuario);
//				cs.setString(5,descripcion);
//				cs.setString(6, presentacion);
//				cs.setString(7, gramaje);
//				cs.setString(8, ingredientes);
//				cs.setString(9, aroma_sabor);
//				cs.execute();
//				cs.close();
//								
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		
//	}

	public void subir_imagen(String idMaterial, String nombre, String idUsuario) {// ya modificado
		try {
			System.out.println("Entre a subir imagen" + idMaterial + " nombre: " + nombre);

			String sql = "{call nuevaImagen(?,?,?)}";

			PreparedStatement ps2 = conexion.getInstance().getCN().prepareCall(sql);
			ps2.setString(1, idMaterial);
			ps2.setString(2, nombre);
			ps2.setString(3, idUsuario);
			ps2.execute();

			ps2.close();

		} catch (Exception e) {
			System.out.println("Error subir_imagen " + e.getMessage());
		}

	}

	// metodo para validad si el producto se encuentra en la tabla de articulos
	// local
	public String validar_local(String material) {
		String salida = "";
		String sql = "SELECT estatus FROM materiales WHERE idMaterial='" + material + "'";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				salida = rs.getString(1); // si encontro el material
			} else {
				salida = ""; // no encontro
			}
			// System.out.println(salida);
			rs.close();
			ps.close();

		} catch (SQLException e) {
			System.out.println("Error al conectar con la BD al validar " + e.getMessage());
		}
		return salida;
	}

	// metodo para validad si el producto se encuentra en la tabla de articulos
	// externa
	public boolean validar_externo(String material) {
		boolean salida = false;
		String sql = "SELECT * FROM materialesSap WHERE idMaterial='" + material + "'";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				salida = true; // si encontro el material
			} else {
				salida = false; // no encontro
			}
			// System.out.println(salida);
			rs.close();
			ps.close();

		} catch (SQLException e) {
			System.out.println("Error validar Externo" + e.getMessage());
			salida = false;
		}
		return salida;
	}

	public producto datosMaterial(String material) {
		String sql = "select top 1 m.descripcionMaterial,m.idProveedor, m.gramaje,m.descripcionProveedor,m.idMarca, m.descripcionMarca,"
				+ "m.idGpoArt,m.descripcionGpoArt,m.idCategoria, m.descripcionCategoria,f.idFamilia,f.familia as descripcionFamilia,"
				+ "(select descripcionSustituto from materialesSap where idMaterialSustituto=m.IdMaterialSustituto) as sustituto,"
				+ " m.descripcionComercial from materiales as m inner join familias as f on m.idRegistroFamilia=m.idRegistroFamilia where idMaterial="
				+ material;
		producto p = new producto();

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				p.setDescripcionMaterial(rs.getString("descripcionMaterial"));
				p.setIdProveedor(rs.getString("idProveedor"));
				p.setDescripcionProveedor(rs.getString("descripcionProveedor"));
				p.setIdMarca(rs.getString("idMarca"));
				p.setDescripcionMarca(rs.getString("descripcionMarca"));
				p.setIdGpoArt(rs.getString("idGpoArt"));
				p.setDescripcionGpoArt(rs.getString("descripcionGpoArt"));
				p.setIdCategoria(rs.getString("idCategoria"));
				p.setDescripcionCategoria(rs.getString("descripcionCategoria"));
				p.setIdFamilia(rs.getString("idFamilia"));
				p.setDescripcionFamilia(rs.getString("descripcionFamilia"));
				p.setGramaje(rs.getString("gramaje"));
				p.setSustituto(rs.getString("sustituto"));
				p.setDescripcionComercial(rs.getString("descripcionComercial"));

				rs.close();
				ps.close();
			}

		} catch (SQLException e) {
			System.out.println("Error datosMaterial" + e.getMessage());
		}
		return p;

	}

//	public List<producto> consultar(String pagina,String datos)
//	{		ArrayList<producto> lista = new ArrayList<>();
//		
//		String sql = "execute nuevo_listado"+"'"+pagina+"',"+"'" +datos+"'";
//		
//		System.out.println("pagina " + pagina);
//	
//		try {
//			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
//			ResultSet rs = ps.executeQuery();
//
//			while (rs.next()) {
//				producto producto = new producto();
//				
//				producto.setIdRegistro(rs.getString("id_registro"));
//				producto.setMaterial(rs.getString("id_material"));
//				producto.setCategoria(rs.getString("id_categoria"));
//				
//				String sql2 = "select count(*) as numero from imagen_conector where numero_material = "+rs.getString(2);
//				//System.out.println(sql2);
//				PreparedStatement ps2 = conexion.getInstance().getCN().prepareStatement(sql2);
//				ResultSet rs2 = ps2.executeQuery();
//				if(rs2.next())
//				{
//					producto.setNumImagenes(rs2.getInt("numero"));//dame el valor del count()
//				}
//				
//				
//				producto.setNombreCorto(rs.getString("descripcion_corta"));
//				producto.setEstatus(rs.getString("estatus"));
//				producto.setIngredientes(rs.getString("Ingredientes"));
//				producto.setSaborOlor(rs.getString("Aroma/Sabor"));
//				producto.setGramaje(rs.getString("Gramaje"));
//				producto.setPresentacion(rs.getString("Presentación"));
//				producto.setDescripcion(rs.getString("Descripcion"));
//	
//				lista.add(producto);
//			}
//			ps.close();
//			rs.close();
//		} catch (SQLException e) {
//			System.out.println("Error en listar productos: " + e.getMessage()+ e.getLocalizedMessage() +e.getErrorCode());
//		}
//		return lista;
//	}
//	
//	public producto consultaIndividual(int id_registro){
//		String sql = "execute listar"+"'"+id_registro+"'";
//		//System.out.print("consulta individual de : " + id_registro);
//		producto p = new producto();
//		try{
//			PreparedStatement ps=conexion.getInstance().getCN().prepareStatement(sql);
//			ResultSet rs=ps.executeQuery();
//			if(rs.next()){
//				p.setIdRegistro(rs.getString("id_registro"));
//				p.setMaterial(rs.getString("id_material"));
//				p.setCategoria(rs.getString("id_categoria"));
//				p.setNombreCorto(rs.getString("descripcion_corta"));
//				p.setEstatus(rs.getString("Estatus"));
//				p.setIngredientes(rs.getString("Ingredientes"));
//				p.setGramaje(rs.getString("Gramaje"));
//				p.setSaborOlor(rs.getString("Aromasabor"));f
//				p.setPresentacion(rs.getString("Presentacion"));
//				p.setDescripcion(rs.getString("Descripcion"));
//				
//
//				String sql2 = "select count(*) as numero from imagen_conector where numero_material = "+rs.getString("id_material");
//				//System.out.println(sql2);
//				PreparedStatement ps2 = conexion.getInstance().getCN().prepareStatement(sql2);
//				ResultSet rs2 = ps2.executeQuery();
//				if(rs2.next())
//				{
//				p.setNumImagenes(rs2.getInt("numero"));
//				}
//							}
//			ps.close();
//			rs.close();
//			
//		}
//		catch(SQLException e){
//			System.out.println("Error ConsultaIndividual DAO:"+e.getMessage());
//		}
//		return p; 
//	}
//	

//	public void ListarImagen(int material, HttpServletResponse response, int num_imagen) {
//		String sql = "select imagen from imagen_conector where numero_material =" + material;
//		InputStream is = null;
//		OutputStream os = null;
//		BufferedInputStream bufferedInputStream = null;
//		BufferedOutputStream bufferedOutputStream = null;
//		response.setContentType("image/*");
//
//		try {
//			os = response.getOutputStream();
//			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
//			ResultSet rs = ps.executeQuery();
//
//			// System.out.println(sql);
//
//			for (int i = 0; i < num_imagen; i++) {
//				// System.out.println("verificar el ciclo for: "+i+" material: "+material);
//				if (rs.next()) {
//					is = rs.getBinaryStream("imagen"); // la primer imagen
//					// System.out.println("Entro: "+is.toString());
//				}
//			}
//
//			bufferedInputStream = new BufferedInputStream(is);
//			int i = 0;
//			while ((i = bufferedInputStream.read()) != -1) {
//				response.getOutputStream().write(i);
//			}
//
//			os.close();
//			is.close();
//
//		} catch (Exception e) {
//			System.out.println("error dentro de listar imagen: " + e.getMessage());
//		}
//	}

//
//	public void modificar_atributos(String id_usuario, String estatus, String id_material, String id_categoria, String descripcion, String presentacion ,String gramaje, String ingredientes, String aroma_sabor ){
//		String sql = "{call modificacion_atributo(?,?,?,?,?,?,?,?,?)}";
//	
//	
//		//System.out.println("call modificar_atributos'"+id_material+"','"+id_usuario+"','"+descripcion+"','"+presentacion+"','"+gramaje+"','"+ingredientes+"','"+aroma_sabor+"','"+estatus);
//		try {
//				CallableStatement cs = conexion.getInstance().getCN().prepareCall(sql);
//				cs.setString(1,id_usuario);
//				cs.setString(2,estatus);
//				cs.setString(3,id_material);
//				cs.setString(4,id_categoria);
//				cs.setString(5,descripcion);
//				cs.setString(6, presentacion);
//				cs.setString(7, gramaje);
//				cs.setString(8, ingredientes);
//				cs.setString(9, aroma_sabor);
//				cs.execute();
//				cs.close();
//				
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		
//	}

//	public List<producto> busqueda(String pagina,String datos,String id )
//	{
//		ArrayList<producto> lista1 = new ArrayList<>();
//		String sql = "{call busqueda(?,?,?)}";
//			try {
//
//			CallableStatement cs = conexion.getInstance().getCN().prepareCall(sql);
//			
//			cs.setString(1,pagina);
//			cs.setString(2,datos);
//			cs.setString(3,id);
//			ResultSet rs = cs.executeQuery();
//			
//		
//
//			while (rs.next()) {
//				producto producto = new producto();
//				
//				producto.setIdRegistro(rs.getString("id_registro"));
//				producto.setMaterial(rs.getString("id_material"));
//				producto.setCategoria(rs.getString("id_categoria"));
//				
//				String sql2 = "select count(*) as numero from imagen_conector where numero_material = "+rs.getString(2);
//				//System.out.println(sql2);
//				PreparedStatement ps2 = conexion.getInstance().getCN().prepareStatement(sql2);
//				ResultSet rs2 = ps2.executeQuery();
//				if(rs2.next())
//				{
//					producto.setNumImagenes(rs2.getInt("numero"));//dame el valor del count()
//				}
//				
//				producto.setNombreCorto(rs.getString("descripcion_corta"));
//				producto.setEstatus(rs.getString("estatus"));
//				producto.setIngredientes(rs.getString("Ingredientes"));
//				producto.setSaborOlor(rs.getString("Aroma/Sabor"));
//				producto.setGramaje(rs.getString("Gramaje"));
//				producto.setPresentacion(rs.getString("Presentación"));
//				producto.setDescripcion(rs.getString("Descripcion"));
//	
//				lista1.add(producto);
//			}
//			cs.close();
//			rs.close();
//		} catch (SQLException e) {
//			System.out.println("Error en busqueda productos: " + e.getMessage()+ e.getLocalizedMessage() +e.getErrorCode());
//		}
//		return lista1;
//	}
//	
	public void eliminar_imagen(String img) {
		// System.out.println("entre a eliminar imagen:" + img);
		try {
			String sql = "DELETE materialesImagenes where nombre='" + img + "'";

			PreparedStatement ps = conexion.getInstance().getCN().prepareCall(sql);
			ps.execute();

			ps.close();

		} catch (Exception e) {
			System.out.println("Error eliminar imagen: " + e.getMessage());
		}

	}

	public int buscarId(int imagen, int material) {
		int idImagen = 0;
		try {

			String sql = "SELECT  id_imagen from imagen_conector where numero_material=" + material
					+ " ORDER BY id_imagen  OFFSET " + (imagen - 1) + " ROWS FETCH NEXT 1 ROWS ONLY";

			PreparedStatement ps = conexion.getInstance().getCN().prepareCall(sql);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				idImagen = rs.getInt("id_imagen");
			}
			rs.close();
			ps.close();
			return idImagen;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return idImagen;

	}

	public void rutasImagenes(String idMaterial) {
		try {

			String sql = "{call rutasImagenes(?)}";

			PreparedStatement ps = conexion.getInstance().getCN().prepareCall(sql);
			ps.setString(1, idMaterial);

			ps.executeUpdate();

			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public List<reporte> AllMaterialesPlataformas(String plataforma) {
		String sql = "exec TotalMaterialesPlataformas '" + plataforma+ "'";
		ArrayList<reporte> lista = new ArrayList<>();

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				reporte reporte = new reporte();
				reporte.setIdPlataforma(rs.getString(1));
				reporte.setNombrePlataforma(rs.getString(2));
				reporte.setDestacado(rs.getString(3));
				reporte.setIdMaterial(rs.getString(4));
				reporte.setDescripcionComercial(rs.getString(5));
				reporte.setDescripcionMaterial(rs.getString(6));
				reporte.setIdMaterialSustituto(rs.getString(7));
				reporte.setIdDescripcionSustituto(rs.getString(8));
				reporte.setDescripcionMarca(rs.getString(9));
				reporte.setEan(rs.getString(10));
				reporte.setUm(rs.getString(11));
				reporte.setConversionUm(rs.getString(12));
				reporte.setGramaje(rs.getString(13));
				reporte.setImagenes(rs.getString(14));
				reporte.setCategoria(rs.getString(15));
				lista.add(reporte);

			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error excel plataforma: " + e.getMessage());
		}
		return lista;
	}

	public List<reporte> ExcelArticulosPlataforma(String plataforma, String fechaInicio, String fechaFin,
			String categoria) {
		String sql = "exec reporteMaterialesPlataformas '" + plataforma + "','" + fechaInicio + "','" + fechaFin + "','"
				+ categoria + "'";
		ArrayList<reporte> lista = new ArrayList<>();

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				reporte reporte = new reporte();
				reporte.setIdPlataforma(rs.getString(1));
				reporte.setNombrePlataforma(rs.getString(2));
				reporte.setDestacado(rs.getString(3));
				reporte.setIdMaterial(rs.getString(4));
				reporte.setDescripcionComercial(rs.getString(5));
				reporte.setDescripcionMaterial(rs.getString(6));
				reporte.setIdMaterialSustituto(rs.getString(7));
				reporte.setIdDescripcionSustituto(rs.getString(8));
				reporte.setDescripcionMarca(rs.getString(9));
				reporte.setEan(rs.getString(10));
				reporte.setUm(rs.getString(11));
				reporte.setConversionUm(rs.getString(12));
				reporte.setGramaje(rs.getString(13));
				reporte.setImagenes(rs.getString(14));
				reporte.setCategoria(rs.getString(15));
				lista.add(reporte);

			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error excel plataforma: " + e.getMessage());
		}
		return lista;
	}

	public List<producto> ExcelArticulosPlataformaCategoria(String plataforma, int fechaInicio, int fechaFin,
			String categoria) {
		ArrayList<producto> lista = new ArrayList<>();

		String sql = "SELECT DISTINCT  m.idMaterial, m.descripcionMaterial, m.descripcionComercial,m.descripcionCategoria, m.idUsuarioModifica,"
				+ "(SELECT TOP 1 infoAtributos from materialesHistorial where idMaterial=m.idMaterial ORDER BY idHistorial DESC ) as infoAtributos, "
				+ "(SELECT TOP 1 cantImagenes from materialesHistorial where idMaterial=m.idMaterial ORDER BY idHistorial DESC ) as cantImagenes,"
				+ "u.correo  " + "FROM materiales as m " + "inner JOIN materialesPlataformas as mp "
				+ "ON m.idMaterial = mp.idMaterial " + "inner JOIN plataformas as p on "
				+ "p.idPlataforma=mp.idPlataforma " + "inner JOIN usuarios as u on "
				+ "u.idUsuario=m.idUsuarioModifica " + "Inner JOIN materialesHistorial as h on "
				+ "h.idMaterial=m.idMaterial " + " where m.descripcionCategoria='" + categoria + "' and p.nombre='"
				+ plataforma + "' and h.fechaModifica BETWEEN  CONVERT(datetime, convert(varchar(10),'" + fechaInicio
				+ "'))" + "AND CONVERT(datetime, convert(varchar(10),'" + fechaFin + "'))";

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				producto producto = new producto();

				producto.setIdMaterial(rs.getString("idMaterial"));
				producto.setDescripcionMaterial(rs.getString("descripcionMaterial"));
				producto.setInfoAtributos(rs.getString("infoAtributos"));
				producto.setCantImagenes(rs.getString("cantImagenes"));
				producto.setUsuarioRegistra(rs.getString("correo"));
				producto.setDescripcionComercial(rs.getString("descripcionComercial"));
				producto.setDescripcionCategoria(rs.getString("descripcionCategoria"));

				lista.add(producto);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out
					.println("Error excel plataforma: " + e.getMessage() + e.getLocalizedMessage() + e.getErrorCode());
		}
		return lista;
	}

//
//public List<producto> ExcelArticulosFaltantes()
//{
//	ArrayList<producto> lista = new ArrayList<>();
//	
//	String sql = "SELECT DISTINCT a.id_material,a.id_categoria,\r\n"
//			+ "				(SELECT TOP 1 aa.descripcion_corta FROM articulos AS aa WHERE aa.id_material=a.id_material) as descripcion\r\n"
//			+ "	FROM articulos AS a\r\n"
//			+ "		where a.id_material NOT IN (\r\n"
//			+ "									select id_material from articulos_conector\r\n"
//			+ "								)\r\n";
//	
//	try {
//		PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
//		ResultSet rs = ps.executeQuery();
//
//		while (rs.next()) {
//			producto producto = new producto();
//			
//			producto.setMaterial(rs.getString(1));
//			producto.setCategoria(rs.getString(2));
//			producto.setNombreCorto(rs.getString(3));
//
//			lista.add(producto);
//		}
//		ps.close();
//		rs.close();
//	} catch (SQLException e) {
//		System.out.println("Error en listar productos: " + e.getMessage()+ e.getLocalizedMessage() +e.getErrorCode());
//	}
//	return lista;
//}

	public List<producto> materialesImagenes() {
		ArrayList<producto> lista = new ArrayList<>();

		String sql = "exec reporteImg ";

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				producto producto = new producto();
				producto.setImagen(rs.getString(1));
				producto.setIdMaterial(rs.getString(2));
				producto.setDescripcionMaterial(rs.getString(3));
				producto.setDescripcionComercial(rs.getString(4));
				producto.setIdProveedor(rs.getString(5));
				producto.setDescripcionProveedor(rs.getString(6));
				producto.setIdCategoria(rs.getString(7));
				producto.setDescripcionCategoria(rs.getString(8));

				lista.add(producto);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error en excel imagen: " + e.getMessage() + e.getLocalizedMessage() + e.getErrorCode());
		}
		return lista;
	}

	public List<producto> ExcelImagen() {
		ArrayList<producto> lista = new ArrayList<>();

		String sql = "SELECT DISTINCT a.idMaterial, a.descripcionMaterial, a.descripcionComercial,a.idProveedor, a.descripcionProveedor, a.idCategoria, a.descripcionCategoria from materiales as a where a.idMaterial NOT IN (select idMaterial from materialesImagenes) order by a.idMaterial";

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				producto producto = new producto();

				producto.setIdMaterial(rs.getString(1));
				producto.setDescripcionMaterial(rs.getString(2));
				producto.setDescripcionComercial(rs.getString(3));
				producto.setIdProveedor(rs.getString(4));
				producto.setDescripcionProveedor(rs.getString(5));
				producto.setIdCategoria(rs.getString(6));
				producto.setDescripcionCategoria(rs.getString(7));
				lista.add(producto);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error en excel imagen: " + e.getMessage() + e.getLocalizedMessage() + e.getErrorCode());
		}
		return lista;
	}

	public int nombreCorrecto(String material, String nombre) {
		nombre = nombre.substring(0, nombre.length() - 4);
		if (!material.equals(nombre)) {

			String nombreModificado = nombre.substring(0, nombre.length() - 2);
			if (material.equals(nombreModificado)) {
				return 2;
			} else {
				return 3; // imagen no pertenece al material

			}

		}
		return 1;

	}

	public int validacionImagenes(String material, String nombre) {
		int aux = 0;
		String sql = "select * from materialesImagenes where nombre ='" + nombre + "'";

		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				aux = 1; // Si existe, se debe modificar
			} else {
				String sql2 = "SELECT COUNT(idImagen)FROM materialesImagenes WHERE idMaterial='" + material + "'";
				try {
					PreparedStatement ps2 = conexion.getInstance().getCN().prepareStatement(sql2);
					ResultSet rs2 = ps2.executeQuery();
					if (rs.next()) {
						if (rs2.getInt(1) == 8) {
							aux = 2; // ya no se puede agregar mas imagenes
						} else {
							aux = 0;
						}
					} else {
						aux = 0;
					}

					ps2.close();
					rs2.close();

				} catch (Exception e) {
					System.out.println("Error en consulta count " + e.getMessage());
				}
			}
			ps.close();
			rs.close();

		} catch (SQLException e) {
			System.out.println("Error en select  * from: " + e.getMessage());
		}
		return aux;
	}

	public int CargaMasivaImagenes(String material) {// Conocer si la imagen pertenece a un material existente
		int aux = 0;
		material = material.substring(0, material.length() - 10);// 9997_1_app.png se quita la terminacion del nombre de
																	// la img
		// ver si existe el material
		String sql = "select * from materiales where idMaterial='" + material + "'";
		try {
			PreparedStatement ps = conexion.getInstance().getCN().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) { // si encontro, se puede agregar
				aux = 1;
			}

			ps.close();
			rs.close();

		} catch (SQLException e) {

			System.out.println("Error CargaMasivaImagenes: " + e.getMessage());

		}
		return aux;
	}

}// END DAO
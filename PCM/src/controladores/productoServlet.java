package controladores;

import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.NumberFormat.Style;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import modelo.datos.conexion;
import modelo.datos.familiaDAO;
import modelo.datos.marcaDAO;
import modelo.datos.plataformaDAO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.swing.text.StyleContext;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lowagie.text.Row;

import modelo.beans.Mail;
import modelo.beans.producto;
import modelo.beans.usuario;
import modelo.datos.productoDAO;
import modelo.datos.usuarioDAO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

@WebServlet(name = "producto", urlPatterns = { "/producto" })
@MultipartConfig(maxFileSize = 16177216) // 15mb

public class productoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private static boolean puedo_procesar_csv = true; // si puedo
	private static Process process;
	private static boolean cargaMasivaImagenes;
	private HSSFSheet sheet;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public productoServlet() {
		super();
	}

	// call this method repeatedly until it returns true
	private static boolean processIsTerminated() {
		try {
			process.exitValue();
		} catch (IllegalThreadStateException itse) {
			return false;
		}
		return true;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");

		String url = "ConsultaGeneral.jsp";
		HttpSession sesion = (HttpSession) request.getSession();
		usuario u = (usuario) sesion.getAttribute("usuario");
		productoDAO pdao = new productoDAO();
		usuarioDAO udao = new usuarioDAO();
		producto medidas = new producto();
		int auxReport = 0;
		List<modelo.beans.producto> productos;
		List<modelo.beans.reporte> reportes;


		switch (op) {
		case "ReporteImagenes":
			auxReport=1;
			List<producto> Nreportes;

			response.setContentType("application/vnd.ms-excel;charset=ISO-8859-1");
			response.setCharacterEncoding("ISO-8859-1");

			response.setHeader("Content-Disposition",
					"attachment; filename=\"Rutas_imagenes.xls\"");

			Nreportes  = pdao.rutaImagen();
			
			HSSFWorkbook Newlibro = new HSSFWorkbook();
			HSSFSheet Nhoja = Newlibro.createSheet();

			// generamos una fila manual
			HSSFRow Nfila_header = Nhoja.createRow(0);
			
			HSSFCell Ncel_header = Nfila_header.createCell(0);
			Ncel_header.setCellValue("ID Material");
			
			Ncel_header = Nfila_header.createCell(1);
			Ncel_header.setCellValue("Ruta");
			
			

			for (int i = 0; i < Nreportes.size(); i++) {
				HSSFRow fila = Nhoja.createRow(i+1);
				fila.createCell(0).setCellValue (Nreportes.get(i).getIdMaterial());
				fila.createCell(1).setCellValue (Nreportes.get(i).getImagen());
			}

			ByteArrayOutputStream NoutByteStream = new ByteArrayOutputStream();
			Newlibro.write(NoutByteStream);
			byte [] NoutArray = NoutByteStream.toByteArray();
			OutputStream NoutStream = response.getOutputStream();
			NoutStream.write(NoutArray);
			NoutStream.flush();

			break;
		case "reporteAtributosAll":
			auxReport=1;
			response.setContentType("application/vnd.ms-excel;charset=ISO-8859-1");
			response.setCharacterEncoding("ISO-8859-1");

			response.setHeader("Content-Disposition",
					"attachment; filename=\"Atributos_Generales.xls\"");

			reportes  = pdao.ReporteAtributosAll();
			
			HSSFWorkbook libro1 = new HSSFWorkbook();
			HSSFSheet hoja1 = libro1.createSheet();

			// generamos una fila manual
			HSSFRow fila_header3 = hoja1.createRow(0);
			
			HSSFCell cel_header3 = fila_header3.createCell(0);
			cel_header3.setCellValue("ID Material");
			
			cel_header3 = fila_header3.createCell(1);
			cel_header3.setCellValue("Descripcion comercial");
			
			cel_header3 = fila_header3.createCell(2);
			cel_header3.setCellValue("Acabado");
			
			cel_header3 = fila_header3.createCell(3);
			cel_header3.setCellValue("Atributo_generico_1");
			
			cel_header3 = fila_header3.createCell(4);
			cel_header3.setCellValue("Atributo_generico_2");
			
			cel_header3 = fila_header3.createCell(5);
			cel_header3.setCellValue("Atributo_generico_3");
			
			cel_header3 = fila_header3.createCell(6);
			cel_header3.setCellValue("Atributo_generico_4");
			
			cel_header3 = fila_header3.createCell(7);
			cel_header3.setCellValue("Atributo_generico_5");
			
			cel_header3 = fila_header3.createCell(8);
			cel_header3.setCellValue("Atributo_generico_6");
			
			cel_header3 = fila_header3.createCell(9);
			cel_header3.setCellValue("Boca");
			
			cel_header3 = fila_header3.createCell(10);
			cel_header3.setCellValue("Contenido");
			
			cel_header3 = fila_header3.createCell(11);
			cel_header3.setCellValue("Crianza");
			
			cel_header3 = fila_header3.createCell(12);
			cel_header3.setCellValue("Informacion_nutricional");
			
			cel_header3 = fila_header3.createCell(13);
			cel_header3.setCellValue("Ingredientes");
			
			cel_header3 = fila_header3.createCell(14);
			cel_header3.setCellValue("Maridaje");
			
			cel_header3 = fila_header3.createCell(15);
			cel_header3.setCellValue("Nariz");
			
			cel_header3 = fila_header3.createCell(16);
			cel_header3.setCellValue("Pais_origen_region");
			
			cel_header3 = fila_header3.createCell(17);
			cel_header3.setCellValue("Porcentaje_alcohol");
			
			cel_header3 = fila_header3.createCell(18);
			cel_header3.setCellValue("Texto");
			
			cel_header3 = fila_header3.createCell(19);
			cel_header3.setCellValue("Tipo_de_uva");
			
			cel_header3 = fila_header3.createCell(20);
			cel_header3.setCellValue("Vista");
			
			cel_header3 = fila_header3.createCell(21);
			cel_header3.setCellValue("Volumen");
			

			for (int i = 0; i < reportes.size(); i++) {
				HSSFRow fila = hoja1.createRow(i+1);
				fila.createCell(0).setCellValue (reportes.get(i).getIdMaterial());
				fila.createCell(1).setCellValue (reportes.get(i).getDescripcionComercial());
				fila.createCell(2).setCellValue (reportes.get(i).getAcabado());
				fila.createCell(3).setCellValue (reportes.get(i).getAtributo_Generico_1());
				fila.createCell(4).setCellValue (reportes.get(i).getAtributo_Generico_2());
				fila.createCell(5).setCellValue (reportes.get(i).getAtributo_Generico_3());
				fila.createCell(6).setCellValue (reportes.get(i).getAtributo_Generico_4());
				fila.createCell(7).setCellValue (reportes.get(i).getAtributo_Generico_5());
				fila.createCell(8).setCellValue (reportes.get(i).getAtributo_Generico_6());
				fila.createCell(9).setCellValue (reportes.get(i).getBoca());
				fila.createCell(10).setCellValue (reportes.get(i).getContenido());
				fila.createCell(11).setCellValue (reportes.get(i).getCrianza());
				fila.createCell(12).setCellValue (reportes.get(i).getInformacion_Nutricional());
				fila.createCell(13).setCellValue (reportes.get(i).getIngredientes());
				fila.createCell(14).setCellValue (reportes.get(i).getMaridaje());
				fila.createCell(15).setCellValue (reportes.get(i).getNariz());
				fila.createCell(16).setCellValue (reportes.get(i).getPais_de_Origen_Region());
				fila.createCell(17).setCellValue (reportes.get(i).getPorcentaje_de_alcohol());
				fila.createCell(18).setCellValue (reportes.get(i).getTexto());
				fila.createCell(19).setCellValue (reportes.get(i).getTipo_de_uva());
				fila.createCell(20).setCellValue (reportes.get(i).getVista());
				fila.createCell(21).setCellValue (reportes.get(i).getVolumen());

			}

			ByteArrayOutputStream outByteStream3 = new ByteArrayOutputStream();
			libro1.write(outByteStream3);
			byte [] outArray3 = outByteStream3.toByteArray();
			OutputStream outStream3 = response.getOutputStream();
			outStream3.write(outArray3);
			outStream3.flush();

			break;
	
		case "reporteAtributos":
			auxReport=1;
			//System.out.println("Reporte atributos");
			String familia = request.getParameter("familia");
			String fechaInicioA = request.getParameter("fechaInicioA");
			String fechaFinA = request.getParameter("fechaFinA");
			
			String[][] datos2 = pdao.reporteAtributos(fechaInicioA,fechaFinA, familia);
			
			//System.out.println("filas:{}, columnas:{}".formatted(datos2.length,datos2[0].length));

			///reporte 
			response.setContentType("application/vnd.ms-excel;charset=ISO-8859-1");
			response.setCharacterEncoding("ISO-8859-1");
			DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now2 = LocalDateTime.now();
			response.setHeader("Content-Disposition",
					"attachment; filename=\"reporteAtributos" + dtf2.format(now2) + ".xls\"");
			HSSFWorkbook libro2 = new HSSFWorkbook();
			HSSFSheet hoja2 = libro2.createSheet();
			
			for (int row = 0; row < datos2.length; row++) { //filas
				
				HSSFRow fila2 = hoja2.createRow(row);
				
			    for (int col = 0; col < datos2[row].length; col++) { //columnas
					// idmaterial
					HSSFCell celda2 = fila2.createCell(col);
					celda2.setCellValue(datos2[row][col]);
					System.out.println("Datos- "+datos2[row][col]);
			    }
				
			}

			// se acomoden todas las columnas de la hoja
			for (int col = 0; col < datos2[0].length; col++) { //columnas
				hoja2.autoSizeColumn(col);
			}

			// Ahora vamos a hacer que la repuesta que se envie al cliente web, sea un
			// archivo de excel
			ByteArrayOutputStream outByteStream2 = new ByteArrayOutputStream();
			libro2.write(outByteStream2);
			byte[] outArray2 = outByteStream2.toByteArray();

			OutputStream outStream2 = response.getOutputStream();
			outStream2.write(outArray2);
			outStream2.flush();
			break;
		case "activarMaterial":
			String tipo = request.getParameter("tipo");
			System.out.println("entre a activar material, el tipo es_:" + tipo);
			String usuario = u.getIdUsuario();
			String materialesActivados = (request.getParameter("materialesActivados")); // 10_1_app.jpg,
			System.out.println("Los materiales activados son: " + materialesActivados);
			String[] materiales = materialesActivados.split(",");
			if (materialesActivados != null) {

				for (int i = 0; i < materiales.length; i++) {
					int existMat = pdao.existMat(materiales[i]);
					if (existMat == 1) {
						System.out.println("El material si existe: " + materiales[i]);
						if (request.getAttribute("activados") == null ) {
							request.setAttribute("activados", "");
						}
						request.setAttribute("activados",
								request.getAttribute("activados") + "<br/> Material " + materiales[i] + " activado");
								pdao.activarMaterial(materiales[i], usuario);
								pdao.historial(materiales[i], "N", usuario);
					} else {
						
						// el material no existe
						System.out.println("El material no existe: " + materiales[i]);
						

						if (request.getAttribute("activados") == null ) {
							request.setAttribute("activados", "");
						}
						
						request.setAttribute("activados",
								request.getAttribute("activados") + "<br/> El material " + materiales[i] + " no existe");

					}

				}

			}
			if(tipo.equals("Admin")) {
				url = "/producto?op=AdministrarMateriales&pagina=1";
			}
			else{
				url = "Comunes/RegistroMasivo.jsp";	
			}
			break;
		case "eliminarMaterial":
			tipo = request.getParameter("tipo");
			String alerta = "";
			System.out.println("entre a eliminar material, el tipo es_ " + tipo);
			// Vamos a recibir una lista de materiales
			// checar si el material existe en materiales
			// checar si existe en materiales Plataformas
			// chechar si el material tiene imagenes
			usuario = u.getIdUsuario();
			String materialesEliminados = (request.getParameter("materialesEliminados")); // 10_1_app.jpg,
			System.out.println("Los materiales a eliminar son: " + materialesEliminados);
			materiales = materialesEliminados.split(",");
			if (materialesEliminados != null) {

				for (int i = 0; i < materiales.length; i++) {
					int existMat = pdao.existMat(materiales[i]);
					if (existMat == 1) {
						System.out.println("El material si existe: " + materiales[i]);
						int existMatPlat = pdao.existMatPlat(materiales[i]);
						if (existMatPlat == 0) { // puedo continuar, el material no tiene plataforma
							System.out.println("El material no tiene plataforma: " + materiales[i]);
							// checo si tiene imagenes
							String tieneImagen = pdao.imagenMaterial(materiales[i]);
							if (tieneImagen != "") {
								//tiene imagen
								String rutasImg = pdao.consultaRutas(materiales[i]); /// PCM/ImagenesBD/1_1_app.jpg|/PCM/ImagenesBD/1_1_dis.jpg
								if (rutasImg != null && rutasImg != " ") {// Tiene imagenes
									System.out.println("El material si tiene img: " + materiales[i]);
									String[] imagen = rutasImg.split("|");
									for (int j = 0; j < imagen.length; j++) {
										// Vamos a eliminar la imagen del servidor
										String auxImg = imagen[j];
										auxImg = auxImg.substring(10, auxImg.length());
										try {
											File fileImagen = new File(
													"E:\\apache-tomcat-7.0.109-windows-x64\\apache-tomcat-7.0.109\\webapps\\PCM\\ImagenesBD\\"
															+ auxImg);
											FileInputStream readImage = new FileInputStream(fileImagen);
											readImage.close();

											if (fileImagen.delete()) {
												System.out.println("File deleted successfully");
											} else {
												System.out.println("Failed to delete the file");
											}
										} catch (Exception e) {
											System.out.println("Error al eliminar img" + e.getMessage());
										}
									}
									pdao.materialEliminar(materiales[i], usuario); // Ya cambie el estatus, elimine img
									pdao.historial(materiales[i], "I", usuario);

									// Eliminacion exitosa
									if (request.getAttribute("eliminados") == null ) {
										request.setAttribute("eliminados", "");
									}
									request.setAttribute("eliminados", request.getAttribute("eliminados") + "<br/> El material "
											+ materiales[i] + " fue eliminado correctamente");
								}
							}
							else {
								System.out.println("No tiene imagen, camos a modificar");
								pdao.materialEliminar(materiales[i], usuario); // Ya cambie el estatus, elimine img
								pdao.historial(materiales[i], "I", usuario);
								if (request.getAttribute("eliminados") == null ) {
									request.setAttribute("eliminados", "");
								}
								
								// Eliminacion exitosa
								request.setAttribute("eliminados", request.getAttribute("eliminados") + "<br/> El material "
										+ materiales[i] + " fue eliminado correctamente");
							}

						} else {
							// el material esta asignado a una plataforma
							System.out.println("El material si tiene plataforma: " + materiales[i]);
							if (request.getAttribute("eliminados") == null ) {
								request.setAttribute("eliminados", "");
							}
							request.setAttribute("eliminados", request.getAttribute("eliminados") + "<br/> El material "
									+ materiales[i] + " pertenece a una plataforma");
						}
					} else {
						// el material no existe
						System.out.println("El material no existe: " + materiales[i]);
						if (request.getAttribute("eliminados") == null ) {
							request.setAttribute("eliminados", "");
						}

						request.setAttribute("eliminados",
								request.getAttribute("eliminados") + "<br/> El material " + materiales[i] + " no existe");

					}

				}

			}
			if(tipo.equals("Admin")) {
				url = "/producto?op=AdministrarMateriales&pagina=1";
			}
			else{
				url = "Comunes/RegistroMasivo.jsp";	
			}
			break;
		case "adminImagenEliminar":
			String imgElim = (request.getParameter("imgElim")); // 10_1_app.jpg,
			if (!imgElim.equals("")) {
				imgElim = imgElim.substring(0, imgElim.length() - 1); // la ultima coma
				String[] imagenesEliminadas = imgElim.split(",");
				if (imagenesEliminadas != null) {
					for (int j = 0; j < imagenesEliminadas.length; j++) {
						String auxImg = imagenesEliminadas[j];
						pdao.eliminar_imagen(auxImg);
						// Eliminar la imagen de la carpeta
//						String ruta = "E:\\apache-tomcat-7.0.109-windows-x64\\apache-tomcat-7.0.109\\webapps\\PCM\\ImagenesBD\\"
//								+ auxImg;
//						File imagen = new File(ruta);
//						FileInputStream readImage = new FileInputStream(imagen);
//						readImage.close();
//						imagen.delete();
						try {
							System.out.println("Eliminamos la imagen");
							File imagen = new File(
									"E:\\apache-tomcat-7.0.109-windows-x64\\apache-tomcat-7.0.109\\webapps\\PCM\\ImagenesBD\\"
											+ auxImg);
							FileInputStream readImage = new FileInputStream(imagen);
							readImage.close();

							if (imagen.delete()) {
								System.out.println("File deleted successfully");
							} else {
								System.out.println("Failed to delete the file");
							}
						} catch (Exception e) {
							System.out.println("Error al eliminar img" + e.getMessage());
						}
						// Editar el campo rutas en la bd
						String idEliminar;
						idEliminar = auxImg.substring(0, auxImg.length() - 10);
						pdao.rutasImagenes(idEliminar);

					}
				}
			}
			url = "/producto?op=AdministrarImg&pagina=1";

			break;
		case "cmMaterial":// excel carga masiva materiales
			auxReport = 1;
			response.setContentType("application/vnd.ms-excel");
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			response.setHeader("Content-Disposition",
					"attachment; filename=\"Registro-Materiales" + dtf.format(now) + ".xls\"");
			PrintWriter out = response.getWriter();
			out.println("idMaterial\tDescripcion comercial\tidMarca\t");
			out.close();
			break;
		/// Vamos a crear los nuevos reportes
		case "infoFaltante":
			auxReport = 1;
			System.out.println("Ya entre a los cambios");
			// String sinInfo = request.getParameter("selectInfoFaltante");
			// Sin atributos
			response.setContentType("application/vnd.ms-excel;charset=ISO-8859-1");
			response.setCharacterEncoding("ISO-8859-1");
			dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			now = LocalDateTime.now();
			response.setHeader("Content-Disposition",
					"attachment; filename=\"InformacionFaltante" + dtf.format(now) + ".xls\"");
			pdao = new productoDAO();
			productos = pdao.materialesSinAtributos();
			HSSFWorkbook libro = new HSSFWorkbook();
			HSSFSheet hoja = libro.createSheet();
			for (int i = 0; i < productos.size(); i++) {
				// antes de todo, generamos la cabecera del archivo de excel, solo la primera
				// vez
				if (i == 0) {
					// generamos una fila manual
					HSSFRow fila = hoja.createRow(i);
					HSSFCell cel = fila.createCell(0);
					cel.setCellValue("IdMaterial");
					cel = fila.createCell(1);
					cel.setCellValue("Descripción Material");
					cel = fila.createCell(2);
					cel.setCellValue("Descripción comercial");
					cel = fila.createCell(3);
					cel.setCellValue("Gramaje");
					cel = fila.createCell(4);
					cel.setCellValue("Atributos");
					cel = fila.createCell(5);
					cel.setCellValue("Familia");
					cel = fila.createCell(6);
					cel.setCellValue("Grupo de articulo");
					cel = fila.createCell(7);
					cel.setCellValue("Marca");
					cel = fila.createCell(8);
					cel.setCellValue("Proveedor");
				}

				HSSFRow fila = hoja.createRow(i + 1);
				// idmaterial
				HSSFCell celda = fila.createCell(0);
				celda.setCellValue(productos.get(i).getIdMaterial());
				celda = fila.createCell(1);
				celda.setCellValue(productos.get(i).getDescripcionMaterial());
				celda = fila.createCell(2);
				celda.setCellValue(productos.get(i).getDescripcionComercial());
				celda = fila.createCell(3);
				celda.setCellValue(productos.get(i).getGramaje());
				celda = fila.createCell(4);
				celda.setCellValue(productos.get(i).getInfoAtributos());
				celda = fila.createCell(5);
				celda.setCellValue(productos.get(i).getDescripcionFamilia());
				celda = fila.createCell(6);
				celda.setCellValue(productos.get(i).getDescripcionGpoArt());
				celda = fila.createCell(7);
				celda.setCellValue(productos.get(i).getDescripcionMarca());
				celda = fila.createCell(8);
				celda.setCellValue(productos.get(i).getDescripcionProveedor());

			}

			// se acomoden todas las columnas de la hoja
			hoja.autoSizeColumn(1);
			hoja.autoSizeColumn(2);
			hoja.autoSizeColumn(3);
			hoja.autoSizeColumn(4);
			hoja.autoSizeColumn(5);
			hoja.autoSizeColumn(6);
			hoja.autoSizeColumn(7);
			hoja.autoSizeColumn(8);


			// Ahora vamos a hacer que la repuesta que se envie al cliente web, sea un
			// archivo de excel
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			libro.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();

			OutputStream outStream = response.getOutputStream();
			outStream.write(outArray);
			outStream.flush();

			break;
		case "AdministrarImg":
			pdao = new productoDAO();
			String pagina = request.getParameter("pagina");
			// String tipo = request.getParameter("tipo");
			// String busqueda = request.getParameter("busqueda");
			List<producto> datos = pdao.listadoImg(pagina);
			request.setAttribute("datos", datos);
			request.setAttribute("pagina", pagina);
			url = "Comunes/AdministracionImagenes.jsp";
			break;
//		case "busquedaImg":
//			pdao = new productoDAO();
//			pagina = request.getParameter("pagina");
//			String tipo = request.getParameter("tipo");
//			String buscador = request.getParameter("buscador");
//			datos = pdao.listadoImg(pagina, tipo, buscador);
//			request.setAttribute("datos", datos);
//			request.setAttribute("pagina", pagina);
//			url = "Comunes/AdministracionImagenes.jsp";
//			break;
		case "cargaMasivaMaterial":// CSV info del material
			/// En este case se guarda en archivo csv para proceder con el registro

			try {
				// obtenemos el csv que nos subio el cliente por la pagina web
				// InputStream is;
				Part csv = request.getPart("archivoCsvMaterial");
				InputStream is = csv.getInputStream();

				File file = new File(
						"E:\\apache-tomcat-7.0.109-windows-x64\\apache-tomcat-7.0.109\\webapps\\PCM\\cache_csv\\cache_csv_m.csv");
				Files.copy(is, file.toPath(), StandardCopyOption.REPLACE_EXISTING);

				// le decimos a la clase que ahora se estaran cargado archivos csv a la bd
				// puedo_procesar_csv = false;

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("error" + e);
			}
			url = "/producto?op=RedireccionaCargaMasiva";
			break;
		case "cargaMasivaMaterialPlataforma":// CSV carga masiva de materiales a plataformas
			System.out.println("Guardando el archivo");
			try {
				// obtenemos el csv que nos subio el cliente por la pagina web
				Part csv = request.getPart("archivoCsvMaterialPlataforma");
				InputStream is = csv.getInputStream();
				File file = new File(
						"E:\\apache-tomcat-7.0.109-windows-x64\\apache-tomcat-7.0.109\\webapps\\PCM\\cache_csv\\cache_csv_mp.csv");
				Files.copy(is, file.toPath(), StandardCopyOption.REPLACE_EXISTING);


			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("error" + e);
			}
			//Necesito quedarme en la pagina
			url="/producto?op=RedireccionaCargaMasiva";
			//url = "/producto?op=Listar&pagina=1";
			break;
		case "bajaMaterialPlataforma":// CSV carga masiva de materiales a plataformas
			System.out.println("Guardando el archivo");
			try {
				// obtenemos el csv que nos subio el cliente por la pagina web
				Part csv = request.getPart("CsvBajaMaterial");
				InputStream is = csv.getInputStream();
				File file = new File(
						"E:\\apache-tomcat-7.0.109-windows-x64\\apache-tomcat-7.0.109\\webapps\\PCM\\cache_csv\\cache_csv_mpb.csv");
				Files.copy(is, file.toPath(), StandardCopyOption.REPLACE_EXISTING);


			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("error" + e);
			}
			url = "/producto?op=RedireccionaCargaMasiva";
			break;
		case "cargaMasivaDC":// CSV descripcion comercial
			try {
	
				Part csv = request.getPart("archivoCsvDC");
				InputStream is = csv.getInputStream();

				File file = new File(
						"E:\\apache-tomcat-7.0.109-windows-x64\\apache-tomcat-7.0.109\\webapps\\PCM\\cache_csv\\cache_csv_dc.csv");
				Files.copy(is, file.toPath(), StandardCopyOption.REPLACE_EXISTING);


			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("error" + e);
			}
			url = "/producto?op=RedireccionaCargaMasiva";
			break;
		case "cargaMasiva":// CSV atributos
			try {
				Part csv = request.getPart("archivoCsv");
				InputStream is = csv.getInputStream();
				File file = new File(
						"E:\\apache-tomcat-7.0.109-windows-x64\\apache-tomcat-7.0.109\\webapps\\PCM\\cache_csv\\cache_csv.csv");
				Files.copy(is, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (Exception e) {
				e.printStackTrace();
			}
			url = "/producto?op=RedireccionaCargaMasiva";
			break;

		case "actualizarEstatus": /// Administracion materiales
			String motivo = "";
			String estatus = request.getParameter("opcion");// ESTATUS PARA EL MATERIAL RECHAZADO O ACEPTADO
			String idMaterialEditar = request.getParameter("idMaterialEditar");
			usuario = u.getIdUsuario();
			// ESTATUS DEL MATERIAL, ACEPTADO, RECHAZADO, PENDIENTE
			pdao.cambiarEstatus(estatus, idMaterialEditar, usuario);
			String gramajeEditar = request.getParameter("gramajeEditar");
			String descripcionComercialEditar = request.getParameter("descripcionComercialE");
			// String marcasE=request.getParameter("marcasE");
			pdao.materialModificar(idMaterialEditar, usuario, descripcionComercialEditar, gramajeEditar);

			if (estatus.equals("R")) {
				pdao.cambiarEstatus("R", idMaterialEditar, usuario);
				motivo = request.getParameter("motivo");
				// System.out.println("EL MOTIVO ES " + motivo);
				String correoUsuario = pdao.usuarioRegistra(idMaterialEditar);
				String descripcionMaterial = pdao.descripcion(idMaterialEditar);
				// System.out.println("CORREO DE USUARIO: " + correoUsuario);
				// Mandar correo a la persona que registro el material para indicar porque fue
				// rechazado

				String titulo = "MATERIAL RECHAZADO PCM";
				String cuerpo = "Buen día, <br>" + "Se informa que el material con el numero:" + idMaterialEditar
						+ ", con descripción:" + descripcionMaterial + "  <br>"
						+ "fue rechazado por el siguiente motivo: <h1>" + motivo + " </h1><br>"
						+ "Favor de realizar las modificaciones correspondientes.";
				Mail mailMr = new Mail(correoUsuario, titulo, cuerpo, "gmail");
				String errorMr = mailMr.send();

				if (errorMr.equals("")) {
					System.out.println("Envio Correcto");
				} else {
					System.out.println("Envio incorrecto");

				}

			} else {

				// CAMBIO EL ESTATUS
				pdao.cambiarEstatus("A", idMaterialEditar, usuario);

				// Modificar los atributos
				String atributoEditar[] = request.getParameterValues("checks");// Texto,ALGO
				if (atributoEditar != null && atributoEditar.length != 0) {
					for (int i = 0; i < atributoEditar.length; i++) {
						// aqui insertamos el atributo en la familia con id atributo[i]
						// System.out.println("El valor del atributo es: " + atributoEditar[i]);
						String buscar = "edit" + atributoEditar[i]; // inputTexto
						String descripcion = request.getParameter(buscar);
						// Saber si ya existia el atributo
						String auxDes = pdao.checarDescripcionAtributo(idMaterialEditar, atributoEditar[i]);
						if (auxDes == "") {
							// no encontro nada, lo puedo registrar normal
							pdao.materialAtributoNuevo(idMaterialEditar, descripcion, atributoEditar[i], usuario);
						} else {// Si encontro, voy a editar la descripcion

							pdao.updateMatAtributo(idMaterialEditar, atributoEditar[i], descripcion);
						}

						// System.out.println("el usuario escribio: " + descripcion);

					}
				}

				// PLATAFORMAS REGISTRADAS
				String plataformasR[] = request.getParameterValues("idchecks");
				if (plataformasR != null && plataformasR.length != 0) {// Check seleccionados
					for (int i = 0; i < plataformasR.length; i++) {
						// System.out.println("La plataforma fue seleccionada :" + plataformasR[i]);
						// chechar que no exista el registro
						String estatusPlataforma = pdao.registroMatpla(plataformasR[i], idMaterialEditar);
						// System.out.println("estatus plataforma " + estatusPlataforma);
						if (estatusPlataforma == null) {// lo puedo agregar
							// Registro por primera vez

							pdao.materialesPlataformasNuevo(idMaterialEditar, plataformasR[i], usuario);

						}
						if (estatusPlataforma == "I") { // estaba inactivo, lo voy a editar a Activo
							pdao.cambiarEstatusMatpla("A", idMaterialEditar, plataformasR[i]);

						}
						// Conocer si se eliminaron imagenes
						imgElim = (request.getParameter("imgElim")); // 10_1_app.jpg,
						if (!imgElim.equals("")) {
							imgElim = imgElim.substring(0, imgElim.length() - 1); // la ultima coma
							String[] imagenesEliminadas = imgElim.split(",");
							if (imagenesEliminadas != null) {
								for (int j = 0; j < imagenesEliminadas.length; j++) {
									String auxImg = imagenesEliminadas[j];
									// auxImg = auxImg.substring(0, auxImg.length() - 4);
									pdao.eliminar_imagen(auxImg);
									// Eliminar la imagen de la carpeta
//									String ruta = "E:\\apache-tomcat-7.0.109-windows-x64\\apache-tomcat-7.0.109\\webapps\\PCM\\ImagenesBD\\"
//											+ auxImg;
//									File imagen = new File(ruta);
//									FileInputStream readImage = new FileInputStream(imagen);
//									readImage.close();
//									imagen.delete();
									try {
										System.out.println("Eliminamos la imagen");
										File imagen = new File(
												"E:\\apache-tomcat-7.0.109-windows-x64\\apache-tomcat-7.0.109\\webapps\\PCM\\ImagenesBD\\"
														+ auxImg);
										FileInputStream readImage = new FileInputStream(imagen);
										readImage.close();

										if (imagen.delete()) {
											System.out.println("File deleted successfully");
										} else {
											System.out.println("Failed to delete the file");
										}
									} catch (Exception e) {
										System.out.println("Error al eliminar img" + e.getMessage());
									}
									// Editar el campo rutas en la bd
									pdao.rutasImagenes(idMaterialEditar);

								}
							}
						}

						// Conocer los Ean que estan activados
						String eanR[] = request.getParameterValues("checksEan" + plataformasR[i]); // value
						if (eanR != null && eanR.length != 0) {
							for (int j = 0; j < eanR.length; j++) {
			
								int aux1 = Integer.parseInt(eanR[j]);
								medidas = pdao.medidas(aux1, idMaterialEditar);

								String puedeRegistrar = pdao.puedeRegistrar(idMaterialEditar, plataformasR[i],
										medidas.getEan());
								// System.out.println("validacion medida: " + puedeRegistrar);

								if (puedeRegistrar.equals("I")) {// Cambio de estatus
									pdao.medidaEditar(plataformasR[i], idMaterialEditar, medidas.getEan(), "A",
											usuario);

								}
								if (puedeRegistrar.equals("si")) {/// Si puedo registrar
									// Primera vez que lo registra
									// System.out.println("Si lo puedo registrar : idMaterial " + idMaterialEditar
									// + "Plataforma" + plataformasR[i] + "medidas" + medidas.getEan() + "um"
									// + medidas.getUm() + "conversion um" + medidas.getConversionUm() + "usuario"
									// + usuario);
									pdao.medidaNueva(idMaterialEditar, plataformasR[i], medidas.getEan(),
											medidas.getUm(), medidas.getConversionUm(), usuario);
								}

							}
						}

						// Conocer los EAN desactivados
						String eanNR[] = request.getParameterValues("checksEanOff" + plataformasR[i]);
						if (eanNR != null && eanNR.length != 0) {
							for (int j = 0; j < eanNR.length; j++) {
								int aux1 = Integer.parseInt(eanNR[j]);
								medidas = pdao.medidas(aux1, idMaterialEditar);
								String puedeRegistrar = pdao.puedeRegistrar(idMaterialEditar,plataformasR[i],
										medidas.getEan());
								if (!puedeRegistrar.equals("") && puedeRegistrar.equals("A")) {// Cambio de estatus
									System.out.println("Voy a eliminar"+idMaterialEditar+plataformasR[i]+ medidas.getEan());
									pdao.EliminarMedida(idMaterialEditar,plataformasR[i], medidas.getEan());
								}

							}
						}

					}

				}
				// PLATAFORMAS NO REGISTRADAS
				String plataformasNR[] = request.getParameterValues("idchecksoff");
				if (plataformasNR != null && plataformasNR.length != 0) {// Check seleccionados
					for (int i = 0; i < plataformasNR.length; i++) {
						// chechar que no exista el registro
						String estatusPlataforma = pdao.registroMatpla(plataformasNR[i], idMaterialEditar);
						System.out.println("Plataformas no registradas, nombre plataforma" + plataformasNR[i]+"El material es " + idMaterialEditar +" estatus" + estatusPlataforma);	
						if (estatusPlataforma != null )
						{ // estaba inactivo, lo voy a editar a Activo
							System.out.println(estatusPlataforma+idMaterialEditar + plataformasNR[i]);
							//pdao.cambiarEstatusMatpla("I", idMaterialEditar, plataformasNR[i]);
							//Eliminamos plataforma
							System.out.println("Vamos a eliminar plataforma");
							pdao.eliminarPlataforma(idMaterialEditar,  plataformasNR[i]);

						}
						//EAN de las plataformas que fueron desactivadas
						String eanR[] = request.getParameterValues("checksEan" + plataformasNR[i]);
						if (eanR != null && eanR.length != 0) {
							for (int j = 0; j < eanR.length; j++) {
								int aux1 = Integer.parseInt(eanR[j]);
								medidas = pdao.medidas(aux1, idMaterialEditar);
								String puedeRegistrar = pdao.puedeRegistrar(idMaterialEditar,plataformasNR[i],
										medidas.getEan());

								if (!puedeRegistrar.equals("") && puedeRegistrar.equals("A")) {// Cambio de estatus
									//System.out.println("Los EAN que estaban activados, los voy a eliminar " +idMaterialEditar + plataformasNR[i]+ medidas.getEan());
									pdao.EliminarMedida(idMaterialEditar,plataformasNR[i], medidas.getEan());

								}

							}
						}

						// Conocer los EAN desactivados de las plataformas desactivadas
						String eanNR[] = request.getParameterValues("checksEanOff" + plataformasNR[i]);
						if (eanNR != null && eanNR.length != 0) {
							// System.out.println("hay eans NR :" + eanNR.length);
							for (int j = 0; j < eanNR.length; j++) {
								int aux1 = Integer.parseInt(eanNR[j]);
								medidas = pdao.medidas(aux1, idMaterialEditar);
								String puedeRegistrar = pdao.puedeRegistrar(idMaterialEditar,plataformasNR[i],
										medidas.getEan());
								if (!puedeRegistrar.equals("") && puedeRegistrar.equals("A")) {// Cambio de estatus
									System.out.println("Los EAN que estaban activados, los voy a eliminar " +idMaterialEditar + plataformasNR[i]+ medidas.getEan());
									pdao.EliminarMedida(idMaterialEditar,plataformasNR[i], medidas.getEan());

								}

							}
						}

					}
				}
if(plataformasR!=null){
	for (int i = 0; i < plataformasR.length; i++) {
		// System.out.println("si hay destacados");

		String destacadoR[] = request.getParameterValues("idchecksD" + plataformasR[i]);
		if (destacadoR != null && destacadoR.length != 0) {
			// System.out.println("si hay al menos uno destacado");

			for (int k = 0; k < destacadoR.length; k++) {
				pdao.materialDestacado(idMaterialEditar, plataformasR[i], '1');
				// System.out.println(idMaterialEditar + " plataforma:" + plataformasR[i]);
				// Se selecciono al menos uno
			}
		}

		// Material NO destacado
		String destacadoNR[] = request.getParameterValues("idchecksDoff" + plataformasR[i]);
		if (destacadoNR != null && destacadoNR.length != 0) {
			for (int l = 0; l < destacadoNR.length; l++) {
				pdao.materialDestacado(idMaterialEditar, plataformasR[i], '0');
			}
		}
	}
	
}	

			}

			// ACTUALIZAR HISTORIAL
			if (estatus.equals("R")) {
				pdao.historialR(idMaterialEditar, estatus, usuario, motivo);
			} else {
				pdao.historial(idMaterialEditar, estatus, usuario);
			}

			url = "/producto?op=AdministrarMateriales&pagina=1";
			break;
//		case "infoMasiva":// Marcas, plataformas,Atributos, 
//			marcaDAO marca = new marcaDAO();
//
//			try {
//				// obtenemos el csv que nos subio el cliente por la pagina web
//				InputStream is;
//				Part csv = request.getPart("archivoCsv");
//				is = csv.getInputStream();
//				CSVReader csvReader = new CSVReader(new InputStreamReader(is));
//				String[] nextRecord;
//				usuario = u.getIdUsuario();
//
//
//				/// DATOS ESTATICOS NECESARIOS
//				String descripcion;
//				String idMarca;
//				String error = "";
//				int numcols = 0;
//				int numcolsdin = 0;
//
//				boolean saltarcabeceras = true;
//				// leemos los datos del archivo de csv uno por uno
//				while ((nextRecord = csvReader.readNext()) != null) {
//					if (saltarcabeceras) {
//						numcols = nextRecord.length;
//						saltarcabeceras = false;
//						continue;
//					} else {
//						// nextrecord es una fila : descripcion
//						descripcion = nextRecord[1];
//						idMarca = nextRecord[0];
//
//						System.out.println("idMarca: " + idMarca+" descripcion: " +descripcion);
//				
////						for (String cell : nextRecord) {
////							descripcion= cell;
////
//							marca.marcaNueva(nextRecord[1],nextRecord[0], usuario);
////							System.out.println(cell);
////
////						}
//
//
//					}
//
//				}
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			url = "/producto?op=Listar&pagina=1";

		// break;

		case "registroFamiliaCsv": // descarga el excel para la carga masiva de atributos
			auxReport = 1;
			String nombreFamilia = request.getParameter("selectFamiliasCsv");
			String atributosFamilia = pdao.atributosFamilia(nombreFamilia);// lista de los atributos: texto,otro,etc
																			// parte dinamica excel
			// constuir el excel
			atributosFamilia = atributosFamilia.trim();

			if (!atributosFamilia.isEmpty()) {
				atributosFamilia = atributosFamilia.substring(0, atributosFamilia.length() - 1);// quitamos la coma del
																								// final
				atributosFamilia = atributosFamilia.replaceAll("@", "\t");
			}

			response.setContentType("application/vnd.ms-excel");
			dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			now = LocalDateTime.now();
			response.setHeader("Content-Disposition",
					"attachment; filename=\"Registro-" + nombreFamilia + dtf.format(now) + ".xls\"");
			out = response.getWriter();
			out.println("idMaterial\t" + atributosFamilia);

			out.close();

			break;
		case "templateCategorias": // descarga el excel para la carga masiva de atributos
			auxReport = 1;
		
			response.setContentType("application/vnd.ms-excel");
			dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			now = LocalDateTime.now();
			response.setHeader("Content-Disposition",
					"attachment; filename=\"Registro-Categorias.xls\"");
			out = response.getWriter();
			out.println("idMaterial\tCategoria\tSubcategoria" );
			out.close();

			break;
		case "registroMaterialesPlataformas": // descarga el excel para la carga masiva materiales plataforma
			auxReport = 1;
			response.setContentType("application/vnd.ms-excel");
			dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			now = LocalDateTime.now();
			response.setHeader("Content-Disposition",
					"attachment; filename=\"Materiales plataforma" + dtf.format(now) + ".xls\"");
			out = response.getWriter();
			out.println("idPlataforma\tidMaterial\tUm\tDestacado");
			out.close();

			break;
		case "bajaMaterialesPlataformas": // descarga el excel para la carga masiva materiales plataforma
			auxReport = 1;
			response.setContentType("application/vnd.ms-excel");
			dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			now = LocalDateTime.now();
			response.setHeader("Content-Disposition",
					"attachment; filename=\"Baja materiales plataforma" + dtf.format(now) + ".xls\"");
			out = response.getWriter();
			out.println("idPlataforma\tidMaterial\tUm");
			out.close();

			break;
		case "templateDc": // Descarga el template para carga masiva de descripcion comercial
			auxReport = 1;
			response.setContentType("application/vnd.ms-excel");
			dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			now = LocalDateTime.now();
			response.setHeader("Content-Disposition",
					"attachment; filename=\"DescripcionComercial-Gramaje-" + dtf.format(now) + ".xls\"");
			out = response.getWriter();
			out.println("idMaterial\tDescripcion comercial\tGramaje");

			out.close();

			break;
		case "BUSQUEDA": // BUSQUEDA PARA ADMIN
			pdao = new productoDAO();
			String id = request.getParameter("buscador");
			String pagina1 = request.getParameter("pagina");
			tipo = request.getParameter("tipo");
			List<producto> datos1 = pdao.busqueda(pagina1);
			request.setAttribute("datos", datos1);
			request.setAttribute("pagina", pagina1);
			url = "Comunes/AdministracionMateriales.jsp";

			break;
		case "busqueda":// BUSQUEDA PARA USUARIOS NORMALES
			pdao = new productoDAO();
			id = request.getParameter("buscador");
			pagina1 = request.getParameter("pagina");
			tipo = request.getParameter("tipo");
			// System.out.println("pagina: " + pagina1 + " tipo:" + tipo);
			datos1 = pdao.busqueda(pagina1);
			request.setAttribute("datos", datos1);
			request.setAttribute("pagina", pagina1);
			url = "Comunes/ConsultaGeneral.jsp";

			break;
//		case "busquedaEstatus": // BUSQUEDA PARA USUARIOS NORMALES
//			// System.out.println("Servlet producto busqueda");
//			pdao = new productoDAO();
//			estatus = request.getParameter("selectFiltro");
//			// System.out.println("id" + id);
//			pagina1 = request.getParameter("pagina");
//			// cantidad1 = "3";
//			// System.out.println("pagina " + pagina1 );
//			datos1 = pdao.filtroBusqueda(pagina1, estatus);
//			request.setAttribute("datos", datos1);
//			request.setAttribute("pagina", pagina1);
//			url = "Comunes/ConsultaGeneral.jsp";
//			break;
//		case "ESTATUS":// BUSQUEDA ESTATUS PARA ADMIN
//			pdao = new productoDAO();
//			estatus = request.getParameter("selectFiltroAdmin");
//			pagina1 = request.getParameter("pagina");
//			datos1 = pdao.filtroBusqueda(pagina1, estatus);
//			request.setAttribute("datos", datos1);
//			request.setAttribute("pagina", pagina1);
//			url = "Comunes/AdministracionMateriales.jsp";
//			break;
		case "AdministrarMateriales":// ADMINISTRACION DE MATERIALES PARA ADMIN
			// System.out.println("Listar productos");
			pdao = new productoDAO();
			pagina = request.getParameter("pagina");
			datos = pdao.busqueda(pagina);
			request.setAttribute("datos", datos);
			request.setAttribute("pagina", pagina);
			url = "Comunes/AdministracionMateriales.jsp";
			break;
		case "RedireccionaCargaMasiva":
			url = "Comunes/RegistroMasivo.jsp";
			break;
		case "Listar":
			pdao = new productoDAO();
			pagina = request.getParameter("pagina");
			datos = pdao.busqueda(pagina);
			request.setAttribute("datos", datos);
			request.setAttribute("pagina", pagina);
			url = "Comunes/ConsultaGeneral.jsp";
			break;
		case "CargaMasivaImagenes":
			cargaMasivaImagenes = true;
			 System.out.println("ENTRO A CARGA MASIVA");
			// Pantalla de espera
			try {
				// tomar el vector de imagenes de la pagina web
				Collection<Part> imagenes = request.getParts();// imagenes en el input multiple
				List<Part> result = new ArrayList<>(imagenes.size() / 2);
				for (Part part : imagenes) {
					result.add(part);
				}
				String[] numMatImg = new String[imagenes.size() / 2];
				InputStream[] imgs = new InputStream[imagenes.size() / 2];
				for (int i = 0; i < imagenes.size() / 2; i++) {
					numMatImg[i] = request.getParameter("numimagen" + i); // nombre de imagen con extension .jpg,.png
					String nombre = numMatImg[i];
					// System.out.println("NOMBRE: " + nombre);
					imgs[i] = result.get(i).getInputStream(); // imagen
					int validacionNombre = pdao.CargaMasivaImagenes(nombre);// ver si existe el material
					String nombreIncorrecto = nombre;
					String material = "";//
					// System.out.println("La validacion es:" + validacionNombre); // el material
					// existe?
					if (validacionNombre == 1) {// Se continua con el registro
						material = nombre.substring(0, nombre.length() - 10);// 9997_1_app.png -10 =9997
						int validacion = pdao.validacionImagenes(material, nombre);// valido si aun queda espacio
						if (validacion == 0) { // Primera vez que se registra la imagen
							usuario = u.getIdUsuario();
							// System.out.println("La imagen sera registrada por primera vez");
							// Debo de cargar las imagenes en la carpeta dentro del proyecto PCM
							// OJO!
							File nf = new File(
									"E:\\apache-tomcat-7.0.109-windows-x64\\apache-tomcat-7.0.109\\webapps\\PCM\\ImagenesBD\\"
											+ numMatImg[i]);// crearemos un file
							pdao.copyInputStreamToFile(imgs[i], nf);
							// System.out.println("Ya guarde la imagen");

							pdao.subir_imagen(material, numMatImg[i], usuario);
							// System.out.println("Voy registre la imagen");

							if (request.getAttribute("error") == null) {
								request.setAttribute("error", "");
							}
							request.setAttribute("error", request.getAttribute("error") + "<br/> La imagen: " + nombre
									+ " fue registrada con exito.");
						}
						if (validacion == 1) { // La imagen sera modifiacada

							System.out.println("La imagen ya existe, voy a modificar:" + numMatImg[i]);
							// Debo de cargar las imagenes en la carpeta dentro del proyecto PCM

							try {
								System.out.println("Eliminamos la imagen para luego crear de nuevo");
								File imagen = new File(
										"E:\\apache-tomcat-7.0.109-windows-x64\\apache-tomcat-7.0.109\\webapps\\PCM\\ImagenesBD\\"
												+ numMatImg[i]);
								FileInputStream readImage = new FileInputStream(imagen);
								readImage.close();

								if (imagen.delete()) {
									System.out.println("File deleted successfully");
								} else {
									System.out.println("Failed to delete the file");
								}
							} catch (Exception e) {
								System.out.println("Error al eliminar img" + e.getMessage());
							}

							// Insertamos la nueva
							System.out.println("Registrar img: ");

							File nf = new File(
									"E:\\apache-tomcat-7.0.109-windows-x64\\apache-tomcat-7.0.109\\webapps\\PCM\\ImagenesBD\\"
											+ numMatImg[i]);// crearemos un file
							pdao.copyInputStreamToFile(imgs[i], nf);

							if (request.getAttribute("error") == null) {
								request.setAttribute("error", "");
							}
							request.setAttribute("error", request.getAttribute("error") + "<br/> La imagen: " + nombre
									+ " fue modificada con exito.");
							usuario = u.getIdUsuario();
							pdao.editarImg(numMatImg[i], usuario); // bd
						}
						if (validacion == 2) {// El material ya no tiene mas espacio para cargar mas imagenes
							if (request.getAttribute("error") == null) { // Caso validacion==2, donde el material no
																			// tiene espacio para mas img
								request.setAttribute("error", "");
							}
							request.setAttribute("error", request.getAttribute("error") + "<br/> El articulo "
									+ material + "rebasó el limite de imagenes.");
						}

					} else {
						// System.out.println("El material no existe");
						if (request.getAttribute("error") == null) {
							request.setAttribute("error", "");
						}
						// Este mensaje se realiza cuando la imagen no perteece a un material existente
						// Al nombre correcto se deben eliminar 3 dgitos, que son la extension .jpg
						nombreIncorrecto = nombreIncorrecto.substring(0, nombreIncorrecto.length() - 4);
						request.setAttribute("error", request.getAttribute("error") + "<br/> El articulo: "
								+ nombreIncorrecto + " no existe");

					}

				}

			} catch (IOException io) {
				System.out.println("Error en carga masiva, producto servlet" + io.getMessage());
			} // Sali del generador de la informacion en el portal
			String errorCorreo = (String) (request.getAttribute("error"));// Tomar el dato de la variable error que se
																			// encuentra en consulta general
			// Salgo del case y retomo los valores de error para poder mandarlo al usuario
			if (errorCorreo != null) {// Tiene algo, lo puedo mandar
				String correos;
				// Vamos a mandar el correo
				correos = udao.alertas(); // Regresa todos los correos los cuales tengo que mandar
				correos = correos.substring(1, correos.length());// Elimino el primer caracter
				String usuariosAlertas[] = correos.split(","); // Separo por
				// Se hace un for para mandar el mismo correo a diferentes personas
				for (int j = 0; j < usuariosAlertas.length; j++) {
					String titulo = "Cambios en imagenes PCM";
					String cuerpo = "Buen día, <br>"
							+ "Se informa que algunas imagenes de PCM han sufrido un cambio, se anexa la información: <br>"
							+ errorCorreo;
					String usuarioCorreo = usuariosAlertas[j];
					Mail mailMr = new Mail(usuarioCorreo, titulo, cuerpo, "gmail");
					String errorMr = mailMr.send();

					if (errorMr.equals("")) {
						System.out.println("Envio Correcto");
					} else {
						System.out.println("Envio incorrecto");
					}

				}
			}
			System.out.println("Todo bien carga masiva");
			//vamos a cambiar la ruta
			//url = "/producto?op=Listar&pagina=1";
			url = "/producto?op=RedireccionaCargaMasiva";
			break;
		case "AllMaterialesPlataformas": // Reporte excel materialesPlataformas
			auxReport = 1;
			String idplataforma = (request.getParameter("plataforma"));
				response.setContentType("application/vnd.ms-excel;charset=ISO-8859-1");
				response.setCharacterEncoding("ISO-8859-1");
				dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
				now = LocalDateTime.now();
				response.setHeader("Content-Disposition",
						"attachment; filename=\"Materiales_al_momento" + dtf.format(now) + ".xls\"");
				pdao = new productoDAO();
		
				reportes = pdao.AllMaterialesPlataformas(idplataforma);
				libro = new HSSFWorkbook();
				hoja = libro.createSheet();
		
				// generamos una fila manual
				HSSFRow fila_header1 = hoja.createRow(0);
				HSSFCell cel_header1 = fila_header1.createCell(0);
				cel_header1.setCellValue("ID plataforma");
				cel_header1 = fila_header1.createCell(1);
				cel_header1.setCellValue("Plataforma");
				cel_header1 = fila_header1.createCell(2);
				cel_header1.setCellValue("Destacado");
				cel_header1 = fila_header1.createCell(3);
				cel_header1.setCellValue("ID material");
				cel_header1 = fila_header1.createCell(4);
				cel_header1.setCellValue("Descripcion");
				cel_header1 = fila_header1.createCell(5);
				cel_header1.setCellValue("Descripcion comercial");
				cel_header1 = fila_header1.createCell(6);
				cel_header1.setCellValue("ID sustituto");
				cel_header1 = fila_header1.createCell(7);
				cel_header1.setCellValue("Descripcion sustituto");
				cel_header1 = fila_header1.createCell(8);
				cel_header1.setCellValue("Marca");
				cel_header1 = fila_header1.createCell(9);
				cel_header1.setCellValue("Categoria");
				cel_header1 = fila_header1.createCell(10);
				cel_header1.setCellValue("Ean");
				cel_header1 = fila_header1.createCell(11);
				cel_header1.setCellValue("Um");
				cel_header1 = fila_header1.createCell(12);
				cel_header1.setCellValue("ConversionUm");
				cel_header1 = fila_header1.createCell(13);
				cel_header1.setCellValue("Gramaje");
				cel_header1 = fila_header1.createCell(14);
				cel_header1.setCellValue("Imagenes");
			

				for (int i = 0; i < reportes.size(); i++) {
					
					HSSFRow fila = hoja.createRow(i+1);
					fila.createCell(0).setCellValue (reportes.get(i).getIdPlataforma());
					fila.createCell(1).setCellValue (reportes.get(i).getNombrePlataforma());
					fila.createCell(2).setCellValue (reportes.get(i).getDestacado());
					fila.createCell(3).setCellValue (reportes.get(i).getIdMaterial());
					fila.createCell(4).setCellValue (reportes.get(i).getDescripcionMaterial());
					fila.createCell(5).setCellValue (reportes.get(i).getDescripcionComercial());
					fila.createCell(6).setCellValue (reportes.get(i).getIdMaterialSustituto());
					fila.createCell(7).setCellValue (reportes.get(i).getIdDescripcionSustituto());
					fila.createCell(8).setCellValue (reportes.get(i).getDescripcionMarca());
					fila.createCell(9).setCellValue (reportes.get(i).getCategoria());
					fila.createCell(10).setCellValue (reportes.get(i).getEan());
					fila.createCell(11).setCellValue (reportes.get(i).getUm());
					fila.createCell(12).setCellValue (reportes.get(i).getConversionUm());
					fila.createCell(13).setCellValue (reportes.get(i).getGramaje());
					fila.createCell(14).setCellValue (reportes.get(i).getImagenes());
				}
				hoja.autoSizeColumn(0);
				hoja.autoSizeColumn(1);
				hoja.autoSizeColumn(2);
				hoja.autoSizeColumn(3);
				hoja.autoSizeColumn(4);
				hoja.autoSizeColumn(5);
				hoja.autoSizeColumn(6);
				hoja.autoSizeColumn(7);
				hoja.autoSizeColumn(8);
				hoja.autoSizeColumn(9);
				hoja.autoSizeColumn(10);
				hoja.autoSizeColumn(11);
				hoja.autoSizeColumn(12);
				hoja.autoSizeColumn(13);
				hoja.autoSizeColumn(14);
				outByteStream = new ByteArrayOutputStream();
				libro.write(outByteStream);
				outArray = outByteStream.toByteArray();
				outStream = response.getOutputStream();
				outStream.write(outArray);
				outStream.flush();

			break;
		case "ReporteMaterialPlataformaExcel": // Reporte excel materialesPlataformas
			auxReport = 1;
			String categoriaExcel = (request.getParameter("categoriaExcel"));
//			if (categoriaExcel.equals("1")) { // Tengo que listar todos
				response.setContentType("application/vnd.ms-excel;charset=ISO-8859-1");
				response.setCharacterEncoding("ISO-8859-1");
				dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
				now = LocalDateTime.now();
				response.setHeader("Content-Disposition",
						"attachment; filename=\"ReporteArticulosPlataforma" + dtf.format(now) + ".xls\"");
				pdao = new productoDAO();
				String plataformaExcel = (request.getParameter("plataformasExcel"));
				String fechaInicioExcel = (request.getParameter("fechaInicioExcel"));
				String fechaFinExcel = (request.getParameter("fechaFinExcel"));
				fechaInicioExcel = fechaInicioExcel.replaceAll("-", "");
				fechaFinExcel = fechaFinExcel.replaceAll("-", "");
				reportes=null;
				reportes = pdao.ExcelArticulosPlataforma(plataformaExcel, fechaInicioExcel, fechaFinExcel,categoriaExcel);
				libro = new HSSFWorkbook();
				hoja = libro.createSheet();
				
				// generamos una fila manual
				HSSFRow fila_header = hoja.createRow(0);
				HSSFCell cel_header = fila_header.createCell(0);
				cel_header.setCellValue("ID plataforma");
				cel_header = fila_header.createCell(1);
				cel_header.setCellValue("Plataforma");
				cel_header = fila_header.createCell(2);
				cel_header.setCellValue("Destacado");
				cel_header = fila_header.createCell(3);
				cel_header.setCellValue("ID material");
				cel_header = fila_header.createCell(4);
				cel_header.setCellValue("Descripcion");
				cel_header = fila_header.createCell(5);
				cel_header.setCellValue("Descripcion comercial");
				cel_header = fila_header.createCell(6);
				cel_header.setCellValue("ID sustituto");
				cel_header = fila_header.createCell(7);
				cel_header.setCellValue("Descripcion sustituto");
				cel_header = fila_header.createCell(8);
				cel_header.setCellValue("Marca");
				cel_header = fila_header.createCell(9);
				cel_header.setCellValue("Categoria");
				cel_header = fila_header.createCell(10);
				cel_header.setCellValue("Ean");
				cel_header = fila_header.createCell(11);
				cel_header.setCellValue("Um");
				cel_header = fila_header.createCell(12);
				cel_header.setCellValue("ConversionUm");
				cel_header = fila_header.createCell(13);
				cel_header.setCellValue("Gramaje");
				cel_header = fila_header.createCell(14);
				cel_header.setCellValue("Imagenes");
				cel_header = fila_header.createCell(15);
				cel_header.setCellValue("Usuario responsable");
				cel_header = fila_header.createCell(16);
				cel_header.setCellValue("Fecha asignacion");

				for (int i = 0; i < reportes.size(); i++) {
					
					HSSFRow fila = hoja.createRow(i+1);
					fila.createCell(0).setCellValue (reportes.get(i).getIdPlataforma());
					fila.createCell(1).setCellValue (reportes.get(i).getNombrePlataforma());
					fila.createCell(2).setCellValue (reportes.get(i).getDestacado());
					fila.createCell(3).setCellValue (reportes.get(i).getIdMaterial());
					fila.createCell(4).setCellValue (reportes.get(i).getDescripcionMaterial());
					fila.createCell(5).setCellValue (reportes.get(i).getDescripcionComercial());
					fila.createCell(6).setCellValue (reportes.get(i).getIdMaterialSustituto());
					fila.createCell(7).setCellValue (reportes.get(i).getIdDescripcionSustituto());
					fila.createCell(8).setCellValue (reportes.get(i).getDescripcionMarca());
					fila.createCell(9).setCellValue (reportes.get(i).getCategoria());
					fila.createCell(10).setCellValue (reportes.get(i).getEan());
					fila.createCell(11).setCellValue (reportes.get(i).getUm());
					fila.createCell(12).setCellValue (reportes.get(i).getConversionUm());
					fila.createCell(13).setCellValue (reportes.get(i).getGramaje());
					fila.createCell(14).setCellValue (reportes.get(i).getImagenes());
					fila.createCell(15).setCellValue (reportes.get(i).getIdUsuarioModifica());
					fila.createCell(16).setCellValue (reportes.get(i).getFechaRegistro());

				}
				hoja.autoSizeColumn(0);
				hoja.autoSizeColumn(1);
				hoja.autoSizeColumn(2);
				hoja.autoSizeColumn(3);
				hoja.autoSizeColumn(4);
				hoja.autoSizeColumn(5);
				hoja.autoSizeColumn(6);
				hoja.autoSizeColumn(7);
				hoja.autoSizeColumn(8);
				hoja.autoSizeColumn(9);
				hoja.autoSizeColumn(10);
				hoja.autoSizeColumn(11);
				hoja.autoSizeColumn(12);
				hoja.autoSizeColumn(13);
				hoja.autoSizeColumn(14);
				hoja.autoSizeColumn(15);
				hoja.autoSizeColumn(16);
				outByteStream = new ByteArrayOutputStream();
				libro.write(outByteStream);
				outArray = outByteStream.toByteArray();
				outStream = response.getOutputStream();
				outStream.write(outArray);
				outStream.flush();

			break;

		case "ExcelImagen":// Descarga reporte excel
			///// Nueva implementacion
			// Nueva implementacion
			System.out.println("Exel img");
			auxReport = 1;
			// Se necesita una codificacion tipo UTF-8 /ISO-8859-1
			response.setContentType("application/vnd.ms-excel;charset=ISO-8859-1");
			response.setCharacterEncoding("ISO-8859-1");
			dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			now = LocalDateTime.now();
			response.setHeader("Content-Disposition",
					"attachment; filename=\"ReporteImagenes" + dtf.format(now) + ".xls\"");
			pdao = new productoDAO();
			/// Total de productos
			String matAll = pdao.cantMat();
			// Total de materiales SIN imagen
			String matImgNo = pdao.cantImgNo();
			// Total de materiales CON imagen
			String matImg = pdao.cantImg();
//			out = response.getWriter();
//			out.println("Materiales SIN imagen: \t" + matImgNo);
//			out.println("Total materiales: \t" + matAll);
//			out.println("Materiales CON imagen:\t" + matImg);
//			out.println("MATERIAL SIN IMAGEN:\t\t\t\t\t\tMATERIAL CON IMAGEN:");
//			out.println(
//					"idMaterial:\tDescripción material:\tDescripción comercial:\tidProveedor\tDescripcion proveedor\tidCategoria\tDescripcion categoria\t\tidMaterial:\tDescripción material:\tDescripción comercial:\tidProveedor\tDescripcion proveedor\tidCategoria\tDescripcion categoria");
			productos = pdao.ExcelImagen();// Materiales sin imagenes
			List<modelo.beans.producto> productosImg = pdao.materialesImagenes();// Materiales con imagenes
			ArrayList<String> ar = new ArrayList<String>();

			// buscamos cual de los dos vectores es el mayor
			if (productos.size() > productosImg.size() ? true : false) {
				// el mayor es producto
				for (int i = 0; i < productos.size(); i++) { //
					try {
						ar.add(i, productos.get(i).getIdMaterial() + "\t" + productos.get(i).getDescripcionMaterial()
								+ "\t" + productos.get(i).getDescripcionComercial() + "\t"
								+ productos.get(i).getIdProveedor() + "\t" + productos.get(i).getDescripcionProveedor()
								+ "\t" + productos.get(i).getIdCategoria() + "\t"
								+ productos.get(i).getDescripcionCategoria() + "\t\t"
								+ productosImg.get(i).getIdMaterial() + "\t"
								+ productosImg.get(i).getDescripcionMaterial() + "\t"
								+ productosImg.get(i).getDescripcionComercial() + "\t"
								+ productosImg.get(i).getIdProveedor() + "\t"
								+ productosImg.get(i).getDescripcionProveedor() + "\t"
								+ productosImg.get(i).getIdCategoria() + "\t"
								+ productosImg.get(i).getDescripcionCategoria() + "\t"
								+ productosImg.get(i).getImagen() + "\t\t");


					} catch (Exception e) {
						ar.add(i, productos.get(i).getIdMaterial() + "\t" + productos.get(i).getDescripcionMaterial()
								+ "\t" + productos.get(i).getDescripcionComercial() + "\t"
								+ productos.get(i).getIdProveedor() + "\t" + productos.get(i).getDescripcionProveedor()
								+ "\t" + productos.get(i).getIdCategoria() + "\t"
								+ productos.get(i).getDescripcionCategoria() + "\t\t\t\t" + " ");
					}
				}
			} else {
				// el mayor es productoIMG
				for (int i = 0; i < productosImg.size(); i++) {
					try {
						ar.add(i, productos.get(i).getIdMaterial() + "\t" + productos.get(i).getDescripcionMaterial()
								+ "\t" + productos.get(i).getDescripcionComercial() + "\t"
								+ productos.get(i).getIdProveedor() + "\t" + productos.get(i).getDescripcionProveedor()
								+ "\t" + productos.get(i).getIdCategoria() + "\t"
								+ productos.get(i).getDescripcionCategoria() + "\t\t"
								+ productosImg.get(i).getIdMaterial() + "\t"
								+ productosImg.get(i).getDescripcionMaterial() + "\t"
								+ productosImg.get(i).getDescripcionComercial() + "\t"
								+ productosImg.get(i).getIdProveedor() + "\t"
								+ productosImg.get(i).getDescripcionProveedor() + "\t"
								+ productosImg.get(i).getIdCategoria() + "\t"
								+ productosImg.get(i).getDescripcionCategoria() + "\t"
								+ productosImg.get(i).getImagen() + "\t");

					} catch (Exception e) {
						ar.add(i,
								" " + "\t\t\t" + productosImg.get(i).getIdMaterial() + "\t"
										+ productosImg.get(i).getDescripcionMaterial() + "\t"
										+ productosImg.get(i).getDescripcionComercial() + "\t"
										+ productosImg.get(i).getIdProveedor() + "\t"
										+ productosImg.get(i).getDescripcionProveedor() + "\t"
										+ productosImg.get(i).getIdCategoria() + "\t"
										+ productosImg.get(i).getDescripcionCategoria()+ "\t"
										+ productosImg.get(i).getImagen());
					}
				}
			}

			// en base al arraylist, vamos a dar la respuesta al cliente
//			for (int i = 0; i < ar.size(); i++) {
//				out.println(ar.get(i));
//			}
//			out.close();

			libro = new HSSFWorkbook();
			hoja = libro.createSheet();
			int indiceauxmateriales = 0;
			for (int i = 0; i < ar.size(); i++) {// es un for con TODAS los materiales con y sin imagenes

				// antes de todo, generamos la cabecera del archivo de excel, solo la primera
				// vez
				if (i == 0) {
					HSSFRow fila = hoja.createRow(i);
					HSSFCell cel = fila.createCell(0);
					cel.setCellValue("Total materiales");
					cel = fila.createCell(1);
					cel.setCellValue(matAll);
					hoja.autoSizeColumn(1);
				}
				if (i == 1) {
					HSSFRow fila = hoja.createRow(i);
					HSSFCell cel = fila.createCell(0);
					cel.setCellValue("Materiales sin imagen");
					cel = fila.createCell(1);
					cel.setCellValue(matImgNo);
					hoja.autoSizeColumn(1);

				}
				if (i == 2) {
					HSSFRow fila = hoja.createRow(i);
					HSSFCell cel = fila.createCell(0);
					cel.setCellValue("Materiales con imagen");
					cel = fila.createCell(1);
					cel.setCellValue(matImg);
					hoja.autoSizeColumn(1);

				}
				if (i == 3) {
					HSSFRow fila = hoja.createRow(i);
					HSSFCell cel = fila.createCell(0);
					cel.setCellValue("");
				}

				if (i == 4) {
					// generamos una fila manual
					HSSFRow fila = hoja.createRow(i);
					HSSFCell cel = fila.createCell(0);
					cel.setCellValue("Materiales SIN imagen");
					cel = fila.createCell(1);
					cel.setCellValue("");
					cel = fila.createCell(2);
					cel.setCellValue("");
					cel = fila.createCell(3);
					cel.setCellValue("");
					cel = fila.createCell(4);
					cel.setCellValue("");
					cel = fila.createCell(5);
					cel.setCellValue("");
					cel = fila.createCell(6);
					cel.setCellValue("");
					cel = fila.createCell(7);
					cel.setCellValue("");// salto
					cel = fila.createCell(8);
					cel.setCellValue("Materiales CON imagen");
					cel = fila.createCell(9);
					cel.setCellValue("");
					cel = fila.createCell(10);
					cel.setCellValue("");
					cel = fila.createCell(11);
					cel.setCellValue("");
					cel = fila.createCell(12);
					cel.setCellValue("");
					cel = fila.createCell(13);
					cel.setCellValue("");
					cel = fila.createCell(15);
					cel.setCellValue("");
					hoja.autoSizeColumn(0);
					hoja.autoSizeColumn(1);
					hoja.autoSizeColumn(2);
					hoja.autoSizeColumn(3);
					hoja.autoSizeColumn(4);
					hoja.autoSizeColumn(5);
					hoja.autoSizeColumn(6);
					hoja.autoSizeColumn(7);
					hoja.autoSizeColumn(8);
					hoja.autoSizeColumn(9);
					hoja.autoSizeColumn(10);
					hoja.autoSizeColumn(11);
					hoja.autoSizeColumn(12);
					hoja.autoSizeColumn(13);
					hoja.autoSizeColumn(14);

					// Fila dos
				}
				if (i == 5) {
					HSSFRow fila = hoja.createRow(i);
					HSSFCell cel = fila.createCell(0);
					cel.setCellValue("Id Material");
					cel = fila.createCell(1);
					cel.setCellValue("Descripción Material");
					cel = fila.createCell(2);
					cel.setCellValue("Descripción comercial");
					cel = fila.createCell(3);
					cel.setCellValue("Id proveedor");
					cel = fila.createCell(4);
					cel.setCellValue("Descripcion proveedor");
					cel = fila.createCell(5);
					cel.setCellValue("Id categoria");
					cel = fila.createCell(6);
					cel.setCellValue("Descripcion categoria");
					cel = fila.createCell(7);
					cel.setCellValue("");// salto
					cel = fila.createCell(8);
					cel.setCellValue("Id Material");
					cel = fila.createCell(9);
					cel.setCellValue("Descripción Material");
					cel = fila.createCell(10);
					cel.setCellValue("Descripción comercial");
					cel = fila.createCell(11);
					cel.setCellValue("Id proveedor");
					cel = fila.createCell(12);
					cel.setCellValue("Descripcion proveedor");
					cel = fila.createCell(13);
					cel.setCellValue("Id categoria");
					cel = fila.createCell(14);
					cel.setCellValue("Descripcion categoria");
					cel = fila.createCell(15);
					cel.setCellValue("Tipo imagen");
				}

				// controlamos todos los datos
				if (i > 5) {
					HSSFRow fila = hoja.createRow(i + 1);
					HSSFCell celda = null;
					try {
						celda = fila.createCell(0);
						celda.setCellValue(productos.get(indiceauxmateriales).getIdMaterial());
						celda = fila.createCell(1);
						celda.setCellValue(productos.get(indiceauxmateriales).getDescripcionMaterial());
						celda = fila.createCell(2);
						celda.setCellValue(productos.get(indiceauxmateriales).getDescripcionComercial());
						celda = fila.createCell(3);
						celda.setCellValue(productos.get(indiceauxmateriales).getIdProveedor());
						celda = fila.createCell(4);
						celda.setCellValue(productos.get(indiceauxmateriales).getDescripcionProveedor());
						celda = fila.createCell(5);
						celda.setCellValue(productos.get(indiceauxmateriales).getIdCategoria());
						celda = fila.createCell(6);
						celda.setCellValue(productos.get(indiceauxmateriales).getDescripcionCategoria());
					} catch (Exception e) {
						celda.setCellValue("");
						celda = fila.createCell(1);
						celda.setCellValue("");
						celda = fila.createCell(2);
						celda.setCellValue("");
						celda = fila.createCell(3);
						celda.setCellValue("");
						celda = fila.createCell(4);
						celda.setCellValue("");
						celda = fila.createCell(5);
						celda.setCellValue("");
						celda = fila.createCell(6);
						celda.setCellValue("");
					}

					// Termina el primero, salto
					try {
						celda = fila.createCell(7);
						celda.setCellValue("");
						celda = fila.createCell(8);
						celda.setCellValue(productosImg.get(indiceauxmateriales).getIdMaterial());
						celda = fila.createCell(9);
						celda.setCellValue(productosImg.get(indiceauxmateriales).getDescripcionMaterial());
						celda = fila.createCell(10);
						celda.setCellValue(productosImg.get(indiceauxmateriales).getDescripcionComercial());
						celda = fila.createCell(11);
						celda.setCellValue(productosImg.get(indiceauxmateriales).getIdProveedor());
						celda = fila.createCell(12);
						celda.setCellValue(productosImg.get(indiceauxmateriales).getDescripcionProveedor());
						celda = fila.createCell(13);
						celda.setCellValue(productosImg.get(indiceauxmateriales).getIdCategoria());
						celda = fila.createCell(14);
						celda.setCellValue(productosImg.get(indiceauxmateriales).getDescripcionCategoria());
						celda = fila.createCell(15);
						celda.setCellValue(productosImg.get(indiceauxmateriales).getImagen());
					} catch (Exception e) {
						celda = fila.createCell(7);
						celda.setCellValue("");
						celda = fila.createCell(8);
						celda.setCellValue("");
						celda = fila.createCell(9);
						celda.setCellValue("");
						celda = fila.createCell(10);
						celda.setCellValue("");
						celda = fila.createCell(11);
						celda.setCellValue("");
						celda = fila.createCell(12);
						celda.setCellValue("");
						celda = fila.createCell(13);
						celda.setCellValue("");
						celda = fila.createCell(14);
						celda.setCellValue("");
						celda = fila.createCell(15);
						celda.setCellValue("");
					}

					// Aumentamos el indiceaux
					indiceauxmateriales++;
				}

			}

			// se acomoden todas las columnas de la hoja
			hoja.autoSizeColumn(1);
			hoja.autoSizeColumn(2);
			hoja.autoSizeColumn(3);
			hoja.autoSizeColumn(4);
			hoja.autoSizeColumn(5);
			hoja.autoSizeColumn(6);
			hoja.autoSizeColumn(7);
			hoja.autoSizeColumn(8);
			hoja.autoSizeColumn(9);
			hoja.autoSizeColumn(10);
			hoja.autoSizeColumn(11);
			hoja.autoSizeColumn(12);
			hoja.autoSizeColumn(13);
			hoja.autoSizeColumn(14);
			hoja.autoSizeColumn(15);
			// Ahora vamos a hacer que la repuesta que se envie al cliente web, sea un
			// archivo de excel
			outByteStream = new ByteArrayOutputStream();
			libro.write(outByteStream);
			outArray = outByteStream.toByteArray();
			outStream = response.getOutputStream();
			outStream.write(outArray);
			outStream.flush();
			break;
		case "REGISTRO":
			// System.out.println("------REGISTRO");
			String idMaterial = request.getParameter("numero_material");
			String gramaje = request.getParameter("gramaje");
			String descripcionComercial = request.getParameter("descripcionComercial");
			usuario = u.getIdUsuario();
			// String idMarca = (pdao.idMarca(nombreMarca));
			// Los campos que puedo cambiar son los siguientes:
			pdao.materialModificar(idMaterial, usuario, descripcionComercial, gramaje);

			// Se continua con el registro de atributos seleccionados
			// System.out.println("atributos seleccionados");
			String atributo[] = request.getParameterValues("checks");//
			if (atributo != null && atributo.length != 0) {// se selecciono al menos uno
				// System.out.println("Seleccione uno, en total son: " + atributo.length);
				for (int i = 0; i < atributo.length; i++) {
					// aqui insertamos el atributo en la familia con id atributo[i]
					// System.out.println("El valor del atributo es: " + atributo[i]);

					String buscar = "input" + atributo[i]; // inputTexto

					String descripcion = request.getParameter(buscar);

					// System.out.println("el usuario escribio: " + descripcion);

					pdao.materialAtributoNuevo(idMaterial, descripcion, atributo[i], usuario);
				}
			}

			// Se continua con el registro de imagenes
			String cantidadImagenes = (request.getParameter("cantImagen"));
			if (cantidadImagenes != null) {
				int cantimagenes = Integer.parseInt(request.getParameter("cantImagen"));
				cantimagenes = cantimagenes - 1; // div hidden cuenta como uno
				// System.out.println("IMAGENES " + cantimagenes);
				InputStream[] imagenes = new InputStream[cantimagenes];

				for (int i = 0; i < cantimagenes; i++) {
					try {
						Part Documento = request.getPart("evidencia" + (i + 1));
						imagenes[i] = Documento.getInputStream();
						String nombreimagen = request.getParameter("nombre" + (i + 1)); // 10_1_APP.PNG
						// System.out.println("idMaterial" + idMaterial + "nombre imagen " +
						// nombreimagen);

						if (nombreimagen.contains(idMaterial)) {// Si el nombre de la imagen tiene el numero de material
																// al que deberia pertenecer, si se puede agregar
							usuario = u.getIdUsuario();
							pdao.subir_imagen(idMaterial, nombreimagen, usuario);
							File nf = new File(
									"E:\\apache-tomcat-7.0.109-windows-x64\\apache-tomcat-7.0.109\\webapps\\PCM\\ImagenesBD\\"
											+ nombreimagen);// crearemos un file
							pdao.copyInputStreamToFile(imagenes[i], nf);
						} else {
							request.setAttribute("error", "La imagen no pertenece al material.");
						}

					} catch (IOException io) {
						System.out.println("Error al subir imagenes:" + io);
					}
				}
			}
			// ACTUALIZAR HISTORIAL
			pdao.historial(idMaterial, "P", usuario);
			request.setAttribute("registro", "El material " + idMaterial + " fue registrado correctamente.");

			url = "/producto?op=Listar&pagina=1";

			break;
		case "modificar":// Este medodo se utiliza cuando el usuario no es admin
			// System.out.println("Entre a Modificar");
			idMaterialEditar = request.getParameter("idMaterialEditar");
			gramajeEditar = request.getParameter("gramajeEditar");
			// marcasE= request.getParameter("marcasE");
			descripcionComercialEditar = request.getParameter("descripcionComercialE");
			usuario = u.getIdUsuario();
			// Modificar los datos base
			pdao.materialModificar(idMaterialEditar, usuario, descripcionComercialEditar, gramajeEditar);
			// Modificar los atributos seleccionados
			String atributoEditar[] = request.getParameterValues("checks");// Texto,ALGO
			if (atributoEditar != null && atributoEditar.length != 0) {
				for (int i = 0; i < atributoEditar.length; i++) {
					// aqui insertamos el atributo en la familia con id atributo[i]
					// System.out.println("El valor del atributo es: " + atributoEditar[i]);
					String buscar = "edit" + atributoEditar[i]; // inputTexto
					String descripcion = request.getParameter(buscar);
					// Saber si ya existia el atributo
					String auxDes = pdao.checarDescripcionAtributo(idMaterialEditar, atributoEditar[i]);
					if (auxDes == "") {
						// no encontro nada, lo puedo registrar normal
						pdao.materialAtributoNuevo(idMaterialEditar, descripcion, atributoEditar[i], usuario);
					} else {// Si encontro, voy a editar la descripcion
						pdao.updateMatAtributo(idMaterialEditar, atributoEditar[i], descripcion);
					}
					// System.out.println("el usuario escribio: " + descripcion);

				}
			}
			// Modificar los atributos no seleccionados
			String atributoEditarC[] = request.getParameterValues("checksOff");// Texto,ALGO
			if (atributoEditarC != null && atributoEditarC.length != 0) {
				for (int i = 0; i < atributoEditarC.length; i++) {
					// aqui insertamos el atributo en la familia con id atributo[i]
					// System.out.println("El valor del atributo es: " + atributoEditar[i]);
					String buscar = "edit" + atributoEditarC[i]; // inputTexto
					String descripcion = request.getParameter(buscar);

					// Saber si ya existia el atributo
					String auxDes = pdao.checarDescripcionAtributo(idMaterialEditar, atributoEditarC[i]);
					if (auxDes == "") {
						// no encontro nada, lo puedo registrar normal
						pdao.materialAtributoNuevo(idMaterialEditar, descripcion, atributoEditarC[i], usuario);
					} else {// Si encontro, voy a editar la descripcion

						pdao.updateMatAtributo(idMaterialEditar, atributoEditarC[i], descripcion);
					}

					// System.out.println("el usuario escribio: " + descripcion);

				}
			}

//Imagenes que se eliminaron
			imgElim = (request.getParameter("imgElim")); // 10_1_app.jpg,
			if (!imgElim.equals("")) {
				imgElim = imgElim.substring(0, imgElim.length() - 1); // la ultima coma
				String[] imagenesEliminadas = imgElim.split(",");
				if (imagenesEliminadas != null) {
					for (int j = 0; j < imagenesEliminadas.length; j++) {
						String auxImg = imagenesEliminadas[j];
						// auxImg = auxImg.substring(0, auxImg.length() - 4);
						pdao.eliminar_imagen(auxImg);
						// Eliminar la imagen de la carpeta
//						String ruta = "E:\\apache-tomcat-7.0.109-windows-x64\\apache-tomcat-7.0.109\\webapps\\PCM\\ImagenesBD\\"
//								+ auxImg;
//						File imagen = new File(ruta);
//						FileInputStream readImage = new FileInputStream(imagen);
//						readImage.close();
//						imagen.delete();
						try {
							System.out.println("Eliminamos la imagen");
							File imagen = new File(
									"E:\\apache-tomcat-7.0.109-windows-x64\\apache-tomcat-7.0.109\\webapps\\PCM\\ImagenesBD\\"
											+ auxImg);
							FileInputStream readImage = new FileInputStream(imagen);
							readImage.close();

							if (imagen.delete()) {
								System.out.println("File deleted successfully");
							} else {
								System.out.println("Failed to delete the file");
							}
						} catch (Exception e) {
							System.out.println("Error al eliminar img" + e.getMessage());
						}
						// Editar el campo rutas en la bd
						pdao.rutasImagenes(idMaterialEditar);

					}
				}
			}

//Imagenes que se agregaron
			int cantidadImagenesInt = Integer.parseInt(request.getParameter("cantImagenE"));
			int auxCant = pdao.cuantasImagenes(idMaterialEditar);
			cantidadImagenesInt = (cantidadImagenesInt - auxCant);
			// System.out.println("CUANTAS VECES DEBO ENTRAR " + cantidadImagenesInt);
			if (cantidadImagenesInt > 0) {
				InputStream[] imagenes = new InputStream[cantidadImagenesInt];

				for (int i = 0; i < cantidadImagenesInt; i++) {
					try {
						Part Documento = request.getPart("evidenciaE" + (i + 1));
						imagenes[i] = Documento.getInputStream();
						String nombreimagen = request.getParameter("nombreE" + (i + 1));// 1_1_app.jpg

						if (nombreimagen.contains(idMaterialEditar)) { // Si la imagen pertenece al material
							usuario = u.getIdUsuario();
							pdao.subir_imagen(idMaterialEditar, nombreimagen, usuario);
							File nf = new File(
									"E:\\apache-tomcat-7.0.109-windows-x64\\apache-tomcat-7.0.109\\webapps\\PCM\\ImagenesBD\\"
											+ nombreimagen);// crearemos un file//4
							pdao.copyInputStreamToFile(imagenes[i], nf);

						} else {
							request.setAttribute("error", "La imagen no pertenece al material.");

						}

					} catch (IOException io) {
						System.out.println("Error al subir img: " + io);
					}
				}
			}

			// CAMBIAR ESTATUS
			pdao.cambiarEstatus("P", idMaterialEditar, usuario);
			// ACTUALIZAR HISTORIAL
			pdao.historial(idMaterialEditar, "P", usuario);

			// Saber en donde estoy para regresarme a ese lugar
			url = "/producto?op=Listar&pagina=1";

			break;
		case "ReporteMaterialPlataforma":// PDF
			// Actualizaciones
			File reporte;
			auxReport = 1;
			Map<String, Object> parameter = new HashMap<String, Object>();
			String categoria = (request.getParameter("categoria"));
			System.out.println("Actualizaciones en plataformas, la categoria es: " + categoria);
			if (categoria == "1") {// Si la categoria es 1 (Regreso todas)
				reporte = new File(request.getRealPath("/WEB-INF/reportes/MaterialesPlataformas.jasper"));

				String plataforma = (request.getParameter("plataformas"));
				parameter.put("plataforma", plataforma);

				String fechaInicio = (request.getParameter("fechaInicio"));
				String fechaFin = (request.getParameter("fechaFin"));
				

				// Realizo los cambios para jasper
				fechaInicio = fechaInicio.replaceAll("-", "");
				fechaFin = fechaFin.replaceAll("-", "");
				int fechainicioMp = Integer.parseInt(fechaInicio);
				int fechafinMp = Integer.parseInt(fechaFin);
				fechafinMp = fechafinMp + 1;
				parameter.put("fechaInicio", fechainicioMp);// Le mando los type int
				parameter.put("fechaFin", fechafinMp);

				byte[] bytes = null;
				try {
					bytes = JasperRunManager.runReportToPdf(reporte.getPath(), parameter,
							conexion.getInstance().getCN());
				} catch (JRException e) {
					e.printStackTrace();
				}

				response.setContentType("application/pdf");
				response.setContentLength(bytes.length);
				ServletOutputStream os = response.getOutputStream();
				os.write(bytes, 0, bytes.length);

				os.flush();
				os.close();
			} else {
				reporte = new File(request.getRealPath("/WEB-INF/reportes/MaterialesPlataformasCategorias.jasper"));
				// Parametros generales
				parameter.put("categoria", categoria);
				String plataforma = (request.getParameter("plataformas"));
				parameter.put("plataforma", plataforma);
				String fechaInicio = (request.getParameter("fechaInicio"));
				String fechaFin = (request.getParameter("fechaFin"));
				// Realizo los cambios para jasper
				fechaInicio = fechaInicio.replaceAll("-", "");
				fechaFin = fechaFin.replaceAll("-", "");
				int fechainicioMp = Integer.parseInt(fechaInicio);
				int fechafinMp = Integer.parseInt(fechaFin);
				fechafinMp = fechafinMp + 1;
				parameter.put("fechaInicio", fechainicioMp);// Le mando los type int
				parameter.put("fechaFin", fechafinMp);

				byte[] bytes = null;
				try {
					bytes = JasperRunManager.runReportToPdf(reporte.getPath(), parameter,
							conexion.getInstance().getCN());
				} catch (JRException e) {
					e.printStackTrace();
				}

				response.setContentType("application/pdf");
				response.setContentLength(bytes.length);
				ServletOutputStream os = response.getOutputStream();
				os.write(bytes, 0, bytes.length);

				os.flush();
				os.close();
			}
			break;

		case "ReporteHistorial":
			auxReport = 1;
			// metodo para sacar los reportes mediante jstl
			reporte = new File(request.getRealPath("/WEB-INF/reportes/Historial.jasper"));
			parameter = new HashMap<String, Object>();
			String idMaterialReporte = (request.getParameter("idMaterialPDF"));
			String fechaInicioH = (request.getParameter("fechaInicioH"));
			String fechaFinH = (request.getParameter("fechaFinH"));

			// generamos un entero de las fechas para obtener el reporte
			fechaInicioH = fechaInicioH.replaceAll("-", "");
			fechaFinH = fechaFinH.replaceAll("-", "");
			int fechain = Integer.parseInt(fechaInicioH);
			int fechafi = Integer.parseInt(fechaFinH);
			fechafi = fechafi + 1;

			parameter.put("idMaterial", idMaterialReporte);
			parameter.put("fechaInicio", fechain);
			parameter.put("fechaFin", fechafi);

			byte[] bytes = null;
			try {
				bytes = JasperRunManager.runReportToPdf(reporte.getPath(), parameter, conexion.getInstance().getCN());
			} catch (JRException e) {
				e.printStackTrace();
			}

			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			ServletOutputStream os = response.getOutputStream();
			os = response.getOutputStream();
			os.write(bytes, 0, bytes.length);

			os.flush();
			os.close();
			break;
		case "ReporteImagen":
			auxReport = 1;
			reporte = new File(request.getRealPath("/WEB-INF/reportes/ReporteImagen.jasper"));
			parameter = new HashMap<String, Object>();
			bytes = null;

			try {
				bytes = JasperRunManager.runReportToPdf(reporte.getPath(), parameter, conexion.getInstance().getCN());
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			os = response.getOutputStream();
			os.write(bytes, 0, bytes.length);

			os.flush();
			os.close();

			break;
		case "ReporteMarcas":
			// System.out.println("Entre a reporte marcas");
			auxReport = 1;
			reporte = new File(request.getRealPath("/WEB-INF/reportes/ReporteMarcas.jasper"));
			parameter = new HashMap<String, Object>();
			bytes = null;

			try {
				bytes = JasperRunManager.runReportToPdf(reporte.getPath(), parameter, conexion.getInstance().getCN());
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			os = response.getOutputStream();
			os.write(bytes, 0, bytes.length);

			os.flush();
			os.close();

			break;

		}
		if (auxReport != 1) {
			RequestDispatcher rd = request.getRequestDispatcher(url);
			rd.forward(request, response);

		}

	}
//
//	private File File(String realPath) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		productoDAO pdao = new productoDAO();
		producto producto = new producto();
		String[] res;
		String url = "ConsultaGeneral.jsp";
		HttpSession sesion = (HttpSession) request.getSession();
		usuario u = (usuario) sesion.getAttribute("usuario");
		producto medidas = new producto();
		int auxReport = 0;
		List<modelo.beans.producto> productos;
		switch (op) {
		case "INDEX":
			System.out.println("INDEX");
			String cant= pdao.CantMaterialesPlataformas();
			response.getWriter().write(cant);
			break;
			
		case "MATERIAL":// buscar material
			String material = request.getParameter("material");

			// Busca el registro
			String interno = pdao.validar_local(material);
			if (interno.equals("")) {
				response.getWriter().write("null");// No encontro
			} else {
				if (interno.equals("N")) {// Regresa estatus nuevo
					producto = pdao.datosMaterial(material);
					String ean = pdao.materialesEan(material);
					ean = ean.substring(0, ean.length() - 1);
					String um = pdao.materialesUm(material);
					um = um.substring(0, um.length() - 1);
//						String gramaje=pdao.materialesGramaje(material);
//						gramaje=gramaje.substring(0, gramaje.length()-1);
					String conversionUm = pdao.materialesConversionUm(material);
					conversionUm = conversionUm.substring(0, conversionUm.length() - 1);
					// Le regreso las rutas de img si tiene
					String rutas = pdao.consultaRutas(material);
					// Le regreso la descripcion comercial si la tiene
					response.getWriter().write(producto.getDescripcionMaterial() + "@" + producto.getIdProveedor() + "@"
							+ producto.getDescripcionProveedor() + "@" + producto.getIdMarca() + "@"
							+ producto.getDescripcionMarca() + "@" + producto.getIdFamilia() + "@"
							+ producto.getDescripcionFamilia() + "@" + producto.getIdGpoArt() + "@"
							+ producto.getDescripcionGpoArt() + "@" + producto.getIdCategoria() + "@"
							+ producto.getDescripcionCategoria() + "@" + ean + "@" + um + "@" + conversionUm + "@"
							+ producto.getSustituto() + "@" + rutas + "@" + producto.getDescripcionComercial());
					// Mandarle la rutas por
				} else {
					response.getWriter().write("no");// No es nuevo
				}

			}

			break;
		case "edicion":
			// System.out.println("Entre a edicion materiales");
			pdao = new productoDAO();
			String idMaterial = request.getParameter("idMaterial");
			// System.out.println(idMaterial);
			producto = pdao.consultaIndividual(idMaterial);
			int cantImagenes = pdao.cuantasImagenes(idMaterial);
			String ean = pdao.materialesEan(idMaterial);
			ean = ean.substring(0, ean.length() - 1);
			
			String um = pdao.materialesUm(idMaterial);
			
				um = um.substring(0, um.length() - 1);
			
			String conversionUm = pdao.materialesConversionUm(idMaterial);
			
				conversionUm = conversionUm.substring(0, conversionUm.length() - 1);
			
			response.getWriter()
					.write(producto.getIdMaterial() + "@" + producto.getDescripcionMaterial() + "@"
							+ producto.getIdFamilia() + "@" + producto.getDescripcionFamilia() + "@"
							+ producto.getIdProveedor() + "@" + producto.getDescripcionProveedor() + "@"
							+ producto.getIdGpoArt() + "@" + producto.getDescripcionGpoArt() + "@"
							+ producto.getIdCategoria() + "@" + producto.getDescripcionCategoria() + "@" + ean + "@"
							+ um + "@" + producto.getGramaje() + "@" + conversionUm + "@" + producto.getSustituto()
							+ "@" + cantImagenes + "@" + producto.getDescripcionComercial() + "@"
							+ producto.getIdMarca() + "@" + producto.getDescripcionMarca());

			break;

		case "atributosFamilia":
			// System.out.println("ENTRE A ATRIBUOS FAMILIA");
			pdao = new productoDAO();
			String nombreFamilia = request.getParameter("familiaAJAX");
			String atributos = pdao.atributosFamilia(nombreFamilia); // hola,como,estas
			atributos = atributos.trim();
			// System.out.println("idFamilia:" + nombreFamilia + "---atributos:" +
			// atributos);
			if (!atributos.isEmpty()) {
				atributos = atributos.substring(0, atributos.length() - 1);// quitamos la coma del final

				StringTokenizer st = new StringTokenizer(atributos, "@");
				int cantAtributos = st.countTokens();
				// System.out.println("------tamaño: " + cantAtributos + " | atributos: " +
				// atributos);
				response.getWriter().write(cantAtributos + "@" + atributos);
			} else {
				response.getWriter().write("");
			}
			break;
		case "atributosFamilia2":
			// System.out.println("ENTRE A ATRIBUOS FAMILIA DOS");
			pdao = new productoDAO();
			String nombreAtributo = request.getParameter("atributoAJAX");
			String nombreFam = request.getParameter("nombreFamiliaAJAX");
			int idMat = Integer.parseInt(request.getParameter("idMaterial"));
			String valoratributo = pdao.atributoFamilia2(nombreAtributo, nombreFam, idMat); // hola,como,estas
			// System.out.println("termine atributosfamilia2: " + valoratributo);
			response.getWriter().write(nombreAtributo + "@" + valoratributo);
			break;
		case "marcasExistentes":
			// System.out.println("ENTRE A marcasExistentes");
			marcaDAO mdao = new marcaDAO();
			String marcas = mdao.marcasExistentes();
			marcas = marcas.substring(0, marcas.length() - 1);
			StringTokenizer st = new StringTokenizer(marcas, ",");
			int cantMarcas = st.countTokens();
			// System.out.println("tamaño: " + cantMarcas + " | familia: " + marcas);
			response.getWriter().write(cantMarcas + "," + marcas);
			break;
		case "marcasExistentesEdicion":
			// System.out.println("ENTRE A marcasExistentes edicion");
			mdao = new marcaDAO();
			idMaterial = request.getParameter("idMaterial");
			String marca = mdao.marcasMaterial(idMaterial);
			String marcasEditar = mdao.marcasExistentesEditar(idMaterial);
			marcasEditar = marcasEditar.substring(0, marcasEditar.length() - 1);
			st = new StringTokenizer(marcasEditar, ",");
			int cantMarcasEditar = st.countTokens();
			// System.out.println("tamaño: " + cantMarcas + " | familia: " + marcas);
			response.getWriter().write(cantMarcasEditar + ";" + marcasEditar + ";" + marca);
			break;
		case "familiasExistentesProducto":
			// System.out.println("ENTRE A familas existentes");
			familiaDAO fdao = new familiaDAO();
			String familias = fdao.familiasExistentes();
			familias = familias.substring(0, familias.length() - 1);
			st = new StringTokenizer(familias, ",");
			int cantFamilias = st.countTokens();
			// System.out.println("tamaño: " + cantFamilias + " | familia: " + familias);
			response.getWriter().write(cantFamilias + "," + familias);

			break;
		case "familiasExistentesCsv":
			// System.out.println("ENTRE A familas existentes CSV");
			fdao = new familiaDAO();
			String familiasCsv = fdao.familiasExistentes();
			familiasCsv = familiasCsv.substring(0, familiasCsv.length() - 1);
			st = new StringTokenizer(familiasCsv, ",");
			int cantFamiliasCsv = st.countTokens();
			// System.out.println("tamaño: " + cantFamiliasCsv + " | familia: " +
			// familiasCsv);
			response.getWriter().write(cantFamiliasCsv + "," + familiasCsv);
			break;

		case "edicion2":
			String idFamilia = request.getParameter("familia");
			// buscar los atributos de esa familia y los regreso asi: atrib1,atrib2,atrib3
			break;
		case "materialImagen":
			// System.out.println("ENTRE A MATERIAL IMAGEN");
			pdao = new productoDAO();
			idMaterial = request.getParameter("idMaterial");
			int cantMaterialImagenes = pdao.cuantasImagenes(idMaterial);
			// System.out.println("Cuantas imagenes:" + cantMaterialImagenes);
			if (cantMaterialImagenes > 0) {
				String materialImagenes = pdao.materialesImagenes(idMaterial);
				materialImagenes = materialImagenes.substring(0, materialImagenes.length() - 1);
				st = new StringTokenizer(materialImagenes, ",");
				// System.out.println("tamaño: " + cantMaterialImagenes + " | familia: " +
				// materialImagenes);
				response.getWriter().write(cantMaterialImagenes + "$" + materialImagenes);
			} else {
				response.getWriter().write(0 + "$" + "/PCM_2.0/Images/sinImagen.png");
			}
			break;
		case "plataformasExistentes":
			// System.out.println("ENTRE A plataformas existentes");
			plataformaDAO pldao = new plataformaDAO();
			String plataformas = pldao.plataformasExistentes();
			plataformas = plataformas.substring(0, plataformas.length() - 1);
			st = new StringTokenizer(plataformas, ";");
			int cantPlataformas = st.countTokens();
			// System.out.println("tamaño: " + cantPlataformas + " | plataformas: " +
			// plataformas);
			response.getWriter().write(cantPlataformas + ";" + plataformas);
			break;
		case "categoriasExistentes":// Listado de categorias para el reporte
			String categorias = pdao.categoriasExistentes();
			categorias = categorias.substring(0, categorias.length() - 1);
			st = new StringTokenizer(categorias, ";");
			int cantCategorias = st.countTokens();
			// System.out.println("tamaño: " + cantPlataformas + " | plataformas: " +
			// plataformas);
			response.getWriter().write(cantCategorias + ";" + categorias);
			break;
		case "ConnectCargaMasivaMateriales":
			// Ejecuta el metodo phyton
			//System.out.println("ConnectCargaMasivaMateriales: control: " + puedo_procesar_csv);
			
			String usuario = u.getIdUsuario();
			try {
				String ruta_py = request.getRealPath("/WEB-INF/utils/csvread.py");
				String ruta_csv = "E:\\apache-tomcat-7.0.109-windows-x64\\apache-tomcat-7.0.109\\webapps\\PCM\\cache_csv\\cache_csv_m.csv";
				String[] cmd = new String[] { "E:\\python\\Python39\\python.exe", ruta_py, ruta_csv, usuario };
				process = Runtime.getRuntime().exec(cmd);
				//Lee el error generado en python
			BufferedReader in = new BufferedReader( new InputStreamReader(this.process.getInputStream()));
	        StringBuilder buffer = new StringBuilder();  
		        String line = null;
		        while ((line = in.readLine()) != null){           
		            buffer.append(line);
		        }
		        int exitCode = this.process.waitFor();
		        System.out.println("Value is: "+buffer.toString());                
		        System.out.println("Process exit value:"+exitCode);        
		        in.close();
			///---

			System.out.println("Aleta de phyton es:  " +buffer.toString());
			response.getWriter().write("OK" +"$"+ buffer.toString());
			
		} catch (Exception e) {
			System.out.println(e.getMessage() + "");
			response.getWriter().write("FALSE");
		}
			
			break;
		case "ConnectMaterialesPlataforma"://ejecuta el codigo phyton
			System.out.println("Entre a java, connect");
			 usuario = u.getIdUsuario();
						try {
							String ruta_py = request.getRealPath("/WEB-INF/utils/csvreadMP.py"); //nombre del archivo de phyton
							String ruta_csv = "E:\\apache-tomcat-7.0.109-windows-x64\\apache-tomcat-7.0.109\\webapps\\PCM\\cache_csv\\cache_csv_mp.csv";
							String[] cmd = new String[] { "E:\\python\\Python39\\python.exe", ruta_py, ruta_csv, usuario };
							process = Runtime.getRuntime().exec(cmd);
			//Lee el error generado en python
						BufferedReader in = new BufferedReader( new InputStreamReader(this.process.getInputStream()));
				        StringBuilder buffer = new StringBuilder();  
					        String line = null;
					        while ((line = in.readLine()) != null){           
					            buffer.append(line);
					        }
					        int exitCode = this.process.waitFor();
					        System.out.println("Respuesta de phyton: "+buffer.toString());                
					        System.out.println("Error: Process exit value:"+exitCode);        
					        in.close();
						///---
					        //VAMOS A MADAR UN CORREO
					        String correos;
							usuarioDAO udao = new usuarioDAO();
							// Vamos a mandar el correo
							correos = udao.alertas(); // Regresa todos los correos los cuales tengo que mandar
							correos = correos.substring(1, correos.length());// Elimino el primer caracter
							String usuariosAlertas[] = correos.split(","); // Separo por
							// Se hace un for para mandar el mismo correo a diferentes personas
							for (int j = 0; j < usuariosAlertas.length; j++) {
								String titulo = "ALTAS PCM";
								String cuerpo = "Buen día, <br>"
										+ "Se informa que algunos materiales de PCM han sufrido un cambio, se anexa la información: <br>"
										+ buffer.toString();
								String usuarioCorreo = usuariosAlertas[j];
								Mail mailMr = new Mail(usuarioCorreo, titulo, cuerpo, "gmail");
								String errorMr = mailMr.send();

								if (errorMr.equals("")) {
									System.out.println("Envio Correcto");
								} else {
									System.out.println("Envio incorrecto");
								}

							}
						System.out.println("Aleta de phyton es:  " +buffer.toString());
						response.getWriter().write("OK" +"$"+ buffer.toString());
					} catch (Exception e) {
						System.out.println(e.getMessage() + "hubo un error");
						response.getWriter().write("FALSE");
					}
						
						break;
		case "ConnectMaterialesBaja"://ejecuta el codigo phyton
			 usuario = u.getIdUsuario();
						try {
							String ruta_py = request.getRealPath("/WEB-INF/utils/csvreadMPB.py"); //nombre del archivo de phyton
							String ruta_csv = "E:\\apache-tomcat-7.0.109-windows-x64\\apache-tomcat-7.0.109\\webapps\\PCM\\cache_csv\\cache_csv_mpb.csv";
							String[] cmd = new String[] { "E:\\python\\Python39\\python.exe", ruta_py, ruta_csv, usuario };
							process = Runtime.getRuntime().exec(cmd);
			//Lee el error generado en python
						BufferedReader in = new BufferedReader( new InputStreamReader(this.process.getInputStream()));
				        StringBuilder buffer = new StringBuilder();  
					        String line = null;
					        while ((line = in.readLine()) != null){           
					            buffer.append(line);
					        }
					        int exitCode = this.process.waitFor();
					        System.out.println("Respuesta de phyton: "+buffer.toString());                
					        System.out.println("Error: Process exit value:"+exitCode);        
					        in.close();
						///---
					        //VAMOS A MADAR UN CORREO
					        String correos;
							usuarioDAO udao = new usuarioDAO();
							// Vamos a mandar el correo
							correos = udao.alertas(); // Regresa todos los correos los cuales tengo que mandar
							correos = correos.substring(1, correos.length());// Elimino el primer caracter
							String usuariosAlertas[] = correos.split(","); // Separo por
							// Se hace un for para mandar el mismo correo a diferentes personas
							for (int j = 0; j < usuariosAlertas.length; j++) {
								String titulo = "BAJAS PCM";
								String cuerpo = "Buen día, <br>"
										+ "Se informa que algunos materiales de PCM han sufrido un cambio, se anexa la información: <br>"
										+ buffer.toString();
								String usuarioCorreo = usuariosAlertas[j];
								Mail mailMr = new Mail(usuarioCorreo, titulo, cuerpo, "gmail");
								String errorMr = mailMr.send();

								if (errorMr.equals("")) {
									System.out.println("Envio Correcto");
								} else {
									System.out.println("Envio incorrecto");
								}

							}
						System.out.println("Aleta de phyton es:  " +buffer.toString());
						response.getWriter().write("OK" +"$"+ buffer.toString());
					} catch (Exception e) {
						System.out.println(e.getMessage() + "");
						response.getWriter().write("FALSE");
					}
						
						break;
		case "ConnectCargaMasivaDC":
			// Ejecuta el metodo phyton
			usuario = u.getIdUsuario();
			try {				
				String ruta_py = request.getRealPath("/WEB-INF/utils/csvDC.py");
				String ruta_csv = "E:\\apache-tomcat-7.0.109-windows-x64\\apache-tomcat-7.0.109\\webapps\\PCM\\cache_csv\\cache_csv_dc.csv";
				String[] cmd = new String[] {"E:\\python\\Python39\\python.exe", ruta_py, ruta_csv, usuario };
				process = Runtime.getRuntime().exec(cmd);
				///---	para leer el error
				BufferedReader in = new BufferedReader( new InputStreamReader(this.process.getInputStream()));
		        StringBuilder buffer = new StringBuilder();  
			        String line = null;
			        while ((line = in.readLine()) != null){           
			            buffer.append(line);
			        }
			        int exitCode = this.process.waitFor();
			        System.out.println("Value is: "+buffer.toString());                
			        System.out.println("Process exit value:"+exitCode);        
			        in.close();
				///---
				System.out.println("Aleta de phyton es:  " +buffer.toString());
				response.getWriter().write("OK" +"$"+ buffer.toString());
			} catch (Exception e) {
				System.out.println(e.getMessage() + "");
				response.getWriter().write("FALSE");
			}

			break;
		case "consultaRutas": // Consulta las rutas de img de un material en especifico
			idMaterial = request.getParameter("idMaterial");
			String descripcionComercial = pdao.descripcionComercial(idMaterial);
			// System.out.println("DescripcionComercial" + descripcionComercial);
			String ruta = pdao.consultaRutas(idMaterial);
			response.getWriter().write(descripcionComercial + "|" + ruta);
			break;
		case "descargaImg": // Se lleva todas las rutas de descarga para ajax, caso 1, descarga de imagen
			// por numero de material
			String materiales = request.getParameter("materiales");
			// Recibo todos los materiales, 1,2,3,4,5,6
			// System.out.println("Recibi:"+ materiales );
			String material1[] = materiales.split(",");
			String rutas = "";
			String aux = "";
			String error = "";
			for (int i = 0; i < material1.length; i++) {
				aux = pdao.consultaRutas(material1[i]);
				// System.out.println("Entre al for, posicion:" + i+" valor "+material1[i]+"
				// ruta: " + aux);
				if (aux != "") {
					rutas += "|" + aux;
				} else {
					error += "," + material1[i];

				}

			}
			// System.out.println("error--- "+error+" rutas:---- "+rutas);
			response.getWriter().write(error + "|" + rutas);// Mandamos el valor de la variable i
			break;
		case "descargaImg2": // Se lleva todas las rutas de descarga para ajax, caso 2, descarga de imagen
								// por descripcion
			String nombreImg = request.getParameter("nombreImg"); // 10_1_app.jpg
			ruta = pdao.consultaImg(nombreImg);
			response.getWriter().write(ruta);
			break;
		case "espera":// Este case funciona para controlar el modal de espera en el proceso de carga
						// de imagenes
			if (cargaMasivaImagenes == true) {
				response.getWriter().write("si");
				System.out.println("Todo bien, deberia marcar true: " + cargaMasivaImagenes);// error
			} else {
				System.out.println("Se ocaciono un problema con la variable:" + cargaMasivaImagenes);// error
				response.getWriter().write("no");
			}

			break;
		case "idMaterial": // Se lleva todos los id materiales para la busqueda
			// por descripcion
			String allMateriales = pdao.allIdMat();
			allMateriales = allMateriales.substring(0, allMateriales.length() - 1);
			response.getWriter().write(allMateriales);
			break;
		case "descripcionMaterial": // Se lleva todos las descripciones para la busqueda
			// por descripcion
			String allDesc = pdao.allDesMat();
			allDesc = allDesc.substring(0, allDesc.length() - 1);
			response.getWriter().write(allDesc);
			break;
		case "categoria": // Se lleva todos las descripciones para la busqueda
			// por descripcion
			String allCategoria;
			allCategoria = pdao.allCategoria();
			allCategoria = allCategoria.substring(0, allCategoria.length() - 1);
			response.getWriter().write(allCategoria);
			break;
		case "proveedor": // Se lleva todos las descripciones para la busqueda
			// por descripcion
			String allProveedor;
			allProveedor = pdao.allProveedor();
			allProveedor = allProveedor.substring(0, allProveedor.length() - 1);
			response.getWriter().write(allProveedor);
			break;
		case "filtrado": // Este filtrado se ocupa para la consulta general
			pdao = new productoDAO();
			idMaterial = request.getParameter("idMaterial");
			String descripcion = request.getParameter("descripcion");
			String familia = request.getParameter("familia");
			String categoria = request.getParameter("categoria");
			String proveedor = request.getParameter("proveedor");
			String plataforma = request.getParameter("plataforma");
			String estatus = request.getParameter("estatus");
			int pagina = 0;
			try {
				pagina = Integer.parseInt(request.getParameter("pagina"));
			} catch (Exception e) {
				pagina = 1;
			}

			List<producto> datos1 = pdao.filtrado(pagina, idMaterial, descripcion, estatus, categoria, familia,
					proveedor, plataforma);
			// response.getWriter().write();
			// System.out.println("LA RESPUESTA ES:"+
			// datos1.get(0).getDescripcionMaterial());
			// respuesta con un json, para que javascript lo tome como un javascript object
			// googleson gson
			String json = "";
			json = new Gson().toJson(datos1);

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			try {
				// damos la respuesta al jsp
				response.getWriter().write(json);
			} catch (IOException e) {
				e.printStackTrace();
			}

			url = "Comunes/AdministracionMateriales.jsp";

			break;
		case "filtradoImg": // Este filtrado se ocupa para la consulta general
			pdao = new productoDAO();
			idMaterial = request.getParameter("idMaterial");
			descripcion = request.getParameter("descripcion");
			familia = request.getParameter("familia");
			categoria = request.getParameter("categoria");
			proveedor = request.getParameter("proveedor");
			plataforma = request.getParameter("plataforma");
			estatus = request.getParameter("estatus");
			pagina = 0;
			try {
				pagina = Integer.parseInt(request.getParameter("pagina"));
			} catch (Exception e) {
				pagina = 1;
			}
			datos1 = pdao.filtradoImg(pagina, idMaterial, descripcion, categoria, familia, proveedor);
			// response.getWriter().write();
			// System.out.println("LA RESPUESTA ES:"+
			// datos1.get(0).getDescripcionMaterial());
			// respuesta con un json, para que javascript lo tome como un javascript object
			// googleson gson
			json = "";
			json = new Gson().toJson(datos1);

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			try {
				// damos la respuesta al jsp
				response.getWriter().write(json);
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (request.getSession().getAttribute("usuario") == null) {
				// no existe una sesion anterior
				url = "/Comunes/Login.jsp";

			}

			url = "Comunes/AdministracionImagenes.jsp";

			break;
//		case "modificar"://Este medodo se utiliza cuando el usuario no es admin
//			System.out.println("Entre a Modificar");
//			String idMaterialEditar = request.getParameter("idMaterialEditar");
//			//json
//			//idmaterial
//			//gramaje
//			//
//			String gramajeEditar = request.getParameter("gramajeEditar");
//			String descripcionComercialEditar = request.getParameter("descripcionComercialE");
//			usuario = u.getIdUsuario();
//			System.out.println(" " +idMaterialEditar +  gramajeEditar + descripcionComercialEditar);
//
//			// Modificar los datos base
//			pdao.materialModificar(idMaterialEditar, usuario, descripcionComercialEditar, gramajeEditar);
//			// Modificar los atributos seleccionados
//			String atributoEditar[] = request.getParameterValues("checks");// Texto,ALGO
//			if (atributoEditar != null && atributoEditar.length != 0) {
//				for (int i = 0; i < atributoEditar.length; i++) {
//					// aqui insertamos el atributo en la familia con id atributo[i]
//					// System.out.println("El valor del atributo es: " + atributoEditar[i]);
//					String buscar = "edit" + atributoEditar[i]; // inputTexto
//					 descripcion = request.getParameter(buscar);
//					// Saber si ya existia el atributo
//					String auxDes = pdao.checarDescripcionAtributo(idMaterialEditar, atributoEditar[i]);
//					if (auxDes == "") {
//						// no encontro nada, lo puedo registrar normal
//						pdao.materialAtributoNuevo(idMaterialEditar, descripcion, atributoEditar[i], usuario);
//					} else {// Si encontro, voy a editar la descripcion
//						pdao.updateMatAtributo(idMaterialEditar, atributoEditar[i], descripcion);
//					}
//					// System.out.println("el usuario escribio: " + descripcion);
//				}
//			}
//			// Modificar los atributos no seleccionados
//			String atributoEditarC[] = request.getParameterValues("checksOff");// Texto,ALGO
//			if (atributoEditarC != null && atributoEditarC.length != 0) {
//				for (int i = 0; i < atributoEditarC.length; i++) {
//					// aqui insertamos el atributo en la familia con id atributo[i]
//					// System.out.println("El valor del atributo es: " + atributoEditar[i]);
//					String buscar = "edit" + atributoEditarC[i]; // inputTexto
//					 descripcion = request.getParameter(buscar);
//
//					// Saber si ya existia el atributo
//					String auxDes = pdao.checarDescripcionAtributo(idMaterialEditar, atributoEditarC[i]);
//					if (auxDes == "") {
//						// no encontro nada, lo puedo registrar normal
//						pdao.materialAtributoNuevo(idMaterialEditar, descripcion, atributoEditarC[i], usuario);
//					} else {// Si encontro, voy a editar la descripcion
//
//						pdao.updateMatAtributo(idMaterialEditar, atributoEditarC[i], descripcion);
//					}
//
//					// System.out.println("el usuario escribio: " + descripcion);
//
//				}
//			}
//
////Imagenes que se eliminaron
//			String imgElim = (request.getParameter("imgElim")); // 10_1_app.jpg,
//			if (!imgElim.equals("")) {
//				imgElim = imgElim.substring(0, imgElim.length() - 1); // la ultima coma
//				String[] imagenesEliminadas = imgElim.split(",");
//				if (imagenesEliminadas != null) {
//					for (int j = 0; j < imagenesEliminadas.length; j++) {
//						String auxImg = imagenesEliminadas[j];
//						// auxImg = auxImg.substring(0, auxImg.length() - 4);
//						pdao.eliminar_imagen(auxImg);
//						// Eliminar la imagen de la carpeta
////						 ruta = "E:\\apache-tomcat-7.0.109-windows-x64\\apache-tomcat-7.0.109\\webapps\\PCM\\ImagenesBD\\"
////								+ auxImg;
////						File imagen = new File(ruta);
////						FileInputStream readImage = new FileInputStream(imagen);
////						readImage.close();
////						imagen.delete();
//						try {
//							System.out.println("Eliminamos la imagen");
//							File imagen = new File(
//								"E:\\apache-tomcat-7.0.109-windows-x64\\apache-tomcat-7.0.109\\webapps\\PCM\\ImagenesBD\\"
//									+ auxImg);
//							FileInputStream readImage = new FileInputStream(imagen);
//							readImage.close();
//							
//							if(imagen.delete())
//					        {
//					            System.out.println("File deleted successfully");
//					        }
//					        else
//					        {
//					            System.out.println("Failed to delete the file");
//					        }
//						} catch (Exception e) {
//							System.out.println("Error al eliminar img" + e.getMessage());
//						}
//						// Editar el campo rutas en la bd
//						pdao.rutasImagenes(idMaterialEditar);
//
//					}
//				}
//			}
//
////Imagenes que se agregaron
//			int cantidadImagenesInt = Integer.parseInt(request.getParameter("cantImagenE"));
//			int auxCant = pdao.cuantasImagenes(idMaterialEditar);
//			cantidadImagenesInt = (cantidadImagenesInt - auxCant);
//			// System.out.println("CUANTAS VECES DEBO ENTRAR " + cantidadImagenesInt);
//			if (cantidadImagenesInt > 0) {
//				InputStream[] imagenes = new InputStream[cantidadImagenesInt];
//
//				for (int i = 0; i < cantidadImagenesInt; i++) {
//					try {
//						Part Documento = request.getPart("evidenciaE" + (i + 1));
//						imagenes[i] = Documento.getInputStream();
//						String nombreimagen = request.getParameter("nombreE" + (i + 1));// 1_1_app.jpg
//
//						if (nombreimagen.contains(idMaterialEditar)) { // Si la imagen pertenece al material
//							usuario = u.getIdUsuario();
//							pdao.subir_imagen(idMaterialEditar, nombreimagen, usuario);
//							File nf = new File(
//									"E:\\apache-tomcat-7.0.109-windows-x64\\apache-tomcat-7.0.109\\webapps\\PCM\\ImagenesBD\\"
//											+ nombreimagen);// crearemos un file//4
//							pdao.copyInputStreamToFile(imagenes[i], nf);
//
//						} else {
//							//request.setAttribute("error", "La imagen no pertenece al material.");
//
//						}
//
//					} catch (IOException io) {
//						System.out.println("Error al subir img: "+ io);
//					}
//				}
//			}
//
//			// CAMBIAR ESTATUS
//			pdao.cambiarEstatus("P", idMaterialEditar, usuario);
//			// ACTUALIZAR HISTORIAL
//			pdao.historial(idMaterialEditar, "P", usuario);
//			
//			response.setContentType("application/json");
//			response.setCharacterEncoding("UTF-8");
//			String js = "{ \"respuesta\": \"listo\"}";
//			JsonObject jsonObject = new JsonParser().parse(js).getAsJsonObject();
//		      
//		    
//			//url = "/producto?op=Listar&pagina=1";
//			response.getWriter().write(jsonObject.toString());
//			
//
//			break;
		default:
			doGet(request, response);
			break;

		}
	}
}

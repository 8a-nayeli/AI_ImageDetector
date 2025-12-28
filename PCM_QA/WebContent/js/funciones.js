//const { forEach } = require("core-js/core/array");
function reporteGenerico() {
	document.getElementById("reporteGenerico").submit();
}
//obtener los valores ingresados de los elementos de un input
function showdatafileRegistro(inputID, inputTextID, inputTextID) {
	//	console.log({idinput,inputText});
	var input = document.getElementById(inputID); //input que carga la imagen
	var nombre = "";
	//nombre = input.files[0].name.replace("." + input.files[0].type.replace("image/", ""), "");
	//nombre = input.file.name.replace(".jpg" ,""); //aqui mero
	var nombre = input.files[0].name;
	//nombre = nombre.replace(".jpg", ""); //aqui merof
	//console.log(nombre);
	document.getElementById(inputTextID).value = nombre;
	//document.getElementById(inputText).value = nombre;
}

function showdatafile(idinput) { //Cargas masivas
	var input = document.getElementById(idinput);
	var i = 0;
	var cantimgs = input.files.length;
	var nombre = "";
	var divimagenes = document.getElementById("cargaImagenes");
	var imagenes = input.files;
	while (i < cantimgs) {
		var imagen = imagenes[i];
		//console.log("imagenes[i] " +imagenes[i]);
		//	nombre = input.files.item(i).name.replace("." + input.files.item(i).type.replace("image/", ""), ""); //aqui mero
		nombre = input.files.item(i).name; //aqui mero
		//console.log("Nombre: " + nombre);

		var nodo = "";
		nodo += "<div hidden id=@imagen" + i + "@>" +
			"<input type=@text@ name=@numimagen" + i + "@ value=@" + nombre + "@ >" +
			"<img id=@imgfile" + i + "@ src=@@ style=@max-width:10%;max-height:10%@ >" +
			"</div ";

		nodo = nodo.replaceAll('@', '"');
		nodo = nodo.replaceAll('#', "'");
		divimagenes.innerHTML += nodo;
		//	var aux = "imgfile" + i;
		//	previewFileIMG(aux, imagen);
		i = i + 1;
	}
	i = 0;
}

/*function limpiarCM() {//Borra los hijos
//	console.log('Entre a limpiar de CM, modificaciones');
	var hijos = 0;
	hijos = document.getElementById("cargaImagenes").children.length;
	//var i = 0;
	
	for(var i=0; i<hijos; i++){ //conocer la cantidad de hijos que tiene el div para poder elimminar sus hijos 
			if (document.getElementById("cargaImagenes").children[0]) {
			document.getElementById("cargaImagenes").children[0].remove();
		}
	}
	document.getElementById("cargaMasivaImagen").value = "";

}*/

//mostrar imagenes en un IMG mediante el file de imagen
/*
function previewFileIMG(imgID, file) {
	var preview = document.getElementById(imgID);
	var nombre = preview.name.replace(".jpg", ""); //aqui mero
	//console.log(nombre);

	var reader = new FileReader();

	reader.onload = (function () {
		preview.src = reader.result;
	})

	if (file) {
		reader.readAsDataURL(file);
	} else {
		preview.src = "";
	}
}
*/


function mostrar_atributos(idcheck, idbox) {
	var aux = document.getElementById(idcheck).checked;
	if ((!aux) == false) {
		$('#' + idbox).show();
		document.getElementById(idbox).value = "";
	} else {
		$('#' + idbox).hide();
		document.getElementById(idbox).value = "";

	}

}

function mostrar_atributos_editar(bool, idcheck, idbox) {
	if (bool) {
		$('#' + idbox).show();
		document.getElementById(idcheck).checked = true;
		document.getElementById(idbox).value = "";
	} else {
		$('#' + idbox).hide();
		document.getElementById(idcheck).checked = false;
		document.getElementById(idbox).value = "";
	}

}


function limpiarModificacion() {
	limpiarForm('materalesImagenes');
	limpiarForm('alertaEdicion');

	/*var alerta= document.getElementById('alertaEdicion');
	var	alert="";
	alerta.innerHTML = alert;*/

}

function limpiarForm(idPlacer) {

	var element = document.getElementById(idPlacer);
	while (element.firstChild) {
		element.removeChild(element.firstChild);
	}
	//console.log("Todo bien" + idPlacer);

}

function limpiarFormAdmin(idPlacer, idPlacer2) {
	limpiarForm(idPlacer);
	var element = document.getElementById(idPlacer2);
	while (element.firstChild) {
		element.removeChild(element.firstChild);
	}


}

function limpiarFormUsuario() {
	document.getElementById("correo_nuevo").value = "";
	document.getElementById("nombre_nuevo").value = "";
	document.getElementById("rol").value = "0";
	document.getElementById("password_nuevo").value = "";
	document.getElementById("id_permiso").value = "";
	document.getElementById("encriptacion").value = "";
}

function limpiarFormPlataforma() {
	document.getElementById("plataformaNueva").value = "";
}

function limpiarFormFamilia() {
	document.getElementById("idFamilia").value = "";
}

function limpiarFormMarca() {
	document.getElementById("marcaNueva").value = "";
}

function generar_credenciales() {
	var variable = Math.floor(Math.random() * (100 - 0)) + 0
	var nombre;
	var correo = document.getElementById("correo_nuevo").value;
	nombre = (correo + variable);
	document.getElementById("nombre_nuevo").value = nombre;
	var long = 8;
	var caracteres = "abcdefghijkmnpqrtuvwxyzABCDEFGHIJKLMNPQRTUVWXYZ2346789";
	var pass = "";
	for (i = 0; i < long; i++) {
		pass += caracteres.charAt(Math.floor(Math.random() * caracteres.length));
	}
	document.getElementById("password_nuevo").value = pass;
	var aux = nombre + pass;
	var pass_encriptada = CryptoJS.MD5(aux);
	document.getElementById("encriptacion").value = pass_encriptada;



}



function sin_caracteres_especiales(e) {
	tecla = (document.all) ? e.keyCode : e.which;

	//Tecla de retroceso para borrar, siempre la permite
	if (tecla == 8) {
		return true;
	}

	// Patron de entrada, en este caso solo acepta numeros y letras
	patron = /[A-Za-z]/;
	tecla_final = String.fromCharCode(tecla);
	return patron.test(tecla_final);
	//fin  de caracteres 

}

function sinEspacios(e) {
	tecla = (document.all) ? e.keyCode : e.which;

	//Tecla de retroceso para borrar, siempre la permite
	if (tecla == 8) {
		return true;
	}

	// Patron de entrada, en este caso solo acepta numeros y letras
	patron = /[A-Za-z-0-9]/ //Caracteres validos
	tecla_final = String.fromCharCode(tecla);
	return patron.test(tecla_final);
	//fin  de caracteres 

}



function solo_numeros(e, identificador, max) {
	tecla = (document.all) ? e.keyCode : e.which;

	//Tecla de retroceso para borrar, siempre la permite
	if (tecla == 8) {
		return true;
	}

	// Patron de entrada, en este caso solo acepta numeros y letras
	patron = /[0-9]/;
	tecla_final = String.fromCharCode(tecla);

	caracteresMax(identificador, max);
	return patron.test(tecla_final);
	//fin  de caracteres 



}


function redireccionar() {
	//console.log("entro aqui");
	location.href = "/PCM_QA/usuario?op=Salir";
}

function validar_datos() {
	var contrasenna = document.getElementById("nuevo_pass").value;
	var correo = document.getElementById("correo_confirmacion").value;

	if (contrasenna.length >= 8) {
		var mayuscula = false;
		var minuscula = false;
		var numero = false;
		var caracter_raro = false;

		for (var i = 0; i < contrasenna.length; i++) {
			if (contrasenna.charCodeAt(i) >= 65 && contrasenna.charCodeAt(i) <= 90) {
				mayuscula = true;
			} else if (contrasenna.charCodeAt(i) >= 97 && contrasenna.charCodeAt(i) <= 122) {
				minuscula = true;
			} else if (contrasenna.charCodeAt(i) >= 48 && contrasenna.charCodeAt(i) <= 57) {
				numero = true;
			} else {
				caracter_raro = true;
			}
		}
		if (mayuscula == true && minuscula == true && caracter_raro == true && numero == true) {

			var pass1 = document.getElementById("nuevo_pass").value;
			var pass2 = document.getElementById("nuevo_pass_confirmacion").value;
			if (pass1 != pass2) {
				Swal.fire('', 'El password no coinciden', 'error');

			} else {
				var usuario = document.getElementById("nombre_usuario").value;
				var passViejo = document.getElementById("pass_confirmacion").value;
				var user = document.getElementById("nombre_usuario").value;
				var passNuevo = pass1;
				var aux1 = user + passViejo;
				//alert(user+" "+passViejo);
				passViejo = CryptoJS.MD5(aux1);
				//alert(user+" "+passViejo+" "+ aux1);
				var aux2 = user + passNuevo;
				passNuevo = CryptoJS.MD5(aux2);
				document.getElementById("encriptacion_pass_actual").value = passViejo;
				document.getElementById("pass_encriptada_nueva").value = passNuevo;
				passViejo = document.getElementById("encriptacion_pass_actual").value;
				passNuevo = document.getElementById("pass_encriptada_nueva").value;
				espera();
				$.ajax({
					type: 'POST',
					data: {
						op: 'AJAX',
						user: usuario,
						passwordViejo: passViejo,
						passwordNuevo: passNuevo,
						pass: pass2,
						correo: correo
					},
					url: 'usuario',
					success: function(res) {
						if (res == "verdadero") {
							$('#modalPass').modal('hide');
							$('#modalRedireccionar').modal('show');
							setTimeout('redireccionar()', 5000);
							Swal.fire('', 'Password cambiado correctamente', 'success');

						} else {
							Swal.fire('', 'Error al intentar cambiar el password, verifica el password actual', 'error');
						}
					}
				});
			}

			return true;
		} else {
			Swal.fire('', 'El password debe contener: minusculas, mayusculas, nnmeros y simbolos', 'error');
			return false;
		}
	} else {
		Swal.fire('', 'La longitud del password debe ser mayor a 7 caracteres', 'error');

		return false;
	}






}


function registro_nuevo_user() {
	var correov = document.getElementById("correo_nuevo").value;
	var rol = document.getElementById("rol").value;

	if (rol == "0" && correov == "") {
		if (rol == "0") {
			Swal.fire('', 'Seleccione un rol valido', 'info');
		}
		if (correov == "") {
			Swal.fire('', 'Ingrese un correo', 'info');

		}


	} else {
		generar_credenciales();
	}
	var nombre = document.getElementById("nombre_nuevo").value;
	if (nombre != "" && rol != "0" && correov != "") {

		var correo = correov + "@merza.com";
		var password = document.getElementById("encriptacion").value;
		var pass1 = document.getElementById("password_nuevo").value;
		Swal.fire({
			text: 'El correo es correcto?' + correo,
			icon: 'warning',
			showDenyButton: true,
			showCancelButton: false,
			confirmButtonText: 'SI',
			confirmButtonColor: '#1570DE',
			denyButtonColor: '#DE3815',
			denyButtonText: 'NO',
		}).then((result) => {
			if (result.isConfirmed) {
				let timerInterval
				espera();
				$.ajax({
					type: 'POST',
					data: {
						op: 'REGISTRO',
						name: nombre,
						email: correo,
						pass: password,
						rol: rol,
						credencial: pass1
					},
					url: 'usuario',
					success: function(res) {
						if (res == "true") {
							$('#modalNuevoUser').modal('hide');
							document.getElementById("correo_nuevo").value = "";
							document.getElementById("rol").value = "";
							document.getElementById("nombre_nuevo").value = "";
							document.getElementById("password_nuevo").value = "";
							document.getElementById("id_permiso").value = "";
							document.getElementById("encriptacion").value = "";
							location.href = "/PCM_QA/usuario?op=Listar&pagina=1";

						} else {
							Swal.fire('', 'El correo ya tiene asignado un usuario, intenta con otro', 'error');
							document.getElementById("correo_nuevo").value = "";
							document.getElementById("rol").value = "";
							document.getElementById("nombre_nuevo").value = "";
							document.getElementById("password_nuevo").value = "";
							document.getElementById("id_permiso").value = "";
							document.getElementById("encriptacion").value = "";


						}
					}
				});



			} else if (result.isDenied) {
				Swal.fire('', 'Modifica el correo', 'info')
			}
		})
	}

}


function mostrarContrasena(id) {
	var tipo = document.getElementById(id);
	if (tipo.type == "password") {
		tipo.type = "text";
	} else {
		tipo.type = "password";
	}
}


function editar_Empleado(idEmpleado) {
	$.ajax({
		type: 'POST',
		data: {
			op: 'Editar',
			id: idEmpleado
		},
		url: 'usuario',
		success: function(res) {
			//console.log(res);
			var valor = res.split(" ");
			$('#modalModificacionUser').modal('show');
			document.getElementById('id_usuario_editar').value = valor[0];
			document.getElementById('nombre_usuario_editar').value = valor[1];
			document.getElementById('correo_editar').value = valor[2];
			var rol = valor[3];

			document.getElementById('rol_editar').selectedIndex = rol - 1;
			var estatus = valor[4];

			if (estatus == "A") {
				document.getElementById('estatus_editar').selectedIndex = "0";
			} else {
				document.getElementById('estatus_editar').selectedIndex = "1";
			}
			var alertas = valor[5];

			if (alertas == "1") {
				document.getElementById('alertas').selectedIndex = "0"; //Activadas
			} else {
				document.getElementById('alertas').selectedIndex = "1"; //Desactivadas
			}


		}
	});
}

function editarPlataforma(idPlataforma) {
	$.ajax({
		type: 'POST',
		data: {
			op: 'Editar',
			id: idPlataforma
		},
		url: 'plataforma',
		success: function(res) {
			//console.log(res);
			var valor = res.split("$");
			$('#modalModificacionPlataforma').modal('show');
			document.getElementById('idPlataforma_editar').value = idPlataforma;
			document.getElementById('nombre_plataforma_editar').value = valor[0];
			var estatus = valor[1];
			if (estatus == "A") {
				document.getElementById('estatus_editar').selectedIndex = "0";
			} else {
				document.getElementById('estatus_editar').selectedIndex = "1";
			}
			var tipo = valor[2];
			if (tipo == "dis") {
				document.getElementById('tipoPlataformaE').selectedIndex = "0";
			} else {
				document.getElementById('tipoPlataformaE').selectedIndex = "1";
			}

		}
	});
}

function editarMarca(idMarca) {
	$.ajax({
		type: 'POST',
		data: {
			op: 'Editar',
			id: idMarca
		},
		url: 'marca',
		success: function(res) {
			var valor = res.split("$");
			$('#modalModificacionMarca').modal('show');
			document.getElementById('idMarca_editar').value = idMarca;
			document.getElementById('nombre_marca_editar').value = valor[0];
			var estatus = valor[1];
			if (estatus == "A") {
				document.getElementById('estatus_editar').selectedIndex = "0";
			} else {
				document.getElementById('estatus_editar').selectedIndex = "1";
			}

		}
	});
}




function previewFile(imgID, inputID, inputTextID) {
	var preview = document.getElementById(imgID);
	var file = document.getElementById(inputID).files[0];
	var reader = new FileReader();

	reader.onloadend = function() {
		preview.src = reader.result;
	}

	if (file) {
		reader.readAsDataURL(file);
	} else {
		preview.src = "";
	}

	showdatafileRegistro(inputID, inputTextID, inputTextID);
}





function login() {
	var nombre = document.getElementById("nombre_usuario").value;

	//funcion startervariables para invocar variables necesarias a lo largo de la vida de la app
	starterVariables();

	$.ajax({
		type: 'POST',
		data: {
			op: 'LOGIN',
			nombre: nombre,
			estatus: estatus
		},
		url: 'usuario',
		success: function(res) {
			if (res == "verdadero") {

				if (estatus == "I") {
					Swal.fire('', 'Usuario inactivo, informa al administrador', 'info');

				}
			} else {
				Swal.fire('', 'El usuario no existe', 'error');
			}
		}
	});
}

function encriptar() {
	var pass = "";
	user = document.getElementById("nombre_usuario").value;
	pass = document.getElementById("password").value;
	var aux = (user + pass);
	var pass_encriptada = CryptoJS.MD5(aux);
	document.getElementById("encriptacion").value = pass_encriptada;
	//Esta variable se utiliza para controlar el alert que se genera despues de un registro de carga masiva
	//localStorage.congelada = "false";
	//Vamos a agregar el usuario a la master

}



function buscar_material() {
	var material = document.getElementById("numero_material").value;
	var divImagenes = document.getElementById("imagenesAgregadas"); //Donde se colocaran las img que ya esta agregadas en el material
	divImagenes.innerHTML = "";
	var imagenesAgregadas = document.getElementById('cuantasImg');
	imagenesAgregadas.innerHTML = "";
	//console.log("Entre a buscar material.");
	$.ajax({
		type: 'POST',
		data: {
			op: 'MATERIAL',
			material: material
		},
		url: '/PCM_QA/producto',
		success: function(res) {
			//console.log("el valor de res es: " + res);
			switch (res) {
				case "null":
					Swal.fire('', 'El articulo no existe.', 'info');
					document.getElementById("descripcion").value = "";
					document.getElementById("idProveedor").value = "";
					document.getElementById("descripcionProveedor").value = "";
					document.getElementById("idGpoArt").value = "";
					document.getElementById("descripcionGpoArt").value = "";
					document.getElementById("idCategoria").value = "";
					document.getElementById("descripcionCategoria").value = "";
					document.getElementById("ean").value = "";
					document.getElementById("um").value = "";
					document.getElementById("gramaje").value = "";
					document.getElementById("conversionUm").value = "";
					document.getElementById("sustituto").value = "";
					break;
				case "no":
					Swal.fire('', 'El articulo ya se encuentra registrado.', 'info');
					document.getElementById("descripcion").value = "";
					document.getElementById("idProveedor").value = "";
					document.getElementById("descripcionProveedor").value = "";
					document.getElementById("idGpoArt").value = "";
					document.getElementById("descripcionGpoArt").value = "";
					document.getElementById("idCategoria").value = "";
					document.getElementById("descripcionCategoria").value = "";
					document.getElementById("ean").value = "";
					document.getElementById("um").value = "";
					document.getElementById("gramaje").value = "";
					document.getElementById("conversionUm").value = "";
					document.getElementById("sustituto").value = "";
					break;
				default:
					valor = res.split("@");
					document.getElementById("descripcion").value = valor[0];
					document.getElementById("idProveedor").value = valor[1];
					document.getElementById("descripcionProveedor").value = valor[2];
					document.getElementById("idMarca").value = valor[3];
					document.getElementById("descripcionMarca").value = valor[4];
					document.getElementById("idFamilia").value = valor[5];
					document.getElementById("descripcionFamilia").value = valor[6];
					document.getElementById("idGpoArt").value = valor[7];
					document.getElementById("descripcionGpoArt").value = valor[8];
					document.getElementById("idCategoria").value = valor[9];
					document.getElementById("descripcionCategoria").value = valor[10];
					document.getElementById("ean").value = valor[11];
					document.getElementById("um").value = valor[12];
					document.getElementById("conversionUm").value = valor[13];
					//Añadir descripcion 
					document.getElementById("descripcionComercial").value = valor[16];
					if (valor[14] == "null") {
						document.getElementById("sustituto").placeholder = "Sin sustituto...";
					} else {
						document.getElementById("sustituto").value = valor[14];
					}
					//console.log("Sali de buscar material");
					//marcasExistentes();
					//familiasExistentesProducto();
					//atributosFamilia();
					//valor en lugar 15 se dividira
					//console.log("El valor de las rutas es: " + valor[15]);
					if (valor[15] != "") { //si tiene rutas
						//console.log("Entre al if");
						var aux = "";
						aux = valor[15];
						console.log("el valor de aux es " + aux);
						var rutas = aux.split("|");
						//console.log("rutas 0" + rutas[0]);
						//console.log("rutas 0" + rutas[1]);
						//console.log(rutas.lenght);
						//console.log("Tengo rutas"+rutas.lenght);//Conocer el valor de rutas;
						//var a=0;
						for (var a = 0; a < rutas.length; a++) {
							//console.log("Entre al while");
							//console.log("El valor de rutas en el lugar" + a + " es: " + rutas[a]);
							var nombreImg = rutas[a].substring(16, rutas[a].length);
							//console.log("El nombre de img deberia ser: " + nombreImg);
							var imagen = "@" + rutas[a] + "@";
							//console.log("SRC IMG:" + imagen);
							if (rutas[a].includes("_app")) {
								medida = "300 x 300";
							} else {
								medida = "1200 x 1200";
							}
							//console.log("La medida deberia ser: " + medida);

							var nodo = "";
							var cuantas = "";
							if (a == 0) {
								cuantas = "<span class=@badge badge-pill badge-warning@>El material tiene " + rutas.length + " imagenes</span>";
								cuantas = cuantas.replaceAll('@', '"');
								imagenesAgregadas.innerHTML = cuantas;
								document.getElementById('cantImagen').value = rutas.length + 1;
							}
							nodo +=
								"<div class=@col-2@ id=@" + rutas[a] + "@>" +
								"<img id=@imgfile" + i + "@ src=" + imagen + " style=@max-width:50%;max-height:50%@ >" +
								"<a href=" + imagen + " download target=@_blank@ ><i class=@fa fa-download@ style=@font-size: 14px; color:blue@></i></a><br>" +
								"<span class=@badge badge-pill badge-warning@>" + medida + "</span>" +
								"<input readonly class=@border border-white@ type=@text@ name=@numimagen" + i + "@ value=@" + nombreImg + "@>" +
								"</div>";
							nodo = nodo.replaceAll('@', '"');
							divImagenes.innerHTML += nodo;
							//	var aux = "imgfile" + i;
							//	previewFileIMG(aux, imagen);
							//a++;
						}
						//	console.log("Algo pasa dentro del while, no puedo entrar");
					}

					seleccionarFamilia();



			}


		}

	});


}


var i = 1;

function duplicater(iddiv, imagen, evidencia, nombre, cantImagen, eliminar, boton, opcion) {


	var original = document.getElementById(iddiv);
	var clone = original.cloneNode(true);
	var cantImagenes = parseInt(document.getElementById(cantImagen).value);

	//si me clonaste, le agrego el atributo required
	clone.setAttribute("style", "display:show;border-style:solid;border-width:1px;margin-bottom:8px");
	clone.setAttribute("required", "required");

	//aumentar cant imagenes
	cantImagenes = (cantImagenes + 1);
	//cantImagenes= parseInt(cantImagenes);

	clone.id = "duplicater" + i;
	clone.onclick = "duplicater" + i;
	//cambiamos id de los hijos
	clone.children[0].children[0].id = evidencia + i;
	clone.children[0].children[0].name = evidencia + i;
	clone.children[0].children[0].setAttribute("onchange", "previewFile('" + imagen + i + "','" + evidencia + i + "','" + nombre + i + "')");
	clone.children[0].children[0].setAttribute("required", "required");

	clone.children[1].id = nombre + (i);
	clone.children[1].name = nombre + (i);

	clone.children[2].id = imagen + (i);
	clone.children[2].name = imagen + (i);
	clone.children[2].src = "";
	if (clone.children[3] != null) {
		clone.children[3].id = eliminar + i;

		clone.children[3].setAttribute("onclick", "elim('duplicater" + i + "','" + imagen + i + "','" + evidencia + i + "','" + nombre + i + "','" + cantImagen + "','" + eliminar + i + "','" + boton + "')");

	}


	//metemos todo 
	original.parentNode.insertBefore(clone, original.nextSibling);
	i = i + 1;
	//desaparecer boton de agregar imagen

	/*	if (document.getElementById(iddiv) != null) {
			if (i + document.getElementById(iddiv).children.length == (8 + 1)) {
				document.getElementById(boton).style.visibility = 'hidden';
				Swal.fire('Sobrepaso el limite de imagenes', '', 'info');


			} else {
				document.getElementById(boton).style.visibility = 'visible';
			}
		} else {*/
	if (opcion == "registro") {
		if (cantImagenes >= 10) {
			//console.log({cantImagenes});
			document.getElementById(boton).style.visibility = 'hidden';
			Swal.fire('', 'Sobrepaso el limite de imagenes', 'info');
		}
		if (cantImagenes < 10) {
			document.getElementById(boton).style.visibility = 'visible';

		}
	} else {

		if ((cantImagenes + 1) >= 10) {
			//console.log({cantImagenes});
			document.getElementById(boton).style.visibility = 'hidden';
			Swal.fire('', 'Sobrepaso el limite de imagenes', 'info');
		}
		if ((cantImagenes + 1) < 10) {
			document.getElementById(boton).style.visibility = 'visible';

		}

	}
	//	}
	document.getElementById(cantImagen).value = cantImagenes;
}




function olvide_pass() {
	var usuario = document.getElementById("usuario_cambio").value;
	var correo = document.getElementById("email_cambio").value;
	var long = 8;
	var caracteres = "abcdefghijkmnpqrtuvwxyzABCDEFGHIJKLMNPQRTUVWXYZ2346789";
	var pass = "";
	for (i = 0; i < long; i++) {
		pass += caracteres.charAt(Math.floor(Math.random() * caracteres.length));
	}
	//ENCRIPTAR EL NOMBRE Y EL CAMPO DE PASS ALEATORIO PARA ENCRIPATAR LA NUEVA CONTRASEÑA
	document.getElementById("password_nuevo_cambio").value = pass;
	var aux = usuario + pass;
	//alert("USUARIO: " + usuario + " PASS: " + pass);
	var pass_encriptada = CryptoJS.MD5(aux);
	document.getElementById("encriptacion_cambio").value = pass_encriptada;
	//alert("PASS ENCRIPTADA" + pass_encriptada);

}



function mostrarImagen(idelemento, num_imagen, num_material) {
	document.getElementById(idelemento).src = 'IMG?op=Producto&num_imagen=' + num_imagen + '&num_material=' + num_material;
	document.getElementById('eliminarmyImage').setAttribute("onclick", "eliminarImagen('" + num_material + "','" + num_imagen + "')");
	document.getElementById('editarmyImage').setAttribute("onclick", "editarImagen('" + num_material + "','" + num_imagen + "')");
	//llenarFormEdicionImagenes("/producto?op=ModificarImagen&num_imagen="+num_imagen+"&num_material="+num_material);
}

function elim(id, imagen, evidencia, nombre, cantImagen, eliminar, boton) {

	//disminuir las variables (ejemplo1 => ejemplo)
	imagen = imagen.slice(0, -1);
	evidencia = evidencia.slice(0, -1);
	nombre = nombre.slice(0, -1);
	var cantImagenes = parseInt(document.getElementById(cantImagen).value); //valor de un elemento web

	var list = document.getElementById(id);
	list.parentNode.removeChild(list);
	//reducir contador de cantidad de imagenes
	i = i - 1;
	//document.getElementById(cantImagen).value = i;


	//buscar si existe un nodo con id mayor al mio(si soy 2, buscar un 3)
	var duplicater = id.substring(0, 10);
	var num = id.substring(10, 11);

	var numero = (parseInt(num) + 1);

	var res = duplicater + numero; //4
	//console.log(res);

	while (document.getElementById(res) != null) {
		//si existe dismiunir sus datos (evidencia3 por evidencia2,etc)

		//5 cosas
		document.getElementById(res).children[0].children[0].setAttribute("onchange", "previewFile('" + imagen + (numero - 1) + "','" + evidencia + (numero - 1) + "','" + nombre + (numero - 1) + "')");
		document.getElementById(res).children[0].children[0].setAttribute("name", evidencia + (numero - 1));
		document.getElementById(res).children[0].children[0].setAttribute("id", evidencia + (numero - 1));

		document.getElementById(res).children[1].setAttribute("name", nombre + (numero - 1));
		document.getElementById(res).children[1].setAttribute("id", nombre + (numero - 1));

		document.getElementById(res).children[2].setAttribute("name", imagen + (numero - 1));
		document.getElementById(res).children[2].setAttribute("id", imagen + (numero - 1));

		document.getElementById(res).children[3].setAttribute("id", eliminar + (numero - 1));
		document.getElementById(res).children[3].setAttribute("onclick", "elim('duplicater" + (numero - 1) + "','" + imagen + (numero - 1) + "','" + evidencia + (numero - 1) + "','" + nombre + (numero - 1) + "','" + cantImagen + "','" + eliminar + (numero - 1) + "','" + boton + "')");


		document.getElementById(res).id = "duplicater" + (numero - 1);

		num = res.substring(10, 11); //4
		numero = parseInt(num); //4
		//console.log(num+"num");

		res = duplicater + (numero + 1);
		numero = numero + 1;
		//console.log("res"+res);


	}

	//quizas exista el placer
	/*	if (document.getElementById(id) != null) {
			if (i + document.getElementById(id).children.length >= 10) {
				document.getElementById(boton).style.visibility = 'hidden';
				Swal.fire('Sobrepaso el limite de imagenes', '', 'info');

			} else {
				document.getElementById(boton).style.visibility = 'visible';
			}
		} else {*/
	if (cantImagenes >= 10) {
		document.getElementById(boton).style.visibility = 'hidden';
		Swal.fire('', 'Sobrepaso el limite de imagenes', 'info');
	}
	if (cantImagenes < 10) {
		document.getElementById(boton).style.visibility = 'visible';

	}
	/*}*/

	cantImagenes--;

	document.getElementById(cantImagen).value = cantImagenes;


	//si no existe no hacer nada, porque yo era el mas grande
}


/*function getMaterialBuscador(idinput, ida) {
	//esta buscando
	//localStorage['isSearch'] = 'true';

	//localStorage['pagina'] = "1";
	//var paginaINT = parseInt(localStorage['pagina']);

	var material = document.getElementById(idinput).value;
	//localStorage['materials'] = material;

	//decirle a next y previous que sus datos ahora son manejados por lo que encuentre buscador
	if(material != "")
	{
		var aux1 = "/PCM_QA/producto?op=busqueda&pagina=" + (paginaINT + 1) + "&buscador=" + material;
		var aux2 = "/PCM_QA/producto?op=busqueda&pagina=" + (paginaINT - 1) + "&buscador=" + material;
	}else
	{
		var aux1 = "/PCM_QA/producto?op=busqueda&pagina=" + (paginaINT + 1) + "&buscador=";
		var aux2 = "/PCM_QA/producto?op=busqueda&pagina=" + (paginaINT - 1) + "&buscador=";
		
	}
	
	if (document.getElementById('idnext') != null)
		document.getElementById('idnext').setAttribute("href", aux1);
	if (document.getElementById('idprevious') != null)
		document.getElementById('idprevious').setAttribute("href", aux2);

	location.href = "/PCM_QA/producto?op=busqueda&pagina=1&buscador=" + material;
}*/

function buscadorAdminMat(idinput, ida) {
	//esta buscando
	localStorage['isSearch'] = 'true';

	localStorage['pagina'] = "1";
	var paginaINT = parseInt(localStorage['pagina']);

	var material = document.getElementById(idinput).value;
	localStorage['materials'] = material;

	//decirle a next y previous que sus datos ahora son manejados por lo que encuentre buscador

	var aux1 = "/PCM_QA/producto?op=buscadorAdminMat&pagina=" + (paginaINT + 1) + "&buscador=" + material;
	var aux2 = "/PCM_QA/producto?op=buscadorAdminMat&pagina=" + (paginaINT - 1) + "&buscador=" + material;

	if (document.getElementById('idnext') != null)
		document.getElementById('idnext').setAttribute("href", aux1);
	if (document.getElementById('idprevious') != null)
		document.getElementById('idprevious').setAttribute("href", aux2);

	location.href = "/PCM_QA/producto?op=buscadorAdminMat&pagina=1&buscador=" + material;
}


function limpiar() {
	localStorage['materials'] = "";
	localStorage['isSearch'] = "false";
	localStorage['pagina'] = "1";
	isSearch = false;
	location.href = "/PCM_QA/producto?op=Listar&pagina=1";
}

function limpiarMat() {
	localStorage['materials'] = "";
	localStorage['isSearch'] = "false";
	localStorage['pagina'] = "1";
	isSearch = false;
	location.href = "/PCM_QA/producto?op=AdministrarMateriales&pagina=1";
}

/*function isSearching(paginaIN) {
	//console.log('isSearching ejecutado =>' + localStorage['isSearch']);
	localStorage['pagina'] = paginaIN.toString();
	var search = localStorage['isSearch'] || 'defaultValue';
	var material = localStorage['materials'] || 'defaultValue';
	var paginaINT = parseInt(localStorage['pagina']);

	if (search == "true") {
		var aux1 = "/PCM_QA/producto?op=busqueda&pagina=" + (paginaINT + 1) + "&buscador=" + material;
		var aux2 = "/PCM_QA/producto?op=busqueda&pagina=" + (paginaINT - 1) + "&buscador=" + material;

		if (document.getElementById('idnext') != null)
			document.getElementById('idnext').setAttribute("href", aux1);
		if (document.getElementById('idprevious') != null)
			document.getElementById('idprevious').setAttribute("href", aux2);
	}
}*/

//Esta funcion inicializa las variables del sistema 
function starterVariables() {
	//vamos a inicializar variables de tipo localstorage al momento de iniciar sesion, para ya solo utilizarlas
	//paginacion normal = 1, paginacion especial (pendiente,rechazado,etc) = 2
	localStorage.PAGINACION = "1";
	localStorage.FILTRO = "T"
	localStorage.IDMATERIAL = "0"
	localStorage.TIPO = "0"

	localStorage['PAGINACION'] = "1"
	localStorage['FILTRO'] = "T"
	localStorage['IDMATERIAL'] = "0"
	localStorage['TIPO'] = "0"

}

function previewNext(pagina) {
	var pag = localStorage['PAGINACION']
	var filtro = localStorage['FILTRO']
	var idMaterial = localStorage['IDMATERIAL']
	var tipo = localStorage['TIPO']

	if (pag == "1") //no tiene filtro
	{
		//caso normal
		var next_normal = "/PCM_QA/producto?op=Listar&pagina=" + (parseInt(pagina) + 1) + "&selectFiltro=" + filtro;
		var previous_normal = "/PCM_QA/producto?op=Listar&pagina=" + (parseInt(pagina) - 1) + "&selectFiltro=" + filtro;

		//vamos a meterlos en los botones de Next y Previuos
		document.getElementById("idnext").setAttribute("href", next_normal);
		if ($('#idprevious').attr('href') != null) {
			document.getElementById("idprevious").setAttribute("href", previous_normal);
		}

	}

	if (pag == "2") //si tiene filtro
	{
		//caso rechazado/pendiente/aceptado
		var next_especial = "/PCM_QA/producto?op=busquedaEstatus&pagina=" + (parseInt(pagina) + 1) + "&selectFiltro=" + filtro;
		var previous_especial = "/PCM_QA/producto?op=busquedaEstatus&pagina=" + (parseInt(pagina) - 1) + "&selectFiltro=" + filtro;

		//vamos a meterlos en los botones de Next y Previuos
		document.getElementById("idnext").setAttribute("href", next_especial);
		if ($('#idprevious').attr('href') != null) {
			document.getElementById("idprevious").setAttribute("href", previous_especial);
		}
	}

	if (pag == "3") //busqueda por id material
	{
		//caso idmaterila
		var next_mat = "/PCM_QA/producto?op=busqueda&pagina=" + (parseInt(pagina) + 1) + "&buscador=" + idMaterial + "&tipo=" + tipo;
		var previous_mat = "/PCM_QA/producto?op=busqueda&pagina=" + (parseInt(pagina) - 1) + "&buscador=" + idMaterial + "&tipo=" + tipo;

		//vamos a meterlos en los botones de Next y Previuos
		document.getElementById("idnext").setAttribute("href", next_mat);
		if ($('#idprevious').attr('href') != null) {
			document.getElementById("idprevious").setAttribute("href", previous_mat);
		}
	}



}

function previewNextAdmin(pagina) {
	var pag = localStorage['PAGINACION']
	var filtro = localStorage['FILTRO']
	var idMaterial = localStorage['IDMATERIAL']
	var idMaterial = localStorage['TIPO']

	if (pag == "1") //no tiene filtro
	{
		//caso normal
		var next_normal = "/PCM_QA/producto?op=AdministrarMateriales&pagina=" + (parseInt(pagina) + 1) + "&selectFiltroAdmin=" + filtro;
		var previous_normal = "/PCM_QA/producto?op=AdministrarMateriales&pagina=" + (parseInt(pagina) - 1) + "&selectFiltroAdmin=" + filtro;

		//vamos a meterlos en los botones de Next y Previuos
		document.getElementById("idnext").setAttribute("href", next_normal);
		if ($('#idprevious').attr('href') != null) {
			document.getElementById("idprevious").setAttribute("href", previous_normal);
		}

	}

	if (pag == "2") //si tiene filtro
	{
		//caso rechazado/pendiente/aceptado
		var next_especial = "/PCM_QA/producto?op=ESTATUS&pagina=" + (parseInt(pagina) + 1) + "&selectFiltroAdmin=" + filtro;
		var previous_especial = "/PCM_QA/producto?op=ESTATUS&pagina=" + (parseInt(pagina) - 1) + "&selectFiltroAdmin=" + filtro;

		//vamos a meterlos en los botones de Next y Previuos
		document.getElementById("idnext").setAttribute("href", next_especial);
		if ($('#idprevious').attr('href') != null) {
			document.getElementById("idprevious").setAttribute("href", previous_especial);
		}
	}

	if (pag == "3") //busqueda por id material
	{
		//caso idmaterila
		var next_mat = "/PCM_QA/producto?op=BUSQUEDA&pagina=" + (parseInt(pagina) + 1) + "&buscador=" + idMaterial;
		var previous_mat = "/PCM_QA/producto?op=BUSQUEDA&pagina=" + (parseInt(pagina) - 1) + "&buscador=" + idMaterial;

		//vamos a meterlos en los botones de Next y Previuos
		document.getElementById("idnext").setAttribute("href", next_mat);
		if ($('#idprevious').attr('href') != null) {
			document.getElementById("idprevious").setAttribute("href", previous_mat);
		}
	}



}

function checkImagenes() {
	document.getElementById('formRegistro').submit();
	if (document.getElementById("descripcion").value != "") {
		var cantImagenes = (i - 1);
		var llamarFormulario = true;
		while (cantImagenes > 0) {
			var elemento = "evidencia" + cantImagenes;
			if (document.getElementById(elemento).value == "") {
				Swal.fire('', 'Selecciona las imagenes', 'info');

				llamarFormulario = false;
			}
			cantImagenes -= 1;
		}


		if (llamarFormulario) {

			document.getElementById('formRegistro').submit();
		}
	} else {
		Swal.fire('', 'Preciona boton buscar', 'info');
	}

}

function checkImagenesEditar() {
	var llamarFormulario = true;
	var cantImagenes_editar = (i - 1);
	while (cantImagenes_editar > 0) {
		var elemento_editar = "evidencia_editar" + cantImagenes_editar;
		if (document.getElementById(elemento_editar).value == "") {
			Swal.fire('', 'Selecciona las imagenes', 'info');
			var llamarFormulario = false;


		}
		cantImagenes_editar -= 1;
	}
	if (llamarFormulario) {
		document.getElementById('formModificacion').submit();
	}

}

function filtroBusqueda() {
	var filtro = document.getElementById('selectFiltro').value;
	if (filtro == "T") {
		//vamos a cambir el valor de variable de localstorage PAGINACION = 1
		localStorage['PAGINACION'] = "1";

		location.href = "/PCM_QA/producto?op=Listar&pagina=1";

	} else {
		//vamos a cambir el valor de variable de localstorage PAGINACION = 2
		localStorage['PAGINACION'] = "2";
		document.getElementById('formFiltro').submit();
	}

	localStorage['FILTRO'] = filtro; //A,R,P,T

}

function filtroBusquedaAdmin() {
	//console.log("BUSQUEDA ADMIN ");
	var filtro = document.getElementById('selectFiltroAdmin').value;
	if (filtro == "T") {
		//vamos a cambir el valor de variable de localstorage PAGINACION = 1
		localStorage['PAGINACION'] = "1";

		location.href = "/PCM_QA/producto?op=AdministrarMateriales&pagina=1";

	} else {
		//vamos a cambir el valor de variable de localstorage PAGINACION = 2
		localStorage['PAGINACION'] = "2";
		document.getElementById('formFiltroAdmin').submit();
	}

	localStorage['FILTRO'] = filtro; //A,R,P,T

}

function getUsuarioBuscador(idinput, ida, servlet) {
	//esta buscando
	localStorage['isSearch'] = 'true';

	localStorage['pagina'] = "1";
	var paginaINT = parseInt(localStorage['pagina']);

	var aux = document.getElementById(idinput).value;
	localStorage[servlet] = aux;

	//decirle a next y previous que sus datos ahora son manejados por lo que encuentre buscador

	var aux1 = "/PCM_QA/" + servlet + "?op=busqueda&pagina=" + (paginaINT + 1) + "&buscador=" + aux;
	var aux2 = "/PCM_QA/" + servlet + "?op=busqueda&pagina=" + (paginaINT - 1) + "&buscador=" + aux;

	if (document.getElementById('idnext') != null)
		document.getElementById('idnext').setAttribute("href", aux1);
	if (document.getElementById('idprevious') != null)
		document.getElementById('idprevious').setAttribute("href", aux2);

	location.href = "/PCM_QA/" + servlet + "?op=busqueda&pagina=1&buscador=" + aux;
}

function buscarIdMaterial(idmaterial) {
	//esta buscando
	localStorage['isSearch'] = 'true';

	localStorage['pagina'] = "1";
	var paginaINT = parseInt(localStorage['pagina']);

	var id = document.getElementById(idmaterial).value;
	var tipo = document.getElementById('selectBusqueda').value;


	//cuando recarge la pagina, el metodo previewNext en el body cargara los datos necesarios en los btns, next y previous
	localStorage['PAGINACION'] = "3";
	localStorage['IDMATERIAL'] = id;
	localStorage['TIPO'] = tipo;


	//location.href = "/PCM_QA/producto?op=busqueda&pagina=" + (1) + "&buscador=" + id;
	location.href = "/PCM_QA/producto?op=busqueda&pagina=" + (1) + "&buscador=" + id + "&tipo=" + tipo;
}


function buscarIdMaterialImg(idmaterial) {
	//esta buscando
	localStorage['isSearch'] = 'true';

	localStorage['pagina'] = "1";
	var paginaINT = parseInt(localStorage['pagina']);

	var id = document.getElementById(idmaterial).value;
	var tipo = document.getElementById('selectBusquedaImg').value;


	//cuando recarge la pagina, el metodo previewNext en el body cargara los datos necesarios en los btns, next y previous
	localStorage['PAGINACION'] = "3";
	localStorage['IDMATERIAL'] = id;
	localStorage['TIPO'] = tipo;


	//location.href = "/PCM_QA/producto?op=busqueda&pagina=" + (1) + "&buscador=" + id;
	location.href = "/PCM_QA/producto?op=busquedaImg&pagina=" + (1) + "&buscador=" + id + "&tipo=" + tipo;
}

function opcionBusqueda() {
	//esta buscando
	localStorage['isSearch'] = 'true';

	localStorage['pagina'] = "1";
	var paginaINT = parseInt(localStorage['pagina']);


	var tipo = document.getElementById('selectBusqueda').value;


	//cuando recarge la pagina, el metodo previewNext en el body cargara los datos necesarios en los btns, next y previous
	localStorage['PAGINACION'] = "3";
	//localStorage['IDMATERIAL'] = id;
	localStorage['TIPO'] = tipo;

	if (tipo == '0') {
		location.href = "/PCM_QA/producto?op=Listar&pagina=1";

	} else {
		document.getElementById('divBuscador').setAttribute("style", "visibility:'visible'");


	}
}

function opcionBusquedaAdmin() {
	//esta buscando
	localStorage['isSearch'] = 'true';

	localStorage['pagina'] = "1";
	var paginaINT = parseInt(localStorage['pagina']);


	var tipo = document.getElementById('selectBusquedaAdmin').value;


	//cuando recarge la pagina, el metodo previewNext en el body cargara los datos necesarios en los btns, next y previous
	localStorage['PAGINACION'] = "3";
	//localStorage['IDMATERIAL'] = id;
	localStorage['TIPO'] = tipo;

	if (tipo == '0') {
		location.href = "/PCM_QA/producto?op=AdministrarMateriales&pagina=1";

	} else {
		document.getElementById('divBuscadorAdmin').setAttribute("style", "visibility:'visible'");


	}
}
/*
function buscarIdMaterialAdmin(idmaterial) ///busqueda para admin
{
	//esta buscando
	localStorage['isSearch'] = 'true';

	localStorage['pagina'] = "1";
	var paginaINT = parseInt(localStorage['pagina']);

	var id = document.getElementById(idmaterial).value;

	//cuando recarge la pagina, el metodo previewNext en el body cargara los datos necesarios en los btns, next y previous
	localStorage['PAGINACION'] = "3";
	localStorage['IDMATERIAL'] = id;

	location.href = "/PCM_QA/producto?op=BUSQUEDA&pagina=" + (1) + "&buscador=" + id;

}
*/
function opcionBusquedaImg() {
	//esta buscando
	localStorage['isSearch'] = 'true';

	localStorage['pagina'] = "1";
	var paginaINT = parseInt(localStorage['pagina']);
	var tipo = document.getElementById('selectBusquedaImg').value;


	//cuando recarge la pagina, el metodo previewNext en el body cargara los datos necesarios en los btns, next y previous
	localStorage['PAGINACION'] = "3";
	//localStorage['IDMATERIAL'] = id;
	localStorage['TIPO'] = tipo;

	if (tipo == '0') {
		location.href = "/PCM_QA/producto?op=AdministrarImg&pagina=1";

	} else {
		document.getElementById('divBuscadorImg').setAttribute("style", "visibility:'visible'");


	}
}

function buscarIdMaterialAdmin(idmaterial) {
	//esta buscando
	localStorage['isSearch'] = 'true';

	localStorage['pagina'] = "1";
	var paginaINT = parseInt(localStorage['pagina']);

	var id = document.getElementById(idmaterial).value;
	var tipo = document.getElementById('selectBusquedaAdmin').value;


	//cuando recarge la pagina, el metodo previewNext en el body cargara los datos necesarios en los btns, next y previous
	localStorage['PAGINACION'] = "3";
	localStorage['IDMATERIAL'] = id;
	localStorage['TIPO'] = tipo;


	//location.href = "/PCM_QA/producto?op=busqueda&pagina=" + (1) + "&buscador=" + id;
	location.href = "/PCM_QA/producto?op=BUSQUEDA&pagina=" + (1) + "&buscador=" + id + "&tipo=" + tipo;
}

/*function limpiarBusqueda(servlet) {
	localStorage[servlet] = "";
	localStorage['isSearch'] = "false";
	localStorage['pagina'] = "1";
	isSearch = false;
	location.href = "/PCM_QA/" + servlet + "?op=Listar&pagina=1";
}*/

function isSearchingUsuario(paginaIN) {
	//console.log('isSearching ejecutado =>' + localStorage['isSearch']);
	localStorage['pagina'] = paginaIN.toString();
	var search = localStorage['isSearch'] || 'defaultValue';
	var usuario = localStorage['usuarios'] || 'defaultValue';
	var paginaINT = parseInt(localStorage['pagina']);

	if (search == "true") {
		var aux1 = "/PCM_QA/usuario?op=busquedaA&pagina=" + (paginaINT + 1) + "&buscador=" + usuario;
		var aux2 = "/PCM_QA/usuario?op=busqueda&pagina=" + (paginaINT - 1) + "&buscador=" + usuario;

		if (document.getElementById('idnext') != null)
			document.getElementById('idnext').setAttribute("href", aux1);
		if (document.getElementById('idprevious') != null)
			document.getElementById('idprevious').setAttribute("href", aux2);
	}
}

function llenarFormEdicionImagenes(valores) {
	document.getElementById('formModalImagenEditar').setAttribute("action", valores);
}


function agregarImagen() {
	var obj = document.getElementById("modificarImagen");

	var aux = '<input type=@file@ accept=@.jpg,@ onchange=@previewFile(#imagenModif#,#evidenciaModif#)@' +
		'name=@evidenciaModif@ id=@evidenciaModif@>' +
		'<img id=@imagenModif@ src=@@style=@max-width:100%; max-height: 100%;@>' +
		'<button type="submit" name=@accion@ value=@editar@ >Actualizar Imagen</button>';

	aux = aux.replaceAll("@", '"');
	aux = aux.replaceAll("#", "'");

	obj.innerHTML += aux;
}


function limpiarEdicion(idPlacer) {
	var obj = document.getElementById(idPlacer);
	while (obj.children[0] != null) {
		obj.children[0].remove();
	}


	if (document.getElementById('placerEdicion') != null) {
		var obj = document.getElementById('placerEdicion');

		while (obj.children[1] != null) //ya que tiene un input oculto que guarda la cantidad de imagenes
		{
			obj.children[1].remove();
		}
	}

	i = 1; //el uno porque indica el nodo oculto para duplicar


	var botonAux = document.getElementById("boton_editar");
	botonAux.setAttribute("style", "visibility:'visible';float:right");

}


//function editarImagen(numero_material, numero_imagen) {
//	if (document.getElementById("evidencia_e").value == "") {
//		Swal.fire('Imagen no cargada', '', 'info');


//	} else {
//		Swal.fire({
//			title: 'Modificar',
//			text: 'Estas seguro de modidicar la imagen?',
//			icon: 'warning',
//			showDenyButton: true,
//			showCancelButton: false,
//			confirmButtonText: 'Modificar',
//			confirmButtonColor: '#DE3815',
//			denyButtonColor: '#1570DE',
//			denyButtonText: 'Cancelar',
//		}).then((result) => {
//			if (result.isConfirmed) {
//				var formulario = document.getElementById("formModalImagenEditar");
//				//console.log("los valores son:" + numero_material + " " + numero_imagen);
//				formulario.setAttribute("action", formulario.getAttribute("action") + "&numero_material=" + numero_material + "&numero_imagen=" + numero_imagen);
//				formulario.submit();
//			} else if (result.isDenied) {
//				Swal.fire('LISTO', 'Imagen NO modificada', 'info')
//			}
//		})

//	}

//}

function pasarCategoria() {
	var cat = document.getElementById("categoria").value;
	if (cat != "") {
		document.getElementById("cat").value = cat;
		return true;
	} else {
		return false;
		Swal.fire('', 'Agrega una categoria', 'info');

	}

}


//function eliminarImagen(numero_material, numero_imagen) {
//	Swal.fire({
//		title: 'Eliminar',
//		text: 'Estas seguro de eliminar imagen?',
//		icon: 'warning',
//		showDenyButton: true,
//		showCancelButton: false,
//		confirmButtonText: 'Eliminar',
//		confirmButtonColor: '#DE3815',
//		denyButtonColor: '#1570DE',
//		denyButtonText: 'Cancelar',
//	}).then((result) => {
//		if (result.isConfirmed) {
//			Swal.fire('LISTO', 'Imagen eliminada', 'success')
//			location.href = "/PCM_QA/producto?op=ELIMINAR&numero_material=" + numero_material + "&numero_imagen=" + numero_imagen;

//		} else if (result.isDenied) {
//			Swal.fire('LISTO', 'Imagen NO eliminada', 'info')
//		}
//	})

//}




function caracteresMax(id, max) {
	if (document.getElementById(id).value.length == max) {
		Swal.fire('', 'Limite de caracteres: ' + max, 'info');

	}

}


function editarUsuario() {
	if (document.getElementById('aux').value == "1") {
		Swal.fire({
			text: 'Informar del cambio al usuario?',
			showDenyButton: true,
			showCancelButton: true,
			confirmButtonText: 'SI',
			denyButtonText: 'NO',
		}).then((result) => {
			/* Read more about isConfirmed, isDenied below */
			if (result.isConfirmed) {
				document.getElementById('aux').value = "2";
				Swal.fire('', 'Correo mandado', 'success')
				document.getElementById('ModificacionUsuario').submit();

			} else if (result.isDenied) {
				document.getElementById('aux').value = 0;
				//Swal.fire('Correo no mandado', '', 'info')
				document.getElementById('ModificacionUsuario').submit();

			}
		})
	} else {
		location.href = "/PCM_QA/usuario?op=Listar&pagina=1";
	}
}


function editarUsuarioCambio() {
	document.getElementById('aux').value = 1;
}


function checkImagenesCm() {
	var llamarFormulario = true;
	var cantImagenesCM = (i - 1);
	while (cantImagenesCM > 0) {
		var elementoCM = "evidenciaCM" + cantImagenesCM;
		if (document.getElementById(elementoCM).value == "") {
			Swal.fire('', 'Selecciona las imagenes', 'info');
			var llamarFormulario = false;


		}
		cantImagenesCM -= 1;
	}
	if (llamarFormulario) {
		document.getElementById('CM').submit();
	}

}






function atributosExistentes() {
	$.ajax({
		type: 'POST',
		data: {
			op: 'atributosExistentes'
		},
		url: 'familia',
		success: function(res) {
			//console.log(res);
			var valor = res.split(",");
			$('#modalNuevaFamilia').modal('show');
			cantAtributos = valor[0];
			var checkBox = '';
			for (var i = 1; i <= cantAtributos; i++) {
				checkBox += `
			<div class="col-6">
				<input name="idchecks" type="checkbox" value="` + valor[i] + `">
				<label>` + valor[i] + `</label>

			</div>
			`;
			}

			document.getElementById('atributosExistentes').innerHTML = checkBox;


		}
	});
}

function atributosFamilia(pfamilia, pidDiv, isEdit, pidMaterial) {
	//console.log("Es el metodo que estoy buscando");
	var auxnull = " ";
	$.ajax({
		type: 'POST',
		data: {
			op: 'atributosFamilia',
			familiaAJAX: pfamilia
		},
		url: '/PCM_QA/producto',
		success: function(res) { //3,Texto,Hola,ETC
			//console.log(res);
			if (!res == "") {
				var valor = res.split("@");
				cantAtributos = valor[0];
				checkBox = '';
				for (var i = 1; i <= cantAtributos; i++) {
					var aux = valor[i];
					if (isEdit) {
						$.ajax({
							type: 'POST',
							data: {
								op: 'atributosFamilia2',
								atributoAJAX: aux,
								nombreFamiliaAJAX: pfamilia,
								idMaterial: pidMaterial
							},
							url: 'producto',
							success: function(res2) {
								var valor = res2.split("@");
								//console.log("Informaciion de atributos Familia " + res2);
								if (!valor[1]) { //cuando ya esta registrado, pero los atributos estan vacios
									//	console.log("Sin atributos");
									checkBox += `
										<div>	
											<label>` + valor[0] + `</label>
											<input  name="checks" id="` + valor[0] + `" type="checkbox" value="` + valor[0] + `" onclick="activarInput('` + valor[0] + `','edit` + valor[0] + `')">	
											<input style="visibility:hidden"  name="checksOff" id="off` + valor[0] + `" type="checkbox" value="` + valor[0] + `" checked>
											<input value="" placeholder="` + valor[0] + `" class="form-control" name="edit` + valor[0] + `" id="edit` + valor[0] + `" type="hidden" >
										</div>
										`;
								} else {
									//	console.log("con atributos");
									checkBox += `
									<div >	
										<label>` + valor[0] + `</label>
										<input  name="checks" id="` + valor[0] + `" type="checkbox" checked value="` + valor[0] + `" onclick="activarInput('` + valor[0] + `','edit` + valor[0] + `')">	
										<input style="visibility:hidden"  name="checksOff" id="off` + valor[0] + `" type="checkbox" value="` + valor[0] + `" checked>
										<input  class="form-control" name="edit` + valor[0] + `" id="edit` + valor[0] + `" type="text" value="` + valor[1] + `">
									</div>
									`;
								}

								document.getElementById(pidDiv).innerHTML = checkBox;

								//console.log("recibi de ajax atributofamilia2: " + res2);
							}
						});
					} else {
						//	console.log("Registro--");
						checkBox += `
						<div>
							<label>` + valor[i] + `</label>
							<input  name="checks" id="` + valor[i].trim() + `" type="checkbox" value="` + valor[i].trim() + `" onclick="activarInput('` + valor[i].trim() + `','input` + valor[i].trim() + `')">

							<input type="hidden" name="checksOff" id="off` + valor[i].trim() + `" type="checkbox" value="` + valor[i].trim() + `" checked>

							<input  class="form-control" name="input` + valor[i].trim() + `" id="input` + valor[i].trim() + `" type="hidden" value="` + auxnull + `">
						</div>
						`;
						document.getElementById(pidDiv).innerHTML = checkBox;

					}

				}
			} else {

				Swal.fire('', 'Familia sin atributos', 'error');

			}


		}
	});
}


function activarInput(idCheck, idtext) {
	var control = document.getElementById(idCheck);
	if (control.checked == true) {
		var input = document.getElementById(idtext);
		input.setAttribute("type", "text");
		document.getElementById('off' + idCheck).checked = false;
	} else {
		var input = document.getElementById(idtext);
		input.setAttribute("type", "hidden");
		document.getElementById('off' + idCheck).checked = true;



	}
}



function marcasExistentes() {
	//	console.log("Entre a marcas existentes");
	$.ajax({
		type: 'POST',
		data: {
			op: 'marcasExistentes'
		},
		url: '/PCM_QA/producto',
		success: function(res) {
			//console.log(res);
			var valor = res.split(",");
			cantMarcas = valor[0];
			var checkBox = '';
			for (var i = 1; i <= cantMarcas; i++) {
				//console.log("i:" + valor[i]);
				var marcas = document.getElementById("marcas");
				var option = document.createElement("option"); //Creamos la opcion
				option.innerHTML = valor[i];
				marcas.appendChild(option);
				//Metemos el texto en la opción
				//Metemos la opción en el select
				//	marcas.value = valor[i];


			}



		}
	});
}



function marcasExistentesEdicion(idMaterial) {
	$.ajax({
		type: 'POST',
		data: {
			op: 'marcasExistentesEdicion',
			idMaterial: idMaterial
		},
		url: 'producto',
		success: function(res) {
			//console.log(res);
			var valor = res.split(";");
			var cantMarcas = valor[0];
			var marcas = valor[1];
			var marcasValor = marcas.split(",");
			var marcaMaterial = valor[2];

			for (var i = 1; i <= cantMarcas;) {
				var marcas = document.getElementById("marcasE");
				var option = document.createElement("option");
				if (i == 1) {
					option.innerHTML = marcaMaterial;
					marcas.appendChild(option);
					i++;
				} else {
					option.innerHTML = marcasValor[(i - 1)];
					marcas.appendChild(option);
					i++;
				}




			}



		}
	});
}

function familiasExistentesProducto() {

	$.ajax({
		type: 'POST',
		data: {
			op: 'familiasExistentesProducto'
		},
		url: 'producto',
		success: function(res) {
			//console.log(res);
			var valorFamilias = res.split(",");
			cantFamilias = valorFamilias[0];
			for (var i = 1; i <= cantFamilias; i++) {
				//console.log(valorFamilias[i]);
				var familias = document.getElementById('familias');
				var optionFamilia = document.createElement("option"); //Creamos la opcion
				optionFamilia.innerHTML = valorFamilias[i]; //Metemos el texto en la opción
				familias.appendChild(optionFamilia); //Metemos la opción en el select

			}

		}
	});
}

function familiasExistentesAtributos() {
	$.ajax({
		type: 'POST',
		data: {
			op: 'familiasExistentesAtributos'
		},
		url: 'atributo',
		success: function(res) {
			//console.log(res);
			var valor = res.split(",");
			$('#modalNuevoAtributo').modal('show');
			cantFamilias = valor[0];
			var checkBox = '';
			for (var i = 1; i <= cantFamilias; i++) {
				checkBox += `
			<div class="col-6">
				<input name="idchecks" type="checkbox" value="` + valor[i] + `">
				<label style="font-size:x-small;">` + valor[i] + `</label>
			</div>
			`;
			}

			document.getElementById('familiasExistentes').innerHTML = checkBox;


		}
	});
}

function editarMaterial(idMaterial) {
	$('#modalModificacionMaterial').modal('show');
	espera2('Recopilando datos...', 2000);
	document.getElementById('idMaterialEditar').value = idMaterial;
	//console.log("Ya entre en editar Materiales")
	$.ajax({
		type: 'POST',
		data: {
			op: 'edicion',
			idMaterial: idMaterial
		},
		url: 'producto',
		success: function(res) {
			//	console.log(res)
			valor = res.split("@");
			//$('#modalModificacionMaterial').modal('show');
			//document.getElementById('idMaterialEditar').value = idMaterial;
			document.getElementById('descripcionMaterialEditar').value = valor[1];
			document.getElementById('idFamiliaEditar').value = valor[2];
			document.getElementById('descripcionFamiliaEditar').value = valor[3];
			atributosFamilia(valor[3], 'atributosFamiliaEdit', true, idMaterial);
			document.getElementById('idProveedorEditar').value = valor[4];
			document.getElementById('descripcionProveedorEditar').value = valor[5];
			document.getElementById('idGpoArtEditar').value = valor[6];
			document.getElementById('descripcionGpoArtEditar').value = valor[7];
			document.getElementById('idCategoriaEditar').value = valor[8];
			document.getElementById('descripcionCategoriaEditar').value = valor[9];
			document.getElementById('eanEditar').value = valor[10];
			document.getElementById('umEditar').value = valor[11];
			//document.getElementById('gramajeEditar').value = valor[12];
			if (valor[12] == "null") {
				document.getElementById("gramajeEditar").placeholder = "Sin gramaje";
				document.getElementById("gramajeEditar").value = "";

			} else {
				document.getElementById("gramajeEditar").value = valor[12];

			}

			document.getElementById('conversionUmEditar').value = valor[13];

			if (valor[14] == "null") {
				document.getElementById("sustitutoEditar").placeholder = "Sin sustituto";

			} else {
				document.getElementById("sustitutoEditar").value = valor[14];

			}
			document.getElementById('cantImagenE').value = valor[15];
			document.getElementById('cantImagenEU').value = valor[15];
			if (valor[16] == "null") {
				document.getElementById("descripcionComercialE").value = "";
			} else {
				document.getElementById("descripcionComercialE").value = valor[16];

			}


			document.getElementById('idMarcaEditar').value = valor[17];
			document.getElementById('descripcionMarcaEditar').value = valor[18];
			materialesImagenes(idMaterial);

		}
	});
}


function seleccionarFamilia() {
	var aux = document.getElementById('descripcionFamilia').value;
	atributosFamilia(aux, 'atributosFamilia', false, 0);



}


function NumText(string) { //solo letras y numeros
	var out = '';
	//Se añaden las letras validas
	var filtro = 'abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890'; //Caracteres validos

	for (var i = 0; i < string.length; i++)
		if (filtro.indexOf(string.charAt(i)) != -1)
			out += string.charAt(i);
	return out;
}

function registrarFamilia() {
	//Validar que la cantidad minima de digitos sea dos


	var pidFamilia = document.getElementById('idFamilia').value;
	//console.log("ENTRE A JS REGISTRAR FAMILIA" + pidFamilia);
	$.ajax({
		type: 'POST',
		data: {
			op: 'confirmacionRegistro',
			idFamilia: pidFamilia
		},
		url: 'familia',
		success: function(res) {
			//console.log(res);
			valor = res.split("@");
			if (valor[0] == "no") {
				Swal.fire('', 'Familia no encontrada, intente otra diferente', 'error');

			}
			if (valor[0] == "internono") {
				Swal.fire('', 'Familia ya registrada, intente otra diferente', 'error');

			}
			if (valor[0] == "si") {

				if (valor[2] == "0") {
					Swal.fire({
						text: 'Registrar la familia sin atributos?' + 'ID Familia: ' + valor[3] + 'Familia: ' + valor[1],
						showDenyButton: true,
						showCancelButton: true,
						confirmButtonText: 'REGISTRAR',
						denyButtonText: 'NO',
					}).then((result) => {
						/* Read more about isConfirmed, isDenied below */
						if (result.isConfirmed) {
							document.getElementById('modalNuevaFamilia').submit();

						} else if (result.isDenied) {

						}
					});

				}
				if (valor[2] == "1") {

					Swal.fire({
						text: 'Registrar familia?' + 'ID Familia: ' + valor[3] + 'Familia:' + valor[1],
						showDenyButton: true,
						showCancelButton: true,
						confirmButtonText: 'REGISTRAR',
						denyButtonText: 'NO',
					}).then((result) => {
						/* Read more about isConfirmed, isDenied below */
						if (result.isConfirmed) {
							document.getElementById('modalNuevaFamilia').submit();

						} else if (result.isDenied) {

						}
					});

				}
			}



		}
	});
}


function familiasExistentesCsv() {

	$.ajax({
		type: 'POST',
		data: {
			op: 'familiasExistentesCsv'
		},
		url: '/PCM_QA/producto',
		success: function(res) {
			//console.log(res);
			//$('#modalCargasMasivas').modal('show');
			var valorFamilias = res.split(",");
			cantFamilias = valorFamilias[0];
			for (var i = 1; i <= cantFamilias; i++) {
				//console.log(valorFamilias[i]);
				var familias = document.getElementById('selectFamiliasCsv');
				var optionFamilia = document.createElement("option"); //Creamos la opcion
				optionFamilia.innerHTML = valorFamilias[i]; //Metemos el texto en la opción
				familias.appendChild(optionFamilia); //Metemos la opción en el select

			}

		}
	});
}


function plataformasExistentes(idmaterial) {


	$.ajax({
		type: 'POST',
		data: {
			op: 'plataformasExistentesv2',
			idMaterial: idmaterial
		},
		url: 'plataforma',
		success: function(res) {
			console.log(res);
			datos = []
			datos = res.split(",")
			var cantPlataforma = parseInt(datos[0]);
			var plataformas = []; //PLATAFORMASBD 

			plataformas = datos[1].split(";");

			var eans = [];
			eans = datos[2].split(";");

			var ums = [];
			ums = datos[3].split(";");

			var conum = [];
			conum = datos[4].split(";");

			var numeroacheckear = []; //Plataformas seleccionadas
			numeroacheckear = datos[5].split(";");

			var destacadas = []; //destacadas
			destacadas = datos[6].split(";");

			var plat_eans = [];
			plat_eans = datos[7].split(";");

			//json para poder decidir mas adelante
			json_plateans = [];

			//construimos un json que nos guarde los valores necesarios y un elemento que le llame register el cual me sive para saber si en el ciclo de llenado de los eans ya se puso como checked ese registro del json
			for (var i = 0; i < plat_eans.length; i++) {
				plat = {}
				plat["name"] = plat_eans[i].split(":")[0];
				plat["ean"] = plat_eans[i].split(":")[1];
				plat["register"] = "no";

				json_plateans.push(plat);
			}

			//HAREMOS UN FOR QUE RECORRERA CADA PLATAFORMA EN LA BD ==> A B C D
			for (var p = 0; p < plataformas.length; p++) {
				//VARIABLES GLOBALES DENTRO DEL FOR
				inicio = `<div class="container">`;
				cabecera = "";
				cuerpo = "";
				fin = "</div>";

				//cada plataforma que exista, el programa debe preguntar si alguna de las plataformas seleccionadas en la bd es igual
				for (var pdb = 0; pdb < numeroacheckear.length; pdb++) {
					//console.log("Plataformas P["+plataformas[p]+"] == Plat select PDB["+numeroacheckear[pdb]+"]")
					if (plataformas[p] == numeroacheckear[pdb] && numeroacheckear.length > 0) {
						//console.log('Si son iguales'); //Pataformas asigandas
						//console.log("Destacados?: "+destacadas[pdb])

						cabecera = `<div>
							<input type="checkbox" id="` + plataformas[p] + `" onclick=cambiarChecks('` + (p) + `') name="idchecks" value="` + plataformas[p] + `" checked>
							<b>
							  <label class="badge badge-pill badge-primary">` + plataformas[p] + `</label>
							</b>
							<input type="checkbox" style="visibility:hidden" name="idchecksoff"  id="` + plataformas[p] + `" value="` + plataformas[p] + `">
							  <input type="checkbox"  name="idchecksD` + plataformas[p] + `"  onclick="cambiarChecksD('` + plataformas[p] + `')"  id="idchecksD` + plataformas[p] + `" value="` + plataformas[p] + `" ` + ((destacadas[pdb] == "1") ? 'checked' : '') + `>
							  <label style="color:blue"> <b>Destacado</b></label>
							<input type="checkbox"  style="visibility:hidden"  name="idchecksDoff` + plataformas[p] + `" id="idchecksDoff` + plataformas[p] + `" value="` + plataformas[p] + `" > 							
							</div>
						  `;
						//encontro = true;
						break;
					} else {
						//console.log('No son iguales'); //Pataformas asigandas
						cabecera = `
							<div>
							 <input type="checkbox" id="` + plataformas[p] + `" onclick=cambiarChecks('` + (p) + `') name="idchecks" value="` + plataformas[p] + `">
							 <b><label class="badge badge-pill badge-primary">` + plataformas[p] + `</label></b>
							 <input type="checkbox"  style="visibility:hidden"  name="idchecksoff" id="` + plataformas[p] + `" value="` + plataformas[p] + `" checked>
							 <input type="checkbox"  name="idchecksD` + plataformas[p] + `" onclick="cambiarChecksD('` + plataformas[p] + `')" id="idchecksD` + plataformas[p] + `" value="` + plataformas[p] + `"><label style="color:blue">
							 <b>Destacado</b></label>
							 <input type="checkbox"  style="visibility:hidden"  name="idchecksDoff` + plataformas[p] + `" id="idchecksDoff` + plataformas[p] + `" value="` + plataformas[p] + `" checked> 				
							</div>
						   `;
						//encontro = true;
						//break;
					}
				} //termino primer for de las plataformas seleccionadas


				//este nos sirve para guardar en los eans que se van a marcar como checked en el siguiente for
				var agregamos = [];
				encontro_ean = false;
				//console.table(json_plateans)
				for (var x = 0; x < plat_eans.length; x++) {
					if (encontro_ean == false) {
						//console.log("P:"+p+" X:"+x)
						//tengo algun registro con esa plataforma?
						//console.log("1.- "+json_plateans[x].name +" == "+ plataformas[p]);

						if (json_plateans[x].name == plataformas[p]) {
							for (var eanss = 0; eanss < eans.length; eanss++) { //BD 2
								//si tengo
								console.log("eanbd: " + eans[eanss] + " == eanselect: " + json_plateans[x].ean);

								if (eans[eanss] == json_plateans[x].ean) {

									agregamos[eanss] = true;
									//break;
								} else {

									//tengo que buscar si en el json_plateans
									// existen mas registros con mi mismo nombre
									for (var down = x + 1; down < json_plateans.length; down++) { // hacer un for por cada registro del json
										console.log("-----eanbd: " + eans[eanss] + " == eanselect: " + json_plateans[down].ean);
										if (json_plateans[down].name == json_plateans[x].name) {
											//si hay mas registros de la misma plataforma

											if (eans[eanss] == json_plateans[x + 1].ean) {
												agregamos[eanss] = true;
												break;
											} else {
												agregamos[eanss] = false;
											}
										}
									}

									//agregamos[eanss] = false;
									//break;
								}
							}
						}
					}
				}

				//mostramos el vector de agregamos por cada grupo de eans de cada plataforma, si esta o no en el
				//console.log(agregamos);


				//en este caso cada ciclo en el siguiente for buscara si en la posicion j -> los eans que existe, si se marcan o no...
				//console.log(agregamos);

				var cuerpo = '<div class="row">';//Inicio la fila
				for (var k = 0; k < eans.length; k++) {
					if (agregamos[k] === true) {
						cuerpo +=
							`<div class="breadcrumb col-6">
						  <div class="col-1">
							<input type="checkbox" name="checksEan` + plataformas[p] + `" value="` + (k + 1) + `" checked 
							onclick=cambiarChecksEan('` + k + `','` + plataformas[p] + `') >
							<input type="checkbox" name="checksEanOff` + plataformas[p] + `" value="` + (k + 1) + `" style="visibility:hidden">
						  </div>
						  <div class="col-10">
							<label for="">Ean: ` + eans[k] + `</label>
							<br>
							  <label for="">UM: ` + ums[k] + `</label>
							<br>
							<label for="">Conversion um: ` + conum[k] + `</label>
						  </div>
						</div>
					  `;
					} else {
						cuerpo +=
							`<div class="breadcrumb col-6">
						  <div class="col-1">
							<input type="checkbox" name="checksEan` + plataformas[p] + `" value="` + (k + 1) + `" onclick=cambiarChecksEan('` + k + `','` + plataformas[p] + `') >
							<input type="checkbox" name="checksEanOff` + plataformas[p] + `" value="` + (k + 1) + `" style="visibility:hidden" checked>
						  </div>
						  <div class="col-10">
							<label for="">Ean: ` + eans[k] + `</label>
							<br>
							<label for="">UM: ` + ums[k] + `</label>
							<br>
							<label for="">Conversion um: ` + conum[k] + `</label>
						  </div>
						</div>
						`;
					}

				}
				cuerpo += '</div>'//Cierro la fila 



				//generamos el BLOQUE por cada PLATAFORMA 
				document.getElementById('plataformasExistentes').innerHTML += (inicio + cabecera + cuerpo + fin);

			} //termino segundo for de todas las plataformas

		}

	});
}


function cambiarChecks(i) { //plataformas
	if (document.getElementsByName('idchecks')[i].checked == true) { //true
		document.getElementsByName('idchecksoff')[i].checked = false;
	} else {
		document.getElementsByName('idchecksoff')[i].checked = true;
	}
}

function cambiarChecksEan(i, plataforma) {
	if (document.getElementsByName('checksEan' + plataforma)[i].checked == true) { //true
		document.getElementsByName('checksEanOff' + plataforma)[i].checked = false;
	} else {
		document.getElementsByName('checksEanOff' + plataforma)[i].checked = true;
	}
}

function cambiarChecksD(plataforma) {
	if (document.getElementById('idchecksD' + plataforma).checked == true) { //true
		document.getElementById('idchecksDoff' + plataforma).checked = false;
	} else {
		document.getElementById('idchecksDoff' + plataforma).checked = true;
	}
}



function editarAdmiMat(idMaterial) {
	plataformasExistentes(idMaterial);
	editarMaterial(idMaterial);
}

function aceptarMaterial() {
	console.log('Vamos a cambiar');
	//Este metodo se utiliza cuando el admin asignara el material a una plataforma
	//Vamos a ver si el gramaje y descripcion comercial estan vacios 
	var gramaje = document.getElementById('gramajeEditar').value;
	var descripcionComercial = document.getElementById('descripcionComercialE').value;
	var id = document.getElementById('idMaterialEditar').value;
	if (gramaje != "" && descripcionComercial != "") { //Sin vacios
		var id = document.getElementById('idMaterialEditar').value;
		Swal.fire({
			text: 'Los datos del material: ' + id + ' son correctos?',
			showDenyButton: true,
			showCancelButton: true,
			confirmButtonText: 'SI',
			denyButtonText: 'NO',
		}).then((result) => {
			if (result.isConfirmed) {
				document.getElementById('opcion').value = "A"
				document.getElementById('idCambioEstatus').submit();
			} else if (result.isDenied) {

			}
		});
	} else {
		Swal.fire({
			text: 'Informacion faltante, estas seguro de continuar?',
			icon: 'warning',
			showDenyButton: true,
			showCancelButton: false,
			confirmButtonText: 'SI',
			confirmButtonColor: '#DE3815',
			denyButtonColor: '#1570DE',
			denyButtonText: 'NO',
		}).then((result) => {
			if (result.isConfirmed) {
				Swal.fire({
					text: 'Los datos del material: ' + id + ' son correctos?',
					showDenyButton: true,
					showCancelButton: true,
					confirmButtonText: 'SI',
					denyButtonText: 'NO',
				}).then((result) => {
					if (result.isConfirmed) {
						document.getElementById('opcion').value = "A"
						document.getElementById('idCambioEstatus').submit();
					} else if (result.isDenied) {

					}
				});

			} else if (result.isDenied) {
				var alerta = document.getElementById('alertaEdicion');
				var alert = "<div class='alert alert-info'>Completa los campos obligatorios</div>";
				alerta.innerHTML = alert;
			}
		})

	}



}

function rechazarMaterial() {
	var id = document.getElementById('idMaterialEditar').value;
	//console.log(id);
	Swal.fire({
		text: 'Estas seguro de rechazar el material ' + id + '?',
		showDenyButton: true,
		showCancelButton: true,
		confirmButtonText: 'SI',
		denyButtonText: 'NO',
	}).then((result) => {
		if (result.isConfirmed) {
			Swal
				.fire({
					text: "Escribe el motivo",
					input: "text",
					showCancelButton: true,
					confirmButtonText: "Guardar",
					cancelButtonText: "Cancelar",
					inputValidator: motivo => {
						// Si el valor es válido, debes regresar undefined. Si no, una cadena
						if (!motivo) {
							return "Escribe el motivo";
						} else {
							return undefined;
						}
					}
				})
				.then(resultado => {
					if (resultado.value) {
						let motivo = resultado.value;
						//console.log("Motivo: " + motivo);

						document.getElementById('opcion').value = "R"
						document.getElementById('motivo').value = motivo;

						//location.href =  "/PCM_QA/producto?op=actualizarEstatus";
						document.getElementById('idCambioEstatus').submit();

					}
				});
		} else if (result.isDenied) { }
	});


}

function checarPlataforma() {
	var aux = 0;
	var err = 0;
	for (var i = 0; i < document.getElementsByName('idchecks').length;) {
		if (document.getElementsByName('idchecks')[i].checked) { //AL MENOS UNA PLATAFORMA SELECCIONADA

			if (document.getElementsByName('idchecks')[i].checked && document.getElementsByName('idchecksoff')[i].checked ||
				document.getElementsByName('idchecks')[i].unchecked && document.getElementsByName('idchecksoff')[i].unchecked)
			//CHECAR SI ES VALIDO
			{
				err = 1;
			} else {
				aux = 1;
			}
		}
		i++;
	}
	//console.log("RESULTADO DE AUX" + aux + "RESULTADO DE ERROR " + err);

	if (aux == 1) {
		if (err == 0) {
			aceptarMaterial();

		} else {
			Swal.fire('', 'Las plataformas deben de tener solo un estatus', 'error');

		}

	} else {
		//'Swal.fire('Selecciona al menos una plataforma', '', 'info');
		aceptarMaterial();

	}
	var aux = 0;
	var err = 0;
}

function editarAtributo(idAtributo) {
	$.ajax({
		type: 'POST',
		data: {
			op: 'Editar',
			id: idAtributo
		},
		url: 'atributo',
		success: function(res) {
			var valor = res.split("$");
			$('#modalModificacionAtributo').modal('show');
			document.getElementById('idAtributo_editar').value = idAtributo;
			document.getElementById('nombre_atributo_editar').value = valor[0];
			var estatus = valor[1];
			if (estatus == "A") {
				document.getElementById('estatus_editar').selectedIndex = "0";
			} else {
				document.getElementById('estatus_editar').selectedIndex = "1";
			}

		}
	});
}


function editarFamilia(idFamilia) {
	$.ajax({
		type: 'POST',
		data: {
			op: 'Editar',
			id: idFamilia
		},
		url: 'familia',
		success: function(res) {
			var valor = res.split("$");
			$('#modalModificacionFamilia').modal('show');
			document.getElementById('idFamilia_editar').value = idFamilia;
			document.getElementById('nombre_familia_editar').value = valor[0];
			var estatus = valor[1];
			if (estatus == "A") {
				document.getElementById('estatus_editar').selectedIndex = "0";
			} else {
				document.getElementById('estatus_editar').selectedIndex = "1";
			}

		}
	});
	atributosFamilias(idFamilia);
}

function atributosFamilias(idFamilia) {
	//console.log("ENTRE atributosFamilias");
	$.ajax({
		type: 'POST',
		data: {
			op: 'checksAtributos',
			idFamilia: idFamilia
		},
		url: 'familia',

		success: function(res) {
			var valor = res.split(",");
			cantFamilia = valor[0];
			numerosregistroidfamilia = valor[1]; //1;3

			var numeroacheckear = [];
			if (numerosregistroidfamilia != "") {
				numeroacheckear = numerosregistroidfamilia.split(";");
			}

			var atributosBD = [];
			for (var i = 0; i < valor[0];) {
				atributosBD[i] = valor[i + 2];
				i++;
			}
			//NECESITAREMOS:
			//
			//numeroacheckear -> es un vector que contiene los atributos registrados //Texto,Ingredientes,Contenido,Indicaciones
			//atributosBD -> es un vector que contiene todos los atributos en la BD

			var checkBox = "";


			var yu = []; //los indices donde se encuentra similitud de los atributos de la familia, con los atributos en la bd

			//si en valor[i+2] es igual a numerochekear[i], entonces lo ponemos activo, si no, inactivo
			// atributosBD.includes(numeroacheckear[i]
			//console.log(atributosBD.some(r => numeroacheckear.includes(r)));
			if (atributosBD.some(r => numeroacheckear.includes(r)) && numeroacheckear.length > 0) {
				for (var a = 0; a < numeroacheckear.length; a++) {
					for (var b = 0; b < cantFamilia; b++) {
						//console.log(atributosBD[b] + " , " + numeroacheckear[a]);
						if (atributosBD[b] == numeroacheckear[a]) {
							yu[a] = b;
						}
					}
				}
			}
			//console.log({atributosBD,numeroacheckear,yu});
			//cuales si y cuales no
			var salvadora = 0;
			for (var x = 0; x < cantFamilia;) {
				if (yu.includes(x)) {
					checkBox += `
							<div class="col-6">
								<input type="checkbox" onclick=cambiarChecksAT(` + x + `) name="idchecks" id="` + atributosBD[x] + `" value="` + atributosBD[x] + `" checked>
								<label>` + atributosBD[x] + `</label>
								<input type="checkbox" style="visibility:hidden" name="idchecksoff" id="` + atributosBD[x] + `" value="` + atributosBD[x] + `" >
							</div>`;
					salvadora++;
				} else {
					checkBox += `
							<div class="col-6">
								<input type="checkbox" onclick=cambiarChecksAT(` + x + `) name="idchecks" id="` + atributosBD[x] + `" value="` + atributosBD[x] + `">
								<label>` + atributosBD[x] + `</label>
								<input type="checkbox" style="visibility:hidden" name="idchecksoff" id="` + atributosBD[x] + `" value="` + atributosBD[x] + `" checked >
							</div>`;
				}
				x++;
			}

			document.getElementById('atributos').innerHTML = checkBox;


		}
	});
}


function cambiarChecksAT(i) { //atributos familias
	//console.log('Entre a cambiar check');
	if (document.getElementsByName('idchecks')[i].checked == true) { //true
		document.getElementsByName('idchecksoff')[i].checked = false;
	} else {
		document.getElementsByName('idchecksoff')[i].checked = true;
	}
}


function aceptarAtributosFamilia() {
	estatus = document.getElementById('estatus_editar').value
	var id = document.getElementById('nombre_familia_editar').value;
	//console.log(id);
	if (estatus == 'A') {

		Swal.fire({
			text: 'Guardar los cambios para la familia : ' + id + ' ?',
			showDenyButton: true,
			showCancelButton: true,
			confirmButtonText: 'SI',
			denyButtonText: 'NO',
		}).then((result) => {
			if (result.isConfirmed) {
				Swal.fire('Cambios guardados correctamente', '', 'success');
				document.getElementById('ModificacionFamilia').submit();

			} else if (result.isDenied) {

			}
		});

	} else {

		Swal.fire({
			text: 'Inhabilitar la familia: ' + id + ' ?',
			showDenyButton: true,
			showCancelButton: true,
			confirmButtonText: 'SI',
			denyButtonText: 'NO',
		}).then((result) => {
			if (result.isConfirmed) {
				document.getElementById('ModificacionFamilia').submit();
			} else if (result.isDenied) {

			}
		});
	}
}

function checarAtributosFamilia() {
	estatus = document.getElementById('estatus_editar').value
	if (estatus == 'A') {
		var aux = 0;
		var err = 0;
		for (var i = 0; i < document.getElementsByName('idchecks').length;) {
			if (document.getElementsByName('idchecks')[i].checked) { //AL MENOS UN ATRIBUTO SELECCIONADO
				aux = 1;
				if (document.getElementsByName('idchecks')[i].checked && document.getElementsByName('idchecksoff')[i].checked ||
					document.getElementsByName('idchecks')[i].unchecked && document.getElementsByName('idchecksoff')[i].unchecked)
				//CHECAR SI ES VALIDO
				{
					err = 1;
				} else {
					aux = 1;
				}
			}
			i++;
		}
		//console.log("RESULTADO DE AUX" + aux + "RESULTADO DE ERROR " + err);

		if (aux == 1) {
			if (err == 0) {
				aceptarAtributosFamilia();

			} else {
				Swal.fire('', 'Los atributos deben de tener solo un estatus', 'error');

			}

		} else {
			//Swal.fire('Selecciona al menos un atributo', '', 'info');
		}
		var aux = 0;
		var err = 0;
	} else { // EL ESTATUS ES I POR LO TANTO NO TENGO QUE HACER EVALUACIONES
		aceptarAtributosFamilia();

	}
}

function materialesImagenes(idMaterial) {
	//console.log('Entre a materialesImagenes');

	$.ajax({
		type: 'POST',
		data: {
			op: 'materialImagen',
			idMaterial: idMaterial
		},
		url: 'producto',
		success: function(res) {
			//console.log("IMAGENES: " + res);
			var imagenes = res.split("$");
			cantImagenes = imagenes[0];

			if (cantImagenes == 0) {
				var img = new Image();
				img.src = imagenes[1];
				document.getElementById('materialesImagenes').appendChild(img);

			} else {
				divImagenes = document.getElementById('materialesImagenes');

				var divHTML = '';

				for (var i = 1; i <= cantImagenes;) {

					//Nombre de imagen 
					var cadena1 = imagenes[i];
					//console.log('cadena1 ' + cadena1);
					var cadena2 = cadena1.slice(16, cadena1.length);
					var rutaCompleta =  imagenes[i];
					//console.log('cadena2 ' + cadena2);
					divHTML = ` 
						<div class="col-6" id=` + cadena2 + ` > <center>
							<img src='` + rutaCompleta + `' id='` + cadena2 + `' style='max-width: 30%' onclick=imgGrande("` + rutaCompleta + `")>
							<div>
								<label name=label` + cadena2 + ` id=label` + cadena2 + `>` + cadena2 + `</label>
								<button id='button` + cadena2 + `' name='button` + cadena2 + `'type='button'
								onclick=eliminarImagen('` + cadena2 + `') class="btn btn-danger">X</button>
							</div> </center>
						</div> `;

					document.getElementById('materialesImagenes').innerHTML += divHTML;


					i++;


				}

			}

		}

	});
}


function eliminarImagen(img) {
	var imgElim = document.getElementById('imgElim').value;
	//console.log("Entre a eliminar Img" +img);
	Swal.fire({
		text: 'Estas seguro de eliminar la imagen : ' + img + ' ?',
		showDenyButton: true,
		confirmButtonText: 'SI',
		denyButtonText: 'NO',
	}).then((result) => {
		if (result.isConfirmed) {
			Swal.fire('', 'Imagen eliminada', 'success');
			var divimg = document.getElementById(img);
			if (!divimg) {
				alert("El elemento selecionado no existe");
			} else {

				padre = divimg.parentNode;
				padre.removeChild(divimg);
				var cantImg = parseInt(document.getElementById('cantImagenEU').value);
				cantImg = cantImg - 1;
				document.getElementById('cantImagenEU').value = cantImg;
				document.getElementById('cantImagenE').value = cantImg;
				imgElim = imgElim + img + ",";
				document.getElementById('imgElim').value = imgElim;

			}

		} else if (result.isDenied) {

		}
	});
}

function espera() {
	let timerInterval
	Swal.fire({
		text: 'Espera un momento',
		timer: 3000,
		timerProgressBar: true,
		didOpen: () => {
			Swal.showLoading()
			timerInterval = setInterval(() => {
				const content = Swal.getContent()
				if (content) {
					const b = content.querySelector('b')
					if (b) {
						b.textContent = Swal.getTimerLeft()
					}
				}
			}, 100)
		},
		willClose: () => {
			clearInterval(timerInterval)
		}
	}).then((result) => {
		/* Read more about handling dismissals below */
		if (result.dismiss === Swal.DismissReason.timer) { }
	});
}


//function csvTimer() {
//	//alert("entre a csvTimer.....");
//	let timerInterval
//	Swal.fire({
//		title: 'Espera un momento por favor',
//		timer: 4000,
//		timerProgressBar: true,
//		didOpen: () => {
//			Swal.showLoading()
//			timerInterval = setInterval(() => {
//				const content = Swal.getHtmlContainer()
//				if (content) {
//					const b = content.querySelector('b')
//					if (b) {
//						b.textContent = Swal.getTimerLeft()
//					}
//				}
//			}, 100)
//		},
//		willClose: () => {
//			clearInterval(timerInterval)
//		}
//	}).then((result) => {
//		/* Read more about handling dismissals below */
//		if (result.dismiss === Swal.DismissReason.timer) {
//			// cuando ya paso el tiempo 
//			//CREAR VARIABLE STORAGE
//			localStorage.congelada = "true";
//			var congeladaAux = localStorage.congelada;
//			//Activar y mandar el form
//			document.getElementById('cargaMasivaCsv').submit();


//		}
//	})



//}
function csvMaterialesBaja() { //Este metodo se llama cuando se carga el archivo de carga masiva de asignacion de materiales a plataformas
	///Metodo que guarda el archivo csv
	let timerInterval
	Swal.fire({
		text: 'Espera un momento por favor',
		timer: 4000,
		timerProgressBar: true,
		didOpen: () => {
			Swal.showLoading()
			timerInterval = setInterval(() => {
				const content = Swal.getHtmlContainer()
				if (content) {
					const b = content.querySelector('b')
					if (b) {
						b.textContent = Swal.getTimerLeft()
					}
				}
			}, 100)
		},
		willClose: () => {
			clearInterval(timerInterval)
		}
	}).then((result) => {
		/* Read more about handling dismissals below */
		if (result.dismiss === Swal.DismissReason.timer) {
			// cuando ya paso el tiempo 
			//CREAR VARIABLE STORAGE
			sessionStorage.congeladaMaterialBaja = "true";
			var congeladaMaterialBaja = sessionStorage.congeladaMaterialBaja;
			//Activar y mandar el form
			document.getElementById('MaterialBaja').submit();

		}
	})



}
function csvMaterialesPlataforma() { //Este metodo se llama cuando se carga el archivo de carga masiva de asignacion de materiales a plataformas
	///Metodo que guarda el archivo csv
	let timerInterval
	Swal.fire({
		text: 'Espera un momento por favor',
		timer: 4000,
		timerProgressBar: true,
		didOpen: () => {
			Swal.showLoading()
			timerInterval = setInterval(() => {
				const content = Swal.getHtmlContainer()
				if (content) {
					const b = content.querySelector('b')
					if (b) {
						b.textContent = Swal.getTimerLeft()
					}
				}
			}, 100)
		},
		willClose: () => {
			clearInterval(timerInterval)
		}
	}).then((result) => {
		/* Read more about handling dismissals below */
		if (result.dismiss === Swal.DismissReason.timer) {
			// cuando ya paso el tiempo 
			//CREAR VARIABLE STORAGE
			sessionStorage.congeladaMaterialPlataforma = "true";
			var congeladaAuxMaterialesPlataforma = sessionStorage.congeladaMaterialPlataforma;
			//Activar y mandar el form
			document.getElementById('cargaMasivaMaterialPlataforma').submit();

		}
	})



}

function csvMateriales() { //Espera el resultado
	///Metodo que guarda el archivo csv
	let timerInterval
	Swal.fire({
		text: 'Espera un momento por favor',
		timer: 4000,
		timerProgressBar: true,
		didOpen: () => {
			Swal.showLoading()
			timerInterval = setInterval(() => {
				const content = Swal.getHtmlContainer()
				if (content) {
					const b = content.querySelector('b')
					if (b) {
						b.textContent = Swal.getTimerLeft()
					}
				}
			}, 100)
		},
		willClose: () => {
			clearInterval(timerInterval)
		}
	}).then((result) => {
		/* Read more about handling dismissals below */
		if (result.dismiss === Swal.DismissReason.timer) {
			// cuando ya paso el tiempo 
			//CREAR VARIABLE STORAGE
			sessionStorage.congeladaMaterial = "true";
			var congeladaAuxM = sessionStorage.congeladaMaterial;
			//Activar y mandar el form
			document.getElementById('cargaMasivaMaterial').submit();

		}
	})



}

function csvDescripcion() { //Espera el resultado
	///Metodo que guarda el archivo csv
	//alert("entre a csvTimer.....");
	let timerInterval
	Swal.fire({
		text: 'Espera un momento por favor',
		timer: 4000,
		timerProgressBar: true,
		didOpen: () => {
			Swal.showLoading()
			timerInterval = setInterval(() => {
				const content = Swal.getHtmlContainer()
				if (content) {
					const b = content.querySelector('b')
					if (b) {
						b.textContent = Swal.getTimerLeft()
					}
				}
			}, 100)
		},
		willClose: () => {
			clearInterval(timerInterval)
		}
	}).then((result) => {
		/* Read more about handling dismissals below */
		if (result.dismiss === Swal.DismissReason.timer) {
			// cuando ya paso el tiempo 
			//CREAR VARIABLE STORAGE
			localStorage.congeladaDescripcion = "true";
			var congeladaAuxDC = localStorage.congeladaDescripcion;
			//Activar y mandar el form
			document.getElementById('cargaMasivaDC').submit();

		}
	})



}
function esperaCargaImg() { //Espera el resultado
	//console.log('Entre a espera carga img');
	//Mostrar el modal de espera	
	// Darle submit al form
	document.getElementById('CargaMasivaImagenes').submit();
	$('#modalCongeladoImg').modal('show');
	$.ajax({
		type: 'POST',
		data: {
			op: 'espera' //Mando al metodo 
		},
		url: '/PCM_QA/producto',

		success: function(res) {
			//	console.log('Entre  a la respues de ajax' + res);
			if (res == 'si') {
				//	console.log('la respuesta de ajax = si');
				$('#modalCongeladoImg').modal('hidden'); //ocultar el modal 
			} else {
				//	console.log('Se ocaciono un error'); // 
			}
		}
	});
}


function procesaCsv() {//23.MAYO.201
	//La funcion procesa los archivos csv que tengo en la lista
	//las funciones que tengo que checar son:
	waitcsvMateriales();//ATRIBUTOS
	waitcsvDc(); //DESCRIPCION COMERCIAL
	waitcsvMaterialesPlataformas();//ALTAS
	bajaMaterialesPlataforma();//BAJAS

	//ME ENTREGA UNA LISTA DE LAS FAMILIAS
	familiasExistentesCsv()


}
//Esta funcion nos sirve para validar si tengo que procesar algun archivo csv
function body(pagina) {
	waitcsvMateriales();
	waitcsvDc();
	waitcsvMaterialesPlataformas();
	bajaMaterialesPlataforma();
	//	waitcsv();
	previewNext(pagina);
	buscarDesMaterial();
	buscaridMaterial();
	buscarCategoria();
	buscarProveedor();
	//isSearching(pagina);

}
function bajaMaterialesPlataforma() {
	console.log('ENTRE A BAJA MATERIALES JS');
	var congeladaMaterialBaja = sessionStorage.congeladaMaterialBaja;
	if (congeladaMaterialBaja == "true") {
		//mostrar un modal del que no se pueda salir hasta que termine el metodo de abajo
		$('#modalCongelado').modal('show');

		$.ajax({

			type: 'POST',
			data: {
				op: 'ConnectMaterialesBaja'
			},
			url: '/PCM_QA/producto',

			success: function(res) {
				console.log(res);
				var arreglo = res.split("$");
				var respuestaOk = arreglo[0];
				var alerta = arreglo[1]
				//vamos a esperar un "OK"
				//o un "FALSE"
				if (respuestaOk == "OK") {
					//localStorage['congelada'] = false;
					$('#modalCongelado').modal('hide');
					//alert("El archivo de CSV ha sido cargado a la BD con exito")
					//crear un elemento nuevo en la pagina web, o algo asi
					Swal.fire({
						text: 'BAJAS: ' + alerta,
						icon: 'info',
						showDenyButton: false,
						showCancelButton: false,
						confirmButtonText: 'Aceptar',
					}).then((result) => {
						if (result.isConfirmed) {
							//location.href = "/PCM_QA/producto?op=Listar&pagina=1";
							location.href = "/PCM_QA/producto?op=RedireccionaCargaMasiva";

						} else if (result.isDenied) {
							location.href = "/PCM_QA/producto?op=RedireccionaCargaMasiva";
							//location.href = "/PCM_QA/producto?op=Listar&pagina=1";

						}
					})
				}
				if (res == "FALSE") {
					$('#modalCongelado').modal('hide');
					Swal.fire('El archivo de CSV tiene un error, revisele e intente de nuevo', '', 'info');
					//localStorage.congelada = "false"
					sessionStorage.congeladaMaterialBaja = "false";

				}


			}
		});
	}
	sessionStorage.congeladaMaterialBaja = "false";

}

function waitcsvMaterialesPlataformas() {
	var congeladaAuxMaterialesPlataforma = sessionStorage.congeladaMaterialPlataforma;
	if (congeladaAuxMaterialesPlataforma == "true") {
		//mostrar un modal del que no se pueda salir hasta que termine el metodo de abajo
		$('#modalCongelado').modal('show');

		$.ajax({

			type: 'POST',
			data: {
				op: 'ConnectMaterialesPlataforma'
			},
			url: '/PCM_QA/producto',

			success: function(res) {
				console.log(res);
				var arreglo = res.split("$");
				var respuestaOk = arreglo[0];
				var alerta = arreglo[1]
				//vamos a esperar un "OK"
				//o un "FALSE"
				if (respuestaOk == "OK") {
					//localStorage['congelada'] = false;
					$('#modalCongelado').modal('hide');
					//alert("El archivo de CSV ha sido cargado a la BD con exito")
					//crear un elemento nuevo en la pagina web, o algo asi
					Swal.fire({
						text: 'Modificaciones: ' + alerta,
						icon: 'info',
						showDenyButton: false,
						showCancelButton: false,
						confirmButtonText: 'Aceptar',
					}).then((result) => {
						if (result.isConfirmed) {
							//location.href = "/PCM_QA/producto?op=Listar&pagina=1";
							//ME TENGO QUE QUEDAR EN LA PAGINA DE CARGAS
							//location.href = "/PCM_QA/Comunes/RegistroMasivo.jsp";
							location.href = "/PCM_QA/producto?op=RedireccionaCargaMasiva";

						} else if (result.isDenied) {
							//location.href = "/PCM_QA/producto?op=Listar&pagina=1";
							location.href = "/PCM_QA/producto?op=RedireccionaCargaMasiva";

						}
					})
				}
				if (res == "FALSE") {
					$('#modalCongelado').modal('hide');
					Swal.fire('', 'El archivo de CSV tiene un error, revisele e intente de nuevo', 'info');
					//localStorage.congelada = "false"
					sessionStorage.congeladaMaterialPlataforma = "false";

				}


			}
		});
	}
	sessionStorage.congeladaMaterialPlataforma = "false";

}
function waitcsvMateriales() { //Este metodo se encarga de ejecutar el archio phyton
	//TOMAR EL VALOR DE LA VARIABLE STORAGE
	var congeladaAuxM = sessionStorage.congeladaMaterial;

	//var congelada = localStorage['congelada']
	if (congeladaAuxM == "true") {
		//mostrar un modal del que no se pueda salir hasta que termine el metodo de abajo
		$('#modalCongelado').modal('show');

		$.ajax({
			//ir al servlet producto y al case ConnectCargaMasivaMateriales

			type: 'POST',
			data: {
				op: 'ConnectCargaMasivaMateriales'
			},
			url: '/PCM_QA/producto',

			success: function(res) {
				//	console.log("La respuesta es" + res);
				var arreglo = res.split("$");
				var respuestaOk = arreglo[0];
				var alerta = arreglo[1]
				//vamos a esperar un "OK"
				//o un "FALSE"
				if (respuestaOk == "OK") {
					//localStorage['congelada'] = false;
					$('#modalCongelado').modal('hide');
					//alert("El archivo de CSV ha sido cargado a la BD con exito")
					//crear un elemento nuevo en la pagina web, o algo asi
					Swal.fire({
						text: 'Modificaciones' + alerta,
						icon: 'info',
						showDenyButton: false,
						showCancelButton: false,
						confirmButtonText: 'Aceptar',
					}).then((result) => {
						if (result.isConfirmed) {
							//location.href = "/PCM_QA/producto?op=Listar&pagina=1";
							location.href = "/PCM_QA/producto?op=RedireccionaCargaMasiva";

						} else if (result.isDenied) {
							//location.href = "/PCM_QA/producto?op=Listar&pagina=1";
							location.href = "/PCM_QA/producto?op=RedireccionaCargaMasiva";


						}
					})
				}
				if (res == "FALSE") {
					$('#modalCongelado').modal('hide');
					Swal.fire('', 'El archivo de CSV tiene un error, revisele e intente de nuevo', 'info');
					//localStorage.congelada = "false"
					sessionStorage.congeladaMaterial = "false";

				}


			}
		});
	}
	sessionStorage.congeladaMaterial = "false";

}
function waitcsvDc() { //Este metodo se encarga de ejecutar el archio phyton
	//TOMAR EL VALOR DE LA VARIABLE STORAGE
	var congeladaAuxDC = localStorage.congeladaDescripcion;

	//var congelada = localStorage['congelada']
	if (congeladaAuxDC == "true") {
		//mostrar un modal del que no se pueda salir hasta que termine el metodo de abajo
		$('#modalCongeladoDC').modal('show');

		$.ajax({
			//ir al servlet producto y al case ConnectCargaMasivaMateriales

			type: 'POST',
			data: {
				op: 'ConnectCargaMasivaDC'
			},
			url: '/PCM_QA/producto',

			success: function(res) {
				//console.log("La respuesta es" + res);
				var arreglo = res.split("$");
				var respuestaOk = arreglo[0];
				var alerta = arreglo[1]
				//vamos a esperar un "OK"
				//o un "FALSE"
				if (respuestaOk == "OK") {
					//localStorage['congelada'] = false;
					$('#modalCongelado').modal('hide');
				
					Swal.fire({
						text: alerta,
						icon: 'info',
						showDenyButton: false,
						showCancelButton: false,
						confirmButtonText: 'Aceptar',
					}).then((result) => {
						if (result.isConfirmed) {
							//location.href = "/PCM_QA/producto?op=Listar&pagina=1";
							location.href = "/PCM_QA/producto?op=RedireccionaCargaMasiva";

						} else if (result.isDenied) {
							//location.href = "/PCM_QA/producto?op=Listar&pagina=1";
							location.href = "/PCM_QA/producto?op=RedireccionaCargaMasiva";


						}
					})

				}
				if (res == "FALSE") {
					$('#modalCongelado').modal('hide');
					Swal.fire('', 'El archivo de CSV tiene un error, revisele e intente de nuevo', 'info');
					//localStorage.congelada = "false"
					sessionStorage.congeladaMaterial = "false";

				}


			}
		});
	}
	localStorage.congeladaDescripcion = "false";

}

//function waitcsv() {
//	//TOMAR EL VALOR DE LA VARIABLE STORAGE
//	var congeladaAux = localStorage.congelada;

//	//var congelada = localStorage['congelada']
//	if (congeladaAux == "true")

//	{
//		//alert("true");

//		//mostrar un modal del que no se pueda salir hasta que termine el metodo de abajo
//		$('#modalCongelado').modal('show');

//		$.ajax({
//			//ir al servlet producto y al case ConnectCargaMasiva

//			type: 'POST',
//			data: {
//				op: 'ConnectCargaMasivaMateriales'
//			},
//			url: '/PCM_QA/producto',
//			success: function (res) {
//				//vamos a esperar un "OK"
//				//o un "FALSE"
//				if (res == "OK") {
//					//localStorage['congelada'] = false;
//					$('#modalCongelado').modal('hide');
//					//alert("El archivo de CSV ha sido cargado a la BD con exito")
//					//crear un elemento nuevo en la pagina web, o algo asi
//					location.href = "/PCM_QA/producto?op=Listar&pagina=1";

//				}
//				if (res == "FALSE") {
//					$('#modalCongelado').modal('hide');
//					Swal.fire('El archivo de CSV tiene un error, revisele e intente de nuevo', '', 'info');
//					//localStorage.congelada = "false"
//					localStorage.congelada = "false";


//				}


//			}
//		});
//	}
//	localStorage.congelada = "false";

//}

function espera2(texto, tiempo) {
	let timerInterval
	Swal.fire({
		text: texto,
		timer: tiempo,
		timerProgressBar: true,
		didOpen: () => {
			Swal.showLoading()
			timerInterval = setInterval(() => {
				const content = Swal.getContent()
				if (content) {
					const b = content.querySelector('b')
					if (b) {
						b.textContent = Swal.getTimerLeft()
					}
				}
			}, 100)
		},
		willClose: () => {
			clearInterval(timerInterval)
		}
	}).then((result) => {
		/* Read more about handling dismissals below */
		if (result.dismiss === Swal.DismissReason.timer) { }
	})
}



function validacionRegistro() {
	var descripcionComercial = document.getElementById('descripcionComercial').value
	//var marcas = document.getElementById('marcas').value
	var gramaje = document.getElementById('gramaje').value
	//var familias = document.getElementById('familias').value
	if (descripcionComercial == "") {
		Swal.fire('', 'Registre la descripcion comercial', 'info');
	}
	if (gramaje == "") {
		Swal.fire('', 'Registre el gramaje', 'info');
	}


	if (gramaje != "" && descripcionComercial != "") {
		//if (familias != 0) {
		//atributos();
		//}
		checkImagenes();
	}

}


function atributos() {
	var aux = 0;
	for (var i = 0; i < document.getElementsByName('checks').length;) {
		if (document.getElementsByName('checks')[i].checked) { //AL MENOS UN ATRIBUTO SELECCIONADO
			aux = 1;

		}
		i++;
	}

	if (aux == 1) {
		checkImagenes();


	} else {
		Swal.fire('', 'Selecciona al menos un atributo', 'info');

	}
}


function decimales(e, identificador) {
	tecla = (document.all) ? e.keyCode : e.which;

	//Tecla de retroceso para borrar, siempre la permite
	if (tecla == 8) {
		return true;
	}
	if (tecla == 46) {
		return true;
	}

	// Patron de entrada, en este caso solo acepta numeros y letras
	patron = /[0-9]/;
	tecla_final = String.fromCharCode(tecla);
	if (parseFloat(document.getElementById(identificador).value) > 999.99) {
		Swal.fire('', 'Respeta la siguiente estructura: 000.00', 'info');
		document.getElementById(identificador).value = 0;
	}
	caracteresMax(identificador, '6');
	return patron.test(tecla_final);
	//fin  de caracteres 



}

/*
function sumarDias() {
	var fecha = new Date(document.getElementById('fechaInicio').value);
	document.getElementById('fechaFin').value = fecha.getFullYear() + '-' + (fecha.getMonth() + 1) + '-' + (fecha.getDate() + 2);
}
*/

function listadoPlataformas() {//listado de plataformas para reporte
	//console.log("Entre a listado plataformas");
	$.ajax({
		type: 'POST',
		data: {
			op: 'plataformasExistentes'
		},
		url: '/PCM_QA/producto',
		success: function(res) {
			//console.log("algo esta fallando : " + res);
			var valor = res.split(";");
			cantPlataformas = valor[0];
			for (var i = 1; i <= cantPlataformas; i++) {
				var plataformas = document.getElementById('plataformas');
				var option = document.createElement('option'); //Creamos la opcion
				option.innerHTML = valor[i]; //Metemos el texto en la opción
				plataformas.appendChild(option); //Metemos la opción en el select
				//	marcas.value = valor[i];
			}
		}
	});
}


function listadoCategoria() {//listado de categorias para reporte
	//console.log("Entre a listado plataformas");
	$.ajax({
		type: 'POST',
		data: {
			op: 'categoriasExistentes'
		},
		url: '/PCM_QA/producto',
		success: function(res) {
			var valor = res.split(";");
			cantCategorias = valor[0];
			for (var i = 1; i <= cantCategorias; i++) {
				var categorias = document.getElementById('categoria');
				var option = document.createElement('option'); //Creamos la opcion
				option.innerHTML = valor[i]; //Metemos el texto en la opción
				categorias.appendChild(option); //Metemos la opción en el select
				//	marcas.value = valor[i];
			}
		}
	});
}

function reportes() {
	listadoPlataformas();
	listadoCategoria();
}

function seleccionarPlataforma() {
	//Esta funcion sive para controlar que se haya seleciconado los selects para el reporte de materiales agregados por plataforma
	plataforma = document.getElementById("plataformas").value;
	categoria = document.getElementById("categoria").value;
	if (plataforma != "0" && categoria != "0") {
		document.getElementById('reporteMatPlataforma').submit();
	}
	if (plataforma == "0") {
		Swal.fire('', 'Seleccionar una plataforma', 'info');

	}
	if (categoria == "0") {
		Swal.fire('', 'Seleccionar una categoria', 'info');

	}
}

function reporteExcelPlataforma() { //Paso la info 
	//console.log("vamos a entrar el metodo");
	var plataforma = document.getElementById("plataformas").value;
	var categoria = document.getElementById("categoria").value;
	var fInicio = document.getElementById("fechaInicio").value;
	if (plataforma != "0") {
		if (fInicio != "") {
			//	console.log("voy a meter los valores en las variables ");
			document.getElementById("plataformasExcel").value = plataforma;
			document.getElementById("categoriaExcel").value = categoria;
			document.getElementById("fechaInicioExcel").value = fInicio;
			document.getElementById("fechaFinExcel").value = document.getElementById("fechaFin").value;
			document.getElementById('reporteMatPlataformaExcel').submit();


		} else {
			Swal.fire('', 'Seleccionar una fecha', 'info');

		}
	} else {
		Swal.fire('', 'Seleccionar una plataforma', 'info');

	}
	//console.log("Valores:  plataforma" +  plataforma + "Categoria: "+  categoria +  "fechaInicio:" + fInicio)  ;
}


function reporteSinInfo() {//Materiales con informacion faltante
	document.getElementById('reporteInfoFaltante').submit();
}

function verMas(idBoton, idMas) {
	//console.log("entre en ver mas");
	var controler = document.getElementById(idBoton).value;
	if (controler == "0") {
		document.getElementById(idBoton).value = 1; //ver collapse
		document.getElementById(idMas).setAttribute("class", "fa fa-caret-square-o-up");

	} else {
		document.getElementById(idBoton).value = 0;
		document.getElementById(idMas).setAttribute("class", "fa fa-caret-square-o-down");


	}

}

function adminImg(idMaterial) { //Esta funcion se encarga de mostrar todas las img de un material, para la parte de administracion
	espera();
	$.ajax({
		type: 'POST',
		data: {
			op: 'consultaRutas',
			idMaterial: idMaterial,
		},
		url: '/PCM_QA/producto',
		success: function(res) {
			var valor = res.split("|");
			//	console.log("RES:" + res);
			//Antes de abrir el modal, limpio 
			var divimagenes = document.getElementById('divImg'); //En este div estaran las img
			var cantimagenes = document.getElementById('cantImg'); //En este div estara la cant de img
			divimagenes.innerHTML = "";
			cantimagenes.innerHTML = "";
			//Abrir modal de imagenes
			$('#adminImg').modal('show');
			//Construir las imagenes mediante innerHTML
			var i = 1;
			var medida = "";
			var nombreImg = "";
			var info = "<div><span class=@badge badge-pill badge-info@> Cantidad de imagenes :" + (valor.length - 1) + "</span></div><br><div style=@text-align:center@><span style=@font-size:0.5cm@>" + valor[0] + "</span></div>";
			info = info.replaceAll('@', '"');
			cantimagenes.innerHTML += info;
			document.getElementById('cantImagenEU').value = (valor.length - 1);
			document.getElementById('cantImagenE').value = (valor.length - 1);


			while (i < valor.length) {
				nombreImg = valor[i].substring(16, valor[i].length);
				var imagen = "@" + valor[i] + "@";
				//console.log("SRC IMG:"+imagen);
				if (valor[i].includes("_app")) {
					medida = "300 x 300";
				} else {
					medida = "1200 x 1200";
				}
				var nodo = "";
				nodo += "<div class=@col-4@ id=@" + nombreImg + "@>" +
					"<img id=@imgfile" + i + "@ src=" + imagen + " style=@max-width:50%;max-height:50%@  onclick=imgGrande(" + imagen + ")>" +
					"<a href=" + imagen + " download target=@_blank@ ><i class=@fa fa-download@ style=@font-size: 14px; color:blue@></i></a><br>" +
					"<span class=@badge badge-pill badge-warning@>" + medida + "</span>" +
					"<input readonly class=@border border-white@ type=@text@ name=@numimagen" + i + "@ value=@" + nombreImg + "@>" +
					"<button id=@button" + nombreImg + "@ name=@button" + nombreImg + "@ type=@button@ onclick=@eliminarImagen('" + nombreImg + "')@ class=@btn btn-danger btn-sm@>x</button>"
				"</div>";


				nodo = nodo.replaceAll('@', '"');
				divimagenes.innerHTML += nodo;
				//	var aux = "imgfile" + i;
				//	previewFileIMG(aux, imagen);
				i = i + 1;
			}
		}
	});
}

function descargaImg() { //Funcion para saber que debo descargar
	var descarga = document.getElementById('selectDescarga').value; // select para saber que opcin debo descargar
	/*	if (descarga == "0") { //todas las img
			document.getElementById('divDescargaImg').style.visibility = 'hidden';
			Swal.fire({
				title: 'Descargar',
				text: 'Estas seguro de descargar todas las imagenes?',
				icon: 'warning',
				showDenyButton: true,
				showCancelButton: false,
				confirmButtonText: 'Descargar',
				confirmButtonColor: '#DE3815',
				denyButtonColor: '#1570DE',
				denyButtonText: 'Cancelar',
			}).then((result) => {
				if (result.isConfirmed) {
					//Redirecciono
					//location.href = "/PCM_QA/producto?op=prueba";
					descargaImgAll();
				} else if (result.isDenied) {
					//No hago nada
				}
			})
		}*/
	if (descarga == "1") { //por material
		//Poner como visible el input 
		document.getElementById('divDescargaImg').style.visibility = 'visible';

	}
	if (descarga == "2") { //por nombre
		//Poner como visible el input 
		document.getElementById('divDescargaImg').style.visibility = 'visible';


	}

}

function alertaModificacion() {
	var gramaje = document.getElementById('gramajeEditar').value;
	var descripcion = document.getElementById('descripcionComercialE').value;

	if (gramaje != "" && descripcion != "") {
		//Submit
		//modificacion();
		document.getElementById('formEdicion').submit();

	} else {

		Swal.fire({
			text: 'Informacion faltante, estas seguro de continuar?',
			icon: 'warning',
			showDenyButton: true,
			showCancelButton: false,
			confirmButtonText: 'SI',
			confirmButtonColor: '#DE3815',
			denyButtonColor: '#1570DE',
			denyButtonText: 'NO',
		}).then((result) => {
			if (result.isConfirmed) {
				//modificacion();
				document.getElementById('formEdicion').submit();
			} else if (result.isDenied) {
				var alerta = document.getElementById('alertaEdicion');
				var alert = "<div class='alert alert-info'>Completa los campos obligatorios</div>";
				alerta.innerHTML = alert;
			}
		})

	}
}

function imagenesDescarga() { //caso especifico de descarga de imagenes 
	var op = document.getElementById('selectDescarga').value
	var materiales = document.getElementById('idMaterialImg').value
	if (op == "1") {
		$.ajax({
			type: 'POST',
			data: {
				op: 'descargaImg',
				materiales: materiales,
			},
			url: '/PCM_QA/producto',
			success: function(res) {
				//console.log('La respuesta es:' + res);
				var valor = res.split("|"); //Separo las rutas de cada imagen 
				for (var i = 1; i < valor.length; i++) {
					if (valor[i] != "") {
						//console.log('Entre ' + i + ' ruta: ' + valor[i]);
						//var nombreImg = valor[i].substring(16, valor[i].length);
						var source =  valor[i];
						var a = document.createElement('a');
						var nombre = valor[i].substring(16, valor[i].length);
						a.download = nombre;
						a.target = '_blank';
						a.href = source;
						a.click();
					}

				}
				if (valor[0] != "") { //Si hubo error
					valor[0] = valor[0].substring(1, valor[0].length);
					Swal.fire('', 'Los siguientes materiales no tienen imagen' + valor[0], 'error');

				}
			}
		});

	}

	if (op == "2") { //Por nombre de imagen
		//console.log('Op 2 ');
		var nombreImg = materiales;
		$.ajax({
			type: 'POST',
			data: {
				op: 'descargaImg2',
				nombreImg: nombreImg,
			},
			url: '/PCM_QA/producto',
			success: function(res) {
				//console.log("Res:" + res);
				//Si la respuesta esta en blanco no redirecciono nada 
				if (res != "") {
					//	console.log('El resultado es: ' + res);
					var source = res;
					var a = document.createElement('a');
					var nombre = res.substring(16, res.length);
					a.download = nombre;
					a.target = '_blank';
					a.href = source;
					a.click();

				} else {
					Swal.fire('', 'La imagen no existe', 'info');

				}

			}
		});
	}
}


function imgGrande(id) {
	//console.log("Vamos bien , el valor es: " + id );
	var imagen = document.getElementById(id);
	$('#ampliarImg').modal('show');
	divImagen = document.getElementById('img');//Donde van a ir las img 
	divImagen = "";
	var nodo = "";
	nodo = "<img  class=@mx-auto d-block@ id=@imgfile" + i + "@ src=" + id + " style=@max-width:100%;max-height:100%@>";
	nodo = nodo.replaceAll('@', '"');
	//divImagen.innerHTML = nodo;
	document.getElementById("img").innerHTML = nodo;
	//Vamos a ponerle el nombre a la imagen 
	var nombreImg = id.substring(38, id.length);
	descripcion = document.getElementById('encabezado');//Donde van a ir la descripcion
	descripcion = "";
	var nodo1 = "";
	nodo1 = "<label>" + nombreImg + "</label>";
	descripcion.innerHTML = nodo1;
	document.getElementById("encabezado").innerHTML = nodo1;
}

function buscaridMaterial() {//Esta funcion regresa todos los numero de materiales existentes para agregarlos al buscador 
	//console.log('Vamos a buscar  por idMaterial');
	//Proveedor, categoria, idMaterial, descripcionMaterial
	$.ajax({
		type: 'POST',
		data: {
			op: 'idMaterial',
		},
		url: '/PCM_QA/producto',

		success: function(res) {
			//console.log('La respuesta que obtuve es :' + res);
			//En res tengo la repuesta, ahora solo la mando 
			var input = document.getElementById('idMaterialBuscador');
			var awesomplete = new Awesomplete(input, {
				minChars: 1,
				autoFirst: true,
				sort: false,
				maxItems: 5
			});
			awesomplete.list = res;
		}
	});

}

function buscarDesMaterial() {//Esta funcion regresa todos los numero de materiales existentes para agregarlos al buscador 
	//console.log('Vamos a buscar  por idMaterial');
	//Proveedor, categoria, idMaterial, descripcionMaterial
	$.ajax({
		type: 'POST',
		data: {
			op: 'descripcionMaterial',
		},
		url: '/PCM_QA/producto',

		success: function(res) {
			//console.log('descripcion material :' + res);
			//En res tengo la repuesta, ahora solo la mando 
			var input = document.getElementById('descripcionMaterialBuscador');
			var awesomplete = new Awesomplete(input, {
				minChars: 1,
				autoFirst: true,
				sort: false,
				maxItems: 5
			});
			awesomplete.list = res;
		}
	});

}

function buscarCategoria() {//Esta funcion regresa todos los numero de materiales existentes para agregarlos al buscador 
	//console.log('Vamos a buscar  por idMaterial');
	//Proveedor, categoria, idMaterial, descripcionMaterial
	$.ajax({
		type: 'POST',
		data: {
			op: 'categoria',
		},
		url: '/PCM_QA/producto',

		success: function(res) {
			//console.log('categoria:' + res);
			//En res tengo la repuesta, ahora solo la mando 
			var input = document.getElementById('categoriaBuscador');
			var awesomplete = new Awesomplete(input, {
				minChars: 1,
				autoFirst: true,
				sort: false,
				maxItems: 5
			});
			awesomplete.list = res;
		}
	});

}

function buscarProveedor() {//Esta funcion regresa todos los numero de materiales existentes para agregarlos al buscador 
	//console.log('Vamos a buscar  por idMaterial');
	//Proveedor, categoria, idMaterial, descripcionMaterial
	$.ajax({
		type: 'POST',
		data: {
			op: 'proveedor',
		},
		url: '/PCM_QA/producto',

		success: function(res) {
			//console.log('proveedor:' + res);
			//En res tengo la repuesta, ahora solo la mando 
			var input = document.getElementById('proveedorBuscador');
			var awesomplete = new Awesomplete(input, {
				minChars: 1,
				autoFirst: true,
				sort: false,
				maxItems: 5,
				match: false
			});
			awesomplete.list = res;
		}
	});

}
function filtroAdmin(pagina) {
	//	console.log('CAMBIOS');

	//Vamos a mostrar la barra de proceso 
	//	document.getElementById('espera').style.visibility = 'visible';

	//Sacamos los filtros
	var idMaterial = document.getElementById('idMaterialBuscador').value
	var descripcion = document.getElementById('descripcionMaterialBuscador').value
	var familia = document.getElementById('familiaBuscador').value
	var categoria = document.getElementById('categoriaBuscador').value
	var proveedor = document.getElementById('proveedorBuscador').value
	var plataforma = document.getElementById('plataformaBuscador').value
	var estatus = document.getElementById('estatusBuscador').value
	if (estatus != '') {
		if (estatus == 'Nuevo') { estatus = 'N' }
		if (estatus == 'Pendiente') { estatus = 'P' }
		if (estatus == 'Rechazado') { estatus = 'R' }
		if (estatus == 'Aceptado') { estatus = 'A' }
		// if (estatus == 'Inactivo') { estatus = 'I' }

	}
	//console.log(estatus+ ' : el estatus fue:')
	var aux = pagina;

	//console.log({ idMaterial, descripcion, familia, categoria, proveedor, plataforma, estatus });

	//Vamos a mandar los datos por ajax 
	$.ajax({
		type: 'POST',
		data: {
			op: 'filtrado',
			idMaterial: idMaterial,
			descripcion: descripcion,
			familia: familia,
			categoria: categoria,
			proveedor: proveedor,
			plataforma: plataforma,
			estatus: estatus,
			pagina: pagina
		},
		url: '/PCM_QA/producto',
		success: function(res) {
			//eliminar los hijos del nodo principal de la interfaz (div central)
			var tablaDinamica = document.getElementById('tablaDinamica');
			tablaDinamica.innerHTML = "";
			//generar de nuevo en el jsp/html los datos de estos datos recien obtenidos

			//console.log("Si funciona asi: "+res[0].idMaterial);
			//console.log(res);

			//generamos la tabla dinamica
			generarTablaDinamicaAdmin(res, aux);

			if (res == '') {
				Swal.fire('', 'No se encontraron coincidencias', 'error');
			}
			//} else {
			//Swal.fire('ERROR', 'No se encontraron coincidencias', 'error');
			//}
		}
	});

}

function filtro(pagina) {
	//	console.log('CAMBIOS');

	//Vamos a mostrar la barra de proceso 
	//	document.getElementById('espera').style.visibility = 'visible';

	//Sacamos los filtros
	var idMaterial = document.getElementById('idMaterialBuscador').value
	var descripcion = document.getElementById('descripcionMaterialBuscador').value
	var familia = document.getElementById('familiaBuscador').value
	var categoria = document.getElementById('categoriaBuscador').value
	var proveedor = document.getElementById('proveedorBuscador').value
	var plataforma = document.getElementById('plataformaBuscador').value
	var estatus = document.getElementById('estatusBuscador').value
	if (estatus != '') {
		if (estatus == 'Nuevo') { estatus = 'N' }
		if (estatus == 'Pendiente') { estatus = 'P' }
		if (estatus == 'Rechazado') { estatus = 'R' }
		if (estatus == 'Aceptado') { estatus = 'A' }
		//if (estatus == 'Inactivo') { estatus = 'I' }

	}
	//console.log(estatus+ ' : el estatus fue:')
	var aux = pagina;

	//console.log({ idMaterial, descripcion, familia, categoria, proveedor, plataforma, estatus });

	//Vamos a mandar los datos por ajax 
	$.ajax({
		type: 'POST',
		data: {
			op: 'filtrado',
			idMaterial: idMaterial,
			descripcion: descripcion,
			familia: familia,
			categoria: categoria,
			proveedor: proveedor,
			plataforma: plataforma,
			estatus: estatus,
			pagina: pagina
		},
		url: '/PCM_QA/producto',
		success: function(res) {
			//eliminar los hijos del nodo principal de la interfaz (div central)
			var tablaDinamica = document.getElementById('tablaDinamica');
			tablaDinamica.innerHTML = "";
			//generar de nuevo en el jsp/html los datos de estos datos recien obtenidos

			//console.log("Si funciona asi: "+res[0].idMaterial);
			//console.log(res);

			//generamos la tabla dinamica
			generarTablaDinamica(res, aux);

			if (res == '') {
				Swal.fire('', 'No se encontraron coincidencias', 'error');
			}
			//} else {
			//Swal.fire('ERROR', 'No se encontraron coincidencias', 'error');
			//}
		}
	});

}
function generarTablaDinamica(result, pagina) {
	var cuerpo = ``;

	//parte principal del cuerpo
	cuerpo += `
		<tbody id="myTable">
			`;

	for (var key in result) {
		cuerpo += `
			<tr>
				<td style="font-size: small;">`+ result[key].idMaterial + `</td>
				<td style="font-size: small;">`+ result[key].descripcionMaterial + `</td>
				<td style="font-size: small;">`+ result[key].descripcionFamilia + `</td>

		`;
		if (result[key].estatus == "N") {
			cuerpo += `<td style="background: lightblue; color: white; font-size: small;">Nuevo</td>`;
		}
		if (result[key].estatus == "A") {
			cuerpo += `<td style="background: lightgreen; color: white; font-size: small;">Aceptado</td>`;
		}
		if (result[key].estatus == "R") {
			cuerpo += `<td style="background: lightred; color: white; font-size: small;">Rechazado</td>`;
		}
		if (result[key].estatus == "P") {
			cuerpo += `<td style="background: lightyellow; color: black; font-size: small;">Pendiente</td>`;
		}
		//if (result[key].estatus == "I") {
		//cuerpo += `<td style="background: gray; color: white; font-size: small;">Inactivo</td>`;
		//}

		if (result[key].estatus == "P" || result[key].estatus == "N" || result[key].estatus == "R") {
			cuerpo += `
			<td><input type="hidden" name="id" value='`+ result[key].idMaterial + `'>
				<div id="`+ result[key].idMaterial + `">
					<div id="`+ result[key].idMaterial + `"> 
						<i class="fa fa-pencil-square-o" style="font-size: 24px; color: blue" onclick="editarMaterial('`+ result[key].idMaterial + `')">
						</i>
					</div>
				</div>
			</td>
			`;
		}

		cuerpo += `</tr></tbody>`;
		//console.log(key, result[key]);
	}

	var cabecera = `<table class="table table-sm">
							<thead class="thead-light">


								<th>Numero de material</th>
								<th>Descripcion material</th>
								<th>Descripcion Familia</th>
								<th>Estatus</th>
								<th>Acciones</th>
								<!-- 						 -->

								</tr>
							</thead>
						`+ cuerpo + `
					</table>`;

	//le agregamos a la tabla dinamica los valores obtenidos
	//console.log('Vamos a hacer cambios 2');
	document.getElementById('tablaDinamica').innerHTML = cabecera;
	pagina = parseInt(pagina)
	//vamos a cambiar los botones next y previous
	if (pagina != 1) {
		//document.getElementById("idprevious").className = "page-items";
		document.getElementById('idprevious').removeAttribute("href");
		document.getElementById('idprevious').setAttribute("onclick", "filtro(" + (pagina - 1) + ")");
	}

	document.getElementById('actual').innerHTML = 'Pagina' + pagina;

	if (document.getElementById('idnext') != null) {
		document.getElementById('idnext').removeAttribute("href");
		document.getElementById('idnext').setAttribute("onclick", "filtro(" + (pagina + 1) + ")");

	}
	//	document.getElementById('espera').style.visibility = 'hidden';

}

function generarTablaDinamicaAdmin(result, pagina) {
	var cuerpo = ``;

	//parte principal del cuerpo
	cuerpo += `
		<tbody id="myTable">
			`;

	for (var key in result) {
		cuerpo += `
			<tr>
				<td style="font-size: small;">`+ result[key].idMaterial + `</td>
				<td style="font-size: small;">`+ result[key].descripcionMaterial + `</td>
				<td style="font-size: small;">`+ result[key].descripcionFamilia + `</td>

		`;
		if (result[key].estatus == "N") {
			cuerpo += `<td style="background: lightblue; color: black; font-size: small;">Nuevo</td>`;
		}
		if (result[key].estatus == "A") {
			cuerpo += `<td style="background: lightgreen; color: black; font-size: small;">Aceptado</td>`;
		}
		if (result[key].estatus == "R") {
			cuerpo += `<td style="background: lightred; color: black; font-size: small;">Rechazado</td>`;
		}
		if (result[key].estatus == "P") {
			cuerpo += `<td style="background: lightyellow; color: black; font-size: small;">Pendiente</td>`;
		}
		//if (result[key].estatus == "I") {
		//cuerpo += `<td style="background: gray; color: white; font-size: small;">Inactivo</td>`;
		//}

		if (result[key].estatus == "P" || result[key].estatus == "N" || result[key].estatus == "R" || result[key].estatus == "A") {
			cuerpo += `
			<td><input type="hidden" name="id" value='`+ result[key].idMaterial + `'>
				<div id="`+ result[key].idMaterial + `">
					<div id="`+ result[key].idMaterial + `"> 
						<i class="fa fa-pencil-square-o" style="font-size: 24px; color: lightseagreen" onclick="editarAdmiMat('`+ result[key].idMaterial + `')">
						</i>
					</div>
				</div>
			</td>
			`;
		}
		//	if (result[key].estatus == "I"  )
		//	{
		//		cuerpo += `
		//		<td><input type="hidden" name="id" value='`+ result[key].idMaterial +`'>
		//			<div id="`+ result[key].idMaterial +`">
		//				<div id="`+ result[key].idMaterial +`"> 
		//					<i  style="font-size: 18px; color: blue" onclick="activarMatAdmin('`+ result[key].idMaterial +`')">
		//					Activar
		//					</i>
		//				</div>
		//			</div>
		//		</td>
		//		`;
		//	}

		cuerpo += `</tr></tbody>`;
		//console.log(key, result[key]);
	}

	var cabecera = `<table class="table table-sm">
							<thead class="thead-light">


								<th>Numero de material</th>
								<th>Descripcion material</th>
								<th>Descripcion Familia</th>
								<th>Estatus</th>
								<th>Acciones</th>
								<!-- 						 -->

								</tr>
							</thead>
						`+ cuerpo + `
					</table>`;

	//le agregamos a la tabla dinamica los valores obtenidos
	//console.log('Vamos a hacer cambios 2');
	document.getElementById('tablaDinamica').innerHTML = cabecera;
	pagina = parseInt(pagina)
	//vamos a cambiar los botones next y previous
	if (pagina != 1) {
		//document.getElementById("idprevious").className = "page-items";
		document.getElementById('idprevious').removeAttribute("href");
		document.getElementById('idprevious').setAttribute("onclick", "filtro(" + (pagina - 1) + ")");
	}

	document.getElementById('actual').innerHTML = 'Pagina' + pagina;

	if (document.getElementById('idnext') != null) {
		document.getElementById('idnext').removeAttribute("href");
		document.getElementById('idnext').setAttribute("onclick", "filtro(" + (pagina + 1) + ")");

	}
	//	document.getElementById('espera').style.visibility = 'hidden';

}

function consultaGeneral() { //FUNCIONA PARA LIMPIAR LOS FILTROS DE LA BUSQUEDA, CUANDO EL USUARIO SE ENCUENTRA EN CONSULTA GENERAL
	location.href = "/PCM_QA/producto?op=Listar&pagina=1";
}

function consultaGeneralAdmi() { //FUNCIONA PARA LIMPIAR LOS FILTROS DE LA BUSQUEDA, CUANDO EL USUARIO SE ENCUENTRA EN ADMIN MATERIALES
	location.href = "/PCM_QA/producto?op=AdministrarMateriales&pagina=1";

}
function consultaGeneralImg() { //Limpia filtros de busqueda 
	location.href = "/PCM_QA/producto?op=AdministrarImg&pagina=1";



}

function cargaInfo() {
	//vamos a cargar toda la informacion que se llena en los buscadores 
	buscarDesMaterial();
	buscaridMaterial();
	buscarCategoria();
	buscarProveedor();
	//<body onload="previewNextAdmin('${pagina}')">

}

function confirmar() {
	//Solo se debe mandar este mensaje si la variable tiene algo
	var control = document.getElementById('imgElim').value;
	if (control != '') {
		//console.log('EL valor de la variable es : ' + control)
		Swal.fire({
			text: 'Estas seguro de eliminar la imagen',
			icon: 'warning',
			showDenyButton: true,
			showCancelButton: false,
			confirmButtonText: 'Eliminar',
			confirmButtonColor: '#DE3815',
			denyButtonColor: '#1570DE',
			denyButtonText: 'Cancelar',
		}).then((result) => {
			if (result.isConfirmed) {
				var formulario = document.getElementById("eliminarImagen");
				formulario.submit();
			} else if (result.isDenied) {
				$('#adminImg').modal('hide');
			}
		})
	}

}
function cancelarEliminarImagen() { //Este metodo funciona para limpiar la variable donde se quedan las imagenes que deben eliminarse 
	document.getElementById('imgElim').value = '';
}

function filtroImg(pagina) {
	//Vamos a mostrar la barra de proceso 
	//	document.getElementById('espera').style.visibility = 'visible';

	//Sacamos los filtros
	var idMaterial = document.getElementById('idMaterialBuscador').value
	var descripcion = document.getElementById('descripcionMaterialBuscador').value
	var familia = document.getElementById('familiaBuscador').value
	var categoria = document.getElementById('categoriaBuscador').value
	var proveedor = document.getElementById('proveedorBuscador').value
	var aux = pagina;
	//Vamos a mandar los datos por ajax 
	$.ajax({
		type: 'POST',
		data: {
			op: 'filtradoImg',
			idMaterial: idMaterial,
			descripcion: descripcion,
			familia: familia,
			categoria: categoria,
			proveedor: proveedor,
			pagina: pagina
		},
		url: '/PCM_QA/producto',
		success: function(res) {
			//eliminar los hijos del nodo principal de la interfaz (div central)
			var tablaDinamica = document.getElementById('tablaDinamica');
			tablaDinamica.innerHTML = "";
			//generamos la tabla dinamica
			generarTablaDinamicaImg(res, aux);

			if (res == '') {
				Swal.fire('', 'No se encontraron coincidencias', 'error');
			}

		}
	});

}


function generarTablaDinamicaImg(result, pagina) {
	var cuerpo = ``;

	//parte principal del cuerpo
	cuerpo += `
		<tbody id="myTable">
			`;

	for (var key in result) {
		cuerpo += `
			<tr>
				<td style="font-size: small;">`+ result[key].idMaterial + `</td>
				<td style="font-size: small;">`+ result[key].descripcionMaterial + `</td>
				<td><input type="hidden" name="id" value='`+ result[key].idMaterial + `'>
				<div id="`+ result[key].idMaterial + `">
					<div id="`+ result[key].idMaterial + `">
						<i class="fa fa-pencil-square-o" style="font-size: 24px; color: lightseagreen" onclick="adminImg('`+ result[key].idMaterial + `')"></i>
					</div>
				</div>
			</td>
			`;
		cuerpo += `</tr></tbody>`;
		//console.log(key, result[key]);
	}

	var cabecera = `<table class="table table-sm">
							<thead class="thead-light">

								<th>Numero de material</th>
								<th>Descripcion material</th>
								<th>Acciones</th>
								</tr>
							</thead>
						`+ cuerpo + `
					</table>`;

	//le agregamos a la tabla dinamica los valores obtenidos
	//console.log('Vamos a hacer cambios 2');
	document.getElementById('tablaDinamica').innerHTML = cabecera;
	pagina = parseInt(pagina)
	//vamos a cambiar los botones next y previous
	if (pagina != 1) {
		//document.getElementById("idprevious").className = "page-items";
		document.getElementById('idprevious').removeAttribute("href");
		document.getElementById('idprevious').setAttribute("onclick", "filtroImg(" + (pagina - 1) + ")");
	}

	document.getElementById('actual').innerHTML = 'Pagina' + pagina;

	if (document.getElementById('idnext') != null) {
		document.getElementById('idnext').removeAttribute("href");
		document.getElementById('idnext').setAttribute("onclick", "filtroImg(" + (pagina + 1) + ")");

	}
	//	document.getElementById('espera').style.visibility = 'hidden';

}

//function modificacion () { //Espera el resultado
//	//console.log('Entre a espera carga img');
//	//Mostrar el modal de espera	
//	// Darle submit al form
//	//document.getElementById('formEdicion').submit();
//	$.ajax({
//		type: 'POST',
//		data: {
//			op: 'modificar' //Mando al metodo 
//		},
//		url: '/PCM_QA/producto',

//		success: function (res) {
//			//	console.log('Entre  a la respues de ajax' + res);
//			if (res.respuesta == 'listo') {
//					console.log('la respuesta de ajax = si');
//			} else {
//				console.log('Se ocaciono un error'); // 
//			}
//		}
//	});
//}

function elimMaterial() {//masivo
	var mat = document.getElementById('materialesEliminados').value

	if (mat != "") {
		Swal.fire({
			text: 'Estas seguro de eliminar el material?',
			icon: 'warning',
			showDenyButton: true,
			showCancelButton: false,
			confirmButtonText: 'Eliminar',
			confirmButtonColor: '#DE3815',
			denyButtonColor: '#1570DE',
			denyButtonText: 'Cancelar',
		}).then((result) => {
			if (result.isConfirmed) {
				Swal.fire({
					text: 'Los materiales a eliminar los siguientes: ' + mat + ' quieres continuar?',
					icon: 'warning',
					showDenyButton: true,
					showCancelButton: false,
					confirmButtonText: 'Eliminar',
					confirmButtonColor: '#DE3815',
					denyButtonColor: '#1570DE',
					denyButtonText: 'Cancelar',
				}).then((result) => {
					if (result.isConfirmed) {
						document.getElementById('eliminarMaterial').submit();
					} else if (result.isDenied) {
					}
				})
			} else if (result.isDenied) {
			}
		})
	}
	else {

		Swal.fire('', 'Agrega al menos un material', 'info');

	}


}


function eliminarMatAdmin() {
	var mat = document.getElementById('idMaterialEditar').value
	document.getElementById('materialesEliminados').value = mat;
	Swal.fire({
		text: 'Estas seguro de eliminar el material?',
		icon: 'warning',
		showDenyButton: true,
		showCancelButton: false,
		confirmButtonText: 'Eliminar',
		confirmButtonColor: '#DE3815',
		denyButtonColor: '#1570DE',
		denyButtonText: 'Cancelar',
	}).then((result) => {
		if (result.isConfirmed) {
			Swal.fire({
				text: 'El material a eliminar es el siguiente: ' + mat + 'Quieres continuar?',
				icon: 'warning',
				showDenyButton: true,
				showCancelButton: false,
				confirmButtonText: 'Eliminar',
				confirmButtonColor: '#DE3815',
				denyButtonColor: '#1570DE',
				denyButtonText: 'Cancelar',
			}).then((result) => {
				if (result.isConfirmed) {
					Swal.fire('', 'Eliminado', 'success');
					document.getElementById('eliminarMaterial').submit();
				} else if (result.isDenied) {
				}
			})
		} else if (result.isDenied) {
		}
	})

}

function activarMaterial() {//masivo
	var mat = document.getElementById('materialesActivados').value
	if (mat != "") {
		Swal.fire({
			text: 'Estas seguro de activar el material?',
			icon: 'warning',
			showDenyButton: true,
			showCancelButton: false,
			confirmButtonText: 'Activar',
			confirmButtonColor: '#DE3815',
			denyButtonColor: '#1570DE',
			denyButtonText: 'Cancelar',
		}).then((result) => {
			if (result.isConfirmed) {
				Swal.fire({
					text: 'Los materiales para activar son los siguientes: ' + mat + ' quieres continuar?',
					icon: 'warning',
					showDenyButton: true,
					showCancelButton: false,
					confirmButtonText: 'Activar',
					confirmButtonColor: '#DE3815',
					denyButtonColor: '#1570DE',
					denyButtonText: 'Cancelar',
				}).then((result) => {
					if (result.isConfirmed) {
						document.getElementById('activarMaterial').submit();
					} else if (result.isDenied) {
					}
				})
			} else if (result.isDenied) {
			}
		})
	}
	else {

		Swal.fire('', 'Agrega al menos un material', 'info');

	}


}
function activarMatAdmin(idmaterial) {
	Swal.fire({
		text: 'Estas seguro de activar el material?',
		icon: 'warning',
		showDenyButton: true,
		showCancelButton: false,
		confirmButtonText: 'Activar',
		confirmButtonColor: '#DE3815',
		denyButtonColor: '#1570DE',
		denyButtonText: 'Cancelar',
	}).then((result) => {
		if (result.isConfirmed) {
			Swal.fire({
				text: 'El material para activar es el siguiente: ' + idmaterial + ' quieres continuar?',
				icon: 'warning',
				showDenyButton: true,
				showCancelButton: false,
				confirmButtonText: 'Activar',
				confirmButtonColor: '#DE3815',
				denyButtonColor: '#1570DE',
				denyButtonText: 'Cancelar',
			}).then((result) => {
				if (result.isConfirmed) {
					Swal.fire('', 'Activado', 'success');
					location.href = "/PCM_QA/producto?op=activarMaterial&tipo=Admin&materialesActivados=" + idmaterial;
				} else if (result.isDenied) {
				}
			})
		} else if (result.isDenied) {
		}
	})

}

function reporteAtributos() {
	var familia = document.getElementById("familia").value
	var fecha1 = document.getElementById("fechaInicioA").value
	var fecha2 = document.getElementById("fechaFinA").value

	if (familia == "0") {
		Swal.fire('', 'Selecciona una familia', 'info');
	}
	if (fecha1 == "" || fecha2 == "") {
		Swal.fire('', 'Ingresa campo fecha', 'info');
	}
	if (fecha1 != "" && fecha1 != "" && fecha2 != "") {
		document.getElementById('reporteAtributos').submit();
	}

}

function bajaMateriales() {
	//Tenemos que mandar una alerta para confirmar la baja de materiales
	Swal.fire({
		text: 'Estas seguro de dar de baja los materiales?',
		icon: 'warning',
		showDenyButton: true,
		showCancelButton: false,
		confirmButtonText: 'Si',
		confirmButtonColor: '#DE3815',
		denyButtonColor: '#1570DE',
		denyButtonText: 'Cancelar',
	}).then((result) => {
		if (result.isConfirmed) {
			Swal.fire({
				text: 'Estas seguro de continuar?',
				icon: 'warning',
				showDenyButton: true,
				showCancelButton: false,
				confirmButtonText: 'Si',
				confirmButtonColor: '#DE3815',
				denyButtonColor: '#1570DE',
				denyButtonText: 'Cancelar',
			}).then((result) => {
				if (result.isConfirmed) {
					//Mando el form
					csvMaterialesBaja();

				} else if (result.isDenied) {
				}
			})
		} else if (result.isDenied) {
		}
	})
}

function cantMaterialesPlataformas() {
	console.log('SI ENTRE');
	$.ajax({
		type: 'POST',
		data: {
			op: 'INDEX',
		},
		url: '/PCM_QA/producto',
		success: function(res) {
			//console.log("Res:" + res);
			//Si la respuesta esta en blanco no redirecciono nada 
			if (res != "") {
				console.log('ENTRE A INDEX');
				//EL ORDEN ES :Merzava,duerova,b2b,b2c,imagenes,cava,wabi
				var valor = res.split(",");
				document.getElementById('MerzaVa').innerHTML = valor[0];
				document.getElementById('DueroVa').innerHTML = valor[1];
				document.getElementById('B2B').innerHTML = valor[2];
				document.getElementById('B2C').innerHTML = valor[3];
				document.getElementById('IMG').innerHTML = valor[4];
				document.getElementById('Cava').innerHTML = valor[5];
				document.getElementById('Wabi').innerHTML = valor[6];


			}

		}
	});
}

function modalImagenes() {
	console.log("Modal img");
	//abrimos el modal de img
	$('#modalInfoImagen').modal('show');

}
function infoExtra() {
	console.log("info extra");
	//abrimos el modal de img
	$('#modalMaterialesPlataforma').modal('show');
	//	
}



function validardatos() { //esta funcion me sirve para confirmar que el usuario que ingreso si existe
	var usuario = document.getElementById("nombreUsuario").value;
	espera();
	$.ajax({
		type: 'POST',
		data: {
			op: 'validoUsuario',
			usuario: usuario
		},
		url: '/PCM_QA/usuario',

		success: function(response) {
			if (response != "") {
				registrarToken(usuario, response);
			}
			else {
				Swal.fire('', 'El usuario no existe', 'info')

			}

		}
	});
}

function registrarToken(usuario, correo) {
	console.log("REGISTRAR TOKEN:" + usuario + correo);
	var token;
	var tokenMD5;
	//generamos una cadena aleatoria
	token = Math.random().toString(36).replace(/[^a-z]+/g, '');
	//var tokenMD5 = CryptoJS.MD5(token);
	var tokenMD5 = token;
	//vamos a mandar el usuario y el token para registrarlos a la bd
	//   console.log("encriptacion " + usuarioMD5 + "token" + tokenMD5);
	$.ajax({
		type: 'POST',
		data: {
			op: 'registroToken',
			usuario: usuario,
			token: tokenMD5
		},
		url: '/PCM_QA/usuario',
		success: function(response) {
			console.log(response);
			if (response == "si") {
				mandarCorreo(usuario, correo, tokenMD5);
			}
			else {
				Swal.fire('', 'Hubo un problema, vuelve a intentarlo', 'info')
			}
		}
	});
}


function mandarCorreo(usuario, correo, tokenMD5) {
	console.log("MANDAR CORREO:" + usuario + correo + tokenMD5);
	console.log("entre a mandar correo");
	$.ajax({
		type: 'POST',
		data: {
			op: 'restablecerPass',
			usuario: usuario,
			correo: correo,
			tokenMD5: tokenMD5
		},
		url: '/PCM_QA/usuario',
		success: function(response) {
			console.log("el usuario es:" + response);
			if (response == "verdadero") {
				timer();
			}
			else {
				Swal.fire('', 'Hubo un problema, vuelve a intentarlo', 'info')
			}
		}
	});
}

function timer() {
	let timerInterval
	Swal.fire({
		title: '',
		html: 'En breve recibira un correo para cambiar tu password.',
		timer: 8000,
		timerProgressBar: true,
		didOpen: () => {
			Swal.showLoading()
			const b = Swal.getHtmlContainer().querySelector('b')
			timerInterval = setInterval(() => {
				b.textContent = Swal.getTimerLeft()
			}, 100)
		},
		willClose: () => {
			clearInterval(timerInterval)
		}
	}).then((result) => {
		/* Read more about handling dismissals below */
		if (result.dismiss === Swal.DismissReason.timer) {
		}
	})
	setTimeout(function() { window.location.href = "http://10.1.0.40:8080"; }, 2000);

}

//METODO PARA CAMBIO DE PASS cuando la olvida
function cambioPass(usuario, pass) {
	var aux2 = usuario + pass;
	var passEncriptada = CryptoJS.MD5(aux2);
	//Tengo que pasar el valor a otra variable
	var pass = passEncriptada.toString();
	connect(usuario, pass);
}

function connect(usuario, pass) {

	$.ajax({
		type: 'POST',
		data: {
			op: 'CambioPass',
			user: usuario,
			pass: pass

		},
		url: 'usuario',
		success: function(res) {
			if (res == "true") {
				//				$('#modalRedireccionar').modal('show');
				setTimeout('redireccionar()', 5000);
				Swal.fire('', 'Password cambiado correctamente', 'success');

			}
		}
	});
}



function validarPass() { //esta funcion me sirve para confirmar que el usuario que ingreso si existe
	var usuario = document.getElementById('nombreUsuario').value;
	var passnueva = document.getElementById('nuevoPass').value;
	var passconfirmacion = document.getElementById('confirmarPass').value;

	//tengo que tomar el token
	url = window.location.href
	url = url.split("aux=")
	token = url[1];

	if (passnueva == passconfirmacion) {//si son iguales vamos a continuar
		if (passconfirmacion.length > 4) {
			//Tenemos que validar en usuario por ajax

			$.ajax({
				type: 'POST',
				data: {
					op: 'nombreUsuario',
					usuario: usuario
				},
				url: 'usuario',
				success: function(res) {
					if (res == "") {
						Swal.fire('', 'El usuario no existe', 'error');

					} else {
						//Valido si el token es para el usaurio y si esta disponible
						//cambio de contraseña
						tokenValido(usuario, token, passconfirmacion);

					}
				}
			});


			//fin ajax
		} else {
			Swal.fire('', 'Password debil', 'info');

		}
	} else {
		Swal.fire('', 'Los password no son iguales', 'info');

	}

}

function tokenValido(user, token, passconfirmacion) {
	$.ajax({
		type: 'POST',
		data: {
			op: 'ValidaToken',
			user: user,
			token: token
		},
		url: 'usuario',
		success: function(res) {
			if (res == "NO") {
				Swal.fire('', 'Datos incorrectos', 'error');

			} else {
				//
				cambioPass(user, passconfirmacion);

			}
		}
	});
}

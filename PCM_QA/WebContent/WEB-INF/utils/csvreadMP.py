import csv
import sys
import pyodbc
import re

# GLOBAL VARIABLES
connection = None
user_bd = r'UsrPos'
pass_bd = r'P0s&Usr'
bd_name = r'ADU_PCM_Calidad'
ip_bd = '10.1.0.11'

noMat = "No existen los siguientes materiales: "
siMat = "Materiales asignados correctamente: "
noUm = "Los siguientes UM no existen para su numero de material: "
alerta = ""
noPlataforma="No existe la plataforma con ID: "

#DATOS EN EL ARCHIVO CSV: IDMATERIAL, IDPLATAFORMA, UM, DESTACADO

# Create a SQL Server database connection.
def createDBConnection(server, database, user, password):
    '''
    Take inputs server instance name, database name, username and password
    Return a SQL Server database connection
    '''

    global connection

    try:
        connection = pyodbc.connect(Driver="{SQL Server}",
                                    Server=server,
                                    Database=database,
                                    UID=user,
                                    PWD=password,
                                    autocommit=True)
    except Exception as e:
        print("Error al conectar con la base de datos: "+str(e))


# Close the database connection
def closeDBConnection():
    '''Take input connection and close the database connection'''
    global connection

    try:
        connection.close()
    except pyodbc.ProgrammingError:
        pass


def read_csv(csv_path, idusuario, ip_bd, bd_name, user_bd, pass_bd):
    #invocamos las variables globales
    global siMat
    global noMat
    global alerta
    global noUm
    global noPlataforma

    with open(csv_path, mode='r') as csv_file:
        csv_reader = csv.DictReader(csv_file)
        try:
            # get fieldnames from DictReader object and store in list
            headers = csv_reader.fieldnames

            #print("Headers: ",headers)

            num_cols = len(headers)

            # generamos un for por cada registro del archivo de csv
            for row in csv_reader:
                #EL PRIMER REGISTRO DEL CSV ES EL IDPLATAFORMA
                res_idPlataforma = row[headers[0]]
                existe_idPlataforma = False

                #EL SEGUNDO REGISTRO DEL CSV ES EL IDMATERIAL
                res_idMaterial = row[headers[1]]
                existe_idMaterial = False

                 #EL TERCER REGISTRO DEL CSV ES LA UNIDAD DE MEDIDA 
                res_um = row[headers[2]].upper()
                existe_um = False

                 #EL CUARTO REGISTRO DEL CSV ES LA UNIDAD DE MEDIDA 
                res_destacado= row[headers[3]]

                #### REGLA 1 ##################################################
                # creamos la conexion a la BD
                createDBConnection(ip_bd, bd_name, user_bd, pass_bd)

                # obtenemos el cursor de la conexion a la bd
                cursor = connection.cursor()

                # ejecutamos el query
                cursor.execute('select idPlataforma from plataformas where idPlataforma= ' + res_idPlataforma)
                #print('select idPlataforma from plataformas where idPlataforma= ' + res_idPlataforma)

                # accedemos al result set
                rows = cursor.fetchall()
                if(rows == []):
                    existe_idPlataforma = False
                    noPlataforma = noPlataforma + res_idPlataforma +" , "
                else:
                    for row1 in rows:
                        if (row1.idPlataforma == None):
                            existe_idPlataforma = False
                            noPlataforma = noPlataforma + res_idPlataforma +" , "
                        else:
                            existe_idPlataforma = True

                cursor.close()
                closeDBConnection()
                ##############################################################

                 #### REGLA 2 ##################################################
                # creamos la conexion a la BD
                createDBConnection(ip_bd, bd_name, user_bd, pass_bd)

                # obtenemos el cursor de la conexion a la bd
                cursor = connection.cursor()

                # ejecutamos el query
                cursor.execute('select idMaterial from materiales where idMaterial= ' + res_idMaterial)
                #print('select idMaterial from materiales where idMaterial= ' + res_idMaterial)

                # accedemos al result set
                rows = cursor.fetchall()
                if(rows == []):
                    existe_idMaterial = False
                    noMat = noMat + res_idMaterial +" , "
                else:
                    for row1 in rows:
                        if (row1.idMaterial == None):
                            existe_idMaterial = False
                            noMat = noMat + res_idMaterial +" , "
                        else:
                            existe_idMaterial = True

                cursor.close()
                closeDBConnection()
                ##############################################################
                      #### REGLA 3 ##################################################
                # creamos la conexion a la BD
                createDBConnection(ip_bd, bd_name, user_bd, pass_bd)

                # obtenemos el cursor de la conexion a la bd
                cursor = connection.cursor()

                # ejecutamos el query
                sql="select um from materialessap where um = ? and idMaterial = ?"

                try:
                    #print("try execute:")
                    #print("select idMaterial from materialessap where um = '"+res_um+"' and idMaterial = '"+res_idMaterial+"'")
                    cursor.execute(sql, [res_um, res_idMaterial])
                except Exception as e:
                    print("Error al ejecutar el select: "+str(e))

                # accedemos al result set
                rows = cursor.fetchall()

                #print("---------")
                #for row1 in rows:
                #    print("um: ",row1.um)
                #print("----------")

                if(rows == []):
                    existe_um = False
                    noUm = noUm + res_um + "," +  res_idMaterial +" , " 
                else:
                    for row1 in rows:
                        if (row1.um == None or row1.um == ""):
                            existe_um = False
                            noUm = noUm + res_um + "," +  res_idMaterial +" , " 
                        else:
                            existe_um = True

                cursor.close()
                closeDBConnection()
                ##############################################################
                #for i in range(num_cols):
                try:
                    #print("if existe_idMaterial == True and existe_idPlataforma== True and existe_um==True")
                    #print("     existe_idMaterial:",existe_idMaterial)
                    #print("     existe_idPlataforma: ",existe_idPlataforma)
                    #print("     existe_um: ",existe_um)
                    if (existe_idMaterial == True and existe_idPlataforma== True and existe_um==True): #Deben existir todos para poder procesar el archivo
                        siMat= siMat + res_idMaterial + ","
                        # creamos la conexion a la BD
                        createDBConnection(ip_bd, bd_name, user_bd, pass_bd)

                        # obtenemos el cursor de la conexion a la bd
                        cursor = connection.cursor()

                        # ejecutamos el query
                        params = (res_idMaterial, res_idPlataforma, res_um, idusuario, res_destacado)
                                
                        #print(params)
                        cursor.execute("{CALL materialAceptado (?,?,?,?,?)}", params)
                        connection.commit()

                        cursor.close()
                        closeDBConnection()
                        
                        #aqui vamos a poner cada fila(row) en el historial

                        createDBConnection(ip_bd,bd_name,user_bd,pass_bd)
                        cursor = connection.cursor()
            
                        #aqui le pasamos los parametros para el procedimiento almacenado(numero_material,p,id_usuario)
                        params = (res_idMaterial,"A",idusuario)
                                
                        #Ejecutamos el procedimiento almacenado
                        cursor.execute("{CALL historial (?,?,?)}",params)
                        connection.commit()
            
                        cursor.close()
                        closeDBConnection()
                except Exception as e:
                    print("Error en ultimo if: "+str(e))
                            
            ##############################################################
            if(noMat == "No existen los siguientes materiales: "):
                noMat =""
            if(siMat ==  "Materiales asignados correctamente: "):
                siMat =""
            if(noUm == "Los siguientes UM no existen para su numero de material: "):
                noUm =""
            if(noPlataforma == "No existe la plataforma con ID: "):
                noPlataforma =""
            alerta = siMat + noMat + noUm + noPlataforma
            print(alerta)        
        except Exception as e:
            print("error: " + str(e)) 

if __name__ == '__main__':
    csv_path = sys.argv[1]
    id_usuario = sys.argv[2]

    read_csv(csv_path, id_usuario, ip_bd, bd_name, user_bd, pass_bd)
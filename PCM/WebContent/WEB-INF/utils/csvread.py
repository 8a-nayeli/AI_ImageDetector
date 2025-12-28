import csv
import sys
import pyodbc
import re

# GLOBAL VARIABLES
connection = None
user_bd = r'UsrPos'
pass_bd = r'P0s&Usr'
bd_name = r'ADU_PCM'
ip_bd = '10.1.0.11'

noMat = "No existen los siguientes materiales: "
siMat = "Los siguientes materiales se modificaron correctamente: "
alerta = ""
contFilas = 0

# Create a SQL Server database connection.
def createDBConnection(server, database, user, password):
    '''
    Take inputs server instance name, database name, username and password
    Return a SQL Server database connection
    '''

    global connection

    connection = pyodbc.connect(Driver="{SQL Server}",
                                Server=server,
                                Database=database,
                                UID=user,
                                PWD=password,
                                autocommit=True)


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
    global contFilas
    global siMat
    global noMat
    global alerta

    with open(csv_path, mode='r') as csv_file:
        csv_reader = csv.DictReader(csv_file)
        try:
            # get fieldnames from DictReader object and store in list
            headers = csv_reader.fieldnames

            # print(headers)

            num_cols = len(headers)

            # generamos un for por cada registro del archivo de csv
            for row in csv_reader:
                contFilas = contFilas + 1
       
                res_idMaterial = row[headers[0]]
                existe_idMaterial = False

                # obtenemos unos datos necesario de la BD para la primer insercion

                #### REGLA 1 ##################################################
                # creamos la conexion a la BD
                createDBConnection(ip_bd, bd_name, user_bd, pass_bd)

                # obtenemos el cursor de la conexion a la bd
                cursor = connection.cursor()

                # ejecutamos el query
                cursor.execute('select top 1 idMaterial from materiales where idMaterial = ' + res_idMaterial)

                # accedemos al result set
                rows = cursor.fetchall()
                for row1 in rows:
                    # print(row.idMaterial)
                    if (row1.idMaterial == None):
                        existe_idMaterial = False
                        noMat = noMat + res_idMaterial +" , "
                    else:
                        existe_idMaterial = True
                        siMat = siMat + res_idMaterial + " , "

                cursor.close()
                closeDBConnection()
                ##############################################################

                if (existe_idMaterial == True):
                    num_cols_din = num_cols - 1

                    for i in range(num_cols_din):
                        # asegurarnos de que exista el idmaterial y el idmarca
                        if (existe_idMaterial == True):
                            # insertaremos el nombre del atributo y su descripcion
                            nombre_atributo = headers[i + 1]  # +1 porque las columnas fijas son 1
                            
                            descripcion_atributo = row[headers[i + 1]]

                            #### Registramos el atributo ##################################

                            # creamos la conexion a la BD
                            createDBConnection(ip_bd, bd_name, user_bd, pass_bd)

                            # obtenemos el cursor de la conexion a la bd
                            cursor = connection.cursor()

                            # ejecutamos el query
                            params = (res_idMaterial, descripcion_atributo, nombre_atributo, idusuario)
                            
                            #print(params)
                            if(descripcion_atributo != "" or descripcion_atributo != None):
                                cursor.execute("{CALL materialAtributoNuevo (?,?,?,?)}", params)
                                connection.commit()

                            cursor.close()
                            closeDBConnection()
                    
                    #aqui vamos a poner cada fila(row) en el historial

                    createDBConnection(ip_bd,bd_name,user_bd,pass_bd)
                    cursor = connection.cursor()

                    #aqui le pasamos los parametros para el procedimiento almacenado(numero_material,p,id_usuario)
                    params = (res_idMaterial,"P",idusuario)
                    
                    #Ejecutamos el procedimiento almacenado
                    cursor.execute("{CALL historial (?,?,?)}",params)
                    connection.commit()

                    cursor.close()
                    closeDBConnection()

                    ##############################################################

                # print(res_idMaterial, res_DescripcionComercial, res_idMarca, res_gramaje)
            if(noMat == 'No existen los siguientes materiales: '):
                alerta = siMat
            else:
                alerta = siMat+noMat
            print(alerta)        
        except Exception as e:
            print("error: " + str(e)) 


if __name__ == '__main__':
    csv_path = sys.argv[1]
    id_usuario = sys.argv[2]

    read_csv(csv_path, id_usuario, ip_bd, bd_name, user_bd, pass_bd)
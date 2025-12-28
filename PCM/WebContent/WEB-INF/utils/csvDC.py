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
   #las variables globales se deben invocar primero, para poder leerlas y escribirlas despues 
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
            aux = False

            if (num_cols == 3):
                aux = True
            else:
                aux = False


            # generamos un for por cada registro del archivo de csv
            for row in csv_reader:
            
                res_idMaterial = row[headers[0]]
                res_DescripcionComercial = row[headers[1]]
                res_Gramaje = row[headers[2]]
                existe_idMaterial = False

                # obtenemos unos datos necesario de la BD para la primer insercion

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

                # -----------------------------------------------------------------
                # creamos la conexion a la BD
                # ahora podemos hacer una insercion en la BD
                if(existe_idMaterial == True and aux == True):
                    if len(res_DescripcionComercial) > 0: #Vemos si la descripcionComercial esta vacia
                        createDBConnection(ip_bd, bd_name, user_bd, pass_bd)
                        cursor = connection.cursor()
                        sql="update materiales set descripcionComercial=? where idmaterial=?"
                        cursor.execute(sql, [res_DescripcionComercial, res_idMaterial])
                        connection.commit()
                        cursor.close()
                        closeDBConnection()

                    if len(res_Gramaje) > 0: #Vemos si el gramaje estan vacio
                        createDBConnection(ip_bd, bd_name, user_bd, pass_bd)
                        cursor = connection.cursor()
                        sql="update materiales set gramaje=? WHERE idmaterial=?"
                        cursor.execute(sql, [res_Gramaje, res_idMaterial])
                        connection.commit()
                        cursor.close()
                        closeDBConnection()
            
            #imprimimos la lista de los materiales que no existen en la BD
           
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
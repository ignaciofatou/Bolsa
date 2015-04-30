/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//http://www.hermosaprogramacion.com/2014/07/mysql-java-conectar-como.html
//http://fernando-gaitan.com.ar/conectar-java-con-mysql-en-netbeans/

/**
 *
 * @author Ignacio
 */
public class BaseDeDatos {
    
    //Constantes
    private final String DRIVER  = "com.mysql.jdbc.Driver";
    private final String CAD_CON = "jdbc:mysql://";
    private final String HOST    = "localhost";
    private final String PUERTO  = "";
    private final String BBDD    = "bolsavalores";
    private final String USUARIO = "root";
    private final String CLAVE   = "";
    
    //Atributos
    private Connection con = null;
    
    public BaseDeDatos(){
        //Establecemos el Registro
        setRegistro();
        
        //Establecemos la Conexion
        setConexion();
    }
    //Establece el Registro
    private void setRegistro(){
        try{
            Class.forName(DRIVER).newInstance();
            System.out.println("Registro Correcto");
        }catch(ClassNotFoundException cl){
            System.out.println(cl.getMessage());            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    //Establece la Conexion
    private void setConexion(){
        try {
            con = DriverManager.getConnection(getCadenaConexion(), USUARIO, CLAVE);
            System.out.println("Conexion Correcta");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }
    //Obtenemos la Cadena de Conexion
    private String getCadenaConexion(){
        String cadConexion = CAD_CON + HOST;
        
        //Si se Indica el Numero de Puerto
        if (!PUERTO.isEmpty())
            cadConexion = cadConexion + PUERTO;

        //Retornamos la Cadena de Conexion
        return cadConexion + "/" + BBDD;
    }    
    //Cerramos la Conexion
    public void cerrar() {
        try {
            if (con != null)
                con.close();
        }catch(SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
        }         
    }
    //Retornamos la Conexion
    public Connection getConexion() {
        return con;
    }
}

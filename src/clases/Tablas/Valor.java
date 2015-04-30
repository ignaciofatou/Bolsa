/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.Tablas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Ignacio
 */
public class Valor {

    //Constantes
    private final String COD_VALOR   = "COD_VALOR";
    private final String COD_CAT     = "COD_CAT";
    private final String DECIMALES   = "DECIMALES";
    private final String DESCRIPCION = "DESCRIPCION";
    private final int    DECIMAL_DEF = 3;
    
    //Constantes de BBDD
    private final String QUERY_INSERT_VALOR   = "INSERT INTO VALORES (COD_VALOR, COD_CAT, DECIMALES, DESCRIPCION) VALUES (?, ?, ?, ?)";
    private final String QUERY_SELECT_VALORES = "SELECT COUNT(1) FROM VALORES WHERE COD_VALOR = ?";
    private final String QUERY_DATOS_VALOR    = "SELECT COD_VALOR, FECHA, APERTURA, MAXIMO, MINIMO, CIERRE, VOLUMEN FROM DATOS_VALORES WHERE COD_VALOR = ? ORDER BY FECHA ASC";
    
    //Atributos
    private String codValor;
    private String codCategoria;
    private int    numDecimales;
    private String descripcion;
    
    //ArrayList de Objetos de tipo DatoValor
    private ArrayList<DatoValor> datosValor = new ArrayList();
    
    //Atributos de BBDD
    private Connection con;
    
    //Constructor que a partir del Result asigna el valor a los Atributos
    public Valor(Connection con, java.sql.ResultSet rs){
        //Guardamos la Conexion
        this.con = con;
        
        try{
            this.codValor     = rs.getString(COD_VALOR);
            this.codCategoria = rs.getString(COD_CAT);
            this.numDecimales = rs.getInt(DECIMALES);
            this.descripcion  = rs.getString(DESCRIPCION);
        }catch(java.sql.SQLException ex){
            System.out.println(ex.getMessage());
        }
        
        //Recuperamos los Datos de la Tabla de Datos Valores
        cargaTablaDatosValor();
    }

    //Constructor Normal
    public Valor(String codValor, String codCategoria, int numDecimales, String descripcion) {
        this.codValor     = codValor;
        this.codCategoria = codCategoria;
        this.numDecimales = numDecimales;
        this.descripcion  = descripcion;
    }
    
    //Constructor Simplificado
    public Valor(String codValor, String codCategoria) {
        this.codValor     = codValor;
        this.codCategoria = codCategoria;
        this.numDecimales = DECIMAL_DEF;
        this.descripcion  = codValor;
    }

    //Recupera los Datos de la Tabla de Datos Valores en un ArrayList de DatoValor
    //Filtramos por COD_VALOR
    private void cargaTablaDatosValor(){

        try{
            //Ejecutamos la Query Filtando por Codigo de Valor
            PreparedStatement cmd = con.prepareStatement(QUERY_DATOS_VALOR);
            cmd.setString(1, codValor);
            ResultSet rs = cmd.executeQuery();
            
            //Recorremos todos los Datos de Entrada
            while(rs.next()){
                //Nuevo Campo de Dato Valor
                DatoValor nuevoDatoValor = new DatoValor(rs);
                
                //Añadimos nuevo campo a la lista
                datosValor.add(nuevoDatoValor);
                
                //Traza
                System.out.println(nuevoDatoValor.toString());
            }            
        }catch(Exception ex){
            System.out.println("Error Recuperando los Datos del Valor: " + this.codValor);
        }
    }
    
    
    //Inserccion en la Tabla de VALORES
    public boolean insertValorBBDD(){
        
        //Si el Valor No Existe en BBDD -> Es Valido para Insertar
        if (!isValores(this.codValor)){
            try{
                PreparedStatement cmd = con.prepareStatement(QUERY_INSERT_VALOR);
                cmd.setString(1, this.codValor);
                cmd.setString(2, this.codCategoria);
                cmd.setInt(3, this.numDecimales);
                cmd.setString(4, this.descripcion);
                cmd.executeUpdate();
                System.out.println("Insertado el Valor: " + this.codValor + " en la Tabla de VALORES");
                
                //Se ha Insertado
                return true;

            }catch(Exception ex){
                System.out.println("Error al Insertar el Valor: " + this.codValor + " en la Tabla de VALORES");
                ex.printStackTrace();
                return false;
            }
            
        }
        //No se ha Insertado
        return false;
    }    

    //Comprueba que el Codigo de Valor Exista en la Tabla VALORES
    public boolean isValores(String codValor){        
        try{
            PreparedStatement cmd = con.prepareStatement(QUERY_SELECT_VALORES);
            cmd.setString(1, codValor);
            java.sql.ResultSet rs = cmd.executeQuery();
            
            if (rs.next())
                return (rs.getInt(1) == 1);
            else
                return false;            

        }catch(Exception ex){
            return false;
        }
    }
    
    @Override
    public String toString(){
        return COD_VALOR + ": " + this.getCodValor() + ", " + COD_CAT + ": " + this.getCodCategoria() + ", " + DECIMALES + ": " + this.getNumDecimales() + ", " + DESCRIPCION + ": " + this.getDescripcion();
    }   

    /**
     * @return the codValor
     */
    public String getCodValor() {
        return codValor;
    }

    /**
     * @return the codCategoria
     */
    public String getCodCategoria() {
        return codCategoria;
    }

    /**
     * @return the numDecimales
     */
    public int getNumDecimales() {
        return numDecimales;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }    
}

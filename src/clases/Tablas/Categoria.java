/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.Tablas;

import clases.DatosMegaBolsa.MegaBolsa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Ignacio
 */
public class Categoria {

    //Constantes
    private final String COD_CAT      = "COD_CAT";
    private final String DESCRIPCION  = "DESCRIPCION";
    private final String URL          = "URL";
    private final String FORMATOFECHA = "FORMATOFECHA";
    private final String COMODIN      = "COMODIN";
    private final String EXTENSION    = "EXTENSION";
    
    //Constantes de BBDD
    private final String QUERY_VALORES  = "SELECT COD_VALOR, COD_CAT, DECIMALES, DESCRIPCION FROM VALORES WHERE COD_CAT = ? ORDER BY COD_VALOR ASC";
    private final String QUERY_PATRONES = "SELECT COD_CAMPO, ORDEN, TIPO_DATO FROM PATRON_DATOS ORDER BY ORDEN ASC";
    
    //Atributos
    private String codCategoria;
    private String descripcion;
    private String urlDatosFichero;
    private String formFechaFichero;
    private String comodinFichero;
    private String extensionFichero;

    //ArrayList de Objetos de tipo Valor y de tipo Patron
    private ArrayList<Valor>  valores  = new ArrayList();
    private ArrayList<Patron> patrones = new ArrayList();

    //Atributos de BBDD
    private Connection con;

    //Constructor desde BBDD
    public Categoria(Connection con, java.sql.ResultSet rs){
        //Guardamos la Conexion
        this.con = con;
        
        try{            
            this.codCategoria     = rs.getString(COD_CAT);
            this.descripcion      = rs.getString(DESCRIPCION);
            this.urlDatosFichero  = rs.getString(URL);
            this.formFechaFichero = rs.getString(FORMATOFECHA);
            this.comodinFichero   = rs.getString(COMODIN);
            this.extensionFichero = rs.getString(EXTENSION);
        }catch(java.sql.SQLException ex){
            System.out.println(ex.getMessage());
        }

        //Cargamos los Valores de Dicha Categoria
        cargaTablaValores();
        
        //Cargamos los Patrones
        cargaTablaPatrones();
    }

    //Recupera los Datos de la Tabla de Valores en un ArrayList de Valor
    //Filtramos por Categoria
    private void cargaTablaValores(){

        try{
            //Ejecutamos la Query Filtando por Codigo de Categoria
            PreparedStatement cmd = con.prepareStatement(QUERY_VALORES);
            cmd.setString(1, codCategoria);
            ResultSet rs = cmd.executeQuery();

            //Recorremos la Lista de Valores
            while(rs.next()){
                //Nuevo Campo de Valor
                Valor nuevoValor = new Valor(rs);
                
                //Añadimos nuevo campo a la lista
                valores.add(nuevoValor);
                
                //Traza
                //System.out.println(nuevoValor.toString());
            }            
        }catch(Exception ex){
            System.out.println("Error Recuperando los Valores");
        }
    }

    //Recupera los Datos de la Tabla de Patrones en un ArrayList de Patron
    private void cargaTablaPatrones(){
        try{
            Statement cmd = con.createStatement();
            ResultSet rs = cmd.executeQuery(QUERY_PATRONES);

            //Recorremos todos los Datos de Entrada
            while(rs.next()){
                //Nuevo Campo de Patron
                Patron nuevoPatron = new Patron(rs);

                //Añadimos nuevo campo a la lista
                patrones.add(nuevoPatron);

                //Traza
                //System.out.println(nuevoPatron.toString());
            }            
        }catch(Exception ex){
            System.out.println("Error Recuperando los Patrones");
        }
    }
    
    
    //Actualiza los Datos de los Valores de la Web MegaBolsa
    public void actualizaDatosMegaBolsa(){

        //Obtenemos los Datos de los Valores de la Web de MegaBolsa
        datosValores = new MegaBolsa(con, urlDatosFichero, comodinFichero, extensionFichero, codCategoria);

        //Actualizamos los Datos de los Valores de la Web de MegaBolsa (Tarea en Paralelo)
        getDatosValores().start();
    }

    @Override
    public String toString(){
        return COD_CAT + ": " + this.getCodCategoria() + ", " + DESCRIPCION + ": " + this.getDescripcion() + ", " + URL + ": " + this.getUrlDatosFichero() + ", " + FORMATOFECHA + ": " + this.getFormFechaFichero() + ", " + COMODIN + ": " + this.getComodinFichero() + ", " + EXTENSION + ": " + this.getExtensionFichero();
    }

    /**
     * @return the codCategoria
     */
    public String getCodCategoria() {
        return codCategoria;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @return the urlDatosFichero
     */
    public String getUrlDatosFichero() {
        return urlDatosFichero;
    }

    /**
     * @return the formFechaFichero
     */
    public String getFormFechaFichero() {
        return formFechaFichero;
    }

    /**
     * @return the comodinFichero
     */
    public String getComodinFichero() {
        return comodinFichero;
    }

    /**
     * @return the extensionFichero
     */
    public String getExtensionFichero() {
        return extensionFichero;
    }

    /**
     * @return the datosValores
     */
    public MegaBolsa getDatosValores() {
        return datosValores;
    }
}

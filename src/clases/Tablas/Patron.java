/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.Tablas;


/**
 *
 * @author Ignacio
 */
public class Patron {
    
    //Constantes
    private final String COD_CAMPO = "COD_CAMPO";
    private final String ORDEN     = "ORDEN";
    private final String TIPO_DATO = "TIPO_DATO";
    
    //Atributos
    private String codValor;
    private int    orden;
    private String tipoDato;
    
    public Patron(java.sql.ResultSet rs){
        try{
            this.codValor = rs.getString(COD_CAMPO);
            this.orden    = rs.getInt(ORDEN);
            this.tipoDato = rs.getString(TIPO_DATO);
        }catch(java.sql.SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public Patron(String codValor, int orden, String tipoDato){
        this.codValor = codValor;
        this.orden    = orden;
        this.tipoDato = tipoDato;
    }
    
    @Override
    public String toString(){
        return COD_CAMPO + ": " + this.codValor + ", " + ORDEN + ": " + this.orden + ", " + TIPO_DATO + ": " + this.tipoDato;
    }

    /**
     * @return the codValor
     */
    public String getCodValor() {
        return codValor;
    }

    /**
     * @return the orden
     */
    public int getOrden() {
        return orden;
    }

    /**
     * @return the tipoDato
     */
    public String getTipoDato() {
        return tipoDato;
    }
}

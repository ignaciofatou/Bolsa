/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.DatosMegaBolsa;

/**
 *
 * @author Ignacio
 */
public class CampoLinea {
    //Atributos
    private String contenido;
    private int    posicion;
    
    public CampoLinea(String contenido, int posicion){
        this.contenido = contenido;
        this.posicion  = posicion;
    }

    /**
     * @return the contenido
     */
    public String getContenido() {
        return contenido;
    }

    /**
     * @return the posicion
     */
    public int getPosicion() {
        return posicion;
    }
}

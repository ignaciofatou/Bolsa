/*
 * Lo vamos a Usar para Descargar los Datos de un Solo dia de varios Valores
  */
package clases.DatosMegaBolsa;

import clases.Tablas.DatoValor;
import java.util.ArrayList;

/**
 *
 * @author Ignacio
 */
public class DatoDiaValores {
    private ArrayList <DatoValor> datoDiaValores;
    private String fecha;
    
    //Constructor
    public DatoDiaValores(){
        datoDiaValores = new ArrayList();
    }

    //Incluimos un Nuevo Valor
    public void setNuevoValor(DatoValor datoValor){
        datoDiaValores.add(datoValor);
    }
    /**
     * @return the datoDiaValores
     */
    public ArrayList <DatoValor> getDatoDiaValores() {
        return datoDiaValores;
    }

    /**
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }    
    
    //Recorremos todos los Datos de los Valores y los Insertamos en la BBDD
    public void insertaDatoValoresBBDD(java.sql.Connection con){
        for (DatoValor datoValor:datoDiaValores){
            datoValor.insertaDatoValorBBDD(con);
        }
    }
}

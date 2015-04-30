/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.Tablas;

import java.sql.PreparedStatement;

/**
 *
 * @author Ignacio
 */
public class FicheroTratado {
    //Atributos
    private String nomFichero;
    private int    numRegistros;
    
    //Constantes SQL
    private final static String QUERY_SELECT_FICHERO = "SELECT COUNT(1) FROM FICHEROS_TRATADOS WHERE FICHERO = ?";
    private final String        QUERY_INSERT_FICHERO = "INSERT INTO FICHEROS_TRATADOS (FICHERO, NUM_REG) VALUES (?, ?)";

    public FicheroTratado(String nomFichero, int numRegistros){
        this.nomFichero   = nomFichero;
        this.numRegistros = numRegistros;
    }

    //Comprueba que Si el Fichero existe ya en la Tabla FICHEROS_TRATADOS
    public static boolean estaYaTratado(java.sql.Connection con, String nomFichero){        
        try{
            PreparedStatement cmd = con.prepareStatement(QUERY_SELECT_FICHERO);
            cmd.setString(1, nomFichero);
            java.sql.ResultSet rs = cmd.executeQuery();
            
            if (rs.next())
                return (rs.getInt(1) == 1);
            else
                return false;            

        }catch(Exception ex){
            return false;
        }
    }

    //Inserccion en la Tabla de VALORES
    public boolean insertFicheroBBDD(java.sql.Connection con){

        //Si el Valor No Existe en BBDD -> Es Valido para Insertar
        if (!estaYaTratado(con, this.nomFichero)){
            try{
                PreparedStatement cmd = con.prepareStatement(QUERY_INSERT_FICHERO);
                cmd.setString(1, this.nomFichero);
                cmd.setInt(2, this.numRegistros);
                cmd.executeUpdate();
                System.out.println("Insertado el Fichero: " + this.nomFichero + " en la Tabla de FICHEROS_TRATADOS");
                
                //Retornamos que todo ha ido Correctamente
                return true;

            }catch(Exception ex){
                System.out.println("Error al Insertar el Fichero: " + this.nomFichero + " en la Tabla de FICHEROS_TRATADOS");
                ex.printStackTrace();
                return false;
            }
            
        }
        //No se ha Insertado
        return false;
    }

    /**
     * @return the nomFichero
     */
    public String getNomFichero() {
        return nomFichero;
    }

    /**
     * @return the numRegistros
     */
    public int getNumRegistros() {
        return numRegistros;
    }
}

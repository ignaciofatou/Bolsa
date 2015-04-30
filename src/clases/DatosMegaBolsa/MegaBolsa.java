/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.DatosMegaBolsa;

import clases.Fecha;
import clases.Tablas.DatoValor;
import clases.Tablas.FicheroTratado;
import clases.Tablas.PatronesCampos;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Ignacio
  * http://www.megabolsa.com/cierres/150410.txt
  * http://www.megabolsa.com/cierres/150402I.txt
 */
public class MegaBolsa extends Thread{
    
    //Atributos de Conexion a Base de Datos
    Connection con;
    
    //Atributos
    private PatronesCampos patronesCampos;
    private String         direccionWEB;
    private String         comodinWEB;
    private String         extensionWEB;
    private String         categoria;
    private int            diaEnProceso = 0;
    
    //Constantes
    public final int NUM_DIAS = 300;
    
    //Constructor
    public MegaBolsa(Connection con, String direccionWEB, String comodinWEB, String extensionWEB, String categoria){
        //Asignamos la Conexion
        this.con = con;

        //Obtenemos los Patrones para Recuperar Ordenadamente los Campos de la Web
        patronesCampos = new PatronesCampos(con);
        
        //Obtenemos el Resto de Datos para Generar la URL
        this.direccionWEB = direccionWEB;
        this.comodinWEB   = comodinWEB;
        this.extensionWEB = extensionWEB;
        this.categoria    = categoria;
    }
    
    //Recupera los Datos de la Web megabolsa.com y los Inserta en la Base de Datos
    private void setDatosToBBDD(String fecha){
        //Retorna los Datos de los Valores Para un Dia
        DatoDiaValores datoDiaValores = new DatoDiaValores();
        DatoValor      datoValor;
        
        //URL desde la que vamos a Obtener la Informacion
        String direccionInformacion = this.direccionWEB + fecha + this.comodinWEB + this.extensionWEB;
        
        //Comprobamos que el Fichero no haya sido Ya tratado
        if (!FicheroTratado.estaYaTratado(con, direccionInformacion)){
            System.out.println("Tratamos Fichero: "+direccionInformacion);
            //Variables Para leer la URL
            URL accesoURL;
            String lineaLeida;

            try
            {
                //Accedemos a la URL
                accesoURL = new URL(direccionInformacion);
                BufferedReader buffer = new BufferedReader(new InputStreamReader(accesoURL.openStream())); 

                //Recorremos el Fichero de la URL hasta el Final
                while ((lineaLeida = buffer.readLine()) != null){
                    //Por Cada Linea Creamos un Nuevo Dato Valor
                    datoValor = new DatoValor(patronesCampos, lineaLeida, categoria);                

                    //Añadimos el Nuevo Dato del Valor al ArrayList
                    datoDiaValores.setNuevoValor(datoValor);
                }
                //Cerramos el Buffer
                buffer.close();

            }catch (Exception e){
                //Suele dar este error con los Fines de Semana que no hay Datos para la Bolsa
                System.out.println("Error al Acceder al Fichero: " + e.getMessage());
            }
            //Insertamos los Datos de los Valores en BBDD
            datoDiaValores.insertaDatoValoresBBDD(con);

            //Insertamos el Fichero Tratado (Para no volver a tratarlo)
            FicheroTratado fichTratado = new FicheroTratado(direccionInformacion, datoDiaValores.getDatoDiaValores().size());
            fichTratado.insertFicheroBBDD(con);
            System.out.println("El Fichero Ha Sido Tratado Con Exito: "+direccionInformacion);
        }
        else
            System.out.println("El Fichero Ya Fue Tratado: "+direccionInformacion);
    }
    
    //Actualiza los Datos de los Valores el Numero de dias Indicado en NUM_DIAS
    private void updateDatosBBDD(){

        //Obtenemos la Fecha Actual
        Calendar cal  = Calendar.getInstance(); 
        Date datFecha = cal.getTime();
        String strFecha;
        
        for (diaEnProceso = 0; diaEnProceso < NUM_DIAS; diaEnProceso++){
            //Restamos un dia a la Fecha
            datFecha = Fecha.sumarRestarDiasFecha(datFecha, -1);
            
            //Sacamos la Fecha formateada
            strFecha = Fecha.getStrFecha(datFecha, "YYMMdd");
            setDatosToBBDD(strFecha);
        }
    }
    
    @Override
    public void run(){
        updateDatosBBDD();
    }

    /**
     * @return the diaEnProceso
     */
    public int getDiaEnProceso() {
        return diaEnProceso;
    }
}

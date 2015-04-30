/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author Ignacio
 */
public class Operacion {
    
    //Constantes
    public static final String LARGO = "largo";
    public static final String CORTO = "corto";
    
    //Atributos
    private String operacion;
    private String nombreValor;
    private double volumen;
    private double precioApertura;
    private String fechaApertura;
    private String horaApertura;
    private double precioCierre;
    private String fechaCierre;
    private String horaCierre;
    private double tasas;
    
    //Atributos Calculados
    private double beneficioBruto;
    private double beneficioNeto;
    
    //Constructor con Fecha de Apertura Automatico y sin Cierre
    public Operacion(String operacion, String nombreValor, double volumen, double precioApertura){
        this.operacion      = operacion;
        this.nombreValor    = nombreValor;
        this.volumen        = volumen;
        this.precioApertura = precioApertura;
        this.fechaApertura  = getFechaActual();
        this.horaApertura   = getHoraActual();
    }
    //Constructor con Fecha de Apertura Manual y sin Cierre
    public Operacion(String operacion, String nombreValor, double volumen, double precioApertura, String fechaApertura, String horaApertura){
        this.operacion      = operacion;
        this.nombreValor    = nombreValor;
        this.volumen        = volumen;
        this.precioApertura = precioApertura;
        this.fechaApertura  = fechaApertura;
        this.horaApertura   = horaApertura;
    }
    //Constructor con Fecha de Cierre Automatico sin Tasas
    public Operacion(String operacion, String nombreValor, double volumen, double precioApertura, String fechaApertura, String horaApertura, double precioCierre){
        this.operacion      = operacion;
        this.nombreValor    = nombreValor;
        this.volumen        = volumen;
        this.precioApertura = precioApertura;
        this.fechaApertura  = fechaApertura;
        this.horaApertura   = horaApertura;
        this.precioCierre   = precioCierre;
        this.fechaCierre    = getFechaActual();
        this.horaCierre     = getHoraActual();
    }
    //Constructor con Fecha de Cierre Manual sin Tasas
    public Operacion(String operacion, String nombreValor, double volumen, double precioApertura, String fechaApertura, String horaApertura, double precioCierre, String fechaCierre, String horaCierre){
        this.operacion      = operacion;
        this.nombreValor    = nombreValor;
        this.volumen        = volumen;
        this.precioApertura = precioApertura;
        this.fechaApertura  = fechaApertura;
        this.horaApertura   = horaApertura;
        this.precioCierre   = precioCierre;
        this.fechaCierre    = fechaCierre;
        this.horaCierre     = horaCierre;
    }
    //Constructor con Fecha de Cierre Automatico y Tasas
    public Operacion(String operacion, String nombreValor, double volumen, double precioApertura, String fechaApertura, String horaApertura, double precioCierre, double tasas){
        this.operacion      = operacion;
        this.nombreValor    = nombreValor;
        this.volumen        = volumen;
        this.precioApertura = precioApertura;
        this.fechaApertura  = fechaApertura;
        this.horaApertura   = horaApertura;
        this.precioCierre   = precioCierre;
        this.fechaCierre    = getFechaActual();
        this.horaCierre     = getHoraActual();
        this.tasas          = tasas;
    }
    //Constructor con Fecha de Cierre Manual y Tasas
    public Operacion(String operacion, String nombreValor, double volumen, double precioApertura, String fechaApertura, String horaApertura, double precioCierre, String fechaCierre, String horaCierre, double tasas){
        this.operacion      = operacion;
        this.nombreValor    = nombreValor;
        this.volumen        = volumen;
        this.precioApertura = precioApertura;
        this.fechaApertura  = fechaApertura;
        this.horaApertura   = horaApertura;
        this.precioCierre   = precioCierre;
        this.fechaCierre    = fechaCierre;
        this.horaCierre     = horaCierre;
        this.tasas          = tasas;
    }    

    
    public void cierreOperacion(double precioCierre){
        this.precioCierre   = precioCierre;
        this.fechaCierre    = getFechaActual();
        this.horaCierre     = getHoraActual();
    }
    public void cierreOperacion(double precioCierre, String fechaCierre, String horaCierre){
        this.precioCierre   = precioCierre;
        this.fechaCierre    = fechaCierre;
        this.horaCierre     = horaCierre;
    }
    public void cierreOperacion(double precioCierre, double tasas){
        this.precioCierre   = precioCierre;
        this.fechaCierre    = getFechaActual();
        this.horaCierre     = getHoraActual();
        this.tasas          = tasas;
    }
    public void cierreOperacion(double precioCierre, String fechaCierre, String horaCierre, double tasas){
        this.precioCierre   = precioCierre;
        this.fechaCierre    = fechaCierre;
        this.horaCierre     = horaCierre;
        this.tasas          = tasas;
    }
    
    public String getFechaActual(){
        return "2014-04-10";
    }
    public String getHoraActual(){
        return "14:36:25";
    }
    
}

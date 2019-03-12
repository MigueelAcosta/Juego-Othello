package Modelo;

import javafx.beans.property.SimpleStringProperty;

/** 
 * clase del modelo contenedora de los atributos de una partida
 * @author Miguel Acosta
 */
public class PartidaCliente {
    private final SimpleStringProperty fecha = new SimpleStringProperty();
    private final SimpleStringProperty adversario = new SimpleStringProperty();
    private final SimpleStringProperty duracion = new SimpleStringProperty();
    private final  SimpleStringProperty resultado = new SimpleStringProperty();

    /**
     * constructor de la clase
     */
    public PartidaCliente() {
    }

    /**
     * constructor sobrecargado
     * @param fecha fecha de la partida
     * @param adversario adversario contra quien se jugo
     * @param duracion tiempo que duro el juego
     * @param resultado resultado final del juego
     */
    public PartidaCliente(String fecha, String adversario, String duracion, String resultado){
        this.fecha.set(fecha);
        this.adversario.set(adversario);
        this.duracion.set(duracion);
        this.resultado.set(resultado);
    }
    /**
     * obtener fecha de la partida
     * @return fecha de la partida
     */
    public String getFecha() {
        return fecha.get();
    }
/**
 * obtener adversario en la partida
 * @return adversario
 */
    public String getAdversario() {
        return adversario.get();
    }
/**
 * obtener duracion de la partida
 * @return duracion
 */
    public String getDuracion() {
        return duracion.get();
    }
/**
 * obtener resultado
 * @return resultado
 */
    public String getResultado() {
        return resultado.get();
    }
/**
 * establecer fecha de la partida
 * @param fecha fecha de la partida
 */
    public void setFecha(String fecha) {
        this.fecha.set(fecha);
    }
/**
 * establecer adversario de la partida
 * @param adversario adversario
 */
    public void setAdversario(String adversario) {
        this.adversario.set(adversario);
    }
/**
 * establecer duracion de la partida
 * @param duracion 
 */
    public void setDuracion(String duracion) {
        this.duracion.set(duracion);
    }
/**
 * establecer resultado de la partida
 * @param resultado resultado de la partida
 */
    public void setResultado(String resultado) {
        this.resultado.set(resultado);
    }
    
    
}
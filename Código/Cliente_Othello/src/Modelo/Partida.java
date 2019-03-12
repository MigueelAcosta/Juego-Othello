package Modelo;

import javafx.beans.property.SimpleStringProperty;
/**
 * Clase contenedora de los atributos y metodos del objeto partida
 * @author Miguel Acosta
 */
public class Partida {
    private final SimpleStringProperty fecha = new SimpleStringProperty();
    private final SimpleStringProperty adversario = new SimpleStringProperty();
    private final SimpleStringProperty duracion = new SimpleStringProperty();
    private final  SimpleStringProperty resultado = new SimpleStringProperty();

    /**
     * constructor de la clase Partida
     */
    public Partida() {
    }

    /**
     * Constructor sobre cargado de la clase Partida
     * @param fecha fecha sobre la partida
     * @param adversario adversario con quien se jugo la partida
     * @param duracion tiempo de juego
     * @param resultado resultado de la partida
     */
    public Partida(String fecha, String adversario, String duracion, String resultado){
        this.fecha.set(fecha);
        this.adversario.set(adversario);
        this.duracion.set(duracion);
        this.resultado.set(resultado);
    }
    /**
     * obtener fecha de la partida
     * @return fecha de partida
     */
    public String getFecha() {
        return fecha.get();
    }
/**
 * obtener adversario de la partida
 * @return adversario
 */
    public String getAdversario() {
        return adversario.get();
    }
/**
 * obtener duracion de la partida
 * @return duracion de la partida
 */
    public String getDuracion() {
        return duracion.get();
    }
/**
 * obtener resultado de la partida
 * @return resultado
 */
    public String getResultado() {
        return resultado.get();
    }
/**
 * establecer fecha de la partida
 * @param fecha fecha
 */
    public void setFecha(String fecha) {
        this.fecha.set(fecha);
    }
/**
 * Establecer el adversario de la partida
 * @param adversario adversario de la partida
 */
    public void setAdversario(String adversario) {
        this.adversario.set(adversario);
    }
/**
 * establecer la duracion de la partida
 * @param duracion duracion que tuvo la partida
 */
    public void setDuracion(String duracion) {
        this.duracion.set(duracion);
    }
/**
 * Establecer el resultado que tuvo una partida
 * @param resultado resultado que tuvo una partida
 */
    public void setResultado(String resultado) {
        this.resultado.set(resultado);
    }
    
    
}

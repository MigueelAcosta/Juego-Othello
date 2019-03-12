package Modelo;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 *Clase contenedora de los atributos y metodos del objeto mejor jugador
 * @author Miguel Acosta
 * @author Mauricio Capistran
 */
public class MejorJugador{
    private final SimpleStringProperty usuario = new SimpleStringProperty();
    private final SimpleIntegerProperty numeroDeVictorias = new SimpleIntegerProperty();

    public MejorJugador() {
    }
    /**
     * constructor de la clase MejorJugador
     * @param usuario nombre de usuario
     * @param numeroDeVictorias numero de victorias del usuario
     */
    public MejorJugador(String usuario, int numeroDeVictorias){
        this.usuario.set(usuario);
        this.numeroDeVictorias.set(numeroDeVictorias);
    }

    public void setUsuario(String usuario){
        this.usuario.set(usuario);
    }
    
    public void setNumeroDePartidas(int numeroDePartidas){
        this.numeroDeVictorias.set(numeroDePartidas);
    }
    
    public String getUsuario() {
        return usuario.get();
    }

    public int getNumeroDeVictorias() {
        return numeroDeVictorias.get();
    }
}

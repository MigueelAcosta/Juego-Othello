package Modelo;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Clase del modelo contenedora de los atributos de mejor jugador
 * @author Miguel Acosta
 */
public class MejorJugador implements Comparable<MejorJugador>{
    private final SimpleStringProperty usuario = new SimpleStringProperty();
    private final SimpleIntegerProperty numeroDeVictorias = new SimpleIntegerProperty();

    /**
     * constructor de la clase
     */
    public MejorJugador() {
    }
    
    /**
     * constructor sobrecargado
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
    
    /**
     * sobre escritura del metodo compareTo para comparar jugadores
     * @param t jugador a comparar
     * @return valor de validacion
     */
    @Override
    public int compareTo(MejorJugador t) {
        if(this.numeroDeVictorias.get()==t.getNumeroDeVictorias()){
            return 0;
        }else{
            if(this.numeroDeVictorias.get()>t.getNumeroDeVictorias()){
                return -1;
            }else{
                return 1;
            }
        }
    }
}

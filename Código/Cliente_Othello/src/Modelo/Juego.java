package Modelo;
import com.google.gson.Gson;

/**
 * Clase contenedora de los atributos del juego
 * @author Miguel Acosta
 * @author Mauricio Juarez
 */
public class Juego {
    private int ID;
    private boolean tipoDeJuego;
    private String turno;
    private int estadoJuego;
    private Integer[][] tablero;
    private final Jugador jugadorUno = new Jugador();
    private final Jugador jugadorDos = new Jugador();
    
    public static final boolean VS_CPU = false;
    public static final boolean VS_JUGADOR = true;
    public static final int EN_ESPERA = 0;
    public static final int EN_PROGRESO = 1;
    public static final int FIN_DEL_JUEGO_EMPATE = 2;
    public static final int FIN_DEL_JUEGO_GANADOR_BLANCO = 3;
    public static final int FIN_DEL_JUEGO_GANADOR_NEGRO = 4;
    
    /**
     * Clase contenedora de los atributos del objeto jugador
     */
    class Jugador {
        public String nombre = "";
        public String imagen = "";
        public Integer numeroDeFichas = 0;
        public Integer numeroDeMovimientos = 0;
    }
/**
 * EStablecer identificador
 * @param ID identificador
 */
    public void setID(int ID) {
        this.ID = ID;
    }
/**
 * establecer tipo de juego
 * @param tipoDeJuego modalidad de juego
 */
    public void setTipoDeJuego(boolean tipoDeJuego) {
        this.tipoDeJuego = tipoDeJuego;
    }
/**
 * establecer estado de un juego
 * @param estadoJuego estado del juego
 */
    public void setEstadoJuego(int estadoJuego) {
        this.estadoJuego = estadoJuego;
    }
    /**
     * establecer el turno del jugador
     * @param turno turno
     */
    public void setTurno(String turno) {
        this.turno = turno;
    }
/**
 * establecer el estado del tablero
 * @param tablero tablero
 */
    public void setTablero(Integer[][] tablero) {
        this.tablero = tablero;
    }

    public void setJugadorUno(String imagen, String nombre, Integer numeroDeFichas, Integer numeroDeMovimientos) {
        this.jugadorUno.imagen = imagen;
        this.jugadorUno.nombre = nombre;
        this.jugadorUno.numeroDeFichas = numeroDeFichas;
        this.jugadorUno.numeroDeMovimientos = numeroDeMovimientos;
    }

    public void setJugadorDos(String imagen, String nombre, Integer numeroDeFichas, Integer numeroDeMovimientos) {
        this.jugadorDos.imagen = imagen;
        this.jugadorDos.nombre = nombre;
        this.jugadorDos.numeroDeFichas = numeroDeFichas;
        this.jugadorDos.numeroDeMovimientos = numeroDeMovimientos;
    }
/**
 * obtener el identificador del juego
 * @return identificador del juego
 */
    public int getID() {
        return ID;
    }
    /**
     * obtener la modalidad del juego
     * @return modalidad del juego
     */
    public boolean getTipoDeJuego() {
        return tipoDeJuego;
    }
    /**
     * obtener el estado del juego
     * @return estado del juego
     */
    public int getEstadoJuego() {
        return estadoJuego;
    }
/**
 * Obtener turno del juegador
 * @return turno
 */
    public String getTurno() {
        return turno;
    }
/**
 * obtener estado del tablero de juego
 * @return estado del tablero
 */
    public Integer[][] getTablero() {
        return tablero;
    }
    /**
     * obtener la imagen del jugador uno
     * @return imagen del jugador 1
     */
    public String getImagenJugadorUno(){
        return this.jugadorUno.imagen;
    }
    /**
     * obtener el nombre del jugador 1
     * @return nombre del jugador 1
     */
    public String getNombreJugadorUno(){
        return this.jugadorUno.nombre;
    }
/**
 * Obtener numero de fichas del jugador uno
 * @return numero de fichas del jugador 1
 */
    public Integer getNumeroDeFichasJugadorUno(){
        return this.jugadorUno.numeroDeFichas;
    }
/**
 * obtener numero de movimientos del jugador uno
 * @return movimientos jugador 1
 */
    public Integer getNumeroDeMovimientosJugadorUno(){
        return this.jugadorUno.numeroDeMovimientos;
    }
    /**
     * obtener imagen del jugador 2
     * @return imagen jugador 2
     */
    public String getImagenJugadorDos(){
        return this.jugadorDos.imagen;
    }
    /**
     * obtener el nombre del jugador 2
     * @return nombre del jugador 2
     */
    public String getNombreJugadorDos(){
        return this.jugadorDos.nombre;
    }
/**
 * obtener el numero de fichas del jugador 2
 * @return fichas del jugador 2
 */
    public Integer getNumeroDeFichasJugadorDos(){
        return this.jugadorDos.numeroDeFichas;
    }
/**
 * obtener el numero de movimientos del jugador 2
 * @return movimientos del jugador 2
 */
    public Integer getNumeroDeMovimientosJugadorDos(){
        return this.jugadorDos.numeroDeMovimientos;
    }
    /**
     * convierte un objeto en una cadena Json
     * @return la cadena Json
     */
    public String toJson() {
        return new Gson().toJson(this);
    }
    /**
     * convierte una cadena Json a un objeto 
     * @param json objeto a convertir
     * @return objeto resultado de la conversion
     */
    public static Juego fromJson(String json){
        return new Gson().fromJson(json, Juego.class);
    }
    /**
     * verifica el turno del jugador
     * @return valor de validacion
     */
    public boolean esMiTurno(){
        if(this.jugadorUno.nombre.equals(
                Configuracion.getConfiguracion().getNombreDeUsuario())){
            return this.turno.equals("TurnoNegro");
        }else{
            return this.turno.equals("TurnoBlanco");
        }
    }
}

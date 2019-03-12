package Modelo;

import Interfaces.Cliente;
import com.google.gson.Gson;
import java.util.Calendar;

/**
 * Clase del modelo contenedora de los atributos del juego
 * @author Miguel Acosta
 * @author Mauricio Juarez
 */
public class Juego {

    private int ID;
    private boolean tipoDeJuego;
    private String turno;
    private int estadoJuego;
    private Integer[][] tablero;
    
    private Calendar fechaIncio;
    private Calendar fechaFin;
    
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
     * Clase del modelo contenedora de los atributos del jugador
     */
    class Jugador {

        public Cliente cliente;
        public String nombre = "";
        public String imagen = "";
        public Integer numeroDeFichas = 0;
        public Integer numeroDeMovimientos = 0;
    }
/**
 * establecer identificador del juego
 * @param ID identificador del juego
 */
    public void setID(int ID) {
        this.ID = ID;
    }
/**
 * establecer la modalidad de juego
 * @param tipoDeJuego modalidad de juego
 */
    public void setTipoDeJuego(boolean tipoDeJuego) {
        this.tipoDeJuego = tipoDeJuego;
    }
/**
 * establecer el estado de una partida
 * @param estadoJuego estado de una partida
 */
    public void setEstadoJuego(int estadoJuego) {
        this.estadoJuego = estadoJuego;
    }
/**
 * establecer el turno en una partida
 * @param turno 
 */
    public void setTurno(String turno) {
        this.turno = turno;
    }
/**
 * establecer el jugador 1 de la partida
 * @param cliente jugador 1
 */
    public void setClienteJugadorUno(Cliente cliente) {
        this.jugadorUno.cliente = cliente;
    }
    /**
     * establecer fecha de inicio de la partida
     * @param fechaIncio fecha de inicio de la partida
     */
        public void setFechaIncio(Calendar fechaIncio) {
        this.fechaIncio = fechaIncio;
    }
/**
 * establecer fecha del fin de la partida
 * @param fechaFin fecha de terminacion de la partida
 */
    public void setFechaFin(Calendar fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * establecer el jugador 2 de la partida
     * @param cliente jugador 2
     */
    public void setClienteJugadorDos(Cliente cliente) {
        this.jugadorDos.cliente = cliente;
    }

    /**
     * Establecer el estado del tablero 
     * @param tablero tablero
     */
    public void setTablero(Integer[][] tablero) {
        this.tablero = tablero;
    }
    /**
     * Aumentar el numero de movimientos que ha realizado el jugador uno
     */
    public void aumentarNumeroDeMovimientosDeJugadorUno(){
        this.jugadorUno.numeroDeMovimientos++;
    }
    /**
     * aumentar el numero de movimientos que ha realizado el jugador dos
     */
    public void aumentarNumeroDeMovimientosDeJugadorDos(){
        this.jugadorDos.numeroDeMovimientos++;
    }
    /**
     * Establecer el numero de fichas en el tablero del jugador 1
     * @param numeroDeFichas numero de fichas del jugador 1
     */
    public void setNumeroDeFichasJugadorUno(int numeroDeFichas){
        this.jugadorUno.numeroDeFichas=numeroDeFichas;
    }
    /**
     * Establecer el numero de fichas en el tablero del jugador 2
     * @param numeroDeFichas numero de fichas del jugador 2
     */
    public void setNumeroDeFichasJugadorDos(int numeroDeFichas){
        this.jugadorDos.numeroDeFichas=numeroDeFichas;
    }

    /**
     * Establecer el jugador uno del juego
     * @param imagen imagen asiganada del jugador
     * @param nombre nombre de usuario del jugador
     * @param numeroDeFichas numero de fichas en el tablero
     * @param numeroDeMovimientos movimientos realizados por el jugador
     */
    public void setJugadorUno(String imagen, String nombre, Integer numeroDeFichas, Integer numeroDeMovimientos) {
        this.jugadorUno.imagen = imagen;
        this.jugadorUno.nombre = nombre;
        this.jugadorUno.numeroDeFichas = numeroDeFichas;
        this.jugadorUno.numeroDeMovimientos = numeroDeMovimientos;
    }

    /**
     * Establecer el jugador dos del juego
     * @param imagen imagen asignada del jugador
     * @param nombre nombre de usuario del jugador
     * @param numeroDeFichas numero de fichas en el tablero
     * @param numeroDeMovimientos  movimientos realizados por el jugador
     */
    public void setJugadorDos(String imagen, String nombre, Integer numeroDeFichas, Integer numeroDeMovimientos) {
        this.jugadorDos.imagen = imagen;
        this.jugadorDos.nombre = nombre;
        this.jugadorDos.numeroDeFichas = numeroDeFichas;
        this.jugadorDos.numeroDeMovimientos = numeroDeMovimientos;
    }

    public int getID() {
        return ID;
    }

    public Cliente getClienteJugadorUno() {
        return this.jugadorUno.cliente;
    }

    public Cliente getClienteJugadorDos() {
        return this.jugadorDos.cliente;
    }

    public boolean getTipoDeJuego() {
        return tipoDeJuego;
    }

    public String getFechaDeJuego(){
        return this.fechaFin.get(Calendar.DAY_OF_MONTH) + "/"
                + (this.fechaFin.get(Calendar.MONTH)+1) + "/"
                + this.fechaFin.get(Calendar.YEAR)
                ;
    }
    
    /**
     * convierte el tiempo en horas con minutos y los muestra
     * @return tiempo transcurrido
     */
    public String getDuracionDeJuego(){
        long tiempo = this.fechaFin.getTimeInMillis() - this.fechaIncio.getTimeInMillis();
        int horas = (int) (tiempo/(60000*60));
        int minutos = (int) ((tiempo%(60000*60))/60000);
        int segundos = (int) (((tiempo%(60000*60))%60000)/1000);
        return horas+":"+minutos+":"+segundos;
    }
    
    public int getEstadoJuego() {
        return estadoJuego;
    }

    public String getTurno() {
        return turno;
    }

    public Integer[][] getTablero() {
        return tablero;
    }

    public String getImagenJugadorUno() {
        return this.jugadorUno.imagen;
    }

    public String getNombreJugadorUno() {
        return this.jugadorUno.nombre;
    }

    public Integer getNumeroDeFichasJugadorUno() {
        return this.jugadorUno.numeroDeFichas;
    }

    public Integer getNumeroDeMovimientosJugadorUno() {
        return this.jugadorUno.numeroDeMovimientos;
    }

    public String getImagenJugadorDos() {
        return this.jugadorDos.imagen;
    }

    public String getNombreJugadorDos() {
        return this.jugadorDos.nombre;
    }

    public Integer getNumeroDeFichasJugadorDos() {
        return this.jugadorDos.numeroDeFichas;
    }

    public Integer getNumeroDeMovimientosJugadorDos() {
        return this.jugadorDos.numeroDeMovimientos;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static Juego fromJson(String json) {
        return new Gson().fromJson(json, Juego.class);
    }
}

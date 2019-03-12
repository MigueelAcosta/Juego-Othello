package Modelo;

import Interfaces.Cliente;
import Interfaces.ConexionBaseDeDatos;
import Interfaces.Servidor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Conexion {

    private final Remote conexion;
/**
 * construcor de la clase
 */
    public Conexion(){
        this.conexion = this.obtenerConexionConServidor();
    }

    private Remote obtenerConexionConServidor(){
        Configuracion configuracion = Configuracion.getConfiguracion();
        try{
        return Naming.lookup("rmi://" + configuracion.getIP()
                + ":" + configuracion.getPuerto()
                + "/ServidorOthello"
        );
        }catch(NotBoundException | MalformedURLException | RemoteException e){
            return null;
        }
    }   

    /**
     * Valida que un usuario se encuentre en la base de datos del sistema
     * @param usuario nombre de usuario que se validara
     * @param contraseña contrasena que se validara para el acceso al sistema
     * @return valor de verificacion
     * @throws RemoteException error en la conexion 
     */
    public boolean validarUsuario(String usuario, String contraseña) throws RemoteException {
        return ((ConexionBaseDeDatos) this.conexion).validarUsuario(usuario, contraseña);
    }

    /**
     * Registra a un usuario en la base de datos del sistema
     * @param usuario nombre de usuario que se registrara
     * @param contraseña contrasena que se asignara para el acceso al sistemaq
     * @return valor de validacion
     * @throws RemoteException error con la base de datos
     */
    public boolean registrarUsuario(String usuario, String contraseña) throws RemoteException {
        return ((ConexionBaseDeDatos) this.conexion).registrarUsuario(usuario, contraseña);
    }
/**
 * Obtiene el historial reciente de partidas de un usuario
 * @return arreglo de objetos tipo partida
 * @throws RemoteException error con la base de datos
 */
    public ArrayList<Partida> obtenerHistorialDePartidas() throws RemoteException {
        return new Gson().fromJson(
                ((ConexionBaseDeDatos) this.conexion).obtenerHistorialDePartidas(Configuracion.getConfiguracion().getNombreDeUsuario()), new TypeToken<ArrayList<Partida>>() {
        }.getType());
    }
/**
 * Selecciona a los mejores jugadores en la base de datos dependiendo de un criterio
 * @return arreglo con los mejores jugadores segun el criterio dado
 * @throws RemoteException error con la base de datos
 */
    public ArrayList<MejorJugador> obtenerMenoresJugadores() throws RemoteException {
        return new Gson().fromJson(((ConexionBaseDeDatos) this.conexion).obtenerMenoresJugadores(), new TypeToken<ArrayList<MejorJugador>>() {
        }.getType());
    }

    /**
     * obtiene el numero de victorias de un usuario
     * @return numero de victorias registradas en la base de datos
     * @throws RemoteException error con la base de datos
     */
    public int obtenerNumeroDeVictorias() throws RemoteException {
        return ((ConexionBaseDeDatos) this.conexion).obtenerNumeroDeVictorias(
                Configuracion.getConfiguracion().getNombreDeUsuario()
        );
    }

    /**
     * Crea una conexion entre 2 clientes
     * @param cliente objeto que busca iniciar la conexion
     * @throws RemoteException error con la conexion al servidor
     */
    public void jugarEnLinea(Cliente cliente) throws RemoteException {
        ((Servidor) this.conexion).conectar(cliente,
                Configuracion.getConfiguracion().getNombreDeUsuario(), Juego.VS_JUGADOR);
    }
/**
 * Crea una conexion con el servidor para jugar contra una cpu 
 * @param cliente objeto que busca iniciar la conexion
 * @throws RemoteException  error con la conexion al servidor
 */
    public void jugarContraCPU(Cliente cliente) throws RemoteException {
        ((Servidor) this.conexion).conectar(cliente,
                Configuracion.getConfiguracion().getNombreDeUsuario(), Juego.VS_CPU);
    }

    /**
     * guarda el estado de juego de una partida
     * @param idJuego identificador del juego 
     * @return valor de validacion 
     * @throws RemoteException error en la conexion con el servidor
     */
    public boolean guardarJuego(int idJuego) throws RemoteException {
        return ((Servidor) this.conexion).guardarJuego(
                Configuracion.getConfiguracion().getNombreDeUsuario(), idJuego);
    }

    /**
     * Reanuda el estado de un juego en una partida
     * @param cliente cliente que desea reanudar el juego
     * @return valor de validacion
     * @throws RemoteException error en la conexion con el servidor
     */
    public boolean reanudarJuegoContraCPU(Cliente cliente) throws RemoteException {
        return ((Servidor) this.conexion).reanudarJuego(
                Configuracion.getConfiguracion().getNombreDeUsuario(),
                cliente,
                Juego.VS_CPU);
    }
    /**
     * Reanuda el estado de una partida con una conexion entre 2 jugadores
     * @param cliente cliente que desea reanudar el juego
     * @return valor de validacion
     * @throws RemoteException error de conexion con el serividor
     */

    public boolean reanudarJuegoContraJugador(Cliente cliente) throws RemoteException {
        return ((Servidor) this.conexion).reanudarJuego(
                Configuracion.getConfiguracion().getNombreDeUsuario(),
                cliente,
                Juego.VS_JUGADOR);
    }

    /**
     * Cancela la espera de juego una vez que el jugador se reconecta o el tiempo se acabo
     * @throws RemoteException error de conexion con el servidor
     */
    public void cancelarEsperaDeJuego() throws RemoteException {
        ((Servidor) this.conexion).cancelarEsperaDeJuego();
    }

    /**
     * Se envia un movimiento al servidor para modificar el estado logico de la partida
     * @param x eje X de la coordenada
     * @param y eje Y de la coordenada
     * @param idJuego identificador del juego donde se modificara el tablero
     * @return valor de validacion 
     * @throws RemoteException error de conexion con el servidor
     */
    public boolean realizarMovimiento(int x, int y, int idJuego) throws RemoteException {
        return ((Servidor) this.conexion).realizarMovimiento(x, y, idJuego);
    }
    
    /**
     * Envia al servidor la rendicion de un jugador
     * @param idJuego identificador del juego
     * @throws RemoteException error de conexion con el servidor
     */
    public void rendirse(int idJuego) throws RemoteException{
        ((Servidor) this.conexion).rendirse(
                Configuracion.getConfiguracion().getNombreDeUsuario()
                , idJuego);
    }

    /**
     * Se cierra conexion con el servidor
     */
    public void desconectar() {
        String usuario = Configuracion.getConfiguracion().getNombreDeUsuario();
        if (!usuario.equals("")) {
            try {
                ((Servidor) this.conexion).desconectar(usuario);
            } catch (RemoteException ex) {
            }
        }
    }
}

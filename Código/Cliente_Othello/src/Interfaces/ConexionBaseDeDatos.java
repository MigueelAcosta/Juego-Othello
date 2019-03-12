package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * Interfaz para el manejo de la base de datos
 * @author Miguel Acosta
 */
public interface ConexionBaseDeDatos extends Remote{
    /**
     * interfaz para la validacion de un usuario que desea entrar al sistema
     * @param usuario nombre de ususario
     * @param contraseña contrasena
     * @return valor de verificacion
     * @throws RemoteException error de conexion con el servidor
     */
    public boolean validarUsuario(String usuario, String contraseña)throws RemoteException;
    /**
     * Interfaz para el registro de un usuario en la base de datos
     * @param usuario nombre de usuario que se registrara
     * @param contraseña contrasena que se asiganara al usuario
     * @return valor de verificacion
     * @throws RemoteException error en la conexion
     */
    public boolean registrarUsuario(String usuario, String contraseña)throws RemoteException;
    /**
     * interfaz para obtener el historial de partidas de un usuario
     * @param usuario usuario dle que se van a recuperar las partidas
     * @return cadena contenedora de un objeto
     * @throws RemoteException error en la conexion
     */
    public String obtenerHistorialDePartidas(String usuario)throws RemoteException;
    /**
     * Interfaz para obtener a los jugadores con mas victorias
     * @return cadena contenedora de un objeto 
     * @throws RemoteException error de conexion
     */
    public String obtenerMenoresJugadores()throws RemoteException;
    /**
     * obtener numero de victorias de un usuario
     * @param usuario nombre del usuario a recuperar
     * @return Numero de victorias
     * @throws RemoteException error de conexion
     */
    public int obtenerNumeroDeVictorias(String usuario)throws RemoteException;
}

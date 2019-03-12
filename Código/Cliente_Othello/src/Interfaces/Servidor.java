package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * Interfaz de control del servidor con los clientes
 * @author Miguel Acosta
 * @author Mauricio Juarez
 */
public interface Servidor extends Remote {
    /**
     * Conectar un cliente con el servidor
     * @param cliente instancia del cliente
     * @param nombreJugador nombre del jugador con el que se conectara
     * @param tipoDeJuego modalidad de juego
     * @throws RemoteException error de conexion
     */
    public void conectar(Cliente cliente, String nombreJugador, boolean tipoDeJuego) throws RemoteException;

    /**
     * interfaz para el manejo de los movimientos
     * @param x eje X del movimiento
     * @param y eje Y del movimiento
     * @param idJuego identificador del juego donde se realizara el movimiento
     * @return valor de verificacion
     * @throws RemoteException error en la conexion
     */
    public boolean realizarMovimiento(int x, int y, int idJuego) throws RemoteException;

    public boolean guardarJuego(String usuario, int idJuego) throws RemoteException;

    public boolean reanudarJuego(String usuario, Cliente cliente, boolean tipoJuego) throws RemoteException;
    
    public void cancelarEsperaDeJuego() throws RemoteException;
    
    public void rendirse(String usuario, int idJuego) throws RemoteException;
    
    public void desconectar(String usuario) throws RemoteException;
}

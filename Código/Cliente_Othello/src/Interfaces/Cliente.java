package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaz utilizada para la comunicacion del cliente con el servidor
 * @author Miguel Acosta
 * @author Mauricio Juarez
 */
public interface Cliente extends Remote{
    /**
     * Actualiza el estado de un juego
     * @param juego juego que se va a actualizar
     * @throws RemoteException error de conexion con el servidor
     */
    public void actualizarJuego(String juego)throws RemoteException;
}

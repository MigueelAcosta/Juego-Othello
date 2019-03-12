package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * interfaz para el modelo 
 * @author Miguel Acosta
 * @author Mauricio Juarez
 */
public interface Cliente extends Remote{
    /**
     * interfaz para la actualizacion del estado de una partida
     * @param juego juego que se va a actualizar
     * @throws RemoteException error con el servidor
     */
    public void actualizarJuego(String juego)throws RemoteException;
}

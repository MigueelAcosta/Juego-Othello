package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * interfaz utilizada para las operaciones realizadas en el servidor
 * @author Miguel Acosta
 */
public interface Servidor extends Remote {

    public void conectar(Cliente cliente, String nombreJugador, boolean tipoDeJuego) throws RemoteException;

    public boolean realizarMovimiento(int x, int y, int idJuego) throws RemoteException;

    public boolean guardarJuego(String usuario, int idJuego) throws RemoteException;

    public boolean reanudarJuego(String usuario, Cliente cliente, boolean tipoJuego) throws RemoteException;
    
    public void cancelarEsperaDeJuego() throws RemoteException;
    
    public void rendirse(String usuario, int idJuego) throws RemoteException;
    
    public void desconectar(String usuario) throws RemoteException;
}

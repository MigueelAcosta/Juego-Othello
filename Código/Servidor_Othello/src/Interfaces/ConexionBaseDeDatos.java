package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * interfaz utilizada para el manejo de la base de datos
 * @author Miguel Acosta
 * @author Mauricio Juarez
 */
public interface ConexionBaseDeDatos extends Remote{
    public boolean validarUsuario(String usuario, String contraseña)throws RemoteException;
    public boolean registrarUsuario(String usuario, String contraseña)throws RemoteException;
    public String obtenerHistorialDePartidas(String usuario)throws RemoteException;
    public String obtenerMenoresJugadores()throws RemoteException;
    public int obtenerNumeroDeVictorias(String usuario)throws RemoteException;
}

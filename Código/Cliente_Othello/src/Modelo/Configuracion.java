package Modelo;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ResourceBundle;
/**
 * Clase de configuracion del cliente
 * @author Miguel Acosta
 * @author Mauricio Juarez
 */
public class Configuracion {
    private static Configuracion configuracion = null;
    
    private String nombreDeUsuario = null;
    private String IP = "localhost";
    private int Puerto = 8080; 
    private ResourceBundle resource;
    private boolean sesionIniciada = false;
    private boolean tipJuego;

    private Configuracion() {
        this.resource = ResourceBundle.getBundle("Recursos.Idiomas.Idioma_es_mx");
    }
/**
 * obtener conficuracion del cliente
 * @return configuracion
 */
    public static Configuracion getConfiguracion() {
        if(configuracion == null){
            configuracion = new Configuracion();
        }
        return configuracion;
    }
    /**
     * establecer nombre de usuario
     * @param nombreDeUsuario nombre de usuario
     */
    public void setNombreDeUsuario(String nombreDeUsuario) {
        this.nombreDeUsuario = nombreDeUsuario;
    }
/**
 * establecer tipo de juego
 * @param tipJuego tipo de juego
 */
    public void setTipJuego(boolean tipJuego) {
        this.tipJuego = tipJuego;
    }
    /**
     * Establecer la ip del juego
     * @param IP IP
     */
    public void setIP(String IP) {
        this.IP = IP;
    }
/**
 * establecer puerto para conexion
 * @param Puerto puerto
 */
    public void setPuerto(int Puerto) {
        this.Puerto = Puerto;
    }
/**
 * establecer idioma del cliente
 * @param idioma idioma
 */
    public void setIdioma(String idioma) {
        this.resource = ResourceBundle.getBundle(idioma);
    }
/**
 * establecer estado de la sesion del usuario
 * @param sesionIniciada valor de verificacion
 */
    public void setSesionIniciada(boolean sesionIniciada) {
        this.sesionIniciada = sesionIniciada;
    }
    /**
     * Obtener el nombre de usuario
     * @return nombre de usuario
     */
    public String getNombreDeUsuario() {
        return nombreDeUsuario;
    }
    /**
     * obtener ip del servidor
     * @return ip
     */
    public String getIP() {
        return IP;
    }
    /**
     * obtener puerto del servidor
     * @return puerto
     */
    public int getPuerto() {
        return Puerto;
    }
/**
 * obtener tip del juego
 * @return tip
 */
    public boolean getTipJuego() {
        return tipJuego;
    }
    /**
     * obtener recursos del idioma
     * @return recursos
     */
    public ResourceBundle getIdioma() {
        return this.resource;
    }
/**
 * verificar si la sesion esta iniciada
 * @return valor de verificacion
 */
    public boolean isSesionIniciada() {
        return sesionIniciada;
    }
    /**
     * Obtiene conexion con el servidor
     * @return conexion establecida
     * @throws RemoteException error en la conexion con el servidor
     * @throws NotBoundException error en la obtencion de conexion
     */
    public Remote obtenerConexionConElServidor() throws RemoteException, NotBoundException {
        return LocateRegistry.getRegistry(
                this.IP
        ).lookup("ServidorOthello");
    }
}

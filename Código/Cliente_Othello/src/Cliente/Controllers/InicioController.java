package Cliente.Controllers;

import Cliente.EscenaPrincipal;
import Modelo.Configuracion;
import Interfaces.ControlledScreen;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javax.swing.JOptionPane;
import Modelo.Conexion;
import java.awt.HeadlessException;
import java.rmi.RemoteException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * Clase controladora de la escena principal y su funcionabilidad
 * @author Miguel Acosta
 */
public class InicioController implements Initializable, ControlledScreen {

    private EscenaPrincipal escena;
    @FXML
    private Label titulo;
    @FXML
    private Label subTituloInicio;
    @FXML
    private Label labelUsuario;
    @FXML
    private Label labelContraseña;
    @FXML
    private Label mensajeInicio;
    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtContraseña;
    @FXML
    private Button botonInicio;
    @FXML
    private Label subTituloCrear;
    @FXML
    private Label labelNombre;
    @FXML
    private Label labelCuentaContraseña;
    @FXML
    private Label labelReContraseña;
    @FXML
    private Label mensajeCrear;
    @FXML
    private TextField txtNombre;
    @FXML
    private PasswordField txtCuentaContraseña;
    @FXML
    private PasswordField txtReContraseña;
    @FXML
    private Button botonRegistrarse;
    @FXML
    private Button configuracion;
    @FXML
    private ImageView fondo;

    /**
     * Verifica si una cadena contiene caracteres no validos
     * @param cadena valor a verificar
     * @return valor de verificacion
     */
    public boolean esAlfanumerico(String cadena) {
        for (int i = 0; i < cadena.length(); ++i) {
            char caracter = cadena.charAt(i);
            if (!Character.isLetterOrDigit(caracter)) {
                return false;
            }
        }
        return true;
    }
/**
 * Verifica si una cadena es del tamaño correcto para crear un nombre de usuario
 * @param cadena valor a verificar
 * @return valor de verificacion
 */
    public boolean tamañoUsurioCorrecto(String cadena) {
        return cadena.length() <= 20;
    }
/**
 * Verifica si una cadena es del tamaño correcto para ser asignado a una contraseña
 * @param contraseña valor a verificar
 * @return valor de verificacion
 */
    public boolean tamañoContraseñaCorrecto(String contraseña) {
        return contraseña.length() >= 8 && contraseña.length() <= 16;
    }

    /**
     * Verifica si una cadena esta vacia
     * @param cadena cadena a verificar
     * @return valor de verificacion
     */
    public boolean esVacio(String cadena) {
        return cadena.equals("");
    }
/**
 * A partir de un evento programado inicia sesion en el sistema
 * @param evento evento programado
 */
    @FXML
    private void iniciarSesion(ActionEvent evento) {        
        String usuario = this.txtUsuario.getText();
        String contraseña = this.txtContraseña.getText();
        String mensaje = "";
        ResourceBundle recursos = Configuracion.getConfiguracion().getIdioma();
        if (!esVacio(this.txtUsuario.getText()) && !esVacio(this.txtContraseña.getText())) {
            if (this.esAlfanumerico(usuario)) {
                if (this.tamañoUsurioCorrecto(usuario)) {
                    if (this.tamañoContraseñaCorrecto(contraseña)) {
                        try {
                            if (new Conexion().validarUsuario(usuario, contraseña)) {
                                Configuracion.getConfiguracion().setSesionIniciada(true);
                                Configuracion.getConfiguracion().setNombreDeUsuario(usuario);
                                this.escena.cargarEscena(EscenaPrincipal.EscenaMenuPrincipal);
                            } else {
                                mensaje = recursos.getString("MensajeCuentaInvalida");
                            }
                        } catch (RemoteException e) {
                            mensaje = recursos.getString("MensajeSinConexion");
                        }
                    } else {
                        mensaje = recursos.getString("MensajeTamañoContraseña");
                    }
                } else {
                    mensaje = recursos.getString("MensajeTamañaUsuario");
                }
            } else {
                mensaje = recursos.getString("MensajeNoEsAlfanumerico");
            }
        } else {
            mensaje = recursos.getString("MensajeCamposVacios");
        }
        this.mensajeInicio.setText(mensaje);
    }
/**
 * A partir de un evento programado se toman valores y se crea un registro en la base de datos del sistema
 * @param evento evento programado
 */
    @FXML
    private void crearCuenta(ActionEvent evento) {
        String usuario = this.txtNombre.getText();
        String contraseña = this.txtCuentaContraseña.getText();
        String reContraseña = this.txtReContraseña.getText();
        ResourceBundle recursos = Configuracion.getConfiguracion().getIdioma();
        String mensaje = "";
        if (!esVacio(usuario) && !esVacio(contraseña) && !esVacio(reContraseña)) {
            if (this.esAlfanumerico(usuario)) {
                if (this.tamañoUsurioCorrecto(usuario)) {
                    if (contraseña.equals(reContraseña)) {
                        if (this.tamañoContraseñaCorrecto(contraseña)) {
                            try {
                                if (new Conexion().registrarUsuario(usuario, contraseña)) {
                                    this.txtNombre.setText("");
                                    this.txtCuentaContraseña.setText("");
                                    this.txtReContraseña.setText("");
                                    JOptionPane.showMessageDialog(null, recursos.getString("MensajeCuentaCreada"));
                                } else {
                                    mensaje = recursos.getString("MensajeFalloCrearCuenta");
                                }
                            } catch (HeadlessException | RemoteException e) {
                                mensaje = recursos.getString("MensajeSinConexion");
                            }
                        } else {
                            mensaje = recursos.getString("MensajeTamañoContraseña");
                        }
                    } else {
                        mensaje = recursos.getString("MensajeContraseñasNoiguales");
                    }
                } else {
                    mensaje = recursos.getString("MensajeTamañaUsuario");
                }
            } else {
                mensaje = recursos.getString("MensajeNoEsAlfanumerico");
            }
        } else {
            mensaje = recursos.getString("MensajeCamposVacios");
        }
        this.mensajeCrear.setText(mensaje);
    }
/**
 * apartir de un evento se cambia la configuracion que eligio el usuario
 * @param evento evento programado
 */
    @FXML
    private void cambiarConfiguracion(ActionEvent evento) {
        this.escena.cargarEscena(EscenaPrincipal.EscenaConfiguracion);
    }
/**
 * metodo de inicializacion de la escena
 * @param location direccion por defecto
 * @param resources recursos tomados para establecer los valores de la escena
 */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fondo.setImage(new Image("Recursos/Imagenes/Fondo_Inicio.jpg"));
        ResourceBundle recursos = Configuracion.getConfiguracion().getIdioma();
        this.titulo.setText(recursos.getString("Titulo"));
        this.subTituloInicio.setText(recursos.getString("SubTituloInicio"));
        this.labelUsuario.setText(recursos.getString("LabelUsuario"));
        this.labelContraseña.setText(recursos.getString("LabelContraseña"));
        this.botonInicio.setText(recursos.getString("BotonInicio"));
        this.subTituloCrear.setText(recursos.getString("SubTituloCrear"));
        this.labelNombre.setText(recursos.getString("LabelNombre"));
        this.labelCuentaContraseña.setText(recursos.getString("LabelContraseña"));
        this.labelReContraseña.setText(recursos.getString("LabelReContraseña"));
        this.botonRegistrarse.setText(recursos.getString("BotonCrear"));
        this.configuracion.setText(recursos.getString("BotonConfiguracion"));
        this.mensajeInicio.setText("");
        this.mensajeCrear.setText("");
        this.txtNombre.setPromptText(recursos.getString("MensajePrompUsuario"));
        this.txtCuentaContraseña.setPromptText(recursos.getString("MensajePrompContraseña"));
    }
/**
     * Metodo para asignar escena a la ventana
     * @param escena escena que sera asignada
     */
    @Override
    public void setEscena(EscenaPrincipal escena) {
        this.escena = escena;
    }
}

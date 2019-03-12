package Cliente.Controllers;

import Cliente.EscenaPrincipal;
import Modelo.Configuracion;
import Interfaces.ControlledScreen;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * Clase encargada de el controlador de configuraciones del cliente
 * @author Miguel Acosta
 */
public class ConfiguracionController implements Initializable, ControlledScreen {
    private EscenaPrincipal escena;
    @FXML
    private Label titulo;
    @FXML
    private Label idioma;
    @FXML
    private Label ip;
    @FXML
    private Label puerto;
    @FXML
    private TextField txtIp;
    @FXML
    private TextField txtPuerto;
    @FXML
    private Button cancelar;
    @FXML
    private Button guardar;
    @FXML
    private ComboBox idiomas;
    @FXML
    private Label mensajeIP;
    @FXML
    private Label mensajePuerto;
    @FXML
    private ImageView fondo;
    
    
    @FXML
    private void cancelarConfiguracion(ActionEvent evento) {
        if (Configuracion.getConfiguracion().isSesionIniciada()) {
            this.escena.cargarEscena(EscenaPrincipal.EscenaMenuPrincipal);
        } else {
            this.escena.cargarEscena(EscenaPrincipal.EscenaInicio);
        }
    }
    /**
     * Verificacion de cambios en la configuracion
     * @return valor booleano de verificacion
     */
    public boolean sinCambios() {
        return idiomas.getValue() == null
                && this.txtIp.getText().equals("")
                && this.txtPuerto.getText().equals("");
    }
    /**
     * Convierte un objeto de tipo cadena a entero
     * @param numero cadena a convertir
     * @return cadena convertida
     */
    public int stringToNumber(String numero) {
        try {
            return Integer.parseInt(numero);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    /**
     * Metodo para validar la direccion de un nuevo puerto
     * @return valor para verificar si el nuevo metodo es valido
     */
    public boolean validarPuerto() {
        int numero;
        if (!this.txtPuerto.getText().equals("")) {
            numero = stringToNumber(this.txtPuerto.getText());
            if (numero != -1) {
                if (numero < 1 || numero > 9999) {
                    this.mensajePuerto.setText(
                            Configuracion.getConfiguracion().getIdioma().getString("MensajePuertoFueraDeRango")
                    );
                    return false;
                }
            } else {
                this.mensajePuerto.setText(
                        Configuracion.getConfiguracion().getIdioma().getString("MensajePuertoInvalido")
                );
                return false;
            }
        } else {
            return true;
        }
        Configuracion.getConfiguracion().setPuerto(numero);
        return true;
    }
    /**
     * Metodo encargado de verificar la direccion ip que se usara en el cliente
     * @return valor para verificar si la direccion ip es valida
     */
    public boolean validarIp() {
        if (!this.txtIp.getText().equals("")) {
            if (!this.txtIp.getText().equals("localhost")) {
                String octetos[] = this.txtIp.getText().split("\\.");
                if (octetos.length == 4) {
                    for (String octeto : octetos) {
                        int numero = stringToNumber(octeto);
                        if (numero == -1) {
                            this.mensajeIP.setText(
                                    Configuracion.getConfiguracion().getIdioma().getString("MensajeIpInvalido")
                            );
                            return false;
                        }
                        if (numero < 0 || numero > 255) {
                            this.mensajeIP.setText(
                                    Configuracion.getConfiguracion().getIdioma().getString("MensajeOctetoInvalido")
                            );
                            return false;
                        }                        
                    }
                } else {
                    this.mensajeIP.setText(
                            Configuracion.getConfiguracion().getIdioma().getString("MensajeIpIcompleta")
                    );
                    return false;
                }
            }
        } else {
            return true;
        }
        Configuracion.getConfiguracion().setIP(this.txtIp.getText());
        return true;
    }
    /**
     * metodo encargado de guardar los cambios en la configuracion del cliente
     * @param evento accion de presionar un boton
     * @throws MalformedURLException 
     */
    @FXML
    private void guardarConfiguracion(ActionEvent evento) throws MalformedURLException {
        if (validarPuerto()) {
            if (validarIp()) {
                if (this.idiomas.getValue() != null) {
                    if (this.idiomas.getValue().equals(Configuracion.getConfiguracion().getIdioma().getString("IdiomaEspañol"))) {
                        Configuracion.getConfiguracion().setIdioma("Recursos.Idiomas.Idioma_es_mx");
                    } else {
                        Configuracion.getConfiguracion().setIdioma("Recursos.Idiomas.Idioma_en_us");
                    }
                }
                if (Configuracion.getConfiguracion().isSesionIniciada()) {
                    this.escena.cargarEscena(EscenaPrincipal.EscenaMenuPrincipal);
                } else {
                    this.escena.cargarEscena(EscenaPrincipal.EscenaInicio);
                }
            }
        }
    }
    /**
     * metodo de inicializacion con parametros elegidos por el usuario
     * @param url direccion por defecto
     * @param rb recursos para la configuracion de la escena
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.fondo.setImage(new Image("Recursos/Imagenes/Fondo_Configuracion.png"));
        ResourceBundle recursos = Configuracion.getConfiguracion().getIdioma();
        this.titulo.setText(recursos.getString("ConfigTitulo"));
        this.idioma.setText(recursos.getString("ConfigIdioma"));
        this.ip.setText(recursos.getString("ConfigIP"));
        this.puerto.setText(recursos.getString("ConfigPuerto"));
        this.cancelar.setText(recursos.getString("ConfigCancelar"));
        this.guardar.setText(recursos.getString("ConfigGuardar"));
        this.txtIp.setPromptText(Configuracion.getConfiguracion().getIP());
        this.txtPuerto.setPromptText(String.valueOf(Configuracion.getConfiguracion().getPuerto()));
        this.mensajeIP.setText("");
        this.mensajePuerto.setText("");
        ObservableList<String> opciones = FXCollections.observableArrayList(
                recursos.getString("IdiomaEspañol"),
                recursos.getString("IdiomaIngles")
        );
        this.idiomas.setItems(opciones);
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

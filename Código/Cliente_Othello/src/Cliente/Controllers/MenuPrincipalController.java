package Cliente.Controllers;

import Cliente.EscenaPrincipal;
import Modelo.Configuracion;
import Interfaces.ControlledScreen;
import Modelo.Conexion;
import Modelo.Juego;
import Modelo.MejorJugador;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * Clase manejadora de la interfaz principal del sistema
 * @author Miguel Acosta
 */
public class MenuPrincipalController implements Initializable, ControlledScreen {

    private EscenaPrincipal escena;
    @FXML
    private Label titulo;
    @FXML
    private Label mensajeBienvenida;
    @FXML
    private Label tituloTabla;
    @FXML
    private Label numeroDeVictorias;
    @FXML
    private Button jugarVsCPU;
    @FXML
    private Button jugarEnLinea;
    @FXML
    private Button historial;
    @FXML
    private Button configuracion;
    @FXML
    private ImageView fondo;
    @FXML
    private TableView<MejorJugador> tabla;
    @FXML
    private TableColumn columnaUsuario;
    @FXML
    private TableColumn columnaNumeroDeVictorias;
    private ObservableList<MejorJugador> datos;

    @FXML
    private void jugarContraLaCPU(ActionEvent evento) {
        Configuracion.getConfiguracion().setTipJuego(Juego.VS_CPU);
        this.escena.cargarEscena(EscenaPrincipal.EscenaJuego);
    }

    @FXML
    private void jugarEnLinea(ActionEvent evento) {
        Configuracion.getConfiguracion().setTipJuego(Juego.VS_JUGADOR);
        this.escena.cargarEscena(EscenaPrincipal.EscenaJuego);
    }

    @FXML
    private void verHistorialDePartidas(ActionEvent evento) {
        this.escena.cargarEscena(EscenaPrincipal.EscenaHistorial);
    }

    @FXML
    private void cambiarConfiguracion(ActionEvent evento) {
        this.escena.cargarEscena(EscenaPrincipal.EscenaConfiguracion);
    }
/**
 * Se inicializa la escena con los recursos dados por el usuario
 * @param url direccion por defecto
 * @param rb recursos que se estableceran en la escena
 */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.fondo.setImage(new Image("Recursos/Imagenes/Fondo_MenuPrincipal.jpg"));
        rb = Configuracion.getConfiguracion().getIdioma();
        this.titulo.setText(rb.getString("Titulo"));
        this.mensajeBienvenida.setText(rb.getString("Bienvenida") + Configuracion.getConfiguracion().getNombreDeUsuario());
        this.tituloTabla.setText(rb.getString("MejoresJugadores"));
        this.jugarVsCPU.setText(rb.getString("BotonJugarVsCPU"));
        this.jugarEnLinea.setText(rb.getString("BotonJugarEnLinea"));
        this.historial.setText(rb.getString("BotonHistorial"));
        this.configuracion.setText(rb.getString("BotonConfiguracion"));
        this.columnaUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        this.columnaNumeroDeVictorias.setCellValueFactory(new PropertyValueFactory<>("numeroDeVictorias"));
        this.columnaUsuario.setText(rb.getString("ColumnaUsuario"));
        this.columnaNumeroDeVictorias.setText(rb.getString("ColumnaNumeroDeVictorias"));
        this.datos = FXCollections.observableArrayList();
        this.tabla.setItems(datos);
        try {
            for (MejorJugador jugador : new Conexion().obtenerMenoresJugadores()) {
                datos.add(jugador);
            }
            this.numeroDeVictorias.setText(rb.getString("NoVictorias") + new Conexion().obtenerNumeroDeVictorias());
        } catch (Exception e) {
            datos.add(new MejorJugador(rb.getString("MensajeSinConexionCorto"), -1));
        }
    }

    /**
     * Se establece la escena en la ventana 
     * @param escena escena que se va a cargar
     */
    @Override
    public void setEscena(EscenaPrincipal escena) {
        this.escena = escena;
    }
}

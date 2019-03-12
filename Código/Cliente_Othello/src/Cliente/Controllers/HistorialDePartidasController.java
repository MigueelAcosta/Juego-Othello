package Cliente.Controllers;

import Cliente.EscenaPrincipal;
import Interfaces.ConexionBaseDeDatos;
import Interfaces.ControlledScreen;
import Modelo.Conexion;
import Modelo.Configuracion;
import Modelo.Partida;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
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
 * Clase controladora de la interfaz y el comportamiento del historial de partidas
 * @author Miguel Acosta
 */
public class HistorialDePartidasController implements Initializable, ControlledScreen {
    private EscenaPrincipal escena;
    @FXML
    private Label titulo;
    @FXML
    private Button regresar;
    @FXML
    private ImageView fondo;
    @FXML
    private TableView<Partida> tabla;
    @FXML
    private TableColumn columnaFecha;
    @FXML
    private TableColumn columnaAdversario;
    @FXML
    private TableColumn columnaDuracion;
    @FXML
    private TableColumn columnaResultado;
    private ObservableList<Partida> datos;

    @FXML
    private void regresarAlMenuPrincipal(ActionEvent evento) {
        this.escena.cargarEscena(EscenaPrincipal.EscenaMenuPrincipal);
    }
/**
 * metodo inicializador de la escena
 * @param url direccion por defecto
 * @param rb recursos tomados para establecer escena
 */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.fondo.setImage(new Image("Recursos/Imagenes/Fondo_Historial.jpg"));
        
        rb = Configuracion.getConfiguracion().getIdioma();
        this.titulo.setText(rb.getString("HistorialTitulo"));
        this.regresar.setText(rb.getString("BotonRegresar"));
        
        this.columnaFecha.setCellValueFactory(
                new PropertyValueFactory<>("fecha")
        );
        this.columnaAdversario.setCellValueFactory(
                new PropertyValueFactory<>("adversario")
        );
        this.columnaDuracion.setCellValueFactory(
                new PropertyValueFactory<>("duracion")
        );
        this.columnaResultado.setCellValueFactory(
                new PropertyValueFactory<>("resultado")
        );
        
        this.columnaFecha.setText(rb.getString("ColumnaFecha"));
        this.columnaAdversario.setText(rb.getString("ColumnaAdversario"));
        this.columnaDuracion.setText(rb.getString("ColumnaDuracion"));
        this.columnaResultado.setText(rb.getString("ColumnaResultado"));
        this.datos = FXCollections.observableArrayList();
        this.tabla.setItems(datos);
        
        try{
            for(Partida partida: new Conexion().obtenerHistorialDePartidas()){
                partida.setResultado(rb.getString(partida.getResultado()));
                datos.add(partida);
            }
        }catch(RemoteException e){
            this.datos.add(new Partida(
                    "",Configuracion.getConfiguracion().getIdioma().getString("MensajeSinConexionCorto")
                    ,"",""));
        }
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

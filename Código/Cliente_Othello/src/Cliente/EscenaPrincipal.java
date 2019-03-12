package Cliente;

import Interfaces.Cliente;
import Interfaces.ControlledScreen;
import Modelo.Conexion;
import java.io.IOException;
import java.rmi.RemoteException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Clase para la seleccion de escenas en las interfaces
 * @author Miguel Acosta
 */

public class EscenaPrincipal extends Application implements Cliente{
    private Stage mainStage;
    private Group mainScene;
   
    public static String EscenaInicio = "FXML/Inicio.fxml";
    public static String EscenaConfiguracion = "FXML/Configuracion.fxml";
    public static String EscenaMenuPrincipal = "FXML/MenuPrincipal.fxml";
    public static String EscenaHistorial = "FXML/HistorialDePartidas.fxml";
    public static String EscenaJuego = "FXML/Juego.fxml";

    public Stage getMainStage() {
        return this.mainStage;
    }
/**
 * metodo manejador de escenas
 * @param escena escena que sera cargada en la ventana
 * @return Escena principal
 */
    @SuppressWarnings("empty-statement")
    public boolean cargarEscena(String escena) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(escena));
            AnchorPane screen = (AnchorPane) myLoader.load();
            if (escena.equals(EscenaPrincipal.EscenaJuego)) {
                this.mainStage.setHeight(650);
                this.mainStage.setWidth(900);
                this.mainStage.setOnCloseRequest(Event ->{
                    new Conexion().desconectar();
                    System.exit(0);
                });
            } else {
                this.mainStage.setHeight(520);
                this.mainStage.setWidth(710);
            }
            if (this.mainScene.getChildren().isEmpty()) {
                mainScene.getChildren().add(screen);
            } else {
                mainScene.getChildren().remove(0);
                mainScene.getChildren().add(0, screen);
            }
            ControlledScreen miEscena = (ControlledScreen) myLoader.getController();
            miEscena.setEscena(this);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
/**
 * Metodo de inicializacion de la ventana principal
 * @param primaryStage ventana que sera inicializada
 * @throws IOException error en la entrada/salida 
 */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Group root = new Group();
        this.mainStage = primaryStage;
        this.mainScene = root;
        this.mainStage.setResizable(false);
        this.mainStage.setTitle("Othello");
        this.mainStage.getIcons().add(new Image("Recursos/Imagenes/icono.png"));
        this.cargarEscena(EscenaPrincipal.EscenaInicio);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /**
     * metodo de actualizacion del estado de un juego
     * @param juego nombre del juego a actualizar
     * @throws RemoteException error al establecer la conexion con el servidor
     */
    @Override
    public void actualizarJuego(String juego) throws RemoteException {
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

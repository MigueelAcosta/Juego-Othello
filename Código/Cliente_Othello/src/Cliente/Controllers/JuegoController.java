package Cliente.Controllers;

import Cliente.EscenaPrincipal;
import Interfaces.Cliente;
import Interfaces.ControlledScreen;
import Modelo.Conexion;
import Modelo.Configuracion;
import Modelo.Juego;
import java.awt.HeadlessException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;

/**
 * Clase controlador de los eventos posibles durante el juego
 *
 * @author Miguel Acosta
 */
public class JuegoController implements Initializable, ControlledScreen, Cliente {

    private EscenaPrincipal escena;
    @FXML
    private GridPane tablero;
    @FXML
    private Label titulo;
    @FXML
    private Label turno;
    @FXML
    private Label imagenJugadorUno;
    @FXML
    private Label nombreJugadorUno;
    @FXML
    private Label numeroDeFichasJugadorUno;
    @FXML
    private Label numeroDeMovimientoJugadorUno;
    @FXML
    private Label imagenJugadorDos;
    @FXML
    private Label nombreJugadorDos;
    @FXML
    private Label numeroDeFichasJugadorDos;
    @FXML
    private Label numeroDeMovimientoJugadorDos;
    @FXML
    private Button guardar;
    @FXML
    private Button rendirse;
    @FXML
    private ImageView fondo;
    public Juego juegoActual = new Juego();

    private final int VACIO = 0;
    private final int BLANCO = 1;
    private final int NEGRO = 2;
    private final int JUGABLE = 3;

    /**
     * A partir de un evento programado un usuario termina y pierde
     * automaticamente la partida
     *
     * @param e evento programado
     */
    @FXML
    public void rendirse(ActionEvent e) {
        try {
            new Conexion().rendirse(this.juegoActual.getID());
        } catch (RemoteException ex) {
            this.notificarSinConexion();
        }
    }

    /**
     * A partir de un evento se guarda el estado de una partida
     *
     * @param e evento programado
     */
    @FXML
    public void guardar(ActionEvent e) {
        if (juegoActual.getEstadoJuego() == Juego.EN_PROGRESO) {
            try {
                new Conexion().guardarJuego(this.juegoActual.getID());
                if (this.juegoActual.getTipoDeJuego() == Juego.VS_JUGADOR) {
                    JOptionPane.showMessageDialog(null, Configuracion.getConfiguracion()
                            .getIdioma().getString("MensajeGuardarYSalir"));
                }
                this.escena.cargarEscena(EscenaPrincipal.EscenaMenuPrincipal);
            } catch (HeadlessException | RemoteException ex) {
                notificarSinConexion();
            }
        } else {
            if (this.juegoActual.getEstadoJuego() == Juego.EN_ESPERA) {
                try {
                    new Conexion().cancelarEsperaDeJuego();
                } catch (RemoteException ex) {
                    this.notificarSinConexion();
                }
            }
            this.escena.cargarEscena(EscenaPrincipal.EscenaMenuPrincipal);
        }
    }

    /**
     * Se notifica al usuario que no hay conexion
     */
    public void notificarSinConexion() {
        JOptionPane.showMessageDialog(null,
                Configuracion.getConfiguracion().getIdioma().getString("MensajeSinConexion")
        );
    }

    /**
     * Crea una conexion
     *
     * @throws RemoteException error al establecer conexion
     */
    public void conectar() throws RemoteException {
        UnicastRemoteObject.exportObject(this, 0);
    }

    /**
     * Inicializa la escena con los parametros dados por el usuario
     *
     * @param url direccion por defecto
     * @param rb recursos para el establecimiento de la escena
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.fondo.setImage(new Image("Recursos/Imagenes/Fondo_Juego.png"));
        rb = Configuracion.getConfiguracion().getIdioma();
        this.rendirse.setVisible(false);
        this.titulo.setText(rb.getString("Titulo"));
        this.guardar.setText(rb.getString("BotonRegresar"));
        this.inicializarTablero();
        juegoActual.setEstadoJuego(Juego.EN_ESPERA);
        try {
            this.conectar();
            if (Configuracion.getConfiguracion().getTipJuego() == Juego.VS_JUGADOR) {
                if (!new Conexion().reanudarJuegoContraJugador(this)) {
                    new Conexion().jugarEnLinea(this);
                }
            } else if (!new Conexion().reanudarJuegoContraCPU(this)) {
                new Conexion().jugarContraCPU(this);
            }
        } catch (Exception e) {
            notificarSinConexion();
        }
    }

    /**
     * Se establece la interfaz de espera de oponente en la interaz de usuario
     */
    public void cargarEspera() {
        for (Node nodo : this.tablero.getChildren()) {
            if (GridPane.getColumnIndex(nodo) != null) {
                int y = GridPane.getRowIndex(nodo);
                int x = GridPane.getColumnIndex(nodo);
                Image imagen = null;
                String directorio = "Recursos/Imagenes/Tema_Estandar/"
                        + Configuracion.getConfiguracion().getIdioma().getString("RutaEspera");
                if (y == 3) {
                    switch (x) {
                        case 2:
                            imagen = new Image(directorio + "Esperando_01.gif");
                            break;
                        case 3:
                            imagen = new Image(directorio + "Esperando_02.gif");
                            break;
                        case 4:
                            imagen = new Image(directorio + "Esperando_03.gif");
                            break;
                        case 5:
                            imagen = new Image(directorio + "Esperando_04.gif");
                            break;
                        case 6:
                            imagen = new Image(directorio + "Esperando_05.gif");
                            break;
                        case 7:
                            imagen = new Image(directorio + "cargar.gif");
                            break;
                    }
                }
                ((ImageView) nodo).setImage(imagen);
            }
        }
    }

    /**
     * Se inicializa el tablero para un juego nuevo
     */
    public void inicializarTablero() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.tablero.setGridLinesVisible(false);
                ImageView imagen = new ImageView();
                imagen.setFitHeight(70);
                imagen.setFitWidth(70);
                imagen.setImage(new Image("Recursos/Imagenes/Tema_Estandar/defecto.png"));
                this.tablero.add(imagen, i, j);
                GridPane.setValignment(imagen, VPos.CENTER);
                GridPane.setHalignment(imagen, HPos.CENTER);
                imagen.setOnMouseClicked((MouseEvent event) -> {
                    Object sourceObject = event.getSource();
                    ImageView spot = (ImageView) sourceObject;
                    int y = GridPane.getRowIndex(spot);
                    int x = GridPane.getColumnIndex(spot);
                    if (this.juegoActual.esMiTurno()) {
                        try {
                            if (!(new Conexion().realizarMovimiento(y, x, this.juegoActual.getID()))) {
                                JOptionPane.showMessageDialog(null,
                                        Configuracion.getConfiguracion().getIdioma().getString("MensajeMovimientoInvalido")
                                );
                            }
                        } catch (HeadlessException | RemoteException ex) {
                            this.notificarSinConexion();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,
                                Configuracion.getConfiguracion().getIdioma().getString("MensajeTurnoInvalido")
                        );
                    }
                });
            }
        }
    }

    /**
     *
     * Se actualizan los campos de la interfaz del juego
     */
    public void actualizarEtiquetas() {
        ResourceBundle idioma = Configuracion.getConfiguracion().getIdioma();
        this.turno.setText(idioma.getString(juegoActual.getTurno()));
        String directorio = "Recursos/Imagenes/Tema_Estandar/";

        ImageView imagenViewJugadorUno = new ImageView();
        imagenViewJugadorUno.setFitHeight(80);
        imagenViewJugadorUno.setFitWidth(80);
        imagenViewJugadorUno.setImage(new Image(
                directorio + juegoActual.getImagenJugadorUno()
        ));
        this.imagenJugadorUno.setGraphic(imagenViewJugadorUno);
        this.nombreJugadorUno.setText(juegoActual.getNombreJugadorUno());
        this.numeroDeFichasJugadorUno.setText(idioma.getString("Fichas")
                + juegoActual.getNumeroDeFichasJugadorUno()
        );
        this.numeroDeMovimientoJugadorUno.setText(idioma.getString("Movimientos")
                + juegoActual.getNumeroDeMovimientosJugadorUno()
        );
        ImageView imagenViewJugadorDos = new ImageView();
        imagenViewJugadorDos.setFitHeight(80);
        imagenViewJugadorDos.setFitWidth(80);
        imagenViewJugadorDos.setImage(new Image(directorio + juegoActual.getImagenJugadorDos())
        );
        this.imagenJugadorDos.setGraphic(imagenViewJugadorDos);
        this.nombreJugadorDos.setText(juegoActual.getNombreJugadorDos());
        this.numeroDeFichasJugadorDos.setText(idioma.getString("Fichas")
                + juegoActual.getNumeroDeFichasJugadorDos()
        );
        this.numeroDeMovimientoJugadorDos.setText(idioma.getString("Movimientos")
                + juegoActual.getNumeroDeMovimientosJugadorDos()
        );
        this.rendirse.setVisible(true);
        this.rendirse.setText(idioma.getString("BotonRendirse"));
        this.guardar.setText(idioma.getString("BotonGuardarYSalir"));
    }

    /**
     * Se actualiza el tablero de juego con el tablero logico
     *
     * @param tablero tablero que se actualizara
     */
    public void actualizarTablero(Integer tablero[][]) {
        for (Node nodo : this.tablero.getChildren()) {
            if (GridPane.getColumnIndex(nodo) != null) {
                int y = GridPane.getRowIndex(nodo);
                int x = GridPane.getColumnIndex(nodo);
                Image imagen = null;
                String directorio = "Recursos/Imagenes/Tema_Estandar/";
                switch (tablero[y][x]) {
                    case VACIO:
                        imagen = new Image(directorio + "vacio.png");
                        break;
                    case BLANCO:
                        imagen = new Image(directorio + "blanco.png");
                        break;
                    case NEGRO:
                        imagen = new Image(directorio + "negro.png");
                        break;
                    case JUGABLE:
                        if (this.juegoActual.esMiTurno()) {
                            imagen = new Image(directorio + "jugable.png");
                        } else {
                            imagen = new Image(directorio + "vacio.png");
                        }
                        break;
                    default: 
                        break;
                }
                ((ImageView) nodo).setImage(imagen);
            }
        }
    }

    private void cargarResultado(String directorio) {
        this.tablero.getChildren().stream().filter((nodo) -> (GridPane.getColumnIndex(nodo) != null)).map((nodo) -> {
            int y = GridPane.getRowIndex(nodo);
            int x = GridPane.getColumnIndex(nodo);
            ((ImageView) nodo).setImage(new Image(
                    directorio + y + (x + 1) + ".png"));
            return nodo;
        }).forEachOrdered((nodo) -> {
            ((ImageView) nodo).setDisable(false);
        });
    }

    private void cargarEmpate() {
        String directorio = "Recursos/Imagenes/Tema_Estandar/"
                + Configuracion.getConfiguracion().getIdioma().getString("RutaEmpate");
        this.cargarResultado(directorio);
    }

    private void cargarVictoria() {
        String directorio = "Recursos/Imagenes/Tema_Estandar/"
                + Configuracion.getConfiguracion().getIdioma().getString("RutaVictoria");
        this.cargarResultado(directorio);
    }

    private void cargarDerrota() {
        String directorio = "Recursos/Imagenes/Tema_Estandar/"
                + Configuracion.getConfiguracion().getIdioma().getString("RutaDerrota");
        this.cargarResultado(directorio);
    }

    /**
     * Se carga el resultado de la partida y se muestra en la interfaz
     */
    public void cargarResultadoDelJuego() {
        this.rendirse.setVisible(false);
        this.guardar.setText(Configuracion.getConfiguracion()
                .getIdioma()
                .getString("BotonRegresar"));
        if (this.juegoActual.getEstadoJuego() == Juego.FIN_DEL_JUEGO_EMPATE) {
            this.cargarEmpate();
        } else if (Configuracion.getConfiguracion().getNombreDeUsuario()
                .equals(this.juegoActual.getNombreJugadorUno())) {

            if (this.juegoActual.getEstadoJuego() == Juego.FIN_DEL_JUEGO_GANADOR_NEGRO) {
                this.cargarVictoria();
            } else {
                this.cargarDerrota();
            }
        } else if (this.juegoActual.getEstadoJuego() == Juego.FIN_DEL_JUEGO_GANADOR_BLANCO) {
            this.cargarVictoria();
        } else {
            this.cargarDerrota();
        }
    }

    /**
     * Se actualiza el estado del juego y los componentes de la interfaz
     *
     * @param juego juego que se actualizara
     * @throws RemoteException error en la conexion
     */
    @Override
    public void actualizarJuego(String juego) throws RemoteException {
        Platform.runLater(() -> {
            juegoActual = Juego.fromJson(juego);
            switch (juegoActual.getEstadoJuego()) {
                case Juego.EN_ESPERA:
                    this.cargarEspera();
                    break;
                case Juego.EN_PROGRESO:
                    this.actualizarTablero(juegoActual.getTablero());
                    this.actualizarEtiquetas();
                    break;
                case Juego.FIN_DEL_JUEGO_EMPATE:
                case Juego.FIN_DEL_JUEGO_GANADOR_BLANCO:
                case Juego.FIN_DEL_JUEGO_GANADOR_NEGRO:
                    this.cargarResultadoDelJuego();
                    break;
            }
        });
    }

    /**
     * Se establece la escena en la ventana
     *
     * @param escena escena que se cargara
     */
    @Override
    public void setEscena(EscenaPrincipal escena) {
        this.escena = escena;
    }
}

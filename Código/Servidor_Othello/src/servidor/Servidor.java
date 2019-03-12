package servidor;

import BaseDeDatos.MejoresjugadoresJpaController;
import BaseDeDatos.PartidaJpaController;
import BaseDeDatos.PartidacontracpuJpaController;
import BaseDeDatos.UsuarioJpaController;
import BaseDeDatos.exceptions.NonexistentEntityException;
import Interfaces.Cliente;
import Modelo.MejorJugador;
import Modelo.PartidaCliente;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import Interfaces.ConexionBaseDeDatos;
import Modelo.Juego;
import Modelo.Othello;
import Persistencia.Mejoresjugadores;
import Persistencia.Partida;
import Persistencia.Partidacontracpu;
import Persistencia.Usuario;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Persistence;
/**
 * Clase principal del servidor
 * @author Miguel Acosta
 * @author Mauricio Juarez
 */
public class Servidor extends UnicastRemoteObject implements ConexionBaseDeDatos, Interfaces.Servidor {

    private Integer ID_JUEGO = 0;
    private final HashMap<Integer, Juego> juegos;
    private final HashMap<String, Integer> juegosPendientes;
    private Juego juegoEnLinea = null;

    /**
     * constructor de la clase
     * @throws RemoteException 
     */
    public Servidor() throws RemoteException {
        this.juegos = new HashMap<>();
        this.juegosPendientes = new HashMap<>();
    }

    /**
     * Valida un usuario que intenta ingresar con su usuario y contrasena al sistema
     * @param usuario usuario a verificar
     * @param contraseña contrasena a verificar
     * @return valor de verificacion
     * @throws RemoteException error de conexion con el servidor
     */
    @Override
    public boolean validarUsuario(String usuario, String contraseña) throws RemoteException {
        UsuarioJpaController usuarioController = new UsuarioJpaController(
                Persistence.createEntityManagerFactory("Servidor_OthelloPU", null)
        );
        Usuario user = usuarioController.findUsuario(usuario);
        return this.makeHash(contraseña).equals(user.getContraseña());
    }
    /**
     * registro de un usuario en la base de datos
     * @param usuario usuario que se registrara en la base de datos
     * @param contraseña contrasena que se asiganara a el usuario en la BD
     * @return valor de verificacion
     * @throws RemoteException error de conexion con el servidor
     */
    @Override
    public boolean registrarUsuario(String usuario, String contraseña) throws RemoteException {
        try {
            UsuarioJpaController usuarioController = new UsuarioJpaController(
                    Persistence.createEntityManagerFactory("Servidor_OthelloPU", null)
            );
            Usuario user = new Usuario();
            user.setUsuario(usuario);
            user.setContraseña(this.makeHash(contraseña));
            user.setNumerodevictorias(0);
            usuarioController.create(user);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Obtiene el historial de partidas de un usuario del sistema
     * @param usuario usuario que se buscara
     * @return lista de partidas del usuario 
     * @throws RemoteException error de conexion con el servidor
     */
    @Override
    public String obtenerHistorialDePartidas(String usuario) throws RemoteException {
        ArrayList<PartidaCliente> partidas = new ArrayList<>();
        PartidaJpaController partidaController = new PartidaJpaController(
                Persistence.createEntityManagerFactory("Servidor_OthelloPU", null)
        );
        partidaController.findPartidaEntities().stream().filter((partida) -> (partida.getUsuario().equals(usuario))).forEachOrdered((partida) -> {
            partidas.add(new PartidaCliente(
                    partida.getFecha(),
                    partida.getAdversario(),
                    partida.getDuracion(),
                    partida.getResultado()
            ));
        });
        return new Gson().toJson(partidas);
    }
/**
 * Obtiene los jugadores con la puntuacion de partidas ganadas mas alta
 * @return lista de jugadores
 * @throws RemoteException error de recuperacion
 */
    @Override
    public String obtenerMenoresJugadores() throws RemoteException {
        List<MejorJugador> mejoresJugadores = new ArrayList<>();
        MejoresjugadoresJpaController jugadores = new MejoresjugadoresJpaController(
                Persistence.createEntityManagerFactory("Servidor_OthelloPU", null)
        );
        for (Mejoresjugadores jugador : jugadores.findMejoresjugadoresEntities()) {
            mejoresJugadores.add(new MejorJugador(
                    jugador.getUsuario(),
                    jugador.getNumeroDeVictorias()
            ));
        }
        
        Collections.sort(mejoresJugadores);
        
        return new Gson().toJson(mejoresJugadores);
    }

    /**
     * obtiene el numero de victorias de un usuario
     * @param usuario usuario que se buscara
     * @return numero de victorias
     * @throws RemoteException error de recuperacion
     */
    @Override
    public int obtenerNumeroDeVictorias(String usuario) throws RemoteException {
        return new UsuarioJpaController(
                Persistence.createEntityManagerFactory("Servidor_OthelloPU", null))
                .findUsuario(usuario)
                .getNumerodevictorias();
    }

    private String makeHash(String string) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            byte[] hash = messageDigest.digest(string.getBytes());
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                stringBuilder.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    private Juego inicializarJuego() {
        Juego juego = new Juego();
        juego.setFechaIncio(new GregorianCalendar());
        juego.setTurno("TurnoNegro");

        juego.setEstadoJuego(Juego.EN_ESPERA);
        Integer tablero[][] = {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 3, 0, 0, 0, 0},
            {0, 0, 3, 1, 2, 0, 0, 0},
            {0, 0, 0, 2, 1, 3, 0, 0},
            {0, 0, 0, 0, 3, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}
        };
        juego.setTablero(tablero);
        return juego;
    }

    /**
     * conecta a un cliente con un jugador esperando partida
     * @param cliente cliente que se va a conectar
     * @param nombreJugador jugador con el que se va a conectar el cliente
     * @param tipoDeJuego modalidad de juego
     * @throws RemoteException error de conexion con el servidor
     */
    @Override
    public void conectar(Cliente cliente, String nombreJugador, boolean tipoDeJuego) throws RemoteException {
        if (tipoDeJuego == Juego.VS_JUGADOR) {
            if (this.juegoEnLinea == null) {
                this.juegoEnLinea = this.inicializarJuego();
                this.juegoEnLinea.setID(this.ID_JUEGO);
                this.juegoEnLinea.setTipoDeJuego(Juego.VS_JUGADOR);
                this.juegoEnLinea.setClienteJugadorUno(cliente);
                this.juegoEnLinea.setJugadorUno("negro.png", nombreJugador, 0, 0);
                this.ID_JUEGO++;
                cliente.actualizarJuego(this.juegoEnLinea.toJson());
            } else {
                this.juegoEnLinea.setEstadoJuego(Juego.EN_PROGRESO);
                this.juegoEnLinea.setClienteJugadorDos(cliente);
                this.juegoEnLinea.setJugadorDos("blanco.png", nombreJugador, 0, 0);
                this.juegos.put(this.juegoEnLinea.getID(), this.juegoEnLinea);
                this.juegoEnLinea.getClienteJugadorUno().actualizarJuego(this.juegoEnLinea.toJson());
                this.juegoEnLinea.getClienteJugadorDos().actualizarJuego(this.juegoEnLinea.toJson());
                this.juegoEnLinea = null;
            }
        } else {
            Juego juegoVsCPU = this.inicializarJuego();
            juegoVsCPU.setID(this.ID_JUEGO);
            juegoVsCPU.setEstadoJuego(Juego.EN_PROGRESO);
            juegoVsCPU.setTipoDeJuego(Juego.VS_CPU);
            juegoVsCPU.setClienteJugadorUno(cliente);
            juegoVsCPU.setJugadorUno("negro.png", nombreJugador, 0, 0);
            juegoVsCPU.setClienteJugadorDos(null);
            juegoVsCPU.setJugadorDos("blanco.png", "CPU", 0, 0);
            this.juegos.put(juegoVsCPU.getID(), juegoVsCPU);
            cliente.actualizarJuego(juegoVsCPU.toJson());
            this.ID_JUEGO++;
        }
    }

    /**
     * Se realiza un movimiento en el tablero
     * @param x valor del eje X del movimiento
     * @param y valor del eje Y del movimiento
     * @param idJuego identificador del juego donde se realizara el movimiento
     * @return valor de verificacion
     * @throws RemoteException error de conexion con el servidor
     */
    @Override
    public boolean realizarMovimiento(int x, int y, int idJuego) throws RemoteException {
        Juego juego = this.juegos.get(idJuego);        
        Othello logicaOthello = new Othello(juego.getTablero());
        if (juego.getTipoDeJuego() == Juego.VS_JUGADOR) {
            int color = (juego.getTurno().equals("TurnoNegro") ? Othello.NEGRO : Othello.BLANCO);
            if (logicaOthello.realizarMovimientoContraJugador(x, y, color)) {
                juego.setEstadoJuego(logicaOthello.getEstado());
                juego.setTablero(logicaOthello.getTablero());
                juego.setNumeroDeFichasJugadorUno(logicaOthello.getFichasNegras());
                juego.setNumeroDeFichasJugadorDos(logicaOthello.getFichasBlancas());
                if (color == Othello.NEGRO) {
                    juego.setTurno("TurnoBlanco");
                    juego.aumentarNumeroDeMovimientosDeJugadorUno();
                } else {
                    juego.setTurno("TurnoNegro");
                    juego.aumentarNumeroDeMovimientosDeJugadorDos();
                }
                this.juegos.replace(idJuego, juego);
                juego.getClienteJugadorUno().actualizarJuego(juego.toJson());
                juego.getClienteJugadorDos().actualizarJuego(juego.toJson());
                if(juego.getEstadoJuego()!=Juego.EN_ESPERA){
                    if(juego.getEstadoJuego()!=Juego.EN_PROGRESO){
                        this.finDelJuego(idJuego);
                    }
                }
                
                return true;
            } else {
                return false;
            }
        } else {
            if (logicaOthello.realizarMovimientoContraCPU(x, y)) {
                juego.setTablero(logicaOthello.getTablero());
                juego.setEstadoJuego(logicaOthello.getEstado());
                juego.aumentarNumeroDeMovimientosDeJugadorUno();
                juego.aumentarNumeroDeMovimientosDeJugadorDos();
                juego.setNumeroDeFichasJugadorUno(logicaOthello.getFichasNegras());
                juego.setNumeroDeFichasJugadorDos(logicaOthello.getFichasBlancas());
                this.juegos.replace(idJuego, juego);
                juego.getClienteJugadorUno().actualizarJuego(juego.toJson());
                if(juego.getEstadoJuego()!=Juego.EN_ESPERA){
                    if(juego.getEstadoJuego()!=Juego.EN_PROGRESO){
                        this.finDelJuego(idJuego);
                    }
                }
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Aumentar la puntuacion de partidas ganadas de un usuario en la base de datos
     * @param usuario usuario al que se le aumentara la puntuacion
     */
    private void aumentarUnoEnPatidasGanadas(String usuario) {
        UsuarioJpaController usuarioController = new UsuarioJpaController(
                Persistence.createEntityManagerFactory("Servidor_OthelloPU", null)
        );
        Usuario auxiliar = usuarioController.findUsuario(usuario);
        try {
            usuarioController.destroy(usuario);
            auxiliar.setNumerodevictorias(auxiliar.getNumerodevictorias() + 1);
            usuarioController.create(auxiliar);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Guardar una partida en la base de datos
     * @param jugador Jugador que esta guardando la partida
     * @param fecha fecha de guardado
     * @param adversario jugador contra quien se esta en la partida
     * @param duracion duracion de la partida
     * @param resultado resultado de la partida
     */
    private void guardarPartidaEnBD(String jugador, String fecha, String adversario, String duracion, String resultado) {
        PartidaJpaController partidaController = new PartidaJpaController(
                Persistence.createEntityManagerFactory("Servidor_OthelloPU", null)
        );
        Partida partida = new Partida();
        partida.setUsuario(jugador);
        partida.setFecha(fecha);
        partida.setAdversario(adversario);
        partida.setDuracion(duracion);
        partida.setResultado(resultado);
        partidaController.create(partida);
    }

    /**
     * Actualiza la lista de mejores jugadores segun sus victorias
     */
    private void actualizarMejoresJugadores() {
        UsuarioJpaController usuarioController = new UsuarioJpaController(
                Persistence.createEntityManagerFactory("Servidor_OthelloPU", null)
        );

        MejoresjugadoresJpaController mejorJugadorController = new MejoresjugadoresJpaController(
                Persistence.createEntityManagerFactory("Servidor_OthelloPU", null)
        );
        for (Mejoresjugadores mejor : mejorJugadorController.findMejoresjugadoresEntities()) {
            try {
                mejorJugadorController.destroy(mejor.getUsuario());
            } catch (NonexistentEntityException ex) {
            }
        }
        List<Usuario> usuarios = usuarioController.findUsuarioEntities();
        Collections.sort(usuarios);
        int maximo = 0;
        for (Usuario usuario : usuarios) {
            try {
                if (maximo < 10) {
                    Mejoresjugadores mejor = new Mejoresjugadores();
                    mejor.setUsuario(usuario.getUsuario());
                    mejor.setNumeroDeVictorias(usuario.getNumerodevictorias());
                    mejorJugadorController.create(mejor);
                    maximo++;
                } else {
                    break;
                }
            } catch (Exception ex) {
            }
        }
    }

    /**
     * Da una partida como terminada
     * @param idJuego identificador del juego que se terminara
     */
    private void finDelJuego(int idJuego) {
        Juego auxiliar = this.juegos.get(idJuego);
        auxiliar.setFechaFin(new GregorianCalendar());
        switch (auxiliar.getEstadoJuego()) {
            case Juego.FIN_DEL_JUEGO_EMPATE:
                this.guardarPartidaEnBD(auxiliar.getNombreJugadorUno(),
                        auxiliar.getFechaDeJuego(),
                        auxiliar.getNombreJugadorDos(),
                        auxiliar.getDuracionDeJuego(),
                        "PartidaEmpate");
                this.guardarPartidaEnBD(auxiliar.getNombreJugadorDos(),
                        auxiliar.getFechaDeJuego(),
                        auxiliar.getNombreJugadorUno(),
                        auxiliar.getDuracionDeJuego(),
                        "PartidaEmpate");
                break;
            case Juego.FIN_DEL_JUEGO_GANADOR_NEGRO:
                this.guardarPartidaEnBD(auxiliar.getNombreJugadorUno(),
                        auxiliar.getFechaDeJuego(),
                        auxiliar.getNombreJugadorDos(),
                        auxiliar.getDuracionDeJuego(),
                        "PartidaVictoria");
                this.aumentarUnoEnPatidasGanadas(auxiliar.getNombreJugadorUno());
                this.guardarPartidaEnBD(auxiliar.getNombreJugadorDos(),
                        auxiliar.getFechaDeJuego(),
                        auxiliar.getNombreJugadorUno(),
                        auxiliar.getDuracionDeJuego(),
                        "PartidaDerrota");
                this.actualizarMejoresJugadores();
                break;
            case Juego.FIN_DEL_JUEGO_GANADOR_BLANCO:
                this.guardarPartidaEnBD(auxiliar.getNombreJugadorUno(),
                        auxiliar.getFechaDeJuego(),
                        auxiliar.getNombreJugadorDos(),
                        auxiliar.getDuracionDeJuego(),
                        "PartidaDerrota");
                this.guardarPartidaEnBD(auxiliar.getNombreJugadorDos(),
                        auxiliar.getFechaDeJuego(),
                        auxiliar.getNombreJugadorUno(),
                        auxiliar.getDuracionDeJuego(),
                        "PartidaVictoria");
                this.aumentarUnoEnPatidasGanadas(auxiliar.getNombreJugadorDos());
                this.actualizarMejoresJugadores();
                break;
            default:
                break;
        }
        this.juegos.remove(idJuego);
    }

    /**
     * Se guarda el estado de un juego
     * @param usuario usuario que guarda el juego
     * @param idJuego identificador del juego a guardar
     * @return valor de verificacion
     * @throws RemoteException error al intertar guardar
     */
    @Override
    public boolean guardarJuego(String usuario, int idJuego) throws RemoteException {
        Juego auxiliar = this.juegos.get(idJuego);
        if (auxiliar.getTipoDeJuego() == Juego.VS_JUGADOR) {
            this.juegosPendientes.put(usuario, auxiliar.getID());
            auxiliar.setEstadoJuego(Juego.EN_ESPERA);
            if (auxiliar.getNombreJugadorUno().equals(usuario)) {
                auxiliar.getClienteJugadorDos().actualizarJuego(auxiliar.toJson());
            } else {
                auxiliar.getClienteJugadorUno().actualizarJuego(auxiliar.toJson());
            }
            new Thread() {
                private final String user = usuario;

                @Override
                public void run() {
                    try {
                        Thread.sleep(180000);
                        if (juegosPendientes.containsKey(user)) {
                            Juego pendiente = juegos.get(juegosPendientes.get(user));
                            juegosPendientes.remove(this.user);
                            if (pendiente.getNombreJugadorUno().equals(user)) {
                                pendiente.setEstadoJuego(Juego.FIN_DEL_JUEGO_GANADOR_BLANCO);
                                pendiente.getClienteJugadorDos().actualizarJuego(pendiente.toJson());
                            } else {
                                pendiente.setEstadoJuego(Juego.FIN_DEL_JUEGO_GANADOR_NEGRO);
                                pendiente.getClienteJugadorUno().actualizarJuego(pendiente.toJson());
                            }
                            finDelJuego(pendiente.getID());
                        }
                    } catch (InterruptedException | RemoteException ex) {
                    }//Se desconectaron ambos jugadores}
                }
            }.start();
            return true;
        } else {
            PartidacontracpuJpaController partidaController = new PartidacontracpuJpaController(
                    Persistence.createEntityManagerFactory("Servidor_OthelloPU", null)
            );
            Partidacontracpu juegoCPU = new Partidacontracpu();
            juegoCPU.setUsuario(usuario);
            String tablero = new Gson().toJson(auxiliar.getTablero());
            juegoCPU.setJuego(tablero);
            try {
                partidaController.create(juegoCPU);
                this.juegos.remove(idJuego);
            } catch (Exception ex) {
                return false;
            }
            return true;
        }
    }

    /**
     * recupera el estado de una partida que ha sido guardada anteriormente
     * @param usuario nombre de usuario de quien reanuda el juego
     * @param cliente instancia del cliente que recupera el juego
     * @param tipoJuego modalidad del juego
     * @return valor de verificacion
     * @throws RemoteException error de recuperacion
     */
    @Override
    public boolean reanudarJuego(String usuario, Cliente cliente, boolean tipoJuego) throws RemoteException {
        if (tipoJuego == Juego.VS_JUGADOR) {
            if (this.juegosPendientes.containsKey(usuario)) {
                Juego auxiliar = this.juegos.get(this.juegosPendientes.get(usuario));
                this.juegosPendientes.remove(usuario);
                auxiliar.setEstadoJuego(Juego.EN_PROGRESO);
                if (auxiliar.getNombreJugadorUno().equals(usuario)) {
                    auxiliar.setClienteJugadorUno(cliente);
                } else {
                    auxiliar.setClienteJugadorDos(cliente);
                }
                this.juegos.replace(auxiliar.getID(), auxiliar);
                auxiliar.getClienteJugadorUno().actualizarJuego(auxiliar.toJson());
                auxiliar.getClienteJugadorDos().actualizarJuego(auxiliar.toJson());
                return true;
            } else {
                return false;
            }
        } else {
            PartidacontracpuJpaController partidaController = new PartidacontracpuJpaController(
                    Persistence.createEntityManagerFactory("Servidor_OthelloPU", null)
            );
            Partidacontracpu partidaCPU = partidaController.findPartidacontracpu(usuario);
            if (partidaCPU != null) {
                try {
                    Integer tablero[][] = new Gson().fromJson(partidaCPU.getJuego(), Integer[][].class);
                    Othello othello = new Othello(tablero);
                    othello.actualizarMarcadores();
                    Juego auxiliar = this.inicializarJuego();
                    auxiliar.setJugadorUno("negro.png", usuario, othello.getFichasNegras(), 0);
                    auxiliar.setJugadorDos("blanco.png", "CPU", othello.getFichasBlancas(), 0);
                    auxiliar.setTablero(tablero);
                    auxiliar.setTurno("TurnoNegro");
                    auxiliar.setEstadoJuego(Juego.EN_PROGRESO);
                    auxiliar.setID(this.ID_JUEGO);
                    this.ID_JUEGO++;
                    auxiliar.setClienteJugadorUno(cliente);
                    this.juegos.put(auxiliar.getID(), auxiliar);
                    auxiliar.getClienteJugadorUno().actualizarJuego(auxiliar.toJson());
                    partidaController.destroy(usuario);
                    return true;
                } catch (NonexistentEntityException | JsonSyntaxException | RemoteException ex) {
                    return false;
                }
            }
            return false;
        }
    }

    /**
     * Se da la victoria al contrincante de forma automatica
     * @param usuario usuario que se ha rendido
     * @param idJuego juego que se va a terminar
     * @throws RemoteException 
     */
    @Override
    public void rendirse(String usuario, int idJuego) throws RemoteException {
        Juego juegoAuxiliar = this.juegos.get(idJuego);
        if (juegoAuxiliar.getNombreJugadorUno().equals(usuario)) {
            juegoAuxiliar.setEstadoJuego(Juego.FIN_DEL_JUEGO_GANADOR_BLANCO);
        } else {
            juegoAuxiliar.setEstadoJuego(Juego.FIN_DEL_JUEGO_GANADOR_NEGRO);
        }

        juegoAuxiliar.getClienteJugadorUno().actualizarJuego(juegoAuxiliar.toJson());
        if (juegoAuxiliar.getTipoDeJuego() == Juego.VS_JUGADOR) {
            juegoAuxiliar.getClienteJugadorDos().actualizarJuego(juegoAuxiliar.toJson());
        }
        this.juegos.replace(idJuego, juegoAuxiliar);
        this.finDelJuego(idJuego);
    }

    /**
     * Un juego en espera se cancela
     * @throws RemoteException error con el servidor
     */
    @Override
    public void cancelarEsperaDeJuego() throws RemoteException {
        this.juegoEnLinea = null;
    }

    /**
     * Se desconecta un cliente del servidor
     * @param usuario usuario que se desconectara
     * @throws RemoteException error en la desconexion
     */
    @Override
    public void desconectar(String usuario) throws RemoteException {
        for (Juego juego : this.juegos.values()) {
            if (juego.getNombreJugadorUno().equals(usuario)
                    || juego.getNombreJugadorDos().equals(usuario)) {
                this.guardarJuego(usuario, juego.getID());
            }
        }
    }

    public static void main(String[] args) throws RemoteException, MalformedURLException, IOException, NotBoundException {
        Runtime.getRuntime().exec("rmiregistry");
        int puerto = 8080;
        String url = "rmi://localhost:8080/ServidorOthello";
        LocateRegistry.createRegistry(puerto);
        Naming.rebind(url, new Servidor());
        System.out.println("Servidor corriendo en: " + url);
    }
}

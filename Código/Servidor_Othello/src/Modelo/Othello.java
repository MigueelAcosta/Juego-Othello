package Modelo;

import java.util.ArrayList;
import java.util.Random;

/**
 * Clase encargada de la logica del juego
 * @author Miguel Acosta
 */
public class Othello {
    private Integer tablero[][];
    private int fichasBlancas = 0;
    private int fichasNegras = 0;
    private int estado;
    private final int[] sentidoFila = {-1, -1, -1, 0, 0, 1, 1, 1};
    private final int[] sentidoColumna = {-1, 0, 1, -1, 1, -1, 0, 1};

    private final int VACIO = 0;
    public static final int BLANCO = 1;
    public static final int NEGRO = 2;
    private final int JUGABLE = 3;
    private final int TAMAÑO = 8;

    /**
     * constructor de la clase
     * @param tablero tablero para crear o reanudar un juego
     */
    public Othello(Integer tablero[][]) {
        this.tablero = tablero;
    }

    /**
     * clase para el manejo de coordenadas dentro de la matriz logica del juego
     */
    class Coordenada {

        private final int x;
        private final int y;

        public Coordenada(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    public Integer[][] getTablero() {
        return tablero;
    }

    public int getEstado() {
        return estado;
    }

    public int getFichasBlancas() {
        return fichasBlancas;
    }

    public int getFichasNegras() {
        return fichasNegras;
    }
    
    private int obtenerOponente(int color) {
        return color == Othello.BLANCO ? Othello.NEGRO : Othello.BLANCO;
    }

    private boolean esMovimientoValido(int x, int y, int color) {//turno 1 Negro; 2 Blanco
        if (this.tablero[x][y] == Othello.BLANCO || this.tablero[x][y] == Othello.NEGRO) {
            return false;
        }
        int oponente = this.obtenerOponente(color);
        boolean valido = false;
        for (int i = 0; i < this.TAMAÑO; i++) {
            int fila = x + this.sentidoFila[i];
            int columna = y + this.sentidoColumna[i];
            boolean piezaEncontrada = false;
            while (fila >= 0 && fila <= 7 && columna >= 0 && columna <= 7) {
                if (this.tablero[fila][columna] == oponente) {
                    piezaEncontrada = true;
                } else if (this.tablero[fila][columna] == color && piezaEncontrada) {
                    valido = true;
                } else {
                    break;
                }
                fila += this.sentidoFila[i];
                columna += this.sentidoColumna[i];
            }
        }
        return valido;
    }

    private ArrayList<Coordenada> movimientoValidos(int color) {
        ArrayList<Coordenada> coordenadas = new ArrayList<>();
        for (int i = 0; i < this.TAMAÑO; i++) {
            for (int j = 0; j < this.TAMAÑO; j++) {
                if (this.esMovimientoValido(i, j, color)) {
                    coordenadas.add(new Coordenada(i, j));
                }
            }
        }
        return coordenadas;
    }
    
   private void realizarMovimiento(int x, int y, int color) {
        this.quitarRecomendados();
        this.tablero[x][y] = color;
        ArrayList<Coordenada> auxiliar = new ArrayList<>();
        ArrayList<Coordenada> temporal = new ArrayList<>();
        int oponente = this.obtenerOponente(color);
        for (int i = 0; i < this.TAMAÑO; i++) {
            int fila = x + this.sentidoFila[i];
            int columna = y + this.sentidoColumna[i];
            boolean encontrado = false;
            while (fila >= 0 && fila <= 7 && columna >= 0 && columna <= 7) {
                if (this.tablero[fila][columna] == oponente) {
                    temporal.add(new Coordenada(fila, columna));
                    encontrado = true;
                } else {
                    if ((this.tablero[fila][columna] == color) && encontrado) {
                        for (Coordenada cx : temporal) {
                            auxiliar.add(cx);
                        }
                    } else {
                        temporal.clear();
                        break;
                    }
                }
                fila += this.sentidoFila[i];
                columna += this.sentidoColumna[i];
            }
        }
        for (Coordenada a : auxiliar) {
            this.tablero[a.getX()][a.getY()] = color;
        }
    }

    /**
     * metodo encargado de realizar movimiento de un jugador en un juego en linea
     * @param x valor del eje X del movimiento
     * @param y valor del eje y del movimiento
     * @param color color del jugador en turno
     * @return verificaion de realizacion del movimiento
     */
    public boolean realizarMovimientoContraJugador(int x, int y, int color) {
        if (this.esMovimientoValido(x, y, color)) {
            this.realizarMovimiento(x, y, color);
            this.agregarNuevosRecomendados(this.movimientoValidos(this.obtenerOponente(color)));
            this.actualizarMarcadores();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Se encarga de realizar un movimiento en un juego contra una cpu
     * @param x valor del eje X del movimiento
     * @param y valor del eje Y del movimiento
     * @return verificacion de realizacion del movimiento
     */
    public boolean realizarMovimientoContraCPU(int x, int y) {
        ArrayList<Coordenada> coordenadas;
        if (this.esMovimientoValido(x, y, Othello.NEGRO)) {
            this.realizarMovimiento(x, y, Othello.NEGRO);
            coordenadas = this.movimientoValidos(Othello.BLANCO);
            if (!coordenadas.isEmpty()) {
                int movimientoCPU = Math.abs((new Random().nextInt()) % coordenadas.size());
                Coordenada coordenadaCPU = coordenadas.get(movimientoCPU);
                this.realizarMovimiento(coordenadaCPU.getX(), coordenadaCPU.getY(), Othello.BLANCO);
                this.agregarNuevosRecomendados(this.movimientoValidos(Othello.NEGRO));
                this.actualizarMarcadores();
            }else{
                this.actualizarMarcadores();
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Actualiza la puntuacion de cada jugador en el juego
     */
    public void actualizarMarcadores() {
        int blanco = 0;
        int negro = 0;
        int jugable = 0;
        for (int i = 0; i < this.TAMAÑO; i++) {
            for (int j = 0; j < this.TAMAÑO; j++) {
                switch (this.tablero[i][j]) {
                    case BLANCO:
                        blanco++;
                        break;
                    case NEGRO:
                        negro++;
                        break;
                    case JUGABLE:
                        jugable++;
                        break;
                }
            }
        }
        this.fichasBlancas = blanco;
        this.fichasNegras = negro;
        if (jugable == 0) {
            if(this.fichasBlancas==this.fichasNegras){
                this.estado = Juego.FIN_DEL_JUEGO_EMPATE;
            }else{
                if(this.fichasBlancas>this.fichasNegras){
                    this.estado=Juego.FIN_DEL_JUEGO_GANADOR_BLANCO;
                }else{
                    this.estado=Juego.FIN_DEL_JUEGO_GANADOR_NEGRO;
                }
            }
        } else {
            this.estado = Juego.EN_PROGRESO;
        }
    }

    private void quitarRecomendados() {
        for (int i = 0; i < this.TAMAÑO; i++) {
            for (int j = 0; j < this.TAMAÑO; j++) {
                if (this.tablero[i][j] == this.JUGABLE) {
                    this.tablero[i][j] = this.VACIO;
                }
            }
        }
    }

    private void agregarNuevosRecomendados(ArrayList<Coordenada> coordenadas) {
        this.quitarRecomendados();
        for (Coordenada coordenada : coordenadas) {
            this.tablero[coordenada.getX()][coordenada.getY()] = this.JUGABLE;
        }
    }

}


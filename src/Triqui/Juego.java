package Triqui;

public final class Juego {

    //<editor-fold defaultstate="collapsed" desc="Variables convensionales para determinar el fin del juego »">
    public final static int TERMINAR_NA = -1;
    
    public final static int TERMINAR_COL1 = 0;
    public final static int TERMINAR_COL2 = 1;
    public final static int TERMINAR_COL3 = 2;
    
    public final static int TERMINAR_FIL1 = 3;
    public final static int TERMINAR_FIL2 = 4;
    public final static int TERMINAR_FIL3 = 5;
    
    public final static int TERMINAR_DIAG1 = 6;
    public final static int TERMINAR_DIAG2 = 7;
    //</editor-fold>

    int Jugador = 1;

    int[][] Tablero = new int[3][3];

    int Ganador = 0;
    int Linea_Terminar = -1;

    public Juego() {
        LimpiarTablero();
    }

    void reiniciarJuego() {//<editor-fold defaultstate="collapsed" desc="Implementación de código »">
        LimpiarTablero();
        Ganador = 0;
        Linea_Terminar = TERMINAR_NA;
    }//</editor-fold>

    void terminarJuego() {//<editor-fold defaultstate="collapsed" desc="Implementación de código »">
        Linea_Terminar = BuscarFin();
        if (Linea_Terminar != TERMINAR_NA) {
            switch (Linea_Terminar) {
                //<editor-fold defaultstate="collapsed" desc="Asignar al ganador en la columna »">
                case TERMINAR_COL1:
                case TERMINAR_COL2:
                case TERMINAR_COL3:
                    Ganador = Tablero[Linea_Terminar][0];
                    break;
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="Asignar al ganador en la fila »">
                case TERMINAR_FIL1:
                case TERMINAR_FIL2:
                case TERMINAR_FIL3:
                    Ganador = Tablero[0][Linea_Terminar - 3];
                    break;
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="Asignar al ganador en la diagonal principal »">
                case TERMINAR_DIAG1:
                    Ganador = Tablero[0][0];
                    break;
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="Asignar al ganador en la diagonal secundaria »">
                case TERMINAR_DIAG2:
                    Ganador = Tablero[0][2];
                    break;
                //</editor-fold>
            }
        }
    }//</editor-fold>

    void LimpiarTablero() {//<editor-fold defaultstate="collapsed" desc="Implementación de código »">
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Tablero[i][j] = 0;
            }
        }
    }//</editor-fold>

    void registrarJugada(int c, int f) {//<editor-fold defaultstate="collapsed" desc="Implementación de código »">
        if (Ganador!=0) {
            //<editor-fold defaultstate="collapsed" desc="Si ya hay un ganador, no registra la jugada">
            return;
            //</editor-fold>
        }
        if (Tablero[c][f]!=0) {
            //<editor-fold defaultstate="collapsed" desc="Si ya han usado previamente esa celda, entonces no hace nada">
            return;
            //</editor-fold>
        }
        Tablero[c][f] = Jugador;
        if (Jugador == 1) {
            Jugador = 2;
        } else {
            Jugador = 1;
        }
        terminarJuego();
    }//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Métodos que buscan una línea finalizadora en el tablero, busca al ganador »">
    int BuscarFin() {//<editor-fold defaultstate="collapsed" desc="Implementación de código »">
        {
            //<editor-fold defaultstate="collapsed" desc="Buscar el fin en las columnas »">
            int col = BuscarFinColumnas();
            if (col != -1) {
                return col;
            }
            //</editor-fold>
        }
        {
            //<editor-fold defaultstate="collapsed" desc="Buscar el fin en las filas »">
            int fil = BuscarFinFilas();
            if (fil != -1) {
                return fil + 3;
            }
            //</editor-fold>
        }
        {
            //<editor-fold defaultstate="collapsed" desc="Buscar el fin en la diagonal principal »">
            if (BuscarFinDiagonalPrincipal()) {
                return TERMINAR_DIAG1;
            }
            //</editor-fold>
        }
        {
            //<editor-fold defaultstate="collapsed" desc="Buscar el fin en la diagonal secundaria »">
            if (BuscarFinDiagonalSecundaria()) {
                return TERMINAR_DIAG2;
            }
            //</editor-fold>
        }
        {
            //<editor-fold defaultstate="collapsed" desc="No se encontró fin »">
            return TERMINAR_NA;
            //</editor-fold>
        }
    }//</editor-fold>

    int BuscarFinColumnas() {//<editor-fold defaultstate="collapsed" desc="Implementación de código »">
        int piv = 1;
        for (int c = 0; c < 3; c++) {
            //<editor-fold defaultstate="collapsed" desc="Recorrido de cada una de las columnas del juego »">
            for (int f = 0; f < 3; f++) {
                //<editor-fold defaultstate="collapsed" desc="Verificación de la columna para determinar si esta columna finalizará el juego">
                if (Tablero[c][f] == 0) {
                    //<editor-fold defaultstate="collapsed" desc="Si en esa columna hay una celda vacía, se descarta esa columna como columna de finalización »">
                    break;
                    //</editor-fold>
                }
                if (f == 0) {
                    //<editor-fold defaultstate="collapsed" desc="Se toma el primer dato de la columna como dato pivote »">
                    piv = Tablero[c][f];
                    continue;
                    //</editor-fold>
                }
                if (Tablero[c][f] != piv) {
                    //<editor-fold defaultstate="collapsed" desc="Si algún dato de la columna no coincide con el dato pivote, se descarta esa columna como columna de finalización »">
                    break;
                    //</editor-fold>
                }
                if (f == 2) {
                    //<editor-fold defaultstate="collapsed" desc="Se retorna la columna finalizadora del juego »">
                    /*
                    * Si se llega al último dato de la columna y todos
                    * coinciden con el dato pivote, entonces se ha encontrado a
                    * la columna ganadora, consecuentemente se retorna
                     */
                    return c;
                    //</editor-fold>
                }
                //</editor-fold>
            }
            //</editor-fold>
        }
        return TERMINAR_NA;
    }//</editor-fold>

    int BuscarFinFilas() {//<editor-fold defaultstate="collapsed" desc="Implementación de código »">
        int piv = 1;
        for (int f = 0; f < 3; f++) {
            //<editor-fold defaultstate="collapsed" desc="Recorrido de cada una de las filas del juego »">
            for (int c = 0; c < 3; c++) {
                //<editor-fold defaultstate="collapsed" desc="Verificación de la fila para determinar si esta fila finalizará el juego »">
                if (Tablero[c][f] == 0) {
                    //<editor-fold defaultstate="collapsed" desc="Si en esa fila hay una celda vacía, se descarta esa fila como fila de finalización »">
                    break;
                    //</editor-fold>
                }
                if (c == 0) {
                    //<editor-fold defaultstate="collapsed" desc="Se toma el primer dato de la fila como dato pivote »">
                    piv = Tablero[c][f];
                    continue;
                    //</editor-fold>
                }
                if (Tablero[c][f] != piv) {
                    //<editor-fold defaultstate="collapsed" desc="Si algún dato de la fila no coincide con el dato pivote, se descarta esa fila como fila de finalización »">
                    break;
                    //</editor-fold>
                }
                if (c == 2) {
                    //<editor-fold defaultstate="collapsed" desc="Se retorna la fila finalizadora del juego »">
                    /*
                    * Si se llega al último dato de la fila y todos
                    * coinciden con el dato pivote, entonces se ha encontrado a
                    * la columna ganadora, consecuentemente se retorna
                     */
                    return f;
                    //</editor-fold>
                }
                //</editor-fold>
            }
            //</editor-fold>
        }
        return TERMINAR_NA;
    }//</editor-fold>

    boolean BuscarFinDiagonalPrincipal() {//<editor-fold defaultstate="collapsed" desc="Implementación de código »">
        return BuscarFinDiagonal(true);
    }//</editor-fold>

    boolean BuscarFinDiagonalSecundaria() {//<editor-fold defaultstate="collapsed" desc="Implementación de código »">
        return BuscarFinDiagonal(false);
    }//</editor-fold>

    boolean BuscarFinDiagonal(boolean DiagonalPrincipal) {//<editor-fold defaultstate="collapsed" desc="Implementación de código »">
        int piv = 1;
        for (int c = 0; c < 3; c++) {
            //<editor-fold defaultstate="collapsed" desc="Verificación de los datos de la diagonal »">
            int f = DiagonalPrincipal ? c : 2 - c;
            if (Tablero[c][f] == 0) {
                //<editor-fold defaultstate="collapsed" desc="Si se encuentra una celda vacía en la diagonal, entonces la diagonal se descarta como finalizadora »">
                return false;
                //</editor-fold>
            }
            if (c == 0) {
                //<editor-fold defaultstate="collapsed" desc="El primer dato de la diagonal se toma como dato pivote »">
                piv = Tablero[c][f];
                continue;
                //</editor-fold>
            }
            if (Tablero[c][f] != piv) {
                //<editor-fold defaultstate="collapsed" desc="Si algún dato de la fila no coincide con el pivote, entonces se descarta la diagonal como finalizadora">
                return false;
                //</editor-fold>
            }
            //</editor-fold>
        }
        /**
         * Al salir de el ciclo se asume que todas las celdas de la diagonal
         * cumplen con las reglas para que la diagonal sea la finalizadora del
         * juego, por consecuencia se retorna true, para indicar que sí finaliza
         */
        return true;
    }//</editor-fold>
    //</editor-fold>

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementacion;

import model.Player;

/**
 *
 * @author CrazyJMB
 */
public class Matriz {
    private final int ROWS = 7;
    private final int COLUMNS = 8;

    private Player[][] playerPOS;

    public Matriz() {
        playerPOS = new Player[COLUMNS][ROWS];
    }

    public Player getPlayerAtPos(int x, int y) {
        return playerPOS[x][y];
    }

    public void setPlayerOnPos(int x, int y, Player p) {
        playerPOS[x][y] = p;
    }

    public boolean isColumnFill(int x) {
        for (int i = ROWS - 1; i >= 0; i--) {
            if (getPlayerAtPos(x, i) == null) {
                return false;
            }
        }
        return true;
    }

    // PRECONDICION: Comprobar que la columna no esta llena
    public int posNextInColumn(int x) {
        int y = ROWS - 1;
        while (y >= 0 && getPlayerAtPos(x, y) != null) {
            y--;
        }
        return y;
    }

    public Player isWin(int x, int y) {
        
        if (getWinHorizontal(y) != null) {
            return getWinHorizontal(y);
        }

        if (getWinVertical(x) != null) {
            return getWinVertical(x);
        }

        if (getWinDiagonalDerIzq(x, y) != null) {
            return getWinDiagonalDerIzq(x, y);
        }

        if (getWinDiagonalIzqDer(x, y) != null) {
            return getWinDiagonalIzqDer(x, y);
        }

        return null;
    }

    public void showContent() {
        for (int y = 0; y < ROWS; y++) {
            for (int x  = 0; x < COLUMNS; x++) {
                if (getPlayerAtPos(x, y) != null) {
                    System.out.print(getPlayerAtPos(x, y).getNickName() + " ");
                } else {
                    System.out.print(getPlayerAtPos(x, y) + " ");
                }
            }
            System.out.println();
        }
    }

    // Metodo verificado
    private Player getWinVertical(int x) {
        // Comprobamos la verticales en curso
        for (int y = 0; y < ROWS - 3; y++) {
            if (getPlayerAtPos(x, y) == getPlayerAtPos(x, y + 1) &&
                    getPlayerAtPos(x, y + 1) == getPlayerAtPos(x, y + 2) &&
                    getPlayerAtPos(x, y + 2) == getPlayerAtPos(x, y + 3)) {
                return getPlayerAtPos(x, y);
            }
        }

        return null;
    }

    // Metodo verificado
    private Player getWinHorizontal(int y) {
        // Comprobamos la horizontal en curso
        for (int x = 0; x < COLUMNS - 3; x++) {
            if (getPlayerAtPos(x, y) == getPlayerAtPos(x + 1, y) &&
                    getPlayerAtPos(x + 1, y) == getPlayerAtPos(x + 2, y) &&
                    getPlayerAtPos(x + 2, y) == getPlayerAtPos(x + 3, y)) {
                return getPlayerAtPos(x, y);
            }
        }
        return null;
    }

    // Metodo verificado
    private Player getWinDiagonalIzqDer(int x, int y) {
        // Buscar el principio de la diagonal
        int currentX = x, currentY = y;
        while (currentX > 0 && currentY > 0) {
                currentX--;
                currentY--;
        }

        while (currentX < COLUMNS - 3 && currentY < ROWS - 3) {
            if (getPlayerAtPos(currentX, currentY) == getPlayerAtPos(currentX + 1, currentY + 1) &&
                    getPlayerAtPos(currentX + 1, currentY + 1) == getPlayerAtPos(currentX + 2, currentY + 2) &&
                    getPlayerAtPos(currentX + 2, currentY + 2) == getPlayerAtPos(currentX + 3, currentY + 3)) {
                return getPlayerAtPos(currentX, currentY);
            }
            currentX++;
            currentY++;
        }
        return null;
    }

    private Player getWinDiagonalDerIzq(int x, int y) {
        // Buscar principio de la diagonal
        int currentX = x, currentY = y;

        while (currentX < COLUMNS - 1 && currentY > 0) {
            currentX++;
            currentY--;
        }

        while (currentX > 3 && currentY < ROWS - 3) {
            if (getPlayerAtPos(currentX, currentY) == getPlayerAtPos(currentX - 1, currentY + 1) &&
                    getPlayerAtPos(currentX - 1, currentY + 1) == getPlayerAtPos(currentX - 2, currentY + 2) &&
                    getPlayerAtPos(currentX - 2, currentY + 2) == getPlayerAtPos(currentX - 3, currentY + 3)) {
                return getPlayerAtPos(currentX, currentY);
            }
            currentX--;
            currentY++;
        }
        return null;
    }
}

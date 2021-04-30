/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author CrazyJMB
 */
public class InGameScreenController implements Initializable {

    // Instancias
    private final int COLUMNS = 8;
    private final int ROWS = 7;
    
    private Integer currentX;
    private Integer currentY;
    
    private int[][] circulosColocados = new int[COLUMNS][ROWS];
    private boolean[] columnasLlenas = new boolean[COLUMNS];
    
    private Boolean turno = true; // Turno jugador 1 = true
    
    @FXML
    private GridPane gridPane;
    @FXML
    private BorderPane borderPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Create grid
        makeGrid();
    } 
    
    @FXML
    private void ponerDisc(MouseEvent event) {
        if (currentX != null) {
            // Comprobamos si la fila esta vacia o llena
            int y = ROWS - 1;
            while (y >= 0 && getArrayAtPos(currentX, y) != 0) {
                y--;
            }
            
            // Comprobamos si esta llena
            if (y < 0) {
                System.out.println("Columna llena");
                
            } else {
                
                // Conseguimos el circulo de la pos y lo cambiamos de color
                Circle circle = getCircleByRowColumnIndex(y, currentX, gridPane);
                circle.setFill(turno ? Color.BLUE:Color.RED);
                
                // Actualizamos el array y cambiamos de turno
                circulosColocados[currentX][y] = turno ? 1:2;
                turno = !turno;
                
                // Actualizamos el contador de columnas llenas
                if (y == 0) {
                    columnasLlenas[currentX] = true;
                }
            }
            
            // Comprobar si alguien (ha ganado en este movimiento)
            int winner = checkWin();
            if (winner != -1) {
                // Hay ganador
                // Si es 1, es el jugador 1
                // Si es 2 es el jugador 2
                System.out.println("Winner: " + winner);
            }
        }
    }

    @FXML
    private void newGameButton(ActionEvent event) {
        for (int i = 0; i < COLUMNS; i++) {
            System.out.println(columnasLlenas[i]);
        }
    }

    @FXML
    private void restartGame(ActionEvent event) {
        for (int x = 0; x < COLUMNS; x++) {
            for (int y = 0; y < ROWS; y++) {
                //gridPane.getChildren().remove(0);
                Circle circle = getCircleByRowColumnIndex(y, x, gridPane);
                circle.setFill(Color.TRANSPARENT);
            }
        }
        
        // Reiniciamos todos los contadores
        
    }
    
    private int getArrayAtPos(int posX, int posY) {
        int result = 0;
        for (int i = 0; i <= posX; i++) {
            for (int j = 0; j <= posY; j++) {
                result = circulosColocados[posX][posY];
            }
        }
        return result;
    }
    
    private int checkWin() {
        
        // Comprobar horizonalmente
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMNS - 3; c++) {
                if (circulosColocados[r][c] == 1 && circulosColocados[r][c+1] == 1 && circulosColocados[r][c+2] == 1 && circulosColocados[r][c+3] == 1) {
                    return 1; // Gana el jugador 1
                }
                
                if (circulosColocados[r][c] == 2 && circulosColocados[r][c+1] == 2 && circulosColocados[r][c+2] == 2 && circulosColocados[r][c+3] == 2) {
                    return 2; // Gana el jugador 2
                }
            }
        }
        
        // Comprobar verticalmente
        for (int c = 0; c < COLUMNS; c++) {
            for (int r = 0; r < ROWS - 3; r++) {
                if (circulosColocados[c][r] == 1 && circulosColocados[c][r+1] == 1 && circulosColocados[c][r+2] == 1 && circulosColocados[c][r+3] == 1) {
                    return 1; // Gana el jugador 1
                }
                
                if (circulosColocados[c][r] == 2 && circulosColocados[c][r+1] == 2 && circulosColocados[c][r+2] == 2 && circulosColocados[c][r+3] == 2) {
                    return 2; // Gana el jugador 2
                }
            }
        }
        
        // Comprobar diagonalmente
        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < 10; j++) {
                
            }
        }

        return -1;
    }
    
    private void makeGrid() {
        for (int x = 0; x < COLUMNS; x++) {
            for (int y = 0; y < ROWS; y++) {
                // Constraints
                ColumnConstraints column = new ColumnConstraints();
                column.setHalignment(HPos.CENTER); 
                column.setFillWidth(true);
                
                gridPane.getColumnConstraints().addAll(column);
                
                Circle circle = new Circle();
                circle.radiusProperty().bind(Bindings.divide(gridPane.widthProperty(), 18));
                circle.setFill(Color.TRANSPARENT);
                
                gridPane.add(circle, x, y);
                GridPane.setHalignment(circle, HPos.CENTER);
                
            }
        }
    }
    
    private void makeColumnsOverlay() {
                
        // Creacion del rectangulo
        Rectangle rect = new Rectangle();
        rect.heightProperty().bind(Bindings.divide(gridPane.heightProperty(), 1));
        rect.widthProperty().bind(Bindings.divide(gridPane.widthProperty(), 16));
        
        for (int x = 0; x < COLUMNS; x++) {
            rect.translateXProperty().bind(Bindings.divide(gridPane.widthProperty(), (16-2*x)));
            borderPane.setCenter(rect);
        }
        
        rect.setOnMouseEntered(event -> rect.setFill(Color.valueOf("#eeeeee66")));
        rect.setOnMouseExited(event -> rect.setFill(Color.TRANSPARENT));
    }
    
    @FXML
    public void getMousePosition(MouseEvent event) {
        Node clickedNode = event.getPickResult().getIntersectedNode();
        if (clickedNode != gridPane) {
            // click on descendant node
            currentX = GridPane.getColumnIndex(clickedNode);
            currentY = GridPane.getRowIndex(clickedNode);
        }
    }
    
    @FXML
    private void setMousePosition(MouseEvent event) {
        currentX = null;
        currentY = null;
    }

    public Node getNodeByRowColumnIndex(final int row,final int column,GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();
        for(Node node : childrens) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
        return result;
    }
    
    public Circle getCircleByRowColumnIndex(final int row,final int column,GridPane gridPane) {
        Circle result = null;
        ObservableList<Node> childrens = gridPane.getChildren();
        for(Node node : childrens) {
            if(node instanceof Circle && gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = (Circle) node;
                break;
            }
        }
        return result;
    }
    
}

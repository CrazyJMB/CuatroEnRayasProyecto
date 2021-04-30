/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DBAccess.Connect4DAOException;
import implementacion.Matriz;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import model.Player;
import static controlador.LogInAppController.loginPlayer;
import static controlador.MainScreenController.secondPlayer;
import java.time.LocalDateTime;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Connect4;
import static model.Connect4.getSingletonConnect4;

/**
 * FXML Controller class
 *
 * @author CrazyJMB
 */
public class InGameScreenController implements Initializable {
    
    // Instancias
    private final int COLUMNS = 8;
    private final int ROWS = 7;
    
    private Player playerOne;
    private Player playerTwo;
    
    private boolean turnoPlayer = true;
    private boolean playingVSmachine = false;
    
    private Integer currentX;
    private Integer currentY;
    
    private Matriz circulosPos;
    private boolean[] columnasLlenas = new boolean[COLUMNS];
    
    private Alert alert = new Alert(Alert.AlertType.NONE);
    
    // Base de datos
    private Connect4 db;

    @FXML
    private BorderPane borderPane;
    @FXML
    private GridPane gridPane;
    @FXML
    private HBox firstPlayerAviso;
    @FXML
    private Circle firstPlayerAvatar;
    @FXML
    private Text firstPlayerUsername;
    @FXML
    private Text firstPlayerScore;
    @FXML
    private HBox secondPlayerAviso;
    @FXML
    private Circle secondPlayerAvatar;
    @FXML
    private Text secondPlayerUsername;
    @FXML
    private Text secondPlayerScore;
    @FXML
    private Circle firstPlayerAvisoCircle;
    @FXML
    private Circle secondPlayerAvisoCircle;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Cargamos la base de datos
        try {
            db = getSingletonConnect4();
        } catch (Connect4DAOException e) {
            System.out.println(e.toString());
        }
        
        // Inicializar
        circulosPos = new Matriz();
        
        // Crear el grid del juego
        makeGrid();
        
        // Añadimos la info de los jugadores a la pantalla
        firstPlayerAvatar.setFill(new ImagePattern(loginPlayer.getAvatar()));
        firstPlayerUsername.setText(loginPlayer.getNickName());
        firstPlayerScore.setText("" + loginPlayer.getPoints());
        
        if (secondPlayer != null) {
            // Hay un segundo usuario logeado
            secondPlayerAvatar.setFill(new ImagePattern(secondPlayer.getAvatar()));
            secondPlayerUsername.setText(secondPlayer.getNickName());
            secondPlayerScore.setText("" + secondPlayer.getPoints());
        } else {
            // Es la CPU
            Player CPU = db.getPlayer("CPU");
            secondPlayerAvatar.setFill(new ImagePattern(CPU.getAvatar()));
            secondPlayerUsername.setText(CPU.getNickName());
            secondPlayerScore.setText(" -- ");
            secondPlayer = CPU;
            playingVSmachine = true;
        }
        
        // Seleccionar el orden de los jugadores
        whoIsFirst();
        
    } 

    @FXML
    private void setMousePosition(MouseEvent event) {
        currentX = null;
        currentY = null;
    }

    @FXML
    private void getMousePosition(MouseEvent event) {
        Node clickedNode = event.getPickResult().getIntersectedNode();
        if (clickedNode != gridPane) {
            // click en un nodo (Cirulo)
            currentX = GridPane.getColumnIndex(clickedNode);
            currentY = GridPane.getRowIndex(clickedNode);
        }
    }
    
    @FXML
    private void newGameButton(ActionEvent event) {
        for (int x = 0; x < COLUMNS; x++) {
            for (int y = 0; y < ROWS; y++) {
                //gridPane.getChildren().remove(0);
                Circle circle = getCircleByRowColumnIndex(y, x, gridPane);
                circle.setFill(Color.TRANSPARENT);
            }
        }
        circulosPos = new Matriz();
        
        
        // Actualizar el score
        updateScore();
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {
        // Desconectamos al segundo usuario
        secondPlayer = null;

        // Salimos del modo multijugador
        try {
            Parent mainMenuParent = FXMLLoader.load(getClass().getResource("/vista/MainScreen.fxml"));

            Scene mainMenuScene = new Scene(mainMenuParent);

            // Se obtiene la informacion de la ventana (Stage)
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Menu principal");
            window.setScene(mainMenuScene);
            //Ventana reajustable
            window.setResizable(false);
            window.show();

        } catch (Exception e) {
            System.out.println("No se pudo cargar la escena");
        }
    }
    
    @FXML
    private void onClickGridPane(MouseEvent event) {
        // Verificar turno
        if (playingVSmachine) {
            if (playerOne.getNickName().equals("CPU") && turnoPlayer) {
                // La maquina empieza
                Random rnd = new Random();
                currentX = rnd.nextInt(8);
            } else if (playerTwo.getNickName().equals("CPU") && !turnoPlayer) {
                // La maquina empieza
                Random rnd = new Random();
                currentX = rnd.nextInt(8);
            }
        }
        
        // Si hemos selecionado una casilla
        if (currentX != null) {
            // Comprobamos si la columna esta vacia o llena
            if (!circulosPos.isColumnFill(currentX)) {
                // Columna vacia, conseguimos la pos
                currentY = circulosPos.posNextInColumn(currentX);
                
                // Conseguimos el circulo de la pos y lo cambiamos de color
                Circle circle = getCircleByRowColumnIndex(currentY, currentX, gridPane);
                circle.setFill(turnoPlayer ? Color.BLUE:Color.RED);
                
                // Actualizamos la matriz y cambiamos de turno
                circulosPos.setPlayerOnPos(currentX, currentY, turnoPlayer ? playerOne:playerTwo);
                turnoPlayer = !turnoPlayer;
                
                // Cambiamos la etiqueta de aviso del jugador que le toca
                if (firstPlayerAviso.isVisible()) {
                    firstPlayerAviso.setVisible(false);
                    secondPlayerAviso.setVisible(true);
                } else {
                    firstPlayerAviso.setVisible(true);
                    secondPlayerAviso.setVisible(false);
                }

                // Actualizamos el contador de columnas llenas
                columnasLlenas[currentX] = circulosPos.isColumnFill(currentX);
            } else {
                System.out.println("Columna llena");
            }
            
            // Comprobamos si alguien ha ganado en este turno
            if (circulosPos.isWin(currentX, currentY) != null) {
                circulosPos.showContent();
                // Creacion de la partida con ganador y perdedor
                setGameWinner();
                
                // Aviso al usuario del ganador
                ButtonType buttonTypeSi = new ButtonType("Si");
                ButtonType buttonTypeNo = new ButtonType("No", ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);
                alert.setAlertType(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Ganador");
                alert.setHeaderText("El ganador de esta partida es: " + circulosPos.isWin(currentX, currentY).getNickName());
                alert.setContentText("¿Quieres volver a jugar?");
                
                // Opcion seleccionada por el usuario
                Optional<ButtonType> respuesta = alert.showAndWait();
                if (respuesta.get() == buttonTypeSi) {
                    // Vaciamos el tablero
                    newGameButton(null);
                    // Volvemos a selecionar quien empieza
                    whoIsFirst();
                    wait(100);
                } else {
                    cerrarSesion(null);
                }
            }
            if (playingVSmachine) {
                if (playerOne.getNickName().equals("CPU") && turnoPlayer) {
                    onClickGridPane(null);
                } else if (playerTwo.getNickName().equals("CPU") && !turnoPlayer) {
                    onClickGridPane(null);
                }
            }               
        }
    }
    
    @FXML
    private void startGameMachine(MouseEvent event) {
        onClickGridPane(event);
    }
    
    // Metodos auxiliares de la clase
    private void whoIsFirst() {
        Random rnd = new Random();
        int random = rnd.nextInt(2);
        if (random == 0) {
            playerOne = loginPlayer;
            playerTwo = secondPlayer;
            
            // Activamos la etiqueta del primer jugador
            firstPlayerAviso.setVisible(true);
            firstPlayerAvisoCircle.setFill(Color.BLUE);
            secondPlayerAvisoCircle.setFill(Color.RED);
        } else {
            playerOne = secondPlayer;
            playerTwo = loginPlayer;
            
            // Activamos la etiqueta del primer jugador
            secondPlayerAviso.setVisible(true);
            firstPlayerAvisoCircle.setFill(Color.RED);
            secondPlayerAvisoCircle.setFill(Color.BLUE);
        }
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

    private void setGameWinner() {
        try {
            if (playingVSmachine) {
                // Jugando contra la maquina
                if (circulosPos.isWin(currentX, currentY) == playerOne) {
                    db.regiterRound(LocalDateTime.now(), playerOne, playerTwo);
                    playerOne.plusPoints(db.getPointsAlone());
                } else {
                    db.regiterRound(LocalDateTime.now(), playerTwo, playerOne);
                    playerTwo.plusPoints(db.getPointsAlone());
                }
            } else {
                // Jugando contra otro jugador
                if (circulosPos.isWin(currentX, currentY) == playerOne) {
                    db.regiterRound(LocalDateTime.now(), playerOne, playerTwo);
                    playerOne.plusPoints(db.getPointsRound());
                } else {
                    db.regiterRound(LocalDateTime.now(), playerTwo, playerOne);
                    playerTwo.plusPoints(db.getPointsRound());
                }
            }
        } catch (Connect4DAOException e) {
            System.out.println(e);
        }
            
        
    }
    
    private static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private void updateScore() {
        firstPlayerScore.setText("" + loginPlayer.getPoints());
        secondPlayerScore.setText("" + secondPlayer.getPoints());
    }

}

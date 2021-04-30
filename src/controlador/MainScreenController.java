/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DBAccess.Connect4DAOException;
import static controlador.LogInAppController.loginPlayer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Connect4;
import static model.Connect4.getSingletonConnect4;
import model.Player;

/**
 * FXML Controller class
 *
 * @author CrazyJMB
 */
public class MainScreenController implements Initializable {

    // Instacias
    public static Player player;
    public static Player secondPlayer;
    
    private InGameScreenController controller;
    
    private Connect4 db;
    
    @FXML
    private Text username;
    @FXML
    private Text points;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button multiplayerPlayButton;
    @FXML
    private Circle avatarCircle;

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
        
        // Cargamos al usuario
        player = loginPlayer;
        
        // Cargamos la informacion del usuario en la ventana
        if (player != null) {
            username.setText(player.getNickName());
            points.setText("" + player.getPoints());
        } else {
            System.out.println("Error al cargar el usuario");
        }
        
        // Cargamos el avatar en el circulo
        avatarCircle.setFill(new ImagePattern(player.getAvatar()));
    }    

    @FXML
    private void editProfileButton(ActionEvent event) {
    }

    @FXML
    private void closeSesion(ActionEvent event) {
        // Ponemos loginPlayer a null - no es necesario
        player = null;
        // Salimos a la ventana de inicio
        try {
            Parent LogInParent = FXMLLoader.load(getClass().getResource("/vista/LogInApp.fxml"));
            Scene LogInScene = new Scene(LogInParent);

            // Se obtiene la informacion de la ventana (Stage)
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Inicio de sesion");
            window.setScene(LogInScene);
            //Ventana reajustable
            window.setResizable(false);
            window.show();

        } catch (Exception e) {
            System.out.println("No se pudo cargar la escena");
        }
        
    }

    @FXML
    private void playVsMachine(ActionEvent event) {
        try {
            Parent InGameParent = FXMLLoader.load(getClass().getResource("/vista/InGameScreen.fxml"));
            
            Scene InGameScene = new Scene(InGameParent);

            // Se obtiene la informacion de la ventana (Stage)
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Inicio de sesion");
            window.setScene(InGameScene);
            //Ajustar tamaño minimo
            window.setMinWidth(1280);
            window.setMinHeight(720);
            //Ventana reajustable
            window.setResizable(true);
            window.show();

        } catch (Exception e) {
            System.out.println("No se pudo cargar la escena");
        }
    }

    @FXML
    private void seeRanking(ActionEvent event) {
    }

    @FXML
    private void multiplayerPlay(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        
        // Comrpobamos que el usuario existe y no es el que esta logeado
        if (db.exitsNickName(usernameField.getText()) && !player.getNickName().equals(usernameField.getText())) {
            secondPlayer = db.loginPlayer(usernameField.getText(), passwordField.getText());
        } else {
            secondPlayer = null;
        }
        
        if (secondPlayer == null) {
            // Los datos son incorrectos
            if (player.getNickName().equals(usernameField.getText())) {
                alert.setTitle("Error");
                alert.setHeaderText("Usted ya ha iniciado sesion");
                alert.setContentText("Por favor, que inicie sesion el otro jugador");
                alert.showAndWait();
            }
            else if (db.exitsNickName(usernameField.getText()) && !player.getNickName().equals(usernameField.getText())) {
                alert.setTitle("Error en la contraseña");
                alert.setContentText("Por favor vuelva a introducir la contraseña correctamente");
                alert.showAndWait();
                passwordField.setText("");
            } else {
                // El usuario no existe en la base de datos
                alert.setTitle("Error inicio sesion");
                alert.setHeaderText("Se ha equivocado o la cuenta no existe");
                alert.setContentText("Por favor revise los datos introducidos.\nSi no tiene cuenta, puede crearse una cuenta nueva");
                alert.showAndWait();
            }
        } else {
            // Segundo jugador logeado correctamente
            try {
                Parent InGameParent = FXMLLoader.load(getClass().getResource("/vista/InGameScreen.fxml"));
                Scene InGameScene = new Scene(InGameParent);

                // Se obtiene la informacion de la ventana (Stage)
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setTitle("Multiplayer");
                window.setScene(InGameScene);
                //Ajustar tamaño minimo
                window.setMinWidth(1280);
                window.setMinHeight(720);
                //Ventana reajustable
                window.setResizable(true);
                window.show();

            } catch (Exception e) {
                System.out.println("No se pudo cargar la escena");
            }
        }
            
    }

    @FXML
    private void activatePlayButton(KeyEvent event) {
        //Manejar boton de logIn
        BooleanBinding usernameBinding = Bindings.createBooleanBinding(() -> {
            return usernameField.getText().isEmpty();
        }, usernameField.textProperty());
        
        BooleanBinding password = Bindings.createBooleanBinding(() -> {
            return passwordField.getText().isEmpty();
        }, passwordField.textProperty());
        
        multiplayerPlayButton.disableProperty().bind(usernameBinding.or(password));
        
        // Hacer logIn con la tecla enter
        if (multiplayerPlayButton.disableProperty().get() == false) {
            if (event.getCode().equals(KeyCode.ENTER)) {
                multiplayerPlay(new ActionEvent(multiplayerPlayButton, null));
            }
        }
    }
    
}

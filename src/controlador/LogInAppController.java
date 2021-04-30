/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DBAccess.Connect4DAOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Connect4;
import static model.Connect4.getSingletonConnect4;
import model.Player;

/**
 * FXML Controller class
 *
 * @author CrazyJMB
 */
public class LogInAppController implements Initializable {

    // Player comun en el juego
    public static Player loginPlayer;
    
    // Base de datos
    private Connect4 db;
    
    @FXML
    private TextField username;
    @FXML
    private ImageView facebookLogIn;
    @FXML
    private ImageView googleLogIn;
    @FXML
    private ImageView appleLogIn;
    @FXML
    private TextField password;
    @FXML
    private Button logInButton;
    @FXML
    private ImageView logo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        // Boton de logIn desactivado hasta que haya texto
        logInButton.setDisable(true);
        
        // Inicializamos la base de datos
        try {
            db = getSingletonConnect4();
        } catch (Connect4DAOException e) {
            System.out.println(e);
        }
        
        // Crear usuario CPU, si no existe
        userCPU();
        
        logo.setImage(new Image("/img/Logo.png", false));
    }    

    @FXML
    private void LogInAction(ActionEvent event) {
        // Inicializacion de alerta
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        
        // Comprobamos que el usuario existe en la base de datos
        if (!"".equals(username.getText()) || !"".equals(password.getText())) {
            // Si han introducido datos
            loginPlayer = LogInCheckInfo();
            if (loginPlayer == null) {
                // Los datos son incorrectos
                if (db.exitsNickName(username.getText())) {
                    alert.setTitle("Error en la contraseña");
                    alert.setContentText("Por favor vuelva a introducir la contraseña correctamente");
                    alert.showAndWait();
                    password.setText("");
                } else {
                    // El usuario no existe en la base de datos
                    alert.setTitle("Error inicio sesion");
                    alert.setHeaderText("Se ha equivocado o la cuenta no existe");
                    alert.setContentText("Por favor revise los datos introducidos.\nSi no tiene cuenta, puede crearse una cuenta nueva");
                    alert.showAndWait();
                }
            } else {
                // Acceso a la scena de usuario logeado
                try {
                    Parent InGameParent = FXMLLoader.load(getClass().getResource("/vista/MainScreen.fxml"));
                    Scene InGameScene = new Scene(InGameParent);
                    
                    // Se obtiene la informacion de la ventana (Stage)
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setTitle("Lobby");
                    window.setScene(InGameScene);
                   
                    //Ventana reajustable
                    window.setResizable(false);
                    window.show();

                } catch (Exception e) {
                    System.out.println("No se pudo cargar la escena");
                }
            }
            
        }
    }

    @FXML
    private void rememberPassword(MouseEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/vista/RememberPassword.fxml"));
          
            
            stage.setScene(new Scene(root));
            stage.setTitle("Recuperar datos");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
 
        } catch (Exception e) {
            System.out.println("No se pudo cargar la escena");
        }
    }

    @FXML
    private void createNewAccount(MouseEvent event) {
        try {
            Parent newUserParent = FXMLLoader.load(getClass().getResource("/vista/NewUserScreen.fxml"));
            Scene newUserScene = new Scene(newUserParent);
            
            // Se obtiene la informacion de la ventana (Stage)
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Inicio de sesion");
            window.setScene(newUserScene);
            window.setResizable(false);
            window.show();
            
        } catch (Exception e) {
            System.out.println("No se pudo cargar la escena");
        }
    }
    
    private Player LogInCheckInfo() {
        
        // Comprobamos si los datos del usuario existen
        if (db.exitsNickName(username.getText())) {
            // Si existe el usuario cargamos al loginPlayer
            return db.loginPlayer(username.getText(), password.getText());
        }
        return null;
    }

    @FXML
    private void activateLogInButton(KeyEvent event) {
        //Manejar boton de logIn
        BooleanBinding usernameField = Bindings.createBooleanBinding(() -> {
            return username.getText().isEmpty();
        }, username.textProperty());
        
        BooleanBinding passwordField = Bindings.createBooleanBinding(() -> {
            return password.getText().isEmpty();
        }, password.textProperty());
        
        logInButton.disableProperty().bind(usernameField.or(passwordField));
        
        // Hacer logIn con la tecla enter
        if (logInButton.disableProperty().get() == false) {
            if (event.getCode().equals(KeyCode.ENTER)) {
                LogInAction(new ActionEvent(logInButton, null));
            }
        }
    }

    private void userCPU() {
        if (!db.exitsNickName("CPU")) {
            try {
                db.registerPlayer("CPU", "", "admin", new Image("img/avatars/default.png", false), LocalDate.now(), 0);
            } catch (Connect4DAOException e) {
                System.out.println(e);
            }
        }
    }
    
}

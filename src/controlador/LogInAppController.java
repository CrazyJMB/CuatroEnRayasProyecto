/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author CrazyJMB
 */
public class LogInAppController implements Initializable {

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void LogInAction(ActionEvent event) {
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
    private void rememberPassword(MouseEvent event) {
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
    
}

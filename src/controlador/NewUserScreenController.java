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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author CrazyJMB
 */
public class NewUserScreenController implements Initializable {

    @FXML
    private Circle avatarViewCircle;
    @FXML
    private TextField username;
    @FXML
    private TextField email;
    @FXML
    private TextField password;
    @FXML
    private TextField passwordRe;
    @FXML
    private DatePicker dataPicker;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void changeAvatar(ActionEvent event) {
    }

    @FXML
    private void createNewUser(ActionEvent event) {
    }

    @FXML
    private void cancelButton(ActionEvent event) {
        try {
            Parent LogInAppParent = FXMLLoader.load(getClass().getResource("/vista/LogInApp.fxml"));
            Scene LogInAppScene = new Scene(LogInAppParent);
            
            // Se obtiene la informacion de la ventana (Stage)
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Creación de usuario");
            window.setScene(LogInAppScene);
            window.setResizable(false);
            window.show();
            
        } catch (Exception e) {
            System.out.println("No se pudo cargar la escena");
        }
    }
    
}

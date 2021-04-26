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
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author supee
 */
public class ChangeAvatarController extends NewUserScreenController implements Initializable {

    private Image avatar1Image = new Image("/img/avatar1.png", false);
    private Image avatar2Image = new Image("/img/avatar2.png", false);
    private Image avatar3Image = new Image("/img/avatar3.png", false);
    private Image avatar4Image = new Image("/img/avatar4.png", false);
    private Image avatarDefaultImage = new Image("/img/default.png", false);
    
    @FXML
    private RadioButton Avatar1;
    @FXML
    private Circle avatar1Circle;
    @FXML
    private RadioButton Avatar2;
    @FXML
    private Circle avatar2Circle;
    @FXML
    private RadioButton Avatar3;
    @FXML
    private Circle avatar3Circle;
    @FXML
    private RadioButton Avatar4;
    @FXML
    private Circle avatar4Circle;
    @FXML
    private RadioButton DefaultAvatar;
    @FXML
    private Circle avatarDefaultCircle;
    @FXML
    private Button ConfirmarButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        avatar1Circle.setFill(new ImagePattern(avatar1Image));
        avatar2Circle.setFill(new ImagePattern(avatar2Image));
        avatar3Circle.setFill(new ImagePattern(avatar3Image));
        avatar4Circle.setFill(new ImagePattern(avatar4Image));
        avatarDefaultCircle.setFill(new ImagePattern(avatarDefaultImage));
    }    

    @FXML
    private void Confirmar2Button(ActionEvent event) {
        
        if (Avatar1.isSelected()){avatarImage = avatar1Image;} 
        if (Avatar2.isSelected()){avatarImage = avatar2Image;}
        if (Avatar3.isSelected()){avatarImage = avatar3Image;}
        if (Avatar4.isSelected()){avatarImage = avatar4Image;}
        if (DefaultAvatar.isSelected()){avatarImage = avatarDefaultImage;}
        volverCreacionDeUsuario(event);    
        
    }

    @FXML
    private void CancelarButton(ActionEvent event) {
        volverCreacionDeUsuario(event);
    }
    private void volverCreacionDeUsuario(ActionEvent event) {
        try {
            Parent LogInAppParent = FXMLLoader.load(getClass().getResource("/vista/NewUserScreen.fxml"));
            Scene newUserScene = new Scene(LogInAppParent);
            
            // Se obtiene la informacion de la ventana (Stage)
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Crear Usuario Nuevo");
            window.setScene(newUserScene);
            window.setResizable(false);
            window.show();
            
        } catch (Exception e) {
            System.out.println("No se pudo cargar la escena");
        }
    }
}

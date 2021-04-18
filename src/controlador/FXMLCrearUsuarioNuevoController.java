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
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author CrazyJMB
 */
public class FXMLCrearUsuarioNuevoController implements Initializable {

    @FXML
    private Circle AvatarCircle;
    @FXML
    private DatePicker BirthDate;
    @FXML
    private TextField UserName;
    @FXML
    private TextField Email;
    @FXML
    private TextField Password;
    @FXML
    private TextField PasswordRe;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void CreateUserButton(MouseEvent event) {
    }

    @FXML
    private void CancelButton(MouseEvent event) {
        try {
            Parent inicioSesionParent = FXMLLoader.load(getClass().getResource("/vista/FXMLCuatroEnRaya.fxml"));
            Scene inicioSesionScene = new Scene(inicioSesionParent);
            
            // Se obtiene la informacion de la ventana (Stage)
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Inicio de sesion");
            window.setScene(inicioSesionScene);
            window.setResizable(false);
            window.show();
            
        } catch (Exception e) {
            System.out.println("No se pudo cargar la escena");
        }
    }

    @FXML
    private void ChangeAvatarButton(MouseEvent event) {
    }

    
}

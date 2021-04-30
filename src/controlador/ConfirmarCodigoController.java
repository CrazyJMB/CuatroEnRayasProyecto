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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author supee
 */
public class ConfirmarCodigoController extends GeneradorCodigoController implements Initializable {

    @FXML
    private Button confirmarButton;
    @FXML
    private TextField codigoButton;
    @FXML
    private Label passwordExit;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       
        }
        

    @FXML
    private void ConfirmarCodigo(ActionEvent event) {
        //Cogemos la variable codigo del GeneradorCodigoCOntroller
        if (codigo == Integer.parseInt(codigoButton.getText())){
            
            String pass = player.getPassword();
            passwordExit.setText("La contraseña es: " + pass);
            volverInicio(event);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Error");
            alert.setContentText("Vuelva a introducir el codigo");
            alert.showAndWait();
        }
    }
        
    private void volverInicio(ActionEvent event) {
        try {
            Parent LogInAppParent = FXMLLoader.load(getClass().getResource("/vista/LogInApp.fxml"));
            Scene LogInAppScene = new Scene(LogInAppParent);
            
            // Se obtiene la informacion de la ventana (Stage)
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Inicio de sesion");
            window.setScene(LogInAppScene);
            window.setResizable(false);
            window.show();
            
        } catch (Exception e) {
            System.out.println("No se pudo cargar la escena");
        }
    }


}

   


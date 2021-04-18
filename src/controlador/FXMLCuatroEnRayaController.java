/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author CrazyJMB
 */
public class FXMLCuatroEnRayaController implements Initializable {

    @FXML
    private TextField UserName;
    @FXML
    private TextField UserPassword;
    @FXML
    private Label CrearNuevaCuentaButton;
    @FXML
    private Label VerRankingButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void LogInAccount(MouseEvent event) {
    }

    @FXML
    private void CreateNewAccount(MouseEvent event) {
        try {
            Parent crearUsuarioParent = FXMLLoader.load(getClass().getResource("/vista/FXMLCrearUsuarioNuevo.fxml"));
            Scene crearUsuarioScene = new Scene(crearUsuarioParent);
            
            // Se obtiene la informacion de la ventana (Stage)
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Creación de usuario");
            window.setScene(crearUsuarioScene);
            window.setResizable(false);
            window.show();
            
        } catch (Exception e) {
            System.out.println("No se pudo cargar la escena");
        }
        
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author supee
 */
public class GeneradorCodigoController extends rememberPass implements Initializable {

    @FXML
    private TextField GeneradorCodigo;

    /**
     * Initializes the controller class.
     */
    
    Random rand = new Random();
    public int codigo = rand.nextInt(999999);
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        GeneradorCodigo.setText("" + codigo);
        
    }    

    private void continuarAconfimar(MouseEvent event) {
       try {
            Parent LogInAppParent = FXMLLoader.load(getClass().getResource("/vista/ConfirmarCodigo.fxml"));
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

    @FXML
    private void continuarAconfimar(ActionEvent event) {
        try {
            Parent LogInAppParent = FXMLLoader.load(getClass().getResource("/vista/ConfirmarCodigo.fxml"));
            Scene LogInAppScene = new Scene(LogInAppParent);
            
            // Se obtiene la informacion de la ventana (Stage)
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Inicio de sesion");
            window.setScene(LogInAppScene);
            window.setResizable(false);
            window.show();
            
        } catch (Exception e) {
            System.out.println("No se pudo cargar la escena");
            System.out.println(e.getMessage());
        }
    }
}
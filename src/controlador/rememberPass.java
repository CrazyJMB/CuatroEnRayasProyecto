/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import DBAccess.Connect4DAOException;
import java.net.URL;
import java.util.Random;
import java.util.*;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.swing.Action;
import javax.swing.JOptionPane;
import model.Connect4;
import static model.Connect4.getSingletonConnect4;
import model.Player;


/**
 * FXML Controller class
 *
 * @author supee
 */
public class rememberPass extends LogInAppController implements Initializable {

    private Connect4 db;
    public Player player;
    
    @FXML
    private TextField correoText;
    @FXML
    private TextField usernameRE;
    @FXML
    private Button confirmarEmailButton;

    /**
     * Initializes the controller class.
     */
    
    private boolean LogInCheck() {
        return player.checkEmail(correoText.getText());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Inicializamos la base de datos
        try {
            db = getSingletonConnect4();
        } catch (Connect4DAOException e) {
            System.out.println(e);
        }
        
    }    

    @FXML
    private void confirmarEmail(ActionEvent event) {
        try{
        if (LogInCheck()){
       
            Parent LogInAppParent = FXMLLoader.load(getClass().getResource("/vista/GeneradorCodigo.fxml"));
            Scene LogInAppScene = new Scene(LogInAppParent);
            
            // Se obtiene la informacion de la ventana (Stage)
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(LogInAppScene);
            window.setResizable(false);
            window.show();
        } else {
           Alert alert = new Alert(Alert.AlertType.WARNING);
           alert.setHeaderText("Error");
           alert.setContentText("Vuelva a introducir los datos");
           alert.showAndWait();
        }
        }  
        catch (Exception e) {
            System.out.println("No se pudo cargar la escena");
            System.out.println(e.getMessage());
        }
    
        
    }    
}
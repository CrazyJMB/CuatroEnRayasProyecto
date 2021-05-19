/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import DBAccess.Connect4DAOException;
import java.net.URL;
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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import model.Connect4;
import static model.Connect4.getSingletonConnect4;
import model.Player;


/**
 * FXML Controller class
 *
 * @author supee
 */
public class RememberPassword implements Initializable {

    private Connect4 db;
    
    public static Player currentPlayerOnRemeberPassword;
    
    public static String codigo;
    
    @FXML
    private TextField correoText;
    @FXML
    private TextField usernameRE;
    @FXML
    private Button confirmarEmailButton;

    /**
     * Initializes the controller class.
     */
     
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
                // El usuario introduzco bien los datos
                Random rand = new Random();
                codigo = "" + rand.nextInt(999999);
                ButtonType buttonTypeCopy = new ButtonType("Copiar");
                ButtonType buttonTypeSiguiente = new ButtonType("Siguiente", ButtonBar.ButtonData.YES);
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Código de recuperacion");
                alert.setContentText("El código para recuperar la contraseña es: \n\t " + codigo);
                alert.getButtonTypes().setAll(buttonTypeCopy, buttonTypeSiguiente);
                
                Optional<ButtonType> respuesta = alert.showAndWait();
                
                if (respuesta.get() == buttonTypeCopy) {
                    Clipboard clipboard = Clipboard.getSystemClipboard();
                    ClipboardContent content = new ClipboardContent();
                    content.putString(codigo);
                    clipboard.setContent(content);
                    
                    // Conseguido el código nos vamos a la ventana para introducirlo
                    Parent LogInAppParent = FXMLLoader.load(getClass().getResource("/vista/ConfirmarCodigo.fxml"));
                    Scene LogInAppScene = new Scene(LogInAppParent);

                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setTitle("Recuperacion de contraseña");
                    window.setScene(LogInAppScene);
                    window.setResizable(false);
                    window.show();
                    
                } else if (respuesta.get() == buttonTypeSiguiente) {
                    // Conseguido el código nos vamos a la ventana para introducirlo
                    Parent LogInAppParent = FXMLLoader.load(getClass().getResource("/vista/ConfirmarCodigo.fxml"));
                    Scene LogInAppScene = new Scene(LogInAppParent);

                    LogInAppScene.getStylesheets().add("/visualizacion/ConfirmarCodigoStyleSheet.css");
                    
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setResizable(false);
                    window.setTitle("Recuperacion de contraseña");
                    window.setScene(LogInAppScene);
                    window.show();
                }
                
            } else {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Error");
               alert.setHeaderText("Datos erroneos");
               alert.setContentText("Vuelva a introducir los datos");
               alert.showAndWait();
            }
        }  
        catch (Exception e) {
            System.out.println("No se pudo cargar la escena");
        }
    }   
    
    
    private boolean LogInCheck() {
        // Comprobamos si existe el usuario en la base de datos
        if (db.exitsNickName(usernameRE.getText())) {
            currentPlayerOnRemeberPassword = db.getPlayer(usernameRE.getText());
            if (currentPlayerOnRemeberPassword.getEmail().equals(correoText.getText())) {
                return true;
            }
        }
        return false;
    }
}


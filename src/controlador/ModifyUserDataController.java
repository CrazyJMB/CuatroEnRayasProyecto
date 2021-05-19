/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DBAccess.Connect4DAOException;
import static controlador.ChangeAvatarController.selectedImage;
import static controlador.MainScreenController.player;
import static controlador.NewUserScreenController.avatarImage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Player;

/**
 * FXML Controller class
 *
 * @author CrazyJMB
 */
public class ModifyUserDataController implements Initializable {

    @FXML
    private Circle avatarViewCircle;
    @FXML
    private TextField username;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField passwordRe;
    @FXML
    private DatePicker dataPicker;
    @FXML
    private Button updateDataButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Inicializamos las casillas con los valores del usuario
        if (player != null) {
            avatarViewCircle.setFill(new ImagePattern(player.getAvatar()));
            username.setText(player.getNickName());
            email.setText(player.getEmail());
            dataPicker.setValue(player.getBirthdate());
            System.out.println(player.getBirthdate());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al acceder al usuario");
            alert.setContentText("Por favor cierre la ventana y vuelva a intentarlo.");
        }
    }

    @FXML
    private void changeAvatar(ActionEvent event) {
        try {    
            Stage StageAux = new Stage();
            Parent changeAvatarParent = FXMLLoader.load(getClass().getResource("/vista/ChangeAvatar.fxml"));
            Scene changeAvatarScene = new Scene(changeAvatarParent);
            
            changeAvatarScene.getStylesheets().add("/visualizacion/ChangeAvatarStyleSheet.css");
            
            StageAux.setScene(changeAvatarScene);
            StageAux.setTitle("Seleccion de avatar");
            StageAux.initModality(Modality.APPLICATION_MODAL);
            StageAux.showAndWait();
            
            // Actualizamos el avatar
            if (selectedImage != null) {
                avatarImage = selectedImage;
            } else {
                System.out.println("Avatar no seleccionado");
            }
            avatarViewCircle.setFill(new ImagePattern(avatarImage));
            
        } catch (IOException e) {
            System.out.println("Error al intentar cambiar de avatar");
        }
    }
    
    @FXML
    private void updateUserData(ActionEvent event) {
        // Comprobacion de datos
        Boolean validEmail = Player.checkEmail(email.getText());
        Boolean validPassword = true;
        Boolean validPasswordRe = true;
        Boolean validaDataPicker = checkAge(dataPicker.getValue());
        if (!password.getText().isEmpty()) {
            validPassword = Player.checkPassword(password.getText());
            validPasswordRe = password.getText().equals(passwordRe.getText());
        }
        
        // Creación de la alerta
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        if (!validEmail) {
            alert.setHeaderText("Cuenta de correo no valida");
            alert.setContentText("El correo introducido no tiene un formato válido\nFormato: example@dominio.com");
            alert.showAndWait();
        } else if (!validPassword) {
            alert.setHeaderText("Contraseña no válida");
            alert.setContentText("Contraseña valida si:\n\t- contiene entre 8 y 20 caracteres\n\t- al menus una letra mayúscula\n\t- al menus una letra minúscula\n\t- al menos un digito\n\t- contiene un carácter especial: !#$%&*()-+=\n\t- no contiene ningun espacio");
            alert.showAndWait();
        } else if (!validPasswordRe) {
            alert.setHeaderText("No coincide la contraseña");
            alert.setContentText("No coinciden las contraseñas, por favor introduzcalas de nuevo");
            alert.showAndWait();
        } else if (!validaDataPicker) {
            alert.setHeaderText("Edad minima no conseguida");
            alert.setContentText("La edad minima para poder jugar es de 12 años");
            alert.showAndWait();
        }
        
        try {
            // Actualizar datos de jugador
            if (validEmail && !email.getText().equals(player.getEmail())) {
                player.setEmail(email.getText());
            }
            if (validPassword && validPasswordRe && !password.getText().isEmpty() && !password.getText().equals(player.getPassword())) {
                player.setPassword(password.getText());
            }
            if (validaDataPicker && !dataPicker.getValue().equals(player.getBirthdate())) {
                player.setBirthdate(dataPicker.getValue());
            }
            
            // Actualizamos el avatar
            player.setAvatar(selectedImage);
            
        } catch (Connect4DAOException ex) {
            System.out.println(ex);
        }
        
        if (validEmail && validPassword && validPasswordRe && validaDataPicker) {
            cancelButton(event);
        }
        
    }

    @FXML
    private void cancelButton(ActionEvent event) {
        try {
            Parent MainScreenParent = FXMLLoader.load(getClass().getResource("/vista/MainScreen.fxml"));
            Scene MainScreenScene = new Scene(MainScreenParent);
            
            // Se obtiene la informacion de la ventana (Stage)
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Lobby");
            window.setScene(MainScreenScene);
            window.setResizable(false);
            window.show();
            
        } catch (Exception e) {
            System.out.println("No se pudo cargar la escena");
        }
    }

     private Boolean checkAge(LocalDate fechaDada) {
        //Fecha actual 
        LocalDate fechaActual = LocalDate.now();
        
        // Comprobamos la edad
        if ((fechaActual.getYear() - fechaDada.getYear()) < 12) {
            // Persona menor de 12 años
            return false;
        }
        if ((fechaActual.getYear() - fechaDada.getYear()) == 12) {
            if (fechaActual.getMonthValue() == fechaDada.getMonthValue()) {
                if (fechaActual.getDayOfMonth() > fechaDada.getDayOfMonth()) {
                    // La persona ha cumplido los 12 años
                    return true;
                }
            }
            return false;
        }
        return true;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DBAccess.Connect4DAOException;
import static controlador.ChangeAvatarController.selectedImage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
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
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Connect4;
import static model.Connect4.getSingletonConnect4;
import model.Player;

/**
 * FXML Controller class
 *
 * @author CrazyJMB
 */
public class NewUserScreenController implements Initializable {
    
    NewUserScreenController newUserScreenControler;
    
    private Connect4 db = null;
    
    public static Image avatarImage = new Image("/img/avatars/avatar1.png", false);
            
    @FXML
    private Circle avatarViewCircle;
    @FXML
    private TextField username;
    @FXML
    private TextField email;
    @FXML
    private DatePicker dataPicker;
    @FXML
    private Button createNewUserButton;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField passwordRe;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        createNewUserButton.setDisable(true);
        
        // Inicializamos el dataPicker
        dataPicker.setValue(LocalDate.now());
        
        // Inicializamos la base de datos
        try {
            db = getSingletonConnect4();
        } catch (Connect4DAOException e) {
            System.out.println(e.toString());
        }
        
        // Imagen por defecto en el avatar
        avatarViewCircle.setFill(new ImagePattern(avatarImage));
    }    

    @FXML
    private void changeAvatar(ActionEvent event) {
        System.out.println(avatarImage);
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
    private void createNewUser(ActionEvent event) {
        //Creacion de la alerta
        Alert alert = new Alert(Alert.AlertType.WARNING);
        
        // Fase de comprobacion de datos
        Boolean validUser = Player.checkNickName(username.getText());
        Boolean validEmail = Player.checkEmail(email.getText());
        Boolean validPassword = Player.checkPassword(password.getText());
        Boolean validPasswordRe = password.getText().equals(passwordRe.getText());
        Boolean validaDataPicker = checkAge(dataPicker.getValue());
        
        if (db.exitsNickName(username.getText())) {
            alert.setHeaderText("Nombre de usuario en uso");
            alert.setContentText("Introduzca un nombre de usuario distinto");
            alert.showAndWait();
        } else {
            alert.setTitle("Error");
            if (!validUser) {
                alert.setHeaderText("Nombre de usuario no valido");
                alert.setContentText("El nombre de usuario tiene un formato no valido.\nEs válido si tiene entre 6 y 15 caracteres, mayúsculas, minúsculas o los guiones \'-\' y \'_\'");
                alert.showAndWait();
            } else if (!validEmail) {
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
        }
        
        // Creacion del usuario en la base de datos
        if (validUser && validEmail && validPassword && validPasswordRe && validaDataPicker) {
            try {
                db.registerPlayer(username.getText(), email.getText(), password.getText(), selectedImage, dataPicker.getValue(), 0);
            } catch (Connect4DAOException e) {
                System.out.println(e);
            }
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Usuario creado correctamente");
            alert.setContentText("Le transladamos a la ventana de inicio de sesion");
            alert.showAndWait();
            
            // Vuelta a la pagina de log in
            volverInicioDeUsuario(event);
        }
    }

    @FXML
    private void cancelButton(ActionEvent event) {
        volverInicioDeUsuario(event);
    }

    @FXML
    private void createNewUserButtonHandler(KeyEvent event) {
        //Manejar boton de createNewUser
        BooleanBinding usernameField = Bindings.createBooleanBinding(() -> {
            return username.getText().isEmpty();
        }, username.textProperty());
        
        BooleanBinding emailField = Bindings.createBooleanBinding(() -> {
            return email.getText().isEmpty();
        }, email.textProperty());
        
        BooleanBinding passwordField = Bindings.createBooleanBinding(() -> {
            return password.getText().isEmpty();
        }, password.textProperty());
        
        BooleanBinding passwordReField = Bindings.createBooleanBinding(() -> {
            return passwordRe.getText().isEmpty();
        }, passwordRe.textProperty());
        
        createNewUserButton.disableProperty().bind(usernameField.or(emailField).or(passwordField).or(passwordReField));
        
        // Hacer logIn con la tecla enter
        if (createNewUserButton.disableProperty().get() == false) {
            if (event.getCode().equals(KeyCode.ENTER)) {
                createNewUser(new ActionEvent(createNewUserButton, null));
            }
        }
    }
    
    private void volverInicioDeUsuario(ActionEvent event) {
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
    
    public void setAvatar(Image img) {
        avatarImage = img;
    }
}

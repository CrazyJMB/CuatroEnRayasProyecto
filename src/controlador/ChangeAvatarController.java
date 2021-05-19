/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import static javafx.beans.binding.Bindings.not;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author supee
 */
public class ChangeAvatarController extends NewUserScreenController implements Initializable {

    private final Image imageOne = new Image("/img/avatars/default.png", false);
    private final Image imageTwo = new Image("/img/avatars/avatar1.png", false);
    private final Image imageTree = new Image("/img/avatars/avatar2.png", false);
    private final Image imageFour = new Image("/img/avatars/avatar3.png", false);
    private final Image imageFive = new Image("/img/avatars/avatar4.png", false);
    
    public static Image selectedImage = null;
    
    @FXML
    private Circle avatarOne;
    @FXML
    private Circle avatarTwo;
    @FXML
    private Circle avatarThree;
    @FXML
    private Circle avatarFour;
    @FXML
    private Circle avatarFive;
    @FXML
    private ToggleGroup selectedAvatar;
    @FXML
    private RadioButton radioButtonOne;
    @FXML
    private RadioButton radioButtonTwo;
    @FXML
    private RadioButton radioButtonThree;
    @FXML
    private RadioButton radioButtonFour;
    @FXML
    private RadioButton radioButtonFive;
    @FXML
    private Button confirmar;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        confirmar.setDisable(true);
        
        // Cargar las imagenes en los circulos
        avatarOne.setFill(new ImagePattern(imageOne));
        avatarTwo.setFill(new ImagePattern(imageTwo));
        avatarThree.setFill(new ImagePattern(imageTree));
        avatarFour.setFill(new ImagePattern(imageFour));
        avatarFive.setFill(new ImagePattern(imageFive));
    }    

    @FXML
    private void searchIMG(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Buscar avatar");
        
        // Filtro para facilitar la busqueda
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Todos los archivos", "*.*"),
                new FileChooser.ExtensionFilter("JPG", ".jpg"),
                new FileChooser.ExtensionFilter("PNG", ".png")
        );
        
        // Obtener la imagen
        File imgFile = fileChooser.showOpenDialog(null);
        
        // Mostrar la imagen
        if (imgFile != null) {
            selectedImage = new Image("file:" + imgFile.getAbsolutePath(), false);
            
            // Cerramos la ventana
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();

            stage.close();
        }
    }

    @FXML
    private void confirmarButton(ActionEvent event) {
        if (radioButtonOne.isSelected()){
            selectedImage = imageOne;
        } 
        if (radioButtonTwo.isSelected()){
            selectedImage = imageTwo;
        } 
        if (radioButtonThree.isSelected()){
            selectedImage = imageTree;
        } 
        if (radioButtonFour.isSelected()){
            selectedImage = imageFour;
        } 
        if (radioButtonFive.isSelected()){
            selectedImage = imageFive;
        }
        
        // Cerramos la ventana
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        
        stage.close();
    }

    @FXML
    private void cancelarButton(ActionEvent event) {
        // No seleccionamos avatar
        selectedAvatar = null;
        
        // Cerramos la ventana
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        
        stage.close();
    }

    @FXML
    private void confirmarButtonHandler(MouseEvent event) {
         //Manejar boton de createNewUser
        BooleanBinding firstAvatar = Bindings.createBooleanBinding(() -> {
            return !radioButtonOne.isFocused();
        }, radioButtonOne.focusedProperty());
        
        BooleanBinding secondAvatar = Bindings.createBooleanBinding(() -> {
            return !radioButtonTwo.isFocused();
        }, radioButtonTwo.focusedProperty());
        
        BooleanBinding ThirdAvatar = Bindings.createBooleanBinding(() -> {
            return !radioButtonThree.isFocused();
        }, radioButtonThree.focusedProperty());
        
        BooleanBinding FourAvatar = Bindings.createBooleanBinding(() -> {
            return !radioButtonFour.isFocused();
        }, radioButtonFour.focusedProperty());
        
        BooleanBinding FiveAvatar = Bindings.createBooleanBinding(() -> {
            return !radioButtonFive.isFocused();
        }, radioButtonFive.focusedProperty());
        
        confirmar.disableProperty().bind(not(firstAvatar.or(secondAvatar).or(ThirdAvatar).or(FourAvatar).or(FiveAvatar)));
    }
}

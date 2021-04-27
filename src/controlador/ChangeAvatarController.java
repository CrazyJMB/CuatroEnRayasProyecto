/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.File;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
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

    private Image ImageOne = new Image("/img/avatars/default.png", false);
    private Image ImageTwo = new Image("/img/avatars/avatar1.png", false);
    private Image ImageTree = new Image("/img/avatars/avatar2.png", false);
    private Image ImageFour = new Image("/img/avatars/avatar3.png", false);
    private Image ImageFive = new Image("/img/avatars/avatar4.png", false);
    
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
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        // Cargar las imagenes en los circulos
        avatarOne.setFill(new ImagePattern(ImageOne));
        avatarTwo.setFill(new ImagePattern(ImageTwo));
        avatarThree.setFill(new ImagePattern(ImageTree));
        avatarFour.setFill(new ImagePattern(ImageFour));
        avatarFive.setFill(new ImagePattern(ImageFive));
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
            Image image = new Image("file:" + imgFile.getAbsolutePath());
            avatarImage = image;
        }
        
    }

    @FXML
    private void confirmarButton(ActionEvent event) {
        
        
        if (radioButtonOne.isSelected()){
            setAvatar(ImageOne);
        } 
        if (radioButtonTwo.isSelected()){
            avatarImage = ImageTwo;
        } 
        if (radioButtonThree.isSelected()){
            avatarImage = ImageTree;
        } 
        if (radioButtonFour.isSelected()){
            avatarImage = ImageFour;
        } 
        if (radioButtonFour.isSelected()){
            avatarImage = ImageFive;
        }
    }

    @FXML
    private void cancelarButton(ActionEvent event) {
        
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        
        stage.close();
    }
}

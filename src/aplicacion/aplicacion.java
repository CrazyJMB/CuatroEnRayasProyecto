/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author CrazyJMB
 */
public class aplicacion extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/InGameScreen.fxml"));
        
        Scene scene = new Scene(root);
        
        //Aplicar hoja de estilo a la escena
        scene.getStylesheets().add("/CSS/LogInAppStyleSheet.css");
        
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setTitle("Inicio de sesion");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

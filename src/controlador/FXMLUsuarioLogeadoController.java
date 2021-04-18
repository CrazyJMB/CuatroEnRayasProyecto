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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author CrazyJMB
 */
public class FXMLUsuarioLogeadoController implements Initializable {

    @FXML
    private Text nombreUsuario;
    @FXML
    private Text partidasJugadas;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void jugarContraLaMaquina(ActionEvent event) {
    }

    @FXML
    private void jugarContraOtroJugador(ActionEvent event) {
    }

    @FXML
    private void VerRanking(ActionEvent event) {
    }
    
}

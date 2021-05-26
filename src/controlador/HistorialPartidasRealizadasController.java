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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;

/**
 * FXML Controller class
 *
 * @author CrazyJMB
 */
public class HistorialPartidasRealizadasController implements Initializable {

    @FXML
    private DatePicker fechaFin;
    @FXML
    private DatePicker fechaInicio;
    @FXML
    private TableColumn<?, ?> fechaColumn;
    @FXML
    private TableColumn<?, ?> winnerColumn;
    @FXML
    private TableColumn<?, ?> loserColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void goStatsView(ActionEvent event) {
    }

    @FXML
    private void goMainScreen(ActionEvent event) {
    }

    
}

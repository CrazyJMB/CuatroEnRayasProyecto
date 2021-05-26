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
import javafx.scene.chart.BarChart;
import javafx.scene.control.DatePicker;

/**
 * FXML Controller class
 *
 * @author CrazyJMB
 */
public class HistorialPartidasRealizadasStatsController implements Initializable {

    @FXML
    private DatePicker fechaIni;
    @FXML
    private DatePicker fechaFin;
    @FXML
    private BarChart<?, ?> barChart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void volverHistorial(ActionEvent event) {
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import model.Round;

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
    private BarChart<LocalDate, Round> barChart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
        
        // Formato DatePicker
        fechaIni.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate fecha = fechaFin.getValue();
                setDisable(empty || date.compareTo(fecha) > 0 );
            }
        });
        
        fechaFin.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate fecha = fechaIni.getValue();
                setDisable(empty || date.compareTo(fecha) < 0 );
            }
        });
    }    

    @FXML
    private void volverHistorial(ActionEvent event) {
    }
    
}

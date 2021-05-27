/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DBAccess.Connect4DAOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.NavigableMap;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Connect4;
import model.Round;

/**
 * FXML Controller class
 *
 * @author CrazyJMB
 */
public class HistorialPartidasRealizadasController implements Initializable {

    // Base de datos
    private Connect4 db = null;
    
    private ObservableList<Round> observableRound = FXCollections.observableArrayList();
    
    @FXML
    private DatePicker fechaFin;
    @FXML
    private DatePicker fechaInicio;
    @FXML
    private TableColumn<Round, String> fechaColumn;
    @FXML
    private TableColumn<Round, String> winnerColumn;
    @FXML
    private TableColumn<Round, String> loserColumn;
    @FXML
    private TextField nickPlayer;
    @FXML
    private ToggleButton ganadas;
    @FXML
    private ToggleButton perdidas;
    @FXML
    private TableView<Round> tablaHistorial;
    @FXML
    private Button estadisticas;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Cargamos la base de datos
        try {
            db = Connect4.getSingletonConnect4();
        } catch (Connect4DAOException ex) {
            System.out.println(ex);
        }
        
        // Enable buttons
        BooleanBinding nickName = Bindings.createBooleanBinding(() -> {
            return nickPlayer.getText().isEmpty();
        }, nickPlayer.textProperty());
        
        ganadas.disableProperty().bind(nickName);
        perdidas.disableProperty().bind(nickName);
        
        // Formato columnas (Tabla)
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("timeStamp"));
        winnerColumn.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getWinner().getNickName()));
        loserColumn.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getLoser().getNickName()));
        
        // Inicializamos las fechas (Fechas por defecto: Ayer - hoy)
        fechaInicio.setValue(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()- 1));
        fechaFin.setValue(LocalDate.now());
        
        // Formato DatePicker
        fechaInicio.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate fecha = fechaFin.getValue();
                setDisable(empty || date.compareTo(fecha) > 0 );
            }
        });
        
        fechaFin.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate fecha = fechaInicio.getValue();
                setDisable(empty || date.compareTo(fecha) < 0 );
            }
        });
        
        updateTable(null);
    }    

    @FXML
    private void goStatsView(ActionEvent event) {
        try {
            Parent statsViewParent = FXMLLoader.load(getClass().getResource("/vista/HistorialPartidasRealizadasStats.fxml"));
            Scene statsViewScene = new Scene(statsViewParent);

            // Se obtiene la informacion de la ventana (Stage)
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Estadisticas de partidas");
            window.setScene(statsViewScene);
            //Ventana reajustable
            window.setResizable(false);
            window.show();

        } catch (Exception e) {
            System.out.println("No se pudo cargar la escena");
            System.out.println(e);
        }
    }

    @FXML
    private void goMainScreen(ActionEvent event) {
        try {
            Parent mainScreenParent = FXMLLoader.load(getClass().getResource("/vista/MainScreen.fxml"));
            Scene mainScreenScene = new Scene(mainScreenParent);

            // Se obtiene la informacion de la ventana (Stage)
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Lobby");
            window.setScene(mainScreenScene);
            //Ventana reajustable
            window.setResizable(false);
            window.show();

        } catch (Exception e) {
            System.out.println("No se pudo cargar la escena");
        }
    }

    @FXML
    private void updateTable(ActionEvent event) {
        
        // Partidas realizadas (En general)
        setDataInTime();
            
        if (!nickPlayer.getText().isEmpty()) {                      // Partidas realizadas por X jugador
            estadisticas.setDisable(true);
            
            if (ganadas.isSelected()&& perdidas.isSelected()) {     // Partidas ganadas y perdidas por X jugador
                estadisticas.setDisable(false);
                
            } else if (ganadas.isSelected()) {                      // Partidas ganadas por X jugador
                setDataWithPlayerWins();
                
            } else if (perdidas.isSelected()) {                     // Partidas perdidas por X jugador
                setDataWithPlayerLose();
                
            } else  {                                               // Historia partidas de X jugador
                setDataWithPlayerNick();
            }
        }
    }
    
    private void setDataInTime() {
        
        observableRound.clear();
        
        TreeMap<LocalDate, List<Round>> roundPerDay = db.getRoundsPerDay();
        // Cambiamos el orden (Descendente - para ser mas reciente - menos reciente)
        NavigableMap<LocalDate, List<Round>> roundPerDayReverse = roundPerDay.descendingMap();
        
        roundPerDayReverse.forEach((LocalDate date, List<Round> rounds) -> {
            //System.out.println("Dia: " + date + "partidas jugadas: " + rounds.size());
            
            // Mostar partidas
            if (date.isEqual(fechaInicio.getValue()) || date.isEqual(fechaFin.getValue()) ||
                    (date.isAfter(fechaInicio.getValue()) && date.isBefore(fechaFin.getValue()))) {
                for (int i = 0; i < rounds.size(); i++) {
                    Round aux = rounds.get(i);
                    //System.out.println(aux); 
                    observableRound.add(aux);
                }
            }
        });
        
        tablaHistorial.setItems(observableRound);
    }

    
    // Metodos de busqueda
    private void setDataWithPlayerNick() {
        FilteredList<Round> filteredList = new FilteredList<>(observableRound, i -> 
                i.getWinner().getNickName().startsWith(nickPlayer.getText()) ||
                    i.getLoser().getNickName().startsWith(nickPlayer.getText()));
        
          
        System.out.println(filteredList);
        tablaHistorial.setItems(filteredList);
    }

    private void setDataWithPlayerWins() {
        FilteredList<Round> filteredList = new FilteredList<>(observableRound, i -> i.getWinner().getNickName().startsWith(nickPlayer.getText()));
        tablaHistorial.setItems(filteredList);
    }

    private void setDataWithPlayerLose() {
        FilteredList<Round> filteredList = new FilteredList<>(observableRound, i -> i.getLoser().getNickName().startsWith(nickPlayer.getText()));
        tablaHistorial.setItems(filteredList);
    }

    @FXML
    private void updateStatus(KeyEvent event) {
        updateTable(null);
    }
    
}

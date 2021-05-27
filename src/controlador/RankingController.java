/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DBAccess.Connect4DAOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Connect4;
import model.Player;

/**
 * FXML Controller class
 *
 * @author CrazyJMB
 */
public class RankingController implements Initializable {

    // Base de datos
    private Connect4 db = null;
    
    private ObservableList<Player> observablePlayers;
    
    @FXML
    private TableView<Player> ranking;
    @FXML
    private TableColumn<Player, Image> avatarColumn;
    @FXML
    private TableColumn<Player, String> usernameColumn;
    @FXML
    private TableColumn<Player, Double> pointsColumn;
    @FXML
    private TextField filterField;

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
        
        observablePlayers = FXCollections.observableList(db.getConnect4Ranking());
        
        
        avatarColumn.setCellValueFactory((cellData) -> new SimpleObjectProperty<Image>(cellData.getValue().getAvatar()));
        avatarColumn.setCellFactory((columna) -> {
            return new TableCell<Player, Image>() {
                private final ImageView view = new ImageView();
                
                @Override
                protected void updateItem(Image item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setGraphic(null);
                    } else {
                        view.setFitHeight(60);
                        view.setFitWidth(50);
                        view.setImage(item);
                        setGraphic(view);
                    }
                }
            };
        });
        avatarColumn.setStyle("-fx-alignment: CENTER;"); // Imagenes en el medio
        
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("nickName"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
        
        ranking.setItems(observablePlayers);
        
        // Filtro de busqueda
        FilteredList<Player> filteredData = new FilteredList<>(observablePlayers, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(player -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                        return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (player.getNickName().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches first name.
                } else  
                    return false; // Does not match.
            });
        });
        
        SortedList<Player> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(ranking.comparatorProperty());
        ranking.setItems(sortedData);
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
}

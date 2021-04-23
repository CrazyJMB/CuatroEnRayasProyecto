/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * FXML Controller class
 *
 * @author CrazyJMB
 */
public class InGameScreenController implements Initializable {

    private int TILE_SIZE;
    private static final int COLUMNS = 8;
    private static final int ROWS = 7;
            
    @FXML
    private Pane gamePane;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Creamos el tablero
        Shape gridShape = makeGrid((int) gamePane.getMinWidth() / 9);
        // añadimos a el panel el grid con los circulos
        gamePane.getChildren().add(gridShape);
        // Añadimos al pane las columnas selecionadas
        gamePane.getChildren().addAll(makeColumns());
        
    }    

    private Shape makeGrid(int tileSize) {
        TILE_SIZE = tileSize;
        System.out.println(TILE_SIZE);
        
        Shape shape = new Rectangle((COLUMNS + 1) * TILE_SIZE, (ROWS + 1) * TILE_SIZE);
        
        for (int i = 0; i< COLUMNS; i++){
            for (int j = 0; j < ROWS; j++) {
                Circle circle = new Circle(TILE_SIZE / 2);
                circle.setCenterX(TILE_SIZE / 2);
                circle.setCenterY(TILE_SIZE / 2);
                circle.setTranslateX(i * (TILE_SIZE + 5) + TILE_SIZE / 4);
                circle.setTranslateY(j * (TILE_SIZE + 5) + TILE_SIZE / 4);
                
                shape = Shape.subtract(shape, circle);
            }
        }
        
        //Decoracion del tablero
        Light.Distant light = new Light.Distant();
        light.setAzimuth(60.0);
        light.setElevation(50.0);
        
        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(1.0);
        
        // Efectos al tablero
        shape.setFill(Color.LIGHTBLUE);
        shape.setEffect(lighting);
       
        return shape;
    }
    
    private List<Rectangle> makeColumns() {
        List<Rectangle> list = new ArrayList<>();
        
        for (int x = 0; x < COLUMNS; x++) {
            Rectangle rect = new Rectangle(TILE_SIZE , (ROWS + 1) * TILE_SIZE);
            rect.setTranslateX(x * (TILE_SIZE + 5) + TILE_SIZE / 4);
            rect.setFill(Color.TRANSPARENT);
            
            rect.setOnMouseEntered(e -> rect.setFill(Color.rgb(200, 200, 50, 0.3)));
            rect.setOnMouseExited(e -> rect.setFill(Color.TRANSPARENT));
            
            list.add(rect);
        }
        
        return list;
    }
    
}

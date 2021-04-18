package nhom2.window;

import javafx.scene.control.CheckBox;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import nhom2.graph.*;
import nhom2.graphview.*;


public class Main extends Application {

    @Override
    public void start(Stage stage) {
    	Graph<String, String> g = build_sample_digraph();
        //Graph<String, String> g = build_flower_graph();
        System.out.println(g);
        
        //SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();
        GraphPanel<String, String> graphView = new GraphPanel<>(g);
       
        Scene scene = new Scene(graphView, 200, 500);
        graphView.init();
        graphView.start_automatic_layout();
        stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("JavaFX SmartGraph Visualization");
        stage.setMinHeight(500);
        stage.setMinWidth(800);
        stage.setScene(scene);
        stage.show();  
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Graph<String, String> build_sample_digraph() {

    	GraphEdgeList<String ,String> g = new GraphEdgeList<String,String>();

        g.insertVertex("A");
        g.insertVertex("B");
        g.insertVertex("C");
        g.insertVertex("D");
        g.insertVertex("E");
        g.insertVertex("F");
        g.insertVertex("G");
        g.insertVertex("H");

        g.insertEdge("A", "B", "AB");
        g.insertEdge("B", "A", "AB2");
        g.insertEdge("A", "C", "AC");
        g.insertEdge("A", "D", "AD");
        g.insertEdge("B", "C", "BC");
        g.insertEdge("C", "D", "CD");
        g.insertEdge("B", "E", "BE");
        g.insertEdge("F", "D", "DF");
        g.insertEdge("F", "D", "DF2");
        g.insertEdge("G", "H", "GH");

        //yep, its a loop!
        g.insertEdge("A", "A", "Loop");

        return g;
    }

}


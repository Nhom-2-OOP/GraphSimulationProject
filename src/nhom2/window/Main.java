package nhom2.window;

import java.util.concurrent.TimeUnit;
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
	GraphEdgeList<String, String> g = build_sample_digraph();
	GraphPanel<String, String> graphView = new GraphPanel<>(g);
	Scene scene = new Scene(graphView, 200, 500);

	public void init() {
        graphView.init();
        graphView.start_automatic_layout();
	}
    @Override
    public void start(Stage stage) {

        stage = new Stage();
        stage.setTitle("Graph Visualization");
        stage.setMinHeight(500);
        stage.setMinWidth(800);
        stage.setScene(scene);
        stage.show();  
    }

    public static void main(String[] args) {
        launch(args);
    }

    private GraphEdgeList<String, String> build_sample_digraph() {

    	GraphEdgeList<String ,String> g = new GraphEdgeList<String,String>();

        g.insertVertex("A");
        g.insertVertex("B");
        g.insertVertex("C");
        g.insertVertex("D");
        g.insertVertex("E");
        g.insertVertex("F");


        g.insertEdge("A", "B", "AB");
        g.insertEdge("B", "A", "AB2");
        g.insertEdge("A", "C", "AC");
        g.insertEdge("A", "D", "AD");
        g.insertEdge("B", "C", "BC");
        g.insertEdge("C", "D", "CD");
        g.insertEdge("B", "E", "BE");
        g.insertEdge("F", "D", "DF"); 
        g.insertEdge("F", "D", "DF2");
        g.insertEdge("B", "F", "BF");

        return g;
    }

}


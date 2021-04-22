package nhom2.window;

import java.util.concurrent.TimeUnit;
import javafx.scene.control.CheckBox;

import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import nhom2.button.AutoPlacementButton;
import nhom2.button.CaptureGraphPanel;
import nhom2.button.CircularPlacementButton;
import nhom2.graph.*;
import nhom2.graphview.*;


public class Main extends Application {

    @Override
    public void start(Stage stage) {
    	
    	// Tao scene bieu dien do thi
    	GraphEdgeList<String, String> g = build_sample_digraph();
    	GraphPanel<String, String> graphView = new GraphPanel<>(g);
    	SubScene subSceneOne = new SubScene(graphView,500,500);
    	graphView.init();
        graphView.start_automatic_layout();
        
        // Tao nut sap dinh theo vong tron
    	CircularPlacementButton ButtonOne = new CircularPlacementButton(graphView);
    	ButtonOne.setText("Sắp đỉnh theo vòng tròn");
    	SubScene subSceneTwo = new SubScene(ButtonOne,150,30);
    	
    	// Tao nut sap dinh tu dong
    	AutoPlacementButton ButtonTwo = new AutoPlacementButton(graphView);
    	ButtonTwo.setText("Sắp đỉnh tự động");
    	SubScene subSceneThree = new SubScene(ButtonTwo,150,30);
    	
    	// Tao nut luu anh do thi
    	CaptureGraphPanel ButtonThree = new CaptureGraphPanel(subSceneOne, stage);
    	ButtonThree.setText("Lưu ảnh đồ thị");
    	SubScene subSceneFour = new SubScene(ButtonThree,150,30);
    	
    	// Tao layout VBox
    	VBox root = new VBox(10);
    	root.getChildren().addAll(subSceneOne, subSceneTwo, subSceneThree, subSceneFour);
    	
    	Scene scene = new Scene(root, 521, 650);
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

    	GraphEdgeList<String,String> g = new GraphEdgeList<String,String>(false);

        g.insertVertex("A");
        g.insertVertex("B");
        g.insertVertex("C");
        g.insertVertex("D");
        g.insertVertex("E");
        g.insertVertex("F");
        
        g.insertVertex("G");
        g.insertVertex("H");
        g.insertVertex("I");
        g.insertVertex("J");
        g.insertVertex("AA");
        g.insertVertex("BB");
        g.insertVertex("CC");
        g.insertVertex("DD");
        g.insertVertex("EE");
        g.insertVertex("FF");
        
        g.insertVertex("GG");
        g.insertVertex("HH");
        g.insertVertex("II");
        g.insertVertex("JJ");

        g.insertEdge("A", "B", "AB1");
        g.insertEdge("A", "C", "AC");
        g.insertEdge("A", "G", "AG");
        g.insertEdge("A", "H", "AH");
        
        g.insertEdge("A", "D", "AD");
        
        g.insertEdge("D", "E", "DE");
        g.insertEdge("D", "F", "DF");
        g.insertEdge("D", "I", "DI");
        g.insertEdge("D", "J", "DJ");
        
//      g.insertEdge("AA", "BB", "AB1");
        g.insertEdge("AA", "CC", "AC1");
        g.insertEdge("AA", "GG", "AG1");
        g.insertEdge("AA", "HH", "AH1");
        
        g.insertEdge("AA", "DD", "AD1");
        
        g.insertEdge("DD", "EE", "DE1");
        g.insertEdge("DD", "FF", "DF1");
        g.insertEdge("DD", "II", "DI1");
        g.insertEdge("DD", "JJ", "DJ1");
        
        
        g.insertEdge("BB", "B", "BBB");
        g.insertEdge("I", "BB", "ADD1");
        g.insertEdge("I", "H", "HII");
        g.insertEdge("C", "H", "HCII");
        return g;
    }

}


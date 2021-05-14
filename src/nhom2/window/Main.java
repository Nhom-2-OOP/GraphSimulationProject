package nhom2.window;

import java.util.concurrent.TimeUnit;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import nhom2.button.AutoPlacementButton;
import nhom2.button.CaptureGraphPanel;
import nhom2.button.CircularPlacementButton;
import nhom2.button.ColoringButton;
import nhom2.button.FindPathButton;
import nhom2.button.InfoButton;
import nhom2.button.InputButton;
import nhom2.button.RandomPlacementButton;
import nhom2.graph.*;
import nhom2.graphview.*;


public class Main extends Application {
	public static GraphEdgeList<String, String> g= build_sample_digraph();
	public static GraphPanel<String, String> graphView;
    @Override
    public void start(Stage stage) {
    	
    	// Tao scene bieu dien do thi
    	graphView = new GraphPanel<>(g);
    	SubScene subSceneGraphPanel = new SubScene(graphView,800,600);
    	graphView.init();
        //graphView.start_automatic_layout();
        
    	//Tap nut sap xep dinh ngau nhien
    	RandomPlacementButton btnRanPla = new RandomPlacementButton(graphView);
    	btnRanPla.setText("Sắp đỉnh ngẫu nhiên");
    	SubScene subSceneRanPla = new SubScene(btnRanPla,150,30);
    	
        // Tao nut sap dinh theo vong tron
    	CircularPlacementButton btnCircularPla = new CircularPlacementButton(graphView);
    	btnCircularPla.setText("Sắp đỉnh theo vòng tròn");
    	SubScene subSceneCircularPla = new SubScene(btnCircularPla,150,30);
    	
    	// Tao nut sap dinh tu dong
    	AutoPlacementButton btnAutoPla = new AutoPlacementButton(graphView);
    	btnAutoPla.setText("Sắp đỉnh tự động");
    	SubScene subSceneAutoPla = new SubScene(btnAutoPla,150,30);
    	
    	// Tao nut luu anh do thi
    	CaptureGraphPanel btnCaptureGP = new CaptureGraphPanel(subSceneGraphPanel, stage);
    	btnCaptureGP.setText("Lưu ảnh đồ thị");
    	SubScene subSceneCaptureGP = new SubScene(btnCaptureGP,150,30);
    	
    	// Tao nut input do thi
    	InputButton btnInput = new InputButton(stage);
    	btnInput.setText("Input đồ thị");
    	SubScene subSceneInput = new SubScene(btnInput,150,30);

    	// Tao nut tu tim duong di
    	FindPathButton<String, String> btnFindPath = new FindPathButton(stage, graphView);
    	btnFindPath.setText("Tự đường đi");
    	SubScene subSceneFindPath = new SubScene(btnFindPath,150,30);
    	
    	// Tao nut tu dong tim duong di
    	Button btnAutoFindPath = new Button();
    	btnAutoFindPath.setText("Tự động tìm đường đi");
    	SubScene subSceneAutoFindPath = new SubScene(btnAutoFindPath,150,30);
    	
    	// Tao nut to mau do thi
    	ColoringButton ColoringButton = new ColoringButton(graphView);
    	ColoringButton.setText("Tô màu đồ thị");
    	SubScene subSceneColoring = new SubScene(ColoringButton,150,30);
    	
    	// Tao nut to mau do thi
    	Button DFSButton = new Button();
    	DFSButton.setText("Tìm đường DFS");
    	SubScene subSceneDFS = new SubScene(DFSButton,150,30);
    	
    	// Tao nut to mau do thi
    	Button BFSButton = new Button();
    	BFSButton.setText("Tìm đường BFS");
    	SubScene subSceneBFS = new SubScene(BFSButton,150,30);
    	
    	// Tao nut thông tin đồ thị
    	Button InfoButton = new InfoButton();
    	InfoButton.setText("Thông tin nhóm");
    	SubScene subSceneInfo = new SubScene(InfoButton,150,30);

    	// Tao layout VBox
    	GridPane buttonArea = new GridPane();
    	buttonArea.add(subSceneInput, 0, 0);
    	buttonArea.add(subSceneCaptureGP, 0, 1);
    	buttonArea.add(subSceneRanPla, 0, 2);
    	buttonArea.add(subSceneAutoPla, 0, 3);
    	buttonArea.add(subSceneCircularPla, 0, 4);
    	buttonArea.add(subSceneFindPath, 0, 5);
    	buttonArea.add(subSceneAutoFindPath, 0, 6);
    	buttonArea.add(subSceneColoring, 0, 7);
    	buttonArea.add(subSceneDFS, 0, 8);
    	buttonArea.add(subSceneBFS, 0, 9);
    	buttonArea.add(subSceneInfo, 0, 10);
    	buttonArea.setVgap(10);

    	GridPane root = new GridPane();
    	root.add(buttonArea, 0, 0);
    	root.add(subSceneGraphPanel, 1, 0);
    	root.setHgap(20);
    	root.setPadding(new Insets(10, 10, 100, 10));
    	
    	Scene scene = new Scene(root, 1000, 650);
        stage = new Stage();
        stage.setTitle("Graph Visualization");
        stage.setMinHeight(800);
        stage.setMinWidth(800);
        stage.setScene(scene);
        stage.show();  
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static GraphEdgeList<String, String> build_sample_digraph() {

    	GraphEdgeList<String,String> g = new GraphEdgeList<String,String>(false);

        g.insertEdge("A", "B", "AB1");
        g.insertEdge("A", "C", "AC");
        g.insertEdge("A", "G", "AG");    
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
        
        
        g.insertEdge("BB", "B", "BBB1");
        g.insertEdge("BB", "B", "BBB2");
        g.insertEdge("B", "BB", "BBB3");
        g.insertEdge("I", "BB", "ADD1");
        g.insertEdge("I", "H", "HII");
        g.insertEdge("BB", "H", "BHBB");
        g.insertEdge("B", "GG", "BHBB1");
        
        return g;
    }
    public static void setGraph(GraphEdgeList<String, String> NewGraph) {
    	g = NewGraph;
    	graphView.Renew(NewGraph, true); 
    }
}


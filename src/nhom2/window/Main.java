package nhom2.window;

import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import nhom2.button.AutoPlacementButton;
import nhom2.button.BFSButton;
import nhom2.button.CaptureGraphPanel;
import nhom2.button.CircularPlacementButton;
import nhom2.button.ColoringButton;
import nhom2.button.DFSButton;
import nhom2.button.AutoFindPaths;
import nhom2.button.FindPathButton;
import nhom2.button.InfoButton;
import nhom2.button.InputButton;
import nhom2.button.RandomPlacementButton;
import nhom2.button.buttonAreaVBox;
import nhom2.graph.*;
import nhom2.graphview.*;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.ColumnConstraints;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;


public class Main extends Application {
	public static GraphEdgeList<String, String> g= build_sample_digraph();
	public static GraphPanel<String, String> graphView;	
	public static Node NodeButtonArea;

	@Override
	public void start(Stage stage) {

		// Tao scene bieu dien do thi
		graphView = new GraphPanel<>(g);
		SubScene subSceneGraphPanel = new SubScene(graphView,800,600);
		//graphView.start_automatic_layout();
		GridPane root = new GridPane();


		//VBox
		VBox buttonArea = new buttonAreaVBox().area(graphView, subSceneGraphPanel, stage, root);

		//		GridPane root = new GridPane();
		root.setHgap(10);
		root.setPadding(new Insets(10, 10, 10, 10));

		//row0
		RowConstraints r = new RowConstraints();
		r.setPercentHeight(80);
		r.setValignment(VPos.CENTER);
		root.getRowConstraints().add(r);
		
		r = new RowConstraints();
		r.setPercentHeight(20);
		r.setValignment(VPos.CENTER);
		root.getRowConstraints().add(r);

		// col 0
		ColumnConstraints c = new ColumnConstraints();
		//		c.setPercentWidth(15);
		c.setPrefWidth(300);
		c.setHalignment(HPos.CENTER);
		root.getColumnConstraints().add(c);
		root.add(buttonArea, 0, 0);

		//col 1
		c = new ColumnConstraints();
		//		c.setPercentWidth(85);
		c.setHalignment(HPos.CENTER);
		root.getColumnConstraints().add(c);
		Pane graphPane = new Pane(); 
		root.add(graphPane, 1, 0);
		root.setHgrow(graphPane, Priority.ALWAYS);
		graphPane.resize(500, 500);
		graphPane.getChildren().add(subSceneGraphPanel);
		subSceneGraphPanel.heightProperty().bind(graphPane.heightProperty());
		subSceneGraphPanel.widthProperty().bind(graphPane.widthProperty());
		graphView.init();

		Scene scene = new Scene(root);
		stage = new Stage();
		stage.setTitle("Nhóm 2 - OOP - Graph Visualization");
		stage.setMinWidth(800);
		stage.setMinHeight(700);
		stage.setScene(scene);
		stage.show();  

		NodeButtonArea = root.getChildren().get(0);
		root.setManaged(false);
		//		root.getChildren().remove(0);
	}

	public static Node getNodeButtonArea() {
		return NodeButtonArea;
	}

	public static void main(String[] args) {
		launch(args);
	}




	private static GraphEdgeList<String, String> build_sample_digraph() {

		GraphEdgeList<String,String> g = new GraphEdgeList<String,String>(true);

		g.insertEdge("A", "B", "AB1");
		g.insertEdge("A", "C", "AC");
		g.insertEdge("A", "G", "AG");
		g.insertEdge("A", "H", "AH");    
		g.insertEdge("A", "D", "AD");

		g.insertEdge("D", "E", "DE");
		g.insertEdge("D", "F", "DF");
		g.insertEdge("D", "I", "DI");
		g.insertEdge("D", "J", "DJ");

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
		g.insertEdge("C", "H", "HCII");
		g.insertEdge("BB", "H", "BHBB");
		g.insertEdge("DD", "H", "1");
		return g;
	}


	public static void setGraph(GraphEdgeList<String, String> NewGraph) {
		g = NewGraph;
		graphView.Renew(NewGraph, true); 
	}
}
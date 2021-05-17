package nhom2.window;

import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
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
import nhom2.button.ButtonAreaVBox;
import nhom2.graph.*;
import nhom2.graphview.*;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.scene.layout.ColumnConstraints;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;


public class Main extends Application {
	public static GraphEdgeList<String, String> g= build_sample_digraph();
	public static GraphPanel<String, String> graphView;	
	public static Node nodeCol1Start;

	@Override
	public void start(Stage stage) {

		// Tao scene bieu dien do thi
		graphView = new GraphPanel<>(g);
		
		
		SubScene subSceneGraphPanel = new SubScene(graphView,800,600);
		//graphView.start_automatic_layout();
		GridPane root = new GridPane();

		//		GridPane root = new GridPane();
//		root.setPadding(new Insets(0, 0, 100, 0));

		//row0
		RowConstraints r = new RowConstraints();
		r.setPercentHeight(100);
		//		r.setValignment(VPos.CENTER);
		root.getRowConstraints().add(r);

//		//row1
//		r = new RowConstraints();
//		r.setPercentHeight(20);
//		r.setValignment(VPos.CENTER);
//		root.getRowConstraints().add(r);

		// col 0
		ColumnConstraints c = new ColumnConstraints();
		c.setPrefWidth(55);
		c.setHalignment(HPos.CENTER);
		root.getColumnConstraints().add(c);


		// col 1
		c = new ColumnConstraints();
		c.setPrefWidth(245);
		c.setHalignment(HPos.LEFT);
		root.getColumnConstraints().add(c);
		Pane col1Pane = new Pane();
		VBox labelButton = new ButtonAreaVBox().label();
		
		col1Pane.getChildren().add(labelButton);
		
		//col 2
		c = new ColumnConstraints();
		c.setHalignment(HPos.CENTER);
		root.getColumnConstraints().add(c);
		Pane graphPane = new Pane(); 
		
		root.setHgrow(graphPane, Priority.ALWAYS);
		graphPane.resize(500, 500);
		graphPane.getChildren().add(subSceneGraphPanel);
		subSceneGraphPanel.heightProperty().bind(graphPane.heightProperty());
		subSceneGraphPanel.widthProperty().bind(graphPane.widthProperty());
		graphView.init();


		//buttonArea
		GridPane buttonArea = new ButtonAreaVBox().area(graphView, subSceneGraphPanel, stage, root);
		
		root.add(graphPane, 2, 0);		
		root.add(buttonArea, 0, 0);
		root.add(col1Pane, 1, 0);
		nodeCol1Start = root.getChildren().get(2);

		Scene scene = new Scene(root);

		scene.getStylesheets().add(getClass().getResource("design/test.css").toExternalForm());
		//System.out.println(scene.getStylesheets());


		stage = new Stage();
		stage.setTitle("Nhóm 2 - OOP - Graph Visualization");
		stage.setMinWidth(800);
		stage.setMinHeight(700);
		stage.setScene(scene);
		stage.show();  
		
		root.getStyleClass().add("rootMain");
		root.setManaged(false);
	}


	public static Node getNodeCol1Start() {
		return nodeCol1Start;
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
//		for (Edge<String, String> edge : NewGraph.edges.values()) {
//            Vertex vertex = edge.Vertices()[0];
//            Vertex oppositeVertex = edge.Vertices()[1];
//            System.out.println(vertex.element() + " " + oppositeVertex.element());
//        }
		g = NewGraph;
		graphView.Renew(NewGraph, true); 
	}
}
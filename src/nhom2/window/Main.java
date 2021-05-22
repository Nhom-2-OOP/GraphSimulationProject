package nhom2.window;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import nhom2.button.ButtonAreaVBox;
import nhom2.button.MapButton;
import nhom2.button.ScaleButton;
import nhom2.graph.*;
import nhom2.graphview.*;
import nhom2.graphview.MiniMap.MiniMap;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.ColumnConstraints;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import nhom2.graphview.Zoom.SceneGestures;

public class Main extends Application {
	public static GraphEdgeList<String, String> g= build_sample_digraph();
	public static GraphPanel<String, String> graphView;	
	public static Node nodeCol1Start;

	@Override
	public void start(Stage stage) {

		// Tao scene bieu dien do thi
		graphView = new GraphPanel<>(g);
		
		
		SubScene subSceneGraphPanel = new SubScene(graphView,0,0);
		GridPane root = new GridPane();

		//row0
		RowConstraints r = new RowConstraints();
		r.setPercentHeight(100);
		root.getRowConstraints().add(r);

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
		
		Screen screen = Screen.getPrimary();
		ScaleButton scaleBut = new ScaleButton(graphView);	
		
		
		MiniMap miniMap = new MiniMap( graphView);
		
		MapButton mapBut = new MapButton(miniMap);

		root.setHgrow(graphPane, Priority.ALWAYS);
		graphPane.resize(screen.getVisualBounds().getWidth() - 300, screen.getVisualBounds().getHeight());
		graphPane.getChildren().add(subSceneGraphPanel);
		graphPane.getChildren().add(scaleBut);
		graphPane.getChildren().add(mapBut);
		graphPane.getChildren().add(miniMap);

		
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

		scene.getStylesheets().add(getClass().getResource("design/windowCSS.css").toExternalForm());

		stage = new Stage();
		stage.setTitle("Nhóm 2 - OOP - Graph Visualization");
		
		stage.setMinHeight(screen.getVisualBounds().getHeight());
		stage.setMinWidth(screen.getVisualBounds().getWidth());
		stage.setMaximized(true);
		stage.setScene(scene);
		stage.show();  
		
		root.getStyleClass().add("rootMain");
		
		graphPane.getStyleClass().add("graphPane");
		SceneGestures sceneGestures = new SceneGestures(graphView, scaleBut);
		subSceneGraphPanel.addEventFilter( MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
		subSceneGraphPanel.addEventFilter( MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
		subSceneGraphPanel.addEventFilter( ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());

	}


	public static Node getNodeCol1Start() {
		return nodeCol1Start;
	}

	public static void main(String[] args) {
		launch(args);
	}




	private static GraphEdgeList<String, String> build_sample_digraph() {

		GraphEdgeList<String,String> g = new GraphEdgeList<String,String>(false);

		g.insertEdge("A", "B", "AB1");
//		g.insertEdge("A", "C", "AC");
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

		g.insertEdge("BB", "B", "BBB2");
		g.insertEdge("I", "BB", "ADD1");
		g.insertEdge("I", "H", "HII");
		g.insertEdge("C", "H", "HCII");
		g.insertEdge("BB", "H", "BHBB");
		g.insertEdge("DD", "H", "1");
		return g;
	}


	public static void setGraph(GraphEdgeList<String, String> NewGraph) {
		g = NewGraph;
		int n = g.vertices.size();
		if (n > 100) {
			if (n <= 1000) GraphPanel.VertexR = 10;
			else GraphPanel.VertexR = 5;
		}
		else {
			GraphPanel.VertexR = 15;
		}
		graphView.Renew(NewGraph, true); 
	}
}
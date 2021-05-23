package nhom2.window;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
import nhom2.window.MulTab.GraphTab;
import nhom2.window.PaneGraph.PaneGraph;
//import sun.tools.tree.ThisExpression;

public class Main extends Application {
	public static GraphEdgeList<String, String> g= build_sample_digraph();
	public static GraphPanel<String, String> graphView;	
	public static Node nodeCol1Start;

	@Override
	public void start(Stage stage) {

		// Tao scene bieu dien do thi
		graphView = new GraphPanel<>(g);

		Screen screen = Screen.getPrimary();

		GridPane root = new GridPane();
		root.getStyleClass().add("rootMain");

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
		

		Button test = new Button();
		
//		GridPane col2GP = new GridPane();
//		SubScene subtest = new SubScene (test, 40, 40);
//		col2GP.add(subtest, 0, 0);
//		col2GP.add(graphPane, 0, 1);
//		
		
		TabPane tabPane = new TabPane();
		
		GraphTab tab1 = new GraphTab(g);
		tabPane.getTabs().add(tab1);

		
//		GraphTab tab2 = new GraphTab(g);
//		tabPane.getTabs().add(tab2);
		
		
		
		tabPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
		    @Override
		    public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
		        System.out.println(tabPane.getSelectionModel().getSelectedIndex());
		    }
		}); 
		
		
		
		
		
		
		

		//buttonArea
		GridPane buttonArea = new ButtonAreaVBox().area(graphView, stage, root);
		
//		root.add(graphPane, 2, 0);	
//		root.add(col2GP, 2, 0);
		root.add(tabPane, 2, 0);
		
		
		
		
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
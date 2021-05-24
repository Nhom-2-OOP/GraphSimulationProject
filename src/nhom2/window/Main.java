package nhom2.window;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import nhom2.button.ButtonAreaVBox;
import nhom2.graph.*;
import nhom2.graphview.*;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.ColumnConstraints;
import javafx.geometry.HPos;
import nhom2.window.MulTab.GraphTab;
//import sun.tools.tree.ThisExpression;

public class Main extends Application {
	public static GraphEdgeList<String, String> g= build_sample_digraph();
	public static GraphPanel<String, String> graphView;	
	public static Node nodeCol1Start;
	public int numOfTab = 1;
	public static Stage stage;
	public static GridPane root;

	@Override
	public void start(Stage stage) {
		this.stage = stage;

		Screen screen = Screen.getPrimary();

		root = new GridPane();
		//		this.root = root;
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


		TabPane tabPane = new TabPane();

		GridPane buttonArea = new ButtonAreaVBox().area(graphView, stage, root);


		root.add(tabPane, 2, 0);
		root.add(buttonArea, 0, 0);
		root.add(col1Pane, 1, 0);



		Tab addGraphTabBut = new Tab();
		addGraphTabBut.setText("+");
		Label addLabel = new Label();
		addLabel.setText("+");
		addGraphTabBut.setGraphic(addLabel);
		addGraphTabBut.setClosable(false);
//		addGraphTabBut.setStyle("addGraphTabBut");
		
//		GraphTab addGraphTabBut = new GraphTab(new );

		GraphTab tab1 = new GraphTab(g);

		tabPane.getTabs().add(addGraphTabBut);
		tabPane.getTabs().add(tab1);

//		tabPane.getStyleClass().add("tabPane");

		ArrayList<GraphTab> listTab = new ArrayList<GraphTab>();
		//		GraphTab[] listTab = new GraphTab[11];
		listTab.add(0, tab1);
		listTab.add(1, tab1);
		tab1.setButtonArea();
		graphView = tab1.graphView;
		tabPane.getSelectionModel().select(1);;


		tabPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
				if(tabPane.getSelectionModel().isSelected(0)) {
					if(numOfTab < 10) {
						GraphTab newTab = new GraphTab(new GraphEdgeList<String,String>(false));
						tabPane.getTabs().add(newTab);

						numOfTab++;
						listTab.add(newTab);
					}
					tabPane.getSelectionModel().selectLast();
				}
				else {
					listTab.get(tabPane.getSelectionModel().getSelectedIndex()).setButtonArea();
					graphView = listTab.get(tabPane.getSelectionModel().getSelectedIndex()).graphView;
					tabPane.getTabs().get(tabPane.getSelectionModel().getSelectedIndex()).setOnClosed(event -> {
						listTab.remove(tabPane.getSelectionModel().getSelectedIndex());
					});
				}
			}
		}); 



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
package nhom2.button.graphAlgo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nhom2.button.utilities.BackButton;
import nhom2.graphview.*;
import nhom2.graphview.Edge.EdgeLine;
import nhom2.graphview.Vertex.VertexNode;
import nhom2.window.Main;


public class PathFindingButton<V,E> extends Button{

	public PathFindingButton(GridPane root, Stage stage, GraphPanel<V,E> graphView) {
		GridPane grid = new GridPane();

		//BackButton
		BackButton backBut = new BackButton(root);

		// Tao nut tu tim duong di
		FindPathButton<String, String> btnFindPath = new FindPathButton(stage, graphView);
		btnFindPath.setText("Tự tìm đường đi");

		// Tao nut tu dong tim duong di
		Button btnAutoFindPath = new AutoFindPaths<String, String>(graphView);
		btnAutoFindPath.setText("Tự động tìm đường");

		// Tao nut BFS
		BFSButton<String, String> BFSButton = new BFSButton(root, graphView);
		BFSButton.setText("Tìm đường theo BFS");

		// Tao nut DFS
		DFSButton<String, String> DFSButton = new DFSButton(root, graphView);
		DFSButton.setText("Tìm đường theo DFS");
		
		// Tao nut dijkstra
		ShortestPathButton<String, String> ShortestPathButton = new ShortestPathButton(root, graphView);
		ShortestPathButton.setText("Tìm đường đi ngắn nhất");
		
		// Tao nut cay khung
		MinimumSpanningTreeButton<String, String> MinimumSpanningTreeButton = new MinimumSpanningTreeButton(root, graphView);
		MinimumSpanningTreeButton.setText("Tìm cây khung nhỏ nhất");

		VBox placeList = new VBox(0);
		
		btnFindPath.getStyleClass().add("Col1ChooseButton");
		btnAutoFindPath.getStyleClass().add("Col1ChooseButton");
		BFSButton.getStyleClass().add("Col1ChooseButton");
		DFSButton.getStyleClass().add("Col1ChooseButton");
		ShortestPathButton.getStyleClass().add("Col1ChooseButton");
		MinimumSpanningTreeButton.getStyleClass().add("Col1ChooseButton");
		
		placeList.getChildren().addAll(btnFindPath,btnAutoFindPath, BFSButton, DFSButton,ShortestPathButton, MinimumSpanningTreeButton);
		placeList.setPadding(new Insets(30, 0, 0, 10));
		grid.add(backBut, 0, 0);
		grid.add(placeList, 0, 1);	

		
		this.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				for (VertexNode tmp : graphView.vertexNodes.values())
					tmp.setStyle("-fx-fill: #96d1cd");
				for (EdgeLine tmp : graphView.edgeNodes.values()) {
					tmp.setStyle("-fx-stroke: #45597e");
					if(graphView.theGraph.isDirected==true) tmp.getAttachedArrow().setStyle(" -fx-stroke: #45597e");
				}
				root.getChildren().remove(2);
				root.add(grid, 1, 0);
				backBut.setGridBack(Main.getNodeCol1Start());
				DFSButton.setNodeBack(grid);
				BFSButton.setNodeBack(grid);
				ShortestPathButton.setNodeBack(grid);
				MinimumSpanningTreeButton.setNodeBack(grid);
			}
		});
		
		this.setOnMouseEntered(mouseEvent -> {
			this.getStyleClass().add("btnPathFindingEntered");
		});
		this.setOnMouseExited(mouseEvent -> {
			this.getStyleClass().remove(4);
		});

	}

}

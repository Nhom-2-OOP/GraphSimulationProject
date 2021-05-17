package nhom2.button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.Queue;
import java.util.TreeMap;
import java.util.TreeSet;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;

import nhom2.graph.*;
import nhom2.graphview.*;
import nhom2.graphview.Edge.EdgeLine;
import nhom2.graphview.Edge.EdgeNode;
import nhom2.graphview.Vertex.VertexNode;
import nhom2.window.Main;


public class PathFindingButton extends Button{

	public PathFindingButton(GridPane root, Stage stage, GraphPanel graphView) {
		GridPane grid = new GridPane();

		//BackButton
		BackButton backBut = new BackButton(root);

		// Tao nut tu tim duong di
		FindPathButton<String, String> btnFindPath = new FindPathButton(stage, graphView);
		btnFindPath.setText("Tự tìm đường đi");
		SubScene subSceneFindPath = new SubScene(btnFindPath,245,30);

		// Tao nut tu dong tim duong di
		Button btnAutoFindPath = new AutoFindPaths<String, String>(graphView);
		btnAutoFindPath.setText("Tự động tìm đường");
		SubScene subSceneAutoFindPath = new SubScene(btnAutoFindPath,245,30);

		// Tao nut BFS
		BFSButton<String, String> BFSButton = new BFSButton(root, graphView);
		BFSButton.setText("Tìm đường theo BFS");
		SubScene subSceneBFS = new SubScene(BFSButton,245,30);

		// Tao nut DFS
		DFSButton<String, String> DFSButton = new DFSButton(root, graphView);
		DFSButton.setText("Tìm đường theo DFS");
		SubScene subSceneDFS = new SubScene(DFSButton,245,30);

		VBox placeList = new VBox(10);
		placeList.getChildren().addAll(backBut, subSceneFindPath, subSceneAutoFindPath, subSceneBFS, subSceneDFS);
		grid.getChildren().add(placeList);

		
		this.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				root.getChildren().remove(2);
				root.add(grid, 1, 0);
				backBut.setGridBack(Main.getNodeCol1Start());
				DFSButton.setNodeBack(grid);
				BFSButton.setNodeBack(grid);
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

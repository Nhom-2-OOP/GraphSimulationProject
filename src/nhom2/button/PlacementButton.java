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


public class PlacementButton extends Button{
	

	public PlacementButton( GridPane root, GraphPanel graphView) {
		GridPane grid = new GridPane();
		System.out.println(Main.getNodeCol1Start());
		//BackButton
		BackButton backBut = new BackButton(root);
		
		
		//Tap nut sap xep dinh ngau nhiên
		RandomPlacementButton btnRanPla = new RandomPlacementButton(graphView);
		btnRanPla.setText("Sắp xếp đỉnh ngẫu nhiên");
		SubScene subSceneRanPla = new SubScene(btnRanPla,245,30);

		// Tao nut sap dinh theo vong tron
		CircularPlacementButton btnCircularPla = new CircularPlacementButton(graphView);
		btnCircularPla.setText("Sắp xếp đỉnh theo vòng tròn");
		SubScene subSceneCircularPla = new SubScene(btnCircularPla,245,30);

		// Tao nut sap dinh tu dong
		AutoPlacementButton btnAutoPla = new AutoPlacementButton(graphView);
		btnAutoPla.setText("Sắp xếp đỉnh tự động");
		SubScene subSceneAutoPla = new SubScene(btnAutoPla,245,30);

		VBox placeList = new VBox(10);
		placeList.getChildren().addAll(backBut, subSceneRanPla, subSceneCircularPla, subSceneAutoPla);
		grid.getChildren().add(placeList);
		
		this.setText("Placement");
		this.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				backBut.setGridBack(Main.getNodeCol1Start());
				root.getChildren().remove(2);
				root.add(grid, 1, 0);
			}
		});

	}

}

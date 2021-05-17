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
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.Node;

import nhom2.graph.*;
import nhom2.graphview.*;
import nhom2.graphview.Edge.EdgeLine;
import nhom2.graphview.Edge.EdgeNode;
import nhom2.graphview.Vertex.VertexNode;
import nhom2.window.Main;


public class FindVertexButton<V,E> extends Button{
	

	public FindVertexButton( GridPane root, GraphPanel<V,E> graphView) {
		GridPane grid = new GridPane();

		//BackButton
		BackButton backBut = new BackButton(root);

		Label lbStartVertex = new Label("Nhập tên đỉnh:");
		TextField tfStartVertex = new TextField();
		Button find = new Button("Tìm");
		tfStartVertex.setPromptText("Tên đỉnh");
		
		tfStartVertex.setPrefWidth(85);
		tfStartVertex.setMaxWidth(85);
		
		grid.add(backBut, 0, 0);
		grid.add(lbStartVertex, 0, 1);
		grid.add(tfStartVertex, 0, 2);
		grid.add(find, 0, 3);

		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setMinSize(500, 200);
		
		EventHandler<ActionEvent> current = backBut.getOnAction();
		
		backBut.setOnAction(e -> {
	        current.handle(e);
	        for (VertexNode<V> tmp : graphView.vertexNodes.values())
				tmp.setStyle("-fx-fill: #96d1cd");
	    });

		find.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				for (VertexNode<V> tmp : graphView.vertexNodes.values())
					tmp.setStyle("-fx-fill: #96d1cd");
	
				tfStartVertex.commitValue();
				String dataStart = tfStartVertex.getText();
				Vertex<V> startVertex = graphView.theGraph.vertices.get(dataStart);
				if (startVertex == null) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setHeaderText("Đỉnh không tồn tại trong đồ thị");
					alert.show();
					return;
				}
				graphView.vertexNodes.get(startVertex).setStyle("-fx-fill: red");
			}
		});
		

		this.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				backBut.setGridBack(Main.getNodeCol1Start());
				root.getChildren().remove(2);
				root.add(grid, 1, 0);
			}
		});
		
		this.setOnMouseEntered(mouseEvent -> {
			this.getStyleClass().add("FindButtonEntered");
		});
		this.setOnMouseExited(mouseEvent -> {
			this.getStyleClass().remove(4);
		});
	}

}

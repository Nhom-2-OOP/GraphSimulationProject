package nhom2.button;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import nhom2.graph.*;
import nhom2.graphview.*;
import nhom2.graphview.Edge.EdgeNode;
import nhom2.graphview.Vertex.VertexNode;

public class FindPathButton<V, E> extends Button {
	private final int LENGTH_LIST_VERTEX = 5;
	private final int MAX_STEP = 20;
	private Stage stage;
	protected Scene View;
	private Vertex<V> currentVertex;
	private Vertex<V> endVertex;
	private int countStep = 0;
	TextField textPath = new TextField("");

	public void a(GridPane grid, GraphPanel<V, E> graphView, Vertex<V> v) {
		// lay ra danh sach dinh ke, canh ke
		Map<Vertex<V>, Edge<E, V>> unknown = graphView.theGraph.adjList.get(v);
		Set<Vertex<V>> adjVertex = unknown.keySet();
		
		ChoiceBox<String> listNextVertex = new ChoiceBox();
		
		int cntV = 0; // so dinh ke hien thi ra
		// ObservableList<String> nextList = FXCollections.observableArrayList();

		for (Vertex<V> iterator : adjVertex) {
			// nextList.add(iterator.element().toString());
			String mi = new String(iterator.element().toString());
			listNextVertex.getItems().add(mi);
			cntV++;
			if (cntV >= LENGTH_LIST_VERTEX)
				break;
		}

		listNextVertex.setMinWidth(100);
		grid.add(listNextVertex, 3, 1);

		// sự kiện click chon dinh

		listNextVertex.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String input = listNextVertex.getValue();
				Vertex<V> inputVertex = graphView.theGraph.vertices.get(input);
				VertexNode<V> inputVertexNode = graphView.vertexNodes.get(inputVertex);
				Edge<E, V> inputEdge = graphView.theGraph.adjList.get(currentVertex).get(inputVertex);
				EdgeNode<E, V> inputEdgeNode = graphView.edgeNodes.get(inputEdge);

				// thay đổi màu đỉnh và cạnh
				inputVertexNode.setStyle("-fx-fill: yellow");
				inputEdgeNode.setStyle("-fx-stroke: blue");
				inputEdgeNode.getAttachedArrow().setStyle("-fx-stroke: blue");
				textPath.appendText(input + " ->");
				currentVertex = inputVertex;
			}
		});

	}

	public FindPathButton(Stage s, GraphPanel<V, E> graphView) {
		GridPane grid = new GridPane();
		TextField tfEndVertex = new TextField();
		TextField tfStartVertex = new TextField();
		Button btFind = new Button("Find");
		Button btn = new Button("OK");
		Button reset = new Button("Reset");
		
		btFind.setMinWidth(80);
		btn.setMinWidth(100);
		reset.setMinWidth(100);
		btn.setVisible(false);
		reset.setVisible(false);
		textPath.setEditable(false);
		textPath.setMinWidth(100);
		tfStartVertex.setPromptText("Dinh bat dau");
		tfStartVertex.setPrefWidth(85);
		tfStartVertex.setMaxWidth(85);
		tfEndVertex.setPromptText("Dinh ket thuc");
		tfEndVertex.setPrefWidth(85);
		tfEndVertex.setMaxWidth(85);

		grid.add(new Label("StartVertex"), 0, 0);
		grid.add(tfStartVertex, 1, 0);
		grid.add(new Label("EndVertex"), 0, 1);
		grid.add(tfEndVertex, 1, 1);
		grid.add(btFind, 1, 2);
		grid.add(new Label("Path"), 0, 5);
		grid.add(textPath, 1, 4, 3 ,4);
		grid.add(btn, 3, 2);
		grid.add(reset, 3, 3);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 100, 20));
		grid.setMinSize(400, 200);
		
		try {
			Parent root = grid;
			this.View = new Scene(root);
			this.stage = s;
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				stage.setScene(View);
				stage.show();
			}
		});

		btFind.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// reset status graphPanel
				for (VertexNode<V> tmp : graphView.vertexNodes.values())
					tmp.setStyle("-fx-fill: #96d1cd");
				for (EdgeNode<E, V> tmp : graphView.edgeNodes.values()) {
					tmp.setStyle("-fx-stroke: #45597e");
					tmp.getAttachedArrow().setStyle(" -fx-stroke: #45597e");
				}
				textPath.clear();
				
				tfStartVertex.commitValue();
				String dataStart = tfStartVertex.getText();
				tfEndVertex.commitValue();
				String dataEnd = tfEndVertex.getText();

				// thuat toan
				Vertex<V> startVertex = graphView.theGraph.vertices.get(dataStart);
				endVertex = graphView.theGraph.vertices.get(dataEnd);
				// chuyển màu riêng 2 đỉnh start và end
				VertexNode<V> startVertexNode = graphView.vertexNodes.get(startVertex);
				VertexNode<V> endVertexNode = graphView.vertexNodes.get(endVertex);
				startVertexNode.setStyle("-fx-fill: red");
				endVertexNode.setStyle("-fx-fill: red");
				
				if (startVertex == null || endVertex == null) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setHeaderText("Đỉnh không tồn tại trong đồ thị");
					alert.show();
					return;
				}
				
				textPath.appendText(dataStart + " ->");
				currentVertex = startVertex;
				grid.add(new Label("Choose next vertex then click OK"), 3, 0);
				btn.setVisible(true);
				reset.setVisible(true);
				
				a(grid, graphView, currentVertex);
			}
		});
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				countStep++;
				if (countStep > MAX_STEP) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("The path has reached its limit step!");
					alert.showAndWait();
					return;
				}
				if (currentVertex.equals(endVertex)) {
					textPath.appendText("Success");
					graphView.vertexNodes.get(currentVertex).setStyle("-fx-fill: red");
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("Done!");
					alert.showAndWait();
					return;
				}
				// TODO Auto-generated method stub
				a(grid, graphView, currentVertex);
			}
		});
		reset.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				for (VertexNode<V> tmp : graphView.vertexNodes.values())
					tmp.setStyle("-fx-fill: #96d1cd");
				for (EdgeNode<E, V> tmp : graphView.edgeNodes.values()) {
					tmp.setStyle("-fx-stroke: #45597e");
					tmp.getAttachedArrow().setStyle(" -fx-stroke: #45597e");
				}
				countStep =0;
				textPath.setText("");
			}

		});
	}
}

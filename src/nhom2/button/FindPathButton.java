package nhom2.button;

import java.util.Map;
import java.util.Set;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import nhom2.graph.*;
import nhom2.graphview.*;
import nhom2.graphview.Edge.EdgeLine;
import nhom2.graphview.Edge.EdgeNode;
import nhom2.graphview.Vertex.VertexNode;

public class FindPathButton<V, E> extends Button {
	private final int LENGTH_LIST_VERTEX = 5;
	private final int MAX_STEP = 20;
	private Stage stage;
	protected Scene View;
	private Vertex<V> currentVertex;
	private Vertex<V> endVertex;
	private Vertex<V> collection[] = new Vertex[21];
	private int countStep = 0;
	private TextField textPath = new TextField("");
	private String input;
	private ChoiceBox<String> listNextVertex = new ChoiceBox();

	// set listNextVertex
	public void setChoiceBox(GridPane grid, GraphPanel<V, E> graphView, Vertex<V> v) {
		// lay ra danh sach dinh ke, canh ke
		Map<Vertex<V>, Edge<E, V>> unknown = graphView.theGraph.adjList.get(v);
		Set<Vertex<V>> adjVertex = unknown.keySet();

		listNextVertex.getItems().clear();
		int cntV = 0; // so dinh ke hien thi ra

		for (Vertex<V> iterator : adjVertex) {
			String mi = new String(iterator.element().toString());
			listNextVertex.getItems().add(mi);
			cntV++;
			if (cntV >= LENGTH_LIST_VERTEX)
				break;
		}


		// sự kiện click chon dinh

		listNextVertex.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				input = listNextVertex.getValue();
			}
		});

	}

	// constructor
	public FindPathButton(Stage s, GraphPanel<V, E> graphView) {
		GridPane grid = new GridPane();
		TextField tfEndVertex = new TextField();
		TextField tfStartVertex = new TextField();
		Button btFind = new Button("Find");
		Button btOk = new Button("OK");
		Button reset = new Button("Reset");
		Button back = new Button("Back");
		HBox pathPane = new HBox();
		pathPane.setAlignment(Pos.CENTER_LEFT);
		
		btFind.setMinWidth(80);
		btOk.setMinWidth(100);
		reset.setMinWidth(100);
		back.setMinWidth(100);
		listNextVertex.setMinWidth(100);
		
		textPath.setEditable(false);
		textPath.setMinWidth(300);
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
		grid.add(new Label("Choose next vertex then click OK"), 3, 0);
		grid.add(listNextVertex, 3, 1);
		grid.add(btOk, 3, 2);
		grid.add(reset, 3, 3);
		grid.add(back, 3, 4);
		pathPane.getChildren().addAll(new Label("Path"), textPath);
		grid.add(pathPane, 0, 5, 4, 5);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(30, 30, 100, 30));
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

		// nut Find
		btFind.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// reset status graphPanel
				for (VertexNode<V> tmp : graphView.vertexNodes.values())
					tmp.setStyle("-fx-fill: #96d1cd");
				for (EdgeLine<E, V> tmp : graphView.edgeNodes.values()) {
					tmp.setStyle("-fx-stroke: #45597e");
					if (graphView.edgesWithArrows)
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
				collection[0]=startVertex;
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

				textPath.appendText(dataStart);
				currentVertex = startVertex;
				

				setChoiceBox(grid, graphView, currentVertex);
			}
		});
		
		// nut OK
		btOk.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					Vertex<V> inputVertex = graphView.theGraph.vertices.get(input);
					VertexNode<V> inputVertexNode = graphView.vertexNodes.get(inputVertex);
					Edge<E, V> inputEdge = graphView.theGraph.adjList.get(currentVertex).get(inputVertex);
					EdgeLine<E, V> inputEdgeNode = graphView.edgeNodes.get(inputEdge);
					// thay đổi màu đỉnh và cạnh
					inputVertexNode.setStyle("-fx-fill: yellow");
					inputEdgeNode.setStyle("-fx-stroke: blue");
					if (graphView.edgesWithArrows)
						inputEdgeNode.getAttachedArrow().setStyle("-fx-stroke: blue");
					
					textPath.appendText( "->" + input);
					currentVertex = inputVertex;
					countStep++;
					collection[countStep] = inputVertex;
				}
				catch(NullPointerException e) {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setContentText("Please choose vertex!");
					alert.showAndWait();
					return;
				}
				
				// dieu kien rang buoc ket thuc
				if (countStep > MAX_STEP) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("The path has reached its limit step!");
					alert.showAndWait();
					return;
				}
				if (currentVertex.equals(endVertex)) {
					//textPath.deleteText(textPath.getText().length() - 2, textPath.getText().length());
					graphView.vertexNodes.get(currentVertex).setStyle("-fx-fill: red");
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("Done!");
					alert.showAndWait();
					return;
				}
				// TODO Auto-generated method stub
				setChoiceBox(grid, graphView, currentVertex);
			}
		});
		
		// nut reset
		reset.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				for (VertexNode<V> tmp : graphView.vertexNodes.values())
					tmp.setStyle("-fx-fill: #96d1cd");
				for (EdgeLine<E, V> tmp : graphView.edgeNodes.values()) {
					tmp.setStyle("-fx-stroke: #45597e");
					if (graphView.edgesWithArrows)
						tmp.getAttachedArrow().setStyle(" -fx-stroke: #45597e");
				}
				countStep = 0;
				textPath.setText("");
				listNextVertex.getItems().clear();
			}

		});
		
		// nut back
		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (countStep >0) {
					currentVertex = collection[countStep-1];
					VertexNode<V> backVertexNode = graphView.vertexNodes.get(collection[countStep]);
					Edge<E, V> backEdge = graphView.theGraph.adjList.get(currentVertex).get(collection[countStep]);
					EdgeLine<E, V> backEdgeNode = graphView.edgeNodes.get(backEdge);

					// backtracking
					int lenVertex = collection[countStep].element().toString().length();
					textPath.deleteText(textPath.getText().length()-2-lenVertex,textPath.getText().length());
					backVertexNode.setStyle("-fx-fill: #96d1cd");
					backEdgeNode.setStyle(" -fx-stroke: #45597e");
					if (graphView.edgesWithArrows)
						backEdgeNode.getAttachedArrow().setStyle(" -fx-stroke: #45597e");
					currentVertex = collection[--countStep];
					
					setChoiceBox(grid, graphView, currentVertex);
				}
				else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("Cannot Back!");
					alert.showAndWait();
				}
			}

		});

	}
}

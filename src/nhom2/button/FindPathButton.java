package nhom2.button;

import java.util.Set;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import nhom2.graph.*;
import nhom2.graphview.*;
import nhom2.graphview.Edge.EdgeLine;
import nhom2.graphview.Edge.EdgeNode;
import nhom2.graphview.Vertex.VertexNode;

public class FindPathButton<V, E> extends Button {
	private final int LENGTH_LIST_VERTEX = 10;
	private final int MAX_STEP = 20;
	private Stage stage;
	protected Scene View;
	private Vertex<V> currentVertex;
	private Vertex<V> endVertex;
	private Vertex<V> collection[] = new Vertex[21];
	private int countStep = 0;
	private TextField textPath = new TextField("");
	private ListView<String> listNV = new ListView<String>();

	public void setListNextVertex(GraphPanel<V, E> graphView, Vertex<V> v) {
		// lay ra danh sach dinh ke, canh ke
		Map<Vertex<V>, Edge<E, V>> unknown = graphView.theGraph.adjList.get(v);
		Set<Vertex<V>> adjVertex = unknown.keySet();

		listNV.getItems().clear();
		int cntV = 0; // so dinh ke hien thi ra

		for (Vertex<V> iterator : adjVertex) {
			if(graphView.theGraph.isInAPath(endVertex).contains(iterator)) {
				String mi = new String(iterator.element().toString());
				listNV.getItems().add(mi);
				cntV++;
			}
			if (cntV >= LENGTH_LIST_VERTEX)
				break;
		}
		if (listNV.getItems().isEmpty())
			listNV.getItems().add("No adj vertex");

	}

	// constructor
	public FindPathButton(Stage s, GraphPanel<V, E> graphView) {

		// label start
		Label lbstart = new Label("Start");
		lbstart.setLayoutX(14.0);
		lbstart.setLayoutY(77.0);
		lbstart.setPrefWidth(30);

		// label end
		Label lbend = new Label("End");
		lbend.setLayoutX(14.0);
		lbend.setLayoutY(117.0);
		lbend.setPrefWidth(30);

		// label NextVertex
		Label lbnext = new Label("Next vertex");
		lbnext.setLayoutX(195.0);
		lbnext.setLayoutY(48.0);

		// TextField Start
		TextField tfStartVertex = new TextField();
		tfStartVertex.setPromptText("Dinh bat dau");
		tfStartVertex.setPrefWidth(85);
		tfStartVertex.setLayoutX(50.0);
		tfStartVertex.setLayoutY(72.0);

		// TextField End
		TextField tfEndVertex = new TextField();
		tfEndVertex.setPromptText("Dinh ket thuc");
		tfEndVertex.setPrefWidth(85);
		tfEndVertex.setLayoutX(50.0);
		tfEndVertex.setLayoutY(112.0);

		// nut Find
		Button btFind = new Button("Find");
		btFind.setPrefWidth(85.0);
		btFind.setLayoutX(50.0);
		btFind.setLayoutY(152.0);

		// nut Next
		Button btNext = new Button("Next");
		btNext.setLayoutX(310.0);
		btNext.setLayoutY(72.0);
		btNext.setPrefWidth(74.0);

		// nut reset
		Button reset = new Button("Reset");
		reset.setLayoutX(310.0);
		reset.setLayoutY(152);
		reset.setPrefWidth(74.0);

		// nut back
		Button back = new Button("Back");
		back.setLayoutX(310.0);
		back.setLayoutY(112.0);
		back.setPrefWidth(74.0);

		// Path
		HBox pathPane = new HBox();
		pathPane.setSpacing(20);
		pathPane.getChildren().addAll(new Label("Path"), textPath);
		pathPane.setAlignment(Pos.CENTER_LEFT);
		textPath.setEditable(false);
		textPath.setMinWidth(300);
		
//		grid.setAlignment(Pos.CENTER_LEFT);
//		grid.add(new Label("Start"), 0, 0);
//		grid.add(tfStartVertex, 1, 0);
//		grid.add(new Label("End"), 0, 1);
//		grid.add(tfEndVertex, 1, 1);
//		grid.add(btFind, 1, 2);
//		grid.add(new Label("Next vertex"), 3, 0);
//		grid.add(listNV, 3, 1, 3, 4);
//		grid.add(btOk, 4, 1);
//		grid.add(reset, 4, 2);
//		grid.add(back, 4, 3);
//		grid.add(pathPane, 0, 5, 4, 5);
//		grid.setHgap(30);
//		grid.setVgap(10);
//		grid.setPadding(new Insets(30, 30, 100, 30));
//		grid.setMinSize(350, 250);
		
		pathPane.setLayoutX(36.0);
		pathPane.setLayoutY(220.0);

		// danh sach dinh ke
		listNV.setMaxSize(85, 105);
		listNV.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		listNV.setLayoutX(180.0);
		listNV.setLayoutY(72.0);

		// Pane
		Pane pane = new Pane();
		pane.setPrefHeight(400.0);
		pane.setPrefWidth(400.0);
		pane.setPadding(new Insets(50,50,50,50));
		pane.getChildren().addAll(lbstart, lbend, tfStartVertex, tfEndVertex, btFind, 
									listNV, btNext, reset, back,
									pathPane, lbnext);

//>>>>>>> 03fc62d6096351f593db189f814a5f2128e132b6
		try {
			Parent root = pane;
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

				if (startVertex == null || endVertex == null) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText("Đỉnh không tồn tại trong đồ thị");
					alert.show();
					return;
				}

				collection[0] = startVertex;
				// chuyển màu riêng 2 đỉnh start và end
				VertexNode<V> startVertexNode = graphView.vertexNodes.get(startVertex);
				VertexNode<V> endVertexNode = graphView.vertexNodes.get(endVertex);
				startVertexNode.setStyle("-fx-fill: red");
				endVertexNode.setStyle("-fx-fill: red");

				textPath.appendText(dataStart);
				currentVertex = startVertex;

				setListNextVertex(graphView, currentVertex);
			}
		});

		// nut OK
		btNext.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					String input = listNV.getSelectionModel().getSelectedItem();
					Vertex<V> inputVertex = graphView.theGraph.vertices.get(input);
					VertexNode<V> inputVertexNode = graphView.vertexNodes.get(inputVertex);
					Edge<E, V> inputEdge = graphView.theGraph.adjList.get(currentVertex).get(inputVertex);
					EdgeLine<E, V> inputEdgeNode = graphView.edgeNodes.get(inputEdge);
					// thay đổi màu đỉnh và cạnh
					inputVertexNode.setStyle("-fx-fill: yellow");
					inputEdgeNode.setStyle("-fx-stroke: blue");
					if (graphView.edgesWithArrows)
						inputEdgeNode.getAttachedArrow().setStyle("-fx-stroke: blue");

					textPath.appendText("->" + input);
					currentVertex = inputVertex;
					countStep++;
					collection[countStep] = inputVertex;
				} catch (NullPointerException e) {
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
					// textPath.deleteText(textPath.getText().length() - 2,
					// textPath.getText().length());
					graphView.vertexNodes.get(currentVertex).setStyle("-fx-fill: red");
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("Done!");
					alert.showAndWait();
					return;
				}
				// TODO Auto-generated method stub
				setListNextVertex(graphView, currentVertex);
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
				listNV.getItems().clear();
			}

		});

		// nut back
		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (countStep > 0) {
					currentVertex = collection[countStep - 1];
					VertexNode<V> backVertexNode = graphView.vertexNodes.get(collection[countStep]);
					Edge<E, V> backEdge = graphView.theGraph.adjList.get(currentVertex).get(collection[countStep]);
					EdgeLine<E, V> backEdgeNode = graphView.edgeNodes.get(backEdge);

					// backtracking
					int lenVertex = collection[countStep].element().toString().length();
					textPath.deleteText(textPath.getText().length() - 2 - lenVertex, textPath.getText().length());
					backVertexNode.setStyle("-fx-fill: #96d1cd");
					backEdgeNode.setStyle(" -fx-stroke: #45597e");
					if (graphView.edgesWithArrows)
						backEdgeNode.getAttachedArrow().setStyle(" -fx-stroke: #45597e");
					currentVertex = collection[--countStep];

					setListNextVertex(graphView, currentVertex);
				} else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("Cannot Back!");
					alert.showAndWait();
				}
			}

		});

	}

}

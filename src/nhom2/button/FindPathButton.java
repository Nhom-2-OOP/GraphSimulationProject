
package nhom2.button;

import java.util.Set;
import java.util.Stack;
import java.util.HashMap;
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
import javafx.scene.control.CheckBox;
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
	private Vertex<V> endVertex, startVertex;
	private Stack<Vertex<V>> traceVertex = new Stack<>();
	private Stack<Edge<E,V>> traceEdge = new Stack<>();
	private int countStep = 0;
	private TextField textPath = new TextField("");
	private ListView<String> listNV = new ListView<String>();
	private CheckBox checkbox = new CheckBox("Smart Vertex");
	public void setListNextVertex(GraphPanel<V, E> graphView, Vertex<V> v) {
		// lay ra danh sach dinh ke, canh ke
		Map<Vertex<V>, Edge<E, V>> unknown = graphView.theGraph.adjList.get(v);
		Set<Vertex<V>> adjVertex = unknown.keySet();

		listNV.getItems().clear();
		int cntV = 0; // so dinh ke hien thi ra

		for (Vertex<V> iterator : adjVertex) {
			if(checkbox.isSelected())
				if(!graphView.theGraph.isInAPath(endVertex).contains(iterator) ) 
					continue;
			String mi = new String(iterator.element().toString());
			listNV.getItems().add(mi);
			cntV++;
			
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
		pathPane.setSpacing(10);
		pathPane.getChildren().addAll(new Label("Path"), textPath);
		pathPane.setAlignment(Pos.CENTER_LEFT);
		textPath.setEditable(false);
		textPath.setMinWidth(300);

		pathPane.setLayoutX(14.0);
		pathPane.setLayoutY(220.0);
		
		// check box
		checkbox.setLayoutX(180.0);
		checkbox.setLayoutY(180.0);
		
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
									pathPane, lbnext, checkbox);

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
				stage.setTitle("Lựa chọn đường đi");
				stage.show();
			}
		});
		
		this.setOnMouseEntered(mouseEvent -> {
			this.getStyleClass().add("Col1ChooseButtonEntered");		
		});
		this.setOnMouseExited(mouseEvent -> {
			this.getStyleClass().remove(2);
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
				startVertex = graphView.theGraph.vertices.get(dataStart);
				endVertex = graphView.theGraph.vertices.get(dataEnd);

				if (startVertex == null || endVertex == null) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setHeaderText("Đỉnh không tồn tại trong đồ thị");
					alert.show();
					return;
				}

				traceVertex.push(startVertex);
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

		// nut Next
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
					// the coloring of both start and end vertex is permanent
					if(inputVertex != endVertex && inputVertex != startVertex) {
						inputVertexNode.setStyle("-fx-fill: lime");
						
					}
					if(traceVertex.peek() != startVertex)
						graphView.vertexNodes.get(traceVertex.peek()).setStyle("-fx-fill : yellow");
					inputEdgeNode.setStyle("-fx-stroke: blue");
					if (graphView.edgesWithArrows)
						inputEdgeNode.getAttachedArrow().setStyle("-fx-stroke: blue");
					
						
					textPath.appendText("->" + input);
					currentVertex = inputVertex;
					countStep++;
					traceVertex.push(inputVertex);
					traceEdge.push(inputEdge);
				} catch (NullPointerException e) {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setContentText("Please choose vertex!");
					alert.showAndWait();
					return;
				}

				// dieu kien rang buoc ket thuc
				if (countStep > MAX_STEP) {
					Alert alert = new Alert(AlertType.WARNING);
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
				traceEdge.clear();
				traceVertex.clear();
			}

		});

		// nut back
		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (countStep > 0) {
					countStep--;
					Vertex<V> garbageVertex = traceVertex.pop();
					currentVertex = traceVertex.peek();
					
					// highlight đỉnh hiện tại nếu khác đỉnh gốc
					if(currentVertex != startVertex) graphView.vertexNodes.get(currentVertex).setStyle("-fx-fill : lime");
					
					VertexNode<V> backVertexNode = graphView.vertexNodes.get(garbageVertex);
					Edge<E, V> backEdge = traceEdge.pop();
					EdgeLine<E, V> backEdgeNode = graphView.edgeNodes.get(backEdge);
					
					// backtracking
					
					// get back TextField textPath
					int lenVertex = garbageVertex.element().toString().length();
					textPath.deleteText(textPath.getText().length() - 2 - lenVertex, textPath.getText().length());
					
					// the coloring of both start and end vertex is permanent
					if(garbageVertex != endVertex && garbageVertex != startVertex) 
						if(!traceVertex.contains(garbageVertex))
							backVertexNode.setStyle("-fx-fill: #96d1cd");
						else
							backVertexNode.setStyle("-fx-fill: yellow");
					
					if(!traceEdge.contains(backEdge)) {
						backEdgeNode.setStyle(" -fx-stroke: #45597e");
						if (graphView.edgesWithArrows)
							backEdgeNode.getAttachedArrow().setStyle(" -fx-stroke: #45597e");
					}
					
					setListNextVertex(graphView, currentVertex);
				} 
				else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setContentText("Cannot Back!");
					alert.showAndWait();
				}
			}

		});

	}
}


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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import nhom2.graph.*;
import nhom2.graphview.*;
import nhom2.graphview.Edge.EdgeNode;
import nhom2.graphview.Vertex.VertexNode;

public class FindPathButton<V, E> extends Button{
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
			String mi = new String(iterator.element().toString());
			listNV.getItems().add(mi);
			cntV++;
			if (cntV >= LENGTH_LIST_VERTEX)
				break;
		}
		if(listNV.getItems().isEmpty()) listNV.getItems().add("No adj vertex");

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
		
		tfStartVertex.setPromptText("Dinh bat dau");
		tfStartVertex.setPrefWidth(80);
		tfEndVertex.setPromptText("Dinh ket thuc");
		tfEndVertex.setPrefWidth(80);
		pathPane.setSpacing(20);
		pathPane.getChildren().addAll(new Label("Path"), textPath);
		pathPane.setAlignment(Pos.CENTER_LEFT);
		listNV.setMaxSize(80, 100);
		listNV.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		btFind.setMinWidth(80);
		btOk.setMinWidth(80);
		reset.setMinWidth(80);
		back.setMinWidth(80);
		textPath.setEditable(false);
		textPath.setMinWidth(300);
		
		
		grid.setAlignment(Pos.CENTER_LEFT);
		grid.add(new Label("Start"), 0, 0);
		grid.add(tfStartVertex, 1, 0);
		grid.add(new Label("End"), 0, 1);
		grid.add(tfEndVertex, 1, 1);
		grid.add(btFind, 1, 2);
		grid.add(new Label("Next vertex"), 3, 0);
		grid.add(listNV, 3, 1, 3, 4);
		grid.add(btOk, 4, 1);
		grid.add(reset, 4, 2);
		grid.add(back, 4, 3);
		grid.add(pathPane, 0, 5, 4, 5);
		grid.setHgap(30);
		grid.setVgap(10);
		grid.setPadding(new Insets(30, 30, 100, 30));
		grid.setMinSize(350, 250);

		
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
				for (EdgeNode<E, V> tmp : graphView.edgeNodes.values()) {
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
				
				collection[0]=startVertex;
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
		btOk.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					String input = listNV.getSelectionModel().getSelectedItem();
					Vertex<V> inputVertex = graphView.theGraph.vertices.get(input);
					VertexNode<V> inputVertexNode = graphView.vertexNodes.get(inputVertex);
					Edge<E, V> inputEdge = graphView.theGraph.adjList.get(currentVertex).get(inputVertex);
					EdgeNode<E, V> inputEdgeNode = graphView.edgeNodes.get(inputEdge);
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
				for (EdgeNode<E, V> tmp : graphView.edgeNodes.values()) {
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
				if (countStep >0) {
					currentVertex = collection[countStep-1];
					VertexNode<V> backVertexNode = graphView.vertexNodes.get(collection[countStep]);
					Edge<E, V> backEdge = graphView.theGraph.adjList.get(currentVertex).get(collection[countStep]);
					EdgeNode<E, V> backEdgeNode = graphView.edgeNodes.get(backEdge);

					// backtracking
					int lenVertex = collection[countStep].element().toString().length();
					textPath.deleteText(textPath.getText().length()-2-lenVertex,textPath.getText().length());
					backVertexNode.setStyle("-fx-fill: #96d1cd");
					backEdgeNode.setStyle(" -fx-stroke: #45597e");
					if (graphView.edgesWithArrows)
						backEdgeNode.getAttachedArrow().setStyle(" -fx-stroke: #45597e");
					currentVertex = collection[--countStep];
					
					setListNextVertex(graphView, currentVertex);
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

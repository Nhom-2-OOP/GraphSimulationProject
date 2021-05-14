package nhom2.button.findpath;

import java.util.Map;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import nhom2.graph.Edge;
import nhom2.graph.Vertex;
import nhom2.graphview.GraphPanel;
import nhom2.graphview.Edge.EdgeNode;
import nhom2.graphview.Vertex.VertexNode;
import nhom2.window.Main;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class findpathController<V, E> {
	private final int LENGTH_LIST_VERTEX = 5;
	private final int MAX_STEP = 20;
	private Vertex<String> currentVertex;
	private Vertex<String> endVertex;
	private Vertex<String> collection[] = new Vertex[21];
	private int countStep = 0;
	private String input;


	public void setChoiceBox(GraphPanel<String, String> graphView, Vertex<String> currentVertex2) {
		// lay ra danh sach dinh ke, canh ke
		Map<Vertex<String>, Edge<String, String>> unknown = graphView.theGraph.adjList.get(currentVertex2);
		Set<Vertex<String>> adjVertex = unknown.keySet();

		listNextVertex.getItems().clear();
		int cntV = 0; // so dinh ke hien thi ra

		for (Vertex<String> iterator : adjVertex) {
			String mi = new String(iterator.element().toString());
			listNextVertex.getItems().add(mi);
			cntV++;
			if (cntV >= LENGTH_LIST_VERTEX)
				break;
		}

	}

	@FXML
	private Button btFind;
	@FXML
	private Button btOk;
	@FXML
	private Button reset;
	@FXML
	private Button back;
	@FXML
	private TextField textPath;
	@FXML
	private TextField tfStartVertex;
	@FXML
	private TextField tfEndVertex;
	@FXML
	private ChoiceBox<String> listNextVertex;

	@FXML
	private void find(ActionEvent e) {
		for (VertexNode<String> tmp : Main.graphView.vertexNodes.values())
			tmp.setStyle("-fx-fill: #96d1cd");
		for (EdgeNode<String, String> tmp : Main.graphView.edgeNodes.values()) {
			tmp.setStyle("-fx-stroke: #45597e");
			if (Main.graphView.edgesWithArrows)
				tmp.getAttachedArrow().setStyle(" -fx-stroke: #45597e");
		}
		textPath.clear();
		tfStartVertex.commitValue();
		String dataStart = tfStartVertex.getText();
		tfEndVertex.commitValue();
		String dataEnd = tfEndVertex.getText();
		// thuat toan
		Vertex<String> startVertex = Main.graphView.theGraph.vertices.get(dataStart);
		endVertex = Main.graphView.theGraph.vertices.get(dataEnd);
		collection[0] = startVertex;
		// chuyển màu riêng 2 đỉnh start và end
		VertexNode<String> startVertexNode = Main.graphView.vertexNodes.get(startVertex);
		VertexNode<String> endVertexNode =Main.graphView.vertexNodes.get(endVertex);
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
		

		setChoiceBox(Main.graphView, currentVertex);
	}	
	@FXML
	private void ok(ActionEvent e) {
	try {
		input = listNextVertex.getValue();
		Vertex<String> inputVertex = Main.graphView.theGraph.vertices.get(input);
		VertexNode<String> inputVertexNode = Main.graphView.vertexNodes.get(inputVertex);
		Edge<String, String> inputEdge = Main.graphView.theGraph.adjList.get(currentVertex).get(inputVertex);
		EdgeNode<String, String> inputEdgeNode = Main.graphView.edgeNodes.get(inputEdge);
		// thay đổi màu đỉnh và cạnh
		inputVertexNode.setStyle("-fx-fill: yellow");
		inputEdgeNode.setStyle("-fx-stroke: blue");
		if (Main.graphView.edgesWithArrows)
			inputEdgeNode.getAttachedArrow().setStyle("-fx-stroke: blue");
		
		textPath.appendText( "->" + input);
		currentVertex = inputVertex;
		countStep++;
		collection[countStep] = inputVertex;
	}
	catch(NullPointerException x) {
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
		Main.graphView.vertexNodes.get(currentVertex).setStyle("-fx-fill: red");
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText("Done!");
		alert.showAndWait();
		return;
	}
	// TODO Auto-generated method stub
	setChoiceBox(Main.graphView, currentVertex);
}
	@FXML
	private void reset(ActionEvent e) {
		// TODO Auto-generated method stub
		for (VertexNode<String> tmp : Main.graphView.vertexNodes.values())
			tmp.setStyle("-fx-fill: #96d1cd");
		for (EdgeNode<String, String> tmp : Main.graphView.edgeNodes.values()) {
			tmp.setStyle("-fx-stroke: #45597e");
			if (Main.graphView.edgesWithArrows)
				tmp.getAttachedArrow().setStyle(" -fx-stroke: #45597e");
		}
		countStep = 0;
		textPath.setText("");
		listNextVertex.getItems().clear();
	}
	@FXML
	private void back(ActionEvent e) {
			// TODO Auto-generated method stub
		if (countStep >0) {
			currentVertex = collection[countStep-1];
			VertexNode<String> backVertexNode = Main.graphView.vertexNodes.get(collection[countStep]);
			Edge<String, String> backEdge = Main.graphView.theGraph.adjList.get(currentVertex).get(collection[countStep]);
			EdgeNode<String, String> backEdgeNode = Main.graphView.edgeNodes.get(backEdge);

			// backtracking
			int lenVertex = collection[countStep].element().toString().length();
			textPath.deleteText(textPath.getText().length()-2-lenVertex,textPath.getText().length());
			backVertexNode.setStyle("-fx-fill: #96d1cd");
			backEdgeNode.setStyle(" -fx-stroke: #45597e");
			if (Main.graphView.edgesWithArrows)
				backEdgeNode.getAttachedArrow().setStyle(" -fx-stroke: #45597e");
			currentVertex = collection[--countStep];
			
			setChoiceBox(Main.graphView, currentVertex);
		}
		else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("Cannot Back!");
			alert.showAndWait();
		}
	}
	
	
	}

	
	


package nhom2.button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import nhom2.graph.*;
import nhom2.graphview.*;
import nhom2.graphview.Edge.EdgeLine;
import nhom2.graphview.Edge.EdgeNode;
import nhom2.graphview.Vertex.VertexNode;

public class BFSButton<V, E> extends Button{
	private Stage stage;
	protected Scene View;
	
	private GraphPanel<V,E> GraphView;
	
	private Map<Vertex<V>, Vertex<V>> parVertex = new HashMap<>();
	private Map<Vertex<V>,Integer> IsVisited = new HashMap();
	private ArrayList<Vertex<V>> VisitingOrder = new ArrayList<>();
	private Vertex<V> curVertex;
	
	private Node gridBack;
	
	public Node getNodeBack() {
		return gridBack;
	}
	public void setNodeBack(Node nodeBack) {
		this.gridBack = nodeBack;
	}
	
	private void BFS(Vertex start) {
//		IsVisited.put(v, new Integer(1));
//		VisitingOrder.add(v);
//
//		Set<Vertex<V>> adjVertex = GraphView.theGraph.adjList.get(v).keySet();
//		
//		for (Vertex<V> u: adjVertex) {
//			if (IsVisited.get(u).intValue() == 0) {
//				FS(u);
//				parVertex.put(u, v);
//			}
//		}
		Queue<Vertex<V>> queue = new LinkedList<Vertex<V>>();
		queue.add(start);
		IsVisited.put(start, new Integer(1));
		VisitingOrder.add(start);
		while (!queue.isEmpty()) {
			Vertex v = queue.poll();
			Set<Vertex<V>> adjVertex = GraphView.theGraph.adjList.get(v).keySet();
			for (Vertex<V> u: adjVertex) {
				if (IsVisited.get(u).intValue() == 0) {
					queue.add(u);
					IsVisited.put(u, new Integer(1));
					VisitingOrder.add(u);
					parVertex.put(u, v);
				}
			}
		}
	}
	
	public BFSButton(GridPane root, GraphPanel<V, E> graphView) {
		this.GraphView = graphView;
		
		GridPane grid = new GridPane();
		Label lbStartVertex = new Label("Start Vertex:");
		TextField tfStartVertex = new TextField();
		Button finish = new Button("Hiển thị kết quả");
		Button step = new Button("Hiển thị từng bước");
		Button next = new Button("Next");
		Button reset = new Button("Reset");
		Label lb = new Label();
		BackButton backBut = new BackButton(root);
		
		EventHandler<ActionEvent> current = backBut.getOnAction();
		
		backBut.setOnAction(e -> {
	        current.handle(e);
	        for (VertexNode<V> tmp : graphView.vertexNodes.values())
				tmp.setStyle("-fx-fill: #96d1cd");
			for (EdgeLine<E, V> tmp : graphView.edgeNodes.values()) {
				tmp.setStyle("-fx-stroke: #45597e");
				if(graphView.theGraph.isDirected==true) tmp.getAttachedArrow().setStyle(" -fx-stroke: #45597e");
			}
	    });

		
		tfStartVertex.setPromptText("Nhập đỉnh bắt đầu");
		tfStartVertex.setPrefWidth(85);
		tfStartVertex.setMaxWidth(85);
		next.setVisible(false);
		reset.setVisible(false);
		
		grid.add(backBut, 0, 0);
		grid.add(lbStartVertex, 0, 1);
		grid.add(tfStartVertex, 0, 2);
		grid.add(finish, 0, 3);
		grid.add(step, 0, 4);
		grid.add(next, 0, 5);
		grid.add(reset, 0, 6);
		grid.add(lb, 0, 7);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setMinSize(500, 200);
		
		this.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				for (VertexNode<V> tmp : graphView.vertexNodes.values())
					tmp.setStyle("-fx-fill: #96d1cd");
				for (EdgeLine<E, V> tmp : graphView.edgeNodes.values()) {
					tmp.setStyle("-fx-stroke: #45597e");
					if(graphView.theGraph.isDirected==true) tmp.getAttachedArrow().setStyle(" -fx-stroke: #45597e");
				}
				root.getChildren().remove(2);
				root.add(grid, 1, 0);
				backBut.setGridBack(gridBack);
				}
		});
		
		this.setOnMouseEntered(mouseEvent -> {
			this.getStyleClass().add("Col1ChooseButtonEntered");		
		});
		this.setOnMouseExited(mouseEvent -> {
			this.getStyleClass().remove(2);
		});
		
		
		finish.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				lb.setText("");
				next.setVisible(false);
				for (VertexNode<V> tmp : graphView.vertexNodes.values())
					tmp.setStyle("-fx-fill: #96d1cd");
				for (EdgeLine<E, V> tmp : graphView.edgeNodes.values()) {
					tmp.setStyle("-fx-stroke: #45597e");
					if(graphView.theGraph.isDirected==true) tmp.getAttachedArrow().setStyle(" -fx-stroke: #45597e");
				}
				parVertex.clear();
				IsVisited.clear();
				VisitingOrder.clear();
				reset.setVisible(false);
				tfStartVertex.commitValue();
				String dataStart = tfStartVertex.getText();
				Vertex<V> startVertex = graphView.theGraph.vertices.get(dataStart);
				if (startVertex == null) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setHeaderText("Đỉnh không tồn tại trong đồ thị");
					alert.show();
					return;
				}
				
				for (Vertex<V> v: graphView.theGraph.vertices.values()) IsVisited.put(v, new Integer(0));
				parVertex.put(startVertex, startVertex);
				BFS(startVertex);
				
				for (Vertex<V> v: parVertex.keySet()) {
					VertexNode VertexView = (VertexNode)GraphView.vertexNodes.get(v);
					VertexView.setStyle("-fx-fill: red");
					if (v != startVertex) {
						EdgeLine EdgeView = (EdgeLine)GraphView.edgeNodes.get(GraphView.theGraph.getEdge(parVertex.get(v), v));
//						System.out.print(parVertex.get(v).element() + " ");
//						System.out.println(v.element());
						EdgeView.setStyle("-fx-stroke: blue");
						if(GraphView.theGraph.isDirected == true) EdgeView.getAttachedArrow().setStyle("-fx-stroke: blue");
					}
				}
				
				reset.setVisible(true);
			}
		});
		
		//button hiển thị từng bước
		step.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				lb.setText("");
				next.setVisible(false);
				for (VertexNode<V> tmp : graphView.vertexNodes.values())
					tmp.setStyle("-fx-fill: #96d1cd");
				for (EdgeLine<E, V> tmp : graphView.edgeNodes.values()) {
					tmp.setStyle("-fx-stroke: #45597e");
					if(graphView.theGraph.isDirected==true) tmp.getAttachedArrow().setStyle(" -fx-stroke: #45597e");
				}
				reset.setVisible(false);
				
				tfStartVertex.commitValue();
				String dataStart = tfStartVertex.getText();
				Vertex<V> startVertex = graphView.theGraph.vertices.get(dataStart);
				if (startVertex == null) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setHeaderText("Đỉnh không tồn tại trong đồ thị");
					alert.show();
					return;
				}
				reset.setVisible(true);
				next.setVisible(true);
				graphView.vertexNodes.get(startVertex).setStyle("-fx-fill: red");
				
				parVertex.clear();
				IsVisited.clear();
				VisitingOrder.clear();
				
				for (Vertex<V> v: graphView.theGraph.vertices.values()) IsVisited.put(v, new Integer(0));
				parVertex.put(startVertex, startVertex);
				BFS(startVertex);
				
				if (VisitingOrder.size() == 1){
					lb.setText("Done!");
					next.setVisible(false);
					return;
				}
				
				curVertex = startVertex;
			}
			
		});

		next.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				VisitingOrder.remove(0);
				VertexNode VertexView = (VertexNode)GraphView.vertexNodes.get(VisitingOrder.get(0));
				VertexView.setStyle("-fx-fill: red");
				EdgeLine EdgeView = (EdgeLine)GraphView.edgeNodes.get(GraphView.theGraph.getEdge(parVertex.get(VisitingOrder.get(0)), VisitingOrder.get(0)));
				EdgeView.setStyle("-fx-stroke: blue");
				if(GraphView.theGraph.isDirected == true) EdgeView.getAttachedArrow().setStyle("-fx-stroke: blue");
				if (VisitingOrder.size() == 1) {
					lb.setText("Done!");
					next.setVisible(false);
					return;
				}
			}
			
		});
		
		reset.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				lb.setText("");
				next.setVisible(false);
				for (VertexNode<V> tmp : graphView.vertexNodes.values())
					tmp.setStyle("-fx-fill: #96d1cd");
				for (EdgeLine<E, V> tmp : graphView.edgeNodes.values()) {
					tmp.setStyle("-fx-stroke: #45597e");
					if(graphView.theGraph.isDirected==true) tmp.getAttachedArrow().setStyle(" -fx-stroke: #45597e");
				}
				reset.setVisible(false);
			}
			
		});
	}
	
}
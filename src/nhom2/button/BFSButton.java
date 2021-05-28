package nhom2.button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import nhom2.graph.*;
import nhom2.graphview.*;
import nhom2.graphview.Edge.EdgeLine;
import nhom2.graphview.Vertex.VertexNode;

public class BFSButton<V, E> extends Button{
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
		
		BackButton backBut = new BackButton(root);
		
		GridPane gridChild = new GridPane();
		Label lbStartVertex = new Label("Start Vertex:");
		TextField tfStartVertex = new TextField();
		Button finish = new Button("Hiển thị kết quả");
		Button step = new Button("Hiển thị từng bước");
		Button next = new Button("Next");
		Label lb = new Label();
		
		tfStartVertex.setPromptText("Nhập đỉnh bắt đầu");
		
		next.setVisible(false);
		Button reset = new Button("Reset");
		reset.setVisible(false);
			
		gridChild.setVgap(30);

		
		gridChild.add(lbStartVertex, 0, 0);
		gridChild.add(tfStartVertex, 0, 1);
		gridChild.add(finish, 0, 2);
		gridChild.add(step, 0, 3);
		HBox nexResBox = new HBox(60);
		nexResBox.getChildren().addAll(next, reset);
		gridChild.add(lb, 0, 4);
		gridChild.add(nexResBox, 0, 4);
		
		grid.add(backBut, 0, 0);
		grid.add(gridChild, 0, 1);
	
		gridChild.setPadding(new Insets(30, 10, 0, 10));
		gridChild.setHalignment(tfStartVertex, HPos.RIGHT);

		lbStartVertex.getStyleClass().add("lbStartVerTexFS");
		tfStartVertex.getStyleClass().add("tfStartVertexFS");
		finish.getStyleClass().add("FSFinishStep");
		step.getStyleClass().add("FSFinishStep");
		next.getStyleClass().add("FSNextReset");
		reset.getStyleClass().add("FSNextReset");
		nexResBox.getStyleClass().add("nexResBox");
		lb.getStyleClass().add("FSlb");
		
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
					Alert alert = new Alert(AlertType.WARNING);
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
					Alert alert = new Alert(AlertType.WARNING);
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
					lb.setVisible(true);
					next.setVisible(false);
					return;
				}
			}
			
		});
		
		reset.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				lb.setText("");
				lb.setVisible(false);
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
		
		finish.setOnMouseEntered(mouseEvent -> {
			finish.getStyleClass().add("FSFinishStepEntered");
		});
		finish.setOnMouseExited(mouseEvent -> {
			finish.getStyleClass().remove(2);
		});
		step.setOnMouseEntered(mouseEvent -> {
			step.getStyleClass().add("FSFinishStepEntered");
		});
		step.setOnMouseExited(mouseEvent -> {
			step.getStyleClass().remove(2);
		});
		
		
		next.setOnMouseEntered(mouseEvent -> {
			next.getStyleClass().add("FSNextResetEntered");
		});
		next.setOnMouseExited(mouseEvent -> {
			next.getStyleClass().remove(2);
		});
		reset.setOnMouseEntered(mouseEvent -> {
			reset.getStyleClass().add("FSNextResetEntered");
		});
		reset.setOnMouseExited(mouseEvent -> {
			reset.getStyleClass().remove(2);
		});
		
	}
	
}
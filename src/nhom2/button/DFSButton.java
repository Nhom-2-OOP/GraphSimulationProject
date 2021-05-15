package nhom2.button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import nhom2.graph.*;
import nhom2.graphview.*;
import nhom2.graphview.Edge.EdgeLine;
import nhom2.graphview.Edge.EdgeNode;
import nhom2.graphview.Vertex.VertexNode;

public class DFSButton<V, E> extends Button{
	private Stage stage;
	protected Scene View;
	Map<Vertex<V>,Integer> mark = new HashMap();
	
	private void DFSUtil(Vertex<V> currVertex, GraphPanel<V, E> graphView, Map<Vertex<V>,Integer> mark) {
		Stack<Vertex<V>> stack = new Stack<>();
		Map<Vertex<V>, Vertex<V>> preVertex = new HashMap();
		stack.push(currVertex);
		currVertex = stack.peek();
		stack.pop();
		Vertex<V> tmpstart = currVertex;
		VertexNode<V> startVertexNode = graphView.vertexNodes.get(currVertex);
		startVertexNode.setStyle("-fx-fill: red");
		Map<Vertex<V>, Edge<E, V>> tempstart = graphView.theGraph.adjList.get(currVertex);
		Set<Vertex<V>> adjStartVertex = tempstart.keySet();
		Iterator iteratorStart = adjStartVertex.iterator();
		while(iteratorStart.hasNext()) {
			currVertex = (Vertex<V>) iteratorStart.next();
			if(mark.get(currVertex).intValue()==0) {
				preVertex.put(currVertex, tmpstart);
				stack.push(currVertex);
			}
		}

		while(stack.empty() == false) {
			currVertex = stack.peek();
			stack.pop();
			Vertex<V> tmpcurr = currVertex;
			//thay đổi màu của đỉnh và cạnh
			if(mark.get(currVertex).intValue()==0) {
				VertexNode<V> currVertexNode = graphView.vertexNodes.get(currVertex);
				currVertexNode.setStyle("-fx-fill: red");
				Edge<E,V> edge = graphView.theGraph.adjList.get(preVertex.get(currVertex)).get(currVertex);
				EdgeLine<E,V> edgeNode = graphView.edgeNodes.get(edge);
				edgeNode.setStyle("-fx-stroke: blue");
				if(graphView.theGraph.isDirected==true)
					edgeNode.getAttachedArrow().setStyle("-fx-stroke: blue");
				mark.put(currVertex, 1);
			}
			
			//push đỉnh kề của đỉnh vừa tô màu và stack
			Map<Vertex<V>, Edge<E, V>> temp = graphView.theGraph.adjList.get(currVertex);
			Set<Vertex<V>> adjVertex = temp.keySet();
			Iterator iterator = adjVertex.iterator();
			while(iterator.hasNext()) {
				currVertex = (Vertex<V>) iterator.next();
				if(mark.get(currVertex).intValue()==0) {
					preVertex.put(currVertex, tmpcurr);
					stack.push(currVertex);
				}
			}
		}
	}
	private void DFS(Vertex<V> startVertex, GraphPanel<V, E> graphView) {
		Map<Vertex<V>, VertexNode<V>> tmp = graphView.vertexNodes;
		Set<Vertex<V>> vertex = tmp.keySet();
		for(Vertex<V> iterator : vertex)
			mark.put(iterator, 0);
		Vertex<V> currVertex = startVertex;
		DFSUtil(currVertex,graphView,mark);
	}
	
	//phần hiển thị từng bước
	private Map<Vertex<V>, Vertex<V>> preVertexStep = new HashMap();//
	private Stack<Vertex<V>> stackStep = new Stack<>();
	private Vertex<V> tmpnext;
	private void DFSstep(Vertex<V> startVertex, GraphPanel<V, E> graphView) {
		Vertex<V> currVertex = startVertex;
		Vertex<V> tmpcurr = currVertex;
		mark.put(currVertex, 1);
		Map<Vertex<V>, Edge<E, V>> temp = graphView.theGraph.adjList.get(currVertex);
		Set<Vertex<V>> adjVertex = temp.keySet();
		Iterator iterator = adjVertex.iterator();
		while(iterator.hasNext()) {
			currVertex = (Vertex<V>) iterator.next();
			if(mark.get(currVertex).intValue()==0) {
				preVertexStep.put(currVertex, tmpcurr);
				stackStep.push(currVertex);
			}			
		}

		currVertex = stackStep.peek();
		VertexNode<V> currVertexNode = graphView.vertexNodes.get(currVertex);
		currVertexNode.setStyle("-fx-fill: red");
		Edge<E,V> edge = graphView.theGraph.adjList.get(preVertexStep.get(currVertex)).get(currVertex);//
		EdgeLine<E,V> edgeNode = graphView.edgeNodes.get(edge);
		edgeNode.setStyle("-fx-stroke: blue");
		if(graphView.theGraph.isDirected==true)
			edgeNode.getAttachedArrow().setStyle("-fx-stroke: blue");
		tmpnext = currVertex;
		stackStep.pop();
	}
	
	//DFS button
	public DFSButton(Stage stg, GraphPanel<V, E> graphView) {
		GridPane grid = new GridPane();
		Label lbStartVertex = new Label("Start Vertex:");
		TextField tfStartVertex = new TextField();
		Button finish = new Button("Hiển thị kết quả");
		Button step = new Button("Hiển thị từng bước");
		Button next = new Button("Next");
		Button reset = new Button("Reset");
		Label lb = new Label();

		
		tfStartVertex.setPromptText("Nhập đỉnh bắt đầu");
		tfStartVertex.setPrefWidth(85);
		tfStartVertex.setMaxWidth(85);
		next.setVisible(false);
		reset.setVisible(false);
		
		grid.add(lbStartVertex, 0, 0);
		grid.add(tfStartVertex, 1, 0);
		grid.add(finish, 1, 1);
		grid.add(step, 1, 2);
		grid.add(next, 2, 2);
		grid.add(reset, 1, 3);
		grid.add(lb, 3, 2);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 100, 10));
		grid.setMinSize(500, 200);
		
		try {
			Parent root = grid;
			this.View = new Scene(root);
			this.stage = stg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				stage.setScene(View);
				stage.show();
			}
		});
		
		//button hiển thị kết quả
		finish.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
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
				DFS(startVertex,graphView);
			}
		});
		
		//button hiển thị từng bước
		step.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
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
				Map<Vertex<V>, VertexNode<V>> tmp = graphView.vertexNodes;
				Set<Vertex<V>> vertex = tmp.keySet();
				for(Vertex<V> iterator : vertex)
					mark.put(iterator, 0);
				DFSstep(startVertex, graphView);
			}
			
		});
		
		next.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(stackStep.isEmpty()) {
					lb.setText("Done!");
					next.setVisible(false);
					return;
				}
				Vertex<V> currVertex = tmpnext;
				stackStep.remove(currVertex);
				VertexNode<V> currVertexNode = graphView.vertexNodes.get(currVertex);
				currVertexNode.setStyle("-fx-fill: red");
				DFSstep(currVertex,graphView);
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
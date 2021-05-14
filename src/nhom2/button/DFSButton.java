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
import nhom2.graphview.Edge.EdgeNode;
import nhom2.graphview.Vertex.VertexNode;

public class DFSButton<V, E> extends Button{
	private Stage stage;
	protected Scene View;
//	private Vertex<V> startVertex;
//	private Vertex<V> currVertex;
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
//		TreeMap<Vertex<V>,Edge<E, V>> tempstart1 = new TreeMap<>(tempstart);//
		Set<Vertex<V>> adjStartVertex = tempstart.keySet();
//		SortedSet<Vertex<V>> adjStartVertexSort = new TreeSet<>(adjStartVertex);//
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
			if(mark.get(currVertex).intValue()==0) {
				VertexNode<V> currVertexNode = graphView.vertexNodes.get(currVertex);
				currVertexNode.setStyle("-fx-fill: red");
				Edge<E,V> edge = graphView.theGraph.adjList.get(preVertex.get(currVertex)).get(currVertex);//
				EdgeNode<E,V> edgeNode = graphView.edgeNodes.get(edge);
				edgeNode.setStyle("-fx-stroke: blue");
				if(graphView.theGraph.isDirected==true)
					edgeNode.getAttachedArrow().setStyle("-fx-stroke: blue");
				mark.put(currVertex, 1);

			}
			Map<Vertex<V>, Edge<E, V>> temp = graphView.theGraph.adjList.get(currVertex);
//			TreeMap<Vertex<V>,Edge<E, V>> temp1 = new TreeMap<>(temp);
			Set<Vertex<V>> adjVertex = temp.keySet();
//			SortedSet<Vertex<V>> adjVertexSort = new TreeSet<>(adjVertex);//
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
//		TreeMap<Vertex<V>,VertexNode<V>> tmp1 = new TreeMap<>(tmp);
		Set<Vertex<V>> vertex = tmp.keySet();
//		SortedSet<Vertex<V>> vertexSort = new TreeSet<>(vertex);//
		for(Vertex<V> iterator : vertex)
			mark.put(iterator, 0);
		Vertex<V> currVertex = startVertex;
		DFSUtil(currVertex,graphView,mark);
//		vertex.remove(currVertex);
//		Iterator iterator = vertex.iterator();
//		while(iterator.hasNext()) {
//			currVertex =(Vertex<V>) iterator.next();
//			if(mark.get(currVertex).intValue()==0)
//				DFSUtil(currVertex,graphView,mark);
//		}
	}
	
	private Map<Vertex<V>, Vertex<V>> preVertexStep = new HashMap();//
	private Stack<Vertex<V>> stackStep = new Stack<>();
	private void DFSstep(Vertex<V> startVertex, GraphPanel<V, E> graphView) {
		Vertex<V> tempstack[] = new Vertex[1000];//
		int i=1;
		Vertex<V> currVertex = startVertex;
		Vertex<V> tmpcurr = currVertex;
//		VertexNode<V> currVertexNode = graphView.vertexNodes.get(currVertex);
//		currVertexNode.setStyle("-fx-fill: red");
		mark.put(currVertex, 1);
		Map<Vertex<V>, Edge<E, V>> temp = graphView.theGraph.adjList.get(currVertex);
		Set<Vertex<V>> adjVertex = temp.keySet();
		Iterator iterator = adjVertex.iterator();
		while(iterator.hasNext()) {
			currVertex = (Vertex<V>) iterator.next();
			if(mark.get(currVertex).intValue()==0) {
				preVertexStep.put(currVertex, tmpcurr);
				stackStep.push(currVertex);
//				tempstack[i]= currVertex;
//				i++;
			}			
		}
//		for(int j=i-1; j>=1;j--)
//			stackStep.push(tempstack[j]);
		currVertex = stackStep.peek();
//		stackStep.pop();
		VertexNode<V> currVertexNode = graphView.vertexNodes.get(currVertex);
		currVertexNode.setStyle("-fx-fill: red");
		Edge<E,V> edge = graphView.theGraph.adjList.get(preVertexStep.get(currVertex)).get(currVertex);//
		EdgeNode<E,V> edgeNode = graphView.edgeNodes.get(edge);
		edgeNode.setStyle("-fx-stroke: blue");
		if(graphView.theGraph.isDirected==true)
			edgeNode.getAttachedArrow().setStyle("-fx-stroke: blue");
		mark.put(currVertex, 1);
	}
	
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
				}
				Vertex<V> currVertex = stackStep.peek();
				stackStep.pop();
				VertexNode<V> currVertexNode = graphView.vertexNodes.get(currVertex);
				currVertexNode.setStyle("-fx-fill: red");
				DFSstep(currVertex,graphView);
			}
			
		});
		
		reset.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				lb.setText("");
				for (VertexNode<V> tmp : graphView.vertexNodes.values())
					tmp.setStyle("-fx-fill: #96d1cd");
				for (EdgeNode<E, V> tmp : graphView.edgeNodes.values()) {
					tmp.setStyle("-fx-stroke: #45597e");
					tmp.getAttachedArrow().setStyle(" -fx-stroke: #45597e");
				}
				
			}
			
		});
	}
	
}

package nhom2.button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.Queue;
import java.util.TreeMap;
import java.util.TreeSet;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;

import nhom2.graph.*;
import nhom2.graphview.*;
import nhom2.graphview.Edge.EdgeLine;
import nhom2.graphview.Edge.EdgeNode;
import nhom2.graphview.Vertex.VertexNode;
import nhom2.window.Main;

public class BFSButton<V, E> extends Button{
	private Pane pane;
	private Stage stage;
	protected Scene View;
	Map<Vertex<V>,Integer> mark = new HashMap();

	private void BFSUtil(Vertex<V> currVertex, GraphPanel<V, E> graphView, Map<Vertex<V>,Integer> mark) {
		Queue<Vertex<V>> queue = new LinkedList<Vertex<V>>();
		Map<Vertex<V>, Vertex<V>> preVertex = new HashMap();
		queue.offer(currVertex); // t van chưa biet xử lý biệt lệ nên tạm dùng offer  
		currVertex = queue.peek();
		queue.poll();
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
				preVertex.put(currVertex, tmpstart); // Lấy hết các adjacent của tmpstart cho vào queue 
				queue.offer(currVertex);
			}
		}

		while(queue.peek() != null) {// khi queue chưa rỗng
			currVertex = queue.peek();
			queue.poll();
			Vertex<V> tmpcurr = currVertex;
			if(mark.get(currVertex).intValue()==0) {
				VertexNode<V> currVertexNode = graphView.vertexNodes.get(currVertex);
				currVertexNode.setStyle("-fx-fill: red");
				Edge<E,V> edge = graphView.theGraph.adjList.get(preVertex.get(currVertex)).get(currVertex);//
				EdgeLine<E,V> edgeNode = graphView.edgeNodes.get(edge);
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
					preVertex.put(currVertex, tmpcurr); // cái này cũng giống như trên
					queue.offer(currVertex);
				}
			}
		}
	}
	private void BFS(Vertex<V> startVertex, GraphPanel<V, E> graphView) {
		Map<Vertex<V>, VertexNode<V>> tmp = graphView.vertexNodes;
		//		TreeMap<Vertex<V>,VertexNode<V>> tmp1 = new TreeMap<>(tmp);
		Set<Vertex<V>> vertex = tmp.keySet();
		//		SortedSet<Vertex<V>> vertexSort = new TreeSet<>(vertex);//
		for(Vertex<V> iterator : vertex)
			mark.put(iterator, 0);
		Vertex<V> currVertex = startVertex;
		BFSUtil(currVertex,graphView,mark);
		//		vertex.remove(currVertex);
		//		Iterator iterator = vertex.iterator();
		//		while(iterator.hasNext()) {
		//			currVertex =(Vertex<V>) iterator.next();
		//			if(mark.get(currVertex).intValue()==0)
		//				BFSUtil(currVertex,graphView,mark);
		//		}
	}

	private Map<Vertex<V>, Vertex<V>> preVertexStep = new HashMap();//
	private Queue<Vertex<V>> queueStep = new LinkedList<Vertex<V>>();
	private void BFSstep(Vertex<V> startVertex, GraphPanel<V, E> graphView) {
		Vertex<V> tempqueue[] = new Vertex[1000];//
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
				preVertexStep.put(currVertex, tmpcurr); // lấy các adjacent của tmpcurr cho vào queue
				queueStep.offer(currVertex);
				//				tempqueue[i]= currVertex;
				//				i++;
			}			
		}
		//		for(int j=i-1; j>=1;j--)
		//			queueStep.push(tempqueue[j]);
		currVertex = queueStep.peek();// dequeue
		//		queueStep.poll();              
		VertexNode<V> currVertexNode = graphView.vertexNodes.get(currVertex);
		currVertexNode.setStyle("-fx-fill: red");
		Edge<E,V> edge = graphView.theGraph.adjList.get(preVertexStep.get(currVertex)).get(currVertex);//
		EdgeLine<E,V> edgeNode = graphView.edgeNodes.get(edge);
		edgeNode.setStyle("-fx-stroke: blue");
		if(graphView.theGraph.isDirected==true)
			edgeNode.getAttachedArrow().setStyle("-fx-stroke: blue");
		mark.put(currVertex, 1);
	}

	public BFSButton(GridPane root, GraphPanel<V, E> graphView) { //con cai thiet ke giao dien nay chua ro lam nen idol nao giup voi?
		GridPane grid = new GridPane();
		Label lbStartVertex = new Label("Start Vertex:");
		TextField tfStartVertex = new TextField();
		Button finish = new Button("Hiển thị kết quả");
		Button step = new Button("Hiển thị từng bước");
		Button next = new Button("Next");
		Button reset = new Button("Reset");
		BackButton backBut = new BackButton(root, grid);
		
		tfStartVertex.setPromptText("Nhập đỉnh bắt đầu");
		tfStartVertex.setPrefWidth(85);
		tfStartVertex.setMaxWidth(85);
		next.setVisible(false);
		reset.setVisible(false);

		grid.add(backBut, 0, 0);
		grid.add(lbStartVertex, 0, 2);
		grid.add(tfStartVertex, 0, 3);
		grid.add(finish, 0, 4);
		grid.add(step, 0, 5);
		grid.add(next, 1, 6);
		grid.add(reset, 0, 6);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));

		this.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Node box = Main.getNodeButtonArea();
				root.getChildren().remove(box);
				root.add(grid, 0, 0);
		
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
				BFS(startVertex,graphView);
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
				BFSstep(startVertex, graphView);
			}

		});

		next.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Vertex<V> currVertex = queueStep.peek();
				queueStep.poll();
				VertexNode<V> currVertexNode = graphView.vertexNodes.get(currVertex);
				currVertexNode.setStyle("-fx-fill: red");
				BFSstep(currVertex,graphView);
			}

		});

		reset.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				for (VertexNode<V> tmp : graphView.vertexNodes.values())
					tmp.setStyle("-fx-fill: #96d1cd");
				for (EdgeLine<E, V> tmp : graphView.edgeNodes.values()) {
					tmp.setStyle("-fx-stroke: #45597e");
					tmp.getAttachedArrow().setStyle(" -fx-stroke: #45597e");
				}

			}

		});
	}

}

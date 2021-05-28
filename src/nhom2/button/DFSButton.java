package nhom2.button;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
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
import javafx.stage.Stage;
import nhom2.graph.*;
import nhom2.graphview.*;
import nhom2.graphview.Edge.EdgeLine;
import nhom2.graphview.Vertex.VertexNode;

public class DFSButton<V, E> extends Button{
	private Stage stage;
	protected Scene View;
	Map<Vertex<V>,Integer> mark = new HashMap();
	private Node gridBack;
	private Label lb = new Label();
	private Button next = new Button("Next");
	
	public Node getNodeBack() {
		return gridBack;
	}
	public void setNodeBack(Node nodeBack) {
		this.gridBack = nodeBack;
	}
	
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
	private void DFSstep(Vertex<V> currVertex, GraphPanel<V, E> graphView) {
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
		while(stackStep.contains(currVertex))
			stackStep.remove(currVertex);
		temp = graphView.theGraph.adjList.get(currVertex);
		adjVertex = temp.keySet();
		iterator = adjVertex.iterator();
		int i=1;
		while(iterator.hasNext()) {
			if(mark.get((Vertex<V>)iterator.next()).intValue()==0) {i=0; break;}
		}
		if(stackStep.isEmpty() && i==1) {
			lb.setText("Done!");
			next.setVisible(false);
			return;
	}
	}
	
	//DFS button
	public DFSButton(GridPane root, GraphPanel<V, E> graphView) {
		GridPane grid = new GridPane();
		
		BackButton backBut = new BackButton(root);
		
		GridPane gridChild = new GridPane();
		Label lbStartVertex = new Label("Start Vertex:");
		TextField tfStartVertex = new TextField();
		Button finish = new Button("Hiển thị kết quả");
		Button step = new Button("Hiển thị từng bước");
		
		tfStartVertex.setPromptText("Nhập đỉnh bắt đầu");
		tfStartVertex.setPrefWidth(85);
		tfStartVertex.setMaxWidth(85);
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
		
		//button hiển thị kết quả
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
						DFS(startVertex,graphView);
					}
				});
				
				//button hiển thị từng bước
				step.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						stackStep.removeAllElements();
						lb.setText("");
						for (VertexNode<V> tmp : graphView.vertexNodes.values())
							tmp.setStyle("-fx-fill: #96d1cd");
						for (EdgeLine<E, V> tmp : graphView.edgeNodes.values()) {
							tmp.setStyle("-fx-stroke: #45597e");
							if(graphView.theGraph.isDirected==true) tmp.getAttachedArrow().setStyle(" -fx-stroke: #45597e");
						}
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
						Map<Vertex<V>, VertexNode<V>> tmp = graphView.vertexNodes;
						Set<Vertex<V>> vertex = tmp.keySet();
						for(Vertex<V> iterator : vertex)
							mark.put(iterator, 0);
						Map<Vertex<V>, Edge<E, V>> temp = graphView.theGraph.adjList.get(startVertex);
						if(temp.size()==0) {
							lb.setText("Done!");
							next.setVisible(false);
							return;
						}
						mark.put(startVertex, 1);
						tmpnext = startVertex;
					}
					
				});
				
				next.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						Vertex<V> currVertex = tmpnext;
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
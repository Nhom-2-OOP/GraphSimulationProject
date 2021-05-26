package nhom2.button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import nhom2.graph.Edge;
import nhom2.graph.Vertex;
import nhom2.graphview.GraphPanel;
import nhom2.graphview.Edge.EdgeLine;
import nhom2.graphview.Vertex.VertexNode;

/*Cai 
 
 */

public class ShortestPathButton<V,E> extends Button {
	private Button next = new Button("Next");
	private Label lb = new Label();
	private Node gridBack;
	//heap + map data structure
    public BinaryMinHeap<Vertex<V>> minHeap = new BinaryMinHeap<Vertex<V>>();
	//stores shortest distance from root to every vertex
    public Map<Vertex<V>,Integer> distance = new HashMap<>();
    //stores parent of every vertex in shortest distance
    public Map<Vertex<V>, Vertex<V>> parent = new HashMap<>();
	
	public Node getNodeBack() {
		return gridBack;
	}
	public void setNodeBack(Node nodeBack) {
		this.gridBack = nodeBack;
	}

	public ShortestPathButton(GridPane root, GraphPanel<V, E> graphView) {		
		GridPane grid = new GridPane();
		
		BackButton backBut = new BackButton(root);
		
		GridPane gridChild = new GridPane();
		Label lbStartVertex = new Label("Start Vertex:");
		Label lbEndVertex = new Label("End Vertex:");
		TextField tfStartVertex = new TextField();
		TextField tfEndVertex = new TextField();
		Button finish = new Button("Hiển thị kết quả");
		Button step = new Button("Hiển thị từng bước");
		
		tfStartVertex.setPromptText("Nhập đỉnh bắt đầu");
		tfStartVertex.setPrefWidth(85);
		tfStartVertex.setMaxWidth(85);
		
		tfEndVertex.setPromptText("Nhập đỉnh kết thúc");
		tfEndVertex.setPrefWidth(85);
		tfEndVertex.setMaxWidth(85);
		
		next.setVisible(false);
		Button reset = new Button("Reset");
		reset.setVisible(false);
			
		gridChild.setVgap(30);
		
		gridChild.add(lbStartVertex, 0, 0);
		gridChild.add(tfStartVertex, 0, 1);
		gridChild.add(lbEndVertex, 0, 2);
		gridChild.add(tfEndVertex, 0, 3);
		gridChild.add(finish, 0, 4);
		gridChild.add(step, 0, 5);
		HBox nexResBox = new HBox(60);
		nexResBox.getChildren().addAll(next, reset);
		gridChild.add(lb, 0, 6);
		gridChild.add(nexResBox, 0, 6);
		
		grid.add(backBut, 0, 0);
		grid.add(gridChild, 0, 1);
	
		gridChild.setPadding(new Insets(30, 10, 0, 10));
		gridChild.setHalignment(tfStartVertex, HPos.RIGHT);
		gridChild.setHalignment(tfEndVertex, HPos.RIGHT);

		lbStartVertex.getStyleClass().add("lbStartVerTexFS");
		lbEndVertex.getStyleClass().add("lbStartVerTexFS");
		tfStartVertex.getStyleClass().add("tfStartVertexFS");
		tfEndVertex.getStyleClass().add("tfStartVertexFS");
		finish.getStyleClass().add("FSFinishStep");
		step.getStyleClass().add("FSFinishStep");
		next.getStyleClass().add("FSNextReset");
		reset.getStyleClass().add("FSNextReset");
		nexResBox.getStyleClass().add("nexResBox");
		lb.getStyleClass().add("FSlb");
		this.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(graphView.theGraph.isWeighted == false) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setHeaderText("Tính năng chỉ hỗ trợ đồ thị có trọng số");
					alert.show();
				}
				else {
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
				tfEndVertex.commitValue();
				String dataEnd = tfEndVertex.getText();
				Vertex<V> endVertex = graphView.theGraph.vertices.get(dataEnd);
				if (startVertex == null) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setHeaderText("Đỉnh không tồn tại trong đồ thị");
					alert.show();
					return;
				}
				reset.setVisible(true);
				shortestPath(graphView,startVertex,endVertex);
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
				Map<Vertex<V>, Edge<E, V>> temp = graphView.theGraph.adjList.get(startVertex);
				if(temp.size()==0) {
					lb.setText("Done!");
					next.setVisible(false);
					return;
				}
				for(Vertex<V> vertex : graphView.theGraph.VertexList()){
		            minHeap.add(Integer.MAX_VALUE, vertex);
		        }
				minHeap.decrease(startVertex, 0);
		        distance.put(startVertex, 0);
		        parent.put(startVertex, null);
			}
		});
		
		next.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stepDijkstra(graphView);
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
				distance = new HashMap<>();
				parent = new HashMap<>();
				minHeap = new BinaryMinHeap<Vertex<V>>();
				reset.setVisible(false);
			}	
		});
	}
	
	public void shortestPath(GraphPanel<V, E> graphView, Vertex<V> sourceVertex, Vertex<V> endVertex){
        
        //initialize all vertex with infinite distance from source vertex
        for(Vertex<V> vertex : graphView.theGraph.VertexList()){
            minHeap.add(Integer.MAX_VALUE, vertex);
        }

        //set distance of source vertex to 0
        minHeap.decrease(sourceVertex, 0);

        //put it in map
        distance.put(sourceVertex, 0);

        //source vertex parent is null
        parent.put(sourceVertex, null);

        //iterate till heap is not empty
        while(!minHeap.empty()){
            //get the min value from heap node which has vertex and distance of that vertex from source vertex.
            BinaryMinHeap<Vertex<V>>.Node heapNode = minHeap.extractMinNode();
            Vertex<V> current = heapNode.key;
            
            if(heapNode.weight == Integer.MAX_VALUE)
            	break;

            //update shortest distance of current vertex from source vertex
            distance.put(current, heapNode.weight);

            //iterate through all edges of current vertex
            for(Edge<E, V> edge : graphView.theGraph.incidentEdges(current)){

                //get the adjacent vertex
                Vertex<V> adjacent = graphView.theGraph.opposite(current, edge);

                //if heap does not contain adjacent vertex means adjacent vertex already has shortest distance from source vertex
                if(!minHeap.containsData(adjacent)){
                    continue;
                }

                //add distance of current vertex to edge weight to get distance of adjacent vertex from source vertex
                //when it goes through current vertex
                int newDistance = distance.get(current) + graphView.theGraph.edgeWeight.get(edge);

                //see if this above calculated distance is less than current distance stored for adjacent vertex from source vertex
                if(minHeap.getWeight(adjacent) > newDistance) {
                    minHeap.decrease(adjacent, newDistance);
                    parent.put(adjacent, current);
                }
            }
        }
        
        if(endVertex == null) {
        	for(Vertex<V> i : parent.keySet()) {
        		if(parent.get(i)!=null) {
        			graphView.vertexNodes.get(i).setStyle("-fx-fill: red");
        			graphView.vertexNodes.get(parent.get(i)).setStyle("-fx-fill: red");
        			Edge<E,V> edge = graphView.theGraph.adjList.get(parent.get(i)).get(i);
        			EdgeLine<E,V> edgeNode = graphView.edgeNodes.get(edge);
    				edgeNode.setStyle("-fx-stroke: blue");
    				if(graphView.theGraph.isDirected==true)
    					edgeNode.getAttachedArrow().setStyle("-fx-stroke: blue");
        		}
        	}
        }
        else {
        	if(parent.containsKey(endVertex) == false) {
        		Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setHeaderText("Không có đường đi từ đỉnh bắt đầu đến đỉnh kết thúc");
				alert.show();
        	}
        	else {
	        	Vertex<V> i = endVertex;
	        	graphView.vertexNodes.get(i).setStyle("-fx-fill: red");
	        	while(parent.get(i)!=null) {
	        		graphView.vertexNodes.get(parent.get(i)).setStyle("-fx-fill: red");
	        		Edge<E,V> edge = graphView.theGraph.adjList.get(parent.get(i)).get(i);
	    			EdgeLine<E,V> edgeNode = graphView.edgeNodes.get(edge);
					edgeNode.setStyle("-fx-stroke: blue");
					if(graphView.theGraph.isDirected==true)
						edgeNode.getAttachedArrow().setStyle("-fx-stroke: blue");
					i = parent.get(i);
	        	}
        	}
        }
	 }
	
	//phần hiển thị từng bước
	private Stack<Vertex<V>> stackStep = new Stack<>();
	public void stepDijkstra (GraphPanel<V, E> graphView) {
		BinaryMinHeap<Vertex<V>>.Node heapNode = minHeap.extractMinNode();
        Vertex<V> current = heapNode.key;
        
        if(heapNode.weight == Integer.MAX_VALUE) {
        	lb.setText("Done!");
			next.setVisible(false);
			return;
        }
        graphView.vertexNodes.get(current).setStyle("-fx-fill: red");
        if(parent.get(current)!=null) {
        	graphView.vertexNodes.get(parent.get(current)).setStyle("-fx-fill: red");
    		Edge<E,V> edge = graphView.theGraph.adjList.get(parent.get(current)).get(current);
			EdgeLine<E,V> edgeNode = graphView.edgeNodes.get(edge);
			edgeNode.setStyle("-fx-stroke: blue");
			if(graphView.theGraph.isDirected==true)
				edgeNode.getAttachedArrow().setStyle("-fx-stroke: blue");
        }
        //update shortest distance of current vertex from source vertex
        distance.put(current, heapNode.weight);

        //iterate through all edges of current vertex
        for(Edge<E, V> edge : graphView.theGraph.incidentEdges(current)){

            //get the adjacent vertex
            Vertex<V> adjacent = graphView.theGraph.opposite(current, edge);

            //if heap does not contain adjacent vertex means adjacent vertex already has shortest distance from source vertex
            if(!minHeap.containsData(adjacent)){
                continue;
            }

            //add distance of current vertex to edge weight to get distance of adjacent vertex from source vertex
            //when it goes through current vertex
            int newDistance = distance.get(current) + graphView.theGraph.edgeWeight.get(edge);

            //see if this above calculated distance is less than current distance stored for adjacent vertex from source vertex
            if(minHeap.getWeight(adjacent) > newDistance) {
                minHeap.decrease(adjacent, newDistance);
                parent.put(adjacent, current);
            }
        }
	}
}

package nhom2.button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import nhom2.graph.Edge;
import nhom2.graph.Vertex;
import nhom2.graphview.GraphPanel;
import nhom2.graphview.Edge.EdgeLine;
import nhom2.graphview.Vertex.VertexNode;

public class MinimumSpanningTreeButton<V, E> extends Button{
	private Map<Vertex<V>, Integer> mark = new HashMap();
	private Node gridBack;
	
	//sắp xếp trọng số của cạnh theo thứ tự tăng dần
	private Map<Edge<E, V>, Integer> sortbyValue(Map<Edge<E, V>, Integer> map){
		List<Entry<Edge<E, V>, Integer>> list = new ArrayList<>(map.entrySet());
		list.sort(Entry.comparingByValue());
		Map<Edge<E, V>, Integer> result = new LinkedHashMap<>();
		for(Entry<Edge<E, V>,Integer> entry : list)
			result.put(entry.getKey(), entry.getValue());
		return result;
	}
	
	private void Kruscal(GraphPanel<V, E> graphView) {
		Integer cnt=1;
		Map<Vertex<V>, VertexNode<V>> tmp = graphView.vertexNodes;
		Set<Vertex<V>> vertex = tmp.keySet();
		for(Vertex<V> iterator : vertex)//đánh dấu đỉnh
			mark.put(iterator, 0);
		
//		graphView.addWeightedFeature();///////////////RAMDOM ĐỂ TEST
		Map<Edge<E, V>, Integer> sortedEdge = sortbyValue(graphView.theGraph.edgeWeight);// sắp xếp
		//xét từng cạnh theo thứ tự tăng dần
		for(Edge<E, V> i : sortedEdge.keySet()) {
//			System.out.println(sortedEdge.get(i));
			Vertex<V> tmpvertex[] = i.Vertices();//lấy 2 đỉnh của cạnh
			if(mark.get(tmpvertex[0]).intValue() == mark.get(tmpvertex[1]).intValue()) {
				if(mark.get(tmpvertex[0]).intValue() == 0) {
					VertexNode<V> verNode = graphView.vertexNodes.get(tmpvertex[0]);
					verNode.setStyle("-fx-fill: red");
					verNode = graphView.vertexNodes.get(tmpvertex[1]);
					verNode.setStyle("-fx-fill: red");
					EdgeLine<E,V> edgeNode = graphView.edgeNodes.get(i);
					edgeNode.setStyle("-fx-stroke: blue");
					mark.put(tmpvertex[0], cnt);
					mark.put(tmpvertex[1], cnt);
					cnt++;
				}
			}
			else {
				if(mark.get(tmpvertex[0]).intValue() == 0) {
					VertexNode<V> verNode = graphView.vertexNodes.get(tmpvertex[0]);
					verNode.setStyle("-fx-fill: red");
					EdgeLine<E,V> edgeNode = graphView.edgeNodes.get(i);
					edgeNode.setStyle("-fx-stroke: blue");
					mark.put(tmpvertex[0], mark.get(tmpvertex[1]).intValue());
				}
				else {
					if(mark.get(tmpvertex[1]).intValue() == 0) {
						VertexNode<V> verNode = graphView.vertexNodes.get(tmpvertex[1]);
						verNode.setStyle("-fx-fill: red");
						EdgeLine<E,V> edgeNode = graphView.edgeNodes.get(i);
						edgeNode.setStyle("-fx-stroke: blue");
						mark.put(tmpvertex[1], mark.get(tmpvertex[0]).intValue());
					}
					else {
						EdgeLine<E,V> edgeNode = graphView.edgeNodes.get(i);
						edgeNode.setStyle("-fx-stroke: blue");
						int intTmpVertex = mark.get(tmpvertex[0]).intValue();
						for(Vertex<V> j : vertex) {
							if(mark.get(j).intValue() == intTmpVertex)
								mark.put(j, mark.get(tmpvertex[1]).intValue());
						}
					}
				}
			}

		}
		
	}
	
	public Node getNodeBack() {
		return gridBack;
	}
	public void setNodeBack(Node nodeBack) {
		this.gridBack = nodeBack;
	}
	
	// MinimumSpanningTreeButton
	public MinimumSpanningTreeButton(GridPane root, GraphPanel<V, E> graphView) {
		GridPane grid = new GridPane();

		GridPane gridChild = new GridPane();
		
		gridChild.setVgap(30);
		BackButton backBut = new BackButton(root);
		Button kruscal = new Button();
		this.setText("Cây khung nhỏ nhất");
		kruscal.setText("Tìm");
		Button reset = new Button("Reset");
		reset.setVisible(false);
		
		gridChild.add(kruscal, 0, 0);
		gridChild.add(reset, 1, 0);
		gridChild.setVgap(30);
		gridChild.setHgap(30);
		
		grid.add(backBut, 0, 0);
		grid.add(gridChild, 0, 1);
		
		grid.setHgap(10);
		grid.setVgap(10);
		gridChild.setPadding(new Insets(30, 10, 0, 10));
		grid.setPadding(new Insets(10, 10, 10, 10));
		
		kruscal.getStyleClass().add("FSFinishStep00");
		reset.getStyleClass().add("FSFinishStep00");
		
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
			reset.setVisible(false);
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
		
		kruscal.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				//reset
				for (VertexNode<V> tmp : graphView.vertexNodes.values())
					tmp.setStyle("-fx-fill: #96d1cd");
				for (EdgeLine<E, V> tmp : graphView.edgeNodes.values())
					tmp.setStyle("-fx-stroke: #45597e");
				
				if(graphView.theGraph.isWeighted == false) {
					Alert mess = new Alert(AlertType.CONFIRMATION);
					mess.setHeaderText("Thuật toán không phù hợp với đồ thị không trọng số!");
					mess.show();
					return;
				}
				if(graphView.theGraph.isDirected == true) {
					Alert mess = new Alert(AlertType.CONFIRMATION);
					mess.setHeaderText("Thuật toán không phù hợp với đồ thị có hướng!");
					mess.show();
					return;
				}
				
				Kruscal(graphView);
				reset.setVisible(true);
			}
			
		});
		
		reset.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
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

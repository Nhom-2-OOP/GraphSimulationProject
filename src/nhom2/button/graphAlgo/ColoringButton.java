package nhom2.button.graphAlgo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import nhom2.graph.GraphEdgeList;
import nhom2.graph.Vertex;
import nhom2.graphview.GraphPanel;
import nhom2.graphview.Vertex.VertexNode;

public class ColoringButton extends Button {
	private GraphPanel graph;
	private final int MAXCOLORNUMBER = 20;
	public ColoringButton(GraphPanel graphView) {
		this.graph = graphView;
		this.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(graph.isColored==false) {
		    		greedyColoring(graph.theGraph, graph.vertexNodes);
		    		graph.isColored=true;
		    	}
		    	else {
		    		returnColor(graph.theGraph, graph.vertexNodes);
		    		graph.isColored=false;
		    	}
			}
			
		});
		this.setOnMouseEntered(mouseEvent -> {
			this.getStyleClass().add("ColoringButtonEntered");
		});
		this.setOnMouseExited(mouseEvent -> {
			this.getStyleClass().remove(4);
		});
	}

	
	public <V,E> void greedyColoring(GraphEdgeList<V, E> graph, Map<Vertex<V>, VertexNode<V>> vertexNodes) {
		if(graph.isDirected==true) {
			Alert infrom = new Alert(Alert.AlertType.ERROR);
			infrom.setHeaderText("Không thể tô màu đồ thị có hướng");
			infrom.showAndWait();
		}
		else {
			List<Vertex<V>> vertices = new ArrayList<>(graph.VertexList());
			Map<Vertex<V>,  Integer> result = new HashMap<>();
			
			for(Vertex<V> i : vertices) 
				result.put(i, -1);
			result.put(vertices.get(0), 1);
			
			boolean available[] = new boolean[vertices.size()];
			
			Arrays.fill(available, true);
			
			for (int i = 1; i < vertices.size(); i++) {			
				List<Vertex<V>> adjVertices = new ArrayList<Vertex<V>>(graph.incidentVertex(vertices.get(i)));
				
				for(int j=0; j<adjVertices.size(); j++) {
					if(result.get(adjVertices.get(j))!=-1) 
						available[result.get(adjVertices.get(j))]=false;
				}
				Integer cr;
				for(cr=1; cr<=MAXCOLORNUMBER; cr++)
					if(available[cr]==true)
						break;
				if(cr>MAXCOLORNUMBER) {
					Alert infrom = new Alert(Alert.AlertType.ERROR);
					infrom.setHeaderText("Số màu cần tô vượt quá khả năng chương trình");
					infrom.setContentText("Chương trình chỉ có khả năng tô tối đa 20 màu. Đồ thị của bạn cần hơn 20 màu để tô");
					infrom.showAndWait();
					return;
				}
				result.put(vertices.get(i), cr);
				Arrays.fill(available, true);
			}
			for(Vertex<V> j : vertices) {
				String style = "vertexcolor" + result.get(j).toString();
				vertexNodes.get(j).setStyleClass(style);
			}
		}
	}
	
	public <V,E> void returnColor(GraphEdgeList<V, E> graph, Map<Vertex<V>, VertexNode<V>> vertexNodes) {
		List<Vertex<V>> vertices = new ArrayList<>(graph.VertexList());
		for(Vertex<V> j : vertices)
			vertexNodes.get(j).setStyleClass("vertex");
	}
}
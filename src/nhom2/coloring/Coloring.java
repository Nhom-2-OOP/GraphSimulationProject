package nhom2.coloring;

import java.util.*;
import javafx.scene.control.Alert;
import nhom2.graph.*;
import nhom2.graphview.Vertex.VertexNode;

public class Coloring {
	private final int MAXCOLORNUMBER = 20;
	
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
					infrom.setHeaderText("Số màu cần tô vượt quá khả năng của chương trình");
					infrom.setContentText("Chương trình chỉ hỗ trợ tô tối đa 20 màu");
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
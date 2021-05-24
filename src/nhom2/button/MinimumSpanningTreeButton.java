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
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import nhom2.graph.Edge;
import nhom2.graph.Vertex;
import nhom2.graphview.GraphPanel;
import nhom2.graphview.Edge.EdgeLine;
import nhom2.graphview.Vertex.VertexNode;

public class MinimumSpanningTreeButton<V, E> extends Button{
	private Map<Vertex<V>, Integer> mark = new HashMap();
	
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
		
		graphView.addWeightedFeature();///////////////RAMDOM ĐỂ TEST
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
	
	// MinimumSpanningTreeButton
	public MinimumSpanningTreeButton(GridPane root, GraphPanel<V, E> graphView) {
		GridPane grid = new GridPane();
		Button kruscal = new Button();
		kruscal.setText("Cây khung nhỏ nhất");
		grid.add(kruscal, 1, 1);
		
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));
		
		kruscal.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				//reset
				for (VertexNode<V> tmp : graphView.vertexNodes.values())
					tmp.setStyle("-fx-fill: #96d1cd");
				for (EdgeLine<E, V> tmp : graphView.edgeNodes.values())
					tmp.setStyle("-fx-stroke: #45597e");
				
				Kruscal(graphView);
			}
			
		});
	}

}

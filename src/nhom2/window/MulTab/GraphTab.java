package nhom2.window.MulTab;

import javafx.scene.control.Tab;
import nhom2.graph.GraphEdgeList;
import nhom2.window.PaneGraph.PaneGraph;

public class GraphTab extends Tab{

	public GraphTab(GraphEdgeList<String, String> NewGraph) {
		
		PaneGraph graphPane = new PaneGraph(NewGraph);
		this.setText("tab");
		this.setContent(graphPane);
	}

}

package nhom2.window.PaneGraph;

import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import nhom2.button.MapButton;
import nhom2.button.ScaleButton;
import nhom2.graph.GraphEdgeList;
import nhom2.graphview.GraphPanel;
import nhom2.graphview.MiniMap.MiniMap;
import nhom2.graphview.Zoom.SceneGestures;

public class PaneGraph extends Pane {
	public static GraphEdgeList<String, String> g;
	public static GraphPanel<String, String> graphView;	

	public PaneGraph(GraphEdgeList<String, String> NewGraph) {
//		
		// Tao scene bieu dien do thi
		this.graphView = new GraphPanel<>(NewGraph);
		SubScene subSceneGraphPanel = new SubScene(graphView,0,0);
		
		Screen screen = Screen.getPrimary();
		ScaleButton scaleBut = new ScaleButton(graphView);	
		
//		setGraph(NewGraph);

		MiniMap miniMap = new MiniMap(graphView);
		
		MapButton mapBut = new MapButton(miniMap);
		
		this.resize(screen.getVisualBounds().getWidth() - 300, screen.getVisualBounds().getHeight() - 40);
		
		
		this.getChildren().add(subSceneGraphPanel);
		this.getChildren().add(scaleBut);
		this.getChildren().add(mapBut);
		this.getChildren().add(miniMap);
		
		subSceneGraphPanel.heightProperty().bind(this.heightProperty());
		subSceneGraphPanel.widthProperty().bind(this.widthProperty());
				
		this.getStyleClass().add("graphPane");
		SceneGestures sceneGestures = new SceneGestures(graphView, scaleBut);
		subSceneGraphPanel.addEventFilter( MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
		subSceneGraphPanel.addEventFilter( MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
		subSceneGraphPanel.addEventFilter( ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());
		graphView.init();

	}
	
	public static void setGraph(GraphEdgeList<String, String> NewGraph) {
		g = NewGraph;
		int n = g.vertices.size();
		if (n > 100) {
			if (n <= 1000) GraphPanel.VertexR = 10;
			else GraphPanel.VertexR = 5;
		}
		else {
			GraphPanel.VertexR = 15;
		}
		graphView.Renew(NewGraph, true); 
	}

}

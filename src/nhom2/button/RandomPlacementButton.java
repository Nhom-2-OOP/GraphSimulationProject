package nhom2.button;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import nhom2.graphview.GraphPanel;
import nhom2.graphview.Placement.RandomPlacementStrategy;

public class RandomPlacementButton extends Button{
	private GraphPanel<String, String> graph;
	public RandomPlacementButton(GraphPanel<String, String> GraphView) {
		this.graph = GraphView;
		
		this.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				graph.timer.stop();
				AutoPlacementButton.Check = false;
				//System.out.println(graph.theGraph.vertices.size());
				graph.placementStrategy = new RandomPlacementStrategy();
				graph.initPlacement();
			}
			
		});
		
		this.setOnMouseEntered(mouseEvent -> {
			this.getStyleClass().add("Col1ChooseButtonEntered");		

		});
		this.setOnMouseExited(mouseEvent -> {
			this.getStyleClass().remove(2);
		});
	}
}

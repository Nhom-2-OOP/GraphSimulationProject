package nhom2.button;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import nhom2.graphview.GraphPanel;
import nhom2.graphview.RandomPlacementStrategy;

public class AutoPlacementButton extends Button{
	private GraphPanel graph;
	public AutoPlacementButton(GraphPanel GraphView) {
		this.graph = GraphView;
		this.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				graph.placementStrategy = new RandomPlacementStrategy();
				graph.init();
				graph.timer.start();;
			}
			
		});
	}
}
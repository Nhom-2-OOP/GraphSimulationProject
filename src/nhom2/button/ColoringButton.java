package nhom2.button;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import nhom2.coloring.Coloring;
import nhom2.graphview.GraphPanel;
import nhom2.graphview.Placement.CircularSortedPlacementStrategy;

public class ColoringButton extends Button {
	private GraphPanel graph;
	public ColoringButton(GraphPanel graphView) {
		this.graph = graphView;
		this.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				graph.setColor();
			}
			
		});
	}
}

package nhom2.button;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.text.TextAlignment;
import nhom2.graphview.GraphPanel;
import nhom2.graphview.Placement.CircularSortedPlacementStrategy;

public class CircularPlacementButton extends Button{
	private GraphPanel graph;
	public CircularPlacementButton(GraphPanel GraphView) {
		this.graph = GraphView;
		this.setText("Sắp xếp đỉnh theo hình tròn");
		this.setTextAlignment(TextAlignment.LEFT);
		this.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				graph.timer.stop();
				AutoPlacementButton.Check = false;
				graph.placementStrategy = new CircularSortedPlacementStrategy();
				graph.init();
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

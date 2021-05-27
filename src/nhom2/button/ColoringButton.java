package nhom2.button;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import nhom2.graphview.GraphPanel;

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
		this.setOnMouseEntered(mouseEvent -> {
			this.getStyleClass().add("ColoringButtonEntered");
		});
		this.setOnMouseExited(mouseEvent -> {
			this.getStyleClass().remove(4);
		});
	}
}
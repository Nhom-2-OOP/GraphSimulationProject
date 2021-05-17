package nhom2.button;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import nhom2.graphview.GraphPanel;
import nhom2.graphview.Placement.RandomPlacementStrategy;

public class AutoPlacementButton extends Button{
	private GraphPanel graph;
	public static boolean Check = false;
	private Label lb = new Label("Sắp xếp đỉnh tự động");
	public AutoPlacementButton(GraphPanel GraphView) {
		this.graph = GraphView;
		this.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (graph.theGraph.vertices.size() >= 1000) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("Chỉ dùng với đồ thị có ít hơn 1000 đỉnh");
					alert.showAndWait();
				}
				else {
					if (!Check) {
						Check = true;
						graph.timer.start();
					} else {
						Check = false;
						graph.timer.stop();
					}
				}
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
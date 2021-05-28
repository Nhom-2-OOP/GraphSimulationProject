package nhom2.button;

import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.*;

public class BackButton extends Button {
	public Node gridBack;

	public Node getGridBack() {
		return gridBack;
	}

	public void setGridBack(Node gridBack) {
		this.gridBack = gridBack;
	}

	public BackButton(GridPane root) {
		this.setPrefSize(40, 20);
		this.getStyleClass().add("BackButton");
		this.setPadding(new Insets(20, 0, 0, 0));
		this.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				root.getChildren().remove(2);
				root.add(gridBack, 1, 0);

			}
		});
		
		this.setOnMouseEntered(mouseEvent -> {
			this.getStyleClass().add("BackButtonEntered");
		});
		this.setOnMouseExited(mouseEvent -> {
			this.getStyleClass().remove(2);
		});

	}

}
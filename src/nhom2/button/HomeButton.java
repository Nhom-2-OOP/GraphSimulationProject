package nhom2.button;

import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.*;
import nhom2.window.Main;



public class HomeButton extends Button {


	public HomeButton(GridPane root) {
//		System.out.println(this.getStyle());
		this.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				root.getChildren().remove(2);
				root.add(Main.getNodeCol1Start(), 1, 0);
			}
		});
		
		this.setOnMouseEntered(mouseEvent -> {
			this.getStyleClass().add("btnHomeEntered");
		});
		this.setOnMouseExited(mouseEvent -> {
			this.getStyleClass().remove(4);
		});
	}

}

package nhom2.button;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import nhom2.graphview.GraphPanel;

public class InfoButton extends Button{
	public InfoButton() {
		this.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		this.setOnMouseEntered(mouseEvent -> {
			this.getStyleClass().add("InfoButtonEntered");
		});
		this.setOnMouseExited(mouseEvent -> {
			this.getStyleClass().remove(4);
		});
	}

}

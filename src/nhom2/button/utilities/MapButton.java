package nhom2.button.utilities;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Screen;
import nhom2.graphview.MiniMap.MiniMap;

public class MapButton extends Button{

	public MapButton(MiniMap miniMap) {
		this.setPrefSize(50, 50);
		Screen screen = Screen.getPrimary();
		this.setTranslateX(screen.getVisualBounds().getWidth() - 350);
		this.setTranslateY(0);
		this.setVisible(true);
		this.getStyleClass().add("mapButton");
		
		this.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				miniMap.setVisible(true);
			}
		});
		
		this.setOnMouseEntered(mouseEvent -> {
			getStyleClass().add("mapButtonEnter");
		});
		this.setOnMouseExited(mouseEvent -> {
			getStyleClass().remove(2);
		});
		
	}
	
	
}

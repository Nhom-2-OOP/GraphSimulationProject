package nhom2.button;
import javafx.scene.control.Button;

import javafx.event.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class InputButton extends Button  {
	private static Scene View;
	public static Stage stage;
	public InputButton(Stage s)  {

		try {
			Parent root = FXMLLoader.load(InputButton.class.getResource("input/InputButton.fxml"));
			InputButton.View = new Scene(root);
			InputButton.stage = s;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setOnAction(new EventHandler<ActionEvent>() {
	
			public void handle(ActionEvent event) {
				stage.setScene(View);
				stage.setTitle("Nhập đồ thị");
				stage.show();
			}
		});
		this.setOnMouseEntered(mouseEvent -> {
			this.getStyleClass().add("btnInputEntered");
		});
		this.setOnMouseExited(mouseEvent -> {
			this.getStyleClass().remove(4);
		});
	}
}

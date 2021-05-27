package nhom2.button;
import javafx.scene.control.Button;

import javafx.event.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class AddWeightButton extends Button  {
	public static Scene View;
	public static Stage stage;
	public AddWeightButton()  {

		this.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				stage = new Stage();
				try {
					Parent root = FXMLLoader.load(AddWeightButton.class.getResource("addWeight/AddWeightButton.fxml"));
					AddWeightButton.View = new Scene(root);
					AddWeightButton.stage = new Stage();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				AddWeightButton.stage.setScene(AddWeightButton.View);
				AddWeightButton.stage.show();

			}
		});

		this.setOnMouseEntered(mouseEvent -> {
			this.getStyleClass().add("btnAddWeightEntered");
		});
		this.setOnMouseExited(mouseEvent -> {
			this.getStyleClass().remove(4);
		});
	}


}

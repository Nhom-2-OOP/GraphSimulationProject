package nhom2.button.info;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Guide extends Button{

	public Guide() {
		this.setText("User Guide");
		
		this.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Stage stage = new Stage();
				VBox root = new VBox();
				//				root.setSpacing(10);
				root.setPadding(new Insets(15,20,10,10));

				


				



				stage.setScene(new Scene(root, 800, 600));
				stage.setTitle("HƯỚNG DẪN SỬ DỤNG");
				stage.show();
			}
		});

	}

}

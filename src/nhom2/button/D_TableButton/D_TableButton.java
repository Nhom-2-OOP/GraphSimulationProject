package nhom2.button.D_TableButton;
import javafx.scene.control.Button;

import javafx.event.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class D_TableButton extends Button  {
	public static Scene View;
	public static Stage stage;
	public D_TableButton(String text)  {
		super(text);
		this.setOnAction(new EventHandler<ActionEvent>() {
		
			public void handle(ActionEvent event) {
				stage = new Stage();
				try {
					Parent root = FXMLLoader.load(D_TableButton.class.getResource("D_TableButton.fxml"));
					D_TableButton.View = new Scene(root);
					D_TableButton.stage = new Stage();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				stage.setTitle("Bảng chạy thuật toán Dijkstra");
				D_TableButton.stage.setScene(D_TableButton.View);

				D_TableButton.stage.show();
			}
		});
	}


}

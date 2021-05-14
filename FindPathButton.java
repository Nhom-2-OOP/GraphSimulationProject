package nhom2.button;

import java.util.Map;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import nhom2.graph.*;
import nhom2.graphview.*;
import nhom2.graphview.Edge.EdgeNode;
import nhom2.graphview.Vertex.VertexNode;

public class FindPathButton extends Button {
	private Scene View;
	public static Stage stage;
	public FindPathButton(Stage s)  {
		try {
			Parent root = FXMLLoader.load(InputButton.class.getResource("findpath/FindPath.fxml"));
			this.View = new Scene(root);
			this.stage = s;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setOnAction(new EventHandler<ActionEvent>() {
	
			public void handle(ActionEvent event) {
				stage.setScene(View);
				stage.show();
			}
		});
	}
}

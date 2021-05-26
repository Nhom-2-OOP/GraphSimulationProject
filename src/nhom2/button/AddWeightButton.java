package nhom2.button;
import javafx.scene.control.Button;

import javafx.event.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import nhom2.graphview.GraphPanel;
public class AddWeightButton extends Button  {
	public static Scene View;
	public static Stage stage;
	public AddWeightButton()  {
<<<<<<< Updated upstream

=======
		
>>>>>>> Stashed changes
		
		this.setOnAction(new EventHandler<ActionEvent>() {
	
			public void handle(ActionEvent event) {
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

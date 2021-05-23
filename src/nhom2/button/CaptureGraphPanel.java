package nhom2.button;

import javafx.event.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import nhom2.graphview.GraphPanel;

import javax.imageio.ImageIO;


public class CaptureGraphPanel extends Button{
	private GraphPanel view;
	public CaptureGraphPanel(GraphPanel graphView, Stage stage) {
		this.view = graphView;
		this.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				boolean isCaptured = true;
				try {
				WritableImage writableImage = view.snapshot(new SnapshotParameters(), null);
				
				// Tao FileChooser luu anh
				FileChooser fileChooser = new FileChooser();
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
	            fileChooser.getExtensionFilters().add(extFilter);
	            File file = fileChooser.showSaveDialog(stage);
	            
				
	            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
	            } catch (Exception ex) {
	            	isCaptured = false;
	            	informNull();
	            }
				if(isCaptured) {
					informSuccess();
				}
			}
			
		});
		
		this.setOnMouseEntered(mouseEvent -> {
			this.getStyleClass().add("btnCaptureGPEntered");
		});
		this.setOnMouseExited(mouseEvent -> {
			this.getStyleClass().remove(4);
		});
		
	}
	private static void informSuccess() {
		Alert inform = new Alert(Alert.AlertType.INFORMATION);
		inform.setHeaderText("Lưu ảnh đồ thị thành công!");
		inform.showAndWait();
	}
	private static void informNull() {
		Alert inform = new Alert(Alert.AlertType.INFORMATION);
		inform.setHeaderText("Chưa lưu ảnh!");
		inform.showAndWait();
	}
}

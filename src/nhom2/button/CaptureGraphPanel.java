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
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import nhom2.graphview.GraphPanel;

import javax.imageio.ImageIO;


public class CaptureGraphPanel extends Button{
	private SubScene View;
	public CaptureGraphPanel(SubScene view, Stage stage) {
		this.View = view;
		this.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				WritableImage writableImage = view.snapshot(new SnapshotParameters(), null);
				
				// Tao FileChooser luu anh
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Chọn địa chỉ lưu");
				fileChooser.setInitialFileName("Ảnh đồ thị" + ".png");
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
		        fileChooser.getExtensionFilters().add(extFilter);
		        File file = fileChooser.showSaveDialog(stage);
			
				try {
	                ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
	            } catch (IOException ex) {
	                Logger.getLogger(CaptureGraphPanel.class.getName()).log(Level.SEVERE, null, ex);
	            }
			}
			
		});
	}
}

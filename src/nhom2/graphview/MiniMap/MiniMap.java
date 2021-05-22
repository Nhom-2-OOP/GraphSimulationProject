package nhom2.graphview.MiniMap;



import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.SnapshotParameters;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Transform;
import javafx.stage.Screen;
import javafx.util.Duration;
import nhom2.graphview.GraphPanel;
import nhom2.graphview.Zoom.SceneGestures;


public class MiniMap extends GridPane{
	int size;
	double height;
	double width;
	
	
	public MiniMap(GraphPanel graphView) {
		this.setVisible(false);
		this.size = 300;
		Screen screen = Screen.getPrimary();
		this.width = size;
		this.height = size/(screen.getVisualBounds().getWidth() - 300) * screen.getVisualBounds().getHeight();
		this.setTranslateX(screen.getVisualBounds().getWidth() - 339 - size);
		this.setTranslateY(0);
		
		
		GridPane borderMap = new GridPane();
		borderMap.getStyleClass().add("borderMap");
		
		ImageView img = new ImageView();
		
		
		Button minimizeMap = new Button();
		minimizeMap.getStyleClass().add("minimizeMapBut");
		minimizeMap.setText("-");
		
		this.setValignment(minimizeMap, VPos.TOP);
		this.setHgap(10);
		
		this.add(img, 1, 0);
		this.add(borderMap, 1, 0);
		this.add(minimizeMap, 0,0);
		
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.01), ev -> {
			SnapshotParameters sp =  new SnapshotParameters();
			sp.setTransform(Transform.scale(width / (graphView.getWidth() * graphView.getScale()), height/ (graphView.getHeight() * graphView.getScale())));
			WritableImage writableImage = graphView.snapshot(sp, null);	
			img.setImage(writableImage);
		}));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();

		minimizeMap.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				setVisible(false);
			}
		});
		minimizeMap.setOnMouseEntered(mouseEvent -> {
			minimizeMap.getStyleClass().add("minimizeMapButEnter");
		});
		minimizeMap.setOnMouseExited(mouseEvent -> {
			minimizeMap.getStyleClass().remove(2);
		});
	}
}

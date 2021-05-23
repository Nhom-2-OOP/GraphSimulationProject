package nhom2.graphview.MiniMap;



import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
//		this.height = size/graphView.getWidth() * graphView.getHeight();
		this.setTranslateX(screen.getVisualBounds().getWidth() - 339 - size);
		this.setTranslateY(0);
		
		Pane nowView = new Pane();
		SubScene subView = new SubScene(nowView, 70, 70);
		DoubleProperty viewWidth = new SimpleDoubleProperty(0);
		DoubleProperty viewHeight = new SimpleDoubleProperty(0);
		subView.heightProperty().bind(viewHeight);
		subView.widthProperty().bind(viewWidth);
		nowView.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1); -fx-background-radius: 0;");
		
		GridPane borderMap = new GridPane();
		borderMap.add(subView, 0, 0);
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
		
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.05), ev -> {
			SnapshotParameters sp =  new SnapshotParameters();
			
			double w0 = graphView.getWidth();
			double h0 = graphView.getHeight();
			
			double x1 = graphView.getTranslateX();
			double y1 = - graphView.getTranslateY();
			
			//size graphView after * scale
			double h1 = graphView.getScale() * graphView.getHeight(); 
			double w1 = graphView.getScale() * graphView.getWidth();
			
			//rate graphView after -> minimap
			double mapScale = width / w1;
			
//			if(mapScale <= 1) {

				sp.setTransform(Transform.scale(mapScale, mapScale));

				WritableImage writableImage = graphView.snapshot(sp, null);	
				img.setImage(writableImage);	

				//size of View
				viewHeight.set(h0 * mapScale);
				viewWidth.set(w0 * mapScale);

				// x, y of View
				double xView = (-w0/ 2 - x1 + w1/2)*mapScale;
				double yView = (h1 / 2 + y1 - h0/2)*mapScale;

				if(xView < 0) {
					viewWidth.set(w0 * mapScale + xView);
					xView = 0;
				}
				if(yView < 0) {
//					System.out.println((h0 * mapScale + yView) + " and " + height);
					viewHeight.set(h0 * mapScale + yView);
					yView = 0;
					
				}
				
				if(yView > height - h0 * mapScale) {
					
					viewHeight.set(h0 * mapScale - yView + height - h0 * mapScale - 10);
				}

				
				subView.setTranslateX(xView);
				subView.setTranslateY(yView);
//			}

			
			
			
			
			
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

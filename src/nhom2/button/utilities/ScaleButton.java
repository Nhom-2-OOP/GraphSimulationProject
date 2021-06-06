package nhom2.button.utilities;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import nhom2.graphview.GraphPanel;
import nhom2.graphview.Zoom.SceneGestures;



public class ScaleButton extends HBox{

	public GraphPanel<String, String> graphView;
	private Boolean txtVision = false;
	public Button showScaleBut;
	public TextField txtScale;

	public ScaleButton(GraphPanel<String, String> graphView) {
		this.graphView = graphView;
		this.setHeight(50);

		Button showScaleBut = new Button();
		showScaleBut.getStyleClass().add("showScaleBut11");
		showScaleBut.getStyleClass().add("showScaleBut11"); //bắt buộc
		showScaleBut.setPrefSize(50, 50);

		TextField txtScale = new TextField();
		txtScale.setText(String.valueOf((int)graphView.getScale()*100) + "%");
		txtScale.getStyleClass().add("txtScale");
		txtScale.setVisible(false);
		
		this.txtScale = txtScale;
		this.showScaleBut = showScaleBut;
		this.getChildren().addAll(showScaleBut, txtScale);

		showScaleBut.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				txtVision = !txtVision;
				txtScale.setVisible(txtVision);
			}
		});
		
		txtScale.setOnMousePressed(mouseEvent ->  {
			txtScale.clear();    
		});

		txtScale.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				txtVision = !txtVision;
				txtScale.setVisible(txtVision);
				double scale;
				try {
					scale = ((int) Double.parseDouble(txtScale.getText())) / 100.0;
					graphView.setTranslateX(0);
					graphView.setTranslateY(0);

					scale = SceneGestures.clamp( scale, SceneGestures.getMinScale(), SceneGestures.getMaxScale());
					graphView.setScale( scale);
					
					if(scale > 1) {
						if(showScaleBut.getStyleClass().size() == 3) {
							showScaleBut.getStyleClass().remove(2);
						}
						showScaleBut.getStyleClass().add("showScaleButIn");
					}
					else if (scale < 1){
						if(showScaleBut.getStyleClass().size() == 3) {
							showScaleBut.getStyleClass().remove(2);
						}
						showScaleBut.getStyleClass().add("showScaleButOut");
					}
					else {
						if(showScaleBut.getStyleClass().size() == 3) {
							showScaleBut.getStyleClass().remove(2);
						}
						showScaleBut.getStyleClass().add("showScaleBut11");
					}
				}
				catch(Exception e){	
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setHeaderText("NaN");
					alert.show();
					return;
				}
				txtScale.setText(String.valueOf((int)(graphView.getScale()*100)) + "%");

				if(graphView.getTranslateX() == 0 && graphView.getTranslateY() == 0) {
					graphView.setStyle("-fx-border-width: 0px;");
				}
				else {
					graphView.setStyle("-fx-border-width: 1px;");
				}
			}
		});
		
		showScaleBut.setOnMouseEntered(mouseEvent -> {
			if(graphView.getScale() > 1) {
				showScaleBut.getStyleClass().add("showScaleButInEntered");
			}
			else if (graphView.getScale() < 1){
				showScaleBut.getStyleClass().add("showScaleButOutEntered");
			}
			else {
				showScaleBut.getStyleClass().add("showScaleBut11Entered");
			}
		});
		showScaleBut.setOnMouseExited(mouseEvent -> {
			showScaleBut.getStyleClass().remove(3);
		});
	}

}

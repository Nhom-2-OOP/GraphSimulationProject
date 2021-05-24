package nhom2.graphview.Zoom;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import nhom2.graphview.GraphPanel;
import nhom2.graphview.Zoom.DragContext;
import nhom2.button.ScaleButton;

public class SceneGestures {

	private static final double MAX_SCALE = 10.0d;
	public static double getMaxScale() {
		return MAX_SCALE;
	}

	public static double getMinScale() {
		return MIN_SCALE;
	}

	private static final double MIN_SCALE = .1d;

	private DragContext sceneDragContext = new DragContext();

	GraphPanel<String, String> graphView;
	ScaleButton scaleBut;

	public SceneGestures(GraphPanel<String, String>  graphView, ScaleButton scaleBut) {
		this.graphView = graphView;
		this.scaleBut = scaleBut;
	}

	public EventHandler<MouseEvent> getOnMousePressedEventHandler() {
		return onMousePressedEventHandler;
	}

	public EventHandler<MouseEvent> getOnMouseDraggedEventHandler() {
		return onMouseDraggedEventHandler;
	}

	public EventHandler<ScrollEvent> getOnScrollEventHandler() {
		return onScrollEventHandler;
	}

	private EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

		public void handle(MouseEvent event) {

			// right mouse button => panning
			if( !event.isSecondaryButtonDown())
				return;

			sceneDragContext.mouseAnchorX = event.getSceneX();
			sceneDragContext.mouseAnchorY = event.getSceneY();

			sceneDragContext.translateAnchorX = graphView.getTranslateX();
			sceneDragContext.translateAnchorY = graphView.getTranslateY();

		}

	};

	private EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent event) {

			// right mouse button => panning
			if(! event.isSecondaryButtonDown())
				return;
			if(graphView.getScale() == 1 && graphView.getTranslateX() == 0 && graphView.getTranslateY() == 0) {
				graphView.setStyle("-fx-border-width: 0px;");
			}
			else {
				graphView.setStyle("-fx-border-width: 1px;");
			}

			graphView.setTranslateX(sceneDragContext.translateAnchorX + event.getSceneX() - sceneDragContext.mouseAnchorX);
			graphView.setTranslateY(sceneDragContext.translateAnchorY + event.getSceneY() - sceneDragContext.mouseAnchorY);

			event.consume();

		}
	};

	private EventHandler<ScrollEvent> onScrollEventHandler = new EventHandler<ScrollEvent>() {

		@Override
		public void handle(ScrollEvent event) {

			double delta = 1.2;

			double scale = graphView.getScale(); // currently we only use Y, same value is used for X
			double oldScale = scale;

			if (event.getDeltaY() < 0)
				scale /= delta;
			else
				scale *= delta;

			scale = clamp( scale, MIN_SCALE, MAX_SCALE);

			double f = (scale / oldScale)-1;

			double dx = (event.getSceneX() - (graphView.getBoundsInParent().getWidth()/2 + graphView.getBoundsInParent().getMinX()));
			double dy = (event.getSceneY() - (graphView.getBoundsInParent().getHeight()/2 + graphView.getBoundsInParent().getMinY()));

			if(scale == 1 && graphView.getTranslateX() == 0 && graphView.getTranslateY() == 0) {
				graphView.setStyle("-fx-border-width: 0px;");
			}
			else {
				graphView.setStyle("-fx-border-width: 1px;");
			}

			graphView.setScale( scale);

			// note: pivot value must be untransformed, i. e. without scaling
			graphView.setPivot(f*dx, f*dy);
			
			scaleBut.txtScale.setText(String.valueOf((int)(graphView.getScale()*100)) + "%");

			if(graphView.getScale() > 1) {
				if(scaleBut.showScaleBut.getStyleClass().size() == 3) {
					scaleBut.showScaleBut.getStyleClass().remove(2);
				}
				scaleBut.getChildren().get(0).getStyleClass().add("showScaleButIn");
			}
			else if (graphView.getScale() < 1){
				if(scaleBut.showScaleBut.getStyleClass().size() == 3) {
					scaleBut.showScaleBut.getStyleClass().remove(2);
				}
				scaleBut.getChildren().get(0).getStyleClass().add("showScaleButOut");
			}
			else {
				if(scaleBut.showScaleBut.getStyleClass().size() == 3) {
					scaleBut.showScaleBut.getStyleClass().remove(2);
				}
				scaleBut.getChildren().get(0).getStyleClass().add("showScaleBut11");
			}
			event.consume();
		}

	};


	public static double clamp( double value, double min, double max) {

		if( Double.compare(value, min) < 0)
			return min;

		if( Double.compare(value, max) > 0)
			return max;

		return value;
	}
}


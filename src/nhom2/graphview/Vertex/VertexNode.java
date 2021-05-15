package nhom2.graphview.Vertex;

import java.util.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import nhom2.graph.*;
import nhom2.graphview.*;
import nhom2.graphview.Label.Label;
import nhom2.graphview.Label.LabelledObject;
import nhom2.graphview.Styling.StyleImplementing;

public class VertexNode<T> extends Circle implements VertexView<T>, LabelledObject{
	
	private final Vertex<T> underlyingVertex;
	private final Set<VertexNode<T>> adjacentVertices;
	private Label attachedLabel = null;
	private boolean isDragging = false;
	public ContextMenu contextMenu = new ContextMenu();
	
	private class Point{

        double x, y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
	
	private final Point forceVector = new Point(0, 0);
	private final Point updatedPosition = new Point(0, 0);
	
	private final StyleImplementing styleProxy;
	
	public VertexNode(Vertex<T> v, double x, double y, double radius, boolean allowMove) {
        super(x, y, radius);

        this.underlyingVertex = v;
        this.attachedLabel = null;
        this.isDragging = false;

        this.adjacentVertices = new HashSet<>();

        styleProxy = new StyleImplementing(this);
        styleProxy.addStyleClass("vertex");

        if (allowMove) {
            enableDrag();
        }
    }
	
	private double boundCenterCoordinate(double value, double min, double max) {
        double radius = getRadius();

        if (value < min + radius) {
            return min + radius;
        } else if (value > max - radius) {
            return max - radius;
        } else {
            return value;
        }
    }

	private void enableDrag() {
        final Point dragDelta = new Point(0, 0);
        
        this.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
        	
            @Override
            public void handle(ContextMenuEvent event) {
            	contextMenu.show(attachedLabel, event.getScreenX(), event.getScreenY());
            }
        });
        	
        setOnMousePressed((MouseEvent mouseEvent) -> {
            if (mouseEvent.isPrimaryButtonDown()) {
                // record a delta distance for the drag and drop operation.
                dragDelta.x = getCenterX() - mouseEvent.getX();
                dragDelta.y = getCenterY() - mouseEvent.getY();
                getScene().setCursor(Cursor.MOVE);
                isDragging = true;

                mouseEvent.consume();
            }
        });

        setOnMouseReleased((MouseEvent mouseEvent) -> {
            getScene().setCursor(Cursor.HAND);
            isDragging = false;

            mouseEvent.consume();
        });

        setOnMouseDragged((MouseEvent mouseEvent) -> {
            if (mouseEvent.isPrimaryButtonDown()) {
                double newX = mouseEvent.getX() + dragDelta.x;
                double x = boundCenterCoordinate(newX, 0, getParent().getLayoutBounds().getWidth());
                setCenterX(x);

                double newY = mouseEvent.getY() + dragDelta.y;
                double y = boundCenterCoordinate(newY, 0, getParent().getLayoutBounds().getHeight());
                setCenterY(y);
                mouseEvent.consume();
            }

        });

        setOnMouseEntered((MouseEvent mouseEvent) -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                getScene().setCursor(Cursor.HAND);
            }

        });

        setOnMouseExited((MouseEvent mouseEvent) -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                getScene().setCursor(Cursor.DEFAULT);
            }

        });
        
        this.setOnMouseClicked((MouseEvent mouseEvent) -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                getScene().setCursor(Cursor.DEFAULT);
            }

        });
    }


//	public void addAdjacentVertex(VertexNode<T> v) {
//        this.adjacentVertices.add(v);
//    }
//	
//	public boolean removeAdjacentVertex(VertexNode<T> v) {
//        return this.adjacentVertices.remove(v);
//    }
//	
//	public boolean removeAdjacentVertices(Collection<VertexNode<T>> col) {
//        return this.adjacentVertices.removeAll(col);
//    }
	
	public Point2D getPosition() {
        return new Point2D(getCenterX(), getCenterY());
    }
	
	@Override
	public Vertex<T> getUnderlyingVertex() {
		// TODO Auto-generated method stub
		return underlyingVertex;
	}

	@Override
	public void setPosition(double x, double y) {
		// TODO Auto-generated method stub
		if (isDragging) {
            return;
        }
        setCenterX(x);
        setCenterY(y);
	}

	@Override
	public double getPositionCenterX() {
		// TODO Auto-generated method stub
		return getCenterX();
	}

	@Override
	public double getPositionCenterY() {
		// TODO Auto-generated method stub
		return getCenterY();
	}
	
	public void setPosition(Point2D p) {
	        setPosition(p.getX(), p.getY());
	}
	
	public void reset() {
        forceVector.x = forceVector.y = 0;
        updatedPosition.x = getCenterX();
        updatedPosition.y = getCenterY();
    }
	
	public void addPosition(double x, double y) {
		forceVector.x += x;
		forceVector.y += y;
    }
	
	public Point2D getforceVector() {
		return new Point2D(forceVector.x, forceVector.y);
    }
	
	public Point2D getUpdatedPosition() {
        return new Point2D(updatedPosition.x, updatedPosition.y);
    }
	
	public void updateDelta() {
        updatedPosition.x = updatedPosition.x + forceVector.x;
        updatedPosition.y = updatedPosition.y + forceVector.y;
    }
	
	public void resetForces() {
        forceVector.x = forceVector.y = 0;
        updatedPosition.x = getCenterX();
        updatedPosition.y = getCenterY();
    }
	
	public void addForceVector(double x, double y) {
        forceVector.x += x;
        forceVector.y += y;
    }
	
//	public boolean isAdjacentTo(VertexNode<T> v) {
//        return this.adjacentVertices.contains(v);
//    }
	
	public void moveFromForces() {

        //limit movement to parent bounds
		try {
			double height = getParent().getLayoutBounds().getHeight();
	        double width = getParent().getLayoutBounds().getWidth();
	        updatedPosition.x = boundCenterCoordinate(updatedPosition.x, 0, width);
	        updatedPosition.y = boundCenterCoordinate(updatedPosition.y, 0, height);

	        setPosition(updatedPosition.x, updatedPosition.y);
		}
        catch (Exception e) {
        	//System.out.println(this.getUnderlyingVertex().element());
        }

//        updatedPosition.x = boundCenterCoordinate(updatedPosition.x, 0, width);
//        updatedPosition.y = boundCenterCoordinate(updatedPosition.y, 0, height);
//
//        setPosition(updatedPosition.x, updatedPosition.y);
    }

	@Override
	public void setStyleClass(String cssClass) {
		// TODO Auto-generated method stub
		styleProxy.setStyleClass(cssClass);
	}

	@Override
	public void addStyleClass(String cssClass) {
		// TODO Auto-generated method stub
		styleProxy.addStyleClass(cssClass);
	}

	@Override
	public boolean removeStyleClass(String cssClass) {
		// TODO Auto-generated method stub
		return styleProxy.removeStyleClass(cssClass);
	}

	@Override
	public void attachLabel(Label label) {
		// TODO Auto-generated method stub
		this.attachedLabel = label;
//        label.xProperty().bind(centerXProperty().subtract(label.getLayoutBounds().getWidth() / 2.0));
//        label.yProperty().bind(centerYProperty().add(getRadius() + label.getLayoutBounds().getHeight()));
        label.xProperty().bind(centerXProperty().subtract(3.7));
        label.yProperty().bind(centerYProperty().add(3.7));
	}

	@Override
	public Label getAttachedLabel() {
		// TODO Auto-generated method stub
		return this.attachedLabel;
	}


}

package nhom2.graphview.Edge;

import nhom2.graph.Edge;
import nhom2.graphview.Arrow;
import nhom2.graphview.Label.Label;
import nhom2.graphview.Label.LabelledObject;
import nhom2.graphview.Styling.StyleImplementing;
import nhom2.graphview.Vertex.VertexNode;
import javafx.beans.value.*;
import javafx.scene.Cursor;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class EdgeLine<E,V> extends Line implements EdgeView<E,V>, LabelledObject{
	private static final double MAX_EDGE_CURVE_ANGLE = 20;
	
	private final Edge<E, V> underlyingEdge;
	
	private final VertexNode<V> inbound;
    private final VertexNode<V> outbound;
    
    private Label attachedLabel = null;
    private Arrow attachedArrow = null;
    
    public ContextMenu contextMenu = new ContextMenu();
    
    public StyleImplementing styleProxy;

    public EdgeLine(Edge<E, V> edge, VertexNode inbound, VertexNode outbound) {
        if( inbound == null || outbound == null) {
            throw new IllegalArgumentException("Cannot connect null vertices.");
        }
        
        this.inbound = inbound;
        this.outbound = outbound;
        
        this.underlyingEdge = edge;
        
        styleProxy = new StyleImplementing(this);
        styleProxy.addStyleClass("edge");
        
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
        
        // Dieu chinh phan dau va cuoi cua line ve center cua dinh inbound va dinh outbound
        this.startXProperty().bind(outbound.centerXProperty());
        this.startYProperty().bind(outbound.centerYProperty());
        this.endXProperty().bind(inbound.centerXProperty());
        this.endYProperty().bind(inbound.centerYProperty());
    }
    
	@Override
	public void attachLabel(Label label) {
		// TODO Auto-generated method stub
		label.addStyleClass("edge-weighted");
		this.attachedLabel = label;
        label.xProperty().bind(startXProperty().add(endXProperty()).divide(2).subtract(label.getLayoutBounds().getWidth()));
        label.yProperty().bind(startYProperty().add(endYProperty()).divide(2).add(label.getLayoutBounds().getHeight()));
	}

	@Override
	public Label getAttachedLabel() {
		// TODO Auto-generated method stub
		return attachedLabel;
	}

	@Override
	public Edge<E, V> getUnderlyingEdge() {
		// TODO Auto-generated method stub
		return underlyingEdge;
	}

	@Override
	public void attachArrow(Arrow arrow) {
		// TODO Auto-generated method stub
		this.attachedArrow = arrow;
        
        /* attach arrow to line's endpoint */
        arrow.translateXProperty().bind(endXProperty());
        arrow.translateYProperty().bind(endYProperty());
        
        /* rotate arrow around itself based on this line's angle */
        Rotate rotation = new Rotate();
        rotation.pivotXProperty().bind(translateXProperty());
        rotation.pivotYProperty().bind(translateYProperty());
        rotation.angleProperty().bind(BindingForEdge.toDegrees( 
        		BindingForEdge.atan2( endYProperty().subtract(startYProperty()), 
                endXProperty().subtract(startXProperty()))
        ));
        
        arrow.getTransforms().add(rotation);
        
        /* add translation transform to put the arrow touching the circle's bounds */
        Translate t = new Translate(- outbound.getRadius(), 0);
        arrow.getTransforms().add(t);
	}

	@Override
	public Arrow getAttachedArrow() {
		// TODO Auto-generated method stub
		return this.attachedArrow;
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
	
	
}

package nhom2.graphview.Edge;

import nhom2.graph.*;
import nhom2.graphview.Arrow;
import nhom2.graphview.Label.LabelledObject;
import nhom2.graphview.Styling.StylableObject;

public interface EdgeView<E, V> extends LabelledObject, StylableObject{
	// Tra ve canh ma no bieu dien
	public Edge<E, V> getUnderlyingEdge();
	
	// Gan arrow vao canh
	public void attachArrow(Arrow arrow);
	
	// Tra ve arrow ma canh dang duoc gan vao
	public Arrow getAttachedArrow();
}

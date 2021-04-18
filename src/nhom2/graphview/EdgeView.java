package nhom2.graphview;

import nhom2.graph.*;

public interface EdgeView<E, V> extends LabelledObject, StylableObject{
	// Tra ve canh ma no bieu dien
	public Edge<E, V> getUnderlyingEdge();
	
	// Gan arrow vao canh
	public void attachArrow(Arrow arrow);
	
	// Tra ve arrow ma canh dang duoc gan vao
	public Arrow getAttachedArrow();
}

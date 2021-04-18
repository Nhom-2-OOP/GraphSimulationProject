package nhom2.graphview;

import nhom2.graph.*;

public interface VertexView<V> extends StylableObject{
	
	public Vertex<V> getUnderlyingVertex();
	// Tra ve reference tro toi vertex dang bieu dien
	
	public void setPosition(double x, double y);
	// Dat vi tri cho vertex
	
	public double getPositionCenterX();
	// Lay toa do X cua vertex
	
	public double getPositionCenterY();
	// Lay toa do Y cua vertex
	
	public double getRadius();
	// Lay ban kinh cua hinh tron bieu dien vertex
}

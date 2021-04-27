package nhom2.graphview.Placement;

import java.util.Collection;
import nhom2.graph.*;
import nhom2.graphview.Vertex.VertexView;

public interface PlacementStrategy {
	public <V,E> void place(double width, double height, Graph<V,E> theGraph, Collection<? extends VertexView<V>> vertices);
}

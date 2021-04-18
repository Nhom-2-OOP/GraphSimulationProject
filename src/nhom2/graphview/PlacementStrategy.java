package nhom2.graphview;

import java.util.Collection;
import nhom2.graph.*;

public interface PlacementStrategy {
	public <V,E> void place(double width, double height, Graph<V,E> theGraph, Collection<? extends VertexView<V>> vertices);
}

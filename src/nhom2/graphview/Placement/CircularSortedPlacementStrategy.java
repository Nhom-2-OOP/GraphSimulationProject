package nhom2.graphview.Placement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.geometry.Point2D;
import nhom2.graph.*;
import nhom2.graphview.UtilitiesPoint2D;
import nhom2.graphview.Vertex.VertexView;

public class CircularSortedPlacementStrategy implements PlacementStrategy {

    @Override
    public <V, E> void place(double width, double height, Graph<V, E> theGraph, Collection<? extends VertexView<V>> vertices) {
        Point2D center = new Point2D(width / 2, height / 2);
        int N = vertices.size();
        double angleIncrement = -360f / N;

        boolean first = true;
        Point2D p = null;
        for (VertexView<V> vertex : sort(vertices)) {
            
            if (first) {
                if(width > height)
                    p = new Point2D(center.getX(),
                            center.getY() - height / 2 + vertex.getRadius() * 2);
                else
                    p = new Point2D(center.getX(),
                            center.getY() - width / 2 + vertex.getRadius() * 2);
                
        
                first = false;
            } else {
                p = UtilitiesPoint2D.rotate(p, center, angleIncrement);
            }

            vertex.setPosition(p.getX(), p.getY());
            
        }
    }
    
    protected <V> Collection<VertexView<V>> sort(Collection<? extends VertexView<V>> vertices) {
        
        List<VertexView<V>> list = new ArrayList<>();
        list.addAll(vertices);
        
        Collections.sort(list, new Comparator<VertexView<V>>() {
            @Override
            public int compare(VertexView<V> t, VertexView<V> t1) {
                return t.getUnderlyingVertex().element().toString().compareToIgnoreCase(t1.getUnderlyingVertex().element().toString());
            }
        });
        
        return list;
    }
}

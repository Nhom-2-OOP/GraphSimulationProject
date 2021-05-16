package nhom2.graphview.Placement;

import nhom2.graph.*;
import nhom2.graphview.Vertex.VertexView;

import java.util.Collection;
import java.util.Random;

public class RandomPlacementStrategy implements PlacementStrategy {

    @Override
    public <V, E> void place(double width, double height, Graph<V, E> theGraph, Collection<? extends VertexView<V>> vertices) {
        
        Random rand = new Random();

        for (VertexView<V> vertex : vertices) {
            
            double x = rand.nextDouble() * (width - 20);
            double y = rand.nextDouble() * (height - 20);
            
            x += 10;
            y += 10;
                        
            vertex.setPosition(x, y);
        }
    }
    
}

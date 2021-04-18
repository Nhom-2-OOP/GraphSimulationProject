package nhom2.graphview;

import nhom2.graph.*;
import java.util.Collection;
import java.util.Random;

public class RandomPlacementStrategy implements PlacementStrategy {

    @Override
    public <V, E> void place(double width, double height, Graph<V, E> theGraph, Collection<? extends VertexView<V>> vertices) {
        
        Random rand = new Random();

        for (VertexView<V> vertex : vertices) {
            
            double x = rand.nextDouble() * width;
            double y = rand.nextDouble() * height;
                        
            vertex.setPosition(x, y);
        }
    }
    
}

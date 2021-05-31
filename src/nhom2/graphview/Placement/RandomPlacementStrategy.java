package nhom2.graphview.Placement;

import nhom2.graph.*;
import nhom2.graphview.GraphPanel;
import nhom2.graphview.Vertex.VertexView;

import java.util.Collection;
import java.util.Random;

public class RandomPlacementStrategy implements PlacementStrategy {

    @Override
    public <V, E> void place(double width, double height, Graph<V, E> theGraph, Collection<? extends VertexView<V>> vertices) {
        
        Random rand = new Random();

        for (VertexView<V> vertex : vertices) {
            
            double x = rand.nextDouble() * (width - GraphPanel.VertexR*4);
            double y = rand.nextDouble() * (height - GraphPanel.VertexR*4);
            
            x += GraphPanel.VertexR*2;
            y += GraphPanel.VertexR*2;
                        
            vertex.setPosition(x, y);
        }
    }
    
}

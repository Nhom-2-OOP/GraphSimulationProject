package nhom2.graphview;

import javafx.scene.*;
import nhom2.graph.*;
import static nhom2.graphview.UtilitiesPoint2D.attractiveForce;
import static nhom2.graphview.UtilitiesPoint2D.repellingForce;
import java.io.File;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import java.net.URI;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class GraphPanel<V, E> extends Pane{
	
	private final PlacementStrategy placementStrategy;
	
	private final Graph<V, E> theGraph;
	private final Map<Vertex<V>, VertexNode<V>> vertexNodes;
	private final Map<Edge<E, V>, EdgeView<E,V>> edgeNodes;
	
	private Consumer<VertexView<V>> vertexClickConsumer = null;
	private Consumer<EdgeView<E,V>> edgeClickConsumer = null;
	
	public AnimationTimer timer;
	
	private final double repulsionForce;
	private final double attractionForce;
    private final double attractionScale;
    
    private final boolean edgesWithArrows;
    private final boolean needLabel;
	
    public GraphPanel(Graph<V, E> theGraph) {
        this(theGraph, null, false, true);
    }
    
	public GraphPanel(Graph<V, E> theGraph, URI cssFile, boolean isDirectedGraph, boolean Label) {
		
		this.placementStrategy = new RandomPlacementStrategy();
		this.theGraph = theGraph;
		
		edgesWithArrows = isDirectedGraph;
		needLabel = Label;

		// Doc file css va add vao style
        try {
            String css;
            if( cssFile != null ) {
                css = cssFile.toURL().toExternalForm();
            } else {
                File f = new File("stylingGraph.css");
                css = f.toURI().toURL().toExternalForm();
            }

            getStylesheets().add(css);
            this.getStyleClass().add("graph");
        } catch (MalformedURLException ex) {
            Logger.getLogger(GraphPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        attractionForce = 30;
        attractionScale = 10;
        repulsionForce = 25000;
        
        vertexNodes = new HashMap<>();
        edgeNodes = new HashMap<>(); 
        initNodes();
        
        timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                runLayoutIteration();
            }
        };
	}
	
	private void runLayoutIteration() {
        for (int i = 0; i < 20; i++) {
            resetForces();
            computeForces();
            updateForces();
        }
        applyForces();
    }
	
	private boolean areAdjacent(VertexNode<V> v, VertexNode<V> u) {
        return v.isAdjacentTo(u);
    }
	
	private void computeForces() {
        for (VertexNode<V> v : vertexNodes.values()) {
            for (VertexNode<V> other : vertexNodes.values()) {
                if (v == other) {
                    continue; //NOP
                }

                //double k = Math.sqrt(getWidth() * getHeight() / graphVertexMap.size());
                Point2D repellingForce = repellingForce(v.getUpdatedPosition(), other.getUpdatedPosition(), this.repulsionForce);

                double deltaForceX = 0, deltaForceY = 0;

                if (areAdjacent(v, other)) {
                    Point2D attractiveForce = attractiveForce(v.getUpdatedPosition(), other.getUpdatedPosition(),
                            vertexNodes.size(), this.attractionForce, this.attractionScale);

                    deltaForceX = attractiveForce.getX() + repellingForce.getX();
                    deltaForceY = attractiveForce.getY() + repellingForce.getY();
                } else {
                	Point2D attractiveForce = attractiveForce(v.getUpdatedPosition(), other.getUpdatedPosition(),
                            vertexNodes.size(), 1, 1);
                	deltaForceX = attractiveForce.getX() + repellingForce.getX();
                    deltaForceY = attractiveForce.getY() + repellingForce.getY();
                }
                
                v.addForceVector(deltaForceX, deltaForceY);
            }
        }
    }
	
	private void updateForces() {
        vertexNodes.values().forEach((v) -> {
            v.updateDelta();
        });
    }

    private void applyForces() {
        vertexNodes.values().forEach((v) -> {
            v.moveFromForces();
        });
    }

    private void resetForces() {
        vertexNodes.values().forEach((v) -> {
            v.resetForces();
        });
    }
    
    private int getTotalEdgesBetween(Vertex<V> v, Vertex<V> u) {
        //TODO: It may be necessary to adjust this method if you use another Graph
        //variant, e.g., Digraph (directed graph)
        int count = 0;
        for (Edge<E, V> edge : theGraph.EdgeList()) {
            if (edge.Vertices()[0] == v && edge.Vertices()[1] == u
                    || edge.Vertices()[0] == u && edge.Vertices()[1] == v) {
                count++;
            }
        }
        return count;
    }
    
    private EdgeView CreateAndAddEdge(Edge<E, V> edge, VertexNode<V> graphVertexInbound, VertexNode<V> graphVertexOutbound) {
        int edgeIndex = 1;

        EdgeView graphEdge;

        if (getTotalEdgesBetween(graphVertexInbound.getUnderlyingVertex(), graphVertexOutbound.getUnderlyingVertex()) > 1
        		|| graphVertexInbound == graphVertexOutbound) {
        	EdgeCurve NewEdgeView = new EdgeCurve(edge, graphVertexInbound, graphVertexOutbound, edgeIndex);
            graphEdge = NewEdgeView;
            this.getChildren().add(0, (Node)NewEdgeView);
        } else {
        	EdgeLine NewEdgeView = new EdgeLine<>(edge, graphVertexInbound, graphVertexOutbound);
            graphEdge = NewEdgeView;
            this.getChildren().add(0, (Node)NewEdgeView);
        }

        return graphEdge;
    }
    
    public void init(){
        this.placementStrategy.place(this.widthProperty().doubleValue(),
                    this.heightProperty().doubleValue(),
                    this.theGraph,
                    this.vertexNodes.values());

        //start automatic layout
      }
    public void start_automatic_layout() {
    	timer.start();
    }
    
    private void initNodes() {

        // Them cac vertex vao vertexNodes
    	for (Vertex<V> vertex : theGraph.VertexList()) {
    		VertexNode<V> NewVertexNode = new VertexNode(vertex, 0, 0, 20, true);
            vertexNodes.put(vertex, NewVertexNode);
        }

        /* create edges graphical representations between existing vertices */
        //this is used to guarantee that no duplicate edges are ever inserted

        for (Vertex<V> vertex : vertexNodes.keySet()) {

            Iterable<Edge<E, V>> incidentEdges = theGraph.incidentEdges(vertex);

            for (Edge<E, V> edge : incidentEdges) {

                Vertex<V> oppositeVertex = theGraph.opposite(vertex, edge);

                VertexNode<V> graphVertexIn = vertexNodes.get(vertex);
                VertexNode<V> graphVertexOppositeOut = vertexNodes.get(oppositeVertex);

                graphVertexIn.addAdjacentVertex(graphVertexOppositeOut);
                graphVertexOppositeOut.addAdjacentVertex(graphVertexIn);

                EdgeView<E,V> graphEdge = CreateAndAddEdge(edge, graphVertexIn, graphVertexOppositeOut);
                
                if (this.edgesWithArrows) {
                    Arrow arrow = new Arrow();
                    graphEdge.attachArrow(arrow);
                    this.getChildren().add(arrow);
                }
                edgeNodes.put(edge, graphEdge);
            }

        }

        /* place vertices above lines */
        for (Vertex<V> vertex : vertexNodes.keySet()) {
            VertexNode<V> v = vertexNodes.get(vertex);
            this.getChildren().add(v);
            if (needLabel) {
            	Label label = new Label((String)vertex.element());
                label.addStyleClass("vertex-label");
                this.getChildren().add(label);
                v.attachLabel(label);
            }
        }
    }
    
    /*public static void main(String[] args) {
    	Graph<String, String> p = build_sample_digraph();
    	GraphPanel firstPanel = new GraphPanel(p, null, true, true, 200, 499);
    	//firstPanel.computeForces();
    	Scene scene = new Scene(firstPanel, 202, 500);
    }
    
    private static Graph<String, String> build_sample_digraph() {

    	GraphEdgeList<String ,String> g = new GraphEdgeList<String,String>();

        g.insertVertex("A");
        g.insertVertex("B");
        g.insertVertex("C");
        g.insertVertex("D");
        g.insertVertex("E");
        g.insertVertex("F");

        g.insertEdge("A", "B", "AB");
        g.insertEdge("B", "A", "AB2");
        g.insertEdge("A", "C", "AC");
        g.insertEdge("A", "D", "AD");
        g.insertEdge("B", "C", "BC");
        g.insertEdge("C", "D", "CD");
        g.insertEdge("B", "E", "BE");
        g.insertEdge("F", "D", "DF");
        g.insertEdge("F", "D", "DF2");

        //yep, its a loop!
        g.insertEdge("A", "A", "Loop");

        return g;
    }*/
}

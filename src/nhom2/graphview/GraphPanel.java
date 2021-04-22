package nhom2.graphview;

import java.util.concurrent.TimeUnit;
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
	
	public PlacementStrategy placementStrategy;
	
	private final GraphEdgeList<V, E> theGraph;
	private final Map<Vertex<V>, VertexNode<V>> vertexNodes;
	private final Map<Edge<E, V>, EdgeView<E,V>> edgeNodes;
	
	public AnimationTimer timer;
	
	private final double repulsionForce;
	private final double attractionForce;
    private final double attractionScale;
    
    private final boolean edgesWithArrows;
    private final boolean needLabel;
	
    public GraphPanel(GraphEdgeList<V, E> theGraph) {
        this(theGraph, null, true);
    }
    
	public GraphPanel(GraphEdgeList<V, E> theGraph, URI cssFile, boolean Label) {
		
		this.placementStrategy = new RandomPlacementStrategy();
		this.theGraph = theGraph;
		
		edgesWithArrows = theGraph.isDirected;
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
        
        attractionForce = 20;
        attractionScale = 10;
        repulsionForce = 10000;
        
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
        for (int i = 0; i < 1; i++) {
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
//                	Point2D attractiveForce = attractiveForce(v.getUpdatedPosition(), other.getUpdatedPosition(),vertexNodes.size(), 1, 2);
                	deltaForceX += repellingForce.getX();
                    deltaForceY += repellingForce.getY();
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
        return theGraph.TotalEdgesBetween(u, v);
    }
    
    private EdgeView CreateAndAddEdge(Edge<E, V> edge, VertexNode<V> graphVertexInbound, VertexNode<V> graphVertexOutbound) {
        int edgeIndex = 1;

        EdgeView graphEdge;

        if (getTotalEdgesBetween(graphVertexInbound.getUnderlyingVertex(), graphVertexOutbound.getUnderlyingVertex()) > 1
        		|| graphVertexInbound == graphVertexOutbound) {
        	EdgeCurve NewEdgeView = new EdgeCurve(edge, graphVertexInbound, graphVertexOutbound, edgeIndex);
        	if (((String)(edge.Vertices()[0].element())).equals("A")) {
        		NewEdgeView.styleProxy.removeStyleClass("edge");
        		NewEdgeView.styleProxy.addStyleClass("usingedge");
        	}
            graphEdge = NewEdgeView;
            this.getChildren().add(0, (Node)NewEdgeView);
        } else {
        	EdgeLine NewEdgeView = new EdgeLine<>(edge, graphVertexInbound, graphVertexOutbound);
        	if (((String)(edge.Vertices()[0].element())).equals("A")) {
        		NewEdgeView.styleProxy.addStyleClass("usingedge");
        		NewEdgeView.styleProxy.removeStyleClass("edge");
        	}
            graphEdge = NewEdgeView;
            this.getChildren().add(0, (Node)NewEdgeView);
        }

        return graphEdge;
    }
    
    public void init(){
        this.placementStrategy.place(this.widthProperty().doubleValue(), this.heightProperty().doubleValue(), this.theGraph,this.vertexNodes.values());
      }
    public void start_automatic_layout() {
    	timer.start();
    }
    
    private void initNodes() {
        // Them cac vertex vao vertexNodes. DPT O(n + m)
    	for (Vertex<V> vertex : theGraph.VertexList()) {
    		VertexNode<V> NewVertexNode = new VertexNode(vertex, 0, 0, 10, true);
            vertexNodes.put(vertex, NewVertexNode);
        }

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

        // Attach label cho cac dinh
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
}

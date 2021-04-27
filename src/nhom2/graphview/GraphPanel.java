package nhom2.graphview;

import java.util.concurrent.TimeUnit;
import javafx.scene.*;
import nhom2.graph.*;
import nhom2.graphview.Edge.EdgeNode;
import nhom2.graphview.Label.Label;
import nhom2.graphview.Placement.PlacementStrategy;
import nhom2.graphview.Placement.RandomPlacementStrategy;
import nhom2.graphview.Vertex.VertexNode;

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
import javafx.event.EventHandler;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
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
	private final Map<Edge<E, V>, EdgeNode<E,V>> edgeNodes;
	public Map<Vertex<V>, Map<Vertex<V>, Integer>> NumOfEdge;
	
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
        NumOfEdge = new HashMap<>();
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
        updateEdges();
    }
	
	private void updateEdges() {
		
	}
	private boolean areAdjacent(VertexNode<V> v, VertexNode<V> u) {
        return v.isAdjacentTo(u);
    }
	
	private void computeForces() {
        for (VertexNode<V> v : vertexNodes.values()) {
            for (VertexNode<V> other : vertexNodes.values()) {
                if (v == other) {
                    continue; 
                }

                Point2D repellingForce = repellingForce(v.getUpdatedPosition(), other.getUpdatedPosition(), this.repulsionForce);

                double deltaForceX = 0, deltaForceY = 0;

                if (areAdjacent(v, other)) {
                    Point2D attractiveForce = attractiveForce(v.getUpdatedPosition(), other.getUpdatedPosition(),
                            vertexNodes.size(), this.attractionForce, this.attractionScale);

                    deltaForceX = attractiveForce.getX() + repellingForce.getX();
                    deltaForceY = attractiveForce.getY() + repellingForce.getY();
                } else {
                	Point2D attractiveForce = attractiveForce(v.getUpdatedPosition(), other.getUpdatedPosition(),vertexNodes.size(), 1, 2);
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
    
    private EdgeNode CreateAndAddEdge(Edge<E, V> edge, VertexNode<V> graphVertexInbound, VertexNode<V> graphVertexOutbound) {

        EdgeNode graphEdge;
        
        Vertex<V> inVertex = graphVertexInbound.getUnderlyingVertex();
        Vertex<V> outVertex = graphVertexOutbound.getUnderlyingVertex();
        
        Integer NewNum;
        if (NumOfEdge.get(outVertex).get(inVertex) == null) {
        	NewNum = new Integer(1);
        	NumOfEdge.get(outVertex).put(inVertex, NewNum);
        	NumOfEdge.get(inVertex).put(outVertex, NewNum);
        }
        else {
        	NewNum = new Integer(NumOfEdge.get(outVertex).get(inVertex).intValue() + 1);
        	NumOfEdge.get(outVertex).put(inVertex, NewNum);
        	NumOfEdge.get(inVertex).put(outVertex, NewNum);
        }
        
        int count = getTotalEdgesBetween(graphVertexInbound.getUnderlyingVertex(), graphVertexOutbound.getUnderlyingVertex());
        //System.out.println(NewNum.intValue() + " " + count + " " + (String)inVertex.element() + " " + (String)outVertex.element());
        int index = NewNum.intValue() - 1;
        
        if (count > 1 || graphVertexInbound == graphVertexOutbound) {
        	EdgeNode NewEdgeView = new EdgeNode(edge, graphVertexInbound, graphVertexOutbound, index, false);
            graphEdge = NewEdgeView;
            this.getChildren().add(0, (Node)NewEdgeView);
        } else {
        	EdgeNode NewEdgeView = new EdgeNode(edge, graphVertexInbound, graphVertexOutbound, index, true);
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
            this.getChildren().add(NewVertexNode);
            if (needLabel) {
            	Label label = new Label((String)vertex.element());
                label.addStyleClass("vertex-label");
                this.getChildren().add(label);
                NewVertexNode.attachLabel(label);
                label.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

					@Override
					public void handle(ContextMenuEvent event) {
						// TODO Auto-generated method stub
						NewVertexNode.contextMenu.show(NewVertexNode, event.getScreenX(), event.getScreenY());
					}           	
                }
                );
            }
            NewVertexNode.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
 
            @Override
            public void handle(ContextMenuEvent event) {
                NewVertexNode.contextMenu.show(NewVertexNode, event.getScreenX(), event.getScreenY());;
            }
            });
            Map<Vertex<V>, Integer> newMap = new HashMap<>();
    		NumOfEdge.put(NewVertexNode.getUnderlyingVertex(), newMap);
        }
    	for (Edge<E, V> edge : theGraph.edges.values()) {
            Vertex<V> vertex = edge.Vertices()[0];
            Vertex<V> oppositeVertex = edge.Vertices()[1];

            VertexNode<V> graphVertexIn = vertexNodes.get(vertex);
            VertexNode<V> graphVertexOppositeOut = vertexNodes.get(oppositeVertex);

            graphVertexIn.addAdjacentVertex(graphVertexOppositeOut);
            graphVertexOppositeOut.addAdjacentVertex(graphVertexIn);

            EdgeNode<E,V> graphEdge = CreateAndAddEdge(edge, graphVertexIn, graphVertexOppositeOut);
                
            if (this.edgesWithArrows) {
            	Arrow arrow = new Arrow();
                graphEdge.attachArrow(arrow);
                this.getChildren().add(arrow);
            }
            edgeNodes.put(edge, graphEdge);
    	}

    }
}

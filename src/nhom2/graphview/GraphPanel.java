package nhom2.graphview;

import java.util.concurrent.TimeUnit;
import javafx.scene.*;
import nhom2.coloring.Coloring;
import nhom2.graph.*;
import nhom2.graphview.Edge.EdgeLine;
import nhom2.graphview.Edge.EdgeNode;
import nhom2.graphview.Edge.EdgeView;
import nhom2.graphview.Label.Label;
import nhom2.graphview.Placement.PlacementStrategy;
import nhom2.graphview.Placement.RandomPlacementStrategy;
import nhom2.graphview.Vertex.VertexNode;
import static nhom2.graphview.UtilitiesPoint2D.attractiveForce;
import static nhom2.graphview.UtilitiesPoint2D.repellingForce;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.geometry.Point2D;

import java.net.URI;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;



public class GraphPanel<V, E> extends Pane{
	
	public PlacementStrategy placementStrategy;
	
	public GraphEdgeList<V, E> theGraph;
	public Map<Vertex<V>, VertexNode<V>> vertexNodes;
	public Map<Edge<E, V>, EdgeLine<E,V>> edgeNodes;
	public Map<Vertex<V>, Map<Vertex<V>, Integer>> NumOfEdge;
	
	public double VertexR = 15;
	
	public boolean isColored = false;
	
	public EventHandler<MouseEvent> Handler;
	
	public AnimationTimer timer;
	private ContextMenu contextMenuRoot;
	private final double repulsionForce;
	private final double attractionForce;
    private final double attractionScale;
    
    public boolean edgesWithArrows;
    private boolean edgesWithWeight;
    private boolean needLabelVertex;
    
    DoubleProperty myScale = new SimpleDoubleProperty(1.0);
	
    
    public GraphPanel(GraphEdgeList<V, E> theGraph) {
        this(theGraph, null, true);
    }
    
	public GraphPanel(GraphEdgeList<V, E> theGraph, URI cssFile, boolean Label) {
		
		this.placementStrategy = new RandomPlacementStrategy();
		this.theGraph = theGraph;
		
		if (theGraph != null) edgesWithArrows = theGraph.isDirected;
		needLabelVertex = Label;

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
            this.getStyleClass().add("graphView");
        } catch (MalformedURLException ex) {
            Logger.getLogger(GraphPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        scaleXProperty().bind(myScale);
        scaleYProperty().bind(myScale);

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
        
        contextMenuRoot = new ContextMenu();
        MenuItem item1 = new MenuItem("Add weighted feature");
        item1.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				addWeightedFeature();
			}
        	
        });
      
        contextMenuRoot.getItems().addAll(item1);
        this.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

			@Override
			public void handle(ContextMenuEvent arg0) {
				//contextMenuRoot.show(,arg0.getSceneX(), arg0.getSceneY());
			}
        	
        });
        
        Handler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				// TODO Auto-generated method stub
				if (mouseEvent.getClickCount() == 2) {
	            	TextInputDialog box = new TextInputDialog("Nhập tên đỉnh");
	            	box.setHeaderText("Thêm đỉnh mới");
	            	box.setTitle("Nhập tên đỉnh");
	            	box.setContentText("Nhập tên đỉnh mới: ");
	            	Optional<String> result = box.showAndWait();
	            	if (result.isPresent()) {
	            		if (theGraph.vertices.get(result.get()) != null) {
	            			Alert newAlert = new Alert(AlertType.WARNING);
	            			newAlert.setTitle("Thông báo");
	            			newAlert.setHeaderText("Thêm đỉnh không thành không");
	            			newAlert.setContentText("Tên đỉnh mới đã có trong đồ thị");
	            			newAlert.showAndWait();
	            		}
	            		else {
	            			Insert((V)result.get(), mouseEvent.getX(), mouseEvent.getY());
	            			Alert alert = new Alert(AlertType.INFORMATION);
	            			alert.setTitle("Thông báo");
	            			alert.setHeaderText(null);
	            			alert.setContentText("Thêm đỉnh thành công!");

	            			alert.showAndWait();
	            		}
	            	}
	            }
			}
        };
        
        this.setOnMouseClicked(Handler);
	}
	
    public double getScale() {
        return myScale.get();
    }

    public void setScale( double scale) {
        myScale.set(scale);
    }

    public void setPivot( double x, double y) {
        setTranslateX(getTranslateX()-x);
        setTranslateY(getTranslateY()-y);
    }
	
    // add new vertex
	public void Insert(V v, double x, double y) {
		Vertex<V> vertex = this.theGraph.insertVertex(v);
		VertexNode<V> NewVertexNode = new VertexNode(vertex, x, y, this.VertexR, true);
        vertexNodes.put(vertex, NewVertexNode);
        this.getChildren().add(NewVertexNode);
        if (needLabelVertex) {
        	Label label = new Label((String)vertex.element());
            label.addStyleClass("vertex-label");
            label.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (!mouseEvent.isPrimaryButtonDown()) {
                    getScene().setCursor(Cursor.DEFAULT);
                }

            });
            label.setOnMouseEntered((MouseEvent mouseEvent) -> {
                if (!mouseEvent.isPrimaryButtonDown()) {
                    getScene().setCursor(Cursor.HAND);
                    //System.out.println("f");
                }

            });
            label.setOnMouseExited((MouseEvent mouseEvent) -> {
                if (!mouseEvent.isPrimaryButtonDown()) {
                    getScene().setCursor(Cursor.DEFAULT);
                }

            });
            label.setOnMousePressed((MouseEvent mouseEvent) -> {
                if (!mouseEvent.isPrimaryButtonDown()) 
                {
                    // record a delta distance for the drag and drop operation.
                    NewVertexNode.dragDeltaX = NewVertexNode.getCenterX() - mouseEvent.getX();
                    NewVertexNode.dragDeltaY = NewVertexNode.getCenterY() - mouseEvent.getY();
                    getScene().setCursor(Cursor.MOVE);
                    NewVertexNode.isDragging = true;
                    mouseEvent.consume();
                }
            });

            label.setOnMouseReleased((MouseEvent mouseEvent) -> {
                getScene().setCursor(Cursor.HAND);
                NewVertexNode.isDragging = false;

                mouseEvent.consume();
            });

            label.setOnMouseDragged((MouseEvent mouseEvent) -> {
                if (mouseEvent.isPrimaryButtonDown()) {
                    double newX = mouseEvent.getX() + NewVertexNode.dragDeltaX;
                    NewVertexNode.setCenterX(NewVertexNode.boundCenterCoordinate(newX, 0, NewVertexNode.getParent().getLayoutBounds().getWidth()));

                    double newY = mouseEvent.getY() + NewVertexNode.dragDeltaY;
                    NewVertexNode.setCenterY(NewVertexNode.boundCenterCoordinate(newY, 0, NewVertexNode.getParent().getLayoutBounds().getHeight()));
                    
                    mouseEvent.consume();
                }

            });
            label.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

				@Override
				public void handle(ContextMenuEvent event) {
					// TODO Auto-generated method stub
					NewVertexNode.contextMenu.show(NewVertexNode, event.getScreenX(), event.getScreenY());
				}           	
            }
            );
            this.getChildren().add(label);
            NewVertexNode.attachLabel(label);
        }
        MenuItem item1 = new MenuItem("Thông tin đỉnh");
        item1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            	Alert inform = new Alert(Alert.AlertType.INFORMATION);
        		inform.setHeaderText("Tên đỉnh: " + vertex.element());
        		inform.showAndWait();
            }
        });
        MenuItem item2 = new MenuItem("Xóa đỉnh");
        item2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            	deleteVertex(NewVertexNode);
        		Alert inform = new Alert(Alert.AlertType.INFORMATION);
        		inform.setHeaderText("Xóa đỉnh thành công");
        		inform.showAndWait();
            }
        });
        
        MenuItem item3 = new MenuItem("Thêm cạnh");
        item3.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {	
            	addEdge(NewVertexNode);
            }
        });
        NewVertexNode.contextMenu.getItems().addAll(item1, item2, item3);
        NewVertexNode.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

	        @Override
	        public void handle(ContextMenuEvent event) {
	            NewVertexNode.contextMenu.show(NewVertexNode, event.getScreenX(), event.getScreenY());;
	        }
        });
        
        Map<Vertex<V>, Integer> newMap = new HashMap<>();
		NumOfEdge.put(NewVertexNode.getUnderlyingVertex(), newMap);
	}
	
	public void Renew(GraphEdgeList<V, E> theGraph, boolean Label) {
		timer.stop();
		this.placementStrategy = new RandomPlacementStrategy();
		this.theGraph = theGraph;
		
		edgesWithArrows = theGraph.isDirected;
		needLabelVertex = Label;
		vertexNodes.clear();;
        edgeNodes.clear();; 
		this.getChildren().clear();
		
//		for (Edge<E,V> edge : theGraph.edges.values()) {
//            Vertex vertex = edge.Vertices()[0];
//            Vertex oppositeVertex = edge.Vertices()[1];
//            System.out.println(vertex.element() + " " + oppositeVertex.element());
//        }
       
        initNodes();
        this.init();
        
        Handler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				// TODO Auto-generated method stub
				if (mouseEvent.getClickCount() == 2) {
	            	TextInputDialog box = new TextInputDialog("Nhập tên đỉnh");
	            	box.setHeaderText("Thêm đỉnh mới");
	            	box.setTitle("Nhập tên đỉnh");
	            	box.setContentText("Nhập tên đỉnh mới: ");
	            	Optional<String> result = box.showAndWait();
	            	if (result.isPresent()) {
	            		if (theGraph.vertices.get(result.get()) != null) {
	            			Alert newAlert = new Alert(AlertType.WARNING);
	            			newAlert.setTitle("Thông báo");
	            			newAlert.setHeaderText("Thêm đỉnh không thành không");
	            			newAlert.setContentText("Tên đỉnh mới đã có trong đồ thị");
	            			newAlert.showAndWait();
	            		}
	            		else {
	            			Insert((V)result.get(), mouseEvent.getX(), mouseEvent.getY());
	            			Alert alert = new Alert(AlertType.INFORMATION);
	            			alert.setTitle("Thông báo");
	            			alert.setHeaderText(null);
	            			alert.setContentText("Thêm đỉnh thành công!");

	            			alert.showAndWait();
	            		}
	            	}
	            }
			}
        };
        
        this.setOnMouseClicked(Handler);
        
        //this.start_automatic_layout();
	}
	
	private void runLayoutIteration() {
        for (int i = 0; i < 10; i++) {
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
        return this.theGraph.areAdjacent(v.getUnderlyingVertex(), u.getUnderlyingVertex()) || this.theGraph.areAdjacent(u.getUnderlyingVertex(), v.getUnderlyingVertex());
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
        	//if (v.getUnderlyingVertex().element().equals("E")) System.out.println("yes");
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
    
    private EdgeLine CreateAndAddEdge(Edge<E, V> edge, VertexNode<V> graphVertexInbound, VertexNode<V> graphVertexOutbound) {

        EdgeLine graphEdge;
        
        Vertex<V> inVertex = graphVertexInbound.getUnderlyingVertex();
        Vertex<V> outVertex = graphVertexOutbound.getUnderlyingVertex();
        
//        Integer NewNum;
//        if (NumOfEdge.get(outVertex).get(inVertex) == null) {
//        	NewNum = new Integer(1);
//        	NumOfEdge.get(outVertex).put(inVertex, NewNum);
//        	NumOfEdge.get(inVertex).put(outVertex, NewNum);
//        }
//        else {
//        	NewNum = new Integer(NumOfEdge.get(outVertex).get(inVertex).intValue() + 1);
//        	NumOfEdge.get(outVertex).put(inVertex, NewNum);
//        	NumOfEdge.get(inVertex).put(outVertex, NewNum);
//        }
        
        int count = getTotalEdgesBetween(graphVertexInbound.getUnderlyingVertex(), graphVertexOutbound.getUnderlyingVertex());
//        int index = NewNum.intValue() - 1;
        
//        if (count > 1 || graphVertexInbound == graphVertexOutbound) {
//        	EdgeNode NewEdgeView = new EdgeNode(edge, graphVertexOutbound, graphVertexInbound, index, true);
//       	System.out.println(index + " " + count + " " + graphVertexInbound.getUnderlyingVertex().element() + " " + graphVertexOutbound.getUnderlyingVertex().element());
//       	System.out.print(NewEdgeView.getControlX1() + " " + NewEdgeView.getControlY1()+ " " + NewEdgeView.getControlX2() + " " + NewEdgeView.getControlY2());
//        	graphEdge = NewEdgeView;
//        	
//            this.getChildren().add(0, (Node)NewEdgeView);
//        } else {
//        	EdgeNode NewEdgeView = new EdgeNode(edge, graphVertexOutbound, graphVertexInbound, index, true);
//            graphEdge = NewEdgeView;
//            this.getChildren().add(0, (Node)NewEdgeView);
//        }
        
      if (count > 1 || graphVertexInbound == graphVertexOutbound) {
    	  EdgeLine NewEdgeView = new EdgeLine(edge, graphVertexOutbound, graphVertexInbound);
//    	System.out.println(index + " " + count + " " + graphVertexInbound.getUnderlyingVertex().element() + " " + graphVertexOutbound.getUnderlyingVertex().element());
//    	System.out.print(NewEdgeView.getControlX1() + " " + NewEdgeView.getControlY1()+ " " + NewEdgeView.getControlX2() + " " + NewEdgeView.getControlY2());
    	  graphEdge = NewEdgeView;
    	
    	  this.getChildren().add(0, (Node)NewEdgeView);
      } else {
    	EdgeLine NewEdgeView = new EdgeLine(edge, graphVertexOutbound, graphVertexInbound);
        graphEdge = NewEdgeView;
        this.getChildren().add(0, (Node)NewEdgeView);
      }

      MenuItem item1 = new MenuItem("Xóa cạnh");
      item1.setOnAction(new EventHandler<ActionEvent>() {
          public void handle(ActionEvent e) {
        	  if (theGraph.isDirected) {
        		  if (theGraph.areAdjacent(outVertex, inVertex)) {
        			  Alert alert = new Alert(AlertType.CONFIRMATION);
        			  alert.setTitle("Chọn hướng cạnh xóa");
        			  alert.setHeaderText("Hãy chọn hướng cạnh xóa");
        			  alert.setContentText(null);

        			  ButtonType buttonTypeOne = new ButtonType(inVertex.element() + " đến " + outVertex.element());
        			  ButtonType buttonTypeTwo = new ButtonType(outVertex.element() + " đến " + inVertex.element());
        			  ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

        			  alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

        			  Optional<ButtonType> result = alert.showAndWait();
        			  if (result.get() == buttonTypeOne){
        				  removeEdge(edge, graphEdge);
        			  } else if (result.get() == buttonTypeTwo) {
        				  removeEdge(theGraph.adjList.get(outVertex).get(inVertex), edgeNodes.get(theGraph.adjList.get(outVertex).get(inVertex)));
        			  }
        		  }
        		  else {
        			  removeEdge(edge, graphEdge);
        		  }
        	  }
        	  else {
        		  removeEdge(edge, graphEdge);
        	  }
          }
      });
      
      MenuItem item2 = new MenuItem("Thêm trọng số");
      item2.setOnAction(new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent arg0) {
			if(edgesWithWeight == false) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Đồ thị hiện tại không trọng số");
				alert.showAndWait();
			}
			else {
				TextInputDialog dialog = new TextInputDialog("Trọng số");
				dialog.setContentText("Nhập trọng số");
				
				dialog.showAndWait();
				String rs = dialog.getEditor().getText();
				System.out.println(rs);
				
				Label weight = new Label(rs);
				graphEdge.attachLabel(weight);
				// this.getChildren(). ????
			}
		}
    	  
      });
      graphEdge.contextMenu.getItems().addAll(item1, item2);
      graphEdge.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                graphEdge.contextMenu.show(graphEdge, event.getScreenX(), event.getScreenY());;
            }
      });
      return graphEdge;
    }
    
    public void removeEdge(Edge edge, EdgeView graphEdge) {
    	theGraph.removeEdge(edge);
  	  	getChildren().remove(graphEdge);
  	  	if (theGraph.isDirected) getChildren().remove(graphEdge.getAttachedArrow());
  	  	if (theGraph.isWeighted) getChildren().remove(graphEdge.getAttachedLabel());
  	  	edgeNodes.remove(edge);
  	  	Alert inform = new Alert(Alert.AlertType.INFORMATION);
  	  	inform.setHeaderText("Xóa cạnh thành công");
  	  	inform.showAndWait();
    }
    

    
    public void init(){
        this.placementStrategy.place(this.widthProperty().doubleValue(), this.heightProperty().doubleValue(), this.theGraph,this.vertexNodes.values());
      }
    
    public void start_automatic_layout() {
    	timer.start();
    }
    
    public void deleteWeightedFeature() {
    	if(this.edgesWithWeight == true) {
    		this.edgesWithWeight = false;
    		for(EdgeLine<E,V> edgeline : edgeNodes.values()) {
        		this.getChildren().remove(edgeline.getAttachedLabel());
        	}
    	}
    }
    
    public void addWeightedFeature() {
    	deleteWeightedFeature();
    	this.edgesWithWeight = true;
    	for(Edge<E,V> edge : edgeNodes.keySet()) {
    		EdgeLine<E,V> edgeline = edgeNodes.get(edge);
    		Label weight = new Label(theGraph.edgeWeight.get(edge).toString());
    		edgeline.attachLabel(weight);
    		this.getChildren().add(weight);
    	}
    }
    
    public void deleteVertex(VertexNode v) {
    	//timer.stop();
    	this.getChildren().remove(v.getAttachedLabel());
    	this.getChildren().remove(v);
    	ArrayList<Edge> deleteList = new ArrayList<Edge>();
    	for (Edge e: theGraph.edges.values()) 
    		if (e.Vertices()[0] == v.getUnderlyingVertex() || e.Vertices()[1] == v.getUnderlyingVertex()){
    			deleteList.add(e);
    		}
    	for (Edge e: deleteList)
    		{
    			if (theGraph.isDirected) {
    				this.getChildren().remove(edgeNodes.get(e).getAttachedArrow());
    			}
    			if (theGraph.isWeighted) {
    				this.getChildren().remove(edgeNodes.get(e).getAttachedLabel());
    			}
    			this.getChildren().remove(edgeNodes.get(e));
    			edgeNodes.remove(e);
    		}
    	deleteList.removeAll(deleteList);
    	vertexNodes.remove(v.getUnderlyingVertex());
    	theGraph.removeVertex(v.getUnderlyingVertex());
    }
    
    private void initNodes() {
        // Them cac vertex vao vertexNodes. DPT O(n + m)
    	if (this.theGraph == null) return;
    	for (Vertex<V> vertex : theGraph.VertexList()) {
    		VertexNode<V> NewVertexNode = new VertexNode(vertex, 0, 0, this.VertexR, true);
            vertexNodes.put(vertex, NewVertexNode);
            this.getChildren().add(NewVertexNode);
            if (needLabelVertex) {
            	Label label = new Label((String)vertex.element());
                label.addStyleClass("vertex-label");
                label.setOnMouseClicked((MouseEvent mouseEvent) -> {
                    if (!mouseEvent.isPrimaryButtonDown()) {
                        label.getScene().setCursor(Cursor.DEFAULT);
                    }

                });
                label.setOnMouseEntered((MouseEvent mouseEvent) -> {
                    if (!mouseEvent.isPrimaryButtonDown()) {
                        label.getScene().setCursor(Cursor.HAND);
                        //System.out.println("f");
                    }

                });
                label.setOnMouseExited((MouseEvent mouseEvent) -> {
                    if (!mouseEvent.isPrimaryButtonDown()) {
                        label.getScene().setCursor(Cursor.DEFAULT);
                    }

                });
                label.setOnMousePressed((MouseEvent mouseEvent) -> {
                    if (!mouseEvent.isPrimaryButtonDown()) 
                    {
                        // record a delta distance for the drag and drop operation.
                        NewVertexNode.dragDeltaX = NewVertexNode.getCenterX() - mouseEvent.getX();
                        NewVertexNode.dragDeltaY = NewVertexNode.getCenterY() - mouseEvent.getY();
                        label.getScene().setCursor(Cursor.MOVE);
                        NewVertexNode.isDragging = true;
                        mouseEvent.consume();
                    }
                });

                label.setOnMouseReleased((MouseEvent mouseEvent) -> {
                    label.getScene().setCursor(Cursor.HAND);
                    NewVertexNode.isDragging = false;

                    mouseEvent.consume();
                });

                label.setOnMouseDragged((MouseEvent mouseEvent) -> {
                    if (mouseEvent.isPrimaryButtonDown()) {
                        double newX = mouseEvent.getX() + NewVertexNode.dragDeltaX;
                        NewVertexNode.setCenterX(NewVertexNode.boundCenterCoordinate(newX, 0, NewVertexNode.getParent().getLayoutBounds().getWidth()));

                        double newY = mouseEvent.getY() + NewVertexNode.dragDeltaY;
                        NewVertexNode.setCenterY(NewVertexNode.boundCenterCoordinate(newY, 0, NewVertexNode.getParent().getLayoutBounds().getHeight()));
                        
                        mouseEvent.consume();
                    }

                });
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
            MenuItem item1 = new MenuItem("Thông tin đỉnh");
            item1.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                	Alert inform = new Alert(Alert.AlertType.INFORMATION);
            		inform.setHeaderText("Tên đỉnh: " + vertex.element());
            		inform.showAndWait();
                }
            });
            MenuItem item2 = new MenuItem("Xóa đỉnh");
            item2.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                	deleteVertex(NewVertexNode);
            		Alert inform = new Alert(Alert.AlertType.INFORMATION);
            		inform.setHeaderText("Xóa đỉnh thành công");
            		inform.showAndWait();
                }
            });
            
            MenuItem item3 = new MenuItem("Thêm cạnh");
            item3.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                	addEdge(NewVertexNode);
                }
            });
            MenuItem item4 = new MenuItem("Add weighted feature");
            item4.setOnAction(new EventHandler<ActionEvent>(){

    			@Override
    			public void handle(ActionEvent arg0) {
    				addWeightedFeature();
    			}
            	
            });
            NewVertexNode.contextMenu.getItems().addAll(item1, item2, item3, item4);
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
            
//          System.out.println(vertex.element() + " " + oppositeVertex.element());

            VertexNode<V> graphVertexIn = vertexNodes.get(vertex);
            VertexNode<V> graphVertexOppositeOut = vertexNodes.get(oppositeVertex);
            
            EdgeLine<E,V> graphEdge = CreateAndAddEdge(edge, graphVertexIn, graphVertexOppositeOut);
//          System.out.println(graphEdge.getUnderlyingEdge().Vertices()[0].element() + " "+ graphEdge.getUnderlyingEdge().Vertices()[1].element());
                
            if (this.edgesWithArrows) {
            	Arrow arrow = new Arrow();
                graphEdge.attachArrow(arrow);
                this.getChildren().add(arrow);
            }
            edgeNodes.put(edge, graphEdge);
    	}
    }
    
    public void addEdge(VertexNode NewVertexNode) {
    	Line tmp = new Line();  	
    	tmp.setStyle("-fx-stroke-width: 2; -fx-stroke: #ebaf2f; -fx-stroke-dash-array: 2 5 2 5;");
    	double diffX = 300, diffY = 0;
    	tmp.startXProperty().bind(NewVertexNode.centerXProperty());
    	tmp.startYProperty().bind(NewVertexNode.centerYProperty());
    	tmp.setEndX(NewVertexNode.centerXProperty().doubleValue());
        tmp.setEndY(NewVertexNode.centerYProperty().doubleValue());
    	this.getChildren().add(0, tmp);
    	
    	EventHandler<MouseEvent> myHandler01 = new EventHandler<MouseEvent>() {
    			@Override
    			public void handle(MouseEvent arg0) {
    				// TODO Auto-generated method stub
    				tmp.setEndX(arg0.getSceneX() - diffX);
    	            tmp.setEndY(arg0.getSceneY() - diffY);
    			}
    	};
    	//this.addEventFilter(MouseEvent.MOUSE_MOVED, myHandler01);
    	this.setOnMouseMoved(myHandler01);
    	EventHandler<MouseEvent> myHandler02 = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getClickCount() == 1) {
	    			for (VertexNode v: vertexNodes.values()) {
	    				if (v.contains(arg0.getX(), arg0.getY())) {
	    					setOnMouseMoved(null);
	    					getChildren().remove(0);
	    					removeEventFilter(MouseEvent.MOUSE_MOVED, myHandler01);
	    					if (theGraph.areAdjacent(NewVertexNode.getUnderlyingVertex(),v.getUnderlyingVertex() )) {
	    						setOnMouseClicked(Handler);
	    						Alert inform = new Alert(Alert.AlertType.ERROR);
	    			    		inform.setHeaderText("Đã có cạnh nối!");
	    			    		inform.showAndWait();
	    					} else {
	    						Edge<E, V> edge = theGraph.insertEdge((V)NewVertexNode.getUnderlyingVertex().element(), (V)v.getUnderlyingVertex().element(), (E)((String)NewVertexNode.getUnderlyingVertex().element() + (String)v.getUnderlyingVertex().element()));
	    						EdgeLine<E,V> graphEdge = CreateAndAddEdge(edge, NewVertexNode, v);
	    			            if (edgesWithArrows) {
	    			            	Arrow arrow = new Arrow();
	    			                graphEdge.attachArrow(arrow);
	    			                getChildren().add(arrow);
	    			            }
	    			            edgeNodes.put(edge, graphEdge);
	    						setOnMouseClicked(Handler);
	    						Alert inform = new Alert(Alert.AlertType.INFORMATION);
	    			    		inform.setHeaderText("Thêm cạnh thành công!");
	    			    		inform.showAndWait();
	    					}    					
	    				}
	    			}
	    		}
			}
    	};
    	this.setOnMouseClicked(myHandler02);
    	//this.addEventFilter(MouseEvent.MOUSE_CLICKED, myHandler02);
    }

    
    public void setColor() {
    	Coloring coloring = new Coloring();
    	if(this.isColored==false) {
    		coloring.greedyColoring(this.theGraph, this.vertexNodes);
    		this.isColored=true;
    	}
    	else {
    		coloring.returnColor(this.theGraph, this.vertexNodes);
    		this.isColored=false;
    	}
    }
}

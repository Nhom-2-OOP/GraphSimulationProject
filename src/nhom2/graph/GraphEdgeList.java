package nhom2.graph;

import java.util.*;
import nhom2.graph.*;
import nhom2.window.*;

public class GraphEdgeList<V,E> implements Graph<V,E>{

	public Map<V, Vertex<V>> vertices;
	public Map<E, Edge<E, V>> edges;
	public Map<Vertex<V>, Map<Vertex<V>,Edge<E,V>>> adjList;		//cac canh ke 
	public Map<Vertex<V>, Map<Vertex<V>, Integer>> NumOfEdge;		//so canh giua 2 dinh
	public boolean isDirected;										//co huong hay vo huong								
	
	public GraphEdgeList(boolean isDirectedGraph) {
    	this.vertices = new HashMap<>();
    	this.edges = new HashMap<>();
    	this.adjList = new HashMap<>();
    	this.NumOfEdge = new HashMap<>();
    	this.isDirected = isDirectedGraph;
    }
	
	public void setIsDirected(boolean value) {
		this.isDirected = value;
	}
	
	@Override
	public int NumOfVertex() {
		// DPT O(1)
		return vertices.size();
	}

	@Override
	public int NumOfEdge() {
		// DPT O(1)
		return edges.size();
	}

	@Override
	public Collection<Vertex<V>> VertexList() {
		// DPT O(1)
		return vertices.values();
	}

	@Override
	public Collection<Edge<E, V>> EdgeList() {
		// DPT O(1)
		return edges.values();
	}

	
	public Collection<Vertex<V>> incidentVertex(Vertex<V> v) {
		Collection<Vertex<V>> adjVertices = new ArrayList<Vertex<V>>();
		for(Edge<E, V> i : this.incidentEdges(v))
			adjVertices.add(this.opposite(v, i));
        return adjVertices;
	}
	public Collection<Edge<E, V>> incidentEdges(Vertex<V> v) {	   
		// DPT O(1)
        return adjList.get(v).values();
	}
//	List<Vertex<V>> vertexes = new ArrayList<>(this.VertexList());
	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E, V> e) {
		// DPT O(1)
		Vertex<V>[] list = e.Vertices();
		if (list[0] == v) return list[1];
		else return list[0];
	}

	@Override
	public boolean areAdjacent(Vertex<V> u, Vertex<V> v) {
		// DPT O(1)
		return adjList.get(u).get(v) != null;
	}
	
	// Tao ra MyVertex implement interface Vertex
	class MyVertex implements Vertex<V>{
		
		V vertex;
		
		public MyVertex(V vertex) {
			this.vertex = vertex;
		}
		
		@Override
		public V element() {
			return this.vertex;
		}
		
	}
	

	@Override
	public Vertex<V> insertVertex(V vElement) {
		// DPT O(1)
		if (vertices.get(vElement) != null) return null;
		MyVertex NewVertex = new MyVertex(vElement);
		vertices.put(vElement, NewVertex);
		Map<Vertex<V>,Edge<E,V>> newMapOne = new HashMap<>();
		adjList.put(NewVertex, newMapOne);
		Map<Vertex<V>, Integer> newMapTwo = new HashMap<>();
		NumOfEdge.put(NewVertex, newMapTwo);
		return NewVertex;
	}
	
	// Tao ra MyEdge implement interface Edge
	
	class MyEdge implements Edge<E, V>{
		
		E edge;
		Vertex<V> vertexU;
        Vertex<V> vertexV;
        
        public MyEdge(E element, Vertex<V> vertexOutbound, Vertex<V> vertexInbound) {
            this.edge = element;
            this.vertexU = vertexOutbound;
            this.vertexV = vertexInbound;
        }

		@Override
		public E element() {
			return edge;
		}

		@Override
		public Vertex<V>[] Vertices() {
			Vertex[] vertices = new Vertex[2];
            vertices[0] = vertexU;
            vertices[1] = vertexV;

            return vertices;
		}
		
		public boolean contains(Vertex<V> v) {
            return (isDirected)?(vertexV == v):((vertexV == v)||(vertexU == v));
        }
		
	}

	@Override
	public Edge<E, V> insertEdge(V vElement1, V vElement2, E edgeElement) {
		// DPT O(1)
		if(!vertices.containsKey(vElement1)) this.insertVertex(vElement1);
		if(!vertices.containsKey(vElement2)) this.insertVertex(vElement2);	//Neu chua co diem thi them vao
		MyVertex outVertex = (MyVertex)vertices.get(vElement1);				//Khai bao ra 2 diem
        MyVertex inVertex = (MyVertex)vertices.get(vElement2);
        
        if (!this.isDirected && adjList.get(outVertex).get(inVertex) != null) return null;	//kiem tra xem co huong hay vo huong
        
        MyEdge newEdge = new MyEdge(edgeElement, outVertex, inVertex);		// Khai bao canh
        if (NumOfEdge.get(outVertex).get(inVertex) == null) {
        	Integer NewNum = new Integer(1);
        	NumOfEdge.get(outVertex).put(inVertex, NewNum);
        	NumOfEdge.get(inVertex).put(outVertex, NewNum);
        }
        else {
        	Integer NewNum = new Integer(NumOfEdge.get(outVertex).get(inVertex).intValue() + 1);
        	NumOfEdge.get(outVertex).put(inVertex, NewNum);
        	NumOfEdge.get(inVertex).put(outVertex, NewNum);
        }
        
        adjList.get(outVertex).put(inVertex, newEdge);			//Day diem vao do thi
        if (!this.isDirected) {
        	adjList.get(inVertex).put(outVertex, newEdge);
        }

        edges.put(edgeElement, newEdge);						//Day canh vao do thi

        return newEdge;
	}

	// Tra ve so luong canh giua 2 dinh
	
	public int TotalEdgesBetween(Vertex<V> u, Vertex<V> v) {
		// DPT O(1)
		Integer Pointer = NumOfEdge.get(u).get(v);
		if (Pointer == null) return 0;
		else return Pointer.intValue();
	}
	
	
	
}

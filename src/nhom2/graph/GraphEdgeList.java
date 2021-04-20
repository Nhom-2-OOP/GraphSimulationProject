package nhom2.graph;

import java.util.*;
import nhom2.graph.*;

public class GraphEdgeList<V,E> implements Graph<V,E>{

	private Map<V, Vertex<V>> vertices;
	private Map<E, Edge<E, V>> edges;
	public boolean isDirected = true;
	
	public GraphEdgeList() {
    	this.vertices = new HashMap<>();
    	this.edges = new HashMap<>();
    }
	
	public void setIsDirected(boolean value) {
		this.isDirected = value;
	}
	
	@Override
	public int NumOfVertex() {
		// TODO Auto-generated method stub
		return vertices.size();
	}

	@Override
	public int NumOfEdge() {
		// TODO Auto-generated method stub
		return edges.size();
	}

	@Override
	public Collection<Vertex<V>> VertexList() {
		// TODO Auto-generated method stub
		return vertices.values();
	}

	@Override
	public Collection<Edge<E, V>> EdgeList() {
		// TODO Auto-generated method stub
		return edges.values();
	}

	@Override
	public Collection<Edge<E, V>> incidentEdges(Vertex<V> v) {
		// TODO Auto-generated method stub
		List<Edge<E, V>> incidentEdges = new ArrayList<>();
        for (Edge<E, V> edge : edges.values()) {
            if (((MyEdge) edge).contains(v)) {
                incidentEdges.add(edge);
            }
        }

        return incidentEdges;
	}

	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E, V> e) {
		// TODO Auto-generated method stub
		Vertex<V>[] list = e.Vertices();
		if (list[0] == v) return list[1];
		else return list[0];
	}

	@Override
	public boolean areAdjacent(Vertex<V> u, Vertex<V> v) {
		// TODO Auto-generated method stub
		for (Edge<E, V> edge : edges.values()) {
            if (((MyEdge) edge).contains(u) && ((MyEdge) edge).contains(v)) {
                return true;
            }
        }
        return false;
	}
	
	// Tao ra MyVertex implement interface Vertex
	class MyVertex implements Vertex<V>{
		
		V vertex;
		
		public MyVertex(V vertex) {
			this.vertex = vertex;
		}
		
		@Override
		public V element() {
			// TODO Auto-generated method stub
			return this.vertex;
		}
		
	}

	@Override
	public Vertex<V> insertVertex(V vElement) {
		// TODO Auto-generated method stub
		MyVertex NewVertex = new MyVertex(vElement);
		vertices.put(vElement, NewVertex);
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
			// TODO Auto-generated method stub
			return edge;
		}

		@Override
		public Vertex<V>[] Vertices() {
			// TODO Auto-generated method stub
			Vertex[] vertices = new Vertex[2];
            vertices[0] = vertexU;
            vertices[1] = vertexV;

            return vertices;
		}
		
		public boolean contains(Vertex<V> v) {
            return (vertexU == v || vertexV == v);
        }
		
	}

	@Override
	public Edge<E, V> insertEdge(Vertex<V> u, Vertex<V> v, E edgeElement) {
		// TODO Auto-generated method stub

        MyEdge newEdge = new MyEdge(edgeElement, u, v);
        edges.put(edgeElement, newEdge);

        return newEdge;
	}

	@Override
	public Edge<E, V> insertEdge(V vElement1, V vElement2, E edgeElement) {
		// TODO Auto-generated method stub
		MyVertex outVertex = (MyVertex)vertices.get(vElement1);
        MyVertex inVertex = (MyVertex)vertices.get(vElement2);

        MyEdge newEdge = new MyEdge(edgeElement, outVertex, inVertex);

        edges.put(edgeElement, newEdge);

        return newEdge;
	}


}

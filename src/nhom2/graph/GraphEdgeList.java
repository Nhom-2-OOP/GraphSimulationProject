package nhom2.graph;

import java.util.*;
import java.util.Map.Entry;

public class GraphEdgeList<V,E> implements Graph<V,E>{
	private final int MAX_WEIGHT = 20;
	public Map<V, Vertex<V>> vertices;
	public Map<E, Edge<E, V>> edges;
	public Map<Vertex<V>, Map<Vertex<V>,Edge<E,V>>> adjList;
	public Map<Edge<E,V>, Integer> edgeWeight;
	public boolean isDirected;
	public boolean isWeighted;
	
	
	public GraphEdgeList(boolean isDirectedGraph) {
    	this.vertices = new HashMap<>();
    	this.edges = new HashMap<>();
    	this.adjList = new HashMap<>();
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
	
	public void removeEdge(Edge e) {
		Vertex outVertex = e.Vertices()[0];
		Vertex inVertex = e.Vertices()[1];
		edges.remove(e.element());
		adjList.get(outVertex).remove(inVertex);
		if (!this.isDirected) adjList.get(inVertex).remove(outVertex);
	}
	
	public Collection<Vertex<V>> incidentVertex(Vertex<V> v) {
		return this.adjList.get(v).keySet();
	}

	@Override
	// DPT: O(n)
	public Collection<Edge<E, V>> incidentEdges(Vertex<V> v) {	   
		// DPT O(1)
        return adjList.get(v).values();
	}

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
	
	public Edge getEdge(Vertex<V> u, Vertex<V> v) {
		// DPT O(1)
		return adjList.get(u).get(v);
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
		if(!vertices.containsKey(vElement2)) this.insertVertex(vElement2);
		MyVertex outVertex = (MyVertex)vertices.get(vElement1);
        MyVertex inVertex = (MyVertex)vertices.get(vElement2);
        
        if (adjList.get(outVertex).get(inVertex) != null) return null;
        
        MyEdge newEdge = new MyEdge(edgeElement, outVertex, inVertex);
        
        adjList.get(outVertex).put(inVertex, newEdge);
        if (!this.isDirected) {
        	adjList.get(inVertex).put(outVertex, newEdge);
        }

        edges.put(edgeElement, newEdge);

        return newEdge;
	}
	
	public void removeVertex(Vertex v) {
		Collection<Edge<E, V>> list = edges.values();
		ArrayList<Edge> deleteList = new ArrayList<Edge>();
    	for (Edge e: list) 
    		if (e.Vertices()[0] == v || e.Vertices()[1] == v){
    			deleteList.add(e);
    		}
		for (Edge e: deleteList) 
			{
				edges.remove(e.element());
				adjList.get(e.Vertices()[0]).remove(e.Vertices()[1]);
				if (!this.isDirected) {
					adjList.get(e.Vertices()[1]).remove(e.Vertices()[0]);
				}
			}
		deleteList.removeAll(deleteList);
		adjList.remove(v);
		vertices.remove(v.element());
	}
	
	public Vector<Vertex<V>> isInAPath(Vertex<V> target){
		Vector<Vertex<V>> res = new Vector<>();
		
		// dao nguoc huong cua adjList
		Map<Vertex<V>, Map<Vertex<V>,Edge<E,V>>> adjListReverse = new HashMap<>();
		for(Vertex<V> v : vertices.values()) {
			Map<Vertex<V>, Edge<E,V>> newMap = new HashMap<>();
			adjListReverse.put(v, newMap);
		}
		for(Vertex<V> outVertex : this.adjList.keySet()	) {
			Map<Vertex<V>, Edge<E,V>> map = adjList.get(outVertex);
			for(Vertex<V> inVertex : map.keySet()) {
				adjListReverse.get(inVertex).put(outVertex, map.get(inVertex));
			}
		}
		
		// dfs
		Stack<Vertex<V>> stack = new Stack<>();
		Map<Vertex<V>, Integer> visited = new HashMap<>();
			
		stack.push(target);
		
		while(!stack.empty()) {
			Vertex<V> curV = stack.pop();
			visited.put(curV, 1);
			res.add(curV);
			
			for(Vertex<V> temp : adjListReverse.get(curV).keySet()) {
				if(!visited.containsKey(temp)) {
					stack.push(temp);
				}
			}
		}
		
		return res;
	}
	
	public void TEST() {
		for (Vertex v: this.adjList.get(this.vertices.get("A")).keySet()){
			System.out.println(v.element());
		}
	}


	public Map<Vertex<V>, Map<Vertex<V>, Edge<E, V>>> getAdjList() {
		return adjList;
	}


	public void setAdjList(Map<Vertex<V>, Map<Vertex<V>, Edge<E, V>>> adjList) {
		this.adjList = adjList;
	}
	
	public void setWeightedFeature(Vector<Vector <Integer> > weight) {
		this.isWeighted = true;
		this.edgeWeight = new HashMap<>();
		Map <String, Integer> vecLabel = new HashMap <String, Integer>();
		
		int index1 = 0;
	    for (Entry<V, Vertex<V>> entry : this.vertices.entrySet()) {
	    	vecLabel.put( (String) entry.getKey(), index1);
	    	index1++;
	    }

		for(Edge<E, V> edge : edges.values()) {
			Vertex<V>[] label = edge.Vertices();
			String label1 = label[0].element().toString();
			String label2 = label[1].element().toString();
//			System.out.println(label1 + " " + label2 + " " + Integer.toString(weight.get(vecLabel.get(label1)).get(vecLabel.get(label2))));
			edgeWeight.put(edge, weight.get(vecLabel.get(label1)).get(vecLabel.get(label2)));
		}
	}
	public void setWeightedFeature(int x) {
		this.isWeighted = true;
		edgeWeight = new HashMap<>();
		for(Edge<E, V> edge : edges.values()) {
			edgeWeight.put(edge, 0);
		}
	}
	public void setWeightedFeature() {
		this.isWeighted = true;
		edgeWeight = new HashMap<>();
		Random rd = new Random();
		for(Edge<E, V> edge : edges.values()) {
			Integer num = new Integer(rd.nextInt(MAX_WEIGHT)+1);
			edgeWeight.put(edge, num);
		}
	}
}

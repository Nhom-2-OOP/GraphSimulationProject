package nhom2.graph;

import java.util.Collection;

// V, E la kieu du lieu cua dinh, canh

public interface Graph<V, E> {
	
	public int NumOfVertex();
	// Tra ve so luong dinh cua do thi
	
	public int NumOfEdge();
	// Tra ve so luong canh cua do thi
	
	public Collection<Vertex<V>> VertexList();
	// Tra ve collection gom cac dinh cua do thi
	
	public Collection<Edge<E,V>> EdgeList();
	// Tra ve collection gom cac canh cua do thi
	
	public Collection<Edge<E,V>> incidentEdges(Vertex<V> v);
	// Tra ve collection gom cac canh noi lien voi v
	
	public Vertex<V> opposite(Vertex<V> v, Edge<E, V> e);
	// Tra ve dinh u voi canh u->v
	
	public boolean areAdjacent(Vertex<V> u, Vertex<V> v);
	// Tra ve true neu 2 dinh co duong noi
	
	public Vertex<V> insertVertex(V vElement);
	// Them 1 dinh vao do thi va tra ve reference cua dinh vua them vao
	
	public Edge<E, V> insertEdge(Vertex<V> u, Vertex<V> v, E edgeElement);
	public Edge<E, V> insertEdge(V vElement1, V vElement2, E edgeElement);
	// Them 1 canh vao do thi va tra ve reference cua canh vua them vao
	
	// Xoa canh se bo sung sau
	
}

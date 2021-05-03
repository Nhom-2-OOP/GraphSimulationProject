package nhom2.graph;

public interface Edge<E, V> {
	// E, V la kieu du lieu cua 
	
	public E element();
	// Tra ve canh ma Edge luu
	
	public Vertex<V>[] Vertices();
	// Tra ve mang 2 phan tu gom 2 Vertex la dinh dau va dinh cuoi (u,v) trong do (u -> v) doi voi do thi co huong

}

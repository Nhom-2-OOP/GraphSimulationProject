package nhom2.minHeap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BinaryMinHeap<T> {

    private List<Node> allNodes = new ArrayList<>();
    private Map<T,Integer> nodePosition = new HashMap<>();
        
    public class Node {
        private int weight;
        private T key;
		public T getKey() {
			return key;
		}
		public void setKey(T key) {
			this.key = key;
		}
		public int getWeight() {
			return weight;
		}
		public void setWeight(int weight) {
			this.weight = weight;
		}
    }

    /**
     * Checks where the key exists in heap or not
     */
    public boolean containsData(T key){
        return nodePosition.containsKey(key);
    }

    /**
     * Add key and its weight to they heap
     */
    public void add(int weight,T key) {
        Node node = new Node();
        node.setWeight(weight);
        node.setKey(key);
        allNodes.add(node);
        int size = allNodes.size();
        int current = size - 1;
        int parentIndex = (current - 1) / 2;
        nodePosition.put(node.getKey(), current);

        while (parentIndex >= 0) {
            Node parentNode = allNodes.get(parentIndex);
            Node currentNode = allNodes.get(current);
            if (parentNode.getWeight() > currentNode.getWeight()) {
                swap(parentNode,currentNode);
                updatePositionMap(parentNode.getKey(),currentNode.getKey(),parentIndex,current);
                current = parentIndex;
                parentIndex = (parentIndex - 1) / 2;
            } else {
                break;
            }
        }
    }

    /**
     * Get the heap min without extracting the key
     */
    public T min(){
        return allNodes.get(0).getKey();
    }

    /**
     * Checks with heap is empty or not
     */
    public boolean empty(){
        return allNodes.size() == 0;
    }

    /**
     * Decreases the weight of given key to newWeight
     */
    public void decrease(T data, int newWeight){
        Integer position = nodePosition.get(data);
        allNodes.get(position).setWeight(newWeight);
        int parent = (position -1 )/2;
        while(parent >= 0){
            if(allNodes.get(parent).getWeight() > allNodes.get(position).getWeight()){
                swap(allNodes.get(parent), allNodes.get(position));
                updatePositionMap(allNodes.get(parent).getKey(),allNodes.get(position).getKey(),parent,position);
                position = parent;
                parent = (parent-1)/2;
            }else{
                break;
            }
        }
    }

    /**
     * Get the weight of given key
     */
    public Integer getWeight(T key) {
        Integer position = nodePosition.get(key);
        if( position == null ) {
            return null;
        } else {
            return allNodes.get(position).weight;
        }
    }

    /**
     * Returns the min node of the heap
     */
    public Node extractMinNode() {
        int size = allNodes.size() -1;
        Node minNode = new Node();
        minNode.setKey(allNodes.get(0).getKey());
        minNode.setWeight(allNodes.get(0).getWeight());

        int lastNodeWeight = allNodes.get(size).getWeight();
        allNodes.get(0).setWeight(lastNodeWeight);
        allNodes.get(0).setKey(allNodes.get(size).getKey());
        nodePosition.remove(minNode.getKey());
        nodePosition.remove(allNodes.get(0));
        nodePosition.put(allNodes.get(0).getKey(), 0);
        allNodes.remove(size);

        int currentIndex = 0;
        size--;
        while(true){
            int left = 2*currentIndex + 1;
            int right = 2*currentIndex + 2;
            if(left > size){
                break;
            }
            if(right > size){
                right = left;
            }
            int smallerIndex = allNodes.get(left).getWeight() <= allNodes.get(right).getWeight() ? left : right;
            if(allNodes.get(currentIndex).getWeight() > allNodes.get(smallerIndex).getWeight()){
                swap(allNodes.get(currentIndex), allNodes.get(smallerIndex));
                updatePositionMap(allNodes.get(currentIndex).getKey(),allNodes.get(smallerIndex).getKey(),currentIndex,smallerIndex);
                currentIndex = smallerIndex;
            }else{
                break;
            }
        }
        return minNode;
    }
    /**
     * Extract min value key from the heap
     */
    public T extractMin(){
        Node node = extractMinNode();
        return node.getKey();
    }

    private void printPositionMap(){
        System.out.println(nodePosition);
    }

    private void swap(Node node1,Node node2){
        int weight = node1.getWeight();
        T data = node1.getKey();
        
        node1.setKey(node2.getKey());
        node1.setWeight(node2.getWeight());
        
        node2.setKey(data);
        node2.setWeight(weight);
    }

    private void updatePositionMap(T data1, T data2, int pos1, int pos2){
        nodePosition.remove(data1);
        nodePosition.remove(data2);
        nodePosition.put(data1, pos1);
        nodePosition.put(data2, pos2);
    }
    
    public void printHeap(){
        for(Node n : allNodes){
            System.out.println(n.getWeight() + " " + n.getKey());
        }
    }
    
    public static void main(String args[]){
        BinaryMinHeap<String> heap = new BinaryMinHeap<String>();
        heap.add(3, "Tushar");
        heap.add(4, "Ani");
        heap.add(8, "Vijay");
        heap.add(10, "Pramila");
        heap.add(5, "Roy");
        heap.add(6, "NTF");
        heap.add(2,"AFR");
        heap.decrease("Pramila", 1);
        heap.printHeap();
        heap.printPositionMap();
    }
}

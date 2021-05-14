package nhom2.button;

import java.util.ArrayList;  
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nhom2.graph.Edge;
import nhom2.graph.GraphEdgeList;
import nhom2.graph.Vertex;
import nhom2.graphview.*;
import nhom2.graphview.Edge.EdgeNode;
import nhom2.graphview.Placement.RandomPlacementStrategy;
import nhom2.graphview.Vertex.VertexNode;

public class FindAllPaths<V,E> extends Button {  
	protected ArrayList<String> Traces = new ArrayList<>();   //  1 dãy các đường đi trong đồ thị
	protected ArrayList<String> minPath = new ArrayList<>();
	protected HashMap<String, ArrayList<String>> Adj = new HashMap<>();   // Danh sách các cạnh kề
	protected HashMap<String,Boolean> checked = new HashMap<>();   //  Kiểm tra xem đỉnh đó đã xuất hiện trong dãy đường đi hay chưa
	protected List<ArrayList<String>> result = new ArrayList<>();  //  Danh sách các đường đi
	protected GraphEdgeList<String,String> graph = new GraphEdgeList<String,String>(true);
	protected String start;
	protected String finish;
	protected int numPath;
	protected int posPath;
	public FindAllPaths(GraphPanel graphView) {   
		this.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				graph = graphView.theGraph;
				Stage stage = new Stage();
				GridPane grid = new GridPane();
				TextField begin = new TextField(); 
				begin.setMinWidth(100);
				Label lb1 = new Label("Nhập đỉnh bắt đầu");
				TextField end = new TextField();    
				Label lb2 = new Label("Nhập đỉnh kết thúc");
				Label lb5 = new Label("Chọn thứ tự đường đi hiển thị");
				TextArea paths = new TextArea();      //  
				paths.setEditable(false);
				paths.setMinWidth(480);
				paths.setMinHeight(150);
				
				Label lb3 = new Label("Tìm và hiển thị đường đi ngắn nhất");
				Label lb4 = new Label("Tìm tất cả các đường đi ");
				ChoiceBox<Integer> choosePath = new ChoiceBox();
				
				Button OK1 = new Button();
				Button OK2 = new Button();
				OK2.setText("Select");
				OK2.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						SetUp();
						begin.commitValue();
						end.commitValue();
						start = begin.getText();
						finish = end.getText();
						if (start == "" || finish == "") {
							Alert inform = new Alert(Alert.AlertType.INFORMATION);
				    		inform.setHeaderText("Chưa nhập tên đỉnh!!!");
				    		inform.showAndWait();
						}
						else if (start.equals(finish)) {
							Alert inform = new Alert(Alert.AlertType.INFORMATION);
				    		inform.setHeaderText("Hai đỉnh trùng nhau! Hãy nhập lại!");
				    		inform.showAndWait();
						}
						else if (checkVertexExist(start, finish) ) {
							minPath.clear();
							SetUp(start, finish);
							BFS(start,finish);	
							paths.clear();
							if (minPath.size() > 1) {
								paths.appendText("Đường đi ngắn nhất giữa hai đỉnh : \n");
								paths.appendText(minPath.get(0));
								for (int j = 1; j < minPath.size(); j++) paths.appendText(" -> " + minPath.get(j));
								Reset(graphView);
								changeColor(graphView, minPath);
							}
							else {
								Alert inform = new Alert(Alert.AlertType.INFORMATION);
					    		inform.setHeaderText("Không có đường đi giữa hai đỉnh!!!");
					    		inform.showAndWait();
							}
						}
						else {
							Alert inform = new Alert(Alert.AlertType.INFORMATION);
				    		inform.setHeaderText("Đỉnh nhập vào không tồn tại! \nVui lòng nhập lại");
				    		inform.showAndWait();
						}
					}
				});
				OK1.setText("Select");
				OK1.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						SetUp();
						begin.commitValue();
						end.commitValue();
						start = begin.getText();
						finish = end.getText();
						if (start == "" || finish == "") {
							Alert inform = new Alert(Alert.AlertType.INFORMATION);
				    		inform.setHeaderText("Chưa nhập tên đỉnh!!!");
				    		inform.showAndWait();
						}
						else if (start.equals(finish)) {
							Alert inform = new Alert(Alert.AlertType.INFORMATION);
				    		inform.setHeaderText("Hai đỉnh trùng nhau! Hãy nhập lại!");
				    		inform.showAndWait();
						}
						else if (checkVertexExist(start, finish) ) {
							result.clear();
							Reset(graphView);
							SetUp(start, finish);
							Find(start,finish);	
							paths.clear();
							if (result.size() != 0 ) {
								paths.appendText("Các đường đi giữa hai đỉnh đã cho : \n");
								if (result.size() == 50) {
									Alert inform = new Alert(Alert.AlertType.INFORMATION);
						    		inform.setHeaderText("Số lượng đường đi lớn nên chỉ tìm giới hạn số lượng đường đi!!!");
						    		inform.showAndWait();
								}
								for (int i = 1; i<=result.size(); i++) {
									paths.appendText("Đường đi thứ " + i + " : " + result.get(i-1).get(0));
									for (int j = 1; j < result.get(i-1).size(); j++) 
										paths.appendText(" -> " + result.get(i-1).get(j));
									paths.appendText("\n");
								}  
								choosePath.getItems().clear();
								for (int i = 1; i<= numPath; i++) {
									choosePath.getItems().add(i);
								}
								choosePath.setOnAction(new EventHandler<ActionEvent>() {
									@Override
									public void handle(ActionEvent arg0) {
										int index = choosePath.getValue();
										Reset(graphView);
										changeColor(graphView, result.get(index - 1));
									}
								});
							}
							else {
								Alert inform = new Alert(Alert.AlertType.INFORMATION);
					    		inform.setHeaderText("Không có đường đi giữa hai đỉnh!!!");
					    		inform.showAndWait();
							}
						}
						else {
							Alert inform = new Alert(Alert.AlertType.INFORMATION);
				    		inform.setHeaderText("Đỉnh nhập vào không tồn tại! \nVui lòng nhập lại");
				    		inform.showAndWait();
						}
					}
				});
				
				Button close = new Button();
				close.setText(" Close ");
				close.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent arg0) {
						Reset(graphView);
						stage.close();
					}
				});
				grid.add(lb1, 0, 0);
				grid.add(begin, 1 , 0);
				grid.add(lb2, 0 , 1);
				grid.add(end, 1, 1);
				grid.add(lb3, 0 , 2);
				grid.add(OK2, 1, 2);
				grid.add(lb4, 0, 3);
				grid.add(OK1, 1 , 3);
				grid.add(lb5, 0 , 4);
				grid.add(choosePath, 1 , 4);
				grid.add(paths, 0 , 12, 3, 3);
				grid.add(close, 1, 22);
				grid.setHgap(10);
				grid.setVgap(10);
				grid.setPadding(new Insets(10, 10, 100, 10));
				grid.setMinSize(500, 200);
		    	stage.setScene(new Scene(grid, 500, 400));
				stage.setTitle("Tìm đường đi giữa hai đỉnh cho trước");
		        stage.show();	
			}
		});		
	}
	public void SetUp () {
		Set<Vertex<String>> set = graph.adjList.keySet();
        for (Vertex<String> key : set) {
        	ArrayList<String> list = new ArrayList<>();
        	for (Vertex v : graph.adjList.get(key).keySet()) {
        		list.add((String)v.element());
        	}
        	String tmp = key.element();
        	Adj.put(tmp, list);
        	checked.put(tmp, true);
        }
	}
	public void SetUp (String start, String end) {
		Traces.clear();
		Traces.add(start);
        checked.replace(start, false);
	}
	public void Find (String start, String end) {
        if (Adj.size() > 20) {
        	Alert inform = new Alert(Alert.AlertType.INFORMATION);
    		inform.setHeaderText("Số đỉnh lớn có thể tốn rất nhiều thời gian!!!");
    		inform.showAndWait();
        }
        TryFind (start, end);
        numPath = result.size();
	}
	public void TryFind (String s, String end) {
		for (int i = 0; i< Adj.get(s).size(); i++) {
			String tmp = Adj.get(s).get(i);
			if (tmp.equals(end)) {
				Traces.add(tmp);
				ArrayList<String> cloneArr = new ArrayList<>();
				cloneArr = (ArrayList<String>)Traces.clone();
				if (result.size() < 50) result.add(cloneArr); 
				else break;
				Traces.remove(tmp);
			}
			else {
				if (checked.get(tmp) == true) {
					Traces.add(tmp);
					checked.replace(tmp, false);
					if (result.size() < 50) TryFind (tmp , end);
					else break;
					Traces.remove(tmp);
					checked.replace(tmp, true);
				}
			}
		}
	}  
	
	public void BFS (String start, String end) {
		HashMap<String, String> parent = new HashMap<>();
		parent.put(start, null);
		minPath.add(start);
		ArrayList<String> minPathOp = new ArrayList<>();
        ArrayList<String> parents = new ArrayList<>();
        ArrayList<String> children = new ArrayList<>();
        parents.add(start);
        boolean breaken = false;
        do {
        	for (int i = 0; i< parents.size(); i++) {
        		String tmp1 = parents.get(i);
        		for (int j = 0; j < Adj.get(tmp1).size(); j++) {
        			String tmp2 = Adj.get(tmp1).get(j);
        			if (checked.get(tmp2) == true) {
        				checked.replace(tmp2, false);
        				children.add(tmp2);
        				parent.put(tmp2, tmp1);
        				if (tmp2.equals(end)) {
        					while (parent.get(tmp2) != null) {
        						minPathOp.add(tmp2);
        						tmp2 = parent.get(tmp2);
        					}
        					breaken = true;
        					break;
        				}
        			}
        		}
        	}
        	if (breaken == true) break;
        	parents.clear();
        	if (children.size() != 0) parents = (ArrayList<String>)children.clone();
        	else break;
        	children.clear();
        }
        while (parents.size() != 0);
        if (minPathOp.size() != 0) for (int i = minPathOp.size() - 1; i>= 0; i--) minPath.add(minPathOp.get(i)); 
	}  
	
	public void changeColor (GraphPanel<V, E> graphView, ArrayList<String> adj) {
		// Thay đổi màu đỉnh đầu
		String input = adj.get(0);
		Vertex<V> currentVertex = graphView.theGraph.vertices.get(input);
		VertexNode<V> currentVertexNode = graphView.vertexNodes.get(currentVertex);
		currentVertexNode.setStyle("-fx-fill: red");
		for (int i = 1; i< adj.size(); i++) {
			input = adj.get(i);
			Vertex<V> inputVertex = graphView.theGraph.vertices.get(input);
			VertexNode<V> inputVertexNode = graphView.vertexNodes.get(inputVertex);
			Edge<E, V> inputEdge = graphView.theGraph.adjList.get(currentVertex).get(inputVertex);
			EdgeNode<E, V> inputEdgeNode = graphView.edgeNodes.get(inputEdge);

			// thay đổi màu đỉnh và cạnh			
			if (i < adj.size() -1) inputVertexNode.setStyle("-fx-fill: yellow ");  
			else inputVertexNode.setStyle("-fx-fill: red ");  
			inputEdgeNode.setStyle("-fx-stroke: blue");
			inputEdgeNode.getAttachedArrow().setStyle("-fx-stroke: blue "); 
			currentVertex = inputVertex;
		}
	}
	//  Kiểm tra 2 đỉnh nhập vào có phải là các đỉnh trong đồ thị không
	public boolean checkVertexExist (String st, String fi) {
		int i = 0;
		Iterator<String> iterator1 = Adj.keySet().iterator();
		while (iterator1.hasNext()) {
			String tmp1 = iterator1.next();
			if (fi.equals(tmp1)) {
				i = 1;
				break;
			}
		}
		if (i == 0) return false;
		Iterator<String> iterator2 = Adj.keySet().iterator();
		while (iterator2.hasNext()) {
			String tmp2 = iterator2.next();
			if (st.equals(tmp2)) return true;
		}
		return false;
	}
	// Reset màu đồ thị về mặc định
	public void Reset(GraphPanel<V,E> graphView) {
		for (VertexNode<V> tmp : graphView.vertexNodes.values())
			tmp.setStyle("-fx-fill: #96d1cd");
		for (EdgeNode<E, V> tmp : graphView.edgeNodes.values()) {
			tmp.setStyle("-fx-stroke: #45597e");
			tmp.getAttachedArrow().setStyle(" -fx-stroke: #45597e");
		}
	}
}
package nhom2.button.addWeight;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Vector;
import nhom2.button.AddWeightButton;
import nhom2.button.InputButton;
import nhom2.graph.*;
import nhom2.graphview.GraphPanel;
import nhom2.window.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.util.Callback;

public class AddWeightButtonController implements Initializable {
	private static GraphEdgeList<String, String> g;
	private static GraphPanel<String, String> graphView;
	private static int n;
	private static String[][] Data;
	private static Vector<Vector <Integer> > weight;
	@FXML
	private Button activateTable;
	@FXML 
	private TableView Table;
	@FXML
	private TextArea Input;
	@FXML
	private Button Save;
	@FXML
	private Button Random;
	@FXML
	private Text nan;
	@FXML
	private Text update;
	@FXML
	private Button OFF;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		graphView = Main.getGraph();
		if(graphView!=null) {
			g = graphView.theGraph;
			n = g.NumOfVertex();
			if(g.isWeighted == true)
				nan.setVisible(false);
			update.setVisible(false);
			Input.setVisible(false);
			Save.setVisible(false);
		
			initTable(Table);
			setTable(Table);
			Table.setEditable(true);
		}
		
	}
	@FXML
	private void off(ActionEvent e) {
		if(g.isWeighted == false) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("Không thể xóa");
    		alert.setContentText("Đồ thị hiện tại không trọng số");
    		alert.showAndWait();
		}
		else if(informOff()) {
			nan.setVisible(true);
			update.setVisible(false);
			g.isWeighted = false;
			g.setWeightedFeature(0);
			
			setTable(Table);
			graphView.deleteWeightedFeature();
		}
		
	}
	@FXML
	private void activateTable(ActionEvent e) {
		Input.setVisible(false);
		Save.setVisible(false);
		Table.setVisible(true);
		initTable(Table);
		setTable(Table);
	}
	@FXML
	private void activateH(ActionEvent e) {
		Input.setVisible(true);
		Save.setVisible(true);
		Table.setVisible(false);
	}
	@FXML
	private void addRand(ActionEvent e) {
		g.setWeightedFeature();
		graphView.displayWeightAttribute();
		informSuccess();
	}
	@FXML 
	private void input(ActionEvent e) {
		String data = "";
		Input.commitValue();
		data = Input.getText();
	    Input.clear();
	    dataToGraph(data);
	}

	private void dataToGraph(String data) {
		Vector<Vector <Integer> > w = new Vector<Vector <Integer> > ();
		String[] split = data.split("\n");
		ArrayList<String> rows = new ArrayList<String>();
		for(String s: split){
		   rows.add(s);
		}
		Vector<Integer> v = new Vector<Integer> () ;
		for(String s: rows)  {
			v.clear();
	    	String[] t = s.split(" ");
	    	
	    	int size = t.length;
	    	for(int i=0;i<size;i++) { 
	    		v.add(Integer.parseInt(t[i]));
	    	}
	    	w.add(v);
	    }
		try {
			g.setWeightedFeature(w);
			graphView.displayWeightAttribute();
		    informSuccess();
		}
		catch(Exception e){
			e.printStackTrace();
			informInputError();
		}
	}
	private static void informInputError() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText("Không thể thêm trọng số");
		alert.setContentText("Ma trận không khớp!");
		alert.showAndWait();
	}
	private static void informSuccess() {
	
		Alert inform = new Alert(Alert.AlertType.INFORMATION);
		inform.setHeaderText("Thêm trọng số thành công!");
		inform.showAndWait();
		AddWeightButton.stage.close();
	}
	@FXML
	private void inputFromExternal(ActionEvent e) {
		char [] data = new char [10000000];
		boolean isOpened = true;
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Mở tệp trọng số");
			File file = fileChooser.showOpenDialog(InputButton.stage);
			if(fileChooser != null) {
				isOpened = true;
				try (BufferedReader reader = new BufferedReader(new FileReader(file))){
					reader.read(data);
			    }
				catch (IOException ex) {
					ex.printStackTrace();
			    }
			}
			
		}
		catch (Exception ex){
			informNull();
			isOpened = false;
		}
		
		if(isOpened) {
			Input.setText(String.valueOf(data));
			String datas = "";
			Input.commitValue();
			datas = Input.getText();
		    Input.clear();
		    dataToGraph(datas);
		}
	}
	private static boolean informOff() {
		Alert inform = new Alert(Alert.AlertType.CONFIRMATION);
		inform.setHeaderText("Bạn có chắc muốn tắt trọng số?");
		Optional<ButtonType> result = inform.showAndWait();
		return result.get() == ButtonType.OK;
	}
	private static void informNull() {
		Alert inform = new Alert(Alert.AlertType.INFORMATION);
		inform.setHeaderText("Chưa mở file!");
		inform.showAndWait();
	}
	public void initTable(TableView table) {
		 
	      TableColumn[] Tc = new TableColumn[n+1] ;
	      Vector <String> vecLabel = new Vector<String> ();
	      for (Entry<String, Vertex<String>> entry : g.vertices.entrySet()) {
	    	  vecLabel.add(entry.getKey());
	      }
	      double with = (table.getPrefWidth() - 60)/ ( n ) < 25 ? 25 : (table.getPrefWidth() - 60)/ ( n );
	      for (int i = 0; i <= n; i++) {
	    	  final int colNo = i;
	    	  if(i==0) {
	    		  Tc[i] = new TableColumn<String[],String>("");
	    		  Tc[i].setPrefWidth(45);
	    	  }
	    	  else {
	    	  
	    	  Tc[i] = new TableColumn<String[],String>(vecLabel.get(i - 1));
	          Tc[i].setPrefWidth(with);
	    	  }
	    	  
	    	  Tc[i].setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
		           @Override
		           public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
		        	   return new SimpleStringProperty((p.getValue()[colNo]));
		           }
		      });
	    	  Tc[i].setCellFactory(TextFieldTableCell.forTableColumn()); 
	    	  Tc[i].setOnEditCommit(new EventHandler<CellEditEvent>() {
			
					public void handle(CellEditEvent event) {
						if(event.getOldValue() == "-") {
							informError();
							setTable(Table);
						}
						else {
							ObservableList selectedCells = Table.getSelectionModel().getSelectedCells();
							TablePosition tablePosition = (TablePosition) selectedCells.get(0);
							updateOnCommit(event,tablePosition.getRow(),tablePosition.getColumn());
						}
						
					}
	    	  });
	    	  Tc[i].setStyle( "-fx-alignment: CENTER;");
	      }
	      table.getColumns().clear();
	      table.getColumns().addAll(Tc);
	}
	public void updateOnCommit(CellEditEvent event,int row, int col) {
		Data[row][col] = event.getNewValue().toString();
		Table.getItems().clear();
		Table.setItems(FXCollections.observableArrayList(Data));
		nan.setVisible(false);
		update.setVisible(true);
		weight.get(row).set(col - 1,Integer.parseInt(event.getNewValue().toString()));
//		System.out.println(weight);
		g.setWeightedFeature(weight);
		
		graphView.displayWeightAttribute();
		
	}
	public void informError() {
		Alert inform = new Alert(Alert.AlertType.INFORMATION);
		inform.setHeaderText("Cạnh không tồn tại!");
		inform.showAndWait();
	}
	public void setTable(TableView table) {
		Data = new String[n][n+1];
		Map <Integer, String> vecLabel = new HashMap <Integer, String>();
	
		int index1 = 0;
	    for (Entry<String, Vertex<String>> entry : g.vertices.entrySet()) {
	    	vecLabel.put(index1, entry.getKey());
	    	index1++;
	    }
		int index = 0;
		Map<Edge<String, String>, Integer> m = g.edgeWeight;
		if(m == null) nan.setVisible(true);
		for (String label : vecLabel.values()) {
			Data[index][0] = label;
			for(int j=0;j<vecLabel.size();j++) {
			
				if( g.areAdjacent(g.vertices.get(label), g.vertices.get(vecLabel.get(j))) ) {
					Edge e = g.getEdge(g.vertices.get(label), g.vertices.get(vecLabel.get(j)));
					if(m != null)
						Data[index][j+1] = m.get(e).toString();
					else Data[index][j+1] = "0";
				}
				else {
					Data[index][j+1] = "-";
				}
			}
			index++;
		}
		weight = new Vector<Vector <Integer> > ();
		for(String[] S : Data) {
			Vector<Integer> v = new Vector<Integer>();
			for(int i =1;i<=n;i++) {
				if(S[i] == "-") v.add(0);
				else v.add(Integer.parseInt(S[i]));
			}
			weight.add(v);
		}
		ObservableList<String[]> data1 = FXCollections.observableArrayList(Data);
		table.setItems(data1);
	}
}

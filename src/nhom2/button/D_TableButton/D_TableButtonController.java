package nhom2.button.D_TableButton;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.Map.Entry;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import nhom2.button.ShortestPathButton;
import nhom2.graph.Edge;
import nhom2.graph.Vertex;
import nhom2.window.Main;

public class D_TableButtonController implements Initializable {
	private static int n;
	private static String alertColor;
	private static String[][] data = null ;	
	private static Map<Integer, Integer> color;
	private static Map <Integer, String> vecLabel;
	@FXML 
	private TableView Table;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		n=ShortestPathButton.getNumbVertex();
		initTable();
		setTable();
	}
	 Callback factory = new Callback<TableColumn<String[], String>, TableCell<String[], String>>(){
		    public TableCell<String[], String> call(TableColumn<String[], String> param) {
		        return new TableCell<String[], String>() {
		            @Override
		            public void updateIndex(int i) {
		                super.updateIndex(i);
		            }
		            
		            @Override
		            protected void updateItem(String item, boolean empty) {
		                super.updateItem(item, empty);
		                // assign item's toString value as text
		                if (empty || item == null) {
		                    setText(null);
		                } else {
		                    setText(item.toString());
		                    int colIndex = this.getTableView().getColumns().indexOf(this.getTableColumn());
		                    if(color.get(this.getIndex()) == colIndex ) {
		                    	this.setStyle("-fx-background-color: #aa2222; -fx-text-fill:white");
		                    }
		                }
		            }
		        };
		    }
		};
	public void initTable() {
		 
	      TableColumn[] Tc = new TableColumn[n+1] ;

		  double width = (Table.getPrefWidth() - 60)/ ( n ) < 60.0 ? 60.0 : (Table.getPrefWidth() - 60)/ ( n );
	      for (int i = 0; i <= n; i++) {
	    	  final int colNo = i;
	    	  if(i==0) {
	    		  Tc[i] = new TableColumn<String[],String>("Step");
	    		  Tc[i].setPrefWidth(60);
	    	  }
	    	  else {
	    	  Tc[i] = new TableColumn<String[],String>(vecLabel.get(i));
	          Tc[i].setPrefWidth(width);
	    	  }
	    	  
	    	  Tc[i].setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
		           @Override
		           public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
		        	   return new SimpleStringProperty((p.getValue()[colNo]));
		           }
		      });
	    	  Tc[i].setStyle( "-fx-alignment: CENTER;");

	    	  Tc[i].setCellFactory(factory);
	      }
	      Table.getColumns().clear();
	      Table.getColumns().addAll(Tc);
	}
	public void setTable() {
		if(data != null) {
			
			Map<String, Integer> vecLabelRev = new HashMap<String,Integer>();
			for(int i=1;i<n+1;i++) {
				vecLabelRev.put( data[0][i],i);
			}
			
			color = new HashMap <Integer, Integer>();
			String[][] data1 = new String[n][n+1];
			for(int i=1;i<n+1;i++) {
				color.put(i-1, vecLabelRev.get(data[i][0]) );
				data1[i-1][0] = "Step " + Integer.toString(i-1);
				for(int j=1;j<n+1;j++) {
					data1[i-1][j] = data[i][j];
				}
			}

			ObservableList<String[]> Data = FXCollections.observableArrayList(data1);
			Table.setItems(Data);
		}
	}
	public static void setData(String[][] s) {
		data = s;
	}
	public static void setVecLabel(Map <Integer, String> m ){
		vecLabel = m;
	}
}
